package com.yonyou.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.util.BussnissException;
import com.yonyou.util.RmConfigUtil;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.sql.SqlTableUtil;

/**
 * 
* @ClassName: RmDictReferenceUtil 
* @Description: 数据字典工具类
* @author 博超
* @date 2016年12月26日 
*
 */
public class RmDictReferenceUtil {
	
	// 主键(两表通用)
	public static final String ID="ID";
	// 数据字典类型表（主表）
	public static final String TYPE_TABLE_NAME = "RM_CODE_TYPE";

	public static final String TYPE_TYPE_KEYWORD = "TYPE_KEYWORD";
	public static final String TYPE_NAME = "NAME";
	public static final String TYPE_USABLE_STATUS = "USABLE_STATUS";
	
	// 数据字典数据表（子表）
	public static final String DATA_TABLE_NAME = "RM_CODE_DATA";

	public static final String DATA_CODE_TYPE_ID = "CODE_TYPE_ID";
	public static final String DATA_DATA_KEY = "DATA_KEY";
	public static final String DATA_DATA_VALUE = "DATA_VALUE";
	public static final String DATA_USABLE_STATUS = "USABLE_STATUS";
	
	//ConcurrentHashMap<类型关键字,ConcurrentHashMap<关键字,数据值>>
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,String>> dictReferenceMap = null;
	
	//redis缓存
	private static CacheManager cacheManager = (CacheManager) SpringContextUtil.getBean("cacheManager");
	private static String REDIS_KEY = "DICTREFERENCECACHE";
	
	/**
	 * 
	* @Title: clear 
	* @Description: 清空服务器数据字典缓存
	 */
	public static void clear(){
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.removeCache(REDIS_KEY);
		}else{
			dictReferenceMap=null;
		}
	}
	
	/**
	 * 
	* @Title: initDictReference 
	* @Description: 从数据库初始化所有数据字典信息
	 */
	public static void init(){
		
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> dictReferenceMapTemp = new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();

		SqlTableUtil sql = new SqlTableUtil(DATA_TABLE_NAME, "data");
		sql.appendSelFiled("*").appendSelFiled("type.TYPE_KEYWORD").appendJoinTable(TYPE_TABLE_NAME +" type","data."+DATA_CODE_TYPE_ID+" = type."+ID).appendWhereAnd("data.DR = 0 AND data.USABLE_STATUS = 1 AND type.DR = 0 AND type.USABLE_STATUS = 1").appendOrderBy(ID);
		List<Map<String, Object>> lDictReference = BaseDao.getBaseDao().query(sql);
		System.out.println("lDictReference" + lDictReference);
		
		for (Iterator<Map<String, Object>> itLDictReference = lDictReference.iterator(); itLDictReference.hasNext();) {
			Map<String, Object> dictData= itLDictReference.next();
			ConcurrentHashMap<String, String> newDictData = new ConcurrentHashMap<String, String>();
			for (String key : dictData.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (dictData.get(key)== null?"":dictData.get(key).toString());
				newDictData.put(key, valueStr);
			}
			
			String typeKeyword = newDictData.get(TYPE_TYPE_KEYWORD);

			if(!dictReferenceMapTemp.containsKey(typeKeyword)){
				dictReferenceMapTemp.put(typeKeyword, new ConcurrentHashMap<String,String>()); 
			}
			dictReferenceMapTemp.get(typeKeyword).put(newDictData.get(DATA_DATA_KEY), newDictData.get(DATA_DATA_VALUE));
		}
		System.out.println("dictReferenceMap" + dictReferenceMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY, dictReferenceMapTemp);
		}else{
			dictReferenceMap = dictReferenceMapTemp;
		}
	}
	
	/**
	 * 
	* @Title: getDictReference 
	* @Description: 根据类型关键字获取相关数据字典信息 
	* @param typeKeyword 类型关键字
	* @return ConcurrentHashMap<类型关键字,ConcurrentHashMap<数据关键字,数据值>>
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,String> getDictReference(String typeKeyword) throws BussnissException{
		
		if (RmConfigUtil.getIsClusterMode()) {
			if (!cacheManager.exists(REDIS_KEY))
				init();
		} else {
			if (null == dictReferenceMap) {
				synchronized (RmDictReferenceUtil.class) {
					if (null == dictReferenceMap)
						init();
				}
			}
		}
		
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> dictReferenceMapTemp = new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();
		if(RmConfigUtil.getIsClusterMode()){
			dictReferenceMapTemp = cacheManager.get(REDIS_KEY);
		}else{
			dictReferenceMapTemp = dictReferenceMap;
		}
		if(!dictReferenceMapTemp.containsKey(typeKeyword)){
			throw new BussnissException("数据字典中类型关键字" + typeKeyword + "不存在!");
		}
		return dictReferenceMapTemp.get(typeKeyword);

	}
	
	/**
	 * 
	* @Title: transByDict 
	* @Description: 将结果集中需要根据数据字典翻译的字段进行加上翻译内容 
	* @param lResult 结果集
	* @param dataSourceCode 数据源编码
	* @return List<Map<String, Object>> 翻译后结果集
	* @throws BussnissException
	 */
	public static List<Map<String, Object>> transByDict(List<Map<String, Object>> lResult,String dataSourceCode) throws BussnissException {
		
		ConcurrentHashMap<String, String> mDataSource= DataSourceUtil.getDataSource(dataSourceCode);
		String displayFields = mDataSource.get(DataSourceUtil.DATASOURCE_DISPLAY_FIELD);
		Map<String, ConcurrentHashMap<String, String>> fieldsInfoMap = MetaDataUtil.getMetaDataFields(mDataSource.get(DataSourceUtil.DATASOURCE_METADATA_CODE));
		
		//FIXME 不能用','，暂定用'&'
		String[] arrDisFields = displayFields.split("&");
		//要翻译字段配置：Tname(字段名[,数据字典key])
		for (String disField : arrDisFields) {
			
			if (disField.startsWith("TNAME") && disField.endsWith(")")) {
				String fieldTemp = disField.substring("TNAME(".length(), disField.length() - 1);
				String[] temp = fieldTemp.split(",");
				
				for (Iterator<Map<String, Object>> itLResult = lResult.iterator(); itLResult.hasNext();) {
					Map<String, Object> mResult = itLResult.next();
					String fieldName = temp[0];
					String dictKey = "";
					Object tempValue;
					if (mResult.containsKey(fieldName)) {
						
						if (1 == temp.length) {// 未配置key，默认取元数据中配置
							dictKey = fieldsInfoMap.get(fieldName).get(MetaDataUtil.FIELD_INPUT_FORMART);
						} else if (2 == temp.length) {
							dictKey = temp[1];
						}else{
							throw new BussnissException("数据源-" + dataSourceCode+ "-中展示字段-" + disField + "-为非法配置");
						}
						
						//如果数据字典中未进行配置，则展示原始数据，提高友好性
						tempValue = mResult.get(fieldName);
						try {
							if (getDictReference(dictKey).containsKey(mResult.get(fieldName).toString())) {
								tempValue = getDictReference(dictKey).get(mResult.get(fieldName).toString());
							}
						} catch (Exception e) {
							// TODO: handle exception
						} finally {
							mResult.put(disField, tempValue);
						}
					
					} else {
						throw new BussnissException("数据源-" + dataSourceCode+ "-中展示字段-" + disField + "-未在返回字段中配置");
					}
				}
				
			}
				
		}
		return lResult;
		
	}
	
}

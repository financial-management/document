package com.yonyou.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.business.filter.ReadDataFilter;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.util.BussnissException;

import com.yonyou.util.CloneUtil;
import com.yonyou.util.RmConfigUtil;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.sql.SqlTableUtil;

/**
 * 
* @ClassName: MetaDataUtil 
* @Description: 元数据工具类-可根据元数据编码获取元数据信息 
* @author 博超
* @date 2016年12月26日 
*
 */
public class MetaDataUtil {
	
	// 主键(两表通用)
	public static final String ID="ID";

	// 元数据定义表
	public static final String METADATA_TABLE_NAME = "CD_METADATA";
	
	// 元数据编码
	public static final String METADATA_DATA_CODE = "DATA_CODE";

	//  元数据名称
	public static final String METADATA_DATA_NAME = "DATA_NAME";
	public static final String METADATA_DATA_DISNAME = "DATA_DISNAME";
	public static final String METADATA_DATA_RESOURCE = "DATA_RESOURCE";
	public static final String METADATA_SUMMARY = "SUMMARY";
	public static final String METADATA_DATA_TYPE= "DATA_TYPE";
	public static final String METADATA_SYSTEM_FLAG= "SYSTEM_FLAG";

	// 元数据字段表
	public static final String FIELD_TABLE_NAME = "CD_METADATA_DETAIL";

	public static final String FIELD_METADATA_ID = "METADATA_ID";
	public static final String FIELD_CODE = "FIELD_CODE";
	public static final String FIELD_NAME = "FIELD_NAME";
	public static final String FIELD_TYPE = "FIELD_TYPE";
	public static final String FIELD_LENGTH = "FIELD_LENGTH";
	public static final String FIELD_NULL_FLAG = "NULL_FLAG";
	public static final String FIELD_KEY_FLAG = "KEY_FLAG";
	public static final String FIELD_SORT = "SORT";
	public static final String FIELD_INPUT_TYPE = "INPUT_TYPE";
	public static final String FIELD_INPUT_FORMART = "INPUT_FORMART";
	public static final String FIELD_INPUT_HTML = "INPUT_HTML";
	public static final String FIELD_SUMMARY = "SUMMART";
	
	
	//ConcurrentHashMap<元数据编码（真实表表名）,ConcurrentHashMap<字段名,字段值>>
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,String>> metadataMap = null;
		//	new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();
	
	//ConcurrentHashMap<元数据编码（真实表表名）,ConcurrentHashMap<字段编码（真实表字段名）,ConcurrentHashMap<子表字段名,子表字段值>>>
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>> fieldsMap = null;
	
	//redis缓存
	private static CacheManager cacheManager = (CacheManager) SpringContextUtil.getBean("cacheManager");
	private static String REDIS_KEY_METADATA = "METADATACACHE";
	private static String REDIS_KEY_FIELDS = "FIELDSCACHE";
	
	/**
	 * 
	* @Title: init 
	* @Description: 从数据库初始化所有元数据
	 */
	public static void init(){
		initMetaData();
		initFieldsMap();
	}
	
	/**
	 * 
	* @Title: clear 
	* @Description: 清空服务器元数据缓存
	 */
	public static void clear(){
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.removeCache(REDIS_KEY_METADATA);
			cacheManager.removeCache(REDIS_KEY_FIELDS);
		}else{
			metadataMap=null;
			fieldsMap=null;
			}
	}
	
	/**
	 * 
	* @Title: initMetaData 
	* @Description: 从数据库初始化所有元数据表信息
	 */
	public static void initMetaData() {
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> metadataMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();

		SqlTableUtil sql = new SqlTableUtil(METADATA_TABLE_NAME, "").appendSelFiled("*").appendWhereAnd("DR = 0");
		List<Map<String, Object>> lMetaData = BaseDao.getBaseDao().query(sql);
		System.out.println("lMetaData" + lMetaData);

		for (Iterator<Map<String, Object>> itLMetaData = lMetaData.iterator(); itLMetaData.hasNext();) {
			Map<String, Object> metaData = itLMetaData.next();
			ConcurrentHashMap<String, String> newMetaData = new ConcurrentHashMap<String, String>();
			for (String key : metaData.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (metaData.get(key)== null?"":metaData.get(key).toString());
				newMetaData.put(key, valueStr);
			}
			String dateCode = newMetaData.get(METADATA_DATA_CODE);
			metadataMapTemp.put(dateCode, newMetaData);
		}
		System.out.println("metadataMap" + metadataMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY_METADATA, metadataMapTemp);
		}else{
			metadataMap = metadataMapTemp;
			}
	}
	
	/**
	 * 
	* @Title: initFieldsMap 
	* @Description: 从数据库初始化所有元数据字段表信息
	 */
	public static void initFieldsMap(){
		ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> fieldsMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>>();

		SqlTableUtil sql = new SqlTableUtil(FIELD_TABLE_NAME, "field");
		sql.appendSelFiled("*").appendJoinTable(METADATA_TABLE_NAME +" meta","field."+FIELD_METADATA_ID+"= meta."+ID).appendSelFiled("meta", METADATA_DATA_CODE).appendWhereAnd("meta.DR = 0 AND field.DR = 0").appendOrderBy(FIELD_METADATA_ID);
		List<Map<String, Object>> lFieldsData = BaseDao.getBaseDao().query(sql);
		System.out.println("lFieldsData" + lFieldsData);
		
		for (Iterator<Map<String, Object>> itLFieldsData = lFieldsData.iterator(); itLFieldsData.hasNext();) {
			Map<String, Object> fieldsData= itLFieldsData.next();
			ConcurrentHashMap<String, String> newFieldsData = new ConcurrentHashMap<String, String>();
			for (String key : fieldsData.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (fieldsData.get(key)== null?"":fieldsData.get(key).toString());
				newFieldsData.put(key, valueStr);
			}
			
			String fieldCode = newFieldsData.get(FIELD_CODE);
			String dateCode = newFieldsData.get(METADATA_DATA_CODE);
			if(!fieldsMapTemp.containsKey(dateCode)){
				fieldsMapTemp.put(dateCode, new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>()); 
			}
			fieldsMapTemp.get(dateCode).put(fieldCode, newFieldsData);
		}
		System.out.println("fieldsMap" + fieldsMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY_FIELDS, fieldsMapTemp);
		}else{
			fieldsMap = fieldsMapTemp;
			}
	}
	
	/**
	 * 
	* @Title: getMetaData 
	* @Description: 根据元数据编码获取元数据信息 
	* @param metaDataCode 元数据编码
	* @return ConcurrentHashMap<元数据字段名,元数据字段值>
	 * @throws BussnissException 
	 */
	public static ConcurrentHashMap<String, String>  getMetaData(String metaDataCode) throws BussnissException{

		if(RmConfigUtil.getIsClusterMode()){	
			if(!cacheManager.exists(REDIS_KEY_METADATA))
				initMetaData();
		}else{
			if(null == metadataMap){
				synchronized (MetaDataUtil.class) {
					if(null == metadataMap)
						initMetaData();
					}
				}
			}
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> metadataMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		if (RmConfigUtil.getIsClusterMode()) {
			metadataMapTemp = cacheManager.get(REDIS_KEY_METADATA);
		} else {
			metadataMapTemp = metadataMap;
		}
		if(!metadataMapTemp.containsKey(metaDataCode)){
			throw new BussnissException("元数据编码"+metaDataCode+"不存在！");
		}
		//返回深拷贝对象
		return CloneUtil.clone(metadataMapTemp.get(metaDataCode));
	}
	
	/**
	 * 
	* @Title: getMetaDataFields 
	* @Description: 根据元数据编码获取元数据字段信息 
	* @param metaDataCode 元数据编码
	* @return ConcurrentHashMap<元数据编码,ConcurrentHashMap<元数据字段名,元数据字段值>>
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,ConcurrentHashMap<String,String>>  getMetaDataFields(String metaDataCode) throws BussnissException{
		
		if(RmConfigUtil.getIsClusterMode()){
			if(!cacheManager.exists(REDIS_KEY_FIELDS))
				initFieldsMap();
		}else{
			if(null == fieldsMap){
				synchronized(MetaDataUtil.class){
					if(null == fieldsMap)
						initFieldsMap();
				}
			}
		}
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> fieldsMapTemp = new ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>();
		if(RmConfigUtil.getIsClusterMode()){
			fieldsMapTemp = cacheManager.get(REDIS_KEY_FIELDS);
		}else{
			fieldsMapTemp = fieldsMap;
		}
		if(!fieldsMapTemp.containsKey(metaDataCode)){
			throw new BussnissException("元数据编码"+metaDataCode+"不存在！");
		}
		//返回深拷贝对象
		return CloneUtil.clone(fieldsMapTemp.get(metaDataCode));

	}
	
	/**
	 * 
	* @Title getProcessedFields 
	* @Description 获取经查询过滤器（数据源处配置）处理后的元数据字段信息
	* @param metaDataCode
	* @return
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,ConcurrentHashMap<String,String>>  getProcessedFields(String metaDataCode,String fliterName) throws BussnissException{
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> fieldsMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		fieldsMapTemp = getMetaDataFields(metaDataCode);
		if(!"".equals(fliterName)){
			try {
				ReadDataFilter fliter = (ReadDataFilter) Class.forName(fliterName).newInstance();
				fliter.doColFilter(fieldsMapTemp);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fieldsMapTemp;
	}
	
	/**
	 * 
	* @Title: getFiledNameMap 
	* @Description: 根据元数据编码获取对应表所有字段中英对照-未设置中文则直接使用原字段名
	* @param metaDataCode 元数据编码
	* @return Map<字段英文名,字段中文名>
	* @throws BussnissException
	 */
	public static Map<String,String> getFiledNameMap(String metaDataCode,String fliterName) throws BussnissException{
		
		Map<String,String> filedNameMap =new HashMap<String,String>();
		Map<String, ConcurrentHashMap<String, String>> metaDataFieldsMap = getProcessedFields(metaDataCode, fliterName);
		for (String filed : metaDataFieldsMap.keySet()) {
			//初始化时已经将null值转化为空字符串，无需再考虑
			String filedName = metaDataFieldsMap.get(filed).get(FIELD_NAME).trim();
			filedNameMap.put(filed, "" == filedName ? filed : filedName);
		}
		return filedNameMap;
	}
	
	/**
	 * 
	* @Title: getFiledTypeMap 
	* @Description: 根据元数据编码获取对应表所有字段数据库类型-未设置中文则直接使用原字段名
	* @param metaDataCode 元数据编码
	* @return Map<字段英文名,字段数据库类型>
	* @throws BussnissException
	 */
	public static Map<String,String> getFiledTypeMap(String metaDataCode) throws BussnissException{
		
		Map<String,String> filedTypeMap =new HashMap<String,String>();
		Map<String, ConcurrentHashMap<String, String>> metaDataFieldsMap =getMetaDataFields(metaDataCode);
		for (String filed : metaDataFieldsMap.keySet()) {
			//初始化时已经将null值转化为空字符串，无需再考虑
			String filedType = metaDataFieldsMap.get(filed).get(FIELD_TYPE).trim();
			filedTypeMap.put(filed, "" == filedType ? filed : filedType);
		}
		return filedTypeMap;
	}
}

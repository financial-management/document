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
import com.yonyou.util.page.PageUtil;
import com.yonyou.util.sql.SQLUtil;
import com.yonyou.util.sql.SqlTableUtil;

/**
 * 
* @ClassName DataSourceUtil 
* @Description 数据源工具类-可根据数据编码源获取数据源信息
* @author 博超
* @date 2016年12月26日 
*
 */

public class DataSourceUtil {

	/**
	 * ConcurrentHashMap<数据源编码,<表字段名,表字段值>>
	 */
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMap = null;
	
	//redis缓存
	private static CacheManager cacheManager = (CacheManager) SpringContextUtil.getBean("cacheManager");
	private static String REDIS_KEY = "DATASOURCECACHE";

	//表及表字段常量
	public static final String DATASOURCE_TABLE_NAME = "CD_DATASOURCE";
	public static final String DATASOURCE_ID = "ID";
	public static final String DATASOURCE_CODE = "DATASOURCE_CODE";
	public static final String DATASOURCE_NAME = "DATASOURCE_NAME";
	public static final String DATASOURCE_DISNAME = "DATASOURCE_DISNAME";
	public static final String DATASOURCE_METADATA_ID = "METADATA_ID";
	public static final String DATASOURCE_METADATA_CODE = "METADATA_CODE";
	public static final String DATASOURCE_FILTER_RULE = "FILTER_RULE";
	public static final String DATASOURCE_GROUPBY = "GROUPBY";
	public static final String DATASOURCE_ORDERBY = "ORDERBY";
	public static final String DATASOURCE_JOIN_TABLES = "JOIN_TABLES";
	public static final String DATASOURCE_JOIN_ON = "JOIN_ON";
	public static final String DATASOURCE_REPLACEDATA = "REPLACEDATA";
	public static final String DATASOURCE_RETURN_FIELD = "RETURN_FIELD";
	public static final String DATASOURCE_DISPLAY_FIELD = "DISPLAY_FIELD";
	public static final String DATASOURCE_QUERY_FIELD = "QUERY_FIELD";
	public static final String DATASOURCE_QUERY_FILTER_CLASS = "QUERY_FILTER_CLASS";
	public static final String DATASOURCE_INSERT_FILTER_CLASS = "INSER_FILTER_CLASS";
	public static final String DATASOURCE_JS_SCRIPT = "JS_SCRIPT";
	public static final String DATASOURCE_ALIS_TABLE = "ALIS_TABLE";
	public static final String DATASOURCE_SYSTEM_FLAG= "SYSTEM_FLAG";
	public static final String DATASOURCE_MAINTAIN_FIELD = "MAINTAIN_FIELD";
	public static final String DATASOURCE_UPDATE_FIELD = "UPDATE_FIELD";//独立修改页面字段
	public static final String DATASOURCE_PROXY_PAGE_CODE ="PROXY_PAGE_CODE";  //代理页面编码
	
	public static final String DATASOURCE_MAINTAIN_REPLACEDATA="REPLACEDATA";
	/**
	 *
	* @Title: init 
	* @Description: 从数据库初始化所有数据源 
	 */
	public static void init() {
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		
		// MetaDataUtil.getMetaData("").get(MetaDataUtil.METADATA_DATE_CODE);
		SqlTableUtil sqlEntity = new SqlTableUtil(DATASOURCE_TABLE_NAME, "").appendSelFiled("*").appendWhereAnd("DR = 0");
		List<Map<String, Object>> lDataSource = BaseDao.getBaseDao().query(sqlEntity);
		System.out.println("lDataSource" + lDataSource);
		
		for (Iterator<Map<String, Object>> itLDataSource = lDataSource.iterator(); itLDataSource.hasNext();) {
			Map<String, Object> dataSource = itLDataSource.next();
			ConcurrentHashMap<String, String> newDataSource = new ConcurrentHashMap<String, String>();
			for (String key : dataSource.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (dataSource.get(key)== null?"":dataSource.get(key).toString());
				newDataSource.put(key, valueStr);
			}
			String datasourceCode = newDataSource.get(DATASOURCE_CODE);
			dataSourceMapTemp.put(datasourceCode, newDataSource);
		}
		System.out.println("dataSourceMap" + dataSourceMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY, dataSourceMapTemp);
		}else{
			dataSourceMap = dataSourceMapTemp;
		}
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: 初始化指定数据源编码的数据源 
	* @param datasourceCode 数据源编码
	 */
	public static void init(String datasourceCode) {
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		// MetaDataUtil.getMetaData("").get(MetaDataUtil.METADATA_DATE_CODE);
//		if(null == dataSourceMap)
//			dataSourceMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		
		SqlTableUtil sqlEntity = new SqlTableUtil(DATASOURCE_TABLE_NAME, "").appendSelFiled("*").appendWhereAnd("DR = 0");
		sqlEntity.appendWhereAnd(DATASOURCE_CODE + "= '" + datasourceCode + "'");
		List<Map<String, Object>> lDataSource = BaseDao.getBaseDao().query(sqlEntity);
		System.out.println("lDataSource" + lDataSource);
		
		for (Iterator<Map<String, Object>> itLDataSource = lDataSource.iterator(); itLDataSource.hasNext();) {
			Map<String, Object> dataSource = itLDataSource.next();
			ConcurrentHashMap<String, String> newDataSource = new ConcurrentHashMap<String, String>();
			for (String key : dataSource.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (dataSource.get(key)== null?"":dataSource.get(key).toString());
				newDataSource.put(key, valueStr);
			}
			dataSourceMapTemp.put(datasourceCode, newDataSource);
			if(RmConfigUtil.getIsClusterMode()){
				cacheManager.set(REDIS_KEY, dataSourceMapTemp);
				}else{
					dataSourceMap = dataSourceMapTemp;
				}
		}
		System.out.println("dataSourceMap" + dataSourceMapTemp);
		
	}

	/**
	 * 
	* @Title: clear 
	* @Description: 清除服务器数据源缓存 
	 */
	public static void clear(){
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.removeCache(REDIS_KEY);
		}else{
			dataSourceMap = null;
			}
	}
	
	/**
	 * 
	* @Title: getDataSource 
	* @Description: 根据数据源编码获取数据源信息 
	* @param datasourceCode 数据源编码
	* @return ConcurrentHashMap<数据字段名,数据源字段值> 数据源信息 
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,String>  getDataSource(String datasourceCode) throws BussnissException{
		
		if(RmConfigUtil.getIsClusterMode()){	
			if(!cacheManager.exists(REDIS_KEY))
				init();
		}else{
			if(null == dataSourceMap){
				synchronized (DataSourceUtil.class) {
					if(null == dataSourceMap)
						init();
					}
				}	
			}
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();	
		if(RmConfigUtil.getIsClusterMode()){
			dataSourceMapTemp = cacheManager.get(REDIS_KEY);
			}else{
				dataSourceMapTemp = dataSourceMap;
			}
		if(!dataSourceMapTemp.containsKey(datasourceCode)){
			throw new BussnissException("数据源编码"+datasourceCode+"不存在！");
		}
		return dataSourceMapTemp.get(datasourceCode);
	}
	
	/**
	 * 
	* @Title: dataSourceToSQL 
	* @Description: 根据数据源编码组装SqlTableUtil实例——用于查询,提供字段中英对照 
	* @param datasourceCode 数据源编码
	* @return SqlTableUtil实例
	* @throws BussnissException
	 */
	public static SqlTableUtil dataSourceToSQL(String datasourceCode)
			throws BussnissException {

		ConcurrentHashMap<String, String> dataSourceMap = getDataSource(datasourceCode);
		String alis_table =dataSourceMap.get(DATASOURCE_ALIS_TABLE);
		if(SQLUtil.isEmpty(alis_table)){
			alis_table=dataSourceMap.get(DATASOURCE_METADATA_CODE);
		}
		SqlTableUtil sqlEntity = new SqlTableUtil(dataSourceMap.get(DATASOURCE_METADATA_CODE), alis_table);
		sqlEntity.init(dataSourceMap.get(DATASOURCE_RETURN_FIELD),dataSourceMap.get(DATASOURCE_DISPLAY_FIELD),dataSourceMap.get(DATASOURCE_FILTER_RULE));
		
		//FIXME 暂未考虑常量值字段
		
		//关联表
		String joinStr = dataSourceMap.get(DATASOURCE_JOIN_TABLES);
		if(joinStr != null && joinStr.length()>0){
			sqlEntity.appendJoinTable(dataSourceMap.get(DATASOURCE_JOIN_TABLES),dataSourceMap.get(DATASOURCE_JOIN_ON));
		}
		
		//排序
		String orderStr = dataSourceMap.get(DATASOURCE_ORDERBY);
		if (null != orderStr && orderStr.length() > 0) {
			String[] orderByTemp = orderStr.split(",");
			for (int i = 0; i < orderByTemp.length; i++) {
				String[] orderByStr = orderByTemp[i].trim().split("\\s+");
				if (orderByStr.length == 1) {
					sqlEntity.appendOrderBy(orderByStr[0]);
				} else if (orderByStr.length == 2) {
					sqlEntity.appendOrderBy(orderByStr[0], orderByStr[1]);
				} else {
					//TODO
					throw new BussnissException("数据源ORDER_BY字段配置错误");
				}
			}
		}
		
		//分组
		String groupStr = dataSourceMap.get(DATASOURCE_GROUPBY);
		if (null != groupStr && groupStr.length() > 0) {
//			FIXME 分组表达式中可能包括函数，不能使用','分割字段，暂定'&'
			String[] groupByTemp = groupStr.split("&");
			for (int i = 0; i < groupByTemp.length; i++) {
				String[] groupByStr = groupByTemp[i].trim().split("\\.");
				if (groupByStr.length == 1) {
					sqlEntity.appendGroup(groupByStr[0]);
				} else if (groupByStr.length == 2) {
					sqlEntity.appendGroup(groupByStr[1], groupByStr[0]);
				} else {
					//TODO
					throw new BussnissException("数据源GROUP_BY字段配置错误");
				}
			}

		}
		String filed_class =dataSourceMap.get(DATASOURCE_QUERY_FILTER_CLASS)==null?"":dataSourceMap.get(DATASOURCE_QUERY_FILTER_CLASS);
		//添加翻译类
//		sqlEntity.setFiled_class(filed_class);
		//添加字段中英文对照
		sqlEntity.appendFileNameMap(MetaDataUtil.getFiledNameMap(dataSourceMap.get("METADATA_CODE"),filed_class));
		return sqlEntity;

	}
	
	/**
	 * 
	* @Title: dataSourceToSQL 
	* @Description: 根据数据源编码、流程节点编码组装SqlTableUtil实例——专用于稽核数据查询
	* @param datasourceCode 数据源编码
	* @param nodeCode 流程节点编码
	* @return SqlTableUtil实例
	* @throws BussnissException
	 */
	public static SqlTableUtil dataSourceToSQL(String datasourceCode, String nodeCode)
			throws BussnissException {
		
		SqlTableUtil sqlEntity = dataSourceToSQL(datasourceCode);
		Map<String, Object> mLastNode = WorkflowNodeUtil.getLastNode(nodeCode);
		if(!mLastNode.isEmpty()){
			sqlEntity.appendWhereAnd(mLastNode.get(WorkflowNodeUtil.NODE_SOURCE_WRITE_FIELD)
					+ " = "
					+ mLastNode.get(WorkflowNodeUtil.NODE_SOURCE_WRITE_VALUE));
		}
		return sqlEntity;
	
	}
	

	/**
	 * 
	* @Title: dataSourceToResult 
	* @Description: 根据数据源编码返回查询结果集
	* @param datasourceCode
	* @return List<Map<String, Object>> 结果集
	* @throws BussnissException
	 */
	public static List<Map<String, Object>> dataSourceToResult(String datasourceCode) throws BussnissException{
		
		List<Map<String, Object>> lResult = BaseDao.getBaseDao().query(dataSourceToSQL(datasourceCode));
		return lResult;
		
	}
	
	/**
	 * 
	* @Title: dataSourceToResult 
	* @Description: 根据数据源编码返回查询结果集
	* @param datasourceCode 数据源编码
	* @param index 起始序号
	* @param size 条数
	* @return List<Map<String, Object>> 结果集
	* @throws BussnissException
	 */
	public static List<Map<String, Object>> dataSourceToResult(String datasourceCode,int index,int size) throws BussnissException{
		
		List<Map<String, Object>> lResult = BaseDao.getBaseDao().query(dataSourceToSQL(datasourceCode),index,size);
		return lResult;
		
	}

}

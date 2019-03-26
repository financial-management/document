package com.yonyou.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.util.BussnissException;
import com.yonyou.util.CloneUtil;
import com.yonyou.util.RmConfigUtil;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.page.PageUtil;
import com.yonyou.util.sql.SqlTableUtil;

/**
 * 代理页面工具类 
* @ClassName ProxyPageUtil 
* @author 博超
* @date 2017年3月8日
 */
public class ProxyPageUtil {
	
	// 主键(主子表通用)
	public static final String ID="ID";

	// 代理页面表(主表)
	public static final String PROXYPAGE_TABLE_NAME = "CD_PROXY_PAGE";
	// 代理选择页面表(子表)
	public static final String PROXYSELECT_TABLE_NAME = "CD_PROXY_SELECT_PAGE";
	// 代理列表页面表(子表)
	public static final String PROXYLIST_TABLE_NAME = "CD_PROXY_LIST_PAGE";
	// 代理维护页面表(子表)
	public static final String PROXYMAIN_TABLE_NAME = "CD_PROXY_MAIN_PAGE";
	// 代理独立修改页面表(子表)
	public static final String PROXYUPDATE_TABLE_NAME = "CD_PROXY_UPDATE_PAGE";
	// 代理公共属性页面表(子表)
	public static final String PROXYUTIL_TABLE_NAME = "CD_PROXY_UTIL_MESSAGE";
	
	// 主表字段
	public static final String PROXYPAGE_META_ID = "META_ID";
	public static final String PROXYPAGE_META_CODE = "DATA_CODE";
	public static final String PROXYPAGE_META_NAME = "DATA_NAME";
	public static final String PROXYPAGE_PAGE_CODE = "PAGE_CODE";
	public static final String PROXYPAGE_PAGE_NAME = "PAGE_NAME";
	public static final String PROXYPAGE_PAGE_DISNAME = "PAGE_DISNAME";
	public static final String PROXYPAGE_QUERY_ROW= "QUERY_LAYOUT_ROW";
	public static final String PROXYPAGE_MAIN_ROW= "MAIN_LAYOUT_ROW";
	public static final String PROXYPAGE_CHECKBOX_TYPE= "CHECKBOX_TYPE";

	// 子表通用字段
	//外键
	public static final String PROXYDETAIL_PAGE_ID = "PAGE_ID";
	public static final String PROXYDETAIL_META_DETAIL_ID = "META_DETAIL_ID";
	public static final String PROXYDETAIL_FIELD_CODE = "FIELD_CODE";
	public static final String PROXYDETAIL_FIELD_NAME = "FIELD_NAME";
	public static final String PROXYDETAIL_FIELD_TYPE = "FIELD_TYPE";
	public static final String PROXYDETAIL_INPUT_TYPE = "INPUT_TYPE";
	public static final String PROXYDETAIL_INPUT_FORMART = "INPUT_FORMART";
	public static final String PROXYDETAIL_INPUT_HTML = "INPUT_HTML";
	public static final String PROXYDETAIL_SORT = "SORT";
	public static final String PROXYDETAIL_NULL_FLAG = "NULL_FLAG";
	public static final String PROXYDETAIL_KEY_FLAG = "KEY_FLAG";
	
	//子表差异字段
	public static final String PROXYSELECT_CONDITION_TYPE = "CONDITION_TYPE";
	public static final String PROXYLIST_IS_HIDDEN = "IS_HIDDEN";
	public static final String PROXYMAIN_IS_READONLY = "IS_READONLY";
	
	//ConcurrentHashMap<页面编码,ConcurrentHashMap<字段名,字段值>>
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,String>> proxyPageMap = null;
		//	new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();
	
	//ConcurrentHashMap<页面编码_子表类型,ConcurrentHashMap<字段编码（真实表字段名）,ConcurrentHashMap<子表字段名,子表字段值>>>
	//子表类型见PageUtil
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>> proxyDetailMap = null;
	
	//redis缓存
	private static CacheManager cacheManager = (CacheManager) SpringContextUtil.getBean("cacheManager");
	private static String REDIS_KEY_PROXYPAGE = "PROXYPAGECACHE";
	private static String REDIS_KEY_PROXYDETAIL = "PROXYDETAILCACHE";
	
	//关联关系枚举：(表名,页面类型)
	public enum PageTypeEnum{
		SELECT_PAGE(PROXYSELECT_TABLE_NAME,PageUtil.PAGE_TYPE_FOR_SELECT),
		LIST_PAGE(PROXYLIST_TABLE_NAME,PageUtil.PAGE_TYPE_FOR_TABLE),
		MAINT_PAGE(PROXYMAIN_TABLE_NAME,PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE),
		UPDATE_PAGE(PROXYUPDATE_TABLE_NAME,PageUtil.PAGE_TYPE_FOR_INDEPENDENT_UPDATE),
		UTIL_PAGE(PROXYUTIL_TABLE_NAME,PageUtil.PAGE_TYPE_FOR_UTIL);
		
		private final String tableName;
		private final String pageType;
		
		private PageTypeEnum(String _tableName, String _pageType){
			this.tableName = _tableName;
			this.pageType = _pageType;
		}

		public String getTableName() {
			return tableName;
		}

		public String getPageType() {
			return pageType;
		}
		
	}
	
	/**
	 * 初始化缓存MAP
	 */
	public static void init(){
		initProxyPageMap();
		initProxyDetailMap();
	}
	
	/**
	 * 清空服务器缓存
	 */
	public static void clear(){
		proxyPageMap = null;
		proxyDetailMap = null;
	}
	
	/**
	 * 从数据库初始化proxyPageMap
	 */
	public static void initProxyPageMap() {
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> proxyPageMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();

		SqlTableUtil sql = new SqlTableUtil(PROXYPAGE_TABLE_NAME, "").appendSelFiled("*").appendWhereAnd("DR = 0");
		List<Map<String, Object>> lProxyPageData = BaseDao.getBaseDao().query(sql);
		System.out.println("lProxyPageData" + lProxyPageData);

		for (Iterator<Map<String, Object>> itLProxyPageData = lProxyPageData.iterator(); itLProxyPageData.hasNext();) {
			Map<String, Object> proxyPageData = itLProxyPageData.next();
			ConcurrentHashMap<String, String> newProxyPageData = new ConcurrentHashMap<String, String>();
			for (String key : proxyPageData.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (proxyPageData.get(key)== null?"":proxyPageData.get(key).toString());
				newProxyPageData.put(key, valueStr);
			}
			String pageCode = newProxyPageData.get(PROXYPAGE_PAGE_CODE);
			proxyPageMapTemp.put(pageCode, newProxyPageData);
		}
		System.out.println("proxyPageMap" + proxyPageMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY_PROXYPAGE, proxyPageMapTemp);
		}else{
			proxyPageMap = proxyPageMapTemp;
			}
	}
	
	/**
	 * 从数据库初始化所有代理子表信息
	 */
	public static void initProxyDetailMap(){
		ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> proxyDetailMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>>();
		//循环初始化所有子表信息
		for (PageTypeEnum pageType : PageTypeEnum.values()) {
			initSingleProxyDetailMap(pageType, proxyDetailMapTemp);
		}
		System.out.println("proxyDetailMap" + ":" + proxyDetailMapTemp);
		
		//集群模式下，将缓存移入redis
		if(RmConfigUtil.getIsClusterMode()){
			cacheManager.set(REDIS_KEY_PROXYDETAIL, proxyDetailMapTemp);
		}else{
			proxyDetailMap = proxyDetailMapTemp;
			}
	}
	
	/**
	 * 初始化单个子表信息，装进proxyDetailMap
	* @param pageType
	* @param proxyDetailMapTemp
	 */
	public static void initSingleProxyDetailMap(PageTypeEnum pageType,ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> proxyDetailMapTemp){
		SqlTableUtil sql = new SqlTableUtil(pageType.getTableName(), "detail");
		sql.appendSelFiled("*").appendJoinTable(PROXYPAGE_TABLE_NAME +" page","detail."+PROXYDETAIL_PAGE_ID+"= page."+ID).appendSelFiled("page", PROXYPAGE_PAGE_CODE).appendWhereAnd("detail.DR = 0 AND page.DR = 0").appendOrderBy(PROXYDETAIL_PAGE_ID);
		List<Map<String, Object>> lProxyDetailData = BaseDao.getBaseDao().query(sql);
		System.out.println("lProxyDetailData-" + pageType.getPageType() + ":" + lProxyDetailData);
		
		for (Iterator<Map<String, Object>> itLProxyDetailData = lProxyDetailData.iterator(); itLProxyDetailData.hasNext();) {
			Map<String, Object> proxyDetailData = (Map<String, Object>) itLProxyDetailData.next();
			// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
			ConcurrentHashMap<String, String> newProxyDetailData = new ConcurrentHashMap<String, String>();
			for (Entry<String,Object> entry : proxyDetailData.entrySet()) {
				String valueStr = (entry.getValue()==null?"":entry.getValue().toString());
				newProxyDetailData.put(entry.getKey(), valueStr);
			}
			
			String fieldCode = newProxyDetailData.get(PROXYDETAIL_FIELD_CODE);
			//key:pagecode_pagetype
			String detailKey = newProxyDetailData.get(PROXYPAGE_PAGE_CODE)+"_"+pageType.getPageType();
			if(!proxyDetailMapTemp.containsKey(detailKey)){
				proxyDetailMapTemp.put(detailKey, new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>()); 
			}
			proxyDetailMapTemp.get(detailKey).put(fieldCode, newProxyDetailData);
		}
	}
	
	/**
	 * 根据页面编码获取代理页面主表信息
	* @param pageCode
	* @return
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String, String>  getProxyPageData(String pageCode) throws BussnissException{

		if(RmConfigUtil.getIsClusterMode()){	
			if(!cacheManager.exists(REDIS_KEY_PROXYPAGE))
				initProxyPageMap();
		}else{
			if(null == proxyPageMap){
				synchronized (ProxyPageUtil.class) {
					if(null == proxyPageMap)
						initProxyPageMap();
					}
				}
			}
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> proxyPageMapTemp = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		if (RmConfigUtil.getIsClusterMode()) {
			proxyPageMapTemp = cacheManager.get(REDIS_KEY_PROXYPAGE);
		} else {
			proxyPageMapTemp = proxyPageMap;
		}
		if(!proxyPageMapTemp.containsKey(pageCode)){
			throw new BussnissException("页面编码"+pageCode+"不存在！");
		}
		//返回深拷贝对象
		return CloneUtil.clone(proxyPageMapTemp.get(pageCode));
	}
	
	/**
	 * 根据页面编码_子表类型获取元数据字段信息
	* @param detailKey
	* @return
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,ConcurrentHashMap<String,String>>  getProxyDetailData(String detailKey) throws BussnissException{
		
		if(RmConfigUtil.getIsClusterMode()){
			if(!cacheManager.exists(REDIS_KEY_PROXYDETAIL))
				initProxyDetailMap();
		}else{
			if(null == proxyDetailMap){
				synchronized(ProxyPageUtil.class){
					if(null == proxyDetailMap)
						initProxyDetailMap();
				}
			}
		}
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> proxyDetailMapTemp = new ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>();
		if(RmConfigUtil.getIsClusterMode()){
			proxyDetailMapTemp = cacheManager.get(REDIS_KEY_PROXYDETAIL);
		}else{
			proxyDetailMapTemp = proxyDetailMap;
		}
		if(!proxyDetailMapTemp.containsKey(detailKey)){
			return null;
		}
		//返回深拷贝对象
		return proxyDetailMapTemp.get(detailKey);

	}
}

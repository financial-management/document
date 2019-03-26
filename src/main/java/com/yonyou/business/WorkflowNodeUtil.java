package com.yonyou.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.sql.SqlTableUtil;

/**
 * 
* @ClassName: WorkflowNodeUtil 
* @Description: 稽核流程节点工具类
* @author 博超
* @date 2016年12月26日 
*
 */
public class WorkflowNodeUtil {
	
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> NodeMap = null;

	public static final String NODE_TABLE_NAME = "CD_WORKFLOW_NODE";
	public static final String NODE_ID = "ID";
	public static final String NODE_CODE = "NODE_CODE";
	public static final String NODE_NAME = "NODE_NAME";
	public static final String NODE_ORDER = "NODE_ORDER";
	public static final String NODE_SOURCE_DATASOURCE_ID = "SOURCE_DATASOURCE_ID";
	public static final String NODE_SOURCE_DATASOURCE_CODE = "SOURCE_DATASOURCE_CODE";
	public static final String NODE_SOURCE_NEXT = "SOURCE_NEXT";
	public static final String NODE_SOURCE_WRITE_FIELD = "SOURCE_WRITE_FIELD";
	public static final String NODE_SOURCE_WRITE_VALUE = "SOURCE_WRITE_VALUE";
	public static final String NODE_WORKFLOW_ID = "WORKFLOW_ID";
	public static final String NODE_FILTER_CLASS = "NODE_FILTER_CLASS";
	public static final String NODE_TARGET_DATASOURCE_ID = "TARGET_DATASOURCE_ID";
	public static final String NODE_TARGET_DATASOURCE_CODE = "TARGET_DATASOURCE_CODE";
	public static final String NODE_TARGET_NEXT = "TARGET_NEXT";
	public static final String NODE_SOURCE_SUM_HAND = "SOURCE_SUM_HAND";
	public static final String NODE_TARGET_SUM_HAND ="TARGET_SUM_HAND";
	public static final String NODE_TARGET_WRITE_FIELD = "TARGET_WRITE_FIELD";
	public static final String NODE_TARGET_WRITE_VALUE = "TARGET_WRITE_VALUE";

	/**
	 * 
	* @Title: init 
	* @Description: 从数据库初始化所有流程节点信息
	 */
	public static void init() {
		NodeMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		SqlTableUtil sql = new SqlTableUtil(NODE_TABLE_NAME, "").appendSelFiled("*");
		List<Map<String, Object>> lNode = BaseDao.getBaseDao().query(sql);
		System.out.println("lNode" + lNode);

		for (Iterator<Map<String, Object>> itLNode = lNode.iterator(); itLNode.hasNext();) {
			Map<String, Object> Node = itLNode.next();
			ConcurrentHashMap<String, String> newNode= new ConcurrentHashMap<String, String>();
			for (String key : Node.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				String valueStr = (Node.get(key)== null?"":Node.get(key).toString());
				newNode.put(key,valueStr);
			}
			String NodeCode = newNode.get(NODE_CODE);
			NodeMap.put(NodeCode, newNode);
		}
		System.out.println("NodeMap" + NodeMap);
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: 初始化指定节点编码的流程节点
	* @param NodeCode 节点编码
	 */
	public static void init(String NodeCode) {
		NodeMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		if(null == NodeMap)
			NodeMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		
		SqlTableUtil sql = new SqlTableUtil(NODE_TABLE_NAME, "").appendSelFiled("*");
		sql.appendWhereAnd(NODE_CODE + "=" + NodeCode);
		List<Map<String, Object>> lNode= BaseDao.getBaseDao().query(sql);
		System.out.println("lDataSource" + lNode);
		
		for (Iterator<Map<String, Object>> itLNode = lNode.iterator(); itLNode.hasNext();) {
			Map<String, Object> Node = itLNode.next();
			ConcurrentHashMap<String, String> newDataSource = new ConcurrentHashMap<String, String>();
			for (String key : Node.keySet()) {
				// 转化类型：Map<String, Object> => ConcurrentHashMap<String, String>
				newDataSource.put(key, Node.get(key).toString());
			}
			NodeMap.put(NodeCode, newDataSource);
		}
		System.out.println("NodeMap" + NodeMap);
	}

	/**
	 * 
	* @Title: clear 
	* @Description: 清空服务器流程节点信息缓存
	 */
	public static void clear(){
		NodeMap=null;
	}
	
	/**
	 * 
	* @Title: getNode 
	* @Description: 根据流程节点编码获取流程节点信息
	* @param NodeCode 节点编码
	* @return  ConcurrentHashMap<流程节点字段名,流程节点字段值>
	* @throws BussnissException
	 */
	public static ConcurrentHashMap<String,String>  getNode(String NodeCode) throws BussnissException{
			
		if(null == NodeMap){
			synchronized(WorkflowNodeUtil.class){
				if(null == NodeMap)
					init();
				}
		}
		if(!NodeMap.containsKey(NodeCode)){
			throw new BussnissException("");
		}
		return NodeMap.get(NodeCode);
	}
	
	/**
	 * 
	* @Title: getLastNode 
	* @Description: 依据当前流程节点编码获取上级流程节点编码信息，无上级则返回本级流程信息（俩回写值均改为初始状态-0） 
	* @param nodeCode 节点编码
	* @return Map<字段名, 字段值> 上级流程节点编码信息
	* @throws BussnissException
	 */
	public static Map<String, Object> getLastNode(String nodeCode) throws BussnissException{
		String workflowId =getNode(nodeCode).get(NODE_WORKFLOW_ID);
		SqlTableUtil sql = DataSourceUtil.dataSourceToSQL(NODE_TABLE_NAME)
				.appendWhereAnd(NODE_WORKFLOW_ID + "=" + workflowId)
				.appendOrderBy("NODE_ORDER", "ASC");
		List<Map<String,Object>> lNode =BaseDao.getBaseDao().query(sql);
		
		Map<String,Object> lastNode = lNode.get(0);
		lastNode.put(NODE_SOURCE_WRITE_VALUE, "0");
		lastNode.put(NODE_TARGET_WRITE_VALUE, "0");
		if(lNode.size()>0){
			for(Map<String,Object> nodeInfo : lNode){
				if(nodeCode.equals(nodeInfo.get(NODE_CODE))){
					break;
				}
				lastNode =nodeInfo;
				
			}
		}else{
			throw new BussnissException("无法获取节点"+nodeCode+"所属流程下任何节点，此节点可能已失效");
		}
		
		return lastNode;
	}
	
	/**
	 * 
	* @Title: findLastRuleCode 
	* @Description: 依据当前流程节点编码获取上级流程节点编码code 
	* @param nodeCode 节点编码
	* @return last_rule_code 上级流程节点编码
	* @throws BussnissException
	 */
	public static String findLastRuleCode(String nodeCode) throws BussnissException{
		
		Map<String, Object> dataMap =getLastNode(nodeCode);
		String last_rule_code =dataMap.get(NODE_CODE).toString();
		return last_rule_code;
	}
	
	/**
	 * 
	* @Title: findLastWriteValue 
	* @Description: 依据当前流程节点编码及其上级稽核一方数据源编码获取此数据源稽核回写值
	* @param nodeCode 节点编码
	* @param dataSourceCode 数据源编码
	* @return 字段回写值
	* @throws BussnissException
	 */
	public static String findLastWriteValue(String nodeCode,String dataSourceCode) throws BussnissException{
		
		Map<String, Object> dataMap =WorkflowNodeUtil.getLastNode(nodeCode);
		String last_write_value = findWriteValueByDataCode(dataSourceCode, dataMap);
		last_write_value=last_write_value.equals("")?"0":last_write_value;
		return last_write_value;
	}
	
	/**
	 * 
	* @Title: findWriteFiledByDataCode 
	* @Description: 依据当前流程节点编码及其一方元数据编码获取此数元数据回写字段 
	* @param nodeCode 节点编码
	* @param metaCode 元数据编码
	* @return 回写字段
	* @throws BussnissException
	 */
	public static String findWriteFiledByDataCode(String nodeCode,String metaCode) throws BussnissException{
		metaCode = metaCode.toUpperCase();
		Map<String, String> dataMap =WorkflowNodeUtil.getNode(nodeCode);
		String source_datasource  =dataMap.get(NODE_SOURCE_DATASOURCE_CODE);
		if(source_datasource.equals(metaCode)){
			return dataMap.get(WorkflowNodeUtil.NODE_SOURCE_WRITE_FIELD);
		}else{
			return dataMap.get(WorkflowNodeUtil.NODE_TARGET_WRITE_FIELD);
		}
	}
	
	/**
	 * 
	* @Title: findWriteValueByDataCode 
	* @Description: 依据当前流程节点编码及其一方数据源编码获取此数据源稽核回写值
	* @param nodeCode 节点编码
	* @param dataCode 数据源编码
	* @return
	* @throws BussnissException
	 */
	public static String findWriteValueByDataCode(String nodeCode,String dataCode) throws BussnissException{
		Map<String, String> dataMap =WorkflowNodeUtil.getNode(nodeCode);
		return findWriteValueByDataCode(dataCode,dataMap);
	}
	
	/**
	 * 
	* @Title: findWriteValueByDataCode 
	* @Description: 根据节点信息及稽核一方数据源编码获取其待回写值
	* @param dataCode 稽核一方数据源编码
	* @param dataMap 节点信息
	* @return
	* @throws BussnissException
	 */
	private static String findWriteValueByDataCode(String dataCode,Map dataMap) throws BussnissException{
		dataCode = dataCode.toUpperCase();
		String source_datasource  =(String)dataMap.get(NODE_SOURCE_DATASOURCE_CODE);
		if(source_datasource.equals(dataCode)){
			return (String)dataMap.get(NODE_SOURCE_WRITE_VALUE);
		}else{
			return (String)dataMap.get(NODE_TARGET_WRITE_VALUE);
		}
	}
	
}

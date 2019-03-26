package com.yonyou.util.wsystem.api;

import java.util.Map;

import net.sf.json.JSONObject;

import com.yonyou.util.HttpRequestUtil;
import com.yonyou.util.JsonUtils;

/**
 * 访问接口
 * @author moing_ink
 * <p>创建时间 ： 2016年12月27日
 * @version 1.0
 */
public abstract class HTTPApi {
	
	/**
	 * 接口请求成功返回编码
	 */
	public static final String SUCESS_CODE = "000000";
	
	/**
	 * 访问连接
	 */
	public String http_url =findHttpUrl();
	/**
	 * 获取路径
	 * @return 路径
	 */
	public abstract String findHttpUrl();
	
	/**
	 * 服务编码
	 */
	public static String HEAD_SERVICE_CODE="serviceCode";
	
	/**
	 * 调用者服务编码
	 */
	public static String HEAD_CUSTOMER="customer";
	
	/**
	 * 请求服务时间 年月日 时分秒：毫秒
	 */
	public static String HEAD_REQTIME="reqTime";
	
	/**
	 * 请求服务状态 000000 成功
	 */
	public static String HEAD_RETCODE="retCode";
	
	
	/**
	 * 终端上送唯一流水号
	 */
	public static String HEAD_SERIALNO="serialNo";
	
	/**
	 * 调用者服务编码
	 */
	public static String HEAD_USERID="userId";
	
	/**
	 * 接口返回数据
	 */
	public static String HEAD_DATA="data";
	
	/**
	 * 设置访问信息
	 * @param joinStr
	 * @return JSON对象
	 */
	public JSONObject sendHTTP(String joinStr){
		return HttpRequestUtil.httpRespJSON(http_url, joinStr);
	}
	/**
	 * 根据MAP数据设置访问信息
	 * @param joinStr
	 * @return 数据集合
	 */
	public Map<String,String> sendHTTPForMap(String joinStr){
		return HttpRequestUtil.httpRespMap(http_url, joinStr);
	}
	/**
	 * 设置访问信息
	 * @param dataMap 数据
	 * @return JSON对象
	 */
	public JSONObject sendHTTP(Map<String,String> dataMap){
		return this.sendHTTP(this.bulidJsonStr(dataMap));
	}
	
	/**
	 * 根据MAP数据设置访问信息
	 * @param dataMap 数据
	 * @return 数据集合
	 */
	public Map<String,String> sendHTTPForMap(Map<String,String> dataMap){
		return this.sendHTTPForMap(this.bulidJsonStr(dataMap));
	}
	
	/**
	 * 构建json信息
	 * @param dataMap
	 * @return json信息
	 */
	public String bulidJsonStr(Map<String,String> dataMap){
		String jsonStr =JsonUtils.map2json(dataMap);
		
		return jsonStr;
	}
	
}

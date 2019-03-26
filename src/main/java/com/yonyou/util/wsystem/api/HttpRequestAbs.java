package com.yonyou.util.wsystem.api;

import java.util.Map;

import com.yonyou.util.wsystem.util.SoaFlowEncapsulation;

import net.sf.json.JSONObject;


public abstract class HttpRequestAbs {
	
	public abstract String httpRespString(String url, String jsonString) ;
	
	
	public JSONObject httpRespJSON(String url, String jsonString){
		jsonString=SoaFlowEncapsulation.encode(jsonString);
		System.out.println("请求加密：josnString"+jsonString);
		String jsonReturnString =httpRespString( url,  jsonString);
		System.out.println(jsonReturnString);
		jsonReturnString=SoaFlowEncapsulation.decode(jsonReturnString);
		System.out.println("解码后："+jsonReturnString);
		if(jsonReturnString.length()==0){
			jsonReturnString="{'message':''}";
		}
		return JSONObject.fromObject(jsonReturnString);
	}
	/**
	 * HTTP请求，返回map数据
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public  Map httpRespMap(String url, String jsonString) {
		return (Map) httpRespJSON(url, jsonString);
	}
}

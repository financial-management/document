package com.yonyou.business.api;

import net.sf.json.JSONObject;

import com.yonyou.util.PropertyFileUtil;

public class ApiUtil {
	
	/**
	 * 根据返回编码将相应信息拼装进json
	 * @param json
	 * @param code
	 * @return
	 */
	public static JSONObject ApiJsonPut(String code) {
		JSONObject json = new JSONObject();
		json.put("retCode", code);
		json.put("retMsg",PropertyFileUtil.getValue(code));
		return json;
	}

}

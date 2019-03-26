package com.yonyou.business.api;

import net.sf.json.JSONObject;

import com.yonyou.doc.FileManager;

/**
 * 接口实现类-由批次号获取附件列表
* @ClassName B005002001_001 
* @author hubc
* @date 2018年6月1日
 */
public class B005002001_001 extends ApiBussAbs {
	
	@Override
	public JSONObject doDeal(JSONObject jsonData) {
		if (jsonData.containsKey("batchno")) {
			String retHtml = FileManager.getListHtml(jsonData.getString("batchno"), false);
			retJsonObject = ApiUtil.ApiJsonPut("000000");
			retJsonObject.put("data", retHtml);
		} else {
			retJsonObject = ApiUtil.ApiJsonPut("005001");
		}

		return retJsonObject;
	}
	
}

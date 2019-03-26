package com.yonyou.business.api;

import net.sf.json.JSONObject;

import com.yonyou.doc.FileManager;

/**
 * 接口实现类-删除附件
* @ClassName B005004001_001 
* @author hubc
* @date 2018年6月1日
 */
public class B005004001_001 extends ApiBussAbs {
	
	@Override
	public JSONObject doDeal(JSONObject jsonData) {
		if (jsonData.containsKey("fileid")) {
			boolean flag = FileManager.delete(jsonData.getString("fileid"));
			if(flag){
				retJsonObject = ApiUtil.ApiJsonPut("000000");
				retJsonObject.put("data", "");
			}else{
				retJsonObject = ApiUtil.ApiJsonPut("005006");
			}
		} else {
			retJsonObject = ApiUtil.ApiJsonPut("005001");
		}

		return retJsonObject;
	}
	
}

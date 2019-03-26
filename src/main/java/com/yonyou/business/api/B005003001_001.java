package com.yonyou.business.api;

import net.sf.json.JSONObject;

import com.yonyou.doc.DocBean;
import com.yonyou.doc.FileManager;

/**
 * 接口实现类-下载附件
* @ClassName B005003001_001 
* @author hubc
* @date 2018年6月1日
 */
public class B005003001_001 extends ApiBussAbs {
	
	@Override
	public JSONObject doDeal(JSONObject jsonData) {
		if (jsonData.containsKey("fileid")) {
			DocBean docBean = FileManager.download(jsonData.getString("fileid"));
			if(docBean != null){
				JSONObject fileJsonObject = new JSONObject();
				fileJsonObject.put("filename", docBean.getFileName());
				fileJsonObject.put("filecontent", docBean.getFileContent());
				retJsonObject = ApiUtil.ApiJsonPut("000000");
				retJsonObject.put("data", fileJsonObject);
			}else{
				retJsonObject = ApiUtil.ApiJsonPut("005005");
			}
		} else {
			retJsonObject = ApiUtil.ApiJsonPut("005001");
		}

		return retJsonObject;
	}
	
}

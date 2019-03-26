package com.yonyou.business.api;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yonyou.doc.DocBean;
import com.yonyou.doc.FastdfsClient;
import com.yonyou.doc.FileManager;

/**
 * 接口实现类-上传附件
* @ClassName B005001001_001 
* @author hubc
* @date 2018年5月23日
 */
public class B005001001_001 extends ApiBussAbs {
	
	@Override
	public JSONObject doDeal(JSONObject jsonData) {
		if (jsonData.containsKey("userid") && jsonData.containsKey("companyid")
				&& jsonData.containsKey("filecontent") && jsonData.containsKey("filename") && jsonData.containsKey("bscode")) {
			
			String userId = jsonData.getString("userid");
			String companyId = jsonData.getString("companyid");
			String fileName = jsonData.getString("filename");
			byte[] fileContent = jsonData.getString("filecontent").getBytes();
			String bsCode = jsonData.getString("bscode");
			String batchNo = null;
			if(jsonData.containsKey("batchno")){
				batchNo = jsonData.getString("batchno");
			}
			//追加描述信息
			Map<String, String> descriptions = null; 
			if(jsonData.containsKey("descriptions") && jsonData.getJSONObject("descriptions") != null){
				descriptions = jsonData.getJSONObject("descriptions");
			}
			
			int fileSize = fileContent.length;
			if(FastdfsClient.maxFileSize > 0 && fileSize > FastdfsClient.maxFileSize){
				retJsonObject = ApiUtil.ApiJsonPut("005002");
			}
			
			DocBean bean = new DocBean();
			bean.setUserId(userId);
			bean.setCompanyId(companyId);
			bean.setFileName(fileName);
			bean.setFileContent(fileContent);
			bean.setBsCode(bsCode);
			bean.setBatchNo(batchNo);
			bean.setDescription(descriptions);
			HashMap<String, String> retMap = FileManager.upload(bean);
			if(retMap == null){
				retJsonObject = ApiUtil.ApiJsonPut("005003");
			}else{
				retJsonObject = ApiUtil.ApiJsonPut("000000");
				retJsonObject.put("data", retMap);
			}
		} else {
			retJsonObject = ApiUtil.ApiJsonPut("005001");
		}

		return retJsonObject;
	}
	
}

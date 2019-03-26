package com.yonyou.util.wsystem.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.util.PropertyFileUtil;
import com.yonyou.util.wsystem.api.HTTPApi;

public class DocClient extends HTTPApi {

	public static Logger logger = LoggerFactory.getLogger(DocClient.class);

	/**
	 * 上传附件调用服务编码
	 */
	static final String UPLOAD_SERCODE = "005001001#001";
	/**
	 * 获取附件列表调用服务编码
	 */
	static final String GETLIST_SERCODE = "005002001#001";
	/**
	 * 下载附件调用服务编码
	 */
	static final String DOWNLOAD_SERCODE = "005003001#001";
	/**
	 * 删除附件调用服务编码
	 */
	static final String DELETE_SERCODE = "005004001#001";
	
	@Override
	public String findHttpUrl() {
		return PropertyFileUtil.getValue("DOC_URL");
	}
	
	/**
	 * 调用接口上传附件
	* @param reqMap
	 * @throws IOException 
	 */
	public Map<String, String> uploadAffix(Map<String, String> reqMap, MultipartFile file){
		Map<String, String> retMap = null;
		reqMap.put("filename", file.getOriginalFilename());
		try {
			reqMap.put(HEAD_SERVICE_CODE, UPLOAD_SERCODE);
			reqMap.put("filecontent",new String(file.getBytes()));
			Map<String, String> result = sendHTTPForMap(reqMap);
//			if(SUCESS_CODE.equals(result.get(HEAD_RETCODE))){
				retMap = JSONObject.fromObject(result.get(HEAD_DATA));
//			}
		} catch (IOException e) {
			logger.error("前台上传文件读取失败");
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 调用接口由批次号获取附件列表
	* @param reqMap
	* @param file
	* @return
	 */
	public String getAffixListHtml(String batchNo){
		String retHtml = null;
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put(HEAD_SERVICE_CODE, GETLIST_SERCODE);
		reqMap.put("BATCHNO", batchNo);
		Map<String, String> result = sendHTTPForMap(reqMap);
		if(SUCESS_CODE.equals(result.get(HEAD_RETCODE))){
			retHtml = result.get(HEAD_DATA);
		}
		return retHtml;
	}
	
	/**
	 * 调用接口下载附件
	* @param fileId
	* @return
	 */
	public Map<String, Object> downloadAffix(String fileId){
		Map<String, Object> retMap = new HashMap<>();
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put(HEAD_SERVICE_CODE, DOWNLOAD_SERCODE);
		reqMap.put("fileid", fileId);
		Map<String, String> result = sendHTTP(reqMap);
		if(SUCESS_CODE.equals(result.get(HEAD_RETCODE))){
			retMap = JSONObject.fromObject(result.get(HEAD_DATA));
		}
		return retMap;
	}
	
	/**
	 * 调用接口删除附件
	* @param reqMap
	* @param file
	* @return
	 */
	public boolean deleteAffix(String fileId){
		boolean flag = false;
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put(HEAD_SERVICE_CODE, DELETE_SERCODE);
		reqMap.put("fileid", fileId);
		Map<String, String> result = sendHTTPForMap(reqMap);
		if(SUCESS_CODE.equals(result.get(HEAD_RETCODE))){
			flag = true;
		}else{
			logger.error("调用接口删除附件失败！请求信息：" + reqMap);
		}
		return flag;
	}
	
	
}


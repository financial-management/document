package com.yonyou.util.service;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.util.RmIdFactory;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;

@Service
public class DocumentFolderService  {


	/**
	 * 添加个人文档
	 * @param directoryJson
	 * @param userId
	 * @throws Exception
	 */
	public void addPersonFolder (JSONObject directoryJson,String userId) throws Exception{
		String [] ids = RmIdFactory.requestId("DOC_DIRECTORY", 1);
		for (String string : ids) {
			
			System.out.println(string);
		}
		String id = ids[0];
		directoryJson.put("ID",id);
		directoryJson.put("DR",0);
		BaseDao.getBaseDao().insert("DOC_DIRECTORY", directoryJson);
		JSONObject authorizeJson = new JSONObject();
		authorizeJson.put("DIRECTORY_ID", id);
		authorizeJson.put("AUTHORIZE_OBJECT_USER", userId);
		authorizeJson.put("AUTHORIZE_OBJECT_ROLE", 0);
		authorizeJson.put("IS_SAVE", 0);
		authorizeJson.put("IS_DELETE", 0);
		authorizeJson.put("IS_DONWLOAD", 0);
		authorizeJson.put("DR", 0);
		authorizeJson.put("IS_ADMIN", 1);
		BaseDao.getBaseDao().insert("DOC_AUTHORIZE", authorizeJson);
	}
}

package com.yonyou.util.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.log.BusLogger;
import com.yonyou.util.sql.SqlWhereEntity;

@Service
public class DocumentMgrService {

	public String insertPersionDirectory(String jsons, String userID)
			throws BussnissException {
		String datasourceDirectory = "DOC_DIRECTORY";
		String datasourceAuth = "DOC_AUTHORIZE";

		// 目录创建数据
		JSONObject json = JSONObject.fromObject(jsons);
		json.put("GROUP_TYPE", "2");// 个人目录创建类型为个人授权时候不查看个人文件夹
		String dirId = BaseDao.getBaseDao().insertByTransfrom(
				datasourceDirectory, json);

		// 构建角色授权数据
		Map map = new HashMap();
		map.put("DIRECTORY_ID", dirId);
		map.put("STATIC_CODE", json.get("STATIC_CODE"));
		map.put("AUTHORIZE_OBJECT_USER", userID);
		map.put("IS_SAVE", "0");
		map.put("IS_DELETE", "0");
		map.put("IS_DONWLOAD", "0");
		BaseDao.getBaseDao().insertByTransfrom(datasourceAuth, map);
		return dirId;
	}

	public String deletePersionDirectory(String jsons, String userID)
			throws BussnissException {
		String datasourceDirectory = "DOC_DIRECTORY";
		String datasourceAuth = "DOC_AUTHORIZE";

		// 删除目录权限
		JSONObject json = JSONObject.fromObject(jsons);
		SqlWhereEntity whereEntity = new SqlWhereEntity();
		String sql = "update doc_authorize set DR='1'  where DIRECTORY_ID="
				+ json.get("ID");

		BaseDao.getBaseDao().update(sql);
		Map<String, Object> entity1 = new HashMap<String, Object>();
		entity1.put("ID", json.get("ID"));
		entity1.put("DR", "1");
		int i = BaseDao.getBaseDao().update(datasourceDirectory, entity1,
				whereEntity);

		return String.valueOf(i);

	}

	public String saveAuth(String jsons) throws BussnissException {
		// 针对选择的目录的父目录进行授权
		String datasourceAuth = "DOC_AUTHORIZE";

		JSONObject json = JSONObject.fromObject(jsons);

		String selSql = "select * from doc_directory where FIND_IN_SET(id,getParLst('"+json.getString("DIRECTORY_ID")+"'));";
		List<Map<String, Object>> list=BaseDao.getBaseDao().query(selSql, "");
		List<Map<String,Object>> authList =new ArrayList();
		System.out.println("取出数据"+list.size());
		if(list.isEmpty()||list.size()==0)
		{
			Map tmpMap=new HashMap();
			tmpMap.put("DIRECTORY_ID", json.getString("DIRECTORY_ID")!=null?json.getString("DIRECTORY_ID"):"");
			tmpMap.put("DIRECTORY_NAME", json.getString("DIRECTORY_NAME")!=null?json.getString("DIRECTORY_NAME"):"");
			tmpMap.put("AUTHORIZE_OBJECT_ORG", json.getString("AUTHORIZE_OBJECT_ORG")!=null?json.getString("AUTHORIZE_OBJECT_ORG"):"");
			tmpMap.put("ORG_NAME", json.getString("ORG_NAME")!=null?json.getString("ORG_NAME"):"");
			tmpMap.put("AUTHORIZE_OBJECT_ROLE", json.getString("AUTHORIZE_OBJECT_ROLE")!=null?json.getString("AUTHORIZE_OBJECT_ROLE"):"");
			tmpMap.put("ROLE_NAME", json.getString("ROLE_NAME")!=null?json.getString("ROLE_NAME"):"");
			tmpMap.put("AUTHORIZE_OBJECT_USER", json.getString("AUTHORIZE_OBJECT_USER")!=null?json.getString("AUTHORIZE_OBJECT_USER"):"");
			tmpMap.put("USER_NAME", json.getString("USER_NAME")!=null?json.getString("USER_NAME"):"");
			tmpMap.put("IS_SAVE", json.getString("IS_SAVE")!=null?json.getString("IS_SAVE"):"");
			tmpMap.put("IS_DONWLOAD", json.getString("IS_DONWLOAD")!=null?json.getString("IS_DONWLOAD"):"");
			tmpMap.put("IS_DELETE", json.getString("IS_DELETE")!=null?json.getString("IS_DELETE"):"");
			tmpMap.put("IS_ADMIN", json.getString("IS_ADMIN")!=null?json.getString("IS_ADMIN"):"");	
			tmpMap.put("DR", "0");
			
			BaseDao.getBaseDao().insert(datasourceAuth, tmpMap);
			
		}else
		{
			for(int i=0;i<list.size();i++)
			{
				Map tmpMap=list.get(i);
				Map newMap=new HashMap();
				newMap.put("DIRECTORY_ID", tmpMap.get("ID"));
				newMap.put("DIRECTORY_NAME", tmpMap.get("NAME"));
				
				newMap.put("AUTHORIZE_OBJECT_ORG", json.getString("AUTHORIZE_OBJECT_ORG")!=null?json.getString("AUTHORIZE_OBJECT_ORG"):"");
				newMap.put("ORG_NAME", json.getString("ORG_NAME")!=null?json.getString("ORG_NAME"):"");
				newMap.put("AUTHORIZE_OBJECT_ROLE", json.getString("AUTHORIZE_OBJECT_ROLE")!=null?json.getString("AUTHORIZE_OBJECT_ROLE"):"");
				newMap.put("ROLE_NAME", json.getString("ROLE_NAME")!=null?json.getString("ROLE_NAME"):"");
				newMap.put("AUTHORIZE_OBJECT_USER", json.getString("AUTHORIZE_OBJECT_USER")!=null?json.getString("AUTHORIZE_OBJECT_USER"):"");
				newMap.put("USER_NAME", json.getString("USER_NAME")!=null?json.getString("USER_NAME"):"");
				newMap.put("IS_SAVE", json.getString("IS_SAVE")!=null?json.getString("IS_SAVE"):"");
				newMap.put("IS_DONWLOAD", json.getString("IS_DONWLOAD")!=null?json.getString("IS_DONWLOAD"):"");
				newMap.put("IS_DELETE", json.getString("IS_DELETE")!=null?json.getString("IS_DELETE"):"");
				newMap.put("IS_ADMIN", json.getString("IS_ADMIN")!=null?json.getString("IS_ADMIN"):"");	
				newMap.put("DR", "0");
				
				System.out.println(newMap.toString());
				authList.add(newMap);
			}
			BaseDao.getBaseDao().insert(datasourceAuth, authList);
		}
		
		
		
		return "1";
	}

}

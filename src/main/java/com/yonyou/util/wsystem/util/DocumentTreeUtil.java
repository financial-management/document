package com.yonyou.util.wsystem.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yonyou.util.JsonUtils;

public class DocumentTreeUtil {
	//获得jstree的普通树
	public static String getClickTree(List<Map<String, Object>> mylist)
	{
		List<Map<String, Object>> jsonList = new ArrayList();
		if (!mylist.isEmpty()) {
			for (int i = 0; i < mylist.size(); i++) {
				Map<String, Object> jsoMap = new HashMap();
				Map searchParams = mylist.get(i);
				jsoMap.put("id", searchParams.get("ID"));
				jsoMap.put("text", searchParams.get("NAME"));
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("readOnly", searchParams.get("READ_ONLY"));
				data.put("name", searchParams.get("NAME"));
				data.put("id", searchParams.get("ID"));
				data.put("group_type", searchParams.get("GROUP_TYPE"));
				data.put("isDirectory", searchParams.get("IS_DIRECTORY"));
				jsoMap.put("data", data);
				if (String.valueOf(searchParams.get("PARENT_ID")).equals("0") || String.valueOf(searchParams.get("PARENT_ID")).equals("")) {
					jsoMap.put("parent", "#");
				} else {
					jsoMap.put("parent", searchParams.get("PARENT_ID"));
				}
				jsonList.add(jsoMap);
				
			}

			return JsonUtils.object2json(jsonList);
		}
		return null;
	}

	//获得带权限的树
	public static String getAuthClickTree(List<Map<String, Object>> mylist)
	{
		List<Map<String, Object>> jsonList = new ArrayList();
		if (!mylist.isEmpty()) {
			for (int i = 0; i < mylist.size(); i++) {
				Map<String, Object> jsoMap = new HashMap();
				Map searchParams = mylist.get(i);
				jsoMap.put("id", searchParams.get("ID"));
				jsoMap.put("text", searchParams.get("NAME"));
				Map<String, Object> data = new HashMap<String, Object>();
				//data.put("readOnly", searchParams.get("READ_ONLY"));
				data.put("name", searchParams.get("NAME"));
				data.put("id", searchParams.get("ID"));
				data.put("group_type", searchParams.get("GROUP_TYPE"));
////				data.put("IS_ADMIN", searchParams.get("IS_ADMIN"));
////				data.put("IS_DELETE", searchParams.get("IS_DELETE"));
//				data.put("IS_SAVE", searchParams.get("IS_SAVE"));
				data.put("opreators", getDocumentOperator(String.valueOf(searchParams.get("AUTHS"))));
				jsoMap.put("data", data);
				if (String.valueOf(searchParams.get("PARENT_ID")).equals("0") || String.valueOf(searchParams.get("PARENT_ID")).equals("")) {
					jsoMap.put("parent", "#");
				} else {
					jsoMap.put("parent", searchParams.get("PARENT_ID"));
				}
				jsonList.add(jsoMap);
				
			}

			return JsonUtils.object2json(jsonList);
		}
		return null;
	}
	
	
	private static String getDocumentOperator(String auths)
	{
		
		System.out.println(auths);
		String operators="";
		String tmpSave="1";
		String tmpDonw="1";
		String tmpDele="1";
		String tmpAdmin="1";
		//v.IS_SAVE,v.IS_DONWLOAD,v.IS_DELETE,v.IS_ADMIN顺序
		if(auths==null||auths.equals(""))
		{
			operators="1111";
		}else if(auths.contains(","))
		{
			String[] operator=auths.split(",");
			for(int i=0;i<operator.length;i++)
			{
				char[] chars=operator[i].toCharArray();
				if(tmpSave.equals("1")&&chars[0]=='0')
				{
					tmpSave="0";
				}
				if(tmpDonw.equals("1")&&chars[1]=='0')
				{
					tmpDonw="0";
				}
				if(tmpDele.equals("1")&&chars[2]=='0')
				{
					tmpDele="0";
				}
				if(tmpAdmin.equals("1")&&chars[3]=='0')
				{
					tmpAdmin="0";
				}
			}
			operators=tmpSave+tmpDonw+tmpDele+tmpAdmin;
		}else
		{
			operators=auths;
		}
		
		return operators;
	}
}

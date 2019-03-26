package com.yonyou.util.wsystem.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.util.BussnissException;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;
import com.yonyou.util.wsystem.api.HttpRequestAbs;

public class HttpRequestByData extends HttpRequestAbs{
	
	private String dataSourceCode ="CD_HTTP_MESSAGE";
	
	@Autowired
	private IBaseDao baseDao =(BaseDao)SpringContextUtil.getBean("dcmsDAO");
	@Override
	public String httpRespString(String url, String jsonString) {
		// TODO 自动生成的方法存根
		//通过数据库获取 json信息 
		
		String returnJsonMessage ="";
		System.out.println(returnJsonMessage);
		try {
			SqlTableUtil httpTable =DataSourceUtil.dataSourceToSQL(dataSourceCode);
			SqlWhereEntity sqlWhere =new SqlWhereEntity();
			sqlWhere.putWhere("SERVICE_CODE", jsonString, WhereEnum.EQUAL_STRING);
			Map<String,Object> returnMap =baseDao.find(httpTable.appendSqlWhere(sqlWhere));
			if(returnMap!=null&&returnMap.containsKey("RETURN_MESSAGE")){
				returnJsonMessage =(String)returnMap.get("RETURN_MESSAGE");
			}
//			}else{
//				initHttpMessage();
//				//return httpRespString(url,jsonString);
//			}
			
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		return returnJsonMessage;
	}
	
	
	private void initHttpMessage(){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("SERVICE_CODE", "001");
		map.put("SERVICE_NAME", "用户");
		map.put("RETURN_MESSAGE", "{'NAME':'MOING_INK'}");
		
		Map<String,Object> map1 =new HashMap<String,Object>();
		map1.put("SERVICE_CODE", "002001");
		map1.put("SERVICE_NAME", "应收差异调整按钮");
		map1.put("RETURN_MESSAGE","{\"message\":\"<button type='button' buttonToken='button_check' onclick='click_check(this)' name='复核' id='CHECK' isCheckBox='yes' hiddenType='show' class='btn btn-default'><span class='glyphicon' aria-hidden='true'></span>复核</button><button type='button' buttonToken='button_checkback' onclick='click_checkback(this)' name='撤销复核' id='CHECKBACK' isCheckBox='yes' hiddenType='show' class='btn btn-default'><span class='glyphicon' aria-hidden='true'></span>撤销复核</button><button type='button' buttonToken='button_import' onclick='click_import(this)' name='EXCEL导入' id='IMPORT' isCheckBox='' hiddenType='show' class='btn btn-default'><span class='glyphicon' aria-hidden='true'></span>EXCEL导入</button>\",\"js\":\"function click_check(abc){alert('复核按钮请求')}  function click_checkback(abc){alert('112233')} \"}");
		
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		list.add(map);
		list.add(map1);
		baseDao.insert(dataSourceCode, list);
	}
	
	
}

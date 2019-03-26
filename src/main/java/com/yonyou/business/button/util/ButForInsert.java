package com.yonyou.business.button.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.IBaseDao;

public class ButForInsert extends ButtonAbs{

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		// TODO 自动生成的方法存根
		
		String dataSourceCode =request.getParameter("dataSourceCode");
		String tabName = findTableNameByDataSourceCode(dataSourceCode);
		if(dataSourceCode!=null&&dataSourceCode.length()>0){
			Map<String,String> dataMap=DataSourceUtil.getDataSource(dataSourceCode);
			if(dataMap!=null){
				tabName=dataMap.get(DataSourceUtil.DATASOURCE_METADATA_CODE);
			}
		}
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		this.appendData(json,request);
		try {
			dcmsDAO.insertByTransfrom(tabName, json);
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String jsonMessage = "{\"message\":\"保存成功\"}";
		this.ajaxWrite(jsonMessage, request, response);
		
		return null;
	}

	@Override
	protected boolean befortOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	protected void afterOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO 自动生成的方法存根
		
	}

}

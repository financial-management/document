package com.yonyou.business.button.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlWhereEntity;

public class ButForUpdate extends ButtonAbs {

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		// TODO 自动生成的方法存根
		String dataSourceCode =request.getParameter("dataSourceCode");
		String tabName = findTableNameByDataSourceCode(dataSourceCode);
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		this.appendData(json,request);
		SqlWhereEntity whereEntity =new SqlWhereEntity();
		try {
			this.appendWhereByIDs(json, whereEntity);
			dcmsDAO.updateByTransfrom(tabName, json, whereEntity);
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		String jsonMessage = "{\"message\":\"修改成功\"}";
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
	
	private void appendWhereByIDs(JSONObject json,SqlWhereEntity whereEntity){
		
			String id = json.getString("ID");
			whereEntity.putWhere("ID", id, WhereEnum.EQUAL_INT);
	}
	
}

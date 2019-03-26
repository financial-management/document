package com.yonyou.business.button.util;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlWhereEntity;

public class ButForAudit extends ButtonAbs {

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		// TODO 自动生成的方法存根
		String dataSourceCode =request.getParameter("dataSourceCode");
		String tabName = findTableNameByDataSourceCode(dataSourceCode);
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		//this.appendData(json,request);
		SqlWhereEntity whereEntity =new SqlWhereEntity();
		Map<String,Object> data =new HashMap<String,Object>();
		String audit_type=request.getParameter("audit_type");
		data.put("BILL_STATUS", audit_type);
		if(!audit_type.equals("1")){
			String audit_message =request.getParameter("audit_message");
			if(audit_message.trim().toUpperCase().equals("NULL")){
				audit_message="";
			}
			data.put("APPROVER", TOKEN_ENTITY.USER.getName());
			data.put("AUDTI_DATE", new Timestamp(new java.util.Date().getTime()));
			data.put("APPROVAL_OPINION", audit_message);
		}
		this.appendWhereByIDs(json, whereEntity);
		dcmsDAO.update(tabName, data, whereEntity);
		
		
		
		String jsonMessage = "{\"message\":\""+this.findReturnMessage(audit_type)+"\"}";
		this.ajaxWrite(jsonMessage, request, response);
		return null;
	}
	
	private String findReturnMessage(String audit_type){
		String totalMessage="";
		switch(audit_type){
			
		case "1":
			totalMessage="提交成功"; break;
		case "2":
			totalMessage="提交成功"; break;
		case "3":
			totalMessage="审批成功"; break;
		case "4":
			totalMessage="审批成功"; break;
		case "7":
			totalMessage="退回成功"; break;
		}
		
		return totalMessage;
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

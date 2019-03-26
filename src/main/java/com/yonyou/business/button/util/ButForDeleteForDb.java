package com.yonyou.business.button.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SqlWhereEntity;
import com.yonyou.util.sql.SQLUtil.WhereEnum;

public class ButForDeleteForDb extends ButtonAbs {

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		// TODO 自动生成的方法存根
		String dataSourceCode =request.getParameter("dataSourceCode");
		String tabName = findTableNameByDataSourceCode(dataSourceCode);
		JSONArray jsonArray = JSONArray.fromObject(request.getParameter("jsonData"));
		SqlWhereEntity sqlWhere=new SqlWhereEntity();
		StringBuffer ids =new StringBuffer();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jb = (JSONObject) jsonArray.get(i);
			String id = jb.getString("ID");
			ids.append(id).append(",");
		}
		if(ids.length()>0){
			ids.setLength(ids.length()-1);
		}
		sqlWhere.putWhere("ID", ids.toString(), WhereEnum.IN);
 		dcmsDAO.delete(tabName, sqlWhere);
		String jsonMessage = "{\"message\":\"删除成功\"}";
		
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

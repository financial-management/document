package com.yonyou.business.button.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.RmDictReferenceUtil;
import com.yonyou.business.button.ButtonAbs;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.util.BussnissException;
import com.yonyou.util.JsonUtils;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.replace.WhereReplaceUtil;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

public class ButForSelect extends ButtonAbs{

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		// TODO 自动生成的方法存根
		String dataSourceCode = request.getParameter("dataSourceCode");
		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		
		SqlWhereEntity whereEntity =getQueryCondition(request);
		sqlEntity.appendSqlWhere(getQueryCondition(request));
		String pageParam=this.findPageParam(request);
		if(pageParam!=null&&pageParam.length()>0){
			sqlEntity.appendWhereAnd(pageParam);
		}
		String limitStr = request.getParameter("limit");
		String offsetStr = request.getParameter("offset");
		int limit = limitStr != null ? Integer.parseInt(limitStr) : 10;
		int offset = offsetStr != null ? Integer.parseInt(offsetStr) : 0;
		
		//添加替换数据
		WhereReplaceUtil.appendReplaceWhereSql(sqlEntity, dataSourceCode, request);
		
		List<Map<String, Object>> mapList = dcmsDAO.query(sqlEntity, offset, limit);
		mapList = RmDictReferenceUtil.transByDict(mapList, dataSourceCode);
		int total = 10;
		total = dcmsDAO.queryLength(sqlEntity);
		sqlEntity.clearTableMap();
		// 需要返回的数据有总记录数和行数据
		String json = "{\"total\":" + total + ",\"rows\":" + JsonUtils.object2json(mapList) + "}";
		System.out.println("*********************"+"\t"+TOKEN_ENTITY.USER.getName()+
				"\t"+TOKEN_ENTITY.COMPANY.getCompany_name()+"****\t"+TokenUtil.findSystemDateString());
		this.ajaxWrite(json, request, response);
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

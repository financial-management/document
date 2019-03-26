package com.yonyou.business.button;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.MetaDataUtil;
import com.yonyou.business.ProxyPageUtil;
import com.yonyou.business.entity.TokenEntity;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.util.BussnissException;
import com.yonyou.util.ConditionTypeUtil;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.page.proxy.PageBulidHtmlAbs;
import com.yonyou.util.page.proxy.bulid.PageBulidHtmlBySel;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlWhereEntity;

public abstract class ButtonAbs {
	
	protected TokenEntity TOKEN_ENTITY =null;
	
	protected  abstract boolean befortOnClick(IBaseDao dcmsDAO,HttpServletRequest request,HttpServletResponse response);
	
	public Object onClick(IBaseDao dcmsDAO,HttpServletRequest request,HttpServletResponse response) throws IOException, BussnissException{
		TOKEN_ENTITY =TokenUtil.initTokenEntity(request);
		Object returnObj =null;
		//如果验证不通过，befortOnClick()返回false，反之返回true
		if(befortOnClick(dcmsDAO,request,response)){
			return null;
		};
		returnObj=execute(dcmsDAO,request,response);
		afterOnClick(dcmsDAO,request,response);
		return returnObj;
	}
	protected abstract Object execute(IBaseDao dcmsDAO,HttpServletRequest request,HttpServletResponse response) throws IOException,BussnissException;
	
	
	protected  abstract void afterOnClick(IBaseDao dcmsDAO,HttpServletRequest request,HttpServletResponse response);
	
	protected void ajaxWrite(String ajaxMessage,HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(ajaxMessage);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: getQueryCondition 
	* @Description: 依据规范，由request获取所有查询条件，并拼接查询条件实例。
	* @param request
	* @return
	* @throws BussnissException
	 */
	protected SqlWhereEntity getQueryCondition(HttpServletRequest request) throws BussnissException {
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "SEARCH-");
		//获取字段信息map——包含元数据+代理信息
		PageBulidHtmlAbs justForSelFieldInfo = new PageBulidHtmlBySel();
		Map<String, ConcurrentHashMap<String, String>> selFieldMap = justForSelFieldInfo.findData(dataSourceCode);
		
		SqlWhereEntity sqlWhere = new SqlWhereEntity();
		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
				
			String columnName = entry.getKey();
			Object value = entry.getValue();
				
			// FIXME 参选回写带来的问题-回传了一个数组
			if (value instanceof String[]) {
				value = ((String[]) value)[0];
			}

			if (value != null && value.toString().length() > 0) {
				WhereEnum whereEnum = null;
				
				//配置为日期区间的columnName为字段名+后缀
				if (selFieldMap.containsKey(columnName)) {
					Map<String, String> fieldInfo = selFieldMap.get(columnName);
					// 拼接逻辑：以代理中配置的CONDITION_TYPE为主，若未配置则依据元数据的INPUT_TYPE
					if (fieldInfo.containsKey(ProxyPageUtil.PROXYSELECT_CONDITION_TYPE)
							&& null != fieldInfo.get(ProxyPageUtil.PROXYSELECT_CONDITION_TYPE)
							&& "" != fieldInfo.get(ProxyPageUtil.PROXYSELECT_CONDITION_TYPE).trim()) {
						String condTypeCode = fieldInfo.get(ProxyPageUtil.PROXYSELECT_CONDITION_TYPE);
						whereEnum = ConditionTypeUtil.ConditionMapBase.get(condTypeCode);
					} else {
						String inputType = fieldInfo.get(MetaDataUtil.FIELD_INPUT_TYPE);
						whereEnum = ConditionTypeUtil.ConditionMapForInputType.get(inputType);
					}
				} else {
					// 日期区间特殊处理
					if (columnName.indexOf("_FROM") != -1) {
						columnName = columnName.replaceAll("_FROM", "");
						whereEnum = WhereEnum.TO_DATE_GREATER;
					} else if (columnName.indexOf("_TO") != -1) {
						columnName = columnName.replaceAll("_TO", "");
						whereEnum = WhereEnum.TO_DATE_LESS;
					}
				}
				
				sqlWhere.putWhere(columnName, value.toString(), whereEnum);
			}
		}
		return sqlWhere;
	}
	
	protected String findTableNameByDataSourceCode(String dataSourceCode) throws BussnissException{
		String tableName="";
		Map<String,String> dataMap=DataSourceUtil.getDataSource(dataSourceCode);
		if(dataMap!=null&&dataMap.containsKey(DataSourceUtil.DATASOURCE_METADATA_CODE)){
			tableName= dataMap.get(DataSourceUtil.DATASOURCE_METADATA_CODE);
		}
		
		return tableName;
		
	}
	
	protected String findPageParam(HttpServletRequest request){
		String pageParam=request.getParameter("pageParam");
		if(pageParam!=null&&pageParam.length()>2){
			pageParam=pageParam.substring(1, pageParam.length()-1);
		}
		return pageParam;
	}
	
	protected void appendData(Map<String,String> dataMap,HttpServletRequest request){
		
	}
}

package com.yonyou.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.MetaDataUtil;
import com.yonyou.business.ProxyPageUtil;
import com.yonyou.business.RmDictReferenceUtil;
import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.ConditionTypeUtil;
import com.yonyou.util.JsonUtils;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.page.PageUtil;
import com.yonyou.util.page.proxy.PageBulidHtmlAbs;
import com.yonyou.util.page.proxy.bulid.PageBulidHtmlBySel;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

@RestController
@RequestMapping(value = "/button")
public class ButtonController {
	
	
	@Autowired
	protected IBaseDao dcmsDAO;
	
	protected Map<String,ButtonAbs> findButtonMap(){
		return new HashMap<String,ButtonAbs>();
	}
	
	@RequestMapping(params = "cmd=button")
	public void button(HttpServletRequest request, HttpServletResponse response)  {

		String buttonToken =request.getParameter("buttonToken");
		
		if(findButtonMap().containsKey(buttonToken)){
			try {
				findButtonMap().get(buttonToken).onClick(dcmsDAO, request, response);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (BussnissException e) {
				// TODO 自动生成的 catch 块
				this.ajaxWrite(e.getMessage(), request, response);
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: 查询数据库记录 
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=init")
	public void init(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		sqlEntity.appendSqlWhere(getQueryCondition(request));
		String limitStr = request.getParameter("limit");
		String offsetStr = request.getParameter("offset");
		int limit = limitStr != null ? Integer.parseInt(limitStr) : 10;
		int offset = offsetStr != null ? Integer.parseInt(offsetStr) : 0;
		List<Map<String, Object>> mapList = dcmsDAO.query(sqlEntity, offset, limit);
		mapList = RmDictReferenceUtil.transByDict(mapList, dataSourceCode);
		System.out.println("#######mapList\t" + mapList);
		int total = 10;
		total = dcmsDAO.queryLength(sqlEntity);
		sqlEntity.clearTableMap();
		System.out.println("#######length\t" + 10);
		// 需要返回的数据有总记录数和行数据
		String json = "{\"total\":" + total + ",\"rows\":" + JsonUtils.object2json(mapList) + "}";
		System.out.println("############" + json);
		
		out.print(json);
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
	public SqlWhereEntity getQueryCondition(HttpServletRequest request) throws BussnissException {
		
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

	/**
	 * 
	* @Title: queryColumns 
	* @Description: 初始化列布局
	* @param request
	* @param response
	* @throws IOException
	* @throws BussnissException
	 */
	@RequestMapping(params = "cmd=queryColumns")
	public void queryColumns(HttpServletRequest request,HttpServletResponse response) throws IOException, BussnissException {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		String jsonMessage = IBootUtil.findColJosnStr(IBootUtil.findShowFiledNameMap(sqlEntity.getShowFiledMap(),sqlEntity.getFiledNameMap()));
		System.out.println("####   " + jsonMessage);
		
		out.print(jsonMessage);
		out.flush();
		out.close();

	}
	
	/**
	 * 
	* @Title: queryParam 
	* @Description: 初始化页面查询条件
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=queryParam")
	public void queryParam(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		String paramHtml = PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_SELECT);
		
		out.print(paramHtml);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: queryMaintainCols 
	* @Description: 初始化维护页面
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=queryMaintainCols")
	public void queryMaintainCols(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		String maintainHtml = PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE);
		
		out.print(maintainHtml);
		out.flush();
		out.close();

	}
	
	protected void ajaxWrite(String ajaxMessage,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			out.print(ajaxMessage);
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally{
			
			out.flush();
			out.close();
		}
		
	}
	
	
}

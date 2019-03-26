package com.yonyou.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.util.BussnissException;
import com.yonyou.util.page.PageUtil;
import com.yonyou.util.page.table.column.CheckBoxEnum;
import com.yonyou.util.page.table.column.TableColumn;
import com.yonyou.util.page.table.column.TableColumnUtil;
import com.yonyou.util.sql.SqlTableUtil;


/**
 * 
* @ClassName: ReferenceController 
* @Description: 参选页面控制类
* @author 德发
* @date 2016年12月27日 
*
 */
@RestController
@RequestMapping(value = "/reference")
public class ReferenceController extends BaseController {
	
	@Override
	public void queryColumns(HttpServletRequest request,HttpServletResponse response) throws IOException, BussnissException {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		String isRadio = request.getParameter("isRadio");
		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		TableColumn tableColumn =TableColumnUtil.findTableColumn();
		
		if(isRadio!=null&&isRadio.length()>0&&isRadio.equals("1")){
			tableColumn.setCheckBoxEnum(CheckBoxEnum.RADIO);
		}
		String jsonMessage = tableColumn.findColJosnStr(IBootUtil.findShowFiledNameMap(sqlEntity.getShowFiledMap(),sqlEntity.getFiledNameMap()));;
		System.out.println("####   " + jsonMessage);
		
		out.print(jsonMessage);
		out.flush();
		out.close();

	}
	@Override
	public void queryParam(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "SEARCH-");
		String dataSourceCode = request.getParameter("dataSourceCode");
		String paramHtml =PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_SELECT,bulidDataMap(searchParams));
		out.print(paramHtml);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: bulidDataMap 
	* @Description: 工具方法-将页面查询字段id拼装回字段
	* @param searchParams
	* @return
	 */
	private Map<String,Object> bulidDataMap(Map<String,Object> searchParams){
		Map<String,Object> dataMap =new HashMap<String,Object>();
		if(searchParams!=null&&searchParams.size()>0){
			for(String key:searchParams.keySet()){
				String dataKey =key;
				if(key.indexOf("-")!=-1){
					dataKey=key.split("-")[1];
				}
				dataMap.put(dataKey, searchParams.get(key));
			}
		}
		return dataMap;
	}
	
}

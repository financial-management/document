package com.yonyou.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.RmDictReferenceUtil;
import com.yonyou.util.JsonUtils;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

/**
 * @author XIANGJIAN
 * @date 创建时间：2017年4月28日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/billQuery")
public class BillQueryController extends BaseController {
	
	/**
	 * 
	* @Title: init 
	* @Description: 查询数据库记录 
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=getBillSelect")
	public void init(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String message = "" ; 
		String dataSourceCode = request.getParameter("dataSourceCode");
		
		String metadataId = "";
		Map<String,String> dataMap=DataSourceUtil.getDataSource(dataSourceCode);
		if(dataMap!=null&&dataMap.containsKey(DataSourceUtil.DATASOURCE_METADATA_ID)){
			metadataId= dataMap.get(DataSourceUtil.DATASOURCE_METADATA_ID);
		}
		Boolean dateFlag = true ; 
		Boolean statusFlag = true ; 
		String queryField = dataMap.get("QUERY_FIELD");
		if(queryField.indexOf("BILL_STATUS") != -1){
			statusFlag = false ; 
		}
		if(queryField.indexOf("BILL_DATE") != -1){
			dateFlag = false ; 
		}
		
		
		SqlTableUtil stu = new SqlTableUtil("CD_METADATA_DETAIL","");
		SqlWhereEntity swe = new SqlWhereEntity();
		swe.putWhere("METADATA_ID", metadataId, WhereEnum.EQUAL_STRING).putWhere("FIELD_CODE", "BILL_STATUS", WhereEnum.EQUAL_STRING).putWhere("DR", "0", WhereEnum.EQUAL_STRING);
		stu.appendSelFiled("INPUT_FORMART").appendSqlWhere(swe);
		List<Map<String, Object>>  list =  dcmsDAO.query(stu);
		
		String billStatus = "" ; 
		int len = list.size() ; 
		if(queryField.trim().length() == 0  ){
			//message =getErrorMessage( "该数据源暂未配置查询字段！") ;
			message = "";
			System.out.println(this.getClass().getName() +  " -----该数据源暂未配置查询字段！");
		}else{
			if(len > 0){
				for(Map<String,Object> map : list){
					if(map.get("INPUT_FORMART") == null){
						//message =getErrorMessage("BILL_STATUS该字段暂未配置数据字段!") ;
						message = "";
						System.out.println(this.getClass().getName() +  " -----BILL_STATUS该字段暂未配置数据字段!");
					}else{
						billStatus = map.get("INPUT_FORMART").toString() ;
						String html = getHtml(RmDictReferenceUtil.getDictReference(billStatus),statusFlag,dateFlag);
						message = JsonUtils.object2json(html) ; 
					}
				}
			}else{
				//message =getErrorMessage( "没有BILL_STATUS字段，请核实后再操作！" ); 
				message = "";
				System.out.println(this.getClass().getName() +  " -----没有BILL_STATUS字段，请核实后再操作！");
			}
		}
		out.print(message);
		out.flush();
		out.close();
	}
	
	private String SEL_SHOW_NAME="审批";
	
	private String getDateHtml(boolean dateFlag){
		if(dateFlag){
			return "" ;
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("<div class='m-row' id='bill_date_div'>"+
					"<label>"+SEL_SHOW_NAME+"日期：</label>"+
					"<span class='badge' id='bill_date_noLimit'>不限</span>"+
					"<span class='' id='bill_date_today'>今天</span>"+
					"<span class='' id='bill_date_this_week'>本周</span>"+
					"<span class='' id='bill_date_this_month'>本月</span>"+
					"</div>");
			return sb.toString() ; 
		}
	}
	
	private String getStatusHtml(ConcurrentHashMap<String, String> concurrentHashMap,boolean statusFlag){
		if(statusFlag){
			return "" ;
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("<div class='m-row' id='bill_status_div'>"+
					"<label>"+SEL_SHOW_NAME+"状态：</label>"+
					"<span class='badge' value=''>不限</span>");
			for(String  str : concurrentHashMap.keySet()){
				sb.append("<span class=''  value='"+str+"'>"+concurrentHashMap.get(str)+"</span>" );
			}
			sb.append("</div>");
			return sb.toString() ; 
		}
	}
	
	private String getErrorMessage(String message){
		StringBuffer sb = new StringBuffer();
			sb.append("<div class='m-row'>");
			sb.append("<label>"+message+"</label>");
			sb.append("</div>");
		return sb.toString() ; 
	}
	
	private String getHtml(ConcurrentHashMap<String, String> concurrentHashMap,boolean statusFlag , boolean dateFlag){
		if(statusFlag && dateFlag){
			return "<div></div>" ; 
		}
		StringBuffer sb = new StringBuffer(); 
		sb.append("<div class='searchbox'>");
			sb.append(getDateHtml(dateFlag));
			sb.append(getStatusHtml(concurrentHashMap, statusFlag)) ;
		sb.append("</div>");
		return sb.toString() ; 
	}
	
	
}

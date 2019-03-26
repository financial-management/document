package com.yonyou.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.MetaDataUtil;
import com.yonyou.business.ProxyPageUtil;
import com.yonyou.business.RmDictReferenceUtil;
import com.yonyou.business.WorkflowNodeUtil;
import com.yonyou.business.button.util.IPublicBusColumn;
import com.yonyou.business.entity.TokenEntity;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.doc.DocBean;
import com.yonyou.doc.FileManager;
import com.yonyou.util.BussnissException;
import com.yonyou.util.ConditionTypeUtil;
import com.yonyou.util.JsonUtils;
import com.yonyou.util.SerialNumberUtil;
import com.yonyou.util.SerialNumberUtil.SerialType;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.page.PageUtil;
import com.yonyou.util.page.proxy.PageBulidHtmlAbs;
import com.yonyou.util.page.proxy.bulid.PageBulidHtmlBySel;
import com.yonyou.util.replace.WhereReplaceUtil;
import com.yonyou.util.sql.DataSourceCodeConstants;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;
import com.yonyou.util.validator.BulidValidMain;
import com.yonyou.util.wsystem.service.DocClient;

/**
 * 
* @ClassName: BaseController 
* @Description: 基础controller类,可处理基本CRUD操作，如存在其他业务操作可继承后重写方法
* @author 博超
* @date 2016年12月27日 
*
 */
@RestController
@RequestMapping(value = "/base")
public class BaseController  implements DataSourceCodeConstants{

	@Autowired
	protected IBaseDao dcmsDAO;
	
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
		String pageParam=request.getParameter("pageParam");
		if(pageParam!=null&&pageParam.length()>2){
			pageParam=pageParam.substring(1, pageParam.length()-1);
			sqlEntity.appendWhereAnd(pageParam);
		}
		sqlEntity.appendSqlWhere(getQueryCondition(request));
		
		String limitStr = request.getParameter("limit");
		String offsetStr = request.getParameter("offset");
		int limit = limitStr != null ? Integer.parseInt(limitStr) : 10;
		int offset = offsetStr != null ? Integer.parseInt(offsetStr) : 0;
		//添加替换数据
		WhereReplaceUtil.appendReplaceWhereSql(sqlEntity, dataSourceCode, request);
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
	 * 更改doc_document表字段 ISDOCSAVETRUE【业务是否保存】 状态	 batch_no
	 * @param request
	 * @param response
	 * @throws Exception
	 * {"batch_no":"DOC000000000015532"}
	 */
	@RequestMapping(params = "cmd=isdocsavetrue") 
	public void IsDocSaveTrue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject reqjson = JSONObject.fromObject(URLDecoder.decode(request.getParameter("jsonData"),"utf-8"));
		System.out.println("com.yonyou.web.BaseController.IsDocSaveTrue(HttpServletRequest, HttpServletResponse)入参{}"+reqjson);
		String batchNo=reqjson.getString("batch_no").toString();
		if(batchNo != null && batchNo.length() >0){
			String result = FileManager.getListHtml(batchNo, false);
			if (result!= null && result.length() >0) {
				DocBean db=new DocBean();
				db.setBatchNo(batchNo);
				FileManager.updataIsDocSaveTrueByBatchno(db);
			}
		}
		
	}
	
	@RequestMapping(params = "cmd=uploadCor") 
	public void uploadCor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(params = "cmd=jsload") 
	public void jsload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String nodeCode = request.getParameter("nodeCode");
		if(null==nodeCode || "".equals(nodeCode)){
			
	}
		String roleCode = request.getParameter("roleCode");
		if(null==roleCode || "".equals(roleCode)){
			
			
		}

		 PrintWriter out;
			try {
				String jsstr =" function loadjsTest(){ "
						+ "alert('11111');\r\n"
						+ "alert('22222');\r\n"
						+ "alert('33333');\r\n"
						+ "}";
				out = response.getWriter();
				response.setContentType("application/json;charset=UTF-8");   
				out.write(jsstr.toString());
			    out.flush();	                  
			    out.close(); 
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}  
			
	}
	
	@RequestMapping(params = "cmd=buttonload") 
	public void buttonload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 PrintWriter out;
			try {
				String buttonstr =" <button id=\"button\" type=\"button\" class=\"btn btn-default\"	onclick=\"loadjsTest()\">动态测试</button>";
				out = response.getWriter();
				response.setContentType("application/json;charset=UTF-8");   
				out.write(buttonstr.toString());
			    out.flush();	                  
			    out.close(); 
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}  
			
	}
	
	/**
	 * 
	* @Title: uploadAffix 
	* @Description: 上传附件
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=uploadAffix") 
	public void uploadAffix(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="files") MultipartFile files) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		Map<String,Object> dataMap =new HashMap<String, Object>();
		TokenEntity token = TokenUtil.initTokenEntity(request);
        
		try{
			if(null==token){			
				result.put("token", "获取为空！");
			}
			//制单人
			dataMap.put(IPublicBusColumn.CREATOR_ID, token.USER.getId());
			dataMap.put(IPublicBusColumn.CREATOR_NAME, token.USER.getName());
	        	
			//组织字段
			dataMap.put(IPublicBusColumn.ORGANIZATION_ID, token.COMPANY.getCompany_id());
			dataMap.put(IPublicBusColumn.ORGANIZATION_NAME, token.COMPANY.getCompany_name());
			
			if(null==token.USER.getId() || "".equals(token.USER.getId())){			
				result.put("userid", "获取为空！");
			}
			if(null==token.COMPANY.getCompany_id() || "".equals(token.COMPANY.getCompany_id())){			
				result.put("companyid", "获取为空！");
			}
				
			
			System.err.println("***************************************"+dataMap);
			
			String batchNo = request.getParameter("batchNo");
			String bs_code =request.getParameter("bs_code");
			String bscode=request.getParameter("code");
			
			
			if(bs_code=="" || "".equals(bs_code) || bs_code==null){
				bs_code=bscode;   //备用
			}
			
			
			//System.out.println(request.getParameter("fieldCode"));
			if(null==bs_code || "".equals(bs_code.trim())){			
				result.put("bs_code", "获取为空！");
			}
			//有参数获取异常，返回报错
			if(result.size()>0){
				return ;				
			}
			String directoryId=request.getParameter("did");
			String isbusiness=request.getParameter("isbusiness");//标识是否是业务过来的 1 是
			System.out.println("我的目录权限是："+directoryId);
			DocBean bean = new DocBean();
			bean.setBatchNo(batchNo);
			bean.setUserId(token.USER.getId());
			bean.setCompanyId(token.COMPANY.getCompany_id());
			String fileName=URLDecoder.decode(files.getOriginalFilename());
			bean.setFileName(fileName);
			bean.setFileContent(files.getBytes());
			if(directoryId!=null&&!directoryId.equals(""))			
				bean.setDirectoryId(directoryId);
	//		FIXME
	//		bean.setBsCode(bsCode);
			bean.setBatchNo(batchNo);
			/*如果是业务过来的则判断是否有BS_CODE 没有返回ERR*/
			if (bs_code.equals("")) {
				if (isbusiness != null && isbusiness.equals("1")) {
					String jsonstr = "{\"status\":\"fail\"}";
					PrintWriter out = response.getWriter();
					response.setCharacterEncoding("utf-8");
					out.print(JSONObject.fromObject(jsonstr));
					out.flush();
					out.close();
				}
			}
			bean.setBsCode(bs_code);//
			result = FileManager.uploadqx(bean,dataMap);	
		}catch(Exception e){
		
			result.put("exception", e.getMessage());
			e.printStackTrace();
		}finally{
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.print(JsonUtils.object2json(result));
			out.flush();
			out.close();
        }
	}
	

	@RequestMapping(params = "cmd=uploadAffixtain") 
	public void uploadAffixtain(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="files") MultipartFile files) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		TokenEntity token = TokenUtil.initTokenEntity(request);
		String batchNo = request.getParameter("batchNo");
		//数据源编码
		String bs_code =request.getParameter("bscode");
		
		String directoryId=request.getParameter("did");
		
		if(batchNo==null || "".equals(batchNo)){
			
			batchNo=SerialNumberUtil.getSerialCode(SerialType.DOC_BATCH_NO, "DOC");
		}
		directoryId="1";
		System.out.println("我的目录权限是："+directoryId);
		DocBean bean = new DocBean();
		bean.setBatchNo(batchNo);
		bean.setUserId(token.USER.getId());
		bean.setCompanyId(token.COMPANY.getCompany_id());
		bean.setFileName(files.getOriginalFilename());
		bean.setFileContent(files.getBytes());
		if(directoryId!=null&&!directoryId.equals(""))
			
			bean.setDirectoryId(directoryId);
//		FIXME
//		bean.setBsCode(bsCode);
		bean.setBatchNo(batchNo);
		bean.setBsCode(bs_code);
		
		
		result = FileManager.upload(bean);
				
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.print(JsonUtils.object2json(result));
		out.flush();
		out.close();
	}
	
	
	
	
	
	
	/**
	 * 获取附件列表
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=getAffixList") 
	public void getAffixList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String result = null;
		String batchNo = request.getParameter("batchNo");
		if(batchNo != null && batchNo.length() >0){
			result = FileManager.getListHtml(batchNo, false);
		}
		
		JSONObject retJson =new JSONObject();
		retJson.put("message",result);
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.print(retJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 下载附件
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=downloadAffix") 
	public ResponseEntity<byte[]> downloadAffix(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileId = request.getParameter("fid");
		DocBean bean = FileManager.download(fileId);
		ResponseEntity<byte[]> responseEntity = null;
		if(bean != null){
			byte[] fileContent = bean.getFileContent();
			HttpHeaders headers = new HttpHeaders();
			List<Charset> charSet = new ArrayList<Charset>();
			charSet.add(Charset.forName("UTF-8"));
			headers.setAcceptCharset(charSet);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(bean.getFileName(), "UTF-8"));  
			responseEntity = new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.CREATED);
		}

		return responseEntity;
	}
	
	/**
	 * 
	 * @Title: deleteAffix 
	 * @Description: 删除附件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=deleteAffix") 
	public void deleteAffix(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileId = request.getParameter("fid");
		JSONObject result = new JSONObject();
		if(FileManager.delete(fileId)){
			result.put("message", "删除成功");
		}else{
			result.put("message", "删除失败,请联系管理员");
		}
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.print(result);
		out.flush();
		out.close();
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
		String jsonMessage = PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_TABLE);
		
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
	
	@RequestMapping(params = "cmd=queryValids")
	public void queryValids(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String dataSourceCode = request.getParameter("dataSourceCode");
		JSONObject validJson = BulidValidMain.findValidJson(PageUtil.findFiledData(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE));
		JSONObject returnJson =new JSONObject();
		System.out.println("------------------------------");
		System.out.println(validJson);
		returnJson.put("message", validJson);
		out.print(returnJson);
		out.flush();
		out.close();

	}
	
	/**
	 * 
	* @Title: queryIndUpdateCols 
	* @Description: 初始化修改页面
	* @param request
	* @param response
	* @throws Exception
	 */
	@RequestMapping(params = "cmd=queryIndUpdateCols")
	public void queryIndUpdateCols(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String dataSourceCode = request.getParameter("dataSourceCode");
		String indUpdateHtml;
		//如数据源没有配置独立修改页面字段则取维护页面配置字段（及其维护页面代理设置）-兼容原有数据
		String updateField = DataSourceUtil.getDataSource(dataSourceCode).get(DataSourceUtil.DATASOURCE_UPDATE_FIELD);
		if( updateField!=null && updateField.trim().length()>0 ){
			indUpdateHtml = PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INDEPENDENT_UPDATE);
		}else{
			indUpdateHtml = PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE);
		}
		
		out.print(indUpdateHtml);
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
	@RequestMapping(params = "cmd=findData")
	public void findData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String dataSourceCode = request.getParameter("dataSourceCode");
		String pageParam=request.getParameter("pageParam");
		String showType =request.getParameter("showType");
		SqlTableUtil sqlTableUtil =DataSourceUtil.dataSourceToSQL(dataSourceCode);
		if(pageParam!=null&&pageParam.length()>2){
			pageParam=pageParam.substring(1, pageParam.length()-1);
		}
		sqlTableUtil.appendWhereAnd(pageParam);
		Map<String,Object> dataMap =this.dcmsDAO.find(sqlTableUtil);
		String maintainHtml="";
		if(showType!=null&&showType.length()>0&&"TEXT".equals(showType.toUpperCase())){
			maintainHtml =PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE_TEXT,dataMap);
		}else{
			maintainHtml =PageUtil.findPageHtml(dataSourceCode, PageUtil.PAGE_TYPE_FOR_INSERT_UPDATE,dataMap);
		}
		
		out.print(maintainHtml);
		out.flush();
		out.close();

	}
	
	/**
	 * 
	* @Title: insRow 
	* @Description: 新增行 
	* @param request
	* @param response
	* @throws IOException
	 */
	@RequestMapping(params = "cmd=insRow")
	public void insRow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String tabName = request.getParameter("tabName");
		
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
//		Map<String,Object> entity = json;
		try {
			tabName=this.findTableName(tabName, request);
			dcmsDAO.insertByTransfrom(tabName, json);
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		String jsonMessage = "{\"message\":\"保存成功\"}";
		out.print(jsonMessage);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: delRows 
	* @Description: 删除行-支持多行 
	* @param request
	* @param response
	* @throws IOException
	* @throws BussnissException
	 */
	@RequestMapping(params = "cmd=delRows")
	public void delRows(HttpServletRequest request,HttpServletResponse response) throws IOException, BussnissException {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String tabName = request.getParameter("tabName");
		JSONArray jsonArray = JSONArray.fromObject(request.getParameter("jsonData"));
		List<Map<String, Object>> entityList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jb = (JSONObject) jsonArray.get(i);
			String id = jb.getString("ID");
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("ID", id);
			entity.put("DR","1");
			entityList.add(entity);
		}
		tabName=this.findTableName(tabName, request);
 		dcmsDAO.update(tabName, entityList, new SqlWhereEntity());
		
		String jsonMessage = "{\"message\":\"删除成功\"}";
		out.print(jsonMessage);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: editRow 
	* @Description: 修改行 
	* @param request
	* @param response
	* @throws IOException
	 */
	@RequestMapping(params = "cmd=editRow")
	public void editRow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		String tabName = request.getParameter("tabName");
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		try {
			tabName=this.findTableName(tabName, request);
			dcmsDAO.updateByTransfrom(tabName, json, new SqlWhereEntity());
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		String jsonMessage = "{\"message\":\"修改成功\"}";
		out.print(jsonMessage);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: getQueryCondition 
	* @Description: 依据规范，由request获取所有查询条件，并拼接查询条件实例。
	* @param request
	* @return
	 * @throws Exception 
	 */
	public SqlWhereEntity getQueryCondition(HttpServletRequest request) throws Exception {
		
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
					} else {
						throw new Exception("数据源"+dataSourceCode+"所关联元数据中不存在请求字段"+columnName+",无法获取相关字段信息");
					}
				}
				
				sqlWhere.putWhere(columnName, value.toString(), whereEnum);
			}
		}
		return sqlWhere;
	}
	
	/**
	 * 
	* @Title: cacheClear 
	* @Description: 服务器缓存清理 
	* @param request
	* @param response
	* @throws IOException
	 */
	@RequestMapping(params = "cmd=cacheClear")
	public void cacheClear(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		int cacheEnum = Integer.parseInt(request.getParameter("cacheEnum"));
		String cacheName = "";
		String messageTemp = "清理成功!";
		switch (cacheEnum) {
		case 0:
			cacheName = "所有缓存";
			DataSourceUtil.clear();
			MetaDataUtil.clear();
			RmDictReferenceUtil.clear();
			WorkflowNodeUtil.clear();
			ProxyPageUtil.clear();
			break;
		case 1:
			cacheName = "数据源缓存";
			DataSourceUtil.clear();
			break;
		case 2:
			cacheName = "元数据缓存";
			MetaDataUtil.clear();
			break;
		case 3:
			cacheName = "数据字典缓存";
			RmDictReferenceUtil.clear();
			break;
		case 4:
			cacheName = "代理页面缓存";
			ProxyPageUtil.clear();
			break;
		default:
			messageTemp = "ERROR：未知缓存";
			break;
		}
		
		String jsonMessage = "{\"message\":\""+cacheName+ messageTemp +"\"}";
		out.print(jsonMessage);
		out.flush();
		out.close();
	}
	
	private String findTableName(String tabName,HttpServletRequest request) throws BussnissException{
		String tableName=tabName;
		String dataSourceCode =request.getParameter("dataSourceCode");
		if(dataSourceCode!=null&&dataSourceCode.length()>0){
			Map<String,String> dataMap=DataSourceUtil.getDataSource(dataSourceCode);
			if(dataMap!=null){
				tableName=dataMap.get(DataSourceUtil.DATASOURCE_METADATA_CODE);
			}
		}
		return tableName;
	}
	
	@ResponseBody
	@RequestMapping(params = "cmd=allNoticeJSON") //排序后按照未查看状态在前其次时时间在前方式传5条数据
	public void allNoticeJSON(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//String dataSourceCode = request.getParameter("dataSourceCode");
		List<Map<String, Object>> mapList = dcmsDAO.query(DataSourceUtil.dataSourceToSQL("NF_NOTICE"));
		String json=JsonUtils.object2json(mapList);
		
		out.print(json); //对页面显示公告进行排序 1 未阅在前(IS_READ=0) 2 时间大在前(根据数据库记录问题可以取最后的数据)
						//SELECT * FROM NF_NOTICE ORDER BY IS_READ ASC, NOTICE_DATE DESC;
		out.flush();
 		out.close();
	}
	
	@ResponseBody
	@RequestMapping(params = "cmd=noticeJSON") //排序后按照未查看状态在前其次时时间在前方式传5条数据
	public void noticeJSON(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String dataSourceCode = request.getParameter("dataSourceCode");
		SqlTableUtil sqlTableUtil =DataSourceUtil.dataSourceToSQL(dataSourceCode);
		//添加替换数据
		WhereReplaceUtil.appendReplaceWhereSql(sqlTableUtil, dataSourceCode, request);
		List<Map<String, Object>> mapList = dcmsDAO.query(sqlTableUtil,1,5);
		String json=JsonUtils.object2json(mapList);
		
		out.print(json); //对页面显示公告进行排序 1 未阅在前(IS_READ=0) 2 时间大在前(根据数据库记录问题可以取最后的数据)
						//SELECT * FROM NF_NOTICE ORDER BY IS_READ ASC, NOTICE_DATE DESC;
		out.flush();
 		out.close();
	}
	@ResponseBody	
	@RequestMapping(params = "cmd=updateState") 
	public void updateState(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
 		String id=request.getParameter("ID");
 		if (id!=null) {
			System.err.println("###########################获取ajaxID参数："+id);
			SqlTableUtil sqlEntity = new SqlTableUtil("NF_NOTICE", "").appendSelFiled("IS_READ").appendWhereAnd("ID = "+id);
			List<Map<String, Object>> aList = dcmsDAO.query(sqlEntity);
			Object mapp=aList.get(0).get("IS_READ");
			if (mapp.equals("0")) {
				//update NF_NOTICE set IS_READ='1' where ID = '1000999900000000024';
				dcmsDAO.updateBySql("update NF_NOTICE set IS_READ='1' where ID = "+id);
			}
		}
 		
	}
    //扫描文件夹下的所有的文件并进行上传
	@RequestMapping(params = "cmd=enc" ,method= RequestMethod.POST)
	public  void  enc(HttpServletResponse response,HttpServletRequest request)throws Exception{
		String message=request.getParameter("message");
		Map<String,String> map=new HashMap<String,String>();
		JSONArray jObject=new JSONArray().fromObject(message);
		
		for(int i=0;i<jObject.size();i++){
			JSONObject jsons=jObject.getJSONObject(i);
			Iterator iter = jsons.keys();
			while(iter.hasNext()){
				String  name=(String) iter.next();
				map.put(name,jsons.getString(name));
			}	
		}
		readAllFile(map.get("src"),map);
	}
	private static List<String> listname=new ArrayList<String>();
	private static List<String> listNO=new ArrayList<String>();
	public static void readAllFile(String filepath, Map<String, String> map) throws Exception {
		File file= new File(filepath);
		if(!file.isDirectory()){ 
			 //listname.add(file.getName());
			 FileInputStream fileInputStream = new FileInputStream(file);
			 //if(listNO.size()>1){}else{listNO.add(SerialNumberUtil.getSerialCode(SerialType.DOC_BATCH_NO, "DOC"));}
			 MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
             busUpload(multipartFile,map,"");
		}else if(file.isDirectory()){
			 System.out.println("文件");
			 listname.add(file.getName());
			 listNO.add(SerialNumberUtil.getSerialCode(SerialType.DOC_BATCH_NO, "DOC"));
			 map.put("BUSCODE",listname.get(listname.size()-1));
			 map.put("bsno", listNO.get(listNO.size()-1));
			 //if(listname.size()==2){map.put("BUSCODE",listname.get(1));}
			String[] filelist=file.list();
			for(int i = 0;i<filelist.length;i++){
				File readfile = new File(filepath);
				if (!readfile.isDirectory()) {
                    listname.add(readfile.getName());
                    FileInputStream fileInputStream = new FileInputStream(readfile);
                    MultipartFile multipartFile = new MockMultipartFile(readfile.getName(), readfile.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                    //busUpload(multipartFile,map);
                    
				} else if (readfile.isDirectory()) {
					readAllFile(filepath + java.io.File.separator + filelist[i],map);//递归
				}
			}
		}
		//for(int i = 0;i<listname.size();i++){
		//	System.out.println(listname.get(i));
		//}
	}

	//业务附件上传
		public static void busUpload(MultipartFile files,Map<String,String> message,String bsNo) throws Exception {
			
			Map<String, String> result = new HashMap<>();
			//TokenEntity token = TokenUtil.initTokenEntity(request);
			String batchNo =message.get("bsno"); //request.getParameter("batchNo");
			//数据源编码
			String bs_code =message.get("datacode");
			
			String directoryId="";//request.getParameter("did");
			if(batchNo==null || "".equals(batchNo)){
				batchNo=SerialNumberUtil.getSerialCode(SerialType.DOC_BATCH_NO, "DOC");
			}
			directoryId="1";
			System.out.println("我的目录权限是："+directoryId);
			DocBean bean = new DocBean();
			bean.setBatchNo(batchNo);
			bean.setUserId("000");
			bean.setCompanyId("000");
			bean.setFileName(files.getOriginalFilename());
			bean.setFileContent(files.getBytes());
			if(directoryId!=null&&!directoryId.equals(""))
				bean.setDirectoryId(directoryId);
//			FIXME
			bean.setBatchNo(batchNo);
			bean.setBsCode(bs_code);
			result = FileManager.allUpload(bean,message);	
//			PrintWriter out = response.getWriter();
//			response.setCharacterEncoding("utf-8");
//			out.print(JsonUtils.object2json(result));
//			out.flush();
//			out.close();
		}
	
	
}

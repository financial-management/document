package com.yonyou.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.net.aso.l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.RmDictReferenceUtil;
import com.yonyou.business.entity.TokenEntity;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.util.BussnissException;
import com.yonyou.util.JsonUtils;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.TreeUtil;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.log.BusLogger;
import com.yonyou.util.replace.WhereReplaceUtil;
import com.yonyou.util.service.DocumentMgrService;
import com.yonyou.util.service.IBaseService;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.wsystem.util.DocumentTreeUtil;

@RestController
@RequestMapping(value = "/documentTree")
public class DocumentTreeController extends BaseController {

	@Autowired
	private DocumentMgrService service = (DocumentMgrService) SpringContextUtil
			.getBean("docService");

	@RequestMapping(value = "ajaxTree", method = RequestMethod.GET)
	public void ajaxDatas(HttpServletRequest request,
			HttpServletResponse response) throws BussnissException {
		String dataSourceCode = "DOC_DIRECTORY_AUTH_TREE";
		String userid = request.getParameter("");
		String roleID = request.getParameter("");// 号隔开 如果只有一个 不需要加 ，

		//userid = "0";
		//roleID = "0";

//		String authSql = "select d. *,a.AUTHORIZE_OBJECT_ROLE,a.AUTHORIZE_OBJECT_USER "
//				+ "from doc_directory d,doc_authorize a "
//				+ "where d.ID=a.DIRECTORY_ID  AND d.DR='0'";

		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		 //String totalCode= request.getParameter("TOTAL_CODE");

		String condition = "DR='0' ";
//		if (!userid.equals("")) {
//			authSql = authSql+" AND (AUTHORIZE_OBJECT_USER = '" + userid + "' OR "
//					+ "AUTHORIZE_OBJECT_ROLE in('" + roleID + "'))";
//		}

		sqlEntity.appendWhereAnd(condition);
		List<Map<String, Object>> mapList = BaseDao.getBaseDao().query(sqlEntity);
		mapList = RmDictReferenceUtil.transByDict(mapList, dataSourceCode);
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			String json = DocumentTreeUtil.getClickTree(mapList);// toJOSNString(mapList);
			System.out.println("json:" + json);
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	
	@RequestMapping(value = "ajaxAuthTree", method = RequestMethod.GET)
	public void ajaxAuthDatas(HttpServletRequest request,
			HttpServletResponse response) throws BussnissException {
		String dataSourceCode = "DOC_DIRECTORY_AUTH_TREE";
		String userid = request.getParameter("");
		String roleID = request.getParameter("");// 号隔开 如果只有一个 不需要加 ，


		SqlTableUtil sqlEntity = DataSourceUtil.dataSourceToSQL(dataSourceCode);
		 //String totalCode= request.getParameter("TOTAL_CODE");

		//group_type
		String condition = "DR='0' AND GROUP_TYPE!='2'";
		

		sqlEntity.appendWhereAnd(condition);
		List<Map<String, Object>> mapList = BaseDao.getBaseDao().query(sqlEntity);
		mapList = RmDictReferenceUtil.transByDict(mapList, dataSourceCode);
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			String json = DocumentTreeUtil.getClickTree(mapList);// toJOSNString(mapList);
			System.out.println("json:" + json);
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	
	/**
	 * 
	 * @Title: save
	 * @Description: 个人文件夹授权
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=dirPersion")
	public void persionSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(request.getParameter("jsonData"));
		String jsonData = request.getParameter("jsonData");
		TokenEntity tokenEntity = TokenUtil.initTokenEntity(request);
		String userid = tokenEntity.USER.getId();

		System.out.println("当前用户ID:" + userid);
		String id = service.insertPersionDirectory(jsonData, userid);
		
		//记录日志
		HashMap<String, String> map = new HashMap();		
		 
		map.put(BusLogger.LOG_ACTION_TYPE, "login_log");
		map.put(BusLogger.LOG_USER_ID, tokenEntity.USER.getId());
		map.put(BusLogger.LOG_USER_ID_NAME, tokenEntity.USER.getName());
		map.put(BusLogger.LOG_OWNER_ORG_ID, tokenEntity.COMPANY.getCompany_id());
		//map.put(BusLogger.LOG_CONTENT, "登录人:"+loginId+";角色:"+roleIds+";公司:"+company_id+";token:"+tokenEntity.getToken());
		map.put(BusLogger.LOG_OPERATION_INFO, "login");		
		map.put(BusLogger.LOG_ACTION_MODULE, "login");	
		
		BusLogger.record_log(map);
		
		PrintWriter out = response.getWriter();
		System.out.println("个人文档ID:" + id);
		out.print("创建成功");
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 个人文件夹删除 级联删除权限
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=delPersion")
	public void persionDel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(request.getParameter("jsonData"));
		String jsonData = request.getParameter("jsonData");
		TokenEntity token = TokenUtil.initTokenEntity(request);
		String userid = token.USER.getId();

		System.out.println("当前用户ID:" + userid);
		String id = service.deletePersionDirectory(jsonData, userid);
		PrintWriter out = response.getWriter();
		System.out.println("删除数据:" + id);
		out.print("删除成功");
		out.flush();
		out.close();
	}
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 个人文件夹删除 级联删除权限
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=authSave")
	public void authSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(request.getParameter("jsonData"));
		String jsonData = request.getParameter("jsonData");
		
		String id = service.saveAuth(jsonData);
		PrintWriter out = response.getWriter();
		System.out.println("删除数据:" );
		String jsonMessage = "{\"message\":\"操作成功\"}";
		out.print(jsonMessage);
		out.flush();
		out.close();
	}

	
	
	
	@RequestMapping(value = "authDocumentTree", method = RequestMethod.GET)
	public void authDocumentTree(HttpServletRequest request,
			HttpServletResponse response) throws BussnissException {
		String dataSourceCode = "DOC_AUTH_VIEW";
		String selSql = "select *, count(distinct v.`NAME`),GROUP_CONCAT(v.IS_SAVE,v.IS_DONWLOAD,v.IS_DELETE,v.IS_ADMIN) AS AUTHS from doc_auth_view v  where dr='0' ";
		TokenEntity tokenEntity = TokenUtil.initTokenEntity(request);
		String userid=tokenEntity.USER.getId();
		//userid="1000105000000000028";
		String roleid=tokenEntity.ROLE.getRoleId();
		String orgid=tokenEntity.COMPANY.getCompany_id();
		if(userid!=null&&!userid.equals(""))
		{
			selSql=selSql+" AND (AUTHORIZE_OBJECT_USER='"+userid+"' ";
			if(roleid!=null&&!roleid.equals(""))
			{
				selSql=selSql+" OR AUTHORIZE_OBJECT_ROLE='"+roleid+"' ";

			}
			if(orgid!=null&&!orgid.equals(""))
			{
				selSql=selSql+" OR AUTHORIZE_OBJECT_ORG='"+orgid+"' ";

			}
			selSql=selSql+ ")";
		}
		selSql=selSql+" OR PARENT_ID='1'  group by v.`NAME`";

		List<Map<String, Object>> list=BaseDao.getBaseDao().query(selSql, "");
		//List<Map<String,Object>> authList =new ArrayList();
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			String json = DocumentTreeUtil.getAuthClickTree(list);// toJOSNString(mapList);
			System.out.println("json:" + json);
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 查询子目录
	 * @param request
	 * @param response
	 * @throws BussnissException
	 */
	@RequestMapping(value = "queryChildTree")
	@ResponseBody
	public String queryChildTree(@RequestBody String jsonStr) throws BussnissException {
		System.out.println(jsonStr);
		JSONObject jsonObject = new JSONObject();
		JSONObject json=null;
		try {
			json = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(json==null||!json.containsKey("ID")){
			jsonObject.put("status", "fail");
			jsonObject.put("msg", "参数错误");
			return jsonObject.toString();
		}
		List<Map<String, Object>> query = BaseDao.getBaseDao().query("select * from doc_directory d where d.PARENT_ID='"+json.get("ID")+"' and d.DR='0'", "");
		if(query.size()!=0){
			jsonObject.put("status", "fail");
			jsonObject.put("msg", "下级目录删除后才可删除本目录");
			return jsonObject.toString();
		}

		jsonObject.put("status", "success");
		return jsonObject.toString();
	}
	/**
	 * 查询父目录
	 * @param request
	 * @param response
	 * @throws BussnissException
	 */
	@RequestMapping(value = "/queryParentTree")
	@ResponseBody
	public String queryParentTree(HttpServletRequest request) throws BussnissException {
		JSONObject jsonObject = new JSONObject();
		if(request.getParameter("ID")==null||request.getParameter("ID").length()==0){
			jsonObject.put("status", "fail");
			jsonObject.put("msg", "参数错误");
			return jsonObject.toString();
		}

		List<Map<String, Object>> query = BaseDao.getBaseDao().query("select concat(id) as ID,concat(parent_id) as PARENT_ID from doc_directory d where   d.DR='0'", "");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		getParent(query,list,request.getParameter("ID"));
		
		jsonObject.put("data", list);
		jsonObject.put("status", "success");
		return jsonObject.toString();
	}

	private void getParent(List<Map<String, Object>> data,List<Map<String, Object>> parentList,String id){
		for (Map<String, Object> map : data) {
			if(map.get("ID").toString().equals(id)){
				parentList.add(map);
				if(map.get("PARENT_ID").equals(0)){
					return ;
				}
				getParent(data, parentList, map.get("PARENT_ID").toString());
				return ;
			}
			
		}
	}
	
	@RequestMapping(value= "fzmessage", method = RequestMethod.GET)
	public void fzmessage(HttpServletRequest request,
			HttpServletResponse response,String id) throws Exception {
        
		   IBaseDao isDao=BaseDao.getBaseDao();
		   //select `NAME`,BS_CODE from doc_directory where id in (select DIRECTORY_ID from doc_authorize where AUTHORIZE_OBJECT_USER=1000105000000000267) and CREATOR_ID="1000105000000000267"  GROUP BY `NAME`
		   //select  DISTINCT(NAME),BS_CODE from  doc_directory  where BS_CODE  in   (select BS_CODE from doc_document GROUP BY BS_CODE)
		   //select `NAME`,BS_CODE from doc_directory where BS_CODE in (select d.BS_CODE from doc_document d , (select DIRECTORY_ID,AUTHORIZE_OBJECT_USER from doc_authorize where AUTHORIZE_OBJECT_USER=1000105000000000267) q where d.USER_ID=q.AUTHORIZE_OBJECT_USER  GROUP BY d.BS_CODE) GROUP BY BS_CODE
		   //select `NAME`,BS_CODE from doc_directory where id in (select DIRECTORY_ID from doc_authorize where AUTHORIZE_OBJECT_USER="+id+") and CREATOR_ID="+id+"  GROUP BY `NAME`
		   //select `NAME`,BS_CODE from doc_directory where BS_CODE in (select d.BS_CODE from doc_document d , (select DIRECTORY_ID,AUTHORIZE_OBJECT_USER from doc_authorize where AUTHORIZE_OBJECT_USER="+id+" ) q where d.USER_ID=q.AUTHORIZE_OBJECT_USER  GROUP BY d.BS_CODE) GROUP BY BS_CODE
		   
		   
		   //select q.`NAME`,c.BS_CODE from doc_document c ,(select d.BS_CODE,d.`NAME` from doc_directory d , (select DIRECTORY_ID,AUTHORIZE_OBJECT_USER from doc_authorize where AUTHORIZE_OBJECT_USER=1000105000000000267) p where d.ID=p.DIRECTORY_ID GROUP BY d.`NAME`) q where q.BS_CODE=c.BS_CODE GROUP BY q.`NAME` 
		   String sql="select q.`NAME`,c.BS_CODE from doc_document c ,(select d.BS_CODE,d.`NAME` from doc_directory d , (select DIRECTORY_ID,AUTHORIZE_OBJECT_USER from doc_authorize where AUTHORIZE_OBJECT_USER="+id+") p where d.ID=p.DIRECTORY_ID GROUP BY d.`NAME`) q where q.BS_CODE=c.BS_CODE GROUP BY q.`NAME`";
		   List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		   list=isDao.query(sql,"");
	
		   String jc="{'id':0,'text':1,'data':{'id':0,'group_type':'2','opreators':'0002','name':1},'parent':'1'}";
		   JSONObject jsObject=new JSONObject().fromObject(jc);
		   List<String> lStrings=new ArrayList<String>();
		   for(Map<String,Object> map:list){
			   if(map.get("NAME")==""|| map.get("NAME")==null || map.get("NAME").equals("")){
				   continue ;
			   }
			   
			   //for(Map.Entry<String,Object> entry:map.entrySet()){
				   JSONObject jsObjects=new JSONObject().fromObject(jsObject.get("data"));
					  jsObjects.put("id",map.get("BS_CODE"));
					  jsObjects.put("name",map.get("NAME"));
					  
				      jsObject.put("id",map.get("BS_CODE"));
				      jsObject.put("text",map.get("NAME"));
				      jsObject.put("data",jsObjects);
				      
				     
			   //}
			   lStrings.add(jsObject.toString());
		   }
		   lStrings.add("{'id':'1','text':'联通智网 ','data':{'id':'1','group_type':'0','opreators':'0000','name':'联通智网 '},'parent':'#'}");
		   response.getWriter().print(JSONArray.fromObject(lStrings));
		   response.getWriter().flush();
		   response.getWriter().close();
	}
}

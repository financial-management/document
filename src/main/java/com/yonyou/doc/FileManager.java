package com.yonyou.doc;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.util.BussnissException;
import com.yonyou.util.SerialNumberUtil;
import com.yonyou.util.SerialNumberUtil.SerialType;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

public class FileManager{
	
  public static Logger logger = LoggerFactory.getLogger(FileManager.class);
                 
                 
  private static IBaseDao dcmsDAO = BaseDao.getBaseDao();
  
  public static final String DIRECTORY = "DOC_DIRECTORY";
  public static final String DOCUMENT = "DOC_DOCUMENT";
  public static final String AUTHORIZE = "DOC_AUTHORIZE";
  
  
  /**
   * 更改文档单据保存状态
   * @param bean
   */
  public static void updataIsDocSaveTrueByBatchno(DocBean bean){
	  dcmsDAO.updateBySql("UPDATE doc_document SET ISDOCSAVETRUE=1 WHERE BATCH_NO='"+bean.getBatchNo()+"'");
  }
  
  /**
   * 上传附件
  * @param bean
  * @return HashMap<String, String> 包含dfs_path、batch_no、file_id
   */
  public static HashMap<String, String> upload(DocBean bean){
	  HashMap<String, String> retMap = null;
	  //上传至FastDFS
	  String dfsPath = FastdfsClient.upload(bean.getFileContent(), bean.getFileName(), bean.getDescription());
	  if(dfsPath != null){
		  String userId = bean.getUserId();
		  String companyId = bean.getCompanyId();
		  
		  HashMap<String, String> docEntity = new HashMap<>();
		  HashMap<String, Object> docEntitys = new HashMap<>();
		  docEntity.put("FILE_NAME", bean.getFileName());
		  docEntity.put("USER_ID", userId);
		  docEntity.put("USER_NAME", bean.getUserName());
//		  docEntity.put("UPLOAD_TIME", System.currentTimeMillis());
		  docEntity.put("BS_CODE", bean.getBsCode());
		  if(bean.getBsCode()!=null&&!bean.getBsCode().equals(""))
		  {
			  docEntity.put("DIRECTORY_ID", findDirectory(bean.getBsCode(), companyId));
		  }else
		  {
			  docEntity.put("DIRECTORY_ID",bean.getDirectoryId());
		  }
		  docEntity.put("DFS_PATH", dfsPath);
		  //批次号自动生成，同单据同字段下附件批次号相同
		  String batchNo = bean.getBatchNo();
		  if(batchNo == null || batchNo.length() == 0){
			  batchNo = getBatchNo();
		  }
		  docEntity.put("BATCH_NO", batchNo);
		  //业务附件固定属性
		  docEntity.put("USABLE_STATUS", "0");
		  docEntity.put("READ_ONLY", "1");
		  for(Map.Entry<String,String> mEntry:docEntity.entrySet()){
			  docEntitys.put(mEntry.getKey(),mEntry.getValue());
		  }
		  
		  
		  //try { 
			String fileId = dcmsDAO.insert(DOCUMENT, docEntitys);
			//上传成功返回map，包含dfs_path、batch_no、file_id
			
			System.err.println("______________________________"+fileId);
			
			retMap = new HashMap<>();
			retMap.put("filename", bean.getFileName());
			retMap.put("dfspath", dfsPath);
			retMap.put("batchno", batchNo);
			retMap.put("fileid", fileId);
		  //} catch (BussnissException e) {
			//logger.error("上传文件入库失败");
			//FastdfsClient.delete(dfsPath);
			//e.printStackTrace();
		 // }
	  }
	  return retMap;
  }
  
  public static HashMap<String, String> uploadqx(DocBean bean,Map<String,Object> lmMap){
	  HashMap<String, String> retMap = null;

	  //上传至FastDFS
	  String dfsPath = FastdfsClient.upload(bean.getFileContent(), bean.getFileName(), bean.getDescription());
	  if(dfsPath != null){
		  String userId = bean.getUserId();
		  String companyId = bean.getCompanyId();
		  
		  HashMap<String, String> docEntity = new HashMap<>();
		  HashMap<String, Object> docEntitys = new HashMap<>();
		  docEntity.put("FILE_NAME", bean.getFileName());
		  docEntity.put("USER_ID", userId);
		  docEntity.put("USER_NAME", bean.getUserName());
//		  docEntity.put("UPLOAD_TIME", System.currentTimeMillis());
		  docEntity.put("BS_CODE", bean.getBsCode());
		  if(bean.getBsCode()!=null&&!bean.getBsCode().equals(""))
		  {
			  docEntity.put("DIRECTORY_ID", findDirectoryqx(bean.getBsCode(), companyId,lmMap));
		  }else
		  {
			  docEntity.put("DIRECTORY_ID",bean.getDirectoryId());
		  }
		  docEntity.put("DFS_PATH", dfsPath);
		  //批次号自动生成，同单据同字段下附件批次号相同
		  String batchNo = bean.getBatchNo();
		  if(batchNo == null || batchNo.length() == 0){
			  batchNo = getBatchNo();
		  }
		  docEntity.put("BATCH_NO", batchNo);
		  //业务附件固定属性
		  docEntity.put("USABLE_STATUS", "0");
		  docEntity.put("READ_ONLY", "1");
//		  for(Map.Entry<String,String> mEntry:docEntity.entrySet()){
//			  docEntitys.put(mEntry.getKey(),mEntry.getValue());
//		  }
		  
		  
		  //try {
		  for(Map.Entry<String,Object> Entry:lmMap.entrySet()){
			  docEntitys.put(Entry.getKey(),Entry.getValue());
		  }
		  
		  for(Map.Entry<String,String> Entrys:docEntity.entrySet()){
			  docEntitys.put(Entrys.getKey(),Entrys.getValue());
		  }
		  

		    String fileId = dcmsDAO.insert(DOCUMENT, docEntitys);
			//上传成功返回map，包含dfs_path、batch_no、file_id
			
			System.err.println("______________________________"+fileId);
			
			retMap = new HashMap<>();
			retMap.put("filename", bean.getFileName());
			retMap.put("dfspath", dfsPath);
			retMap.put("batchno", batchNo);
			retMap.put("fileid", fileId);
		  //} catch (BussnissException e) {
			//logger.error("上传文件入库失败");
			//FastdfsClient.delete(dfsPath);
			//e.printStackTrace();
		 // }
	  }
	  return retMap;
  }
  
  /**
   * 业务附件批量上传
   * */
  public static HashMap<String, String> allUpload(DocBean bean,Map<String,String> allMap){
	  HashMap<String, String> retMap = null;
	  //上传至FastDFS
	  String dfsPath = FastdfsClient.upload(bean.getFileContent(), bean.getFileName(), bean.getDescription());
	  if(dfsPath != null){
		  String userId = bean.getUserId();
		  String companyId = bean.getCompanyId();
		  
		  HashMap<String, String> docEntity = new HashMap<>();
		  HashMap<String, Object> docEntitys = new HashMap<>();
		  docEntity.put("FILE_NAME", bean.getFileName());
		  docEntity.put("USER_ID", userId);
		  docEntity.put("USER_NAME", bean.getUserName());
//		  docEntity.put("UPLOAD_TIME", System.currentTimeMillis());
		  docEntity.put("BS_CODE", bean.getBsCode());
		  if(bean.getBsCode()!=null&&!bean.getBsCode().equals(""))
		  {
			  docEntity.put("DIRECTORY_ID", findDirectory(bean.getBsCode(), companyId));
		  }else
		  {
			  docEntity.put("DIRECTORY_ID",bean.getDirectoryId());
		  }
		  docEntity.put("DFS_PATH", dfsPath);
		  //批次号自动生成，同单据同字段下附件批次号相同
		  String batchNo = bean.getBatchNo();
		  if(batchNo == null || batchNo.length() == 0){
			  batchNo = getBatchNo();
		  }
		  docEntity.put("BATCH_NO", batchNo);
		  //业务附件固定属性
		  docEntity.put("USABLE_STATUS", "0");
		  docEntity.put("READ_ONLY", "1");
		  
		  for(Map.Entry<String,String> mEntry:docEntity.entrySet()){
			  docEntitys.put(mEntry.getKey(),mEntry.getValue());
		  }
		  
		  for(Map.Entry<String,String> mEntrys:allMap.entrySet()){
			  docEntitys.put(mEntrys.getKey(),mEntrys.getValue());
		  }
		  
		  docEntitys.remove("src");
		  docEntitys.remove("bsno");
		  //try { 
			String fileId = dcmsDAO.insert(DOCUMENT, docEntitys);
			//上传成功返回map，包含dfs_path、batch_no、file_id
			
			System.err.println("______________________________"+fileId);
			
			retMap = new HashMap<>();
			retMap.put("filename", bean.getFileName());
			retMap.put("dfspath", dfsPath);
			retMap.put("batchno", batchNo);
			retMap.put("fileid", fileId);
		  //} catch (BussnissException e) {
			//logger.error("上传文件入库失败");
			//FastdfsClient.delete(dfsPath);
			//e.printStackTrace();
		 // }
	  }
	  return retMap;
  }
  
  
  /**
   * 由批次号获取文件清单(html)
  * @param batchNo
  * @param needUsable 是否需要过滤可用状态
  * @return html代码
   */
  public static String getListHtml(String batchNo, boolean needUsable){
	  SqlTableUtil sqlTableUtil = new SqlTableUtil(DOCUMENT, "").appendSelFiled("*").appendWhereAnd("DR = 0 and BATCH_NO ='" + batchNo +"'");
	  if(needUsable){
		  sqlTableUtil.appendWhereAnd("USABLE_STATUS = 1");
	  }
	  List<Map<String, Object>> fileList = dcmsDAO.query(sqlTableUtil);
	  
	  StringBuffer listHtml = new StringBuffer("");
	  for (Iterator<Map<String, Object>> iterator = fileList.iterator(); iterator.hasNext();) {
		Map<String, Object> tempFile = iterator.next();
		String fileId = String.valueOf(tempFile.get("ID"));
		String fileName = (String)tempFile.get("FILE_NAME");
		fileName=URLDecoder.decode(fileName);
//		listHtml.append("<li class='list-group-item' id='"
//				+fileId+"'>"+ fileName +"<a href='/document/base?cmd=downloadAffix&fid="
//				+fileId+"' class='btn green'>下载</a><a href='javascript:deleteAffix(\""
//				+fileId+"\")' class='btn red'>删除</a></li>");
		listHtml.append("<div class=\"col-md-4 col-xs-4 col-sm-4 listmessage\" id='"+ fileId+ "'>"
				+ "<div class=\"col-md-9 col-xs-9 col-sm-9 listhide\" data-toggle=\"tooltip\" data-placement=\"top\" title=\""+fileName+"\">"+fileName+"</div>"+
    			"<div class=\"col-md-3 col-xs-3 col-sm-3 listbtn\">"
    			+ "<a href=\"/document/base?cmd=downloadAffix&fid="+ fileId + "\" class=\"btnlist\"  data-toggle=\"tooltip\" data-placement=\"top\" title=\"下载\" id=\"files_a_don\" ><span class=\"glyphicon glyphicon-download-alt\"></span></a>"
    					+ "<a href='javascript:deleteAffix(\""+fileId+"\")' class=\"btnlist\"  data-toggle=\"tooltip\" data-placement=\"top\" title=\"删除\" id=\"files_a_del\" ><span class=\"glyphicon glyphicon-trash\"></span></a></div></div>");
	}
	  return listHtml.toString();
  }

  /**
   * 下载文件
   * @param fileId
   * @return
   */
  public static DocBean download(String fileId){
	  DocBean doc = null;
	  SqlTableUtil sqlTableUtil = new SqlTableUtil(DOCUMENT, "").appendSelFiled("*").appendWhereAnd("DR = 0 and id =" + fileId);
	  Map<String, Object> file= dcmsDAO.find(sqlTableUtil);
	  
	  if(file != null && file.get("DFS_PATH") != null){
		  byte[] fileContent = FastdfsClient.download((String)file.get("DFS_PATH"));
		  if(fileContent != null){
			doc = new DocBean();
		  	doc.setFileName(String.valueOf(file.get("FILE_NAME")));
		  	doc.setFileContent(fileContent);
		  }
	  }else{
		  logger.error(DOCUMENT + "表数据异常：Id：" + fileId);
	  }
	  return doc;
  }
  
  /**
   * 删除文件
  * @param fileId
  * @return
   */
  public static boolean delete(String fileId){
	  SqlWhereEntity whereEntity = new SqlWhereEntity();
	  whereEntity.putWhere("ID", fileId, WhereEnum.EQUAL_INT);
	  whereEntity.putWhere("DR", "0", WhereEnum.EQUAL_STRING);
	  
	  SqlTableUtil sqlTableUtil = new SqlTableUtil(DOCUMENT, "").appendSelFiled("*").appendSqlWhere(whereEntity);
	  Map<String, Object> file = dcmsDAO.find(sqlTableUtil);
	  
	  boolean flag = false;
	  if(file.size() > 0 && file.get("DFS_PATH") != null){
		  int i = dcmsDAO.delete(DOCUMENT, whereEntity);
		  if(i == 1){
			  flag = FastdfsClient.delete((String)file.get("DFS_PATH"));
		  }
	  }
	  return flag;
  }
  
  
  /**
   * 创建批次号
  * @return
   */
  private static String getBatchNo(){
	 return SerialNumberUtil.getSerialCode(SerialType.DOC_BATCH_NO, "DOC");
  }
  
  /**
   * 附件存放目录
  * @param userId
  * @param companyId
  * @return
   */
  private static String findDirectory(String bs_code, String companyId){
	  if(bs_code==null||bs_code.equals(""))
		{
			bs_code="1";
		}else
		{
			String sql= "SELECT * from doc_directory t where t.BS_CODE='"+bs_code+"' and t.ORG_ID='"+companyId+"'";
			List<Map<String, Object>> list = dcmsDAO.query(sql,"");
			if (list.size()==0) {

				String sql1= "SELECT * from doc_directory t where (t.BS_CODE is null or t.BS_CODE='') and t.ORG_ID='"+companyId+"'";
				List<Map<String, Object>> list1 = dcmsDAO.query(sql1,"");
				if(list1.size()==0){
					bs_code="1";
					return bs_code;
				}else{

					String sql2= "SELECT * FROM `cd_datasource` a where a.DR='0' and a.DATASOURCE_CODE='"+bs_code+"'";
					List<Map<String, Object>> list2 = dcmsDAO.query(sql2,"");
					if(list2.size()==0){
						bs_code="1";
						return bs_code;
					}else{
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("NAME", list2.get(0).get("DATASOURCE_DISNAME"));
						map.put("LEVEL", 1);
						map.put("INDEX_GRP", "1");
						map.put("PARENT_ID", list1.get(0).get("ID"));
						map.put("READ_ONLY", "1");
						map.put("VERSION", 1);
						map.put("DR", 0);
						map.put("STATIC_CODE", "1234");
						map.put("GROUP_TYPE", "0");
						map.put("BS_CODE", bs_code);
						map.put("ORG_ID", companyId);
						
						String insert_id = dcmsDAO.insert("doc_directory", map);
						return insert_id;
					}
				}
			}
			return list.get(0).get("ID").toString();
		}
	  return bs_code;
  }
  
  private static String findDirectoryqx(String bs_code, String companyId,final Map<String,Object> lMap){
	  if(bs_code==null||bs_code.equals(""))
		{
			bs_code="1";
		}
	  else{   
		    //业务目录下创建当前登录人目录
			//select c.ID , y.ID yid,y.PARENT_ID,c.`NAME`,y.d1 ,y.f1 from doc_directory c , (select d.ID,d.PARENT_ID,d.`NAME` d1 ,f.`NAME` f1 from  doc_directory d , (select id ,`NAME` from doc_directory where name="华北区域分公司") f where d.PARENT_ID=f.id and d.`NAME`="业务") y where  c.PARENT_ID=y.ID and c.BS_CODE="CUMA_ACCOUNT"
		  
		    //String creatproString="select * from doc_directory  where  PARENT_ID in ( select id from doc_directory where PARENT_ID in (select id from doc_directory where name="+"'"+lMap.get("ORGANIZATION_NAME")+"'"+") and name='业务') and BS_CODE="+"'"+bs_code+"'";
		    //String creatproString="select c.ID , c.PARENT_ID c1 ,y.PARENT_ID y1,c.`NAME`,y.d1 ,y.f1 from doc_directory c , (select d.ID ,d.PARENT_ID,d.`NAME` d1 ,f.`NAME` f1 from  doc_directory d , (select id ,`NAME` from doc_directory where name='"+lMap.get("ORGANIZATION_NAME")+"') f where d.PARENT_ID=f.id and d.`NAME`='业务') y where  c.PARENT_ID=y.ID and c.BS_CODE='"+bs_code+"'";	  
		      String creatproString="select c.ID c,c.PARENT_ID p,y.m,c.NAME from doc_directory c , (select d.ID d,f.m from  doc_directory d , (select id m,`NAME` from doc_directory where name='"+lMap.get("ORGANIZATION_NAME")+"') f where d.PARENT_ID=f.m and d.`NAME`='业务') y where  c.PARENT_ID=y.d and c.BS_CODE='"+bs_code+"'";
			  List<Map<String, Object>> listpro = dcmsDAO.query(creatproString,"");
			
			if(listpro.size()<=0){
				
				String creatproStrings=" select id,parent_id from doc_directory where PARENT_ID in (select id from doc_directory where name="+"'"+lMap.get("ORGANIZATION_NAME")+"'"+") and name='业务'";
				
				final List<Map<String, Object>> listpros = dcmsDAO.query(creatproStrings,"");
				
				if(listpros.size()<=0){
					bs_code="1";
					return bs_code;
				}
				else{
				
				String sql2p= "SELECT * FROM `cd_datasource` a where a.DR='0' and a.DATASOURCE_CODE='"+bs_code+"'";
				
				final List<Map<String, Object>> crpro = dcmsDAO.query(sql2p,"");				
				
				if(crpro.size()==0){
					bs_code="1";
					return bs_code;
				}else{
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("NAME", crpro.get(0).get("DATASOURCE_DISNAME"));
					map.put("LEVEL", 1);
					map.put("INDEX_GRP", "1");
					map.put("PARENT_ID", listpros.get(0).get("ID"));
					map.put("READ_ONLY", "1");
					map.put("VERSION", 1);
					map.put("DR", 0);
					map.put("STATIC_CODE", "1234");
					map.put("GROUP_TYPE", "0");
					map.put("BS_CODE", bs_code);
					map.put("ORG_ID", companyId);
					
					  for(Map.Entry<String,Object> Entry:lMap.entrySet()){
						  map.put(Entry.getKey(),Entry.getValue());
					  }
					 final String insert_idyu= dcmsDAO.insert("doc_directory", map);	//创建	
					  
					 Thread aThread=new Thread(new Runnable() {
							public void run() {
								
								docqx(listpros,insert_idyu,lMap,crpro.get(0).get("DATASOURCE_DISNAME").toString());
							}
						});
					  aThread.start();
					 
					 
					  
					  return insert_idyu;
			  }
				
			
		     }
			}
			else {
				
//				
//				Map<String,Object> mapa=new HashMap<String,Object>();
//				     mapa.put("ID","");
//				     mapa.put("RESOURCE_TYPE","");
//                     mapa.put("DIRECTORY_ID",listpro.get(0).get("ID").toString());
//					 mapa.put("STATIC_CODE","");
//					 mapa.put("AUTHORIZE_OBJECT_ROLE","");
//				     mapa.put("AUTHORIZE_OBJECT_USER",lMap.get("CREATOR_ID")); 
//				     mapa.put("IS_SAVE","0");
//					 mapa.put("IS_DELETE","0"); 
//					 mapa.put("IS_DONWLOAD","0");
//					 mapa.put("DR","0");
//					 mapa.put("IS_ADMIN","1");
//					 mapa.put("AUTHORIZE_OBJECT_ORG","");
//					 mapa.put("ROLE_NAME","");
//					 mapa.put("USER_NAME",lMap.get("CREATOR_NAME"));
//					 mapa.put("ORG_NAME","");
//					 mapa.put("DIRECTORY_NAME",listpro.get(0).get("NAME").toString());
//				
//					 for(Map.Entry<String,Object> Entry:lMap.entrySet()){
//						  mapa.put(Entry.getKey(),Entry.getValue().toString());
//					  }
//				    
//					for(int i=0;i<3;i++){
//						if(i==0){
//							dcmsDAO.insert("DOC_AUTHORIZE", mapa);
//						}
//						else if(i==1){
//							
//							mapa.put("DIRECTORY_ID",listpro.get(0).get("PARENT_ID").toString());
//							mapa.put("DIRECTORY_NAME","业务");
//							dcmsDAO.insert("DOC_AUTHORIZE", mapa);
//						}
//						else{
//							mapa.put("DIRECTORY_ID",listpro.get(0).get("M").toString());
//							mapa.put("DIRECTORY_NAME",mapa.get("ORGANIZATION_NAME"));
//							dcmsDAO.insert("DOC_AUTHORIZE", mapa);
//						}
//						
//					}
				
				
				//dcmsDAO.insert("DOC_AUTHORIZE", mapa);
				
				return listpro.get(0).get("ID").toString();
			}
			/*
			//公共文件
			String sql= "SELECT * from doc_directory t where t.BS_CODE='"+bs_code+"' and t.ORG_ID='"+companyId+"'";
			List<Map<String, Object>> list = dcmsDAO.query(sql,"");
			if (list.size()==0) {

				String sql1= "SELECT * from doc_directory t where (t.BS_CODE is null or t.BS_CODE='') and t.ORG_ID='"+companyId+"'";
				List<Map<String, Object>> list1 = dcmsDAO.query(sql1,"");
				if(list1.size()==0){
					bs_code="1";
					return bs_code;
				}else{

					String sql2= "SELECT * FROM `cd_datasource` a where a.DR='0' and a.DATASOURCE_CODE='"+bs_code+"'";
					List<Map<String, Object>> list2 = dcmsDAO.query(sql2,"");
					if(list2.size()==0){
						bs_code="1";
						return bs_code;
					}else{
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("NAME", list2.get(0).get("DATASOURCE_DISNAME"));
						map.put("LEVEL", 1);
						map.put("INDEX_GRP", "1");
						map.put("PARENT_ID", list1.get(0).get("ID"));
						map.put("READ_ONLY", "1");
						map.put("VERSION", 1);
						map.put("DR", 0);
						map.put("STATIC_CODE", "1234");
						map.put("GROUP_TYPE", "0");
						map.put("BS_CODE", bs_code);
						map.put("ORG_ID", companyId);
						String insert_id = dcmsDAO.insert("doc_directory", map);
						return insert_id;
					}
				}
			}
			return list.get(0).get("ID").toString();
		}
			*/
		}
	  return bs_code;
  }
  
  public static Map<String,Object>  qxmessage(Map<String,Object> map){	  
	  return map;  
  }
  
  public static void docqx(List<Map<String, Object>> listpros,String insert_idyu,Map<String,Object> lMap,String name){
	       
	       //不取公共的ID  
	       Date aDates=new Date();
	       SimpleDateFormat st=new SimpleDateFormat("yyyyMMddHHmmss"); 
	  
	  
	  //判断是否有权限  
		 String isString="select * from doc_authorize where directory_id=";
		 
		 Map<String,Object> mapa=new HashMap<String,Object>();
	     mapa.put("RESOURCE_TYPE","");
         mapa.put("DIRECTORY_ID",insert_idyu);
		 mapa.put("STATIC_CODE","");
		 mapa.put("AUTHORIZE_OBJECT_ROLE","");
	     mapa.put("AUTHORIZE_OBJECT_USER",lMap.get("CREATOR_ID")); 
	     mapa.put("IS_SAVE","0");
		 mapa.put("IS_DELETE","0"); 
		 mapa.put("IS_DONWLOAD","0");
		 mapa.put("DR","0");
		 mapa.put("IS_ADMIN","1");
		 mapa.put("AUTHORIZE_OBJECT_ORG","");
		 mapa.put("ROLE_NAME","");
		 mapa.put("USER_NAME",lMap.get("CREATOR_NAME"));
		 mapa.put("ORG_NAME","");
		 mapa.put("DIRECTORY_NAME",name);
	
		 for(Map.Entry<String,Object> Entry:lMap.entrySet()){
			  mapa.put(Entry.getKey(),Entry.getValue());
		  }
	    
		for(int i=0;i<4;i++){
			if(i==0){
				mapa.put("ID",st.format(aDates)+i);
				
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list=dcmsDAO.query(isString+insert_idyu,"");
				if(list.size()<=0){dcmsDAO.insert("DOC_AUTHORIZE", mapa);}
				else{continue ;}
			}
			else if(i==1){
				mapa.put("ID",st.format(aDates)+i);
				mapa.put("DIRECTORY_ID",listpros.get(0).get("ID"));
				mapa.put("DIRECTORY_NAME","业务");
				
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list=dcmsDAO.query(isString+listpros.get(0).get("ID"),"");							
				if(list.size()<=0){dcmsDAO.insert("DOC_AUTHORIZE", mapa);}
				else{continue ;}
				
			}
			else if(i==2){
				mapa.put("ID",st.format(aDates)+i);
				mapa.put("DIRECTORY_ID",listpros.get(0).get("PARENT_ID"));
				mapa.put("DIRECTORY_NAME",mapa.get("ORGANIZATION_NAME"));
				
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list=dcmsDAO.query(isString+listpros.get(0).get("PARENT_ID"),"");
				if(list.size()<=0){dcmsDAO.insert("DOC_AUTHORIZE", mapa);}
				else{continue ;}
				
				
			}
			else if(i==3){
				mapa.put("ID",st.format(aDates)+i);
				mapa.put("DIRECTORY_ID","1");
				mapa.put("DIRECTORY_NAME","联通智网");
				
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list=dcmsDAO.query(isString+"1"+"  and  user_name='"+lMap.get("CREATOR_NAME")+"'","");
				if(list.size()<=0){dcmsDAO.insert("DOC_AUTHORIZE", mapa);}
				else{continue ;}
			}
			
		}
	  
	  
  }
  
  
}
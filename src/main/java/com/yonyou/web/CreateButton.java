package com.yonyou.web;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.util.RmPartyConstants;

import com.yonyou.util.JsonUtils;
import com.yonyou.util.ZipUtils;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;
@RestController
@RequestMapping(value = "/createButton")
public class CreateButton {
	
	@Autowired
	protected IBaseDao dcmsDAO;
	//用户   公司  菜单  数据源
	public static String getButtonInfo(String userId , String companyId, String menuCode , String dataSourceCode) throws Exception{
		System.out.println( "system CreateButton------userId    	 =" +userId); 
		System.out.println( "system CreateButton------companyId 	 =" +companyId ); 
		System.out.println( "system CreateButton------menuCode  	 =" +menuCode ); 
		System.out.println( "system CreateButton------dataSourceCode=" +dataSourceCode); 
		
		StringBuffer sb = new StringBuffer() ; 
		sb.append("SELECT * FROM RM_BUTTON RB WHERE ID IN ( ");
		sb.append("SELECT  RBRM.BUTTON_ID FROM RM_BUTTON_RELATION_MENU RBRM WHERE RBRM.BUTTON_CODE IN ( ");
		sb.append("SELECT DISTINCT A.TOTAL_CODE FROM RM_AUTHORIZE_RESOURCE A JOIN RM_AUTHORIZE_RESOURCE_RECORD B ");
		sb.append("ON A.ID = B.AUTHORIZE_RESOURCE_ID ");
		sb.append("WHERE SUBSTR(A.TOTAL_CODE, 0, LENGTH(A.TOTAL_CODE) - 3) ='"+menuCode+"' ");
		sb.append("AND B.PARTY_ID IN (SELECT R.ID FROM RM_ROLE R JOIN RM_PARTY_ROLE PR ON R.ID = PR.ROLE_ID WHERE PR.OWNER_PARTY_ID = '" + userId + "' AND ((R.IS_SYSTEM_LEVEL = '1' AND ((R.OWNER_ORG_ID IS NULL)OR(R.OWNER_ORG_ID IS NOT NULL AND EXISTS (SELECT ID FROM RM_PARTY_RELATION RR WHERE RR.PARTY_VIEW_ID = '" + RmPartyConstants.viewId + "' AND RR.PARENT_PARTY_ID = R.OWNER_ORG_ID START WITH RR.CHILD_PARTY_ID = '" + companyId + "' CONNECT BY NOCYCLE PRIOR RR.PARENT_PARTY_ID = RR.CHILD_PARTY_ID)) )) OR (R.IS_SYSTEM_LEVEL = '0' AND R.OWNER_ORG_ID ='" + companyId + "')) AND R.ENABLE_STATUS='1')");
		sb.append(" AND B.IS_BUTTON_AUTHORITY = '1' AND A.DR = '0' AND B.DR = '0')  AND RBRM.DR = '0' ) AND RB.DR = '0'");
		sb.append(" ORDER BY RB.BUTTON_DESC ");
		List<Map<String, Object>> list = BaseDao.getBaseDao().query(sb.toString(), "");
		List<Map<String, Object>> pageList = new ArrayList<Map<String,Object>>(); 
		int len = list.size() ; 
		boolean flag = true ; 
		String publicStr = "查询新增删除修改" ; 
		if(len < 1){
			System.out.println("----------------------" +"没有给角色菜单编码"+menuCode+"下的按钮未授权！");
			return "buttonJson = []";
		}else{
			for(Map<String,Object> map : list){
				if(dataSourceCode.equals(map.get("BUTTON_BATCH"))){
					pageList.add(map);
					flag = false ; 
				}
				if(map.get("BUTTON_BATCH") == null && publicStr.indexOf(map.get("BUTTON_NAME").toString()) > -1){
					pageList.add(map);
				}
			}
			if(flag){
				return "buttonJson = " + getButtonJson(list) + getJsContent(list);
			}else{
				String buttonBatch = "" ; 
				if(pageList.size() < 1){
					System.out.println("----------------------" +"没有给角色菜单编码"+menuCode+"下的按钮未授权！");
					return "buttonJson = []";
				}else{
					for(Map<String,Object> map : pageList){	//	如果该数据源的页面已经含有了独有的按钮，则去掉集合中的公共按钮
						if(publicStr.indexOf(map.get("BUTTON_NAME").toString()) > -1 && dataSourceCode.equals(map.get("BUTTON_BATCH"))){
							buttonBatch += map.get("BUTTON_NAME").toString() ; 
						}
					}
					for(Map<String,Object> map : list){
						if(buttonBatch.indexOf(map.get("BUTTON_NAME").toString()) > -1 && map.get("BUTTON_BATCH") == null){
							pageList.remove(map);
						}
					}
				}
				
				if(pageList.size() < 1){
					System.out.println("----------------------" +"没有给角色菜单编码"+menuCode+"下的按钮未授权！");
					return "buttonJson = []";
				}else{
					return "buttonJson = " + getButtonJson(pageList) + getJsContent(pageList);
				}
			}
		}
	}
	
	/**解码
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static  byte[] decodeBase64(String input) throws Exception{  
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
        Method mainMethod= clazz.getMethod("decode", String.class);  
        mainMethod.setAccessible(true);  
         Object retObj=mainMethod.invoke(null, input);  
         return (byte[])retObj;  
    } 
	
	private static String getJsContent(List<Map<String,Object>> list) throws Exception{
		String jsContent = "" ;
		String space = "JTBB" ;		//空格
		String a = "" ; 
		for(Map<String,Object> map : list){
			if(!"".equals(map.get("JSCONTENT").toString())){
				String str = map.get("JSCONTENT").toString() ; 
				str = ZipUtils.gunzip(str) ;
				map.put("JSCONTENT", str);
				jsContent =space+ str ;
				map.remove("JSCONTENT");
				//a +=new String(decodeBase64(jsContent),"utf-8") ; 
				a+=URLDecoder.decode(new String(decodeBase64(jsContent)),"utf-8") ;
			}
		}
		return a ; 
	}
	
	private static String getButtonJson(List<Map<String,Object>> list){
		if(list.size() > 0){
			List<Map<String, Object>> templist = new  ArrayList<Map<String,Object>>(); 
			for(Map<String, Object > map : list){
				if(!"1".equals(map.get("ISHIDDEN"))){
					Map<String,Object> m = new HashMap<String, Object>();
					m.put("SPAN_CSS", map.get("SPAN_CSS"));
					m.put("BUTTON_TOKEN", map.get("BUTTON_TOKEN"));
					m.put("HTML_CLICK_NAME", map.get("HTML_CLICK_NAME"));
					m.put("BUTTON_DESCRIPTION", map.get("BUTTON_DESCRIPTION"));
					m.put("HTML_POSITION", map.get("HTML_POSITION"));
					m.put("ISCHECKBOX", map.get("ISCHECKBOX"));
					m.put("ISHIDDEN", map.get("ISHIDDEN"));
					m.put("BUTTON_NAME", map.get("BUTTON_NAME"));
					templist.add(m);
				}
			}
			if(templist.size()>0){
				return JsonUtils.list2json(templist);
			}
			return null ; 
		}
		return null ; 
	}
	
	
}

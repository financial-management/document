package com.yonyou.business.entity;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.util.SpringContextUtil;

public class TokenUtil {
	
	
	public static String findSystemDateString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		java.util.Date curTime =new java.util.Date(System.currentTimeMillis());
		java.sql.Timestamp sqlDate=new java.sql.Timestamp (curTime.getTime());
		return dateFormat.format(sqlDate);
	}
	
	public static Date findSystemSqlDate(){
		return new Date(System.currentTimeMillis());
	}
	
	public static java.sql.Timestamp findSystemSqlTimestamp(){
		java.util.Date curTime =new java.util.Date(System.currentTimeMillis());
		java.sql.Timestamp sqlDate=new java.sql.Timestamp (curTime.getTime());
		return sqlDate;
	}
	
	//redis缓存
	private static CacheManager cacheManager = (CacheManager) SpringContextUtil.getBean("cacheManager");
	
	public static TokenEntity initTokenEntity(HttpServletRequest request) {
		
		 TokenEntity  tokenEntity =null;
		 String token = getToken(request);
		 tokenEntity =bulidTokenEntityByToken(token);
		 
		 if(tokenEntity==null){
			 tokenEntity =new TokenEntity();
			 tokenEntity.setEmpty(true);
		 }
		 return tokenEntity;
	}
	
	/**
	 * 由请求获取token-无token则返回空字符串
	* @param request
	* @return
	 */
	public static String getToken(HttpServletRequest request){
		String token =request.getParameter("token");
		//请求中没有则尝试由cookie获取
		if(token==null || token.length()==0){
			token = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null){
		        for(Cookie cookie : cookies){
		        	if("token".equals(cookie.getName())){
		        		token=cookie.getValue();
		        	}
		        }
		    }
		}
		return token;
	}
	
	private static  TokenEntity bulidTokenEntityByToken(String token){
		TokenEntity tokenEntity =null;
		if(token!=null&&token.length()>0){
			tokenEntity = cacheManager.get("SESSION_"+token);
		}else{
			System.out.println("没有获取token信息");
		}
		return tokenEntity;
	}
	
}

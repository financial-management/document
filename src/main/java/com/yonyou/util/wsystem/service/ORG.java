package com.yonyou.util.wsystem.service;

import com.yonyou.util.PropertyFileUtil;
import com.yonyou.util.sql.SQLUtil;
import com.yonyou.util.sql.StringToObjUtil;
import com.yonyou.util.wsystem.api.IMetaDataEntity;
import com.yonyou.util.wsystem.entity.MetaDetal;

/**
 * 组织处理类
 * @author moing_ink
 * <p>创建时间 ： 2016年12月27日
 * @version 1.0
 */
public class ORG   {
	
	private static ORG org =new ORG();
	
	/**
	 * 创建人员调用信息
	 */
	public static USER USER =org.new USER();
	
	
	
	public class USER extends IMetaDataEntity<USER>{
		
		@Override
		protected USER initObj() {
			// TODO 自动生成的方法存根
			return this;
		}
		@Override
		protected void initTable() {
			// TODO 自动生成的方法存根
			TABLE ="RM_USER";
			ALIS_TABLE="US";
		}
		public  MetaDetal ID =new MetaDetal("ID","主键",StringToObjUtil.STRING,"","","");;
		public  MetaDetal NAME=new MetaDetal("NAME","名称",StringToObjUtil.STRING,"","","");
		public  MetaDetal LOCK_STATUS=new MetaDetal("LOCK_STATUS","锁定状态",StringToObjUtil.STRING,"","","");
		public  MetaDetal LOGIN_ID=new MetaDetal("LOGIN_ID","登陆人ID",StringToObjUtil.STRING,"","","");
		public  MetaDetal PASSWORD=new MetaDetal("PASSWORD","密码",StringToObjUtil.STRING,"","","");
		public  MetaDetal AUTHEN_TYPE=new MetaDetal("AUTHEN_TYPE","类型",StringToObjUtil.STRING,"","","");
		public  MetaDetal EMAIL=new MetaDetal("EMAIL","邮件",StringToObjUtil.STRING,"","","");
		public  MetaDetal LOGIN_STATUS=new MetaDetal("LOGIN_STATUS","登陆状态",StringToObjUtil.STRING,"","","");
		public  MetaDetal LAST_LOGIN_DATE=new MetaDetal("LAST_LOGIN_DATE","最后登陆时间",StringToObjUtil.DATE,"","","");
		public  MetaDetal LAST_LOGIN_IP=new MetaDetal("LAST_LOGIN_IP","最后登陆ID",StringToObjUtil.STRING,"","","");
		public  MetaDetal MODIFY_DATE=new MetaDetal("MODIFY_DATE","修改时间",StringToObjUtil.DATE,"","","");
		public  MetaDetal MODIFY_IP=new MetaDetal("MODIFY_IP","修改ID",StringToObjUtil.STRING,"","","");
		public  MetaDetal MODIFY_USER_ID=new MetaDetal("MODIFY_USER_ID","修改用户ID",StringToObjUtil.STRING,"","","");
		
		@Override
		public String findHttpUrl() {
			return PropertyFileUtil.getValue("SYSTEM_URL");
		}
	    
		}
		
	/**
	 * 创建按钮调用信息
	 */
	public static BUTTON BUTTON =org.new BUTTON();
	
	public class BUTTON extends IMetaDataEntity<BUTTON>{
		
		@Override
		protected BUTTON initObj() {
			// TODO 自动生成的方法存根
			return this;
		}
		@Override
		protected void initTable() {
			// TODO 自动生成的方法存根
			TABLE ="RM_BUTTON";
			ALIS_TABLE="RB";
		}
		
		public  MetaDetal ID =new MetaDetal("ID","主键",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_NAME =new MetaDetal("BUTTON_NAME","按钮名称",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_TOKEN =new MetaDetal("BUTTON_TOKEN","按钮标识",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_TYPE =new MetaDetal("BUTTON_TYPE","按钮类型",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_DESC =new MetaDetal("BUTTON_DESC","按钮排序",StringToObjUtil.NUMBER,"","","");
		public  MetaDetal BUTTON_ENTITY =new MetaDetal("BUTTON_ENTITY","实体类",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_DESCRIPTION =new MetaDetal("BUTTON_DESCRIPTION","描述",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_BATCH =new MetaDetal("BUTTON_BATCH","批次",StringToObjUtil.STRING,"","","");
		public  MetaDetal BUTTON_CODE =new MetaDetal("BUTTON_CODE","按钮编码",StringToObjUtil.STRING,"","","");
		public  MetaDetal HTML_CLICK_NAME =new MetaDetal("HTML_CLICK_NAME","方法名",StringToObjUtil.STRING,"","","");
		public  MetaDetal HTML_ID =new MetaDetal("HTML_ID","按钮ID属性",StringToObjUtil.STRING,"","","");
		public  MetaDetal HTML_POSITION =new MetaDetal("HTML_POSITION","按钮所在位置",StringToObjUtil.STRING,"","","");
		public  MetaDetal JSCONTENT =new MetaDetal("JSCONTENT","脚本内容",StringToObjUtil.STRING,"","","");
		public  MetaDetal ISCHECKBOX =new MetaDetal("ISCHECKBOX","是否多选",StringToObjUtil.CHAR,"","","");
		public  MetaDetal ISHIDDEN =new MetaDetal("ISHIDDEN","是否隐藏",StringToObjUtil.CHAR,"","","");
		public  MetaDetal BUTTON_CSS =new MetaDetal("BUTTON_CSS","按钮css样式",StringToObjUtil.STRING,"","","");
		public  MetaDetal REMARK =new MetaDetal("REMARK","备注",StringToObjUtil.STRING,"","","");
		public  MetaDetal SPAN_CSS =new MetaDetal("SPAN_CSS","图标css样式",StringToObjUtil.STRING,"","","");
		public  MetaDetal DR =new MetaDetal("DR","删除标识",StringToObjUtil.CHAR,"","","");
		public  MetaDetal TS =new MetaDetal("TS","时间戳",StringToObjUtil.STRING,"","","");
		public  MetaDetal VERSION =new MetaDetal("VERSION","版本号",StringToObjUtil.STRING,"","","");
		public  MetaDetal MODIFY_DATE =new MetaDetal("MODIFY_DATE","修改时间",StringToObjUtil.DATE,"","","");
		public  MetaDetal MODIFY_IP =new MetaDetal("MODIFY_IP","修改IP",StringToObjUtil.STRING,"","","");
		public  MetaDetal MODIFY_USER_ID =new MetaDetal("MODIFY_USER_ID","修改用户ID",StringToObjUtil.STRING,"","","");
		public  MetaDetal IS_PUBLIC_BUTTON =new MetaDetal("IS_PUBLIC_BUTTON","是否公共按钮",StringToObjUtil.CHAR,"","","");
		@Override
		public String findHttpUrl() {
			// TODO 自动生成的方法存根
			return PropertyFileUtil.getValue("SYSTEM_URL");
		}
	    
	}
	
	public static void main(String args[]){
	   //System.out.println(ORG.TT.getFileds());
	   //----------------------------------------
	   System.out.println(ORG.USER.ALIS_TABLE);
	   System.out.println(ORG.USER.ID.getCode());
	   System.out.println(ORG.USER.NAME.getName());
	   System.out.println(SQLUtil.findSelectSql(ORG.USER.findSqlTableUtil().appendSelFiled("aabbcc")));
	   System.out.println(SQLUtil.findSelectSql(ORG.USER.findSqlTableUtil()));
	   System.out.println(SQLUtil.findSelectSql(ORG.USER.findSqlTableUtil()));
	   System.out.println(SQLUtil.findSelectSql(ORG.USER.findSqlTableUtil()));
	}


	
}

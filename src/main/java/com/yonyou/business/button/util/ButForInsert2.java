package com.yonyou.business.button.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yonyou.business.DataSourceUtil;
import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.jdbc.RmJdbcTemplate;
import com.yonyou.util.sql.SQLUtil;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

public class ButForInsert2 extends ButtonAbs{

	@Override
	protected boolean befortOnClick(IBaseDao dcmsDAO,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		System.out.println("--------------------------------ButForInsert2----------------------------------------");
		String dataSourceCode = request.getParameter("dataSourceCode");//CD_METADATA_DETAIL
		String tabName = findTableNameByDataSourceCode(dataSourceCode);//CD_METADATA_DETAIL
		if(dataSourceCode != null & dataSourceCode.length() >0){
			ConcurrentHashMap<String, String> dataMap = DataSourceUtil.getDataSource(dataSourceCode);
			if(dataMap != null){
				 tabName = dataMap.get(DataSourceUtil.DATASOURCE_METADATA_CODE); 
			}
		}
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		try {
			
			//要修改表结构的那张表
			String TABLENAME = this.getTableName(json, dcmsDAO);
			//查询当前系统中所有的表
			SqlTableUtil stu= new SqlTableUtil("USER_TABLES", "");
			stu.appendSelFiled("TABLE_NAME");
			List<Map<String, Object>> query = dcmsDAO.query(stu);
			List<String> list2=new ArrayList<String>();
			for (Map<String, Object> map : query) {
				System.out.println(map);
				 list2.add(map.get("TABLE_NAME").toString());
			}
			//如果当前系统中没有这张表，需要建立
			if(!list2.contains(TABLENAME)){
				String sql="CREATE TABLE "+TABLENAME+"(ID VARCHAR2(19) not null,TS VARCHAR2(24) DEFAULT SYSDATE,"
						+ "DR CHAR(1) DEFAULT '0',VERSION NUMBER(20) DEFAULT 1)";
				dcmsDAO.updateBySql(sql);
			}
			
			
			//新增字段,字段类型
			String sql2="ALTER TABLE "+TABLENAME+" ADD "+json.get("FIELD_CODE")+" "+ json.get("FIELD_TYPE");
			
			
			//表中原来所有的列名
			SqlTableUtil stu2 = new SqlTableUtil(tabName, "");
			SqlWhereEntity swe2 = new SqlWhereEntity();
			swe2.putWhere("METADATA_ID", json.getString("METADATA_ID"), WhereEnum.EQUAL_STRING).putWhere("DR", "0", WhereEnum.EQUAL_STRING);
			stu2.appendSelFiled("FIELD_CODE").appendSqlWhere(swe2);
			List<Map<String, Object>> fieldCodes = dcmsDAO.query(stu2);
			List<String> list=new ArrayList<String>();
			for (Map<String, Object> map : fieldCodes) {
				 list.add(map.get("FIELD_CODE").toString());
			}
			
			if(list.contains(json.get("FIELD_CODE"))){
				String returnMessage = "亲，表中已有该字段，请重新输入字段";
				this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
				return null;
			}
			
			//新增字段长度
			if(!json.get("FIELD_TYPE").equals("DATE"))	{
				sql2 += "("+json.get("FIELD_LENGTH")+")";
			}	
			
			String sql=null;
			String sql3=null;
			Map<String, Object> map = this.tableIsNull(json, dcmsDAO);
			//表为空时判断null和主键
			if(map.isEmpty()){
					if(json.get("NULL_FLAG").equals("1")){
						sql2 +=  " NULL ";
					}else{
						sql2 += " NOT NULL ";
					}
					
					//表中没有主键
					if(this.getKeyFlag(json, dcmsDAO)==false){
							if(json.get("KEY_FLAG").equals("1")){//设置主键
									if(json.get("NULL_FLAG").equals("1")){
										if(!json.get("FIELD_TYPE").equals("DATE"))	{
											 sql2 = "ALTER TABLE "+TABLENAME+" ADD "+json.get("FIELD_CODE")+" "+json.get("FIELD_TYPE")+"("+json.get("FIELD_LENGTH")+")"+" NOT NULL ";
										}else{
											sql2 = "ALTER TABLE "+TABLENAME+" ADD "+json.get("FIELD_CODE")+" "+json.get("FIELD_TYPE")+" NOT NULL ";
										}	
										//把页面显示为not null json中 
										sql3="UPDATE " +tabName+" SET NULL_FLAG=0 WHERE METADATA_ID='"+ json.getString("METADATA_ID")+"' AND FIELD_CODE='"+json.get("FIELD_CODE")+"'";
									}else{
											 sql2 = "ALTER TABLE "+TABLENAME+" ADD "+json.get("FIELD_CODE")+" "+json.get("FIELD_TYPE")+"("+json.get("FIELD_LENGTH")+")";
										 }
									dcmsDAO.updateBySql(sql2);
									//插入元数据明细表
									dcmsDAO.insertByTransfrom(tabName, json);
									if(json.get("KEY_FLAG").equals("1")){
										 if(json.get("NULL_FLAG").equals("1")){
											  dcmsDAO.updateBySql(sql3);
										  }
									}
									sql="ALTER TABLE "+ TABLENAME +" ADD CONSTRAINT PK_"+TABLENAME+" PRIMARY KEY("+json.get("FIELD_CODE")+")"; 
									dcmsDAO.updateBySql(sql); 
									dcmsDAO.updateBySql("COMMENT ON COLUMN"+" "+TABLENAME+"."+json.get("FIELD_CODE")+" "+"is"+" "+"'"+json.get("FIELD_NAME")+"'");
							}else{
								dcmsDAO.updateBySql(sql2);
								//插入元数据明细表
								dcmsDAO.insertByTransfrom(tabName, json);
								dcmsDAO.updateBySql("COMMENT ON COLUMN"+" "+TABLENAME+"."+json.get("FIELD_CODE")+" "+"is"+" "+"'"+json.get("FIELD_NAME")+"'");
							}
							
					}else{
					//表中已有主键
							if(json.get("KEY_FLAG").equals("1")){//又请求设置主键 返回
								String returnMessage = "亲，表中已有主键!(注：一个表中只能有一个主键)";
								this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
								return null;
							}else{//无请求主键设置可以插入
								 dcmsDAO.updateBySql(sql2);
								 //插入元数据明细表
								 dcmsDAO.insertByTransfrom(tabName, json);
								 dcmsDAO.updateBySql("COMMENT ON COLUMN"+" "+TABLENAME+"."+json.get("FIELD_CODE")+" "+"is"+" "+"'"+json.get("FIELD_NAME")+"'");
							}
							
					}
			}else{//表不为空 可以添加字段但都必须为null
					
				   //请求非空标识 返回
					if(json.get("NULL_FLAG").equals("0")||json.get("KEY_FLAG").equals("1")){
						String returnMessage = "亲，表中已有数据，添加字段都必须为null和不为主键";
						this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
						return null;
					}else{
						sql2 +=  " NULL ";
						dcmsDAO.updateBySql(sql2);
						//插入元数据明细表
						dcmsDAO.insertByTransfrom(tabName, json);
						dcmsDAO.updateBySql("COMMENT ON COLUMN"+" "+TABLENAME+"."+json.get("FIELD_CODE")+" "+"is"+" "+"'"+json.get("FIELD_NAME")+"'");
					}
					
				}
			this.setDefault(json, dcmsDAO);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		String jsonMessage = "{\"message\":\"保存成功\"}";
		this.ajaxWrite(jsonMessage, request, response);
		return null;
	}
	
	
	@Override
	protected void afterOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO 自动生成的方法存根
		
	}
	
	
	
	/**
	 * 获取欲修改的表名
	 * @param json
	 * @param dcmsDAO
	 * @return
	 */
	public String getTableName(JSONObject json,IBaseDao dcmsDAO){
		//根据METADATA_ID 到CD_METADATA表得到data_code(student表)
		SqlTableUtil stu = new SqlTableUtil("CD_METADATA", "");
		SqlWhereEntity swe = new SqlWhereEntity();
		swe.putWhere("DR", "0", WhereEnum.EQUAL_STRING).putWhere("ID", json.getString("METADATA_ID"), WhereEnum.EQUAL_STRING);
		stu.appendSelFiled("*").appendSqlWhere(swe);
		Map<String,Object> map =  dcmsDAO.find(stu);
		String TABLENAME = (String)map.get("DATA_CODE");//获得表名STUDENT
		return TABLENAME;
	}
	
	/**
	 * 查看要修改表结构的那张表是否为空
	 * @param json
	 * @param dcmsDAO
	 * @return
	 */
	public Map<String,Object> tableIsNull(JSONObject json,IBaseDao dcmsDAO){
		String TABLENAME = this.getTableName(json, dcmsDAO);
		SqlTableUtil stu = new SqlTableUtil(TABLENAME, "");
		stu.appendSelFiled("*");
		Map<String,Object> map3 =  dcmsDAO.find(stu);
		return map3;
	}
	
	/**
	 * 判断表中原来是否有主键
	 * @param json
	 * @param dcmsDAO
	 * @return
	 */
	public boolean getKeyFlag(JSONObject json,IBaseDao dcmsDAO){
		String TABLENAME = this.getTableName(json, dcmsDAO);
		SqlTableUtil stu4 = new SqlTableUtil("user_cons_columns", "");
		SqlWhereEntity swe3 = new SqlWhereEntity();
		swe3.putWhere("TABLE_NAME", TABLENAME, WhereEnum.EQUAL_STRING).putWhere("CONSTRAINT_NAME", "PK_"+TABLENAME, WhereEnum.EQUAL_STRING);
		stu4.appendSelFiled("COUNT(*)").appendSqlWhere(swe3);
		Map<String, Object> map = dcmsDAO.find(stu4);
		if(Integer.valueOf(map.get("COUNT(*)").toString()) <= 0){
			return false;////表中无主键约束
		}else{
			return true;
		}
		
	}
	
	
	
	
	/**
	 * 插入时如果null标识和主键标识用户没有选择
	 * 即("请选择"),设为默认null(即0)和非主键(即0)
	 * @param json
	 * @param dcmsDAO
	 */
	
	
	public void setDefault(JSONObject json,IBaseDao dcmsDAO){
		System.out.println(json);
		String sql=null;
		if(json.get("NULL_FLAG").equals("")){
			sql="UPDATE  CD_METADATA_DETAIL SET NULL_FLAG=1 WHERE  METADATA_ID="+"'"+json.getString("METADATA_ID")+"'"+"  AND FIELD_CODE="+"'"+json.getString("FIELD_CODE")+"'";
			dcmsDAO.updateBySql(sql);
		}
		
		if(json.get("KEY_FLAG").equals("")){
			sql="UPDATE  CD_METADATA_DETAIL SET KEY_FLAG=0 WHERE  METADATA_ID="+"'"+json.getString("METADATA_ID")+"'"+"  AND FIELD_CODE="+"'"+json.getString("FIELD_CODE")+"'";
			dcmsDAO.updateBySql(sql);
		}
		
		
	}
	
	
	

}

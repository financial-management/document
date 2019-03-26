package com.yonyou.business.button.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.yonyou.business.button.ButtonAbs;
import com.yonyou.util.BussnissException;
import com.yonyou.util.jdbc.IBaseDao;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;
import com.yonyou.util.sql.SQLUtil.WhereEnum;

public class ButForUpdate2 extends ButtonAbs {

	@Override
	protected Object execute(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BussnissException {
		String dataSourceCode =request.getParameter("dataSourceCode");
		String tabName = findTableNameByDataSourceCode(dataSourceCode);
		JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
	
		try {
			// 获得原来的列
			Map<String, Object> fieldCodes = this.getFieldCode(tabName,
					dcmsDAO, json);
			String TABLENAME2 = this.getTableName(json, dcmsDAO);
			//修改列类型 [ALTER TABLE STUDENT MODIFY  HOBBY   VARCHAR(20) NULL PRIMARY KEY]
			String sql="ALTER TABLE "+TABLENAME2+" MODIFY "+fieldCodes.get("FIELD_CODE");
			// 表为空时修改
			if (this.tableIsNull(json, dcmsDAO) == true) {
				
				if(!fieldCodes.get("FIELD_TYPE").equals(json.get("FIELD_TYPE"))){
					 sql+=" "+json.get("FIELD_TYPE");
				}else{
					sql+=" "+fieldCodes.get("FIELD_TYPE");
				}
				
				
				//修改类型长度
				if(!fieldCodes.get("FIELD_LENGTH").equals(json.get("FIELD_LENGTH"))){
					 sql+="("+json.get("FIELD_LENGTH")+")";
				}else{
					sql+="("+fieldCodes.get("FIELD_LENGTH")+")";
				}

				//如果只是类型和长度修改 
				if(!fieldCodes.get("FIELD_TYPE").equals(json.get("FIELD_TYPE"))||!fieldCodes.get("FIELD_LENGTH").equals(json.get("FIELD_LENGTH"))){
					dcmsDAO.updateBySql(sql);
				}
				
				String id = json.getString("METADATA_ID");
				
				//修改主键和空标识
				String sql2=null;
				String sql3=null;
				String sql5=null;
				System.out.println(fieldCodes.get("KEY_FLAG"));
				System.out.println(json.get("KEY_FLAG"));
				
				//修改主键及非空
				//主键更改时 且主键一定不为null,一个表中只能有一个主键
				if(!fieldCodes.get("KEY_FLAG").equals(json.get("KEY_FLAG"))){
								//表中无主键约束
								if(this.getKeyFlag(json, dcmsDAO)==false){
											if(json.get("KEY_FLAG").equals("1")){
													 //向表中添加主键约束
													 sql2="ALTER TABLE "+ TABLENAME2 +" ADD CONSTRAINT PK_"+TABLENAME2+" PRIMARY KEY("+fieldCodes.get("FIELD_CODE")+")"; 
													 sql +=" NOT NULL ";
													//把页面显示为not null json中 
													json.put("NULL_FALG", "0");
													 dcmsDAO.updateBySql(sql2);
													if(json.get("NULL_FLAG").equals(1)){
														dcmsDAO.updateBySql(sql);
													}
												
											 }
								}else{
								//表中已有主键约束
											//表中已有的主键就不是这次要求更改的字段
													if(!this.getKeyFieldCode(json, dcmsDAO).equals(fieldCodes.get("FIELD_CODE")) ){
																	 	if(json.get("KEY_FLAG").equals("1")){//要求设置主键
																	 		//删除表中已有的主键约束
																	 		sql3="ALTER TABLE "+TABLENAME2+" DROP CONSTRAINT PK_"+TABLENAME2;
																	 		//更新元数据明细表原来主键状态
																	 		sql5="UPDATE " +tabName+" SET KEY_FLAG=0 WHERE METADATA_ID='"+id+"' AND FIELD_CODE='"+this.getKeyFieldCode(json, dcmsDAO)+"'";
																	 		
																	 		//向表中添加主键约束
																	 		sql2="ALTER TABLE "+ TABLENAME2 +" ADD CONSTRAINT PK_"+TABLENAME2+" PRIMARY KEY("+fieldCodes.get("FIELD_CODE")+")"; 
																	 		sql +=" NOT NULL ";
																	 		//更新元数据明细表空标识状态
																	 		json.put("NULL_FLAG", "0");
																	 		json.put("KEY_FLAG", "1");
																	 		dcmsDAO.updateBySql(sql3);
																	 		dcmsDAO.updateBySql(sql5);
																	 		if(json.get("NULL_FLAG").equals(1)){
																				dcmsDAO.updateBySql(sql);
																			}
																			dcmsDAO.updateBySql(sql2);
																	 	}
													}else{
													//表中已有的主键就是这次要求更改的字段
															if(json.get("KEY_FLAG").equals("0")){//要求撤销主键
																//删除表中已有的主键约束
														 		sql3="ALTER TABLE "+TABLENAME2+" DROP CONSTRAINT PK_"+TABLENAME2;
														 		json.put("KEY_FLAG", "0");
														 		dcmsDAO.updateBySql(sql3);
															}
													}
									}	
										
								
						}else{
						//主键不更改时 主键不修改，修改的都不是主键
						//非空标识改变
							System.out.println("***"+fieldCodes.get("NULL_FLAG"));
							System.out.println("***"+json.get("NULL_FLAG"));
							if(!fieldCodes.get("NULL_FLAG").toString().equals(json.get("NULL_FLAG")))
									if(json.get("NULL_FLAG").equals("1")){
										 sql +="  NULL ";
										 dcmsDAO.updateBySql(sql);
										 dcmsDAO.updateByTransfrom(tabName, json, new SqlWhereEntity());
									}else if(json.get("NULL_FLAG").equals("0")){
										sql +=" NOT NULL ";
										 dcmsDAO.updateBySql(sql);
									}
							}
				
				// 修改数据编码和注释
				this.usualUpdate(json, dcmsDAO, tabName);
				
				 dcmsDAO.updateByTransfrom(tabName, json, new SqlWhereEntity());
			} else {
			// 表非空时修改
				// 表非空时主键和非空标识不能改变
				if (!json.get("NULL_FLAG").equals(fieldCodes.get("NULL_FLAG").toString())|| !json.get("KEY_FLAG").equals(fieldCodes.get("KEY_FLAG").toString())) {
					String returnMessage = "亲，表中已有数据，不可修改是否为空和是否是主键哦";
					this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
			  		return null;
				}

				// 修改数据类型和数据长度
				// 要修改的此列为空
				if (this.chooseISNull(json, dcmsDAO, tabName)==true) {
					// 类型变动
					if (!fieldCodes.get("FIELD_TYPE").equals(json.get("FIELD_TYPE"))) {
						sql += " " + json.get("FIELD_TYPE");
					} else {
						sql += " " + fieldCodes.get("FIELD_TYPE");
					}
					//修改类型长度
					if(!fieldCodes.get("FIELD_LENGTH").equals(json.get("FIELD_LENGTH"))){
						 sql+="("+json.get("FIELD_LENGTH")+")";
					}else{
						sql+="("+fieldCodes.get("FIELD_LENGTH")+")";
					}
				} else {
					// 要修改的此列不为空//数据类型不能变
					// 类型变动
					if (!fieldCodes.get("FIELD_TYPE").equals(json.get("FIELD_TYPE"))) {
						String returnMessage = "要修改数据类型，则要修改的列必须为空";
						this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
				  		return null;
					} else {
						sql += " " + fieldCodes.get("FIELD_TYPE");
					}

					// 长度变动
					if (!fieldCodes.get("FIELD_LENGTH").equals(json.get("FIELD_LENGTH"))) {
						// 原来的类型不为number, 可以修改，加上现在的长度
						if (!fieldCodes.get("FIELD_TYPE").equals("NUMBER")) {
							sql += "(" + json.get("FIELD_LENGTH") + ")";
						} else {
							// 原来的类型是number
							// 原来的长度大于现在的长度
							if (Integer.valueOf(fieldCodes.get("FIELD_LENGTH").toString()) > Integer.valueOf(json.get("FIELD_LENGTH").toString())) {
								String returnMessage = "NUMBER类型 修改的列不为空，不能减少精度或标度";
								this.ajaxWrite( "{\"message\":\""+returnMessage+"\"}", request, response);
						  		return null;
							} else{
								sql += "(" + json.get("FIELD_LENGTH") + ")";
							}
						}
					} else {
						// //长度不变动
						sql += "(" + json.get("FIELD_LENGTH") + ")";
					}

				}
				// 如果只是类型和长度修改
				if (!fieldCodes.get("FIELD_TYPE").equals(json.get("FIELD_TYPE"))
						|| !fieldCodes.get("FIELD_LENGTH").equals(
								json.get("FIELD_LENGTH"))) {
					dcmsDAO.updateBySql(sql);
				}

				// 修改类型和注释
				this.usualUpdate(json, dcmsDAO, tabName);
				 dcmsDAO.updateByTransfrom(tabName, json, new SqlWhereEntity());
			}
		
			
			
		} catch (BussnissException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		String jsonMessage = "{\"message\":\"修改成功\"}";
		this.ajaxWrite(jsonMessage, request, response);
		return null;
	}

	@Override
	protected boolean befortOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO 自动生成的方法存根
		return false;
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
		String id = json.getString("METADATA_ID");
		SqlTableUtil stu = new SqlTableUtil("CD_METADATA", "");
		SqlWhereEntity swe = new SqlWhereEntity();
		swe.putWhere("DR", "0", WhereEnum.EQUAL_STRING).putWhere("ID", id, WhereEnum.EQUAL_STRING);
		stu.appendSelFiled("*").appendSqlWhere(swe);
		Map<String,Object> map =  dcmsDAO.find(stu);
		String TABLENAME2 = (String)map.get("DATA_CODE");//获得表名STUDENT
		return TABLENAME2;
	}
	
	
	
	/**
	 * 查询表中原来的所有列名
	 * @param tabName 元数据明细表
	 * @param dcmsDAO
	 * @param json
	 * @return
	 */
	public Map<String, Object> getFieldCode(String tabName,IBaseDao dcmsDAO,JSONObject json){
		//select * from CD_METADATA_DETAIL WHERE METADATA_ID='1000118'AND ID='1000100100000000746'查询原来的列名
		String id = json.getString("METADATA_ID");
		SqlTableUtil stu2 = new SqlTableUtil(tabName, "");
		SqlWhereEntity swe2 = new SqlWhereEntity();
		swe2.putWhere("METADATA_ID", id, WhereEnum.EQUAL_STRING).putWhere("ID", (String) json.get("ID"), WhereEnum.EQUAL_STRING).putWhere("DR", "0", WhereEnum.EQUAL_STRING);
		stu2.appendSelFiled("*").appendSqlWhere(swe2);
		System.out.println(stu2);
		Map<String, Object> find = dcmsDAO.find(stu2);
		return find;
	}
	
	
	
	/**
	 * 判断表中原来是否有主键
	 * @param json
	 * @param dcmsDAO
	 * @return
	 */
	public boolean getKeyFlag(JSONObject json,IBaseDao dcmsDAO){
		String TABLENAME2 = this.getTableName(json, dcmsDAO);
		SqlTableUtil stu4 = new SqlTableUtil("user_cons_columns", "");
		SqlWhereEntity swe3 = new SqlWhereEntity();
		swe3.putWhere("TABLE_NAME", TABLENAME2, WhereEnum.EQUAL_STRING).putWhere("CONSTRAINT_NAME", "PK_"+TABLENAME2, WhereEnum.EQUAL_STRING);
		stu4.appendSelFiled("COUNT(*)").appendSqlWhere(swe3);
		Map<String, Object> map = dcmsDAO.find(stu4);
		if(Integer.valueOf(map.get("COUNT(*)").toString()) <= 0){
			return false;////表中无主键约束
		}else{
			return true;
		}
		
	}
	
	
	
	/**
	 * 获取主键字段名
	 * @param json
	 * @param dcmsDAO
	 */
	public String getKeyFieldCode(JSONObject json,IBaseDao dcmsDAO){
		String TABLENAME2 = this.getTableName(json, dcmsDAO);
		SqlTableUtil stu5 = new SqlTableUtil("user_cons_columns", "");
		SqlWhereEntity swe5 = new SqlWhereEntity();
		swe5.putWhere("TABLE_NAME", TABLENAME2, WhereEnum.EQUAL_STRING).putWhere("CONSTRAINT_NAME", "PK_"+TABLENAME2, WhereEnum.EQUAL_STRING);
		stu5.appendSelFiled("COLUMN_NAME").appendSqlWhere(swe5);
		Map<String, Object> map2 = dcmsDAO.find(stu5);
		String KeyFieldCode = map2.get("COLUMN_NAME").toString();
		return KeyFieldCode;
	}
	
	
	/**
	 * 查看要修改表结构的那张表是否为空
	 * @param json
	 * @param dcmsDAO
	 * @return
	 */
	public boolean tableIsNull(JSONObject json,IBaseDao dcmsDAO){
		String TABLENAME2 = this.getTableName(json, dcmsDAO);
		SqlTableUtil stu = new SqlTableUtil(TABLENAME2, "");		
		stu.appendSelFiled("*");
		Map<String,Object> map3 =  dcmsDAO.find(stu);
		if(map3.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 判断选中修改的那一列在表中是否为空
	 * @param json
	 * @param dcmsDAO
	 */
	public boolean chooseISNull(JSONObject json,IBaseDao dcmsDAO,String tabName){
		
		String TABLENAME2 = this.getTableName(json, dcmsDAO);
		Map<String, Object> fieldCodes = this.getFieldCode(tabName, dcmsDAO, json);
		String object = (String) fieldCodes.get("FIELD_CODE");
		
		SqlTableUtil stu1 = new SqlTableUtil(TABLENAME2, "");
		stu1.appendSelFiled(" COUNT("+object+")"+ " AS LENGTH " );
		Map<String, Object> map3  = dcmsDAO.find(stu1);
		System.out.println(map3);
		System.out.println(Integer.valueOf(map3.get("LENGTH").toString()));
		 if(Integer.valueOf(map3.get("LENGTH").toString())<0||Integer.valueOf(map3.get("LENGTH").toString())==0){
			 return true;
		 }else{
			 return false;
		 }
		 
	}
	
	
	
	/**
	 * 普通的修改类型和注释
	 * @param json
	 * @param dcmsDAO
	 * @throws BussnissException 
	 */
	public void usualUpdate(JSONObject json,IBaseDao dcmsDAO,String tabName) throws BussnissException{
		
		String TABLENAME2 = this.getTableName(json, dcmsDAO);
		Map<String, Object> fieldCodes = this.getFieldCode(tabName, dcmsDAO, json);
		String fieldCode = (String)fieldCodes.get("FIELD_CODE");
		
		//修改列名 [ALTER TABLE STUDENT RENAME COLUMN HOBBY TO HOBBY1;]
		String sql1="ALTER TABLE "+TABLENAME2+" RENAME COLUMN "+fieldCode+" TO ";
		String sql2="COMMENT ON COLUMN "+TABLENAME2+"."+json.getString("FIELD_CODE")+" is "+"'";
		if(!fieldCodes.get("FIELD_CODE").equals(json.get("FIELD_CODE"))){
				 sql1+=json.get("FIELD_CODE");
				 dcmsDAO.updateBySql(sql1);
		 }
			
		//修改注释 [comment on column STUDENT.字段名 is 字段注释;]
		if(!fieldCodes.get("FIELD_NAME").equals(json.get("FIELD_NAME"))){
				sql2+=json.get("FIELD_NAME")+"'";
				dcmsDAO.updateBySql(sql2);
		}
	}
	
	
	
	
}

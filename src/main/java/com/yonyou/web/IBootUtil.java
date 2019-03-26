package com.yonyou.web;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yonyou.util.sql.SQLUtil;

/**
 * 
* @ClassName: IBootUtil 
* @Description: 列组装工具类
* @author 德发
* @date 2016年12月27日 
*
 */
public class IBootUtil {
	
	/**
	 * 
	* @Title: findColJosnStr 
	* @Description: 组装展示列-均可见
	* @param cols
	* @return 展示列
	 */
	public static String findColJosnStr(String [] cols){
		JSONArray obj =new JSONArray();
		obj.add(findCheck("checkbox"));
		for(String col:cols){
			obj.add(findJsonObj(col, col, true));
		}
		return obj.toString();
	}
	
	/**
	 * 
	* @Title: findShowFiledNameMap 
	* @Description: 翻译展示字段
	* @param showFiledMap
	* @param fieldCodeToNameMap
	* @return 翻译后展示字段信息
	 */
	public static Map<String,String> findShowFiledNameMap(Map<String,String> showFiledMap,Map<String,String> fieldCodeToNameMap){
		Map<String,String> showFiledNameMap =new LinkedHashMap<String,String>();
		showFiledNameMap.put("RMRN", "序");
		for(String showFiled:showFiledMap.keySet()){
			String filedName="";
			//showFiled带有TNAME()函数为需由数据字典翻译字段
			String showKey =showFiled.toUpperCase();
			if (showFiled.startsWith("TNAME") && showFiled.endsWith(")")) {
				//函数有无参数影响取字段名逻辑
				int endIndex = showFiled.length() - 1;
				if(showFiled.contains(",")){
					endIndex = showFiled.split(",")[0].length();
				}
				showFiled = showFiled.substring("TNAME(".length(), endIndex);
			}
//			if(showFiled.startsWith(TranslateUtil.TRANSLATE_MARK)){
//				showFiled=showFiled.substring(2, showFiled.length());
//			}
			
			if(fieldCodeToNameMap!=null){
				if(fieldCodeToNameMap.containsKey(showFiled.toLowerCase())){
					filedName=fieldCodeToNameMap.get(showFiled.toLowerCase());
				}else if(fieldCodeToNameMap.containsKey(showFiled.toUpperCase())){
					filedName=fieldCodeToNameMap.get(showFiled.toUpperCase());
				}
			}else{
				filedName=showKey.toUpperCase();
			}
			
			showFiledNameMap.put(showKey.toUpperCase(), filedName);
		}
		return showFiledNameMap;
	}
	
	/**
	 * 
	* @Title: findColJosnStr 
	* @Description: 组装展示列-均可见
	* @param showMap 展示字段
	* @return
	 */
	public static String findColJosnStr(Map<String,String> showMap){
		JSONArray obj =new JSONArray();
		obj.add(findCheck("checkbox"));
		for(String filed:showMap.keySet()){
			String val =showMap.get(filed);
			if(!SQLUtil.isEmpty(val)){
				obj.add(findJsonObj(filed, val, true));
			}else{
				obj.add(findJsonObj(filed, filed, true));
			}
		}
		return obj.toString();
	}
	
	/**
	 * 
	* @Title: findColJosn 
	* @Description: 组装展示列-均可见 
	* @param showMap
	* @return
	 */
	//FIXME 与上一个方法重复，为满足需要暂时分开，后面需要整体考虑后翻新一版——建议返回map，字符串和JSONArray都不好进行加工处理
	public static JSONArray findColJosn(Map<String,String> showMap){
		JSONArray obj =new JSONArray();
		obj.add(findCheck("checkbox"));
		for(String filed:showMap.keySet()){
			String val =showMap.get(filed);
			if(!SQLUtil.isEmpty(val)){
				obj.add(findJsonObj(filed, val, true));
			}else{
				obj.add(findJsonObj(filed, filed, true));
			}
		}
		return obj;
	}
	
	/**
	 * 
	* @Title: findColJosnStr 
	* @Description: 组装展示列-均可见
	* @param rows
	* @param row_names
	* @return
	* @throws UnsupportedEncodingException
	 */
	public static String findColJosnStr(String rows,String row_names) throws UnsupportedEncodingException{
		JSONArray obj =new JSONArray();
		obj.add(findCheck("checkbox"));
		if(rows!=null&&rows.length()>0){
			if(rows.indexOf(",")!=-1){
				int row_name_length=0;
				int row_index=0;
				String [] row_name_array =new String[]{row_names};
				if(row_names!=null&&row_names.length()>0){
					if(row_names.indexOf(",")!=-1){
						row_name_array=row_names.split(",");
						row_name_length=row_name_array.length;
					}
				}
				for(String row:rows.split(",")){
					if(row_index<row_name_length||row_index==0){
						obj.add(findJsonObj(row, row_name_array[row_index], true));
					}else{
						obj.add(findJsonObj(row, row, true));
					}
					row_index++;
				}
			}else{
				obj.add(findJsonObj(rows, rows, true));
			}
		}
		System.out.println("##JsonArray"+obj.toString());
		return obj.toString();
	}
	
	/**
	 * 
	* @Title: findQuerySql 
	* @Description: 拼装查询SQL
	* @param table
	* @param cols
	* @param limit
	* @param offset
	* @param whereStr
	* @return
	 */
	public static String findQuerySql(String table,String cols,int limit,int offset,String whereStr){
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT R,").append(cols).append(" FROM")
		   .append(" ( SELECT ROWNUM AS R , ").append(cols).append(" FROM ").append(table).append(" ").append(whereStr).append(" )");
		if(limit>0){
			sql.append(" WHERE R >=").append(offset+1).append(" AND R<=").append((offset+limit));
		}
		
		System.out.println(sql);
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: findJsonObj 
	* @Description: 拼装列时加入默认参数
	* @param field
	* @param title
	* @param isVisible
	* @return
	 */
	private static JSONObject findJsonObj(String field,String title,boolean isVisible){
		JSONObject obj =new JSONObject();
		obj.put("field", field);
		obj.put("title", title);
		obj.put("visible", isVisible);
		obj.put("align", "center");
		obj.put("sortable", true);
		return obj;
	}
	
	/**
	 * 
	* @Title: findJsonObj 
	* @Description: 拼装列时加入默认参数-打开行内编辑
	* @param field
	* @param title
	* @param isVisible
	* @return
	 */
	private static JSONObject findJsonObj(String field,String title,boolean isVisible,boolean editInLine){
		JSONObject obj =new JSONObject();
		obj.put("field", field);
		obj.put("title", title);
		obj.put("visible", isVisible);
		obj.put("align", "center");
		obj.put("sortable", true);
		if(!"RMRN".equals(field)){
			obj.put("editable", editInLine);
		}
		return obj;
	}
	
	/**
	 * 
	* @Title: findCheck 
	* @Description: 加上选择框
	* @param check
	* @return
	 */
	private static JSONObject findCheck(String check){
		JSONObject obj =new JSONObject();
		obj.put(check, true);
		return obj;
	}
}

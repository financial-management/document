package com.yonyou.business;

import java.util.Map;
import java.util.Map.Entry;


/**
 * 
* @ClassName: RmHtmlHelper 
* @Description: html代码拼装 
* @author 博超
* @date 2016年12月26日 
*
 */
public class RmHtmlHelper {  
    
	/**
	 * 
	* @Title: getSelectField 
	* @Description: 创建一个select，接受map
	* @param idValue 字段名-HTML的id值 
	* @param mOptionValue 要显示的option列表
	* @return select的HTML代码
	 */
	public static String getSelectField(String idValue,Map<String, String> mOptionValue) {
		StringBuffer returnString = new StringBuffer();
		try {
			returnString.append("<select class='form-control' id='" + idValue + "'>");
			returnString.append("<option value=''>--请选择--</option>");
			if (mOptionValue != null && mOptionValue.size() > 0) {
				for (Entry<String, String> entry : mOptionValue.entrySet()) {
					String tempKey = entry.getKey();
					String tempValue = entry.getValue();
					returnString.append("<option value='" + tempKey + "' ");
					returnString.append(">" + tempValue + "</option>");
				}
			}
			returnString.append("</select> ");
			return returnString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
    
	/**
	 * 
	* @Title: getRadioField 
	* @Description: 功能: 创建一个radio列表
	* @param strName 名称
	* @param nDisplaySize 显示的长度，超过则截取
	* @param aOptionValue 要显示的option列表
	* @param strCompare 要比较的字符串
	* @param strProperty 额外的属性,例如"onchange='change()'"
	* @return radio的HTML代码
	 */
    public static String getRadioField(String strName, int nDisplaySize, String[][] aOptionValue, String strCompare, String strProperty) {
        StringBuffer returnStr = new StringBuffer();
        try {
            if (strProperty == null)
                strProperty = "";
            if (strCompare == null)
                strCompare = "";

            if (aOptionValue != null) {
                for (int i = 0; i < aOptionValue.length; i++) {
                    String tempKey = aOptionValue[i][0];
                    String tempValue = aOptionValue[i][1];
                    if (!"".equals(returnStr.toString())) {
                        returnStr.append("&nbsp;&nbsp;");
                    }
                    /* 截去超长的字符 */
                    if (tempValue != null && tempValue.length() > nDisplaySize && nDisplaySize >= 0) {
                        tempValue = tempValue.substring(0, nDisplaySize);
                    }
                    returnStr.append("<input type='radio' name='" + strName + "' value='" + tempKey + "' ");
                    if (tempKey.equals(strCompare)) {
                        returnStr.append(" checked ");
                    }
                    returnStr.append(strProperty);
                    returnStr.append("/>");
                    returnStr.append(tempValue);
                }
            }
            return returnStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    } 
}

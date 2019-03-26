package com.yonyou.business.button;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.util.HttpRequestUtil;
import com.yonyou.util.jdbc.IBaseDao;

/**生成按钮脚本
 * @author XIANGJIAN
 * @date 创建时间：2017年1月13日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/buttonBuild")
public class ButtonBuildUtil {
	
	@Autowired
	protected IBaseDao dcmsDAO;
	
	
	/**获取按钮的html代码，并反馈到视图
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=build")
	public void button(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String jsonMessage = "{\"message\":\"\"}" ;
		//JSONObject json = JSONObject.fromObject(request.getParameter("jsonData"));
		String dataSourceCode =request.getParameter("dataSourceCode");
		//FIXME 现按钮为由js获取
//		JSONObject jsonObject=HttpRequestUtil.httpRespJSON(null, dataSourceCode);
//		jsonMessage =jsonObject.toString();
		//jsonMessage = "{\"message\":\""+this.getButtonsHtml(jsonArray)+"\"}";
		System.out.println(jsonMessage);
		out.print(jsonMessage);
		out.flush();
		out.close();
	}
		

	private JSONArray jsonArray(){
		String name1 = "复核" ;
		String name2 = "撤销复核" ;
		String name3 = "EXCEL导入";
		String code1 = getBaseCode("check");
		String code2 = getBaseCode("checkBack");
		String code3 = getBaseCode("import");
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
			json.put("buttonName", name1);
			json.put("buttonToken", code1);
			json.put("buttonClickName", code1);
			json.put("buttonId", code1);
			json.put("isCheckBox", "yes");
			json.put("buttonHiddenType", "show");
			json.put("buttonClass", "btn btn-default");
			json.put("spanClass", "glyphicon");
			jsonArray.add(json);
		JSONObject json1 = new JSONObject();
			json1.put("buttonName", name2);
			json1.put("buttonToken", code2);
			json1.put("buttonClickName", code2);
			json1.put("buttonId", code2);
			json1.put("isCheckBox", "yes");		//多选："yes";	  单选："no";   不选：""
			json1.put("buttonHiddenType", "show");		// show    or   hidden
			json1.put("buttonClass", "btn btn-default");
			json1.put("spanClass", "");
			jsonArray.add(json1);
		JSONObject json2 = new JSONObject();
			json2.put("buttonName", name3);
			json2.put("buttonToken", code3);
			json2.put("buttonClickName", code3);
			json2.put("buttonId", code3);
			json2.put("isCheckBox", "");		//多选："yes";	  单选："no";   不选：""
			json2.put("buttonHiddenType", "show");		// show    or   hidden
			json2.put("buttonClass", "btn btn-default");
			json2.put("spanClass", "");
			jsonArray.add(json2);
		return jsonArray;
	}

	
	/**获取多个按钮的html代码
	 * @param jsonArray	json数组
	 * @return html代码
	 */
	protected String getButtonsHtml(JSONArray jsonArray) {
		StringBuffer html = new StringBuffer() ;
		for(int i = 0 ;i < jsonArray.size() ; i++){
			JSONObject json = (JSONObject) jsonArray.get(i);
			html.append(getButtonHtml(json));
		}
		return html.toString();	
	}
	
	
	/** 获取某个按钮的html代码
	 * @param json json对象
	 * @return html代码
	 */
	protected String getButtonHtml(JSONObject json) {
		StringBuffer sb = new StringBuffer();
		sb.append("<button type='button'")
			.append(" buttonToken='"+getButtonToken(json)+"'")
			.append(" onclick='"+getButtonClickName(json)+"'")
			.append(" name='"+getButtonName(json)+"'")
			.append(" id='"+getButtonId(json)+"'")
			.append(" isCheckBox='"+getButtonCheckBox(json)+"'")
			.append(" hiddenType='"+getButtonHiddenType(json)+"'")
			.append(" class='"+getButtonClass(json)+"'>")
				.append("<span class='").append(getSpanClass(json)).append("' aria-hidden='true'></span>")
			.append(getButtonName(json))
		.append("</button>\\r\\n");
		return sb.toString();	
	}
	
	/** 获取按钮名称属性
	 * @param json json对象
	 * @return 按钮名称属性
	 */
	protected String getButtonName(JSONObject json) {
		if(isEmptyString(json.getString("buttonName"))){
			return json.getString("buttonName");
		}
		return "";
	}
	
	/** 获取按钮唯一标识属性
	 * @param json json对象
	 * @return 按钮唯一标识属性
	 */
	protected String getButtonToken(JSONObject json) {
		if(isEmptyString(json.getString("buttonToken"))){
			return setButtonToken(json.getString("buttonToken"));
		}
		return "";
	}
	
	
	/**按钮是否支持多选
	 * @param json json对象
	 * @return 支持多选返回yes,单选返回no , 不选返回"" ,默认
	 */
	protected String getButtonCheckBox(JSONObject json) {
		if(json.getString("isCheckBox") != null){
			return json.getString("isCheckBox");
		}
		return "no";
	}
	
	/** 获取按钮点击事件名
	 * @param json json对象
	 * @return 按钮点击事件名
	 */
	protected String getButtonClickName(JSONObject json) {
		if(isEmptyString(json.getString("buttonClickName"))){
			return setButtonClickName(json.getString("buttonClickName"));
		}
		return "";
	}
	
	/** 获取按钮ID属性
	 * @param json json对象
	 * @return 按钮ID属性
	 */
	protected String getButtonId(JSONObject json) {
		if(isEmptyString(json.getString("buttonId"))){
			return setButtonId(json.getString("buttonId"));
		}
		return "";
	}
	
	/** 获取按钮class样式
	 * @param json json对象
	 * @return class样式
	 */
	protected String getButtonClass(JSONObject json) {
		if(isEmptyString(json.getString("buttonClass"))){
			return json.getString("buttonClass");
		}
		return "btn btn-default";
	}
	
	/** 获取按钮名称属性
	 * @param json json对象
	 * @return 按钮名称属性
	 */
	protected String getSpanClass(JSONObject json) {
		if(isEmptyString(json.getString("spanClass"))){
			return json.getString("spanClass");
		}
		return "glyphicon";
	}
	
	/** 获取按钮名称属性
	 * @param json json对象
	 * @return 按钮名称属性
	 */
	protected String getButtonHiddenType(JSONObject json) {
		if(isEmptyString(json.getString("buttonHiddenType"))){
			return json.getString("buttonHiddenType");
		}
		return "";
	}
	
	
	/**验证字符串是否为空或null
	 * @param str 字符串
	 * @return 不为空或NUL返回true, 反之false
	 */
	private boolean isEmptyString(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}
		return false;
	}
	
	
	/** 设置按钮ID
	 * @param str 字符串
	 * @return 大写的ID值
	 */
	private String setButtonId(String str){
		return str.toUpperCase();
	}
	
	/**设置按钮点击事件
	 * @param str 字符串
	 * @return 点击事件名
	 */
	private String setButtonClickName(String str){
		return "click_"+str+"(this)";
	}
	
	/** 设置按钮的唯一标识
	 * @param str 字符串
	 * @return 
	 */
	private String setButtonToken(String str){
		return "button_"+str;
	}
	
	private String getBaseCode(String str){
		return str.toLowerCase();
	}
	
}

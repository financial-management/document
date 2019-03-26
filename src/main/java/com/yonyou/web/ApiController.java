package com.yonyou.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.business.api.ApiBussAbs;
import com.yonyou.business.api.ApiUtil;
import com.yonyou.util.PropertyFileUtil;
import com.yonyou.util.wsystem.util.SoaFlowEncapsulation;

@RestController
@RequestMapping(value = "/service")
public class ApiController extends BaseController{
	
	@RequestMapping(value="/*")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonData = JSONObject.fromObject(SoaFlowEncapsulation.decode(request.getParameter("jsonData")));// 请求json数据
		JSONObject retJsonObject = new JSONObject();// 返回json数据
		System.out.println("请求json数据:" + jsonData);
		
		if (jsonData == null || jsonData.isEmpty()) {
			//未获取到请求JSON数据
			retJsonObject = ApiUtil.ApiJsonPut("000001");
		} else if (!jsonData.containsKey("serviceCode")) {
			//请求JSON中未获取到服务编码
			retJsonObject = ApiUtil.ApiJsonPut("000002");
		} else {
			String serviceCode = jsonData.getString("serviceCode");// 服务编码

			if (serviceCode == null || serviceCode.length() != 13 || serviceCode.charAt(9) != '#') {
				//服务编码格式错误
				retJsonObject = ApiUtil.ApiJsonPut("000003");
			} else {
				// 拼出处理类名
				String implClassName = PropertyFileUtil.getValue("LocalServicePrefix") + serviceCode.replace('#', '_');
				System.out.println("处理类：" + implClassName);
				ApiBussAbs apiBussiness = null;
				try {
					apiBussiness = (ApiBussAbs) Class.forName(implClassName).newInstance();
					retJsonObject = apiBussiness.proxyDeal(jsonData);
				} catch (ClassNotFoundException e) {
					//服务编码有误,找不到相应处理类
					retJsonObject = ApiUtil.ApiJsonPut("000004");
				} catch (InstantiationException e) {
					//相应处理类无法被实例化
					retJsonObject = ApiUtil.ApiJsonPut("000005");
				} catch (IllegalAccessException e) {
					//相应处理类方法无法访问
					retJsonObject = ApiUtil.ApiJsonPut("000006");
				}
			}
		}

		System.out.println("返回json数据:" + retJsonObject);
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String returnMessage =retJsonObject.toString();
		System.out.println("加密前："+returnMessage);
		returnMessage =SoaFlowEncapsulation.encode(returnMessage);
		System.out.println("加密后："+returnMessage);
		writer.print(returnMessage);
		writer.flush();
	}
}

package com.yonyou.business.api;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

public abstract class ApiBussAbs {
	/**
	 * 返回的json数据
	 */
	protected JSONObject retJsonObject = new JSONObject();
	/**
	 * 子类要实现的类
	 * @param jsonData
	 * @return
	 */
	public abstract JSONObject doDeal(JSONObject jsonData);
	
	public JSONObject proxyDeal(JSONObject jsonData){
		//可加入如校验等其他处理
		
		//将所有JSON中所有KEY转为小写
		JsonConfig config = new JsonConfig();
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String str) {
				return str.toLowerCase();
			}
		 });
		config.setRootClass(JSONObject.class);
		JSONObject bean = (JSONObject) JSONObject.toBean(jsonData , config);
	    System.out.println("转换后JSON：" + bean);
		
	    return doDeal(bean);
	}
}

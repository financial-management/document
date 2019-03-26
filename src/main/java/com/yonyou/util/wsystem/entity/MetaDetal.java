package com.yonyou.util.wsystem.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 元数据数据类
 * @author moing_ink
 * <p>创建时间 ： 2016年12月27日
 * @version 1.0
 */
public class MetaDetal {
	
	private String code;
	private String name;
	private String type;
	private String input_type;
	private String input_formart;
	private String input_html;
	
	/**
	 * 常量标示
	 */
	public final static String CODE="code";
	public final static String NAME="name";
	public final static String TYPE="type";
	public final static String INPUT_TYPE="input_type";
	public final static String INPUT_FORMART="input_formart";
	public final static String INPUT_HTML="input_html";
	/**
	 * 业务数据MAP
	 */
	private Map<String,String> dataMap =new HashMap<String,String>();
	/**
	 * 根据MAP初始化
	 * @param map
	 */
	public MetaDetal(Map<String,String> map){
		//追加dataMap信息
		this.appendMap(map);
		String _code =this.getValueByKey(CODE);
		String _name =this.getValueByKey(NAME);
		String _type =this.getValueByKey(TYPE);
		String _input_type =this.getValueByKey(INPUT_TYPE);
		String _input_formart =this.getValueByKey(INPUT_FORMART);
		String _input_html =this.getValueByKey(INPUT_HTML);
		this.init(_code, _name, _type, _input_type, _input_formart, _input_html);
	}
	
	/**
	 * 根据属性以及业务MAP初始化
	 * @param _code
	 * @param _name
	 * @param _type
	 * @param _input_type
	 * @param _input_formart
	 * @param _input_html
	 * @param map
	 */
	public MetaDetal(String _code,String _name,String _type,String _input_type,String _input_formart,String _input_html,Map<String,String> map){
		//追加dataMap信息
		this.appendMap(map);
		this.init(_code, _name, _type, _input_type, _input_formart, _input_html);
	}
	
	/**
	 * 根据属性初始化
	 * @param _code
	 * @param _name
	 * @param _type
	 * @param _input_type
	 * @param _input_formart
	 * @param _input_html
	 */
	public MetaDetal(String _code,String _name,String _type,String _input_type,String _input_formart,String _input_html){
		this.init(_code, _name, _type, _input_type, _input_formart, _input_html);
	}
	
	/**
	 * 添加Map信息
	 * @param map
	 */
	public void addMap(Map<String,String> map){
		//追加dataMap信息
		this.appendMap(map);
	}
	
	/**
	 * 添加MAP信息
	 * @param key
	 * @param value
	 */
    public void putKV(String key,String value){
    	//查询是否存在，如果存在抛弃
    	removeMapK(key);
		dataMap.put(key, value);
	}
    
    /**
     * 获取MAP属性
     * @param key
     * @return
     */
    public String getValueByKey(String key){
    	String value="";
    	if(dataMap.containsKey(key)){
    		value =dataMap.get(key);
    	}
    	return value;
    }
    
    public void removeMapK(String key){
    	//查询是否存在，如果存在抛弃
    	if(dataMap.containsKey(key)){
			dataMap.remove(key);
		}
    }
    
    public void removeMap(){
    	dataMap.clear();
    }
    
    /**
     * 初始化方法
     * @param _code
     * @param _name
     * @param _type
     * @param _input_type
     * @param _input_formart
     * @param _input_html
     */
	public void init(String _code,String _name,String _type,String _input_type,String _input_formart,String _input_html){
		code=_code;
		name=_name;
		type=_type;
		input_type=_input_type;
		input_formart=_input_formart;
		input_html=_input_html;
	}
	
	/**
	 * 追加MAP信息
	 * @param map
	 */
	private void appendMap(Map<String,String> map){
		if(map!=null&&map.size()>0){
			for(String key:map.keySet()){
				this.putKV(key, map.get(key));
			}
		}
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInput_type() {
		return input_type;
	}
	public void setInput_type(String input_type) {
		this.input_type = input_type;
	}
	public String getInput_formart() {
		return input_formart;
	}
	public void setInput_formart(String input_formart) {
		this.input_formart = input_formart;
	}
	public String getInput_html() {
		return input_html;
	}
	public void setInput_html(String input_html) {
		this.input_html = input_html;
	}
	
	
	
}

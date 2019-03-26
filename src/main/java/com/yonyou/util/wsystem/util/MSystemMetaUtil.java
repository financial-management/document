package com.yonyou.util.wsystem.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.yonyou.util.wsystem.entity.MetaDetal;

/**
 * 系统元数据转换工具类
 * @author moing_ink
 * <p>创建时间 ： 2016年12月27日
 * @version 1.0
 */
public class MSystemMetaUtil {
	
	/** 编码以及名称标示*/
	private static  int CODE_AND_NAME =1;
	/** 编码以及编码标示*/
	private static  int CODE_TO_CODE =2;
	
	/**
	 * 获取字段编码名称对应关系
	 * @param object 对象
	 * @return 数据
	 */
	public  static Map<String,String> getFiledsForCodeAndName(Object object){
		 return getFileds(object,CODE_AND_NAME);
	}
	/**
	 * 获取编码编码的对应关系
	 * @param object 对象
	 * @return 数据
	 */
	public  static Map<String,String> getFiledsForCodeAndCode(Object object){
		 return getFileds(object,CODE_TO_CODE);
	}
	
	/**
	 * 获取列数据
	 * @param object 对象
	 * @param type 类型
	 * @return 列数据集合
	 */
	private static Map<String,String> getFileds(Object object,int type){
		Map<String,String> filedsMap =new HashMap<String,String>();
		Class c =object.getClass();
		String key ="";
		String value="";
		for(Field f:c.getFields()){
			try {
				System.out.println(f.getType()+" "+f.getName()+" ");
				if(f.getType().equals(MetaDetal.class)){
						Object obj = f.get(object);
						MetaDetal m=(MetaDetal)obj;
						key =m.getCode();
						value =m.getCode();
						if(type==CODE_AND_NAME){
							value=m.getName();
						}
						filedsMap.put(key, value);
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		System.out.println(filedsMap.size());
		return filedsMap;
		
	}
}

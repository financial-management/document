package com.yonyou.doc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.util.tools.helper.RmStringHelper;

public class ApiBeanHelper {
	
	public void populate() {
		
	}

	/**
	 * 为bean相应属性注值
	* @param bean
	* @param key
	* @param value
	* @param valueClass
	* @return
	 */
	public static boolean setBeanValue(Object bean, String key, String value, Class valueClass) {
		boolean flag = false;
		Class prototypeClass = bean.getClass();
		Field[] fields = prototypeClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].getName().equalsIgnoreCase(key)){
				String setMethodName = "set" + RmStringHelper.toFirstUpperCase(fields[i].getName());
				Method setMethod = null;
				try {
					setMethod = prototypeClass.getDeclaredMethod(setMethodName, new Class[]{valueClass});
					setMethod.invoke(bean, new Object[]{value});
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
}

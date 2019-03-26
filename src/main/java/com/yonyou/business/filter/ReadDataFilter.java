package com.yonyou.business.filter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.business.MetaDataUtil;

/** 
 * @author zzh
 * @version 创建时间：2016年9月14日
 * 自定义数据源查询过滤器
 */
public abstract class ReadDataFilter extends MetaDataUtil {

	//转换目标
	//FIXME 待扩充
	protected enum TransTarget{
		TRANSNAME,//更改字段中文翻译
	}
	
	public abstract void doFilter(String dataSourceCode,List<ConcurrentHashMap<String,Object>> dataMap) ;
	
	public abstract void doColFilter(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> colMap);
	
	public abstract void doDateFilter(String dataSourceCode,List<ConcurrentHashMap<String,Object>> dataMap) ;
	
	/**
	 * 
	* @Title transCol 
	* @Description 修改元数据字段信息——暂只支持更改中文翻译，待扩充
	* @param colMap
	* @param transMap
	 */
	protected void transCol(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> colMap, Map<String, String> transMap ,TransTarget transTarget){
		for(Entry<String,String> transEntry : transMap.entrySet()){
			String fieldCode = transEntry.getKey();
			switch (transTarget) {
			case TRANSNAME:
				//更改字段中文翻译
				colMap.get(fieldCode).put(FIELD_NAME, transEntry.getValue());
				break;
			
			default:
				break;
			}
				
		}
	}
}

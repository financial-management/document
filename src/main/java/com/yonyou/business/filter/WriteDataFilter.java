package com.yonyou.business.filter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * @author zzh
 * @version 创建时间：2016年9月14日
 * 自定义数据源写入过滤器
 */
public abstract class WriteDataFilter {

	public abstract void doFilter(String dataSourceCode,List<ConcurrentHashMap<String,Object>> dateMap) ;
}

package com.yonyou.business.filter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * @author zzh
 * @version 创建时间：2016年9月14日
 * 自定义数据源写入过滤器实现类
 * 写入的数据源可通过该类进行过滤，所有业务自定义写入过滤都需要继承该类
 */
public class WriteDefaultDataFilterImpl extends WriteDataFilter{

	@Override
	public void doFilter(String dataSourceCode, List<ConcurrentHashMap<String,Object>> dateMap) {
		// TODO 自动生成的方法存根
		
	}

}

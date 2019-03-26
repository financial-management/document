package com.yonyou.business;

public class cacheInitUtil {
	
	public void init(){
		System.out.println("由数据库读取各类缓存数据");
		//是否项目启动时，启动全部调度
		//QuartzManager.startJobs();
		MetaDataUtil.init();
		DataSourceUtil.init();
		ProxyPageUtil.init();
		RmDictReferenceUtil.init();
	}
	
	public void destory(){
		System.out.println("清理各类缓存数据");
		MetaDataUtil.clear();
		DataSourceUtil.clear();
		ProxyPageUtil.clear();
		RmDictReferenceUtil.clear();
	}
}
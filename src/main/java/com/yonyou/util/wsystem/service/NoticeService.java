package com.yonyou.util.wsystem.service;

import com.yonyou.util.wsystem.api.HTTPApi;

public class NoticeService extends HTTPApi{
	
	private String url;
	
	public NoticeService(String _http_url){
		url=_http_url;
	}
	@Override
	public String findHttpUrl() {
		// TODO 自动生成的方法存根
		return url;
	}
	public String getHttp_url() {
		return url;
	}
	public void setHttp_url(String http_url) {
		this.url = http_url;
	}
	
	
}

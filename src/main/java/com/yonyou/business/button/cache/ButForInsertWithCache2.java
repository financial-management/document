package com.yonyou.business.button.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.business.button.util.ButForInsert2;
import com.yonyou.util.jdbc.IBaseDao;

public class ButForInsertWithCache2 extends ButForInsert2{

	@Override
	protected void afterOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO 自动生成的方法存根
		String dataSourceCode = request.getParameter("dataSourceCode");
		ClearCacheUtil.clearCache(dataSourceCode);
		
	}

}

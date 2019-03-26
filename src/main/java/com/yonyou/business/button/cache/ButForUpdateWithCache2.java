package com.yonyou.business.button.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.business.button.util.ButForUpdate;
import com.yonyou.business.button.util.ButForUpdate2;
import com.yonyou.util.jdbc.IBaseDao;

public class ButForUpdateWithCache2 extends ButForUpdate2 {

	@Override
	protected void afterOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {

		String dataSourceCode = request.getParameter("dataSourceCode");
		ClearCacheUtil.clearCache(dataSourceCode);

	}

}

package com.yonyou.business.button.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.business.button.util.ButForDelete;
import com.yonyou.util.jdbc.IBaseDao;

public class ButForDeleteWithCache extends ButForDelete {

	@Override
	protected void afterOnClick(IBaseDao dcmsDAO, HttpServletRequest request,
			HttpServletResponse response) {

		String dataSourceCode = request.getParameter("dataSourceCode");
		ClearCacheUtil.clearCache(dataSourceCode);

	}

}

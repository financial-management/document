package com.yonyou.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.business.button.ButtonAbs;
import com.yonyou.business.button.cache.ButForDeleteWithCache;
import com.yonyou.business.button.cache.ButForInsertWithCache;
import com.yonyou.business.button.cache.ButForInsertWithCache2;
import com.yonyou.business.button.cache.ButForUpdateWithCache;
import com.yonyou.business.button.cache.ButForUpdateWithCache2;
import com.yonyou.business.button.util.ButForAudit;
import com.yonyou.business.button.util.ButForDeleteForDb;
import com.yonyou.business.button.util.ButForInsert;
import com.yonyou.business.button.util.ButForPhysicalDelete;
import com.yonyou.business.button.util.ButForSelect;
import com.yonyou.business.button.util.ButForUpdate;


@RestController
@RequestMapping(value = "/buttonBase")
public class ButtonBase extends ButtonController {

	
	private Map<String,ButtonAbs> buttonMap =new HashMap<String,ButtonAbs>();
	{
		/*************************公共的*****************/
		buttonMap.put("add", 	new ButForInsert());//新增
		buttonMap.put("update", new ButForUpdate());//修改 
		buttonMap.put("query", 	new ButForSelect());//查询
		buttonMap.put("delete", new ButForDeleteForDb());//删除 //应demo需要 暂换成物理删除  ；ButForDelete 为逻辑删除  --moing_Ink
		buttonMap.put("physicalDelete", new ButForPhysicalDelete());//物理删除
		
		/*************************带缓存的数据的增删改*****************/
		buttonMap.put("insertWithCache", new ButForInsertWithCache());//新增
		buttonMap.put("updateWithCache", new ButForUpdateWithCache());//修改
		buttonMap.put("deleteWithCache", new ButForDeleteWithCache());//删除
		
		/*************************插入、修改数据字段*****************/
		buttonMap.put("insertWithCache2", new ButForInsertWithCache2());
		buttonMap.put("updateWithCache2", new ButForUpdateWithCache2());
		
		/******************************审批*****************************************/
		buttonMap.put("audit", new ButForAudit());
	}
	
	@Override
	protected Map<String, ButtonAbs> findButtonMap() {
		// TODO 自动生成的方法存根
		return buttonMap;
	}
	
	
	@Override
	public void init(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO 自动生成的方法存根
		super.init(request, response);
	}

	
}

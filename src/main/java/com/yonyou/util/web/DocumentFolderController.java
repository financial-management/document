package com.yonyou.util.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.business.entity.TokenEntity;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.util.SpringContextUtil;
import com.yonyou.util.service.DocumentFolderService;
import com.yonyou.util.service.DocumentMgrService;
import com.yonyou.web.BaseController;

@RestController
@RequestMapping(value="/documentFolder")
public class DocumentFolderController extends BaseController{
	@Autowired
	private DocumentFolderService documentFolderService= (DocumentFolderService) SpringContextUtil.getBean("docFoldService");
	@RequestMapping(value="/addPersonFolder")
	public String addPersonFolder(@RequestBody String jsonStr,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		JSONObject fromObject=null;
		try {
			fromObject = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			jsonObject.put("status", "fail");
			jsonObject.put("msg", "参数格式错误");
			return jsonObject.toString();
		}
		TokenEntity tokenEntity = TokenUtil.initTokenEntity(request);
		String userId = tokenEntity.USER.getId();
		JSONObject directoryJson = fromObject;
		try {
			documentFolderService.addPersonFolder(directoryJson, userId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("status", "fail");
			jsonObject.put("msg", "添加目录失败");
			return jsonObject.toString();
		}
		jsonObject.put("status", "success");
		return jsonObject.toString();
	}
}

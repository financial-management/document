
function findUrl(modifyName) {
	return findUrlParam('base', modifyName, '');
}

function findUrlParam(actionName, modifyName, params) {
	var url = context + "/" + actionName + "?cmd=" + modifyName + params;
	return findUrlByToken(url);
}

function findUrlByToken(url){
	var mark ="?"
	if(url.indexOf("?")!=-1){
		mark ="&";
	}
	return url+mark+"token="+token;
}

function findUrlByDirectory(params)
{
	return findUrlParam("documentTree","dirPersion","");
}

function findUrlByAuthSave(params)
{
	return findUrlParam("documentTree","authSave","");
}

function findUrlByDelete(params)
{
	return findUrlParam("documentTree","delPersion","");
}

String.prototype.format = function(args) {
	if (arguments.length > 0) {
		var result = this;
		if (arguments.length == 1 && typeof (args) == "object") {
			for ( var key in args) {
				var reg = new RegExp("({" + key + "})", "g");
				result = result.replace(reg, args[key]);
			}
		} else {
			for (var i = 0; i < arguments.length; i++) {
				if (arguments[i] == undefined) {
					return "";
				} else {
					var reg = new RegExp("({[" + i + "]})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
		return result;
	} else {
		return this;
	}
}

function findPageParam() {
	return findPageParamByDataSourceCode(dataSourceCode);
}

function findPageParamByDataSourceCode(_dataSourceCode) {
	var pageParam = "&dataSourceCode=" + _dataSourceCode;
	//组装查询参数
	$("[id^=SEARCH-]").each(function() {
		if($(this).val().length > 0){
	  		pageParam += "&"+$(this).attr("id")+"="+$(this).val();
	  		}
	  	});
	return encodeURI(pageParam);
}

/**
 *由insPage块获取字段id、value，组装为json字符串 
 */
function getJson($div){
	var json = "{";
	$div.find("[id]").each(function() {
	  json += "\"" + $(this).attr("id") + "\":" + "\"" + $(this).val() + "\",";
	});
	json = json.substring(0,json.length-1);
	json += "}";
	return json;
}

function findCheckMessage() {
	var message = "table:" + JSON.stringify(oTable.bootMethod($table, "getSelections"));
	oTable.showModal('modal', message);
}

function getSelections () {
	//注意：bootstrap-table内置getSelections方法所返回的json不能直接用于ajax传输——存在JSONNull数据
	return JSON.stringify(oTable.bootMethod($table, "getSelections"));
}

function getData () {
	//注意：bootstrap-table内置getSelections方法所返回的json不能直接用于ajax传输——存在JSONNull数据
	return JSON.stringify(oTable.bootMethod($table, "getData"));
}

function getDataJsonArray() {
	//注意：bootstrap-table内置getSelections方法所返回的json不能直接用于ajax传输——存在JSONNull数据
	return oTable.bootMethod($table, "getData");
}

function getTableJsons(){
	return oTable.bootMethod($table, "getSelections");
}

function getTableJsonsByDiv($t){
	return oTable.bootMethod($t, "getSelections");
}

function  busEvent(t,buttonParams,_dataSourceCode){
	
	var message = transToServer(findBusUrlByPublicParam(t,buttonParams,_dataSourceCode),getSelections());
	oTable.showModal('modal', message);
}

function findBusUrlByPublicParam(t,buttonParams,_dataSourceCode){
	var buttonToken = $(t).attr("buttonToken");
	return findBusUrlByButtonTonken(buttonToken,buttonParams,_dataSourceCode);
}

function findBusUrlByButtonTonken(buttonToken,buttonParams,_dataSourceCode){
	return  findUrlParam('buttonBase','button','&buttonToken='+buttonToken+findPageParamByDataSourceCode(_dataSourceCode)+buttonParams);
}

function loadJs(file) {
	var script=document.createElement("script");  
	script.type="text/javascript";  
	script.src=file;  
	document.getElementsByTagName('head')[0].appendChild(script);  
}


function loadScript(url, callback) {
	  var script = document.createElement("script");
	  script.type = "text/javascript";
	  if(typeof(callback) != "undefined"){
		//页面模板默认按钮暂时加载
		callback();
	    if (script.readyState) {
	      script.onreadystatechange = function () {
	        if (script.readyState == "loaded" || script.readyState == "complete") {
	          script.onreadystatechange = null;
	          callback();
	        }
	      };
	    } else {
	      script.onload = function () {
	        callback();
	      };
	    }
	  }
	  script.src = url;
	  document.body.appendChild(script);
	}

function loadJsIsFLEF(filespec){
	$.load( filespec , ''  , function(responseText, textStatus, XMLHttpRequest){
	    if(textStatus !== "success"){
	        alert("文件下载失败");//console.log("文件下载失败");
	    }
	} );
}

function loadBusJsByDataSource(dataSourceCode){
	
	loadJs(findBusJsUrl(dataSourceCode));
}

function findBusJsUrl(dataSourceCode){
	var fileName=dataSourceCode;
	if(menuCode!=''&&menuCode.length>0){
		fileName=dataSourceCode+'_'+menuCode;
	}
	
	var file ="../pages/busJs/"+fileName+".js";
	return file;
}

function appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue){

	return url+"&_dataSourceCode="+_dataSourceCode
		      +"&ParentPKField="+ParentPKField
		      +"&ParentPKValue="+ParentPKValue;
}

function clickFunction(row,tr){
	
}

function bulidCacheTableJsonArray(){
	
}



function isToggle(){
	
	$("div[ishidden=true]").toggle();
}

//不使用组件上传附件
function uploadAffix(tid){
	console.log(tid);
	var $a = $("#"+tid);
	console.log($a.val());
	//var formData = new FormData($("#"+tid));
	$.ajax({
		type: "POST",
		url: "/document/base?cmd=uploadAffix&fieldCode="+tid+"&batchNo="+$a.val(),
		data: new FormData($a.parent()[0]),
		dataType: "json",
		async: false,  
		cache: false,  
		contentType: false,  
		processData: false,
		success: function (data) {
			$a.val(data['batchno']);
			var fileId = data['fileid'];
			$("#fileList_"+tid).append("<li class='list-group-item' id='"
				+fileId+"'>"+ data['filename'] + "<a href='/document/base?cmd=downloadAffix&fid="
				+fileId+"' class='btn green'>下载</a><a href='javascript:deleteAffix(\""
				+fileId+"\")' class='btn red'>删除</a></li>");
		},
		error: function(data) {
			alert("上传附件失败，请联系管理员"+data);
		}
	});
	clearFile();
}

//删除附件
function deleteAffix(fid){
	oTable.confirm('确定删除此文件吗?', function(result){
		if(result){
			var message = transToServer('/document/base?cmd=deleteAffix&fid='+fid,null);
			if(message.indexOf('成功') > 0){
				$("#"+fid).remove();	
			}
			oTable.showModal('modal',message);
		}
	});
}
//清空file域
function clearFile(){
	var file = $(".file") 
	file.after(file.clone().val("")); 
	file.remove();
}

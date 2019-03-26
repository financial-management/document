var oTable = new TableInit();

var $table = $('#table');
var $queryParam = $("#queryParam");
var $inspage = $("#insPage");

//新模板用两变量分别存储后台返回的新增/修改页面html
var tempInsHtml = "";
var tempUpdHtml = "";

var buttonJson =null;
//var selectPageParam='';
//var listPageParam='';
//var maintainPageParam='';

var validJson ={} ;

function bulidAlonePage() {
	bulidPage(true,true,true,true);
}

//function initPageParam(_selectPageParam,_listPageParam,_maintainPageParam){
//	selectPageParam=pageParamFormat(_selectPageParam);
//	listPageParam=pageParamFormat(_listPageParam);
//	maintainPageParam=pageParamFormat(_maintainPageParam);
//}


function bulidPage(isBulidSelect,isBulidButton,isBulidListPage,isBulidMaintainPage){
	//showLoading("请稍等！");
	if(dataSourceCode!=null&&dataSourceCode!='null'){
		if(pageName!=null&&pageName!='null'){
			$("#pageName").html(pageName);
		}
		if(isBulidSelect){
			bulidSelect($queryParam,dataSourceCode,'');
		}
		if(isBulidButton){
			bulidButton($("#button_div"),dataSourceCode);
		}
		if(isBulidListPage){
			var param =_query_param;
			if(_query_param==null||_query_param.length==0){
				param=initTableParam;
			}
			bulidListPage($table,dataSourceCode,param);
		}
		if(isBulidMaintainPage){
			bulidMaintainPage($inspage,dataSourceCode,'');
		}
		$inspage.bootstrapValidator(validJson);
		$('.form_date').datetimepicker({
    		minView: 'month',         //设置时间选择为年月日 去掉时分秒选择
   			format:'yyyy-mm-dd',
    	    weekStart: 1,
    	    todayBtn:  1,
    	    autoclose: 1,
    	    todayHighlight: 1,
    	    startView: 2,
    	    forceParse: 0,
    	    showMeridian: 1,
    	    language: 'zh-CN'              //设置时间控件为中文
    	});
    	//存在附件字段时页面追加“已上传文件列表”
    	$inspage.find(".file").each(function() {
    		$inspage.append("<div class='col-md-6 list-group' id='fileList_"+$(this).siblings(".fileHidden").attr("ID")+"'><a href='#' class='list-group-item active'>已上传文件列表</a></div>");
    	});
    	//附件插件初始化
    	// $('.file').fileinput({
	        // language: 'zh',
	        // hideThumbnailContent: true,
	        // uploadUrl:"/cdp/", 
	        // // uploadExtraData://上传时额外传入的参数
	        // // deleteUrl:
	        // // deleteExtraData:
	        // // allowedFileExtensions:['jpg','gif'],//允许的文件后缀
	        // // showPreview: false,//是否显示预览区域
	        // preferIconicPreview: true,
	        // showCancel: false,
	        // showClose: false,
	        // maxFileSize: 102400,//KB
	        // maxFileCount: 100,
	        // //dropZoneEnabled:false, //是否显示拖拽区域
	        // // dropZoneTitleClass//拖拽区域类属性
	        // // elCaptionText://标题栏提示信息
	    // });
	}else{
		alert("dataSource is null");
	}
}
//构建按钮
function bulidButton($b,_dataSourceCode){
	bulidButtonHtml($b,_dataSourceCode);
}

function bulidSelect($q,_dataSourceCode,_selectPageParam){
	oTable.initQueryParam($q, paramurl + findPageParamByDataSourceCode(_dataSourceCode)+_selectPageParam);
}

function bulidListPage($t,_dataSourceCode,_listPageParam){
	//oTable.initCols($t, colurl + findPageParamByDataSourceCode(_dataSourceCode), qusurl + findPageParamByDataSourceCode(_dataSourceCode)+_listPageParam);
	bulidListPageForQusUrl($t,_dataSourceCode,_listPageParam,qusurl);
}

function bulidListPageForQusUrl($t,_dataSourceCode,_listPageParam,_qusurl){
	//oTable.initCols($t, colurl + findPageParamByDataSourceCode(_dataSourceCode), qusurl + findPageParamByDataSourceCode(_dataSourceCode)+_listPageParam);
	if(_qusurl!=null){
		_qusurl= _qusurl + findPageParamByDataSourceCode(_dataSourceCode)+_listPageParam;
	}
	oTable.initCols($t, colurl + findPageParamByDataSourceCode(_dataSourceCode),_qusurl);
}


function bulidListPageForQusUrlAndColUrl($t,_dataSourceCode,_listPageParam,_qusurl,_colurl){
	//oTable.initCols($t, colurl + findPageParamByDataSourceCode(_dataSourceCode), _qusurl + findPageParamByDataSourceCode(_dataSourceCode)+_listPageParam);
	oTable.initCols($t, _colurl + findPageParamByDataSourceCode(_dataSourceCode),  _qusurl + findPageParamByDataSourceCode(_dataSourceCode)+_listPageParam);
}

function bulidMaintainPage($i,_dataSourceCode,_maintainPageParam){
	if(_maintainPageParam!=null&&_maintainPageParam.length>0){
		tempInsHtml = oTable.initMaintainCols($i, findDatanurl + findPageParamByDataSourceCode(_dataSourceCode)+_maintainPageParam);
	}else{
		tempInsHtml = oTable.initMaintainCols($i, maintainurl + findPageParamByDataSourceCode(_dataSourceCode));
	}
	if(isNewModel != true){
		$i.append(tempInsHtml);	
	}else{
		//新模板获取独立修改页面（新增与修改分开）
		tempUpdHtml = oTable.initMaintainCols($i, indupdateurl + findPageParamByDataSourceCode(_dataSourceCode));
	}
	
	validJson=transToServer(findUrlParam('base','queryValids','&dataSourceCode='+dataSourceCode),'');
}

function setReadonlyByDiv($div){
	$div.each(function(){
		$(this).attr("readonly","readonly");
	});
}


function setUnReadonlyByDiv($div){
	$div.each(function(){
		$(this).attr("readonly","");
	});
}

function GetRequest() { 
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
	var str = url.substr(1); 
	strs = str.split("&"); 
	for(var i = 0; i < strs.length; i ++) { 
	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
	} 
	} 
	return theRequest; 
	}

function changeURLPar(destiny, par, par_value) 
	{ 
	var pattern = par+'=([^&]*)'; 
	var replaceText = par+'='+par_value; 
	if (destiny.match(pattern)) 
	{ 
	var tmp = '/\\'+par+'=[^&]*/'; 
	tmp = destiny.replace(eval(tmp), replaceText); 
	return (tmp); 
	} 
	else 
	{ 
	if (destiny.match('[\?]')) 
	{ 
	return destiny+'&'+ replaceText; 
	} 
	else 
	{ 
	return destiny+'?'+replaceText; 
	} 
	} 
	return destiny+'\n'+par+'\n'+par_value; 
} 


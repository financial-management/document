
function queryTable(t) {
	var param =_query_param;
	if(_query_param==null||_query_param.length==0){
		param=initTableParam;
	}

	queryTableByParam(t,dataSourceCode,param);
}

function queryTableByDataSourceCode(t,v_dataSourceCode){
	var param =_query_param;
	if(_query_param==null||_query_param.length==0){
		param=initTableParam;
	}
	queryTableByParam(t,v_dataSourceCode,param);
}


function queryTableByParam(t,_dataSourceCode,_query_param){
	var queryButtonToken=$("#query_buttontoken").val();
	var buttonToken='';
	if(queryButtonToken!=null&&queryButtonToken.length>0){
		buttonToken=$("#query_buttontoken").val();
	}else{
		if(query_buttonToken!=null&&query_buttonToken.length>0){
			buttonToken = query_buttonToken;
		}else{
			buttonToken = $(t).attr("buttonToken");
		}
		
		$("#query_buttontoken").val(buttonToken);
	}
	oTable.queryTable($table, findBusUrlByButtonTonken(buttonToken,_query_param,_dataSourceCode));
}

function detail(t){
	detailDataByDataSourceCode(t,dataSourceCode);
}

function detailDataByDataSourceCode(t,dataSourceCode){
	var selected = JSON.parse(getSelections());
	
}

function delRows(t){
	delRowsByDataSourceCode(t,dataSourceCode);
}

function delRowsByDataSourceCode(t,_dataSourceCode){
	var selected = JSON.parse(getSelections());
	if(selected.length < 1){
		oTable.showModal('modal', "请至少选择一条数据进行删除");
		return;
		}
	var message = transToServer(findBusUrlByPublicParam(t,'',_dataSourceCode),getSelections());
	oTable.showModal('modal', message);
	queryTableByDataSourceCode(t,_dataSourceCode);
}

function back(t){
	var cache_dataSourceCode =$("#cache_dataSourceCode").val();
	var _dataSourceCode=dataSourceCode;
	if(cache_dataSourceCode!=null&&cache_dataSourceCode.length>0){
		_dataSourceCode=cache_dataSourceCode;
	}
	togByDataSourceCode(t,_dataSourceCode);
	$inspage.find('[id]').val("");
	$("#ins_or_up_buttontoken").val("");
	//重置附件组件
	//$('.file').fileinput('reset');
	//不使用组件时清空file域
	clearFile();
	//清空附件清单
	$("[id^=fileList]").children("li").remove();
}


function save(t){
	$inspage.data( "bootstrapValidator").validate();
    if(!$inspage.data('bootstrapValidator').isValid()){ 
        return ; 
    }
    
    var cache_dataSourceCode =$("#cache_dataSourceCode").val();
	var _dataSourceCode=dataSourceCode;
	if(cache_dataSourceCode!=null&&cache_dataSourceCode.length>0){
		_dataSourceCode=cache_dataSourceCode;
	}
	saveByDataSourceCode(t,_dataSourceCode);
	
}

function saveByDataSourceCode(t,_dataSourceCode){
	savaByQuery(t,_dataSourceCode,$inspage);
}

function savaByQuery(t,_dataSourceCode,$div){
	var message ="";
	var buttonToken =$("#ins_or_up_buttontoken").val();
	message = transToServer(findBusUrlByButtonTonken(buttonToken,'',_dataSourceCode),getJson($div));
	oTable.showModal('modal', message);
	back(t);
	queryTableByDataSourceCode(t,_dataSourceCode);
}

function savaByQueryForSuper(t,_dataSourceCode,$div){
	var message ="";
	var buttonToken =$(t).attr("buttonToken");
	message = transToServer(findBusUrlByButtonTonken(buttonToken,'',_dataSourceCode),getJson($div));
	oTable.showModal('modal', message);
}

function updateRow(t){
	updataRowByDataSourceCode(t,dataSourceCode);
}

function updataRowByDataSourceCode(t,_dataSourceCode){
	updataRowByQuery(t,_dataSourceCode,$inspage);
}

function updataRowByQuery(t,_dataSourceCode,$updateDiv){
	var buttonToken=$(t).attr("buttonToken");
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行修改");
		return;
	}
	if(isNewModel == true){
		$updateDiv.html(tempUpdHtml);
		if(typeof handle_common === "function"){
			handle_common();
		}
		if(typeof handle_update === "function"){
			handle_update();
		}
	}
	$updateDiv.find("[id]").each(function() {			
	  	$(this).val(selected[0][$(this).attr("id")]);
	});
	//存在文件字段时需要由后台获取附件清单
	$updateDiv.find(".file").each(function() {
		var $batchNo = $(this).siblings(".fileHidden");
		var batchNo = $batchNo.val();
		if( batchNo != null && batchNo.length > 0){
			var tempHtml = transToServer(findUrlParam('base','getAffixList','&batchNo='+batchNo),null);
			if(tempHtml != null){
				$("#fileList_"+$batchNo.attr("ID")).append(tempHtml);
			}else{
				alert("请求附件清单失败！请联系管理员");
				return;
			}
		}
	});

	$("#ins_or_up_buttontoken").val(buttonToken);
	$("#cache_dataSourceCode").val(_dataSourceCode);
	togByDataSourceCode(t,_dataSourceCode);
}

function tog(t){
	if(isNewModel == true){
		$inspage.html(tempInsHtml);
		if(typeof handle_common === "function"){
			handle_common();
		}
		if(typeof handle_insert === "function"){
			handle_insert();
		}
	}
	togByDataSourceCode(t,dataSourceCode);
}

function togByDataSourceCode(t,_dataSourceCode){
	bulidValid();
	var name =$(t).html();
	if(name!=null&&name.length>0){
		$("#tog_titleName").html(name);
	}
	var buttonToken=$(t).attr("buttonToken");
	$("#ins_or_up_buttontoken").val(buttonToken);
	$("#cache_dataSourceCode").val(_dataSourceCode);
	var $bulidtable = $("#bulidTable");
	var $bulidpage = $("#bulidPage");
	if($bulidtable.is(":hidden")){
		$bulidtable.slideDown();
		$bulidpage.slideUp();
	}else{
		$bulidpage.slideDown();
		$bulidtable.slideUp();
	}
}

function bulidValid(){
	$inspage.data('bootstrapValidator').destroy();
	$inspage.data('bootstrapValidator', null);
	$inspage.bootstrapValidator(validJson);
}


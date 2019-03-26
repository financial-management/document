
function write_multiselect_html(datasourceCode , jsonMessage){
	var multiselectId = "multiselectModal" ; 
	if($("#"+multiselectId).length>0){
		$("#"+multiselectId).modal("show");
	}else{
		$(document.body).append(
			"<div class='modal fade' id='"+multiselectId+"'>"+
				"<div class='modal-dialog'  style='width: 80%;height: 80%'>"+
					"<div class='modal-content' >"+
						"<div class='modal-header'>"+
							"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>"+
							"×"+
							"</button>"+
							"<h4 class='modal-title' id='"+multiselectId+"Label'>左右选择</h4>"+
						"</div>"+
						"<div id='"+multiselectId+"body' class='modal-body' style='height: auto' ></div>"+
						"<div id='iframeBody' class='modal-body' style='height: auto' >"+
							"<iframe id='multiselectIfrema' src='busPage/multiselect.jsp' scrolling='yes' frameborder='0' width='99%' height='515'></iframe>"+
						"</div>"+
						"<div class='modal-footer'>"+
							"<button type='button' class='btn btn-primary ok' id='multiselect_ok' onclick='multiselectOk()' data-dismiss='modal'>确认</button>"+
							"<button type='button' class='btn btn-default cancel' id='multiselect_cancel' data-dismiss='modal'>取消</button>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>"+
			"<input type='hidden' value='' id='multiselectHidden'>");
		$("#multiselectHidden").val(joinOptions(jsonMessage));
		$("#"+multiselectId).modal("show");
		$('#multiselectModal').on('hidden.bs.modal', function() {
			$("#multiselectModal").remove();
			$("#multiselectHidden").remove();
		});
	}
}



function joinOptions (jsonMessage) {
	var jsonArray = JSON.parse(jsonMessage);
	var jsonLen = jsonArray.length;
	var optionHtml = "" ;
	var alisTable = $("#ALIS_TABLE").val();
	var joinTables = $("#JOIN_TABLES").val();
	//获取字段最大长度，以便拼装
	var index = 0 ; 
	for(var i = 0 ; i < jsonLen ; i++){
		if(jsonArray[i]['FIELD_NAME'].length > jsonArray[index]['FIELD_NAME'].length){
			index = i ;
		}
	}
	var maxValueLength = jsonArray[index]['FIELD_NAME'].length;
	
	for(var i = 0 ; i < jsonLen ; i++){
		var fieldCodeStr = jsonArray[i]['FIELD_CODE'] ; 
		var fieldNameStr = jsonArray[i]['FIELD_NAME'] ; 
		optionHtml += "<option value='"+buildFieldId(fieldCodeStr , joinTables)+"'>"+
			buildFieldCode(fieldNameStr, joinTables , maxValueLength)+
			//buildFieldCode(fieldCodeStr, joinTables , maxValueLength) +
			buildFieldId(fieldCodeStr , joinTables) +　"【"+
			jsonArray[i]['DATA_NAME']+"："+
			jsonArray[i]['DATA_CODE']+"】"+
			"</option>";
	}
	return optionHtml ;
}

function pad2(str, n) {   
	if (str.length >= n) {
		return str;   
	}else{
		return pad2(str + "＊", n);   
	}
}  

function buildFieldCode(str , joinTables , maxValueLength){
	if("" != joinTables){
		
	}else{		//单表情况
		str = str.substr(str.indexOf('.')+1) ; 
	}
	return pad2(str , maxValueLength);
}

function buildFieldId(fieldCodeStr , joinTables){
	if("" != joinTables){
		return fieldCodeStr ; 
	}else{
		return fieldCodeStr.substr(fieldCodeStr.indexOf('.')+1);
	}
}

//点击确定回填
function multiselectOk(){
	$("#RETURN_FIELD").val($("#multiselectIfrema").contents().find("#VIEW_RETURN_FIELD").val());
	$("#DISPLAY_FIELD").val($("#multiselectIfrema").contents().find("#VIEW_DISPLAY_FIELD").val());
	$("#UPDATE_FIELD").val($("#multiselectIfrema").contents().find("#VIEW_UPDATE_FIELD").val());
	$("#MAINTAIN_FIELD").val($("#multiselectIfrema").contents().find("#VIEW_MAINTAIN_FIELD").val());
	$("#QUERY_FIELD").val($("#multiselectIfrema").contents().find("#VIEW_QUERY_FIELD").val());
}

function allTableName(){
	var metaDataCode = $("#METADATA_CODE").val();
	var joinTables = $("#JOIN_TABLES").val();
	
	var dataCodes = metaDataCode + ","; 
	if(joinTables == ""){	//单表查询
		dataCodes = metaDataCode + ","; 
	}else{
		if(joinTables.indexOf(",") == -1){	//两表联查
			if(joinTables.indexOf(" ") != -1){	//有别名
				dataCodes += joinTables.split(" ")[0] + ",";
			}else{	//没有别名
				dataCodes += joinTables + ",";
			}
		}else{
			var arr = joinTables.split(",") ; 	//多表联查
			for(var i = 0 ; i < arr.length ; i++){
				dataCodes += arr[i].trim().split(" ")[0]+ ",";
			}
		}
	}
	dataCodes = dataCodes.substr(0,dataCodes.length-1);
	return dataCodes ; 
}

function setOptgroupHtml(jsonArray , joinTables , maxValueLength){
	var jsonLen = jsonArray.length;
	var joinTables = $("#JOIN_TABLES").val();
	var metaDataCode = $("#METADATA_CODE").val();
	if(joinTables == ""){//单表
		return ; 
	}else{ 	//多表
		var tables = allTableName().split(",") ; 
		var optgroupHtml = "" ; 
		for(var a = 0 ; a < tables.length ; a++){
			
		
			for(var i = 0 ; i < jsonLen ; i++){
				var fieldCodeStr = jsonArray[i]['FIELD_CODE'] ; 
				var fieldNameStr = jsonArray[i]['FIELD_NAME'] ; 
				var fieldDataName = jsonArray[i]['DATA_NAME'] ;
				var fieldDataCode = jsonArray[i]['DATA_CODE'] ;
				optgroupHtml += " <optgroup label='"+fieldDataName+":"+fieldDataCode+"'>" ; 
			}
		}
	}
}







/**参照*/
function write_multiselect_ref(getColumnInfoUrl ,jsonMessage){
	var multiselectId = "multiselectModalRef" ; 
	if($("#"+multiselectId).length>0){
		$("#"+multiselectId).modal("show");
	}else{
		$(document.body).append(
			"<div class='modal fade' id='"+multiselectId+"'>"+
				"<div class='modal-dialog'  style='width: 90%;height: 90%'>"+
					"<div class='modal-content' >"+
						"<div class='modal-header'>"+
							"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>"+
							"×"+
							"</button>"+
							"<h4 class='modal-title' id='"+multiselectId+"Label'>参照回写</h4>"+
						"</div>"+
						"<div id='"+multiselectId+"body' class='modal-body' style='height: auto' ></div>"+
						"<div id='iframeBody' class='modal-body' style='height: auto' >"+
							"<iframe id='multiselectRefIfrema' src='busPage/metadata_multiselect.jsp' scrolling='yes' frameborder='0' width='99%' height='700'></iframe>"+
						"</div>"+
						"<div class='modal-footer'>"+
							"<button type='button' class='btn btn-primary ok' id='multiselect_ok' onclick='multiselectJoinOk()' data-dismiss='modal'>确认</button>"+
							"<button type='button' class='btn btn-default cancel' id='multiselect_cancel' data-dismiss='modal'>取消</button>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>"+
			"<input type='hidden' value='' id='multiselectReferHidden'>"
		);
		$("#multiselectReferHidden").val(joinColumnOptions(jsonMessage));
		$("#"+multiselectId).modal("show");
		$('#multiselectModalRef').on('hidden.bs.modal', function() {
			$("#multiselectModalRef").remove();
			$("#multiselectReferHidden").remove();
		});
	}
}

function joinColumnOptions (jsonMessage) {
	var jsonArray = JSON.parse(jsonMessage);
	var jsonLen = jsonArray.length;
	var optionHtml = "" ;
	//获取字段最大长度，以便拼装
	var index = 0 ; 
	for(var i = 0 ; i < jsonLen ; i++){
		if(jsonArray[i]['FIELD_NAME'].length > jsonArray[index]['FIELD_NAME'].length){
			index = i ;
		}
	}
	var maxValueLength = jsonArray[index]['FIELD_NAME'].length;
	for(var i = 0 ; i < jsonLen ; i++){
		var fieldCodeStr = jsonArray[i]['FIELD_CODE'] ; 
		var fieldNameStr = jsonArray[i]['FIELD_NAME'] ; 
		optionHtml += "<option value='"+fieldCodeStr+"'>"+
		fieldCodeStr+" → "+
		fieldNameStr +　
			"</option>";
	}
	return optionHtml ;
}

function multiselectJoinOk(){
	if(typeof(beforeConfirm) == "function"){
		if(beforeConfirm()){
			return ; 
		}
	}
	
	var refStr = $("#multiselectRefIfrema").contents().find("#multiselectRefer_to option");
	var writeStr = $("#multiselectRefIfrema").contents().find("#multiselect_to option");
	var refContent = "";
	$(refStr).each(function(index,element){ 
		refContent += $(this).val()  + ":" + $(writeStr).eq(index).val() + ";";
	});
	
	refContent = refContent.substr(0,refContent.length-1) + "," ; 
	var datasourceCode = $("#multiselectRefIfrema").contents().find("#DATASOURCE_CODE").val()+",";
	var option = $("#multiselectRefIfrema").contents().find("#CHOOSE_OPTION option:selected").val();
	var inputFormart ="REF(" + datasourceCode + refContent + option + ")" ;
	$("#INPUT_FORMART").val(inputFormart);
}

function beforeConfirm(){
	var referLength = $("#multiselectRefIfrema").contents().find("#multiselectRefer_to option").length;
	var length = $("#multiselectRefIfrema").contents().find("#multiselect_to option").length;
	var isCheckbox = $("#multiselectRefIfrema").contents().find("#CHOOSE_OPTION").val();
	if(referLength!=length)	{
		oTable.showModal('提示', "字段数量不匹配！");
		return true;
	}
	if(""== isCheckbox){
		oTable.showModal('提示', "请选择'单选'  or '多选'!");
		return true;
	}
	return false ; 
}

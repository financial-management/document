<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>选择框</title>
<script src="../js/jquery.min.js"></script>

<script src="../multiselect-master/multiselect.js"></script>
<script src="../multiselect-master/prettify.min.js"></script>
<link href="../multiselect-master/prettify.css" rel="stylesheet" />
<link href="../multiselect-master/style.css" rel="stylesheet" />

<script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../vendor/bootstrap-table/src/bootstrap-table.js"></script>
<link rel="stylesheet" href="../../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../../vendor/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="../js/examples.css">

</head>
<body>

	<form class="form-horizontal">
		<div class="panel panel-primary">
			<div class="panel-body" id="bulidTable">
				<div class="panel panel-primary">
					<div class="panel-body" id="pageField">
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="col-md-2">
								<label class="control-label" for="VIEW_RETURN_FIELD">查询返回字段：</label>
							</div>
							<div class="col-md-10">
								<input class="form-control" id="VIEW_RETURN_FIELD" name="VIEW_RETURN_FIELD" value="" type="TEXT">
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="col-md-2">
								<label class="control-label" for="VIEW_DISPLAY_FIELD">展示页面字段：</label>
							</div>
							<div class="col-md-10">
								<input class="form-control" id="VIEW_DISPLAY_FIELD" name="VIEW_DISPLAY_FIELD" value="" type="TEXT">
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="col-md-2">
								<label class="control-label" for="VIEW_UPDATE_FIELD">修改页面字段：</label>
							</div>
							<div class="col-md-10">
								<input class="form-control" id="VIEW_UPDATE_FIELD" name="VIEW_UPDATE_FIELD" value="" type="TEXT">
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="col-md-2">
								<label class="control-label" for="VIEW_MAINTAIN_FIELD">维护页面字段：</label>
							</div>
							<div class="col-md-10">
								<input class="form-control" id="VIEW_MAINTAIN_FIELD" name="VIEW_MAINTAIN_FIELD" value="" type="TEXT">
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="col-md-2">
								<label class="control-label" for="VIEW_QUERY_FIELD">查询条件字段：</label>
							</div>
							<div class="col-md-10">
								<input class="form-control" id="VIEW_QUERY_FIELD" name="VIEW_QUERY_FIELD" value="" type="TEXT">
							</div>
						</div>
					</div>
					<div class="row" id="MultiSelectDiv">
						<div class="col-xs-5">
							<select name="from" id="multiselect" class="form-control" size="10" multiple="multiple"> </select>
						</div>
						<div class="col-xs-2">
							<button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
							<button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
							<button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
							<button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
							<button type="button" id="multiselect_upSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-up"></i></button>
							<button type="button" id="multiselect_downSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-down"></i></button>
						</div>
						<div class="col-xs-5">
							<select name="to" id="multiselect_to" class="form-control" size="10" multiple="multiple"></select>
						</div>
					</div>
				</div>
			</div>
	</form>
</body>

<input type="hidden" value="" id="METADATA_CODE"><!-- 元数据编码 -->
<input type="hidden" value="" id="METADATA_ID"><!-- 元数据ID -->
<input type="hidden" value="" id="JOIN_TABLES"><!-- 关联表 -->
<input type="hidden" value="" id="ALIS_TABLE"><!-- 表别名 -->
<input type="hidden" value="" id="FIELD_CODE"><!-- 字段名 -->
<input type="hidden" value="" id="FIELD_NAME"><!-- 显示名称 -->
<script type="text/javascript">
var metaDataId = $('#METADATA_ID', window.parent.document).val();
var metaDataCode = $('#METADATA_CODE', window.parent.document).val();
var alisTable = $('#ALIS_TABLE', window.parent.document).val();
var joinTables = $('#JOIN_TABLES', window.parent.document).val();
var jsonMessage = $("#multiselectHidden", window.parent.document).val();
var returnField = $("#RETURN_FIELD", window.parent.document).val();
var displayField = $("#DISPLAY_FIELD", window.parent.document).val();
var updateField = $("#UPDATE_FIELD", window.parent.document).val();
var maintainField = $("#MAINTAIN_FIELD", window.parent.document).val();
var queryField = $("#QUERY_FIELD", window.parent.document).val();

var viewDisplayField = "";
var viewMaintainField = "";
var viewQueryField = "";
var viewReturnField = "";
$(function() {
	
	$('#multiselect').multiselect({
		keepRenderingSort : false,
		sort : false,
		afterMoveToRight : function($left, $right, options) {
			fieldDispaly();
		},
		afterMoveToLeft : function($left, $right, option) {
			fieldDispaly();
		}
	});

	//上下移动排序
	$("#multiselect").html(jsonMessage);
	$("#multiselect_downSelected,#multiselect_upSelected").click(function() {
		var $opt = $("#multiselect_to option:selected:first");
		//  if (!$opt.length) return;
		if (this.id == "multiselect_upSelected") {
			$opt.prev().before($opt);
			fieldDispaly();
		} else {
			$opt.next().after($opt);
			fieldDispaly();
		}
	});
	//按Alt加上下鍵也可以移动
	$("#multiselect_to").keydown(function(evt) {
		//  if (!evt.altKey) return;
		var k = evt.which;
		if (k == 38) {
			$("#multiselect_upSelected").click();
			fieldDispaly();
			return false;
		} else if (k == 40) {
			$("#multiselect_downSelected").click();
			fieldDispaly();
			return false;
		}
	});

	//设置四个返回字段的默认值
	if (returnField.length > 0) {
		if (returnField.indexOf("*") != -1) {
			$("#VIEW_RETURN_FIELD").val(getAllOption());
		} else {
			$("#VIEW_RETURN_FIELD").val(returnField);
		}
	}
	if (displayField.length > 0) {
		$("#VIEW_DISPLAY_FIELD").val(displayField);
	}
	if(updateField.length > 0){
		$("#VIEW_UPDATE_FIELD").val(updateField);
	}
	if (maintainField.length > 0) {
		$("#VIEW_MAINTAIN_FIELD").val(maintainField);
	}
	if (queryField.length > 0) {
		$("#VIEW_QUERY_FIELD").val(queryField);
	} 

	//设置四个返回字段的可写属性，以及点击之后触发的方法
	var forValue = "";
	$("#pageField input").not(":first").attr("disabled", true);
	$("#pageField label").each(function() {
		$(this).click(function() {
			$("#pageField input").attr("disabled", true);
			forValue = $(this).attr("for");
			$("#" + forValue).attr("disabled", false);
			if(forValue == "VIEW_RETURN_FIELD"){
				setTimeout("modifyOption()",100); 
			}else{
				setTimeout(function(){viewLeftAndRight(getFiledArray($("#" + forValue).val()));},100);
			}
		});
	});
	//初始化加载返回字段信息，并放入右边框
	modifyOption();
});

//根据字段筛选情况，回写相应页面
function setFieldValue(writeField, $rightMultiselect) {
	viewDisplayField = "";
	var viewField = "";
	var temp = "" ; 
	$rightMultiselect.find("option").each(function() {
		viewDisplayField += $(this).val() + "&";
		if($(this).val().indexOf(".") > -1 && writeField != "VIEW_RETURN_FIELD"){
			temp = $(this).val().substr($(this).val().indexOf(".")+1) ; 
			viewField += temp + ",";
		}else{
			viewField += $(this).val() + "," ;
		}
	});
	
	if(writeField == "VIEW_RETURN_FIELD"){
		$("#VIEW_RETURN_FIELD").val(subStringField(viewField));
		synField();	
	}if(writeField == "VIEW_DISPLAY_FIELD"){
		$("#VIEW_DISPLAY_FIELD").val(subStringField(viewDisplayField));
	}else{
		$("#"+writeField).val(subStringField(viewField));
	}
}

/**展示字段、维护字段、条件字段视图*/
function viewLeftAndRight(objArr) {
	//将查询返回字段option字段插入右边
	modifyOption();
	//移除左边option
	$("#multiselect").find("option").remove();
	//将剩余option插入左边备用
	$("#multiselect_to").find("option").appendTo($("#MultiSelectDiv #multiselect"));
	
	//根据不同字段，匹配相应option至右框
	if (objArr.length > 0) {
		var matchIndex = new Array(objArr.length);
		$("#MultiSelectDiv option").each(function() {
			var thisValue = $(this).val() ; 
			if(thisValue.indexOf(".") != -1){
				$(this).val(thisValue.substr(thisValue.indexOf(".")+1));//回写展示字段、维护字段、条件字段去别名
			}
			//保留原展示字段、维护字段、条件字段中已有顺序
			var temp = objArr.indexOf($(this).val());
			if (temp != -1) {
				matchIndex.splice(temp,1,$(this));
			}
		});
		for(var i=0; i<matchIndex.length; i++){
			if(matchIndex[i]!=undefined){
				matchIndex[i].appendTo($("#multiselect_to"));
			}
		}
	}
	fieldDispaly();
}

/**设置展示字段、维护字段、条件字段在返回字段的基础上进行操作！   可以理解为右边的option是返回字段操作之后的数据，要操作其余几个字段，可以摒弃左边的option*/
function modifyOption(){
	//重新加载选择框数据
	$("#MultiSelectDiv option").remove();
	$("#multiselect").html(jsonMessage);
	//返回字段数组
	var returnFieldArr = getFiledArray($("#VIEW_RETURN_FIELD").val());
	
	if (returnFieldArr.length > 0) {
		$("#MultiSelectDiv option").each(function() {
			if (returnFieldArr.indexOf($(this).val()) != -1) {
				$(this).appendTo($("#multiselect_to"));
			}
		});
	}
}

/**获取所有option并返回其value，以“,”分隔*/
function getAllOption() {
	var allOptions = "";
	//清除缓存
	$("#MultiSelectDiv option").remove();
	//获取新数据
	$("#multiselect").html(jsonMessage);
	$("#MultiSelectDiv option").each(function() {
		allOptions += $(this).val() + ",";
	});
	return subStringField(allOptions);
}

/**字段显示配置*/
function fieldDispaly(){
	setFieldValue($("#pageField input").not(":disabled").attr("ID"), $("#multiselect_to"));
}

/**截取字符串末含有“,”的字符串*/
function subStringField(object) {
	if (object.length > 0) {
		return object.substr(0, object.length - 1);
	} else {
		return "";
	}
}

/**拆分成数组*/
function getFiledArray(obj) {
	var arr = "";
	if (obj.indexOf('&') != -1) {
		arr = obj.split("&");
		return arr;
	} else if (obj.indexOf(',') != -1) {
		arr = obj.split(",") ;
		return arr;
	} else {
		return obj.split(" ") ;
	}
}

/**返回字段改变时，同步更新展示字段、维护字段、条件字段*/
function synField(){
	var viewReturnField = getFiledArray($("#VIEW_RETURN_FIELD").val());
	var viewDisplayField = getFiledArray($("#VIEW_DISPLAY_FIELD").val());
	var viewUpdateField = getFiledArray($("#VIEW_UPDATE_FIELD").val());
	var viewMaintainField = getFiledArray($("#VIEW_MAINTAIN_FIELD").val());
	var viewQueryField = getFiledArray($("#VIEW_QUERY_FIELD").val());
	var displayFieldTemp = "" ; 
	var maintainFieldTemp = "" ;
	var updateFieldTemp = "" ;
	var queryFieldTemp = "" ; 
	for(var i = 0 ; i < viewReturnField.length ; i++){
		var temp = "" ; 
		if(viewReturnField[i].indexOf(".") != -1){
			temp = viewReturnField[i].substr(viewReturnField[i].indexOf(".")+1) ; 
		}else{
			temp = viewReturnField[i] ; 
		}
		
		if(viewDisplayField.indexOf(temp) > -1){
			displayFieldTemp += temp + "&" ; 
		}
		if(viewUpdateField.indexOf(temp) > -1){
			updateFieldTemp += temp + "," ; 
		}
		if(viewMaintainField.indexOf(temp) > -1){
			maintainFieldTemp += temp + "," ; 
		}
		if(viewQueryField.indexOf(temp) > -1){
			queryFieldTemp += temp + "," ; 
		}
	}
	$("#VIEW_DISPLAY_FIELD").val(subStringField(displayFieldTemp));
	$("#VIEW_UPDATE_FIELD").val(subStringField(updateFieldTemp));
	$("#VIEW_MAINTAIN_FIELD").val(subStringField(maintainFieldTemp));
	$("#VIEW_QUERY_FIELD").val(subStringField(queryFieldTemp));
}


    </script>
</html>
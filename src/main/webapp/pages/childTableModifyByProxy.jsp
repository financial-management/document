<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>子表模板</title>
</head>
<body>

	<form class="form-horizontal">
		<div class="panel panel-primary">

			<div class="panel-body" id="bulidTable">
				<div class="panel panel-primary">
					<div class="panel-heading" id='pageName'>子表模板</div>

					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-heading">查询条件</div>
							<div class="panel-body" id="queryParam"></div>
						</div>

						<div id="toolbar">
							<button type="button" class="btn btn-info" onclick="window.history.back(-1);">
								<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回主表
							</button>
							<div id='button_div'></div>
						</div>

						<table id="table"></table>

					</div>
				</div>
			</div>

			<div class="panel-body" id="bulidPage" style="display: none">
				<div class="panel panel-primary">
					<div class="panel-heading">新增</div>
					<div class="panel-body">

						<div>
							<button type="button" class="btn btn-default"
								onclick="save(this)">
								<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
							</button>
							<button type="button" class="btn btn-default"
								onclick="back(this)">
								<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回
							</button>
						</div>

						<div class="panel-body" id="insPage">
							<!--主表主键  用于 新增、修改-->
							<input type='hidden' name="ParentPK">
							<input type='hidden' id="META_DETAIL_ID" name="META_DETAIL_ID">
						</div>

					</div>
				</div>
			</div>

		</div>
	</form>

	<!-- 主表主键 用于查询 -->
	<input type="hidden" name="ParentPK_Query" />

</body>
<jsp:include page="../include/public.jsp"></jsp:include>

<script type="text/javascript">
	
	$(function() {
		bulidPage(true,true,true,true);
		
	});
	
	//主表主键在子表字段名
	var ParentPKField = '<%=request.getParameter("ParentPKField")%>';
	//主表主键值
	var ParentPKValue = '<%=request.getParameter("ParentPKValue")%>';
	var meta_id ='<%=request.getParameter("META_ID")%>';
	$('input[name="ParentPK"]').attr('id',ParentPKField);
	$('input[name="ParentPK_Query"]').attr('id','SEARCH-'+ParentPKField);
	$('input[name^="ParentPK"]').val(ParentPKValue);

	//重写back方法
	function back(t){
		tog(t);
		//排除主表主键
		$inspage.find('[id]').not('#'+ParentPKField).val("");
		$("#ins_or_up_buttontoken").val("");
	}
	
	var columns =['FIELD_CODE','FIELD_NAME','FIELD_TYPE','NULL_FLAG','KEY_FLAG','SORT','INPUT_TYPE','INPUT_FORMART','INPUT_HTML'];
	
	function ref_write_json(rejsonArray){
		
		if(rejsonArray.length==1){
			var jsonObject =rejsonArray[0];
			var prefix = "#";
			if(globalRef_pageType == 'SELECT'){
				prefix += "SEARCH-";
				}
			for(var i in columns){
				var mark =prefix+columns[i];
				$(mark).val(jsonObject[columns[i]]);
			}
			$("#META_DETAIL_ID").val(jsonObject["ID"]);
		}
	}
	
	function ref_query_param(u){
		return "&SEARCH-METADATA_ID="+'<%=request.getParameter("META_ID")%>';
		
	}
</script>

</html>
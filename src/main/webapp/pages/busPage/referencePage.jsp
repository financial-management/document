<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>动态配置列表</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendor/bootstrap-table/src/bootstrap-table.css">
<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/vendor/bootstrap-table/src/bootstrap-table.js"></script>
<script src="<%=request.getContextPath()%>/pages/js/checkbootTable.js"></script>
<link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/vendor/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
</head>
<body>
	<form>

		<div class="panel-body">
			<div class="panel panel-default">
				<div class="panel-heading">查询条件</div>
				<div class="panel-body" id="reference_query_param"></div>
			</div>
			<div id="reference_toolbar">
				<button id="confirmButton" type="button" class="btn btn-primary"
					data-dismiss="modal" onclick="Rconfirm()">确定</button>
				<button id="queryButton" type="button" class="btn btn-default"
					onclick="queryReferenceTable()">查询</button>
			</div>
			<table id="reference_table"></table>
		</div>
	</form>
	<script>
		var $reference_table = $('#reference_table');
		var reference_oTable = new TableInit("reference_toolbar");
		var url = context+'/reference?cmd=';
		$(function() {
			var dataSourceCode =$("#ReferenceDataSourceCode").val();
			bulidReferenceTable(dataSourceCode);
		});
		
		
		function bulidReferenceTable(dataSourceCode){
			reference_oTable.initQueryParam(url+"queryParam"+findReferencePageParam(dataSourceCode,true),$("#reference_query_param"));
			reference_oTable.initCols($reference_table, url+"queryColumns" + findReferencePageParam(dataSourceCode,true), url+"init" + findReferencePageParam(dataSourceCode,true));
		}
		

		function queryReferenceTable() {
			var dataSourceCode =$("#ReferenceDataSourceCode").val();
			reference_oTable.queryTable($reference_table, url+"init" + findReferencePageParam(dataSourceCode,false));
		}
		
		function showConfig() {
			$("#showConfig").show(500);
		}

		function findReferencePageParam(dataSourceCode,isInit) {
			var isRadio =$("#ReferenceIsRadio").val();
			var pageParam = "&dataSourceCode="+dataSourceCode+"&isRadio="+isRadio;
			
			if(isInit){
				pageParam=pageParam+findExtParam(dataSourceCode);
			}else{
				//pageParam=pageParam+findExtParam(dataSourceCode);
				pageParam=pageParam+bulidPageParam()+findExtParam(dataSourceCode);
			}
			
			
			
			return encodeURI(pageParam);
		}
		
		function bulidPageParam(){
			
			var pageParam ="";
			//组装查询参数
			var $params;
			$params = $("#reference_query_param").find('[id^=SEARCH-]');
			$params.each(function() {
				if($(this).val().length > 0){
				  	pageParam += "&"+$(this).attr("id")+"="+$(this).val();
				  	}
			});
			return pageParam;
			
		}
		
		function findExtParam(u){
			var ext_query_param="";
			if(typeof(ref_query_param) == "function"){
				ext_query_param=ref_query_param(u);
			}
			return ext_query_param;
		}
		

		function Rconfirm(){
			clearSelParam();
			var rejsonArray =reference_oTable.bootMethod($reference_table,"getSelections");
			var isRun=true;
			if(typeof(ref_write_json) == "function"){
				isRun=ref_write_json(rejsonArray);
			}
			if(isRun){
				var mappings =$("#ReferenceMapping").val();
				var errorMessage='';
				mapping_array=mappings.split(";");
				
				for(var i=0;i<mapping_array.length;i++){
					colMessage =mapping_array[i].split(":");
					var write_message ='';
					if(!isWriteNull(rejsonArray[0][colMessage[0]])){
						for(var index in rejsonArray){
							if(rejsonArray[index][colMessage[0]].length>0){
								write_message=write_message+rejsonArray[index][colMessage[0]]+",";
							}
						}
						if(write_message.length>0){
							write_message=write_message.substr(0,write_message.length-1);
						}
					}else{
						errorMessage=errorMessage+"参选页面，没有["+colMessage[0]+"]字段的信息</br>";
					}
					if(globalRef_pageType == 'SELECT'){
						colMessage[1] = "SEARCH-" + colMessage[1];
						}
					$write =$("div> input[id=\'"+colMessage[1]+"\']");
					// if(!isWriteNull($write.val())){
						// if(write_message.length>0){
							// write_col($write,write_message,colMessage[1]);
						// }
					// }else{
						// errorMessage=errorMessage+"回写页面，没有["+colMessage[1]+"]字段的信息</br>";
					// }
					// 				
					if(write_message.length>0){
						write_col($write,write_message,colMessage[1]);
					}
				}
				
				if(errorMessage.length>0){
					errorMessage="<font color='red' >"+errorMessage+"</font>";
					reference_oTable.showModal("回填情况",errorMessage);
				}else{
					write_end();
				}
			}else{
				write_end();
			}
			
			
		}
		
		
		function write_col($write,write_message,colName){
			if(typeof(ref_write) == "function"){
				ref_write($write,write_message,colName);
			}else{
				if($write.length>0){
					$write.val(write_message);
				}else{
					$("#"+colName).val(write_message);
				}
				
				
			}
		}
		
		function write_end(){
			
			if(typeof(ref_end) == "function"){
				ref_end();
			}
		}
		
		
		function isWriteNull(variable){
			if (variable != null && variable != undefined &&  variable != 'undefined' ) {
				return false;
			}else{
				return true;
			}
			
		}
		
		
		function findCheckMessage(){
			var message ="table:"+JSON.stringify(oTable.bootMethod($left_table,"getSelections"))+"   table1:"+JSON.stringify(oTable.bootMethod($rigth_table,"getSelections"));
			oTable.showModal('modal',message);
		}
		
		function clearSelParam(){
			$("#reference_query_param").find("[id^=SEARCH-]").val("");
		}
    	

	</script>
</body>
</html>
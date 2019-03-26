<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>单表模板</title>
	</head>
	<body>
	
	
	<form class="form-horizontal">
		<div class="panel panel-primary">
				
				<div class="panel-body" id="bulidPage">
					<div class="panel panel-primary">
						<div class="panel-heading">
							新增
						</div>
						<div class="panel-body">
							
							<div>
								<button type="button" class="btn btn-default" onclick="save(this)">
									<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
								</button>
								<button type="button" class="btn btn-default" onclick="back(this)">
									<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回
								</button>
							</div>
							<!-- 维护页面 -->
							<div class="panel-body" id="insPage">
								<!--主表主键  用于 新增、修改-->
								<input type='hidden' name="subCode">
							</div>
								<input type='hidden' name="ParentPK">
						
						</div>
					</div>
				</div>
				
			</div>		
	</form>
		<!-- 缓存参数 -->
		<!-- 是否为修改页面 -->
		</body>
	<jsp:include page="../include/public.jsp"></jsp:include>
	<script type="text/javascript">
	  $(function() {
			    var mainPageParam =pageParamFormat(ParentId +" ="+ParentPKValue);
				bulidMaintainPage($("#insPage"),_dataSourceCode);
				
				$("#PARENT_ID").attr("readonly","readonly");
				
				$("#PARENT_ID").val(ParentPKValue);
				
				var Request = new Object(); 
				Request = GetRequest();
				var subCode = Request['subCode'];
				$("#TOTAL_CODE").attr("value",subCode);
				$("#TOTAL_CODE").attr("readonly","readonly");
				$("#GROUP_TYPE").val(2);
				$("#GROUP_TYPE").attr("disabled","true");
	  });
	  
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
	
	function save(t){
		setReadonlyByDiv($("#insPage input[type='text']"));
		var jsonData="";
		jsonData+="{";
		$("#insPage input[type='text']").each(function(index,value){
			jsonData+="'"+$(this).attr("name")+"':'"+$(this).val()+"'";
			if(index!=$("#insPage input[type='text']").length-1)
				jsonData+=",";
		});
		
		$("#insPage select").each(function(index,value){
			jsonData+=",";
			jsonData+="'"+$(this).attr("name")+"':'"+$(this).val()+"'";
		});
		jsonData+="}";
		$.ajax({
			type:"post",
			dataType:"json",
			data:jsonData,
			url:"/document/documentFolder/addPersonFolder?token="+token,
			contentType:"application/json",
			success:function(data){
				console.log(data);
				if(data.status=="success"){
	
					var message ="添加成功";
					oTable.showModal('提示', message);
					window.parent.loadJstree($("#PARENT_ID").val());
				}else{
					oTable.showModal('提示', data.msg);
				}
			},
			error:function(){
				var message ="添加出错，请联系管理员";
				oTable.showModal('提示', message);
			}
		});
		
	}
	
	
	//重写back方法
	function back(t){
		window.history.back(-1);
	}
	</script>
</html>
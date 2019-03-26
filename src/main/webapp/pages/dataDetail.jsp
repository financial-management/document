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
				
				<div class="panel-body" id="bulidTable">
					<div class="panel panel-primary">
						<div class="panel-heading" id ='pageName'>
							菜单管理
						</div>
						
						<div class="panel-body">
							<div class="panel panel-default">
								<!-- 
								<div>
									<button type="button" class="btn btn-default" onclick="save(this)">
										<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
									</button>
									<button type="button" class="btn btn-default" onclick="back(this)">
										<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回
									</button>
								</div> -->
								<div class="panel-heading" >
									信息
								</div>
								<!-- 维护页面-->
								<div class="panel-body" id="superInsertPage">			
								</div>
							</div>
							
							<div id="toolbar">
								<!-- 按钮页面 -->
								<!-- <button type="button" class="btn btn-info" onclick="window.history.back(-1);">
										<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回主表
								</button> -->
								<div id='button_div' buttonType='super'></div>
								<div id='button_child_div' buttonType='child'></div>
								<div id='button_child_div_001' >
								</div>
							</div>
							<!-- 列表页面 -->
							<table id="table"></table>

						</div>
					</div>
				</div>

				<div class="panel-body" id="bulidPage" style="display: none">
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
								<input type='hidden' name="ParentPK">
							</div>
						
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
				var Request = new Object(); 
				Request = GetRequest();
				var isReadonly = Request['isReadonly'];
			    /*if(isReadonly=='1'){
			    	mainPageParam=mainPageParam+"&showType=text";
			    }*/
				bulidMaintainPage($("#superInsertPage"),dataSourceCode,mainPageParam);
				bulidMaintainPage($("#insPage"),_dataSourceCode,mainPageParam);
				bulidButton($("#button_div"),_dataSourceCode,'');
				//bulidButton($("#button_child_div"),_dataSourceCode,'');
				//bulidButton($("#button_child_div_001"),_dataSourceCode,'');
				//bulidListPage($("#table"),_dataSourceCode,_query_param);
				
				//alert(isReadonly);
				if(isReadonly=='1'){
					//setReadonlyByDiv($("#superInsertPage input[type='text']"));
					$("#superInsertPage").find("[id]").each(function(){
						$(this).attr("disabled",true);
					});
				}else if(isReadonly=='0'){
					$("#superInsertPage").find("[id='TOTAL_CODE']").attr("disabled",true);
				}
	  });
		
	//重写back方法
	function back(t){
		tog(t);
		//排除主表主键
		$inspage.find('[id]').not('#'+ParentPKField).val("");
		$("#ins_or_up_buttontoken").val("");
	}
	</script>
</html>
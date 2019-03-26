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
							单表模板
						</div>
						
						<div class="panel-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									查询条件
								</div>
								<!-- 查询条件页面 -->
								<div class="panel-body" id="queryParam">			
								</div>
							</div>
							
							<div id="toolbar">
								<!-- 按钮页面 -->
								<div id='button_div'>
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
							<div class="alert alert-info" id="message" style="display: none"></div>
							
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
								
								<input type='hidden' id="META_ID" name="META_ID">
							</div>
						
						</div>
					</div>
				</div>
				
			</div>
		</form>
		
	</body>
	<jsp:include page="../include/public.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
				bulidPage(true,true,true,true);
		});
	</script>
</html>
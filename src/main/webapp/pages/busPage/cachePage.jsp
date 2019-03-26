<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>缓存管理</title>
		<link rel="stylesheet" href="../../vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="../../vendor/bootstrap-table/src/bootstrap-table.css">
		<script src="../../vendor/jquery/jquery.min.js"></script>
		<script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="../../vendor/bootstrap-table/src/bootstrap-table.js"></script>
		<script src="../js/bootTable.js"></script>
	</head>

	<body>

		<form class="form-horizontal">
			<div class="panel panel-warning">
				
				<div class="panel-body" id="bulidTable">
					<div class="panel panel-danger">
						<div class="panel-heading">
							缓存管理
						</div>
							
						<div id="toolbar">
							<div class="form-group">
								<div class='col-sm-3'>
									<button type="button" class="btn-lg btn-warning btn-block" onclick="cacheClear(1)">
										<span class="glyphicon glyphicon-trash" aria-hidden="true">清空数据源缓存
									</button>
								</div>
								<div class='col-sm-3'>
									<button type="button" class="btn-lg btn-warning btn-block" onclick="cacheClear(2)">
										<span class="glyphicon glyphicon-trash" aria-hidden="true">清空元数据缓存
									</button>
								</div>
								<div class='col-sm-3'>
									<button type="button" class="btn-lg btn-warning btn-block" onclick="cacheClear(3)">
										<span class="glyphicon glyphicon-trash" aria-hidden="true">清空数据字典缓存
									</button>
								</div>
								<div class='col-sm-3'>
									<button type="button" class="btn-lg btn-warning btn-block" onclick="cacheClear(4)">
										<span class="glyphicon glyphicon-trash" aria-hidden="true">清空代理页面缓存
									</button>
								</div>
							</div>
							<button type="button" class="btn-lg btn-danger btn-block" onclick="cacheClear(0)">
								<span class="glyphicon glyphicon-off" aria-hidden="true">清空所有缓存
							</button>
						</div>
					</div>
				</div>
			</div>
		</form>

		<script>
			function cacheClear(cacheEnum) {
				var context = '<%=request.getContextPath()%>';
				$.ajax({
					url:context+'/base?cmd=cacheClear',
					data:{'cacheEnum':cacheEnum},
					type:'POST',
					dataType:'json',
					async:false,
					success:function(data){
						message = data["message"];
						alert(message);
						return false;},
					error:function(data){
						alert('请求失败');
						}
				}); 
			}
		</script>
		
	</body>
</html>
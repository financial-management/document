<%@page import="com.yonyou.util.wsystem.service.ORG"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>系统管理</title>
		<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
		<script src="../vendor/jquery/jquery.min.js"></script>
		<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="../vendor/bootstrap-table/src/bootstrap-table.js"></script>
		<script src="js/bootTable.js"></script>
		<script src="../vendor/bootstrap/js/bootstrap-treeview.js"></script>
		<script src="../vendor/bootstrap/js/tree.js"></script>
		<script id="buttonjs" src=""></script>
	</head>
    
	<body>

		<form>
			<div class="page-header">
				<h1>系统管理<small>System Manager,登录人:</small></h1>
			</div>					
			<div class="col-md-2">
				<ul id="myTab1" class="nav nav-pills nav-stacked">
			          <li >
			          	<div id="treeview1" ></div>
			          </li>
		         </ul>
			</div>
			<div class="col-md-10">
				<div class="tab-content">
					<div class="tab-pane fade in active" id='message'>
							<iframe id="menucontent" src="default.html" scrolling="yes" frameborder="0"  width="100%" height="2000"></iframe>
					</div>
				</div>
			</div>
			
		</form>

	</body>
		<script type="text/javascript">
           function  onclickmenu(str){
           	 $("#menucontent").attr("src",str);
           }  		
  	</script>
</html>
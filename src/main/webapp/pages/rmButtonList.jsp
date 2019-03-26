<%@ page language="java" contentType="text/html; charset=UTF-8" import="
com.yonyou.business.entity.TokenUtil,
com.yonyou.business.entity.TokenEntity"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>æé®ç®¡ç</title>
		<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
		<script src="../vendor/jquery/jquery.min.js"></script>
		<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="../vendor/bootstrap-table/src/bootstrap-table.js"></script>
		<script src="js/bootTable.js"></script>
	</head>
	<body>
		<form>
			<div class="col-md-12">
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#metaDate" data-toggle="tab">按钮菜单关联</a>
					</li>
					<li>
						<a href="#dataSource" data-toggle="tab">按钮管理</a>
					</li>
				</ul>
			</div>
		</br>
			<div class="col-md-12">
				<div class="tab-content">
					<div class="tab-pane fade in active" id='metaDate'>
						<iframe src='rmButtonRelationMenu.jsp?pageCode=RM_BUTTON_RELATION_MENU&pageName=按钮管理关联配置&totalCode=<%=request.getParameter("totalCode")%>&userId=<%=TokenUtil.initTokenEntity(request).USER.getId()%>&companyId=<%=TokenUtil.initTokenEntity(request).COMPANY.getCompany_id()%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
					<div class="tab-pane fade" id='dataSource'>
						<iframe src='singleTableModify.jsp?pageCode=RM_BUTTON&pageName=按钮管理&totalCode=<%=request.getParameter("totalCode")%>&userId=<%=TokenUtil.initTokenEntity(request).USER.getId()%>&companyId=<%=TokenUtil.initTokenEntity(request).COMPANY.getCompany_id()%>' scrolling="yes" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
				</div>
			</div>
			
		</form>

	</body>
	<script type="text/javascript">
		$(function() {
				$('#myTab a[href="#metaDate"]').tab('show');
			});
      function  showdiv(str){
            $('#myTab a[href="#metaDate"]').tab('show');
       } 
  	</script>
</html>
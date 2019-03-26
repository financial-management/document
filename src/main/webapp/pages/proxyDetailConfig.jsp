<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>页面代理明细</title>
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
						<a href="#util" data-toggle="tab">公共代理</a>
					</li>
					<li >
						<a href="#select" data-toggle="tab">查询代理</a>
					</li>
					<li>
						<a href="#list" data-toggle="tab">列表代理</a>
					</li>
					<li >
						<a href="#maint" data-toggle="tab">维护代理</a>
					</li>
					<li >
						<a href="#update" data-toggle="tab">修改代理</a>
					</li>
				</ul>
			</div>
		</br>
			<div class="col-md-12">

				<div class="tab-content">

					<div class="tab-pane fade in active" id='util'>
						<iframe src='childTableModifyByProxy.jsp?pageCode=CD_PROXY_UTIL_MESSAGE&pageName=公共代理列表&ParentPKField=PAGE_ID&ParentPKValue=<%=request.getParameter("ParentPKValue")%>&META_ID=<%=request.getParameter("META_ID")%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
					<div class="tab-pane fade" id='select'>
						<iframe src='childTableModifyByProxy.jsp?pageCode=CD_PROXY_SELECT_PAGE&pageName=查询页面代理列表&ParentPKField=PAGE_ID&ParentPKValue=<%=request.getParameter("ParentPKValue")%>&META_ID=<%=request.getParameter("META_ID")%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
					<div class="tab-pane fade" id='list'>
						<iframe src='childTableModifyByProxy.jsp?pageCode=CD_PROXY_LIST_PAGE&pageName=列表页面代理列表&ParentPKField=PAGE_ID&ParentPKValue=<%=request.getParameter("ParentPKValue")%>&META_ID=<%=request.getParameter("META_ID")%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
					<div class="tab-pane fade " id='maint'>
						<iframe src='childTableModifyByProxy.jsp?pageCode=CD_PROXY_MAIN_PAGE&pageName=维护页面代理列表&ParentPKField=PAGE_ID&ParentPKValue=<%=request.getParameter("ParentPKValue")%>&META_ID=<%=request.getParameter("META_ID")%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>
					<div class="tab-pane fade " id='update'>
						<iframe src='childTableModifyByProxy.jsp?pageCode=CD_PROXY_UPDATE_PAGE&pageName=修改页面代理列表&ParentPKField=PAGE_ID&ParentPKValue=<%=request.getParameter("ParentPKValue")%>&META_ID=<%=request.getParameter("META_ID")%>' scrolling="no" frameborder="0"  width="99%" height="1000"></iframe>
					</div>

				</div>
			</div>
			
		</form>

	</body>
	<script type="text/javascript">
		var ParentPKValue = '<%=request.getParameter("ParentPKValue")%>';
  	</script>
</html>
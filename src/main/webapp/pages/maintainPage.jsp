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
						<div class="panel-heading" id ='pageName'>
							单表模板
						</div>
						<div class="panel-body">
							<div class="panel-body" id="insPage"></div>
						</div>
						
						<div id='button_div'></div>
					</div>
				</div>
				
			</div>
		</form>
		<!-- 是否为修改页面 -->
		<input type="hidden" id="ins_or_up_buttontoken"/>
		<input type="hidden" id="query_buttontoken"/>
		
	</body>
	<jsp:include page="../include/public.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
				bulidPage(true,false,false,true);
		});
	</script>
</html>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>子表模板</title>
</head>
<%
	String [] pageCodes = request.getParameter("pageCode").split(",");
    String [] pageNames = request.getParameter("pageName").split(",");
    String token =request.getParameter("token");
    String ParentPKField =request.getParameter("ParentPKField");
	//主表主键值
	String ParentPKValue =request.getParameter("ParentPKValue");
    String initTableParam =URLEncoder.encode("SEARCH-"+ParentPKField+"="+ParentPKValue);
    
    
%>
<body>

	<form class="form-horizontal">
		<div class="panel panel-primary">

			<div class="panel-body" id="bulidTable">
				<div class="panel panel-primary">
					<div class="panel-heading" id='pageName'>详情</div>

					<div class="panel-body">
						
						<button type="button" class="btn btn-info" onclick="window.history.back(-1);">
								<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回主表
						</button>
					</div>
				</div>
			</div>

			<div class="panel-body" id="bulidPage" >
			    
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#left" data-toggle="tab"><%=pageNames[0] %></a>
					</li>
					<li >
						<a href="#rigth" data-toggle="tab"><%=pageNames[1] %></a>
					</li>
				</ul>
				
				<div class="col-md-12">
					<div class="tab-content">
						<div class="tab-pane fade in active" id="left">
					    	<iframe id="leftPage"  width="100%"  scrolling="no" frameborder="0"  height="650px" src="singleTableModify.jsp?pageCode=<%= pageCodes[0] %>&pageName=<%=pageNames[0]%>&initTableParam=<%=initTableParam%>&token=<%=token%>"></iframe>
					    </div>
						<div class="tab-pane fade" id="rigth">
							<iframe id="rigthPage"  width="100%" scrolling="no" frameborder="0"  height="650px" src="singleTableModify.jsp?pageCode=<%= pageCodes[1] %>&pageName=<%=pageNames[1]%>&initTableParam=<%=initTableParam %>&token=<%=token%>"></iframe>
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

	
	//主表主键在子表字段名
	var ParentId = '<%=ParentPKField %>';
	//主表主键值
	var ParentValue = '<%=ParentPKValue %>';
	

</script>

</html>
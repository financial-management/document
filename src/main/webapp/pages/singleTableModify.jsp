<%@page import="com.yonyou.util.theme.ThemePath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>单表模板</title>
	</head>
	
	<%

		String ContextPath = request.getContextPath();
		String pages="/pages/";
		String pagePath =ThemePath.findPath(request, ThemePath.SUB_SYSTEM_SINGLE_PAGE).replace(ContextPath+pages, "");
	%>
	<body>
		<jsp:forward page="<%=pagePath%>"></jsp:forward>
	</body>
</html>
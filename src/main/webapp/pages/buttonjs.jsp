<%@ page language="java" import="com.yonyou.web.CreateButton,
com.yonyou.business.entity.TokenUtil,
com.yonyou.business.entity.TokenEntity,java.io.Serializable" %>
<script type="text/javascript">
<%
String userId 	= request.getParameter("userId") ; 
//String companyId= TokenUtil.initTokenEntity(request).COMPANY.getCompany_id();
String companyId=  request.getParameter("companyId") ; 
String muneCode = request.getParameter("totalCode") ; 
String pageCode = request.getParameter("pageCode") ; 
if("".equals(companyId)|| null ==companyId){
	companyId = TokenUtil.initTokenEntity(request).COMPANY.getCompany_id();
}
if("".equals(userId)|| null ==userId){
	userId = TokenUtil.initTokenEntity(request).USER.getId();
}
//<%=new CreateButton().getButtonInfo(userId,companyId,muneCode,pageCode)
%>
</script> 		
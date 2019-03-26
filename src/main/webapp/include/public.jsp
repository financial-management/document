
<!-- bootstrapIE8è¡¥ä¸ -->
<%@page import="com.yonyou.util.theme.ThemePath"%>
<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/html5.js"></script>
<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/respond.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
  <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap-datetimepicker.min.css" media="screen">
<!-- <link rel="stylesheet" href="../vendor/bootstrap-fileinput/css/fileinput.min.css"  media="all"> -->
<link rel="stylesheet" href="<%=ThemePath.findPath(request, ThemePath.SUB_SYSTEM_CSS)%>">
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../vendor/bootstrap-table/src/bootstrap-table.js"></script>
<!-- <script src="../vendor/bootstrap-fileinput/js/fileinput.js"></script> -->
<!-- <script src="../vendor/bootstrap-fileinput/js/locales/zh.js"></script> -->
<script src="../vendor/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="../vendor/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="../pages/js/reference.js"></script>
<script type="text/javascript">
var isBuildButtonByFile = true ; //æ¯å¦å¼å¯jsæä»¶è°è¯æ¨¡å¼,true :jsæä»¶è·åæé®  ï¼ false:æ°æ®åºè·åå·²æææé®ï¼
var context = '<%=request.getContextPath()%>';
var dataSourceCode ='<%=request.getParameter("pageCode")%>';
var pageName ='<%=request.getParameter("pageName")%>';
var menuCode ='<%=request.getParameter("menuCode")!=null?request.getParameter("menuCode"):""%>';
var rolecode='';
var corpcode='';
var token='<%=request.getParameter("token")!=null?request.getParameter("token"):""%>';

var isNewModel = false;

var _dataSourceCode='<%= request.getParameter("_dataSourceCode")%>';
var ParentId ='ID';
var ParentPKField='<%=request.getParameter("ParentPKField")!=null?request.getParameter("ParentPKField"):""%>';
var ParentPKValue='<%=request.getParameter("ParentPKValue")!=null?request.getParameter("ParentPKValue"):""%>';
var _query_param='';
if(ParentPKField!=null&&ParentPKField.length>0){
	_query_param =pageParamFormat(ParentPKField+" = "+ParentPKValue);
}	
var query_buttonToken ='';
$('input[name="ParentPK"]').attr('id',ParentPKField).val(ParentPKValue);
function pageParamFormat(p){
	if(p!=null&p.length>0){
		return "&pageParam="+"'"+p+"'";
	}else{
		return "";
	}
}

var cacheTableJsonArray =null;

var initTableParam='<%=request.getParameter("initTableParam")!=null?request.getParameter("initTableParam").replaceAll(",", "&"):""%>';
if(initTableParam!=null&&initTableParam.length>0){
	initTableParam="&"+initTableParam;
}else{
	initTableParam ="";
}

</script>
<script src="../js/public.js"></script>
<script src="../js/properties.js"></script>
<script src="../js/crud.js"></script>
<script src="../js/bootTable.js"></script>
<script src="../js/common.js"></script>
<script src="../js/initPage.js"></script>
<script src="../js/buttonJs.js"></script>
<script src="../js/validation.js"></script>

<input type="hidden" id="ins_or_up_buttontoken"/>
<input type="hidden" id="query_buttontoken" value="query"/>
<input type="hidden" id="cache_dataSourceCode"/>
<input type="hidden" id="directoryid" value="<%=request.getParameter("ParentPKValue")!=null?request.getParameter("ParentPKValue"):""%>"/>

<link href="../vendor/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="../vendor/Bootstrap-icon-picker/css/icon-picker.css" rel="stylesheet" /> 
<script src="../vendor/Bootstrap-icon-picker/js/iconPicker.js"></script>

<script src="../pages/js/multiselectDemo.js"></script>

<link rel="stylesheet" href="../pages/vailidator/css/bootstrapValidator.css"/>
<script type="text/javascript" src="../pages/vailidator/js/bootstrapValidator.js"></script>
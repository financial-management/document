<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<%
	
  String ContextPath =request.getContextPath();

%>
<head>
 <script src="<%= ContextPath%>/echarts/echarts.min.js"></script>
 <script src="<%= ContextPath%>/echarts/theme/dark.js"></script>
  <script src="<%= ContextPath%>/echarts/echart.pubinit.js"></script>
<meta charset="utf-8">
<title>报表DEMO</title>
</head>

<body>
	
	<div id="main" style="width: 99%;height:600px;"></div>
    <script type="text/javascript">
    	
    	function setParntHeigth(){
			 if(parent['setHeigth']){
			 	 alert($(document.body).height());
				 parent['setHeigth']($(document.body).height());
			 }
		}
    	
        // 基于准备好的dom，初始化echarts实例
		echartPub.title="112233";
		echartPub.type='bar';
		echartPub.name="11";
		echartPub.legend=["11"];
		echartPub.x=["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"];
		echartPub.data= [
                    {name: 'A', value: 1212},
                    {name: 'B', value: 2323},
                    {name: 'C', value: 1919}
                ];
        // 使用刚指定的配置项和数据显示图表。
		setParntHeigth();
    </script>
	<script src="<%= ContextPath %>/echarts/echarts.public.js"></script>
</body>

</html>

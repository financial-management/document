<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
			<title>父页面</title>
<!-- <link href="../css/fileinput.min.css" rel="stylesheet">
<link href="../css/fileinput-rtl.min.css" rel="stylesheet"> -->
	</head>
	<body>

		<form class="form-horizontal">
			<div class="panel panel-primary">
				
				<div class="panel-body" id="bulidTable">
					<div class="panel panel-primary">
						<div class="panel-heading" id ='pageName'>
							文件列表
						</div>
						
							<div id="bill_date_and_status"></div>
								<!-- 查询条件页面 -->
							<div class="panel-body" id="queryParam" style="display: none;">			
							</div>
							
							
							<div class="input-box-toggle" onclick="moreToggle()">
								<span class="caret"></span>更多搜索条件
							</div>
							<div id='button_div'> </div>
							<!-- 列表页面 -->
							<table id="table"></table>

					</div>
				</div>

				<div class="panel-body" id="bulidPage" style="display: none">
					<div class="panel panel-primary">
						<div class="panel-heading">
							新增
						</div>
						<div class="panel-body">
							
							<div>
								<button type="button" class="btn btn-default" onclick="save(this)">
									<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
								</button>
								<button type="button" class="btn btn-default" onclick="back(this)">
									<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回
								</button>
							</div>
							<!-- 维护页面 -->
							<div class="panel-body" id="insPage"></div>
						
						</div>
					</div>
				</div>
				
			</div>
			<input type="hidden" id="isNewStyle" value="1" />
			<div id="ymquery" style="display: none;" class="button-group sel" buttontoken="query" onclick="queryTable(this)"> <span class="img"></span> <span>查询</span> </div>
		</form>
		<!-- 是否为修改页面 -->
 <script type="text/javascript">
 var fileuploadmaxSize = 100;//文件上传大小限制
 
 	function submit_before(){
		return toVaild();
	}
 
 	function toVaild() {
		var val = document.getElementById("inputfile").value;
		if (val != "") {
			var fsize = $("#inputfile")[0].files[0].size;
		    //var filesize = (fsize / 1024).toFixed(2);//kb
			var filesize = (fsize / (1024*1024) ).toFixed(2);//kb
			var filesizeMB = (fsize / (1024*1024) ).toFixed(2)+'MB';//kb
			//console.info((size/(1024*1024)).toFixed(2)+'MB');//mb
			//alert("校验成功，之后进行提交");
			if (filesize<=fileuploadmaxSize) {
				return true;
			}else{
				alert("上传附件太大!请传输100MB及以下大小的文件");
				return false;
			}
		} else {
			alert("未选择文件，不进行提交");
			return false;
		}
	}
	
//不使用组件上传附件
function uploadAffixDid(){

           var code="<%=request.getParameter("ParentPKValue")%>";
           var batchNo="";
           //alert(code);

			$.ajax({
				type: "POST",
				url: "/document/base?cmd=uploadAffix&did=" + $("#did").val() +"&token="+ token+"&batchNo="+batchNo+"&code="+code,
				data: new FormData($("#form_file")[0]),
				dataType: "json",
				async: false,  
				cache: false,  
				contentType: false,  
				processData: false,
				success: function (data) {
					//console.info(data)
					//alert("上传附件成功")
					oTable.showModal('操作提示', "上传附件成功");
					$("#inputfile").val("");
					$("#modalcolose").click();
				},
				error: function(data) {
					alert("上传附件失败，请联系管理员"+data);
					$("#inputfile").val("");
					$("#modalcolose").click();
				}
			});
}
	
function iframeF5(){
//var aurl = window.parent.document.referrer;
//console.info("付url{}"+aurl)
//var url = window.location.href;
//console.info("本url{}"+url)
//alert(test);
//window.location.reload();
//var a=document.getElementById('menucontent');
//window.parent.frames[1].location.reload(); 
//document.frames('#menucontent').location.reload();
//document.getElementById('menucontent').contentWindow.location.reload(true);
//$("menucontent").window.location.reload()
//	var url = "/pages/documentList.jsp?token="+token;
//	console.info("url{}"+url)
//	url = context + url;
//	console.info("context + url{}"+url)
//	dataSourceCode="DOC_DOCUMENT_SELECT";
//	window.location.href = url;
queryTableByDataSourceCode("",dataSourceCode);
}


function submit_(){
	if(toVaild()){
		uploadAffixDid();
		queryTableByDataSourceCode("",dataSourceCode);
	}else{
	$("#inputfile").val("");
	}
}
 </script>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<form enctype="multipart/form-data" action="#" id="form_file"><!--  onsubmit="return submit_before()" -->
  <input type="hidden" name="did" id="did"/>
  <input type="hidden" name="token" id="token"/>
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header" style="height: 40px;">
      	<div>
	        <strong class="modal-title" id="exampleModalLabel" style="float: left;">文件上传</strong>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
        </div>
      </div>
      <div class="modal-body">
        <input type="file" name="files" id="inputfile"/>
      </div>
      <div class="modal-footer">
        <button id="modalcolose" type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" title="upload" onclick="submit_()" >上传</button><!-- onclick="submit_()" -->
      </div>
    </div>
  </div>
    </form>
</div>
	</body>



<jsp:include page="../include/listPublic.jsp"></jsp:include>
<jsp:include page="./buttonjs.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			bulidPage(true,true,true,true);
		});
		function addFolder(){
			var ParentPKValue=getQueryString("ParentPKValue");
			var url="/document/pages/addDetail_auth.jsp?pageCode=DOC_DIRECTORY_SELECT&menuCode=DEMO&_dataSourceCode=DOC_DIRECTORY_SELECT&ParentPKField=&ParentPKValue="+ParentPKValue+"&token="+token;
			window.location.href=url;
		}
		function getQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
	</script>
</html>
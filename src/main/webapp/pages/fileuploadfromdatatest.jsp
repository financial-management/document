<%@page import="com.yonyou.util.wsystem.service.ORG"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>文件上传——运用H5内置FormData对象</title>
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="../vendor/bootstrap-table/src/bootstrap-table.css">
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../vendor/bootstrap-table/src/bootstrap-table.js"></script>
<script src="js/bootTable.js"></script>
<script src="../vendor/bootstrap/js/bootstrap-treeview.js"></script>
<script src="../vendor/bootstrap/js/tree.js"></script>
<script id="buttonjs" src=""></script>
</head>
<body>

  <form id="form1" enctype="multipart/form-data" method="post" action="Upload.aspx">
    <div class="row">
      <label for="fileToUpload">Select a File to Upload</label><br />
      <input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();"/>
    </div>
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
    <div class="row">
      <input type="button" onclick="uploadFile()" value="Upload" />
    </div>
    <div id="progressNumber"></div>
  </form>

</body>
<script type="text/javascript">
var context = '<%=request.getContextPath()%>';
      function fileSelected() {
        var file = document.getElementById('fileToUpload').files[0];
        if (file) {
          var fileSize = 0;
          if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
          else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

          document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
          document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
          document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
        }
      }

      function uploadFile() {
        var fd = new FormData();
        fd.append("files", document.getElementById('fileToUpload').files[0]);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.open("POST",context+ "/base?cmd=uploadAffix&did=1");
        xhr.send(fd);
      }

      function uploadProgress(evt) {
        if (evt.lengthComputable) {
          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
          document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
        }
        else {
          document.getElementById('progressNumber').innerHTML = '无法计算';
        }
      }

      function uploadComplete(evt) {
        /* 当服务器响应后，这个事件就会被触发 */
        alert(evt.target.responseText);
      }

      function uploadFailed(evt) {
        alert("上传文件发生了错误尝试");
      }

      function uploadCanceled(evt) {
        alert("上传被用户取消或者浏览器断开连接");
      }

/*https://blog.csdn.net/q1056843325/article/details/53759963
http://localhost/document/pages/fileuploadfromdatatest.jsp
*/
  	</script>
</html>
buttonJson =[
              {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
              {name:'上传',fun:'uploadFile(this)',buttonToken:'button_button_insert'},
              {name:'下载',fun:'downFile(this)',buttonToken:'send'},
              {name:'日志查看',fun:'',buttonToken:'search'},
              {name:'删除文件',fun:'deleteFile(this)',buttonToken:'deleteFile'},
              {name:'添加目录',fun:'addFolder()',buttonToken:''}
			];


//子表  数据源编码
var _dataSourceCode='DOC_DOCUMENT_SELECT';
//父表 主键[在子表的外键字段]
var ParentPKField='';
//值
var ParentPKValue='';

function uploadFile()
{
	var did=$('#directoryid').val();
	$('#did').val(did);
	$('#exampleModal').modal('show');
}

function downFile()
{
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行操作");
		return;
	}
	var fid=JSON.parse(getSelections())[0]["ID"];
	var url ="/document/base?cmd=downloadAffix&fid="+fid;
	//url=appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue);
	window.open(url);
	
}

function deleteFile()
{
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行操作");
		return;
	}
	var fid=JSON.parse(getSelections())[0]["ID"];
	var url ="/document/base?cmd=deleteAffix&fid="+fid;
	var verButtonDelete = transToServer(url,null);
	if(verButtonDelete!="0"){
		oTable.showModal('删除失败', verButtonDelete);
		return;
	}
	var message = transToServer(findBusUrlByPublicParam(t,'',dataSourceCode),getSelections());
	oTable.showModal('提示', message);
	queryTableByDataSourceCode(t,_dataSourceCode);
	
}

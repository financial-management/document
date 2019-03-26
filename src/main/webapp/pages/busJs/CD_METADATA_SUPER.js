
buttonJson =[
              {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
              {name:'新增',fun:'tog(this)',buttonToken:'add'},
              {name:'修改',fun:'updateRow(this)',buttonToken:'update'},
              {name:'删除',fun:'delRows(this)',buttonToken:'delete'},
              {name:'查看',fun:'view(this)',buttonToken:''}
			];

//子表  数据源编码
var _dataSourceCode='CD_METADATA_DETAIL';
//父表 主键[在子表的外键字段]
var ParentPKField='METADATA_ID';
//值
var ParentPKValue='';

function view(t){
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行操作");
		return;
	}
	ParentPKValue=JSON.parse(getSelections())[0]["ID"];
	var url =context+"/pages/demoChild.jsp?pageCode="+dataSourceCode+"&menuCode=DEMO";
	url=appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue);
	window.location.href=url;
}

function appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue){

	return url+"&_dataSourceCode="+_dataSourceCode
		      +"&ParentPKField="+ParentPKField
		      +"&ParentPKValue="+ParentPKValue;
}

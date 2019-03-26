buttonJson =[
             {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
             {name:'新增',fun:'tog(this)',buttonToken:'insertWithCache'},
             {name:'修改',fun:'updateRow(this)',buttonToken:'updateWithCache'},
             {name:'删除',fun:'delRows(this)',buttonToken:'deleteWithCache'},
             {name:'页面明细',fun:'view(this)',buttonToken:'checkMeta'}
			];



function view(){
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行操作");
		return;
	}
	window.location.href=context+"/pages/proxyDetailConfig.jsp?ParentPKValue="+JSON.parse(getSelections())[0]["ID"]
		+"&META_ID="+JSON.parse(getSelections())[0]["META_ID"];
}
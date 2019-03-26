buttonJson =[
             {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
             {name:'新增',fun:'tog(this)',buttonToken:'insertWithCache'},
             {name:'修改',fun:'updateRow(this)',buttonToken:'updateWithCache'},
             {name:'删除',fun:'delRows(this)',buttonToken:'deleteWithCache'},
             {name:'元数据字段',fun:'view(this)',buttonToken:'checkMeta'},
             {name:'存储过程',fun:'tableProc(this)',buttonToken:'procedure'},
			];



function view(){
	
	//查看子表相关配置
	var pageCode = 'CD_METADATA_DETAIL';//子表数据源
	var pageName = '元数据字段列表';//列表显示名称
	var ParentPKField = 'METADATA_ID';//主表主键在子表中字段名
	
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据进行操作");
		return;
	}
	window.location.href=context+"/pages/childTableModify.jsp?pageCode="+pageCode+"&pageName="+pageName+"&ParentPKField="+ParentPKField+"&ParentPKValue="+JSON.parse(getSelections())[0]["ID"];
}
var isButton =false;
function tableProc(t){
	isButton =true;
	//checkReference('REF(RM_BUTTON,BUTTON_NAME:SEARCH-BUTTON_NAME,0)');
	checkReference(this,'REF(USER_TABLES,TABLE_NAME:SEARCH-TABLE_NAME_NAME,0)','','SELECT');
}

function ref_write_json(rejsonArray){
	if(rejsonArray.length == 0){
		oTable.showModal('modal', "请至少选择一条数据进行操作");
		return;
	}
	if(isButton){
		isButton=false;
		var tableNameValues = '';
		var buttonToken = "procedure";
		var dataSourceCode = "USER_TABLES"
		for(var i = 0;i< rejsonArray.length ; i++){
			tableNameValues += rejsonArray[i]['TABLE_NAME']+',';
		}
		tableNameValues = tableNameValues.substring(0,tableNameValues.length-1);
		var bindUrl = context+'/buttonBase?cmd=button&tableNameValues='+tableNameValues+'&buttonToken='+buttonToken+'&dataSourceCode='+dataSourceCode;
		oTable.showModal('modal', transToServer(bindUrl,JSON.stringify(rejsonArray)));
	}else{
		return true;
	}
}
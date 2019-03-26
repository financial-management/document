buttonJson =[
              {name:'查询1',fun:'queryDemo(this)',buttonToken:'query',buttonType:'child'},
              {name:'新增1',fun:'togByDataSourceCode(this,\''+_dataSourceCode+'\')',buttonToken:'add',buttonType:'child'},
              {name:'修改1',fun:'updataRowByDataSourceCode(this,\''+_dataSourceCode+'\')',buttonToken:'update',buttonType:'child'},
              {name:'删除1',fun:'delRowsByDataSourceCode(this,\''+_dataSourceCode+'\')',buttonToken:'delete',buttonType:'child'},
              {name:'保存super',fun:'updataRowSuper(this)',buttonToken:'update',buttonType:'super'},
              {name:'返回',fun:'queryDemo(this,\''+_dataSourceCode+'\')',buttonToken:'query',buttonType:''}
			];


function queryDemo(t){
	queryTableByParam(t,_dataSourceCode,_query_param);
}


function updataRowSuper(t){
	savaByQueryForSuper(t,dataSourceCode,$("#superInsertPage"));
}
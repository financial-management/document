

buttonJson =[
             {name:'001',fun:'test(this)',buttonToken:'query'},
             {name:'002',fun:'tog(this)',buttonToken:'add'}
			];


function test(bus){
	var abc =$(bus).attr("buttonToken");
	alert("aaa_bb");
	oTable.queryTable($table, findBusUrlByPublicParam(bus,''));
}


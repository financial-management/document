buttonJson =[
              {name:'新增',fun:'add(this)',buttonToken:'add',buttonType:'super'},
              {name:'修改',fun:'newUpdate(this)',buttonToken:'update',buttonType:'super'},
              {name:'保存',fun:'updataRowSuper(this)',buttonToken:'update',buttonType:'super'}
             
			];

function doBack(t){
	//history.go(-1);
	window.location.href="demoSuper.jsp?pageCode="+dataSourceCode+"&menuCode=SUPER&pageName=文档目录管理";
}

function add(t){
	
	var url =context+"/pages/addDetail.jsp?pageCode="+dataSourceCode+"&menuCode=DEMO";
	url=appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue);
	window.location.href=url;
}

function appendChildUrl(url,_dataSourceCode,ParentPKField,ParentPKValue){

	return url+"&_dataSourceCode="+_dataSourceCode
		      +"&ParentPKField="+ParentPKField
		      +"&ParentPKValue="+ParentPKValue;
		      //+"&subCode="+getSubCode($("#ID").val());
}

function getSubCode(code){

	var res="";
	$.ajax({  
        //url : "/system/functionNode/sub?dataSourceCode="+dataSourceCode+"&totalCode="+code,
		url : "/system/functionNode/sub",
        dataType : "json",  
        type : "GET",
        async: false,
        data:{"dataSourceCode":dataSourceCode,"totalCode":code},
        success : function(data) {  
            if(data) {  
            	if(!isNaN(data.subCode)){
            		res = (parseInt(data.subCode)+1)+"";
            	}
            }else{  
                alert("请求失败！");
            }  
        }  
    });
	
	return res;
}

function updataRowSuper(t){
	savaByQueryForSuper(t,dataSourceCode,$("#superInsertPage"));
	var newUrl = window.location.href;
	newUrl = changeURLPar(newUrl,"isReadonly","1")
	//alert(newUrl);
	window.location.href = newUrl;
}

function newUpdate(t){
	var newUrl = window.location.href;
	newUrl = changeURLPar(newUrl,"isReadonly","0")
	//alert(newUrl);
	//newUrl = newUrl + "&isReadonly=0"	// 0为否，1为是
	//setUnReadonlyByDiv($("#superInsertPage"));
	
	window.location.href = newUrl;
	//window.location.href = currentUrl; 
	//locatiion.replace(newUrl);
	//location.reload(); 
}



function delRows(t){
	var id = $("#ID").val();
	//alert(id);
	var jsonData ='[{"ID":"'+id+'"}]';
	var message = transToServer(findBusUrlByPublicParam(t,'',dataSourceCode),jsonData);
	oTable.showModal('modal', message);
	//可以跳转，但是没有显示提示框，直接跳转了。
	var url = "/pages/demoSuper.jsp?pageCode=RM_FUNCTION_NODE&menuCode=SUPER"
	url = context + url;
	window.location.href = url;
	
	//x = document.getElementById("menucontent");
	//x.src = url;
}



buttonJson =[
              {name:'新增',fun:'add(this)',buttonToken:'add',buttonType:'super'},
              {name:'修改',fun:'newUpdate(this)',buttonToken:'update',buttonType:'super'},
             {name:'保存',fun:'updataRowSuper(this)',buttonToken:'update',buttonType:'super'},
              {name:'删除',fun:'delMyRows(this)',buttonToken:'delete',buttonType:'super'}
             
			];

function doBack(t){
	//history.go(-1);
	//window.location.href="demoSuper.jsp?pageCode="+dataSourceCode+"&menuCode=SUPER&pageName=文档目录管理";
}

function add(t){
	
	var url =context+"/pages/docAddDetail.jsp?pageCode="+dataSourceCode+"&menuCode=DEMO";
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
	window.parent.loadJstree($("#superInsertPage").find("#ID").val());
	window.parent.openTreeNodeById($("#superInsertPage").find("#ID").val());
	/*var newUrl = window.location.href;
	newUrl = changeURLPar(newUrl,"isReadonly","1")
	//alert(newUrl);
	window.location.href = newUrl;*/
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

function delMyRows(t){
	
	
	var id = $("#ID").val();
	//alert(id);
	var jsonData ='{"ID":"'+id+'"}';
	$.ajax({
		type:"post",
		dataType:"json",
		data:jsonData,
		url:"../documentTree/queryChildTree",
		contentType:"application/json",
		success:function(data){
			console.log(data);
			if(data.status=="success"){

				message = transToServer(findUrlByDelete(),jsonData);
				var message ="删除成功";
				alert(message);
				window.parent.loadJstree($("#PARENT_ID").val());
			}else{
				alert(data.msg);
			}
		},
		error:function(){
			alert("删除出错，请联系管理员");
		}
	});
	//return ;
	//alert(jsonData);
	/*message = transToServer(findUrlByDelete(),jsonData);
	var message ="删除成功";*/
//	oTable.showModal('modal', message);
	/*alert(message);
	window.parent.location.reload();*/
	
	//x = document.getElementById("menucontent");
	//x.src = url;
}




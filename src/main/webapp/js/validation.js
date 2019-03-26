//注意   CheckFieldIsNotEqual 验证状态字段值  数字 只能为个位。
function checkBusStart(tableJsons,checkJson){
	var returnFlay =false;
	var trueInt=0;
	var errorString ='';
	for(var i=0;i<tableJsons.length;i++){
		var trueIntByJ=0;
		for(var j=0;j<checkJson.length;j++){
			if(checkJson[j]['CheckValue'].indexOf(tableJsons[i][checkJson[j]['CheckColumn']])!=-1&&checkJson[j]['IsEqual']){
				trueIntByJ++;
			}else{
				errorString=errorString+bulidErrorMessageHtml(tableJsons[i]["RMRN"],checkJson[j]['ErrorMessage']);
			}
		}
		if(trueIntByJ==checkJson.length){
			trueInt++;
		}
	}
	if(trueInt==tableJsons.length){
		returnFlay=true;
	}else{
		oTable.showModal('付款管理',errorString );
	}
	return returnFlay;
}



function businessOperations(tableJsons,t,params){
	oTable.showModal('提示',transToServer(findBusUrlByPublicParam(t,params,dataSourceCode),JSON.stringify(tableJsons)));
	queryTable(t);
}


function busPublcFunction(tableJsons,checkSelect,checkBus,t,params){
	if(checkSelect){
		if(checkBus){
			businessOperations(tableJsons,t,params);
		}
	}
}

function bulidErrorMessageHtml(rmrnVal,errorMessage){
	var errorString ="第 "+rmrnVal+" 条， "+errorMessage;
	return '<font color="red">'+errorString+'<font/> <br/>';
}



function isCheckSelect(tableJsons){
	if(tableJsons.length == 0){
		oTable.showModal('modal', "请选择记录");
		return false;
	}else{
		return true;
	}
}
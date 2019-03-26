buttonJson =[
             {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
             {name:'新增',fun:'tog(this)',buttonToken:'insertWithCache'},
             {name:'修改',fun:'updateRow(this)',buttonToken:'updateWithCache'},
             {name:'删除',fun:'delRows(this)',buttonToken:'deleteWithCache'},
             {name:'物理新增',fun:'tog(this)',buttonToken:'metadataPhyIns'},
             {name:'物理修改',fun:'updateRow(this)',buttonToken:'metadataPhyUpd'}
             
			];
var $inputFormart = $("#INPUT_FORMART");
changeStyle();
$("#INPUT_TYPE").change(function() {
	if('3'==$(this).val()){
		changeStyle();
		$("#inputFormartImgSearch").attr('onclick',"setRefField(this)");
	}else{
		$("#inputFormartImgSearch").removeAttr('onclick');
	}
});

function changeStyle(){
	if($("#formart_multiselect").length > 0){
		return ; 
	}
	$inputFormart.parent().append('<div id="formart_multiselect" class="input-group"></div>');
	$inputFormart.appendTo($("#formart_multiselect"));
	$("#formart_multiselect").append('<span class="input-group-addon" id="inputFormartImgSearch" style="cursor:pointer" onclick="setRefField(this)"><span class="glyphicon glyphicon-search"></span></span> ');
	if('3'==$("#INPUT_TYPE").val()){
		$("#inputFormartImgSearch").attr('onclick',"setRefField(this)");
	}else{
		$("#inputFormartImgSearch").removeAttr('onclick');
	}
}

function setRefField(t){
	var metaDataId = $("#METADATA_ID").val();
	if(metaDataId ==''){
		oTable.showModal('modal', '请选择元数据！');
		return  ; 
	}
	var getColumnInfoUrl = context+'/multiselect?cmd=getColumnInfo&metaDataId='+metaDataId;
	var message = transToServer(getColumnInfoUrl,null);
	var jsonMessage = JSON.stringify(message);
	write_multiselect_ref(getColumnInfoUrl ,jsonMessage);
}
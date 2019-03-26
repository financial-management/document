buttonJson =[
             {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
             {name:'新增',fun:'tog(this)',buttonToken:'insertWithCache'},
             {name:'修改',fun:'updateRow(this)',buttonToken:'updateWithCache'},
             {name:'删除',fun:'delRows(this)',buttonToken:'deleteWithCache'},
             {name:'效果预览',fun:'pageView()'}
			];

$(function(){
	$(document.body).append(
			'<!--查看数据源展示效果专用模态框-->'+
			'<div class="modal fade" id="ViewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
				'<div class="modal-dialog" style="width: 90%;height: 90%">'+
					'<div class="modal-content">'+
						'<div class="modal-header">'+
							'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'+
								'×'+
							'</button>'+
							'<h4 class="modal-title" id="myModalLabel">数据源展示效果预览</h4>'+
						'</div>'+
						'<div class="modal-body"></div>'+
						'<div class="modal-footer">'+
							'<button type="button" class="btn btn-default" data-dismiss="modal">'+
								'关闭'+
							'</button>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>'
	);
})

function pageView(){
	
	var selected = JSON.parse(getSelections());
	if(selected.length != 1){
		oTable.showModal('modal', "请选择一条数据预览效果");
		return;
	}
	
	var viewUrl = context+'/pages/dataSourceView.html?' + Math.random(1000);
	$.get(viewUrl, '', function(data) {
		$('#ViewModal .modal-body').html(data);
		return;
	})
	$('#ViewModal').modal('show');
}


function ref_query_param(u){
	
	if(u=='CD_PROXY_PAGE_REF'){
		return "&SEARCH-META_ID="+$("#METADATA_ID").val();
	}else{
		return "";
	}
}

function ref_start(t,col,page_type){
	if(col=='PROXY_PAGE_CODE'){
		var metadata_id =$("#METADATA_ID").val();
		if(metadata_id!=null&&metadata_id.length>0){
			return true;
		}else{
			oTable.showModal('modal', '<font color=red>请先选择元数据主键信息！</font>');
		}
	}else{
		return true;
	}
}

$(function(){
	$("#RETURN_FIELD").parent().append('<div class="input-group" id="inputGroup"></div>');
	$("#RETURN_FIELD").appendTo($("#inputGroup"));
	$("#inputGroup").append('<span class="input-group-addon" style="cursor:pointer" onclick="setField()"><span class="glyphicon glyphicon-search"></span></span> ');
});

function setField(){
	var metaDataId = $("#METADATA_ID").val();
	var metaDataCode = $("#METADATA_CODE").val();
	if(metaDataId =='' || metaDataCode == ''){
		oTable.showModal('modal', '请选择元数据！');
		return  ; 
	}
	var dataCodes = allTableName() ; 
	dataSourceCodeUrl = "CD_METADATA_MULTISELECT";
	var getJsonByIdUrl = context+'/multiselect?cmd=getOptions&dataSourceCodeUrl='+dataSourceCodeUrl+'&dataCodes='+dataCodes;
	var message = transToServer(getJsonByIdUrl,null);
	var jsonMessage = JSON.stringify(message);
	write_multiselect_html(dataSourceCodeUrl,jsonMessage);
}

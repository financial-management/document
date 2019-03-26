var TableInit = function(toolbar) {
	var oTableInit = new Object();
	// 初始化Table
	var v_toolbar="#"+toolbar;
	oTableInit.queryTable = function($e, url) {
		$e.bootstrapTable('destroy'); 
		$e.bootstrapTable({
			url : url, // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			dataType: "json",
			toolbar: v_toolbar,
			striped : false, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [5,10,25,50,100], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
 			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			columns : oTableInit.columns,
			formatLoadingMessage: function () {  
			     return "请稍等，正在加载中...";  
			},  
			formatNoMatches: function () {  //没有匹配的结果  
				if(url==null){
					 return '请【查询】数据！'; 
				}
			    return '无符合条件的记录';  
			},
			formatRefresh: function () {
	            return '刷新';
	        },
	        formatToggle: function () {
	            return '视图切换';
	        },
	        formatColumns: function () {
	            return '展示列';
	        },
			queryParams : function (params) {
				  var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				   limit: params.limit, //页面大小
				   offset: params.offset//页码
				  };
				  return temp;
		    }


		});

		//$table.bootstrapTable('checkBy', {field:'id', values:[1, 2, 3]})
	};
	
    
    oTableInit.bootMethod =function($e,method){
    	return $e.bootstrapTable(method);
    };
   
    
    oTableInit.initCols =function($e,colurl,qusurl){
    	var data_str="";
    	$.ajax({
    		async: false,
            type: "post",
            url: colurl,
            dataType: "text",
            success: function(data){
            	data_str=data;
            }
        });
		oTableInit.columns =JSON.parse(data_str);
		oTableInit.queryTable($e,qusurl);
    	isDestroy=true;
    };
    
    oTableInit.initSelCol=function(selColMess){
    	$(v_toolbar).html(selColMess);
    }
    
    oTableInit.initQueryParam = function(paramurl,$id){
    	var queryParamHtml = "";
    	$.ajax({
    		async: false,
    		type: "post",
			url: paramurl,
			dataType: "html",
			success: function(data){
				queryParamHtml = data;
				}
			});
    	$id.html(queryParamHtml);
    	
    	$('.form_date').datetimepicker({
    		minView: 'month',         //设置时间选择为年月日 去掉时分秒选择
   			format:'yyyy-mm-dd',
    	    weekStart: 1,
    	    todayBtn:  1,
    	    autoclose: 1,
    	    todayHighlight: 1,
    	    startView: 2,
    	    forceParse: 0,
    	    showMeridian: 1,
    	    language: 'zh-CN'              //设置时间控件为中文
    	});
    }
    
    oTableInit.showModal =function(_title,message){
    	
    	var _id ="modal"
    	var id_mark ="#"+_id;
    	var $id =$(id_mark);
    	if($id.length>0){
    		$("#"+_id+"message").html(message);
    		$("#"+_id+"title").html(_title);
    		$id.modal("show");
    	}else{
    		 $(document.body)
    			.append(
    					"<div class='modal fade' id='"+_id+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>"
    							+ "<div class='modal-dialog'>"
    							+ "<div class='modal-content'>"
    							+ "<div class='modal-header'>"
    							+ "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>"
    							+ "<h4 class='modal-title' id='"+_id+"title'>"+_title+"</h4>"
    							+ "</div>"
    							+ "<div class='modal-body' id='"+_id+"message'>"
    							+ message
    							+ "</div>"
    							+ "<div class='modal-footer'>"
    							+ " <button type='button' class='close' data-dismiss='modal' >关闭</button>"
    							+ "  </div>"
    							+ " </div>"
    							+ "</div>"
    							+ "</div>");
    		    $(id_mark).modal("show");
    	}
    
    }
    
    
	
	return oTableInit;
};


function hideModal(_id){
	var id_mark ="#"+_id;
	var $id =$(id_mark).modal("hidden");
}
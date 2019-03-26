var TablesInit = function($e,$toolBar) {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.queryTable = function(url) {
		$e.bootstrapTable('destroy'); 
		$e.bootstrapTable({
			url : url, // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			dataType: "json",
			toolbar: $toolBar,
			striped : false, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			onlyInfoPagination : true,//只显示总数据数，而不显示分页按钮
			sortable : true, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
//			pageSize : 10, // 每页的记录行数（*）
//			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
 			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
//			height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
	        onLoadError: function(status,response){
		    	//登录超时
		    	if(response.getResponseHeader("TIMEOUTURL")!=null){
		    		window.top.location.href = response.getResponseHeader("TIMEOUTURL");
		    	}
		    	return "数据加载失败";
		    } 
		});
	};
	
    
    oTableInit.bootMethod =function($e,method){
    	return $e.bootstrapTable(method);
    };
   
    //初始化列布局后调用queryTable展现数据
    oTableInit.initCols =function(colurl,qusurl){
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
        oTableInit.queryTable(qusurl);
    	isDestroy=true;
    };
    
    //初始化页面查询条件
    oTableInit.initQueryParam = function($pa,paramurl){
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
    	$pa.prepend(queryParamHtml);//防止覆盖页面设置的多table情况下的公共按钮
    	
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
    
    //初始化维护（新增&修改）页面
    oTableInit.initMaintainCols = function($ip,maintainurl){
    	var maintainHtml = "";
    	$.ajax({
    		async: false,
    		type: "post",
			url: maintainurl,
			dataType: "html",
			success: function(data){
				maintainHtml = data;
				}
			});
    	$ip.append(maintainHtml);//防止覆盖主表ID等页面手动配置字段
    }
    
    //向服务器发送表的增删改请求
    oTableInit.operateTable =function(tabName,url,jsonData){
    	var message; 
    	$.ajax({
    		async: false,
    		type: "post",
			url: url,
			dataType: "json",
			data:{"tabName":tabName,"jsonData":jsonData},
			success: function(data){
				//FIXME data["message"]改为了data——为适应操作失败的情况，依据返回的其他数据做是否返回list页面的处理，还未更新至其他js
				message = data;
				},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				message ="{\"flag\":\"false\",\"message\":\"请求失败！\"}";
				}
			});
    	return message;
    };
    
    //模态框
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
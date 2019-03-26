var page_size=0;

var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.queryTable = function($e, url) {
		$e.bootstrapTable('destroy'); 
		$e.bootstrapTable({
			url : url, // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			dataType: "json",
			toolbar: "#toolbar",
			striped : false, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
 			showColumns : false, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
//			height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			showExport: false,                     //是否显示导出
	        exportDataType: "selected",              //basic', 'all', 'selected'.
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
		    },
		    onClickRow: function (row,tr) {  
		        clickFunction(row,tr);
		    },
		    onLoadSuccess: function (data){
		    	//cacheTableJsonArray=oTable.bootMethod($table, "getData");
		    	page_heigth=$(document.body).height();
		    	setParntHeigth(page_heigth);
		    	bulidCacheTableJsonArray();
		    },
		    onLoadError: function(status,response){
		    	//登录超时
		    	if(response.getResponseHeader("TIMEOUTURL")!=null){
		    		window.top.location.href = response.getResponseHeader("TIMEOUTURL");
		    	}
		    	return "数据加载失败";
		    },
		    onDblClickRow :function (row,tr){
		    	dblClickFunction(row,tr);
		    }


		});
		//$table.bootstrapTable('checkBy', {field:'id', values:[1, 2, 3]})
	};
	
    
    oTableInit.bootMethod =function($e,method){
    	return $e.bootstrapTable(method);
    };
   
    //初始化列布局后调用queryTable展现数据
    oTableInit.initCols =function($e,colurl,qusurl){
    	var data_str="";
    	$.ajax({
    		async: false,
            type: "post",
            url: colurl,
            dataType: "text",
            success: function(data){
            	data_str=data;
            	oTableInit.columns =JSON.parse(data_str);
            	oTableInit.queryTable($e,qusurl);
            }
        });
    	isDestroy=true;
    };
    
    //初始化页面查询条件
    oTableInit.initQueryParam = function($e,paramurl){
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
    	$e.append(queryParamHtml);
    }
    
    //初始化维护（新增&修改）页面
    oTableInit.initMaintainCols = function($e,maintainurl){
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
    	//$e.append(maintainHtml);//防止覆盖主表ID等页面手动配置字段
    	return maintainHtml;
    }
    
    
    
    //模态框
	oTableInit.showModal =function(_title,message){
	    	
	    	var _id ="modal"
	    	var id_mark ="#"+_id;
	    	var $id =$(id_mark);
	    	if($id.length>0){
	    		$("#"+_id+"message").html(message);
	    		$("#"+_id+"title").html("提示");
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
	//确认框
	oTableInit.confirmOfJson = function(params){
			params.title = "操作提示" ; 
			var _id ="bootstrap_confirmOfJson"
	    	var id_mark ="#"+_id;
	    	var $id =$(id_mark);
	    	if($id.length>0){
	    		$("#"+_id+"message").html(params.message);
	    		$("#"+_id+"title").html(params.title);
	    		$id.modal("show");
	    	}else{
	    		 $(document.body)
	    			.append(
	    					"<div class='modal fade' id='"+_id+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>"
	    							+ "<div class='modal-dialog'>"
	    							+ "<div class='modal-content'>"
	    							+ "<div class='modal-header'>"
	    							+ "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>"
	    							+ "<h4 class='modal-title' id='"+_id+"title'>"+params.title+"</h4>"
	    							+ "</div>"
	    							+ "<div class='modal-body' id='"+_id+"message'>"
	    							+ params.message
	    							+ "</div>"
	    							+ "<div class='modal-footer'>"
	    							+ "<button type='button' class='btn btn-primary ok' id='bootstrap_confirmOfJson_ok' data-dismiss='modal'>确认</button>"
						            + "<button type='button' class='btn btn-default cancel' id='bootstrap_confirmOfJson_cancel' data-dismiss='modal'>取消</button>"
	    							+ "  </div>"
	    							+ " </div>"
	    							+ "</div>"
	    							+ "</div>");
	    		    $(id_mark).modal("show");
	    	}
		$("#bootstrap_confirmOfJson_ok").click(function() {
			 params.operate(true);
			 $this.stopPropagation();
		});
		$("#bootstrap_confirmOfJson_cancel").click(function() {
			 params.operate(false);
			 $this.stopPropagation();
		});
		/**
		 *调用方法 
		 oTable.confirmOfJson({
			title: "操作提示",
	      message: "您确认要删除此数据吗？",
	      operate: function (reselt) {
	          if (reselt) {
	              alert(123);
	          } else {
	              alert(321);
	          }
	      }
		});*/
	}
	//确认框
	oTableInit.confirm = function(message,callback){
		if(callback == undefined || typeof(callback)!="function"){
			oTable.showModal("格式错误", "函数格式有误 !, oTableInit.confirm('提示信息',回调函数)");
			return ;
		}
		var _id ="bootstrap_confirm"
    	var id_mark ="#"+_id;
    	var $id =$(id_mark);
    	if($id.length>0){
    		$("#"+_id+"message").html(message);
    		$id.modal("show");
    	}else{
    		 $(document.body)
    			.append(
    					"<div class='modal fade' id='"+_id+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>"
    							+ "<div class='modal-dialog'>"
    							+ "<div class='modal-content'>"
    							+ "<div class='modal-header'>"
    							+ "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>"
    							+ "<h4 class='modal-title' id='"+_id+"title'>操作提示</h4>"
    							+ "</div>"
    							+ "<div class='modal-body' id='"+_id+"message'>"
    							+ message
    							+ "</div>"
    							+ "<div class='modal-footer'>"
    							+ "<button type='button' class='btn btn-primary ok' id='bootstrap_confirm_ok' data-dismiss='modal'>确认</button>"
					            + "<button type='button' class='btn btn-default cancel' id='bootstrap_confirm_cancel' data-dismiss='modal'>取消</button>"
    							+ "  </div>"
    							+ " </div>"
    							+ "</div>"
    							+ "</div>");
    		    $(id_mark).modal("show");
    	}
		$("#bootstrap_confirm_ok").click(function() {
			 callback(true);
			$this.stopPropagation();
		});
		$("#bootstrap_confirm_cancel").click(function() {
			 callback(false);
			$this.stopPropagation();
		});
		
	/**调用方法
	 * 	oTable.confirm("您确定执行此操作吗？",function(result){
			if(result){
				alert(123);
			}else{
				alert(321);
			}
		});
	 */
	}
	
	return oTableInit;
};


function stateFormatter(value, row, index) {
	
    return value;
}
function dblClickFunction(row,tr){
	//showDetailPage("detailsPage.jsp?pageCode="+dataSourceCode+"&ParentPKField=ID&ParentPKValue="+row["ID"]);
}

function setParntHeigth(heigth){
	 if(parent['setHeigth']){
		 parent['setHeigth'](heigth);
	 }
}
		//全局 变量	由各参选的onclick方法决定值
		var globalRef_col = "";//参选发起列id
		var globalRef_pageType = "";//参选发起页面类型-'SELECT'&'INSUP'

		function checkReference(t,message,col,page_type){
			
			if(isRefStart(t,col,page_type)){
				var error="";
				var s="REF(";
				var e=")";
				if(message.length>=4){
					var start =message.substr(0,4);
					var main =message.substr(4,message.length-5);
					var end =message.substr(message.length-1,1);
					if(start==s&&end==e&&main.indexOf("(")==-1&&main.indexOf(")")==-1){
						writeMessage =main.split(",");
						if(writeMessage.length==3){
							globalRef_col = col;
							globalRef_pageType = page_type;
							return reference_remote(writeMessage[0],writeMessage[1],writeMessage[2]);
						}
					}
				}
				error="函数格式有误 !, REF(数据源编码,参照字段1:页面回写字段1;参照字段2:页面回写字段2,是否单选[1 or 0])";
				alert(error+"\n"+message);
				return ;
			}
		}
		
		function isRefStart(t,col,page_type){
			if(typeof(ref_start) == "function"){
				return ref_start(t,col,page_type);
			}else{
				return true;
			}
		}
		
		function reference_remote(u, mapping, isRadio) {
			write_html();
			url = context+'/pages/busPage/referencePage.jsp?dataSourceCode=' + u + '&isRadio=' + isRadio + '&t=' + Math.random(1000);
			$('#ReferenceDataSourceCode').val(u);
			$('#ReferenceIsRadio').val(isRadio);
			$('#ReferenceMapping').val(mapping);
			$.get(url, '', function(data) {
				$('#ReferenceModal .modal-body').html(data);
			})
			$('#ReferenceModal').modal({
				show : true,
				backdrop : true
			})
		}
		
		function ref_page(url,dataSource,mapping,isRadio){
			write_html();
			$('#ReferenceDataSourceCode').val(dataSource);
			$('#ReferenceIsRadio').val(isRadio);
			$('#ReferenceMapping').val(mapping);
			$.get(url, '', function(data) {
				$('#ReferenceModal .modal-body').html(data);
			})
			$('#ReferenceModal').modal({
				show : true,
				backdrop : true
			});
		}
		
		
		function write_html(){
	    	
	    	var $id =$("#ReferenceModal");
	    	if($id.length>0){
	    	}else{
	    		 $(document.body)
	    			.append(
	    		  	"<div class='modal fade' id='ReferenceModal'>"+
					"<div class='modal-dialog'  style='width: 80%;height: 80%'>"+
					"<div class='modal-content' >"+
					"<div class='modal-header'>"+
							"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>"+
							"×"+
							"</button>"+
							"<h4 class='modal-title' id='ReferenceModalLabel'>参照</h4>"+
							"</div>"+
							"<div id='ReferenceModal_body' class='modal-body' style='height: auto' ></div>"+
							"<div class='modal-footer'>"+
							"<button type='button' class='btn btn-default ' data-dismiss='modal'>"+
									"关  闭"+
							"</button>"+
							"</div>"+
							"</div>"+
					"</div>"+
					"</div>"+
	    		  	"<input type='hidden' id='ReferenceDataSourceCode' />"+
				    "<input type='hidden' id='ReferenceMapping' value='' />"+
				    "<input type='hidden' id='ReferenceIsRadio' value='' />"
				    
	    			);
	    	}
	    	
	    	$('#ReferenceModal').on('hide.bs.modal', function() {
				$(this).removeData('modal');
				$('#ReferenceModal .modal-body').html('');
				$('#ReferenceDataSourceCode').val('');
				$('#ReferenceMapping').val('');
				$('#ReferenceIsRadio').val('');
			});
	    
	    }

		function reference_removeVal(id) {
			$('#' + id).val('');
		}
		
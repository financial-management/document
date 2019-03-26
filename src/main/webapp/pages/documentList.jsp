<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据文档管理</title>
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="tree/themes/default/style.min.css" />  
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="tree/jstree.js"></script>  
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
</head>
<body>

<form>

		<div class="tabbable" id="tabs-146746">
				<div class="tab-content">
					<div class="tab-pane active" id="panel-847015">
						<div class="col-md-3 col-xs-3 col-sm-3" style="height:100%;overflow:scroll">
							<ul id="myTab1" class="nav nav-pills nav-stacked">
					          <li >
					          	  <div id="jstree_div"></div>  
					          </li>
		       				</ul>
						</div>
				
						<div class="col-md-9 col-xs-9 col-sm-9" style="padding:0;">
						<div class="tab-content">
							
							<div class="tab-pane fade in active" id='message'>
									<iframe id="menucontent" src="" scrolling="yes" frameborder="0"  width="99%" height="99%"></iframe>
							</div>
						</div>
						
						</div>
			
					</div>
				</div>
			</div>

						
			
		</form>
		  
    <script type="text/javascript">
   var token='<%=request.getParameter("token")!=null?request.getParameter("token"):""%>';
       token="<%=request.getParameter("token")%>";
         $(function() { 
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" : function (obj, callback){  
                            $.ajax({  
                                url : "/document/documentTree/authDocumentTree?token="+token, 
                                dataType : "json",  
                                type : "GET",  
                                success : function(data) {  
                                    //console.info(data); 
                                    if(data) {  
                                        callback.call(this, data);  
                                    }else{  
                                        $("#jstree_div").html("暂无数据！");  
                                    }  
                                }  
                            });  
                    }  
                },  
             //   "plugins" : ["sort"]  
            }).bind("select_node.jstree", function(event, data) {  
                var inst = data.instance;  
                
                var selectedNode = inst.get_node(data.selected);
                toDetail(selectedNode.data);  
                //$("#message").html(selectedNode.data);
               
                //console.info(selectedNode.aria-level);  
                //var level = $("#"+selectedNode.id).attr("aria-level");  
                //loadConfig(inst, selectedNode);
            });  
        });  
      
        function toDetail(data){
        
            
            //alert(JSON.stringify(data));
            
            //return ;
        
        
        	//alert(data.id);
            x = document.getElementById("menucontent");
        	var opts=data.opreators;
        	
        	//公共文件夹
        	//非个人文档需要控制
        	
        	if(data.group_type!=="2"){
        		//按钮权限控制    			
        		x.src = "demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=SUPER&ParentPKField=DIRECTORY_ID&ParentPKValue="+data.id+"&opts="+opts+"&token="+token;
        	}else
        	{
        	    if(data["id"]=="3"){
        	     //x.src = "demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=PERSION&ParentPKField=DIRECTORY_ID&ParentPKValue="+data.id+"&token="+token;
        	     
        	     var ge="3";
        	     x.src = "demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=SUPER&ParentPKField=DIRECTORY_ID&ParentPKValue="+data.id+"&opts="+ge+"&token="+token;
        	    }
        		//无权限控制
        		else{
        		//x.src = "demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=SUPER&ParentPKField=DIRECTORY_ID&ParentPKValue="+data.id+"&opts="+"0001"+"&token="+token;
        		x.src = "demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=PERSION&ParentPKField=DIRECTORY_ID&ParentPKValue="+data.id+"&token="+token;
        	  }
        	}
        }
        
        function loadConfig(inst, selectedNode){  
            var temp = selectedNode.id;  
            //inst.open_node(selectedNode);  
            //alert(temp);  
            $.ajax({  
                url : "/system/copyOfParty/ajax?PARENT_PARTY_CODE='"+temp+"' ",  
                dataType : "json",  
                type : "GET",  
                success : function(data) {  
                    if(data) { 
                       selectedNode.children = [];  
                       $.each(data, function (i, item) {  
                                var obj = {text:item};  
                                //$('#jstree_div').jstree('create_node', selectedNode, obj, 'last');  
                                inst.create_node(selectedNode,item,"last");  
                       });  
                       inst.open_node(selectedNode);  
                    }else{  
                        $("#jstree_div").html("暂无数据！");  
                    }  
                }  
            });  
        }  
        
        var openNodeId=null;
        function loadJstree(id){
        	openNodeId=id;
        	$.jstree.destroy ();
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" : function (obj, callback){  
                            $.ajax({  
                                url : "/document/documentTree/authDocumentTree?token="+token,
                                dataType : "json",  
                                type : "GET",  
                                success : function(data) {  
                                    //console.info(data); 
                                    if(data) {  
                                        callback.call(this, data);  
                                    }else{  
                                        $("#jstree_div").html("暂无数据！");  
                                    }  
                                }  
                            });  
                    }  
                },  
             //   "plugins" : ["sort"]  
            }).bind("select_node.jstree", function(event, data) {  
                var inst = data.instance;  
                
                var selectedNode = inst.get_node(data.selected);
                toDetail(selectedNode.data);  
                //$("#message").html(selectedNode.data);
               
                //console.info(selectedNode.aria-level);  
                //var level = $("#"+selectedNode.id).attr("aria-level");  
                //loadConfig(inst, selectedNode);
            }).bind("loaded.jstree", function (e, data) {
          		$.ajax({
	                url : "/document/documentTree/queryParentTree?ID="+openNodeId,  
	                type : "get",  
	                success : function(data1) {  
	               		 data1=JSON.parse(data1);
	                	if(data1!=null&&data1.status=="success"){
	                		$(data1.data).each(function(){
								var node=$('#jstree_div').jstree("get_node", this.ID);
								data.instance.open_node(node);
								$("#"+openNodeId+"_anchor").click();
	                		});
	                	}else{
		            		alert("系统出错，请联系管理员");
	                	}
		            }
	            });
			}); 
        }
    </script>  
    
</body>
</html>
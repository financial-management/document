<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文档目录管理</title>
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="tree/themes/default/style.min.css" />  
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="tree/jstree.js"></script>  
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
</head>
<body>
<%
	String token=request.getParameter("token");
%>
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
						<!-- <div class="panel-body" id="bulidPage" style="display: none">
							<div class="panel panel-primary">
								<div class="panel-heading">
									新增
								</div>
								<div class="panel-body">
									
									<div>
										<button type="button" class="btn btn-default" onclick="save(this)">
											<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
										</button>
										<button type="button" class="btn btn-default" onclick="back(this)">
											<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>返回
										</button>
									</div>
									维护页面
									<div class="panel-body" id="insPage"></div>
								
								</div>
					</div>
				</div> -->
						</div>
			 
					</div>
				</div>
			</div>

						
			
		</form>
		   
    <script type="text/javascript">
       var token='<%=request.getParameter("token")!=null?request.getParameter("token"):""%>';
    
         $(function() {
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" : function (obj, callback){  
                            $.ajax({  
                                url : "/document/documentTree/ajaxTree?token="+token,
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
                var inst = data.instance;  
                var selectedNode = inst.get_node("3");
                inst.delete_node("3");
			});  
       
     	 }); 
        function toDetail(data){
        	//alert(data.id);
            x = document.getElementById("menucontent");
        	
        
        	//公共文件夹
        	if(data.group_type=='0'){       		
        		//只读文件夹
        		if(data.readOnly=='0')
        		{
        			x.src = "docDetail.jsp?pageCode=DOC_DIRECTORY_SELECT&menuCode=READ&_dataSourceCode=DOC_DIRECTORY_SELECT&ParentPKField=&ParentPKValue="+data.id+"&isReadonly=1&token="+token;
        		}else
        		{
        			x.src = "docDetail.jsp?pageCode=DOC_DIRECTORY_SELECT&menuCode=MGR&_dataSourceCode=DOC_DIRECTORY_SELECT&ParentPKField=&ParentPKValue="+data.id+"&isReadonly=1&token="+token;
        		}
        	}else
        	{
        		//个人文件夹
        			x.src = "docDetail.jsp?pageCode=DOC_DIRECTORY_SELECT&menuCode=PER&_dataSourceCode=DOC_DIRECTORY_SELECT&ParentPKField=&ParentPKValue="+data.id+"&isReadonly=1&token="+token;
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
                                url : "/document/documentTree/ajaxTree?token="+token,
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
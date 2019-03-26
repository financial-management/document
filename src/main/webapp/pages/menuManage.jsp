<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在此处插入标题</title>
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
						<div class="col-md-2">
							<ul id="myTab1" class="nav nav-pills nav-stacked">
					          <li >
					          	  <div id="jstree_div"></div>  
					          </li>
		       				</ul>
						</div>
				
						<div class="col-md-10">
						<div class="tab-content">
							<!-- <div><script src="singleTableModify.jsp?pageCode=RM_FUNCTION_NODE&menuCode=001&pageName=菜单管理" type="text/javascript"></script></div> -->
							
							<div class="tab-pane fade in active" id='message'>
									<iframe id="menucontent" src="demoSuper.jsp?pageCode=RM_FUNCTION_NODE&menuCode=SUPER&pageName=菜单管理" scrolling="yes" frameborder="0"  width="99%" height="2000"></iframe>
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
         $(function() { 
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" : function (obj, callback){  
                            $.ajax({  
                                url : "/system/functionNode/ajax?TOTAL_CODE='100'",  
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
        	//alert(data.id);
        	x = document.getElementById("menucontent");
        	x.src = "menuDetail.jsp?pageCode=RM_FUNCTION_NODE&menuCode=DEMO&_dataSourceCode=RM_FUNCTION_NODE&ParentPKField=&ParentPKValue="+data.id+"&isReadonly=1";
        }
        
        function loadConfig(inst, selectedNode){  
			alert(selectedNode.data);
			alert(selectedNode.text);
			alert(selectedNode.id);
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
    </script>  
    
</body>
</html>
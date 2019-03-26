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
									<iframe id="menucontent" src="docAuthSuper.jsp?pageCode=DOC_AUTHORIZE_MGR&menuCode=AUTH&_dataSourceCode=DOC_AUTHORIZE_MGR&ParentPKField=DIRECTORY_ID&ParentPKValue=1&DIRNAME=联通智网&token=<%=request.getParameter("token") %>" scrolling="yes" frameborder="0"  width="99%" height="99%"></iframe>
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
                            $.ajax({                         //ajaxTree    ajaxAuthTree
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
            });  
        });  
      
        function toDetail(data){
        	//alert(data.id);
            x = document.getElementById("menucontent");
        	
       	 x.src = "docAuthSuper.jsp?pageCode=DOC_AUTHORIZE_MGR&menuCode=AUTH&ParentPKField=DIRECTORY_ID&_dataSourceCode=DOC_AUTHORIZE_MGR&ParentPKValue="+data.id+"&DIRNAME="+data.name+"&token="+token;
        	
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
    </script>  
    
</body>
</html>
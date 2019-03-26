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
<script src="tree/jstree.min.js"></script>  
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
</head>
<body>
<form>
	
	<div class="col-md-12">
		<div class="col-md-2">
			<ul id="myTab1" class="nav nav-pills nav-stacked">
	          <li >
	          	  <div id="jstree_div"></div>  
	          </li>
			</ul>
		</div>
					
		<div class="col-md-10">
			<div class="tab-content">
				<div class="tab-pane fade in active" id='message'>
					<iframe id="menucontent" src="singleTableModify.jsp?pageCode=RM_PARTY_MANAGE&menuCode=001&pageName=组织管理" scrolling="yes" frameborder="0"  width="99%" height="2000"></iframe>
				</div>
			</div>
		</div>
	</div>		
		
</form>
		  
    <script type="text/javascript">
  
    showTree();
        function showTree(){
        	//$('#jstree_div').empty();
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" : function (obj, callback){  
                            $.ajax({  
                                url : "/system/party/ajax?PARENT_PARTY_CODE='A'",  
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
                "plugins" : ["sort"]  
            }).bind("select_node.jstree", function(event, data) {  
                var inst = data.instance;  
                
                var selectedNode = inst.get_node(data.selected);
                toDetail(selectedNode.data);  
                //$("#message").html(selectedNode.data);
               
                //console.info(selectedNode.aria-level);  
                //var level = $("#"+selectedNode.id).attr("aria-level");  
                //loadConfig(inst, selectedNode);
                
            });  
        }; 
        

      
        function toDetail(data){
        	x = document.getElementById("menucontent");
        	var partyType = data.CHILD_PARTY_TYPE_ID;
        	var partyId = data.CHILD_PARTY_ID;
        	var partyCode = data.CHILD_PARTY_CODE;
        	//根据团体类型跳转到不同的信息页
        	//alert("&&&&&&js tree&&&&&");
        	if(partyType=="1000200800000000001"){
	        	x.src = "partyDetail.jsp?pageCode=TM_COMPANY&menuCode=DEMO&_dataSourceCode=TM_COMPANY&ParentPKField=PARENT_PARTY_ID&ParentPKValue="+partyId+"&ParentPartyCode="+partyCode+"&isReadonly=1";
        	}else if(partyType=="1000200800000000002"){
        		x.src = "partyDetail.jsp?pageCode=TM_DEPARTMENT&menuCode=DEMO&_dataSourceCode=TM_DEPARTMENT&ParentPKField=PARENT_PARTY_ID&ParentPKValue="+partyId+"&ParentPartyCode="+partyCode+"&isReadonly=1";
        	}else if(partyType=="1000200800000000004"){
        		x.src = "partyDetail.jsp?pageCode=TM_DEPARTMENT&menuCode=DEMO&_dataSourceCode=TM_COMPANY&ParentPKField=PARENT_PARTY_ID&ParentPKValue="+partyId+"&ParentPartyCode="+partyCode+"&isReadonly=1";
        	}else{
        		alert("暂不支持此功能！");
        	}
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
        
        function refreshParent(){
			 //alert("write_end3");
			 parent.refreshFrame();
			 //alert("write_end3");
		}
    </script>  
    
</body>
</html>
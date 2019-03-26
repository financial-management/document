<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色授权</title>
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../vendor/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="tree/themes/default/style.min.css" />
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="tree/jstree.min.js"></script>
<link rel="stylesheet" href="../vendor/bootstrap/css/bootstrap.min.css">

</head>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>角色授权</title>
</head>
<body>
	<form class="form-horizontal">
		<div class="panel panel-primary">
			<div class="panel-body" id="bulidTable">
				<div class="panel panel-primary">
					<div class="panel-heading" id='pageName'><h4 style="float: left;margin: 0">角色授权</h4>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font color="white">温馨提示：双击菜单可对该菜单下的按钮授权！</font></div>
					<div class="panel-body">
						<div class="col-md-3"
							style="border-color: blue; border: 1px solid blue; border-radius: 4px">
							<table width=98% align=center border=0 cellpadding=0
								cellspacing=0>
								<tr>
									<td><b><font class=p12>角色名称：</font><font color=red class=p14><%=request.getParameter("roleName") != null ? request.getParameter("roleName") : ""%></font></b>&nbsp;&nbsp;
									</td>
								</tr>
							</table>
							<ul id="myTab1" class="nav nav-pills nav-stacked">
								<li><br>
								<br>
									<div id="jstree_div"></div> <br>
								<br>
								<br></li>
							</ul>
							<div align="center" id="button_div_tree">
								<button type="button" class="btn btn-primary" onclick="javascript:updateRoleFunctionNode()">菜单授权</button>
								<button type="button" class="btn btn-default" onclick="javascript:resetRoleFunctionNode()">重置</button>
								<button type="button" class="btn btn-default" onclick="javascript:history.back()">返回</button>
								<br>
								<br>
							</div>
						</div>
						<div class="col-md-9">
							<div class="tab-content">
								<div class="tab-pane fade in active" id='message'>
									<iframe id="roleButtonRelationPage" scrolling="yes" frameborder="0" width="99%" height="2000"></iframe>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" value="" name="function_node_ids" id="function_node_ids">
		<input type="hidden" value="" name="function_node_id_names" id="function_node_id_names">
		<input type="hidden" value="" name="role_id" id="role_id">
	</form>

	<script type="text/javascript">
    	var token='<%=request.getParameter("token")!=null?request.getParameter("token"):""%>';
    	var roleId = '<%=request.getParameter("roleId") != null ? request.getParameter("roleId") : ""%>';
    	var roleName = '<%=request.getParameter("roleName") != null ? request.getParameter("roleName") : ""%>';
    	var inst;
    	var selectedNode=[];
    	var menuCode = '';
    	var menuName = '';
          $(function() { 
            $('#jstree_div').jstree({  
                'core' : {  
                    'check_callback': true,  
                    "data" :function (obj, callback){  
                        $.ajax({  
                            url : "/system/functionNode/authorizeTree?TOTAL_CODE='100'&roleId="+roleId+"&token="+token,  
                            dataType : "json",  
                            type : "GET",  
                            success : function(data) {  
                                if(data) {  
                                    callback.call(this, data);  
                                }else{  
                                    $("#jstree_div").html("暂无数据！");  
                                }  
                            },
                            error : function(){
                            	$("#jstree_div").html("请求失败，请联系管理员！");
                            }  
                        });  
                    }
                },  
                "plugins" : ["sort","checkbox"]  
            }).bind("activate_node.jstree", function (obj, e) {
			    // 获取当前节点
			    var currentNode = e.node;
			    menuCode = currentNode.id ; 
			    menuName = currentNode.text ; 
			    $("#function_node_ids").val(menuCode);
            	$("#function_node_id_names").val(menuName);
            	$("#role_id").val(roleId);
			}).bind("changed.jstree", function(event, data) {
                inst = data.instance;  
                selectedNode = inst.get_selected(true);
                if(data.selected.length==0){
                	selectedNode=[];
                }
               // var selectedNode1 = inst.get_node(data.selected);
               // toButton(selectedNode1.data);  
            }).bind('dblclick.jstree',function(event,data){//菜单树双击时间
            	var selectedNode1 = inst.get_node(true);
      			toButton(event.view);  
    		});
			document.getElementById("roleButtonRelationPage").src = "roleButtonRelation.jsp?pageCode=RM_BUTTON_RELATION_MENU_ROLE&pageName=角色按钮授权管理";
        	
        	$('#jstree_div').jstree(true).get_all_checked = function(full) {
    		var tmp=new Array;
    		for(var i in this._model.data){
        		if(this.is_undetermined(i)||this.is_checked(i)){tmp.push(full?this._model.data[i]:i);}
    		}
    return tmp;
};
        });
        
        //每次改变菜单树节点子页面同步更新
        function toButton(data){
        	//console.log(data.menuName+data.menuCode);
        	var arr = $('#jstree_div').jstree(true).get_selected();
        	var flag = true ; 
        	for(var i = 0 ; i < arr.length ; i++){
        		if(arr[i] == menuCode){
        			flag = false ; 
        		}
        	}
        	if(flag){
        		alert("请先单击选中"+menuName);
        		$('#function_node_id_names').val('');
				$('#function_node_ids').val('');
				$("#role_id").val('');
        		document.getElementById("roleButtonRelationPage").src = "roleButtonRelation.jsp?pageCode=RM_BUTTON_RELATION_MENU_ROLE&pageName=角色按钮授权管理";
        		return  ;
        	}
        	if(''==menuName){
        		menuName = '角色按钮授权管理';
        	}
        	x = document.getElementById("roleButtonRelationPage");
        	x.src = "roleButtonRelation.jsp?pageCode=RM_BUTTON_RELATION_MENU_ROLE&pageName="+menuName;
        }
        
        function updateRoleFunctionNode(){
        	var url = "/system/role/authorize";
        	
        	//改变获取节点数据方案：原为获取所有选中节点，改为获取所有选中以及半选中节点以支持某目录只授权部分节点——同时后台逻辑有些许改动[RoleController.java]
        	//var selectedNodes = JSON.stringify(selectedNode).replace(/\"/g,"\\\"");
        	//var jsonData = '{"roleId":"'+roleId+'","selectedNode":"'+selectedNodes+'"}';
        	var selectAllNodes = $('#jstree_div').jstree(true).get_all_checked(true);
        	console.log(JSON.stringify(selectAllNodes));
        	var jsonData = '{"roleId":"'+roleId+'","selectedNode":"'+JSON.stringify(selectAllNodes).replace(/\"/g,"\\\"")+'"}';
        	
        	$.ajax({  
				url : "/system/role/authorize",
		        dataType : "json",  
		        type : "POST",
		        //async: false,
		        data:{"jsonData":jsonData},
		        success : function(data) {  
		            if(data) {  
		            	alert("操作成功！");
		            }else{  
		                alert("操作失败！");
		            }  
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown){
		        	alert("操作失败！错误编码："+XMLHttpRequest.status);
		        } 
		    });
        }
        
        function resetRoleFunctionNode(){
        	inst.deselect_all();
        }
    </script>
</body>
</html>
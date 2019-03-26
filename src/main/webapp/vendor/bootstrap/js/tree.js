$(function() {

	var defaultData = [
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'default.html\')">介绍</a>',
				href : 'message',
				tags : [ '4' ]
			},
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'mainConfig.html\')">配置</a>',
				href : '#config',
				tags : [ '0' ]
			},
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=CD_DATASOURCE&menuCode=001&pageName=Demo\')">DEMO</a>',
				href : '#demo',
				tags : [ '0' ]
			},
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=QRTZ_JOB_MESSAGE&pageName=调度任务管理\')">调度任务管理</a>',
				href : '#demo',
				tags : [ '0' ]
			},
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=QRTZ_SYSTEM_MESSAGE&pageName=调度服务管理\')">调度服务管理</a>',
				href : '#demo',
				tags : [ '0' ]
			},
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=QRTZ_NOTICE_MESSAGE&pageName=调度日志\')">调度日志</a>',
				href : '#demo',
				tags : [ '0' ]
			},
			/*{
				text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_PARTY_MANAGE&menuCode=001&pageName=组织管理\')">组织管理</a>',
				href : '#config',
				tags : [ '0' ]
			},*/
			{
				text : '<a href="#" onclick="javascript:onclickmenu(\'partyManage.html\')">组织管理</a>',
				href : '#config',
				tags : [ '0' ],
				nodes :　[
				         {
				        	 text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=TM_COMPANY&pageName=公司管理\')">公司管理</a>',
				        	 href:'#functionNode',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=TM_DEPARTMENT&pageName=部门管理\')">部門管理</a>',
				        	 href:'#button',
				        	 tags:['0']
				         }
				         ]
			},
			{
				text : '权限管理',
				href : '#property',
				tags :　[ '4' ],
				nodes :　[
				         /*{
				        	 text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_FUNCTION_NODE&menuCode=001&pageName=菜单管理\')">菜单管理</a>',
				        	 href:'#functionNode',
				        	 tags:['0']
				         },*/
				         {
				        	 text : '<a href="#" onclick="javascript:onclickmenu(\'menuManage.jsp\')">菜单管理</a>',
				        	 href:'#functionNode',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'rmButtonList.jsp\')">按钮管理</a>',
				        	 href:'#button',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_ROLE_MANAGE&menuCode=SUPER&pageName=角色管理\')">角色管理</a>',
				        	 href:'#role',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'userManage.jsp?pageCode=RM_USER&menuCode=SUPER\')">用户管理</a>',
				        	 href:'#user',
				        	 tags:['0']
				         }
				        ]
			},
			
			{
				text : '扩展功能',
				href : '',
				tags : [ '6' ],
				nodes : [ 
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_LOG&menuCode=SUPER&pageName=日志\')">日志</a>',
					href : '#log',
					tags : [ '0' ]
				} ,
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_USER_ONLINE_RECORD&menuCode=SUPER&pageName=登录日志\')">登录日志</a>',
					href : '#log',
					tags : [ '0' ]
				} ,
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_PARTY_VIEW&menuCode=SUPER&pageName=团体视图\')">团体视图</a>',
					href : '#partyView',
					tags : [ '0' ]
				} ,
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_PARTY_TYPE&menuCode=SUPER&pageName=团体类型\')">团体类型</a>',
					href : '#partyType',
					tags : [ '0' ]
				},
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_AUTHORIZE&menuCode=SUPER&pageName=权限类别\')">权限类别</a>',
					href : '#authorize',
					tags : [ '0' ]
				},
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'demoSuper.jsp?pageCode=RM_AUTHORIZE_RESOURCE&menuCode=SUPER&pageName=授权资源\')">授权资源</a>',
					href : '#authResource',
					tags : [ '0' ]
				},
				{
					text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_SYSTEM_PARAMETER&menuCode=001&pageName=系统参数\')">系统参数</a>',
					href : '#sysParameter',
					tags : [ '0' ]
				}]
			},
			{
				text : '个人工作台',
				href : '#',
				tags :　[ '4' ],
				nodes :　[
				         {
				        	 text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_THEME_INFO&pageName=主题管理\')">主题管理</a>',
				        	 href:'#theme',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=RM_PICTURE_INFO&pageName=图片管理\')">图片管理</a>',
				        	 href:'#picture',
				        	 tags:['0']
				         },
				         {
				        	 text:'<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=ROLE_THEME_INFO&pageName=角色主题配置\')">角色主题配置</a>',
				        	 href:'#roleTheme',
				        	 tags:['0']
				         }]
			},
			
			{
	              text: '通知功能',
	              href: '',
	              tags: ['5'],
	              nodes: [
	                      
	                    {
		                        text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_NOTIFY_PLAN&pageName=通知计划表\')">通知计划表</a>',
		                        href: '',
		                        tags: ['0']
		                },
	                    {
	                      text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_USER_GROUP&pageName=用户组配置\')">用户组配置</a>',
	                      href: '',
	                      tags: ['0']
	                    },
	                    {
	                        text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_SEND_OPERATION&pageName=消息维护表\')">消息维护表</a>',
	                        href: '',
	                        tags: ['0']
	                    },
	                    {
	                        text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_TEMPLATE&pageName=模板维护表\')">模板维护表</a>',
	                        href: '',
	                        tags: ['0']
	                    },
	                  
	                    {
	                        text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_NOTIFY_LOG&pageName=通知日志表\')">通知日志表</a>',
	                        href: '',
	                        tags: ['0']
	                    },
	                   
	                    {
	                        text: '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=NF_NOTICE_MANAGE&pageName=公告管理\')">公告管理</a>',
	                        href: '',
	                        tags: ['0']
	                    }
	                    
	                  ]
	          },
			{
				text : 'DEMO',
				href : '',
				tags : [ '4' ],
				nodes : [ {
					text : 'demo1',
					href : '#',
					tags : [ '4' ],
					nodes : [
							{
								text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=CD_DATASOURCE&menuCode=001&pageName=Demo1\')">Demo1</a>',
								href : '#Demo1',
								tags : [ '0' ]
							},
							{
								text : 'Demo2',
								href : '#',
								tags : [ '4' ],
								nodes : [
										{
											text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=CD_DATASOURCE&menuCode=001&pageName=Demo21\')">Demo21</a>',
											href : '#Demo21',
											tags : [ '0' ]
										},
										{
											text : '<a href="#" onclick="javascript:onclickmenu(\'singleTableModify.jsp?pageCode=CD_DATASOURCE&menuCode=002&pageName=Demo22\')">Demo22</a>',
											href : '#Demo22',
											tags : [ '0' ]
										} ]
							} ]
				} ]
			} ];

	var alternateData = [ {
		text : 'Parent 1',
		tags : [ '2' ],
		nodes : [ {
			text : 'Child 1',
			tags : [ '3' ],
			nodes : [ {
				text : 'Grandchild 1',
				tags : [ '6' ]
			}, {
				text : 'Grandchild 2',
				tags : [ '3' ]
			} ]
		}, {
			text : 'Child 2',
			tags : [ '3' ]
		} ]
	}, {
		text : 'Parent 2',
		tags : [ '7' ]
	}, {
		text : 'Parent 3',
		icon : 'glyphicon glyphicon-earphone',
		href : '#demo',
		tags : [ '11' ]
	}, {
		text : 'Parent 4',
		icon : 'glyphicon glyphicon-cloud-download',
		href : '/demo.html',
		tags : [ '19' ],
		selected : true
	}, {
		text : 'Parent 5',
		icon : 'glyphicon glyphicon-certificate',
		color : 'pink',
		backColor : 'red',
		href : 'http://www.tesco.com',
		tags : [ 'available', '0' ]
	} ];

	var json = '[' + '{' + '"text": "Parent 1",' + '"nodes": [' + '{'
			+ '"text": "Child 1",' + '"nodes": [' + '{'
			+ '"text": "Grandchild 1"' + '},' + '{' + '"text": "Grandchild 2"'
			+ '}' + ']' + '},' + '{' + '"text": "Child 2"' + '}' + ']' + '},'
			+ '{' + '"text": "Parent 2"' + '},' + '{' + '"text": "Parent 3"'
			+ '},' + '{' + '"text": "Parent 4"' + '},' + '{'
			+ '"text": "Parent 5"' + '}' + ']';

	$('#treeview1').treeview({
		enableLinks : true,
		color : "blue",
		backColor : "white",
		onhoverColor : "#00F5FF",
		borderColor : "white",
		selectedColor : "blue",
		selectedBackColor : "#7AC5CD",
		data : defaultData
	});

	$('#treeview2').treeview({
		levels : 1,
		data : defaultData
	});

	$('#treeview3').treeview({
		levels : 99,
		data : defaultData
	});

	$('#treeview4').treeview({

		color : "#428bca",
		data : defaultData
	});

	$('#treeview5').treeview({
		color : "#428bca",
		expandIcon : 'glyphicon glyphicon-chevron-right',
		collapseIcon : 'glyphicon glyphicon-chevron-down',
		nodeIcon : 'glyphicon glyphicon-bookmark',
		data : defaultData
	});

	$('#treeview6').treeview({
		color : "#428bca",
		expandIcon : "glyphicon glyphicon-stop",
		collapseIcon : "glyphicon glyphicon-unchecked",
		nodeIcon : "glyphicon glyphicon-user",
		showTags : true,
		data : defaultData
	});

	$('#treeview7').treeview({
		color : "#428bca",
		showBorder : false,
		data : defaultData
	});

	$('#treeview8').treeview({
		expandIcon : "glyphicon glyphicon-stop",
		collapseIcon : "glyphicon glyphicon-unchecked",
		nodeIcon : "glyphicon glyphicon-user",
		color : "yellow",
		backColor : "purple",
		onhoverColor : "orange",
		borderColor : "red",
		showBorder : false,
		showTags : true,
		highlightSelected : true,
		selectedColor : "yellow",
		selectedBackColor : "darkorange",
		data : defaultData
	});

	$('#treeview9').treeview({
		expandIcon : "glyphicon glyphicon-stop",
		collapseIcon : "glyphicon glyphicon-unchecked",
		nodeIcon : "glyphicon glyphicon-user",
		color : "yellow",
		backColor : "purple",
		onhoverColor : "orange",
		borderColor : "red",
		showBorder : false,
		showTags : true,
		highlightSelected : true,
		selectedColor : "yellow",
		selectedBackColor : "darkorange",
		data : alternateData
	});

	$('#treeview10').treeview({
		color : "#428bca",
		enableLinks : true,
		data : defaultData
	});

	var $searchableTree = $('#treeview-searchable').treeview({
		data : defaultData,
	});

	var search = function(e) {
		var pattern = $('#input-search').val();
		var options = {
			ignoreCase : $('#chk-ignore-case').is(':checked'),
			exactMatch : $('#chk-exact-match').is(':checked'),
			revealResults : $('#chk-reveal-results').is(':checked')
		};
		var results = $searchableTree.treeview('search', [ pattern, options ]);

		var output = '<p>' + results.length + ' matches found</p>';
		$.each(results, function(index, result) {
			output += '<p>- ' + result.text + '</p>';
		});
		$('#search-output').html(output);
	}

	$('#btn-search').on('click', search);
	$('#input-search').on('keyup', search);

	$('#btn-clear-search').on('click', function(e) {
		$searchableTree.treeview('clearSearch');
		$('#input-search').val('');
		$('#search-output').html('');
	});

	var initSelectableTree = function() {
		return $('#treeview-selectable').treeview(
				{
					data : defaultData,
					multiSelect : $('#chk-select-multi').is(':checked'),
					onNodeSelected : function(event, node) {
						$('#selectable-output').prepend(
								'<p>' + node.text + ' was selected</p>');
					},
					onNodeUnselected : function(event, node) {
						$('#selectable-output').prepend(
								'<p>' + node.text + ' was unselected</p>');
					}
				});
	};
	var $selectableTree = initSelectableTree();

	var findSelectableNodes = function() {
		return $selectableTree.treeview('search', [
				$('#input-select-node').val(), {
					ignoreCase : false,
					exactMatch : false
				} ]);
	};
	var selectableNodes = findSelectableNodes();

	$('#chk-select-multi:checkbox').on('change', function() {
		console.log('multi-select change');
		$selectableTree = initSelectableTree();
		selectableNodes = findSelectableNodes();
	});

	// Select/unselect/toggle nodes
	$('#input-select-node').on('keyup', function(e) {
		selectableNodes = findSelectableNodes();
		$('.select-node').prop('disabled', !(selectableNodes.length >= 1));
	});

	$('#btn-select-node.select-node').on('click', function(e) {
		$selectableTree.treeview('selectNode', [ selectableNodes, {
			silent : $('#chk-select-silent').is(':checked')
		} ]);
	});

	$('#btn-unselect-node.select-node').on('click', function(e) {
		$selectableTree.treeview('unselectNode', [ selectableNodes, {
			silent : $('#chk-select-silent').is(':checked')
		} ]);
	});

	$('#btn-toggle-selected.select-node').on('click', function(e) {
		$selectableTree.treeview('toggleNodeSelected', [ selectableNodes, {
			silent : $('#chk-select-silent').is(':checked')
		} ]);
	});

	var $expandibleTree = $('#treeview-expandible').treeview(
			{
				data : defaultData,
				onNodeCollapsed : function(event, node) {
					$('#expandible-output').prepend(
							'<p>' + node.text + ' was collapsed</p>');
				},
				onNodeExpanded : function(event, node) {
					$('#expandible-output').prepend(
							'<p>' + node.text + ' was expanded</p>');
				}
			});

	var findExpandibleNodess = function() {
		return $expandibleTree.treeview('search', [
				$('#input-expand-node').val(), {
					ignoreCase : false,
					exactMatch : false
				} ]);
	};
	var expandibleNodes = findExpandibleNodess();

	// Expand/collapse/toggle nodes
	$('#input-expand-node').on('keyup', function(e) {
		expandibleNodes = findExpandibleNodess();
		$('.expand-node').prop('disabled', !(expandibleNodes.length >= 1));
	});

	$('#btn-expand-node.expand-node').on('click', function(e) {
		var levels = $('#select-expand-node-levels').val();
		$expandibleTree.treeview('expandNode', [ expandibleNodes, {
			levels : levels,
			silent : $('#chk-expand-silent').is(':checked')
		} ]);
	});

	$('#btn-collapse-node.expand-node').on('click', function(e) {
		$expandibleTree.treeview('collapseNode', [ expandibleNodes, {
			silent : $('#chk-expand-silent').is(':checked')
		} ]);
	});

	$('#btn-toggle-expanded.expand-node').on('click', function(e) {
		$expandibleTree.treeview('toggleNodeExpanded', [ expandibleNodes, {
			silent : $('#chk-expand-silent').is(':checked')
		} ]);
	});

	// Expand/collapse all
	$('#btn-expand-all').on('click', function(e) {
		var levels = $('#select-expand-all-levels').val();
		$expandibleTree.treeview('expandAll', {
			levels : levels,
			silent : $('#chk-expand-silent').is(':checked')
		});
	});

	$('#btn-collapse-all').on('click', function(e) {
		$expandibleTree.treeview('collapseAll', {
			silent : $('#chk-expand-silent').is(':checked')
		});
	});

	var $checkableTree = $('#treeview-checkable').treeview(
			{
				data : defaultData,
				showIcon : false,
				showCheckbox : true,
				onNodeChecked : function(event, node) {
					$('#checkable-output').prepend(
							'<p>' + node.text + ' was checked</p>');
				},
				onNodeUnchecked : function(event, node) {
					$('#checkable-output').prepend(
							'<p>' + node.text + ' was unchecked</p>');
				}
			});

	var findCheckableNodess = function() {
		return $checkableTree.treeview('search', [
				$('#input-check-node').val(), {
					ignoreCase : false,
					exactMatch : false
				} ]);
	};
	var checkableNodes = findCheckableNodess();

	// Check/uncheck/toggle nodes
	$('#input-check-node').on('keyup', function(e) {
		checkableNodes = findCheckableNodess();
		$('.check-node').prop('disabled', !(checkableNodes.length >= 1));
	});

	$('#btn-check-node.check-node').on('click', function(e) {
		$checkableTree.treeview('checkNode', [ checkableNodes, {
			silent : $('#chk-check-silent').is(':checked')
		} ]);
	});

	$('#btn-uncheck-node.check-node').on('click', function(e) {
		$checkableTree.treeview('uncheckNode', [ checkableNodes, {
			silent : $('#chk-check-silent').is(':checked')
		} ]);
	});

	$('#btn-toggle-checked.check-node').on('click', function(e) {
		$checkableTree.treeview('toggleNodeChecked', [ checkableNodes, {
			silent : $('#chk-check-silent').is(':checked')
		} ]);
	});

	// Check/uncheck all
	$('#btn-check-all').on('click', function(e) {
		$checkableTree.treeview('checkAll', {
			silent : $('#chk-check-silent').is(':checked')
		});
	});

	$('#btn-uncheck-all').on('click', function(e) {
		$checkableTree.treeview('uncheckAll', {
			silent : $('#chk-check-silent').is(':checked')
		});
	});

	var $disabledTree = $('#treeview-disabled').treeview(
			{
				data : defaultData,
				onNodeDisabled : function(event, node) {
					$('#disabled-output').prepend(
							'<p>' + node.text + ' was disabled</p>');
				},
				onNodeEnabled : function(event, node) {
					$('#disabled-output').prepend(
							'<p>' + node.text + ' was enabled</p>');
				},
				onNodeCollapsed : function(event, node) {
					$('#disabled-output').prepend(
							'<p>' + node.text + ' was collapsed</p>');
				},
				onNodeUnchecked : function(event, node) {
					$('#disabled-output').prepend(
							'<p>' + node.text + ' was unchecked</p>');
				},
				onNodeUnselected : function(event, node) {
					$('#disabled-output').prepend(
							'<p>' + node.text + ' was unselected</p>');
				}
			});

	var findDisabledNodes = function() {
		return $disabledTree.treeview('search', [
				$('#input-disable-node').val(), {
					ignoreCase : false,
					exactMatch : false
				} ]);
	};
	var disabledNodes = findDisabledNodes();

	// Expand/collapse/toggle nodes
	$('#input-disable-node').on('keyup', function(e) {
		disabledNodes = findDisabledNodes();
		$('.disable-node').prop('disabled', !(disabledNodes.length >= 1));
	});

	$('#btn-disable-node.disable-node').on('click', function(e) {
		$disabledTree.treeview('disableNode', [ disabledNodes, {
			silent : $('#chk-disable-silent').is(':checked')
		} ]);
	});

	$('#btn-enable-node.disable-node').on('click', function(e) {
		$disabledTree.treeview('enableNode', [ disabledNodes, {
			silent : $('#chk-disable-silent').is(':checked')
		} ]);
	});

	$('#btn-toggle-disabled.disable-node').on('click', function(e) {
		$disabledTree.treeview('toggleNodeDisabled', [ disabledNodes, {
			silent : $('#chk-disable-silent').is(':checked')
		} ]);
	});

	// Expand/collapse all
	$('#btn-disable-all').on('click', function(e) {
		$disabledTree.treeview('disableAll', {
			silent : $('#chk-disable-silent').is(':checked')
		});
	});

	$('#btn-enable-all').on('click', function(e) {
		$disabledTree.treeview('enableAll', {
			silent : $('#chk-disable-silent').is(':checked')
		});
	});

	var $tree = $('#treeview12').treeview({
		data : json
	});
});
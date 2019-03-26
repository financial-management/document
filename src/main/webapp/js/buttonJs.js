
var buttonHtmlForButton ='<button type="button" class="btn btn-default" buttonToken="{buttonToken}" onclick="{fun}">{name}</button>';
var buttonHtmlForSelect ='<button type="button" class="btn btn-primary" buttonToken="{buttonToken}" onclick="{fun}">{name}</button>';
var buttonHtmlForImgTemp='<div class="button-group {buttonClass}" buttonToken="{buttonToken}" onclick="{fun}"> <span class="img" ></span> <span>{name}</span> </div>' ;
var buttonHtmlForImg='<div class="button-group {SPAN_CSS}" buttontoken="{BUTTON_TOKEN}" onclick="{HTML_CLICK_NAME}" title="{BUTTON_DESCRIPTION}" buttonType="{HTML_POSITION}" isCheckbox="{ISCHECKBOX}" isHidden="{ISHIDDEN}"><span class="img"></span><span>{BUTTON_NAME}</span></div>';
	
var buttonHtml="";

var imgType = {
			  '查询':'sel',
			  '新增':'ad',
              '修改':'up',
              '删除':'de',
              '提交':'su',
              '审批':'au',
              '复制':'f',
              '修改本人':'xgbr',
              '按钮授权':'ansq',
              '绑定':'bind',
              '保存':'save',
              '保存super':'saveSuper',
              '变更申请单':'bgsqd',
              '驳回报账':'bhbz',
              '布局切换':'bjqh',
              '查看':'ck',
              '查看关系树':'ckgxs',
              '查看明细':'ckmx',
              '查看说明':'cksm',
              '撤销勾兑':'cxgd',
              '处理完成':'clwc',
              '存储':'store',
              '打印':'print',
              '导出':'export',
              '导出当页':'exportCur',
              '导出全部':'exportAll',
              '导入':'import',
              '冻结申请单':'djsqd',
              '返回':'back',
              '复制本行':'fzbh',
              '更改基础信息':'chgeBaseInfo',
              '更改扩展信息':'ggkzxx',
              '回收':'recovery',
              '回填':'backfill',
              '回填销户日期':'htxhrq',
              '激活':'activate',
              '检查数据':'checkData',
              '角色设置':'roleSet',
              '解绑':'unBind',
              '开立回填单':'klhtd',
              '类型数据':'lxsj',
              '联查单据':'lcdj',
              '联查回填单':'lchtd',
              '联查审批流':'lcspl',
              '密码策略':'mmcl',
              '密码重置':'mmcz',
              '明细列表':'detailList',
              '末行':'mh',
              '纳入自动归集体系':'nrzdgjtx',
              '批量导入':'pldr',
              '批量回填':'plht',
              '清除':'clear',
              '清空':'empty',
              '取消':'cancel',
              '确认生效':'qrsx',
              '确认支付':'qrzf',
              '确认执行':'qrzx',
              '上一步':'syb',
              '上一行':'syh',
              '设置互斥角色':'szhcjs',
              '生成支付确认单':'sczfqrd',
              '收益测算':'sycs',
              '手动对账':'sddz',
              '手工下载':'sgxz',
              '首行':'firstRow',
              '授权':'grant',
              '搜索器':'searcher',
              '锁定':'lock',
              '提交至财务公司':'subToFin',
              '提交至银行':'subToBank',
              '添加角色':'addRole',
              '添加用户':'addUser',
              '停用效果预览':'tyxgyl',
              '网银补录':'wybl',
              '物理新增':'wlAdd',
              '物理修改':'wlUpdate',
              '下一步':'nextStep',
              '下一行':'nextRow',
              '线下支付':'xxzf',
              '销户申请单':'xhsqd',
              '新增部门':'addDept',
              '新增菜单':'addMenu',
              '新增公司':'addCompany',
              '新增指标':'addQuota',
              '修改本机构':'xgbjg',
              '修改所有':'updateAll',
              '验证信息同步状态':'yzxxtbzt',
              '页面明细':'detail',
              '用户设置':'userSetting',
              '预览':'preview',
              '元数据字段':'ysjzd',
              '在线查询':'onlineSel',
              '账户信息同步ERP':'infoSynERP',
              '支付':'payment',
              '支取':'draw',
              '重新评定':'review',
              '重置':'reset',
              '重置全部':'resetAll',
              '自动对账':'autoRec',
              '作废':'void',
              '存储过程':'ccgc',
              '电子支付':'dzzf',
              '柜台支付':'gtzf',
              '领用':'lingy',
              '年检审核':'njsh',
              '取消导入':'unExport',
              '确认':'confirm',
              '设置':'setting',
              '停用':'stop',
              '退回':'return',
              '效果预览':'xgyl',
              '遗失':'lost',
              '整单驳回':'zdbh'
              };

var initButtonJson =[
                     {name:'查询',fun:'queryTable(this)',buttonToken:'query'},
                     {name:'新增',fun:'tog(this)',buttonToken:'add'},
                     {name:'修改',fun:'updateRow(this)',buttonToken:'update'},
                     {name:'删除',fun:'delRows(this)',buttonToken:'delete'}
                    ];

function bulidButtonHtml($button,dataSource){
	//修改查询按钮位置
	if ($("#isNewStyle").val() != "1") {
		buttonHtml=buttonHtmlForButton;
	}else{
		$button.attr("class","button-menu");
		buttonHtml=buttonHtmlForImg;
	}
	findButtonHtmlByJs($button,dataSource);
}

function findButtonHtmlByJs($button,dataSource){
	var buttonTypeVal =$button.attr("buttonType");
	
	if(isBuildButtonByFile ){
		loadScript(findBusJsUrl(dataSource),function(){  //加载test.js,成功后，并执行回调函数
			if(buttonJson==null||buttonJson.length==0){
				buttonJson=initButtonJson;
			}
			if ($("#isNewStyle").val() != "1") {
				buttonHtml=buttonHtmlForButton;
			}else{
				$button.attr("class","button-menu");
				buttonHtml = buttonHtmlForImgTemp ; 
			}
			$button.html(bulidHtmltemp($button,buttonTypeVal,buttonJson));
			loadButtonJsEnd();
			//修改查询按钮位置
			appendBulidSelect();
			
		});
	}else{
		$button.html(bulidHtml($button,buttonTypeVal,buttonJson));
		loadButtonJsEnd();
		//修改查询按钮位置
		appendBulidSelect();
	}
	showBillSelectHtml(dataSourceCode);
}

function loadButtonJsEnd(){
	if(typeof(loadJsFunction) == "function"){
		loadJsFunction();
	}
}

function bulidHtmltemp($button,buttonTypeVal,jsonByJs){
	var html ='';
	
	for(var i =0;i<jsonByJs.length;i++){
		var buttonClass='xz';
		
		if(imgType[jsonByJs[i]["name"]]!=null){
			buttonClass=imgType[jsonByJs[i]["name"]];
		}
		jsonByJs[i]["buttonClass"]=buttonClass;
		
		if(buttonTypeVal!=null&&buttonTypeVal.length>0){
			if(jsonByJs[i]["buttonType"]==buttonTypeVal){
				if(isLoadSelectButton(jsonByJs[i]["name"])){
					html=html+buttonHtml.format(jsonByJs[i])+"&nbsp;";
				}
			}
		}else{
			if(isLoadSelectButton(jsonByJs[i]["name"])){
				html=html+buttonHtml.format(jsonByJs[i])+"&nbsp;";
			}
		}
		
	}
	return html;
}

function bulidHtml($button,buttonTypeVal,jsonByJs){
	var html ='';
	if( null == jsonByJs){
		console.log('没有获取到按钮信息！');
		return ; 
	}
	for(var i =0;i<jsonByJs.length;i++){
		var buttonClass='xz';
		if(imgType[jsonByJs[i]["BUTTON_NAME"]]!=null){
			buttonClass=imgType[jsonByJs[i]["BUTTON_NAME"]];
		}
		jsonByJs[i]["SPAN_CSS"]=buttonClass;
		
		if(buttonTypeVal!=null&&buttonTypeVal.length>0){
			if(jsonByJs[i]["HTML_POSITION"]==buttonTypeVal){
				if(isLoadSelectButton(jsonByJs[i]["BUTTON_NAME"])){
					html=html+buttonHtml.format(jsonByJs[i])+"&nbsp;";
				}
			}
		}else{
			if(isLoadSelectButton(jsonByJs[i]["BUTTON_NAME"])){
				html=html+buttonHtml.format(jsonByJs[i])+"&nbsp;";
			}
		}
	}
	return html;
}



function bulidHtmlBySelect(jsonByJs){
	var html ='';
	for(var i =0;i<jsonByJs.length;i++){
		if(jsonByJs[i]["name"]=='查询'){
			html=html+buttonHtmlForSelect.format(jsonByJs[i])+"&nbsp;";
		}
	}
	return html;
}


function buttonToServer(url,jsonData){
	var message;
	var jsNode;
	$.ajax({
		async: false,
		type: "post",
		url: url,
		dataType: "json",
		data:{"jsonData":jsonData},
		success: function(data){
			message = data['message'];
			jsNode =data['js'];
			},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			message ="请求失败";
			}
		});
	writeJs(jsNode);
	return message;
}

function writeJs(jsNode){
	 var myScript= document.createElement("script");
     myScript.type = "text/javascript";
     myScript.appendChild(document.createTextNode(jsNode));
     document.body.appendChild(myScript)
}

function isLoadSelectButton(name){
	var returnType=true;
	if(false){
		if(name=='查询'){
			returnType=!returnType;
		}
	}
	return returnType
}

function appendBulidSelect(){
	if(false){
		var selectButtonPage=$("#selectButtonPage").html();
		$("#selectButtonPage").html(bulidHtmlBySelect(buttonJson)+selectButtonPage);
	}
}


function showBillSelectHtml( dataSourceCode){
	$("#bill_date_and_status").append(getBillStatus(dataSourceCode));
	billDateOnclick();
	billStatusOnclick();
}

function billDateOnclick(dateField){
	var dateField = "BILL_DATE" ; 
	var dateFrom = dateField+'_FROM' ; 
	var dateTo = dateField+'_TO' ; 
	$("#bill_date_div span").each(function(index) {
		$(this).click(function() {
			$("#bill_date_div span").removeClass('badge');
			$(this).addClass('badge');
			var thisId = $(this).attr("id");
			if('bill_date_noLimit'==thisId){
				$("#queryParam  input[id*='"+dateFrom+"']").val("");
				$("#queryParam  input[id*='"+dateTo+"']").val("");
			}
			if('bill_date_today'==thisId){
				$("#queryParam  input[id*='"+dateFrom+"']").val(formatDate(new Date()));
			}
			if('bill_date_this_week'==thisId){
				$("#queryParam  input[id*='"+dateFrom+"']").val(getWeekStartDate());
			}
			if('bill_date_this_month'==thisId){
				$("#queryParam  input[id*='"+dateFrom+"']").val(getMonthStartDate());
			}
			queryTable($("#selectButtonPage button[buttontoken='query']").get(0));
		});
	});
}

function billStatusOnclick(){
	var statusField = 'BILL_STATUS';
	$("#bill_status_div span").each(function(index) {
		$(this).click(function() {
			$("#bill_status_div span").removeClass('badge');
			$(this).addClass('badge');
			$("#queryParam  [name*='"+statusField+"']").val($(this).attr("value"));
			queryTable($("#selectButtonPage button[buttontoken='query']").get(0));
		});
	});
}

function getBillStatus( dataSourceCode ){
	var message  ; 
	var url = context+'/billQuery?cmd=getBillSelect&dataSourceCode='+dataSourceCode;
	$.ajax({
		async: false,
		type: "post",
		url: url,
		dataType: "text",
		data:{"jsonData":null},
		success: function(data){
			message = data;
			},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			message ="请求失败";
			}
		});
		if(message.indexOf('>') == -1){
			return message ; 
		}
	return  JSON.parse(message)  ;
}

function moreToggle(){
	$("#queryParam").toggle();
	setParntHeigth($(document.body).height());
}

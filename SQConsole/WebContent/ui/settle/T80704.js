Ext.onReady(function() {
	/*var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false,
				dataIndex : "id",
				listeners : {//auditStatus
					'beforerowselect' : function(sumColModel, rowIndex, keepExisting, record) {
						if ((record.data.saStatus) == '1' || (record.data.saStatus) == '2' ) {
							return false; // 不能进行选择
						} else {
							return true;
						}
					}
				}
	});*/
	var sm = new Ext.grid.CheckboxSelectionModel();
	var sumGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=sumrzInfUpYQauditS'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'seqNo'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'seqNo',mapping: 'seqNo'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'accFlag',mapping: 'accFlag'},
			{name: 'auditStatus',mapping: 'auditStatus'},
			{name: 'auditId',mapping: 'auditId'},
			{name: 'auditDate',mapping: 'auditDate'},
			{name: 'recId',mapping: 'recId'},
			{name: 'recDate',mapping: 'recDate'},
			{name: 'settleAccName',mapping: 'settleAccName'},
			{name: 'settleAccNum',mapping: 'settleAccNum'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'handAmt',mapping: 'handAmt'},
			{name: 'sumAmt',mapping: 'sumAmt'},
			{name: 'saStatus',mapping: 'saStatus'},
			{name: 'sumrzDate',mapping: 'sumrzDate'},
			{name: 'sumrzNote',mapping: 'sumrzNote'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'dirOpenBank',mapping: 'dirOpenBank'},
			{name: 'dirBankProvince',mapping: 'dirBankProvince'},
			{name: 'dirBankCity',mapping: 'dirBankCity'},
			{name: 'compOpenBank',mapping: 'compOpenBank'},
			{name: 'compBankProvince',mapping: 'compBankProvince'},
			{name: 'compBankCity',mapping: 'compBankCity'},
			{name: 'corpOpenBank',mapping: 'corpOpenBank'},
			{name: 'corpBankProvince',mapping: 'corpBankProvince'},
			{name: 'corpBankCity',mapping: 'corpBankCity'},//settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			{name: 'settleRpt',mapping: 'settleRpt'},//结算账户类型
			{name: 'companyNam',mapping: 'companyNam'},//对公账号名称
			{name: 'settleAcct',mapping: 'settleAcct'},//对公账号
			{name: 'legalNam',mapping: 'legalNam'},//对私账号名称
			{name: 'feeAcct',mapping: 'feeAcct'},//对私账号
			{name: 'dirAccountName',mapping: 'dirAccountName'},//定向委托账号名称
			{name: 'dirAccount',mapping: 'dirAccount'},//定向委托账号
			
			{name: 'compAccountBankCode',mapping: 'compAccountBankCode'},//对公账户开户行行号
			{name: 'bankAccountCode',mapping: 'bankAccountCode'},//对私账户开户行行号
			
			{name: 'causeStat',mapping: 'causeStat'},//银行返回值
			
			{name: 'acctBankCode',mapping: 'acctBankCode'},//银行行号代码
		])
	});
	
	sumGridStore.load({
		params:{start: 0}
	});
	
	var sumColModel = new Ext.grid.ColumnModel([
		sm,
		{header: '结算日期',dataIndex: 'instDate',width:200},
		{header: '主键',dataIndex: 'seqNo',hidden:true},
		{header: '商户号',dataIndex: 'mchtNm',width:200,align: 'center'},
		{header: '账户类型',dataIndex: 'settleRpt',renderer: settleRptVal,align: 'center'},
		{header: '结算账户',dataIndex: 'settleAccName',align: 'center'},
		{header: '结算账号',dataIndex: 'settleAccNum',align: 'center'},
		{header: '开户行',dataIndex: 'bankName',align: 'center'},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center'},
		{header: '手续费',dataIndex: 'handAmt',align: 'center'},
		{header: '结算金额',dataIndex: 'sumAmt',align: 'center'},
		{header: '支付状态',dataIndex: 'causeStat',width:300,align: 'center'},
		{header: '划款状态',dataIndex: 'saStatus',renderer: saStateVal,align: 'center'},
		{header: '审核状态',dataIndex: 'auditStatus',renderer: saStateValAudit,align: 'center'},
		{header: '发起划款人',dataIndex: 'auditId',align: 'center'},
		{header: '发起划款日期',dataIndex: 'auditDate',width: 100,renderer: formatDt,align: 'center'},
		{header: '审核划款人',dataIndex: 'recId',align: 'center'},
		{header: '审核划款日期',dataIndex: 'recDate',width: 100,renderer: formatDt,align: 'center'},
		{header: '划款日期',dataIndex: 'sumrzDate',width: 100,renderer: formatDt,align: 'center'},
		{header: '备注',dataIndex: 'sumrzNote',align: 'center',width:100,id:'sumrzNoteId'},
		{header: '定向委托账号开户总行名称',dataIndex: 'dirOpenBank',width:200,align: 'center'},
		{header: '定向委托账号开户行所在省',dataIndex: 'dirBankProvince',width:200,align: 'center'},
		{header: '定向委托账号开户行所在市',dataIndex: 'dirBankCity',width:200,align: 'center'},
		{header: '对公账号开户总行名称',dataIndex: 'compOpenBank',width:200,align: 'center'},
		{header: '对公账号开户行所在省',dataIndex: 'compBankProvince',width:200,align: 'center'},
		{header: '对公账号开户行所在市',dataIndex: 'compBankCity',width:200,align: 'center'},
		{header: '对私账号开户总行名称',dataIndex: 'corpOpenBank',width:200,align: 'center'},
		{header: '对私账号开户行所在省',dataIndex: 'corpBankProvince',width:200,align: 'center'},
		{header: '对私账号开户行所在市',dataIndex: 'corpBankCity',width:200,align: 'center'},
		{header: '结算账户类型',dataIndex: 'settleRpt',width:200,align: 'center',hidden:true},
		{header: '对公账号名称',dataIndex: 'companyNam',width:200,align: 'center',hidden:true},
		{header: '对公账号',dataIndex: 'settleAcct',width:200,align: 'center',hidden:true},
		{header: '对私账号名称',dataIndex: 'legalNam',width:200,align: 'center',hidden:true},
		{header: '对私账号',dataIndex: 'feeAcct',width:200,align: 'center',hidden:true},
		{header: '定向委托账号名称',dataIndex: 'dirAccountName',width:200,align: 'center',hidden:true},
		{header: '定向委托账号',dataIndex: 'dirAccount',width:200,align: 'center',hidden:true},
		
		{header: '对公账户开户行行号',dataIndex: 'compAccountBankCode',width:200,align: 'center',hidden:true},
		{header: '对私账户开户行行号',dataIndex: 'bankAccountCode',width:200,align: 'center',hidden:true},
	]);
	
	function settleRptVal(val){
		if(val=='1'){
			return '对私账户';
		}
		if(val=='2'){
			return '对公账户';
		}
		if(val=='3'){
			return '定向委托账户';
		}
	}
	
	function saStateVal(val){
		if(val=='0'){
			return '<font color="red">失败</font>';
		}
		if(val=='1'){
			return '<font color="green">成功</font>';
		}
		if(val=='2'){
			return '<font color="green">划款中</font>';
		}
	}
	//审核状态
	function saStateValAudit(val){
		if(val=='0'){
			return '<font color="red">新增划款</font>';
		}
		if(val=='1'){
			return '<font color="green">划款审核通过</font>';
		}
		if(val=='2'){
			return '<font color="red">划款审核拒绝</font>';
		}
		if(val=='3'){
			return '<font color="red">新增二次划款</font>';
		}
		if(val=='4'){
			return '<font color="green">二次划款审核通过</font>';
		}
		if(val=='5'){
			return '<font color="red">二次划款审核拒绝</font>';
		}
	}
	/****************商户划款*********************/
	var sucAddForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
	/*	items: [{
			xtype: 'htmleditor',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '划款日期*',
			allowBlank: false,
			emptyText:'格式：yyyymmdd',
			id:'sumrzDateId',
			name: 'sumrzDateId',
			width: 120
		}],*/
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(sucAddForm.getForm().isValid()) {
					var recs = sumGrid.getSelectionModel().getSelections();
					var array = new Array();
					for(var index = 0; index < recs.length; index++) {
						var record = recs[index];
						var data = {
								seqNo: record.get('seqNo')
						};
						array.push(data);
					}
					sucAddForm.getForm().submit({
						url: 'T80701Action_remittance.asp',
						waitMsg: '正在商户划款表信息，请稍候......',
						params: {
							infList: Ext.encode(array),
//							sumrzDate:typeof(Ext.getCmp('sumrzDateId').getValue()) == 'string' ? '' : Ext.getCmp('sumrzDateId').getValue().dateFormat('Ymd'),
							txnId: '80224',
							subTxnId: '01'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,sucAddForm);
							sucAddWin.hide();
							sumGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,sucAddForm);
						}
					});
				}
			}
		}, {
			text: '关闭',
			handler: function() {
				sucAddWin.hide();			
			}
		}]
	});
	
	var sucAddWin = new Ext.Window({
		title : '商户结算划款',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 300,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ sucAddForm ]
	});
    /****************end******************/
	
	/****************失败回填******************/
	var falseAddForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '索引号',
			width:150,
			hideLabel :true,
			hidden : true,
			id: 'seqNo'
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '划款日期*',
			allowBlank: false,
			emptyText:'格式：yyyymmdd',
			id:'sumrzDate',
			name: 'sumrzDate',
			width: 120
		},{
	       xtype: 'textarea',
		   labelStyle: 'padding-left: 5px',
			fieldLabel: '备注*',
			allowBlank: false,
			id: 'sumrzNote',
			name: 'sumrzNote',
			maxLength: 200,
			width:120
		  }],
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(falseAddForm.getForm().isValid()) {										
                   
					falseAddForm.getForm().submit({
						url: 'T80701Action_falseAdd.asp',
						waitMsg: '正在商户划款信息，请稍候......',
						params: {
							txnId: '80224',
							subTxnId: '02'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,falseAddForm);
							falseAddWin.hide();
							sumGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,falseAddForm);
						}
					});
				}
			}
		}, {
			text: '重填',
			handler: function() {
			    var falseRecords = sumGrid.getSelectionModel().getSelections();
				falseAddForm.getForm().reset();
				falseAddForm.getForm().loadRecord(falseRecords[0]);
				falseAddForm.getForm().clearInvalid();			
			}
		}]
	});
	
	
	var falseAddWin = new Ext.Window({
		title : '划款失败回填',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 300,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ falseAddForm ]
	});
	
	 /****************end******************/
	
	// 菜单集合
	var menuArr = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
		
		/**
 ** 加法函数，用来得到精确的加法结果
 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
 ** 调用：accAdd(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
	function accAdd(arg1, arg2) {
		var r1, r2, m, c;
		try {
			r1 = arg1.toString().split(".")[1].length;
		} catch (e) {
			r1 = 0;
		}
		try {
			r2 = arg2.toString().split(".")[1].length;
		} catch (e) {
			r2 = 0;
		}
		c = Math.abs(r1 - r2);
		m = Math.pow(10, Math.max(r1, r2));
		if (c > 0) {
			var cm = Math.pow(10, c);
			if (r1 > r2) {
				arg1 = Number(arg1.toString().replace(".", ""));
				arg2 = Number(arg2.toString().replace(".", "")) * cm;
			} else {
				arg1 = Number(arg1.toString().replace(".", "")) * cm;
				arg2 = Number(arg2.toString().replace(".", ""));
			}
		} else {
			arg1 = Number(arg1.toString().replace(".", ""));
			arg2 = Number(arg2.toString().replace(".", ""));
		}
		return (arg1 + arg2) / m;
	}

	// 给Number类型增加一个add方法，调用起来更加方便。
	Number.prototype.add = function(arg) {
		return accAdd(arg, this);
	};
		//拒绝
		var sucMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			handler: function() {
			var sucRecords = sumGrid.getSelectionModel().getSelections();
					var amount = 0;
					for(var i = 0; i < sucRecords.length; i++){
						var count = sucRecords[i];
						amount = accAdd(count.get('sumAmt'), amount);
					}
					showConfirm('你<font color="red">审核</font>'+sucRecords.length+'条记录，金额：'+ amount +'.确认拒绝！',sumGrid,function(bt) {
						if(bt == 'yes') {
						if(sucRecords.length == 0) {
							showErrorMsg("请勾选数据！",sumGrid);
							return;
						}
						showMask("正在提交划款信息，请稍后....",sumGrid);
						var array = new Array();
							for(var index = 0; index < sucRecords.length; index++) {
								var record = sucRecords[index];
								var data = {
										seqNo: record.get('seqNo'),//主键
										mchtNm: record.get('mchtNm'),//商户号
										accFlag: record.get('accFlag'),//账户类型
										settleAccName: record.get('settleAccName'),//结算账户
										settleAccNum: record.get('settleAccNum'),//结算账号
										bankName: record.get('bankName'),//开户行
										txnAmt: record.get('txnAmt'),//交易金额
										handAmt: record.get('handAmt'),//手续费
										sumAmt: record.get('sumAmt'),//结算金额
										saStatus: record.get('saStatus'),//状态
										sumrzDate: record.get('sumrzDate'),//划款日期
										sumrzNote: record.get('sumrzNote'),//备注
										dirOpenBank: record.get('dirOpenBank'),//定向委托账号开户总行名称
										dirBankProvince: record.get('dirBankProvince'),//定向委托账号开户行所在省
										dirBankCity: record.get('dirBankCity'),//定向委托账号开户行所在市
										compOpenBank: record.get('compOpenBank'),//对公账号开户总行名称
										compBankProvince: record.get('compBankProvince'),//对公账号开户行所在省
										compBankCity: record.get('compBankCity'),//对公账号开户行所在市
										corpOpenBank: record.get('corpOpenBank'),//对私账号开户总行名称
										corpBankProvince: record.get('corpBankProvince'),//对私账号开户行所在省
										corpBankCity: record.get('corpBankCity'),//对私账号开户行所在市
										settleRpt: record.get('settleRpt'),//结算账户类型
										companyNam: record.get('companyNam'),//对公账号名称
										settleAcct: record.get('settleAcct'),//对公账号
										legalNam: record.get('legalNam'),//对私账号名称
										feeAcct: record.get('feeAcct'),//对私账号名称
										dirAccountName: record.get('dirAccountName'),//定向委托账号名称
										auditId: record.get('auditId'),//发起划款人
										auditDate: record.get('auditDate')//发起划款日期
								};
								array.push(data);
							}
							Ext.Ajax.request({
							url: 'T80701Action_refuseRem.asp',
							params: {
								infList: Ext.encode(array),
								txnId: '80230',
								subTxnId: '02'
							},
							success: function(rsp,opt) {
								hideMask();
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,sumGrid);
								} else {
									showErrorMsg(rspObj.msg,sumGrid);
								}
								sumGrid.getStore().reload();
							},
							failure: function(){
								hideMask();
							}
						});
						}
					});
			}
		};
		var refuseMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			handler: function() {
			var sucRecords = sumGrid.getSelectionModel().getSelections();
					var amount = 0;
					for(var i = 0; i < sucRecords.length; i++){
						var count = sucRecords[i];
						amount = accAdd(count.get('sumAmt'), amount);
					}
					showConfirm('你<font color="red">审核</font>'+sucRecords.length+'条记录，金额：'+ amount +'.确认通过！',sumGrid,function(bt) {
						if(bt == 'yes') {
						if(sucRecords.length == 0) {
							showErrorMsg("请勾选数据！",sumGrid);
							return;
						}
						showMask("正在提交划款信息，请稍后....",sumGrid);
						var array = new Array();
							for(var index = 0; index < sucRecords.length; index++) {
								var record = sucRecords[index];
								var data = {
										seqNo: record.get('seqNo'),//主键
										mchtNm: record.get('mchtNm'),//商户号
										accFlag: record.get('accFlag'),//账户类型
										settleAccName: record.get('settleAccName'),//结算账户
										settleAccNum: record.get('settleAccNum'),//结算账号
										bankName: record.get('bankName'),//开户行
										txnAmt: record.get('txnAmt'),//交易金额
										handAmt: record.get('handAmt'),//手续费
										sumAmt: record.get('sumAmt'),//结算金额
										saStatus: record.get('saStatus'),//状态
										sumrzDate: record.get('sumrzDate'),//划款日期
										sumrzNote: record.get('sumrzNote'),//备注
										dirOpenBank: record.get('dirOpenBank'),//定向委托账号开户总行名称
										dirBankProvince: record.get('dirBankProvince'),//定向委托账号开户行所在省
										dirBankCity: record.get('dirBankCity'),//定向委托账号开户行所在市
										compOpenBank: record.get('compOpenBank'),//对公账号开户总行名称
										compBankProvince: record.get('compBankProvince'),//对公账号开户行所在省
										compBankCity: record.get('compBankCity'),//对公账号开户行所在市
										corpOpenBank: record.get('corpOpenBank'),//对私账号开户总行名称
										corpBankProvince: record.get('corpBankProvince'),//对私账号开户行所在省
										corpBankCity: record.get('corpBankCity'),//对私账号开户行所在市
										settleRpt: record.get('settleRpt'),//结算账户类型
										companyNam: record.get('companyNam'),//对公账号名称
										settleAcct: record.get('settleAcct'),//对公账号
										legalNam: record.get('legalNam'),//对私账号名称
										feeAcct: record.get('feeAcct'),//对私账号名称
										dirAccountName: record.get('dirAccountName'),//定向委托账号名称
										dirAccount: record.get('dirAccount'),//定向委托账号
										auditId: record.get('auditId'),//发起划款人
										auditDate: record.get('auditDate'),//发起划款日期
										
										acctBankCode: record.get('acctBankCode'),//银行开户行代码
										
										compAccountBankCode: record.get('compAccountBankCode'),//对公账户开户行行号
										bankAccountCode: record.get('bankAccountCode'),//对私账户开户行行号
								};
								array.push(data);
							}
							Ext.Ajax.request({
							url: 'T80701Action_remittance.asp',
							params: {
								infList: Ext.encode(array),
								txnId: '80230',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								hideMask();
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,sumGrid);
								} else {
									showErrorMsg(rspObj.msg,sumGrid);
								}
								sumGrid.getStore().reload();
							},
							failure: function(){
								hideMask();
							}
						});
						}
					});
			}
		};
		
		menuArr.push(queryCondition);  //[0]
		menuArr.push('-');				//[3]
		menuArr.push(refuseMenu); 			//[4]
		menuArr.push('-');				//[1]
		menuArr.push(sucMenu); 			//[2]
		
	// 信息列表
	var sumGrid = new Ext.grid.EditorGridPanel({
		title: '商户结算划款审核',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'sumrzNoteId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sumGridStore,
		sm: sm,
		cm: sumColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sumGridStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/***************************查询条件*************************/
	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 250
		},{
			xtype: 'datefield',
			id: 'instDateId',
			name: 'instDateId',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '清算日期',
			width:150,
			editable: false			
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idAccFlag',
			fieldLabel: '账户类型',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','对私账户'],['2','对公账户']],
				reader: new Ext.data.ArrayReader()
			})
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				sumGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	sumGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			instDate: typeof(queryForm.findById('instDateId').getValue()) == 'string' ? '' : queryForm.findById('instDateId').getValue().dateFormat('Ymd'),
			accFlag: queryForm.getForm().findField('idAccFlag').getValue(),
			mchtNo: queryForm.getForm().findField('mchtNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [sumGrid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCheckInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'flowUpd',mapping: 'flowUpd'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'updTs',mapping: 'updTs'}
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}</p>',
			'<p>申请原因：{partNum}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 80},
		{header: '商户名称',dataIndex: 'mchtNm',width: 180,id: 'mchtNm'},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 100},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 80,renderer: mchntSt},
		{header: '经办人',dataIndex: 'flowUpd',width: 170,id: 'flowUpd'},
		{header: '终端数量',dataIndex: 'termCount',width: 50}
	]);
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 60},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 90},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
		 selectedRecord = mchntGrid.getSelectionModel().getSelected();
		if(selectedRecord.get('mchtStatus')=='1'){
			addAcceptForm.getForm().reset();
			Ext.getCmp('isRoute').checked=true;
			addAcceptWin.show();
		}else{
			showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20201Action.asp?method=accept',
						params: {
							mchntId: rec.get('mchtNo'),
							txnId: '20201',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,mchntGrid);
							} else {
								showErrorMsg(rspObj.msg,mchntGrid);
							}
							// 重新加载商户待审核信息
							mchntGrid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}}
	};
	/******添加审核通过start by zhangqy*******/
	
	var addAcceptForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 140,
		waitMsgTarget: true,
		items: [{

			fieldLabel: '借记卡单笔交易限额',
			allowBlank: true,
//			blankText: '单笔交易金额不能为空',
//			emptyText: '请输入单笔交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingleDebit',
			name: 'daySingleDebit',
			vtype: 'isMoney',
			width: 300
		},
		{
			fieldLabel: '借记卡单日交易金额',
			/*allowBlank: false,
			blankText: '单日交易金额不能为空',*/
//			emptyText: '请输入单日交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmtDebit',
			name: 'dayAmtDebit',
			vtype: 'isMoney',
			width: 300
		},
		{
			fieldLabel: '借记卡单月交易金额',
			/*allowBlank: false,
			blankText: '单日交易金额不能为空',*/
//			emptyText: '请输入单月交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmtDebit',
			name: 'monAmtDebit',
			vtype: 'isMoney',
			width: 300
		},{
			fieldLabel: '贷记卡单笔交易限额*',
			allowBlank: false,
			blankText: '单笔交易金额不能为空',
//			emptyText: '请输入单笔交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingleCredit',
			name: 'daySingleCredit',
			vtype: 'isMoney',
			width: 300
		},
		{
			fieldLabel: '贷记卡单日交易金额*',
			allowBlank: false,
			blankText: '单日交易金额不能为空',
//			emptyText: '请输入单日交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmtCredit',
			name: 'dayAmtCredit',
			vtype: 'isMoney',
			width: 300
		},
		{
			fieldLabel: '贷记卡单月交易金额*',
			allowBlank: false,
			blankText: '单日交易金额不能为空',
//			emptyText: '请输入单月交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmtCredit',
			name: 'monAmtCredit',
			vtype: 'isMoney',
			width: 300				
		},/*{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户组别',
			allowBlank: true,
			hiddenName: 'mchtGrp',
			anchor: '90%',			width: 300,
			listeners: {
			'select': function() {
			Ext.getCmp('idmcc').reset();
			Ext.getCmp('idmcc').parentP=this.value;
			Ext.getCmp('idmcc').getStore().reload();
			}
		    }
		},{
	        xtype: 'dynamicCombo',
		    labelStyle: 'padding-left: 5px',
			fieldLabel: '适用MCC',
			methodName: 'getMcc',
			displayField: 'displayField',
			valueField: 'valueField',
			editable: true,
			allowBlank: true,
			anchor: '90%',			blankText: '请选择商户MCC',
			id: 'idmcc',
			width: 300,
			hiddenName: 'mcc'
		},*/{
			xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '是否开通路由',
			id: 'isRoute',
			name: 'isRoute',
			anchor: '90%'
		},{
			xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '是否加入白名单',
			id: 'iswhite',
			name: 'iswhite',
			anchor: '90%'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(addAcceptForm.getForm().isValid()) {
					
					var daySingleDebit = addAcceptForm.getForm().findField('daySingleDebit').getValue();
                    if(daySingleDebit.indexOf('.') == -1 && daySingleDebit.length>13){//20121115
                    	showErrorMsg("借记卡单日交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                    var dayAmtDebit = addAcceptForm.getForm().findField('dayAmtDebit').getValue();
                    if(dayAmtDebit.indexOf('.') == -1 && dayAmtDebit.length>13){//20121115
                    	showErrorMsg("借记卡单笔交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                   var monAmtDebit = addAcceptForm.getForm().findField('monAmtDebit').getValue();
                   if(monAmtDebit.indexOf('.') == -1 && monAmtDebit.length>13){//20121115
                    	showErrorMsg("借记卡单月交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                    if(Number(dayAmtDebit) < Number(daySingleDebit))
                    {
                        showErrorMsg("[借记卡单日交易金额]不能小于[借记卡单笔交易金额]",addAcceptForm);
                        return;
                    }
                   if(Number(monAmtDebit) < Number(dayAmtDebit))
                    {
                        showErrorMsg("[借记卡单月累计交易金额]不能小于[借记卡单日交易金额]",addAcceptForm);
                        return;
                    }
					var daySingleCredit = addAcceptForm.getForm().findField('daySingleCredit').getValue();
                    if(daySingleCredit.indexOf('.') == -1 && daySingleCredit.length>13){//20121115
                    	showErrorMsg("贷记卡单日交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                    var dayAmtCredit = addAcceptForm.getForm().findField('dayAmtCredit').getValue();
                    if(dayAmtCredit.indexOf('.') == -1 && dayAmtCredit.length>13){//20121115
                    	showErrorMsg("贷记卡单笔交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                   var monAmtCredit = addAcceptForm.getForm().findField('monAmtCredit').getValue();
                   if(monAmtCredit.indexOf('.') == -1 && monAmtCredit.length>13){//20121115
                    	showErrorMsg("贷记卡单月交易金额整数部分不能超过13位，请重新填写",addAcceptForm);
                        return;
                    }
                    if(Number(dayAmtCredit) < Number(daySingleCredit))
                    {
                        showErrorMsg("[贷记卡单日交易金额]不能小于[贷记卡单笔交易金额]",addAcceptForm);
                        return;
                    }
                   if(Number(monAmtCredit) < Number(dayAmtCredit))
                    {
                        showErrorMsg("[贷记卡单月累计交易金额]不能小于[贷记卡单日交易金额]",addAcceptForm);
                        return;
                    }
                   
					addAcceptForm.getForm().submit({
						url: 'T20201Action.asp?method=acceptAdd',
						waitMsg: '正在审核商户信息，请稍候......',
						params: {
							mchntId: selectedRecord.get('mchtNo'),
							txnId: '20201',
							subTxnId: '01'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addAcceptForm);
							addAcceptWin.hide();
							// 重新加载商户待审核信息
							mchntGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addAcceptForm);
						}
					});
				}
			}
		}, {
			text: '重填',
			handler: function() {
				addAcceptForm.getForm().reset();			
			}
		}]
	});

	
	// 添加审核通过
	var addAcceptWin = new Ext.Window({
		title : '商户添加审核通过',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 500,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ addAcceptForm ]
	});
	/******添加审核通过end*******/
	
	
	
	var backMenu = {
		text: '退回',
		width: 85,
		iconCls: 'back',
		disabled: true,
		handler:function() {
			showConfirm('确认退回吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入退回原因',true,back);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function back(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('退回原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入退回原因',true,back);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = mchntGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20201Action.asp?method=back',
				params: {
					mchntId: rec.get('mchtNo'),
					txnId: '20201',
					subTxnId: '02',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户待审核信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入退回原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = mchntGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20201Action.asp?method=refuse',
				params: {
					mchntId: rec.get('mchtNo'),
					txnId: '20201',
					subTxnId: '03',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户待审核信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			if(mchntGrid.getSelectionModel().getSelected().get('mchtStatus')=='1'){
				showMchntDetailS4Add(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
			}else{
				showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
			}
			
		}
	};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	
	
	
	menuArr.push(acceptMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(backMenu);  //[2]
	menuArr.push('-');         //[3]
	menuArr.push(refuseMenu);  //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);  //[8]
	
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 260,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户综合审核',
		region: 'center',
		iconCls: 'T20201',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();

			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[2].enable();
			if(rec.get('mchtStatus')=='1'){
			mchntGrid.getTopToolbar().items.items[4].enable();	
			}
			mchntGrid.getTopToolbar().items.items[6].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('endDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册开始日期不能大于注册结束日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('startDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册结束日期不能小于注册开始日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','新增待审核'],['3','修改待审核'],['5','冻结待审核'],['7','恢复待审核'],['9','注销待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_NORMAL',
			fieldLabel: '所属分公司',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(), 
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});
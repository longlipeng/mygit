Ext.onReady(function() {	
	
	//交易类型
	/*var txnNumStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_LIMIT',function(ret){
		txnNumStore.loadData(Ext.decode(ret));
	});*/
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	
	 var ActionStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('ACTION',function(ret){
		ActionStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtFeeInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'dayNum',mapping: 'dayNum'},
			{name: 'dayAmt',mapping: 'dayAmt'},
			{name: 'daySingle',mapping: 'daySingle'},
			{name: 'monNum',mapping: 'monNum'},
			{name: 'monAmt',mapping: 'monAmt'},
			{name: 'saState',mapping:'saState'},
			{name: 'remark',mapping:'remark'}
			/*{name: 'saAction',mapping:'saAction'}*/
		])
	});
	
	var mchntStore1 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   	{name: 'uid',mapping: 'id'},
			{name: 'mchtCdU',mapping: 'mchtCd'},
			{name: 'mchtNmU',mapping: 'mchtNm'},
			{name: 'txnNumU',mapping: 'txnNum'},
			{name: 'cardTypeU',mapping: 'cardType'},
			{name: 'dayNumU',mapping: 'dayNum'},
			{name: 'dayAmtU',mapping: 'dayAmt'},
			{name: 'daySingleU',mapping: 'daySingle'},
			{name: 'monNumU',mapping: 'monNum'},
			{name: 'monAmtU',mapping: 'monAmt'},
			{name: 'saStateU',mapping:'saState'},
			/*{name: 'saAction',mapping:'saAction'}*/
		])
	});
	
	
	
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
    		new Ext.grid.RowNumberer(),
    		{id: 'mchtCd',header: '商户编号',dataIndex: 'mchtCd',sortable: true},
    		{id: 'mchtNm',header: '商户名称',dataIndex: 'mchtNm',sortable: true},
    		/*{header: '交易名称',dataIndex: 'txnNum',width: 100,renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnNumStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }},*/
    		{header: '卡类型',dataIndex: 'cardType',width: 80 , renderer: cardTypeT},
/*    		{header: '单日累计交易笔数',dataIndex: 'dayNum',width: 110,
    		 	editor: new Ext.form.TextField({
    				allowBlank: false,
    				regex:/^[0-9]+$/,
    				maxLength: 5,
					maskRe: /^[0-9]+$/
    		 	})},*/
    		{header: '单日单笔交易金额',dataIndex: 'daySingle',width: 110
       		 /*editor: new Ext.form.TextField({
       		 	maxLength: 15,
       		 	maskRe: /^[0-9\\.]+$/,
       			allowBlank: false,
       			vtype: 'isMoney'
       		 })*/},
    		
    		 {header: '单日累计交易金额',dataIndex: 'dayAmt',width: 110
        		/* editor: new Ext.form.TextField({
        		 	maxLength: 15,
        		 	maskRe: /^[0-9\\.]+$/,
        			allowBlank: false,
        			vtype: 'isMoney'
        		 })*/},

        	{header: '单月累计交易金额',dataIndex: 'monAmt',width: 110
            		/* editor: new Ext.form.TextField({
            		 	maxLength: 22,
            		 	maskRe: /^[0-9\\.]+$/,
            			allowBlank: false,
            			vtype: 'isMoney'
            	})*/},
/*        	 {header: '受控动作',dataIndex: 'saAction',width: 100,editor: new Ext.form.ComboBox({
				 	store: ActionStore,
					displayField: 'displayField',
					valueField: 'valueField',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true}),renderer:action},*/
			{header: '状态',dataIndex: 'saState',width: 120,renderer: riskInfoState}

    		 /*{header: '单月累计交易笔数',dataIndex: 'monNum',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 6,
        			allowBlank: false,
					maskRe: /^[0-9]+$/
        		 })},*/

    	]);
	

	
/*	// 转译受控动作
	function action(val){
	if(val=='0'){
		return '提示'; 
		}
		else{
		return '拒绝';
		}
	}*/
	
	//转译卡种类
	function cardTypeT(val) {
		if(val == '00'){
			return '贷记卡';
		}else if (val == '01'){
			return '借记卡';
		}else if (val == '02'){
			return '准贷记卡';
		}else if (val == '03'){
			return '预付卡';
		}else {
			return '未知卡种类';
		}
	}
	
	
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
	
  /*******************************交易限额操作记录****************************/
	var logStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo4MchtFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'brhID',mapping: 'brhID'},
			{name: 'oprID',mapping: 'oprID'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'},
			{name: 'param1',mapping: 'param1'},
			{name: 'param2',mapping: 'param2'},
			{name: 'param3',mapping: 'param3'},
			{name: 'param4',mapping: 'param4'},
			{name: 'param5',mapping: 'param5'},
			{name: 'param6',mapping: 'param6'},
			{name: 'reserve1',mapping: 'reserve1'}
		])
	});
	logStore.on('beforeload', function() {
		logStore.removeAll();
		Ext.apply(this.baseParams, {
            start: 0,
            flag:"9",
            mchnNoQ: oprGrid.getSelectionModel().getSelected().data.mchtCd,
            cardTypeQ:oprGrid.getSelectionModel().getSelected().data.cardType
        });
	});
	
	var logColModel = new Ext.grid.ColumnModel([
	     new Ext.grid.RowNumberer(),
	     {header: '操作人',dataIndex: 'oprID',sortable: true,width: 90,renderer:getOpr},
	     {header: '操作时间',dataIndex: 'txnTime',sortable: true,width: 100,renderer: formatTs},
	     {header: '操作类型',dataIndex: 'param6',sortable: true,width:70},
		 {header: '操作状态',dataIndex: 'reserve1',sortable: true,width:55},
		 {header: '备注',dataIndex: 'refuseInfo',sortable: true,width:120,id:'refuseInfo'}
	     ]);
	
	function getOpr(val){
		if(val!=null&&val!=''){
		return getRemoteTrans(val,"getOprNm");
		}else{
			return val;
		}
	}
	
	
	logGrid = new Ext.grid.GridPanel({
		title: '操作记录',
		region: 'east',
		width: 400,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
//		autoExpandColumn: 'refuseInfo',
		stripeRows: true,
		store: logStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: logColModel,
		clicksToEdit: true,
		forceValidation: true,
//		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载操作记录列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: logStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	/*******************************交易限额操作记录end****************************/
		
	/******************************限额信息添加******************************/
	
	/*var txnNumCombo = new Ext.form.ComboBox({
		hidden:true,
		store: txnNumStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择交易类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个交易类型',
		fieldLabel: '交易类型*',
		id: 'txnNumId',
		hiddenName: 'txnNum'
	});*/
	
	var cardTypeCombo = new Ext.form.ComboBox({
		store: cardTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择卡类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个卡类型',
		fieldLabel: '卡类型*',
		hiddenName: 'cardType'
	});
	
	
	var addMenu = {
		text: '商户限额新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 140,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'idMchtCd',
			allowBlank: false,
			editable: true,
			width: 400
    	},/*txnNumCombo,*/
		/*cardTypeCombo,*/
		{
    		xtype: 'dynamicCombo',
    		methodName: 'getCARDSTYLE',
    		/*store: cardTypeStore,
    		displayField: 'displayField',
    		valueField: 'valueField',*/
    		emptyText: '请选择卡类型',
    		mode: 'local',
    		width: 400,
    		/*triggerAction: 'all',
    		forceSelection: true,
    		typeAhead: true,
    		selectOnFocus: true,
    		editable: false,*/
    		allowBlank: false,
    		blankText: '请选择一个卡类型',
    		fieldLabel: '卡类型*',
    		hiddenName: 'cardType'
    	},
/*		{
			fieldLabel: '单日累计交易笔数*',
			allowBlank: false,
			blankText: '单日累计交易笔数不能为空',
			emptyText: '请输入单日累计交易笔数',
			maxLength: 5,
			regex:/^[0-9]+$/,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'dayNum',
			name: 'dayNum',
			vtype: 'alphanum',
			width: 400
		},*/
		{
			fieldLabel: '单日单笔交易金额*',
			allowBlank: false,
			blankText: '单日单笔交易金额不能为空',
			emptyText: '请输入单日单笔交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingle',
			name: 'daySingle',
			vtype: 'isMoney',
			width: 400
		},{
			fieldLabel: '单日累计交易金额*',
			allowBlank: false,
			blankText: '单日累计交易金额不能为空',
			emptyText: '请输入单日累计交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmt',
			name: 'dayAmt',
			vtype: 'isMoney',
			width: 400
		},

		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmt',
			name: 'monAmt',
			vtype: 'isMoney',
			width: 400
		}/*,{
			xtype: 'basecomboselect',
			baseParams: 'SAACTION',
			fieldLabel: '受控动作*',
			allowBlank: false,
			blankText: '该输入受控动作',
			emptyText: '请输入受控动作',
			id: 'idsaAction',
			hiddenName: 'saAction',
			width: 300,
			maskRe:/^[0-9]$/
		}*//*,
		{
			fieldLabel: '单月累计交易笔数*',
			allowBlank: false,
			blankText: '单月累计交易笔数不能为空',
			emptyText: '请输入单月累计交易笔数',
			maxLength: 6,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'monNum',
			name: 'monNum',
			vtype: 'alphanum',
			width: 400
		},*/
]
	});
	
	var oprWin = new Ext.Window({
		title: '商户限额添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
//                    var dayNum = oprInfoForm.getForm().findField('dayNum').getValue();
//                    var monNum = oprInfoForm.getForm().findField('monNum').getValue();
//                    if(Number(monNum) < Number(dayNum))
//                    {
//                        showErrorMsg("[单月累计交易笔数]不能小于[单日累计交易笔数]",oprInfoForm);
//                        return;
//                    }
                    var daySingle = oprInfoForm.getForm().findField('daySingle').getValue();
                    if(daySingle.indexOf('.') == -1 && daySingle.length>13){//20121115
                    	showErrorMsg("单日累计交易金额整数部分不能超过13位，请重新填写",oprInfoForm);
                        return;
                    }
                    var dayAmt = oprInfoForm.getForm().findField('dayAmt').getValue();
                    if(dayAmt.indexOf('.') == -1 && dayAmt.length>13){//20121115
                    	showErrorMsg("单日单笔交易金额整数部分不能超过13位，请重新填写",oprInfoForm);
                        return;
                    }
                    var monAmt = oprInfoForm.getForm().findField('monAmt').getValue();
                    if(Number(dayAmt) < Number(daySingle))
                    {
                        showErrorMsg("[单日累计交易金额]不能小于[单日单笔交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monAmt) < Number(dayAmt))
                    {
                        showErrorMsg("[单月累计交易金额]不能小于[单日累计交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monAmt)==0||Number(daySingle)==0||Number(dayAmt)==0){
                    	showErrorMsg("商户限额的交易笔数和金额不能为0",oprInfoForm);
                        return;
                    }
                  /*  var txn = oprInfoForm.getForm().findField('txnNum').getValue();
                    var card = oprInfoForm.getForm().findField('cardType').getValue();
                    if(txn == '1501' || txn == 'E786'){
                    	//01 借记卡
                    	if(!(card == '01')){
                    		showErrorMsg("T+0的交易只能配置为借记卡",oprInfoForm);
                        	return;
                    	}
                    }*/
					oprInfoForm.getForm().submitNeedAuthorise({
						url: 'T20304Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,oprInfoForm);
							//重置表单
							oprInfoForm.getForm().reset();
							oprWin.hide();
							//重新加载列表
							oprGrid.getStore().reload();
							
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,oprInfoForm);
						},
						params: {
							txnId: '20304',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(oprColModel);
			}
		}]
	});
	
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				showConfirm('确定要删除该条商户限额信息吗？',oprGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入进行该操作的原因',true,delFunc);
					}
				});
			}
		};
	
	function delFunc(bt,text) {
		if(bt == 'ok') {
			var rec = oprGrid.getSelectionModel().getSelected();
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('操作原因不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,delFunc);
				return;
			}
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delFunc);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20304Action.asp?method=delete',
				params: {					 
					mchtCd: rec.get('mchtCd'),
					cardType: rec.get('cardType'),
					txnId: '20304',
					subTxnId: '02',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,oprGrid);
						oprGrid.getStore().reload();
						oprGrid.getTopToolbar().items.items[2].disable();
					} else {
						showErrorMsg(rspObj.msg,oprGrid);
					}
				}
			});
			hideProcessMsg();
		}
	}
	
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						mchtCd : record.get('mchtCd'),
						mchtNm  : record.get('mchtNm'),
						cardType : record.get('cardType'),
						daySingle: record.get('daySingle'),
						dayAmt: record.get('dayAmt'),
						monAmt: record.get('monAmt'),
						txnNum: record.get('txnNum'),
						//dayNum: record.get('dayNum'),
						saAction:record.get('saAction')
					};
                    var monAmt = record.get('monAmt');
                    var daySingle = record.get('daySingle');
                    var dayAmt = record.get('dayAmt');
                    if((Number(monAmt)==0)||Number(daySingle)==0||Number(dayAmt)==0){
                    	showErrorMsg("商户限额的交易笔数和金额不能为0",oprInfoForm);
                        return;
                    }
                    
                    /*if(Number(monNum) < Number(dayNum))
                    {
                        showErrorMsg("[单月累计交易笔数]不能小于[单日累计交易笔数]",oprInfoForm);
                        return;
                    }*/
                    if(Number(dayAmt) < Number(daySingle))
                    {
                        showErrorMsg("[单日累计交易金额]不能小于[单日单笔交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monAmt) < Number(dayAmt))
                    {
                        showErrorMsg("[单月累计交易金额]不能小于[单日累计交易金额]",oprInfoForm);
                        return;
                    }
//                    var txn = record.get('txnNum');
//                    var card = record.get('cardType');
//                    if(txn == '1501' || txn == 'E786'){
//                    	//01 借记卡
//                    	if(!(card == '01')){
//                    		showErrorMsg("T+0的交易只能配置为借记卡",oprInfoForm);
//                        	return;
//                    	}
//                    }
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T20304Action.asp?method=update',
					method: 'post',
					params: {
						cstMchtFeeList: Ext.encode(array),
						txnId: '20304',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
						//oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
	mchntStore.on('beforeload',function(){
		this.rejectChanges();
	})
	
	var updTermForm = new Ext.form.FormPanel({
		frame: true,
		defaultType: 'textfield',
		border: true,
		labelWidth: 140,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '商户编号*',
			hiddenName: 'idmchtCdU',
			id: 'idmchtCdU',
			readOnly: true,
			width: 400
    	},{
    		xtype: 'textfield',
    		width: 400,
    		readOnly: true,
    		id: 'cardTypeU',
    		fieldLabel: '卡类型*',
    		hiddenName: 'cardTypeU'
    	},{
			fieldLabel: '单日单笔交易金额*',
			allowBlank: false,
			blankText: '单日单笔交易金额不能为空',
			emptyText: '请输入单日单笔交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingleU',
			name: 'daySingleU',
			vtype: 'isMoney',
			width: 400
		},{
			fieldLabel: '单日累计交易金额*',
			allowBlank: false,
			blankText: '单日累计交易金额不能为空',
			emptyText: '请输入单日累计交易金额',
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmtU',
			name: 'dayAmtU',
			vtype: 'isMoney',
			width: 400
		},

		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmtU',
			name: 'monAmtU',
			vtype: 'isMoney',
			width: 400
		},/*{
    		xtype: 'textarea',
    		width: 400,
    		readOnly: true,
    		id: 'remark',
    		fieldLabel: '备注',
    		hiddenName: 'remark'
    	},*/{
    		xtype: 'textarea',
    		width: 400,
    		id: 'remarkU',
    		maxLength: 50,
    		fieldLabel: '添加备注',
    		hiddenName: 'remarkU'
    	}]
    });
	
    var updTermWin = new Ext.Window({
        title: '限额修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 650,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
                if(updTermForm.getForm().isValid()) {
                	var rec = oprGrid.getSelectionModel().getSelected();
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T20304Action.asp?method=newUpdate',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessDtl(action.result.msg,updTermForm);
                            oprGrid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            oprGrid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            //updTermPanel.setActiveTab('info3Upd');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                        	mchtCd: Ext.getCmp('idmchtCdU').getValue(),
							cardType: rec.get('cardType'),
                            txnId: '20304',
                            subTxnId: '05'
                        }
                    });
                }
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
            }
        }]
    });
	
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            selectedRecord = oprGrid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",oprGrid);
                return;
            }    
            mchntStore1.load({
                params: {
                     storeId: 'getMchtFeeInfEdit',
                     cardType: selectedRecord.get('cardType'),
                     mchtCd: selectedRecord.get('mchtCd')
                },
                callback: function(records, options, success){
                    if(success){
                    	updTermForm.getForm().loadRecord(records[0]);
                    	
                    	Ext.getCmp('idmchtCdU').setValue(records[0].id.mchtCd);
                    	Ext.getCmp('cardTypeU').setValue(cardTypeT(records[0].id.cardType));
                    	//alert(records[0].id.mchtCd);	
                    	
                    	updTermWin.show();
                    }else{
                    	updTermWin.hide();
                    }
                }
            });
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(editMenu);			// [4]
	//menuArr.push(upMenu);		// [4]	
	menuArr.push('-');  //[5]
	menuArr.push(queryCondition);  //[6]
	menuArr.push('-');  
	menuArr.push('单位金额(人民币)：元');  

	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '商户限额信息',
		region: 'center',
		iconCls: 'cityCode',
		autoExpandColumn: 'mchtNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		//clicksToEdit: true,
		//forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载商户限额信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})/*,
		renderTo: Ext.getBody()*/
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			//oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = oprGrid.getSelectionModel().getSelected().data.mchtCd;
		var cardTypeQ = oprGrid.getSelectionModel().getSelected().data.cardType;
		logStore.load({
			params: {
				start: 0,
				flag:"9",
	            mchnNoQ: id,
	            cardTypeQ:cardTypeQ
			}
		});
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		
		var rec = oprGrid.getSelectionModel().getSelected();
		if(rec.get('saState')=='2'){
			oprGrid.getTopToolbar().items.items[4].enable();
			oprGrid.getTopToolbar().items.items[2].enable();
		}else{
			oprGrid.getTopToolbar().items.items[4].disable();
			oprGrid.getTopToolbar().items.items[2].disable();
		}
		
		/*termStore.load({
			params: {
				start: 0,
				mchntNo: rec.get('mchntCd')
			}
		});*/
	
		
		}
	});
	
	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'mchtCdId',
			allowBlank: true,
			editable: true,
			width: 400
    	}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 560,
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
			mchntId: queryForm.findById('mchtCdId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid,logGrid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	var sumGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=sumrzInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'seqNo'
		},[
			{name: 'seqNo',mapping: 'seqNo'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'accFlag',mapping: 'accFlag'},
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
			{name: 'recUpdTs',mapping: 'recUpdTs'}
		])
	});
	
	sumGridStore.load({
		params:{start: 0}
	});
	
	var sumColModel = new Ext.grid.ColumnModel([
		{header: '商户号',dataIndex: 'mchtNm',width:200,align: 'center'},
		{header: '账户类型',dataIndex: 'accFlag',renderer: accFlagVal,align: 'center'},
		{header: '结算账户',dataIndex: 'settleAccName',align: 'center'},
		{header: '结算账号',dataIndex: 'settleAccNum',align: 'center'},
		{header: '开户行',dataIndex: 'bankName',align: 'center'},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center'},
		{header: '手续费',dataIndex: 'handAmt',align: 'center'},
		{header: '结算金额',dataIndex: 'sumAmt',align: 'center'},
		{header: '状态',dataIndex: 'saStatus',renderer: saStateVal,align: 'center'},
		{header: '划款日期',dataIndex: 'sumrzDate',width: 100,renderer: formatDt,align: 'center'},
		{header: '备注',dataIndex: 'sumrzNote',align: 'center',id:'sumrzNote'}
	]);
	
	
	function accFlagVal(val){
		if(val=='1'){
			return '对私账户';
		}
		if(val=='2'){
			return '对公账户';
		}
	}
	
	function saStateVal(val){
		if(val=='0'){
			return '<font color="red">失败</font>';
		}
		if(val=='1'){
			return '<font color="green">成功</font>';
		}
	}
	
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
		menuArr.push(queryCondition);  //[1]
		
		// 信息列表
	var sumGrid = new Ext.grid.EditorGridPanel({
		title: '划账表汇总查询',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'sumrzNote',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sumGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: sumColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sumGridStore,
//			pageSize: System[QUERY_RECORD_COUNT],
			pageSize:20,
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
			methodName: 'getMchntNo',
			hiddenName: 'mchtNo',
			editable: true,
			width: 250
		},{
			xtype: 'datefield',
			id: 'instDateId',
			name: 'instDateId',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '结算日期',
			width:150,
			editable: false			
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idAccFlag',
			fieldLabel: '账户状态',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','对私账户'],['2','对公账户']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idSaStatus',
			fieldLabel: '出款状态',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','失败'],['1','成功']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'datefield',
			id: 'sumrzDateId',
			name: 'sumrzDateId',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '划款日期',
			width:150,
			editable: false			
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
			saStatus: queryForm.getForm().findField('idSaStatus').getValue(),
			mchtNo: queryForm.getForm().findField('mchtNo').getValue(),
			sumrzDate: typeof(queryForm.findById('sumrzDateId').getValue()) == 'string' ? '' : queryForm.findById('sumrzDateId').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [sumGrid],
		renderTo: Ext.getBody()
	});
});
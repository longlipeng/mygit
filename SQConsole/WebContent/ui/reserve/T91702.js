Ext.onReady(function(){
	
	//数据来源
	var oprGridStore =  new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblRejectedNotify'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'trnNo'
		},[
		   {name: 'trnNo', mapping: 'trnNo'},
		   {name: 'trnDate', mapping: 'trnDate'},
		   {name: 'trnPayerBankNo', mapping: 'trnPayerBankNo'},
		   {name: 'trnPayerAcctNo', mapping: 'trnPayerAcctNo'},
		   {name: 'trnPayerAcctName', mapping: 'trnPayerAcctName'},
		   {name: 'trnPayeeAcctNo', mapping: 'trnPayeeAcctNo'},
		   {name: 'trnPayeeAcctName', mapping: 'trnPayeeAcctName'},
		   {name: 'trnTxnAmt', mapping: 'trnTxnAmt'},
		   {name: 'trnPayeeAcctBal', mapping: 'trnPayeeAcctBal'},
		   {name: 'trnAcctDate', mapping: 'trnAcctDate'},
		   {name: 'trnOrigTxnNo', mapping: 'trnOrigTxnNo'},
		   {name: 'trnOrigTxnDate', mapping: 'trnOrigTxnDate'},
		   {name: 'trnMerId', mapping: 'trnMerId'},
		   {name: 'trnMerName', mapping: 'trnMerName'},
		])
	});
	
	//数据加载
	oprGridStore.load({
		params: {
			start: 0
		}
	});
	
	//显示列表信息列
	var oprColModel = new Ext.grid.ColumnModel([
        {header: '主键', dataIndex: 'trnNo', align: 'center', hidden: true},
        {header: '交易日期', dataIndex: 'trnDate', align: 'center'},
        {header: '付款方开户行行号', dataIndex: 'trnPayerBankNo', align: 'center'},
        {header: '付款方账号', dataIndex: 'trnPayerAcctNo', align: 'center'},
        {header: '付款方账户名称', dataIndex: 'trnPayerAcctName', align: 'center'},
        {header: '收款方账号', dataIndex: 'trnPayeeAcctNo', align: 'center'},
        {header: '收款方账户名称', dataIndex: 'trnPayeeAcctName', align: 'center'},
        {header: '金额', dataIndex: 'trnTxnAmt', align: 'center'},
        {header: '收款方虚拟记账余额', dataIndex: 'trnPayeeAcctBal', align: 'center'},
        {header: '银联记账日期', dataIndex: 'trnAcctDate', align: 'center'},
        {header: '原交易流水号', dataIndex: 'trnOrigTxnNo', align: 'center'},
        {header: '原交易日期', dataIndex: 'trnOrigTxnDate', align: 'center'},
        {header: '客户代码', dataIndex: 'trnMerId', align: 'center'},
        {header: '客户名称', dataIndex: 'trnMerName', align: 'center'},
	]);
	
	
	var menuArry = new Array();
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler: function(){
				queryWin1.show();
			}
		};
		menuArry.push(queryCondition);
	
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '退汇通知',
		iconCls: 'T104',
		region: 'center',
		//自动充满表格未用空间的列
		//autoExpandColumn: '',
		frame: true,
		border: true,
		//是否显示列分割线
		columnLines: true,
		//表格是否隔行换色
		stripeRows: true,
		store: oprGridStore,
		//:是否单选模式
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArry,
		loadMask: {
			msg: '正在加载退汇通知列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	//form表单
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '交易日期',
        }]
	});
	
	//查询条件窗口
	var queryWin1 = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 250,
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
			handler: function(){
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function(){
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload',function(){
		Ext.apply(this.baseParams, {
			start: 0,
			date: queryForm.getForm().findField('date').getValue().format('Ymd')
		});
	});
	
	var manView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
});
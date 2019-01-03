Ext.onReady(function(){
	
	//数据来源
	var oprGridStore =  new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblAccountNotify'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'tanNo'
		},[
		   {name: 'tanNo', mapping: 'tanNo'},
		   {name: 'tanDate', mapping: 'tanDate'},
		   {name: 'tanPayerBankNo', mapping: 'tanPayerBankNo'},
		   {name: 'tanPayerAcctNo', mapping: 'tanPayerAcctNo'},
		   {name: 'tanPayerAcctName', mapping: 'tanPayerAcctName'},
		   {name: 'tanPayeeAcctNo', mapping: 'tanPayeeAcctNo'},
		   {name: 'tanPayeeAcctName', mapping: 'tanPayeeAcctName'},
		   {name: 'tanTxnAmt', mapping: 'tanTxnAmt'},
		   {name: 'tanPayeeAcctBal', mapping: 'tanPayeeAcctBal'},
		   {name: 'tanAcctDate', mapping: 'tanAcctDate'},
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
        {header: '主键', dataIndex: 'tanNo', align: 'center', hidden: true},
        {header: '交易日期', dataIndex: 'tanDate', align: 'center'},
        {header: '付款方开户行行号', dataIndex: 'tanPayerBankNo', align: 'center'},
        {header: '付款方账号', dataIndex: 'tanPayerAcctNo', align: 'center'},
        {header: '付款方账户名称', dataIndex: 'tanPayerAcctName', align: 'center'},
        {header: '收款方账号', dataIndex: 'tanPayeeAcctNo', align: 'center'},
        {header: '收款方账户名称', dataIndex: 'tanPayeeAcctName', align: 'center'},
        {header: '金额', dataIndex: 'tanTxnAmt', align: 'center'},
        {header: '收款方虚拟记账余额', dataIndex: 'tanPayeeAcctBal', align: 'center'},
        {header: '银联记账日期', dataIndex: 'tanAcctDate', align: 'center'},
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
		title: '来账通知',
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
			msg: '正在加载来账通知列表......'
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
//			format: 'Y-m-d',
//			altFormats: 'Y-m-d',
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
//		alert(queryForm.findById('date').getValue()); //Thu Jan 03 2019 00:00:00 GMT+0800 (中国标准时间)
//		alert("1" + typeof(queryForm.getForm().findField('date').getValue())); //object
//		alert(queryForm.findById('date').getValue().dateFormat('Ymd')); //20190103
//		alert("2" + typeof(queryForm.getForm().findField('date').getValue().format('Ymd'))); //string 不能为空
		Ext.apply(this.baseParams, {
			start: 0,
			date: typeof(queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
//			date: queryForm.getForm().findField('date').getValue().format('Ymd')
		});
	});
	
	var manView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
});
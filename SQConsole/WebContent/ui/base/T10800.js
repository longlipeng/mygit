Ext.onReady(function() {
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankNoS'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'bankNo'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'accountName',mapping: 'accountName'},
			{name: 'bankAccount',mapping: 'bankAccount'},
			{name: 'region',mapping: 'region'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: '银行编号', dataIndex: 'bankNo', align: 'center', id: 'bankNo'},
		{header: '银行名称', dataIndex: 'bankName', align: 'center'},
		{header: '账户户名', dataIndex: 'accountName', align: 'center', id:'accountName'},
		{header: '银行账号', dataIndex: 'bankAccount', align: 'center'},
		{header: '地区', dataIndex: 'region', align: 'center'}
	]);
	
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
		
		menuArr.push(queryCondition);  //[0]
	
	// 银行账户列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '备付金账户查询',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'accountName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载备付金账户列表......'
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
	
/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		labelWidth: 80,
		autoHeight: true,
		items: [{
					xtype:'textfield',
					fieldLabel: '银行名称',
					name: 'bankName',
					id: 'bankName',
					width: 150
				},{
					xtype:'textfield',
					fieldLabel: '银行账号',
					id: 'bankAccount',
					name: 'bankAccount',
					width: 150
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			bankName: queryForm.findById('bankName').getValue(),
			bankAccount: queryForm.findById('bankAccount').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});
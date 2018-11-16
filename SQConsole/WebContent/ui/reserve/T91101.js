Ext.onReady(function() {
	
	//数据来源
	var oprGridStore  = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=rosterGridStoreTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'rosterId'
		},[
		   {name: 'rosterId',mapping: 'rosterId'},
		   {name: 'rosterBankCard',mapping: 'rosterBankCard'},
		   {name: 'rosterAccount',mapping: 'rosterAccount'},
		   {name: 'rosterAccountName',mapping: 'rosterAccountName'},
		   {name: 'rosterStatus',mapping: 'rosterStatus'},
		   {name: 'rosterLaunchTime',mapping: 'rosterLaunchTime'},
		   {name: 'rosterLaunchName',mapping: 'rosterLaunchName'},
		   {name: 'rosterAuditTime',mapping: 'rosterAuditTime'},
		   {name: 'rosterAuditName',mapping: 'rosterAuditName'}
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
    	{header: '账户白名单id',dataIndex: 'rosterId',align: 'center',hidden: true},
    	{header: '收款方开户行行号',dataIndex: 'rosterBankCard',align: 'center'},
    	{header: '收款方账号',dataIndex: 'rosterAccount',align: 'center'},
    	{header: '收款方账户名称',dataIndex: 'rosterAccountName',align: 'center'},
    	{header: '审核状态',dataIndex: 'rosterStatus',renderer: rosterStatus,align: 'center'},
    	{header: '发起日期',dataIndex: 'rosterLaunchTime',align: 'center'},
    	{header: '发起人员',dataIndex: 'rosterLaunchName',align: 'center'},
    	{header: '审核日期',dataIndex: 'rosterAuditTime',align: 'center'},
    	{header: '审核人员',dataIndex: 'rosterAuditName',align: 'center'}
	]);
	
	//审核状态
	function rosterStatus(val){
		if(val=='0'){
			return '<font color="green">正常</font>';
		}else if(val=='1'){
			return '<font color="gray">修改待审核</font>';
		}else if(val=='2'){
			return '<font color="gray">删除待审核</font>';
		}else if(val=='3'){
			return '<font color="gray">新增待审核</font>';
		}
	}
	
	//按钮集合
	var menuArr1 = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler: function(){
				queryWin1.show();
			}
		};
		menuArr1.push(queryCondition);
	
	//账户白名单列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '账户白名单查询',
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
		tbar: menuArr1,
		loadMask: {
			msg: '正在加载账户白名单列表......'
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
			xtype: 'dynamicCombo',
			fieldLabel: '白名单账户',
			methodName: 'getRosterAccountAll',
			hiddenName: 'rosterAccount',
			editable: true,
			width: 250
        }]
	});
	
	//查询条件窗口
	var queryWin1 = new Ext.Window({
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
			rosterAccount: queryForm.getForm().findField('rosterAccount').getValue()
		});
	});
	var manView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});
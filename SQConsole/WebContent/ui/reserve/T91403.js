Ext.onReady(function(){
	
	//历史余额查询任务数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblHistoryQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'historyNo'
		},[
			{name: 'historyNo',mapping: 'historyNo'},
			{name: 'historyDate',mapping: 'historyDate'},
			{name: 'historyAcctNo',mapping: 'historyAcctNo'},
			{name: 'historyAcctName',mapping: 'historyAcctName'},
			{name: 'historyOpenBal',mapping: 'historyOpenBal'},
			{name: 'historyEncBal',mapping: 'historyEncBal'},
			{name: 'historyTotalDbtrQty',mapping: 'historyTotalDbtrQty'},
			{name: 'historyTotalCdtrQty',mapping: 'historyTotalCdtrQty'},
			{name: 'historyTotalDbtrAmt',mapping: 'historyTotalDbtrAmt'},
			{name: 'historyTotalCdtrAmt',mapping: 'historyTotalCdtrAmt'},
		]),
	//	autoLoad: true  //二次加载  用于第一次加载为空 第二次加载数据的情况
	});
	
	//数据加载
	redempGridStore.load({
		params: {
			start: 0
		}
	});
	
	var redempColModel = new Ext.grid.ColumnModel([
		{header: '交易流水号',dataIndex: 'historyNo',width: 100,hidden:true},
		{header: '交易日期',dataIndex: 'historyDate',width: 150,align: 'center'},
		{header: '虚拟账号',dataIndex: 'historyAcctNo',width: 100,align: 'center'},
		{header: '虚拟账户名称',dataIndex: 'historyAcctName',width: 100,align: 'center'},
		{header: '期初虚拟记账余额',dataIndex: 'historyOpenBal',width: 100,align: 'center'},
		{header: '期末虚拟记账余额',dataIndex: 'historyEncBal',width: 100,align: 'center'},
		{header: '借记总笔数',dataIndex: 'historyTotalDbtrQty',width: 100,align: 'center'},
		{header: '贷记总笔数',dataIndex: 'historyTotalCdtrQty',width: 100,align: 'center'},
		{header: '借记总金额',dataIndex: 'historyTotalDbtrAmt',width: 100,align: 'center'},
		{header: '贷记总金额',dataIndex: 'historyTotalCdtrAmt',width: 100,align: 'center'},
	]);
	
	var menuArr = new Array();
	
	var queryCondition = {
		text: '日期录入',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var addBalance = {
		text: '历史余额查询',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.add('-');    //[0]
	menuArr.add(queryCondition);  //[1]
//	menuArr.add('-');    //[2]
//	menuArr.add(addBalance);    //[3]
	
	var redempGrid = new Ext.grid.EditorGridPanel({
		title: '历史余额查询',
		region: 'center',
		iconCls: 'T91403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: '',
		stripeRows: true,
		store: redempGridStore,
		tbar:menuArr,
		cm: redempColModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: redempGridStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 15,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//***1234****
	//form表单
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 250,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '交易日期',
			allowBlank: false,
			blankText: '请选择交易日期',
			emptyText: '请输入受益人编号'
		}]
	});
	
	var queryWin = new Ext.Window({
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
			handler: function() {
				queryForm.getForm().submit({
					url: 'T91401Action_inits.asp',
//					waitMsg: '正在提交，请稍后......',
					success: function(form,action){
						showSuccessMsg(action.result.msg,queryForm);
						//成功关闭窗口
						queryWin.hide();
						//重新加载查询表数据
						redempGridStore.load();
					},
					failure: function(form,action) {
						//打印失败错误信息
						showErrorMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '91403',
						subTxnId: '01',
					}
				});
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
//	redempGridStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			date: queryForm.getForm().findField('date').getValue().format('Ymd')
//		});
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
	queryForm.getForm().findField('date').setValue(TORDAY);
});
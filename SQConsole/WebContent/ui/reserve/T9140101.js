Ext.onReady(function(){
	
	//备款任务数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=balanceReserveQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'balanceNo'
		},[
			{name: 'balanceNo',mapping: 'balanceNo'},
			{name: 'balanceDate',mapping: 'balanceDate'},
			{name: 'balanceAcsBankNo',mapping: 'balanceAcsBankNo'},
			{name: 'balanceAcctBal',mapping: 'balanceAcctBal'},
			{name: 'balanceAvlbBal',mapping: 'balanceAvlbBal'},
			{name: 'balanceAcsAcctBal',mapping: 'balanceAcsAcctBal'},
			{name: 'balanceAcsAcctName',mapping: 'balanceAcsAcctName'},
			{name: 'balanceAvlbQuotaAmt',mapping: 'balanceAvlbQuotaAmt'}
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
		{header: '交易流水号',dataIndex: 'balanceNo',width: 100,hidden:true},
		{header: '交易日期',dataIndex: 'balanceDate',width: 150,align: 'center'},
		{header: 'ACS备付金存管账号',dataIndex: 'balanceAcsBankNo',width: 100,align: 'center'},
		{header: '备付金虚拟记账余额',dataIndex: 'balanceAcctBal',width: 100,align: 'center'},
		{header: '备付金虚拟记账可用余额',dataIndex: 'balanceAvlbBal',width: 100,align: 'center'},
		{header: 'ACS备付金存管账户可用余额',dataIndex: 'balanceAcsAcctBal',width: 100,align: 'center'},
//		{header: 'ACS备付金存管账户名称',dataIndex: 'balanceAcsAcctName',width: 100,align: 'center'},
		{header: '可支取额度',dataIndex: 'balanceAvlbQuotaAmt',width: 100,align: 'center'},
	]);
	
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
	
	var addBalance = {
		text: '账户备付金余额查询',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			Ext.Ajax.request({
				url: 'T91401Action_init.asp',
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,redempGrid);
					} else {
						showErrorMsg(rspObj.msg,redempGrid);
					}
					redempGridStore.getStore().reload();
				},
				params: {
					txnId: '80101',
					subTxnId: '01'
				}
			});
		}
	};
	
	menuArr.add('-');    //[0]
	menuArr.add(queryCondition);  //[1]
	menuArr.add('-');    //[2]
	menuArr.add(addBalance);    //[3]
	
	var redempGrid = new Ext.grid.EditorGridPanel({
		title: '人行备付金余额查询',
		region: 'center',
		iconCls: 'T9140101',
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
			pageSize: 20,
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
			blankText: '请选择交易日期'
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
//				redempGridStore.load();
				queryForm.getForm().submit({
					url: 'T91401Action_inits.asp',
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,redempGrid);
						} else {
							showErrorMsg(rspObj.msg,redempGrid);
						}
						redempGridStore.getStore().reload();
					},
					params: {
						txnId: '80101',
						subTxnId: '01'
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
	
	redempGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			date: queryForm.getForm().findField('date').getValue().format('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
	
});
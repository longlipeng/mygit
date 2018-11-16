Ext.onReady(function() {
	
	
	// 冻结商户信息
	var frozenStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=frozen'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'frozenDate',mapping: 'frozenDate'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'frozenAccount',mapping: 'frozenAccount'},
			{name: 'frozenAccountFinish',mapping: 'frozenAccountFinish'},
			{name: 'frozenAccountNoFinish',mapping: 'frozenAccountNoFinish'},
			{name: 'frozenRoute',mapping: 'frozenRoute'},
			{name: 'frozenReason',mapping: 'frozenReason'},
			{name: 'frozenFinishFlag',mapping: 'frozenFinishFlag'},
			{name: 'stats',mapping: 'stats'},
			{name: 'salesStats',mapping: 'salesStats'},
			{name: 'desId',mapping: 'desId'}
		])
	});
	
	frozenStore.load({
		
	});
	
	
	var frozenColModel = new Ext.grid.ColumnModel([
		{header: '冻结日期',dataIndex: 'frozenDate',width: 100},
		{header: '商户号',dataIndex: 'mchtNo',width: 200,renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 100},
		{header: '冻结金额',dataIndex: 'frozenAccount',width: 90},
		{header: '已冻结金额',dataIndex: 'frozenAccountFinish',width: 80},
		{header: '未完成冻结金额',dataIndex: 'frozenAccountNoFinish',width: 120},
		{header: '冻结方式',dataIndex: 'frozenRoute',width: 100,renderer:getRoute},
		{header: '冻结备注',dataIndex: 'frozenReason',width: 270},
		{header: '冻结完成标志',dataIndex: 'frozenFinishFlag',width: 100,renderer:getFlag},
		{header: '审核状态',dataIndex: 'stats',width: 100,renderer:getStats},
		{header: '退货完成状态',dataIndex: 'salesStats',width: 100,renderer:getSalesStats},
		{header: '后台渠道',dataIndex: 'desId',width: 100,renderer:getDesId}
	]);
	
	
	function getRoute(val){
		if(val=='1'){
			return '风控规则冻结 '; 
		}else if(val=='2'){
			return '手动风控冻结 ';
		}else if(val=='3'){
			return '负金额冻结 ';
		}else if(val=='4'){
			return '商户退货冻结 ';
		}
	}
	
	function getSalesStats(val){
		if(val=='1'){
			return '退货已完成 '; 
		}else if(val=='0'){
			return '退货未完成 '; 
		}else if(val=='-'){
			return ' - '; 
		}
	}
	function getDesId(val){
		if(val=='1708'){
			return '上汽通道 '; 
		}else if(val=='1719'){
			return '单用途卡通道 '; 
		}else if(val==''){
			return ' - '; 
		}
	}
//	salesstats
	function getFlag(val){
		if(val=='0'){
			return '<font color="red">未完成</font>'; 
		}
		if(val=='1'){
			return '<font color="green">已完成</font>';
		}
	}
	function getStats(val){
    	if(val == '0')
            return "<font color='gray'>新增待审核</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改待审核</font>";
    	if(val == '4')
    		return "<font color='gray'>删除待审核</font>";
    	if(val == '5')
            return "<font color='red'>新增审核拒绝</font>";
    }
	
	var menuArray = new Array();
	
		  
	
	var queryMenu = {
		text: '查询',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	

	menuArray.add(queryMenu);
	
	//冻结商户grid
	var grid = new Ext.grid.EditorGridPanel({
		title: '延迟结算查询',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: frozenStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: frozenColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载冻结商户列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: frozenStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	

	
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [ 
		{
			xtype: 'datefield',
			id: 'frozenDate_query',
			name: 'frozenDate_query',
			fieldLabel: '冻结日期',
			width:128
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchtcd',
			id: 'mchtNo_query',
			name: 'mchtNo_query',
			width:230,
			fieldLabel: '商户号',
			 callFunction: function() {
					//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
					Ext.getCmp('termId_query').reset();
					Ext.getCmp('termId_query').parentP=this.value;
					Ext.getCmp('termId_query').getStore().reload();
				}
		},{
			xtype: 'dynamicCombo',
			id: 'termId_query',
			name: 'termId_query',
			fieldLabel: '终端号',
			methodName: 'getTermIdMchnt3'
		},{
			xtype: 'combo',
			id: 'frozenRoute_query',
			fieldLabel: '冻结方式',
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','风控冻结 '],['2','手动风控冻结'],['3','负金额冻结'],['4','退货风控冻结']]
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
				frozenStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	frozenStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			frozenDate_query: typeof(queryForm.findById('frozenDate_query').getValue()) == 'string'?'':queryForm.findById('frozenDate_query').getValue().dateFormat('Ymd'),
			mchtNo_query: queryForm.findById('mchtNo_query').getValue(),
			termId_query: queryForm.findById('termId_query').getValue(),
			frozenRoute_query:queryForm.findById('frozenRoute_query').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
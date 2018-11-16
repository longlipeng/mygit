Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getNewBatMain'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'nds',mapping: 'nds'},
			{name: 'NewLoadtblNTxnfile',mapping: 'NewLoadtblNTxnfile'},
			{name: 'NewCalcCharge',mapping: 'NewCalcCharge'},
			{name: 'NewBrchProc',mapping: 'NewBrchProc'},
			{name: 'NewCreateTblAlgoDtl',mapping: 'NewCreateTblAlgoDtl'},
			{name: 'NewSettlmt',mapping: 'NewSettlmt'},
			{name: 'NewDayFile',mapping: 'NewDayFile'},
			{name: 'NewcreateTblNTxn',mapping: 'NewcreateTblNTxn'},
			{name: 'md',mapping: 'md'},
			{name: 'memory_2',mapping: 'memory_2'},
			{name: 'memory_1',mapping: 'memory_1'},
			{name: 'fileback_1',mapping: 'fileback_1'},
			{name: 'fileback_2',mapping: 'fileback_2'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '日期',dataIndex:'nds',width: 100},		
		{header: '加载流水数据',dataIndex: 'NewLoadtblNTxnfile',width: 110,renderer:state},
		{header: '计算商户手续费',dataIndex: 'NewCalcCharge',width: 110,renderer:state},
		{header: '计算交易成本',dataIndex: 'NewBrchProc',width: 110,renderer:state},
		{header: '生成交易流水文件',dataIndex: 'NewCreateTblAlgoDtl',width: 110,renderer:state},
		{header: '生成入账流水文件',dataIndex: 'NewSettlmt',width: 110,renderer:state},
		{header: '生成需处理流水数据',dataIndex: 'NewcreateTblNTxn',width: 110,renderer:state},
		{header: '发送文件到基础应用平台',dataIndex: 'NewDayFile',width: 110,renderer:state},
		{header: '内存(2)',dataIndex: 'memory_2',width: 110,renderer:state},
		{header: '内存(1)',dataIndex: 'memory_1',width: 110,renderer:state},
		{header: '备份(1)',dataIndex: 'fileback_1',width: 110,renderer:state},
		{header: '备份(2)',dataIndex: 'fileback_2',width: 110,renderer:state}
	]);
	
	function state(val) {
		if(val == '2') 
			return '<font color="green">执行成功</font>';
		else
			return '<font color="red">失败</font>';
	}
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '日终监控',
		region: 'center',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载日终监控列表......'
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
		});
	});
	
	
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集
		
	var TxnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	var ChannelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('TXNNUM',function(ret){
		TxnStore.loadData(Ext.decode(ret));
	});
	SelectOptionsDWR.getComboData('RISKCHANNEL',function(ret){
		ChannelStore.loadData(Ext.decode(ret));
	});
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=risktranctl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
		    {name: 'channelname',mapping: 'channelname'},
			{name: 'cardbinold',mapping: 'cardbinold'},
			{name: 'txnnumold',mapping: 'txnnumold'},
			{name: 'sastatue',mapping: 'sastatue'},
			{name: 'id',mapping: 'id'},
			{name: 'creOprId',mapping: 'creOprId'},
			{name: 'upOprId',mapping: 'upOprId'},
			{name: 'creTime',mapping: 'creTime'},
			{name: 'upTime',mapping: 'upTime'}
		]),
		autoLoad: true
	});
	
	function txnRen(val){
		return TxnStore.query("valueField",val).first().data.displayField;
		 
	}
	function txnChannel(val){
		return ChannelStore.query("valueField",val).first().data.displayField;
		 
	}
	function binRen(val){
		if(val=='*')return "所有卡bin";
			return val;
	}

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '渠道号',dataIndex: 'channelname',width: 100},
		{header: '卡bin',dataIndex: 'cardbinold',width: 150,renderer:binRen},
		{header: '交易码',dataIndex: 'txnnumold',width: 180},
		{header: '审核状态',dataIndex: 'sastatue',width: 120,renderer:statue},
		{header: '创建人',dataIndex: 'creOprId'},
		{header: '审核人',dataIndex: 'upOprId'},
		{header: '创建时间',dataIndex: 'creTime',renderer: formatDt},
		{header: '审核时间',dataIndex: 'upTime',renderer: formatDt},
		{id: 'id',header: 'id',dataIndex: 'id',sortable: true,width: 300,hidden:true}
	]);
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '删除';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '删除未审核';
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
	
	//菜单添加
	var menuArr = new Array();
	menuArr.push(queryCondition);  //[0]
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			id: 'idStatu',
			fieldLabel: '审核状态',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','删除'],['2','正常'],['3','修改未审核'],['4','删除未审核']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '渠道号',
			methodName: 'getChannel',
			id:'Channelid',
			hiddenName: 'channel',
			editable: true,
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
			gridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var grid = new Ext.grid.EditorGridPanel({
		title:'渠道交易黑名单管理',
		iconCls: 'T10108',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载渠道交易黑名单信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//保存按钮初始化不可用
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}		
	});
	
	/********************************主界面*************************************************/
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			statue: queryForm.findById('idStatu').getValue(),
			channel: queryForm.findById('Channelid').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});

Ext.onReady(function() {
	//拒绝类型
	var refuseTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('REFUSE_TYPE',function(ret){
		refuseTypeStore.loadData(Ext.decode(ret));
	});	
	//交易数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
//	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
	SelectOptionsDWR.getComboData('TXNNUM',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	//权限
	var limitStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('LIMIT',function(ret){
		limitStore.loadData(Ext.decode(ret));
	});	
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo7'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'brhID',mapping: 'brhID'},
			{name: 'oprID',mapping: 'oprID'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'},
			{name: 'param1',mapping: 'param1'},
			{name: 'param2',mapping: 'param2'},
			{name: 'param3',mapping: 'param3'},
			{name: 'param4',mapping: 'param4'}
		])
	});
	
	function txnRen(val){
		return txnStore.query("valueField",val).first().data.displayField;
	}
	function limit(val){
		return limitStore.query("valueField",val).first().data.displayField;
	}
	
	/** *************************查询条件************************ */
	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 200,
		autoHeight : true,
		items : [{
			name : 'refuseType',
			fieldLabel : '拒绝类型*',
			allowBlank : false,
			blankText : '拒绝类型不能为空',
			store:refuseTypeStore,
			xtype: 'basecomboselect',
			id : 'idrefuseType',
			hiddenName : 'refuseType',
			width : 150
		}]
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,width: 120,renderer: formatTs},
		{header: '操作员所属分公司',dataIndex: 'brhID',width: 150},
		{header: '操作员编号',dataIndex: 'oprID',width: 100},
		{header: '拒绝类型',dataIndex: 'refuseType',renderer: riskInfoRefuseState},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 100},
		{header: '渠道号',dataIndex: 'param1',width: 100,id:'param1'	},
		{header: '卡bin',dataIndex: 'param2',width: 100,id:'param2'},
		{header: '交易码',dataIndex: 'param3',width: 100,id:'param3'	,renderer:txnRen},
		{header: '交易权限',dataIndex: 'param4',width: 100,id:'param4',renderer:limit}
	]);
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	var menuArr = new Array();
	var queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};

	menuArr.push(queryConditionMebu); // [0]
	
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 300,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [
			{
				id : 'minimize',
				handler : function(event, toolEl, panel, tc) {
					panel.tools.maximize.show();
					toolEl.hide();
					queryWin.collapse();
					queryWin.getEl().pause(1);
					queryWin.setPosition(10, Ext.getBody().getViewSize().height - 30);
				},
				qtip : '最小化',
				hidden : false
			}, {
				id : 'maximize',
				handler : function(event, toolEl, panel, tc) {
					panel.tools.minimize.show();
					toolEl.hide();
					queryWin.expand();
					queryWin.center();
				},
				qtip : '恢复',
				hidden : true
			} ],
		buttons : [ {
			text : '查询',
			handler : function() {
			reasonStore.load({
				params : {
					start : 0,
					refuseType: queryForm.findById('idrefuseType').getValue()
					}
				});
		}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	var grid = new Ext.grid.GridPanel({
		title: '渠道交易权限审核拒绝原因查询',
		region: 'center',
		iconCls: 'T20103',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		tbar : menuArr,
		//autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载渠道交易权限审核拒绝原因列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});

	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
				start: 0,
			    refuseType: queryForm.findById('idrefuseType').getValue()
		});
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
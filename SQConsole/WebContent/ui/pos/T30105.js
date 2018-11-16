Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'termId',mapping: 'termId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,width: 150,renderer: formatTs},
		{header: '终端号',dataIndex: 'termId',width: 150},
		//{header: '交易机构',dataIndex: 'brhId',width: 100},
		{header: '交易操作员',dataIndex: 'oprId'},
		{header: '交易类型',dataIndex: 'refuseType',width: 150},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 200,id:'refuseInfo'}
	]);
	
	var grid = new Ext.grid.GridPanel({
		title: '终端拒绝原因查询',
		region: 'center',
		iconCls: 'T30105',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载终端拒绝信息列表......'
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
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	grid.on({
		'rowdblclick':function(){
			 var rec = grid.getSelectionModel().getSelected();
			 if(rec.get('refuseType')=='新增审核拒绝'){
			 	showConfirm('确定要恢复该条记录吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.requestNeedAuthorise( {
									url : 'T201001Action.asp?method=recoverTerm',
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);
											grid.getStore().reload();
										} else {
											showErrorMsg(rspObj.msg, grid);
										}
									},
									params : {
										termId : rec.get('termId'),
										txnId : '201001',
										subTxnId : '03'
									}
								});
							}
						});
			 }
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
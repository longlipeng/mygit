Ext.onReady(function() {
	
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agenfeequery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'feeid'
		},[
			{name: 'agenid',mapping: 'agenid'},
			{name: 'feeid',mapping: 'feeid'},
			{name: 'termid',mapping: 'termid'},
			{name: 'mtchno',mapping: 'mtchno'},
			{name: 'mcccode',mapping: 'mcccode'},
			{name: 'tradeeacceptreg',mapping: 'tradeeacceptreg'},
			{name: 'statue',mapping: 'statue'}
		]),
		autoLoad: true
	});

	var mchntColModel = new Ext.grid.ColumnModel([
	    {id: 'feeid',header: '分润编号',dataIndex: 'feeid',width: 120,sortable: true},
	    {header: '机构号',dataIndex: 'agenid',width: 120},
		{header: '终端号',dataIndex: 'termid',width: 120,renderer:all},
		{header: '商户编号',dataIndex: 'mtchno',width: 150,renderer:all},
		{header: 'MCC码',dataIndex: 'mcccode',width: 100,renderer:all},
		{header: '交易受理地区',dataIndex: 'tradeeacceptreg',width: 180,renderer:all},
		{header: '审核状态',dataIndex: 'statue',width: 100}
	]);
	function all(val){
		if(val=='*'){
			return '全部支持'; 
		}else{
			return val;
		}
	}
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showAgencyFeeDetailS(mchntGrid.getSelectionModel().getSelected().get('feeid'), 
					mchntGrid.getSelectionModel().getSelected().get('agenid'),mchntGrid)
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

	menuArr.push(detailMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(queryCondition);  //[2]
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '机构分润查询',
		region: 'center',
		iconCls: 'T10109',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构分润信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.agenid
//		termStore.load({
//			params: {
//				start: 0,
//				mchntNo: id
//				}
//			})
	});
//	termStore.on('beforeload', function() {
//		termStore.removeAll();
//	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
		}
	});

	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '审核状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','正常'],['2','修改未审核'],['8','注销未审核'],['7','注销']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '机构编号',
			methodName: 'getAgenID',
			hiddenName: 'agenid',
			editable: true,
			width: 200
		},{
			xtype: 'textfield',
			fieldLabel: 'MCC码',
			id: 'mccCode',
			editable: true,
			width: 100
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
				mchntStore.reload();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			agenid: queryForm.getForm().findField('agenid').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mccCode:queryForm.findById('mccCode').getValue()
//			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
//			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
//			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
//			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});
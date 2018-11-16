Ext.onReady(function() {
	
	
	
	
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=hisdiscalquery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'DISC_ID'
		},[
			{name: 'DISC_ID',mapping: 'DISC_ID'},
			{name: 'TERM_ID',mapping: 'TERM_ID'},
			{name: 'MCHT_NO',mapping: 'MCHT_NO'},
			{name: 'TO_BRCH_NO',mapping: 'TO_BRCH_NO'},
			{name: 'FK_BRCH_NO',mapping: 'FK_BRCH_NO'},
			{name: 'SA_SATUTE',mapping: 'SA_SATUTE'}
		]),
		autoLoad: true
	});

	var mchntColModel = new Ext.grid.ColumnModel([
	
	     {header: '费率代码',dataIndex: 'DISC_ID',width: 100,hiddeen:true},
	    {header: '终端代码',dataIndex: 'TERM_ID',width: 200},
		{header: '商户代码',dataIndex: 'MCHT_NO',width: 200,renderer:all},
		{header: '目的机构',dataIndex: 'TO_BRCH_NO',width: 200,renderer:all},
		{header: '发卡机构',dataIndex: 'FK_BRCH_NO',width: 200,renderer:all},
		{header: '审核状态',dataIndex: 'SA_SATUTE',width: 200,renderer: riskInfoState}
		
	]);
	function all(val){
		if(val=='*'){
			return '全部支持'; 
		}
		else{
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
			showAgencyDetailS(mchntGrid.getSelectionModel().getSelected().get('DISC_ID'),mchntGrid)
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

	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户手续费规则查询',
		region: 'center',
		iconCls: 'T10403',
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
			msg: '正在加载商户手续费规则信息列表......'
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
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'basecomboselect',
			id: 'idStatus',
			fieldLabel: '审核状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['2','正常状态'],['3','修改未审核'],['4','注销未审核']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 200
		},{
			xtype: 'textfield',
			fieldLabel: '商户编号',
			id: 'idmchtid',
			name: 'mchtid',
			editable: true,
			width: 200
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
				mchntStore.load();
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
			idStatus: queryForm.findById('idStatus').getValue(),
			idmchtid: queryForm.getForm().findField('idmchtid').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});
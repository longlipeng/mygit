Ext.onReady(function() {
	
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ageninfoquery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'agenid'
		},[
			{name: 'agenid',mapping: 'agenid'},
			{name: 'agenname',mapping: 'agenname'},
			{name: 'statue',mapping: 'statue'},
			{name: 'agenregbody',mapping: 'agenregbody'},
			{name: 'bankname',mapping: 'bankname'},
			{name: 'agenincomeaccountname',mapping: 'agenincomeaccountname'},
			{name: 'agenexpressaccountname',mapping: 'agenexpressaccountname'},
			{name: 'tranType',mapping: 'tranType'}
//			{name: 'commEmail',mapping: 'commEmail'},
//			{name: 'manager',mapping: 'manager'},
//			{name: 'contact',mapping: 'contact'},
//			{name: 'commTel',mapping: 'commTel'},
//			{name: 'applyDate',mapping: 'applyDate'},
//			{name: 'mchtStatus',mapping: 'mchtStatus'},
//			{name: 'termCount',mapping: 'termCount'},
//			{name: 'crtOprId',mapping: 'crtOprId'},
//			{name: 'updOprId',mapping: 'updOprId'},
//			{name: 'updTs',mapping: 'updTs'}
		]),
		autoLoad: true
	});
//	var mchntRowExpander = new Ext.ux.grid.RowExpander({
//		tpl: new Ext.Template(
//			'<p>商户英文名称：{engName}</p>',
//			'<p>商户MCC：{mcc:this.getmcc()}</p>',
//			'<p>商户地址：{addr}</p>',
//			'<p>邮编：{postCode}</p>',
//			'<p>电子邮件：{commEmail}</p>',
//			'<p>法人代表名称：{manager}</p>',
//			'<p>联系人姓名：{contact}</p>',
//			'<p>联系人电话：{commTel}</p>',
//			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
//			'<p>最近更新时间：{updTs}</p>',{
//			getmcc : function(val){return getRemoteTrans(val, "mcc");}
//			}
//		)
//	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
	//	mchntRowExpander,
		{id: 'agenid',header: '机构号',dataIndex: 'agenid',width: 100},
		{header: '机构名称',dataIndex: 'agenname',width: 200,id: 'agenname'},
		{header: '审核状态',dataIndex: 'statue',renderer: mchntSt},
		{header: '机构交易地区',dataIndex: 'agenregbody',width: 130,renderer:function(val){return getRemoteTrans(val, "AgencyCity");}},
		{header: '开户行名称',dataIndex: 'bankname',width: 80},
		{header: '机构收入账号开户名',dataIndex: 'agenincomeaccountname',width: 150},
		{header: '机构支出账号开户名',dataIndex: 'agenexpressaccountname',width: 150},
		{header: '交易连接类型',dataIndex: 'tranType',width: 150,hidden:true}
	]);
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 60},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 90},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
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
			showAgencyDetailS(mchntGrid.getSelectionModel().getSelected().get('agenid'),mchntGrid.getSelectionModel().getSelected().get('tranType'),mchntGrid)
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
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('agenid'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};	
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 260,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '机构信息查询',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'agenname',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
	//	plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载机构信息列表......'
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
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			})
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});	
	
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
	
	// Mcc下拉列表
//	var MccStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data',
//		idProperty: 'valueField'
//	});
//	
//	SelectOptionsDWR.getComboData('MCC',function(ret){
//		MccStore.loadData(Ext.decode(ret));
//	});
//	// Mcc下拉列表
//	var MccCombo = new Ext.form.ComboBox({
//		store: MccStore,
//		displayField: 'displayField',
//		valueField: 'valueField',
//		emptyText: '请选择Mcc',
//		mode: 'local',
//		triggerAction: 'all',
//		forceSelection: true,
//		typeAhead: true,
//		selectOnFocus: true,
//		editable: false,
//		allowBlank: true,
//		blankText: '请选择Mcc',
//		fieldLabel: 'Mcc',
//		id: 'mcc'
//	});
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '审核状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','正常'],['2','修改未审核'],['3','新增拒绝']
//				           ,['4','修改拒绝'],['6','注销拒绝']
				           ,['7','注销'],['8','注销未审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '机构编号',
			methodName: 'getAgenID',
			hiddenName: 'agenid',
			editable: true,
			width: 380
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
			agenid: queryForm.getForm().findField('agenid').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue()
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
Ext.onReady(function() {
	
	// 当前选择记录
	var record;
	
	// 差错信息登记数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=errorList'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'errType',mapping: 'errType'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'fee1',mapping: 'fee1'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'registTime',mapping: 'registTime'},
			{name: 'startTime',mapping: 'startTime'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
		{header: '商户编号',dataIndex: 'mchntNo',width: 120},
		{header: '差错类型',dataIndex: 'errType',width: 100},		
		{header: '本金',dataIndex: 'amt1',width: 100},
		{header: '手续费',dataIndex: 'fee1',width: 100},
		{header: '是否入账',dataIndex: 'reserved',width: 100,renderer: saAction},
		{header: '登记时间',dataIndex: 'registTime',width: 120,renderer: formatTs},
		{header: '生效时间',dataIndex: 'startTime',width: 120,renderer: formatTs}
	]);
	
	
	// 受控动作
	function saAction(val) {
		if(val == '0')
			return '<font color="gray">未入账</font>';
		else if(val == '1')
			return '<font color="red">已入账</font>';
		else 
			return '<font color="green">状态未知</font>';
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
	
	// 差错信息登记列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '差错信息列表',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: mchntRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntRiskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载差错信息列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: mchntRiskStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.on({
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[6] != undefined) {
				grid.getTopToolbar().items.items[6].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	// 可选差错交易类型下拉列表
	var errTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM',function(ret){
		errTypeStore.loadData(Ext.decode(ret));
	});
	
	// 可选差错交易类型下拉列表
	var errTypeCombo = new Ext.form.ComboBox({
		store: errTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择差错交易类型',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个差错交易类型',
		fieldLabel: '差错交易类型',
		id: 'errTypeId',
		name: 'errType',
		hiddenName: 'errType'
	});
	

	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 600,
		autoHeight: true,
		items: [{
                columnWidth: 1,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
						xtype: 'combo',
						fieldLabel: '商户编号*',
						allowBlank: false,
						store: mchntStore,
						id: 'mchntId',
						hiddenName: 'mchnt',
						editable: true,
						blankText: '商户编号不能为空',
						emptyText: '请选择商户编号',
						width: 250
					}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
						xtype: 'combo',
						fieldLabel: '差错交易类型*',
						allowBlank: false,
						store: errTypeStore,
						id: 'erTypeId',
						hiddenName: 'erType',
						editable: true,
						emptyText: '请选择差错交易类型',
						width: 250
					}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '起始登记时间',
                    allowBlank: true,
                    id: 'stRegTime',
                    width: 250,
                    name: 'stRegTime'
                }]
            }
            ,{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终止登记时间',
                    allowBlank: true,
                    id: 'enRegTime',
                    width: 250,
                    name: 'enRegTime'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '起始生效时间',
                    allowBlank: true,
                    id: 'stTime',
                    width: 250,
                    name: 'stTime'
                }]
            }
            ,{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终止生效时间',
                    allowBlank: true,
                    id: 'enTime',
                    width: 250,
                    name: 'enTime'
                }]
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
				mchntRiskStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.findById('mchntId').getValue(),
			errType: queryForm.findById('erTypeId').getValue(),
			stRegTime: queryForm.findById('stRegTime').getValue(),
			enRegTime: queryForm.findById('enRegTime').getValue(),
			stTime: queryForm.findById('stTime').getValue(),
			enTime: queryForm.findById('enTime').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});
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
		},[ {name: 'errSeqNo',mapping: 'errSeqNo'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'mchntNm',mapping: 'mchntNm'},
			{name: 'errType',mapping: 'errType'},
			{name: 'errDesc',mapping: 'errDesc'},
			{name: 'cdFlag',mapping: 'cdFlag'},
			{name: 'sysSeqNo',mapping: 'sysSeqNo'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'fee1',mapping: 'fee1'},
			{name: 'registOpr',mapping: 'registOpr'},
			{name: 'reserved',mapping: 'reserved'},			
			{name: 'registTime',mapping: 'registTime'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recordFlag',mapping: 'recordFlag'},
			{name: 'errStatus',mapping: 'errStatus'},
			{name: 'startTime',mapping: 'startTime'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([	
	    {header: '差错编号',dataIndex: 'errSeqNo',width: 120},                                               
		{header: '商户编号',dataIndex: 'mchntNo',width: 120},
		{header: '审核状态',dataIndex: 'errStatus',width: 80,renderer: shAction,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','已审核'],['0','未审核'],['2','审核退回']]
			}),
			editable: false,
			allowBlank: false,
			blankText: '请选择一个审核状态',
			hiddenName: 'shActionEdit'
		})},
		{header: '差错类型',dataIndex: 'errType',width: 100},		
		{header: '本金',dataIndex: 'amt1',width: 100	},
		{header: '手续费',dataIndex: 'fee1',width: 100},
		{header: '是否入账',dataIndex: 'reserved',width: 100},	
		{header: '登记时间',dataIndex: 'registTime',width: 120,renderer: formatTs},
		{header: '生效时间',dataIndex: 'startTime',width: 120,renderer: formatTs}
	]);
	
	// 审核类型
	function shAction(val) {
		if(val == '1')
			return '<font color="gray">已审核</font>';
		else if(val == '0')
			return '<font color="red">未审核</font>';
		else if(val == '2')
			return '<font color="blue">审核退回</font>';
//		else if(val == '4')
//			return '<font color="green">已取消黑名单</font>';
	}
	var menuArray = new Array();
	var errAccept = {
	        text: '审核状态',
	        width: 85,
	        iconCls: 'accept',
	        disabled: true,
	        handler:function() {
				showConfirm('确认审核状态吗？',grid,function(bt) {
					if(bt == 'yes') {
						var modifiedRecords = grid.getStore().getModifiedRecords();
						if(modifiedRecords.length == 0) {
							return;
						}
						var store = grid.getStore();
						var len = store.getCount();
						for(var i = 0; i < len; i++) {
							var record = store.getAt(i);
						}
						showProcessMsg('正在提交审核状态信息，请稍后......');
						//存放要修改的卡黑名单信息
						var array = new Array();
						for(var index = 0; index < modifiedRecords.length; index++) {
							var record = modifiedRecords[index];
							var data = {
								id : record.get('errSeqNo'),
								errSeqNo: record.get('errSeqNo'),
								mchntNo: record.get('mchntNo'),
								errStatus: record.get('errStatus'),
								errType: record.get('errType'),
								amt1: record.get('amt1'),
								fee1: record.get('fee1'),
								reserved: record.get('reserved'),
								registTime: record.get('registTime'),
								startTime: record.get('startTime'),
								termId: record.get('termId'),
								mchntNm: record.get('mchntNm'),
								errDesc: record.get('errDesc'),
								cdFlag: record.get('cdFlag'),
								sysSeqNo: record.get('sysSeqNo'),
								registOpr: record.get('registOpr'),
								recCrtTs: record.get('recCrtTs'),
								recUpdTs: record.get('recUpdTs'),
								recordFlag: record.get('recordFlag')
							
							};
							array.push(data);
						}
						Ext.Ajax.request({
							url: 'T60503Action.asp?method=update',
							params: {
								errSeqNoList: Ext.encode(array),
								txnId: '60503',
								subTxnId: '02'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载商户待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
	   }


	var queryMenu = {
		text: '查询',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	
	menuArray.add(errAccept);

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
			if(grid.getTopToolbar().items.items[0] != undefined) {
				grid.getTopToolbar().items.items[0].enable();
			
			}
		}
		
	});
	
	grid.getSelectionModel().on({
		//单击行，使审核按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据所选择的状态判断哪个按钮可用
//			rec = Grid.getSelectionModel().getSelected();
//			grid.getTopToolbar().items.items[0].enable();
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
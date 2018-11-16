Ext.onReady(function() {	
		
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=storesTerm'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'branchId',mapping: 'branchId'},
			{name: 'termSn',mapping: 'termSn'},
			{name: 'instDate',mapping: 'instDate'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: '商户号',dataIndex: 'mchtNo',id: 'mchtNo',width: 120},
		{header: '终端号',dataIndex: 'termId',id: 'termId',width: 100/*,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})*/},
		{header: '门店号',dataIndex: 'branchId', id: "branchId", width: 100/*,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})*/},
		{header: '终端序列号',dataIndex: 'termSn',width: 250 
		/*,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})*/},
		{header: '创建日期',dataIndex: 'instDate',width: 250,renderer: formatTs,sortable: true /*,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})*/}
    ]);
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条发卡终端信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T60101Action.asp?method=delete1',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
									oprGrid.getStore().reload();
									oprGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
							},
							params: { 
								termSn: rec.get('termSn'),								
								txnId: '60101',
								subTxnId: '04'
							}
						});
					}
				});
			}
		}};
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	var menuArr = new Array();
	menuArr.push('-');				 // [0]
	menuArr.push(queryCondition);	 // [1]
	menuArr.push('-');				 // [2]
	menuArr.push(delMenu);			 // [3]
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '终端发卡终端信息',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载终端发卡终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[3].disable();
		oprGrid.getStore().rejectChanges();
	});
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[3].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户号',
			id:'idmchtNo',
			name: 'mchtNo',
			width: 150
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '终端号',
			id:'idtermId',
			name: 'termId',
			width: 150
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '门店号',
			id:'idbranchId',
			name: 'branchId',
			width: 150
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '终端序列号',
			id:'idtermSn',
			name: 'termSn',
			width: 150
		},{
			xtype: 'datefield',
			id: 'idinstDate',
			name: 'instDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '创建日期',
			editable: false
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			mchtNo: queryForm.getForm().findField('mchtNo').getValue(),
			termId: queryForm.getForm().findField('termId').getValue(),
			branchId: queryForm.getForm().findField('branchId').getValue(),
			termSn: queryForm.getForm().findField('termSn').getValue(),
			instDate: queryForm.getForm().findField('instDate').getValue() == '' ? '' : queryForm.getForm().findField('instDate').getValue().format('Ymd')
//			instDate: queryForm.getForm().findField('instDate').getValue().format('Ymd')
//			if(queryForm.findById('endDate').getValue()!=null && queryForm.findById('endDate').getValue()!=''){},
//	        		endDateTemp = queryForm.findById('endDate').getValue().format('Ymd'),
//					instDate: queryForm.getForm().findField('instDate').getValue().format('Ymd'),
		});
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
});
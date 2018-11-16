Ext.onReady(function() {
	// 应答码信息数据集
	var rspCodeMapStore= new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=rspCodeMapInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'srcId',mapping: 'srcId'},
			{name: 'destId',mapping: 'destId'},
			{name: 'srcRspCode',mapping: 'srcRspCode'},
			{name: 'srcRspCodeLOld',mapping: 'srcRspCodeLOld'},
			{name: 'destRspCode',mapping: 'destRspCode'},
			{name: 'destRspCodeLOld',mapping: 'destRspCodeLOld'},
			{name: 'rspCodeDspOld',mapping: 'rspCodeDspOld'},
			{name: 'statusid',mapping: 'statusid'},
			{name: 'oprtime',mapping: 'oprtime'},
			{name: 'checktime',mapping: 'checktime'},
			{name: 'oprid',mapping: 'oprid'},
			{name: 'checkid',mapping: 'checkid'}
		]),
		autoLoad:true
	}); 
	
	var rspCodeMapColMode = new Ext.grid.ColumnModel([
		{header: '源编号',dataIndex: 'srcId',width: 80,sortable:true},
		{header: '目的编号',dataIndex: 'destId',width: 80},
		{header: '源应答码',dataIndex: 'srcRspCode',width: 80},
		{header: '源应答码长度',dataIndex: 'srcRspCodeLOld',width: 80,
			 editor: new Ext.form.TextField({
			 	maxLength: 40,
				allowBlank: false,
				vtype: 'isOverMax',
				regex:/^[0-9]{1}$/,
				regexText: '该输入项只能为1位数字'
		})},
		{header: '目的应答码',dataIndex: 'destRspCode',width: 80},
		{header: '目的应答码长度',dataIndex: 'destRspCodeLOld',width: 100,
			 editor: new Ext.form.TextField({
			 	maxLength: 40,
				allowBlank: false,
				vtype: 'isOverMax',
				regex:/^[0-9]{1}$/,
				regexText: '该输入项只能为1位数字'
		})},
		{header: '状态',dataIndex: 'statusid',width: 80,renderer: riskInfoState},
		{header: '操作人',dataIndex: 'oprid',width: 60},
		{header: '操作时间',dataIndex: 'oprtime',width: 110,renderer: formatTs},
		{header: '审核人',dataIndex: 'checkid',width: 60},
		{header: '审核时间',dataIndex: 'checktime',width: 110,renderer: formatTs},
		{header: '应答码说明',dataIndex: 'rspCodeDspOld',width: 300,
			 editor: new Ext.form.TextField({
			 	maxLength: 40,
				allowBlank: false,
				vtype: 'isOverMax'
		})}
	]);
	
	var menuArr = new Array();
	//应答码添加表单
	var rspCodeMapForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '源编号',
			allowBlank: false,
			emptyText: '请输入源编号',
			id: 'idsrcId',
			name: 'srcId',
			width: 200,
			maxLength: 4,
//			maskRe:/^[0-9]$/,
			maxLengthText: '源编号最多可以输入4个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '目的编号',
			allowBlank: false,
//			blankText: '目的编号不能为空',
			emptyText: '请输入目的编号',
			id: 'iddestId',
			name: 'destId',
//			maskRe:/^[0-9]$/,
			width: 200,
			maxLength: 4,
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '源应答码',
			allowBlank: false,
			blankText: '源应答码不能为空',
			emptyText: '请输入源应答码',
			id: 'idsrcRspCode',
			maxLength: 128,
			name: 'srcRspCode',
			regex:/^[0-9a-zA-Z]+$/,
			regexText: '该输入项只能包含数字、字母',
			width: 200
		},{
			fieldLabel: '源应答码长度',
			allowBlank: false,
			blankText: '源应答码长度不能为空',
			emptyText: '请输入源应答码长度',
			id: 'idsrcRspCodeLOld',
			name: 'srcRspCodeLOld',
			regex:/^[0-9]{1}$/,
			regexText: '该输入项只能为1位数字',
			width: 200
		},{
			fieldLabel: '目的应答码',
			allowBlank: false,
			blankText: '目的应答码不能为空',
			emptyText: '请输入目的应答码',
			id: 'iddestRspCode',
			name: 'destRspCode',
			maxLength: 128,
			regex:/^[0-9a-zA-Z]+$/,
			regexText: '该输入项只能包含数字、字母',
			width: 200
		},{
			fieldLabel: '目的应答码长度',
			allowBlank: false,
			blankText: '目的应答码长度不能为空',
			emptyText: '请输入目的应答码长度',
			id: 'iddestRspCodeLOld',
			name: 'destRspCodeLOld',
			regex:/^[0-9]{1}$/,
			regexText: '该输入项只能为1位数字',
			width: 200
		},{
			fieldLabel: '应答码说明',
			allowBlank: false,
			blankText: '应答码说明不能为空',
			emptyText: '请输入应答码说明',
			maxLength: 64,
			id: 'idrspCodeDspOld',
			name: 'rspCodeDspOld',
			width: 200
		}
	]
	});
	//应答码添加窗口
	var rspCodeWin = new Ext.Window({
		title: '应答码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [rspCodeMapForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(rspCodeMapForm .getForm().isValid()) {
					rspCodeMapForm.getForm().submit({
						url: 'T20109Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,rspCodeMapForm);
							//重置表单
							rspCodeMapForm.getForm().reset();
							//重新加载应答码列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,rspCodeMapForm);
						},
						params: {
							txnId: '20109',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
			rspCodeMapForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
			rspCodeWin.hide(grid);
			}
		}]
	});
	
	//保存
	var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = grid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = grid.getStore();
				var len = store.getCount();
				for(var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}		
				//存放要修改的参数信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						srcId: record.get('srcId'),
						destId: record.get('destId'),
						srcRspCode: record.get('srcRspCode'),
						srcRspCodeLOld: record.get('srcRspCodeLOld'),
						destRspCode: record.get('destRspCode'),
						destRspCodeLOld: record.get('destRspCodeLOld'),
						rspCodeDspOld: record.get('rspCodeDspOld')
					};
					array.push(data);
				}
				showProcessMsg('正在保存信息，请稍后......');
				Ext.Ajax.request({
					url: 'T20109Action.asp?method=update',
					method: 'post',
					params: {
					parameterList: Ext.encode(array),
						txnId: '20109',
						subTxnId: '02'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						grid.enable();
						if(rspObj.success) {
							grid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,grid);
						} else {
							grid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,grid);
						}
						grid.getTopToolbar().items.items[6].disable();
						grid.getStore().reload();
						hideProcessMsg();
					}
				});
				grid.getTopToolbar().items.items[6].disable();
				hideProcessMsg();
			}
		};
	//添加
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
		rspCodeWin.show();
		rspCodeWin.center();
		}
	};
	
	//删除
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
					
					showConfirm('确定要删除该条记录吗？',grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T20109Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
										//grid.getTopToolbar().items.items[3].disable();
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
									srcId: rec.get('srcId'),
									destId:rec.get('destId'),
									srcRspCode:rec.get('srcRspCode'),
									destRspCode:rec.get('destRspCode'),
									txnId: '20109',
									subTxnId: '03'
								}
							});
						}
					});
				}
			}
		};
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');  //[1]
	menuArr.push(addMenu);  //[2]
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	// 监控模式定时器
	var task = {
		run: function() {
			rspCodeMapStore.load({
				params: {start: 0}
			});
		},
		interval: 10000
	};
	
	// 应答码信息
	var grid = new Ext.grid.EditorGridPanel({
		title: '应答码信息',
		iconCls : 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: rspCodeMapStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: rspCodeMapColMode,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载应答码信息列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: rspCodeMapStore,
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
			//激活菜单
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('statusid')=='1'||rec.get('statusid')=='3'||rec.get('statusid')=='4')//非正常状态不可以删除
				grid.getTopToolbar().items.items[4].disable();
			else
				grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[6].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		'beforeedit':function(e){
			if(e.record.get('statusid')=='4'||e.record.get('statusid')=='1'){
				e.cancel=true;
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[6] != undefined) {
				grid.getTopToolbar().items.items[6].enable();
			}
		}
	});
	
	/***************************查询条件*************************/
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 200,
		autoHeight: true,
		items: [{
			xtype:'textfield',
			id: 'searchSrcId',
			width: 160,
			name: 'searchSrcId',
			fieldLabel: '源编号'
		},{
			xtype: 'textfield',
			id: 'searchDestId',
			width: 160,
			name: 'searchDestId',
			fieldLabel: '目的编号'
		},{
			xtype: 'textfield',
			id: 'searchSrcRspCode',
			width: 160,
			name: 'searchSrcRspCode',
			fieldLabel: '源应答码'
		},{
				xtype: 'textfield',
				id: 'searchDestRspCode',
				width: 160,
				name: 'searchDestRspCode',
				fieldLabel: '目的应答码'
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
			rspCodeMapStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
		}}]
	});

	rspCodeMapStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			srcId:queryForm.findById('searchSrcId').getValue(),
			destId:queryForm.findById('searchDestId').getValue(),
			srcRspCode:queryForm.findById('searchSrcRspCode').getValue(),
			destRspCode:queryForm.findById('searchDestRspCode').getValue()
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
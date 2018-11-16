Ext.onReady(function() {
	
	// 应答码信息数据集
	var respCodeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=respCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'respCodeId',mapping: 'respCodeId'},
			{name: 'respCodeNum',mapping: 'respCodeNum'},
			{name: 'respCodeName',mapping: 'respCodeName'},
			{name: 'respCodeDesc',mapping: 'respCodeDesc'},
			{name:'statusId',mapping:'statusId'}
		]),
		autoLoad: true
	}); 
	
	var respCodeColMode = new Ext.grid.ColumnModel([
		{header: '应答码编号',dataIndex: 'respCodeId',width: 100,sortable: true
	    },
		{header: '应答码代码',dataIndex: 'respCodeNum',width: 100,
		 editor: new Ext.form.TextField({
				 	maxLength: 40,
					allowBlank: false,
					vtype: 'isOverMax'
		})},
		{header: '应答码名称',dataIndex: 'respCodeName',width: 150,
		 editor: new Ext.form.TextField({
		 	maxLength: 40,
			allowBlank: false,
			vtype: 'isOverMax'
		 })},
		{header: '应答码描述',dataIndex: 'respCodeDesc',width: 600,
		 editor: new Ext.form.TextField({
				 	maxLength: 60,
					allowBlank: false,
					vtype: 'isOverMax'
				 })},
		{header: '审核状态',dataIndex: 'statusId',width: 100}
	]);
	
	
	var menuArr = new Array();
		//应答码添加表单
	var respCodeInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '应答码编号*',
			allowBlank: false,
			emptyText: '请输入应答码编号',
			id: 'respCodeId',
			name: 'respCodeId',
			width: 200,
			maxLength: 4,
			maskRe:/^[0-9]$/,
			maxLengthText: '应答码编号最多可以输入4个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '应答码代码*',
			allowBlank: false,
			blankText: '应答码代码不能为空',
			emptyText: '请输入应答码代码',
			id: 'respCodeNum',
			name: 'respCodeNum',
			maskRe:/^[0-9]$/,
			width: 200,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '应答码名称*',
			allowBlank: false,
			blankText: '应答码名称不能为空',
			emptyText: '请输入应答码名称',
			id: 'respCodeName',
			name: 'respCodeName',
			width: 200
		},{
			fieldLabel: '应答码描述*',
			allowBlank: false,
			blankText: '应答码描叙不能为空',
			emptyText: '请输入应答码描述',
			id: 'respCodeDesc',
			name: 'respCodeDesc',
			width: 200
		}
	]
	});
	//应答码添加窗口
	var respCodeWin = new Ext.Window({
		title: '应答码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [respCodeInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(respCodeInfoForm .getForm().isValid()) {
					respCodeInfoForm.getForm().submit({
						url: 'T20107Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,respCodeInfoForm);
							//重置表单
							respCodeInfoForm.getForm().reset();
							//重新加载应答码列表
							grid.getStore().reload();
							
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,respCodeInfoForm);
						},
						params: {
							txnId: '10201',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
			respCodeInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
			respCodeWin.hide(grid);
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
						respCodeid : record.get('respCodeId'),
						respCodeName : record.get('respCodeName'),
						respCodeNum : record.get('respCodeNum'),
						respCodeDesc : record.get('respCodeDesc')
					};
					array.push(data);
				}
				showProcessMsg('正在保存信息，请稍后......');
				Ext.Ajax.request({
					url: 'T20107Action.asp?method=update',
					method: 'post',
					params: {
					respCodeList : Ext.encode(array),
						txnId: '10201',
						subTxnId: '03'
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
			respCodeWin.show();
			respCodeWin.center();
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
								url: 'T20107Action.asp?method=delete',
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
									respCodeId: rec.get('respCodeId'),
									txnId: '10201',
									subTxnId: '02'
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
			respCodeStore.load({
				params: {start: 0}
			});
		},
		interval: 10000
	};
	
	// 应答码信息
	var grid = new Ext.grid.EditorGridPanel({
		title: '应答码信息',
		iconCls: 'T20107',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: respCodeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: respCodeColMode,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载应答码信息列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: respCodeStore,
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
			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	
	
	grid.on({
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
			xtype: 'textfield',
			id: 'sereachRespCodeId',
			width: 160,
			name: 'sereachRespCodeId',
			fieldLabel: '应答码编号'
		},{
			xtype: 'textfield',
			id: 'sereachRespCodeNum',
			width: 160,
			name: 'sereachRespCodeNum',
			fieldLabel: '应答码代码'},
		  {
			xtype: 'textfield',
			id: 'sereachRespCodeName',
			width: 160,
			name: 'sereachRespCodeName',
			fieldLabel: '应答码名称'}
			]
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
			respCodeStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});

	respCodeStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			respCodeId: queryForm.findById('sereachRespCodeId').getValue(),
			respCodeNum: queryForm.findById('sereachRespCodeNum').getValue(),
			respCodeName: queryForm.findById('sereachRespCodeName').getValue()
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
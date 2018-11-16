Ext.onReady(function() {
	
	/************************************系统参数信息*******************************************/
	var sysParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=sysParamInfos'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'owner',mapping: 'owner'},
			{name: 'key',mapping: 'key'},
			{name: 'type',mapping: 'type'},
			{name: 'value',mapping: 'value'},
			{name: 'descr',mapping: 'descr'},
			{name: 'reserve',mapping: 'reserve'}
		])
	});
	 
	sysParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'owner',header: '参数属主',dataIndex: 'owner',sortable: true,width: 100},
		{header: '参数键值',dataIndex: 'key',width: 120},
		{header: '类型',dataIndex: 'type'},
		{header: '显示名称',dataIndex: 'value',width: 150,
		 editor: new Ext.form.TextField({
		 	maxLength: 200,
			allowBlank: false,
			vtype: 'isOverMax'
		 })},
		{header: '参数描述',dataIndex: 'descr',id:'descr',width: 200,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			maxLength: 60,
			vtype: 'isOverMax'
		 })},
		{header: '备注',dataIndex: 'reserve',width: 200,
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 400,
			vtype: 'isOverMax'
		 })}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
		}
	};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
		
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
						Ext.Ajax.request({
							url: 'T10202Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								owner: rec.get('owner'),
								key: rec.get('key'),
								txnId: '10202',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	
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
//			showProcessMsg('正在保存系统参数信息，请稍后......');
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					owner : record.get('owner'),
					key: record.get('key'),
					type: '00',                 //能维护的参数信息默认类型为00
					value: record.get('value'),
					descr: record.get('descr'),
					reserve: record.get('reserve')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10202Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10202',
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
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
//					hideProcessMsg();
				}
			});
			grid.getTopToolbar().items.items[2].disable();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('-');  //[3]
	menuArr.push(queryCondition);  //[4]
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '系统参数信息维护',
		iconCls: 'T10204',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn:'descr',
//		autoHeight: true,
		store: sysParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载系统参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sysParamStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//激活菜单
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	//添加系统参数表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 380,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '系统参数属主*',
			id: 'owner',
			name: 'owner',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '系统参数属主不能为空',
			emptyText: '请输入系统参数属主',
			vtype: 'isOverMax'
		},{
			fieldLabel: '参数键值*',
			id: 'key',
			name: 'key',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '参数键值不能为空',
			emptyText: '请输入参数键值',
			vtype: 'isOverMax'
		},{
			fieldLabel: '参数类型*',
			xtype: 'combo',
			width: 200,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['00','可维护'],['01','不可维护']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'type',
			emptyText: '请选择参数类型',
			fieldLabel: '参数类型*',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个参数类型'
		},{
			fieldLabel: '界面显示名称*',
			id: 'value',
			name: 'value',
			width: 200,
			maxLength: 200,
			allowBlank: false,
			blankText: '界面显示名称不能为空',
			emptyText: '请输入界面显示名称',
			vtype: 'isOverMax'
		},{
			fieldLabel: '参数描述*',
			allowBlank: false,
			blankText: '参数描述不能为空',
			emptyText: '请输入参数描述',
			id: 'descr',
			name: 'descr',
			width: 200,
			maxLength: 60,
			vtype: 'isOverMax'
		},{
			fieldLabel: '备注信息',
			allowBlank: true,
			id: 'reserve',
			name: 'reserve',
			width: 200,
			maxLength: 400,
			vtype: 'isOverMax'
		}]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '参数信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 380,
		autoHeight: true,
		layout: 'fit',
		items: [paramInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(paramInfoForm.getForm().isValid()) {
					paramInfoForm.getForm().submit({
						url: 'T10202Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10202',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				paramInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				paramWin.hide(grid);
			}
		}]
	});
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [
			{
			xtype: 'dynamicCombo',
			methodName: 'getOwner',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '参数属性',
			displayField: 'displayField',
           valueField: 'valueField',
           id:'IDowner',
			name: 'owner',
			editable: true,
			anchor: '90%'
		},/*{
			xtype: 'dynamicCombo',
			methodName: 'getIDkey',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '参数键值',
			 hiddenName: 'key',
			displayField: 'displayField',
            valueField: 'valueField',
            id:'IDkey',
			editable: true,
			anchor: '90%'
		},{
			xtype: 'dynamicCombo',
			methodName: 'getIdValue',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '显示名称',
			 hiddenName: 'value',
			displayField: 'displayField',
            valueField: 'valueField',
            id:'IDvalue',
			editable: true,
			anchor: '90%'
		}*/
		
			{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '参数键值',
			 id:'IDkey',
			name: 'key',
			anchor: '90%'
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '显示名称',
			id:'IDvalue',
			name: 'value',
			anchor: '90%'
		}]
	});
	sysParamStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			owner: queryForm.getForm().findField('IDowner').getValue(),
			key: queryForm.getForm().findField('IDkey').getValue(),
			value: queryForm.getForm().findField('IDvalue').getValue()
		});
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 450,
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
				sysParamStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
			    queryForm.getForm().reset();//20120806修改，是重置Form对象，不是重置Store
			}
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	// 主面板
	var tabPanel = new Ext.TabPanel({
		items: [grid],
		renderTo: Ext.getBody(),
		activeTab: 0
	});
});
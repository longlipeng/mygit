Ext.onReady(function() {	
		
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=branch'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'id',mapping: 'id'},
			{name: 'branchId',mapping: 'branchId'},
			{name: 'branchName',mapping: 'branchName'},
			{name: 'branchAddr',mapping: 'branchAddr'},
			{name: 'createTime',mapping: 'createTime'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: 'ID',dataIndex: 'id',id: 'id',width: 120,hidden:true},
		{header: '门店号',dataIndex: 'branchId',id: 'branchId',width: 100/*,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})*/},
		{header: '门店名称',dataIndex: 'branchName',width: 100,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})},
		{header: '门店地址',dataIndex: 'branchAddr',width: 250
			,editor : new Ext.form.TextField( {
				maxLength : 150,
				allowBlank : false,
				vtype : 'isOverMax'
		})},
		{header: '创建时间',dataIndex: 'createTime',id: 'createTime',width: 120,renderer: formatTs,sortable: true}
    ]);
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条门店信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T60101Action.asp?method=delete',
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
								branchId: rec.get('branchId'),								
								txnId: '60101',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}};
	
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						id : record.get('id'),
						branchId : record.get('branchId'),
						branchName : record.get('branchName'),
						branchAddr: record.get('branchAddr')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T60101Action.asp?method=update',
					method: 'post',
					params: {
					tblIssueBranchInfList: Ext.encode(array),
						txnId: '60101',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
						oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            selectedRecord = oprGrid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            termInfoStore1.load({
                params: {
                     storeId: 'getHolidayInf',
                     id: selectedRecord.get('id')
                },
                callback: function(records, options, success){
                    if(success){
                       updTermForm.getForm().loadRecord(records[0]);
                       updTermWin.show();
                    }else{
                       updTermWin.hide();
                    }
                }
            });
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
		
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 380,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '门店号*',
			id: 'branchId',
			name: 'branchId',
			width: 180,
			maxLength: 20,
			allowBlank: false,
			blankText: '门店号不能为空',
			emptyText: '请输入门店号',
			vtype: 'isOverMax'
		},{
			fieldLabel: '门店名称*',
			id: 'branchName',
			name: 'branchName',
			width: 180,
			maxLength: 100,
			allowBlank: false,
			blankText: '门店名称不能为空',
			emptyText: '请输入门店名称',
			vtype: 'isOverMax'
		},{
			fieldLabel: '门店地址*',
			id: 'branchAddr',
			name: 'branchAddr',
			width: 180,
			maxLength: 150,
			allowBlank: false,
			blankText: '门店地址不能为空',
			emptyText: '请输入门店地址',
			vtype: 'isOverMax'
		}]
	});
	
	//添加窗口
	var paramWin = new Ext.Window({
		title: '添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
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
						url: 'T60101Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
							paramWin.hide(oprGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '60101',
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
				paramWin.hide(oprGrid);
			}
		}]
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
		}
	};
	var menuArr = new Array();
	menuArr.push(addMenu);			 // [0]
	menuArr.push('-');			   	 // [1]
//	menuArr.push(delMenu);		 	 // [2]
//	menuArr.push('-');			 	 // [3]
	menuArr.push(upMenu);			 // [4]
	menuArr.push('-');               // [5]
	menuArr.push(queryCondition);    // [6]
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '终端发卡门店信息',
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
			msg: '正在加载终端发卡门店信息列表......'
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
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[3].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[0].enable();
		oprGrid.getTopToolbar().items.items[2].enable();
		oprGrid.getTopToolbar().items.items[4].enable();
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
			fieldLabel: '门店号',
			id:'idbranchId',
			name: 'branchId',
			width: 150
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '门店名称',
			id:'idbranchName',
			name: 'branchName',
			width: 150
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '门店地址',
			id:'idbranchAddr',
			name: 'branchAddr',
			width: 150
		},{
			xtype: 'datefield',
			id: 'idcreateTime',
			name: 'createTime',
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
			branchId: queryForm.getForm().findField('branchId').getValue(),
			branchName: queryForm.getForm().findField('branchName').getValue(),
			branchAddr: queryForm.getForm().findField('branchAddr').getValue(),
			createTime: queryForm.getForm().findField('createTime').getValue() == '' ? '' : queryForm.getForm().findField('createTime').getValue().format('Ymd')
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
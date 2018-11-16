Ext.onReady(function() {	
	// 可选机构数据集
	/*var brhCityStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CITY_CODE_OLD',function(ret){
		brhCityStore.loadData(Ext.decode(ret));
	});*/
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cityCodeInfoNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'CITY_CODE',mapping: 'CITY_CODE'},
			{name: 'CITY_DES',mapping: 'CITY_DES'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'CITY_CODE',header: '机构地区代码',dataIndex: 'CITY_CODE',sortable: true},
		{header: '机构地区名称',dataIndex: 'CITY_DES',align: 'center',width: 300,
			editor: new Ext.form.TextField({
				maxLength: 60,
				allowBlank: false
		 	})}
    ]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			cityCodeWin.show();
			cityCodeWin.center();
		}
	};
	
	// 地区码表单
	var cityCodeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '地区代码*',
			id: 'CITY_CODE',
			name: 'CITY_CODE',
			allowBlank: false,
			maskRe:/^[0-9]$/,
			regex:/^[0-9]{4}$/,
			regexText:'地区代码为4个数字',
			blankText: '该输入项只能包含数字',
			width:150
		},{
			fieldLabel: '地区名称*',
			id: 'CITY_DES',
			name: 'CITY_DES',
			width: 150,
			maxLength: 120,
			allowBlank: false,
			blankText: '地区名称不能为空',
			emptyText: '请输入地区名称',
			vtype: 'isOverMax'
		}]
	});
	
	// 地区码信息添加窗口
	var cityCodeWin = new Ext.Window({
		title: '地区码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(cityCodeForm.getForm().isValid()) {
					cityCodeForm.getForm().submitNeedAuthorise({
						url: 'T10201Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cityCodeForm);
							//重置表单
							cityCodeForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cityCodeForm);
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
				cityCodeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin.hide(oprGrid);
			}
		}]
	});
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该条地区信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10201Action.asp?method=delete',
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
								CITY_CODE: rec.get('CITY_CODE'),
								txnId: '10201',
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
			var modifiedRecords = oprGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
//			showProcessMsg('正在保存地区码信息，请稍后......');
			//存放要修改的地区码信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					CITY_CODE : record.get('CITY_CODE'),
					CITY_DES : record.get('CITY_DES')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10201Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    cityCodeList: Ext.encode(array),
					txnId: '10201',
					subTxnId: '03'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,cityCodeForm);
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
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(upMenu);		// [4]
	menuArr.push('-');          // [5]
	menuArr.push(queryCondition);// [6]	
	
	// 地区码信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '地区码信息',
		iconCls: 'cityCode',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载地区码信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
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
			fieldLabel: '地区代码',
			 id:'idcitycodesearch',
			name: 'citycodesearch',
			anchor: '90%'
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '地区名称',
			width:'300',
			id:'idcityname',
			name: 'cityname',
			anchor: '90%'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 250,
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
			    queryForm.getForm().reset();
			}
		}]
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});	
	
	oprGrid.getStore().on('beforeload',function() {
		Ext.apply(this.baseParams, {
			cityCode: queryForm.getForm().findField('idcitycodesearch').getValue(),
			cityName: queryForm.getForm().findField('idcityname').getValue()
		});
	});
});
Ext.onReady(function() {
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankNoStat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'bankNo'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'accountName',mapping: 'accountName'},
			{name: 'bankAccount',mapping: 'bankAccount'},
			{name: 'creTime',mapping: 'creTime'},
			{name: 'bankStatus',mapping: 'bankStatus'},
			{name: 'region',mapping: 'region'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: '银行编号', dataIndex: 'bankNo', align: 'center', id: 'bankNo'},
		{header: '银行名称', dataIndex: 'bankName', align: 'center',
			editor: new Ext.form.TextField({
				allowBlank: false,
				maxLength: 50,
				vtype: 'isOverMax'
		})},
		{header: '账户户名', dataIndex: 'accountName', align: 'center', id:'accountName',
			editor: new Ext.form.TextField({
				allowBlank: false,
				maxLength: 50,
				vtype: 'isOverMax'
		})},
		{header: '银行账号', dataIndex: 'bankAccount', align: 'center',
			editor: new Ext.form.TextField({
				allowBlank: false,
				maxLength: 50,
				vtype: 'isOverMax'
		})},
		{header: '地区', dataIndex: 'region', align: 'center',
			editor: new Ext.form.TextField({
				allowBlank: false,
				maxLength: 50,
				vtype: 'isOverMax'
		})},
		{header: '创建日期', dataIndex: 'creTime', align: 'center', renderer: formatTs, width: 150},
		{header: '状态', dataIndex: 'bankStatus', align: 'center', renderer: bankState}
	]);
	
	function bankState(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		}else if(val == '1') {
			return '<font color="red">审核不通过</font>';
		}else if(val == '2') {
			return '<font color="gray">添加待审查</font>';
		}else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		}else if(val == '4') {
			return '<font color="red">审核不通过</font>';
		}
		return val;
	}
	
	// 菜单集合
	var menuArr = new Array();
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			bankWin.show();
			bankWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条备付金账户信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10801Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
									oprGrid.getStore().reload();
									oprGrid.getTopToolbar().items.items[4].disable();
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
							},
							params: { 
								bankNo: rec.get('bankNo'),
								txnId: '10801',
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
			if(modifiedRecords.length > 1) {
				alert("只允许修改一条记录！");  
				return;
			}
			var record = modifiedRecords['0'];
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10801Action.asp?method=update',
				method: 'post',
				params: {
					bankNo : record.get('bankNo'),
					bankName: record.get('bankName'),
					accountName: record.get('accountName'),
					bankAccount: record.get('bankAccount'),
					region: record.get('region'),
					txnId: '10801',
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
					oprGrid.getTopToolbar().items.items[6].disable();
					oprGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	menuArr.push(queryCondition);  // [0]
//	menuArr.push('-');			   // [1]
//	menuArr.push(addMenu);		   // [2]
	menuArr.push('-');			   // [3]
	menuArr.push(delMenu);		   // [4]
	menuArr.push('-');			   // [5]
	menuArr.push(upMenu);		   // [6]
	// 银行账户列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '备付金账户维护',
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'accountName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载备付金账户列表......'
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
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
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
			//激活菜单
			oprGrid.getTopToolbar().items.items[2].enable();
			
		}
	});
	
	
	
/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		labelWidth: 80,
		autoHeight: true,
		items: [{
					xtype:'textfield',
					fieldLabel: '银行名称',
					name: 'bankName',
					id: 'bankName',
					width: 150
				},{
					xtype:'textfield',
					fieldLabel: '银行账号',
					id: 'bankAccount',
					name: 'bankAccount',
					width: 150
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	// 银行账户添加表单
	var bankInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 330,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '银行名称*',
			allowBlank: false,
			emptyText: '请输入银行名称',
			id: 'bankName',
			name: 'bankName',
			width: 160,
			maxLength: 40,
			vtype: 'isOverMax'
		},{
			fieldLabel: '账户户名*',
			allowBlank: false,
			blankText: '账户户名不能为空',
			emptyText: '请输入账户户名',
			maxLength: 40,
			width: 160,
			id: 'accountName',
			name: 'accountName',
			vtype: 'isOverMax'
		},{
			fieldLabel: '银行账号*',
			width: 160,
			allowBlank: false,
			blankText: '银行账号不能为空',
			emptyText: '请输入银行账号',			
			vtype: 'isOverMax',
			id: 'bankAccount',
			name: 'bankAccount'
		},{
			fieldLabel: '地区*',
			width: 160,
			allowBlank: false,
			blankText: '地区不能为空',
			emptyText: '请输入地区',			
			vtype: 'isOverMax',
			id: 'region',
			name: 'region'
		}]
	});
	
	// 银行账户添加窗口
	var bankWin = new Ext.Window({
		title: '银行账户添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 330,
		autoHeight: true,
		layout: 'fit',
		items: [bankInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(bankInfoForm.getForm().isValid()) {
					bankInfoForm.getForm().submitNeedAuthorise({
						url: 'T10801Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,bankInfoForm);
							//重置表单
							bankInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
							bankWin.hide(oprGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,bankInfoForm);
						},
						params: {
							txnId: '10801',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				bankInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				bankWin.hide(oprGrid);
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			bankName: queryForm.findById('bankName').getValue(),
			bankAccount: queryForm.findById('bankAccount').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});
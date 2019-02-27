Ext.onReady(function(){
	
	//数据来源
	var oprGridStore  = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=rosterGridStoreTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'rosterId'
		},[
		   {name: 'rosterId',mapping: 'rosterId'},
		   {name: 'rosterBankCard',mapping: 'rosterBankCard'},
		   {name: 'rosterAccount',mapping: 'rosterAccount'},
		   {name: 'rosterAccountName',mapping: 'rosterAccountName'},
		   {name: 'rosterStatus',mapping: 'rosterStatus'},
		   {name: 'rosterLaunchTime',mapping: 'rosterLaunchTime'},
		   {name: 'rosterLaunchName',mapping: 'rosterLaunchName'},
		   {name: 'rosterAuditTime',mapping: 'rosterAuditTime'},
		   {name: 'rosterAuditName',mapping: 'rosterAuditName'}
	    ])
	});
	
	//加载数据源
	oprGridStore.load({
		params: {
			start: 0
		}
	});
	
	//显示列表信息列
	var oprColModel = new Ext.grid.ColumnModel([
    	{header: '账户白名单id',dataIndex: 'rosterId',align: 'center',id: 'rosterId',hidden: true},
    	{header: '收款方开户行行号',dataIndex: 'rosterBankCard',align: 'center',
    		editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/^[0-9]*$/,
				regexText:'开户行行号只能是数字',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
    	{header: '收款方账号',dataIndex: 'rosterAccount',align: 'center',
    		editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/^[0-9]*$/,
				regexText:'收款方账号只能是数字',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
    	{header: '收款方账户名称',dataIndex: 'rosterAccountName',align: 'center',
    		editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/^[a-zA-Z0-9\u4e00-\u9fa5]+$/,
				regexText:'账户名称有误重新输入',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
    	{header: '审核状态',dataIndex: 'rosterStatus',renderer: rosterStatus,align: 'center'},
    	{header: '发起日期',dataIndex: 'rosterLaunchTime',align: 'center'},
    	{header: '发起人员',dataIndex: 'rosterLaunchName',align: 'center'},
    	{header: '审核日期',dataIndex: 'rosterAuditTime',align: 'center'},
    	{header: '审核人员',dataIndex: 'rosterAuditName',align: 'center'}
	]);
	
	//审核状态
	function rosterStatus(val){
		if(val=='0'){
			return '<font color="green">正常</font>';
		}else if(val=='1'){
			return '<font color="gray">修改待审核</font>';
		}else if(val=='2'){
			return '<font color="gray">删除待审核</font>';
		}else if(val=='3'){
			return '<font color="gray">新增待审核</font>';
		}
	}
	
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
		id: 'delete1',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function(){
			if(oprGrid.getSelectionModel().hasSelection()){
				var rec = oprGrid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条账户白名单吗?',oprGrid,function(bt){
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T91101Action.asp?method=delete',
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
								rosterId: rec.get('rosterId'),
								txnId: '91102',
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
		id: 'update1',
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var rec = oprGrid.getSelectionModel().getSelected();
			var modifiedRecords = oprGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			if(modifiedRecords.length > 1) {
				alert("只允许修改一条记录！");  
				return;
			}
			var record = modifiedRecords['0'];
	//		alert(record.get('rosterAccount'));
			Ext.Ajax.requestNeedAuthorise({
				url: 'T91101Action.asp?method=update',
				method: 'post',
				params: {
					rosterId: rec.get('rosterId'),
					rosterBankCard: rec.get('rosterBankCard'),
					rosterAccount: rec.get('rosterAccount'),
					rosterAccountName: record.get('rosterAccountName'),
					txnId: '91102',
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
	menuArr.push('-');			   // [1]
	menuArr.push(addMenu);		   // [2]
	menuArr.push('-');			   // [3]
	menuArr.push(delMenu);		   // [4]
	menuArr.push('-');			   // [5]
	menuArr.push(upMenu);		   // [6]
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '账户白名单维护',
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
			msg: '正在加载白名单账户列表......'
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
		Ext.getCmp('update1').disable();
	//	oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			Ext.getCmp('update1').enable();
		}
	});

	//选中数据删除按钮显示
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
	//		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
			//激活菜单
	//		oprGrid.getTopToolbar().items.items[4].enable();
			
			var rec = oprGrid.getSelectionModel().getSelected();
			if(rec.get('rosterStatus')=='0'){
				Ext.getCmp('delete1').enable();
			}else{
				Ext.getCmp('delete1').disable();
			}
		}
	});
	
	//form表单
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '白名单账户',
			methodName: 'getRosterAccountAll',
			hiddenName: 'rosterAccount',
			editable: true,
			width: 250
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var bankInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '白名单开户行行号*',
			allowBlank: false,
			blankText: '开户行行号不能为空',
			emptyText: '请输入开户行行号',
			regex:/^[0-9]*$/,
			regexText:'开户行行号只能是数字',
			width: 150,
			id: 'rosterBankCard',
			name: 'rosterBankCard',
		},{
			fieldLabel: '白名单账号*',
			width: 150,
			allowBlank: false,
			blankText: '白名单账号不能为空',
			emptyText: '请输入白名单账号',	
			regex:/^[0-9]*$/,
			regexText:'账号只能是数字',
			vtype: 'isOverMax',
			id: 'rosterAccount',
			name: 'rosterAccount'
		},{
			fieldLabel: '白名单账户名称*',
			width: 150,
			allowBlank: false,
			blankText: '白名单账户名称不能为空',
			emptyText: '请输入白名单账户名称',		
			regex:/^[a-zA-Z0-9\u4e00-\u9fa5]+$/,
			regexText:'账户名称有误重新输入',
			vtype: 'isOverMax',
			id: 'rosterAccountName',
			name: 'rosterAccountName'
		}]
	});
	
	var bankWin = new Ext.Window({
		title: '账户白名单添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
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
				//	alert("进来了");
					bankInfoForm.getForm().submit({
						url: 'T91101Action.asp?method=add',
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
							txnId: '91102',
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
			rosterAccount: queryForm.getForm().findField('rosterAccount').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});
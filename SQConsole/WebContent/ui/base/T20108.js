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
		{header: '应答码代码',dataIndex: 'respCodeNum',width: 100},
		{header: '应答码名称',dataIndex: 'respCodeName',width: 150},
		{header: '应答码描述',dataIndex: 'respCodeDesc',width: 600},
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
	//通过
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled:false,
		handler:function() {
		showConfirm('确认审核通过吗？',grid,function(bt) {
			if(bt == 'yes') {
				showProcessMsg('正在提交审核信息，请稍后......');
				rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T20108Action.asp?method=accept',
					params: {
						respCodeId: rec.get('respCodeId'),
						statusId:rec.get('statusId'),
						txnId: '20201',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,grid);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
						// 重新应答码待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		});
	}
	};
	
	//删除
	var refuseMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: false,
			handler: function() {
		showConfirm('确认拒绝吗？',grid,function(bt) {
			if(bt == 'yes') {
				showInputMsg('提示','请输入拒绝原因',true,refuse);
			}
		});
	   }
		};
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20108Action.asp?method=refuse',
				params: {
					respCodeId: rec.get('respCodeId'),
					txnId: '20201',
					subTxnId: '03',
					refuseInfo: text
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
	}
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	

   
	menuArr.push(acceptMenu);  
	menuArr.push('-');
	menuArr.push(refuseMenu);
	menuArr.push('-');
	menuArr.push(queryConditionMebu);
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
		title: '应答码审核',
		iconCls: 'T20108',
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
	
	
	/***************************查询条件*************************/
	var queryConditionMebu = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
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
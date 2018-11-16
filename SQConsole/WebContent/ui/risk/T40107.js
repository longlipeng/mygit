Ext.onReady(function() {
	// 当前选择记录
	var record;
	
	// 风险模型数据集
	var riskModelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			id: 'saModelKind'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saModelName',mapping: 'saModelName'},
			{name: 'saLimitNum',mapping: 'saLimitNum'},
			{name: 'saLimitAmount',mapping: 'saLimitAmount'},
			{name: 'saBeUse',mapping: 'saBeUse'},
			{name: 'saAction',mapping: 'saAction'}
		]),
		autoLoad: true
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
		{id: 'saModelKind',header: '模型',dataIndex: 'saModelKind',width: 60},
		{header: '模型名称',dataIndex: 'saModelName',width: 500},
		{header: '受控交易笔数',dataIndex: 'saLimitNum',width: 160,editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '请输入受控交易笔数',
			regex: /^[0-9]{1,8}$/,
			regexText: '只能输入1-8位数字'
		})},
		{header: '受控金额（元）',dataIndex: 'saLimitAmount',width: 160,editor: new Ext.form.TextField({
			vtype: 'isMoney',
			maxLength: 10,
			allowBlank: false,
			blankText: '请输入受控交易金额'
		})},
		{header: '使用标识',dataIndex: 'saBeUse',width: 60,renderer: saBeUse,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','未启用'],['1','启用']],
				reader: new Ext.data.ArrayReader()
			})
		})},{header: '受控动作',dataIndex: 'saAction',width: 60,renderer: saAction,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','关注'],['2','预警'],['3','冻结']],
				reader: new Ext.data.ArrayReader()
			})
		})}
	]);


	var menuArr = new Array();


	var addMenu = {
			iconCls: 'add',
			width: 85,
			text: '添加',
			hidden:true,
			handler: function() {
				cardRiskWin.show();
				cardRiskWin.center();
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
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
					//record.get(''))
			}
			//存放要修改的监控模型
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('saModelKind'),
					saModelName: record.get('saModelName'),
					saLimitNum: record.get('saLimitNum'),
					saLimitAmount: record.get('saLimitAmount'),
					saBeUse: record.get('saBeUse'),
					saAction: record.get('saAction'),
					saWarnlvt: record.get('saAction')

				};
				array.push(data);
			}
			grid.getTopToolbar().items.items[2].disable();
			Ext.Ajax.request({
				url: 'T40107Action.asp?method=update',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40107',
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
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			//disabled: true,
			handler: function() {		
				if(grid.getSelectionModel().hasSelection()) {
//					var rec = grid.getSelectionModel().getSelected();
//					var saModelKind = rec.get('saModelKind');
						
					showConfirm('确定要删除该风险规则吗？',grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T40107Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
										//20120809修改：操作删除之后，查询按钮变暗
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
									saModelKind: grid.getSelectionModel().getSelected().get('saModelKind'),
									txnId: '40107',
									subTxnId: '03'
								}
							});
						}
					});
				}
			}
		};
	
	var queryMenu = {
			text: '查询',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(upMenu);  
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	// 转译启用标识
	function saBeUse(val) {
		if(val == '1') {
			return '<font color="green">启用</font>';
		} else {
			return '<font color="red">未启用</font>';
		}
	}
	
	// 转译受控动作
	function saAction(val) {
		if(val == '1') {
			return '<font color="green">关注</font>';
		} else if(val == '2') {
			return '<font color="gray">预警</font>';
		} else if(val == '3') {
			return '<font color="red">冻结</font>';
		}else {
			return '未知的受控动作';
		}
	}

	// 风险模型列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '风险模型',
		iconCls: 'risk',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight : false,
		clicksToEdit: true,
		store: riskModelStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn:'saModelKind',
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险模型列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskModelStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		'beforeedit':function(e){
//			if(e.record.get('saModelKind')=='C3'&&e.column==4){
//				e.cancel=true;
//			}else 
//			if(e.record.get('saModelKind')=='M3'&&e.column==2){
//				e.cancel=true;
//			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	

	
	// 风险规则添加表单
	var cardRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			id: 'saModelKind',
			name: 'saModelKind',
			maxLength: 2,
			regex: /^[0-9A-Z]{2}$/,
			regexText: '模型只能是数字或大写字母',
			fieldLabel: '模型*',
			allowBlank: false,
			blankText: '模型不能为空',
			emptyText: '请输入模型'
		},{
			xtype: 'textfield',
			id: 'saModelName',
			name: 'saModelName',
			maxLength: 64,
			//regex: /^[0-9]{1,19}$/,
			//regexText: '卡号只能是数字',
			fieldLabel: '模型名称*',
			allowBlank: false,
			blankText: '模型名称不能为空',
			emptyText: '请输入模型名称'
		},{
			xtype: 'textfield',
			id: 'saLimitNum',
			name: 'saLimitNum',
			maxLength: 8,
			regex: /^[0-9]{1,8}$/,
			regexText: '受控交易笔数只能是数字',
			fieldLabel: '受控交易笔数*',
			allowBlank: false,
			blankText: '受控交易笔数不能为空',
			emptyText: '请输入受控交易笔数'
		},{
			xtype: 'textfield',
			id: 'saLimitAmount',
			name: 'saLimitAmount',
			maxLength: 8,
			regex: /^[0-9]{1,12}$/,
			regexText: '受控金额（圆）只能是数字',
			fieldLabel: '受控金额（圆）*',
			allowBlank: false,
			blankText: '受控金额（圆）不能为空',
			emptyText: '请输入受控金额（圆）'
		},{
			fieldLabel: '使用标识*',
			xtype: 'combo',
			width: 120,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','不启用'],['1','启用']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'saBeUse',
			emptyText: '请选择使用标识',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个使用标识'
		},{
			fieldLabel: '受控动作*',
			xtype: 'combo',
			width: 120,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','关注'],['2','预警'],['3','冻结']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'saAction',
			emptyText: '请选择受控动作',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个受控动作'
		}]
	});
	
	// 风险规则添加窗口
	var cardRiskWin = new Ext.Window({
		title: '风险规则添加',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		resizable: false,
		autoHeight: true,
		items: [cardRiskForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(cardRiskForm.getForm().isValid()) {
					cardRiskForm.getForm().submit({
						url: 'T40107Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cardRiskWin);
							//重新加载参数列表
							grid.getStore().reload();
							cardRiskForm.getForm().reset();
							cardRiskWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cardRiskWin);
						},
						params: {
							txnId: '40107',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cardRiskForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cardRiskWin.hide();
			}
		}]
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'saModelKindQ',
			name: 'saModelKindQ',
			maxLength: 2,
			regex: /^[0-9A-Z]{1,2}$/,
			regexText: '模型只包含数字或大写字母',
			fieldLabel: '模型*',
			allowBlank: true,
			blankText: '模型不能为空',
			emptyText: '请输入模型'
		},{
			xtype: 'textfield',
			id: 'saModelNameQ',
			name: 'saModelNameQ',
			maxLength: 64,
			//regex: /^[0-9]{1,19}$/,
			//regexText: '卡号只能是数字',
			fieldLabel: '模型名称*',
			allowBlank: true,
			blankText: '模型名称不能为空',
			emptyText: '请输入模型名称'
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
				if(queryForm.getForm().isValid()) {
					riskModelStore.load();
					queryWin.hide();
				}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	riskModelStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			saModelKind: queryForm.findById('saModelKindQ').getValue(),
			saModelName: queryForm.findById('saModelNameQ').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
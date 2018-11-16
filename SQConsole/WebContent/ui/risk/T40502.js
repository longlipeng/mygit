Ext.onReady(function() {
	
	// 商户事后风险预警系数待审核数据集
	var cstAfterRuleInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckCstAfterRuleInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'ruleId',mapping: 'ruleId'},
			{name: 'instId',mapping: 'instId'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'days',mapping: 'days'},
			{name: 'warnLvt',mapping: 'warnLvt'},
			{name: 'warnCount',mapping: 'warnCount'},
			{name: 'warnAmt',mapping: 'warnAmt'},
			{name: 'saState',mapping: 'saState'},
			{name: 'createTime',mapping: 'createTime'},
			{name: 'updateTime',mapping: 'updateTime'},
			{name: 'creator',mapping: 'creator'},
			{name: 'updateOpr',mapping: 'updateOpr'}
		])
	});
	
	cstAfterRuleInfStore.load({
		params: {
			start: 0
		}
	});
	
	var cardRiskColModel = new Ext.grid.ColumnModel([ {
		header : '规则编号',
		dataIndex : 'ruleId',
		width : 100,
		sortable : true
	}, {
		header : '分公司编号',
		dataIndex : 'instId',
		width : 100
	}, {
		header : '商户类别',
		dataIndex : 'mcc',
		width : 100
	}, {
		header : '天数',
		dataIndex : 'days',
		width : 80
	}, {
		header : '预警系数',
		dataIndex : 'warnLvt',
		width : 80
	}, {
		header : '预警笔数',
		dataIndex : 'warnCount',
		width : 80
	}, {
		header : '预警金额（元）',
		dataIndex : 'warnAmt',
		width : 80
	}, {
		header : '状态',
		dataIndex : 'saState',
		width : 80,
		renderer: riskInfoState
	}, {
		header : '创建时间',
		dataIndex : 'createTime',
		renderer: formatTs,
		width : 100
	},{
		header : '修改时间',
		dataIndex : 'updateTime',
		renderer: formatTs,
		width : 100
	}, {
		header : '创建人',
		dataIndex : 'creator',
		width : 80
	}, {
		header : '修改人',
		dataIndex : 'updateOpr',
		width : 80
	}]);
	
	
	var menuArray = new Array();
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',grid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T40502Action.asp?method=accept',
							params: {
							    saState: rec.get('saState'),
							    ruleId: rec.get('ruleId'),
							    instId: rec.get('instId'),
							    mcc: rec.get('mcc'),
								txnId: '40502',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载商户事后风险预警系数待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
		};
		
		var refuseMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: true,
			handler:function() {
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
					url: 'T40502Action.asp?method=refuse',
					params: {
					    saState: rec.get('saState'),
						ruleId: rec.get('ruleId'),
					    instId: rec.get('instId'),
					    mcc: rec.get('mcc'),
						txnId: '40502',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,grid);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
						// 重新加载商户事后风险预警系数待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArray.add(acceptMenu);//[0]
	menuArray.add('-');       //[1]
	menuArray.add(refuseMenu);//[2]
	
	// 待审核的商户事后风险预警系数列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户事后风险预警系数审核',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: cstAfterRuleInfStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRiskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户事后风险预警系数待审核列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: cstAfterRuleInfStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			//rec = mchntGrid.getSelectionModel().getSelected();

			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	/*grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function(sm, rowIdx, r) {
		var id = grid.getSelectionModel().getSelected().data.mchtNo;
	}
	});*/
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	
	// 风险监控模型待审核数据集
	var ctlTxnInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getToCheckRiskModelInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			id: 'saModelKind'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saBranchCode',mapping: 'saBranchCode'},
			{name: 'saDays',mapping: 'saDays'},
			{name: 'saLimitNum',mapping: 'saLimitNum'},
			{name: 'saLimitAmount',mapping: 'saLimitAmount'},
			{name: 'saBeUse',mapping: 'saBeUse'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saState',mapping: 'saState'},
			{name: 'modiZoneNo',mapping: 'modiZoneNo'},
			{name: 'modiOprId',mapping: 'modiOprId'},
			{name: 'modiTime',mapping: 'modiTime'},
			{name: 'saDaysM',mapping: 'saDaysM'},
			{name: 'saLimitNumM',mapping: 'saLimitNumM'},
			{name: 'saLimitAmountM',mapping: 'saLimitAmountM'},
			{name: 'saBeUseM',mapping: 'saBeUseM'},
			{name: 'saActionM',mapping: 'saActionM'}
		]),
		autoLoad: true
	});
	
	ctlTxnInfoStore.load({
		params: {
			start: 0
		}
	});
	
	var riskColModel = new Ext.grid.ColumnModel([
	 	    new Ext.grid.RowNumberer(),
	 		{id: 'saModelKind',header: '模型名称',dataIndex: 'saModelKind',renderer: saModelKind,width: 350},
	 		{header: '有效天数N',dataIndex: 'saDaysM',width: 70},
	 		{header: '受控交易笔数',dataIndex: 'saLimitNumM'},
	 		{header: '受控金额（元）',dataIndex: 'saLimitAmountM'},
	 		{header: '使用标识',dataIndex: 'saBeUseM',width: 60,renderer: saBeUse},
	 		{header: '受控动作',dataIndex: 'saActionM',width: 60,renderer: saAction},
	 		{header: '审核状态',dataIndex: 'saState',width: 80,renderer: riskInfoState},
	 		{header: '更新分公司',dataIndex: 'modiZoneNo',width: 80},
	 		{header: '更新操作员',dataIndex: 'modiOprId',width: 80},
	 		{header: '更新时间',dataIndex: 'modiTime',width: 100,renderer: formatTs}
	 	]);
	
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
							url: 'T40105Action.asp?method=accept',
							params: {
							    saModelKind: rec.get('saModelKind'),
								txnId: '40105',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载风险监控模型待审核信息
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
					url: 'T40105Action.asp?method=refuse',
					params: {
					    saModelKind: rec.get('saModelKind'),
						txnId: '40105',
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
						// 重新加载卡黑名单待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArray.add(acceptMenu);//[0]
	menuArray.add('-');       //[1]
	menuArray.add(refuseMenu);//[2]
	
	// 风险监控模型列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '风险监控模型审核',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: ctlTxnInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险监控模型列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: ctlTxnInfoStore,
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
			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	// 转译风险模型
	function saModelKind(val) {
		if(val == 'C1') {
			return 'N日内，同一卡号在同一商户内交易限制';
		} else if(val == 'C2') {
			return 'N日内，同一卡号在同一受理行内交易限制';
		} else if(val== 'C3') {
			return 'N日内，同一卡号交易限制';
		} else if(val == 'M1') {
			return '同一商户当日某笔授权回应为"查询发卡方"后，继续进行同金额同卡号交易';
		} else if(val == 'M2') {
			return '同一商户当日内发生的授权回应在受控范围内';
		} else if(val == 'M3') {
			return '同一商户当日同一卡号交易限制';
		} else if(val == 'M4') {
			return '同一商户当日交易金额限制';
		} else if(val == 'M5') {
			return '同一商户当日有超过一笔同金额的限制';
		} else if(val == 'R1') {
			return '同卡在几家商户交易间隔短于正常时间';
		} else {
			return '未知的模型描述';
		}
	}
	
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
		if(val == '0') {
			return '<font color="green">警告</font>';
		} else if(val == '1') {
			return '<font color="red">拒绝</font>';
		} else {
			return '未知的受控动作';
		}
	}
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
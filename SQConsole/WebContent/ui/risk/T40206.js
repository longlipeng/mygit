Ext.onReady(function() {
	
	// 卡黑名单数据集
	var cardRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckCardRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saCardNo',mapping: 'saCardNo'},
			{name: 'saState',mapping: 'saState'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saInitOprId',mapping: 'saInitOprId'},
			{name: 'saInitTime',mapping: 'saInitTime'},
			{name: 'saModiOprId',mapping:'saModiOprId'},
			{name: 'remarkAdd',mapping: 'remarkAdd'},
			{name: 'riskRole',mapping: 'riskRole'}
		])
	});
	
	cardRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var cardRiskColModel = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),                                             	
		{header: '交易卡号',dataIndex: 'saCardNo',width: 200},
		{header: '状态',dataIndex: 'saState',width: 120,renderer: riskInfoState},
//		{header: '受控金额',dataIndex: 'saLimitAmt',width: 90},
//		{header: '受控动作',dataIndex: 'saAction',width: 90},
		{header: '申请人',dataIndex: 'saInitOprId',width: 90},
		{header: '审核人',dataIndex: 'saModiOprId',width: 90},
		{header: '风险规则',dataIndex: 'riskRole',width: 90},
		{header: '申请人',dataIndex: 'saInitOprId',width: 90},
		{header: '申请时间',dataIndex: 'saInitTime',width: 150,renderer: formatTs},
		{header: '申请备注',dataIndex: 'remarkAdd',width: 250}
		
		
	]);
	cardRiskColModel.setHidden(3,true);
	cardRiskColModel.setHidden(4,true);
	
	var menuArray = new Array();
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showInputMsg('提示','请输入审核备注',true,accept);
			}
		};
	
	// 通过按钮调用方法
	function accept(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('审核备注最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入审核备注',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T40206Action.asp?method=accept',
				params: {
				    saCardId: rec.get('saCardNo'),
					txnId: '40206',
					subTxnId: '01',
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
		
	
	
	/*function(bt) {
		if(bt == 'yes') {
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T40206Action.asp?method=accept',
				params: {
					saCardId: rec.get('saCardNo'),
					txnId: '40206',
					subTxnId: '01'
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
	}*/
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
					alert('审核备注最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入审核备注',true,refuse);
					return;
				}
				showProcessMsg('正在提交审核信息，请稍后......');
				rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T40206Action.asp?method=refuse',
					params: {
					    saCardId: rec.get('saCardNo'),
						txnId: '40206',
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
	
	// 卡黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '卡黑名单审核',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: cardRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRiskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载卡黑名单列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: cardRiskStore,
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
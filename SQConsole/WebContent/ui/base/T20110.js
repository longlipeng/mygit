Ext.onReady(function() {
	
	//应答码待审核数据集
	var ctlTxnInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckRspCodeMapInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'srcId',mapping: 'srcId'},
			{name: 'destId',mapping: 'destId'},
			{name: 'srcRspCode',mapping: 'srcRspCode'},
			{name: 'srcRspCodeLOld',mapping: 'srcRspCodeLOld'},
			{name: 'destRspCode',mapping: 'destRspCode'},
			{name: 'destRspCodeLOld',mapping: 'destRspCodeLOld'},
			{name: 'rspCodeDspOld',mapping: 'rspCodeDspOld'},
			{name: 'statusid',mapping: 'statusid'},
			{name: 'oprtime',mapping: 'oprtime'},
			{name: 'checktime',mapping: 'checktime'},
			{name: 'oprid',mapping: 'oprid'},
			{name: 'checkid',mapping: 'checkid'}
		])
	});
	
	ctlTxnInfoStore.load({
		params: {
			start: 0
		}
	});
	
	var cardRiskColModel = new Ext.grid.ColumnModel([ 
	    {header: '源编号',dataIndex: 'srcId',width: 80,sortable:true},
		{header: '目的编号',dataIndex: 'destId',width: 80},
		{header: '源应答码',dataIndex: 'srcRspCode',width: 80},
		{header: '源应答码长度',dataIndex: 'srcRspCodeLOld',width: 80},
		{header: '目的应答码',dataIndex: 'destRspCode',width: 80},
		{header: '目的应答码长度',dataIndex: 'destRspCodeLOld',width: 100},
		{header: '状态',dataIndex: 'statusid',width: 80,renderer: riskInfoState},
		{header: '申请人',dataIndex: 'oprid',width: 60},
		{header: '申请时间',dataIndex: 'oprtime',width: 110,renderer: formatTs},
//		{header: '审核人',dataIndex: 'checkid',width: 60},
//		{header: '审核时间',dataIndex: 'checktime',width: 110,renderer: formatTs},
		{header: '应答码说明',dataIndex: 'rspCodeDspOld',width: 300}
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
							url: 'T20110Action.asp?method=accept',
							params: {
							    srcId: rec.get('srcId'),
							    destId: rec.get('destId'),
							    srcRspCode: rec.get('srcRspCode'),
							    destRspCode: rec.get('destRspCode'),
								txnId: '20110',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载待审核信息
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
				if(getLength(text.replace(/[ ]/g,"")) == 0) {
					alert('拒绝原因不能为空');
					showInputMsg('提示','请输入进行该操作的原因',true,refuse);
					return;
				}
				
				if(getLength(text) > 60) {
					alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入拒绝原因',true,refuse);
					return;
				}
				showProcessMsg('正在提交审核信息，请稍后......');
				rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T20110Action.asp?method=refuse',
					params: {
						srcId: rec.get('srcId'),
					    destId: rec.get('destId'),
					    srcRspCode: rec.get('srcRspCode'),
					    destRspCode: rec.get('destRspCode'),
						txnId: '20110',
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
						// 重新加载待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArray.add(acceptMenu);//[0]
	menuArray.add('-');       //[1]
	menuArray.add(refuseMenu);//[2]
	
	// 待审核的应答码信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '应答码审核',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: ctlTxnInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRiskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载待审核的应答码信息列表......'
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
			// 根据所选择的状态判断哪个按钮可用
			//rec = mchntGrid.getSelectionModel().getSelected();

			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
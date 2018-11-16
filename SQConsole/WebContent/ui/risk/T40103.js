Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskModelInfoUpdLog'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saBranchCode',mapping:'saBranchCode'},
			{name: 'saFieldName',mapping: 'saFieldName'},
			{name: 'saFieldValueBF',mapping: 'saFieldValueBF'},
			{name: 'saFieldValue',mapping: 'saFieldValue'},
			{name: 'modiOprId',mapping: 'modiOprId'},
			{name: 'modiTime',mapping: 'modiTime'}
		]),
	autoLoad: true
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
		{header: '模型',dataIndex: 'saModelKind',width: 350,renderer:saModelKind,id:'saModelKind'},
		{header: '分公司编号',dataIndex: 'saBranchCode',width: 100},
		{header: '修改属性',dataIndex: 'saFieldName',width: 100,renderer:saFieldName},
		{header: '原值',dataIndex: 'saFieldValueBF',width:100, renderer:saFieldValue},
		{header: '修改值',dataIndex: 'saFieldValue',width: 100, renderer:saFieldValue},
		{header: '修改人',dataIndex: 'modiOprId',width: 100},
		{header: '修改时间',dataIndex: 'modiTime',width: 120,renderer: formatTs}
	]);
	
	var _fName;
	// 转译修改属性
	function saFieldName(val,metadata,record,rowIndex) {
		_fName = val;
		var model = record.get('saModelKind')
		if(val == 'saBeUse') {
			return '使用标识';
		} else if(val == 'saDays' ) {
			return '有效天数N';
		} else if(val == 'saLimitAmount' && model != 'R1') {
			return '受控金额(元)';
		} else if(val == 'saLimitNum') {
			return '受控交易笔数';
		} else if(val == 'saAction') {
			return '受控动作';
		} else {
			return '未知';
		}
	}
	// 原值
	function saFieldValue(val) {
		if(_fName == 'saBeUse') {
			return saBeUse(val);
		} else if(_fName == 'saAction') {
			return saAction(val);
		} else if(_fName == 'saLimitAmount') {
			return Math.ceil(val);
		} else {
			return val;
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
	var menuArr = new Array();
	
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	
	// 风险交易监控
	var grid = new Ext.grid.GridPanel({
		title: '监控模型修改记录',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelKind',
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载监控模型修改记录列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskTxnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'srBrhNo',
			name: 'srBrhNo',
			vtype: 'alphanum',
			fieldLabel: '分公司号'
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '修改开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '修改结束日期'
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
				riskTxnStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	riskTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			srBrhNo: queryForm.findById('srBrhNo').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
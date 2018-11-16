Ext.onReady(function() {
	// 当前选择记录
	var record;
	
	// 风险模型数据集
	var riskModelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRiskModelInfo'
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
	
	// 风险模型数据集
//	var RriskModelStore = new Ext.data.Store({
//		proxy: new Ext.data.HttpProxy({
//			url: 'gridPanelStoreAction.asp?storeId=getRRiskModelInfo'
//		}),
//		reader: new Ext.data.JsonReader({
//			root: 'data',
//			totalProperty: 'totalCount',
//			id: 'saModelKind'
//		},[
//			{name: 'saModelKind',mapping: 'saModelKind'},
//			{name: 'saLimitAmount',mapping: 'saLimitAmount'},
//			{name: 'saBeUse',mapping: 'saBeUse'},
//			{name: 'saAction',mapping: 'saAction'},
//			{name: 'modiZoneNo',mapping: 'modiZoneNo'},
//			{name: 'modiOprId',mapping: 'modiOprId'},
//			{name: 'modiTime',mapping: 'modiTime'}
//		]),
//		autoLoad: true
//	}); 
	
	//var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}); 
	var riskColModel = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    //sm,
		{id: 'saModelKind',header: '模型名称',dataIndex: 'saModelKind',renderer: saModelKind,width: 320}	,
		{header: '有效天数N',dataIndex: 'saDaysM',editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '请输入有效天数',
			regex: /^[0-9]{1,8}$/,
			regexText: '只能输入1-8位数字'
			}),width: 70},
		{header: '受控交易笔数',dataIndex: 'saLimitNumM',editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '请输入受控交易笔数',
			regex: /^[0-9]{1,8}$/,
			regexText: '只能输入1-8位数字'
		})},
		{header: '受控金额（元）',dataIndex: 'saLimitAmountM',editor: new Ext.form.TextField({
			vtype: 'isMoney',
			maxLength: 12,
			allowBlank: false,
			blankText: '请输入受控交易金额'
		})},
		{header: '使用标识',dataIndex: 'saBeUseM',width: 60,renderer: saBeUse,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','未启用'],['1','启用']],
				reader: new Ext.data.ArrayReader()
			})
		})},{header: '受控动作',dataIndex: 'saActionM',width: 60,renderer: saAction,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','警告'],['1','拒绝']],
				reader: new Ext.data.ArrayReader()
			})
		})},{header: '审核状态',dataIndex: 'saState',width: 80,renderer: riskInfoState},
		{header: '更新分公司',dataIndex: 'modiZoneNo',width: 80},
		{header: '更新操作员',dataIndex: 'modiOprId',width: 80},
		{header: '更新时间',dataIndex: 'modiTime',width: 120,renderer: formatTs}
	]);
	
//	var RriskColModel = new Ext.grid.ColumnModel([
//	    new Ext.grid.RowNumberer(),
//	    //sm,
//		{id: 'RsaModelKind',header: '模型名称',dataIndex: 'saModelKind',renderer: saModelKind,width: 400}	,
//		{header: '受控时间(含/秒)',dataIndex: 'saLimitAmount',editor: new Ext.form.TextField({
//			allowBlank: false,
//			blankText: '请输入受控交易笔数',
//			regex: /^[0-9]{1,8}$/,
//			regexText: '只能输入1-8位数字'
//		})},
//		{header: '使用标识',dataIndex: 'saBeUse',width: 60,renderer: saBeUse,editor: new Ext.form.ComboBox({
//			store: new Ext.data.ArrayStore({
//				fields: ['valueField','displayField'],
//				data: [['0','未启用'],['1','启用']],
//				reader: new Ext.data.ArrayReader()
//			})
//		})},	{header: '受控动作',dataIndex: 'saAction',width: 60,renderer: saAction,editor: new Ext.form.ComboBox({
//			store: new Ext.data.ArrayStore({
//				fields: ['valueField','displayField'],
//				data: [['0','警告'],['1','拒绝']],
//				reader: new Ext.data.ArrayReader()
//			})
//		})},
//		{header: '更新分公司',dataIndex: 'modiZoneNo',width: 80},
//		{header: '更新操作员',dataIndex: 'modiOprId',width: 80},
//		{header: '更新时间',dataIndex: 'modiTime',width: 110,renderer: formatTs}
//	]);
	
	var menuArr = new Array();

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
					saBranchCode: record.get('saBranchCode'),
					saDays: record.get('saDays'),
					saBeUse: record.get('saBeUse'),
					saAction: record.get('saAction'),
					saLimitNum: record.get('saLimitNum'),
					saLimitAmount: record.get('saLimitAmount'),
					saDaysModify: record.get('saDaysM'),
					saBeUseModify: record.get('saBeUseM'),
					saActionModify: record.get('saActionM'),
					saLimitNumModify: record.get('saLimitNumM'),
					saLimitAmountModify: record.get('saLimitAmountM') 
				};
				array.push(data);
			}
			grid.getTopToolbar().items.items[0].disable();
			Ext.Ajax.request({
				url: 'T40101Action.asp',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40101',
					subTxnId: '01'
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
	
	menuArr.push(upMenu);  
	
	// 转译风险模型
	function saModelKind(val) {
		if(val == 'C1') {
			return 'N日内，同一卡号在同一商户内交易限制';
		} else if(val == 'C2') {
			return 'N日内，同一卡号在同一受理行内交易限制';
		} else if(val== 'C3') {
			return 'N日内，同一卡号交易笔数限制';
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
		} else if(val == 'M6') {
			return '商户当日单笔消费金额限制';
		}else if(val == 'M7') {
			return '商户当日消费笔数超限限制';
		}else if(val == 'M8') {
			return '商户当日消费累计金额超限限制';
		}else if(val == 'R1') {
			return '同卡在几家商户交易间隔短于正常时间';
		} else if(val=='T1'){
			return '终端当日单笔交易金额限制';
		}else if(val=='T2'){
			return '终端当日交易笔数限制';
		}else if(val=='T3'){
			return ' 终端当日交易累计金额限制';
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
			return '<font color="gray">拒绝</font>';
		} else {
			return '未知的受控动作';
		}
	}

	// 风险模型列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '风险模型',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
//		height: 280,
		autoHeight : false,
		clicksToEdit: true,
		store: riskModelStore,
		//sm: sm,
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
			if(e.record.get('saModelKind')=='C3'&&e.column==4){
				e.cancel=true;
			}else 
			if(e.record.get('saModelKind')=='M3'&&e.column==2){
				e.cancel=true;
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[0] != undefined) {
				grid.getTopToolbar().items.items[0].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	// 风险模型列表
//	var Rgrid = new Ext.grid.EditorGridPanel({
//		title: '风险模型',
//		iconCls: 'risk',
//		frame: true,
//		border: true,
//		columnLines: false,
//		stripeRows: true,
//		region:'center',
//		clicksToEdit: true,
//		store: RriskModelStore,
//		autoExpandColumn:'RsaModelKind',
//		//sm: sm,
//		cm: RriskColModel,
//		forceValidation: true,
//		renderTo: Ext.getBody(),
//		loadMask: {
//			msg: '正在加载风险模型列表......'
//		},
//		tbar:[{
//			text: '保存',
//			width: 85,
//			iconCls: 'reload',
//			disabled: true,
//			handler: function() {
//				var modifiedRecords = Rgrid.getStore().getModifiedRecords();
//				if(modifiedRecords.length == 0) {
//					return;
//				}
//				var store = Rgrid.getStore();
//				var len = store.getCount();
//				for(var i = 0; i < len; i++) {
//					var record = store.getAt(i);
//						//record.get(''))
//				}
//				//存放要修改的监控模型
//				var array = new Array();
//				for(var index = 0; index < modifiedRecords.length; index++) {
//					var record = modifiedRecords[index];
//					var data = {
//						id : record.get('saModelKind'),
//						saLimitAmount: record.get('saLimitAmount'),
//						saBeUse: record.get('saBeUse'),
//						saAction: record.get('saAction')
//					};
//					array.push(data);
//				}
//				Rgrid.getTopToolbar().items.items[0].disable();
//				Ext.Ajax.request({
//					url: 'T40101Action.asp',
//					method: 'post',
//					params: {
//						modelDataList : Ext.encode(array),
//						txnId: '40101',
//						subTxnId: '01'
//					},
//					success: function(rsp,opt) {
//						var rspObj = Ext.decode(rsp.responseText);
//						Rgrid.enable();
//						if(rspObj.success) {
//							Rgrid.getStore().commitChanges();
//							showSuccessMsg(rspObj.msg,Rgrid);
//						} else {
//							Rgrid.getStore().rejectChanges();
//							showErrorMsg(rspObj.msg,Rgrid);
//						}
//						Rgrid.getStore().reload();
//						hideProcessMsg();
//					}
//				});
//			}
//		}],
//		bbar: new Ext.PagingToolbar({
//			store: RriskModelStore,
//			pageSize: System[QUERY_RECORD_COUNT],
//			displayInfo: true,
//			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
//			emptyMsg: '没有找到符合条件的记录'
//		})
//	});
//		
//	Rgrid.getStore().on('beforeload',function() {
//		Rgrid.getStore().rejectChanges();
//	});
//		
//	Rgrid.on({
//		//在编辑单元格后使保存按钮可用
//		'afteredit': function(e) {
//			if(Rgrid.getTopToolbar().items.items[0] != undefined) {
//				Rgrid.getTopToolbar().items.items[0].enable();
//			}
//		}
//	});
//	
//	Rgrid.getSelectionModel().on({
//		'rowselect': function() {
//			//行高亮
//			Ext.get(Rgrid.getView().getRow(Rgrid.getSelectionModel().last)).frame();
//		}
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
//		items: [grid,Rgrid],
		items: [grid],
		renderTo: Ext.getBody()
	});
})
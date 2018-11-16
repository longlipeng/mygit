Ext.onReady(function() {
	
	// 商户交易黑名单数据集
	var ctlTxnInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckTblRiskMchtTranCtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'id',mapping: 'id'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'channel',mapping: 'channel'},
			{name: 'bussType',mapping: 'bussType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'saState',mapping: 'saState'},
			{name: 'oprID',mapping: 'oprID'},
			{name: 'updateTime',mapping: 'updateTime'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'reserved1',mapping: 'reserved1'}
		])
	});
	
	//拒绝类型
	var refuseTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('REFUSE_TYPE',function(ret){
		refuseTypeStore.loadData(Ext.decode(ret));
	});	
	//交易类型
	var TxnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TXNNUM',function(ret){
		TxnStore.loadData(Ext.decode(ret));
	});
	//渠道交易号
	var ChannelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISKCHANNEL',function(ret){
		ChannelStore.loadData(Ext.decode(ret));
	});
	//业务数据集
	var serverStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_BUSSTYPE',function(ret){
		serverStore.loadData(Ext.decode(ret));
	});
	
	function txnRen(val){
		return TxnStore.query("valueField",val).first().data.displayField;
	}
	function txnChannel(val){
		return ChannelStore.query("valueField",val).first().data.displayField;
	}
	function serverRen(val){
		return serverStore.query("valueField",val).first().data.displayField;
	}

	function riskLvl(val){
		if(val=='1')
			return '低风险';
		if(val=='2')
			return '一般风险';
		if(val=='3')
			return '风险商户';
		if(val=='4')
			return '较高风险';
		if(val=='5')
			return '限制商户';
		return '未知风险';
	}
	
	var cardRiskColModel = new Ext.grid.ColumnModel([ {
		header : '商户编号',
		dataIndex : 'mchtNo',
		width : 100,
		sortable : true
	}, {
		header: '商户中文名称',dataIndex: 'mchtNm',width: 200
	}, {
		header : '交易渠道',
		dataIndex : 'channel',
		width : 150
		,renderer:txnChannel
       /*,
		editor : new Ext.form.ComboBox( {
			//maxLength : 40,
			//allowBlank : false,
			//vtype : 'isOverMax'
			store: ctlTxnInfoStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		})*/
	}, {
		header : '业务类型',
		dataIndex : 'bussType',
		width : 150
		,renderer:serverRen
 /*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header : '交易类型',
		dataIndex : 'txnNum',
		width : 150
      ,renderer:txnRen
		/*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header : '状态',
		dataIndex : 'saState',
		width : 150,
		renderer: riskInfoState/*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header: '商户MCC码',dataIndex: 'mcc',width: 80
	}, {
		header: '商户风险等级',dataIndex: 'rislLvl',width: 80,renderer:riskLvl
	}, {
		header: '营业执照编号',dataIndex: 'licenceNo',width: 100
	}, {
		header: '终端数量',dataIndex: 'termCount',width: 80
	}, {
		header: '注册日期',dataIndex: 'applyDate',renderer: formatDt,width: 80
	}, {
		header: '所属分公司',dataIndex: 'bankNo',width: 80
	},{
		header : '操作人',dataIndex : 'oprID',width : 150
	},{
		header : '操作时间',dataIndex : 'updateTime',renderer: formatTs,width : 150
	} ]);
	
	ctlTxnInfoStore.load({
		params: {
			start: 0
		}
	});
	
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
							url: 'T40209Action.asp?method=accept',
							params: {
								id: rec.get('id'),
								txnId: '40209',
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
					url: 'T40209Action.asp?method=refuse',
					params: {
					    id: rec.get('id'),
						txnId: '40209',
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
						// 重新加载商户交易黑名单待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArray.add(acceptMenu);//[0]
	menuArray.add('-');       //[1]
	menuArray.add(refuseMenu);//[2]
	
	// 商户交易黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户交易黑名单审核',
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
			msg: '正在加载商户交易黑名单列表......'
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
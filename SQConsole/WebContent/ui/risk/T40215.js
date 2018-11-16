Ext.onReady(function() {
	
	// 商户交易权限待审核数据集
	var ctlTxnInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckTblRiskMchtTranLimit'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'id',mapping: 'id'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'channelold',mapping: 'channelold'},
			{name: 'bussTypeold',mapping: 'bussTypeold'},
			{name: 'txnNumold',mapping: 'txnNumold'},
			{name: 'cardTypeold',mapping: 'cardTypeold'},
			{name: 'limit',mapping: 'limit'},
			{name: 'saState',mapping: 'saState'},
			{name: 'creOprId',mapping: 'creOprId'},
			{name: 'upOprId',mapping: 'upOprId'},
			{name: 'creTime',mapping: 'creTime'},
			{name: 'upTime',mapping: 'upTime'},
			{name: 'mchtNm',mapping: 'mchtNm'}
		])
	});
	
	ctlTxnInfoStore.load({
		params: {
			start: 0
		}
	});
		// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});		
	//渠道数据集
	var channelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_CHANNEL',function(ret){
		channelStore.loadData(Ext.decode(ret));
	});
	//业务数据集
	var serverStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_BUSSTYPE',function(ret){
		serverStore.loadData(Ext.decode(ret));
	});
	//交易数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_LIMIT_CARDTYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});	
	//权限
	var limitStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('LIMIT',function(ret){
		limitStore.loadData(Ext.decode(ret));
	});	
	function txnRen(val){
		if(txnStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return txnStore.query("valueField",val).first().data.displayField;
		}
		 
	}
	function txnChannel(val){
		if(channelStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return channelStore.query("valueField",val).first().data.displayField;
		}
		 
	}
	function serverRen(val){
		if(serverStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return serverStore.query("valueField",val).first().data.displayField;
		}
		 
	}
	function cardRen(val){
		if(cardTypeStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return cardTypeStore.query("valueField",val).first().data.displayField;
		}
		 
	}
	var cardRiskColModel = new Ext.grid.ColumnModel([ {
		header : '商户编号',
		dataIndex : 'mchtNo',
		width : 100,
		sortable : true
	},{
		header : '商户名',
		dataIndex : 'mchtNm',
		width : 100,
		sortable : true
	}, {
		header : '交易渠道',
		dataIndex : 'channelold',
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
		dataIndex : 'bussTypeold',
		width : 150
       ,renderer:serverRen/*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header : '交易类型',
		dataIndex : 'txnNumold',
		width : 150
      ,renderer:txnRen/*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header : '卡类型',
		dataIndex : 'cardTypeold',
		width : 150
		,renderer:cardRen
	}/*, {
		header : '权限',
		//hidden:true,
		dataIndex : 'limit',
		width : 80
	}*/, {
		header : '状态',
		dataIndex : 'saState',
		width : 80,
		renderer: riskInfoState/*,
		editor : new Ext.form.TextField( {
			maxLength : 40,
			allowBlank : false,
			vtype : 'isOverMax'
		})*/
	}, {
		header : '创建人',
		dataIndex : 'creOprId',
		width : 100
	}, {
		header : '修改时间',
		dataIndex : 'creTime',
		width : 100
		,renderer: formatDt
	} ]);
	
	
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
							url: 'T40215Action.asp?method=accept',
							params: {
								id: rec.get('id'),
								txnId: '40215',
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
					url: 'T40215Action.asp?method=refuse',
					params: {
					    id: rec.get('id'),
						txnId: '40215',
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
	
	// 商户交易权限列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户交易权限审核',
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
			msg: '正在加载商户交易权限列表......'
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
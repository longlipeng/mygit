Ext.onReady(function() {	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCheckInfo4Upd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'flowUpd',mapping: 'flowUpd'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'auditOprId',mapping: 'auditOprId'},
			{name:'agentNo',mapping:'agentNo'},
			{name:'rislLvl',mapping:'rislLvl'}
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 100},
		{header: '商户名称',dataIndex: 'mchtNm',width: 150,id: 'mchtNm'},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 100},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 80,renderer: mchntSt},
		{header: '经办人',dataIndex: 'auditOprId',width: 120,renderer:getOpr},
		{header:'代理商',dataIndex:'agentNo',wedth:150,renderer:getAgent},
		{header: '终端数量',dataIndex: 'termCount',width: 50},
		{header: '商户级别',dataIndex: 'rislLvl',width: 100,renderer:getRislLvl}
	]);
	function getRislLvl(val){
		if(val == '0'){
		return "正常商户";
		}else if(val == '1'){
			return "中级风险商户";
		}else if(val == '2'){
			return "<font color='red'>高级风险商户</font>";
		}else if(val == '3'){
			return "<font color='red'>复审商户</font>";
		}
	}
	function getOpr(val){
		if(val!=null&&val!=''){
		return getRemoteTrans(val,"getOprNm");
		}else{
			return val;
		}
	}
	function getAgent(val){
		if(val!=null&&val!=''){
			return getRemoteTrans(val,"getAgentNm");
		}else{
			return val;
		}
	}
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'posAddr',mapping: 'posAddr'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 60},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 60},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id:'idPosAddr',header: '终端地址',dataIndex: 'posAddr',width: 120,renderer: posAddrVal},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
	function posAddrVal(val){
		if(val!=null&&val!=''){
			return getRemoteTrans(val,"posAddrVal");
		}else{
			return val;
		}
	}
	
	// 菜单集合
	var menuArr = new Array();
	

		
	var auditMenu = {
		text: '审核',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
			auditMchnt4Upd(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);			
		}
	};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	menuArr.push(auditMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(queryCondition);  //[2]
	
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 330,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'idPosAddr',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户维护审核',
		region: 'center',
		iconCls: 'T20201',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();		
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();

			mchntGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('endDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册开始日期不能大于注册结束日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('startDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册结束日期不能小于注册开始日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [/*['1','新增待审核'],*/['3','修改待审核'],['5','冻结待审核'],['7','恢复待审核'],['9','注销待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_NORMAL',
			fieldLabel: '所属分公司',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(), 
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});
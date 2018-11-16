Ext.onReady(function() {	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCheckInfo4Def'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'recId',mapping: 'recId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'legalName',mapping: 'legalName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'busiLicNo',mapping: 'busiLicNo'},
			{name: 'regDate',mapping: 'regDate'},
			{name: 'status',mapping: 'status'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'termCount',mapping: 'termCount'}
		])
	});
	
	
	var mchntColModel = new Ext.grid.ColumnModel([
		{header: '商户名称',dataIndex: 'mchtName',width: 150,id: 'mchtName'},
		{header: '法人',dataIndex: 'legalName',width: 90},
		{header: 'MCC',dataIndex: 'mcc',width: 80,renderer: getmcc},
		{header: '营业执照编号',dataIndex: 'busiLicNo',width: 100},
		{header: '注册日期',dataIndex: 'regDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'status',width: 80,renderer: mchntSt},
//		{header: '提交人',dataIndex: 'oprId',width: 80},
		{header:'代理商',dataIndex:'agentNo',wedth:150,renderer:getAgent},
		{header: '终端数量',dataIndex: 'termCount',width: 50}
	]);
	
	function getmcc(val){
		if(val!=null&&val!=''){
		return getRemoteTrans(val, "mcc");
		}else{
			return val;
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
	
	//终端详情
	var termDetailForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
//		defaultType: 'textfield',
		labelWidth: 120,
		layout:'column',
		waitMsgTarget: true,
		items: [{
            columnWidth: .5,
            layout: 'form',
            items:[{
              xtype: 'combofordispaly',
              fieldLabel: '终端类型',
              baseParams: 'TERM_TYPE',
//              id: 'IDTermTpU',
              hiddenName: 'posTermType'
            }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
              	xtype:'displayfield',
                  fieldLabel: '终端品牌',
                  id: 'posTermBrand',
                  allowBlank: true,
                  maxLength: 40,
					vtype: 'isOverMax'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
              	xtype:'displayfield',
                  fieldLabel: '终端型号',
                  id: 'posTermModel',
                  allowBlank: true,
                  maxLength: 40,
					vtype: 'isOverMax'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
              	xtype:'displayfield',
                  fieldLabel: '终端序列号',
                  id: 'posSn',
                  allowBlank: true,
                  maxLength: 40,
					vtype: 'isOverMax'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
              	xtype:'displayfield',
                  fieldLabel: '电话',
                  id: 'posPhoneNo',
                  allowBlank: true,
                  maxLength: 40,
					vtype: 'isOverMax'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
              	xtype:'displayfield',
                  fieldLabel: 'IMEI号',
                  id: 'posImei',
                  allowBlank: true,
                  maxLength: 40,
					vtype: 'isOverMax'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
                  xtype: 'combofordispaly',
                  fieldLabel: '终端安装地所属省*',
//                  id: 'provinceD',
                  baseParams: 'PROVINCES',
                  hiddenName: 'posProCode'
              }]
          },{
              columnWidth: .5, 
              layout: 'form',
              items: [{
                  xtype: 'combofordispaly',
                  fieldLabel: '终端安装地所属市*',
//                  id:'idcityD',
                  hiddenName: 'posCityCode',
                  baseParams: 'CITIES'
              }]
          },{
              columnWidth: .5, 
              layout: 'form',
              items: [{
                  xtype: 'combofordispaly',
                  fieldLabel: '终端安装地所属区/县',
//                  id:'idareaD',
                  baseParams: 'AREAS',
                  hiddenName:'posCountry'
              }]
          },{
              columnWidth: .5,
              layout: 'form',
              items: [{
                  xtype: 'displayfield',
                  fieldLabel: '终端安装地址',
                  readOnly: true,
//                  id: 'IDtermAddrUpd',
                  name: 'posAddress'
              }]
          }]
	});
	var termDetailWin = new Ext.Window({
		title : '终端详细信息',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 600,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [termDetailForm]
	});
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo4Def'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'posRecId',mapping: 'posRecId'},
			{name: 'posStatus',mapping: 'posStatus'},
			{name: 'posAddr',mapping: 'posAddr'},
			{name: 'posTermType',mapping: 'posTermType'},
			{name: 'posTermBrand',mapping: 'posTermBrand'},
			{name: 'posTermModel',mapping: 'posTermModel'},
			{name: 'posSn',mapping: 'posSn'},
			{name: 'posPhoneNo',mapping: 'posPhoneNo'},
			{name: 'posImei',mapping: 'posImei'},
			{name: 'posProCode',mapping: 'posProCode'},
			{name: 'posCityCode',mapping: 'posCityCode'},
			{name: 'posCountry',mapping: 'posCountry'},
			{name: 'posAddress',mapping: 'posAddress'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '终端状态',dataIndex: 'posStatus',renderer: termSta,width: 60},
		{id:'idPosAddr',header: '终端地址',dataIndex: 'posAddr',width: 120,renderer: posAddrVal}
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
			auditMchnt4Def(mchntGrid.getSelectionModel().getSelected().get('recId'),mchntGrid);			
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
			var posRec=termGrid.getSelectionModel().getSelected();
			termDetailForm.getForm().loadRecord(posRec);
			termDetailWin.show();
//			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
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
		title: '商户进件审核',
		region: 'center',
		iconCls: 'T20201',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtName',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
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
		var id = mchntGrid.getSelectionModel().getSelected().data.recId;
		termStore.load({
			params: {
				start: 0,
				mchntRecId: id
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
		    xtype: 'textfield',
		    fieldLabel: '商户名称',
			id: 'mchtNmId',
			name:'mchtNmId',
			width: 300
	 },{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
		},{
		    xtype: 'dynamicCombo',
			methodName: 'getAgentNo',
			fieldLabel: '代理商',
			width:300,
			hiddenName: 'agentNo'
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
			mchtNm: queryForm.getForm().findField('mchtNmId').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			agentNo:queryForm.getForm().findField('agentNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});
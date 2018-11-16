Ext.onReady(function() {
	
	//省数据集
	var provinceStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('PROVINCES',function(ret){
		provinceStore.loadData(Ext.decode(ret));
	});
	
	//市数据集
	var citiesStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    //区、县数据集
	var areasStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		//proxy:用于从某个途径读取原始数据
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoQueryAre'
		}),
		//reader:用于将原始数据转换成Record实例
		reader: new Ext.data.JsonReader({
			//root:表示当前页显示信息队列
			root: 'data',
			//totalProperty:表示后台数据的记录总数
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'routeFlag',mapping: 'routeFlag'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'auditOprId',mapping: 'auditOprId'},
			{name:'agentNo',mapping:'agentNo'},
			{name:'rislLvl',mapping:'rislLvl'}
		])
		//field:排序的字段                 direction:排序的方式
	 	//sortInfo: {field: 'name', direction: 'DESC'}  
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
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 100},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,id: 'mchtNm'},
		{header: '是否路由',dataIndex: 'routeFlag',width: 60,renderer: isRoute},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 90},
		{header: '注册日期',dataIndex: 'applyDate',width: 70,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt},
		{header: '审核人',dataIndex: 'auditOprId',width: 120,renderer:getOpr},
		{header:'代理商',dataIndex:'agentNo',wedth:150,renderer:getAgent},
		{header: '终端数量',dataIndex: 'termCount',width: 60},
		{header: '商户级别',dataIndex: 'rislLvl',width: 50,renderer:getRislLvl}
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
	
	
	// 是否路由
	function isRoute(val) {
			if(val == '0')
				return "否";
			if(val == '1')
				return "是";
			return val;
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
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid)
		}
	}
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	menuArr.push(detailMenu);  //[0]
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
		title: '商户信息查询',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows:true,
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
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			})
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
		//20120828修复bug：点击右边终端信息列表刷新按钮记录被清除
		Ext.apply(this.baseParams, {
            start: 0,
            mchntNo: mchntGrid.getSelectionModel().getSelected().data.mchtNo
        });
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
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	// Mcc下拉列表
	var MccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCC',function(ret){
		MccStore.loadData(Ext.decode(ret));
	});
	// Mcc下拉列表
	var MccCombo = new Ext.form.ComboBox({
		store: MccStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择Mcc',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择Mcc',
		fieldLabel: 'Mcc',
		id: 'mcc'
	});
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 90,
		items: [/*{
			xtype: 'datefield',
			id: 'crtDate',
			name: 'crtDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '添加日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('updDate').getValue()!='' && (Ext.getCmp('crtDate').getValue() > Ext.getCmp('updDate').getValue())){
						showAlertMsg("创建日期不能大于修改日期，请重新选择！",queryForm);
						Ext.getCmp('crtDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'updDate',
			name: 'updDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '修改日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('crtDate').getValue()!='' && (Ext.getCmp('crtDate').getValue() > Ext.getCmp('updDate').getValue())){
						showAlertMsg("修改日期不能小于创建日期，请重新选择！",queryForm);
						Ext.getCmp('updDate').setValue('');
					}
				}
			}
		},*/{
			xtype: 'datefield',
			id: 'crtDate',
			name: 'crtDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '添加开始时间',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('updDate').getValue()!='' && (Ext.getCmp('crtDate').getValue() > Ext.getCmp('updDate').getValue())){
						showAlertMsg("创建日期不能大于修改日期，请重新选择！",queryForm);
						Ext.getCmp('crtDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'crtEndDate',
			name: 'crtEndDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '添加截止时间',
			editable: false
		},{
			xtype: 'datefield',
			id: 'updDate',
			name: 'updDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '修改开始时间',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('crtDate').getValue()!='' && (Ext.getCmp('crtDate').getValue() > Ext.getCmp('updDate').getValue())){
						showAlertMsg("修改日期不能小于创建日期，请重新选择！",queryForm);
						Ext.getCmp('updDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'updEndDate',
			name: 'updEndDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '修改截止时间',
			editable: false
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['1','新增待审核'],['2','新增审核退回'],['3','修改待审核'],['5','冻结待审核'],['6','冻结'],['7','恢复待审核'],['8','注销'],['9','注销待审核'],['H','黑名单']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 300
		},{
			xtype: 'dynamicCombo',
			methodName: 'getBranchListForSerch',
			fieldLabel: '所属分公司',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			width: 300
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 300
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: '商户组别',
			hiddenName: 'mchtGrp',
			width: 300
		},{
			 xtype: 'dynamicCombo',
			 fieldLabel: '商户所属省',
			 id: 'idprovinceN',
			 methodName: 'getProvinces',
			 hiddenName: 'provinceN',
			 width: 300,
			callFunction : function() {
					Ext.getCmp('idcityN').reset();
					Ext.getCmp('idareaN').reset();
					Ext.getCmp('idcityN').parentP=this.value;
					Ext.getCmp('idcityN').getStore().reload();
					Ext.getCmp('idareaN').getStore().reload();
				}
			/* listeners: {
			    'select': function() {
			    	citiesStore.removeAll();
					SelectOptionsDWR.getComboDataWithParameter('CITIES',queryForm.getForm().findField('idprovince').getValue(),
					function(ret){
					citiesStore.loadData(Ext.decode(ret));
					var rec = citiesStore.getAt(0);
					Ext.getCmp('idcity').setValue(rec.get('valueField'));
					areasStore.removeAll();
					SelectOptionsDWR.getComboDataWithParameter('AREAS',queryForm.getForm().findField('idcity').getValue(),
						function(ret){
							areasStore.loadData(Ext.decode(ret));
							var rec = areasStore.getAt(0);
							Ext.getCmp('idarea').setValue(rec.get('valueField'));
							});
							});
					  }
				}*/
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户所属市',
			id:'idcityN',
			hiddenName: 'cityN',
			methodName: 'getCitys',
			width: 300,
			callFunction : function() {
					Ext.getCmp('idareaN').reset();
					Ext.getCmp('idareaN').parentP=this.value;
					Ext.getCmp('idareaN').getStore().reload();
				}
			/*listeners: {
			'select': function() {
					areasStore.removeAll();
					SelectOptionsDWR.getComboDataWithParameter('AREAS',queryForm.getForm().findField('idcity').getValue(),
						function(ret){
							areasStore.loadData(Ext.decode(ret));
							var rec = areasStore.getAt(0);
							Ext.getCmp('idarea').setValue(rec.get('valueField'));
							});
					    }
					}*/
		},{
		   xtype: 'dynamicCombo',
		   fieldLabel: '商户所属区/县',
		   id:'idareaN',
		   hiddenName:'areaN',
		   methodName: 'getAreas',
		   width: 300
		  /* listeners: {
		   'select': function() {
		   SelectOptionsDWR.getComboDataWithParameter('',mchntForm.getForm().findField('idarea').getValue(),
		   function(ret){	});
		  }
		 }*/
	},{
		    xtype: 'textfield',
		    fieldLabel: '商户拓展人',
			id: 'expanderId',
			name:'expanderId',
			width: 300
	},{
		    xtype: 'basecomboselect',
			baseParams: 'MCHNT_ATTR',
			fieldLabel: '商户性质',
			width:300,
			hiddenName: 'etpsAttr'
	},{
		    xtype: 'dynamicCombo',
			methodName: 'getDiscId',
			fieldLabel: '商户费率',
			width:300,
			hiddenName: 'discId'
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
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			crtDate: typeof(queryForm.findById('crtDate').getValue()) == 'string' ? '' : queryForm.findById('crtDate').getValue().dateFormat('Ymd'),
			updDate: typeof(queryForm.findById('updDate').getValue()) == 'string' ? '' : queryForm.findById('updDate').getValue().dateFormat('Ymd'),
			crtEndDate: typeof(queryForm.findById('crtEndDate').getValue()) == 'string' ? '' : queryForm.findById('crtEndDate').getValue().dateFormat('Ymd'),
			updEndDate: typeof(queryForm.findById('updEndDate').getValue()) == 'string' ? '' : queryForm.findById('updEndDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue(),
			province:queryForm.getForm().findField('provinceN').getValue(),
			city:queryForm.getForm().findField('cityN').getValue(),
			area:queryForm.getForm().findField('areaN').getValue(),
			expander:queryForm.getForm().findField('expanderId').getValue(),
			etpsAttr:queryForm.getForm().findField('etpsAttr').getValue(),
			discId:queryForm.getForm().findField('discId').getValue(),
			agentNo:queryForm.getForm().findField('agentNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});
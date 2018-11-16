Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集
		
	var brhLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	//机构级别
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhLvl',mapping: 'brhLvl'},
			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'resv1',mapping: 'resv1'},
			{name: 'cupBrhId',mapping: 'cupBrhId'}
		]),
		autoLoad: true
	});
	
//进入页面就加载列表
//	gridStore.load({
//		params:{start: 0}
//	});
	
	function brhLvlRender(brhLvl) {
		switch(brhLvl) {
			case '0': return '总行';
			case '1': return '分行';
			case '2': return '支行';
		}
	}
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '编号',dataIndex: 'brhId',sortable: true,width: 50},
		{header: '级别',dataIndex: 'brhLvl',renderer: brhLvlRender,width: 50},
		{header: '上级编号',dataIndex: 'upBrhId',width: 60},
		{header: '机构名称',dataIndex: 'brhName',width: 100},
		{id:'brhAddr',header: '机构地址',dataIndex: 'brhAddr',width: 108},
		{header: '联系方式',dataIndex: 'brhTelNo',width: 80},
		{header: '邮政编码',dataIndex: 'postCode',width: 60},
		{header: '联系人',dataIndex: 'brhContName',width: 80},
		{header: '地区编码',dataIndex: 'resv1',width: 60},
		{header: '银联编号',dataIndex: 'cupBrhId',width: 60}
		
	]);
	
	var menuArr = new Array();

	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	menuArr.push(queryCondition);  //[1]
	
	
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: upBrhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [brhCombo,{
			xtype: 'textfield',
			id: 'searchBrhName',
			width: 160,
			name: 'searchBrhName',
			fieldLabel: '机构名称'
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
			gridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '机构信息查询',
		iconCls: 'T10100',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
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
			//查询机构下操作员信息
			oprGridPanel.getStore().removeAll();
			oprGridPanel.getStore().reload();
			//查询机构下商户信息
			mchntGridPanel.getStore().removeAll();
			mchntGridPanel.getStore().reload();
			//查询机构下终端信息
			termGridPanel.getStore().removeAll();
			termGridPanel.getStore().reload();
		}
	});
	
	/************************************************以下是机构相关操作员信息************************************************************/
	
	/*****************************************操作员信息*****************************************/
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoWithBrh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'}
		])
	});
	
	oprGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			brhId: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var oprTpl = new Ext.Template(
			'<p title="操作员姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{oprName}</p>',
			'<p title="操作员性别"><img src="' + Ext.contextPath + '/ext/resources/images/gender_16.png"/>：{oprGender:this.gender}</p>',
			'<p title="注册日期"><img src="' + Ext.contextPath + '/ext/resources/images/calendar_16.png"/>：{registerDt:this.date}</p>',
			'<p title="操作员联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{oprTel}</p>',
			'<p title="操作员移动电话"><img src="' + Ext.contextPath + '/ext/resources/images/mobile_16.png"/>：{oprMobile}</p>'
	);
	
	oprTpl.gender = function(val) {
		if(val == '0') {
			return '男';
		} else {
			return '女';
		}
	};
	
	oprTpl.date = function(val) {
		if(val.length == 8) {
			return val.substring(0,4) + '年' +
				   val.substring(4,6) + '月' + 
				   val.substring(6,8) + '日';
		} else {
			return val;
		}
	};
	
	var oprRowExpander = new Ext.ux.grid.RowExpander({
		tpl: oprTpl
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		oprRowExpander,
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'center',sortable: true},
		{header: '所在机构编号',align: 'center',dataIndex: 'brhId'}
	]);
	
	var oprGridPanel = new Ext.grid.GridPanel({
		id: 'oprPanel',
		title: '操作员信息',
		frame: true,
		iconCls: 'T104',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		collapsible: true,
		plugins: oprRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	/******************************************商户信息***********************************************/
	
	var mchntGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntId'
		},[
			{name: 'mchntId',mapping: 'mchtNo'},
			{name: 'mchntCnName',mapping: 'mchtNm'},
			{name: 'mchntEnName',mapping: 'mchntEnName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'certificate',mapping: 'certificate'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'email',mapping: 'email'},
			{name: 'legalName',mapping: 'legalName'},
			{name: 'contactName',mapping: 'contactName'},
			{name: 'contactTel',mapping: 'contactTel'},
			{name: 'registerDate',mapping: 'applyDate'},
			{name: 'mchntSt',mapping: 'mchtStatus'}
		])
	});
	
	mchntGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			brhId: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var mchntTpl = new Ext.Template(
			'<p title="商户中文名称"><img src="' + Ext.contextPath + '/ext/resources/images/cn_16.png"/>：{mchntCnName}</p>',
			'<p title="商户英文名称"><img src="' + Ext.contextPath + '/ext/resources/images/en_16.png"/>：{mchntEnName}</p>',
			'<p title="营业执照号码"><img src="' + Ext.contextPath + '/ext/resources/images/certificate_16.png"/>：{certificate}</p>',
			'<p title="商户地址"><img src="' + Ext.contextPath + '/ext/resources/images/branch.png"/>：{addr}</p>',
			'<p title="邮政编码"><img src="' + Ext.contextPath + '/ext/resources/images/post_16.png"/>：{postCode}</p>',
			'<p title="电子邮件地址"><img src="' + Ext.contextPath + '/ext/resources/images/email_16.png"/>：{email}</p>',
			'<p title="法人姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{legalName}</p>',
			'<p title="联系人姓名"><img src="' + Ext.contextPath + '/ext/resources/images/male.png"/>：{contactName}</p>',
			'<p title="联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{contactTel}</p>'
	);
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: mchntTpl
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchntId',header: '商户编号',dataIndex: 'mchntId',sortable: true},
		{header: '商户MCC',dataIndex: 'mcc'}
//		{header: '商户注册日期',dataIndex: 'registerDate',renderer: formatDt},
//		{header: '商户状态',dataIndex: 'mchntSt',renderer: mchntSt}
	]);
		
	var mchntGridPanel = new Ext.grid.GridPanel({
		id: 'mchntPanel',
		title: '商户信息',
		frame: true,
		iconCls: 'T201',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: mchntGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		collapsible: true,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/*******************************************终端信息**********************************************/
	
	// 数据集
	var termTypeMap = new DataMap('TERM_TYPE');
	
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSt',mapping: 'termSt'},
			{name: 'isSuppIc',mapping: 'isSuppIc'},
			{name: 'isSuppCredit',mapping: 'isSuppCredit'},
			{name: 'termType',mapping: 'termType'},
			{name: 'callType',mapping: 'callType'},
			{name: 'callNo',mapping: 'callNo'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'sequenceNo',mapping: 'sequenceNo'},
			{name: 'kbSequenceNo',mapping: 'kbSequenceNo'},
			{name: 'vTeller',mapping: 'vTeller'},
			{name: 'encType',mapping: 'encType'},
			{name: 'bindNo',mapping: 'bindNo'}
		])
	});
	
	termStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			termBranch: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p title="是否支持IC卡"><img src="' + Ext.contextPath + '/ext/resources/images/ic_16.png"/>：{isSuppIc:this.isSuppIc}</p>',
			'<p title="是否支持信用卡"><img src="' + Ext.contextPath + '/ext/resources/images/credit_16.png"/>：{isSuppCredit:this.isSuppCredit}</p>',
			'<p title="终端类型"><img src="' + Ext.contextPath + '/ext/resources/images/pos_16.png"/>：{termType:this.termType}</p>',
			'<p title="拨入类型"><img src="' + Ext.contextPath + '/ext/resources/images/right_16.png"/>：{callType:this.callType}</p>',
			'<p title="加解密类型"><img src="' + Ext.contextPath + '/ext/resources/images/key_16.png"/>：{encType:this.encType}</p>',
			'<p title="拨入号码"><img src="' + Ext.contextPath + '/ext/resources/images/remote_16.png"/>：{callNo}</p>',
			'<p title="所属机构"><img src="' + Ext.contextPath + '/ext/resources/images/branch.png"/>：{brhId}</p>',
			'<p title="终端序列号"><img src="' + Ext.contextPath + '/ext/resources/images/computer_16.png"/>：{sequenceNo}</p>',
			'<p title="密码键盘序列号"><img src="' + Ext.contextPath + '/ext/resources/images/keyboard_16.png"/>：{kbSequenceNo}</p>',
			'<p title="设备虚拟柜员"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{vTeller}</p>',
			'<p title="绑定电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{bindNo}</p>',
			{
				termType: function(val) {
					return termTypeMap[val];
				},
				isSuppIc: function isSuppIc(val) {
					if(val == '1') 
						return '是';
					else if(val == '0')
						return '否';
					return val;
				},
				isSuppCredit: function(val) {
					if(val == '1') 
						return '是';
					else if(val == '0')
						return '否';
					return val;
				},
				callType: function(val) {
					if(val == '00') 
						return 'GPRS拨入';
					else if(val == '01')
						return 'CDMA拨入';
					else if(val == '02') {
						return '其他拨入';
					}
					return val;
				},
				encType: function(val) {
					if(val == '1') 
						return '软件加密';
					else if(val == '0')
						return '加密机加密';
					return val;
				}
			}
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		termRowExpander,
		{id: 'termId',header: '终端编号',dataIndex: 'termId',width: 100},
		{header: '所属商户编号',dataIndex: 'mchntNo',width: 150},
		{header: '终端状态',dataIndex: 'termSt',width: 150,renderer: termSt}
	]);
	
	
	// 终端状态
	function termSt(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">添加待审核</font>';
		} else if(val == '2') {
			return '<font color="gray">添加审核退回</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		} else if(val == '4') {
			return '<font color="gray">修改审核退回</font>';
		} else if(val == '5') {
			return '<font color="gray">停用待审核</font>';
		} else if(val == '6') {
			return '<font color="red">停用</font>';
		} else if(val == '7') {
			return '<font color="gray">恢复待审核</font>';
		}
		return val;
	}
	
	// 终端信息列表
	var termGridPanel = new Ext.grid.GridPanel({
		id: 'termGridPanel',
		title: '终端信息',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		plugins: termRowExpander,
		collapsible: true,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var rightPanel = new Ext.Panel({
		region: 'east',
		split: true,
		width: 265,
		frame: true,
		collapsible: true,
		autoScroll: true,
		title: '机构关联信息',
		items:[oprGridPanel,mchntGridPanel,termGridPanel]
	});
	
	oprGridPanel.on('render',function() {
		new Ext.dd.DragSource(oprGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	mchntGridPanel.on('render',function() {
		new Ext.dd.DragSource(mchntGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	termGridPanel.on('render',function() {
		new Ext.dd.DragSource(termGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	var items;
	var draggedPanel;
	var positionY;
	var itemY;
	var index;
	rightPanel.on('render',function() {
		var rightPanelTarget = new Ext.dd.DropTarget(rightPanel.getEl(),{
			ddGroup: 'dd',
			notifyDrop: function(source,e) {
				items = rightPanel.items.items;
				draggedPanel = Ext.getCmp(source.getEl().id);
				positionY = e.getPageY();
				heigh = 0;
				index = 10;
				for(var i = 0; i < draggedPanel.el.dom.parentNode.childNodes.length; i++) {
					itemY = Ext.getCmp(draggedPanel.el.dom.parentNode.childNodes[i].id).getPosition()[1];
					if(itemY > positionY) {
						index = i;
						break;
					}
				}
				if(index == 10) {
					draggedPanel.el.dom.parentNode.appendChild(draggedPanel.el.dom);
				} else {
					draggedPanel.el.dom.parentNode.insertBefore(draggedPanel.el.dom, draggedPanel.el.dom.parentNode.childNodes[index]);
				}
			}
		});		
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			brhId: queryForm.findById('editBrh').getValue(),
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel,rightPanel]
	});
});
Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集
		
//	var brhLvlStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	
	var TxnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	var ChannelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	var LimitStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('TXNNUM',function(ret){
		TxnStore.loadData(Ext.decode(ret));
	});
//	//上级营业网点编号
	SelectOptionsDWR.getComboData('LIMIT',function(ret){
		LimitStore.loadData(Ext.decode(ret));
	});
	SelectOptionsDWR.getComboData('RISKCHANNEL',function(ret){
		ChannelStore.loadData(Ext.decode(ret));
	});
	function txnChannel(val){
		return ChannelStore.query("valueField",val).first().data.displayField;
		 
	}
	function txnRen(val){
		return TxnStore.query("valueField",val).first().data.displayField;
		 
	}
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=risktranctllimit'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
		    {name: 'channel',mapping: 'channel'},
		    {name: 'channelname',mapping: 'channelname'},
			{name: 'cardbinold',mapping: 'cardbinold'},
			{name: 'txnnumold',mapping: 'txnnumold'},
			{name: 'limit',mapping: 'limit'},
			{name: 'statue',mapping: 'statue'},
			{name: 'id',mapping: 'id'},
			{name: 'creOprId',mapping: 'creOprId'},
			{name: 'upOprId',mapping: 'upOprId'},
			{name: 'creTime',mapping: 'creTime'},
			{name: 'upTime',mapping: 'upTime'}
		]),
		autoLoad: true
	});	

	function binRen(val){
		if(val=='*')
			return "所有卡bin";
		return val;
	}

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		{header: '渠道号',dataIndex: 'channel',width: 120,renderer:txnChannel},
		{header: '渠道号',dataIndex: 'channelname',width: 120},	
		{header: '卡bin',dataIndex: 'cardbinold',width: 120,editor:true,renderer:binRen},
		{header: '交易码',dataIndex: 'txnnumold',width: 150,editor: new Ext.form.ComboBox({
		 	store: TxnStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true})
			 ,renderer:txnRen},
//		{header: '交易权限',dataIndex: 'limit',width: 100,hidden:true,renderer:limit,editor: new Ext.form.ComboBox({
//		 	store: LimitStore,
//			displayField: 'displayField',
//			valueField: 'valueField',
//			mode: 'local',
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: true})},
		{header: '审核状态',dataIndex: 'statue',width: 100,renderer: riskInfoState},
		{id: 'id',header: 'id',dataIndex: 'id',width: 100,hidden:true},
		{header: '创建人',dataIndex: 'creOprId'},
		{header: '审核人',dataIndex: 'upOprId'},
		{header: '创建时间',dataIndex: 'creTime',width: 120,renderer: formatTs},
		{header: '审核时间',dataIndex: 'upTime',width: 120,renderer: formatTs}
	]);
	function limit(val){
		if(val=='00'){
			return '不允许'; 
		}
		else{
			return '允许';
		}
	}
	//添加菜单
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
		}
	};
	//修改菜单
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

			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id: record.get('id'),
					channel: record.get('channel'),
					cardbinold: record.get('cardbinold'),
					txnnumold: record.get('txnnumold'),
					limit:record.get('limit')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T40212Action.asp?method=update',
				method: 'post',
				params: {
					RiskTranList : Ext.encode(array),
					txnId: '40212',
					subTxnId: '03'
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
					grid.getTopToolbar().items.items[2].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	//删除
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('您确定删除渠道交易权限吗？',grid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T40212Action.asp?method=delete',
							params: {
								id: rec.get('id'),
								txnId: '40212',
								subTxnId: '05'
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
	
	//菜单添加
	var menuArr = new Array();
    menuArr.push(addMenu);
    menuArr.push('-');
    menuArr.push(upMenu)
    menuArr.push('-');
    menuArr.push(delMenu);
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
	
	//添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo', 
			fieldLabel: '渠道号*',
			allowBlank: false,
			emptyText: '请输入渠道号',
			methodName: 'getChannel',
			id: 'idchannel',
			hiddenName: 'CHANNEL',
			width: 200,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '卡bin*',
			allowBlank: false,
			blankText: '请输入卡bin',
			emptyText: '请输入卡bin',
			id: 'idcardbin',
			name: 'CARD_BIN',
			maxLength:30,
			width: 200,
			regex:/^[0-9]+$|^[*]?$/,
			regexText:'该输入项只能是数字或单个星号*'
//			maskRe:/^[0-9]$/
		},{
			xtype: 'dynamicCombo', 
			fieldLabel: '交易码*',
			allowBlank: false,
			emptyText: '请输入交易码',
			methodName: 'getTxnClt',
			id: 'idTXN_NUM',
			hiddenName:'TXN_NUM',
			maxLength: 15,
			width: 200,
			blankText: '该输入项只能包含数字'
		}/*,{
			xtype: 'basecomboselect',
			baseParams: 'LIMIT',
			fieldLabel: '权限*',
			allowBlank: false,
			emptyText: '请输入权限',
			id: 'idlimit',
			hiddenName:'LIMIT',
			maxLength: 15,
			width: 200,
			blankText: '该输入项只能包含数字'
		}*/]
	});
	//分公司添加窗口
	var brhWin = new Ext.Window({
		title: '渠道交易权限添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(brhInfoForm.getForm().isValid()) {
					brhInfoForm.getForm().submit({
						url: 'T40212Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,brhInfoForm);
							//重置表单
							brhInfoForm.getForm().reset();
							//重新加载分公司列表
							grid.getStore().reload();
							
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							txnId: '40212',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				brhInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
			}
		}]
	});	
	
	// 可选分公司下拉列表
	var brhCombo = new Ext.form.ComboBox({
		//store: upBrhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择分公司',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个分公司',
		fieldLabel: '分公司编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			id: 'idStatu',
			fieldLabel: '审核状态',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['2','正常'],['3','修改未审核'],['1','删除'],['4','删除未审核']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '渠道号',
			methodName: 'getChannel',
			id:'Channelid',
			hiddenName: 'channel',
			editable: true,
			width: 150
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

	//分公司列表
	var grid = new Ext.grid.EditorGridPanel({
		title:'渠道交易权限维护',
		iconCls: 'T10108',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载渠道交易黑名单权限信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//保存按钮初始化不可用
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	//在编辑单元格后使保存按钮可用
		grid.on({
			//编辑后删除按钮可用
			/*'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},*/
		'beforeedit':function(e){
			if(e.record.get('statue')=='4'||e.record.get('statue')=='1'){
				e.cancel=true;
			}
				
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('statue')!='3' && rec.get('statue')!='4' && rec.get('statue')!='1')//删除未审核、修改未审核 和已删除不可以删除
				grid.getTopToolbar().items.items[4].enable();
			else
				grid.getTopToolbar().items.items[4].disable();
		}		
	});	
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});	
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			statue: queryForm.findById('idStatu').getValue(),
			channel: queryForm.findById('Channelid').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});

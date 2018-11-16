Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集
	
	//卡类型数据集
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	var ActionStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('ACTION',function(ret){
		ActionStore.loadData(Ext.decode(ret));
	});
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=csttermfeeshenheinf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'id'
		},[
			{name: 'mchtcdname',mapping: 'mchtcdname'},
		    {name: 'mchtcd',mapping: 'mchtcd'},
		    {name: 'termid',mapping: 'termid'},
			{name: 'channel',mapping: 'channel'},
			{name: 'daynum',mapping: 'daynum'},
			{name: 'dayamt',mapping: 'dayamt'},
			{name: 'daysingle',mapping: 'daysingle'},
			{name: 'sastatue',mapping: 'sastatue'},
			{name: 'saaction',mapping: 'saaction'},
			{name: 'creId',mapping: 'creId'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'upId',mapping: 'upId'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'monAmt',mapping: 'monAmt'}
		]),
		autoLoad: true
	});
	
//进入页面就加载列表
//	gridStore.load({
//		params:{start: 0}
//	});
	

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户编号',dataIndex: 'mchtcd',width: 100},
		{header: '商户名称',dataIndex: 'mchtcdname',width: 200},
		{header: '终端号',dataIndex: 'termid',width: 100},
		{header: '卡类型',dataIndex: 'cardType',width: 100,/*renderer:function(val){return cardStore.getById(val).displayField;}*/
			renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }	
		
		},
		{header: '日交易金额累计上限',dataIndex: 'dayamt',width: 120},
		{header: '日交易单笔金额上限',dataIndex: 'daysingle',width: 120},
		{header: '月交易金额累计上限',dataIndex: 'monAmt',width: 120},
		{header: '审核状态',dataIndex: 'sastatue',sortable: true,width: 100,renderer:statue},
//		{header: '受控状态',dataIndex: 'saaction',width: 100,renderer:action},
		{header: '修改人',dataIndex: 'creId',width: 90},
		{header: '修改时间',dataIndex: 'recCrtTs',width: 120,renderer: formatTs},
		{header: '审核人',dataIndex: 'upId',width: 90},
		{header: '审核时间',dataIndex: 'recUpdTs',width: 120,renderer: formatTs}		
	]);
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '删除';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '删除未审核';
		}
	}
	function action(val){
	if(val=='0'){
		return '提示'; 
		}
		else{
		return '拒绝';
		}
	}
	//通过
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
						url: 'T30107Action.asp?method=accept',
						params: {
							mchtcd: rec.get('mchtcd'),
							termid: rec.get('termid'),
							cardType:rec.get('cardType'),
							txnId: '30107',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载分公司待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	//拒绝
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
				url: 'T30107Action.asp?method=refuse',
				params: {
					mchtcd: rec.get('mchtcd'),
					termid: rec.get('termid'),
					cardType:rec.get('cardType'),
					txnId: '30107',
					subTxnId: '03',
					refuseInfo: text
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
	}
	//菜单添加
	var menuArr = new Array();
	menuArr.push(acceptMenu);
    menuArr.push('-');
   menuArr.push(refuseMenu);
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
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			id: 'idStatu',
			fieldLabel: '审核状态',
			width: 250,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['3','修改未审核'],['4','删除未审核']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchtcd',
			id:'idmtchid',
			hiddenName: 'mtchid',
			editable: true,
			width: 250,
			callFunction: function() {
					//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
					Ext.getCmp('idtermid').reset();
					Ext.getCmp('idtermid').parentP=this.value;
					Ext.getCmp('idtermid').getStore().reload();
				}
		},{
			xtype: 'dynamicCombo',
			methodName: 'getTermId',
			parentP:'',
			fieldLabel: '终端号',
			id:'idtermid',
			hiddenName: 'termid',
			editable: true,
			width: 250
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
				queryWin.hide();
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
		title:'终端交易限额审核',
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
			msg: '正在加载终端交易限额列表......'
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
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	//在编辑单元格后使保存按钮可用
		grid.on({
			//编辑后拒绝通过按钮可用
			'rowclick': function() {
			if(grid.getTopToolbar().items.items[0] != undefined) {
				grid.getTopToolbar().items.items[0].enable();
			}
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();

		}
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var items;
	var draggedPanel;
	var positionY;
	var itemY;
	var index;
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			statue: queryForm.findById('idStatu').getValue(),
			mtchid: queryForm.findById('idmtchid').getValue(),
			termid: queryForm.findById('idtermid').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});

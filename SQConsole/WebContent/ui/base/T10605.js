Ext.onReady(function() {
	// 获取主密钥信息数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getTermTmkInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instid',mapping: 'instid'},
			{name: 'instname',mapping: 'instname'},
			{name: 'mchtnm',mapping: 'mchtnm'},
			{name: 'mchtid',mapping: 'mchtid'},
			{name: 'termid',mapping: 'termid'},
			{name: 'signedflag',mapping: 'signedflag'},
			{name: 'signtime',mapping: 'signtime'},
			{name: 'respcode',mapping: 'respcode'}
		]),
		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '获取主密钥行编号',dataIndex: 'instid',width: 100,hidden:false},
		{header: '获取主密钥行名称',dataIndex: 'instname',width: 120,hidden:false},
		{header: '商户号',dataIndex: 'mchtid',width: 100,hidden:true},
		{header: '银行核心商户号',dataIndex: 'mchtnm',width: 270},
		{header: '银行核心终端号',dataIndex: 'termid',width: 130},
		{header: '获取主密钥状态',dataIndex: 'signedflag',width: 100,renderer:sign},
		{header: '获取主密钥时间',dataIndex: 'signtime',width: 120,renderer:formatTs},
		{header: '响应码',dataIndex: 'respcode',width: 100}
	]);
	function sign(val){
		if(val=='1'){
		    return '<font color=\'green\'>已获取主密钥</font>';
		}else{
		    return '<font color=\'red\'>未获取主密钥</font>';
		}
	}
	
	var menuArr = new Array();

	//获取主密钥
	var upMenu = {
		text: '获取主密钥',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var mchtid = rec.get('mchtid');		
				var termid=rec.get('termid');
				var instid=rec.get('instid');
				showConfirm('确定要获取主密钥吗？获取主密钥终端号：' + termid,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10605Action.asp?method=update',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg("获取主密钥成功",grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								mchtid: mchtid,
								termid:termid,
								instid:instid,
								txnId: '10605',
								subTxnId: '01'
							}
						}
						);
					}
				});
			}
		}
	};
	
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
	menuArr.push('-');  //[1]
	menuArr.push(upMenu);  //[2]
	
	var grid = new Ext.grid.GridPanel({
		title: '获取交行主密钥',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		//autoExpandColumn: 'cardAccpName',
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: false}),
		cm: txnColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载获取主密钥信息列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: txnStore,
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
	//获取主密钥按钮初始化不可用
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	grid.on({
			//编辑后获取主密钥按钮可用
			'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo', 
			fieldLabel: '商户号*',
			emptyText: '请输入商户号',
			methodName: 'getTmkMchtcd',
			parentP:'03014520',
			id: 'idMCHT_CD',
			hiddenName: 'MCHT_CD',
			lazyRender: true,
			width: 300,
			callFunction:function() {
				//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
				Ext.getCmp('idTERM_ID').reset();
				Ext.getCmp('idTERM_ID').parentP=this.value;
				Ext.getCmp('idTERM_ID').getStore().reload();
			}
		},{
			xtype: 'dynamicCombo',
			methodName: 'getTmkTermId',
			parentP:'BRH03014520',
			fieldLabel: '终端号*',
			editable: true,
			blankText: '该输入项只能包含数字',
			emptyText: '请输入终端号',
			id: 'idTERM_ID',
			hiddenName: 'TERM_ID',
			lazyRender: true,
			width:300
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
			txnStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});

	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			mcht_cd: queryForm.findById('idMCHT_CD').getValue(),
			term_id: queryForm.findById('idTERM_ID').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	
});
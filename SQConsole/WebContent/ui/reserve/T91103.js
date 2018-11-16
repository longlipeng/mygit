Ext.onReady(function(){
	
	//备款任务数据源
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({            
			url: 'gridPanelStoreAction.asp?storeId=rosterGridStoreTmpSH'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'rosterId'
		},[
			{name: 'rosterId',mapping: 'rosterId'},
			{name: 'rosterBankCard',mapping: 'rosterBankCard'},
			{name: 'rosterAccount',mapping: 'rosterAccount'},
			{name: 'rosterAccountName',mapping: 'rosterAccountName'},
			{name: 'rosterStatus',mapping: 'rosterStatus'},
			{name: 'rosterLaunchTime',mapping: 'rosterLaunchTime'},
			{name: 'rosterLaunchName',mapping: 'rosterLaunchName'},
			{name: 'rosterAuditTime',mapping: 'rosterAuditTime'},
			{name: 'rosterAuditName',mapping: 'rosterAuditName'}
		]),
	});
	
	//加载数据源
	oprGridStore.load({
		params: {
			start: 0
		}
	});
	
	//显示列表信息列
	var oprColModel = new Ext.grid.ColumnModel([
    	{header: '账户白名单id',dataIndex: 'rosterId',align: 'center',id: 'rosterId',hidden: true},
    	{header: '收款方开户行行号',dataIndex: 'rosterBankCard',align: 'center'},
    	{header: '收款方账号',dataIndex: 'rosterAccount',align: 'center'},
    	{header: '收款方账户名称',dataIndex: 'rosterAccountName',align: 'center'},
    	{header: '审核状态',dataIndex: 'rosterStatus',renderer: rosterStatus,align: 'center'},
    	{header: '发起日期',dataIndex: 'rosterLaunchTime',align: 'center'},
    	{header: '发起人员',dataIndex: 'rosterLaunchName',align: 'center'},
    	{header: '审核日期',dataIndex: 'rosterAuditTime',align: 'center'},
    	{header: '审核人员',dataIndex: 'rosterAuditName',align: 'center'}
	]);
	
	//审核状态
	function rosterStatus(val){
		if(val=='0'){
			return '<font color="green">正常</font>';
		}else if(val=='1'){
			return '<font color="gray">修改待审核</font>';
		}else if(val=='2'){
			return '<font color="gray">删除待审核</font>';
		}else if(val=='3'){
			return '<font color="gray">新增待审核</font>';
		}
	}
	
	/**
	 ** 加法函数，用来得到精确的加法结果
	 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
	 ** 调用：accAdd(arg1,arg2)
	 ** 返回值：arg1加上arg2的精确结果
	 **/
	function accAdd(arg1, arg2) {
		var r1, r2, m, c;
		try {
			r1 = arg1.toString().split(".")[1].length;
		} catch (e) {
			r1 = 0;
		}
		try {
			r2 = arg2.toString().split(".")[1].length;
		} catch (e) {
			r2 = 0;
		}
		c = Math.abs(r1 - r2);
		m = Math.pow(10, Math.max(r1, r2));
		if (c > 0) {
			var cm = Math.pow(10, c);
			if (r1 > r2) {
				arg1 = Number(arg1.toString().replace(".", ""));
				arg2 = Number(arg2.toString().replace(".", "")) * cm;
			} else {
				arg1 = Number(arg1.toString().replace(".", "")) * cm;
				arg2 = Number(arg2.toString().replace(".", ""));
			}
		} else {
		arg1 = Number(arg1.toString().replace(".", ""));
		arg2 = Number(arg2.toString().replace(".", ""));
		}
		return (arg1 + arg2) / m;
	}
	
	// 给Number类型增加一个add方法，调用起来更加方便。
	Number.prototype.add = function(arg) {
		return accAdd(arg, this);
	};
	
	//菜单集合
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
	
	var refuseMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		handler: function() {
		var redempRecords = oprGrid.getSelectionModel().getSelections();
				/*var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('redempTionMoney'), amount);
				}*/
				showConfirm('你<font color="red">审核</font>'+ redempRecords.length +'条记录，确认通过！',oprGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",oprGrid);
						return;
					}
					showMask("正在提交审核信息，请稍后....",oprGrid);
					var array = new Array();
						for(var index = 0; index < redempRecords.length; index++) {
							var record = redempRecords[index];
							var data = {
								rosterId: record.get('rosterId'),//
								rosterBankCard: record.get('rosterBankCard'),//
								rosterAccount: record.get('rosterAccount'),//
								rosterAccountName: record.get('rosterAccountName'),//
								rosterStatus: record.get('rosterStatus'),//
								rosterLaunchTime: record.get('rosterLaunchTime'),//
								rosterLaunchName: record.get('rosterLaunchName'),//
								rosterAuditTime: record.get('rosterAuditTime'),//
								rosterAuditName: record.get('rosterAuditName'),//
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91101Action.asp?method=rosterAccept',
						params: {
							infList: Ext.encode(array),
							txnId: '80601',
							subTxnId: '04'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,oprGrid);
							} else {
								showErrorMsg(rspObj.msg,oprGrid);
							}
							oprGrid.getStore().reload();
						},
						failure: function(){
							hideMask();
						}
					});
				}
			});
		}
	};	
		
	var sucMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		handler: function() {
		var redempRecords = oprGrid.getSelectionModel().getSelections();
				/*var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('redempTionMoney'), amount);
				}*/
				showConfirm('你<font color="red">审核</font>'+ redempRecords.length +'条记录，确认通过！',oprGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",oprGrid);
						return;
					}
					showMask("正在提交审核信息，请稍后....",oprGrid);
					var array = new Array();
						for(var index = 0; index < redempRecords.length; index++) {
							var record = redempRecords[index];
							var data = {
								rosterId: record.get('rosterId'),//
								rosterBankCard: record.get('rosterBankCard'),//
								rosterAccount: record.get('rosterAccount'),//
								rosterAccountName: record.get('rosterAccountName'),//
								rosterStatus: record.get('rosterStatus'),//
								rosterLaunchTime: record.get('rosterLaunchTime'),//
								rosterLaunchName: record.get('rosterLaunchName'),//
								rosterAuditTime: record.get('rosterAuditTime'),//
								rosterAuditName: record.get('rosterAuditName'),//
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91101Action.asp?method=redempRefuse',
						params: {
							infList: Ext.encode(array),
							txnId: '80601',
							subTxnId: '05'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,oprGrid);
							} else {
								showErrorMsg(rspObj.msg,oprGrid);
							}
							oprGrid.getStore().reload();
						},
						failure: function(){
							hideMask();
						}
					});
				}
			});
		}
	};
		
	menuArr.push(queryCondition);  //[1]
	menuArr.push('-');
	menuArr.push(refuseMenu); //[3]
	menuArr.push('-');
	menuArr.push(sucMenu);  //[5]
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '账户白名单维护',
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'accountName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载白名单账户列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
/***************************查询条件*************************/
	
	//form表单
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '白名单账户',
			methodName: 'getRosterAccountAll',
			hiddenName: 'rosterAccount',
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			rosterAccount: queryForm.getForm().findField('rosterAccount').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
});
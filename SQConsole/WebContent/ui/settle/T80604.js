Ext.onReady(function() {
	/*var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false,
				dataIndex : "id",
				listeners : {
					'beforerowselect' : function(redempColModel, rowIndex, keepExisting, record) {
						if ((record.data.redempTionStatus)=='1' || (record.data.redempTionAccountStatus)=='0') {
							return false; // 不能进行选择
						} else {
							return true;
						}
					}
				}
	});*/
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=redempTionMethodBackFill'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'redempTionId'
		},[
			{name: 'redempTionId',mapping: 'redempTionId'},
			{name: 'redempTionAccountName',mapping: 'redempTionAccountName'},
			{name: 'redempTionAccount',mapping: 'redempTionAccount'},
			{name: 'redempTionMoney',mapping: 'redempTionMoney'},
			{name: 'redempTionBankCard',mapping: 'redempTionBankCard'},
			{name: 'redempTionStatus',mapping: 'redempTionStatus'},
			{name: 'redempTionAccountStatus',mapping: 'redempTionAccountStatus'},
			{name: 'redempTionPayStatus',mapping: 'redempTionPayStatus'},
			{name: 'redempTionAddTime',mapping: 'redempTionAddTime'},
			{name: 'redempTionAddName',mapping: 'redempTionAddName'},
			{name: 'redempTionAuditDate',mapping: 'redempTionAuditDate'},
			{name: 'redempTionAuditName',mapping: 'redempTionAuditName'},
			{name: 'redempTionEnTry',mapping: 'redempTionEnTry'},
			//交易流水号
			{name: 'redemptionBatch',mapping: 'redemptionBatch'},
		])
	});
	
	redempGridStore.load({
		params:{start: 0}
	});
	
	var redempColModel = new Ext.grid.ColumnModel([
		sm,
		{header: '客户编号',dataIndex: 'redempTionId',hidden:true},
		{header: '客户账户名称',dataIndex: 'redempTionAccountName',width:200,align: 'center'},
		{header: '客户账户',dataIndex: 'redempTionAccount',width:200,align: 'center'},
		{header: '赎回金额',dataIndex: 'redempTionMoney',align: 'center'},
		{header: '客户开户行行号',dataIndex: 'redempTionBankCard',align: 'center'},
		{header: '审核状态',dataIndex: 'redempTionStatus',renderer: redempTionStatus,align: 'center'},
		{header: '是否打款',dataIndex: 'redempTionAccountStatus',renderer: accountStatus,align: 'center'},
		{header: '支付状态',dataIndex: 'redempTionPayStatus',align: 'center'},
		{header: '发起日期',dataIndex: 'redempTionAddTime',renderer: formatDt,align: 'center',hidden:true},
		{header: '发起人',dataIndex: 'redempTionAddName',align: 'center',hidden:true},
		{header: '审核日期',dataIndex: 'redempTionAuditDate',renderer: formatDt,align: 'center'},
		{header: '审核人',dataIndex: 'redempTionAuditName',width:300,align: 'center',hidden:true},
		{header: '录入方式',dataIndex: 'redempTionEnTry',renderer: accountEnTryStatus,width:300,align: 'center'},
		{header: '交易流水号',dataIndex: 'redemptionBatch',width:300,align: 'center',hidden:true},
	]);
	
	//录入方式
	function accountEnTryStatus(val){
		if(val=='0'){
			return '<font color="green">系统录入</font>';
		}else if(val=='1'){
			return '<font color="green">手动录入</font>';
		}
	}
	
	//审核状态
	function redempTionStatus(val){
		/*if(val=='0'){
			return '<font color="green">正常</font>';
		}else */if(val=='1'){
			return '<font color="red">新增待审核</font>';
		}else if(val=='2'){
			return '<font color="green">新增审核通过</font>';
		}else if(val=='3'){
			return '<font color="red">赎回待审核</font>';
		}else if(val=='4'){
			return '<font color="red">赎回审核拒绝</font>';
		}else if(val=='5'){
			return '<font color="green">赎回审核通过</font>';
		}else if(val=='6'){
			return '<font color="red">删除待审核</font>';
		}else if(val=='7'){
			return '<font color="green">删除审核拒绝</font>';
		}
	}
	
	//入账状态
	function accountStatus(val){
		if(val=='0'){
			return '<font color="green">成功</font>';
		}else if(val=='1'){
			return '<font color="red">失败</font>';
		}else if(val=='2'){
			return '<font color="gray">赎回中</font>';
		}
	}
	
	// 菜单集合
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
		
		var BackFillMenu = {
			text: '客户赎回状态查询',
			width: 85,
			iconCls: 'edit',
			handler: function() {
			var redempRecords = redempGrid.getSelectionModel().getSelections();
					var amount = 0;
					for(var i = 0; i < redempRecords.length; i++){
						var count = redempRecords[i];
						amount = accAdd(count.get('redempTionMoney'), amount);
					}
					showConfirm('你勾选了'+ redempRecords.length +'条记录，金额：'+ amount +'.确认回填！',redempGrid,function(bt) {
						if(bt == 'yes') {
						if(redempRecords.length == 0) {
							showErrorMsg("请勾选数据！",redempGrid);
							return;
						}
						showMask("正在提交回填信息，请稍后....",redempGrid);
						var array = new Array();
							for(var index = 0; index < redempRecords.length; index++) {
								var record = redempRecords[index];
								var data = {
										redempTionId: record.get('redempTionId'),//主键
										redempTionAccountName: record.get('redempTionAccountName'),//客户账户名称
										redempTionAccount: record.get('redempTionAccount'),//客户账户
										redempTionMoney: record.get('redempTionMoney'),//赎回金额/分
										redempTionBankCard: record.get('redempTionBankCard'),//客户开户行行号
										redempTionStatus: record.get('redempTionStatus'),//审核状态
										redempTionAccountStatus: record.get('redempTionAccountStatus'),//是否打款
										redempTionPayStatus: record.get('redempTionPayStatus'),//支付状态   
										redempTionAddTime: record.get('redempTionAddTime'),//发起日期
										redempTionAddName: record.get('redempTionAddName'),//发起人员
										redempTionAuditDate: record.get('redempTionAuditDate'),//审核时间
										redempTionAuditName: record.get('redempTionAuditName'),//审核人员
										redempTionEnTry: record.get('redempTionEnTry'),//录入方式
										redemptionBatch: record.get('redemptionBatch'),//交易流水号
								};
								array.push(data);
							}
							Ext.Ajax.request({
							url: 'T80601Action_redempBackFill.asp',
							params: {
								infList: Ext.encode(array),
								txnId: '80601',
								subTxnId: '06'
							},
							success: function(rsp,opt) {
								hideMask();
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,redempGrid);
								} else {
									showErrorMsg(rspObj.msg,redempGrid);
								}
								redempGrid.getStore().reload();
							},
							failure: function(){
								hideMask();
							}
						});
						}
					});
			}
		};
		
		var BackFillMenuSDFail = {
				text: '手动回填失败',
				width: 85,
				iconCls: 'edit',
				handler: function() {
				var redempRecords = redempGrid.getSelectionModel().getSelections();
						var amount = 0;
						for(var i = 0; i < redempRecords.length; i++){
							var count = redempRecords[i];
							amount = accAdd(count.get('redempTionMoney'), amount);
						}
						showConfirm('你勾选了'+ redempRecords.length +'条记录，金额：'+ amount +'.确认回填！',redempGrid,function(bt) {
							if(bt == 'yes') {
							if(redempRecords.length == 0) {
								showErrorMsg("请勾选数据！",redempGrid);
								return;
							}
							showMask("正在提交回填信息，请稍后....",redempGrid);
							var array = new Array();
								for(var index = 0; index < redempRecords.length; index++) {
									var record = redempRecords[index];
									var data = {
											redempTionId: record.get('redempTionId'),//主键
											redempTionAccountName: record.get('redempTionAccountName'),//客户账户名称
											redempTionAccount: record.get('redempTionAccount'),//客户账户
											redempTionMoney: record.get('redempTionMoney'),//赎回金额/分
											redempTionBankCard: record.get('redempTionBankCard'),//客户开户行行号
											redempTionStatus: record.get('redempTionStatus'),//审核状态
											redempTionAccountStatus: record.get('redempTionAccountStatus'),//是否打款
											redempTionPayStatus: record.get('redempTionPayStatus'),//支付状态   
											redempTionAddTime: record.get('redempTionAddTime'),//发起日期
											redempTionAddName: record.get('redempTionAddName'),//发起人员
											redempTionAuditDate: record.get('redempTionAuditDate'),//审核时间
											redempTionAuditName: record.get('redempTionAuditName'),//审核人员
											redempTionEnTry: record.get('redempTionEnTry'),//录入方式
									};
									array.push(data);
								}
								Ext.Ajax.request({
								url: 'T80601Action_redempBackFillSDFail.asp',
								params: {
									infList: Ext.encode(array),
									txnId: '80601',
									subTxnId: '07'
								},
								success: function(rsp,opt) {
									hideMask();
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,redempGrid);
									} else {
										showErrorMsg(rspObj.msg,redempGrid);
									}
									redempGrid.getStore().reload();
								},
								failure: function(){
									hideMask();
								}
							});
							}
						});
				}
			};
		
		var BackFillMenuSDSuccess = {
				text: '手动回填成功',
				width: 85,
				iconCls: 'edit',
				handler: function() {
				var redempRecords = redempGrid.getSelectionModel().getSelections();
						var amount = 0;
						for(var i = 0; i < redempRecords.length; i++){
							var count = redempRecords[i];
							amount = accAdd(count.get('redempTionMoney'), amount);
						}
						showConfirm('你勾选了'+ redempRecords.length +'条记录，金额：'+ amount +'.确认回填！',redempGrid,function(bt) {
							if(bt == 'yes') {
							if(redempRecords.length == 0) {
								showErrorMsg("请勾选数据！",redempGrid);
								return;
							}
							showMask("正在提交回填信息，请稍后....",redempGrid);
							var array = new Array();
								for(var index = 0; index < redempRecords.length; index++) {
									var record = redempRecords[index];
									var data = {
											redempTionId: record.get('redempTionId'),//主键
											redempTionAccountName: record.get('redempTionAccountName'),//客户账户名称
											redempTionAccount: record.get('redempTionAccount'),//客户账户
											redempTionMoney: record.get('redempTionMoney'),//赎回金额/分
											redempTionBankCard: record.get('redempTionBankCard'),//客户开户行行号
											redempTionStatus: record.get('redempTionStatus'),//审核状态
											redempTionAccountStatus: record.get('redempTionAccountStatus'),//是否打款
											redempTionPayStatus: record.get('redempTionPayStatus'),//支付状态   
											redempTionAddTime: record.get('redempTionAddTime'),//发起日期
											redempTionAddName: record.get('redempTionAddName'),//发起人员
											redempTionAuditDate: record.get('redempTionAuditDate'),//审核时间
											redempTionAuditName: record.get('redempTionAuditName'),//审核人员
											redempTionEnTry: record.get('redempTionEnTry'),//录入方式
									};
									array.push(data);
								}
								Ext.Ajax.request({
								url: 'T80601Action_redempBackFillSDSuccess.asp',
								params: {
									infList: Ext.encode(array),
									txnId: '80601',
									subTxnId: '08'
								},
								success: function(rsp,opt) {
									hideMask();
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,redempGrid);
									} else {
										showErrorMsg(rspObj.msg,redempGrid);
									}
									redempGrid.getStore().reload();
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
//		menuArr.push('-');
//		menuArr.push(BackFillMenu); //[3]
//		menuArr.push('-');
//		menuArr.push(BackFillMenuSDFail);  //[5]
//		menuArr.push('-');
//		menuArr.push(BackFillMenuSDSuccess);  //[7]
		
	// 信息列表
	var redempGrid = new Ext.grid.GridPanel({
		title: '客户赎回入账审核',
		iconCls: 'T104',
		region:'center',
		//autoExpandColumn:'sumrzNoteId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: redempGridStore,
		sm: sm,
		cm: redempColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: redempGridStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 15,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '客户账户',
			methodName: 'getAccountAll',
			hiddenName: 'redempTionAccount',
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
				redempGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	redempGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			redempTionAccount: queryForm.getForm().findField('redempTionAccount').getValue(),
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
});
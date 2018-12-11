Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false,
				dataIndex : "id",
				listeners : {
					'beforerowselect' : function(redempColModel, rowIndex, keepExisting, record) {
						if ((record.data.redempTionStatus)=='1' || (record.data.redempTionStatus)=='3'/* || (record.data.redempTionAccountStatus)=='0'*/) {
							return false; // 不能进行选择
						} else {
							return true;
						}
					}
				}
	});
	
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=redempTionMethod'
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
			{name: 'redempTionEnTry',mapping: 'redempTionEnTry'}
		])
	});
	
	redempGridStore.load({
		params:{start: 0}
	});
	
	var redempColModel = new Ext.grid.ColumnModel([
   		sm,
   		{header: '客户编号',dataIndex: 'redempTionId',hidden:true},
   		{header: '客户账户名称',dataIndex: 'redempTionAccountName',width:200},
   		{header: '客户账户',dataIndex: 'redempTionAccount',width:200,align: 'center'},
   		{header: '赎回金额',dataIndex: 'redempTionMoney',align: 'center'},
   		{header: '客户开户行行号',dataIndex: 'redempTionBankCard',align: 'center'},
   		{header: '审核状态',dataIndex: 'redempTionStatus',renderer: redempTionStatus,align: 'center'},
   		{header: '是否打款',dataIndex: 'redempTionAccountStatus',renderer: accountStatus,align: 'center'},
   		{header: '支付状态',dataIndex: 'redempTionPayStatus',align: 'center'},
   		{header: '发起日期',dataIndex: 'redempTionAddTime',renderer: formatDt,align: 'center'},
   		{header: '发起人',dataIndex: 'redempTionAddName',align: 'center'},
   		{header: '审核日期',dataIndex: 'redempTionAuditDate',renderer: formatDt,align: 'center'},
   		{header: '审核人',dataIndex: 'redempTionAuditName',width:300,align: 'center'},
   		{header: '录入方式',dataIndex: 'redempTionEnTry',renderer: accountEnTryStatus,width:300,align: 'center'}
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
			return '<font color="gray">赎回已受理</font>';
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
		
		var redempMenu = {
			text: '客户赎回',
			width: 85,
			iconCls: 'edit',
			id: 'redemp',
// disabled: true,
			handler: function() {
			var redempRecords = redempGrid.getSelectionModel().getSelections();
					var amount = 0;
					for(var i = 0; i < redempRecords.length; i++){
						var count = redempRecords[i];
//						amount  = parseFloat(count.get('redempTionMoney')) + parseFloat(amount);
						amount = accAdd(count.get('redempTionMoney'), amount);
					}
					showConfirm('你勾选了'+redempRecords.length+'条记录，金额：'+ amount +'.确认赎回！',redempGrid,function(bt) {
						if(bt == 'yes') {
						if(redempRecords.length == 0) {
							showErrorMsg("请勾选数据！",redempGrid);
							return;
						}
						showMask("正在提交赎回信息，请稍后....",redempGrid);
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
							url: 'T80601Action_redemp.asp',
							params: {
								infList: Ext.encode(array),
								txnId: '80601',
								subTxnId: '03'
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
//					sucAddForm.getForm().reset();
//					sucAddWin.show();
			}
		};
		
		//新增赎回信息按钮
		var redempAdd = {
			text: '添加',
			iconCls: 'add',
			handler: function(){
				redempCodeForm.getForm().reset();
				redempCodeForm.getForm().clearInvalid();
				redempWindow.show();
				redempWindow.center();
			}
		};
		
		var redempDel = {
			text: '删除',
			id: 'redempDelete',
			iconCls: 'delete',
			handler: function(){
				var redempRecords = redempGrid.getSelectionModel().getSelections();
				showConfirm('你勾选了'+redempRecords.length+'条记录，确认删除吗?',redempGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",redempGrid);
						return;
					}
					var array = new Array();
						for(var index = 0; index < redempRecords.length; index++) {
							var record = redempRecords[index];
							var data = {
								redempTionId: record.get('redempTionId'),//主键
							};
							array.push(data);
						}
						Ext.Ajax.request({
							url: 'T80601Action_redempDel.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,redempGrid);
									redempGrid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg,redempGrid);
								}
							},
							params: {
								infList: Ext.encode(array),
								txnId: '80601',
								subTxnId: '02'
							}
						});
					}
				});
				
				/*if(redempGrid.getSelectionModel().hasSelection()){
					var rec = redempGrid.getSelectionModel().getSelected();
					showConfirm('确定要删除该条未入账赎回信息吗？',redempGrid,function(bt){
						if(bt=="yes"){
							Ext.Ajax.request({
								url: 'T80601Action_redempDel.asp',
								success: function(rsp,opt){
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,redempGrid);
										redempGrid.getStore().reload();
									} else {
										showErrorMsg(rspObj.msg,redempGrid);
									}
								},
								params: {
									redempTionId: rec.get('redempTionId'),
									txnId: '80601',
									subTxnId: '02'
								}
							});
						}
					});
				}*/
			}
		};
		
		menuArr.push(queryCondition);  //[1]
		menuArr.push('-');
		menuArr.push(redempMenu); //[3]
		menuArr.push('-');
		menuArr.push(redempAdd);  //[5]
		menuArr.push('-');
		menuArr.push(redempDel); //[7]
		
		//赎回信息form表单
		var redempCodeForm = new Ext.form.FormPanel({
			frame: true,
			autoHeight: true,
			width: 300,
			labelWidth: 160,
			waitMsgTarget: true,
			items: [{
				//输入框类型
				xtype: 'textfield',
				//label文本值
				fieldLabel: '客户账户名称*',
				id: 'redempTionAccountName',
				name: 'redempTionAccountName',
				//非空验证:allowBlank: false不为空,true可为空
				allowBlank: false,
				//非空提示信息
				blankText: '客户账户名称不能为空',
				//可观输入信息是什么
				emptyText: '请输入客户账户名称',
				//text文本输入框宽度
				width:150,
			},{
				//输入框类型
				xtype: 'textfield',
				//label文本值
				fieldLabel: '客户账户*',
				id: 'redempTionAccount',
				name: 'redempTionAccount',
				//非空验证:allowBlank: false不为空,true可为空
				allowBlank: false,
				//非空提示信息
				blankText: '客户账户不能为空',
				//可观输入信息是什么
				emptyText: '请输入客户账户',
				//text文本输入框宽度
				width:150,
				vtype: 'isNumber',
				vtypeText:'只能输入数字'
			},{
				//输入框类型
				xtype: 'textfield',
				//label文本值
				fieldLabel: '赎回金额*',
				id: 'redempTionMoney',
				name: 'redempTionMoney',
				//非空验证:allowBlank: false不为空,true可为空
				allowBlank: false,
				//非空提示信息
				blankText: '赎回金额不能为空',
				//可观输入信息是什么
				emptyText: '请输入赎回金额',
				//text文本输入框宽度
				width:150
			},{
				//输入框类型
				xtype: 'textfield',
				//label文本值
				fieldLabel: '客户开户行行号*',
				id: 'redempTionBankCard',
				name: 'redempTionBankCard',
				//非空验证:allowBlank: false不为空,true可为空
				allowBlank: false,
				//非空提示信息
				blankText: '开户行行号不能为空',
				//可观输入信息是什么
				emptyText: '请输入开户行行号',
				//text文本输入框宽度
				width:150,
				vtype: 'isNumber',
				vtypeText:'只能输入数字'
			}]
		});
		
		//新增赎回信息窗口
		var redempWindow = new Ext.Window({
			title: '赎回信息添加',
			initHidden: true,
			header: true,
			frame: true,
			closable: false,
			modal: true,
			width: 400,
			autoHeight: true,
			layout: 'fit',
			items: [redempCodeForm],
			buttonAlign: 'center',
			closeAction: 'hide',
			iconCls: 'logo',
			resizable: false,
			buttons: [{
				text: '确定',
				handler: function() {
					redempCodeForm.getForm().submit({
						url: 'T80601Action_redempAdd.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,redempCodeForm);
							//添加成功后窗口关闭
							redempWindow.hide();
							var redempAccount = redempCodeForm.getForm().findField('redempTionAccount').getValue();
							//baseParams: 
							redempGridStore.baseParams.redempTionAccount = redempTionAccount;
							//重新加载参数
							redempGridStore.reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,redempCodeForm);
						},
						params: {
							txnId: '80601',
							subTxnId: '01'
						}
					});
				}
			},{
				text: '重置',
				handler: function() {
					redempCodeForm.getForm().reset();
				}
			},{
				text: '关闭',
				handler: function() {
					redempWindow.hide();
				}
			}]
		});
		
		// 信息列表
	var redempGrid = new Ext.grid.GridPanel({
		title: '客户赎回入账',
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
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//选中数据显示按钮
	redempGrid.getSelectionModel().on({
		'rowselect':function(){
			//获取选中的记录
			var rec = redempGrid.getSelectionModel().getSelected();
			//客户赎回按钮显示
			if(rec.get('redempTionStatus')=='1'){
				Ext.getCmp("redemp").disable();
			}
			if(rec.get('redempTionAccountStatus')=='0'){
				Ext.getCmp("redempDelete").disable();
				Ext.getCmp("redemp").disable();
			}else if(rec.get('redempTionAccountStatus')=='1'){
				Ext.getCmp("redempDelete").enable();
			}
			
		}
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
			redempTionAccount: queryForm.getForm().findField('redempTionAccount').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
});
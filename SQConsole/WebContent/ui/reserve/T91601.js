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
	//回款数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=payMentStore1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'paymentId'
		},[
			{name: 'paymentId',mapping: 'paymentId'},
			{name: 'paymentAccount',mapping: 'paymentAccount'},
			{name: 'paymentAccountName',mapping: 'paymentAccountName'},
			{name: 'paymentMoney',mapping: 'paymentMoney'},
			{name: 'paymentDate',mapping: 'paymentDate'},
			{name: 'paymentStatus',mapping: 'paymentStatus'},
			
			{name: 'paymentPayStatus',mapping: 'paymentPayStatus'},
			{name: 'paymentLaunchTime',mapping: 'paymentLaunchTime'},
			{name: 'paymentLaunchName',mapping: 'paymentLaunchName'},
			{name: 'paymentAuditTime',mapping: 'paymentAuditTime'},
			{name: 'paymentAuditName',mapping: 'paymentAuditName'},
			{name: 'paymentAuditStatus',mapping: 'paymentAuditStatus'},
			{name: 'paymentBatch',mapping: 'paymentBatch'},
		]),
	//	autoLoad: true
	});
	
	//数据加载
	redempGridStore.load({
		params: {
			start: 0
		}
	});
	
	var redempColModel = new Ext.grid.ColumnModel([
 		sm,
 		{header: '回款编号',dataIndex: 'paymentId',width: 100,align: 'center',hidden: true},
 		{header: '回款账户',dataIndex: 'paymentAccount',width: 100,align: 'center',
 			editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/^[0-9]*$/,
				regexText:'回款账户只能是数字,不能包含特殊字符',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
 		{header: '回款账户名称',dataIndex: 'paymentAccountName',width: 100,align: 'center',
 			editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/^[a-zA-Z0-9\u4e00-\u9fa5]+$/,
				regexText:'回款账户名称有误重新输入,不能包含特殊字符',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
  		{header: '回款金额',dataIndex: 'paymentMoney',width: 100,align: 'center',
  			editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
				regexText:'回款金额不正确或者包含特殊字符',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
  		{header: '回款时间',dataIndex: 'paymentDate',width: 100,align: 'center',hidden: true},
  		{header: '审核状态',dataIndex: 'paymentAuditStatus',renderer: reserveStatuss,width: 100,align: 'center'},
  		{header: '回款状态',dataIndex: 'paymentStatus',renderer: reserveStatus,width: 100,align: 'center'},
  		
  		{header: '支付状态',dataIndex: 'paymentPayStatus',width: 100,align: 'center'},
  		{header: '发起日期',dataIndex: 'paymentLaunchTime',width: 100,align: 'center'},
  		{header: '发起人员',dataIndex: 'paymentLaunchName',width: 100,align: 'center'},
  		{header: '审核日期',dataIndex: 'paymentAuditTime',width: 100,align: 'center'},
  		{header: '审核人员',dataIndex: 'paymentAuditName',width: 100,align: 'center'},
  		{header: '交易流水号',dataIndex: 'paymentBatch',width: 100,align: 'center',hidden: true},
 	]);
	
	//回款状态
   	function reserveStatus(val){
   		if(val=='0'){
   			return '<font color="green">成功</font>';
   		}else if(val=='1'){
   			return '<font color="red">失败</font>';
   		}else if(val=='2'){
   			return '<font color="gray">回款已受理</font>';
   		}
   	}
   	
	//审核状态
   	function reserveStatuss(val){
   		/*if(val=='0'){
			return '<font color="green">正常</font>';
		}else */if(val=='1'){
			return '<font color="gray">新增待审核</font>';
		}else if(val=='2'){
			return '<font color="green">新增审核通过</font>';
		}else if(val=='3'){
			return '<font color="gray">回款待审核</font>';
		}else if(val=='4'){
			return '<font color="green">回款审核拒绝</font>';
		}else if(val=='5'){
			return '<font color="green">回款审核通过</font>';
		}else if(val=='6'){
			return '<font color="gray">删除待审核</font>';
		}else if(val=='7'){
			return '<font color="green">删除审核拒绝</font>';
		}else if(val=='8'){
			return '<font color="gray">修改待审核</font>';
		}else if(val=='9'){
			return '<font color="green">修改审核通过</font>';
		}else if(val=='0'){
			return '<font color="green">修改审核拒绝</font>';
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
		text: '人行集中缴存回款',
		width: 85,
		iconCls: 'edit',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('paymentMoney'), amount);
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
								paymentId: record.get('paymentId'),//主键
								paymentAccount: record.get('paymentAccount'),//回款账户
								paymentAccountName: record.get('paymentAccountName'),//回款账户名称
								paymentMoney: record.get('paymentMoney'),//回款金额
								paymentStatus: record.get('paymentStatus'),//回款状态
								paymentDate: record.get('paymentDate'),//回款日期
								
								paymentAuditStatus: record.get('paymentAuditStatus'),//审核状态
								paymentPayStatus: record.get('paymentPayStatus'),//支付状态
								paymentLaunchTime: record.get('paymentLaunchTime'),//发起日期
								paymentLaunchName: record.get('paymentLaunchName'),//发起人员
								paymentAuditTime: record.get('paymentAuditTime'),//审核日期
								paymentAuditName: record.get('paymentAuditName'),//审核人员paymentBatch
								paymentBatch: record.get('paymentBatch'),//交易流水号
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_paymentHK.asp',
						params: {
							infList: Ext.encode(array),
							txnId: '90601',
							subTxnId: '01'
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
	
	//新增赎回信息按钮
	var BackFillMenuAdd = {
		text: '添加',
		id: 'menuAdd',
		iconCls: 'add',
		handler: function(){
			redempCodeForm.getForm().reset();
			redempCodeForm.getForm().clearInvalid();
			redempWindow.show();
			redempWindow.center();
		}
	};
	
	
	//回款信息form表单
	var redempCodeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 100,
		waitMsgTarget: true,
		items: [/*{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '回款账户名称*',
			id: 'paymentAccountName',
			name: 'paymentAccountName',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '回款账户名称不能为空',
			//可观输入信息是什么
			emptyText: '请输入回款账户名称',
			//text文本输入框宽度
			width:150,
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '回款账户*',
			id: 'paymentAccount',
			name: 'paymentAccount',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '回款账户不能为空',
			//可观输入信息是什么
			emptyText: '请输入回款账户',
			//text文本输入框宽度
			width:150,
			vtype: 'isNumber',
			vtypeText:'只能输入数字'
		},*/{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '回款金额*',
			id: 'paymentMoney',
			name: 'paymentMoney',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '回款金额不能为空',
			//可观输入信息是什么
			emptyText: '请输入回款金额',
			//text文本输入框宽度
			width:150
		},{
			//下拉框类型dynamicCombo   combo
			xtype: 'combo',
			//label文本值
			fieldLabel: '回款账户*',
			hiddenName: 'paymentInsSeq',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '回款账户不能为空',
			//可观输入信息
			emptyText: '请输入回款金额',
			//text文本输入框宽度
			width:150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['01','01-人行备付金ACS账户'],['73','73-招行客户备付金账户'],['74','74-招行自有资金账户']]
			})
		}]
	});
	
	
	//新增赎回信息窗口
	var redempWindow = new Ext.Window({
		title: '人行集中缴存回款信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 300,
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
					url: 'T91301Action_paymentAdd.asp',
					waitMsg: '正在提交，请稍后......',
					success: function(form,action) {
						showSuccessMsg(action.result.msg,redempCodeForm);
						//添加成功后窗口关闭
						redempWindow.hide();
//						var paymentAccount = redempCodeForm.getForm().findField('paymentAccount').getValue();
//						//baseParams: 
//						redempGridStore.baseParams.paymentAccount = paymentAccount;
//						//重新加载参数
//						redempGridStore.reload();
						redempGridStore.load();
					},
					failure: function(form,action) {
						showErrorMsg(action.result.msg,redempCodeForm);
					},
					params: {
						txnId: '90601',
						subTxnId: '02'
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
	
	
	var BackFillMenuDel = {
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
							paymentId: record.get('paymentId'),//主键
						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T91301Action_paymentDel.asp',
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
							txnId: '90601',
							subTxnId: '03'
						}
					});
				}
			});
		}
	};
	
	var BackFillMenuUp = {
			text: '保存',
			id: 'addReload',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = redempGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				if(modifiedRecords.length > 1) {
					alert("只允许修改一条记录！");  
					return;
				}
				var record = modifiedRecords['0'];
				Ext.Ajax.requestNeedAuthorise({
					url: 'T91301Action_paymentUp.asp',
					params: {
						paymentId: record.get('paymentId'),
						paymentAccount: record.get('paymentAccount'),
						paymentAccountName : record.get('paymentAccountName'),
						paymentMoney: record.get('paymentMoney'),
						txnId: '90601',
						subTxnId: '04'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							redempGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,redempGrid);
						} else {
							redempGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,redempGrid);
						}
						redempGrid.getTopToolbar().items.items[8].disable();
						redempGrid.getStore().reload();
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};
		
	menuArr.push(queryCondition);
//	menuArr.push('-');
//	menuArr.push(BackFillMenu); //[3]
	menuArr.push('-');
	menuArr.push(BackFillMenuAdd);  //[5]
	menuArr.push('-');
	menuArr.push(BackFillMenuDel);  //[7]
	menuArr.push('-');
	menuArr.push(BackFillMenuUp);  //[9]
		
	
	// 信息列表
	var redempGrid = new Ext.grid.EditorGridPanel({
		title: '人行集中缴存回款',
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
			fieldLabel: '回款账户',
			methodName: 'getPayAccountAll',
			hiddenName: 'paymentAccount',
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
	
	//重新加载
	redempGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			paymentAccount: queryForm.getForm().findField('paymentAccount').getValue()
		});
	});
	
	//选中数据显示按钮
	redempGrid.getSelectionModel().on({
		'rowselect':function(){
			//获取选中的记录
			var rec = redempGrid.getSelectionModel().getSelected();
			//审核状态为待审核时,按钮隐藏或显示
			if(rec.get('paymentAuditStatus')=='3'){  //回款待审核
				Ext.getCmp("menuAdd").enable();
				Ext.getCmp("redempDelete").enable();
			}else if(rec.get('paymentAuditStatus')=='6'){  //删除待审核
				Ext.getCmp("menuAdd").enable();
				Ext.getCmp("redempDelete").disable();
			}else if(rec.get('paymentAuditStatus')=='8'){  //修改待审核
				Ext.getCmp("menuAdd").enable();
				Ext.getCmp("redempDelete").enable();
			}else{
				Ext.getCmp("menuAdd").enable();
				Ext.getCmp("redempDelete").enable();
			}
		}
	});
	
	redempGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			Ext.getCmp('addReload').enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
	
});
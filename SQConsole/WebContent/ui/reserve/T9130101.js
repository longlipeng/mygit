Ext.onReady(function(){
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	//备款任务数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=redempGridStoreTmpBK'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'reserveId'
		},[
			{name: 'reserveId',mapping: 'reserveId'},
			{name: 'reserveTime',mapping: 'reserveTime'},
			{name: 'redemptionMoney',mapping: 'redemptionMoney'},
			{name: 'reserveSettleMoney',mapping: 'reserveSettleMoney'},
			{name: 'reserveMoney',mapping: 'reserveMoney'},
			{name: 'reserveStatus',mapping: 'reserveStatus'},
			{name: 'reserveSettleStatus',mapping: 'reserveSettleStatus'},
			{name: 'reservePayStatus',mapping: 'reservePayStatus'},
			{name: 'reserveLaunchTime',mapping: 'reserveLaunchTime'},
			{name: 'reserveLaunchName',mapping: 'reserveLaunchName'},
			{name: 'reserveAuditTime',mapping: 'reserveAuditTime'},
			{name: 'reserveAuditName',mapping: 'reserveAuditName'},
			{name: 'redemptionBatch',mapping: 'redemptionBatch'},
		]),
		autoLoad: true  //二次加载  用于第一次加载为空 第二次加载数据的情况
	});
	
	//数据加载
//	redempGridStore.load({
//		params: {
//			start: 0
//		}
//	});
	
	var redempColModel = new Ext.grid.ColumnModel([
		sm,
		{header: '备款编号',dataIndex: 'reserveId',width: 100,hidden:true},
		{header: '日期',dataIndex: 'reserveTime',width: 150,align: 'center'},
		{header: '赎回金额',dataIndex: 'redemptionMoney',width: 100,align: 'center'},
		{header: '结算金额',dataIndex: 'reserveSettleMoney',width: 100,align: 'center'},
		{header: '备款金额',dataIndex: 'reserveMoney',width: 100,align: 'center',
			editor: new Ext.form.TextField({
				allowBlank: false,
				regex:/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
				regexText:'备款金额不正确或者包含特殊字符',
				maxLength: 50,
				vtype: 'isOverMax'
			})},
		{header: '审核状态',dataIndex: 'reserveStatus',renderer: reserveStatus,width: 100,align: 'center'},
		{header: '备款状态',dataIndex: 'reserveSettleStatus',renderer: reserveStatuss,width: 100,align: 'center'},
		{header: '支付状态',dataIndex: 'reservePayStatus',width: 100,align: 'center'},
		{header: '发起日期',dataIndex: 'reserveLaunchTime',width: 100,align: 'center'},
		{header: '发起人',dataIndex: 'reserveLaunchName',width: 100,align: 'center'},
		{header: '审核日期',dataIndex: 'reserveAuditTime',width: 100,align: 'center'},
		{header: '审核人',dataIndex: 'reserveAuditName',width: 100,align: 'center'},
   		
	]);
	
	//审核状态
	function reserveStatus(val){
		if(val=='0'){
			return '<font color="green">正常</font>';
		}else if(val=='1'){
			return '<font color="gray">修改待审核</font>';
		}else if(val=='2'){
			return '<font color="gray">备款待审核</font>';
		}
	}
	
	//备款状态
	function reserveStatuss(val){
		if(val=='0'){
			return '<font color="green">成功</font>';
		}else if(val=='1'){
			return '<font color="red">失败</font>';
		}else if(val=='2'){
   			return '<font color="gray">备款中</font>';
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
	
	var menuArr = new Array();
	
	/*var refresh = {
		text : '刷新',
		width : 85,
		id : 'query',
		handler : function() {
			redempGridStore.load();
		}
	};*/
	
	var redempMenu = {
		text: '商户备款',
		width: 85,
		iconCls: 'edit',
		id: 'redemp',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
//						amount  = parseFloat(count.get('redempTionMoney')) + parseFloat(amount);
					amount = accAdd(count.get('reserveSettleMoney'), amount);
				}
				showConfirm('你勾选了'+redempRecords.length+'条记录，金额：'+ amount +'.确认备款！',redempGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",redempGrid);
						return;
					}
					showMask("正在提交备款信息，请稍后....",redempGrid);
					var array = new Array();
						for(var index = 0; index < redempRecords.length; index++) {
							var record = redempRecords[index];
							var data = {
								reserveId: record.get('reserveId'),//主键
								reserveTime: record.get('reserveTime'),//日期
								redemptionMoney: record.get('redemptionMoney'),//赎回金额
								reserveSettleMoney: record.get('reserveSettleMoney'),//结算金额
								reserveMoney: record.get('reserveMoney'),//备款金额(可修改)
								reserveStatus: record.get('reserveStatus'),//审核状态(0.正常, 1.修改待审核, 2.备款待审核)
								reserveSettleStatus: record.get('reserveSettleStatus'),//备款状态(0.成功, 1.失败)
								reservePayStatus: record.get('reservePayStatus'),//支付状态
								reserveLaunchTime: record.get('reserveLaunchTime'),//发起日期
								reserveLaunchName: record.get('reserveLaunchName'),//发起人员
								reserveAuditTime: record.get('reserveAuditTime'),//审核时间
								reserveAuditName: record.get('reserveAuditName'),//备款账户
								redemptionAccountName: record.get('redemptionAccountName')//备款账户名称
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_redemp.asp',
						params: {
							infList: Ext.encode(array),
							txnId: '9130101',
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
	
	var upMenu = {
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
				url: 'T91301Action_addRedemp.asp',
				params: {
					reserveId: record.get('reserveId'),
					reserveMoney : record.get('reserveMoney'),
					txnId: '9130101',
					subTxnId: '02'
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
					redempGrid.getTopToolbar().items.items[3].disable();
					redempGrid.getStore().reload();
				},
				failure: function(){
					hideMask();
				}
			});
		}
	};
	
	menuArr.add('-');    //[0]
	menuArr.add(redempMenu);  //[1]
	menuArr.add('-');    //[2]
	menuArr.add(upMenu);   //[3]
	
	var redempGrid = new Ext.grid.EditorGridPanel({
		title: '商户结算备款',
		region: 'center',
		iconCls: 'T91301',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: '',
		stripeRows: true,
		store: redempGridStore,
		tbar:menuArr,
		cm: redempColModel,
		sm: sm,
		clicksToEdit: true,
		forceValidation: true,
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
	
	redempGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			date: date,
			predate: predate,
		});
	});
	
	redempGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			Ext.getCmp('addReload').enable();
		}
	});
	
	//选中数据显示按钮
//	redempGrid.getSelectionModel().on({
//		'rowselect':function(){
//			获取选中数据
//			var rec = redempGrid.getSelectionModel().getSelected();
//			if(rec.get('reserveStatus')=='1'){
//				Ext.getCmp('redemp').disable();
//			}else if(rec.get('reserveStatus')=='0'){
//				Ext.getCmp('redemp').enable();
//			}else{
//				Ext.getCmp('redemp').disable();
//			}
//			
//		}
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
	
});
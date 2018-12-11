Ext.onReady(function(){
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	//备款任务数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=redempGridStoreTmpBK1'
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
			{name: 'reserveBatch',mapping: 'reserveBatch'},
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
		{header: '备款编号',dataIndex: 'reserveId',width: 100,hidden:true},
		{header: '日期',dataIndex: 'reserveTime',width: 150,align: 'center'},
		{header: '赎回金额',dataIndex: 'redemptionMoney',width: 100,align: 'center'},
		{header: '结算金额',dataIndex: 'reserveSettleMoney',width: 100,align: 'center'},
		{header: '备款金额',dataIndex: 'reserveMoney',width: 100,align: 'center'},
		{header: '审核状态',dataIndex: 'reserveStatus',renderer: reserveStatus,width: 100,align: 'center'},
		{header: '备款状态',dataIndex: 'reserveSettleStatus',renderer: reserveStatuss,width: 100,align: 'center'},
		{header: '支付状态',dataIndex: 'reservePayStatus',width: 100,align: 'center'},
		{header: '发起日期',dataIndex: 'reserveLaunchTime',width: 100,align: 'center'},
		{header: '发起人',dataIndex: 'reserveLaunchName',width: 100,align: 'center'},
		{header: '审核日期',dataIndex: 'reserveAuditTime',width: 100,align: 'center'},
		{header: '审核人',dataIndex: 'reserveAuditName',width: 150,align: 'center'},
//		{header: '交易流水号',dataIndex: 'reserveBatch',width: 150,align: 'center',hidden:true},
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
   			return '<font color="gray">备款受理中</font>';
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
	
	/*var refresh = {
		text : '刷新',
		width : 85,
		id : 'query',
		handler : function() {
			batchStore.load();
		}
	};*/
	
	var refuseMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('reserveSettleMoney'), amount);
				}
				showConfirm('你<font color="red">审核</font>'+ redempRecords.length +'条记录，金额：'+ amount +'.确认通过！',redempGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",redempGrid);
						return;
					}
					showMask("正在提交审核信息，请稍后....",redempGrid);
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
								reserveAuditName: record.get('reserveAuditName'),//审核人员
								reserveBatch: record.get('reserveBatch'),//交易流水号
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_redempAccept.asp',
//						url: 'backRcvResponse.asp',
						params: {
							infList: Ext.encode(array),
							txnId: '80601',
							subTxnId: '04'
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
		
	var sucMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('reserveSettleMoney'), amount);
				}
				showConfirm('你<font color="red">审核</font>'+ redempRecords.length +'条记录，金额：'+ amount +'.确认通过！',redempGrid,function(bt) {
					if(bt == 'yes') {
					if(redempRecords.length == 0) {
						showErrorMsg("请勾选数据！",redempGrid);
						return;
					}
					showMask("正在提交审核信息，请稍后....",redempGrid);
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
								reserveAuditName: record.get('reserveAuditName'),//审核人员
								reserveBatch: record.get('reserveBatch'),//交易流水号
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_redempRefuse.asp',
						params: {
							infList: Ext.encode(array),
							txnId: '80601',
							subTxnId: '05'
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
		
	//menuArr.push(refresh);  //[1]
	menuArr.push('-');
	menuArr.push(refuseMenu); //[3]
	menuArr.push('-');
	menuArr.push(sucMenu);  //[5]
	
	//信息列表
	var redempGrid = new Ext.grid.GridPanel({
		title: '商户结算审核',
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
	
	redempGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [redempGrid],
		renderTo: Ext.getBody()
	});
	
});
Ext.onReady(function(){
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	//备款任务数据源
	var redempGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=focusStore'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'focusId'
		},[
			{name: 'focusId',mapping: 'focusId'},
			{name: 'focusAccount',mapping: 'focusAccount'},
			{name: 'focusAccountName',mapping: 'focusAccountName'},
			{name: 'focusMoney',mapping: 'focusMoney'},
			{name: 'focusDate',mapping: 'focusDate'},
			{name: 'focusStatus',mapping: 'focusStatus'},
			
			{name: 'focusPayStatus',mapping: 'focusPayStatus'},
			{name: 'focusLaunchTime',mapping: 'focusLaunchTime'},
			{name: 'focusLaunchName',mapping: 'focusLaunchName'},
			{name: 'focusAuditTime',mapping: 'focusAuditTime'},
			{name: 'focusAuditName',mapping: 'focusAuditName'},
			{name: 'focusAuditStatus',mapping: 'focusAuditStatus'},
			{name: 'focusBatch',mapping: 'focusBatch'},
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
   		{header: '主键',dataIndex: 'focusId',width: 100,align: 'center',hidden: true},
   		{header: '日期',dataIndex: 'focusDate',width: 100,align: 'center'},
   		{header: '备款账户',dataIndex: 'focusAccount',width: 100,align: 'center'},
   		{header: '备款账户名称',dataIndex: 'focusAccountName',width: 100,align: 'center'},
   		{header: '备款金额',dataIndex: 'focusMoney',width: 100,align: 'center'},
   		{header: '备款时间',dataIndex: 'focusAuditTime',width: 100,align: 'center'},
   		{header: '备款状态',dataIndex: 'focusStatus',renderer: reserveStatus,width: 100,align: 'center'},
   		
   		{header: '审核状态',dataIndex: 'focusAuditStatus',renderer: reserveStatuss,width: 100,align: 'center',hidden: true},
   		{header: '支付状态',dataIndex: 'focusPayStatus',width: 100,align: 'center'},
  		{header: '发起日期',dataIndex: 'focusLaunchTime',width: 100,align: 'center',hidden: true},
  		{header: '发起人员',dataIndex: 'focusLaunchName',width: 100,align: 'center',hidden: true},
  		
  		{header: '审核人员',dataIndex: 'focusAuditName',width: 100,align: 'center',hidden: true},
  		{header: '交易流水号',dataIndex: 'focusBatch',width: 100,align: 'center'},
   	]);
   	
	//备款状态
	function reserveStatus(val){
		if(val=='0'){
			return '<font color="green">成功</font>';
		}else if(val=='1'){
			return '<font color="red">失败</font>';
		}else if(val=='2'){
   			return '<font color="gray">备款已受理</font>';
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
			return '<font color="gray">备款待审核</font>';
		}else if(val=='4'){
			return '<font color="green">备款审核拒绝</font>';
		}else if(val=='5'){
			return '<font color="green">备款审核通过</font>';
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
	
	var BackFillMenu = {
		text: '回填状态查询',
		width: 85,
		iconCls: 'edit',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('focusMoney'), amount);
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
								focusId: record.get('focusId'),//主键
								focusAccount: record.get('focusAccount'),//备款账户
								focusAccountName: record.get('focusAccountName'),//备款账户名称
								focusMoney: record.get('focusMoney'),//备款金额
								focusStatus: record.get('focusStatus'),//备款状态
								focusDate: record.get('focusDate'),//备款日期
								
								focusAuditStatus: record.get('focusAuditStatus'),//审核状态
								focusPayStatus: record.get('focusPayStatus'),//支付状态
								focusLaunchTime: record.get('focusLaunchTime'),//发起日期
								focusLaunchName: record.get('focusLaunchName'),//发起人员
								focusAuditTime: record.get('focusAuditTime'),//审核日期
								focusAuditName: record.get('focusAuditName'),//审核人员
								focusBatch: record.get('focusBatch'),//交易流水号
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_focusBackFill.asp',
						params: {
							infList: Ext.encode(array),
							txnId: '91305',
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
		
	var BackFillMenuSDFail = {
		text: '手动回填失败',
		width: 85,
		iconCls: 'edit',
		handler: function() {
		var redempRecords = redempGrid.getSelectionModel().getSelections();
				var amount = 0;
				for(var i = 0; i < redempRecords.length; i++){
					var count = redempRecords[i];
					amount = accAdd(count.get('focusMoney'), amount);
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
								focusId: record.get('focusId'),//主键
								focusAccount: record.get('focusAccount'),//备款账户
								focusAccountName: record.get('focusAccountName'),//备款账户名称
								focusMoney: record.get('focusMoney'),//备款金额
								focusStatus: record.get('focusStatus'),//备款状态
								focusDate: record.get('focusDate'),//备款日期
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_focusFail.asp',
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
					amount = accAdd(count.get('focusMoney'), amount);
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
								focusId: record.get('focusId'),//主键
								focusAccount: record.get('focusAccount'),//备款账户
								focusAccountName: record.get('focusAccountName'),//备款账户名称
								focusMoney: record.get('focusMoney'),//备款金额
								focusStatus: record.get('focusStatus'),//备款状态
								focusDate: record.get('focusDate'),//备款日期
							};
							array.push(data);
						}
						Ext.Ajax.request({
						url: 'T91301Action_focusSuccess.asp',
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
		
	//menuArr.push(refresh);  //[1]
	menuArr.push('-');
	menuArr.push(BackFillMenu);  //[3]
	menuArr.push('-');
	menuArr.push(BackFillMenuSDFail);  //[5]
	menuArr.push('-');
	menuArr.push(BackFillMenuSDSuccess);  //[7]
	
	// 信息列表
	var redempGrid = new Ext.grid.GridPanel({
		title: '人行集中缴存回填',
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
	
	//重新加载
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
Ext.onReady(function() {
	
	
	// 冻结商户信息
	var frozenStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=frozenS'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'frozenDate',mapping: 'frozenDate'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'frozenAccount',mapping: 'frozenAccount'},
			{name: 'frozenAccountFinish',mapping: 'frozenAccountFinish'},
			{name: 'frozenAccountNoFinish',mapping: 'frozenAccountNoFinish'},
			{name: 'frozenRoute',mapping: 'frozenRoute'},
			{name: 'frozenReason',mapping: 'frozenReason'},
			{name: 'frozenFinishFlag',mapping: 'frozenFinishFlag'},
			{name: 'stats',mapping: 'stats'},
			{name: 'desId',mapping: 'desId'}
		])
	});
	
	frozenStore.load({
		
	});
	
	
	var frozenColModel = new Ext.grid.ColumnModel([
		{header: '冻结日期',dataIndex: 'frozenDate',width: 100},
		{header: '商户号',dataIndex: 'mchtNo',width: 200,renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 100},
		{header: '冻结金额',dataIndex: 'frozenAccount',width: 90},
		{header: '已冻结金额',dataIndex: 'frozenAccountFinish',width: 80},
		{header: '未完成冻结金额',dataIndex: 'frozenAccountNoFinish',width: 120},
		{header: '冻结方式',dataIndex: 'frozenRoute',width: 100,renderer:getRoute},
		{header: '冻结备注',dataIndex: 'frozenReason',width: 270},
		{header: '冻结完成标志',dataIndex: 'frozenFinishFlag',width: 100,renderer:getFlag},
		{header: '审核状态',dataIndex: 'stats',width: 100,renderer:getStats},
		{header: '后台渠道',dataIndex: 'desId',width: 100,renderer:getDesId}
		
	]);
	
	
	function getRoute(val){
		if(val=='1'){
			return '风控冻结 '; 
		}
		if(val=='2'){
			return '手动风控冻结 ';
		}
		if(val=='3'){
			return '负金额冻结 ';
		}
		if(val=='4'){
			return '退货风控冻结 ';
		}
	}
	
	function getFlag(val){
		if(val=='0'){
			return '<font color="red">未完成</font>'; 
		}
		if(val=='1'){
			return '<font color="green">已完成</font>';
		}
	}
	function getStats(val){
    	if(val == '0')
            return "<font color='gray'>新增待审核</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改待审核</font>";
    	if(val == '4')
    		return "<font color='gray'>删除待审核</font>";
    	if(val == '5')
            return "<font color='red'>新增审核拒绝</font>";
    }
	function getDesId(val){
		if(val=='1708'){
			return '上汽通道 '; 
		}else if(val=='1719'){
			return '单用途卡通道 '; 
		}else if(val==''){
			return ' - '; 
		}
	}
	var delMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
					showConfirm('确认审核通过吗？',grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T80402Action.asp?method=accept',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
										grid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
									ids: rec.get('id'),
									stats: rec.get('stats'),
									txnId: '80403',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
		var upMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: true,
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
					showConfirm('确认拒绝吗？',grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T80402Action.asp?method=refuse',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
										grid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
									ids: rec.get('id'),
									stats: rec.get('stats'),
									txnId: '80403',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
	var menuArr = new Array();
	menuArr.push(delMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(upMenu);		// [2]
	
	//冻结商户grid
	var grid = new Ext.grid.EditorGridPanel({
		title: '延迟结算审核',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: frozenStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: frozenColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载冻结商户列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: frozenStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据所选择的状态判断哪个按钮可用
			//rec = mchntGrid.getSelectionModel().getSelected();

			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
/*	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
//		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'rowselect': function() {
			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});*/

	
//	frozenStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			frozenDate_query: typeof(queryForm.findById('frozenDate_query').getValue()) == 'string'?'':queryForm.findById('frozenDate_query').getValue().dateFormat('Ymd'),
//			mchtNo_query: queryForm.findById('mchtNo_query').getValue(),
//			termId_query: queryForm.findById('termId_query').getValue(),
//			frozenRoute_query:queryForm.findById('frozenRoute_query').getValue()
//		});
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
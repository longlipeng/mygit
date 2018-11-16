Ext.onReady(function() {
	// 当前选择记录
	var record;
	// 商户白名单待审核数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckWhiteList'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[  
			{name: 'uuid',mapping: 'uuid'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'beginDt',mapping: 'beginDt'},
			{name: 'validity',mapping: 'validity'},
			{name: 'insOpr',mapping: 'insOpr'},
			{name: 'insDt',mapping: 'insDt'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'updDt',mapping: 'updDt'},
			{name: 'state',mapping: 'state'},
			{name: 'appRemark',mapping: 'appRemark'},
			{name: 'addType',mapping: 'addType'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	//商户名
	var mchtNmStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_NM',function(ret){
		mchtNmStore.loadData(Ext.decode(ret));
	});	
	
	function mchtNmRen(val){
		return mchtNmStore.query("valueField",val).first().data.displayField;
	}
	
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
         {header: '主键',dataIndex: 'uuid',width: 180,hidden:true},
		{header: '商户编号',dataIndex: 'mchtNo',width: 180},
		{header: '商户名称',dataIndex: 'mchtNo',width: 180,renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '状态',dataIndex: 'state',width: 120,renderer:riskInfoState},
		{header: '有效期',dataIndex: 'validity',width: 60},
		{header: '起始日期',dataIndex: 'beginDt',width: 120},
		 {header: '添加方式',dataIndex: 'addType',width: 100,renderer:addType},  //0手动添加    1自动添加
		 {header: '申请人',dataIndex: 'insOpr',width: 80},
//		 {header: '审核人',dataIndex: 'updOpr',width: 100}
		 {header: '申请时间',dataIndex: 'insDt',width: 150,renderer:formatTs},
		 {header: '申请备注',dataIndex: 'appRemark',width: 200}
	]);
	
	
	function addType(val){
		if(val == '0')
            return "手动添加";
    	if(val == '1')
            return "自动添加";
		
	}
	
	var menuArray = new Array();
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showInputMsg('提示','请输入审核备注',true,accept);
			}
		};
	
	
	function accept(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('审核备注最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入审核备注',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T41202Action.asp?method=accept',
				params: {
					uuid: rec.get('uuid'),
					txnId: '41202',
					subTxnId: '01',
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
					url: 'T41202Action.asp?method=refuse',
					params: {
					    uuid: rec.get('uuid'),
						txnId: '41202',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,grid);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
						// 重新加载商户黑名单待审核信息
						grid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArray.add(acceptMenu);//[0]
	menuArray.add('-');       //[1]
	menuArray.add(refuseMenu);//[2]
	
	// 卡黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户白名单审核',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: mchntRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntRiskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户白名单列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: mchntRiskStore,
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
			
			// 根据所选择的商户状态判断哪个按钮可用
			//rec = mchntGrid.getSelectionModel().getSelected();

			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
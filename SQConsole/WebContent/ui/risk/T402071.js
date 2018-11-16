Ext.onReady(function() {
	// 当前选择记录
	var record;
	// 商户黑名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=toCheckMchntRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			/*{name: 'saMerNo',mapping: 'saMerNo'},
			{name: 'saMerChName',mapping: 'saMerChName'},
			{name: 'saMerEnName',mapping: 'saMerEnName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saZoneNo',mapping: 'saZoneNo'},
			{name: 'saAdmiBranNo',mapping: 'saAdmiBranNo'},
			{name: 'saConnOr',mapping: 'saConnOr'},
			{name: 'saConnTel',mapping: 'saConnTel'},
			{name: 'saState',mapping: 'saState'}*/
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saZoneNo',mapping: 'saZoneNo'},
			{name: 'saAdmiBranNo',mapping: 'saAdmiBranNo'},
			{name: 'saConnOr',mapping: 'saConnOr'},
			{name: 'saConnTel',mapping: 'saConnTel'},
			{name: 'saState',mapping: 'saState'},
			{name: 'saInitOprId',mapping: 'saInitOprId'},
			{name: 'saModiOprId',mapping: 'saModiOprId'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'saMerChName',mapping: 'saMerChName'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'srMerNo',mapping: 'srMerNo'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'identifyNo',mapping: 'identifyNo'},
			{name: 'datePk',mapping: 'datePk'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'addType',mapping: 'addType'},
			{name: 'saInitTime',mapping: 'saInitTime'},
			{name: 'appRemark',mapping: 'appRemark'},
			{name: 'saArea',mapping: 'saArea'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
        {header: '时间主键',dataIndex: 'datePk',width: 100,hidden:true},
		{header: '商户中文名称',dataIndex: 'saMerChName',width: 150/*,id:'saMerChName'*/},
		{header: '状态',dataIndex: 'saState',width: 90,renderer:riskInfoState},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 110},
		{header: '组织机构代码证号',dataIndex: 'bankLicenceNo',width: 110},
		{header: '法人身份证号码',dataIndex: 'identifyNo',width: 130},
		{header: '法人联系电话',dataIndex: 'managerTel',width: 120},
		{header: '添加方式',dataIndex: 'addType',width: 80,renderer:addType},
		{header: '申请人',dataIndex: 'saInitOprId',width: 80},
		{header: '申请时间',dataIndex: 'saInitTime',width: 140,renderer:formatTs},
		{header: '申请备注',dataIndex: 'appRemark',width: 150},
		{header: '地区',dataIndex: 'saArea',width: 80}
//		{header: '操作人',dataIndex: 'saModiOprId',width: 80}
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
				url: 'T40207Action.asp?method=accept',
				params: {
					datePk: rec.get('datePk'),
					txnId: '40207',
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
					url: 'T40207Action.asp?method=refuse',
					params: {
					    datePk: rec.get('datePk'),
						txnId: '40207',
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
		title: '商户黑名单审核',
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
			msg: '正在加载商户黑名单列表......'
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
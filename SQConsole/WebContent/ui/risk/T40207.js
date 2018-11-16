Ext.onReady(function() {
	// 当前选择记录
	var record;
	// 商户黑名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstEntitys'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},              
			{name: 'cttp',mapping: 'cttp'},              
			{name: 'ctcr',mapping: 'ctcr'},       
			{name: 'ctnm',mapping: 'ctnm'},  
			{name: 'ciid',mapping: 'ciid'},  
			{name: 'cleg',mapping: 'cleg'},        
			{name: 'clid',mapping: 'clid'},         
			{name: 'care',mapping: 'care'},         
			{name: 'lstp',mapping: 'lstp'},         
			{name: 'create_time',mapping: 'create_time'},     
			{name: 'creator',mapping: 'creator'},   
			{name: 'update_time',mapping: 'update_time'},   
			{name: 'updator',mapping: 'updator'},
			{name: 'ckstatus',mapping: 'ckstatus'}
		])
	});
	mchntRiskStore.load({
		params: {
			start: 0,
			ckstatus:'0,6,7'
		}
	});
	var mchntRiskColModel = new Ext.grid.ColumnModel([
        {header: 'id',dataIndex: 'id',width: 10,hidden:true},
		{header: '商户类型',dataIndex: 'cttp',width: 120,renderer:tenantType},
		{header: '商户国籍',dataIndex: 'ctcr',width: 80},
		{header: '商户名称',dataIndex: 'ctnm',width: 120},
		{header: '证件号码',dataIndex: 'ciid',width: 120},
		{header: '法人代表',dataIndex: 'cleg',width: 120},
		{header: '法人证件号码',dataIndex: 'clid',width: 120},
		{header: '所属地区',dataIndex: 'care',width: 120},
		{header: '名单类别',dataIndex: 'lstp',width: 120,renderer:lstpType},//0:黑名单,1:白名单,2:关注名单
		{header: '创建时间',dataIndex: 'create_time',width: 120,renderer: formatTs},
		{header: '创建人',dataIndex: 'creator',width: 120},
		{header: '更新时间',dataIndex: 'update_time',width: 120,renderer: formatTs},
		{header: '更新人',dataIndex: 'updator',width: 100},
		{header: '审核状态',dataIndex: 'ckstatus',width: 100,renderer: checkState}
	]);
	function lstpType(val){
		if(val == '0')
            return "<font color='gray'>黑名单</font>";
    	if(val == '1')
            return "<font color='red'>白名单</font>";
    	if(val == '2')
            return "<font color='green'>关注名单</font>";
	}
	function tenantType(val){
		if(val == '1')
            return "<font color='gray'>个人</font>";
    	if(val == '2')
            return "<font color='red'>机构</font>";
	}
	function checkState(val){
		if(val == '0')
            return "<font color='gray'>新增待审核</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改审核拒绝</font>";
    	if(val == '4')
    		return "<font color='gray'>删除审核拒绝</font>";
    	if(val == '5')
    		return "<font color='gray'>新增审核拒绝</font>";
    	if(val == '6')
    		return "<font color='gray'>新增删除</font>";
    	if(val == '7')
    		return "<font color='gray'>修改待审核</font>";
	}
	var menuArray = new Array();
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',grid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T40202Action.asp?method=acceptNew',
							params: {
							    id: rec.get('id'),
							    ckstatus: rec.get('ckstatus'),
							    creator: rec.get('creator'),
								txnId: '40207',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
	};
		var refuseMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: true,
			handler:function() {
				showConfirm('确认审核拒绝吗？',grid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T40202Action.asp?method=refuseNew',
							params: {
							    id: rec.get('id'),
							    ckstatus: rec.get('ckstatus'),
							    creator: rec.get('creator'),
								txnId: '40207',
								subTxnId: '02'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
		};
		
		// 拒绝按钮调用方法
		
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
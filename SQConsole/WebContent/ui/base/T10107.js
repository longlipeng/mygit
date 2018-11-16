Ext.onReady(function() {
	
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ageninfoquery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'agenid'
		},[
			{name: 'agenid',mapping: 'agenid'},
			{name: 'agenname',mapping: 'agenname'},
			{name: 'statue',mapping: 'statue'},
			{name: 'agenregbody',mapping: 'agenregbody'},
			{name: 'bankname',mapping: 'bankname'},
			{name: 'agenincomeaccountname',mapping: 'agenincomeaccountname'},
			{name: 'agenexpressaccountname',mapping: 'agenexpressaccountname'},
			{name: 'tranType',mapping: 'tranType'}
		])
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		{id: 'agenid',header: '机构号',dataIndex: 'agenid',sortable: true,width: 100},
		{header: '机构名称',dataIndex: 'agenname',width: 200,id: 'agenname'},
		{header: '审核状态',dataIndex: 'statue'},
		{header: '机构交易地区',dataIndex: 'agenregbody',width: 130,renderer:function(val){return getRemoteTrans(val, "AgencyCity");}},
		{header: '开户行名称',dataIndex: 'bankname',width: 80},
		{header: '机构收入账号开户名',dataIndex: 'agenincomeaccountname',width: 150},
		{header: '机构支出账号开户名',dataIndex: 'agenexpressaccountname',width: 150},
		{header: '交易连接类型',dataIndex: 'tranType',width: 150,hidden:true}
	]);
		
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var detailMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			
			setTimeout(function(){bt.enable()},2000);
			showAgencyDetailS(mchntGrid.getSelectionModel().getSelected().get('agenid'),mchntGrid.getSelectionModel().getSelected().get('tranType'),mchntGrid)
		}
	}
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	//注销
	var delMenu = {
		text: '注销',
		width: 85,
		iconCls: 'recycle',
		disabled: true,
		handler:function() {
			showConfirm('您确定注销机构信息吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,delBack);
					
				}
			});
		}
	};
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('操作原因不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10106Action.asp?method=delete',
				params: {
					agenid: mchntGrid.getSelectionModel().getSelected().get('agenid'),
					tranType: mchntGrid.getSelectionModel().getSelected().get('tranType'),
					txnId: '10107',
					subTxnId: '01',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
						mchntGrid.getTopToolbar().items.items[2].disable();
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载机构信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	menuArr.push(detailMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(queryCondition);  //[3]
	menuArr.push('-');//3
    menuArr.push(delMenu);//4

	var mchntGrid = new Ext.grid.GridPanel({
		title: '机构信息维护',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'agenname',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
	//	plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载机构信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
			//修改未审核与注销未审核的不可以注销
			if(rec.get('statue')=='修改未审核'){
				mchntGrid.getTopToolbar().items.items[4].disable();
				mchntGrid.getTopToolbar().items.items[0].enable();
			} else
			//注销未审核、注销状态的不可以修改 与注销
			if(rec.get('statue')=='注销未审核'||rec.get('statue')=='注销'){
				mchntGrid.getTopToolbar().items.items[0].disable();
				mchntGrid.getTopToolbar().items.items[4].disable();
			}
			else{
				mchntGrid.getTopToolbar().items.items[0].enable();
				mchntGrid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '审核状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','正常'],['2','修改未审核'],['3','新增拒绝'],
//				       ['4','修改拒绝'],['6','注销拒绝'],
				       ['7','注销'],['8','注销未审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '机构编号',
			methodName: 'getAgenID',
			hiddenName: 'agenid',
			editable: true,
			width: 380
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			agenid: queryForm.getForm().findField('agenid').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue()
//			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
//			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
//			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
//			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});
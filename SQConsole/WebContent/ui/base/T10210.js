Ext.onReady(function() {	

	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=holidaysshenhe'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'id',mapping: 'id'},
			{name: 'holidayStart',mapping: 'holidayStart'},
			{name: 'holidayEnd',mapping: 'holidayEnd'},
			{name: 'name',mapping: 'name'},
			{name: 'createOprId',mapping: 'createOprId'},
			{name: 'createTime',mapping: 'createTime'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTime',mapping: 'updTime'},
			{name: 'saState',mapping: 'saState'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: '节假日开始日期',dataIndex: 'holidayStart',width: 100,renderer: formatDt},
		{header: '节假日结束日期',dataIndex: 'holidayEnd',width: 100,renderer: formatDt},
		{header: '节假日名称',dataIndex: 'name',width: 150},
		{header: '修改人',dataIndex: 'createOprId',width: 100},    		
    	{header: '修改时间',dataIndex: 'createTime',width: 150,renderer: formatTs},
    	{header: '审核人',dataIndex: 'updOprId',width: 100},
    	{header: '审核时间',dataIndex: 'updTime',width: 150,renderer: formatTs},
    	{header: '审核状态',dataIndex: 'saState',width: 100,renderer: riskInfoState}
    ]);
		
	//通过
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
						url: 'T10210Action.asp?method=accept',
						params: {
							id: rec.get('id'),
							txnId: '10210',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载分公司待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	//拒绝
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
				url: 'T10210Action.asp?method=refuse',
				params: {
					id: rec.get('id'),
					txnId: '10210',
					subTxnId: '03',
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
	//菜单添加
	var menuArr = new Array();
	menuArr.push(acceptMenu);
    menuArr.push('-');
    menuArr.push(refuseMenu);
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push('-');	
	menuArr.push(queryCondition); 
	
	var grid = new Ext.grid.EditorGridPanel({
		title: '节假日审核',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载节假日信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		'beforeedit':function(e){
			if(e.record.get('saState')=='4'){
				e.cancel=true;
			}
				
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		grid.getTopToolbar().items.items[0].enable();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('sastate')!='3'&&rec.get('sastate')!='4')//注销未审核、修改未审核 不可以注销
				grid.getTopToolbar().items.items[2].enable();
			else
				grid.getTopToolbar().items.items[2].disable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idSaState',
			fieldLabel: '审核状态',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增待审核'],['3','修改待审核'],['4','删除待审核']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日开始日期',
			id:'idholidayStart',
			name: 'holidayStart',
			emptyText:'格式：yyyymmdd',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('idholidayEnd').getValue()!='' && 
							(Ext.getCmp('idholidayStart').getValue().format('Ymd') > Ext.getCmp('idholidayEnd').getValue().format('Ymd'))){
						showAlertMsg("起始日期不能大于截止日期，请重新选择！",queryForm);
						Ext.getCmp('idholidayStart').setValue('');
						Ext.getCmp('idholidayEnd').setValue('');}
				}
			}
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日结束日期',
			emptyText:'格式：yyyymmdd',
			id:'idholidayEnd',
			name: 'holidayEnd',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('idholidayStart').getValue()!='' && 
							(Ext.getCmp('idholidayStart').getValue().format('Ymd') > Ext.getCmp('idholidayEnd').getValue().format('Ymd'))){
						showAlertMsg("开始日期不能大于结束日期，请重新选择！",queryForm);
						Ext.getCmp('idholidayStart').setValue('');
						Ext.getCmp('idholidayEnd').setValue('');}
				}
			}
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日名称',
			id:'name',
			name: 'name',
			width: 150
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			saState: queryForm.getForm().findField('idSaState').getValue(),
			holidayStart: queryForm.getForm().findField('idholidayStart').getValue(),
			holidayEnd: queryForm.getForm().findField('idholidayEnd').getValue(),
			name: queryForm.getForm().findField('name').getValue()
		});
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 450,
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
});

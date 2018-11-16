Ext.onReady(function() {
	
	var mchntNetStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntNet&operate=search'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntId'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'status',mapping: 'status'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'legalNm',mapping: 'legalNm'},
			{name: 'createTime',mapping: 'createTime'}
		])
	});
	
	mchntNetStore.load({
		params: {
			start: 0
		}
	});
	
	
	var mchntColModel = new Ext.grid.ColumnModel([
		{id: 'mchntId',header: '入网商户序列号',dataIndex: 'id',sortable: true,width: 100},
		{header: '所属机构',dataIndex: 'brhId',width: 200},
		{header: '商户状态',dataIndex: 'status',renderer: mchntSt},
		{header: '入网商户名称',dataIndex: 'mchtNm'},
		{header: '法人姓名',dataIndex: 'legalNm',width: 200},
		{header: '申请时间',dataIndex: 'createTime',width: 200}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			window.open(Ext.contextPath + '/page/mchnt/T2040101.jsp', 'newwindow', 'height=600,width=790,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		}
	};
	
//	var editMenu = {
//		text: '修改',
//		width: 85,
//		iconCls: 'edit',
//		disabled: true,
//		handler:function() {
//			childWin = window.open(Ext.contextPath + '/T2010102TransAction.asp?mchntId=' + rec.get('mchntId'), 'newwindow', 'height=600,width=790,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
//			childWin.attachEvent('onunload',function() {
//				mchntNetStore.reload();
//			});
//		}
//	}
//	
//	var stopMenu = {
//		text: '停用',
//		width: 85,
//		iconCls: 'stop',
//		disabled: true,
//		handler:function() {
//			showConfirm('确定停用吗？',mchntGrid,function(bt) {
//				if(bt == 'yes') {
//					showProcessMsg('正在提交信息，请稍后......');
//					rec = mchntGrid.getSelectionModel().getSelected();
//					Ext.Ajax.request({
//						url: 'T20101Action.asp?method=stop',
//						params: {
//							mchntId: rec.get('mchntId'),
//							txnId: '20101',
//							subTxnId: '03'
//						},
//						success: function(rsp,opt) {
//							var rspObj = Ext.decode(rsp.responseText);
//							if(rspObj.success) {
//								showSuccessMsg(rspObj.msg,mchntGrid);
//							} else {
//								showErrorMsg(rspObj.msg,mchntGrid);
//							}
//							// 重新加载商户信息
//							mchntGrid.getStore().reload();
//						}
//					});
//					hideProcessMsg();
//				}
//			});
//		}
//	}
//	
//	var recoverMenu = {
//		text: '恢复',
//		width: 85,
//		iconCls: 'recover',
//		disabled: true,
//		handler:function() {
//			showConfirm('确定恢复吗？',mchntGrid,function(bt) {
//				if(bt == 'yes') {
//					showProcessMsg('正在提交信息，请稍后......');
//					rec = mchntGrid.getSelectionModel().getSelected();
//					Ext.Ajax.request({
//						url: 'T20101Action.asp?method=recover',
//						params: {
//							mchntId: rec.get('mchntId'),
//							txnId: '20101',
//							subTxnId: '04'
//						},
//						success: function(rsp,opt) {
//							var rspObj = Ext.decode(rsp.responseText);
//							if(rspObj.success) {
//								showSuccessMsg(rspObj.msg,mchntGrid);
//							} else {
//								showErrorMsg(rspObj.msg,mchntGrid);
//							}
//							// 重新加载商户信息
//							mchntGrid.getStore().reload();
//						}
//					});
//					hideProcessMsg();
//				}
//			});
//		}
//	}
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			window.open(Ext.contextPath + '/MchntNetDetailAction.asp?mchntId=' + rec.get('id'), 'newwindow', 'height=600,width=780,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
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
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
//	menuArr.push(editMenu);    //[2]
//	menuArr.push('-');         //[3]
//	menuArr.push(stopMenu);    //[4]
//	menuArr.push('-');         //[5]
//	menuArr.push(recoverMenu); //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-'); //[9]
	menuArr.push(queryCondition); //[10]
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'id',
			name: 'id',
			vtype: 'alphanum',
			fieldLabel: '入网序列号'
		},{
			xtype: 'textfield',
			id: 'mchtNm',
			name: 'mchtNm',
			vtype: 'alphanum',
			fieldLabel: '入网名称'
		}]
	});
	
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
			mchntNetStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	

	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: mchntNetStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntNetStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
//			if(rec.get('mchntSt') == '0' || rec.get('mchntSt') == '2' || rec.get('mchntSt') == '4') {
//				mchntGrid.getTopToolbar().items.items[2].enable();
//			} else {
//				mchntGrid.getTopToolbar().items.items[2].disable();
//			}
//			if(rec.get('mchntSt') == '0') {
//				mchntGrid.getTopToolbar().items.items[4].enable();
//			} else {
//				mchntGrid.getTopToolbar().items.items[4].disable();
//			}
//			if(rec.get('mchntSt') == '6') {
//				mchntGrid.getTopToolbar().items.items[6].enable();
//			} else {
//				mchntGrid.getTopToolbar().items.items[6].disable();
//			}
			mchntGrid.getTopToolbar().items.items[3].enable();
		}
	});
	
	mchntNetStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			id: queryForm.findById('id').getValue(),
			mchtNm: queryForm.findById('mchtNm').getValue(),
			operate: 'search'
		});
	});
});
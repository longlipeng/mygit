Ext.onReady(function() {
	
	// 当前选择记录
	var record;
	
	// 商户黑名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saZoneNo',mapping: 'saZoneNo'},
			{name: 'saAdmiBranNo',mapping: 'saAdmiBranNo'},
			{name: 'saConnOr',mapping: 'saConnOr'},
			{name: 'saConnTel',mapping: 'saConnTel'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'srMerNo',mapping: 'srMerNo'},
			{name: 'termCount',mapping: 'termCount'},
		//	{name: 'bankNo',mapping: 'bankNo'},
			{name: 'saMerChName',mapping: 'saMerChName'},
			{name: 'saState',mapping: 'saState'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'identifyNo',mapping: 'identifyNo'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'saInitOprId',mapping: 'saInitOprId'},
			{name: 'saModiOprId',mapping: 'saModiOprId'},
			{name: 'datePk',mapping: 'datePk'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'addType',mapping: 'addType'},
			{name: 'saArea',mapping: 'saArea'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
//	var riskLvlStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	SelectOptionsDWR.getComboData('RISKLVL',function(ret){
//		riskLvlStore.loadData(Ext.decode(ret));
//	});
//	function riskLvl(val){
//		return riskLvlStore.query("valueField",val).first().data.displayField;
//	}
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{header: '时间主键',dataIndex: 'datePk',width: 100,hidden:true},
		{header: '商户中文名称',dataIndex: 'saMerChName',width: 150},
		{header: '状态',dataIndex: 'saState',width: 90,renderer:riskInfoState},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 110},
		{header: '组织机构代码证号',dataIndex: 'bankLicenceNo',width: 140},
		{header: '法人身份证号码',dataIndex: 'identifyNo',width: 130},
		{header: '法人联系电话',dataIndex: 'managerTel',width: 120},
		{header: '添加方式',dataIndex: 'addType',width: 80,renderer:addType},   //0手动添加    1审核拒绝
		{header: '申请人',dataIndex: 'saInitOprId',width: 80}, 
		{header: '审核人',dataIndex: 'saModiOprId',width: 80},
		{header: '地区',dataIndex: 'saArea',width: 80}

	]);
	
	function addType(val){
		if(val == '0')
            return "手动添加";
    	if(val == '1')
            return "自动添加";
		
	}
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		text: '添加',
		width: 85,
		handler: function() {
			mchntRiskWin.show();
			mchntRiskWin.center();
		}
	};
	var uploadMenu = {
		text: '批量上传商户黑名单信息',
		width: 85,
		iconCls: 'upload',
		id: 'upload',
		disabled: false,
		handler:function() {
			dialog.show();
		}
	};
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T40202Action.asp?method=uploadFile',
		filePostName : 'xlsFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB', 
		fileTypes : '*.xls',
		fileTypesDescription : '微软Excel文件(1997-2003)',
		title: '商户黑名单上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		postParams: {
			txnId: '40202',
			subTxnId: '01'
		}
	});
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function(){
			mchntRiskWinDel.show();
			mchntRiskWinDel.center();
		}
		/*handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var datePk = record.get('datePk');
						
				showConfirm('确定要删除该黑名单商户吗？' ,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T40202Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[4].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								datePk: datePk,
								txnId: '40202',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}*/
		
	};
		

	
	var queryMenu = {
		text: '查询',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	
	/*var expMenu = {
			text: '导出',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T40202ExpAction.asp',
					timeout : 600000000,
					params: {
				
						srMerNo: queryForm.findById('srMerNo').getValue(),
						saMerChName: queryForm.findById('saMerChName').getValue(),
					
				txnId: '40202',
				subTxnId: '04'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};*/
	
	menuArray.add(addMenu);
	menuArray.add('-');
	//menuArray.add(uploadMenu);
	//menuArray.add('-');
	menuArray.add(delMenu);
//	menuArray.add('-');
//	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
	/*menuArray.add('-');
	menuArray.add(expMenu);*/
	
	// 商户黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户黑名单信息',
		iconCls: 'mchnt',
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
	
	grid.on({
		/*'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		},*/
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('saState')!='4')
				grid.getTopToolbar().items.items[2].enable();
			else
				grid.getTopToolbar().items.items[2].disable();
		}
	});
	
	/*// 编辑完成后自动提交
	grid.on('afteredit',function() {
		record = grid.getSelectionModel().getSelected();
		showProcessMsg('正在提交信息，请稍后......');
		Ext.Ajax.request({
			url: 'T40202Action.asp?method=update',
			params: {
				saMerNo: record.get('saMerNo'),
				saLimitAmt: record.get('saLimitAmt'),
				saAction: record.get('saAction'),
				txnId: '40202',
				subTxnId: '02'
			},
			success: function(rsp,opt) {
				hideProcessMsg();
				var rspObj = Ext.decode(rsp.responseText);
				if(rspObj.success) {
					grid.getStore().commitChanges();
					showSuccessMsg(rspObj.msg,grid);
				} else {
					grid.getStore().rejectChanges();
					showErrorMsg(rspObj.msg,grid);
				}
				// 重新加载终端信息
				grid.getStore().reload();
			}
		});
	})*/
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	// 商户黑名单添加表单
	var mchntRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
//		defaults: {
//			width: 320
//		},
		items: [{
				 xtype: 'textfield',
				 name: 'saMerChName',
				 maxLength:30,
				 fieldLabel: '商户中文名称*',
				 width:140,
				 allowBlank:false
			    },
			    {
				 xtype: 'textfield',
				 name: 'licenceNo',
				 maxLength:20,
				  fieldLabel: '营业执照编号*',
				 width:140,
				 allowBlank:false
			    },
			    {
				  xtype: 'textfield',
				  name: 'bankLicenceNo',
				  maxLength:20,
				  fieldLabel: '组织机构代码证号',
				  width:140
				},
			    {
				  xtype: 'textfield',
				  name: 'identityNo',
				  maxLength:20,
				  fieldLabel: '法人身份证号*',
				  width:140,
				  allowBlank:false
			    },
			    {
				  xtype: 'textfield',
				  name: 'managerTel',
				  maxLength:30,
				  fieldLabel: '法人联系电话',
				  width:140
				},
			    {
				  xtype: 'textfield',
				  name: 'saArea',
				  maxLength:30,
				  fieldLabel: '地区',
				  width:140
				},
			    {
				  xtype: 'textarea',
				  name: 'appRemark',
				  maxLength:60,
				  fieldLabel: '申请备注',
				  width:170,
				  allowBlank:false
				}
		]
	});
	
	// 商户黑名单添加窗口
	var mchntRiskWin = new Ext.Window({
		title: '添加商户黑名单',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 360,
		resizable: false,
		autoHeight: true,
		items: [mchntRiskForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(mchntRiskForm.getForm().isValid()) {
					mchntRiskForm.getForm().submit({
						url: 'T40202Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntRiskWin);
							//重新加载参数列表
							grid.getStore().reload();
							mchntRiskForm.getForm().reset();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntRiskWin);
						},
						params: {
							txnId: '40202',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				mchntRiskForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntRiskWin.hide();
			}
		}]
	});
	
	var mchntRiskFormDel = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textarea',
			name: 'appRemark',
			maxLength:100,
			fieldLabel: '申请备注',
			width:200
		}
		]
	});
	

	var mchntRiskWinDel = new Ext.Window({
		title: '删除商户黑名单',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		resizable: false,
		autoHeight: true,
		items: [mchntRiskFormDel],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(mchntRiskFormDel.getForm().isValid()) {
					var rec = grid.getSelectionModel().getSelected();
					var datePk = rec.get('datePk');
					mchntRiskFormDel.getForm().submit({
						url: 'T40202Action.asp?method=delete',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntRiskWinDel);
							//重新加载参数列表
							grid.getStore().reload();
							mchntRiskFormDel.getForm().reset();
							mchntRiskWinDel.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntRiskWinDel);
						},
						params: {
							datePk: datePk,
							txnId: '40202',
							subTxnId: '03'
						}
					});
				
				}
			}
			
		},{
			text: '重置',
			handler: function() {
				mchntRiskFormDel.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntRiskWinDel.hide();
			}
		}]
	});
	
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [ 
		  {
			xtype: 'textfield',
			id: 'saMerChName',
			name: 'saMerChName',
			fieldLabel: '商户名称'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		forceValidation: true,
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
				mchntRiskStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			saMerChName: queryForm.findById('saMerChName').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
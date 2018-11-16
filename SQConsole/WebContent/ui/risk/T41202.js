Ext.onReady(function() {
	
	// 当前选择记录
	var record;
	

	// 商户白名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=whiteList'
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
	
/*	var riskLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISKLVL',function(ret){
		riskLvlStore.loadData(Ext.decode(ret));
	});
	function riskLvl(val){
		return riskLvlStore.query("valueField",val).first().data.displayField;
	}*/
	
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
		{header: '有效期',dataIndex: 'validity',width: 60,
     	 editor: new Ext.form.TextField({
     		 	maxLength: 8,
     			allowBlank: false,
     			vtype: 'isOverMax',
     			regex:/^[0-9]{1,8}$/,
				regexText: '有效期只能是数字'
	     })},
		{header: '起始日期',dataIndex: 'beginDt',width: 120,
	    	 editor: new Ext.form.DateField({
	     			allowBlank: false,
	     			editable: false, 
	     			format:'Ymd',
	     			submitFormat : String
	     				
		      }),
		      renderer:function(value){
		    	 /* if(Ext.isDate(value))
		    		  return Ext.util.Format.date(value,'Ymd');
		    	  else{
		    		  return value;
		    	  }*/
		    	  return typeof(value) == 'string'?value:value.dateFormat('Ymd');
		      }
		      
		},
	//    {header: '备注',dataIndex: 'note',width: 160}
		 {header: '添加方式',dataIndex: 'addType',width: 100,renderer:addType},  //0手动添加    1自动添加
		 {header: '申请人',dataIndex: 'insOpr',width: 100},
		 {header: '审核人',dataIndex: 'updOpr',width: 100}
	     
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

	
/*	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var uuid = record.get('uuid');
						
				showConfirm('确定要删除该白名单商户吗？商户编号：' + mchtNo,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T41201Action.asp?method=delete',
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
								uuid: uuid,
								txnId: '41201',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};*/
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler:function(){
				mchntRiskWinDel.show();
				mchntRiskWinDel.center();
			}
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
	
	var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				mchntRiskWinUpd.show();
				mchntRiskWinUpd.center();
			}
			/*handler: function() {
				var modifiedRecords = grid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = grid.getStore();
				var len = store.getCount();
				for(var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}
				showProcessMsg('正在保存白名单商户信息，请稍后......');
				
				//存放要修改的白名单商户信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
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
					   uuid: record.get('uuid'),
					   mchtNo: record.get('mchtNo'),
					   validity : record.get('validity'),
					   beginDt:record.get('beginDt'),
					   addType:record.get('addType'),
					   
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T41201Action.asp?method=update',
					method: 'post',
					params: {
					    whiteInfList: Ext.encode(array),
						txnId: '41201',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						grid.enable();
						hideProcessMsg();
						if(rspObj.success) {
							grid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,grid);
						} else {
							grid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,grid);
						}
						grid.getTopToolbar().items.items[4].disable();
						grid.getStore().reload();
					}
				});
			}*/
		};
	
	
/*	var expMenu = {
			text: '导出',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T41201ExpAction.asp',
					timeout : 600000000,
					params: {
						mchtNo_query:queryForm.getForm().findField('mchtNo').getValue(),
					
				txnId: '41201',
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
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
/*	menuArray.add('-');
	menuArray.add(expMenu);*/
	
	// 商户白名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户白名单信息',
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
	
	/*grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});*/
	
	grid.on({
		/*'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		},*/
		//除了正常，其他状态下 禁止修改
		'beforeedit':function(e){
			if(e.record.get('state')=='0'||e.record.get('state')=='1'||e.record.get('state')=='3'||e.record.get('state')=='4'){
				e.cancel=true;
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			
			if (grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		/*'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('saState')!='4')
				grid.getTopToolbar().items.items[2].enable();
			else
				grid.getTopToolbar().items.items[2].disable();
		}*/
	
		//只有正常,和新增待审核状态下可以删除
	'rowselect' : function() {
		// 行高亮
		Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		rec = grid.getSelectionModel().getSelected();
		if(rec.get('state')=='4'){//删除 待审核状态 禁止删除
			grid.getTopToolbar().items.items[2].disable();
		}else if(rec.get('state')=='3'){//修改待审核的状态禁止删除
			grid.getTopToolbar().items.items[2]. disable();
		}else if(rec.get('state')=='1'){//已删除状态 禁止删除
			grid.getTopToolbar().items.items[2]. disable();
		}/*else if(rec.get('state')=='0'){//新增待审核状态也禁止删除
			grid.getTopToolbar().items.items[2]. disable();
		}*/else{
			grid.getTopToolbar().items.items[2]. enable();
		}
	}
	});
	

	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	// 商户白名单添加表单
	var mchntRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		defaults: {
			width: 320
		},
		items: [{
				 xtype: 'dynamicCombo',
			//	 methodName: 'getMchntIdTrue',
				 
				 methodName: 'getMchntId',
				 //xtype: 'combo',
				 fieldLabel: '商户编号*',
				 allowBlank: false,
				 //store: mchntStore,
				 hiddenName: 'mchtNo',
//				 id : 'idsaMerNo',
				 editable: true,
				 blankText: '商户编号不能为空',
				 emptyText: '请选择商户编号',
			 	 width:300
			    },
			    {		
				 xtype: 'textfield',
				 fieldLabel: '有效期*',
				 name:'validity',
				 maxLength:8,
				 regex:/^[0-9]{1,8}$/,
				 regexText: '有效期只能是数字',
				 allowBlank:false,
				 blankText: '有效期不能为空',
				 emptyText:'请输入有效期',
				 width:130
			    },
			    {
					xtype: 'datefield',
					width:163,
					name: 'beginDt',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					fieldLabel: '起始日期*',
					emptyText:'请选择起始日期',
					editable: false
				},
			    {
					xtype: 'textarea',
					width:180,
					name: 'appRemark',
					maxLength:50,
					fieldLabel: '申请备注',
					emptyText:'请填写备注'
				}
		       ]
	         });
	
	// 商户白名单添加窗口
	var mchntRiskWin = new Ext.Window({
		title: '添加商户白名单',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
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
						url: 'T41201Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntRiskWin);
							//重新加载参数列表
							grid.getStore().reload();
							mchntRiskForm.getForm().reset();
							mchntRiskWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntRiskWin);
						},
						params: {
							txnId: '41201',
							subTxnId: '02'
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
		title: '删除白名单',
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
					var uuid = rec.get('uuid');
					mchntRiskFormDel.getForm().submit({
						url: 'T41201Action.asp?method=delete',
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
							uuid: uuid,
							txnId: '41201',
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
	
	//保存表单
	var mchntRiskFormUpd = new Ext.form.FormPanel({
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
	
   //保存 窗口
	var mchntRiskWinUpd = new Ext.Window({
		title: '修改白名单',
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
		items: [mchntRiskFormUpd],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				var modifiedRecords = grid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = grid.getStore();
				var len = store.getCount();
				for(var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}
				showProcessMsg('正在保存白名单商户信息，请稍后......');
				
				//存放要修改的白名单商户信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
					   uuid: record.get('uuid'),
					   mchtNo: record.get('mchtNo'),
					   validity : record.get('validity'),
					   beginDt:record.get('beginDt'),
					   addType:record.get('addType'),
					   state:record.get('state')
					   
					};
					array.push(data);
				}
				
			//	Ext.Ajax.request({
				mchntRiskFormUpd.getForm().submit({
					url: 'T41201Action.asp?method=update',
			//		method: 'post',
					waitMsg: '正在提交，请稍后......',
					params: {
					    whiteInfList: Ext.encode(array),
						txnId: '41201',
						subTxnId: '01'
					},
					failure: function(form,action) {
						showErrorMsg(action.result.msg,mchntRiskWinDel);
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						grid.enable();
						hideProcessMsg();
						/*if(rspObj.success) {
							grid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,grid);
						} else {
							grid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,grid);
						}*/
						grid.getTopToolbar().items.items[4].disable();
						grid.getStore().reload();
						mchntRiskWinUpd.hide();
						
					}
				});
			}
			
		},{
			text: '重置',
			handler: function() {
				mchntRiskFormUpd.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntRiskWinUpd.hide();
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
			xtype: 'dynamicCombo',
			id: 'idmchtNo',
			hiddenName: 'mchtNo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
			width:230
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
			mchtNo_query: queryForm.findById('idmchtNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
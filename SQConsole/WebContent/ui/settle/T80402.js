Ext.onReady(function() {
	
	
	// 冻结商户信息
	var frozenStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=frozen'
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
			{name: 'oprFlag',mapping: 'oprFlag'},
			{name: 'stats',mapping: 'stats'},
			{name: 'salesStats',mapping: 'salesStats'},
			{name: 'desId',mapping: 'desId'}
		])
	});
	
	frozenStore.load({
		
	});
	
	//冻结商户CM
	var frozenColModel = new Ext.grid.ColumnModel([
        {header: '主键ID',dataIndex: 'id',width: 100,hidden:true},
		{header: '冻结日期',dataIndex: 'frozenDate',width: 100},
		{header: '商户号-商户名',dataIndex: 'mchtNo',width: 200,renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 100},
		{header: '冻结金额',dataIndex: 'frozenAccount',width: 90,
			editor:new Ext.form.TextField({
				regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
			    regexText:'请输入金额格式',
			    /*callFunction : function() {
			    //	var args = Ext.getCmp('propTpN').getValue();
			    	var rec = grid.getSelectionModel().getSelected();
			    	alert(rec.get('frozenAccount'));
			    }*/
			    listeners: {
			       'change' : function(field,newValue,oldValue){
			    	 //  alert(newValue);
			    	   var rec = grid.getSelectionModel().getSelected(); 
			    	   rec.set('frozenAccountNoFinish',newValue);
			       }	
			    
			    }
			   /* callFunction : function() {
			    	var rec = grid.getSelectionModel().getSelected();
				     alert(rec.get('frozenAccount'));
			    	
			    }*/
			})},
		{header: '已冻结金额',dataIndex: 'frozenAccountFinish',width: 80},
		{header: '未完成冻结金额',dataIndex: 'frozenAccountNoFinish',width: 120
			/*editor:new Ext.form.TextField({
				regex:/^[0-9]+$/,
			    regexText:'请输入数字',
				maxLength: 200
			})*/},
		{header: '冻结方式',dataIndex: 'frozenRoute',width: 100,renderer:getRoute},
		{header: '冻结备注',dataIndex: 'frozenReason',width: 270,
			editor:new Ext.form.TextField({
				id:'edit_frozenReason',
				maxLength: 200
			})
		},
		{header: '冻结完成标志',dataIndex: 'frozenFinishFlag',width: 100,renderer:getFlag/*,
			editor : new Ext.form.ComboBox( {
	           // hiddenName:'unionFlag',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未完成 '],['1','已完成']],
					reader: new Ext.data.ArrayReader()
				})
			
				})*/
		},
		{header:'审核状态',dataIndex:'stats',width: 100,renderer:getStats},
		{header:'退货完成状态',dataIndex:'salesStats',width: 100,renderer:getSalesStats},
		{header:'后台渠道',dataIndex:'desId',width: 100,renderer:getDesId}
	]);
	
	function getFlag(val){
		if(val=='0'){
			return '<font color="red">未完成</font>'; 
		}
		if(val=='1'){
			return '<font color="green">已完成</font>';
		}
		
	}
	function getSalesStats(val){
		if(val=='1'){
			return '<font color="red">退货已完成</font>'; 
		}else if(val=='0'){
			return '退货未完成 '; 
		}else if(val=='-'){
			return ' - '; 
		}
//		iF(VAL=='1'){
//			RETURN '<FONT COLOR="GREEN">已完成</FONT>';
//		}
		
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
	
	function getRoute(val){
		if(val=='1'){
			return '风控规则冻结 '; 
		}else if(val=='2'){
			return '手动风控冻结 ';
		}else if(val=='3'){
			return '负金额冻结 ';
		}else if(val=='4'){
			return '商户退货冻结 ';
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
	
	
	var menuArray = new Array();
	
		  
	var addMenu = {
			iconCls: 'add',
			text: '添加',
			width: 85,
			handler: function() {
				addWin.show();
				addWin.center();
			}
		};
	
	/*var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {		
				if(grid.getSelectionModel().hasSelection()) {
					var record = grid.getSelectionModel().getSelected();
					var id = record.get('id');
							
					showConfirm('确定要删除该条冻结商户信息吗？',grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T80402Action.asp?method=delete',
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
									id: id,
									txnId: '80402',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};*/
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
				var modifiedRecords = grid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}else if (modifiedRecords.length > 1){
					alert("此处只允许修改一条记录！");
					return;
				}
				
				var stats1 = modifiedRecords[0].get('stats');
				if (stats1 =='2') {
					alert("审核已通过不能修改!");
					return;
				}
				
				var store = grid.getStore();
				var len = store.getCount();
				for(var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}
				
				
				showProcessMsg('正在保存冻结商户信息，请稍后......');
				
				//存放要修改的冻结商户信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
					   id: record.get('id'),
					   frozenDate: record.get('frozenDate'),
					   mchtNo: record.get('mchtNo'),
					   termId: record.get('termId'),
					   frozenAccount:record.get('frozenAccount'),
					   frozenAccountFinish: record.get('frozenAccountFinish'),
					   frozenAccountNoFinish: record.get('frozenAccountNoFinish'),
					   frozenRoute: record.get('frozenRoute'),
					   frozenReason : record.get('frozenReason'),
					   frozenFinishFlag: record.get('frozenFinishFlag'),
					   oprFlag:record.get('oprFlag'),
					   stats:record.get('stats')
					};
					
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T80402Action.asp?method=update',
					method: 'post',
					params: {
					    frozenList: Ext.encode(array),
						txnId: '80402',
						subTxnId: '04'
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
			}
		};

	menuArray.add(addMenu);   //0
	menuArray.add('-');       //1
	menuArray.add(queryMenu);  //2
	menuArray.add('-');       //3
	/*menuArray.add(delMenu);
	menuArray.add('-');*/
	menuArray.add(upMenu);     //4
	
	//冻结商户grid
	var grid = new Ext.grid.EditorGridPanel({
		title: '延迟结算维护',
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
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: frozenStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.on({
		
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},
	    'beforeedit': function(e) {
	    	if(e.record.get('frozenFinishFlag')=='1'||e.record.get('frozenAccountFinish') != '0'){
				e.cancel=true;
			}


	    	
	}
	});
	
	
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			
		   //如果标志是未完成
		   if(rec.get('frozenFinishFlag')=='0'){
				grid.getTopToolbar().items.items[4].enable();
				
		   }
		   //如果标志是已完成
		   else if(rec.get('frozenFinishFlag')=='1'){
				grid.getTopToolbar().items.items[4].disable();
		   }}
	});
	
	

	
	// 添加表单
	var addForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		defaults: {
			width: 320
		},
		items: [/*{
			 xtype: 'datefield',
			 fieldLabel: '冻结日期*',
			 allowBlank: false,
			 id : 'frozenDate',
		 	 width:200
		    },*/{
				 xtype: 'dynamicCombo',
				 fieldLabel: '商户号*',
				 methodName: 'getMchtcd',
				 allowBlank: false,
				 hiddenName: 'mchtNo',
				 editable: true,
				 blankText: '商户号不能为空',
				 emptyText: '请选择商户编号',
			 	 width:230,
			 	 callFunction: function() {
					//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
					Ext.getCmp('id_termId').reset();
					Ext.getCmp('id_termId').parentP=this.value;
					Ext.getCmp('id_termId').getStore().reload();
				}
			    },
			    {		
			    
				 xtype: 'dynamicCombo',
			//	 methodName: 'getTermId',   
				 methodName: 'getTermIdMchnt3', 
				 fieldLabel: '终端号*',
				 hiddenName : 'termId',
				 id : 'id_termId',
				 allowBlank:false,
				 emptyText:'请选择终端号',
				 width:230
			    },
			    {
					xtype: 'textfield',
					width:230,
					name: 'frozenAccount',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					fieldLabel: '冻结金额*',
					allowBlank:false,
					regex: /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					regexText:'请输入金额格式',
		//			emptyText:'请选择起始日期',
					editable: false
				},{
					xtype: 'textfield',
					width:230,
					name: 'frozenReason',
					fieldLabel: '冻结备注'
				//	emptyText:'请填写备注'
				},{
					xtype: 'combo',
					width:230,
					id: 'frozenRoute',
					hiddenName:"valueField",
					allowBlank:false,
					emptyText:'请选择冻结方式',
					fieldLabel: '冻结方式*',
					store: new Ext.data.ArrayStore({
		                fields: ['valueField','displayField'],
		                data: [['2','手动风控冻结'],['4','商户退货冻结']]
		            })
				},{
					xtype: 'combo',
					width:230,
					id: 'desId1',
					hiddenName:"desId",
					allowBlank:false,
					emptyText:'请选择后台渠道',
					fieldLabel: '后台渠道*',
					store: new Ext.data.ArrayStore({
		                fields: ['valueField','displayField'],
		                data: [['1708','上汽通道'],['1719','单用途卡通道']]
		            })
				}
		       ]
	         });
	
	// 添加窗口
	var addWin = new Ext.Window({
		title: '添加冻结商户信息',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		resizable: false,
		autoHeight: true,
		items: [addForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url: 'T80402Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addWin);
							//重新加载参数列表
							grid.getStore().reload();
							addForm.getForm().reset();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addWin);
						},
						params: {
							txnId: '80402',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide();
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
			xtype: 'datefield',
			id: 'frozenDate_query',
			name: 'frozenDate_query',
			fieldLabel: '冻结日期',
			width:128
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchtcd',
			id: 'mchtNo_query',
			name: 'mchtNo_query',
			width:230,
			fieldLabel: '商户号',
		 	 callFunction: function() {
					//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
					Ext.getCmp('termId_query').reset();
					Ext.getCmp('termId_query').parentP=this.value;
					Ext.getCmp('termId_query').getStore().reload();
				}
		},{
			xtype: 'dynamicCombo',
			id: 'termId_query',
			name: 'termId_query',
			fieldLabel: '终端号',
		    methodName: 'getTermIdMchnt3'
		},{
			xtype: 'combo',
			id: 'frozenRoute_query',
			fieldLabel: '冻结方式',
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','风控冻结 '],['2','手动风控冻结'],['3','负金额冻结'],['4','退货风控冻结']]
            })
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
				frozenStore.load();
				//queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	frozenStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			frozenDate_query: typeof(queryForm.findById('frozenDate_query').getValue()) == 'string'?'':queryForm.findById('frozenDate_query').getValue().dateFormat('Ymd'),
			mchtNo_query: queryForm.findById('mchtNo_query').getValue(),
			termId_query: queryForm.findById('termId_query').getValue(),
			frozenRoute_query:queryForm.findById('frozenRoute_query').getValue()
		});
		
		/*if(grid.get('frozenFinishFlag')=='1'){
			grid.getTopToolbar().items.items[4].disable();
			Ext.getCmp('edit_frozenReason').disable();
	    }*/
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
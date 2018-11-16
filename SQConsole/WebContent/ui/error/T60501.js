Ext.onReady(function() {
	
	// 当前选择记录
	var record;
	
	// 差错信息登记数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=errorList'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'errSeqNo',mapping: 'errSeqNo'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'errType',mapping: 'errType'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'fee1',mapping: 'fee1'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'registTime',mapping: 'registTime'},
			{name: 'startTime',mapping: 'startTime'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
	    {id: 'errSeqNo',header: '差错流水号',dataIndex: 'errSeqNo',sortable: true,width: 80},
		{header: '商户编号',dataIndex: 'mchntNo',width: 120},
		{header: '差错类型',dataIndex: 'errType',width: 100},		
		{header: '本金',dataIndex: 'amt1',width: 100,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '本金不能为空',
			emptyText: '请输入本金',
			maxLength: 80
		 })},
		{header: '手续费',dataIndex: 'fee1',width: 100,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '机构名称不能为空',
			emptyText: '请输入机构名称',
			maxLength: 80,
			maxLengthText: '机构名称最多可以输入40个汉字'
		 })},
		{header: '是否入账',dataIndex: 'reserved',width: 100,renderer: saAction},
		{header: '登记时间',dataIndex: 'registTime',width: 120,renderer: formatTs},
		{header: '生效时间',dataIndex: 'startTime',width: 120,renderer: formatTs}
	]);
	
	// 受控动作
	function saAction(val) {
		if(val == '0')
			return '<font color="gray">未入账</font>';
		else if(val == '1')
			return '<font color="red">已入账</font>';
		else 
			return '<font color="green">状态未知</font>';
	}
	
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		text: '手工录入差错信息',
		width: 85,
		handler: function() {
			mchntRiskWin.show();
			mchntRiskWin.center();
		}
	};
	var uploadMenu = {
		text: '批量上传差错信息',
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
		uploadUrl : 'T60501Action.asp?method=uploadFile',
		filePostName : 'xlsFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB', 
		fileTypes : '*.xls',
		fileTypesDescription : '微软Excel文件(1997-2003)',
		title: '差错信息批量上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		postParams: {
			txnId: '60501',
			subTxnId: '01'
		}
	});
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var saMerNo = record.get('saMerNo');
						
				showConfirm('确定要删除该黑名单商户吗？商户编号：' + saMerNo,grid,function(bt) {
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
								saMerNo: saMerNo,
								txnId: '40201',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};
		
	var upMenu = {
		text: '调账保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
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
			showProcessMsg('正在保存差错信息，请稍后......');
					
			//存放要修改的卡黑名单信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('saMerNo'),
					saMerChName: record.get('saMerChName'),
					saMerEnName: record.get('saMerEnName'),
					saLimitAmt: record.get('saLimitAmt'),
					saAction: record.get('saAction'),
					saZoneNo: record.get('saZoneNo'),
					saAdmiBranNo: record.get('saAdmiBranNo'),
					saConnOr: record.get('saConnOr'),
					saConnTel: record.get('saConnTel'),
					saInitZoneNo: record.get('saInitZoneNo'),
					saInitOprId: record.get('saInitOprId'),
					saInitTime: record.get('saInitTime')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T60501Action.asp?method=update',
				method: 'post',
				params: {
					mchtInfList : Ext.encode(array),
					txnId: '60501',
					subTxnId: '02'
				},	
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[6].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
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
	
	menuArray.add(addMenu);
	menuArray.add('-');
	menuArray.add(uploadMenu);
	menuArray.add('-');
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
	
	// 差错信息登记列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '差错信息列表',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn:'errSeqNo',
		clicksToEdit: true,
		store: mchntRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntRiskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载差错信息列表......'
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
	
	grid.on({
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[6] != undefined) {
				grid.getTopToolbar().items.items[6].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
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
	
	// 可选差错交易类型下拉列表
	var errTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM',function(ret){
		errTypeStore.loadData(Ext.decode(ret));
	});
	
	// 可选差错交易类型下拉列表
	var errTypeCombo = new Ext.form.ComboBox({
		store: errTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择差错交易类型',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个差错交易类型',
		fieldLabel: '差错交易类型',
		id: 'errTypeId',
		name: 'errType',
		hiddenName: 'errType'
	});
	
	
	// 卡黑名单添加表单
	var mchntRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 600,
		autoHeight: true,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
                columnWidth: 1,
                layout: 'form',
                width: 500,
	        	labelWidth : 100,
                items: [{
						xtype: 'combo',
						fieldLabel: '商户编号*',
						allowBlank: false,
						store: mchntStore,
						hiddenName: 'mchntNo',
						editable: false,
						blankText: '商户编号不能为空',
						emptyText: '请选择商户编号',
						width: 450,
						listeners: {
							'select': function() {
								T40202.getMchntInfo(this.getValue(),function(ret){
								    var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
									Ext.getCmp('mchntNm').setValue(mchntInfo.mchtNm);
								});
							}
						}
					}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '商户名称*',
                    allowBlank: false,
                    id: 'mchntNm',
                    width: 155,
                    name: 'mchntNm'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [errTypeCombo]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '借贷标志*',
                    allowBlank: false,
                    id: 'cdFalg',
                    width: 155,
                    name: 'cdFalg'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '本金',
                    allowBlank: false,
                    id: 'amt1',
                    width: 155,
                    name: 'amt1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '手续费*',
                    allowBlank: false,
                    id: 'fee1',
                    width: 155,
                    name: 'fee1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '登记时间',
                    allowBlank: true,
                    id: 'registTime',
                    width: 155,
                    name: 'registTime'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '生效时间',
                    allowBlank: true,
                    id: 'startTime',
                    width: 155,
                    name: 'startTime'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '差错描述',
                    allowBlank: true,
                    id: 'errDesc',
                    width: 155,
                    name: 'errDesc'
                }]
            }]
	});
	
	// 差错信息登记添加窗口
	var mchntRiskWin = new Ext.Window({
		title: '手工录入差错信息',
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
						url: 'T60501Action.asp?method=add',
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
							txnId: '60501',
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
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 600,
		autoHeight: true,
		items: [{
                columnWidth: 1,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
						xtype: 'combo',
						fieldLabel: '商户编号*',
						allowBlank: false,
						store: mchntStore,
						id: 'mchntId',
						hiddenName: 'mchnt',
						editable: true,
						blankText: '商户编号不能为空',
						emptyText: '请选择商户编号',
						width: 250
					}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
						xtype: 'combo',
						fieldLabel: '差错交易类型*',
						allowBlank: false,
						store: errTypeStore,
						id: 'erTypeId',
						hiddenName: 'erType',
						editable: true,
						emptyText: '请选择差错交易类型',
						width: 250
					}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '起始登记时间',
                    allowBlank: true,
                    id: 'stRegTime',
                    width: 250,
                    name: 'stRegTime'
                }]
            }
            ,{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终止登记时间',
                    allowBlank: true,
                    id: 'enRegTime',
                    width: 250,
                    name: 'enRegTime'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '起始生效时间',
                    allowBlank: true,
                    id: 'stTime',
                    width: 250,
                    name: 'stTime'
                }]
            }
            ,{
                columnWidth: .5,
                layout: 'form',
                width: 400,
	        	labelWidth : 100,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终止生效时间',
                    allowBlank: true,
                    id: 'enTime',
                    width: 250,
                    name: 'enTime'
                }]
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
			mchntId: queryForm.findById('mchntId').getValue(),
			errType: queryForm.findById('erTypeId').getValue(),
			stRegTime: queryForm.findById('stRegTime').getValue(),
			enRegTime: queryForm.findById('enRegTime').getValue(),
			stTime: queryForm.findById('stTime').getValue(),
			enTime: queryForm.findById('enTime').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});
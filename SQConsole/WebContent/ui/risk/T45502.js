Ext.onReady(function() {
	// 当前选择记录
	var record;
	// 商户黑名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstRegions'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},              
			{name: 'lstp',mapping: 'lstp'},              
			{name: 'rgtp',mapping: 'rgtp'},              
			{name: 'rgcd',mapping: 'rgcd'},       
			{name: 'rgnm',mapping: 'rgnm'},  
			{name: 'remark',mapping: 'remark'},  
			{name: 'status',mapping: 'status'},        
			{name: 'create_time',mapping: 'create_time'},     
			{name: 'creator',mapping: 'creator'},   
			{name: 'update_time',mapping: 'update_time'},   
			{name: 'updator',mapping: 'updator'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
			
		}
	});
	var mchntRiskColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: 'id',dataIndex: 'id',width: 10,hidden:true},
		{header: '名单种类',dataIndex: 'lstp',width: 80},
		{header: '地区类型',dataIndex: 'rgtp',width: 80,renderer:rgtpType},
		{header: '行政地区代码',dataIndex: 'rgcd',width: 120},
		{header: '行政地区名称',dataIndex: 'rgnm',width: 120},
		{header: '备注',dataIndex: 'remark',width: 120},
		{header: '状态',dataIndex: 'status',width: 120,renderer:statusType},
		{header: '创建时间',dataIndex: 'create_time',width: 120,renderer: formatTs},
		{header: '创建人',dataIndex: 'creator',width: 120},
		{header: '更新时间',dataIndex: 'update_time',width: 120,renderer: formatTs},
		{header: '更新人',dataIndex: 'updator',width: 100}
	]);
function rgtpType(val){
		if(val == '0')
            return "<font color='gray'>国家</font>";
    	if(val == '1')
            return "<font color='red'>省</font>";
    	if(val == '2')
            return "<font color='green'>市</font>";
    	if(val == '3')
            return "<font color='green'>县</font>";
	}
	function statusType(val){
		if(val == '1')
            return "<font color='gray'>有效</font>";
    	if(val == '0')
            return "<font color='red'>无效</font>";
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
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var id = record.get('id');
				showConfirm('确定要删除该黑名单地区吗？' ,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T40202Action.asp?method=deleteRegion',
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
								txnId: '40202',
								subTxnId: '06'
							}
						});
					}
				});
			}
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
	
		// 保存
	
	
	menuArray.add(addMenu);
	menuArray.add('-');
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
	menuArray.add('-');
	
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户地区黑名单信息',
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
//				grid.getTopToolbar().items.items[4].enable();
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
		items: [{ 
                xtype: 'combo',
                fieldLabel: '地区类型*',
                id: 'rgtpId',
                hiddenName: 'rgtp',
                allowBlank:false,
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','国家'],['1','省'],['1','市'],['1','县']]
                })
        	},{
				 xtype: 'textfield',
				 name: 'rgcd',
				 maxLength:30,
				 fieldLabel: '行政地区代码*',
				 width:140,
				 allowBlank:false
			    },{
				 xtype: 'textfield',
				 name: 'lstp',
				 maxLength:30,
				 fieldLabel: '名单种类*',
				 width:140,
				 value:"201",
				 allowBlank:false
			    },{
				 xtype: 'textfield',
				 name: 'rgnm',
				 maxLength:20,
				 fieldLabel: '行政地区名称*',
				 width:140,
				 allowBlank:false
			    },{
                xtype: 'combo',
                fieldLabel: '状态*',
                id: 'statusId',
                hiddenName: 'status',
                allowBlank:false,
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','无效'],['1','有效']]
                })
        		}/*,{
				  xtype: 'textarea',
				  name: 'appRemark',
				  maxLength:60,
				  fieldLabel: '申请备注',
				  width:170,
				  allowBlank:false
				}*/
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
						url: 'T40202Action.asp?method=addRegion',
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
							subTxnId: '05'
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
						url: 'T40202Action.asp?method=deleteNew',
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
							id: datePk,
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
		items: [{
			xtype: 'textfield',
			id: 'ctnm',
			name: 'ctnm',
			fieldLabel: '商户名称'
		},{
			xtype: 'textfield',
			id: 'care',
			name: 'care',
			fieldLabel: '所属地区'
		},{
			xtype: 'textfield',
			id: 'ciid',
			name: 'ciid',
			fieldLabel: '证件号码'
		},{ 
                xtype: 'combo',
                fieldLabel: '审核状态',
                id: 'ckstatus',
                name: 'ckstatus',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['1','已删除'],['2','正常'],['3','修改审核拒绝'],['4','删除审核拒绝'],['5','新增审核拒绝'],['6','新增删除'],['7','修改待审核']]
                })
        },{ //lstp
                xtype: 'combo',
                fieldLabel: '名单类别',
                id: 'lstp',
                name: 'lstp',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','黑名单'],['1','白名单'],['2','关注名单']]
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
			ctnm: queryForm.findById('ctnm').getValue(),
			care: queryForm.findById('care').getValue(),
			ciid: queryForm.findById('ciid').getValue(),
			ckstatus: queryForm.findById('ckstatus').getValue(),
			lstp: queryForm.findById('lstp').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
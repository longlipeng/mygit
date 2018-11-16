Ext.onReady(function() {

	
	// 当前选择记录
	var record;
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	//分润方式数据集
	var divideTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('DIVIDETYPE',function(ret){
		divideTypeStore.loadData(Ext.decode(ret));
	});
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	
	// 代理商分润数据集
	var agentDivideStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agentDivide'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'uuid',mapping: 'uuid'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'divideType',mapping: 'divideType'},
			{name: 'discCd',mapping: 'discCd'},
			{name: 'profit',mapping: 'profit'},
			{name: 'minValue',mapping: 'minValue'},
			{name: 'maxValue',mapping: 'maxValue'},
			{name: 'state',mapping: 'state'}
		])
	});
	

	
	
	agentDivideStore.load({

	});
	

	
	var agentDivideCM = new Ext.grid.ColumnModel([
    {header: 'UUID',dataIndex: 'uuid',width: 200,hidden:true},
	{header: '代理商编号 - 代理商名称',dataIndex: 'agentNo',width: 200,sortable: true ,renderer:function(val){return getRemoteTrans(val, "getAgentNm");}},
	{header: '分润方式',dataIndex: 'divideType',width: 100,renderer:function(val){return getRemoteTrans(val, "divideType");}},
	{header: '代理商费率参数',dataIndex: 'discCd',width: 150,renderer:discCd },
    {header: '代理商分润下限值(万元)',dataIndex: 'minValue',width: 150,sortable:true},
    {header: '代理商分润上限值(万元)',dataIndex: 'maxValue',width: 150},
	{header: '代理商分润参数(单位：%)',dataIndex: 'profit',width: 150},
     {header: '状态',dataIndex: 'state',width: 80,renderer:state}
	]);

function state(val){
	if('0' == val)
		return '新增待审核';
	else if('1' == val)
		return '<font color="red">已删除</font>';
	else if('2' == val)
		return '<font color="green">正常</font>';
	else if('3' == val)
		return '修改待审核';
	else if('4' == val)
		return '删除待审核';
}

function discCd(val){    
   if(val == '')
	   return '';
   else{
	   return getRemoteTrans(val, "discCd");
   }
	   
}
	
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		text: '添加',
		width: 85,
		handler: function() {
			addAgentWin.show();
			addAgentWin.center();
		}
	};

	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var modifiedRecords = grid.getSelectionModel().getSelections();
				if(modifiedRecords.length == 0) {
					return;
				}
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
							agentNo: record.get('agentNo'),
							divideType : record.get('divideType'),
							discCd:record.get('discCd'),
							profit:record.get('profit')
					};
					
					array.push(data);
				}
				showConfirm('确定要删除该条记录吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T11502Action.asp?method=delete',
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
								divideList: Ext.encode(array),
								txnId: '11502',
								subTxnId: '03'
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

	var upMenu = {
			text: '保存',
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
				showProcessMsg('正在保存代理商分润信息，请稍后......');
				
				//存放要修改的白名单商户信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
							agentNo: record.get('agentNo'),
							divideType : record.get('divideType'),
							discCd:record.get('discCd'),
							profit:record.get('profit')
							
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T11502Action.asp?method=update',
					method: 'post',
					params: {
					    divideList: Ext.encode(array),
						txnId: '11501',
						subTxnId: '02'
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
	
	
	/*var exportMenu = {
			text: '导出',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T11502ExpAction.asp',
					timeout : 600000000,
					params: {
				
					queryAgentNo:queryForm.getForm().findField('queryAgentNo').getValue(),
					queryDivideType:queryForm.getForm().findField('queryDivideType').getValue(),
					
				txnId: '11502',
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
		};
	
	
	
	var importMenu = {
			text: '导入',
			width: 85,
			id: 'import',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};*/
	
	
	/*menuArray.add(addMenu);
	menuArray.add('-');
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(upMenu);
	menuArray.add('-');*/
	menuArray.add(queryMenu);
	/*menuArray.add('-');
	menuArray.add(exportMenu);
	menuArray.add('-');
	menuArray.add(importMenu);*/

	
	// 代理商分润grid
	var grid = new Ext.grid.EditorGridPanel({
		title: '代理商分润信息',
//		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: agentDivideStore,
//		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm: sm,
		cm: agentDivideCM,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载代理商分润信息......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: agentDivideStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
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
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	/*grid.getSelectionModel().on({
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
	});*/
	

	

	
	// 分润添加表单
	var addAgentForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		items : [ {
			layout : 'column',
			frame : true,

			items : [ {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype: 'dynamicCombo',
					 methodName: 'getAgentNo',
					 fieldLabel: '代理商编号*',
					 allowBlank: false,
					 hiddenName: 'agentNo',
					 blankText: '代理商编号不能为空',
					 emptyText: '请选择商户编号'
					 /*name : 'agentNo',
					 id : 'agentNo'*/
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype: 'basecomboselect',
                    fieldLabel: '分润方式*',
                    allowBlank: false,
                    hiddenName: 'divideType',
                    editable: false,
                    store:divideTypeStore,
                    blankText:'分润方式不能为空',
                    emptyText:'请选择分润方式'             
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'dynamicCombo',
					methodName: 'getDiscCd',
					fieldLabel : '代理商费率参数',
					hiddenName : 'discCd',				
					allowBlank: false
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商分润参数',
					id : 'profit',
					name : 'profit',
					allowBlank: false
				} ]
			}]
		} ]
	  });
	
	
	
	
	// 代理商分润信息新增
	var addAgentWin = new Ext.Window({
		title: '代理商分润信息',
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
		items: [addAgentForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(addAgentForm.getForm().isValid()) {
					addAgentForm.getForm().submit({
						url: 'T11502Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addAgentWin);
							//重新加载参数列表
							grid.getStore().reload();
							addAgentForm.getForm().reset();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addAgentWin);
						},
						params: {
							txnId: '11502',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addAgentForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addAgentWin.hide();
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
			//xtype: 'textfield',
			xtype:'dynamicCombo',
			methodName:'getAgentNo',
			id: 'idqueryAgentNo',
			hiddenName: 'queryAgentNo',
			fieldLabel: '代理商编号'
		},{
	    	xtype: 'basecomboselect',
	    	id:'queryDivideType',
            fieldLabel: '分润方式',
            hiddenName: 'DivideType',
            store:divideTypeStore,
            editable: true,
            width:300,
            anchor: '70%'
		},{
	    	xtype: 'combo',
            fieldLabel: '状态',
            hiddenName: 'state',
            id:'idstate',
            store:new Ext.data.ArrayStore({
            	 fields: ['valueField','displayField'],
                 data: [['0','新增待审核'],['1','已删除'],['2','正常'],['3','修改待审核'],['4','删除待审核']]
            }),
            width:300,
            anchor: '70%'
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
				agentDivideStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	agentDivideStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			agentNo_query: queryForm.findById('idqueryAgentNo').getValue(),
			divideType_query:queryForm.findById('queryDivideType').getValue(),
			state_query:queryForm.findById('idstate').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
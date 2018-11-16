Ext.onReady(function() {
	
	// 当前选择记录
	var record;
	
	// 中石油卡黑名单数据集
	var cardRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cnpcCardRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'customerProfile',mapping: 'customerProfile'},
			{name: 'cardLevel',mapping: 'cardLevel'},
			{name: 'checkCode',mapping: 'checkCode'},
			{name: 'saState',mapping: 'saState'},
			{name: 'initOprId',mapping: 'initOprId'},
			{name: 'initTime',mapping:'initTime'},
			{name: 'verifierOprId',mapping: 'verifierOprId'}
		])
	});
	
	cardRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var cardRiskColModel = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),                                             	
		{header: '客户类别',dataIndex: 'customerProfile',width: 200},
		{header: '卡片等级',dataIndex: 'cardLevel',width: 120},
		{header: '状态',dataIndex: 'saState',width: 90,renderer: cnpcStateInfo},
		{header: '申请人',dataIndex: 'initOprId',width: 90},
		{header: '创建时间',dataIndex: 'initTime',width: 90},
		{header: '审核人',dataIndex: 'verifierOprId',width: 90}
	]);
	 function cnpcStateInfo(val){
    	if(val == '0')
            return "<font color='gray'>正常</font>";
    	if(val == '1')
            return "<font color='red'>新增待审核</font>";
    	if(val == '2')
            return "<font color='green'>修改带审核</font>";
    	if(val == '3')
            return "<font color='gray'>删除待审核</font>";
    	if(val == '4')
    		return "<font color='gray'>修改审核拒绝</font>";
    	if(val == '5')
            return "<font color='red'>新增审核拒绝</font>";
    	if(val == '6')
            return "<font color='red'>删除审核拒绝</font>";
    	if(val == '7')
            return "<font color='red'>已删除</font>";
    }
	
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		width: 85,
		text: '添加',
		handler: function() {
			cardRiskWin.show();
			cardRiskWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function(){
			cardRiskWinDel.show();
			cardRiskWinDel.center();
		}
	};
	var uploadMenu = {
		text: '批量上传卡黑名单信息',
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
		uploadUrl : 'T40201Action.asp?method=uploadFile',
		filePostName : 'xlsFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB', 
		fileTypes : '*.xls',
		fileTypesDescription : '微软Excel文件(1997-2003)',
		title: '卡黑名单上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		postParams: {
			txnId: '40201',
			subTxnId: '01'
		}
	});
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
					url: 'T40201ExpAction.asp',
					timeout : 600000000,
					params: {
						srCardNo: queryForm.findById('srCardNo').getValue(),
					
				txnId: '40201',
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
//	menuArray.add(addMenu);
//	menuArray.add('-');
	//menuArray.add(uploadMenu);
//	menuArray.add('-');
//	menuArray.add(delMenu);
//	menuArray.add('-');
//	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu); //[4]
	/*menuArray.add('-');
	menuArray.add(expMenu);*/
	
	// 卡黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '中石油卡黑名单信息查询',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: cardRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRiskColModel,
		forceValidation: true,
		autoScroll: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载中石油卡黑名单列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: cardRiskStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	/*grid.on({
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});*/
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('saState')!='4')//删除待审核状态，不可以删除
				grid.getTopToolbar().items.items[2].enable();
			else
				grid.getTopToolbar().items.items[2].disable();
		}
	});
	
	
	
	// 卡黑名单添加表单
	var cardRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			id: 'customerProfile',
			name: 'customerProfile',
			maxLength: 1,
			regex: /^[0-9]{1,19}$/,
			regexText: '客户类别只能是数字',
			fieldLabel: '客户类别',
			allowBlank: false,
			blankText: '客户类别不能为空',
			emptyText: '请输入客户类别'
		},{
			xtype: 'textfield',
			name: 'cardLevel',
			maxLength: 1,
			fieldLabel: '卡片等级',
			allowBlank: false,
			regex:/^[0-9]{1,2}$/,
			blankText: '卡片等级不能为空',
			emptyText: '请输入卡片等级'
		}/*,{
			xtype: 'textarea',
			name: 'remarkAdd',
			maxLength:100,
			fieldLabel: '申请备注',
			width:200
		}*/
		]
	});
	
	
	
	// 卡黑名单添加窗口
	var cardRiskWin = new Ext.Window({
		title: '添加中石油卡黑名单',
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
		items: [cardRiskForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(cardRiskForm.getForm().isValid()) {
					cardRiskForm.getForm().submit({
						url: 'T40601Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cardRiskWin);
							//重新加载参数列表
							grid.getStore().reload();
							cardRiskForm.getForm().reset();
							cardRiskWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cardRiskWin);
						},
						params: {
							txnId: '40201',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cardRiskForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cardRiskWin.hide();
			}
		}]
	});
	
	
	//黑名单删除Form
	var cardRiskFormDel = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		items: [
		{}/*{
			xtype: 'textarea',
		//	id: 'remarkAdd',
			name: 'remarkAdd',
			maxLength:100,
			fieldLabel: '申请备注',
			width:200
		}*/
		]
	});
	
	// 卡黑名单删除窗口
	var cardRiskWinDel = new Ext.Window({
		title: '删除中石油卡黑名单',
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
		items: [cardRiskFormDel],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(cardRiskFormDel.getForm().isValid()) {
					var rec = grid.getSelectionModel().getSelected();
					var customerProfile = rec.get('customerProfile');
					var cardLevel = rec.get('cardLevel');
					cardRiskFormDel.getForm().submit({
						url: 'T40601Action.asp?method=delete',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cardRiskWinDel);
							//重新加载参数列表
							grid.getStore().reload();
							cardRiskFormDel.getForm().reset();
							cardRiskWinDel.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cardRiskWinDel);
						},
						params: {
							customerProfile:customerProfile,
							cardLevel:cardLevel,
							txnId: '40201',
							subTxnId: '03'
						}
					});
				
				}
			}
			
		},/*{
			text: '重置',
			handler: function() {
				cardRiskFormDel.getForm().reset();
			}
		},*/{
			text: '关闭',
			handler: function() {
				cardRiskWinDel.hide();
			}
		}]
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 150,
		autoHeight: true,
		items: [
		/*{
			xtype: 'textfield',
			id: 'srCardNo',
			name: 'srCardNo',
			maxLength: 19,
			regex: /^[0-9]{1,19}$/,
			regexText: '卡号只能是数字',
			fieldLabel: '卡号'
		}*/
		{ 
                xtype: 'combo',
                fieldLabel: '状态',
                id: 'saState',
                name: 'saState',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','正常'],['1','新增待审核'],['2','修改带审核'],['3','删除待审核'],['4','修改审核拒绝'],['5','新增审核拒绝'],['6','删除审核拒绝'],['7','已删除']]
                })
        }
		/*,{
			xtype: 'textfield',
			id: 'srBrhNo',
			name: 'srBrhNo',
			vtype: 'alphanum',
			fieldLabel: '分公司号'
		}*/
		,{}]
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
				cardRiskStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	cardRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			saState: queryForm.findById('saState').getValue()
			//srBrhNo: queryForm.findById('srBrhNo').getValue()
			//srAction: queryForm.findById('srActionId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	/*var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO','*',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});*/
	//文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T10111ImpAction.asp?method=upload',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.csv;*.CSV',
		fileTypesDescription : '文件(*.csv;*.CSV)',
		title: '机构费率信息上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
		},
		postParams: {
			txnId: '10111',
			subTxnId: '02'
		}
	});
	
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agenfeequery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'feeid'
		},[
			{name: 'agenid',mapping: 'agenid'},
			{name: 'feeid',mapping: 'feeid'},
			{name: 'termid',mapping: 'termid'},
			{name: 'mtchno',mapping: 'mtchno'},
			{name: 'mcccode',mapping: 'mcccode'},
			{name: 'tradeeacceptreg',mapping: 'tradeeacceptreg'},
			{name: 'statue',mapping:'statue'}
		]),
		autoLoad: true
	});

	function mchntRen(val){
	    if(val == ''){
	    	return '';	    	
	    }	    	
	    else{
		   return getRemoteTrans(val, "mchtIdName2");
	    }
		
	}
	var mchntColModel = new Ext.grid.ColumnModel([
	    {header: '机构号',dataIndex: 'agenid',width: 190,renderer:function(val){return getRemoteTrans(val, "agenNm");}},
	    {id: 'feeid',header: '费率编号',dataIndex: 'feeid',width: 120},
		{header: '终端号',dataIndex: 'termid',width: 120,renderer:all},
		//{header: '商户编号',dataIndex: 'mtchno',width: 240, renderer:function(val){return getRemoteTrans(val, "mchtIdName2");}},
		{header: '商户编号',dataIndex: 'mtchno',width: 240,renderer:mchntRen},
		{header: 'MCC码',dataIndex: 'mcccode',width: 200, renderer:function(val){return getRemoteTrans(val, "mcc2");}},
		{header: '交易受理地区',dataIndex: 'tradeeacceptreg',width: 180, renderer:function(val){return getRemoteTrans(val, "city");}},
		{header: '审核状态',dataIndex: 'statue',width: 120}
	]);
	function all(val){
		if(val=='*'){
			return '全部支持'; 
		}else{
			return val;
		}
	}
	var delMenu = {
		text: '注销',
		width: 85,
		iconCls: 'recycle',
		disabled: true,
		handler:function() {
			if (mchntGrid.getSelectionModel().hasSelection()) {
				var rec = mchntGrid.getSelectionModel().getSelected();
				if(rec.get('statue') == '修改未审核'){
					showAlertMsg("修改未审核状态的机构费率,请勿注销！",grid);
					return;
				}
				if(rec.get('statue')=='注销未审核'){
					showAlertMsg("注销未审核状态的机构费率,请勿注销！",grid);
					return;
				}
				if(rec.get('statue')=='注销'){
					showAlertMsg("注销状态的机构费率,请勿注销！",grid);
					return;
				}
				showConfirm('您确定注销机构费率信息吗？', mchntGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T10110Action.asp?method=delete',
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, mchntGrid);
									mchntGrid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, mchntGrid);
								}
							},
							params : {
								FEE_ID: mchntGrid.getSelectionModel().getSelected().get('feeid'),
								txnId: '10111',
								subTxnId: '01'
							}
						});
					}
				});
			}
		}
		};
	
	var childWin;
		
	var detailMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function(bt) {
			if (mchntGrid.getSelectionModel().hasSelection()) {
				var rec = mchntGrid.getSelectionModel().getSelected();
				if(rec.get('statue') == '注销'){
					showAlertMsg("注销状态的机构费率不可修改！",grid);
					return;
				}
				if(rec.get('statue')=='注销未审核'){
					showAlertMsg("注销未审核状态的机构费率不可修改！",grid);
					return;
				}
			}
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showAgencyFeeDetailS(mchntGrid.getSelectionModel().getSelected().get('feeid'),mchntGrid.getSelectionModel().getSelected().get('agenid'),mchntGrid)
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
	
	var importMenu = {
			text: '导入',
			width: 85,
			id: 'import',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};
	
// 菜单集合
	var menuArr = new Array();
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	menuArr.push('-');//3
    menuArr.push(delMenu);//4
    menuArr.push('-');//3
    menuArr.push(importMenu);//4
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '费率信息维护',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构费率信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.agenid

	});

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
			mchntGrid.getTopToolbar().items.items[0].enable();
			if(rec.get('statue') == '新增未审核'){
				mchntGrid.getTopToolbar().items.items[4].disable();
				}else
			
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
		width: 400,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '审核状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','正常'],['2','修改未审核'],['8','注销未审核'],['7','注销']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '机构编号',
			methodName: 'getAgenID',
			hiddenName: 'agenid',
			editable: true,
			width: 200
		},{
			xtype: 'basecomboselect',
			fieldLabel: 'MCC码类别',
			baseParams: 'MCHNT_GRP_ALL',
		//	id:'idmccGrp',
			hiddenName: 'mccGrp',
			editable: true,
			width: 200
		},{
			//xtype: 'textfield',
			xtype: 'dynamicCombo',
			methodName: 'getMchntMcc',
			hiddenName: 'MCC_CODE1',
			fieldLabel: 'MCC码',
			id: 'mccCode',
			editable: true,
			width: 200
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
				mchntStore.reload();
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
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mccCode:queryForm.findById('mccCode').getValue(),
			mccGrp: queryForm.getForm().findField('mccGrp').getValue()
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
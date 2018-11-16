Ext.onReady(function() {
	/*var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC','*',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});*/
	var sm = new Ext.grid.CheckboxSelectionModel();
	//机构数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agenfeeshenquery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'feeid'
		},[	
			{name: 'agenid',mapping: 'agenid'},
			{name: 'feeid',mapping: 'feeid'},
			{name: 'termid',mapping: 'termid'},
			{name: 'mtchno',mapping: 'mtchno'},
			{name: 'mcccode',mapping: 'mcccode'},
			{name: 'tradeeacceptreg',mapping: 'tradeeacceptreg'},
			{name: 'statue',mapping: 'statue'}
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
	    sm,
		{header: '机构号',dataIndex: 'agenid',width: 190,renderer:function(val){return getRemoteTrans(val, "agenNm");}},
	    {id: 'feeid',header: '费率编号',dataIndex: 'feeid',width: 120,sortable: true},
		{header: '终端号',dataIndex: 'termid',width: 120,renderer:all},
		//{header: '商户编号',dataIndex: 'mtchno',width: 240, renderer:function(val){return getRemoteTrans(val, "mchtIdName2");}},
		{header: '商户编号',dataIndex: 'mtchno',width: 240,renderer:mchntRen},
		{header: 'MCC码',dataIndex: 'mcccode',width: 200, renderer:function(val){return getRemoteTrans(val, "mcc2");}},
		{header: '交易受理地区',dataIndex: 'tradeeacceptreg',width: 200, renderer:function(val){return getRemoteTrans(val, "city");}},
		{header: '审核状态',dataIndex: 'statue',width: 100}
	]);
	
	function all(val){
		if(val=='*'){
			return '全部支持'; 
		} else{
			return val;
		}
	}
		
	// 菜单集合
	var menuArr = new Array();
	
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showAgencyFeeDetailS(mchntGrid.getSelectionModel().getSelected().get('feeid'),
					mchntGrid.getSelectionModel().getSelected().get('agenid'),mchntGrid)
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
	//通过
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
				
				var selectRecords = mchntGrid.getSelectionModel().getSelections();
				if(selectRecords.length == 0){
					return;
				}
				var array = new Array();
				for(i=0;i<selectRecords.length;i++){
					var record = selectRecords[i];
					var data = {
					   feeid:record.get('feeid')							
					};
					
					array.push(data);
				}
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');					
					Ext.Ajax.request({
						url: 'T10112Action.asp?method=accept',
						params: {
						//	id: rec.get('feeid'),
							idList:Ext.encode(array),
							txnId: '10112',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,mchntGrid);
							} else {
								showErrorMsg(rspObj.msg,mchntGrid);
							}
							// 重新加载待审核信息
						mchntGrid.getStore().reload();
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
			showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
//			if (mchntGrid.getSelectionModel().hasSelection()) {
//				var rec = mchntGrid.getSelectionModel().getSelected();
//	
//				showConfirm('您确认审核拒绝吗？', mchntGrid, function(bt) {
//					// 如果点击了提示的确定按钮
//					if (bt == "yes") {
//						Ext.Ajax.requestNeedAuthorise( {
//							url : 'T10112Action.asp?method=refuse',
//							success : function(rsp, opt) {
//								var rspObj = Ext.decode(rsp.responseText);
//								if (rspObj.success) {
//									showSuccessMsg(rspObj.msg, mchntGrid);
//									mchntGrid.getStore().reload();
//								} else {
//									showErrorMsg(rspObj.msg, mchntGrid);
//								}
//							},
//							params : {
//								id: rec.get('feeid'),
//								txnId: '10112',
//								subTxnId: '03'
//							}
//						});
//					}
//				});
//			}
		}
	};
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		var selectRecords = mchntGrid.getSelectionModel().getSelections();
		if(selectRecords.length == 0){
			return;
		}
		var array = new Array();
		for(i=0;i<selectRecords.length;i++){
			var record = selectRecords[i];
			var data = {
			   feeid:record.get('feeid')							
			};
			
			array.push(data);
		}
		
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = mchntGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T10112Action.asp?method=refuse',
				params: {
				 //   id: rec.get('feeid'),
					idList:Ext.encode(array),
					txnId: '10112',
					subTxnId: '03',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载待审核信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	menuArr.push(detailMenu);      //[0]
	menuArr.push('-');             //[1]
	menuArr.push(queryCondition);  //[2]
	menuArr.push('-');             //[3]
	menuArr.push(acceptMenu);      //[4]
	menuArr.push('-');             //[5]
    menuArr.push(refuseMenu);      //[6]
	

	var mchntGrid = new Ext.grid.GridPanel({
		title: '机构费率信息审核',
		region: 'center',
		iconCls: 'T10108',
		frame: true,
		border: true,
		columnLines: true,
		//autoExpandColumn: 'agenname',
		stripeRows: true,
		store: mchntStore,
	//	sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm:sm,
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
		mchntGrid.getTopToolbar().items.items[6].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[4].enable();
			mchntGrid.getTopToolbar().items.items[6].enable();
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
				data: [['0','新增未审核'],['2','修改未审核'],['8','注销未审核']],
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
				mchntStore.load();
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
			mccGrp:queryForm.getForm().findField('mccGrp').getValue() 
		});
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
});
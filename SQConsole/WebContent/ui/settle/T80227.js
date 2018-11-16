Ext.onReady(function() {
	
	var sm = new Ext.grid.CheckboxSelectionModel({
		  
                singleSelect : false,  
                dataIndex:"id",    
                listeners:{'beforerowselect': function( sumColModel, rowIndex, keepExisting,record ) {    
                if((record.data.saStatus)=='1'){ 
                return false; //不能进行选择    
                }else{    
                return true;    
                }    
                }}    
            
	});
	
	var sumGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=sumrzInfUp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'seqNo'
		},[
			{name: 'seqNo',mapping: 'seqNo'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'accFlag',mapping: 'accFlag'},
			{name: 'settleAccName',mapping: 'settleAccName'},
			{name: 'settleAccNum',mapping: 'settleAccNum'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'handAmt',mapping: 'handAmt'},
			{name: 'sumAmt',mapping: 'sumAmt'},
			{name: 'saStatus',mapping: 'saStatus'},
			{name: 'sumrzDate',mapping: 'sumrzDate'},
			{name: 'sumrzNote',mapping: 'sumrzNote'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recUpdTs',mapping: 'recUpdTs'}
		])
	});
	
	sumGridStore.load({
		params:{start: 0}
	});
	
	var sumColModel = new Ext.grid.ColumnModel([
		sm,
		{header: '主键',dataIndex: 'seqNo',hidden:true},
		{header: '商户号',dataIndex: 'mchtNm',width:200,align: 'center'},
		{header: '账户类型',dataIndex: 'accFlag',renderer: accFlagVal,align: 'center'},
		{header: '结算账户',dataIndex: 'settleAccName',align: 'center'},
		{header: '结算账号',dataIndex: 'settleAccNum',align: 'center'},
		{header: '开户行',dataIndex: 'bankName',align: 'center'},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center'},
		{header: '手续费',dataIndex: 'handAmt',align: 'center'},
		{header: '结算金额',dataIndex: 'sumAmt',align: 'center'},
		{header: '状态',dataIndex: 'saStatus',renderer: saStateVal,align: 'center'},
		{header: '划款日期',dataIndex: 'sumrzDate',width: 100,renderer: formatDt,align: 'center'},
		{header: '备注',dataIndex: 'sumrzNote',align: 'center',id:'sumrzNoteId'}
	]);
	
	function accFlagVal(val){
		if(val=='1'){
			return '对私账户';
		}
		if(val=='2'){
			return '对公账户';
		}
	}
	
	function saStateVal(val){
		if(val=='0'){
			return '<font color="red">失败</font>';
		}
		if(val=='1'){
			return '<font color="green">成功</font>';
		}
	}
	
	/****************成功回填*********************/
	var sucAddForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '划款日期*',
			allowBlank: false,
			emptyText:'格式：yyyymmdd',
			id:'sumrzDateId',
			name: 'sumrzDateId',
			width: 120
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(sucAddForm.getForm().isValid()) {
					
					var recs = sumGrid.getSelectionModel().getSelections();
					var array = new Array();
					for(var index = 0; index < recs.length; index++) {
						var record = recs[index];
						var data = {
								seqNo: record.get('seqNo')
						};
						
						array.push(data);
					}
                   
					sucAddForm.getForm().submit({
						url: 'T80224Action_sucAdd.asp',
						waitMsg: '正在回填商户划款表信息，请稍候......',
						params: {
							infList: Ext.encode(array),
							sumrzDate:typeof(Ext.getCmp('sumrzDateId').getValue()) == 'string' ? '' : Ext.getCmp('sumrzDateId').getValue().dateFormat('Ymd'),
							txnId: '80224',
							subTxnId: '01'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,sucAddForm);
							sucAddWin.hide();
							sumGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,sucAddForm);
						}
					});
				}
			}
		}, {
			text: '重填',
			handler: function() {
				sucAddForm.getForm().reset();			
			}
		}]
	});
	
	
	var sucAddWin = new Ext.Window({
		title : '划款成功',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 300,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ sucAddForm ]
	});
    /****************end******************/
	
	/****************失败回填******************/
	var falseAddForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '索引号',
			width:150,
			hideLabel :true,
			hidden : true,
			id: 'seqNo'
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '划款日期*',
			allowBlank: false,
			emptyText:'格式：yyyymmdd',
			id:'sumrzDate',
			name: 'sumrzDate',
			width: 120
		},{
	       xtype: 'textarea',
		   labelStyle: 'padding-left: 5px',
			fieldLabel: '备注*',
			allowBlank: false,
			id: 'sumrzNote',
			name: 'sumrzNote',
			maxLength: 200,
			width:120
		  }],
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(falseAddForm.getForm().isValid()) {										
                   
					falseAddForm.getForm().submit({
						url: 'T80224Action_falseAdd.asp',
						waitMsg: '正在回填商户划款表信息，请稍候......',
						params: {
							txnId: '80224',
							subTxnId: '02'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,falseAddForm);
							falseAddWin.hide();
							sumGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,falseAddForm);
						}
					});
				}
			}
		}, {
			text: '重填',
			handler: function() {
			    var falseRecords = sumGrid.getSelectionModel().getSelections();
				falseAddForm.getForm().reset();
				falseAddForm.getForm().loadRecord(falseRecords[0]);
				falseAddForm.getForm().clearInvalid();			
			}
		}]
	});
	
	
	var falseAddWin = new Ext.Window({
		title : '划款失败回填',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 300,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ falseAddForm ]
	});
	
	 /****************end******************/
	
	// 菜单集合
	var menuArr = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
		
		var sucMenu = {
			text: '商户划款',
			width: 85,
			iconCls: 'edit',
//			disabled: true,
			handler: function() {
			var sucRecords = sumGrid.getSelectionModel().getSelections();
				if(sucRecords.length == 0) {
					return;
				}else{
					sucAddForm.getForm().reset();
					sucAddWin.show();
				}
			}
		};
		var falseMenu = {
			text: '划款失败',
			width: 85,
			iconCls: 'edit',
//			disabled: true,
			handler: function() {
			var falseRecords = sumGrid.getSelectionModel().getSelections();
				if(falseRecords.length == 0) {
					return;
				}else if(falseRecords.length > 1){
					showAlertMsg("只能选择单条信息进行失败回填！",sumGrid);
					return;
				}
				else{					
					falseAddForm.getForm().reset();
					falseAddForm.getForm().loadRecord(falseRecords[0]);
					falseAddForm.getForm().clearInvalid();
					falseAddWin.show();
				}
			}
		};
		var falseMenu1 = {
			text: '批量回填',
			width: 85,
			iconCls: 'edit',
//			disabled: true,
			handler: function() {
			var falseRecords = sumGrid.getSelectionModel().getSelections();
				if(falseRecords.length == 0) {
					return;
				}else if(falseRecords.length > 1){
					showAlertMsg("只能选择单条信息进行失败回填！",sumGrid);
					return;
				}
				else{					
					falseAddForm.getForm().reset();
					falseAddForm.getForm().loadRecord(falseRecords[0]);
					falseAddForm.getForm().clearInvalid();
					falseAddWin.show();
				}
			}
		};
		
		menuArr.push(queryCondition);  //[1]
		menuArr.push('-');
		menuArr.push(sucMenu); 
		menuArr.push('-');
		menuArr.push(falseMenu);
		menuArr.push('-');
		menuArr.push(falseMenu1);
		
		
		// 信息列表
	var sumGrid = new Ext.grid.EditorGridPanel({
		title: '银企直连商户回填',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'sumrzNoteId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sumGridStore,
		sm: sm,
		cm: sumColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sumGridStore,
		//	pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/***************************查询条件*************************/
	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 250
		},{
			xtype: 'datefield',
			id: 'instDateId',
			name: 'instDateId',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '清算日期',
			width:150,
			editable: false			
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idAccFlag',
			fieldLabel: '账户类型',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','对私账户'],['2','对公账户']],
				reader: new Ext.data.ArrayReader()
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
				sumGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	sumGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			instDate: typeof(queryForm.findById('instDateId').getValue()) == 'string' ? '' : queryForm.findById('instDateId').getValue().dateFormat('Ymd'),
			accFlag: queryForm.getForm().findField('idAccFlag').getValue(),
			mchtNo: queryForm.getForm().findField('mchtNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [sumGrid],
		renderTo: Ext.getBody()
	});
});
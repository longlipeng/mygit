 
Ext.onReady(function(){
	
 var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=changeAccInfTmpShen',
			timeout: 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'changeAccount',mapping: 'changeAccount'},
			{name: 'changeReason',mapping: 'changeReason'},
			{name: 'changeFlag',mapping: 'changeFlag'},
			{name: 'confirmAccount',mapping: 'confirmAccount'},
			{name: 'changeDate',mapping: 'changeDate'},
			{name: 'insTs',mapping: 'insTs'},
			{name: 'insOpr',mapping: 'insOpr'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'aprTs',mapping: 'aprTs'},
			{name: 'aprOpr',mapping: 'aprOpr'},
			{name: 'st',mapping: 'st'},
			{name: 'enterTp',mapping: 'enterTp'}
			
		]),
		autoLoad: true
	});
 
//	manuStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			saState:'0' 
//		});
//	})
    var sm = new Ext.grid.CheckboxSelectionModel();
 
	var manuColModel = new Ext.grid.ColumnModel([
  //   		new Ext.grid.RowNumberer(),
            sm,
     		{header: '商户编号',dataIndex: 'mchtNo',width: 200, renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
     		{header: '机构号',dataIndex: 'instCode',width: 190,renderer:agenNm},
     		{header: '终端号',dataIndex: 'termId',width: 100},
     		{header: '调账金额',dataIndex: 'changeAccount',width: 150},	 
     		{header: '调账理由',dataIndex: 'changeReason',width: 100},
     		{header: '调账状态',dataIndex: 'changeFlag',width: 100,renderer:changeflag},
     		{header: '录入时间',dataIndex:'changeDate'},/*{	
    			header : '创建时间',
    			dataIndex : 'insTs',
    			width : 120
    		},*/{
    			header : '创建人',
    			dataIndex : 'insOpr',
    			width : 80
    		},{	
    			header : '修改时间',
    			dataIndex : 'updTs',
    			width : 120
    		},{
    			header : '修改人',
    			dataIndex : 'updOpr',
    			width : 80
    		},{
    			header : '审核时间',
    			dataIndex : 'aprTs',
    			width : 120
    		},{
    			header : '审核人',
    			dataIndex : 'aprOpr',
    			width : 80
    		},{
    			header : '状态',
    			dataIndex : 'st',
    			width : 80,
    			renderer:amtState
    		},{
    			header : '录入方式',
    			dataIndex : 'enterTp',
    			width : 80,
    			renderer:enterTpC
    		}
	                                    	                                     		
	                                    	         ]);
 
	function agenNm(val){
		if(val == ''){
			return '';
		}
		else{
		   return getRemoteTrans(val, "agenNm");
		}
		
	}
	function changeflag(val){

		if(val=='0'){
			return '未调账'; 
		} 
		else if(val=='1'){
			return "已完成";
		}
	
	}
	
	
	function enterTpC(val){
		if(val=='0'){
			return "自动录入";
		}else if(val=='1'){
			return "手动录入";
		}else{
			return "其他";
		}
	}
	
	function amtState(val){
		if(val=='0'){
			return '正常'; 
		} 
		else if(val=='1'){
			return "新增待审核";
		}else if(val=='2'){
			return "修改待审核";
		}else if(val=='3'){
			return "删除待审核";
		}else{
			return "审核拒绝";	
		}
	}
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',manuGrid,function(bt) {
				var modifiedRecords = manuGrid.getSelectionModel().getSelections();
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
							mchtNo: record.get('mchtNo'),
							termId: record.get('termId'),
							changeDate: record.get('changeDate')
					};
					
					array.push(data);
				}
				
				if(bt == 'yes') {
					//showProcessMsg('正在提交审核信息，请稍后......');
					showMask("正在提交审核信息，请稍后....",manuGrid);
					rec = manuGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T100201Action.asp?method=accept',
						params: {
					    changeList: Ext.encode(array),
						txnId: '100202',
						subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,manuGrid);
							} else {
								showErrorMsg(rspObj.msg,manuGrid);
							}
							// 重新加载商户待审核信息
							manuGrid.getStore().reload();
						},
						failure: function(){
							hideMask();
						}
					});
					
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',manuGrid,function(bt) {
				
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		var modifiedRecords = manuGrid.getSelectionModel().getSelections();
		var array = new Array();
		for(var index = 0; index < modifiedRecords.length; index++) {
			var record = modifiedRecords[index];
			var data = {
					mchtNo: record.get('mchtNo'),
					termId: record.get('termId'),
					changeDate: record.get('changeDate')
			};
			
			array.push(data);
		}
		
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showMask("正在提交审核信息，请稍后....",manuGrid);
			rec = manuGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T100201Action.asp?method=refuse',
				params: {
			    changeList: Ext.encode(array),
				txnId: '100202',
				subTxnId: '02',
				refuseInfo: text
				},
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,manuGrid);
					} else {
						showErrorMsg(rspObj.msg,manuGrid);
					}
					manuGrid.getStore().reload();
				},
				failure: function(){
					hideMask();
				}
			});
			//hideProcessMsg();
		}
	}
	
	queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
	/** *************************查询条件************************ */

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 600,
		autoHeight : true,
		items : [/*{
			xtype: 'textfield',
			name: 'mchtNoQ',
			id: 'mchtNoQ',
			fieldLabel: '商户号',
			blankText: '请输入商户号',
			regex: /^[0-9]{1,15}$/,
			regexText: '只能输入数字',
			maxLength:15,
			minLength:15,
			blankText: '请输入15位数字'
			
		},*//*{
            xtype: 'dynamicCombo',
            fieldLabel: '商户编号*',
            methodName: 'getMchntIdAll',
            hiddenName: 'mchtNo',
            id: 'idmchtNoQ',
            displayField: 'displayField',
            valueField: 'valueField',
            width: 300
       },{
			xtype: 'textfield',
			name: 'termIdQ',
			id: 'termIdQ',
			fieldLabel: '终端号',
			blankText: '请输入终端号',
			maxLength:8,
			regex: /^[0-9]{1,8}$/,
			regexText: '只能输入数字',
			maxLength:8,
			minLength:8,
			blankText: '请输入8位数字'
			
		},*/{
            xtype: 'dynamicCombo',
            fieldLabel: '商户编号',
            //methodName: 'getMchntIdTerm',
            methodName: 'getMchntId',
            hiddenName: 'mchtNo',
            id: 'idmchtNoQ',
            displayField: 'displayField',
            valueField: 'valueField',
            width: 300,
			callFunction: function() {
					Ext.getCmp('termIdQ').reset();
					Ext.getCmp('termIdQ').parentP=this.value;
					Ext.getCmp('termIdQ').getStore().reload();
				
			}
       },{
           xtype: 'dynamicCombo',
           fieldLabel: '机构号',
           methodName: 'getAgencyInf1',
           hiddenName: 'instCode',
           id: 'instCodeQ',
           displayField: 'displayField',
           valueField: 'valueField',
           width: 300
      },{
           xtype: 'dynamicCombo',
           fieldLabel: '终端号',
           methodName: 'getTermIdAll',
           hiddenName: 'termNo',
           id: 'termIdQ',
           displayField: 'displayField',
           valueField: 'valueField',
           width: 300
      },{
			xtype: 'datefield',
			name: 'changeDateQ',
			id: 'changeDateQ',
			fieldLabel: '录入时间'
		},{
			xtype: 'datefield',
			name: 'uptTsQ',
			id: 'uptTsQ',
			fieldLabel: '修改时间'
		},{
			xtype: 'basecomboselect',
			//labelStyle: 'padding-left: 5px',
			id: 'enterTpQ',
			fieldLabel: '录入方式',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','自动录入'],['1','手动录入']],
				reader: new Ext.data.ArrayReader()
			})
		}/*,{
			xtype: 'basecomboselect',
			//labelStyle: 'padding-left: 5px',
			id: 'changeFlagQ',
			fieldLabel: '是否调账完成',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','未调账'],['1','已完成']],
				reader: new Ext.data.ArrayReader()
			})
		}*/,{
			xtype: 'panel',
			html: '</br></br>'
	}]
	});
	
	
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 600,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10, Ext.getBody().getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				} ],
		buttons : [ {
			text : '查询',
			handler : function() {
			if(queryForm.getForm().isValid()){
        		manuStore.load();
        	}
		}
			
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			
			mchtNo: queryForm.getForm().findField('idmchtNoQ').getValue(),
            termId: queryForm.getForm().findField('termIdQ').getValue(),
            enterTpQ: queryForm.getForm().findField('enterTpQ').getValue(),
            changeFlag: '0',
            changeDate: typeof(queryForm.findById('changeDateQ').getValue()) == 'string' ? '' : queryForm.findById('changeDateQ').getValue().dateFormat('Ymd'),
            uptTsQ: typeof(queryForm.findById('uptTsQ').getValue()) == 'string' ? '' : queryForm.findById('uptTsQ').getValue().dateFormat('Ymd'),
            stQ:'0',
            instCode: queryForm.getForm().findField('instCodeQ').getValue()		
		});
	});
	
	
	
	var menuArr = new Array();
	menuArr.push(acceptMenu);
	menuArr.push('-');
	menuArr.push(refuseMenu);
	menuArr.push("-");
	menuArr.push(queryConditionMebu);

	
	manuGrid = new Ext.grid.EditorGridPanel({
		title: '商户调账信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		tbar: menuArr,
		cm: manuColModel,
	//	sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm:sm,
		clicksToEdit: true,
		forceValidation: true,
		bbar: new Ext.PagingToolbar({
			store: manuStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将按钮屏蔽
	manuGrid.getStore().on('beforeload',function() {
		manuGrid.getTopToolbar().items.items[0].disable();
		manuGrid.getTopToolbar().items.items[2].disable();
		//manuGrid.getStore().rejectChanges();
	});
	
	manuGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(manuGrid.getView().getRow(manuGrid.getSelectionModel().last)).frame();
		manuGrid.getTopToolbar().items.items[0].enable();
		manuGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
	
})
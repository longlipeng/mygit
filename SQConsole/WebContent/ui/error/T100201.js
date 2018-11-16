Ext.onReady(function(){
	
	var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=changeAccInfTmpWeih',
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
	
	
	
	var manuColModel = new Ext.grid.ColumnModel([
 		new Ext.grid.RowNumberer(),
 		{header: '商户编号',dataIndex: 'mchtNo',width: 200, renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
 		{header: '机构号',dataIndex: 'instCode',width: 190,renderer:agenNm},
 		{header: '终端号',dataIndex: 'termId',width: 100},
 		{header: '调账金额',dataIndex: 'changeAccount',width: 150,
 			editor: new Ext.form.TextField({
      		 	
      			allowBlank: false,
      			vtype: 'isOverMax',
      			maxLength: 16,
      			regex: /^\-{0,1}[0-9]|\.{1}[0-9]{2}$/,
    			regexText: '该输入框只能输入数字'
      		 })},	 
 		{header: '调账理由',dataIndex: 'changeReason',width: 100,
 			editor: new Ext.form.TextField({
  			allowBlank: false,
  			vtype: 'isOverMax',
  			maxLength: 200

  		 })},
 		{header: '调账状态',dataIndex: 'changeFlag',width: 100,renderer:changeflag},
// 		{header: '已完成金钱',dataIndex:'confirmAccount'},
 		{header: '录入时间',dataIndex:'changeDate'}/*,{	
			header : '创建时间',
			dataIndex : 'insTs',
			width : 120
		}*/,{
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
	function changeflag(val){

		if(val=='0'){
			return '未调账'; 
		} 
		else if(val=='1'){
			return "已完成";
		}
	
	}
	function agenNm(val){
		if(val == ''){
			return '';
		}
		else{
		   return getRemoteTrans(val, "agenNm");
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
			return '调账成功'; 
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
	
	
	
	var changeroutInfoForm = new Ext.form.FormPanel( {// 添加表单
		frame : true,
		autoHeight : true,
		width : 600,
		labelWidth : 90,
		defaultType : 'textfield',
		waitMsgTarget : true,
		items : [/*{
			fieldLabel : '商户号*',
			emptyText : '请输入商户号',
			allowBlank : false,
			id : 'mchtNo',
			name : 'mchtNo',
			width : 200,
			maxLength : 15
		},*//*{
            xtype: 'dynamicCombo',
            fieldLabel: '商户编号*',
            methodName: 'getMchntIdAll',
            hiddenName: 'mchtNo',
            id: 'idmchtNo',
            displayField: 'displayField',
            valueField: 'valueField',
            width: 300
       },{
			fieldLabel : '终端号*',
			emptyText : '请输入终端号',
			allowBlank : false,
			id : 'termId',
			name : 'termId',
			width : 200,
			maxLength : 8
		},*/
         {
                xtype: 'dynamicCombo',
                fieldLabel: '商户编号*',
                methodName: 'getMchntId',
                hiddenName: 'mchtNo',
                allowBlank: false,
                emptyText:'请输入商户编号',
                id: 'idmchtNo',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 300,
				listeners: {
					'select': function() {
						Ext.getCmp('idtermId').reset();
						Ext.getCmp('idtermId').parentP=this.value;
						Ext.getCmp('idtermId').getStore().reload();
					}
				}
           },{
               xtype: 'dynamicCombo',
               fieldLabel: '机构号*',
               methodName: 'getAgencyInf1',
               hiddenName: 'instCode',
               id: 'instCodeId',
               displayField: 'displayField',
               valueField: 'valueField',
               allowBlank: false,
               emptyText:'请输入机构号',
               width: 300
          },{
               xtype: 'dynamicCombo',
               fieldLabel: '终端号*',
               methodName: 'getTermIdAll',
               hiddenName: 'termId',
               id: 'idtermId',
               displayField: 'displayField',
               valueField: 'valueField',
               allowBlank: false,
               emptyText:'请输入终端号',
               width: 300
          },{
			xtype: 'textarea',
			fieldLabel : '调账理由*',
			emptyText : '请输入调账原因',
			allowBlank : false,
			id : 'changeReason',
			name : 'changeReason',
			width : 200
		},{
			fieldLabel : '调账金额*',
			emptyText : '请输入调账金额',
			allowBlank : false,
			xtype: 'textfield',
			value:'0.00',
			decimalPrecision: 2,
			id : 'changeAccount',
			name : 'changeAccount',
			regex:/^\-{0,1}[0-9]+\.{1}[0-9]{2}$/,
			regexText:'请输入两位小数',
			maxLength:16,
			width : 200
			
			
		}]
	});
	
	// 添加窗口
	var changeWin = new Ext.Window( {
			title : '信息添加',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 600,
			autoHeight : true,
			layout : 'fit',
			items : [ changeroutInfoForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
					text : '确定',
					handler : function() {
						if (changeroutInfoForm.getForm().isValid()) {
							changeroutInfoForm.getForm().submit({
								url : 'T100201Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									showSuccessMsg(action.result.msg, changeroutInfoForm);
									// 重置表单
									changeroutInfoForm.getForm().reset();
									changeWin.hide();
									// 重新加载列表
									manuGrid.getStore().reload();
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg,changeroutInfoForm);
								},
								params : {
									txnId : '100201',
									subTxnId : '01'
								}
							});
						}
					}
					}, {
						text : '重置',
						handler : function() {
							changeroutInfoForm.getForm().reset();
						}
					}, {
						text : '关闭',
						handler : function() {
							changeWin.hide(manuGrid);
						}
					} ]
			});
	
	// 添加
	var addMenu = {
		text : '添加',
		width : 85,
		iconCls : 'add',
		handler : function() {
			changeWin.show();
			changeWin.center();
		}
	};
	
	// 删除
	var delMenu = {
		text : '删除',
		width : 85,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			if (manuGrid.getSelectionModel().hasSelection()) {
				var rec = manuGrid.getSelectionModel().getSelected();
//				var array = new Array();
//				var data ={
//						mchtNo : rec.get('mchtNo'),
//						termId : rec.get('termId'),
//						changeDate : rec.get('changeDate'),
//				}
//				array.push(data); 
					showConfirm('确定要删除该条记录吗？', manuGrid, function(bt) {
					// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.requestNeedAuthorise( {
								url : 'T100201Action.asp?method=deleteupdate',

								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, manuGrid);
										manuGrid.getStore().reload();
										manuGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg, manuGrid);
									}
									
								},
								params : {
									mchtNo : rec.get('mchtNo'),
									termId : rec.get('termId'),
									changeDate : rec.get('changeDate'),
//									amtList: Ext.encode(array),
									txnId : '100201',
									subTxnId : '02'
								}
							});
						}
					});
			}
		}
	};
	
	var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = manuGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
				var data = {
						mchtNo : record.get('mchtNo'),
						termId: record.get('termId'),
						changeDate: record.get('changeDate'),
						changeReason: record.get('changeReason'),
						changeAccount: record.get('changeAccount')
						
				};
          		
				array.push(data); 
				}
				Ext.Ajax.request({
					url: 'T100201Action.asp?method=updateone',
					method: 'post',
					params: {
					amtList: Ext.encode(array),
						txnId: '100201',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							manuGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,manuGrid);
							manuGrid.getStore().reload();
						} else {
							manuGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,manuGrid);
						}
						manuGrid.getTopToolbar().items.items[4].disable();
						manuGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
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
			
		},*/
     {
            xtype: 'dynamicCombo',
            fieldLabel: '商户编号',
            //methodName: 'getMchntIdTerm',
            methodName: 'getMchntId',
            hiddenName: 'mchtNo',
            id: 'idmchtNoQ',
            displayField: 'displayField',
            valueField: 'valueField',
            parentP:'',
            width: 300,
            callFunction:  function() {
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
			name: 'aprTsQ',
			id: 'aprTsQ',
			fieldLabel: '审核时间'
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
            aprTsQ: typeof(queryForm.findById('aprTsQ').getValue()) == 'string' ? '' : queryForm.findById('aprTsQ').getValue().dateFormat('Ymd'),
            stQ:'0',
            instCode: queryForm.getForm().findField('instCodeQ').getValue()
		});
	});
	
	var menuarr = new Array();
	menuarr.push(addMenu);
	menuarr.push("-");
	menuarr.push(delMenu);
	menuarr.push("-");
	menuarr.push(upMenu);
	menuarr.push("-");
	menuarr.push(queryConditionMebu);

	manuGrid = new Ext.grid.EditorGridPanel({
		title: '商户调账信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		tbar: menuarr,
		cm: manuColModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
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
	
	manuGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(manuGrid.getView().getRow(manuGrid.getSelectionModel().last)).frame();
		manuGrid.getTopToolbar().items.items[2].enable();
		manuGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
	
	
});
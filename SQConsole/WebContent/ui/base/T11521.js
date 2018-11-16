Ext.onReady(function(){
	//通道代码数据集
	var routingChannelStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	SelectOptionsDWR.getComboData('ROUTINGCHANNEL',function(ret){
		routingChannelStore.loadData(Ext.decode(ret));
	});
	//MCC数据集
	var instMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCC2',function(ret){
		instMccStore.loadData(Ext.decode(ret));
	});
	var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblAgentFee',
			timeout: 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'uuId',mapping: 'uuId'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'mccCode',mapping: 'mccCode'},
			{name: 'agentNm',mapping: 'agentNm'},
			{name: 'feeMin',mapping: 'feeMin'},
			{name: 'feeMax',mapping: 'feeMax'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'state',mapping: 'state'},
			{name: 'crtPer',mapping: 'crtPer'},
			{name: 'crtDate',mapping: 'crtDate'},
			{name: 'upPer',mapping: 'upPer'},
			{name: 'upDate',mapping: 'upDate'},
			{name: 'extend1',mapping: 'extend1'},//通道
			{name: 'extend2',mapping: 'extend2'},
			{name: 'extend3',mapping: 'extend3'},
			{name: 'extend4',mapping: 'extend4'},
			{name: 'extend5',mapping: 'extend5'}
		]),
		autoLoad: true
	});
	
	function feeType(val){
		if(val == '00'){
			return '按比例';
		}else if (val == '01'){
			return '固定值';
		}
	}
	function instMccStr(val){
		if(instMccStore.query('valueField',val).first()==undefined){
			return val;
		}else{
			return instMccStore.query('valueField',val).first().data.displayField;
		}
	}
	
	var manuColModel = new Ext.grid.ColumnModel([
 		new Ext.grid.RowNumberer(),
 		{header: 'UUID',dataIndex: 'uuId',width: 100,hidden:true},
 		{header: '代理商编号',dataIndex: 'agentNo',width: 100},
 		{header : '通道',dataIndex : 'extend1',width : 150,editor: new Ext.form.ComboBox({

			store: routingChannelStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			hiddenName: 'extend1',
			allowBlank : false,
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		
				}),renderer:agencyInfo},//通道
// 		{header: '名称',dataIndex: 'agentNm',width: 100},
 		{header: '上限',dataIndex: 'feeMax',width: 150,
 			editor: new Ext.form.TextField({
      		 	
      			allowBlank: false,
      			vtype: 'isOverMax',
      			maxLength: 16,
      			regex: /^[0-9\\.]+$/,
    			regexText: '该输入框只能输入数字'
      		 })},
 		{header: '下限 ',dataIndex: 'feeMin',width: 150,
 			editor: new Ext.form.TextField({
      		 	
      			allowBlank: false,
      			vtype: 'isOverMax',
      			maxLength: 15,
      			regex: /^[0-9\\.]+$/,
    			regexText: '该输入框只能输入数字'
      		 })},	 
 		{header: '费率值',dataIndex: 'feeValue',width: 100,
 			editor: new Ext.form.TextField({
      		 	
      			allowBlank: false,
      			vtype: 'isOverMax',
      			maxLength: 15,
      			regex: /^[0-9\\.]+$/,
    			regexText: '该输入框只能输入数字'
      		 })},
 		{header: '费率方式',dataIndex: 'feeType',width: 100,
     			editor : new Ext.form.ComboBox( {
    				store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['00','按比例'],['01','固定值']]
                    }),
    				displayField: 'displayField',
    				valueField: 'valueField',
    				mode: 'local',
    				triggerAction: 'all',
    				hiddenName: 'feeType',
    				allowBlank : false,
    				forceSelection: true,
    				typeAhead: true,
    				selectOnFocus: true
    			}),renderer: feeType},{header: 'MCC',dataIndex:'mccCode',
    	 			editor: new Ext.form.ComboBox({

    					store: instMccStore,
    					displayField: 'displayField',
    					valueField: 'valueField',
    					mode: 'local',
    					triggerAction: 'all',
    					hiddenName: 'mccCode',
    					allowBlank : false,
    					forceSelection: true,
    					typeAhead: true,
    					selectOnFocus: true,
    					editable: true
    				
    	 				}),renderer:instMccStr
    	 			}

 		/*{header: 'MCC',dataIndex:'mccCode',
 			editor: new Ext.form.TextField({
      		 	
      			allowBlank: false,
      			vtype: 'isOverMax',
      			maxLength: 4,
      			minLength: 4,
      			regex: /^[0-9]{1,4}$/,
    			regexText: '该输入框只能输入数字'
      		 })}*/,
      	{	
			header : '创建时间',
			dataIndex : 'crtDate',
			width : 120
		},{
			header : '创建人',
			dataIndex : 'crtPer',
			width : 80
		},{
			header : '审核时间',
			dataIndex : 'upDate',
			width : 120
		},{
			header : '审核人',
			dataIndex : 'upPer',
			width : 80
		},{
			header : '状态',
			dataIndex : 'state',
			width : 80,
			renderer:amtState
		}]);
	function amtState(val){
		if(val=='0'){
			return '新增待审核'; 
		} else if(val=='1'){
			return "已删除";	
		}else if(val=='2'){
			return "正常";
		}
		else if(val=='3'){
			return "修改待审核";
		}else if(val=='4'){
			return "删除待审核";
		}
	}
	var changeroutInfoForm = new Ext.form.FormPanel( {// 添加表单
		frame : true,
		autoHeight : true,
		width : 600,
		labelWidth : 90,
		defaultType : 'textfield',
		waitMsgTarget : true,
		items : [
         {
                xtype: 'dynamicCombo',
                fieldLabel: '代理商编号*',
                methodName: 'getAgentNo',
                hiddenName: 'agentNo',
                allowBlank: false,
                emptyText:'请输入代理商编号',
                id: 'idagentNoQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 300
           },{
				fieldLabel : '通道*',
				allowBlank : false,
				xtype: 'basecomboselect',
				emptyText : '请选择通道',
				store: routingChannelStore,
				id : 'idextend1',
				hiddenName : 'extend1',
				width : 150
			},{
			fieldLabel : '上限*',
			allowBlank : false,
			xtype: 'textfield',
			id : 'feeMax',
			name : 'feeMax',
			maxLength:14,
			width : 200
		},{
			fieldLabel : '下限*',
			allowBlank : false,
			xtype: 'textfield',
			id : 'feeMin',
			name : 'feeMin',
			maxLength:14,
			width : 200	
		},{
			fieldLabel : '费率值*',
			allowBlank : false,
			xtype: 'textfield',
			id : 'feeValue',
			name : 'feeValue',
			maxLength:14,
			width : 200	
		},{
        	xtype: 'basecomboselect',
        	baseParams: 'MECH_RATE_METHOD',
			fieldLabel: '费率方式*',
			allowBlank: false,
			width : 200,
			blankText: '请输入费率方式',
			id: 'idfeeTypeQ',
			hiddenName: 'feeType'
    	},{

   			xtype: 'dynamicCombo',
   			methodName: 'getMchntMcc',
//			labelStyle: 'padding-left: 5px',
			fieldLabel: 'MCC码*',
			id: 'idmccCode',
			hiddenName: 'mccCode',
			//maxLength: 4,
			allowBlank: false,
			width: 300
        	
    	}/*{
			fieldLabel : 'MCC*',
			allowBlank : false,
			xtype: 'textfield',
			id : 'mccCode',
			name : 'mccCode',
			maxLength:4,
			minLength:4,
			width : 200	
		}*/]
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
			items : [changeroutInfoForm],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
					text : '确定',
					handler : function() {
						if (changeroutInfoForm.getForm().isValid()) {
							changeroutInfoForm.getForm().submit({
								url : 'T11521Action.asp?method=add',
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
									txnId : '11521',
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
								url : 'T11521Action.asp?method=delete',

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
									uuId : rec.get('uuId'),
									txnId : '11521',
									subTxnId : '02'
								}
							});
						}
					});
			}
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
		items : [
     {
            xtype: 'dynamicCombo',
            fieldLabel: '代理商编号',
            methodName: 'getAgentNo',
            hiddenName: 'agentNo',
            id: 'idagentNo',
            displayField: 'displayField',
            valueField: 'valueField',
            parentP:'',
            width: 300
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
		},{
          xtype: 'dynamicCombo',
          fieldLabel: '费率值',
          methodName: 'getFeeValue',
          hiddenName: 'feeValue',
          id: 'idfeeValue',
          displayField: 'displayField',
          valueField: 'valueField',
          width: 300
     },{
			xtype: 'basecomboselect',
			id: 'idextend1',
			fieldLabel: '通道',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1702','翰鑫通道'],['1708','上汽通道']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'basecomboselect',
			id: 'idfeeType',
			fieldLabel: '费率方式',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['00','按比例'],['01','固定值']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'basecomboselect',
			fieldLabel: 'MCC码类别',
			baseParams: 'MCHNT_GRP_ALL',
			id:'idmccGrp',
			hiddenName: 'mccGrp',
			editable: true,
			width: 200
		},{
	           xtype: 'dynamicCombo',
	           fieldLabel: 'MCC',
	           methodName: 'getMchntMcc',
	           hiddenName: 'mccCode',
	           id: 'idmccCode',
	           displayField: 'displayField',
	           valueField: 'valueField',
	           width: 300
	      },{
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
			
			agentNo: queryForm.getForm().findField('idagentNo').getValue(),
			mccCode: queryForm.getForm().findField('idmccCode').getValue(),
			/*feeMin: queryForm.getForm().findField('idfeeMin').getValue(),
            feeMax: queryForm.getForm().findField('idfeeMax').getValue(),*/
			mccGrp: queryForm.getForm().findField('idmccGrp').getValue(),
			extend1: queryForm.getForm().findField('idextend1').getValue(),
            feeValue: queryForm.getForm().findField('idfeeValue').getValue(),
            feeType: queryForm.getForm().findField('idfeeType').getValue(),
            state_query:queryForm.findById('idstate').getValue()
		});
	});
	
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
						uuId: record.get('uuId'),
						feeMax : record.get('feeMax'),
						feeMin: record.get('feeMin'),
						feeValue: record.get('feeValue'),
						feeType: record.get('feeType'),
						mccCode: record.get('mccCode'),
						extend1: record.get('extend1')
						
				};
          		
				array.push(data); 
				}
				Ext.Ajax.request({
					url: 'T11521Action.asp?method=update',
					method: 'post',
					params: {
					amtList: Ext.encode(array),
						txnId: '11521',
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
	var menuarr = new Array();
	menuarr.push(addMenu);
	menuarr.push("-");
	menuarr.push(delMenu);
	menuarr.push("-");
	menuarr.push(upMenu);
	menuarr.push("-");
	menuarr.push(queryConditionMebu);

	manuGrid = new Ext.grid.EditorGridPanel({
		title: '代理商成本费率信息',
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
	//		pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
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
		var rec = manuGrid.getSelectionModel().getSelected();
		if(rec.get('state')=='1'){
			manuGrid.getTopToolbar().items.items[4].disable();
			manuGrid.getTopToolbar().items.items[2].disable();
		}
		if(rec.get('state')=='2'){
			manuGrid.getTopToolbar().items.items[4].disable();
		}
		}
	});
	manuGrid.on( {
		//删除待审核状态 禁止修改
		'beforeedit':function(e){
			if(e.record.get('state')=='1'){
				e.cancel=true;
			}
		},
		'afteredit': function(e) {
			if(manuGrid.getTopToolbar().items.items[4] != undefined) {
				manuGrid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
	
	
});
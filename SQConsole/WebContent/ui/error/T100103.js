var batchStore;
var batchGrid;

Ext.onReady(function(){
	
 var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=manualReturn'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'sysSsn',mapping: 'sysSsn'},
			{name: 'pan',mapping: 'pan'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtReturn',mapping: 'amtReturn'},
			{name: 'saState',mapping: 'saState'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'createDate',mapping: 'createDate'},
			{name: 'ID',mapping:'id'},
			{name: 'txnNum',mapping:'txnNum'},
			{name: 'returnFee',mapping:'returnFee'},
			{name: 'srcId',mapping:'srcId'},
			{name: 'potalSsn',mapping:'potalSsn'},
			{name: 'rspCode',mapping:'rspCode'}
		]),
		autoLoad: true
	});
 
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			instDate: instDate,
			mchtNo:mchtNo,
			termId:termId,
			sysSsn:sysSsn,
			pan:pan,
			amtTrans:amtTrans,
			retrivlRef:retrivlRef,
			createDate:createDate
		});
	})
	var manuColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '原交易日期',dataIndex: 'instDate',width: 120,renderer:formatTs},
		{header: '退货日期',dataIndex: 'createDate',width: 120,renderer:formatTs},
//		{header: '原交易类型',dataIndex: 'txnNum',width: 80,renderer:funType},
		{header: '商户编号',dataIndex: 'mchtNo',width: 100},
		{header: '终端号',dataIndex: 'termId',width: 90},
		{header: '流水号',dataIndex: 'sysSsn',width: 90},
		{header: '原交易卡号',dataIndex: 'pan',width: 120},	 
		{header: '参考号',dataIndex: 'retrivlRef',width: 100},
		{header: '退货手续费',dataIndex: 'returnFee',width: 100},
		{header: '退货金额/元',dataIndex:'amtReturn'},
		{header: '退货状态',dataIndex:'saState',renderer:amtState},
		{header: '操作员',dataIndex:'oprId'},
		{header: 'ID值',dataIndex:'id',hidden:true},
		{header: 'SRC_ID',dataIndex: 'srcId',width: 100,hidden:true},
		{header: '商城退货流水号',dataIndex: 'potalSsn',width: 100,hidden:true},
		{header: '退货应答码',dataIndex: 'rspCode',width: 100,hidden:true}
//		{header: '退货时间',dataIndex:'createDate',width: 150,renderer:formatTs}
	]);
	
	function amtState(val){
		if(val=='0'){
			return '新增待审核'; 
		}else if(val=='1'){
			return "退货成功";
		}else if(val=='2'){
			return "修改待审核";
		}else if(val=='3'){
			return "删除待审核";
		}else if(val=='4'){
			return "审核拒绝";	
		}else if(val=='5'){
			return "后台退货成功，前台处理错误";
		}else if(val=='6'){
			return "退货失败";
		}
	}
	batchStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=amtReturn',
			timeout : 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtReturn',mapping: 'amtReturn'},
			{name:	'src',mapping:'src'},
			{name:	'sysSsn',mapping:'sysSsn'},
			{name:	'termSsn',mapping:'termSsn'},
			{name:	'txnNum',mapping:'txnNum'},
			{name:	'cancelFlag',mapping:'cancelFlag'},
			{name:	'revsalFlag',mapping:'revsalFlag'},
			{name:	'respCode',mapping:'respCode'},
			{name:	'authrId',mapping:'authrId'},
			{name:	'fwdInstIdCode',mapping:'fwdInstIdCode'},
			{name:	'acqInstIdCode',mapping:'acqInstIdCode'},
			{name:	'dateSettlmt',mapping:'dateSettlmt'},
			{name:	'settlAmt',mapping:'settlAmt'},//20120917双击查看详情中清算金额的显示
			{name:	'stlmFlg',mapping:'stlmFlg'},
			{name: 'returnFee',mapping:'returnFee'},
			{name: 'saState',mapping: 'saState'},
			{name: 'createDate',mapping:'createDate'}
		]),
		autoLoad: true
	});
	
	batchStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			instDate: instDate,
			mchtNo:mchtNo,
			termId:termId,
			sysSsn:sysSsn,
			pan:pan,
			amtTrans:amtTrans,
			retrivlRef:retrivlRef,
			createDate:createDate
			
		});
	})
	
	function renderer2(value){ 
		if(value instanceof Date){ 
			return new Date(value).format("Ymd"); 
		}else{ 
			return value; 
		} 
	} 
	var sm = new Ext.grid.CheckboxSelectionModel(); 
	var colModel = new Ext.grid.ColumnModel([
	    sm,                                     
//		new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'instDate',width: 120,renderer:formatTs/*,
			editor:new Ext.grid.GridEditor(new Ext.form.DateField({format:"Ymd",allowBlank:false})), 
			renderer:renderer2*/
		},
//		{header: '退货日期',dataIndex: 'createDate',width: 120,renderer:formatTs,
//			editor:new Ext.grid.GridEditor(new Ext.form.DateField({format:"Ymd",allowBlank:false})), 
//			renderer:renderer2
//		},
//		{header: '原交易类型',dataIndex: 'txnNum',width: 100,renderer:funType},
		{header: '商户编号',dataIndex: 'mchtNo',width: 100},
		{header: '终端号',dataIndex: 'termId',width: 90},
		{header: '流水号',dataIndex: 'sysSsn',width: 90},
		{header: '原交易卡号',dataIndex: 'pan',width: 120},
		{header: '原交易金额/元',dataIndex: 'amtTrans',width: 100},
		{header: '参考号',dataIndex: 'retrivlRef',width: 100},
//		{header: '退货手续费',dataIndex: 'returnFee',width: 100,
//			editor: new Ext.form.TextField({
//    		 	maxLength: 10,
//    		 	maskRe: /^[0-9\\.]+$/,
//    			allowBlank: false,
//    			vtype: 'isMoney'
//    		 })},
		{header: '退货金额/元',dataIndex:'amtReturn', 
			editor: new Ext.form.TextField({
    		 	maxLength: 10,
    		 	maskRe: /^[0-9\\.]+$/,
    			allowBlank: false,
    			vtype: 'isMoney'
    		 })}
//    		 {header: '退货状态',dataIndex:'saState',renderer:amtState}
	]);
	
	var addMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = batchGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var amtTranS=record.get('amtTrans');
                var amtReturn= record.get('amtReturn');
                    if(Number(amtReturn)==0){
                    	showErrorMsg("退货金额不能为0",batchGrid);
                        return;
                    }
                    if(Number(amtReturn)>Number(amtTranS)){
                    	showErrorMsg("退货金额不能大于原交易金额",batchGrid);
                        return;
                   }
                if('1'==record.get("cancelFlag")){
                	showErrorMsg("已撤销的交易不能退货",batchGrid);
                    return;
                }
                if('1'==record.get("revsalFlag")){
                	showErrorMsg("被冲正的交易不能退货",batchGrid);
                    return;
                }
                if(getState()=='失败'){
                	showErrorMsg("失败的交易不能退货",batchGrid);
                    return;
                }
				var data = {
					mchtNo : record.get('mchtNo'),
					termId: record.get('termId'),
					pan: record.get('pan'),
					amtTrans: record.get('amtTrans'),
					retrivlRef: record.get('retrivlRef'),
					amtReturn: record.get('amtReturn'),
					createDate: record.get('createDate'),
					instDate: record.get('instDate'),
					sysSsn: record.get('sysSsn'),
					returnFee: record.get('returnFee'),
					termSsn: record.get('termSsn'),
					txnNum: record.get('txnNum'),
					src:record.get('src')
				
					
				};
          		
				array.push(data); 
				}
				Ext.Ajax.request({
					url: 'T100101Action.asp?method=update',
					method: 'post',
					params: {
						amtReturnList: Ext.encode(array),
						txnId: '100101',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							batchGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,batchGrid);
							//batchGrid.hide();
							//batchGrid.getStore().reload();
							//重新加载已退货信息
							manuGrid.getStore().reload();
						} else {
							batchGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,batchGrid);
						}
						batchGrid.getTopToolbar().items.items[0].disable();
						batchGrid.getStore().reload();
						hideProcessMsg();
					}
				});
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
					var amtTranS=record.get('amtTrans');
                var amtReturn= record.get('amtReturn');
                    if(Number(amtReturn)==0){
                    	showErrorMsg("退货金额不能为0",manuGrid);
                        return;
                    }
                    if(Number(amtReturn)>Number(amtTranS)){
                    	showErrorMsg("退货金额不能大于原交易金额",manuGrid);
                        return;
                   }
                
				var data = {
						mchtNo : record.get('mchtNo'),
						termId: record.get('termId'),
						pan: record.get('pan'),
//						amtTrans: record.get('amtTrans'),
						retrivlRef: record.get('retrivlRef'),
						amtReturn: record.get('amtReturn'),
						createDate: record.get('createDate'),
						instDate: record.get('instDate'),
						sysSsn: record.get('sysSsn'),
						returnFee: record.get('returnFee'),
						ID: record.get('ID')
						
				};
          		
				array.push(data); 
				}
				Ext.Ajax.request({
					url: 'T100101Action.asp?method=updateone',
					method: 'post',
					params: {
						amtReturnList: Ext.encode(array),
						txnId: '100101',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							manuGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,manuGrid);
							//batchGrid.hide();
							//batchGrid.getStore().reload();
							//重新加载已退货信息
							manuGrid.getStore().reload();
						} else {
							manuGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,manuGrid);
						}
						manuGrid.getTopToolbar().items.items[0].disable();
						manuGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: false,
			handler:function() {
				var mchtNo = manuGrid.getSelectionModel().getSelected().data.mchtNo;
				var ID = manuGrid.getSelectionModel().getSelected().data.ID;
				showConfirm('确定要删除该条信息吗？商户编号：' + mchtNo,manuGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T100101Action.asp?method=delete',
								success : function(form, action) {
									var rspObj = Ext.util.JSON.decode(form.responseText);
									if(rspObj.success){
										showSuccessMsg(rspObj.msg,manuGrid);
										manuGrid.getStore().reload();
									}else{
										showErrorMsg(rspObj.msg,manuGrid);
									}
								},
								failure : function(form,action) {
									showErrorMsg(action.result.msg,manuGrid);
								},
								params: { 
									ID: ID,
									txnId: '100101',
									subTxnId: '02'
								}
							});
						}
					});
			}
		};
	
	var cityCodeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			allowBlank: false,
			hiddenName: 'mchtNo',
			id : 'idmchtNo1',
			editable: true,
			blankText: '商户编号不能为空',
			emptyText: '请选择商户编号',
			width:300
		},{
			xtype: 'textfield',
			name: 'sysSsn',
			id: 'sysSsn1',
			fieldLabel: '流水号',
			blankText: '请输入流水号',
			maxLength:6
			
		},{
			xtype: 'textfield',
			name: 'retrivlRef',
			id: 'retrivlRef1',
			fieldLabel: '参考号*',
			allowBlack:false,
			regex: /^[0-9]{1,12}$/,
			regexText: '只能输入数字',
			maxLength:12,
			minLength:12,
			blankText: '请输入12位数字'
		},{
			xtype: 'textfield',
			name: 'termId',
			id: 'termId1',
			fieldLabel: '终端号',
			maxLength:8,
			regex: /^[0-9\\]+$/,
			regexText: '只能输入数字'
		},{
			xtype: 'textfield',
			name: 'pan',
			id: 'pan1',
			fieldLabel: '原交易卡号',
			blankText: '请输入原交易卡号',
			maxLength:22,
			regex: /^[0-9\\]+$/,
			regexText: '只能输入数字'
		},{
			xtype: 'textfield',
			name: 'amtTrans',
			id: 'amtTrans1',
			fieldLabel: '原交易金额/元',
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isMoney'
		},{
			xtype: 'datefield',
			name: 'instDate',
			id: 'instDate1',
			fieldLabel: '交易日期'
		},{
			xtype: 'datefield',
			name: 'createDate',
			id: 'createDate1',
			fieldLabel: '退货日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
	}]
	});
	
	

	// 添加窗口
	var cityCodeWin = new Ext.Window({
		title: '添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
			if(!cityCodeForm.getForm().isValid()) {
				return;
			}
					var dateStr = "&instDate=";
					if(Ext.getCmp('instDate1').getValue()!=null&&Ext.getCmp('instDate1').getValue()!=''){
						dateStr += Ext.getCmp('instDate1').getValue().format('Ymd');
					}
					var retDate = "&createDate=";
					if(Ext.getCmp('createDate1').getValue()!=null&&Ext.getCmp('createDate1').getValue()!=''){
						retDate += Ext.getCmp('createDate1').getValue().format('Ymd');
					}
					window.location.href = Ext.contextPath + '/page/error/T100103.jsp?mchtNo='+Ext.getCmp('idmchtNo1').getValue()
									+'&retrivlRef='+Ext.getCmp('retrivlRef1').getValue()
									+'&termId='+Ext.getCmp('termId1').getValue()
									+'&sysSsn='+Ext.getCmp('sysSsn1').getValue()
									+'&pan='+Ext.getCmp('pan1').getValue()
									+'&amtTrans='+Ext.getCmp('amtTrans1').getValue()+dateStr + retDate;
			
		}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin.hide(manuGrid);
			}
		}]
	});
	
	var additional = {
			text: '添加',
			width: 85,
			iconCls: 'add',
			handler:function() {
			cityCodeWin.show();
			cityCodeWin.center();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');        
	menuArr.push(additional); 
//	menuArr.push('-');        
//	menuArr.push(delMenu);   
	
//	var menuarr = new Array();
//	menuarr.push(delMenu);
//	menuarr.push('-');        
//	menuarr.push(upMenu); 	
	
		
	
	batchGrid = new Ext.grid.EditorGridPanel({
		title: '手工退货',
		region:'north',
		iconCls: 'T301',
		height: 500,
		frame: true,
		border: true,
		columnLines: true,
//		autoExpandColumn: 1,
		stripeRows: true,
		store: batchStore,
		tbar: menuArr,
		cm: colModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载退货信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: batchStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
		
	});
	
	manuGrid = new Ext.grid.EditorGridPanel({
		title: '已退货信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
//		tbar: menuarr,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
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
		var detailForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			labelWidth : 100,
			layout: 'column',
			//waitMsgTarget : true,
			items : [
					{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '商户编号',
				            id: 'mchtNo',
				            name: 'mchtNo'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '终端号',
				            id: 'termId',
				            name: 'termId'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '交易卡号',
				            id: 'pan',
				            name: 'pan'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '交易类型',
				            id: 'txnNum',
				            name: 'txnNum' 
				            
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '交易状态',
				            id: 'respCode',
				            name: 'respCode'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '交易时间',
				            id: 'instDate',
				            name: 'instDate' 
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '是否清算',
				            id: 'stlmFlg',
				            name: 'stlmFlg'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '清算时间',
				            id: 'dateSettlmt',
				            name: 'dateSettlmt'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '交易金额/元',
				            id: 'amtTrans',
				            name: 'amtTrans'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '清算金额/元',
				            id: 'settlAmt',
				            name: 'settlAmt'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '累计已退货金额/元',
				            id: 'src',
				            name: 'src'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '系统参考号',
				            id: 'retrivlRef',
				            name: 'retrivlRef'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '系统流水号',
				            id: 'sysSsn',
				            name: 'sysSsn'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '终端流水号',
				            id: 'termSsn',
				            name: 'termSsn'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '授权号',
				            id: 'authrId',
				            name: 'authrId'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '发卡机构',
				            id: 'fwdInstIdCode',
				            name: 'fwdInstIdCode'
	             		}]
           			},{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
		                    xtype: 'displayfield',
				            fieldLabel: '受理机构',
				            id: 'acqInstIdCode',
				            name: 'acqInstIdCode'
	             		}]
           			}
           ]
		});
	 
		var detailWin = new Ext.Window({
				title : '交易详情',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 800,
				autoHeight : true,
				layout : 'fit',
				items : [ detailForm ],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [{
					text : '关闭',
					handler : function() {
						detailWin.hide(batchGrid);
					}
			}]
		});
		
	//获得交易状态
	function getState(){
		var cancelF = batchStore.getAt(0).get("cancelFlag");
		var revsalF = batchStore.getAt(0).get("revsalFlag");
		var respC = batchStore.getAt(0).get("respCode");
		if("1" == cancelF)
			return "已被撤销";
		if("1" == revsalF)
			return "已被冲正";
		if("00" == respC)	
			return "成功";
		return "失败"	
	}
	
	function getStlmFlg(){
		var stlmF = batchStore.getAt(0).get("stlmFlg");
		if("1" == stlmF)
			return "已清算";
		else
			return "未清算";
	}
	
	function getTransDate(){
		var transdate = batchStore.getAt(0).get("instDate");
		return formatTs(transdate);
	}
	
	batchGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			batchGrid.getTopToolbar().items.items[0].enable();
		},
		'rowdblclick':function(){
			detailForm.getForm().loadRecord(batchStore.getAt(0));
			detailForm.getForm().findField("txnNum").setValue("消费");
			detailForm.getForm().findField("respCode").setValue(getState());
			detailForm.getForm().findField("stlmFlg").setValue(getStlmFlg());
			detailForm.getForm().findField("instDate").setValue(getTransDate());
			detailWin.show();	
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [batchGrid,manuGrid],
		renderTo: Ext.getBody()
	});
	
})
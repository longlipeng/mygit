 
Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false,
				dataIndex : "id",
				listeners : {
					'beforerowselect' : function(sumColModel, rowIndex, keepExisting, record) {
						if ((record.data.misc5) == '1' || (record.data.misc5) == '4') {
							return false; // 不能进行选择
						} else {
							return true;
						}
					}
				}
	});
 var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=xsReturnInf',
			timeout: 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		},[
			{name: 'returnDate',mapping: 'returnDate'},
			{name: 'payDate',mapping: 'payDate'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'termId',mapping: 'termId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'orderNo',mapping: 'orderNo'},
			{name: 'returnOrderno',mapping: 'returnOrderno'},
			{name: 'returnAmt',mapping: 'returnAmt'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'returnFlag',mapping: 'returnFlag'},
			{name: 'misc1',mapping: 'misc1'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'channel',mapping: 'channel'},
			{name: 'channel_discount',mapping: 'channel_discount'},
			{name: 'respCode',mapping: 'respCode'},
			{name: 'misc5',mapping: 'misc5'}
		]),
		autoLoad: true
	});
 
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
					returnDate: typeof(queryForm.findById('returnDate').getValue()) == 'string' ? '' : queryForm.findById('returnDate').getValue().dateFormat('Ymd'),
					transDate: typeof(queryForm.findById('transDate').getValue()) == 'string' ? '' : queryForm.findById('transDate').getValue().dateFormat('Ymd'),
					mchtId: queryForm.getForm().findField('mchtId').getValue(),
		            termId: queryForm.getForm().findField('termId').getValue(),
		            pan: queryForm.getForm().findField('pan').getValue(),
		            orderNo: queryForm.getForm().findField('orderNo').getValue(),
		            returnOrderno: queryForm.getForm().findField('returnOrderno').getValue(),
		            misc5: queryForm.getForm().findField('misc5').getValue(),
		            channel: queryForm.getForm().findField('channel').getValue()
		});
	})
	var manuColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '退货申请时间',dataIndex: 'returnDate',width: 100,renderer:formatTs},
		{header: '审核时间',dataIndex: 'payDate',width: 100,renderer:formatTs},
		{header: '原交易时间',dataIndex: 'transDate',width: 100,renderer:formatTs},
		{header: '商户编号',dataIndex: 'mchtId',width: 200, renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 100},
		{header: '卡号',dataIndex: 'pan',width: 150},	 
		{header: '原交易订单号',dataIndex: 'returnOrderno',width: 100},
		{header: '退货交易订单号',dataIndex: 'orderNo',width: 100},
		{header: '退货金额',dataIndex:'returnAmt'},
		{header: '原交易金额',dataIndex:'amtTrans'},
		{header: '退货状态', dataIndex:'misc5',renderer:fuReturnFlag},
		{header: '退货状态1', dataIndex:'returnFlag',hidden:true},
		{header: '交易来源', dataIndex:'channel',renderer:sourceStat},
		{header: '渠道优惠金额', dataIndex:'channel_discount'},
		{header: '退货状态说明',dataIndex:'misc1',width: 200},
		{header: '操作员',dataIndex:'oprId',width: 200,hidden:true},
		{header: 'respCode',dataIndex:'respCode',width: 200,hidden:true}
	]);
	function sourceStat(val){
		if(val=='02'){
			return '百联'; 
		}else if(val=='01'){
			return "线上商城";
		}else{
			return val;
		}
	}
 	function fuReturnFlag(val) {
		if(val==''){
			return '申请'; 
		}else if(val=='1'){
			return "审核通过退款成功";
		}else if(val=='2'){
			return "审核通过退款失败";
		}else if(val=='3'){
			return "审核失败";
		}else if(val=='4'){
			return "审核拒绝";
		}else {
			return val;
		}
	}
     function amtState(val){
		if(val=='0'){
			return '新增待审核'; 
		} else if(val=='1'){
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
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',manuGrid,function(bt) {
				if(bt == 'yes') {
					var modifyrecords = manuGrid.getSelectionModel().getSelections();
					if(modifyrecords.length == 0){
						return ;
					}
					showMask("正在提交审核信息，请稍后....",manuGrid);
					var array = new Array();
					for(var index = 0;index <modifyrecords.length;index++ ){
						var record = modifyrecords[index];
						var data = {
						orderNo : record.get('orderNo'),//原交易订单号
						mchtId : record.get('mchtId'),//商户号
						termId : record.get('termId'),//终端号
						returnOrderno: record.get('returnOrderno'),//退货交易订单号
						returnAmt: record.get('returnAmt'),//退货金额
						pan: record.get('pan')//卡号
						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T20042Action.asp?method=accept',
						params: {
							infList: Ext.encode(array),
							txnId: '20042',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessAlert1(rspObj.msg,manuGrid);
							} else {
								showSuccessAlert1(rspObj.msg,manuGrid);
//								showErrorMsg(rspObj.msg,manuGrid);
//								showSuccessMsg(rspObj.msg,manuGrid);
							}
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
					var modifyrecords = manuGrid.getSelectionModel().getSelections();
					if(modifyrecords.length == 0){
						return;
					}
					showMask("正在提交审核信息，请稍后....",manuGrid);
					var array = new Array();
					for(var index = 0;index <modifyrecords.length;index++ ){
						var record = modifyrecords[index];
						var data = {
						orderNo : record.get('orderNo'),//原交易订单号
						returnOrderno: record.get('returnOrderno'),//退货交易订单号
						returnAmt: record.get('returnAmt'),//退货金额
						pan: record.get('pan')//卡号
						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T20042Action.asp?method=refuse',
						params: {
							infList: Ext.encode(array),
							txnId: '20042',
							subTxnId: '02'
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
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	var queryConditionMenu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
	};
	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		items : [{
			xtype: 'datefield',
			name: 'returnDate',
			id: 'returnDate',
			fieldLabel: '退货申请时间'
		},{
			xtype: 'datefield',
			name: 'transDate',
			id: 'transDate',
			fieldLabel: '原交易时间'
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
//			allowBlank: false,
			hiddenName: 'mchtId',
			id : 'idmchtId',
			editable: true,
			blankText: '商户编号不能为空',
			emptyText: '请选择商户编号',
			width:200,
			callFunction: function() {
				Ext.getCmp('idtermId').reset();
				Ext.getCmp('idtermId').parentP=this.value;
				Ext.getCmp('idtermId').getStore().reload();
			}
		},{
               xtype: 'dynamicCombo',
               fieldLabel: '终端号',
               methodName: 'getTermIdAll',
               hiddenName: 'termId',
               id: 'idtermId',
               displayField: 'displayField',
               valueField: 'valueField',
               width: 200
          },{
			xtype: 'textfield',
			name: 'pan',
			id: 'pan',
			fieldLabel: '卡号',
			regex: /^[0-9\\]+$/,
			regexText: '只能输入数字',
//			allowBlack:false,
			maxLength:19
		},{
			xtype: 'textfield',
			name: 'returnOrderno',
			id: 'returnOrderno',
			fieldLabel: '原交易订单号',
			maxLength:64
		},{
			xtype: 'textfield',
			name: 'orderNo',
			id: 'orderNo',
			fieldLabel: '退货交易订单号',
			maxLength:64
		},{
			xtype: 'basecomboselect',
			id: 'misc5',
			fieldLabel: '退货状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增待审核'],['1','审核通过退款成功'],['2','审核通过退款失败'],['3','审核失败'],['4','审核拒绝']],
				reader: new Ext.data.ArrayReader()
		})
		},{
			xtype: 'basecomboselect',
			id: 'channel',
			fieldLabel: '交易来源',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['01','线上商城'],['02','百联']],
				reader: new Ext.data.ArrayReader()
		})
		}]
	});
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 500,
		autoHeight : true,
		items : [queryForm],
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
	
	var menuArr = new Array();
	menuArr.push(acceptMenu);
	menuArr.push('-');
	menuArr.push(refuseMenu)
	menuArr.push('-');
	menuArr.push(queryConditionMenu)
	
	manuGrid = new Ext.grid.EditorGridPanel({
		title: '退货待审核信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		tbar: menuArr,
		cm: manuColModel,
//		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm: sm,
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
//var batchStore;
var batchGrid;

Ext.onReady(function(){
	
 var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=xsNTxn'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
//instDate,mchtId,termId,pan,orderNo,amtTrans,transState,instId,resp_code, txn_num
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'termId',mapping: 'termId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'orderNo',mapping: 'orderNo'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'transState',mapping: 'transState'},
			{name: 'instId',mapping: 'instId'},
			{name: 'misc1',mapping: 'misc1'},
			{name: 'respCode',mapping: 'respCode'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'misc2',mapping: 'misc2'}
			
		]),
		autoLoad: true
	});
 
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			instDate: typeof(queryForm.findById('instDate').getValue()) == 'string' ? '' : queryForm.findById('instDate').getValue().dateFormat('Ymd'),
			mchtId: queryForm.getForm().findField('mchtId').getValue(),
		    termId: queryForm.getForm().findField('termId').getValue(),
		    pan: queryForm.getForm().findField('pan').getValue(),
		    orderNo: queryForm.getForm().findField('orderNo').getValue(),
		    instId: queryForm.getForm().findField('instId').getValue(),
		    misc1: queryForm.getForm().findField('misc1').getValue()
		});
	})
	var manuColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '交易时间',dataIndex: 'instDate',width: 120,renderer:formatTs},
		{header: '商户编号',dataIndex: 'mchtId',width: 200, renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 90},
		{header: '卡号',dataIndex: 'pan',width: 120},
		{header: '订单号',dataIndex: 'orderNo',width: 100},
		{header: '交易金额/元',dataIndex: 'amtTrans',width: 100},
//		{header: '交易状态',dataIndex:'transState',renderer:transStat},
		{header: '受理机构',dataIndex:'instId',renderer:instIdStat},
		{header: '交易来源',dataIndex:'misc1',renderer:sourceStat},
		{header: '交易状态',dataIndex:'respCode',renderer:respCodeStat},
		{header: '渠道优惠金额',dataIndex: 'misc2',width: 100},
		{header: '交易码',dataIndex:'txnNum',renderer:txnNumStat}
	]);
	
	function respCodeStat(val){
		if(val=='00'){
			return '成功'; 
		}else{
			return "失败";
		}
	}
	function txnNumStat(val){
		if(val=='5001'){
			return '消费'; 
		}else if(val=='5101'){
			return "退货";
		}
	}
	function transStat(val){
		if(val=='0'){
			return '申请'; 
		}else if(val=='1'){
			return "审核通过";
		}else if(val=='2'){
			return "失败";
		}
	}
	function sourceStat(val){
		if(val=='02'){
			return '百联'; 
		}else if(val=='01'){
			return "线上商城";
		}else{
			return val;
		}
	}
	function instIdStat(val){
		if(val=='00000005'){
			return '单用途卡'; 
		}else if(val=='00000002'){
			return "多用途卡通道";
		}else {
			return val;
		}
	}
	function srcIdFormat(val) {
		if(val=='1901'){
			return '控制台手工退货'; 
		}else if(val=='2901'){
			return "商城退货";
		}
	}
	
	function renderer2(value){ 
		if(value instanceof Date){ 
			return new Date(value).format("Ymd"); 
		}else{ 
			return value; 
		} 
	} 
	
	var queryConditionMebu = {
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
		width : 500,
		autoHeight : true,
		items : [{
			xtype: 'datefield',
			name: 'instDate',
			id: 'instDate',
			fieldLabel: '交易时间'
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
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
			maxLength:19
		},{
			xtype: 'textfield',
			name: 'orderNo',
			id: 'orderNo',
			fieldLabel: '订单号',
			maxLength:64
		}/*,{
			xtype: 'basecomboselect',
			id: 'transState',
			fieldLabel: '交易状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','申请'],['1','审核通过'],['2','失败']],
				reader: new Ext.data.ArrayReader()
		})
		}*/,{
			xtype: 'basecomboselect',
			id: 'instId',
			fieldLabel: '受理机构',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['00000005','单用途卡'],['00000002','多用途卡通道']],
				reader: new Ext.data.ArrayReader()
		})
		},{
			xtype: 'basecomboselect',
			id: 'misc1',
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
	
	var menuarr = new Array();
	menuarr.push(queryConditionMebu); 
	
	manuGrid = new Ext.grid.EditorGridPanel({
		title: '线上交易流水查询',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		tbar: menuarr,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		cm: manuColModel,
		renderTo : Ext.getBody(),
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
		var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
})
Ext.onReady(function() {
	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	// 脱机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnInfoOutline'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'cardAccpName',mapping: 'cardAccpName'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'rcvgCode',mapping: 'rcvgCode'},
			{name: 'fwdInstIdCode',mapping: 'fwdInstIdCode'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'pan',mapping: 'pan'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'txnBatchNo',mapping: 'txnBatchNo'},
			{name: 'txnCerNo',mapping: 'txnCerNo'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'icCert',mapping: 'icCert'},
			{name: 'tvr',mapping: 'tvr'},
			{name: 'tsi',mapping: 'tsi'},
			{name: 'aid',mapping: 'aid'},
			{name: 'atc',mapping: 'atc'},
			{name: 'appTag',mapping: 'appTag'},
			{name: 'appPreName',mapping: 'appPreName'},
			{name: 'respCode',mapping: 'respCode'}
		]),
		autoLoad: true
	}); 

	var txnColModel = new Ext.grid.ColumnModel([
		{header: '交易日期',dataIndex: 'instDate',width: 60},
		{header: '交易时间',dataIndex: 'instTime',width: 60},
		{header: '商户编号',dataIndex: 'cardAccpId',width: 100},
		{header: '商户名称',dataIndex: 'cardAccpName',width: 100,id:'cardAccpName'},
		{header: '终端编号',dataIndex: 'cardAccpTermId',width: 70},
		{header: '受理通道',dataIndex: 'rcvgCode',width: 120,renderer: agencyRen},
		{header: '发卡行名称',dataIndex: 'fwdInstIdCode',width: 120},
		{header: '卡类型',dataIndex: 'cardType',width: 60,renderer: cardType},
		{header: '卡号',dataIndex: 'pan',width: 120},
		{header: '交易类型',dataIndex: 'txnNum',width: 100},
		{header: '交易批次号',dataIndex: 'txnBatchNo',width: 60},
		{header: '交易凭证号',dataIndex: 'txnCerNo',width: 60},
		{header: '交易金额',dataIndex: 'amtTrans',width: 70},
		{header: 'IC卡交易证书',dataIndex: 'icCert',width: 90},
		{header: 'TVR',dataIndex: 'tvr',width: 90},
		{header: 'TSI',dataIndex: 'tsi',width: 90},
		{header: 'AID',dataIndex: 'aid',width: 90},
		{header: 'ATC',dataIndex: 'atc',width: 90},
		{header: '应用标签',dataIndex: 'appTag',width: 90},
		{header: '应用首选名称',dataIndex: 'appPreName',width: 90},
		{header: '应答码',dataIndex: 'respCode',width: 60,renderer: respCode}
	]);
	
	// 受理机构
	function agencyRen(val){
		if(agencyStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return agencyStore.query("valueField",val).first().data.displayField;
		}
	}
	// 应答码
	function respCode(val) {
		if(val == '00')
			return '<font color="green">' + val + '</font>';
		else
			return '<font color="red">' + val + '</font>';
	}
	var menuArr = new Array();
	
	var monitorMenu = new Ext.Button({
		iconCls: 'remote_16',
		text: '启动监控模式',
		enableToggle: true,
		toggleHandler: function(bt,state) {
			// 监控模式
			if(state) {
				this.setText('停止监控模式');
				this.setIconClass('pause');
				queryWin.hide();
				queryForm.getForm().reset();
				grid.getTopToolbar().items.first().disable();
				Ext.TaskMgr.start(task);
			} else { // 手工查询模式
				this.setText('启动监控模式');
				this.setIconClass('remote_16');
				grid.getTopToolbar().items.first().enable();
				Ext.TaskMgr.stop(task);
			}
		}
	});
	
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T50102Action.asp',
				params: {
					startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
					endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
					txnNum: Ext.getCmp('txnNum').getValue(),
					cardType: Ext.getCmp('cardType').getValue(),
					mchntNo: Ext.getCmp('mchntNo').getValue(),
					termId: queryForm.getForm().findField('termId').getValue(),
					pan: queryForm.getForm().findField('pan').getValue(),
					transamtsmall: queryForm.getForm().findField('transamtsmall').getValue(),
					transamtbig: queryForm.getForm().findField('transamtbig').getValue(),
					brhId: Ext.getCmp('brhId').getValue(),
					txnId: '50102',
					subTxnId: '01'
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
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');  //[1]
	menuArr.push(monitorMenu);  //[2]
	menuArr.push('-');
	menuArr.push(report);
	// 监控模式定时器
	var task = {
		run: function() {
			txnStore.load({
				params: {start: 0}
			});
		},
		interval: 10000
	};
	
	// 脱机交易查询
	var grid = new Ext.grid.GridPanel({
		title: '脱机交易查询',
		iconCls: 'T502',
		region: 'center',
		frame: true,
		border: true,
		autoExpandColumn: 'cardAccpName',
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载脱机交易列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: txnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			value : new Date,
			fieldLabel: '交易开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易结束日期'
		},{
			xtype: 'basecomboselect',
			fieldLabel: '交易类型',
			id: 'txnNum',
			hiddenName: 'HtxnNum',
		    baseParams: 'TXN_NUM_OUT',
			editable: true
		},{
	       	xtype: 'basecomboselect',
			fieldLabel: '卡类型',
			baseParams: 'CARD_STYLE',
			id: 'cardType',
			hiddenName: 'CARD_STYLE',
			editable: true
	   	},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntIdAll',
			fieldLabel: '商户编号',
			hiddenName: 'HmchntNo',
			id: 'mchntNo',
			editable: true,
			width: 360
		},{
			xtype: 'textfield',
			fieldLabel: '终端编号',
			id: 'termId',
			width: 200
		},{
			xtype: 'textfield',
			name: 'transamtsmall',
			id: 'transamtsmall',
			fieldLabel: '交易最小金额(元)',
			blankText: '请输入交易最小金额',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			name: 'transamtbig',
			id: 'transamtbig',
			fieldLabel: '交易最大金额(元)',
			blankText: '请输入交易最大金额',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			fieldLabel: '卡号',
			id: 'pan',
			width: 200
		},{
			xtype: 'basecomboselect',
			fieldLabel: '受理通道',
			store: agencyStore,
			width : 200,
			id: 'brhId',
			hiddenName: 'HbrhId',
		    baseParams: 'BRH_BELOW_BRANCH',
			editable: true
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				var startDateTemp = queryForm.findById('startDate').getValue();
	    		var endDateTemp = queryForm.findById('endDate').getValue();
	    		
	        	if(queryForm.findById('startDate').getValue()!=null && queryForm.findById('startDate').getValue()!=''){
	        		startDateTemp = queryForm.findById('startDate').getValue().format('Ymd');
				}
	        	if(queryForm.findById('endDate').getValue()!=null && queryForm.findById('endDate').getValue()!=''){
	        		endDateTemp = queryForm.findById('endDate').getValue().format('Ymd');
				}
	        	if(startDateTemp!=null && startDateTemp!='' && endDateTemp!=null && endDateTemp!=''){
	        		if(startDateTemp > endDateTemp){
		        		showErrorMsg('交易开始日期不能大于交易结束日期，请重新选择',queryForm);
		        		queryForm.findById('startDate').setValue('');
		        		queryForm.findById('endDate').setValue('');
		        		return;
		        	}
	        	}
	        	var smallTemp = queryForm.findById('transamtsmall').getValue();
	        	var bigTemp = queryForm.findById('transamtbig').getValue();
	        	if(queryForm.findById('transamtsmall').getValue()!=null && queryForm.findById('transamtsmall').getValue().trim()!='' 
	        		&& queryForm.findById('transamtbig').getValue()!=null && queryForm.findById('transamtbig').getValue().trim()!=''){
	        		if(parseInt(smallTemp.trim()) > parseInt(bigTemp.trim())){
						showErrorMsg('交易最小金额不能大于交易最大金额',queryWin);
						queryForm.findById('transamtsmall').setValue('');
						queryForm.findById('transamtbig').setValue('');
						return ;
					}
	        	}
				if(queryForm.getForm().isValid()){
	        		txnStore.load();
	        	}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			txnNum: Ext.getCmp('txnNum').getValue(),
			cardType: Ext.getCmp('cardType').getValue(),
			mchntNo: Ext.getCmp('mchntNo').getValue(),
			termId: queryForm.getForm().findField('termId').getValue(),
			pan: queryForm.getForm().findField('pan').getValue(),
			transamtsmall: queryForm.getForm().findField('transamtsmall').getValue(),
			transamtbig: queryForm.getForm().findField('transamtbig').getValue(),
			brhId: Ext.getCmp('brhId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
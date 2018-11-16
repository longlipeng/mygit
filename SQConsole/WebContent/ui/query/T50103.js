Ext.onReady(function() {
	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTxnInfo',
			timeout : 6000000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'cardAccpName',mapping: 'cardAccpName'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'acqInstIdCode',mapping: 'acqInstIdCode'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'transWay',mapping: 'transWay'},
			{name: 'respCode',mapping: 'respCode'},
			{name: 'termBatchNm',mapping: 'termBatchNm'},
			{name: 'orderNo',mapping: 'orderNo'},
			{name: 'transState',mapping: 'transState'},
			{name: 'misc',mapping: 'misc'},
			{name: 'acctId1Len',mapping: 'acctId1Len'}
		]),
		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '交易日期',dataIndex: 'instDate',width: 60},
		{header: '交易时间',dataIndex: 'instTime',width: 60},
		{header: '卡号',dataIndex: 'pan',width: 120},
		{header: '交易类型',dataIndex: 'txnNum',width: 100},

		{header: '商户编号',dataIndex: 'cardAccpId',width: 100},
		{header: '商户名称',dataIndex: 'cardAccpName',width: 100,id:'cardAccpName'},
		{header: 'POS商户编号',dataIndex: 'misc',width: 100},
		{header: '终端编号',dataIndex: 'cardAccpTermId',width: 70},
		{header: '批次号',dataIndex: 'termBatchNm',width: 60},
		{header: '系统流水',dataIndex: 'sysSeqNum',width: 60},
		{header: '交易金额',dataIndex: 'amtTrans',width: 70},
		{header: '检索参考号',dataIndex: 'retrivlRef',width: 90},
		//{header: '受理行',dataIndex: 'acqInstIdCode',width: 80},
		{header: '受理机构',dataIndex: 'brhId',width: 60,renderer: agencyRen},
		{header: '应用异常码',dataIndex: 'orderNo',width: 60,hidden:true},
		{header: '处理状态',dataIndex: 'transState',width: 60},
		{header: '卡类型',dataIndex: 'cardType',width: 60,renderer: cardType},
		{header: '交易方式',dataIndex: 'transWay',width: 60,renderer: transWay},
		{header: '应答码',dataIndex: 'respCode',width: 60,renderer: respCode},
		{header: '原应答码',dataIndex: 'acctId1Len',width: 60}
	]);
	
	// 交易方式数据集
	var transWayMap = new DataMap('TRANS_WAYY');
	
	// 受理机构
	function agencyRen(val){
		if(val.replace(' ','')==''){
             return '';
		}
		if(agencyStore.query("valueField",val).first() == undefined){
			return val;
		}else{
			return agencyStore.query("valueField",val).first().data.displayField;
		}
	}
	// 交易方式
	function transWay(val) {
		var tmp = val.split("-");
		if(tmp[0] == '02' || tmp[0] == '90'){
			if(tmp[1] == '51' || tmp[1] == '52'){
				return "Fall Back";
			}else{
				return "磁条卡";
			}
		}else if(tmp[0] == '05' || tmp[0] == '95'){
			return "IC卡";
		}else if(tmp[0] == '01'){
			return "无卡交易";
		}else if(tmp[0] == '07'){
			return "非接";
		}else if(transWayMap[tmp[0]] != undefined ){
			return transWayMap[tmp[0]];
		}else{
			return tmp[0];
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
		id:'monitorBt',
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
				this.disable();
				monitorForm.getForm().reset();
				monitorWin.show();
//				Ext.TaskMgr.start(task);
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
		    monitorForm.getForm().reset();
			monitorWin.hide();
			queryWin.show();
		}
	};
	
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后....",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T50101Action.asp',
				timeout : 600000000,
				params: {
					startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
					endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
					txnNum: Ext.getCmp('txnNum').getValue(),
					mchntNo: Ext.getCmp('mchntNo').getValue(),
					cardType: Ext.getCmp('cardType').getValue(),
					transWay: Ext.getCmp('trans_Way').getValue(),
					brhId: Ext.getCmp('brhId').getValue(),
					idbinStaNo: Ext.getCmp('idbinStaNo').getValue(),
					termId: queryForm.getForm().findField('termId').getValue(),
					pan: queryForm.getForm().findField('pan').getValue(),
					retrivlRef: queryForm.getForm().findField('retrivlRef').getValue(),
					sysSeqNum: queryForm.getForm().findField('sysSeqNum').getValue(),
					respCode: queryForm.getForm().findField('respCode').getValue(),
					transamtsmall: queryForm.getForm().findField('transamtsmall').getValue(),
					transamtbig: queryForm.getForm().findField('transamtbig').getValue(),
					txnId: '50101',
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
	
	// 联机交易查询
	var grid = new Ext.grid.GridPanel({
		title: '联机交易查询',
		iconCls: 'T501',
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
			msg: '正在加载联机交易列表......'
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
	
	/***************************监控条件*************************/
	
	var monitorForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			name: 'rate',
			id: 'rate',
			fieldLabel: '刷新频率',
			blankText: '请输入刷新频率',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isNumber',
			maxLength: 3
		},{
			xtype: 'textfield',
			name: 'pageSize',
			id: 'pageSize',
			fieldLabel: '记录条数',
			blankText: '请输入显示记录条数',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isNumber',
			maxLength: 3
		},{
			xtype: 'basecomboselect',
			fieldLabel: '交易类型',
			id: 'txnNum1',
			hiddenName: 'HtxnNum1',
		    baseParams: 'TXN_NUM',
			editable: true
		},{
	       	xtype: 'basecomboselect',
			fieldLabel: '卡类型',
			baseParams: 'CARD_STYLE',
			id: 'cardType1',
			hiddenName: 'CARD_STYLE1',
			editable: true
	   	},{
	       	xtype: 'basecomboselect',
	       	baseParams: 'TRANS_WAYY',
			fieldLabel: '交易方式',
			id:'trans_Way1',
			hiddenName: 'TRANS_WAYY1',
			editable: true
	   	},{
			xtype: 'basecomboselect',
			fieldLabel: '受理机构',
			store: agencyStore,
			width : 200,
			id: 'brhId1',
			hiddenName: 'HbrhId1',
		    baseParams: 'BRH_BELOW_BRANCH',
			editable: true
		},{
			
			xtype: 'dynamicCombo',
			methodName: 'getMchntIdAll',
			fieldLabel: '商户编号',
			hiddenName: 'HmchntNo1',
			id: 'mchntNo1',
			editable: true,
			width: 360
		},{
			xtype: 'textfield',
			fieldLabel: '终端编号',
			id: 'termId1'
		},{
			xtype: 'textfield',
			fieldLabel: '卡号',
			id: 'pan1'
		},{
			xtype: 'textfield',
			fieldLabel: '应答码',
			maxLength: 2,
			blankText: '请输入应答码',
			regex: /^[A-Za-z0-9]+$/, 
			regexText:'只能输入字母或数字',
			id: 'respCode1'
		},{
			xtype: 'textfield',
			name: 'transamtsmall',
			id: 'transamtsmall1',
			fieldLabel: '交易最小金额(元)',
			blankText: '请输入交易最小金额',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			name: 'transamtbig',
			id: 'transamtbig1',
			fieldLabel: '交易最大金额(元)',
			blankText: '请输入交易最大金额',
//			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		}]
	});
	
	var monitorWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
		autoHeight: true,
		items: [monitorForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: false,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				monitorWin.collapse();
				monitorWin.getEl().pause(1);
				monitorWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				monitorWin.expand();
				monitorWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '启动监控',
			id:'queryBt',
			handler: function() {
				var rate = Ext.getCmp('rate').getValue();
				if(rate=='')
					rate = 10;
				var pageSize = Ext.getCmp('rate').getValue();
				if(pageSize==''){
					pageSize=System[QUERY_RECORD_COUNT];
				}
	        	var smallTemp = monitorForm.findById('transamtsmall1').getValue();
	        	var bigTemp = monitorForm.findById('transamtbig1').getValue();
	        	if(monitorForm.findById('transamtsmall1').getValue()!=null && monitorForm.findById('transamtsmall1').getValue().trim()!='' 
	        		&& monitorForm.findById('transamtbig1').getValue()!=null && monitorForm.findById('transamtbig1').getValue().trim()!=''){
	        		if(parseInt(smallTemp.trim()) > parseInt(bigTemp.trim())){
						showErrorMsg('交易最小金额不能大于交易最大金额',monitorWin);
						monitorForm.findById('transamtsmall1').setValue('');
						monitorForm.findById('transamtbig1').setValue('');
						return ;
					}
	        	}
	        	var rateTemp = monitorForm.findById('rate').getValue();
	        	if(rateTemp!=null && rateTemp.trim()!=''){
	        		if(parseInt(rateTemp.trim())<5){
	        			showErrorMsg('刷新频率不能低于5秒',monitorWin);
	        			return ;
	        		}
	        	}
	        	if(monitorForm.getForm().isValid()){
	        		Ext.getCmp('txnNum').setValue(Ext.getCmp('txnNum1').getValue()),
			        Ext.getCmp('cardType').setValue(Ext.getCmp('cardType1').getValue()),
			        Ext.getCmp('trans_Way').setValue(Ext.getCmp('trans_Way1').getValue()),
			        Ext.getCmp('brhId').setValue(Ext.getCmp('brhId1').getValue()),
			        Ext.getCmp('mchntNo').setValue(Ext.getCmp('mchntNo1').getValue()),
			        queryForm.getForm().findField('termId').setValue(Ext.getCmp('termId1').getValue()),
			        queryForm.getForm().findField('pan').setValue(Ext.getCmp('pan1').getValue()),			        
			        queryForm.getForm().findField('transamtsmall').setValue(Ext.getCmp('transamtsmall1').getValue()),
			        queryForm.getForm().findField('transamtbig').setValue(Ext.getCmp('transamtbig1').getValue()),
			        queryForm.getForm().findField('respCode').setValue(Ext.getCmp('respCode1').getValue());
	        		grid.bbar.pageSize=Number(pageSize);
	        		task.interval=rate*1000;
	        		Ext.TaskMgr.start(task);
	        		monitorWin.hide();
	        		Ext.getCmp('monitorBt').enable();
	        		
	        	}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				monitorForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				monitorForm.getForm().reset();
				monitorWin.hide();
	            /*Ext.getCmp('monitorBt').enable();
	            Ext.getCmp('monitorBt').setText('启动监控模式');
				Ext.getCmp('monitorBt').setIconClass('remote_16');
				grid.getTopToolbar().items.first().enable();*/
				Ext.getCmp('monitorBt').toggle();
				Ext.getCmp('monitorBt').enable();
			}
		}]
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
			value : new Date,
			altFormats: 'Y-m-d',
			fieldLabel: '交易开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易结束日期'
//			listeners: {
//				'select': function() {
//					if(Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue()){
//						showAlertMsg("交易开始日期不能大于交易结束日期，请重新选择！",queryForm);
//						Ext.getCmp('startDate').setValue('');
//						Ext.getCmp('endDate').setValue('');
//					}
//				}
//			}
		},{
			xtype: 'basecomboselect',
			fieldLabel: '交易类型',
			id: 'txnNum',
			hiddenName: 'HtxnNum',
		    baseParams: 'TXN_NUM',
			editable: true
		},{
	       	xtype: 'basecomboselect',
			fieldLabel: '卡类型',
			baseParams: 'CARD_STYLE',
			id: 'cardType',
			hiddenName: 'CARD_STYLE',
			editable: true
	   	},{
	       	xtype: 'basecomboselect',
	       	baseParams: 'TRANS_WAYY',
			fieldLabel: '交易方式',
			id:'trans_Way',
			hiddenName: 'TRANS_WAYY',
			editable: true
	   	},{
			xtype: 'basecomboselect',
			fieldLabel: '受理机构',
			store: agencyStore,
			width : 200,
			id: 'brhId',
			hiddenName: 'HbrhId',
		    baseParams: 'BRH_BELOW_BRANCH',
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
			id: 'termId'
		},{
			xtype: 'textfield',
			fieldLabel: '卡号',
			id: 'pan'
		},{
			fieldLabel : '卡bin',
			methodName:'getbinStaNo',
			xtype: 'dynamicCombo',
			id : 'idbinStaNo',
			hiddenName: 'binStaNo'
//			labelStyle: 'padding-left: 5px',
//			anchor: '90%'
		},{
			xtype: 'textfield',
			fieldLabel: '检索参考号',
			maxLength: 12,
			regex: /^\w+$/,
			id: 'retrivlRef'
		},{
			xtype: 'textfield',
			fieldLabel: '系统流水号',
			maxLength: 12,
			regex: /^\w+$/,
			id: 'sysSeqNum'
		},{
			xtype: 'textfield',
			fieldLabel: '应答码',
			maxLength: 2,
			blankText: '请输入应答码',
			regex: /^[A-Za-z0-9]+$/, 
			regexText:'只能输入字母或数字',
			id: 'respCode'
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
			id:'queryBt',
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
			transWay: Ext.getCmp('trans_Way').getValue(),
			brhId: Ext.getCmp('brhId').getValue(),
			mchntNo: Ext.getCmp('mchntNo').getValue(),
			idbinStaNo: Ext.getCmp('idbinStaNo').getValue(),
			termId: queryForm.getForm().findField('termId').getValue(),
			pan: queryForm.getForm().findField('pan').getValue(),
			retrivlRef: queryForm.getForm().findField('retrivlRef').getValue(),
			sysSeqNum: queryForm.getForm().findField('sysSeqNum').getValue(),
			transamtsmall: queryForm.getForm().findField('transamtsmall').getValue(),
			transamtbig: queryForm.getForm().findField('transamtbig').getValue(),
			respCode: queryForm.getForm().findField('respCode').getValue(),
			pageSize:Ext.getCmp('pageSize').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	//queryForm.getForm().findField('brhId').setValue(BRHID);
});
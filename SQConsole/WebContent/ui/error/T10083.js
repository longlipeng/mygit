Ext.onReady(function() {
	
	//拒绝类型
	var refuseTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('REFUSE_TYPE',function(ret){
		refuseTypeStore.loadData(Ext.decode(ret));
	});	
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
//			{name: 'brhID',mapping: 'brhID'},
			{name: 'oprID',mapping: 'oprID'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'},
			{name: 'param1',mapping: 'param1'},
			{name: 'param2',mapping: 'param2'},
			{name: 'param3',mapping: 'param3'},
			{name: 'param4',mapping: 'param4'},
			{name: 'param5',mapping: 'param5'},
			{name: 'param6',mapping: 'param6'},
			{name: 'reserve1',mapping: 'reserve1'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			flag:"17"
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,width: 120,renderer: formatTs},
//		{header: '操作员所属分公司',dataIndex: 'brhID',width: 120},
		{header: '拒绝人',dataIndex: 'oprID',width: 70},
		{header: '拒绝类型',dataIndex: 'refuseType',renderer: riskInfoRefuseState},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 220},
		{header: '商户号',dataIndex: 'param1',width: 100,id:'param1'},
		{header: '终端号',dataIndex: 'param2',width: 70,id:'param2'},
		{header: '交易批次号',dataIndex: 'param3',width: 70,id:'param3'},
		{header: '交易凭证号',dataIndex: 'param4',width: 70,id:'param4'},
		{header: '交易日期',dataIndex: 'param5',width: 70,id:'param5'},
		{header: '卡号',dataIndex: 'reserve1',width: 130,id:'reserve1'},
		{header: '交易金额',dataIndex: 'param6',width: 80,id:'param6',renderer:formatAmtTrans}
	
	]);
	
	var menuArr = new Array();
	var queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};

	menuArr.push(queryConditionMebu); // [0]
	
	var grid = new Ext.grid.GridPanel({
		title: '补电子现金消费审核拒绝原因查看',
		region: 'center',
		iconCls: 'T20103',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		tbar : menuArr,
		//autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载补电子现金消费审核拒绝原因列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
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
	
	/** *************************查询条件************************ */
	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 200,
		autoHeight : true,
		items : [{
			name : 'refuseType',
			fieldLabel : '拒绝类型',
			store:refuseTypeStore,
			xtype: 'basecomboselect',
			id : 'idrefuseType',
			hiddenName : 'refuseType',
			width : 150
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '拒绝开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '拒绝结束日期'
		}]
	});

	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 300,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [
			{
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
		        	if(queryForm.getForm().isValid()){
		        		reasonStore.load();
		        	}
			}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
				start: 0,
			    flag:"17",
			    startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
				endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
				refuseType: queryForm.findById('idrefuseType').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
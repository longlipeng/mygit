Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},        // 审核时间           
			{name: 'oprID',mapping: 'oprID'},            //审核人
			{name: 'refuseType',mapping: 'refuseType'},  //操作类型
			{name: 'refuseInfo',mapping: 'refuseInfo'},  //审核备注
			{name: 'param1',mapping: 'param1'},          //商户名
			{name: 'param2',mapping: 'param2'},          //申请备注
			{name: 'param3',mapping: 'param3'},          //申请人
			{name: 'param4',mapping: 'param4'},          //申请时间
			{name: 'param5',mapping: 'param5'},          //当前状态
			{name: 'param6',mapping: 'param6'}           //添加方式
		])
	});
	

	/** *************************查询条件************************ */
	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 300,
		autoHeight : true,
		items : [{
			xtype: 'textfield',
			id: 'saMerChName',
			name: 'saMerChName',
			fieldLabel: '商户名称'
		}]
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户中文名',dataIndex: 'param1',width: 120},
		{header: '添加方式',dataIndex: 'param6',width: 80,renderer:addType},
		{header: '操作类型',dataIndex: 'refuseType',renderer: riskInfoRefuseState},
		//
		{header: '申请时间',dataIndex: 'param4',width: 120,renderer: formatTs},
		//
		{header: '申请人',dataIndex: 'param3',width: 120},
		{header: '申请备注',dataIndex: 'param2',width: 230},
		
		{header: '审核时间',dataIndex: 'txnTime',sortable: true,width: 120,renderer: formatTs},
		{header: '审核人',dataIndex: 'oprID',width: 120},
		
		{header: '审核备注',dataIndex: 'refuseInfo',width: 250},
		{header: '当前状态',dataIndex: 'param5',width: 100,renderer: riskInfoState}
	
		


	]);
	
	function addType(val){
		if(val == '0')
            return "手动添加";
    	if(val == '1')
            return "自动添加";
		
	}
	
	reasonStore.load({
		params: {
			start: 0,
			flag:"2"
		}
	});
	
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
	
	var expMenu = {
			text: '导出',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T40202ExpAction.asp',
					timeout : 600000000,
					params: {
						saMerChName: queryForm.findById('saMerChName').getValue(),
					
				txnId: '40202',
				subTxnId: '04'
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

	menuArr.add(queryConditionMebu); // [0]
	menuArr.add('-');
	menuArr.add(expMenu);
	
	var grid = new Ext.grid.GridPanel({
		title: '商户黑名单操作信息查询',
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
			msg: '正在加载卡黑名单操作信息列表......'
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
	
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 400,
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
			reasonStore.load({
				params : {
					start : 0,
					flag:"2",
					saMerChName: queryForm.findById('saMerChName').getValue()
					}
				});
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
			    flag:"2",
			    saMerChName: queryForm.findById('saMerChName').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	
/*	//拒绝类型
	var refuseTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('REFUSE_TYPE',function(ret){
		refuseTypeStore.loadData(Ext.decode(ret));
	});	*/
	
	
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'}, //拒绝时间
		//	{name: 'brhID',mapping: 'brhID'}, 
			{name: 'oprID',mapping: 'oprID'},  //拒绝人
			{name: 'refuseType',mapping: 'refuseType'},  //拒绝类型
			{name: 'refuseInfo',mapping: 'refuseInfo'},  //拒绝原因
			{name: 'param1',mapping: 'param1'},     //机构号
			{name: 'param2',mapping: 'param2'},     //机构名称
			{name: 'param3',mapping: 'param3'},     //机构类型
			{name: 'param4',mapping: 'param4'}     //交易联接类型
			
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			flag:"92"
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,width: 120,renderer: formatTs},
//		{header: '操作员所属分公司',dataIndex: 'brhID',width: 120},
		{header: '拒绝人',dataIndex: 'oprID',width: 70},
		{header: '拒绝类型',dataIndex: 'refuseType',width:160,renderer: agencyState},
		{header: '机构号',dataIndex: 'param1',width: 90},
		{header: '机构名称',dataIndex: 'param2',width: 130},
		{header: '机构类型',dataIndex: 'param3',width: 150,renderer:agenType},
		{header: '交易联接类型',dataIndex: 'param4',width: 100,renderer:tranType},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 380}
	
	]);
	
	function agenType(val){
		if("01" == val){return "支付"}
		else if("02" == val){return "银联"}
		else if("03" == val){return "银行卡发卡机构"}
		else if("04" == val){return "行业卡发卡机构"}
		else if("05" == val){return "商户拓展机构"}
		else if("06" == val){return "机具提供方"}
		else if("07" == val){return "场地提供方"}
		else if("08" == val){return "其它"}
		else return val;
	}

	
	
	function tranType(val){
		if("01" == val){return "本代本"}
		else if("02" == val){return "本代他"}
		else if("03" == val){return "转接直联"}
		else return val;
	}
	
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
		title: '机构信息审核拒绝原因查看',
		region: 'center',
		iconCls: 'T10115',
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
			msg: '正在加载机构审核拒绝原因列表......'
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
			xtype:'combo',
			name : 'refuseType',
			fieldLabel : '拒绝类型',
			allowBlank : false,
			blankText : '拒绝类型不能为空',
			xtype: 'basecomboselect',
			id : 'idrefuseType',
			hiddenName : 'refuseType',
			width : 150,
			  store: new Ext.data.ArrayStore({
                  fields: ['valueField','displayField'],
                  data: [['0','新增审核拒绝'],['2','修改审核拒绝'],['8','注销审核拒绝']]
              })
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
			reasonStore.load({
				params : {
					start : 0,
					flag:"92",
					refuseType: queryForm.findById('idrefuseType').getValue()
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
			    flag:"13",
			    refuseType: queryForm.findById('idrefuseType').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
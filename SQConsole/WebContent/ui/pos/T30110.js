Ext.onReady(function() {
	
	//卡类型数据集
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	
	//商户名称数据集
	var nameStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_CD',function(ret){
		nameStore.loadData(Ext.decode(ret));
	});
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},   //时间
			{name: 'oprID',mapping: 'oprID'},    //操作员编号
			{name: 'refuseType',mapping: 'refuseType'},  //交易类型
			{name: 'refuseInfo',mapping: 'refuseInfo'}, //拒绝原因
			{name: 'param1',mapping: 'param1'},    //终端号
			{name: 'param2',mapping: 'param2'},    //商户编号
			{name: 'param3',mapping: 'param3'}      //卡类型
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			flag:"8"
		}
	});

	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,width: 120,renderer: formatTs},
		//{header: '商户编号',dataIndex: 'param2',sortable: true,width: 120},
		{header: '商户编号-商户名称',dataIndex: 'param2',width: 230,
			renderer:function(data){
		    	if(null == data) return '';
		    	var record = nameStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }},
		{header: '终端号',dataIndex: 'param1'},
		{header: '卡类型',dataIndex: 'param3',width: 120,
			renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }	},
		{header: '交易操作员',dataIndex: 'oprID',width: 120},
		{header: '交易类型',dataIndex: 'refuseType',width: 90,renderer:statue},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 350}
	]);
	
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '删除';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '删除未审核';
		}
	}
	/*var menuArr = new Array();
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
*/	
	var grid = new Ext.grid.GridPanel({
		title: '终端交易限额拒绝原因查询',
		region: 'center',
		iconCls: 'T30110',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
	//	tbar : menuArr,
		//autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载终端交易限额拒绝原因列表......'
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
/*	
	*//** *************************查询条件************************ *//*
	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 200,
		autoHeight : true,
		items : [{
			name : 'refuseType',
			fieldLabel : '拒绝类型*',
			allowBlank : false,
			blankText : '拒绝类型不能为空',
			store:refuseTypeStore,
			xtype: 'basecomboselect',
			id : 'idrefuseType',
			hiddenName : 'refuseType',
			width : 150
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
					flag:"2",
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
	*/
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
				start: 0,
			    flag:"8"
		});
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstBlackRegions'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			//regionType,regionNo,regionName,regionHome,updateTime
			{name: 'regionId',mapping: 'regionId'},    
			{name: 'regionType',mapping: 'regionType'},              
			{name: 'regionNo',mapping: 'regionNo'},              
			{name: 'regionName',mapping: 'regionName'},              
			{name: 'regionHome',mapping: 'regionHome'},       
			{name: 'updateTime',mapping: 'updateTime'}
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
			id: 'regionNo',
			name: 'regionNo',
			fieldLabel: '地区代码'
		},{ 
			xtype: 'textfield',
			id: 'regionName',
			name: 'regionName',
			fieldLabel: '地区名称'
	    },{ 
	    	xtype: 'textfield',
			id: 'regionHome',
			name: 'regionHome',
			fieldLabel: '数据来源'
	    }]
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),//regionType,regionNo,regionName,regionHome,updateTime
		{header: '编号',dataIndex: 'regionId',width: 120,hidden:true},
		{header: '地区类型',dataIndex: 'regionType',width: 120,hidden:false},
		{header: '地区码',dataIndex: 'regionNo',width: 180},
		{header: '地区名称',dataIndex: 'regionName',width: 180},
		{header: '数据来源',dataIndex: 'regionHome',width: 180},
		{header: '导入时间',dataIndex: 'updateTime',width: 180}
	]);
	reasonStore.load({
		params: {
			start: 0
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
	menuArr.add(queryConditionMebu); // [0]
	menuArr.add('-');
	var grid = new Ext.grid.GridPanel({
		title: '反洗钱-地区黑名单操作信息查询',
		region: 'center',
		iconCls: 'T20103',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		tbar : menuArr,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载地区黑名单操作信息列表......'
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
				params : {//regionNo,regionName,regionHome
					start : 0,
					regionNo: queryForm.findById('regionNo').getValue(),
					regionName: queryForm.findById('regionName').getValue(),
					regionHome: queryForm.findById('regionHome').getValue()
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
			start : 0,
			regionNo: queryForm.findById('regionNo').getValue(),
			regionName: queryForm.findById('regionName').getValue(),
			regionHome: queryForm.findById('regionHome').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
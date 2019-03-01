Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstBlackObserve'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			//blackObserveId,blackObserveName,blackObserveSex,blackObserveBir
			//blackObserveCountry,blackObserveNo,blackObserveType,blackObserveHome,blackObserveEntity,blackObserveAddress,blackObserveCon,blackObserveUpdatetime
			{name: 'blackObserveId',mapping: 'blackObserveId'},    
			{name: 'blackObserveName',mapping: 'blackObserveName'},              
			{name: 'blackObserveSex',mapping: 'blackObserveSex'},              
			{name: 'blackObserveBir',mapping: 'blackObserveBir'},              
			{name: 'blackObserveCountry',mapping: 'blackObserveCountry'},       
			{name: 'blackObserveNo',mapping: 'blackObserveNo'},
			{name: 'blackObserveType',mapping: 'blackObserveType'},              
			{name: 'blackObserveHome',mapping: 'blackObserveHome'},              
			{name: 'blackObserveEntity',mapping: 'blackObserveEntity'},              
			{name: 'blackObserveAddress',mapping: 'blackObserveAddress'},       
			{name: 'blackObserveCon',mapping: 'blackObserveCon'},
			{name: 'blackObserveUpdatetime',mapping: 'blackObserveUpdatetime'}
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
			id: 'blackObserveName',
			name: 'blackObserveName',
			fieldLabel: '名称'
		},{ 
        	xtype: 'textfield',
			id: 'blackObserveCountry',
			name: 'blackObserveCountry',
			fieldLabel: '国家'
        },{ 
			xtype: 'textfield',
			id: 'blackObserveNo',
			name: 'blackObserveNo',
			fieldLabel: '证件'
        },{ 
        	xtype: 'textfield',
			id: 'blackObserveHome',
			name: 'blackObserveHome',
			fieldLabel: '名单来源'
        },{ 
			xtype: 'textfield',
			id: 'blackObserveEntity',
			name: 'blackObserveEntity',
			fieldLabel: '实体类型'
        }]//blackObserveName blackObserveCountry  blackObserveNo blackObserveHome blackObserveEntity
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),//blackObserveId,blackObserveName,blackObserveSex,blackObserveBir
		//blackObserveCountry,blackObserveNo,blackObserveType,blackObserveHome,blackObserveEntity,blackObserveAddress,blackObserveCon,blackObserveUpdatetime
		{header: '编号',dataIndex: 'blackObserveId',width: 80,hidden:true},
		{header: '名称',dataIndex: 'blackObserveName',width: 180},
		{header: '性别',dataIndex: 'blackObserveSex',width: 50},
		{header: '出生/注册时间',dataIndex: 'blackObserveBir',width: 80},
		{header: '国家',dataIndex: 'blackObserveCountry',width: 80},
		{header: '证件',dataIndex: 'blackObserveNo',width: 50},
		{header: '名单类别',dataIndex: 'blackObserveType',width: 50},
		{header: '名单来源',dataIndex: 'blackObserveHome',width: 120},
		{header: '实体类型',dataIndex: 'blackObserveEntity',width: 80},
		{header: '地址',dataIndex: 'blackObserveAddress',width: 80},
		{header: '补充信息',dataIndex: 'blackObserveCon',width: 80},
		{header: '更新时间',dataIndex: 'blackObserveUpdatetime',width: 120}
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
		title: '反洗钱-观察黑名单操作信息查询',
		region: 'center',
		iconCls: 'T20104',
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
			msg: '正在加载观察黑名单操作信息列表......'
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
				params : {////blackObserveName blackObserveCountry  blackObserveNo blackObserveHome blackObserveEntity
					start : 0,
					blackObserveName: queryForm.findById('blackObserveName').getValue(),
					blackObserveCountry: queryForm.findById('blackObserveCountry').getValue(),
					blackObserveNo: queryForm.findById('blackObserveNo').getValue(),
					blackObserveHome: queryForm.findById('blackObserveHome').getValue(),
					blackObserveEntity: queryForm.findById('blackObserveEntity').getValue()
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
				blackObserveName: queryForm.findById('blackObserveName').getValue(),
				blackObserveCountry: queryForm.findById('blackObserveCountry').getValue(),
				blackObserveNo: queryForm.findById('blackObserveNo').getValue(),
				blackObserveHome: queryForm.findById('blackObserveHome').getValue(),
				blackObserveEntity: queryForm.findById('blackObserveEntity').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
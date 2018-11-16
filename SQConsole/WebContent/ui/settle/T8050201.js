Ext.onReady(function() {
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bthNewBatMainInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'newDateSettlmt',mapping: 'newDateSettlmt'},
			{name: 'newExecute',mapping: 'newExecute'},
			{name: 'newState',mapping: 'newState'},
			{name: 'newDis',mapping: 'newDis'}
		])
	});
	
	/*gridStore.load({
		params: {
			start: 0
		}
	});*/
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '日期',dataIndex: 'newDateSettlmt',width: 120,renderer: formatDt},
		{header: '描述',dataIndex: 'newDis',width: 300,id: 'batDsp'},
		{header: '运行状态',dataIndex: 'newState',width: 120,renderer: runState}
	]);
	
	var menuArr = new Array();

	var monitorMenu = new Ext.Button({
		iconCls: 'play',
		id:'idmonitor',
		text: '启动监控模式',
		enableToggle: true,
		toggleHandler: function(bt,state) {
			// 监控模式
			if(state) {
				this.setText('停止监控模式');
				this.setIconClass('monitor');
				Ext.TaskMgr.start(task);
			} else { // 手工查询模式
				this.setText('启动监控模式');
				this.setIconClass('play');
				Ext.TaskMgr.stop(task);
			}
		}
	});
	menuArr.push(monitorMenu);
	
	initTime();
	function initTime(){
		setTimeout(function() {
			Ext.getCmp('idmonitor').toggle(true);
		},100);
	}

	// 监控定时器：刷新频率为半分钟
	var task = {
		run: function() {
			gridStore.load({
				params: {start: 0}
			})
		},
		interval: 10000
	}
	
	var grid = new Ext.grid.GridPanel({
		title: '先结后算监控[监控日期：'+date+']',
		region: 'center',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		//autoExpandColumn: 'batDsp',
		loadMask: {
			msg: '正在加载先结后算信息列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: gridStore,
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
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startTime : date,
			endTime : date
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
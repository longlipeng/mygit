Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblMsThread1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'threadsname',mapping: 'threadsname'},
			{name: 'threadsnumber',mapping: 'threadsnumber'},
			{name: 'servername',mapping: 'servername'},
			{name: 'threaddis',mapping: 'threaddis'},
			{name: 'shouldnumber',mapping: 'shouldnumber'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			flag:"app2"
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '应用服务名',dataIndex: 'threadsname',sortable: true,width: 150},
		{header: '状态',dataIndex: 'threadsnumber',width: 60,renderer:state},
		{header: '启动服务个数',dataIndex: 'threadsnumber',width: 100},
		{header: '服务启动最大数',dataIndex: 'shouldnumber',width: 100},
		{header: '所属服务器',dataIndex: 'servername',width: 80,renderer:name},
		{header: '应用服务描述',dataIndex: 'threaddis',width: 180}
	]);
	
	function state(val) {
		if(val == '0') {
			return '<font color="red">异常</font>';
		} else {
			return '<font color="green">正常</font>';
		}
		return val;
	}
	function name(val) {
		if(val == 'app1') {
			return '服务器一';
		} 
		if(val == 'app2'){
			return '服务器二';
		}
		return val;
	}
	var menuArr = new Array();
	
	var monitorMenu = new Ext.Button({
		iconCls: 'play',
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
	
	// 监控定时器：刷新频率为半分钟
	var task = {
		run: function() {
			reasonStore.load({
				params: {start: 0,flag:"app2"}
			})
		},
		interval: 30000
	}
	
	var grid = new Ext.grid.GridPanel({
		title: '银行卡收单应用服务监控',
		region: 'center',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载应用服务监控列表......'
		},
		tbar: menuArr,
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
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
				start: 0,
			    flag:"app2"
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
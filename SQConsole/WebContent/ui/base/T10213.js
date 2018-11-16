Ext.onReady(function() {
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblOnlineTransMonitor'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'monitortime',mapping: 'monitortime'},
			{name: 'failurepro',mapping: 'failurepro'},
			{name: 'failurecount',mapping: 'failurecount'},
			{name: 'sumcount',mapping: 'sumcount'},
			{name: 'transtype',mapping: 'transtype'},
			{name: 'instid',mapping: 'instid'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '监控时间',dataIndex: 'monitortime',width: 120,renderer:formatTs},
		{header: '失败比例',dataIndex: 'failurepro',width: 80},
		{header: '失败笔数',dataIndex: 'failurecount',width: 100},
		{header: '总笔数',dataIndex: 'sumcount',width: 100},
		{header: '类型',dataIndex: 'transtype',width: 80,renderer:monitorType},
		{header: '交易机构',dataIndex: 'instid',width: 180}
	]);
	function monitorType(val) {
		if(val == '0') 
			return '失败';
		if(val == '1')
			return '拒绝';
		if(val == '2')
			return '超时';
	}
	
	var menuArr = new Array();
	var queryCondition = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};

	menuArr.push(queryCondition);
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
				grid.getTopToolbar().items.items[0].disable();
			} else { // 手工查询模式
				this.setText('启动监控模式');
				this.setIconClass('play');
				Ext.TaskMgr.stop(task);
				grid.getTopToolbar().items.items[0].enable();
			}
		}
	});
	menuArr.push(monitorMenu);
	
	// 监控定时器：刷新频率为半分钟
	var task = {
		run: function() {
			reasonStore.load({
				params: {start: 0}
			})
		},
		interval: 30000
	}
	
	var grid = new Ext.grid.GridPanel({
		title: '联机交易监控',
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
			msg: '正在加载联机交易监控列表......'
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
	
	/** *************************查询条件************************ */

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 250,
		autoHeight : true,
		items : [ {
			xtype: 'basecomboselect',
			store:agencyStore,
			id : 'searchinstId',
			width : 180,
			name : 'instId',
			fieldLabel : '机构'
		},{
			xtype: 'datefield',
			fieldLabel: '开始日期',
			id:'searchstartTime',
			name: 'startTime',
			emptyText: '格式：yyyymmdd',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('searchendTime').getValue()!='' && 
							(Ext.getCmp('searchstartTime').getValue().format('Ymd') > Ext.getCmp('searchendTime').getValue().format('Ymd'))){
						showAlertMsg("开始日期不能大于截止日期，请重新选择！",queryForm);
						Ext.getCmp('searchstartTime').setValue('');
						Ext.getCmp('searchendTime').setValue('');}
				}
			}
		},{
			xtype: 'datefield',
			fieldLabel: '结束日期',
			emptyText:'格式：yyyymmdd',
			id:'searchendTime',
			name: 'endTime',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('searchstartTime').getValue()!='' && 
							(Ext.getCmp('searchstartTime').getValue().format('Ymd') > Ext.getCmp('searchendTime').getValue().format('Ymd'))){
						showAlertMsg("开始日期不能大于截止日期，请重新选择！",queryForm);
						Ext.getCmp('searchstartTime').setValue('');
						Ext.getCmp('searchendTime').setValue('');}
				}
			}
		}]
	});

	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 350,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [{
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
				reasonStore.load();
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
			instId : queryForm.findById('searchinstId').getValue(),
			startTime : queryForm.findById('searchstartTime').getValue(),
			endTime : queryForm.findById('searchendTime').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
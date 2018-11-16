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
	
	gridStore.load({
		params: {
			start: 0
		}
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '日期',dataIndex: 'newDateSettlmt',width: 120,renderer: formatDt},
		{header: '描述',dataIndex: 'newDis',width: 300,id: 'batDsp'},
		{header: '运行状态',dataIndex: 'newState',width: 120,renderer: runState}
	]);
	
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
	
	var sendCondition = {
		text : '重新发起',
		width : 85,
		id : 'send',
		//iconCls : 'query',
		handler : function() {
			sendWin.show();
		}
	};

	menuArr.push(queryCondition);
	menuArr.push('-');
	menuArr.push(sendCondition);

	var grid = new Ext.grid.GridPanel({
		title: '先结后算查询',
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
	
	/** *************************查询条件************************ */

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 250,
		autoHeight : true,
		items : [{
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
				if(queryForm.getForm().isValid()){
					gridStore.load();
	        	}
			}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	var sendForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 250,
		autoHeight : true,
		items : [{
			xtype: 'datefield',
			fieldLabel: '日期',
			id:'sendTime',
			name: 'sendTime',
			emptyText: '格式：yyyymmdd',
			width: 120
		}]
	});
	
	var sendWin = new Ext.Window( {
		title : '重新发起日期',
		layout : 'fit',
		width : 350,
		autoHeight : true,
		items : [ sendForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'send',
		buttons : [ {
			text : '确定',
			handler : function() {
				if(sendForm.getForm().isValid()){
					showMask('正在处理，请稍后......',grid);
					sendConsole("DDDDD",Ext.getCmp('sendTime').getValue().format('Ymd'));
	        	}
			}
		}, {
			text : '取消',
			handler : function() {
				sendForm.getForm().reset();
				sendWin.hide();
			}
		} ]
	});
	
	function sendConsole(value,date){
		HandleOfBatch.sendXjhsMsg(value,date,function(ret){
			if("s"==ret.substr(0,1)){
				hideMask();
				showSuccessAlert("先结后算处理成功！",grid);
				sendForm.getForm().reset();
				sendWin.hide();
			}else{
				hideMask();
				showErrorMsg(ret,grid);
			}
			gridStore.load();
		});
	}
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
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
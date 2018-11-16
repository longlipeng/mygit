Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstRegions'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//id,lstp,rgtp,rgcd,rgnm,remark,status,create_time,creator,update_time,updator
			{name: 'id',mapping: 'id'},              
			{name: 'lstp',mapping: 'lstp'},              
			{name: 'rgtp',mapping: 'rgtp'},              
			{name: 'rgcd',mapping: 'rgcd'},       
			{name: 'rgnm',mapping: 'rgnm'},  
			{name: 'remark',mapping: 'remark'},  
			{name: 'status',mapping: 'status'},        
			{name: 'create_time',mapping: 'create_time'},     
			{name: 'creator',mapping: 'creator'},   
			{name: 'update_time',mapping: 'update_time'},   
			{name: 'updator',mapping: 'updator'}
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
			id: 'rgnm',
			name: 'rgnm',
			fieldLabel: '行政地区名称'
		},{ 
                xtype: 'combo',
                fieldLabel: '状态',
                id: 'status',
                name: 'status',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['1','有效'],['0','无效']]
                })
        },{ 
                xtype: 'combo',
                fieldLabel: '地区类型',
                id: 'rgtp',
                name: 'rgtp',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','国家'],['1','省'],['2','市'],['2','县']]
                })
        }]
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),//id,lstp,rgtp,rgcd,rgnm,remark,status,create_time,creator,update_time,updator
		{header: 'id',dataIndex: 'id',width: 10,hidden:true},
		{header: '名单种类',dataIndex: 'lstp',width: 80},
		{header: '地区类型',dataIndex: 'rgtp',width: 80,renderer:rgtpType},
		{header: '行政地区代码',dataIndex: 'rgcd',width: 120},
		{header: '行政地区名称',dataIndex: 'rgnm',width: 120},
		{header: '备注',dataIndex: 'remark',width: 120},
		{header: '状态',dataIndex: 'status',width: 120,renderer:statusType},
		{header: '创建时间',dataIndex: 'create_time',width: 120,renderer: formatTs},
		{header: '创建人',dataIndex: 'creator',width: 120},
		{header: '更新时间',dataIndex: 'update_time',width: 120,renderer: formatTs},
		{header: '更新人',dataIndex: 'updator',width: 100}
	]);
	function rgtpType(val){
		if(val == '0')
            return "<font color='gray'>国家</font>";
    	if(val == '1')
            return "<font color='red'>省</font>";
    	if(val == '2')
            return "<font color='green'>市</font>";
    	if(val == '3')
            return "<font color='green'>县</font>";
	}
	function statusType(val){
		if(val == '1')
            return "<font color='gray'>有效</font>";
    	if(val == '0')
            return "<font color='red'>无效</font>";
	}
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
		title: '商户地区黑名单操作信息查询',
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
					rgnm: queryForm.findById('rgnm').getValue(),
					status: queryForm.findById('status').getValue(),
					rgtp: queryForm.findById('rgtp').getValue()
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
			    rgnm: queryForm.findById('rgnm').getValue(),
				status: queryForm.findById('status').getValue(),
				rgtp: queryForm.findById('rgtp').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
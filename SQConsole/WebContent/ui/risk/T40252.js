Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lstEntitys'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//		cttp,ctcr,ctnm,ciid,cleg,clid,care,lstp,create_time,cre_opr,update_time,upd_opr,ckstatus
			{name: 'id',mapping: 'id'},              
			{name: 'cttp',mapping: 'cttp'},              
			{name: 'ctcr',mapping: 'ctcr'},       
			{name: 'ctnm',mapping: 'ctnm'},  
			{name: 'ciid',mapping: 'ciid'},  
			{name: 'cleg',mapping: 'cleg'},        
			{name: 'clid',mapping: 'clid'},         
			{name: 'care',mapping: 'care'},         
			{name: 'lstp',mapping: 'lstp'},         
			{name: 'create_time',mapping: 'create_time'},     
			{name: 'creator',mapping: 'creator'},   
			{name: 'update_time',mapping: 'update_time'},   
			{name: 'updator',mapping: 'updator'},
			{name: 'ckstatus',mapping: 'ckstatus'}
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
			id: 'ctnm',
			name: 'ctnm',
			fieldLabel: '商户名称'
		},{
			xtype: 'textfield',
			id: 'care',
			name: 'care',
			fieldLabel: '所属地区'
		},{
			xtype: 'textfield',
			id: 'ciid',
			name: 'ciid',
			fieldLabel: '证件号码'
		},{ 
                xtype: 'combo',
                fieldLabel: '审核状态',
                id: 'ckstatus',
                name: 'ckstatus',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['1','已删除'],['2','正常'],['3','修改审核拒绝'],['4','删除审核拒绝'],['5','新增审核拒绝'],['6','新增删除'],['7','修改待审核']]
                })
        },{ //lstp
                xtype: 'combo',
                fieldLabel: '名单类别',
                id: 'lstp',
                name: 'lstp',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','黑名单'],['1','白名单'],['2','关注名单']]
                })
        }]
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: 'id',dataIndex: 'id',width: 10,hidden:true},
		{header: '商户类型',dataIndex: 'cttp',width: 120,renderer:tenantType},
		{header: '商户国籍',dataIndex: 'ctcr',width: 80},
		{header: '商户名称',dataIndex: 'ctnm',width: 120},
		{header: '证件号码',dataIndex: 'ciid',width: 120},
		{header: '法人代表',dataIndex: 'cleg',width: 120},
		{header: '法人证件号码',dataIndex: 'clid',width: 120},
		{header: '所属地区',dataIndex: 'care',width: 120},
		{header: '名单类别',dataIndex: 'lstp',width: 120,renderer:lstpType},//0:黑名单,1:白名单,2:关注名单
		{header: '创建时间',dataIndex: 'create_time',width: 120,renderer: formatTs},
		{header: '创建人',dataIndex: 'creator',width: 120},
		{header: '更新时间',dataIndex: 'update_time',width: 120,renderer: formatTs},
		{header: '更新人',dataIndex: 'updator',width: 100},
		{header: '审核状态',dataIndex: 'ckstatus',width: 100,renderer: checkState}
	]);
	function lstpType(val){
		if(val == '0')
            return "<font color='gray'>黑名单</font>";
    	if(val == '1')
            return "<font color='red'>白名单</font>";
    	if(val == '2')
            return "<font color='green'>关注名单</font>";
	}
	function tenantType(val){
		if(val == '1')
            return "<font color='gray'>个人</font>";
    	if(val == '2')
            return "<font color='red'>机构</font>";
	}
	function checkState(val){
		if(val == '0')
            return "<font color='gray'>新增待审核</font>";
    	if(val == '1')
            return "<font color='red'>已删除</font>";
    	if(val == '2')
            return "<font color='green'>正常</font>";
    	if(val == '3')
            return "<font color='gray'>修改审核拒绝</font>";
    	if(val == '4')
    		return "<font color='gray'>删除审核拒绝</font>";
    	if(val == '5')
    		return "<font color='gray'>新增审核拒绝</font>";
    	if(val == '6')
    		return "<font color='gray'>新增删除</font>";
    	if(val == '7')
    		return "<font color='gray'>修改待审核</font>";
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
					ctnm: queryForm.findById('ctnm').getValue(),
					care: queryForm.findById('care').getValue(),
					ciid: queryForm.findById('ciid').getValue(),
					ckstatus: queryForm.findById('ckstatus').getValue(),
					lstp: queryForm.findById('lstp').getValue()
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
			    ctnm: queryForm.findById('ctnm').getValue(),
				care: queryForm.findById('care').getValue(),
				ciid: queryForm.findById('ciid').getValue(),
				ckstatus: queryForm.findById('ckstatus').getValue(),
				lstp: queryForm.findById('lstp').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'actNoQ',
                name: 'actNoQ',
                fieldLabel: '活动编号'
            }),new Ext.form.TextField({
                id: 'actNameQ',
                name: 'actNameQ',
                fieldLabel: '活动名称'
            }),{
				width: 130,
				xtype: 'datefield',
				fieldLabel: '起始时间*',
				allowBlank: false,
				format : 'Ymd',
				name :'startDateQ',
				id :'startDateQ'
		  },{											
				width: 130,
				xtype: 'datefield',
				fieldLabel: '截止时间*',
				allowBlank: false,
				format : 'Ymd',
				name :'endDateQ',
				id :'endDateQ'
		   }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                actStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=marketAct'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'state',mapping: 'state'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'actCycle',mapping: 'actCycle'},
			{name: 'organNo',mapping: 'organNo'},
			{name: 'organType',mapping: 'organType'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'remarks',mapping: 'remarks'},
			{name: 'flag01',mapping: 'flag01'},
			{name: 'crtDt',mapping: 'crtDt'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'updDt',mapping: 'updDt'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'recOpr',mapping: 'recOpr'},
			{name: 'misc1',mapping: 'misc1'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNoQ').getValue(),
            actName: Ext.getCmp('actNameQ').getValue(),
            startDate: topQueryPanel.getForm().findField('startDateQ').getValue(),
            endDate: topQueryPanel.getForm().findField('endDateQ').getValue()
        });
    }); 
	
	actStore.load();
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>新增柜员：{crtOpr}</p>',
			'<p>新增时间：{crtDt}</p>',
			'<p>修改柜员：{updOpr}</p>',
			'<p>修改时间：{updDt}</p>',
			'<p>审核柜员：{recOpr}</p>',
			'<p>审核时间：{misc1}</p>'
		)
	});
	
	var actColModel = new Ext.grid.ColumnModel([
	rowExpander,
		{header: '所属机构',dataIndex: 'bankNo'},
		{id: 'actNo',header: '活动编号',dataIndex: 'actNo',width: 100},
		{header: '活动名称',dataIndex: 'actName'},
		{header: '操作',dataIndex: 'flag01',renderer: actOprState},
		{header: '活动状态',dataIndex: 'state',renderer: actState},
		{header: '起始日期',dataIndex: 'startDate'},
		{header: '截止日期',dataIndex: 'endDate'},
		{header: '自动清算单位编号',dataIndex: 'organNo',width: 150},
		{header: '自动清算业务种类编号',dataIndex: 'organType',width: 150}
	]);
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntParticipat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'state',mapping: 'state'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'actFee',mapping: 'actFee'},
			{name: 'flag01',mapping: 'flag01'}
		])
	});
	
	mchntStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNo').getValue()
        });
    }); 
	
    
    

	var mchntColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'mchntNo',header: '商户编号',dataIndex: 'mchntNo',width: 250},
		{header: '商户名称',dataIndex: 'mchtNm',width: 150},
		{header: '活动名称',dataIndex: 'actName'},
		{header: '所属机构',dataIndex: 'bankNo'},
		{header: '操作',dataIndex: 'flag01',renderer: mchntOprState},
		{header: '活动状态',dataIndex: 'state',renderer: actState},
		{header: '起始日期',dataIndex: 'startDate'},
		{header: '截止日期',dataIndex: 'endDate'},
		{header: '费率(%)',dataIndex: 'actFee'}
	]);
	
	var mchntGrid = new Ext.grid.EditorGridPanel({
		title: '商户营销活动商户',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: false,
		forceValidation: true,
		loadMask: {
			msg: '正在加载参与营销活动的商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: 200,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	var editMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            actForm.getForm().loadRecord(selectedRecord);
			actWin.show();
			actWin.center();
		}
	}
   
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel],
        buttonAlign: 'center',
        closeAction: 'hide',
        resizable: false,
        closable: true,
        animateTarget: 'query',
        tools: [{
            id: 'minimize',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.maximize.show();
                toolEl.hide();
                queryWin.collapse();
                queryWin.getEl().pause(1);
                queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
            },
            qtip: '最小化',
            hidden: false
        },{
            id: 'maximize',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.minimize.show();
                toolEl.hide();
                queryWin.expand();
                queryWin.center();
            },
            qtip: '恢复',
            hidden: true
        }]
    });
    
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var menuArr = new Array();
	
	menuArr.push(queryMenu);	//[0]
	menuArr.push(editMenu);		//[1]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '商户营销活动查询',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: actColModel,
		clicksToEdit: true,
		plugins: rowExpander,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载营销活动信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('state') != '2' ) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
   
     
    /**************  商户营销活动表单  *********************/
	var actForm = new Ext.form.FormPanel({
		frame: true,
		height: 300,
		width: 500,
		labelWidth: 150,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
			    columnWidth: .6,
            	layout: 'form',
			    items: [{
                    xtype: 'combo',
                    fieldLabel: '所属分行*',
                    id: 'bankNoU',
                    hiddenName: 'bankNo',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    readOnly: true,
                    width: 130
                 }]
            },{
			    columnWidth: .6,
            	layout: 'form',
			    items: [{	
			         	xtype: 'textfield',
			 			fieldLabel: '商业营销活动编号*',
			 			id: 'actNo',
			 			name: 'actNo',
			 			allowBlank: false,
			 			readOnly: true
				}]
			},{
			    columnWidth: .6,
            	layout: 'form',
			    items: [{	
			         	xtype: 'textfield',
			 			fieldLabel: '商业营销活动名称*',
			 			id: 'actName',
			 			name: 'actName',
			 			readOnly: true,
			 			allowBlank: false
				}]
			},{
				columnWidth: .6,
            	layout: 'form',
			    items: [{
						xtype: 'combo',
						fieldLabel: '状态*',
						allowBlank: false,
						hiddenName :'state',
						id :'stateUpd',
						displayField: 'displayField',
                    	valueField: 'valueField',
                    	readOnly: true,
						store: new Ext.data.ArrayStore({
		                    fields: ['valueField','displayField'],
		                    data: [['0','正常'],['1','未审核'],['2','关闭']]
		                })
			    }]
			},{
			    columnWidth: .6,
            	layout: 'form',
			    items: [{	
			         	xtype: 'datefield',
						fieldLabel: '活动起始时间*',
						allowBlank: false,
						format: 'Ymd',
						name :'startDate',
						id :'startDate',
						readOnly: true
				}]
			},{
				columnWidth: .6,
            	layout: 'form',
			    items: [{
						xtype: 'datefield',
						fieldLabel: '活动截止时间*',
						allowBlank: false,
						readOnly: true,
						format: 'Ymd',
						name :'endDate',
						id :'endDate'
			    }]
			},{
	            columnWidth: .6,
	            layout: 'form',
	            items: [{
	                    xtype: 'textfield',
	                    fieldLabel: '自动清算单位编号 *',
	                    id: 'organNo',
	                    name: 'organNo',
	                    allowBlank: false,
	                    maxLength: 8,
	                    readOnly: true,
	                    vtype: 'isNumber'
	                 }]
	        },{
	            columnWidth: .6,
	            layout: 'form',
	            items: [{
	                    xtype: 'textfield',
	                    fieldLabel: '自动清算业务种类编号* ',
	                    id: 'organType',
	                    name: 'organType',
	                    allowBlank: false,
	                    maxLength: 4,
	                    readOnly: true,
	                    vtype: 'isNumber'
	                 }]
	        },{
		   	 	columnWidth: .8,
	            layout: 'form',
	            items: [{
	            		width: 200,
	                    xtype: 'combo',
		                fieldLabel: '营销活动算法',
		                id: 'actContentA',
		                hiddenName: 'actContent',
		                readOnly: true,
		                store: new Ext.data.ArrayStore({
		                    fields: ['valueField','displayField'],
		                    data: [['0','算法1：应收商户手续费*费率'],['1','算法2：应付银联手续费*费率'],['2','算法3：手续费净额*费率']]
		                })
	                 }]
	        },{
		    layout: 'form',
		    columnWidth: .9,
		    items: [{	
		         	xtype: 'textarea',
		 			fieldLabel: '备注描述',
		 			id: 'remarks',
		 			name: 'remarks',
		 			width: 300,
		 			readOnly: true,
		 			maxLength: 40
		 			}]
		 }]
	});
   
    /***********  商户营销活动窗口  *****************/
	var actWin = new Ext.Window({
		title: '商户营销活动信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [actForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '参与商户',
			handler: function() {
				mchntStore.load();
				mchntWin.show();
				mchntWin.center();
			}
		},{
			text: '关闭',
			handler: function() {
				actWin.hide();
			}
		}]
	});
	
	var mchntWin = new Ext.Window({
		title: '参与商户信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		height: 500,
		layout: 'fit',
		items: [mchntGrid],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '关闭',
			handler: function() {
				mchntWin.hide();
			}
		}]
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})
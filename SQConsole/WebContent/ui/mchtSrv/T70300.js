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
        labelWidth: 100,
		items: [new Ext.form.TextField({
				id: 'qryOrgId',
				name: 'qryOrgId',
				fieldLabel: '专业服务机构号'
			}),{											
				xtype: 'combo',
				fieldLabel: '所属机构',
				hiddenName: 'qBranch',
				id: 'qryBranch',
				store: brhStore
		    }],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				organStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

	var organStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=organInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'OrgId',mapping: 'OrgId'},
			{name: 'Branch',mapping: 'Branch'},
			{name: 'OrgName',mapping: 'OrgName'},
			{name: 'Rate',mapping: 'Rate'},
			{name: 'rateType',mapping: 'RateType'},
			{name: 'Remarks',mapping: 'Remarks'},
			{name: 'Misc',mapping: 'Misc'}
		])
	});
	
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'OrgId',header: '专业服务机构号',dataIndex: 'OrgId',width: 100},
        {header: '所属机构',dataIndex: 'Branch',width: 100},
		{header: '专业服务机构名称',dataIndex: 'OrgName',width: 200},
		{header: '费率(%)',dataIndex: 'Rate'},
		{header: '备注',dataIndex: 'Remarks'}
	]);
	
	
	var updMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
			updateForm.getForm().loadRecord(selectedRecord);
			updateWin.show();
			updateWin.center();
		}
	};
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
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
	var menuArr = new Array();
	
	menuArr.push(queryMenu);
	menuArr.push(updMenu);	

	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '专业服务机构查询',
		iconCls: 'T70300',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: organStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: organStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getTopToolbar().items.items[1].disable();

	//每次在列表信息加载前都将保存按钮屏蔽
	organStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
			orgId: Ext.getCmp('qryOrgId').getValue(),
		    branch: Ext.getCmp('qryBranch').getValue()
		});
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			if(rec != null) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
	
	
	var updateForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '专业服务机构号*',
		 					name: 'OrgId',
		 					id: 'OrgId',
		 					allowBlank: false,
		 					readOnly: true
		 		        }]
		 		},{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'combo',
		 					fieldLabel: '所属机构*',
		 					hiddenName: 'BrancH',
		 					id: 'Branch',
		 					store: brhStore,
		 					displayField: 'displayField',
		 					valueField: 'valueField',
		 					allowBlank: false,
		 					readOnly: true
		 		        }]
		 		},{
			         	layout: 'form',
			         	width: 389,
			         	items: [{	
			         		xtype: 'textfield',
			 				fieldLabel: '专业服务机构名称*',
			 				id: 'OrgName',
			 				name: 'OrgName',
			 				allowBlank: false,
			 				blankText: '机构名称不能为空',
		 					readOnly: true
			         	}]
			   },{
			         	layout: 'form',
			         	width: 389,
			         	items: [{	
			         		xtype: 'numberfield',
			 				fieldLabel: '费率(%)*',
			 				id: 'Rate',
			 				name: 'Rate',
			 				allowBlank: false,
			 				vtype: 'isNumber',
		 					readOnly: true
			         	}]
			   },{
		            layout: 'form',
		            width: 389,
		            items: [{
		            		width: 200,
		                    xtype: 'combo',
			                fieldLabel: '分润类型',
			                id: 'rateTypeA',
			                hiddenName: 'rateType',
			                readOnly: true,
			                store: new Ext.data.ArrayStore({
			                    fields: ['valueField','displayField'],
			                    data: [['0','算法1：应收商户手续费*费率'],['1','算法2：应付银联手续费*费率'],['2','算法3：手续费净额*费率']]
			                })
		                 }]
	        	},{
		         	layout: 'form',
		         	width: 389,
		         	items: [{	
		         		xtype: 'textarea',
		 				fieldLabel: '备注描述',
		 				id: 'Remarks',
		 				name: 'Remarks',
		 				width: 200,
		 				maxLength: 250,
		 				readOnly: true
		 			}]
		         }
		]
	});

	//终端信息窗口
	var updateWin = new Ext.Window({
		title: '专业服务机构信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [updateForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '关闭',
			handler: function() {
				updateWin.hide();
			}
		}]
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})
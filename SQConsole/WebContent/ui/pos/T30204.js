Ext.onReady(function() {
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
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
        items: [/*new Ext.form.TextField({
                id: 'termId',
                name: 'termId',
                fieldLabel: '终端号'
            }),*//*{
            	fieldLabel: '商户编号',
				xtype: 'dynamicCombo',
				methodName: 'getMchntNo',
				id: 'mchntNoQ',
				hiddenName: 'mchntNo',
				editable: true,
				width: 320
			},*/{
                xtype: 'dynamicCombo',
                fieldLabel: '商户编号',
                //methodName: 'getMchntIdTerm',
                //methodName: 'getMchntId',
                methodName: 'getMchntNo',
                hiddenName: 'mchnNo',
                id: 'mchntNoQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 300,
				/*listeners: {
					'select': function() {
						Ext.getCmp('termNoQ').reset();
						Ext.getCmp('termNoQ').parentP=this.value;
						Ext.getCmp('termNoQ').getStore().reload();
					}
				}*/
                callFunction: function() {
					Ext.getCmp('termNoQ').reset();
					Ext.getCmp('termNoQ').parentP=this.value;
					Ext.getCmp('termNoQ').getStore().reload();
				}
           },{
               xtype: 'dynamicCombo',
               fieldLabel: '终端号',
               methodName: 'getTermIdAll',
               hiddenName: 'termNo',
               id: 'termNoQ',
               displayField: 'displayField',
               valueField: 'valueField',
               width: 300
          },{ 
                xtype: 'combo',
                fieldLabel: '申请状态',
                hiddenName: 'state',
                id: 'stateQ',
                displayField: 'displayField',
                valueField: 'valueField',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['2','未申请'],['1','已审核']]
                })
            },{
				xtype: 'datefield',
				name: 'startDate',
				id: 'startDate',
				fieldLabel: '起始创建日期'
			},{
				xtype: 'datefield',
				name: 'endDate',
				id: 'endDate',
				fieldLabel: '结束创建日期'
			},{
                xtype: 'combo',
                fieldLabel: '终端所属分行',
                id: 'termBranchQ',
                hiddenName: 'termBranch',
                store: brhStore,
                displayField: 'displayField',
                valueField: 'valueField'
            },{
                xtype: 'hidden',
                id: 'batchNo',
                name: 'batchNo'
        }],
        buttons: [{
            text: '查询',
            handler: function(){
                termStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });

    // 列表当前选择记录
    var rec;
    
    var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termTmkInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'state',mapping: 'state'},
            {name: 'psamId',mapping: 'psamId'},
            {name: 'batchNo',mapping: 'batchNo'},
            {name: 'reqOpr',mapping: 'reqOpr'},
            {name: 'reqDate',mapping: 'reqDate'},
            {name: 'chkOpr',mapping: 'chkOpr'},
            {name: 'chkDate',mapping: 'chkDate'},
            {name: 'recCrtTs',mapping: 'recCrtTs'}
        ])
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            state: topQueryPanel.getForm().findField('stateQ').getValue(),
            termId: topQueryPanel.getForm().findField('termNoQ').getValue(),
            termSta: 1,
            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
            endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
            mchnNo: topQueryPanel.getForm().findField('mchntNoQ').getValue(),
            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue()
        });
    });
    termStore.load();

    
    var sm = new Ext.grid.CheckboxSelectionModel();
    var termColModel = new Ext.grid.ColumnModel([
    	sm,
//        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {header: '终端号',dataIndex: 'termId',width: 100},
        {header: '商户编号',dataIndex: 'mchntNo',width: 150},
        {header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
        {header: '所属机构号',dataIndex: 'termBranch',renderer: termSignSta},
        {header: '申请状态',dataIndex: 'state',renderer: termState},
        {header: '验证码',dataIndex: 'psamId',width: 150},
        {header: '批次号',dataIndex: 'batchNo'},
        {header: '申请人',dataIndex: 'reqOpr'},
        {header: '申请时间',dataIndex: 'reqDate'},
        {header: '审核人',dataIndex: 'chkOpr'},
        {header: '审核时间',dataIndex: 'chkDate'}
    ]);
    
    
    var reqMenu = {
        text: '单笔申请',
        width: 85,
        iconCls: 'accept',
        disabled: true,
        handler:function() {
            showConfirm('确认申请吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.request({
                        url: 'T30202Action.asp',
                        params: {
                            termId: rec.get('termId'),
                            recCrtTs: rec.get('recCrtTs'),
                            txnId: '30202',
                            subTxnId: '01'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
        }
    }
    
    var queryMenu = {
        text: '查询',
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
    
    
    //终端型号
    var terminalTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
 
	var batchMenu = {
        text: '批量申请',
        width: 85,
        id: 'batch',
        iconCls: 'accept',
        handler:function() {
            showConfirm('确认批量处理吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.requestNeedAuthorise({
                        url: 'T30202Action.asp',
                        params: {
                            termNo: Ext.getCmp('termNoQ').getValue(),
                            mchtCd: topQueryPanel.getForm().findField('mchntNoQ').getValue(),
                            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue(), 
                            state: topQueryPanel.getForm().findField('stateQ').getValue(),  
                            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
            				endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
                            txnId: '30202',
                            subTxnId: '02'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
		}
    };
	var Rdownload = {
			text: '导出',
			width: 85,
			id: 'Rdownload',
			iconCls: 'download',
			handler:function() {
				var selectRecords = grid.getSelectionModel().getSelections();
//				                    grid.getSelectionModel().getSelections();//得到选择所有行
//				var selectRecords = grid.selModel.selections.items;//得到选择所有行
				var array = new Array();
				if(selectRecords.length == 1){
					showErrorMsg("至少勾选两条记录！",grid);
					return;
				}
				for(var i=0; i< selectRecords.length; i++){
					var record = selectRecords[i];
//					alert(record.get('termId'));
					var data = {
					   termIdQ:record.get('termId')							
					};
					array.push(data);
				}
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T30204_DwnAction.asp',
					params: {//查询的参数
					 start: 0,
			            state: topQueryPanel.getForm().findField('stateQ').getValue(),
			            termId: topQueryPanel.getForm().findField('termNoQ').getValue(),
			            termSta: 1,
			            idList:Ext.encode(array),
			            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
			            endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
			            mchnNo: topQueryPanel.getForm().findField('mchntNoQ').getValue(),
			            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue()

					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};
    
    var menuArr = new Array();
    
//    menuArr.push(reqMenu);     
//    menuArr.push(batchMenu);
    menuArr.push(Rdownload);
    menuArr.push(queryMenu);
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端密钥下发导出',
        iconCls: 'T30202',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termStore,
//        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        sm:sm,
        clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        renderTo: Ext.getBody(),
        loadMask: {
            msg: '正在加载终端信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: termStore,
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
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[0].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
            }
        }
    });

    var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})

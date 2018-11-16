Ext.onReady(function() {
    // 终端数据集
	var termStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TERM_ID',function(ret){
		termStore.loadData(Ext.decode(ret));
	});
	// 差错交易类型数据集
	var errFlagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('ERR_FLAG',function(ret){
		errFlagStore.loadData(Ext.decode(ret));
	});
	//交易类型数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        autoWidth:true,
        autoHeight: true,
        labelWidth: 80,
        items:[{layout:'column',
        	items: [{
        		columnWidth: .5,
        		xtype: 'panel',
	        	layout: 'form',
        		items:[{
        			xtype: 'dynamicCombo',
        			methodName: 'getTermId',
    	            fieldLabel: '终端号',
    	            //store: termStore,
    	            hiddenName: 'termId',
    	            id: 'idtermId',
    	            displayField: 'displayField',
    	            valueField: 'valueField'
        		}]
        	},{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易卡号',
                    id: 'idpan',
                    hiddenName: 'pan'
            	}]
            },{
        		columnWidth: .9,
        		xtype: 'panel',
	        	layout: 'form',
        		items:[{
        			xtype: 'dynamicCombo',
                    fieldLabel: '商户编号',
                    methodName: 'getMchntId',
                    emptyText:'请选择商户编号',
                    allowBlank : false,
                    hiddenName: 'mchtId',
                    id: 'idmchtId',
                    width: 400,
                    displayField: 'displayField',
                    valueField: 'valueField'
        		}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '收单行流水号',
                    id: 'idtxnSsn',
                    hiddenName: 'txnSsn'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'combo',
                    fieldLabel: '交易类型',
                    id: 'idtxnNum',
                    hiddenName: 'txnNum',
                    store : txnStore
            	}]
            },{  
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易最小金额（元）',
                    maxLength: 12,
                    maskRe: /^[0-9\\.]+$/,
                    vtype: 'isMoney',
                    id: 'idminOrgTransAmt',
                    hiddenName: 'minOrgTransAmt'
            	}]
            },{  
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '交易开始日期',
                    format : 'Ymd',
                    hiddenName :'startDate',
                    itemCls : 'float-left', 
                    style : 'float:left',
                    id :'startDate'
            	}]
            },{ 
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易最大金额（元）',
                    maxLength: 12,
                    maskRe: /^[0-9\\.]+$/,
                    vtype: 'isMoney',
                    id: 'idmaxOrgTransAmt',
                    hiddenName: 'maxOrgTransAmt'
            	}]
            },{ 
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '交易结束日期',
                    format : 'Ymd',
                    hiddenName :'endDate',
                    itemCls : 'float-left', 
                    style : 'float:left',
                    id :'endDate'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '系统参考号',
                    id: 'idmisc1',
                    hiddenName: 'misc1'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '终端流水号',
                    id: 'idtermSsn',
                    hiddenName: 'termSsn'
            	}]
            }]
        }] ,
        buttons: [{
            text: '查询',
            handler: function() {
	        	if(topQueryPanel.findById('idmchtId').getValue()==''){
	            	showErrorMsg("查询请先选择商户编号",topQueryPanel);
	            	return;
	            }
	        	var startDateTemp = topQueryPanel.findById('startDate').getValue();
        		var endDateTemp = topQueryPanel.findById('endDate').getValue();
        		
	        	if(topQueryPanel.findById('startDate').getValue()!=null && topQueryPanel.findById('startDate').getValue()!=''){
	        		startDateTemp = topQueryPanel.findById('startDate').getValue().format('Ymd');
				}
	        	if(topQueryPanel.findById('endDate').getValue()!=null && topQueryPanel.findById('endDate').getValue()!=''){
	        		endDateTemp = topQueryPanel.findById('endDate').getValue().format('Ymd');
				}
	        	if(startDateTemp!=null && startDateTemp!='' && endDateTemp!=null && endDateTemp!=''){
	        		if(startDateTemp > endDateTemp){
		        		showErrorMsg('交易开始日期不能大于交易结束日期，请重新选择',topQueryPanel);
		        		topQueryPanel.findById('startDate').setValue('');
		        		topQueryPanel.findById('endDate').setValue('');
		        		return;
		        	}
	        	}
	        	var minOrgTransAmt = topQueryPanel.findById('idminOrgTransAmt').getValue();
        		var maxOrgTransAmt = topQueryPanel.findById('idmaxOrgTransAmt').getValue();
        		if(minOrgTransAmt!=null && minOrgTransAmt!='' && maxOrgTransAmt!=null && maxOrgTransAmt!=''){
		        	if(minOrgTransAmt > maxOrgTransAmt){
		        		showErrorMsg('原交易最小金额不能大于原交易最大金额，请重新填写',topQueryPanel);
		        		topQueryPanel.findById('idminOrgTransAmt').setValue('');
		        		topQueryPanel.findById('idmaxOrgTransAmt').setValue('');
		        		return;
		        	}
	        	}
        	    errTxnStore.load({
					params : {
						start : 0
					}
				});
        	    selModel.clearSelections();
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
    
    var errTxnStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=tblAlgoDtl'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'dateSettlmt',mapping: 'dateSettlmt'},
            {name: 'termId',mapping: 'termId'},
            {name: 'mchtId',mapping: 'mchtCd'},
            {name: 'txnNum',mapping: 'txnNum'},
            {name: 'stlmFlg',mapping: 'stlmFlg'},
            {name: 'transDate',mapping: 'transDate'},
            {name: 'transAmt',mapping: 'transAmt'},
            {name: 'pan',mapping: 'pan'},
            {name: 'txnSsn',mapping: 'txnSsn'},
            {name: 'termSsn',mapping: 'termSsn'},
            {name: 'misc1',mapping: 'misc1'},
            {name: 'txnKey',mapping: 'txnKey'},
            {name: 'saState',mapping: 'saState'}
        ]),
        autoLoad: true
    });
    
    errTxnStore.on('beforeload', function() {
    	var startDateTemp = topQueryPanel.findById('startDate').getValue();
    	var endDateTemp = topQueryPanel.findById('endDate').getValue();
        if(topQueryPanel.findById('startDate').getValue()!=null && topQueryPanel.findById('startDate').getValue()!=''){
        	startDateTemp=topQueryPanel.findById('startDate').getValue().format('Ymd');
        }
        if(topQueryPanel.findById('endDate').getValue()!=null && topQueryPanel.findById('endDate').getValue()!=''){
        	endDateTemp=topQueryPanel.findById('endDate').getValue().format('Ymd');
        }
        
        Ext.apply(this.baseParams, {
             start: 0,
             termId: topQueryPanel.findById('idtermId').getValue(),
	         mchtId: topQueryPanel.findById('idmchtId').getValue(),
	         txnNum: topQueryPanel.findById('idtxnNum').getValue(),
	         startDate: startDateTemp,
	         endDate: endDateTemp,
	         minOrgTransAmt: topQueryPanel.findById('idminOrgTransAmt').getValue(),
	         maxOrgTransAmt: topQueryPanel.findById('idmaxOrgTransAmt').getValue(),
	         txnSsn: topQueryPanel.findById('idtxnSsn').getValue(),
	         misc1: topQueryPanel.findById('idmisc1').getValue(),
	         termSsn: topQueryPanel.findById('idtermSsn').getValue(),
	         pan: topQueryPanel.findById('idpan').getValue()
        });
    }); 
    
    var selModel = new Ext.grid.CheckboxSelectionModel(); 
    var errTxnColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        selModel,
        {header: '清算日期',dataIndex: 'dateSettlmt',width: 80,renderer: formatDt},
        {header: '终端号',dataIndex: 'termId',width: 100},
        {header: '商户编号',dataIndex: 'mchtId',width: 100},
        {header: '差错交易代码',dataIndex: 'txnNum',width: 90},
        {header: '清分状态',dataIndex: 'stlmFlg',width: 80,renderer: errTxnStlmFlg},
        {header: '交易日期',dataIndex: 'transDate',width: 100,renderer:formatDt},
        {header: '交易金额（元）',dataIndex: 'transAmt',width: 100,renderer:formatAmtTrans},
		{header: '主账号',dataIndex: 'pan',width: 120,renderer: formatDt},
		{header: '收单行流水号',dataIndex: 'txnSsn',width: 100},
		{header: '终端流水号',dataIndex: 'termSsn',width: 100},
		{header: '系统参考号',dataIndex: 'misc1',width: 100},
		{header: '状态',dataIndex: 'saState',width: 100,renderer: cutSaState}
    ]);
    
    var cutsMenu = {
            text: '追扣',
            width: 85,
            iconCls: 'edit',
            disabled: true,
            handler:function() {
	            var rows = grid.getSelectionModel().getSelections();
				if (rows.length == 0) {
					showAlertMsg("请至少选择一行需要追扣的交易记录！",grid);
					return;
				}
				showConfirm('确定要追扣所选交易记录吗？', grid, function(bt) {
    			   if(bt == 'yes') {
						var array = new Array();
						for ( var index = 0; index < rows.length; index++) {
							var record = rows[index];
							if(record.get('saState')=='0'){
								showAlertMsg("请勿选择追扣待审核状态的已清算交易记录！",grid);
								selModel.clearSelections();
								return;
							}
		                    if(record.get('saState')=='1'){
		                    	showAlertMsg("请勿选择已追扣状态的已清算交易记录！",grid);
		                    	selModel.clearSelections();
								return;
		                    }
							var data = {
								dateSettlmt:record.get('dateSettlmt'),
								txnKey : record.get('txnKey')
							};
							array.push(data);
//								Ext.MessageBox.alert("title","",function(e){alert(record.get('saState'));});
						}
						showProcessMsg('正在提交追扣请求，请稍后......');
						Ext.Ajax.request( {
							url : 'T10032Action.asp?method=cuts',
							method : 'post',
							params : {
							    tblAlgoDtlList : Ext.encode(array),
								txnId : '10032',
								subTxnId : '01'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								grid.enable();
								if (rspObj.success) {
									grid.getStore().commitChanges();
									showSuccessMsg(rspObj.msg, grid);
								} else {
									grid.getStore().rejectChanges();
									showErrorMsg(rspObj.msg, grid);
								}
//								grid.getTopToolbar().items.items[0].disable();
								hideProcessMsg();
								grid.getStore().reload();
								
							}
						});
						hideProcessMsg();
    			   }
				});
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
        width: 600,
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
    menuArr.push(cutsMenu); 
    menuArr.push("-");
    menuArr.push(queryMenu); 
    
    
    // 差错流水信息列表
    var grid = new Ext.grid.GridPanel({
        title: '已清算交易流水查询',
        iconCls: 'T301',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: errTxnStore,
        //sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: errTxnColModel,
        sm: selModel,
        //clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        loadMask: {
            msg: '正在加载已清算交易流水信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: errTxnStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
    
    grid.getSelectionModel().on( {
		'rowselect' : function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelections();
			if(rec!=null){
				grid.getTopToolbar().items.items[0].enable();
			}else
				grid.getTopToolbar().items.items[0]. disable();
		}
	});
    
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

});
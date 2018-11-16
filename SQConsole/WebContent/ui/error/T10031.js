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
        autoWidth:750,
        autoHeight: true,
        labelWidth: 80,
        items:[{layout:'column',
        	items: [{
        		columnWidth: 1,
        		xtype: 'panel',
	        	layout: 'form',
        		items:[{
        			xtype: 'dynamicCombo',
        			methodName: 'getTermId',
    	            fieldLabel: '终端号',
    	            hiddenName: 'termId',
    	            id: 'idtermId',
    	            displayField: 'displayField',
    	            valueField: 'valueField'
        		}]
        	},/*{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易卡号',
                    id: 'idpan',
                    hiddenName: 'pan'
            	}]
            },*/{
        		columnWidth: 1,
        		xtype: 'panel',
	        	layout: 'form',
        		items:[{
        			xtype: 'dynamicCombo',
                    fieldLabel: '商户编号',
//                    methodName: 'getMchntIdAll',
                    methodName: 'getMchntId',
                    hiddenName: 'mchtId',
                    id: 'idmchtId',
                    width: 300,
                    displayField: 'displayField',
                    valueField: 'valueField'
        		}]
            },{
            	columnWidth: 1,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '清算日期',
                    format : 'Y-m-d',
                    hiddenName :'dateSettlmt', 
                    id :'iddateSettlmt',
                    width:160
            	}]
            },{
            	columnWidth: 1,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '交易日期',
                    format : 'Y-m-d',
                    hiddenName :'orgDateTime', 
                    id :'idorgDateTime',
                    width:160
            	}]
            }, { 
            	columnWidth: 1,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'combo',
                    fieldLabel: '差错类型',
                    id: 'iderrType',
                    hiddenName: 'errType',
                    width:160,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['单边帐','单边帐'],['非单边帐','非单边帐']]
                    })
            	}]
            }, { 
            	columnWidth: 1,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '交易名称',
                    id: 'idtxnName',
                    name: 'txnName',
                    width:160
            	}]
            }, { 
            	columnWidth: 1,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'combo',
                    fieldLabel: '差错原因',
                    id: 'iderrCode',
                    hiddenName: 'errCode',
                    width:160,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['1','posp多'],['2','预付费卡核心多'],['3','金额有误'],['4','卡号有误']]
                    })
            	}]
            }/*,{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易最小金额（元）',
                    maxLength: 12,
//                    regex:'/^(([1-9]\d*)|0)(\.\d{1,2})?$/',
//                    maskRe: /^[0-9\\.]+$/,
                    vtype: 'isMoney2',
                    id: 'idminOrgTransAmt',
                    hiddenName: 'minOrgTransAmt'
            	}]
            }, { 
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'combo',
                    fieldLabel: '差错类型',
                    id: 'iderrFlag',
                    hiddenName: 'errFlag',
                    store: errFlagStore
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原交易最大金额（元）',
                    maxLength: 12,
//                    maskRe: /^[0-9\\.]+$/,
                    vtype: 'isMoney2',
                    id: 'idmaxOrgTransAmt',
                    hiddenName: 'maxOrgTransAmt'
            	}]
            },{  
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '原交易开始日期',
                    format : 'Y-m-d',
                    hiddenName :'startDate',
                    value : new Date,
//                    itemCls : 'float-left', 
//                    style : 'float:left',
                    id :'startDate'
            	}]
            },{  
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '差错系统跟踪号',
                    id: 'idtxnSsn',
                    hiddenName: 'txnSsn'
            	}]
            },{ 
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
                    fieldLabel: '原交易结束日期',
                    format : 'Y-m-d',
                    hiddenName :'endDate',
//                    itemCls : 'float-left', 
//                    style : 'float:left',
                    id :'endDate'
            	}]
            },{ 
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'combo',
                    fieldLabel: '原交易类型',
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
                    fieldLabel: '原收单行流水号',
                    id: 'idorgTxnSsn',
                    hiddenName: 'orgTxnSsn'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'datefield',
//                    fieldLabel: '原交易清算开始时间',
                    fieldLabel: '原交易结算开始时间',
                    format : 'Y-m-d',
                    hiddenName :'startTime',
                    id :'startTime'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原系统参考号',
                    id: 'idorgRetrivlRef',
                    hiddenName: 'orgRetrivlRef'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                    xtype: 'textfield',
                    fieldLabel: '原终端流水号',
                    id: 'idorgTermSsn',
                    hiddenName: 'orgTermSsn'
            	}]
            },{
            	columnWidth: .5,
            	xtype: 'panel',
	        	layout: 'form',
            	items:[{
                     xtype: 'datefield',
//                     fieldLabel: '原交易清算结束时间',
                     fieldLabel: '原交易结算结束时间',
                     format : 'Y-m-d',
                     hiddenName :'endTime',
                     id :'endTime'
            	}]
        }*/]
        }] ,
        buttons: [{
            text: '查询',
            handler: function() {
        		var dateSettlmtTemp = topQueryPanel.findById('iddateSettlmt').getValue();      	
        		var orgDateTimeTemp = topQueryPanel.findById('idorgDateTime').getValue(); 
        		
	        	if(topQueryPanel.findById('iddateSettlmt').getValue()!=null && topQueryPanel.findById('iddateSettlmt').getValue()!=''){
	        		dateSettlmtTemp = topQueryPanel.findById('iddateSettlmt').getValue().format('Ymd');
				}
	        	if(topQueryPanel.findById('idorgDateTime').getValue()!=null && topQueryPanel.findById('idorgDateTime').getValue()!=''){
	        		orgDateTimeTemp = topQueryPanel.findById('idorgDateTime').getValue().format('Ymd');
				}
	        	
        	    errTxnStore.load({
					params : {
						 start : 0,
						 termId: topQueryPanel.findById('idtermId').getValue(),
				         mchtId: topQueryPanel.findById('idmchtId').getValue(),
				         dateSettlmt:dateSettlmtTemp,
				         orgDateTime:orgDateTimeTemp,
				         errType:topQueryPanel.findById('iderrType').getValue(),
				         txnName:topQueryPanel.findById('idtxnName').getValue(),
				         errCode:topQueryPanel.findById('iderrCode').getValue()
					}
				});
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
            //url: 'gridPanelStoreAction.asp?storeId=bthCupErrTxn',//多表联合查询
            url: 'gridPanelStoreAction.asp?storeId=bthCupErrInf',//查询视图
            timeout : 6000000
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'mchtId',mapping: 'mchtId'},
            {name: 'mchtNm',mapping: 'mchtNm'},
            {name: 'mchtTp',mapping: 'mchtTp'},
            {name: 'termId',mapping: 'termId'},
            {name: 'txnSsn',mapping: 'txnSsn'},
            {name: 'channelNum',mapping: 'channelNum'},
            {name: 'orgDateTime',mapping: 'orgDateTime'},
            {name: 'orgTransAmt',mapping: 'orgTransAmt'},
            {name: 'txnName',mapping: 'txnName'},
            {name: 'pan',mapping: 'pan'},
            {name: 'errType',mapping: 'errType'},
            {name: 'errCode',mapping: 'errCode'},
            {name: 'instDate',mapping: 'instDate'},
            {name: 'dateSettlmt',mapping: 'dateSettlmt'},
            {name: 'amtFlag',mapping: 'amtFlag'}
        ]),
        autoLoad: true
    });
    
    errTxnStore.on('beforeload', function() {
    	
    	var dateSettlmtTemp = topQueryPanel.findById('iddateSettlmt').getValue();
    	var orgDateTimeTemp = topQueryPanel.findById('idorgDateTime').getValue(); 
    	
        if(topQueryPanel.findById('iddateSettlmt').getValue()!=null && topQueryPanel.findById('iddateSettlmt').getValue()!=''){
           dateSettlmtTemp = topQueryPanel.findById('iddateSettlmt').getValue().format('Ymd');
		}
        if(topQueryPanel.findById('idorgDateTime').getValue()!=null && topQueryPanel.findById('idorgDateTime').getValue()!=''){
        	orgDateTimeTemp = topQueryPanel.findById('idorgDateTime').getValue().format('Ymd');
		}
        
        Ext.apply(this.baseParams, {
            start: 0,
             termId: topQueryPanel.findById('idtermId').getValue(),
	         mchtId: topQueryPanel.findById('idmchtId').getValue(),
	         dateSettlmt:dateSettlmtTemp,
	         orgDateTime:orgDateTimeTemp,
	         errType:topQueryPanel.findById('iderrType').getValue(),
	         txnName:topQueryPanel.findById('idtxnName').getValue(),
	         errCode:topQueryPanel.findById('iderrCode').getValue()
        });
    }); 
    
    var errTxnColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        /*{id: 'dateSettlmt',header: '清算日期',dataIndex: 'dateSettlmt',width: 80,renderer: formatDt},
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {id: 'mchtId',header: '商户编号',dataIndex: 'mchtId',width: 100},*/
        {header: '清算日期',dataIndex: 'dateSettlmt',width: 100,renderer: formatDt,sortable:true},
        {header: '差错类型',dataIndex: 'errType',width: 80,sortable:true/*,renderer: errFlag*/},
        {header: '终端号',dataIndex: 'termId',width: 80},
        {header: '商户编号 - 商户名称',dataIndex: 'mchtId',width: 200,sortable:true, renderer:function(val){return getRemoteTrans(val, "mchntName")}},
    //   {header: '商户名称',dataIndex: 'mchtId',width: 120, renderer:function(val){return getRemoteTrans(val, "mchntName")}},
        {header: '商户类型',dataIndex: 'mchtTp',width: 120, renderer:function(val){return getRemoteTrans(val, "mcc")}},
        {header: '差错原因',dataIndex: 'errCode',width: 120,renderer:errReason},
      //  {header: '清分状态',dataIndex: 'stlmFlg',width: 60/*,renderer: txnStlmFlg*/},
        {header: '交易名称',dataIndex: 'txnName',width: 120},
        {header: '交易日期',dataIndex: 'orgDateTime',width: 100,renderer:formatDt},
        {header: '交易金额(元)',dataIndex: 'orgTransAmt',width: 120,renderer:transAmt},
      //  {header: '原交易金额（元）',dataIndex: 'orgTransAmt',width: 90/*,renderer:formatAmtOlds*/},
		{header: '主账号',dataIndex: 'pan',width: 150},
		{header: '系统流水号',dataIndex: 'txnSsn',width: 100,sortable:true}
    ]);
    
    //分转换为元
    function transAmt(val){
    	var tmp = (val/100).toFixed(2)+'';
    	return tmp ;
    	
    	
    }
   //有问题 
    function errReason(val){
    	if(val == '1')
    		return 'posp多';
    	else if(val == '2')
    		return '核心预付费卡多';
    	else if(val == '3')
    		return '金额有误';
    	else if(val == '4')
    		return '卡号有误';
    	else
    		return '未知原因';
    }
    
    //清分状态
    /* function txnStlmFlg(val,rec,store){
     	if(store.data.errFlag == '5221'){
     		if(store.data.dateSettlmt.trim() == ""){
	     		return '手工处理';
     		}else{
     			return '已清分';
     		}
     	}
    	switch(val) {
	    	case '0': return   '未清分';
			case '1': return   '已清分';
			case '2': return   '手工处理';
			case '3': return   '手工处理';
			default : return '未知';
    	}
    }*/
	 /*** 将12位数字（分）转化为以元单元*/
	function formatAmtOlds(val,rec,store) {
		if(store.data.errFlag == '5221'){
			return "";
		}else if(val.length != 12) {
			return val;
		}else{
			var str = new String(val);
			var beginPosition = 0;
			var result ='';
			if(str.charAt(0) == '-'){
			    result='-';
			     for(var i = 1; i < 10; i++) {
			     	beginPosition = i;
					if(str.charAt(i) != '0') {
						break;
					}
				}
			}else{
				for(var i = 0; i < 10; i++) {
					beginPosition = i;
					if(str.charAt(i) != '0') {
						break;
					}
				}
			}
			return result + val.substring(beginPosition,10) + '.' + val.substring(10,12);
		}
	}
    
    var detailMenu = {
        text: '详细信息',
        width: 85,
        hidden:true,
        iconCls: 'edit',
        disabled: true,
        handler:function() {
            var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            } 
            if(rec.get('errFlag')=='5221'){//补电子现金消费交易
            	showElecCashDetail(selectedRecord.get('txnKey'));
            }else if(selectedRecord.get('stlmFlg')=="2" || selectedRecord.get('stlmFlg')=="3" 
            	||(selectedRecord.get('stlmFlg')=="1" && selectedRecord.get('errFlag')=="3")){
            	showTblAlgoDtlDetail(selectedRecord.get('dateSettlmt'),selectedRecord.get('txnKey'),
            		selectedRecord.get('txnSsn'),selectedRecord.get('transDateTime'));
            }else{
            	showErrTxnDetail(selectedRecord.get('dateSettlmt'),selectedRecord.get('txnNum'),selectedRecord.get('txnSsn'));
            }
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
    var Rdownload = {
			text: '导出',
			width: 85,
			id: 'Rdownload',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10040_DwnAction.asp',
					params: {//查询的参数
//					mchtNo: queryForm.getForm().findField('idmchtNoQ').getValue(),
//		            termId: queryForm.getForm().findField('termIdQ').getValue(),
//		            enterTpQ: queryForm.getForm().findField('enterTpQ').getValue(),
//		            changeFlag: queryForm.getForm().findField('changeFlagQ').getValue(),
//		            stQ: queryForm.getForm().findField('stQ').getValue(),
//		            changeDate: typeof(queryForm.findById('changeDateQ').getValue()) == 'string' ? '' : queryForm.findById('changeDateQ').getValue().dateFormat('Ymd'),
//		            aprTsQ: typeof(queryForm.findById('aprTsQ').getValue()) == 'string' ? '' : queryForm.findById('aprTsQ').getValue().dateFormat('Ymd')

					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,manuGrid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};
    var menuArr = new Array();
    menuArr.push(Rdownload);
    menuArr.push("-");	
    menuArr.push(detailMenu);     
    menuArr.push(queryMenu); 
    
    
    // 差错流水信息列表
    var grid = new Ext.grid.GridPanel({
        title: '差错信息查询',
        iconCls: 'T301',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: errTxnStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: errTxnColModel,
        clicksToEdit: true,
        //autoExpandColumn: 'mchtId',
        forceValidation: true,
        tbar: menuArr,
        loadMask: {
            msg: '正在加载差错信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: errTxnStore,
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
           /* if(rec.get('errFlag')=='5221'){
            	grid.getTopToolbar().items.items[0].disable();
            }else{
            	grid.getTopToolbar().items.items[0].enable();
            }*/
        }
    });

	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

});
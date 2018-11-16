Ext.onReady(function() {
	var actStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ACT_NO',function(ret){
        actStore.loadData(Ext.decode(ret));
    });
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	// 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    
    var queryStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoForActivity'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'connType',mapping: 'connType'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'actFee',mapping: 'actFee'}
		])
	});
	
	queryStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            bankNo: Ext.getCmp('branchQ').getValue(),
            mchtGrp: Ext.getCmp('mchtGrpQ').getValue(),
            connType: Ext.getCmp('connTypeQ').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
    
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    sm.handleMouseDown = Ext.emptyFn;
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 100},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200},
		{header: '商户类型',dataIndex: 'mchtGrp'},
		{header: 'MCC',dataIndex: 'mcc'},
		{header: '所属机构',dataIndex: 'acqInstId'},
		{header: '联接类型',dataIndex: 'connType'},
		{header: '计费算法',dataIndex: 'discNm',width:200},
		{header: '费率(%)',dataIndex: 'actFee',editor: new Ext.form.NumberField({
		 	allowBlank: false,
			emptyText: '费率不能为空',
			maxValue: 100
		 })}
	]);
	var mchntForm = new Ext.form.FormPanel({
		frame: true,
		height: 200,
		autoWidth: true,
		labelWidth: 100,
		waitMsgTarget: true,
		buttonAlign: 'center',
		layout: 'column',
		items: [{
                 columnWidth: .6,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '所属分行',
                    id: 'branchQ',
                    hiddenName: 'branch',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    width: 130
                 }]
            },{
                 columnWidth: .6,
                 layout: 'form',
                 items:[{
                 	xtype: 'textfield',
                    fieldLabel: '商户类型',
                    id: 'mchtGrpQ',
                    name: 'mchtGrp',
                    width: 130
                 }]
            },{
                 columnWidth: .9,
                 layout: 'form',
                 items:[{
	                xtype: 'combo',
	                fieldLabel: '商户编号',
	                store: mchntStore,
	                hiddenName: 'mchnNo',
	                id: 'mchnNoQ',
	                displayField: 'displayField',
	                valueField: 'valueField',
	                width: 300
	           }]
            },{
                columnWidth: .6,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型',
                    id: 'connTypeQ',
                    hiddenName: 'connType',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['Z','直联'],['J','间联']]
                    }),
                    width: 130
                }]
            }],
       buttons: [{
            text: '查询',
            handler: function() 
            {
                queryStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                mchntForm.getForm().reset();
            }
        },{
			text: '关闭',
			handler: function() {
				queryWin.hide();
			}
		}]
	});
	var queryWin = new Ext.Window({
		title: '营销活动商户增加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [mchntForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false
	});
	
	var menuArr = new Array();
	var addMenu = {
		text: '增加商户',
		width: 85,
		iconCls: 'add',
		handler:function() {
			queryWin.show();
			queryWin.center();
		}
	};
	menuArr.push(addMenu);
	
	
	
	//商户信息
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户信息',
		region: 'center',
		frame: true,
		border: true,
		store: queryStore,
		sm: sm,
		cm: cm,
		tbar: menuArr,
		clicksToEdit: true,
		columnLines: true,
		stripeRows: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: queryStore,
			pageSize: 200,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	var activityForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		autoWidth: true,
		labelWidth: 150,
		waitMsgTarget: true,
		buttonAlign: 'center',
		layout: 'column',
		items: [{
			    columnWidth: .5,
            	layout: 'form',
			    items: [{	
			    	xtype: 'combo',
	                fieldLabel: '商业营销活动编号*',
	                store: actStore,
	                hiddenName: 'actNo',
	                id: 'actNoQ',
	                displayField: 'displayField',
	                valueField: 'valueField',
			 		blankText: '活动编号不能为空'
				}]
			}],
        buttons: [{
            text: '确定',
            handler: function() 
            {
            	var selectedOptions = new Array();
            	var actFees = new Array();
				var selects = grid.getSelectionModel().getSelections();
				for(var i = 0;i<selects.length;i++){
					selectedOptions[i] = selects[i].get('mchtNo');
					actFees[i] = selects[i].get('actFee');
				}
            	if(activityForm.getForm().isValid()) {
            		T70200.isExist(activityForm.getForm().findField('actNo').getValue(),selectedOptions,function(ret){
						if(ret == "70202.02")
            			{ 
            				showAlertMsg("所选商户为空，请勾选商户",activityForm);
            				return;
            			}
						if(ret == "70205.05")
							showConfirm('商户已经存在其他营销活动中，是否继续吗？',grid,function(bt) {
								if(bt == "yes") {
									activityForm.getForm().submitNeedAuthorise({
			                        url: 'T70202Action_addMchnt.asp',
			                        waitMsg: '正在提交，请稍后......',
			                        success: function(form,action) {
			                            showSuccessMsg(action.result.msg,activityForm);
			                            mchntForm.getForm().reset();
			                            activityForm.getForm().reset();
			                            grid.getStore().reload();
			                        },
			                        failure: function(form,action) {
			                            showErrorMsg(action.result.msg,activityForm);
			                        },
			                        params: {
			                            txnId: '70202',
			                            subTxnId: '01',
			                            selectedOptions: selectedOptions,
			                            actFees: actFees
			                        }
			                    });
								}
							});
						else
							activityForm.getForm().submitNeedAuthorise({
	                        url: 'T70202Action_addMchnt.asp',
	                        waitMsg: '正在提交，请稍后......',
	                        success: function(form,action) {
	                            showSuccessMsg(action.result.msg,activityForm);
	                            mchntForm.getForm().reset();
	                            activityForm.getForm().reset();
	                            grid.getStore().reload();
	                        },
	                        failure: function(form,action) {
	                            showErrorMsg(action.result.msg,activityForm);
	                        },
	                        params: {
	                            txnId: '70202',
	                            subTxnId: '01',
	                            selectedOptions: selectedOptions,
	                            actFees: actFees
	                        }
	                    });
					});
                }
            }
        },{
            text: '重置',
            handler: function() {
                activityForm.getForm().reset();
            }
        }]
	});

	
	var mainUI = new Ext.Viewport({
		renderTo: Ext.getBody(),
		layout: 'border',
		items:[{
			region: 'north',
			xtype: 'panel',
			height: 76,
			items:[activityForm]
		},grid]
	});

})
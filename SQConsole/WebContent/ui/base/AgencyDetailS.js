
//机构详细信息，在外部用函数封装
function showAgencyDetailS(agenid,tranType,El){

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getAgenInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'agenid'
		},[
			{name: 'AGEN_ID',mapping: 'AGEN_ID'},
			{name: 'AGEN_NAME',mapping:'AGEN_NAME'},
			{name: 'AGEN_TYPE',mapping: 'AGEN_TYPE'},
			{name: 'trantypename',mapping: 'trantypename'},
			{name: 'cardhomename',mapping: 'cardhomename'},
			{name: 'agenregbodyhome',mapping: 'agenregbodyhome'},
			{name: 'agenmechcaltypename',mapping: 'agenmechcaltypename'},
			{name: 'agencaltypename',mapping: 'agencaltypename'},
			{name: 'agencalprincyclename',mapping: 'agencalprincyclename'},
		//	{name: 'agencalprinmodename',mapping: 'agencalprinmodename'},
			{name: 'agencalhandcyclename',mapping: 'agencalhandcyclename'},
		//	{name: 'agencalhandmodelname',mapping: 'agencalhandmodelname'},
			{name: 'agencallubcyclename',mapping: 'agencallubcyclename'},
			{name: 'agencallubmodename',mapping: 'agencallubmodename'},
			{name: 'AGEN_BRAND_RATIO',mapping: 'AGEN_BRAND_RATIO'},
			{name: 'AGEN_MIS_RATIO',mapping: 'AGEN_MIS_RATIO'},
			{name: 'AGEN_BANK_NUM',mapping: 'AGEN_BANK_NUM'},
			{name: 'agenentrymodename',mapping: 'agenentrymodename'},
			{name: 'BANK_NAME',mapping: 'BANK_NAME'},
			{name: 'AGEN_INCOME_ACCOUNT_NAME',mapping: 'AGEN_INCOME_ACCOUNT_NAME'},
			{name: 'AGEN_INCOME_ACCOUNT',mapping: 'AGEN_INCOME_ACCOUNT'},
			{name: 'AGEN_EXPENSES_ACCOUNT_NAME',mapping:'AGEN_EXPENSES_ACCOUNT_NAME'},
			{name: 'AGEN_EXPENSES_ACCOUNT',mapping:'AGEN_EXPENSES_ACCOUNT'},
			{name: 'AGEN_SETTLEMENT_DATE',mapping: 'AGEN_SETTLEMENT_DATE'},
			{name: 'agencleardetailname',mapping: 'agencleardetailname'},
			{name: 'AGEN_PAYMENT_SYSTEM',mapping: 'AGEN_PAYMENT_SYSTEM'},
			{name: 'EXTENSION_FIELD1',mapping:'EXTENSION_FIELD1'},
			{name: 'EXTENSION_FIELD2',mapping:'EXTENSION_FIELD2'},
			{name: 'feeflagname',mapping:'feeflagname'},
			{name: 'FEE_VALUE',mapping:'FEE_VALUE'},
			{name: 'MIN_FEE',mapping:'MIN_FEE'},
			{name: 'MAX_FEE',mapping:'MAX_FEE'},
			{name: 'MIN_TRADE',mapping:'MIN_TRADE'},
			{name: 'AGEN_CAL_HAND_MODE',mapping: 'AGEN_CAL_HAND_MODE'},
			{name: 'AGEN_CAL_PRIN_MODE',mapping: 'AGEN_CAL_PRIN_MODE'},
			{name: 'AGEN_REG_BODY',mapping: 'AGEN_REG_BODY'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});

/*	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getAgenFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'feeid',mapping:'FEE_ID'},
			{name: 'AMOUNT_LIMIT',mapping: 'AMOUNT_LIMIT'},
			{name: 'MCHT_PERCENT_LIMIT',mapping: 'MCHT_PERCENT_LIMIT'},
			{name: 'MCHT_PERCENT_MAX',mapping: 'MCHT_PERCENT_MAX'},
			{name: 'MCHT_LUB_PARAM',mapping: 'MCHT_LUB_PARAM'},
			{name: 'RATE_PARAM1',mapping: 'RATE_PARAM1'},
			{name: 'statue',mapping: 'statue'},
			{name: 'agenid',mapping: 'AGEN_ID'}
		]),
		autoLoad: false
	});*/
	
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankCode2'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'tmpNo',mapping: 'tmpNo'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'bankCode',mapping: 'bankCode'},
			{name: 'state',mapping: 'state'}
		]),
		autoLoad: false
	});

	store.on('beforeload', function() {//20121016修复费率分页问题
		Ext.apply(this.baseParams, {
			start: 0,
			agenid: agenid
        });
	});
	
	var cm = new Ext.grid.ColumnModel({
		columns: [ 
	         new Ext.grid.RowNumberer(),
	   		{header: 'ID',dataIndex: 'id',sortable: true,width:150,hidden:true},
			{header: '发卡行',dataIndex: 'bankCode',sortable: true,width:250, renderer:function(val){return getRemoteTrans(val, "bankCode");}},
			{header: '机构号',dataIndex: 'instCode',sortable: true,width:150,hidden:true},
			{header: '状态',dataIndex: 'state',width: 80,renderer:state}
			]
	});
	function state(val){
		if('0' == val)
			return '新增待审核';
		else if('1' == val)
			return '<font color="red">已删除</font>';
		else if('2' == val)
			return '<font color="green">正常</font>';
		else if('3' == val)
			return '修改待审核';
		else if('4' == val)
			return '删除待审核';
	}

    var detailGrid = new Ext.grid.GridPanel({
		title: '机构发卡行添加',
		autoWidth: true,
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		stripeRows: true,
		 autoScroll:true,
		store: store,
		disableSelection: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载发卡行信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});	
	
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		//html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [ {
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			         labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型',
					hiddenName: 'trantypename'
		        	}
        		]
        	},{
	        	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			 xtype: 'combofordispaly',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '归属卡组织',
					hiddenName: 'cardhomename'
				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构交易地区',
					hiddenName: 'agenregbodyhome',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
	       			 labelStyle: 'padding-left: 5px',
					fieldLabel: '所辖商户清算方式',
					id: 'agenmechcaltypename',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构清算方式',
						id: 'agencaltypename',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			         labelStyle: 'padding-left: 5px',
					fieldLabel: '机构本金清算周期',
					hiddenName: 'agencalprincyclename',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'combofordispaly',
			        	  labelStyle: 'padding-left: 5px',
						fieldLabel: '机构本金清算模式*T+N',
						id: 'AGEN_CAL_PRIN_MODE',
						maxLength: 2,
						width:192
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			    xtype: 'combofordispaly',
			        	  labelStyle: 'padding-left: 5px',
						//fieldLabel: '机构手续费清算模式',
						hiddenName: 'agencalhandmodelname',
						anchor: '90%'
		        	}]
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 320,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
    	        	columnWidth: .33,
    	        	layout: 'form',
    	        	xtype: 'panel',
    	       		items: [{
    						xtype: 'combofordispaly',
    			          labelStyle: 'padding-left: 5px',
    						fieldLabel: '手续费清算周期',
    						hiddenName: 'agencalhandcyclename',
    						anchor: '90%'
    		        	}]
    			},{
    	        	columnWidth: .33,
    	        	xtype: 'panel',
    		        layout: 'form',
    	       		items: [{
    			        	xtype: 'combofordispaly',
    			        	 labelStyle: 'padding-left: 5px',
    						fieldLabel: '手续费清算模式*T+N',
    						//store: mchntMccStore,
    						maxLength: 2,
    						id: 'AGEN_CAL_HAND_MODE',
    						anchor: '90%'
    		        	}]
    			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构品牌服务费承担比例',
						id: 'AGEN_BRAND_RATIO'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',	
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构差错费承担比例',
						hiddenName: 'AGEN_MIS_RATIO'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构开户行机构号',
						hiddenName: 'AGEN_BANK_NUM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构出入账模式',
						id: 'agenentrymodename',
						name: 'agenentrymodename'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行名称',
						id: 'BANK_NAME',
						name: 'BANK_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构收入账号开户名',
						id: 'AGEN_INCOME_ACCOUNT_NAME',
						name: 'AGEN_INCOME_ACCOUNT_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构收入账号',
						id: 'AGEN_INCOME_ACCOUNT',
						name: 'AGEN_INCOME_ACCOUNT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支出账号开户名',
						id: 'AGEN_EXPENSES_ACCOUNT_NAME',
						name: 'AGEN_EXPENSES_ACCOUNT_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支出账号',
						id: 'AGEN_EXPENSES_ACCOUNT',
						name: 'AGEN_EXPENSES_ACCOUNT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构当前清算日期',
						hiddenName: 'AGEN_SETTLEMENT_DATE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构清分明细是否生成',
						id: 'agencleardetailname',
						name: 'agencleardetailname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支付系统行号',
						id: 'AGEN_PAYMENT_SYSTEM',
						name: 'AGEN_PAYMENT_SYSTEM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        		xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '日切时间',
						id: 'EXTENSION_FIELD1',
						name: 'EXTENSION_FIELD1',
						minValue: '00:00',
    					maxValue: '23:59',
    					altFormats: 'H:i',
    					format: 'H:i',
    					editable: true,
    					allowBlank: false
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '超时时间(秒)',
						id: 'EXTENSION_FIELD2',
						name: 'EXTENSION_FIELD2'
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率方式',
						name: 'feeflagname',
						id:'feeflagname'
    				
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
	       				xtype: 'displayfield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '费率值',
						name: 'FEE_VALUE',
						id:'FEE_VALUE'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率下限值',
						name: 'MIN_FEE',
						id:'MIN_FEE'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率上限值',
						name: 'MAX_FEE',
						id:'MAX_FEE'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额',
						name: 'MIN_TRADE',
						id:'MIN_TRADE'
		        	
		        	}]	
				}
				]
            },/*,{
				title:'分润设置',
                id: 'feeamt',
                frame: true,
                autoScroll:true,
                layout: 'border',
                items: [{
                	region: 'center',
                	items: [detailGrid]
                }]
			}*/{//FIXME
				title:'发卡行',
                id: 'idbankadd',
                frame: true,
                layout: 'border',
                items: [{
                	id:'idbankForm',
                	//xtype:'form',
                	region: 'north',
                	height: 30,
                	layout: 'column',
                	items: [{
                		columnWidth: .4,
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'dynamicCombo',
	       					labelStyle: 'padding-left: 5px',
	       					methodName: 'getBankCode',
	       					//fieldLabel: '发卡行*',
							id: 'idbankCode',
							hiddenName: 'bankCode',
							width:200,
							hidden:true
						}]
					},{	columnWidth: .4,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'添加',
							id: 'addbu',
							hidden:true,
							handler: function(){
								saveBankInf();
							}
                		}]
					},{	 
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'textfield',
							iconCls: 'recover',
							text:'临时编号',
					//		fieldLabel:'临时编号',
							id: 'tmpNo',
							name:'tmpNo',
							hidden:true,
							width: 60
                		}]
					}]
                },{ 
                	region: 'center',
                	items:[detailGrid]
                }]
		    }]
       }]
    });

	/*detailGrid.on({
		'rowdblclick':function(){
			showAgencyFeeDetailS(detailGrid.getSelectionModel().getSelected().get('feeid'),
					detailGrid.getSelectionModel().getSelected().get('agenid'),detailGrid)
		 }
	});*/
    var detailWin = new Ext.Window({
    	title: '机构详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 980,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			agenid: agenid,
			tranType:tranType
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
			//	Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				detailWin.setTitle('机构详细信息[机构编号：' + agenid + ']');
				detailWin.show();

				store.load({
					params: {
						start: 0,
						agenid: agenid
					}
				});
			}else{
				showErrorMsg("加载机构信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}


//机构分润详细信息，在外部用函数封装
function showAgencyFeeDetailS(feeid,agenid,El){

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getAgenFeeInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'feeid'
		},[
			{name: 'FEE_ID',mapping: 'FEE_ID'},
			{name: 'AGEN_ID',mapping: 'AGEN_ID'},
			{name: 'AGEN_TYPE',mapping:'AGEN_TYPE'},
			{name: 'TERM_ID',mapping: 'TERM_ID'},
			{name: 'MTCH_NO',mapping: 'MTCH_NO'},
			{name: 'MCC_CODE',mapping: 'MCC_CODE'},
			{name: 'TRADE_ACCEPT_REG',mapping: 'TRADE_ACCEPT_REG'},
			{name: 'AGEN_TARGET_BODY',mapping: 'AGEN_TARGET_BODY'},
			{name: 'AGEN_CRE_CARD',mapping: 'AGEN_CRE_CARD'},
			{name: 'CARD_STYLE',mapping: 'CARD_STYLE'},
			{name: 'CARD_MEDIUM',mapping: 'CARD_MEDIUM'},
			{name: 'TRADE_CHANNEL',mapping: 'TRADE_CHANNEL'},
			{name: 'BUSINESS_TYPE',mapping: 'BUSINESS_TYPE'},
			{name: 'TRAN_TYPE',mapping: 'TRAN_TYPE'},
			{name: 'RES',mapping:'RES'},
			{name: 'MCHT_RATE_TYPE',mapping: 'MCHT_RATE_TYPE'},
			{name: 'AGEN_BANK_NUM',mapping: 'AGEN_BANK_NUM'},
			{name: 'MCHT_RATE_METHOD',mapping: 'MCHT_RATE_METHOD'},
			{name: 'AMOUNT_LIMIT',mapping: 'AMOUNT_LIMIT'},
			{name: 'MCHT_RATE_PARAM',mapping: 'MCHT_RATE_PARAM'},
			{name: 'MCHT_PERCENT_LIMIT',mapping: 'MCHT_PERCENT_LIMIT'},
			{name: 'MCHT_PERCENT_MAX',mapping:'MCHT_PERCENT_MAX'},
			{name: 'MCHT_LUB_TYPE',mapping:'MCHT_LUB_TYPE'},
			{name: 'MCHT_LUB_METHOD',mapping: 'MCHT_LUB_METHOD'},
			{name: 'MCHT_LUB_PARAM',mapping: 'MCHT_LUB_PARAM'},
			{name: 'MCHT_LUB_PERCENT_MAX',mapping: 'MCHT_LUB_PERCENT_MAX'},
			{name: 'MCHT_LUB_PERCENT_LIMIT',mapping: 'MCHT_LUB_PERCENT_LIMIT'},
			{name: 'agentypename',mapping: 'agentypename'},
			{name: 'tradeacceptreg',mapping: 'tradeacceptreg'},	
			{name: 'cardstylename',mapping: 'cardstylename'},
			{name: 'cardmediumname',mapping:'cardmediumname'},
			{name: 'tradechannelname',mapping: 'tradechannelname'},
			{name: 'businesstype',mapping: 'businesstype'},
			{name: 'trantypename',mapping: 'trantypename'},
			{name: 'mechratetypename',mapping: 'mechratetypename'},
			{name: 'mechratemethodname',mapping:'mechratemethodname'},
			{name: 'mechlubtypename',mapping: 'mechlubtypename'},
			{name: 'mechlubmethodname',mapping:'mechlubmethodname'},
			{name: 'RATE_PARAM1',mapping:'RATE_PARAM1'},
			{name: 'LUB_PARAM1',mapping:'LUB_PARAM1'},
			{name: 'EXTEN_FIELD1',mapping:'EXTEN_FIELD1'}
		]),
		autoLoad: false
	});

	//var fm = Ext.form;

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

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getAgenFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'TRAN_TYPE',mapping:'TRAN_TYPE'},
			{name: 'AMOUNT_LIMIT',mapping: 'AMOUNT_LIMIT'},
			{name: 'MCHT_PERCENT_LIMIT',mapping: 'MCHT_PERCENT_LIMIT'},
			{name: 'MCHT_PERCENT_MAX',mapping: 'MCHT_PERCENT_MAX'},
			{name: 'MCHT_LUB_PARAM',mapping: 'MCHT_LUB_PARAM'},
			{name: 'RATE_PARAM1',mapping: 'RATE_PARAM1'}
		]),
		sortInfo: {field: 'AMOUNT_LIMIT', direction: 'ASC'},
		autoLoad: false
	});

//	var cm = new Ext.grid.ColumnModel({
//		columns: [{
//		    header: '交易类型',
//            dataIndex: 'TRAN_TYPE',
//            width: 200
//		},{
//            id: 'AMOUNT_LIMIT',
//            header: '最低交易金额',
//            dataIndex: 'AMOUNT_LIMIT',
//            width: 100
//        },{
//            header: '机构费率下限值',
//            dataIndex: 'MCHT_PERCENT_LIMIT',
//            width: 200
//        },{
//            header: '机构费率上限值',
//            dataIndex: 'MCHT_PERCENT_MAX',
//            width: 200
//        }/*,{
//            header: '机构分润参数',
//            dataIndex: 'MCHT_LUB_PARAM',
//            width: 85
//        }*/,{
//            header: '费率值',
//            dataIndex: 'RATE_PARAM1',
//            width: 85
//        }]
//	});

//    var detailGrid = new Ext.grid.GridPanel({
//		title: '该机构的分润列表',
//		autoWidth: true,
//		frame: true,
//		border: true,
//		height: 250,
//		columnLines: true,
//		stripeRows: true,
//		store: store,
//		disableSelection: true,
//		cm: cm,
//		forceValidation: true
//	});

	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		height:250,
		//html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [ {
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly', 
			        baseParams:'MISC3U',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构代码',
					hiddenName: 'AGEN_ID'
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
					fieldLabel: '机构类型',
					hiddenName: 'agentypename'
				}]
			},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'TRAN_TYPE',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型*',
					hiddenName: 'EXTEN_FIELD1',
					anchor: '90%'
		        	}
        		]
        	},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly',
	       		    baseParams:'MCHNT_NO2',
	       			labelStyle: 'padding-left: 5px',
					fieldLabel: '商户编号',
					id:'MTCH_NO',
					hiddenName: 'MTCH_NO',
					anchor: '90%'
				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号',
					hiddenName: 'TERM_ID',
					anchor: '90%',
					renderer:all
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly', 
			         labelStyle: 'padding-left: 5px',
					fieldLabel: '交易受理地区',
					hiddenName: 'tradeacceptreg',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'combofordispaly',
			        	baseParams:'MCC3',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: 'MCC码',
						id:'MCC_CODE',
						hiddenName: 'MCC_CODE',
						anchor: '90%',
						listeners:{
						click:function(){
							RecordUnSign();
						}
						}
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '目标机构',
						hiddenName: 'AGEN_TARGET_BODY',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'combofordispaly',
			          labelStyle: 'padding-left: 5px',
						fieldLabel: '发卡机构',
						hiddenName: 'AGEN_CRE_CARD',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						hiddenName: 'cardstylename',
						anchor: '90%'
		        	}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '卡介质',
						id: 'cardmediumname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						id: 'tradechannelname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						id: 'businesstype'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						hiddenName: 'trantypename'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率类型',
						hiddenName: 'mechratetypename'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率方式',
						id: 'mechratemethodname',
						name: 'mechratemethodname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '费率值',
						id: 'idRATE_PARAM1',
						name: 'RATE_PARAM1'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额',
						id: 'idAMOUNT_LIMIT',
						name: 'AMOUNT_LIMIT'
		        	}]
				},/*{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率参数',
						id: 'MCHT_RATE_PARAM',
						name: 'MCHT_RATE_PARAM'
		        	}]
				},*/{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率下限值',
						id: 'MCHT_PERCENT_LIMIT',
						name: 'MCHT_PERCENT_LIMIT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率上限值',
						id: 'MCHT_PERCENT_MAX',
						name: 'MCHT_PERCENT_MAX'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润类型',
						id: 'mechlubtypename',
						name: 'mechlubtypename'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润方式',
						id: 'mechlubmethodname',
						name: 'mechlubmethodname'
		        	}]
				},/*{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润参数',
						hiddenName: 'MCHT_LUB_PARAM'
		        	}]
				},*/{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润下限值',
						id: 'MCHT_LUB_PERCENT_LIMIT',
						name: 'MCHT_LUB_PERCENT_LIMIT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润上限值',
						id: 'MCHT_LUB_PERCENT_MAX',
						name: 'MCHT_LUB_PERCENT_MAX'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '分润参数1',
						id: 'idLUB_PARAM1',
						name: 'LUB_PARAM1'
		        	}]
				}]
        }
//        ,{
//        	xtype: 'tabpanel',
//        	id: 'tab',
//        	frame: true,
//            plain: false,
//            activeTab: 0,
//            height: 320,
//            deferredRender: false,
//            enableTabScroll: true,
//            labelWidth: 150,
//        	items:[{
//                title:'基本信息',
//                id: 'basic',
//                frame: true,
//				layout:'column',
//                items: []
//            },{
//				title:'分润列表',
//                id: 'feeamt',
//                frame: true,
//                layout: 'border',
//                items: [{
//                	region: 'center',
//                	items: [detailGrid]
//                }]
//			}
//			]
//       }
        ]
    });
	
	function RecordUnSign(){
		var rec=mchntForm.findById('MCC_CODE').getValue();
	    if(rec!='*'){
	    	return rec;
	    }else{
	    	return '全部支持';
	   }
	}
	
    var detailWin = new Ext.Window({
    	title: '机构费率详细信息',
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
			feeid: feeid
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				
				detailWin.setTitle('机构费率详细信息[机构费率编号：' + feeid + ']');
				detailWin.show();
				store.load({
					params: {
						start: 0,
						agenid:agenid
					}
				});
			}else{
				showErrorMsg("加载机构信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}

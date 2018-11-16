

//机构分润信息修改，在外部用函数封装
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
			{name: 'EXTEN_FIELD1',mapping: 'EXTEN_FIELD1'},//交易联接类型
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
			{name: 'EXTENSION_FIELD1',mapping:'EXTENSION_FIELD1'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
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
		//sortInfo: {field: 'AMOUNT_LIMIT', direction: 'ASC'},
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
//		title: '详细信息',
//		autoWidth: true,
//		frame: true,
//		border: true,
//		height: 210,
//		columnLines: true,
//		stripeRows: true,
//		store: store,
//		disableSelection: true,
//		cm: cm,
//		forceValidation: true
//	});

	var mchntForm = new Ext.form.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		//closeAction:true,
		labelAlign: 'left',
		height:285,
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'dynamicCombo',
			        methodName: 'getAgencyInf2',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构代码*',
					maxLength: 30,
					allowBlank: false,
					blankText: '请输入机构代码',
					id: 'idAGEN_ID',
					hiddenName: 'AGEN_ID',
					anchor: '90%',
					readOnly:true
		        	}
        		]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'AGEN_TYPE',       			
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构类型*',
					id: 'idagentype',
					hiddenName: 'AGEN_TYPE',
					allowBlank: false,
					anchor: '90%'					
		        	}
        		]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			    //    xtype: 'basecomboselect',
			    //    baseParams: 'TRAN_TYPE',
        			xtype:'dynamicCombo',
        			methodName:'getTranType2',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型*',
					id: 'idextenfield1',
					hiddenName: 'EXTEN_FIELD1',
					allowBlank: false,
					anchor: '90%',
					listeners:{						
						'focus':function(){
						    Ext.getCmp('idextenfield1').reset();
							Ext.getCmp('idextenfield1').parentP=Ext.getCmp('idAGEN_ID').getValue();
							Ext.getCmp('idextenfield1').getStore().reload();
							
						}
						
						
					}
		        	}
        		]
        	},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'dynamicCombo',  
			        methodName: 'getMchntNo2',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号(*全支持)',
					allowBlank: true,
					hiddenName: 'MTCH_NO',
				//	maxLength: 15,
					anchor: '90%',
					callFunction: function() {
						//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
						Ext.getCmp('idTERM_ID').reset();
						Ext.getCmp('idTERM_ID').parentP=this.value;
						Ext.getCmp('idTERM_ID').getStore().reload();
					}
		        }]
			},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'dynamicCombo',
			    //    methodName: 'getTermIdMchntOnly',
			        methodName: 'getTermIdMchnt2',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号(*全支持)',
					allowBlank: true,
					id: 'idTERM_ID',
					hiddenName: 'TERM_ID',
					anchor: '90%'
		        	}
        		]
        	},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'dynamicCombo',
		            methodName: 'getAreaCodecode2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '交易受理地区*',
					editable: true,
					id: 'idtradeacceptreg',
					hiddenName:'TRADE_ACCEPT_REG',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'dynamicCombo',
			        	methodName: 'getMchntMcc',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: 'MCC码*',
						id: 'idMCC_CODE',
					    hiddenName: 'MCC_CODE',
				//	    maxLength: 4,
					    allowBlank: false,
					    anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'textfield',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '目标机构',
					id:'idAGEN_TARGET_BODY',
					name: 'AGEN_TARGET_BODY',
					maxLength: 11,
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'textfield',	
						labelStyle: 'padding-left: 5px',
						fieldLabel: '发卡机构',
						allowBlank: true,
						blankText: '请输入发卡机构',
						id: 'idAGEN_CRE_CARD',
						name: 'AGEN_CRE_CARD',
						maxLength: 11,
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						allowBlank: true,
						anchor: '90%',
						blankText: '请选择卡类型',
						id: 'idCARD_STYLE',
						hiddenName: 'CARD_STYLE'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
			            labelStyle: 'padding-left: 5px',
			        	baseParams: 'CARD_MEDIUM',
						fieldLabel: '卡介质',
						allowBlank: true,
						anchor: '90%',
						blankText: '请选择卡介质',
						id: 'idCARD_MEDIUM',
						hiddenName: 'CARD_MEDIUM'
		        	}
		        ]
			},{
			
			
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						baseParams: 'TRADE_CHANNEL',
						fieldLabel: '交易渠道',
						blankText: '请输入交易渠道',
						allowBlank: true,
						width:150,
						id: 'idTRADE_CHANNEL',
						hiddenName: 'TRADE_CHANNEL'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'BUSINESS_TYPE',
						fieldLabel: '业务类型',
						labelStyle: 'padding-left: 5px',
						maxLength: 20,
						vtype: 'isOverMax',
						allowBlank: true,
						blankText: '请输入业务类型',
						width:150,
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						 methodName: 'getTranType',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						allowBlank: true,
						blankText: '请输入交易类型',
						width:150,
						id: 'idTRAN_TYPE',
						hiddenName: 'TRAN_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MECH_RATE_TYPE',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率类型',
						allowBlank: true,
						blankText: '请输入机构费率类型',
						width:150,
						id: 'idMCHT_RATE_TYPE',
						hiddenName: 'MCHT_RATE_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
						fieldLabel: '机构费率方式*',
						baseParams: 'MECH_RATE_METHOD',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						blankText: '请输入机构费率方式',
						width:150,
						id: 'idMCHT_RATE_METHOD',
						hiddenName: 'MCHT_RATE_METHOD'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率值',
						width:150,
						allowBlank: true,
						maxLength: 50,
						id: 'idRATE_PARAM1',
						name: 'RATE_PARAM1'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '最低交易金额',
						allowBlank: true,
						blankText: '请输入最低交易金额',
						labelStyle: 'padding-left: 5px',
						width:150,
						maxLength: 16,
						id: 'idAMOUNT_LIMIT',
						name: 'AMOUNT_LIMIT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构费率参数',
						labelStyle: 'padding-left: 5px',
						width:150,
						maxLength: 8,
						vtype: 'isOverMax',
						id: 'idMCHT_RATE_PARAM',
						name: 'MCHT_RATE_PARAM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						fieldLabel: '机构费率下限值*',
						labelStyle: 'padding-left: 5px',
						id: 'idMCHT_PERCENT_LIMIT',
						name: 'MCHT_PERCENT_LIMIT',
						maxLength: 6,
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						fieldLabel: '机构费率上限值*',
						labelStyle: 'padding-left: 5px',
						id: 'idMCHT_PERCENT_MAX',
						name: 'MCHT_PERCENT_MAX',
						maxLength: 6,
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MECH_LUB_TYPE',
						fieldLabel: '机构分润类型',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						editable: true,
						width:150,
						id: 'idMCHT_LUB_TYPE',
						hiddenName: 'MCHT_LUB_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MECH_LUB_METHOD',
						fieldLabel: '机构分润方式*',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						editable: true,
						width:150,
						id: 'idMCHT_LUB_METHOD',
						hiddenName: 'MCHT_LUB_METHOD'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构分润参数',
						labelStyle: 'padding-left: 5px',
						width:150,
						maxLength: 10,
						allowBlank: true,
						maxLength: 8,
						id: 'idMMCHT_LUB_PARAM',
						name: 'MCHT_LUB_PARAM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构分润下限值(元)*',
						labelStyle: 'padding-left: 5px',
						vtype: 'isOverMax',
						width:150,
						allowBlank: false,
						maxLength: 6,
						id: 'idMCHT_LUB_PERCENT_LIMIT',
						name: 'MCHT_LUB_PERCENT_LIMIT',
						maskRe: /^[0-9\\.]+$/
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构分润上限值(元)*',
						labelStyle: 'padding-left: 5px',
						width:150,
						allowBlank: false,
						maxLength: 6,
						id: 'idMCHT_LUB_PERCENT_MAX',
						name: 'MCHT_LUB_PERCENT_MAX',
						maskRe: /^[0-9\\.]+$/
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润参数1*',
						width:150,
						allowBlank: false,
						maxLength: 50,
						id: 'idLUB_PARAM1',
						name: 'LUB_PARAM1',
						regex:/^[0-9]+(.[0-9]{1,4})?$/
		        	}]
			}]
        }
//        ,{
//        	xtype: 'tabpanel',
//        	id: 'tab',
//        	frame: true,
//            plain: false,
//            activeTab: 0,
//            height: 300,
//            deferredRender: false,
//            enableTabScroll: true,
//            labelWidth: 150,
//        	items:[{
//                title:'基本信息',
//                id: 'basic',
//                frame: true,
//				layout:'column',
//                items: []
//            }
////            ,{
////			
////				title:'分润设置',
////                id: 'feeamt',
////                frame: true,
////                layout: 'border',
////                items: [{
////                	region: 'north',
////                	height: 35,
////                	layout: 'column',
////                	items: [{
////		        		xtype: 'panel',
////		        		layout: 'form',
////                		labelWidth: 70,
////	       				items: [{
////	       					xtype: 'displayfield',
////	       					fieldLabel: '计费代码',
////							id: 'discCode',
////							name: 'discCode',
////							readOnly: true
////						}]
////					}]
////                }
//////                ,{
//////                	region: 'center',
//////                	items: [detailGrid]
//////                }
////                ]	
////			}
//			]
//       }
       ],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() { 
            	subSave();
            }
        },{
            text: '重置',
            handler: function() {
            	hasSub = false;
				mchntForm.getForm().reset();
			}
        }]
    });
	function subSave(){
    	if(mchntForm.getForm().isValid()) {
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		btn.disable();
		frm.submit({
				url:'T10110Action.asp?method=update',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					btn.enable();
					showSuccessAlert(action.result.msg,mchntForm);
                  //  mchntForm.close();
					//mchntForm.getForm().reset();
				},
				failure : function(form,action) {
					btn.enable();
					showErrorMsg(action.result.msg,mchntForm);	
				},
				params: {
					FEE_ID:feeid,
					txnId: '10110',
					subTxnId: '03'
				}
			});
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
			agenid: agenid,
			feeid:feeid
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				//var discCode = baseStore.getAt(0).data.feeRate;
			//	Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				
				detailWin.setTitle('机构费率详细信息[机构费率编号：' + feeid + ']');
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

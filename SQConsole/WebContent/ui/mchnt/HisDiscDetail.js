

//商户详细信息，在外部用函数封装
function showAgencyDetailS(DISC_ID,El){

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getHisDisCal'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'DISC_ID'
		},[
			{name: 'DISC_ID',mapping: 'DISC_ID'},
			{name: 'TERM_ID',mapping: 'TERM_ID'},
			{name: 'MCHT_NO',mapping:'MCHT_NO'},
			{name: 'CITY_CODE',mapping: 'CITY_CODE'},
			{name: 'TO_BRCH_NO',mapping: 'TO_BRCH_NO'},
			{name: 'FK_BRCH_NO',mapping: 'FK_BRCH_NO'},
			{name: 'CARD_TYPE',mapping: 'CARD_TYPE'},
			{name: 'CHANNEL_NO',mapping: 'CHANNEL_NO'},
			{name: 'BUSINESS_TYPE',mapping: 'BUSINESS_TYPE'},
			{name: 'TXN_NUM',mapping: 'TXN_NUM'},
			{name: 'MIN_FEE',mapping: 'MIN_FEE'},
			{name: 'MIN_FEE1',mapping: 'MIN_FEE1'},
			{name: 'MAX_FEE',mapping: 'MAX_FEE'},
			{name: 'MAX_FEE1',mapping: 'MAX_FEE1'},
			{name: 'FLOOR_AMOUNT',mapping: 'FLOOR_AMOUNT'},
			{name: 'FLOOR_AMOUNT1',mapping: 'FLOOR_AMOUNT1'},
			{name: 'FLAG',mapping: 'FLAG'},
			{name: 'FEE_VALUE',mapping:'FEE_VALUE'},
			{name: 'FEE_VALUE1',mapping:'FEE_VALUE1'},
			{name: 'citycodename',mapping: 'citycodename'},
			{name: 'cardtypename',mapping: 'cardtypename'},
			{name: 'channelname',mapping: 'channelname'},
			{name: 'bussinesstypename',mapping: 'bussinesstypename'},
			{name: 'txnnumname',mapping: 'txnnumname'},
			{name: 'flagename',mapping: 'flagename'}
		]),
		autoLoad: false
	});

   var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
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

	var cm = new Ext.grid.ColumnModel({
		columns: [{
		    header: '交易类型',
            dataIndex: 'TRAN_TYPE',
            width: 200
		},{
            id: 'AMOUNT_LIMIT',
            header: '最低交易金额',
            dataIndex: 'AMOUNT_LIMIT',
            width: 100
        },{
            header: '机构费率下限值',
            dataIndex: 'MCHT_PERCENT_LIMIT',
            width: 200
        },{
            header: '机构费率上限值',
            dataIndex: 'MCHT_PERCENT_MAX',
            width: 200
        }/*,{
            header: '机构分润参数',
            dataIndex: 'MCHT_LUB_PARAM',
            width: 85
        }*/,{
            header: '费率值',
            dataIndex: 'RATE_PARAM1',
            width: 85
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '该商户手续费规则列表',
		autoWidth: true,
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true
	});

	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [ {
        		columnWidth: .33,
            	layout: 'form',
            	hidden:true,
        		items: [{
			        xtype: 'textfield', 
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '费率代码',
					id:'idDISC_ID',
					name: 'DISC_ID'
		        	}
        		]
        	},{
	        	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			 xtype: 'textfield', 
	       			 labelStyle: 'padding-left: 5px',
					fieldLabel: '终端代码',
					id:'idTERM_ID',
					name: 'TERM_ID',
					width:178
				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'textfield',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '商户代码',
					id:'idMCHT_NO',
					name: 'MCHT_NO',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'dynamicCombo',
	       			methodName: 'getAreaCodecode',
	       			labelStyle: 'padding-left: 5px',
					fieldLabel: '地区码',
					editable: true,
					id: 'idCITY_CODE',
					hiddenName: 'CITY_CODE',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	 xtype: 'basecomboselect',
			             store:AgenStore,
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '目的机构',
						id:'idTO_BRCH_NO',
						hiddenName: 'TO_BRCH_NO',
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
					id:'idFK_BRCH_NO',
					name: 'FK_BRCH_NO',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'MCHT_CARD_TYPE',
						id: 'idCARD_TYPE',
						hiddenName: 'CARD_TYPE',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						id: 'idCHANNEL_NO',
						hiddenName: 'CHANNEL_NO',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE',
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
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低手续费',
						id: 'MIN_FEE1',
						name: 'MIN_FEE1',
						allowBlank: false,
						maxLength: 17,
						blankText: '请输入最低手续费',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最高手续费',
						blankText: '请输入最高手续费',
						maxLength: 17,
						id: 'idMAX_FEE',
						name: 'MAX_FEE1',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额*',
						allowBlank: false,
						maxLength: 17,
						id: 'idFLOOR_AMOUNT',
						name: 'FLOOR_AMOUNT1',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'FLAG_METHOD',
			        	labelStyle: 'padding-left: 5px',
			        	fieldLabel: '手续费方式*',
						id: 'idFLAG',
						hiddenName: 'FLAG',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '费率',
						maxLength: 17,
						allowBlank: false,
						blankText: '请输入费率',
						id: 'idFEE_VALUE',
						name: 'FEE_VALUE1',
						width:180
		        	}]
				}]
            },{
				title:'分润列表',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'center',
                	items: [detailGrid]
                }]	
			}]
       }], buttons: [{
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
				url:'T2070302Action.asp?method=update',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
						btn.enable();
					showSuccessAlert(action.result.msg,mchntForm);
				},
				failure : function(form,action) {
					btn.enable();
					showErrorMsg(action.result.msg,mchntForm);	
				},
				params: {
					txnId: '20704',
					subTxnId: '03'
				}
			});
		}
    }
    var detailWin = new Ext.Window({
    	title: '商户手续费规则详细信息',
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
			DISC_ID: DISC_ID
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				
				detailWin.setTitle('商户手续费规则详细信息[商户费率代码：' + DISC_ID + ']');
				detailWin.show();

				store.load({
					params: {
						start: 0
						//agenid:agenid
					}
				});
			}else{
				showErrorMsg("加载商户规则信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}

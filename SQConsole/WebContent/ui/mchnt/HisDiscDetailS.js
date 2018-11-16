

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
        		items: [{
			        xtype: 'combofordispaly', 
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '费率代码',
					hiddenName: 'DISC_ID'
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
					fieldLabel: '终端代码',
					hiddenName: 'TERM_ID'
				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '商户代码',
					hiddenName: 'MCHT_NO',
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
					fieldLabel: '地区码',
					id:'citycodename',
					hiddenName:'citycodename',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '目的机构',
						id:'TO_BRCH_NO',
						hiddenName: 'TO_BRCH_NO',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly', 
			         labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					hiddenName: 'FK_BRCH_NO',
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
						hiddenName: 'cardtypename',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'combofordispaly',
			          labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						hiddenName: 'channelname',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						hiddenName: 'bussinesstypename',
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
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						id: 'txnnumname',
						hiddenName: 'txnnumname'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '最低手续费',
						id: 'MIN_FEE1',
						hiddenName: 'MIN_FEE1'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						 labelStyle: 'padding-left: 5px',
						fieldLabel: '最高手续费',
						id: 'MAX_FEE1',
						hiddenName: 'MAX_FEE1'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额',
						id: 'FLOOR_AMOUNT1',
						hiddenName: 'FLOOR_AMOUNT1'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	 labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费方式',
						hiddenName: 'flagename'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '费率',
						id: 'FEE_VALUE',
						name:'FEE_VALUE1'
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
       }]
    });

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
				showErrorMsg("加载机构信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}
















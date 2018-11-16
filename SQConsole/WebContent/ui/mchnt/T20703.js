Ext.onReady(function() {
	//保存是否验证成功的变量
	var verifyResult = false;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    //******************计费算法部分**********开始********
	

	
    var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	var hasSub = false;
	var hasUpload = "0";
	var fm = Ext.form;

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
		       }	 
		,{
          header: '机构费率上限值',
            dataIndex: 'MCHT_PERCENT_MAX',
            width: 200
        }/*,{
             header: '机构分润参数',
            dataIndex: 'MCHT_LUB_PARAM',
            width: 85
        }*/,{
            header: '费率参数1',
            dataIndex: 'RATE_PARAM1',
            width: 85
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['分润代码：',{
			xtype: 'textfield',
			id: 'feeid',
			width: 100
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'widfalg',
			width: 60,
			handler: function(){
				store.load();
			}
		}]
	});



	var gridStore = new Ext.data.Store({
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
		])
	});

		store.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			feeid: Ext.getCmp('feeid').getValue()
			//discNm: Ext.getCmp('serdiscNm').getValue()
		});
	});
	 //******************计费算法部分**********结束********


	var mchntForm = new Ext.form.FormPanel({
        title: '新增手续费规则信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端代码*',
					id: 'idTERM_ID',
					name: 'TERM_ID',
					maxLength: 8,
					allowBlank: false,
					anchor: '90%'
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户代码*',
					maxLength: 15,
					minLength:15,
					id: 'idMCHT_NO',
					name: 'MCHT_NO',
					allowBlank: false,
					anchor: '90%'
				}]
        	},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
	       			xtype: 'dynamicCombo',
	       			methodName: 'getAreaCodecode',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地区码*',
					editable: true,
					id: 'idCITY_CODE',
					hiddenName: 'CITY_CODE',
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构*',
					allowBlank: false,
					blankText: '请选择目的机构',
					id: 'idTO_BRCH_NO',
					hiddenName: 'TO_BRCH_NO',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			 xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构*',
					id: 'idFK_BRCH_NO',
					name: 'FK_BRCH_NO',
					maxLength: 8,
					allowBlank: false,
					anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型*',
						baseParams: 'CARD_STYLE',
						allowBlank: false,
						anchor: '90%',
						blankText: '请选择交易渠道',
						id: 'idCARD_TYPE',
						hiddenName: 'CARD_TYPE'
		        	}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 340,
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
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道*',
						allowBlank: false,
						blankText: '请输入交易渠道',
						id: 'idCHANNEL_NO',
						hiddenName: 'CHANNEL_NO',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型*',
						vtype: 'isOverMax',
						allowBlank: false,
						blankText: '请输入业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型*',
						allowBlank: false,
						blankText: '请输入交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低手续费*',
						allowBlank: false,
						maxLength: 17,
						regex:/^[0-9]+(.[0-9]{1,2})?$/,
						blankText: '请输入最低手续费',
						id: 'idMIN_FEE',
						name: 'MIN_FEE',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最高手续费*',
						allowBlank: false,
						blankText: '请输入最高手续费',
						regex:/^[0-9]+(.[0-9]{1,2})?$/,
						maxLength: 17,
						id: 'idMAX_FEE',
						name: 'MAX_FEE',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额*',
						allowBlank: false,
						regex:/^[0-9]+(.[0-9]{1,2})?$/,
						maxLength: 17,
						id: 'idFLOOR_AMOUNT',
						name: 'FLOOR_AMOUNT',
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
						allowBlank: false,
						id: 'idFLAG',
						hiddenName: 'FLAG',
						width:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率',
						maxLength: 17,
						regex:/^[0-9]+(.[0-9]{1,3})?$/,
						allowBlank: false,
						blankText: '请输入费率',
						id: 'idFEE_VALUE',
						name: 'FEE_VALUE',
						width:180
		        	}]
				}]
            },{
				title:'分润设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 20,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 50,
	       				items: [{}]
					}]
                },{
                	region: 'center',
                	width:600,
                	items: [detailGrid]
                }]
		    }]
        }],
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
					url:'T2070302Action.asp?method=add',
					waitTitle : '请稍候',
					waitMsg : '正在提交表单数据,请稍候...',
					success : function(form, action) {
							btn.enable();
						showSuccessAlert(action.result.msg,mchntForm);
					
						mchntForm.getForm().reset();
					},
					failure : function(form,action) {
						btn.enable();
						showErrorMsg(action.result.msg,mchntForm);	
					},
						params: {
							txnId: '20703',
							subTxnId: '01'
						}
			});
		}
    }

//    gridStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			start: 0,
//			discCd: Ext.getCmp('serdiscCd').getValue(),
//			discNm: Ext.getCmp('serdiscNm').getValue()
//		});
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});
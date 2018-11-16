//机构信息修改，在外部用函数封装
function showAgencyDetailS(agenid,tranType,El){
	 
	 
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getAgenInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
		//	idProperty: 'agenid'
		},[
			{name: 'AGEN_ID',mapping: 'AGEN_ID'},
			{name: 'AGEN_NAME',mapping:'AGEN_NAME'},
			{name: 'AGEN_TYPE',mapping: 'AGEN_TYPE'},
			{name: 'TRAN_TYPE',mapping: 'TRAN_TYPE'},
			{name: 'trantypename',mapping: 'trantypename'},
			{name: 'agentypeName',mapping: 'agentypeName'},
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
			{name: 'AGEN_REG_BODY',mapping: 'AGEN_REG_BODY'},
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
			{name: 'AGEN_CAL_HAND_CYCLE',mapping: 'AGEN_CAL_HAND_CYCLE'},
			{name: 'AGEN_CAL_HAND_MODE',mapping: 'AGEN_CAL_HAND_MODE'},
			{name: 'AGEN_CAL_LUB_CYCLE',mapping: 'AGEN_CAL_LUB_CYCLE'},
			{name: 'AGEN_CAL_LUB_MODE',mapping: 'AGEN_CAL_LUB_MODE'},
			{name: 'AGEN_CAL_PRIN_MODE',mapping: 'AGEN_CAL_PRIN_MODE'},
			{name: 'AGEN_CAL_TYPE',mapping: 'AGEN_CAL_TYPE'},
			{name: 'AGEN_CLEAR_DETAIL',mapping: 'AGEN_CLEAR_DETAIL'},
			{name: 'AGEN_ENTRY_MODE',mapping: 'AGEN_ENTRY_MODE'},
			{name: 'AGEN_MECH_CAL_TYPE',mapping: 'AGEN_MECH_CAL_TYPE'},
			{name: 'AGEN_CAL_PRIN_CYCLE',mapping: 'AGEN_CAL_PRIN_CYCLE'},
			{name: 'yinlian',mapping: 'yinlian'},
			{name: 'visa',mapping: 'visa'},
			{name: 'master',mapping: 'master'},
			{name: 'JCB',mapping: 'JCB'},
			{name: 'dinersClub',mapping: 'dinersClub'},
		    {name: 'americanExpress',mapping: 'americanExpress'},
		    {name: 'EXTENSION_FIELD1',mapping:'EXTENSION_FIELD1'},
		    {name: 'EXTENSION_FIELD2',mapping:'EXTENSION_FIELD2'},
		    {name: 'feeflagname',mapping:'feeflagname'},
		    {name: 'FEE_FLAG',mapping:'FEE_FLAG'},
			{name: 'FEE_VALUE',mapping:'FEE_VALUE'},
			{name: 'MIN_FEE',mapping:'MIN_FEE'},
			{name: 'MAX_FEE',mapping:'MAX_FEE'},
			{name: 'MIN_TRADE',mapping:'MIN_TRADE'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

//	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
//		flagStore.loadData(Ext.decode(ret));
//	});

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

//	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
//		txnStore.loadData(Ext.decode(ret));
//	});

	/*var store = new Ext.data.Store({
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
		height: 180,
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
		}),
		tbar: [{
			xtype: 'textfield',
			id: 'term_ID',
			width: 60,
			hidden:true
		},{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'searc',
			hidden:true,
			width: 60,
			handler: function(){
				store.load({
				params: {
					start: 0
				//	tmpNo: Ext.getCmp('tmpNo').getValue(),
				//	termId:Ext.getCmp('term_ID').getValue()
					}
				});
			}
		},'-',{
			xtype: 'button',
			text:'删除',
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(detailGrid.getSelectionModel().hasSelection()) {
					var rec = detailGrid.getSelectionModel().getSelected();
					showConfirm('确定要删除该条开户行信息吗？',detailGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T1010601Action.asp?method=delete2',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,detailGrid);
										detailGrid.getStore().reload();
										detailGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,detailGrid);
									}
								},
								params: { 
									id: rec.get('id'),
									tmpNo:rec.get('tmpNo'),
									txnId: '1010601',
									subTxnId: '01'
								}
							});
						}
					});
				}
			}
		}]
	});
    
    
    detailGrid.getSelectionModel().on({
		'rowselect':function(){
			rec = detailGrid.getSelectionModel().getSelected();
			if(rec.get('state') == '2' || rec.get('state') == '0'){
			   detailGrid.getTopToolbar().items.items[3].enable();		
			}else{
			   detailGrid.getTopToolbar().items.items[3].disable();	
			}
		}	
	});
    
	var mchntForm = new Ext.form.FormPanel({
		id: 'mchntForm',
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		closeAction:true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'textfield',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构名称',
					maxLength: 30,
					allowBlank: false,
					blankText: '请输入机构名称',
					id: 'idagenname',
					name: 'AGEN_NAME',
					anchor: '90%'
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
					fieldLabel: '机构类型',
					id: 'idagentype',
					hiddenName: 'AGEN_TYPE',
					width:192
		        	}
        		]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'basecomboselect',
        		//	 xtype: 'displayfield',  			
        		//	xtype: 'combofordispaly',
			        baseParams: 'TRAN_TYPE',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型',
					allowBlank: false,
					id: 'idtrantypename',
					hiddenName: 'trantypename',
					anchor: '90%'
					//	disable:true
				   //	readOnly:true,
				   //	width:208
		        	}
        		]
        	},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'dynamicCombo',   
			        methodName: 'getAreaCodecode2',
//			        xtype: 'basecomboselect',
//			        baseParams: 'CITY_CODE_CODE',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构交易地区',
					allowBlank: false,
//					editable: true,
					id:'idagenregbodyhome',
					hiddenName:'AGEN_REG_BODY',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'basecomboselect',
	       			baseParams: 'AGEN_MECH_CAL_TYPE',
	       			 labelStyle: 'padding-left: 5px',
					fieldLabel: '所辖商户清算方式',
					id: 'idagenmechcaltypename',
					hiddenName:'AGEN_MECH_CAL_TYPE',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CAL_TYPE',
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '机构清算方式',
						id: 'idagencaltypename',
						hiddenName:'AGEN_CAL_TYPE',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        labelStyle: 'padding-left: 5px',
			        baseParams: 'AGEN_CAL_PRIN_CYCLE',
					fieldLabel: '机构本金清算周期',
					hiddenName: 'AGEN_CAL_PRIN_CYCLE',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'textfield',	
						labelStyle: 'padding-left: 5px',
						baseParams: 'AGEN_CAL_PRIN_MODE',
						fieldLabel: '机构本金清算模式*T+N',
						maxLength: 2,
						id: 'AGEN_CAL_PRIN_MODE',
						width:192
						
		        	}]
			},{
	        	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '归属卡组织 银联',
						id: 'yinlian',
						name: 'yinlian'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'visa',
						id: 'visa',
						name: 'visa'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'Master',
						id: 'master',
						name: 'master'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'JCB',
						id: 'JCB',
						name: 'JCB'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'DinersClub',
						id: 'dinersClub',
						name: 'dinersClub'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'AmericanExpress',
						id: 'americanExpress',
						name: 'americanExpress'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						hidden:true,
						id: 'idagenid',
						name: 'AGEN_ID'
		        	}]
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 250,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [/*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						baseParams: 'AGEN_CAL_LUB_CYCLE',
						fieldLabel: '机构分润清算周期',
						id: 'idagencallubcycle',
						hiddenName: 'AGEN_CAL_LUB_CYCLE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CAL_LUB_MODE',
						fieldLabel: '机构分润清算模式',
						labelStyle: 'padding-left: 5px',
						id: 'idagencallubmodename',
						hiddenName: 'AGEN_CAL_LUB_MODE'
		        	}]
				},*/{
		        	columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
							xtype: 'basecomboselect',
							baseParams: 'AGEN_CAL_HAND_CYCLE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '手续费清算周期',
							hiddenName: 'AGEN_CAL_HAND_CYCLE',
							anchor: '90%'
			        	}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
				        	xtype: 'textfield',
				        	baseParams: 'AGEN_CAL_HAND_MODE',
				        	labelStyle: 'padding-left: 5px',
							fieldLabel: '手续费清算模式*T+N',
							maxLength: 2,
							id: 'AGEN_CAL_HAND_MODE',
							anchor: '90%'
			        	}
			        ]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构品牌服务费承担比例',
						id: 'idAGEN_BRAND_RATIO',
						name:'AGEN_BRAND_RATIO'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',	
			        	labelStyle: 'padding-left: 5px',
						fieldLabel: '机构差错费承担比例',
						id: 'idAGEN_MIS_RATIO',
						name: 'AGEN_MIS_RATIO'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构开户行机构号',
						labelStyle: 'padding-left: 5px',
						maxLength: 11,
						id:'idAGEN_BANK_NUM',
						name: 'AGEN_BANK_NUM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
						fieldLabel: '机构出入账模式',
						labelStyle: 'padding-left: 5px',
						baseParams: 'AGEN_ENTRY_MODE',
						id: 'idagenentrymodename',
						hiddenName: 'AGEN_ENTRY_MODE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '开户行名称',
						labelStyle: 'padding-left: 5px',
						maxLength: 25,
						id: 'idBANK_NAME',
						name: 'BANK_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构收入账号开户名',
						maxLength: 25,
						labelStyle: 'padding-left: 5px',
						id: 'idAGEN_INCOME_ACCOUNT_NAME',
						name: 'AGEN_INCOME_ACCOUNT_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构收入账号',
						maxLength: 22,
						labelStyle: 'padding-left: 5px',
						id: 'idAGEN_INCOME_ACCOUNT',
						name: 'AGEN_INCOME_ACCOUNT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构支出账号开户名',
						labelStyle: 'padding-left: 5px',
						maxLength: 25,
						id: 'idAGEN_EXPENSES_ACCOUNT_NAME',
						name: 'AGEN_EXPENSES_ACCOUNT_NAME'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						fieldLabel: '机构支出账号',
						labelStyle: 'padding-left: 5px',
						maxLength: 22,
						id: 'idAGEN_EXPENSES_ACCOUNT',
						name: 'AGEN_EXPENSES_ACCOUNT'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构当前清算日期',
						labelStyle: 'padding-left: 5px',
						maxLength: 8,
						id:'idAGEN_SETTLEMENT_DATE',
						name: 'AGEN_SETTLEMENT_DATE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CLEAR_DETAIL',
						fieldLabel: '机构清分明细是否生成',
						labelStyle: 'padding-left: 5px',
						editable: true,
						id: 'idagencleardetailname',
						hiddenName: 'AGEN_CLEAR_DETAIL'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '机构支付系统行号',
						labelStyle: 'padding-left: 5px',
						maxLength:11,
						id: 'idAGEN_PAYMENT_SYSTEM',
						name: 'AGEN_PAYMENT_SYSTEM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{ 		
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '日切时间(HH:MM:SS)',
						id: 'EXTENSION_FIELD1',
						name: 'EXTENSION_FIELD1',
						regex:/^([0-1]?[0-9]|2[0-3])(:|：)([0-5][0-9])(:|：)([0-5][0-9])$/,
    					increment: 10,
    				//	anchor: '55%',
    					width:150
		        	}]	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '超时时间(秒)',
						id: 'EXTENSION_FIELD2',
						name: 'EXTENSION_FIELD2',
    					increment: 10,
    				//	anchor: '55%',
    					width:150
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						allowBlank:true,
						fieldLabel: '费率方式',
						hiddenName: 'FEE_FLAG',
    					increment: 10,
    					width:150,
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['1','按固定值'],['2','按百分比']]
    	                })
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
	       				xtype: 'textfield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '费率值',
				//		id: 'feeValue',
						name: 'FEE_VALUE',
						id:'FEE_VALUE',
    					increment: 10,
    					width:150,
    					allowBlank: true,
    					maxLength: 15,
    					maskRe: /^[0-9\\.]+$/,
    					vtype: 'isOverMax',
    					vtype: 'isMoney'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率下限值',
						name: 'MIN_FEE',
						id:'MIN_FEE',
    					increment: 10,
    					width:150,
    					allowBlank: true,
    					maxLength: 15,
    					maskRe: /^[0-9\\.]+$/,
    					vtype: 'isOverMax',
    					vtype: 'isMoney'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率上限值',
						name: 'MAX_FEE',
						id:'MAX_FEE',
    					increment: 10,
    					width:150,
    					allowBlank: true,
    					maxLength: 15,
    					maskRe: /^[0-9\\.]+$/,
    					vtype: 'isOverMax',
    					vtype: 'isMoney'
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额',
						name: 'MIN_TRADE',
						id:'MIN_TRADE',
    					increment: 10,
    					width:150,
    					allowBlank: true,
    					maxLength: 15,
    					maskRe: /^[0-9\\.]+$/,
    					vtype: 'isOverMax',
    					vtype: 'isMoney'
		        	
		        	}]	
				}]
            },/*{
				title:'分润设置',
                id: 'feeamt',
                frame: true,
                autoScroll:true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 25,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					}]
                },{
                	region: 'center',
                	items: [detailGrid]
                }]	
			}*/{//FIXME
				title:'发卡行添加',
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
                		id:'bankItem',
                		hidden:true,
	       				items: [{
	       					xtype: 'dynamicCombo',
	       					labelStyle: 'padding-left: 5px',
	       					methodName: 'getBankCode',
	       					fieldLabel: '发卡行*',
							id: 'idbankCode',
							hiddenName: 'bankCode',
							width:200
						}]
					},{	columnWidth: .4,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'添加',
							id: 'addbu',
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
        	    Ext.getCmp('mchntForm').form.reset(); 
//        		baseStore.removeAll();
				baseStore.reload();//20120817重置时“机构所属地区”被清空的问题
			}
        }]
    });
	
	function saveBankInf(){
		var bankCode = Ext.getCmp('idbankCode').getValue();
		var tmpNo = Ext.getCmp('tmpNo').getValue();
		
		if(bankCode == ''){
			showErrorMsg('请选择发卡行',mchntForm);
    		return;	
		}
		
		T10106.addBankCode2(tmpNo,bankCode,agenid,function(ret){
			var sta = '';
			var msg = '';
			var tmpNo = '';
    		for(var key in ret){
    			if(key=='result'){
    				sta = ret[key];
    			}else if(key=='msg'){
    				msg=ret[key];	
    			}else if(key=='tmpNo'){
    				tmpNo=ret[key];
    			}
    		}
    		if(sta=='N'){
    			Ext.getCmp('tmpNo').setValue(tmpNo);
    			store.load({
				params: {
					start: 0,
					tmpNo: tmpNo
					}
				});
    		}else{
    			showErrorMsg(msg,mchntForm);	
    		}
    	});

		
	}
	
	function subSave(){
    	if(mchntForm.getForm().isValid()) {
	    	var btn = Ext.getCmp('save');
			var frm = mchntForm.getForm();
			btn.disable();
			frm.submit({
					url:'T10106Action.asp?method=update',
					waitTitle : '请稍候',
					waitMsg : '正在提交表单数据,请稍候...',
					success : function(form, action) {
						btn.enable();
						showSuccessAlert(action.result.msg,mchntForm);
	                  //  mchntForm.close();
						//mchntForm.getForm().reset();
						El.getStore().load();
					},
					failure : function(form,action) {
						btn.enable();
						showErrorMsg(action.result.msg,mchntForm);	
					},
					params: {
						txnId: '10101',
						subTxnId: '03',
						agenid: agenid,
						tranType:tranType
					}
			});
		}
    }
 
/*    detailGrid.on({
		'rowdblclick':function(){
			showAgencyFeeDetailS(detailGrid.getSelectionModel().getSelected().get('feeid'),detailGrid.getSelectionModel().getSelected().get('agenid'),detailGrid)
		 }
	});*/
    var detailWin = new Ext.Window({
    	title: '详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1030,
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
			Ext.getCmp('idtrantypename').disable();
			
			if(success){						
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				
				 var value = mchntForm.getForm().findField('idtrantypename').getValue();
				 if(value.trim() == '本代本'){
					 Ext.getCmp('bankItem').show();
					 Ext.getCmp('addbu').show();
					 
				 }else{
					 Ext.getCmp('bankItem').hide();
					 Ext.getCmp('addbu').hide();
					 
				 }
				 
				mchntForm.getForm().clearInvalid();
				
				var discCode = baseStore.getAt(0).data.feeRate;
			//	Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				
				detailWin.setTitle('机构详细信息[机构编号：' + agenid + ']');
				detailWin.show();
				//FIXME
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
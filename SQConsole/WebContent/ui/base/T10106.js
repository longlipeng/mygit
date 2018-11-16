Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	 
	//开户行 store
	var bankStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankCode'
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
	
	
	//商户费率信息 列表
	var bankColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: 'ID',dataIndex: 'id',sortable: true,width:150,hidden:true},
		{header: '发卡行',dataIndex: 'bankCode',sortable: true,width:250, renderer:function(val){return getRemoteTrans(val, "bankCode");}},
		{header: '机构号',dataIndex: 'instCode',sortable: true,width:150,hidden:true},
		{header: '状态',dataIndex: 'state',width: 80,renderer:state}
		
	]);
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
	
	
	//机构开户行信息 表格
	var bankGridPanel = new Ext.grid.GridPanel({
		title: '机构发卡行添加',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		stripeRows: true,
		store: bankStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: bankColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载机构信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: bankStore,
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
				bankStore.load({
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
				if(bankGridPanel.getSelectionModel().hasSelection()) {
					var rec = bankGridPanel.getSelectionModel().getSelected();
					showConfirm('确定要删除该条开户行信息吗？',bankGridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T1010601Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,bankGridPanel);
										bankGridPanel.getStore().reload();
										bankGridPanel.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,feeGridPanel);
									}
								},
								params: { 
									id: rec.get('id'),								
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
	
	
	bankGridPanel.getSelectionModel().on({
		'rowselect':function(){
			bankGridPanel.getTopToolbar().items.items[3].enable();			
		}				
	});
	
	
	
	
	var mchntForm = new Ext.form.FormPanel({
        title: '新增机构信息',
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
            	xtype: 'panel',
        		items: [{
        			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构号*',
					id: 'agenid',
					name: 'agenid',
//					readOnly:true,
				    regex:/^\d{8}$/,
				    regexText:'请输入8位机构号',
					allowBlank: false,
					anchor: '90%'
				}]
        	}/*{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'dynamicCombo',
	       			methodName: 'getOrgcode',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构号*',
					id: 'idagenid',
					hiddenName: 'agenid',
					allowBlank: false,
					anchor: '90%',
					callFunction: function() {
						Ext.getCmp('idagenname').setValue(this.lastSelectionText.substr(11));
					}
		        }]
        	}*/,{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构名称*',
					id: 'idagenname',
					name: 'agenname',
//					readOnly:true,
				    maxLength: 30,
					allowBlank: false,
					anchor: '90%'
				}]
        	},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'AGEN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构类型*',
					id: 'idagentype',
					hiddenName: 'agentype',
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'TRAN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型*',
					allowBlank: false,
					blankText: '请选择联接类型',
					id: 'idtrantype',
					hiddenName: 'trantype',
					anchor: '90%',
					listeners:{
						'select':function(){
							 var value = mchntForm.getForm().findField('idtrantype').getValue();	
							 if(value == '01'){
								 Ext.getCmp('bankItem').show();
								 Ext.getCmp('addbu').show();
								 
							 }else{
								 Ext.getCmp('bankItem').hide();
								 Ext.getCmp('addbu').hide();
								 
							 }
							 
							
						}
					}
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'dynamicCombo',
		            methodName: 'getAreaCodecode2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构交易地区*',
					editable: true,
					id: 'idagenregbody',
  				    allowBlank: false,
					hiddenName: 'agenregbody',
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			 xtype: 'basecomboselect',
			        baseParams: 'AGEN_MECH_CAL_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所辖商户清算方式*',
					id: 'idagenmechcaltype',
					hiddenName: 'agenmechcaltype',
					allowBlank: false,
					anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CAL_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构清算方式*',
						allowBlank: false,
						id:'idagencaltype',
						hiddenName: 'agencaltype',
						anchor: '90%'
//						listeners: {
//							'select': function() {
//								mchntMccStore.removeAll();
//								Ext.getCmp('idmcc').setValue('');
//								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
//								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
//									mchntMccStore.loadData(Ext.decode(ret));
//								});
//							}
//						}
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{ 
			        	xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CAL_PRIN_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构本金清算周期*',
						allowBlank: false,
						blankText: '请输入本金清算周期',
						id: 'idagencalprincycle',
						hiddenName: 'agencalprincycle',
						anchor: '90%'
		        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构本金清算模式T+N*',
					blankText:'请填写N值',
					allowBlank: false,
					maxLength: 2,
					id: 'agencalprinmode',
					name: 'agencalprinmode',
					width:210
	        	}]
			}/*{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构本金清算模式*',
						baseParams: 'AGEN_CAL_PRIN_MODE',
						allowBlank: false,
						//lazyRender: true,
						anchor: '90%',
						blankText: '请选择机构本金清算模式',
						id: 'idagencalprinmode',
						hiddenName: 'agencalprinmode'
		        	}]
			}*/,{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '归属卡组织 * 银联',
//						id: 'cardhome',
//						name: 'cardhome'
						id: 'yinlian',
						name: 'yinlian'
		        	},{
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
						id: 'Master',
						name: 'Master'
		        	},{
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
						id: 'DinersClub',
						name: 'DinersClub'
		        	},{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'AmericanExpress',
						id: 'AmericanExpress',
						name: 'AmericanExpress'
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
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						baseParams: 'AGEN_CAL_HAND_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构手续费清算周期*',
						allowBlank: false,
						blankText: '请输入手续费清算周期',
						width:150,
						id: 'idagencalhandcycle',
						hiddenName: 'agencalhandcycle'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构手续费清算模式T+N*',
						allowBlank: false,
						width:150,
						maxLength: 2,
						id: 'agencalhandmode',
						name: 'agencalhandmode'
		        	}]
				},/*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						baseParams: 'AGEN_CAL_HAND_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构手续费清算模式*',
						maxLength: 20,
						vtype: 'isOverMax',
						allowBlank: false,
						blankText: '请输入手续费清算模式',
						width:150,
						id: 'idagencalhandmode',
						hiddenName: 'agencalhandmode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						baseParams: 'AGEN_CAL_LUB_CYCLE',
						fieldLabel: '机构分润清算周期*',
						allowBlank: false,
						blankText: '请输入机构分润清算周期',
						width:150,
						id: 'idagencallubcycle',
						hiddenName: 'agencallubcycle'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CAL_LUB_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润清算模式*',
						allowBlank: false,
						blankText: '请输入机构分润清算模式',
						width:150,
						id: 'idagencallubmode',
						hiddenName: 'agencallubmode'
		        	}]
				},*/{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构品牌服务费承担比例',
						width:150,
						maxLength: 6,
						id: 'agenbrandratio',
						name: 'agenbrandratio'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构差错费承担比例',
						width:150,
						maxLength: 6,
						vtype: 'isOverMax',
						id: 'agenmisratio',
						name: 'agenmisratio'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构开户行机构号',
//						regex:/^[a-zA-z]+:/,
//						regexText:'必须是正确的地址格式，如http://www.xxx.com',
//                        maxLength: 60,
//						vtype: 'isOverMax',
						id: 'agenbankname',
						name: 'agenbankname',
						maxLength: 11,
						width:150
						//maxLength: 50
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						baseParams: 'AGEN_ENTRY_MODE',
						fieldLabel: '机构出入账模式*',
						allowBlank: false,
						width:150,
						//maxLength: 60,
						id: 'idagenentrymode',
						hiddenName: 'agenentrymode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行名称',
						allowBlank: true,
						width:150,
						maxLength: 25,
						id: 'bankname',
						name: 'bankname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构收入账号开户名',
						width:150,
						maxLength: 10,
						allowBlank: true,
						maxLength: 25,
						id: 'agenincomeaccountname',
						name: 'agenincomeaccountname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构收入账号',
						//regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
						//regexText: '注册资金必须是正数，如123.45',
						//maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						maxLength: 22,
						id: 'agenincomeaccount',
						name: 'agenincomeaccount'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支出账号开户名',
						width:150,
						allowBlank: true,
						maxLength: 25,
						id: 'agenexpressaccountname',
						name: 'agenexpressaccountname'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支出账号',
						width:150,
						maxLength: 22,
						allowBlank: true,
						vtype: 'isOverMax',
						id: 'agenexpressaccount',
						name: 'agenexpressaccount'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构当前清算日期',
						width:150,
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 8,
						allowBlank: true,
						vtype: 'isOverMax',
						id: 'agensettlementdate',
						name: 'agensettlementdate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'AGEN_CLEAR_DETAIL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构清分明细是否生成',
						width:150,
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						//maxLength: 20,
						allowBlank: true,
						vtype: 'isOverMax',
						id: 'idagencleardetail',
						hiddenName:'agencleardetail'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构支付系统行号',
						width:150,
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
					//	maxLength: 15,
						vtype: 'isOverMax',
						maxLength: 11,
						allowBlank: true,
						id: 'agenpaymentsystem',
						name: 'agenpaymentsystem'
						}]
		        },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '日切时间(HH:MM:SS)',
						id: 'idEXTENSION_FIELD1',
						name: 'EXTENSION_FIELD1',
						regex:/^([0-1][0-9]|2[0-3])(:|：)([0-5][0-9])(:|：)([0-5][0-9])$/,
						vtype: 'isOverMax',
    					width:150
    				}]
		        },
		        {
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
						fieldLabel: '费率方式*',
						hiddenName: 'feeFlag',
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
						fieldLabel: '费率值*',
				//		id: 'feeValue',
						name: 'feeValue',
    					increment: 10,
    					width:150,
    					allowBlank:true,
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
						fieldLabel: '费率下限值*',
				//		id: 'minFee',
						name: 'minFee',
    					increment: 10,
    					width:150,
    					allowBlank:true,
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
						fieldLabel: '费率上限值*',
					//	id: 'maxFee',
						name: 'maxFee',
    					increment: 10,
    					width:150,
    					allowBlank:true,
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
						fieldLabel: '最低交易金额*',
					//	id: 'minTrade',
						name: 'minTrade',
    					increment: 10,
    					width:150,
    					allowBlank:true,
    					maxLength: 15,
    					maskRe: /^[0-9\\.]+$/,
    					vtype: 'isOverMax',
    					vtype: 'isMoney'
		        	
		        	}]	
				}]
			//	}]
//            },{
//				title:'分润设置',
//                id: 'feeamt',
//                frame: true,
//                layout: 'border',
//                items: [{
//                	region: 'north',
//                	height: 20,
//                	layout: 'column',
//                	items: [{
//		        		xtype: 'panel',
//		        		layout: 'form',
//                		labelWidth: 50,
//	       				items: [{}]
//					}]
//                },{
//                	region: 'center',
//                	width:600,
//                	items: [detailGrid]
               },{//FIXME
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
                		hidden:true,
                		id:'bankItem',
	       				items: [{
	       					xtype: 'dynamicCombo',
	       					labelStyle: 'padding-left: 5px',
	       					methodName: 'getBankCode',
	       					fieldLabel: '发卡行*',
							id: 'idbankCode',
							hiddenName: 'bankCode',
							width:200
						//	hidden:true,
						//	hideLabel:true
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
                	items:[bankGridPanel]
                }]
		    }]
		    }]
       ,
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
        		if(!Ext.getCmp('yinlian').getValue()  && !Ext.getCmp('visa').getValue() 
	        		&& !Ext.getCmp('Master').getValue() && !Ext.getCmp('JCB').getValue() 
	        		&& !Ext.getCmp('DinersClub').getValue()  && !Ext.getCmp('AmericanExpress').getValue() ){
					showErrorMsg('归属卡组织不能为空，请选择',mchntForm);
					return ;
				}
            	subSave();
            }
        },{
            text: '重置',
            handler: function() {
				mchntForm.getForm().reset();
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
		
		T10106.addBankCode(tmpNo,bankCode,function(ret){
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
    			bankStore.load({
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
				url:'T10106Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					btn.enable();
					showSuccessAlert(action.result.msg,mchntForm);
					bankStore.removeAll();
					mchntForm.getForm().reset();
				},
				failure : function(form,action) {
					btn.enable();
					showErrorMsg(action.result.msg,mchntForm);	
				},
				params: {
					txnId: '10101',
					subTxnId: '01'
				}
			});
		}
    }

    var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});
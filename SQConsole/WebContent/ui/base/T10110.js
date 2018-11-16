Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});

	var mchntForm = new Ext.form.FormPanel({
        title: '新增机构费率信息',
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
			     //   xtype: 'textfield',
        			xtype:'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '机构代码*',
					id: 'idagenid',
					methodName: 'getAgencyInf2',
					hiddenName: 'AGEN_ID',
				//	maxLength: 8,
					allowBlank: false,
					anchor: '90%',
					callFunction:function(){
						Ext.getCmp('idextenfield1').reset();
						Ext.getCmp('idextenfield1').parentP=this.value;
						Ext.getCmp('idextenfield1').getStore().reload();
						
					}
		        }]
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
				}]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        		//	xtype: 'basecomboselect',
        			xtype:'dynamicCombo',
			    //     baseParams: 'TRAN_TYPE',
        			methodName: 'getTranType2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '交易联接类型*',
					id: 'idextenfield1',
					hiddenName: 'EXTEN_FIELD1',
					allowBlank: false,
					anchor: '90%'
				}]
        	},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号(*全部支持)',
					allowBlank: true,
					id: 'idMTCH_NO',
					methodName: 'getMchntNo2',
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
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'dynamicCombo',
			        //methodName: 'getTermIdMchntOnly',
			        methodName: 'getTermIdMchnt2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号(*全部支持)',
					id: 'idTERM_ID',
					hiddenName: 'TERM_ID',
					maxLength: 8,
					allowBlank: true,
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'dynamicCombo',
		            methodName: 'getAreaCodecode2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '交易受理地区*',
					editable: true,
					id: 'idTRADE_ACCEPT_REG',
  				    allowBlank: false,
					hiddenName: 'TRADE_ACCEPT_REG',
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
					//maxLength: 4,
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
						allowBlank: true,
						id:'idAGEN_TARGET_BODY',
						name: 'AGEN_TARGET_BODY',
						maxLength: 11,
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
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
	        	xtype: 'panel',
		        layout: 'form',
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
	       		items: [{xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡介质',
						baseParams: 'CARD_MEDIUM',
						allowBlank: true,
						anchor: '90%',
						blankText: '请选择卡介质',
						id: 'idCARD_MEDIUM',
						hiddenName: 'CARD_MEDIUM'
						}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
						baseParams: 'TRADE_CHANNEL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						allowBlank: true,
						anchor: '90%',
						blankText: '请输入交易渠道',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						maxLength: 20,
						vtype: 'isOverMax',
						allowBlank: true,
						anchor: '90%',
						blankText: '请输入业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'basecomboselect',
		                store: txnStore,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						allowBlank: true,
						anchor: '90%',
						blankText: '请输入交易类型',
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
						anchor: '90%',
						blankText: '请输入机构费率类型',
						id: 'idMCHT_RATE_TYPE',
						hiddenName: 'MCHT_RATE_TYPE'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MECH_RATE_METHOD',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率方式*',
						allowBlank: false,
						anchor: '90%',
						blankText: '请输入机构费率方式',
						id: 'idMCHT_RATE_METHOD',
						hiddenName: 'MCHT_RATE_METHOD'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '费率值*',
						allowBlank: false,
						regex:/^[0-9]+(.[0-9]{1,4})?$/,
						maxLength: 50,
						anchor: '90%',
						id: 'idRATE_PARAM1',
						name: 'RATE_PARAM1'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最低交易金额(/元)',
						allowBlank: true,
						blankText: '请输入最低交易金额',
						anchor: '90%',
						maskRe: /^[0-9\\.]+$/,
						maxLength: 6,
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率参数',
						maxLength: 8,
						anchor: '90%',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率下限值(元)*',
						id: 'idMCHT_PERCENT_LIMIT',
						name: 'MCHT_PERCENT_LIMIT',
						anchor: '90%',
						maxLength: 6,
						maskRe: /^[0-9\\.]+$/
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构费率上限值(元)*',
						id: 'idMCHT_PERCENT_MAX',
						name: 'MCHT_PERCENT_MAX',
						maxLength: 6,
						anchor: '90%',
						maskRe: /^[0-9\\.]+$/
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MECH_LUB_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润类型',
						allowBlank: true,
						anchor: '90%',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润方式*',
						allowBlank: false,
						anchor: '90%',
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
						width:180,
						allowBlank: true,
						anchor: '90%',
						maxLength: 8,
						id: 'idMCHT_LUB_PARAM',
						name: 'MCHT_LUB_PARAM'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润下限值(元)*',
						vtype: 'isOverMax',
						width:150,
						allowBlank: false,
						anchor: '90%',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构分润上限值(元)*',
						width:150,
						allowBlank: false,
						maxLength: 6,
						anchor: '90%',
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
						anchor: '90%',
						id: 'idLUB_PARAM1',
						name: 'LUB_PARAM1',
						regex:/^[0-9]+(.[0-9]{1,4})?$/
		        	}]
				}]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
	        	if(mchntForm.findById('idagenid').getValue().trim() == ""){
					showErrorMsg('机构代码不能为空或空字符串',mchntForm);
					mchntForm.findById('idagenid').setValue('');
					return ;
				}
//	        	if(mchntForm.findById('idTERM_ID').getValue().trim() == ""){
//					showErrorMsg('终端号不能为空或空字符串',mchntForm);
//					mchntForm.findById('idTERM_ID').setValue('');
//					return ;
//				}
//	        	if(mchntForm.findById('idMTCH_NO').getValue().trim() == ""){
//					showErrorMsg('商户号不能为空或空字符串',mchntForm);
//					mchntForm.findById('idMTCH_NO').setValue('');
//					return ;
//				}
	        	if(mchntForm.findById('idMCC_CODE').getValue().trim() == ""){
					showErrorMsg('MCC码不能为空或空字符串',mchntForm);
					mchntForm.findById('idMCC_CODE').setValue('');
					return ;
				}
	        	/*if(mchntForm.findById('idAGEN_TARGET_BODY').getValue().trim() == ""){
					showErrorMsg('目标机构不能为空或空字符串',mchntForm);
					mchntForm.findById('idAGEN_TARGET_BODY').setValue('');
					return ;
				}
	        	if(mchntForm.findById('idAGEN_CRE_CARD').getValue().trim() == ""){
					showErrorMsg('发卡机构不能为空或空字符串',mchntForm);
					mchntForm.findById('idAGEN_CRE_CARD').setValue('');
					return ;
				}*/
	        	if(mchntForm.findById('idAMOUNT_LIMIT').getValue().indexOf('-') != "-1"){
					showErrorMsg('最低交易金额不能为负数',mchntForm);
					mchntForm.findById('idAMOUNT_LIMIT').setValue('');
					return ;
				}
	        	if(mchntForm.findById('idMCHT_PERCENT_LIMIT').getValue().indexOf('-') != "-1"){
					showErrorMsg('机构费率下限值不能为负数',mchntForm);
					mchntForm.findById('idMCHT_PERCENT_LIMIT').setValue('');
					return ;
				}
	        	if(mchntForm.findById('idMCHT_PERCENT_MAX').getValue().indexOf('-') != "-1"){
					showErrorMsg('机构费率上限值不能为负数',mchntForm);
					mchntForm.findById('idMCHT_PERCENT_MAX').setValue('');
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
     
    function subSave(){
    	if(mchntForm.getForm().isValid()) {
	    	var btn = Ext.getCmp('save');
			var frm = mchntForm.getForm();
			btn.disable();
			frm.submit({
				url:'T10110Action.asp?method=add',
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
					txnId: '10110',
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
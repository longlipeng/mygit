Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '输入值非法'
});
Ext.onReady(function() {
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});	
	//交易
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	var queryForm = new Ext.form.FormPanel({
		title: '按交易挂账',
		frame: true,
		border: true,
		width: 550,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80101',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'dynamicCombo',
//	        store: mchntStore,
//			methodName: 'getMchntIdAll',
			methodName: 'getMchntId',
			displayField: 'displayField',
            valueField: 'valueField',
			id: 'idmchtno',
			hiddenName: 'mchtno',
			fieldLabel: '商户编号*',
			allowBlank: false,
			blankText: '请输入商户编号',
			width:400
		},{
			xtype: 'textfield',
			name: 'termid',
			id: 'termid',
			fieldLabel: '终端号',
			allowBlank: true,
			vtype:'isNumber',
			blankText: '请输入终端号',
			maxLength: 8,
			width:300
		},{
//			xtype: 'dynamicCombo',
			xtype: 'basecomboselect',
			name: 'txnnum',
			id: 'idtxnnum',
			store: txnStore,
			fieldLabel: '交易类型',
			blankText: '请选择交易类型',
			allowBlank: true,
			width:300
		}/*,{
			xtype: 'datefield',
			name: 'transdatestart',
			id: 'transdatestart',
			fieldLabel: '交易起始日期',
			allowBlank: true,
			blankText: '请选择交易起始日期',
			width:300
		}*/,{
			xtype: 'datefield',
			name: 'transdateend',
			id: 'transdateend',
			fieldLabel: '交易日期*',
			allowBlank: false,
			blankText: '请选择交易日期',
			width:300
		},{
			xtype: 'textfield',
			name: 'pan',
			id: 'pan',
			fieldLabel: '交易卡号',
			allowBlank: true,
			blankText: '请选择交易卡号',
			maxLength: 19,
			width:300
		},{
			xtype: 'textfield',
			name: 'transamtsmall',
			id: 'transamtsmall',
			fieldLabel: '交易最小金额(元)',
			allowBlank: true,
			blankText: '请选择交易最小金额',
			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			name: 'transamtbig',
			id: 'transamtbig',
			fieldLabel: '交易最大金额(元)',
			allowBlank: true,
			blankText: '请选择交易最大金额',
			maskRe: /^[0-9\\.]+$/,
            vtype: 'isMoney',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			name: 'txnssn',
			id: 'txnssn',
			fieldLabel: '系统参考号',
			allowBlank: true,
			vtype:'isNumber',
			blankText: '请选择系统参考号',
			maxLength: 12,
			width:300
		},{
			xtype: 'textfield',
			name: 'termssn',
			id: 'termssn',
			fieldLabel: '终端流水号',
			allowBlank: true,
			vtype:'isNumber',
			blankText: '请选择终端流水号',
			maxLength: 12,
			width:300
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '查询',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				if(Ext.getCmp('transdateend').getValue()!='' && Ext.getCmp('transdateend').getValue().format('Ymd')>TORDAY){
					showAlertMsg("交易日期不能晚于当前日期",queryForm);
					return;
				}
				var smallTemp = queryForm.findById('transamtsmall').getValue();
				var bigTemp = queryForm.findById('transamtbig').getValue();
				if(smallTemp!=null && smallTemp!='' && bigTemp!=null && bigTemp!=''){
	        		if(smallTemp > bigTemp){
		        		showErrorMsg('交易最小金额不能大于交易最大金额，请重新输入',queryForm);
		        		queryForm.findById('transamtsmall').setValue('');
		        		queryForm.findById('transamtbig').setValue('');
		        		return;
		        	}
	        	}
				var submitUrl = Ext.contextPath + '/page/error/T1002001.jsp?mchtno=' +Ext.getCmp('idmchtno').getValue()
			  		+'&termid=' +Ext.getCmp('termid').getValue() +'&txnnum=' +Ext.getCmp('idtxnnum').getValue() 
			  		+'&transdateend='+Ext.getCmp('transdateend').getValue().format('Ymd')
			  		+'&pan=' +Ext.getCmp('pan').getValue() +'&transamtsmall=' +Ext.getCmp('transamtsmall').getValue()
			  		+'&transamtbig=' +Ext.getCmp('transamtbig').getValue() +'&txnssn=' +Ext.getCmp('txnssn').getValue()
			  		+'&termssn=' +Ext.getCmp('termssn').getValue();
				
				window.location.href = submitUrl;
				
//				queryForm.getForm().submit({					
//					url: 'T80101Action_init.asp?startdate='+Ext.getCmp('transdateend').getValue().format('Ymd'),
//					
//					success: function(form,action) {
//					    queryForm.getForm().reset();
//					},
//					failure: function(form,action) {
//						showAlertMsg(action.result.msg,queryForm);
//					},
//					params: {
//						txnId: '10020',
//						subTxnId: '04'
//					}
//				});
			}
		},{
			text: '重填',
            handler: function() {
				queryForm.getForm().reset();
            }
		}]
	});
	queryForm.getForm().findField('transdateend').setValue(TORDAY);

})
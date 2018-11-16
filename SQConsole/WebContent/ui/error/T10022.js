Ext.onReady(function() {
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});	
	var queryForm = new Ext.form.FormPanel({
		title: '按商户挂账',
		frame: true,
		border: true,
		width: 450,
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
			methodName: 'getMchntId',
//	        store: mchntStore,
			id: 'idmchtno',
			hiddenName: 'mchtno',
			fieldLabel: '商户编号',
			allowBlank: false,
			blankText: '请输入商户编号',
			width:300
		},{
			xtype: 'datefield',
			name: 'transdate',
			id: 'transdate',
			fieldLabel: '交易日期',
			allowBlank: false,
			blankText: '请选择交易日期',
			width:300
		},{
			xtype: 'textarea',
			name: 'remark',
			id: 'remark',
			maxLength: 140,
			fieldLabel: '备注(挂账原因)',
			allowBlank: false,
			blankText: '请选择挂账原因',
			width:300
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '挂账',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T10020Action.asp?method=add',
					waitMsg: '正在请求挂账，请稍后......',
					success: function(form,action) {
					  showSuccessAlert(action.result.msg,queryForm);
					  queryForm.getForm().reset();
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '10022',
						subTxnId: '03'
					}
				});
			}
		}]
	});
	queryForm.getForm().findField('transdate').setValue(TORDAY);

})
Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '输入值非法'
});
Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '按终端挂账',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80101',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'textfield',
			name: 'termid',
			id: 'termid',
			fieldLabel: '终端号',
			allowBlank: false,
			vtype:'isNumber',
			blankText: '请输入终端号',
			width:150,
			maxLength: 8
		},{
			xtype: 'datefield',
			name: 'transdate',
			id: 'transdate',
			fieldLabel: '交易日期',
			allowBlank: false,
			blankText: '请选择交易日期',
			width:150
		},{
			xtype: 'textarea',
			name: 'remark',
			id: 'remark',
			maxLength: 140,
			fieldLabel: '备注(挂账原因)',
			allowBlank: false,
			blankText: '请填写挂账原因',
			width:150
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '挂账',
			handler: function() {
				if(queryForm.findById('transdate').getValue().format('Ymd') > TORDAY){
					showAlertMsg("交易日期不能大于当前日期，请重新选择！",queryForm);
					queryForm.findById('transdate').setValue('');
					return;
				}
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
						txnId: '10021',
						subTxnId: '02'
					}
				});
			}
		}]
	});
	queryForm.getForm().findField('transdate').setValue(TORDAY);

})
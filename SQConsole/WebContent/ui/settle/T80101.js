Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '对账',
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
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '对账日期',
			allowBlank: false,
			blankText: '请选择对账日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				var crtDate = Ext.getCmp('date').getValue();
				var preDate = new Date(crtDate.getTime()-24*60*60*1000);
		//		alert(crtDate.format('Ymd')+'********'+preDate.format('Ymd'));
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80101Action_init.asp',
					waitMsg: '正在请求对账任务，请稍后......',
					success: function(form,action) {
					window.location.href = Ext.contextPath + '/page/settle/T8010101.jsp?date='+Ext.getCmp('date').getValue().format('Ymd')+'&predate='+preDate.format('Ymd');
					},
					failure: function(form,action) {
						showAlertMsgH(action.result.msg,queryForm);
					},
					params: {
						startdate:Ext.getCmp('date').getValue().format('Ymd'),
						txnId: '80101',
						subTxnId: '01'
					}
				});
			}
		}]
	});
	//queryForm.getForm().findField('date').setValue(YESTERDAY);
	queryForm.getForm().findField('date').setValue(TORDAY);
})
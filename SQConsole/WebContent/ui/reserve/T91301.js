Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '备款',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80601',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '备款日期',
			allowBlank: false,
			blankText: '请选择备款日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				var crtDate = Ext.getCmp('date').getValue();
				//获取前一日日期
				var preDate = new Date(crtDate.getTime()-24*60*60*1000);
		//		alert(crtDate.format('Ymd')+'********'+preDate.format('Ymd'));
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T91301Action_init.asp',
					waitMsg: '正在请求备款任务，请稍后......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/reserve/T9130101.jsp?date='+Ext.getCmp('date').getValue().format('Ymd')+'&predate='+preDate.format('Ymd');
					},
					failure: function(form,action) {
						showAlertMsgH(action.result.msg,queryForm);
					},
					params: {
						startdate:Ext.getCmp('date').getValue().format('Ymd'),
						preDate: preDate.format('Ymd'),
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
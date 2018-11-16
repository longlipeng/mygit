Ext.onReady(function(){
	
	var queryForm = new Ext.form.FormPanel({
		title: '人行集中缴存备款',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T91304',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'focusDate',
			id: 'focusDate',
			fieldLabel: '备款日期',
			allowBlank: false,
			blankText: '请选择备款日期'
		},{
			xtype: 'textfield',
			fieldLabel: '备款金额*',
			allowBlank: false,
			blankText: '备款金额不能为空',
			emptyText: '请输入备款金额',
			id: 'focusMoney',
			name: 'focusMoney',
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
		//		var crtDate = Ext.getCmp('date').getValue();
				//获取前一日日期
		//		var preDate = new Date(crtDate.getTime()-24*60*60*1000);
		//		alert(crtDate.format('Ymd')+'********'+preDate.format('Ymd'));
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T91301Action_focusReserve.asp',
					waitMsg: '正在请求备款任务，请稍后......',
					success: function(form,action) {
						showSuccessMsg(action.result.msg,queryForm);
						
					},
					failure: function(form,action) {
						showAlertMsgH(action.result.msg,queryForm);
					},
					params: {
			//			startdate:Ext.getCmp('focusDate').getValue().format('Ymd'),
						txnId: '91301',
						subTxnId: '01'
					}
				});
			}
		}]
	});
	queryForm.getForm().findField('focusDate').setValue(TORDAY);
});
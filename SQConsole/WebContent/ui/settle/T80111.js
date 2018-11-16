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
//				var crtDate = Ext.getCmp('date').getValue();
//				var preDate = new Date(crtDate.getTime()-24*60*60*1000);
//		//		alert(crtDate.format('Ymd')+'********'+preDate.format('Ymd'));
//				if(!queryForm.getForm().isValid()) {
//					return;
//				}
				var date = queryForm.getForm().findField('date').getValue();
				if(date == ''){
					showErrorMsg('请选择报表日期！',queryForm);
					return;
				}
				
				queryForm.getForm().submit({
					url: 'T80206Action_downloadPetroReport.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		}]
	});
	//queryForm.getForm().findField('date').setValue(YESTERDAY);
//	queryForm.getForm().findField('date').setValue(TORDAY);
})
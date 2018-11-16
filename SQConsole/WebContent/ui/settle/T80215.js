Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '支付对账不平明细报表',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80215',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '清算起止日期',
			allowBlank: false,
			blankText: '请选择日期'
		},
//		{
//			xtype: 'basecomboselect',
//			baseParams: 'AGENCY_ID',
//			fieldLabel: '收单机构*',
//			hiddenName: 'agency',
//			allowBlank: false
//		},
		{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80215Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);

})
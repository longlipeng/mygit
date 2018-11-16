Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '清算',
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
			name: 'startdate',
			id: 'startdate',
			fieldLabel: '清算起始日期',
			allowBlank: false,
			blankText: '请选择清算起始日期',
			listeners: {
				'select': function() {
					if(Ext.getCmp('enddate').getValue()!='' && (Ext.getCmp('startdate').getValue() > Ext.getCmp('enddate').getValue())){
						showAlertMsg("清算起始日期不能大于清算结束日期，请重新选择！",queryForm);
						Ext.getCmp('startdate').setValue('');
						Ext.getCmp('enddate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			name: 'enddate',
			id: 'enddate',
			fieldLabel: '清算结束日期',
			allowBlank: false,
			blankText: '请选择清算结束日期',
			listeners: {
				'select': function() {
					if(Ext.getCmp('startdate').getValue()!='' && (Ext.getCmp('startdate').getValue() > Ext.getCmp('enddate').getValue())){
						showAlertMsg("清算起始日期不能大于清算结束日期，请重新选择！",queryForm);
						Ext.getCmp('startdate').setValue('');
						Ext.getCmp('enddate').setValue('');
					}
				}
			}
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80101Action_init2.asp',
					waitMsg: '正在请求清算任务，请稍后......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + 
							'/page/settle/T8010301.jsp?date='+Ext.getCmp('startdate').getValue().format('Ymd')+
								Ext.getCmp('enddate').getValue().format('Ymd');
					},
					failure: function(form,action) {
						showAlertMsgH(action.result.msg,queryForm);
					},
					params: {
						startdate:Ext.getCmp('startdate').getValue().format('Ymd'),
						enddate:Ext.getCmp('enddate').getValue().format('Ymd'),
						txnId: '80101',
						subTxnId: '01'
					}
				});
			}
		},{
            text: '重填',
            handler: function() {
				queryForm.getForm().reset();
            }
        }]
	});
//	queryForm.getForm().findField('date').setValue(YESTERDAY);
})
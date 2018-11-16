Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '手工退货',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T301',
		waitMsgTarget: true,
		labelWidth: 120,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
				xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				fieldLabel: '商户编号*',
				allowBlank: false,
				hiddenName: 'mchtNo',
				id : 'idmchtNo',
				editable: true,
				blankText: '商户编号不能为空',
				emptyText: '请选择商户编号',
				width:300
			},{
				xtype: 'textfield',
				name: 'sysSsn',
				id: 'sysSsn',
				fieldLabel: '流水号',
				blankText: '请输入流水号',
				maxLength:6,
				
			},{
				xtype: 'textfield',
				name: 'retrivlRef',
				id: 'retrivlRef',
				fieldLabel: '参考号',
				allowBlack:false,
				regex: /^[0-9]{1,12}$/,
				regexText: '只能输入数字',
				maxLength:12,
				minLength:12,
				blankText: '请输入12位数字'
			},{
				xtype: 'textfield',
				name: 'termId',
				id: 'termId',
				fieldLabel: '终端号',
				maxLength:8,
				regex: /^[0-9\\]+$/,
				regexText: '只能输入数字'
			},{
				xtype: 'textfield',
				name: 'pan',
				id: 'pan',
				fieldLabel: '原交易卡号',
				blankText: '请输入原交易卡号',
				maxLength:22,
				regex: /^[0-9\\]+$/,
				regexText: '只能输入数字'
			},{
				xtype: 'textfield',
				name: 'amtTrans',
				id: 'amtTrans',
				fieldLabel: '原交易金额/元',
				maskRe: /^[0-9\\.]+$/,
				vtype: 'isMoney'
			},{
				xtype: 'datefield',
				name: 'instDate',
				id: 'instDate',
				fieldLabel: '交易日期'
			},{
				xtype: 'datefield',
				name: 'createDate',
				id: 'createDate',
				fieldLabel: '退货日期'
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
				/*queryForm.getForm().submit({
					url: 'T80101Action_init.asp',
					waitMsg: '正在查询，请稍后......',
					success: function(form,action) {*/
						var dateStr = "&instDate=";
						if(Ext.getCmp('instDate').getValue()!=null&&Ext.getCmp('instDate').getValue()!=''){
							dateStr += Ext.getCmp('instDate').getValue().format('Ymd');
						}
						var retDate = "&createDate=";
						if(Ext.getCmp('createDate').getValue()!=null&&Ext.getCmp('createDate').getValue()!=''){
							retDate += Ext.getCmp('createDate').getValue().format('Ymd');
						}
						window.location.href = Ext.contextPath + '/page/error/T100101.jsp?mchtNo='+Ext.getCmp('idmchtNo').getValue()
										+'&retrivlRef='+Ext.getCmp('retrivlRef').getValue()
										+'&termId='+Ext.getCmp('termId').getValue()
										+'&sysSsn='+Ext.getCmp('sysSsn').getValue()
										+'&pan='+Ext.getCmp('pan').getValue()
										+'&amtTrans='+Ext.getCmp('amtTrans').getValue()+dateStr + retDate;
					/*},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '80101',
						subTxnId: '01'
					}
				});*/
			}
		}]
	});
})
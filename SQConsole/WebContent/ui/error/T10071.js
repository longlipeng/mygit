Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '手工预授权完成',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T10071',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'textfield',
			name: 'pan',
			id: 'pan',
			fieldLabel: '卡号',
			maxLength: 19,
			vtype: 'isNumber',
			allowBlank: false,
			blankText: '请输入卡号'
		},{
			xtype: 'textfield',
			name: 'amt',
			id: 'amt',
			fieldLabel: '交易金额(元)',
			allowBlank: false,
			vtype: 'isMoney',
			blankText: '请输入交易金额'
		},{
			xtype: 'textfield',
			name: 'code',
			id: 'code',
			fieldLabel: '预授权码',
			vtype: 'isNumber',
			maxLength: 6,
			minLength: 6,
			allowBlank: false,
			blankText: '请输入预授权码'
		},{
            xtype: 'dynamicCombo',
            fieldLabel: '商户编号',
            methodName: 'getMchntIdAll',
            hiddenName: 'mchnNoQ',
            id: 'mchnNo',
            allowBlank: false,
            displayField: 'displayField',
            valueField: 'valueField',
            width: 350
       },{
			xtype: 'dynamicCombo',
			id: 'term',
			fieldLabel: '终端号',
			methodName: 'getTermId',
			hiddenName: 'TERM_ID',
			allowBlank: false,
			width: 200,
			displayField: 'displayField',
            valueField: 'valueField'
		},{
			xtype: 'textfield',
			name: 'batchId',
			id: 'batchId',
			fieldLabel: '原批次号',
			vtype: 'isNumber',
			maxLength: 6,
			minLength: 6,
			allowBlank: false,
			width: 200,
			blankText: '请输入原批次号'
		},{
			xtype: 'textfield',
			name: 'license',
			id: 'license',
			fieldLabel: '原凭证号',
			vtype: 'isNumber',
			maxLength: 6,
			minLength: 6,
			allowBlank: false,
			width: 200,
			blankText: '请输入原凭证号'
		},{
			xtype: 'textfield',
			name: 'transDate',
			id: 'transDate',
			fieldLabel: '原预授权日期',
			allowBlank: false,
			maxLength: 4,
			minLength: 4,
			blankText: '请选择原预授权日期，格式为mmdd',
			width:200
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
//			id: 'confirmbutton',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
//				Ext.get('confirmbutton').hide();
				showConfirm('确定要执行所填条件的手工预授权完成操作吗？', queryForm, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T10071Action.asp?method=complete',
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessAlert(rspObj.msg, queryForm);
									queryForm.getForm().reset();
								} else {
									showErrorMsg(rspObj.msg, queryForm);
								}
//								Ext.get('confirmbutton').show();
							},
							params : {
								pan:queryForm.findById('pan').getValue(),
								amt:queryForm.findById('amt').getValue(),
								code:queryForm.findById('code').getValue(),
								mchnNo:queryForm.findById('mchnNo').getValue(),
								term:queryForm.findById('term').getValue(),
								batchId:queryForm.findById('batchId').getValue(),
								license:queryForm.findById('license').getValue(),
								transDate:queryForm.findById('transDate').getValue(),
								txnId: '10071',
								subTxnId: '01'
							}
						});
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
})
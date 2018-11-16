Ext.onReady(function() {
	var queryForm = new Ext.form.FormPanel({
		title: '备付金报表上传',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80206',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '文件日期*',
			blankText: '请选择日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '上传',
			iconCls: 'upload',
			handler: function(bt) {
				// 文件上传窗口
				var dialog = new UploadDialog({
					uploadUrl : 'T80232Action_upload.asp',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB', 
					fileTypes : '*.XLS;*.xls',
					fileTypesDescription : '文件',
					title: '上传信息',
					scope : this,
					animateTarget: 'upload',
					onEsc: function() {
						this.hide();
					},
					postParams: {
						date: Ext.getCmp('date').getValue().format('Ymd'),
						txnId: '80106',
						subTxnId: '01'
					}
				});
				dialog.show();
			}
		},{
			text: '清空条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		},{
			text: '上传至文件服务器',
			handler: function() {
				showConfirm('您确定上传至文件服务器吗？',queryForm,function(bt) {
					if(bt == "yes") {
						queryForm.getForm().submit({
							url: 'T80233Action.asp?method=uploadfile',
							waitMsg: '正在提交信息，请稍后......',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,queryForm);
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,queryForm);	
							},
							params: {
								txnId: '80232',
								subTxnId: '01'
							}
						})
					}
				});
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})
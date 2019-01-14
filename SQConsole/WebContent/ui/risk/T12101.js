Ext.onReady(function() { 
	var queryForm = new Ext.form.FormPanel({
		title:'反洗钱黑名单批量导入',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		iconCls: 'T40202',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		buttonAlign: 'center',
		html: '<font color="red">注意</font>:请确认反洗钱黑名单文件已全部导入!否则出现数据重复不一致问题!',
		buttons: [{
			text: '反洗钱黑名单批量导入',
			iconCls: 'upload',
			handler: function(bt) {
				// 文件上传窗口
				var dialog = new UploadDialog({
					uploadUrl : 'T40202Action.asp?method=batchImportFile',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB',
					//fileTypes : '*.XLS;*.xls',
					fileTypes : '*.xlsx;*.xls',
					fileTypesDescription : 'excel文件',
					title: '反洗钱黑名单批量导入',
					scope : this,
					animateTarget: 'upload',
					onEsc: function() {
						this.hide();
					},
					exterMethod: function() {
					},
					postParams: {
						txnId: '40202',
						subTxnId: '01'
					}
				});
				dialog.show();
			}
		}/*,
		{
			text: '反洗钱黑名单批量导入',
			handler: function() {
				showConfirm('您确定反洗钱黑名单批量导入吗？',queryForm,function(bt) {
					if(bt == "yes") {
						queryForm.getForm().submit({
							url: 'T40202Action.asp?method=batchCardImportIssue',
							waitMsg: '正在提交信息，请稍后......',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,queryForm);
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,queryForm);	
							},
							params: {
								txnId: '40202',
								subTxnId: '01'
							}
						})
					}
				});
			}
		}*/]
	});
})
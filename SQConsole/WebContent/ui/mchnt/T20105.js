Ext.onReady(function() { 
	var queryForm = new Ext.form.FormPanel({
		title:'商户信息批量导入',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		iconCls: 'T20105',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		buttonAlign: 'center',
		buttons: [{
			text: '商户信息批量导入',
			iconCls: 'upload',
			id: 'uploadBFS',
			handler: function(bt) {
				// 文件上传窗口
				var dialog = new UploadDialog({
					uploadUrl : 'T20105Action.asp?method=uploadBFS',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB',
					fileTypes : '*.csv;*.CSV',
					fileTypesDescription : '文本文件(*.csv;*.CSV)',
					title: '商户批量文件上传',
					scope : this,
					animateTarget: 'upload',
					onEsc: function() {
						this.hide();
					},
					exterMethod: function() {
					},
					postParams: {
						txnId: '20105',
						subTxnId: '01'
					}
				});
				dialog.show();
			}
		}
		]
	});
})
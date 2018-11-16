Ext.onReady(function() {
	//菜单树
	menuTree = new Ext.tree.TreePanel({
		region: 'west',
		useArrows: true,
		autoScroll: true,
		animate: true,
		containerScroll: true,
		width: 200,
		//frame: true,
		split: true,
		//renderTo: Ext.getBody(),
		title: '<center>系统菜单</center>',
		collapsible: true,
		rootVisible: false,
		root: new Ext.tree.AsyncTreeNode({
			text: '测试',
			loader: new Ext.tree.TreeLoader({
				dataUrl: 'tree.asp?id=init'
			})
		}),
		listeners: {
			click: function(node) {
				if(node.leaf) {
					initMask.msg = '系统界面加载中，请稍后......';
					initMask.show();
					Ext.get("mainUI").dom.src = node.attributes.url;
					txnCode = node.attributes.id
					hideToolInitMask.defer(500);
				}
			}
		}
	});
	//打开菜单树
	menuTree.getRootNode().expand(true);
	
	var timeToolItem = new Ext.Toolbar.TextItem('');
	
	// 顶部菜单
	var topToolBar = new Array(toolBarStr);
	
	// 修改操作员授权码
	var resetPwdMenu = {
		text: '修改授权码',
		id: 'key',
		iconCls: 'key',
		handler: function() {
			resetPwdWin.show();
			resetPwdForm.getForm().reset();
			resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
		}
	}
	
	var lockScreenMenu = {
		text: '锁屏',
		id: 'lock',
		iconCls: 'lock',
		handler: function() {
			lockWin.show();
			lockForm.getForm().reset();
			lockForm.get('lockOprId').setValue(operator[OPR_ID]);
		}
	}
	
	var quitMenu = {
		text: '安全退出',
		iconCls: 'quit',
		handler: function() {
			showConfirm('确定要退出吗？',this,function(bt) {
				if(bt == 'yes') {
					Ext.Ajax.request({
						url: 'logout.asp',
						success : function(form, action) {
								window.location.href = server;
					}
				});
//					Ext.Ajax.request({
//						url: 'logout.asp'
//					});
//					window.opener=null;
//       				window.open('', '_self', ''); 
//					window.close();
				}
			});
		}
	}
	topToolBar.add('->');
	topToolBar.add(resetPwdMenu);
	topToolBar.add('-');
//	topToolBar.add(lockScreenMenu);
//	topToolBar.add('-');
	topToolBar.add(quitMenu);
	
	//用户UI主面板
	var mainPanel = new Ext.Panel({
		frame: true,
		html: '<iframe id="mainUI" name="mainUI" width="100%" height="100%" frameborder="0" scrolling="auto"/>',
		region: 'center',
		tbar: topToolBar,
		bbar: [	new Ext.Toolbar.TextItem('All rights reserved©2014. 上海汽车集团股份有限公司'),	{
				xtype: 'tbspacer',
				width: 100
			},
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/user.png" title="操作员"/>' + ' ' + operator['opr_name']),
			'-',
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/branch.png" title="机构"/>' + ' ' /*+ operator['opr_brh_name']*/),
			'-',
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/time.png" title="当前时间"/>' + ' '),
			timeToolItem
		],
		listeners: {
			render: function() {
				Ext.TaskMgr.start({
					run: function() {
						Ext.fly(timeToolItem.getEl()).update(new Date().pattern('yyyy-MM-dd HH:mm:ss'));
					},
					interval: 1000
				});
			}
		}
	});
	//用户界面
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [menuTree,mainPanel],
		renderTo: Ext.getBody()
	});
	
	//移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 2
		});
	}
	
	hideMainUIMask.defer(2000);
	
	/**
	 * 重置授权码表单
	 */
	var resetPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'resetOprId',
			name: 'resetOprId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '原授权码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{8}$/,
			regexText: '授权码必须是8位数字或字母',
			id: 'resetPassword',
			name: 'resetPassword',
			allowBlank: false,
			blankText: '原授权码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '新授权码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{8}$/,
			regexText: '授权码必须是8位数字或字母',
			id: 'resetPassword1',
			name: 'resetPassword1',
			allowBlank: false,
			blankText: '新授权码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '重复授权码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{8}$/,
			regexText: '授权码必须是8位数字或字母',
			id: 'resetPassword2',
			name: 'resetPassword2',
			allowBlank: false,
			blankText: '重复授权码不能为空'
		}]
	});
	
	/**
	 * 修改授权码窗口
	 */
	var resetPwdWin = new Ext.Window({
		title: '授权码修改',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'key',
		items: [resetPwdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'key',
		buttons: [{
			text: '提交',
			handler: function() {
				if(!resetPwdForm.getForm().isValid()) {
					return;
				}
				if(resetPwdForm.get('resetPassword').getValue() == resetPwdForm.get('resetPassword1').getValue()) {
					showAlertMsg('新授权码不能和原始授权码一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				if(resetPwdForm.get('resetPassword1').getValue() != resetPwdForm.get('resetPassword2').getValue()) {
					showAlertMsg('两次输入的新授权码不一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				resetPwdForm.getForm().submit({
					url: 'resetPwd.asp',
					waitMsg: '正在提交，请稍后......',
					success: function(form, action) {
						showMsg(action.result.msg,resetPwdWin,function() {
							resetPwdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,resetPwdWin);
					}
				});
			}
		},{
			text: '清空',
			handler: function() {
				resetPwdForm.getForm().reset();
				resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
			}
		}]
	});
	
	// 锁屏表单
	var lockForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'lockOprId',
			name: 'lockOprId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '授权码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{8}$/,
			regexText: '授权码必须是8位数字或字母',
			id: 'lockPassword',
			name: 'lockPassword',
			allowBlank: false,
			blankText: '请输入授权码'
		}]
	});
	
	// 锁屏对话框
	var lockWin = new Ext.Window({
		title: '屏幕锁定',
		frame: true,
		width: 300,
		height: 300,
		layout: 'fit',
		iconCls: 'lock',
		items: [lockForm],
		resizable: false,
		closable: false,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'lock',
		buttons: [{
			text: '解锁',
			iconCls: 'key',
			tooltip: '解锁',
			handler: function() {
				if(!lockForm.getForm().isValid()) {
					return;
				}
				lockForm.getForm().submit({
					url: 'unlockScreen.asp',
					waitMsg: '正在验证授权码，请稍后......',
					success: function(form, action) {
						lockWin.hide();
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,lockWin);
					}
				});
			}
		}]
	});
	Ext.get("mainUI").dom.src = Ext.contextPath + '/page/system/main_0.jsp';
});

	function lackScreenSubmit(obj,options){
		var form = new Ext.form.FormPanel({
			frame: true,
			border: true,
			width: 300,
			height: 80,
			items: [{
				xtype: 'textfield',
				id: 'username',
				name: 'username',
				fieldLabel: '柜员号',
				maskRe: /^[a-zA-Z0-9]+$/,
				maxLength:8,
				allowBlank: false,
				blankText: '请输入柜员号'
			},{
				xtype: 'textfield',
				inputType: 'password',
				id: 'pass',
				name: 'pass',
				fieldLabel: '授权码',
				allowBlank: false,
				blankText: '请输入柜员授权码'
			}]
	});
	
	var win = new Ext.Window({
		title: '支付统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.submit(options);
						},
						failure : function(form,action) {
							frm.reset();
							showErrorMsg(action.result.msg,obj);
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	})
	win.show();
}

function lackScreenRequest(obj,options){
	var form = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		height: 80,
		items: [{
			xtype: 'textfield',
			id: 'username',
			name: 'username',
			fieldLabel: '柜员号',
			maskRe: /^[a-zA-Z0-9]+$/,
			maxLength:8,
			allowBlank: false,
			blankText: '请输入柜员号'
		},{
			xtype: 'textfield',
			inputType: 'password',
			id: 'pass',
			name: 'pass',
			fieldLabel: '授权码',
			allowBlank: false,
			blankText: '请输入柜员授权码'
		}]
	});
	
	var win = new Ext.Window({
		title: '支付统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.request(options);
						},
						failure : function(form,action) {
							frm.reset();
							Ext.MessageBox.show({
								msg: action.result.msg,
								title: '错误提示',
								animEl: Ext.getBody(),
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR,
								modal: true,
								width: 250
							});
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	})
	win.show();
}
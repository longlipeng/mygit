Ext.onReady(function() {
	// 当前选择记录
	var record;
	// 中石油卡黑名单数据集
	var cardRiskStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'gridPanelStoreAction.asp?storeId=cnpcCardRiskInfo'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data',
							totalProperty : 'totalCount'
						}, [{
									name : 'customerProfile',
									mapping : 'customerProfile'
								}, {
									name : 'cardLevel',
									mapping : 'cardLevel'
								}, {
									name : 'checkCode',
									mapping : 'checkCode'
								}, {
									name : 'saState',
									mapping : 'saState'
								}, {
									name : 'initOprId',
									mapping : 'initOprId'
								}, {
									name : 'initTime',
									mapping : 'initTime'
								}, {
									name : 'verifierOprId',
									mapping : 'verifierOprId'
								}])
			});

	cardRiskStore.load({
				params : {
					start : 0
				}
			});

	var cardRiskColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '客户类别',
				dataIndex : 'customerProfile',
				width : 200,
				editor : new Ext.form.TextField({
							allowBlank : true,
							maxLength : 1,
							regex : /\d+/,
							vtype : 'isOverMax'
						})
			}, {
				header : '卡片等级',
				dataIndex : 'cardLevel',
				width : 120,
				editor : new Ext.form.TextField({
							allowBlank : true,
							maxLength : 1,
							regex : /\d+/,
							vtype : 'isOverMax'
						})
			}, {
				header : '标识',
				dataIndex : 'checkCode',
				width : 90,
				hidden : true
			}, {
				header : '状态',
				dataIndex : 'saState',
				width : 90,
				renderer : cnpcStateInfo
			}, {
				header : '申请人',
				dataIndex : 'initOprId',
				width : 90
			}, {
				header : '创建时间',
				dataIndex : 'initTime',
				width : 90
			}, {
				header : '审核人',
				dataIndex : 'verifierOprId',
				width : 90
			}]);
	function cnpcStateInfo(val) {
		if (val == '0')
			return "<font color='gray'>正常</font>";
		if (val == '1')
			return "<font color='red'>新增待审核</font>";
		if (val == '2')
			return "<font color='green'>修改带审核</font>";
		if (val == '3')
			return "<font color='gray'>删除待审核</font>";
		if (val == '4')
			return "<font color='gray'>修改审核拒绝</font>";
		if (val == '5')
			return "<font color='red'>新增审核拒绝</font>";
		if (val == '6')
			return "<font color='red'>删除审核拒绝</font>";
		if (val == '7')
			return "<font color='red'>已删除</font>";
	}

	var menuArray = new Array();
	
	var addMenu = {
		iconCls : 'add',
		width : 85,
		text : '添加',
		handler : function() {
			cardRiskWin.show();
			cardRiskWin.center();
		}
	};
	var delMenu = {
		text : '删除',
		width : 85,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			if (grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条转充黑名单吗？', grid, function(bt) {
							if (bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
											url : 'T40601Action.asp?method=delete',
											success : function(form, actiont) {
												var rspObj = Ext
														.decode(form.responseText);
												if (rspObj.success) {
													showSuccessMsg(rspObj.msg,
															grid);
													grid.getStore().reload();
													// grid.getTopToolbar().items.items[2].disable();
												} else {
													showErrorMsg(rspObj.msg,
															grid);
												}
											},
											params : {
												customerProfile : rec
														.get('customerProfile'),
												cardLevel : rec
														.get('cardLevel'),
												txnId : '40201',
												subTxnId : '03'
											}
										});
							}
						});
			}
		}
	};
	var queryMenu = {
		text : '查询',
		width : 85,
		id : 'query',
		iconCls : 'query',
		handler : function() {
			queryWin.show();
		}
	};
	
	var upMenu = {
		hidden : true,
		text : '保存',
		width : 85,
		iconCls : 'reload',
		disabled : true,
		handler : function() {
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if (modifiedRecords.length == 0) {
				return;
			}
			var array = new Array();
			for (var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					customerProfile : record.get('customerProfile'),
					cardLevel : record.get('cardLevel'),
					checkCode : record.get('checkCode')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
						url : 'T40601Action.asp?method=update',
						method : 'post',
						params : {
							roleInfoList : Ext.encode(array),
							txnId : '10301',
							subTxnId : '03'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							grid.enable();
							if (rspObj.success) {
								grid.getStore().commitChanges();
								showSuccessMsg(rspObj.msg, grid);
							} else {
								grid.getStore().rejectChanges();
								showErrorMsg(rspObj.msg, grid);
							}
							grid.getTopToolbar().items.items[6].disable();
							grid.getStore().reload();
							hideProcessMsg();
						}
					});
		}
	};

	menuArray.add(addMenu);// [0]
	menuArray.add('-'); // [1]
	menuArray.add(delMenu);// [2]
	menuArray.add('-'); // [3]
	menuArray.add(queryMenu);// [4]
	menuArray.add('-'); // [5]
	menuArray.add(upMenu);// [6]

	var grid = new Ext.grid.EditorGridPanel({
				title : '中石油转充黑名单信息',
				iconCls : 'card',
				frame : true,
				border : true,
				columnLines : true,
				stripeRows : true,
				region : 'center',
				clicksToEdit : true,
				store : cardRiskStore,
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				cm : cardRiskColModel,
				forceValidation : true,
				autoScroll : true,
				renderTo : Ext.getBody(),
				loadMask : {
					msg : '正在加载中石油转充黑名单列表......'
				},
				tbar : menuArray,
				bbar : new Ext.PagingToolbar({
							store : cardRiskStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
			});

	grid.getStore().on('beforeload', function() {
		grid.getTopToolbar().items.items[6].disable();
		grid.getStore().rejectChanges();
	});
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[6] != undefined) {
				grid.getTopToolbar().items.items[6].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		'rowselect' : function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
					.frame();
			rec = grid.getSelectionModel().getSelected();
			if (rec.get('saState') == '7') {
				grid.getTopToolbar().items.items[2].disable();
			} else {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});

	// 卡黑名单添加表单
	var cardRiskForm = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 400,
				autoHeight : true,
				waitMsgTarget : true,
				items : [{
							xtype : 'textfield',
							id : 'customerProfile',
							name : 'customerProfile',
							maxLength : 1,
							regex : /^[0-9]{1,19}$/,
							regexText : '客户类别只能是数字',
							fieldLabel : '客户类别',
							allowBlank : false,
							blankText : '客户类别不能为空',
							emptyText : '请输入客户类别'
						}, {
							xtype : 'textfield',
							name : 'cardLevel',
							maxLength : 1,
							fieldLabel : '卡片等级',
							allowBlank : false,
							regex : /^[0-9]{1,2}$/,
							blankText : '卡片等级不能为空',
							emptyText : '请输入卡片等级'
						}]
			});

	// 卡黑名单添加窗口
	var cardRiskWin = new Ext.Window({
		title : '添加中石油卡黑名单',
		iconCls : 'logo',
		layout : 'fit',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		width : 400,
		resizable : false,
		autoHeight : true,
		items : [cardRiskForm],
		closeAction : 'hide',
		buttonAlign : 'center',
		buttons : [{
			text : '确定',
			handler : function() {
				if (cardRiskForm.getForm().isValid()) {
					cardRiskForm.getForm().submit({
								url : 'T40601Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									showSuccessMsg(action.result.msg,
											cardRiskWin);
									// 重新加载参数列表
									grid.getStore().reload();
									cardRiskForm.getForm().reset();
									cardRiskWin.hide();
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, cardRiskWin);
								},
								params : {
									txnId : '40201',
									subTxnId : '01'
								}
							});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				cardRiskForm.getForm().reset();
			}
		}, {
			text : '关闭',
			handler : function() {
				cardRiskWin.hide();
			}
		}]
	});

	

	// 查询条件
	var queryForm = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 150,
				autoHeight : true,
				items : [{
					xtype : 'combo',
					fieldLabel : '状态',
					id : 'saState',
					name : 'saState',
					store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['0', '正常'], ['1', '新增待审核'],
										['2', '修改带审核'], ['3', '删除待审核'],
										['4', '修改审核拒绝'], ['5', '新增审核拒绝'],
										['6', '删除审核拒绝'], ['7', '已删除']]
							})
				}, {}]
			});

	var queryWin = new Ext.Window({
				title : '查询条件',
				layout : 'fit',
				width : 400,
				autoHeight : true,
				items : [queryForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				resizable : false,
				closable : true,
				animateTarget : 'query',
				tools : [{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10,
								Ext.getBody().getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				}],
				buttons : [{
							text : '查询',
							handler : function() {
								cardRiskStore.load();
								queryWin.hide();
							}
						}, {
							text : '清除查询条件',
							handler : function() {
								queryForm.getForm().reset();
							}
						}]
			});

	cardRiskStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
							start : 0,
							saState : queryForm.findById('saState').getValue()
						});
			});
	var mainView = new Ext.Viewport({
				layout : 'border',
				items : [grid],
				renderTo : Ext.getBody()
			});
});
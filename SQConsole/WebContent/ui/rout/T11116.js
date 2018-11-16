Ext.onReady(function() {
	var fcompanyStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('LOAD_CARD_BIN', function(ret) {
				fcompanyStore.loadData(Ext.decode(ret));
			});
	var instcodeStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('LOAD_INST_CODE', function(ret) {
				instcodeStore.loadData(Ext.decode(ret));
			});
	function cardRen(val) {
		if (fcompanyStore.query("valueField", val).first() == undefined) {
			return val;
		} else {
			return fcompanyStore.query("valueField", val).first().data.displayField;
		}
	}
	// 当前选择记录
	var record;
	// 卡黑名单数据集
	var cardRiskStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=cardbinAreaRouteInf'
				}),
		reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [{
							name : 'card_bin',
							mapping : 'card_bin'
						}, {
							name : 'area_no',
							mapping : 'area_no'
						}, {
							name : 'inst_code',
							mapping : 'inst_code'
						}, {
							name : 'state',
							mapping : 'state'
						}, {
							name : 'crt_per',
							mapping : 'crt_per'
						}, {
							name : 'crt_date',
							mapping : 'crt_date'
						}, {
							name : 'upt_per',
							mapping : 'upt_per'
						}, {
							name : 'upt_date',
							mapping : 'upt_date'
						}])
	});
	cardRiskStore.load({
				params : {
					start : 0
				}
			});

	var cardRiskColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '卡bin',
				dataIndex : 'card_bin',
				width : 200/*,
				editor : new Ext.form.ComboBox({
							store : fcompanyStore,
							displayField: 'displayField',
							valueField: 'valueField',
							mode: 'local',
							hiddenName : 'card_bin',
							triggerAction: 'all',
							allowBlank : false,
							forceSelection: true,
							typeAhead: true,
							selectOnFocus: true,
							editable: true
						}),
		     renderer:cardRen*/
			}, {
				header : '地区码',
				dataIndex : 'area_no',
				width : 120/*,
				editor: new Ext.form.TextField({
	     		 	maxLength: 2,
	     			allowBlank: false,
	     			id:'area_no',
	     			regex : /^[0-9]{1,2}$/,
					regexText:'请输入地区码'
		     	})*/
			}, {
				header : '机构码',
				dataIndex : 'inst_code',
				width : 280
			}, {
				header : '审核状态',
				dataIndex : 'state',
				width : 90,
				renderer : riskInfoState
			}, {
				header : '申请人',
				dataIndex : 'crt_per',
				width : 90
			}, {
				header : '申请时间',
				dataIndex : 'crt_date',
				width : 120,
				renderer : formatTs
			}, {
				header : '审核人',
				dataIndex : 'upt_per',
				width : 90
			}, {
				header : '审核时间',
				dataIndex : 'upt_date',
				width : 120,
				renderer : formatTs
			}]);

	var menuArray = new Array();

	/*var addMenu = {
		iconCls : 'add',
		width : 85,
		text : '添加',
		handler : function() {
			cardRiskWin.show();
			cardRiskWin.center();
		}
	};*/
	var passMenu = {
		text : '通过',
		width : 85,
		iconCls : 'accept',
		disabled : true,
		handler : function() {
			if (grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var card_bin = rec.get('card_bin');
				var area_no = rec.get('area_no');
				var state = rec.get('state');
				showConfirm('确定要通过该卡bin配置吗？卡bin：' + card_bin, grid,
						function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
											url : 'T11115Action.asp?method=accept',
											success : function(rsp, opt) {
												var rspObj = Ext
														.decode(rsp.responseText);
												if (rspObj.success) {
													showSuccessMsg(rspObj.msg,
															grid);
													grid.getStore().reload();
												} else {
													showErrorMsg(rspObj.msg,
															grid);
												}
											},
											params : {
												card_bin : card_bin,
												area_no : area_no,
												state : state,
												txnId : '11115',
												subTxnId : '04'
											}
										});
							}
						});
			}
		}
	};
	var refuseMenu = {
		text : '拒绝',
		width : 85,
		iconCls : 'refuse',
		disabled : true,
		handler : function() {
			if (grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var card_bin = rec.get('card_bin');
				var area_no = rec.get('area_no');
				var state = rec.get('state');
				showConfirm('确定要拒绝该卡bin配置吗？卡bin：' + card_bin, grid,
						function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
											url : 'T11115Action.asp?method=refuse',
											success : function(rsp, opt) {
												var rspObj = Ext.decode(rsp.responseText);
												if (rspObj.success) {
													showSuccessMsg(rspObj.msg,
															grid);
													grid.getStore().reload();
												} else {
													showErrorMsg(rspObj.msg,
															grid);
												}
											},
											params : {
												card_bin : card_bin,
												area_no : area_no,
												state : state,
												txnId : '11115',
												subTxnId : '05'
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
	menuArray.add(passMenu);//[0]
	menuArray.add('-');//[1]
	menuArray.add(refuseMenu);//[2]
	menuArray.add('-');//[3]
	menuArray.add(queryMenu); // [4]

	var grid = new Ext.grid.EditorGridPanel({
				title : '卡Bin地区路由配置',
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
					msg : '正在加载卡黑名单列表......'
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
				grid.getTopToolbar().items.items[0].disable();
				grid.getTopToolbar().items.items[2].disable();
				grid.getStore().rejectChanges();
	});
	grid.getSelectionModel().on({
		// 单击行，使删除按钮可用
		'rowselect' : function() {
			// 行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if (rec.get('state') == '0' ||rec.get('state') == '3' || rec.get('state') == '4'){// 删除待审核状态，不可以删除
				grid.getTopToolbar().items.items[0].enable();
				grid.getTopToolbar().items.items[2].enable();
			}else{
				grid.getTopToolbar().items.items[0].disable();
				grid.getTopToolbar().items.items[2].disable();
			}
		}
	});

	// 查询条件
	var queryForm = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 300,
				autoHeight : true,
				items : [{}, {
							xtype : 'textfield',
							id : 'card_bin',
							name : 'card_bin',
							maxLength : 19,
							regex : /^[0-9]{1,19}$/,
							regexText : '卡BIN只能是数字',
							fieldLabel : '卡BIN'
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
							card_bin : queryForm.findById('card_bin').getValue()
						});
			});
	var mainView = new Ext.Viewport({
				layout : 'border',
				items : [grid],
				renderTo : Ext.getBody()
			});
});
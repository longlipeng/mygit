Ext.onReady(function() {

	// 商户事后风险预警系数数据集
 	var allCom=Ext.data.Record.create([
 	{
        name: 'valueField',
        mapping:'valueField',
        type: 'string' 
    }, {
        name: 'displayField',
        mapping:'displayField',
        type: 'string' 
    }
 	]);
		var cstAfterRuleInfStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=cstAfterRuleInf'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {
				name: 'ruleId',
				mapping: 'ruleId'
			},{
				name : 'instId',
				mapping : 'instId'
			}, {
				name : 'mcc',
				mapping : 'mcc'
			}, {
				name : 'days',
				mapping : 'days'
			}, {
				name : 'warnLvt',
				mapping : 'warnLvt'
			}, {
				name : 'warnCount',
				mapping : 'warnCount'
			}, {
				name : 'warnAmt',
				mapping : 'warnAmt'
			},{
				name : 'saState',
				mapping : 'saState'
			}, {
				name : 'createTime',
				mapping : 'createTime'
			}, {
				name : 'updateTime',
				mapping : 'updateTime'
			}, {
				name : 'creator',
				mapping : 'creator'
			}, {
				name : 'updateOpr',
				mapping : 'updateOpr'
			}]),
			autoLoad : true
		});
		cstAfterRuleInfStore.load( {
			params : {
				start : 0
			}
		});
				
	// 规则编号数据集
	var ruleStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RULE_ID',function(ret){
		ruleStore.loadData(Ext.decode(ret));
	});		
	//分公司编号数据集
	var branchStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRANCH_NO',function(ret){
		branchStore.loadData(Ext.decode(ret));
		var r=new allCom();
		r.data.valueField='*';
		r.data.displayField='*-所有分公司';
		branchStore.insert(0,r);
	});
		
		var CstAfterRuleInfColModel = new Ext.grid.ColumnModel( [ {
			header : '规则编号',
			dataIndex : 'ruleId',
			width : 100
//			editor : new Ext.form.ComboBox( {
//				store: ruleStore,
//				displayField: 'displayField',
//				valueField: 'valueField',
//				mode: 'local',
//				triggerAction: 'all',
//				forceSelection: true,
//				typeAhead: true,
//				selectOnFocus: true,
//				edit: false
//		})
		}, {
			header : '分公司编号',
			dataIndex : 'instId',
			width : 100
//			editor : new Ext.form.ComboBox( {
//				store: branchStore,
//				displayField: 'displayField',
//				valueField: 'valueField',
//				mode: 'local',
//				triggerAction: 'all',
//				forceSelection: true,
//				typeAhead: true,
//				selectOnFocus: true
//			})
		}, {
			header : '商户MCC码',
			dataIndex : 'mcc',
			width : 100
		}, {
			header : '天数',
			dataIndex : 'days',
			width : 80,
			editor: new Ext.form.TextField({
				allowBlank: false,
				blankText: '请输入天数',
				regex: /^([1-9]\d{0,7}|0)$/,
				regexText: '只能输入数字'
			})
		}, {
			header : '预警系数',
			dataIndex : 'warnLvt',
			width : 80,
			editor: new Ext.form.TextField({
				allowBlank: false,
				blankText: '请输入预警系数',
				maxLength: 10,
				maxLengthText: '最多可输入10位数字',
				regex : /^([0-9]\d{0,7}|0)(\.?[0-9]{0,2})$/,
				regexText: '请输入数字'
			})
		}, {
			header : '预警笔数',
			dataIndex : 'warnCount',
			width : 80,
			editor: new Ext.form.TextField({
				allowBlank: false,
				blankText: '请输入预警笔数',
				regex : /^([1-9]\d{0,7}|0)$/,
				regexText: '请输入数字'
			})
		},{
			header : '预警金额（元）',
			dataIndex : 'warnAmt',
			width : 80,
			editor: new Ext.form.TextField({
				allowBlank: false,
				blankText: '请输入预警金额',
				maxLength: 15,
				maxLengthText: '最多可输入15位数字',
				regex : /^([0-9]\d{0,7}|0)(\.?[0-9]{0,2})$/,
				regexText: '请输入数字'
			})
		}, {
			header : '状态',
			dataIndex : 'saState',
			width : 80,
			renderer: riskInfoState
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
			renderer: formatTs,
			width : 100
		}, {
			header : '修改时间',
			dataIndex : 'updateTime',
			renderer: formatTs,
			width : 100
		}, {
			header : '创建者',
			dataIndex : 'creator',
			width : 100
		}, {
			header : '修改者',
			dataIndex : 'updateOpr',
			width : 100
		} ]);

		var menuArr = new Array();
		// 商户事后风险预警系数添加表单
		var cstAfterRiskInfForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 650,
			labelWidth : 90,
			waitMsgTarget : true,
			items : [ {
				xtype: 'basecomboselect',
				fieldLabel: '规则编号*',
				allowBlank: false,
				store: ruleStore,
				name: 'ruleId',
				hiddenName: 'ruleId',
				id : 'idruleId',
				blankText: '规则编号不能为空',
				emptyText: '请选择规则编号',
				width:500
			}, {
				xtype: 'basecomboselect',
				fieldLabel : '分公司编号*',
				allowBlank : false,
				store:branchStore,
				hiddenName : 'instId',
				id : 'idinstId',
				blankText: '分公司编号不能为空',
				emptyText: '请选择分公司编号',
				width : 350/*,
				listeners: {
					'select': function() {
						cstAfterRiskInfForm.findById('idmcc').disable();
					}
				}*/
			},{
				fieldLabel : '商户MCC码*',
				allowBlank : false,
				xtype: 'textfield',
				id : 'idmcc',
				name : 'mcc',
				maxLength:4,
				width : 150/*,
				listeners: {
					'blur': function() {
						if(cstAfterRiskInfForm.findById('idmcc').getValue() != "")
							cstAfterRiskInfForm.findById('idinstId').disable();
						else 
							cstAfterRiskInfForm.findById('idinstId').enable();
					}
				}*/
			}, {
				fieldLabel : '天数',
				xtype: 'textfield',
				allowBlank: true,
				id : 'iddays',
				name : 'days',
				regex : /^([1-9]\d{0,7}|0)$/,
				regexText: '请输入数字',
				width : 150
			}, {
				fieldLabel : '预警系数',
				allowBlank : true,
				xtype: 'textfield',
				id : 'idwarnLvt',
				maxLength: 10,
				maxLengthText: '最多可输入10位数字',
				regex : /^([0-9]\d{0,7}|0)(\.?[0-9]{0,2})$/,
				regexText: '请输入合适的数字',
				name : 'warnLvt',
				width : 150
			} ,{
				fieldLabel : '预警笔数',
				allowBlank : true,
				xtype: 'textfield',
				id : 'idwarnCount',
				regex : /^([1-9]\d{0,7}|0)$/,
				regexText: '请输入数字',
				name : 'warnCount',
				width : 150
			},{
				fieldLabel : '预警金额（元）',
				allowBlank : true,
				xtype: 'textfield',
				maxLength: 15,
				maxLengthText: '最多可输入15位数字',
			    regex : /^([0-9]\d{0,7}|0)(\.?[0-9]{0,2})$/,
				regexText: '请输入合适的数字',
				id : 'idwarnAmt',
				name : 'warnAmt',
				width : 150
			}]
		});
		// 商户事后风险预警系数添加窗口
		var cstAfterRiskInfWin = new Ext.Window({
			title : '商户事后风险预警系数添加',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 650,
			autoHeight : true,
			layout : 'fit',
			items : [ cstAfterRiskInfForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [
					{
						text : '确定',
						handler : function() {
							if(cstAfterRiskInfForm.findById('idmcc').getValue().trim() == ""){
								showErrorMsg('MCC码不能为空或空字符串',cstAfterRiskInfWin);
								cstAfterRiskInfForm.findById('idmcc').setValue('');
								return ;
							}
							if (cstAfterRiskInfForm.getForm().isValid()) {
								cstAfterRiskInfForm.getForm().submit(
								{
									url : 'T40501Action.asp?method=add',
									waitMsg : '正在提交，请稍后......',
									success : function(form,action) {
									showSuccessMsg(action.result.msg,cstAfterRiskInfWin);
									// 重置表单
									cstAfterRiskInfForm.getForm().reset();
									cstAfterRiskInfWin.hide();
									// 重新加载分公司列表
									grid.getStore().reload();
								},
								failure : function(form,action) {
									showErrorMsg(action.result.msg,cstAfterRiskInfWin);
								},
								params : {
									txnId : '40501',
									subTxnId : '01'
								}
								});
							}
						}
					}, {
						text : '重置',
						handler : function() {
							cstAfterRiskInfForm.getForm().reset();
							cstAfterRiskInfForm.findById('idmcc').enable();
							cstAfterRiskInfForm.findById('idinstId').enable();
						}
					}, {
						text : '关闭',
						handler : function() {
							cstAfterRiskInfWin.hide(grid);
						}
					} ]
				});
		// 添加
		var addMenu = {
			text : '添加',
			width : 85,
			iconCls : 'add',
			handler : function() {
				cstAfterRiskInfWin.show();
				cstAfterRiskInfWin.center();
				cstAfterRiskInfForm.findById('idmcc').enable();
				cstAfterRiskInfForm.findById('idinstId').enable();
			}
		};
		// 保存
		var upMenu = {
			text : '保存',
			width : 85,
			iconCls : 'reload',
			disabled : true,
			handler : function() {
				var modifiedRecords = grid.getStore().getModifiedRecords();
				if (modifiedRecords.length == 0) {
					return;
				}
				var store = grid.getStore();
				var len = store.getCount();
				for ( var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}
			// 存放要修改的参数信息
			var array = new Array();
			for ( var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					ruleId : record.get('ruleId'),
					instId : record.get('instId'),
					mcc : record.get('mcc'),
					saState : record.get('saState'),
					days : record.get('days'),
					warnLvt : record.get('warnLvt'),
					warnCount : record.get('warnCount'),
					warnAmt : record.get('warnAmt')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T40501Action.asp?method=update',
				method : 'post',
				params : {
				    cstAfterRuleInfList : Ext.encode(array),
					txnId : '40501',
					subTxnId : '02'
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
					hideProcessMsg();
					grid.getStore().reload();
					
				}
			});
			grid.getTopToolbar().items.items[6].disable();
			
			hideProcessMsg();
		}
		};
		// 删除
		var delMenu = {
			text : '删除',
			width : 85,
			iconCls : 'delete',
			disabled : true,
			handler : function() {
				if (grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();

					showConfirm('确定要删除该条记录吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.requestNeedAuthorise( {
									url : 'T40501Action.asp?method=delete',
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);
											grid.getStore().reload();
											grid.getTopToolbar().items.items[3].disable();
										} else {
											showErrorMsg(rspObj.msg, grid);
										}
									},
									params : {
										ruleId : rec.get('ruleId'),
										instId : rec.get('instId'),
										mcc : rec.get('mcc'),
										txnId : '40501',
										subTxnId : '03'
									}
								});
							}
						});
				}
			}
		};

		var queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};

		menuArr.push(queryConditionMebu); // [0]
		menuArr.push('-'); // [1]
		menuArr.push(addMenu); // [2]
		menuArr.push('-');
		menuArr.push(delMenu);
		menuArr.push('-');
		menuArr.push(upMenu);
		
		// 商户事后风险预警系数信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '商户事后风险预警系数信息',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			// autoExpandColumn: 'cardAccpName',
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : cstAfterRuleInfStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : CstAfterRuleInfColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载商户事后风险预警系数列表......'
			},
			tbar : menuArr,
			bbar : new Ext.PagingToolbar( {
				store : cstAfterRuleInfStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});

		grid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
				rec = grid.getSelectionModel().getSelected();
				if(rec.get('saState')=='4'){//删除 待审核状态 ,禁修修改删除
					grid.getTopToolbar().items.items[4].disable();
				}else if(rec.get('saState')=='3'){//修改待审核状态,禁止删除
					grid.getTopToolbar().items.items[4]. disable();
				}else{
					grid.getTopToolbar().items.items[4]. enable();
				}
			}
		});
		// 每次在列表信息加载前都将保存按钮屏蔽
		grid.getStore().on('beforeload', function() {
			grid.getTopToolbar().items.items[6].disable();
			grid.getStore().rejectChanges();
		});
		grid.on( {
				//删除 待审核状态 禁修改止
				'beforeedit':function(e){
					if(e.record.get('saState')=='4'||e.record.get('saState')=='1'){
						e.cancel=true;
					}
				},
			// 在编辑单元格后使保存按钮可用
				'afteredit' : function(e) {
					if (grid.getTopToolbar().items.items[6] != undefined) {
						grid.getTopToolbar().items.items[6].enable();
					}
				}
			});
		/** *************************查询条件************************ */

		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 650,
			autoHeight : true,
			items : [{
				name : 'ruleId',
				fieldLabel : '规则编号',
				xtype : 'dynamicCombo',
				store:ruleStore,
				//methodName: 'getMchntId',
				id : 'idruleId2',
				hiddenName : 'ruleId',
				width : 500
			}, {
				name : 'instId',
				fieldLabel : '分公司编号',
				allowBlank : false,
				xtype: 'basecomboselect',
				store:branchStore,
				id : 'idinstId2',
				hiddenName : 'instId',
				width : 200
//				,listeners: {20120831注释掉
//					'select': function() {
//						if(queryForm.findById('idinstId2').getValue() != ""){
//							queryForm.findById('idmcc2').setValue("*");
//							queryForm.findById('idmcc2').disable();
//						}else 
//							queryForm.findById('idmcc2').reset();
//					}
//				}
			}, {
				name : 'mcc',
				fieldLabel : '商户MCC码',
				xtype: 'textfield',
				id : 'idmcc2',
				hiddenName : 'mcc',
				width : 200
//				,listeners: {20120831注释掉
//					'blur': function() {
//						if(queryForm.findById('idmcc2').getValue() != ""){
//							queryForm.findById('idinstId2').setValue("*");
//							queryForm.findById('idinstId2').disable();
//						}else 
//							queryForm.findById('idinstId2').reset();
//					}
//				}
			}]
		});

		var queryWin = new Ext.Window( {
			title : '查询条件',
			layout : 'fit',
			width : 650,
			autoHeight : true,
			items : [ queryForm ],
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
							queryWin.setPosition(10, Ext.getBody().getViewSize().height - 30);
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
					} ],
			buttons : [ {
				text : '查询',
				handler : function() {
				cstAfterRuleInfStore.load({
					params : {
						start : 0,
						ruleId: queryForm.findById('idruleId2').getValue(),
						instId: queryForm.findById('idinstId2').getValue(),
						mcc: queryForm.findById('idmcc2').getValue()
						}
					});
			}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
					queryForm.findById('idmcc2').enable();
					queryForm.findById('idinstId2').enable();
				}
			} ]
		});

		/*ctlTxnInfoStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				saBrhNo : queryForm.findById('searchSaBrhNo').getValue()
			});
		});*/

		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
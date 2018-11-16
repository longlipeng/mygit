Ext.onReady(function() {

	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	
	// 终端通道信息数据集
	var termChannelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termChannelInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   {name : 'id',mapping : 'id'}, 
		   {name : 'termins',mapping : 'termins'},
		   {name : 'mchtmcc',mapping : 'mchtmcc'},
		   {name : 'mchtid',mapping : 'mchtid'},
		   {name : 'mchttermid',mapping : 'mchttermid'},
		   {name : 'insmcc',mapping : 'insmcc'},
		   {name : 'insmcht',mapping : 'insmcht'},
		   {name : 'insterm',mapping : 'insterm'},
		   {name : 'lmk',mapping : 'lmk'},
		   {name : 'stat',mapping : 'stat'},
		   {name : 'moditime',mapping : 'moditime'},
		   {name : 'modioprid',mapping : 'modioprid'},
		   {name : 'checktime',mapping : 'checktime'},
		   {name : 'checkoprid',mapping : 'checkoprid'},
		   {name : 'reserve01',mapping : 'reserve01'}
		])
		,autoLoad : true
	});
		
		function agencyRen(val){
			return agencyStore.query("valueField",val).first().data.displayField;
		}
	
		var routColModel = new Ext.grid.ColumnModel( [{
			header : '主键',	
			dataIndex : 'id',
			width : 80,
			hidden: true
		},{
			header : '所属机构',
			dataIndex : 'termins',
			width : 160,
			editor : new Ext.form.ComboBox( {
				store: agencyStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			}),renderer:agencyRen
		}, {
			header : '商户MCC码',
			dataIndex : 'mchtmcc',
			width : 80,
			editor : new Ext.form.TextField( {
				maxLength : 4,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '商户号',
			dataIndex : 'mchtid',
			width : 100,
			editor : new Ext.form.TextField( {
				maxLength : 15,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '商户终端号',
			dataIndex : 'mchttermid',
			width : 80,
			editor : new Ext.form.TextField( {
				maxLength : 30,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '机构MCC码',
			dataIndex : 'insmcc',
			width : 80,
			editor : new Ext.form.TextField( {
				maxLength : 30,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '机构商户号',
			dataIndex : 'insmcht',
			width : 100,
			editor : new Ext.form.TextField( {
				maxLength : 15,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '机构终端号',
			dataIndex : 'insterm',
			width : 80,
			editor : new Ext.form.TextField( {
				maxLength : 30,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		}, {
			header : '主密钥',
			dataIndex : 'lmk',
			width : 120,
			editor : new Ext.form.TextField( {
				maxLength : 32,
//				allowBlank : false,
				vtype : 'isOverMax'
			})
		},{
			header : '索引值',
			dataIndex : 'reserve01',
			width : 80,
			renderer: DecToHex
//			,editor : new Ext.form.TextField( {
//				maxLength : 4,
//				minLength : 4,
//				allowBlank : false,
//				vtype : 'isOverMax'
//			})
		},{
			header : '状态',	
			dataIndex : 'stat',
			width : 80,
			renderer: riskInfoState
		},{
			header : '修改时间',	
			dataIndex : 'moditime',
			width : 120,
			renderer: formatTs
		}, {
			header : '修改人',	
			dataIndex : 'modioprid',
			width : 60
		}, {
			header : '审核时间',	
			dataIndex : 'checktime',
			width : 120,
			renderer: formatTs
		}, {
			header : '审核人',	
			dataIndex : 'checkoprid',
			width : 60
		}]);
		
		var menuArr = new Array();
		// 添加表单
		var routInfoForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 350,
			labelWidth : 90,
			defaultType : 'textfield',
			waitMsgTarget : true,
			items : [  {
				fieldLabel : '所属机构*',
				methodName:'getNormalAgentID',
				allowBlank : false,
				emptyText : '请选择所属机构',
				blankText : '所属机构不能为空',
				xtype: 'dynamicCombo',
				id : 'idtermIns',
				hiddenName: 'termIns',
				width : 200
			},{
				fieldLabel : '商户MCC码*',
				allowBlank : false,
				blankText : '商户MCC码不能为空',
				emptyText : '请输入商户MCC码',
				id : 'idmchtMcc',
				name : 'mchtMcc',
//				maskRe : /^[0-9]$/,
				width : 200,
				maxLength : 4
			}, {
				fieldLabel : '商户号*',
				allowBlank : false,
				blankText : '商户号不能为空',
				emptyText : '请输入商户号',
				id : 'idmchtId',
				name : 'mchtId',
				width : 200,
				maxLength : 15
			}, {
				fieldLabel : '商户终端号*',
				allowBlank : false,
				emptyText : '请输入商户终端号',
				id : 'idmchtTermId',
				name : 'mchtTermId',
				width : 200,
				maxLength : 8
			}, {
				fieldLabel : '机构MCC码*',
				allowBlank : false,
				emptyText : '请输入机构MCC码',
				id : 'idinsMcc',
				name : 'insMcc',
				width : 200,
				maxLength : 4
			}, {
				fieldLabel : '机构商户号*',
				emptyText : '请输入机构商户号',
				allowBlank : false,
				id : 'idinsMcht',
				name : 'insMcht',
				width : 200,
				maxLength : 15
			}, {
				fieldLabel : '机构终端号*',
				emptyText : '请输入机构终端号',
				allowBlank : false,
				id : 'idinsTerm',
				name : 'insTerm',
				width : 200,
				maxLength : 8
			}, {
				fieldLabel : '主密钥',
				blankText : '请输入主密钥',
				id : 'idlmk',
				name : 'lmk',
				maskRe: /^[0-9]|[a-f]|[A-F]$/,
                maxLength:32,
				width : 200
			}, {
				fieldLabel : '再次输入主密钥',
				blankText : '请再次输入主密钥',
				id : 'idlmk2',
				name : 'lmk2',
				maskRe: /^[0-9]|[a-f]|[A-F]$/,
                maxLength:32,
				width : 200
			}, {
				fieldLabel : '索引值*',
				blankText : '请输入索引值',
				allowBlank : false,
				id : 'idreserve01',
				name : 'reserve01',
				regex: /^[0-9]+$/,
                maxLength : 4,
                minLength : 4,
				width : 80
			} ]
		});
		
		// 添加窗口
		var routWin = new Ext.Window( {
			title : '终端通道配置信息添加',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 350,
			autoHeight : true,
			layout : 'fit',
			items : [ routInfoForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
				text : '确定',
				handler : function() {
					if (routInfoForm.getForm().isValid()) {
						if(Ext.getCmp('idlmk').getValue()!=Ext.getCmp('idlmk2').getValue()){
							showErrorMsg("两次输入的主密钥不一致！请重新输入",routWin);
							routInfoForm.setActiveTab('idlmk2');
							return;
						}
						routInfoForm.getForm().submit({
							url : 'T11015Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, routInfoForm);
								// 重置表单
								routInfoForm.getForm().reset();
								routWin.hide();
								// 重新加载列表
								grid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg,routInfoForm);
							},
							params : {
								txnId : '11015',
								subTxnId : '01'
							}
						});
					}
				}
				}, {
					text : '重置',
					handler : function() {
						routInfoForm.getForm().reset();
					}
				}, {
					text : '关闭',
					handler : function() {
						routWin.hide(grid);
					}
				} ]
			});
		// 添加
		var addMenu = {
			text : '添加',
			width : 85,
			iconCls : 'add',
			handler : function() {
				routWin.show();
				routWin.center();
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
					id : record.get('id'),
					termIns : record.get('termins'),
					mchtMcc : record.get('mchtmcc'),
					mchtId : record.get('mchtid'),
					mchtTermId : record.get('mchttermid'),
					insMcc : record.get('insmcc'),
					insMcht : record.get('insmcht'),
					insTerm : record.get('insterm'),
					lmk : record.get('lmk'),
					reserve01 : record.get('reserve01')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T11015Action.asp?method=update',
				method : 'post',
				params : {
					parameterList : Ext.encode(array),
					txnId : '11015',
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
					grid.getStore().reload();
					hideProcessMsg();
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
									url : 'T11015Action.asp?method=delete',
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
										id : rec.get('id'),
										txnId : '11015',
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
		
		// 终端通道配置信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '终端通道配置信息维护',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : termChannelStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : routColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载终端通道配置信息列表......'
			},
			tbar : menuArr,
			bbar : new Ext.PagingToolbar( {
				store : termChannelStore,
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
				if(rec.get('stat')=='4'){//删除 待审核状态 禁修改删除
					grid.getTopToolbar().items.items[4].disable();
				}else if(rec.get('stat')=='3'){//修改待审核的状态禁止删除
					grid.getTopToolbar().items.items[4]. disable();
				}else if(rec.get('stat')=='1'){//删除状态 禁修改删除
					grid.getTopToolbar().items.items[4]. disable();
				}else if(rec.get('stat')=='0'){//新增待审核状态 禁修改删除
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
			//删除待审核状态 禁止修改
			'beforeedit':function(e){
				if(e.record.get('stat')=='4'||e.record.get('stat')=='1'){
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
			width : 200,
			autoHeight : true,
			items : [{
				fieldLabel : '所属机构',
				xtype: 'dynamicCombo',
  			    methodName:'getNormalAgentID',
  			    hiddenName : 'searchAgent',
				id : 'idsearchAgent',
				width : 190
			}, {
				xtype: 'textfield',
				id : 'idsearchMchtMcc',
				width : 180,
				name : 'searchMchtMcc',
				fieldLabel : '商户MCC码'
			}, {
				xtype: 'textfield',
				id : 'idsearchMchtId',
				width : 180,
				name : 'searchMchtId',
				fieldLabel : '商户号'
			}, {
				xtype: 'textfield',
				id : 'idsearchMchtTermId',
				width : 180,
				name : 'searchMchtTermId',
				fieldLabel : '商户终端号'
			}, {
				xtype: 'textfield',
				id : 'idsearchInsMcc',
				width : 180,
				name : 'searchInsMcc',
				fieldLabel : '机构MCC码'
			}, {
				xtype: 'textfield',
				id : 'idsearchInsMcht',
				width : 180,
				name : 'searchInsMcht',
				fieldLabel : '机构商户号'
			} , {
				xtype: 'textfield',
				id : 'idsearchInsTerm',
				width : 180,
				name : 'searchInsTerm',
				fieldLabel : '机构终端号'
			}]
		});

		var queryWin = new Ext.Window( {
			title : '查询条件',
			layout : 'fit',
			width : 400,
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
			buttons : [{
				text : '查询',
				handler : function() {
					termChannelStore.load();
				}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
				}
			}]
		});

		termChannelStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				termIns : queryForm.findById('idsearchAgent').getValue(),
				mchtMcc : queryForm.findById('idsearchMchtMcc').getValue(),
				mchtId : queryForm.findById('idsearchMchtId').getValue(),
				mchtTermId : queryForm.findById('idsearchMchtTermId').getValue(),
				insMcc : queryForm.findById('idsearchInsMcc').getValue(),
				insMcht : queryForm.findById('idsearchInsMcht').getValue(),
				insTerm : queryForm.findById('idsearchInsTerm').getValue()
			});
		});
		
		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
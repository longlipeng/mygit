Ext.onReady(function() {

	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	
	// 待审核的终端通道配置信息数据集
		var termChannelStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=termChannelInfToCheck'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ 
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
			]),
			autoLoad : true
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
//			editor : new Ext.form.ComboBox( {
//				store: agencyStore,
//				displayField: 'displayField',
//				valueField: 'valueField',
//				mode: 'local',
//				triggerAction: 'all',
//				forceSelection: true,
//				typeAhead: true,
//				selectOnFocus: true,
//				editable: false
//			})
			renderer:agencyRen
		}, {
			header : '商户MCC码',
			dataIndex : 'mchtmcc',
			width : 80
		}, {
			header : '商户号',
			dataIndex : 'mchtid',
			width : 100
		}, {
			header : '商户终端号',
			dataIndex : 'mchttermid',
			width : 80
		}, {
			header : '机构MCC码',
			dataIndex : 'insmcc',
			width : 80
		}, {
			header : '机构商户号',
			dataIndex : 'insmcht',
			width : 100
		}, {
			header : '机构终端号',
			dataIndex : 'insterm',
			width : 80
		},{
			header : '主密钥',
			dataIndex : 'lmk',
			width : 120
		},{
			header : '索引值',
			dataIndex : 'reserve01',
			width : 80,
			renderer: DecToHex
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
		} ]);

		var menuArray = new Array();
		var acceptMenu = {
				text: '通过',
				width: 85,
				iconCls: 'accept',
				disabled: true,
				handler:function() {
					showConfirm('确认审核通过吗？',grid,function(bt) {
						if(bt == 'yes') {
							showProcessMsg('正在提交审核信息，请稍后......');
							rec = grid.getSelectionModel().getSelected();
							Ext.Ajax.request({
								url: 'T11016Action.asp?method=accept',
								params: {
								    id: rec.get('id'),
									txnId: '11016',
									subTxnId: '01'
								},
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
									// 重新加载待审核信息
									grid.getStore().reload();
								}
							});
							hideProcessMsg();
						}
					});
				}
			};
		
		var refuseMenu = {
				text: '拒绝',
				width: 85,
				iconCls: 'refuse',
				disabled: true,
				handler:function() {
					showConfirm('确认拒绝吗？',grid,function(bt) {
						if(bt == 'yes') {
							showInputMsg('提示','请输入拒绝原因',true,refuse);
						}
					});
				}
			};
			
			// 拒绝按钮调用方法
			function refuse(bt,text) {
				if(bt == 'ok') {
					if(getLength(text) > 60) {
						alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
						showInputMsg('提示','请输入拒绝原因',true,refuse);
						return;
					}
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T11016Action.asp?method=refuse',
						params: {
						    id: rec.get('id'),
							txnId: '11016',
							subTxnId: '02',
							refuseInfo: text
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			}
		
		var queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
		menuArray.push(queryConditionMebu); // [0]
		menuArray.add('-');               //[1]
		menuArray.add(acceptMenu);        //[2]
		menuArray.add('-');               //[3]
		menuArray.add(refuseMenu);        //[4]
		
		// 终端通道配置信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '终端通道配置信息审核',
			iconCls : 'T11016',
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
			tbar : menuArray,
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
				grid.getTopToolbar().items.items[2].enable();
				grid.getTopToolbar().items.items[4].enable();
			}
		});
		/** *************************查询条件************************ */

		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 200,
			autoHeight : true,
			items : [ {
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
			} ]
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
			buttons : [ {
				text : '查询',
				handler : function() {
					termChannelStore.load();
				}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
				}
			} ]
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
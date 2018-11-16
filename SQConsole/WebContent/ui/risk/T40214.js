Ext.onReady(function() {

	// 商户交易权限信息数据集
		var tblRiskMchtTranLimit = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=tblRiskMchtTranLimit'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {name: 'id',mapping: 'id'},{
				name : 'mchtNo',
				mapping : 'mchtNo'
			}, {
				name : 'channelold',
				mapping : 'channelold'
			}, {
				name : 'bussTypeold',
				mapping : 'bussTypeold'
			}, {
				name : 'txnNumold',
				mapping : 'txnNumold'
			}, {
				name : 'cardTypeold',
				mapping : 'cardTypeold'
			}, {
				name : 'limit',
				mapping : 'limit'
			},{
				name : 'saState',
				mapping : 'saState'
			}, 	
			{name: 'creOprId',mapping: 'creOprId'},
			{name: 'upOprId',mapping: 'upOprId'},
			{name: 'creTime',mapping: 'creTime'},
			{name: 'upTime',mapping: 'upTime'},
			{name: 'mchtNm',mapping: 'mchtNm'}
			]),
			autoLoad : true
		});
	tblRiskMchtTranLimit.load( {
		params : {
			start : 0
		}
	});
				
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});		
	//渠道数据集
	var channelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_CHANNEL',function(ret){
		channelStore.loadData(Ext.decode(ret));
	});
	//业务数据集
	var serverStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_BUSSTYPE',function(ret){
		serverStore.loadData(Ext.decode(ret));
	});
	//交易数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_LIMIT_CARDTYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});	
	//权限
	var limitStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('LIMIT',function(ret){
		limitStore.loadData(Ext.decode(ret));
	});	
	function txnRen(val){
		return txnStore.query("valueField",val).first().data.displayField;
		 
	}
	function txnChannel(val){
		return channelStore.query("valueField",val).first().data.displayField;
		 
	}
	function serverRen(val){
		return serverStore.query("valueField",val).first().data.displayField;
		 
	}
	function cardRen(val){
		return cardTypeStore.query("valueField",val).first().data.displayField;
		 
	}
		
		var ctlTxnInfColModel = new Ext.grid.ColumnModel( [ {
			header : '商户编号',
			dataIndex : 'mchtNo',
			width : 100,
			sortable : true
		},{
			header : '商户名',
			dataIndex : 'mchtNm',
			width : 100,
			sortable : true
		}, {
			header : '交易渠道',
			dataIndex : 'channelold',
			width : 150,
			editor : new Ext.form.ComboBox( {
				store: channelStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})
			,renderer:txnChannel
		}, {
			header : '业务类型',
			dataIndex : 'bussTypeold',
			width : 150,
			editor : new Ext.form.ComboBox( {
				store: serverStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})
			,renderer:serverRen
		}, {
			header : '交易类型',
			dataIndex : 'txnNumold',
			width : 150,
			editor : new Ext.form.ComboBox( {
				store: txnStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})
			,renderer:txnRen
		}, {
			header : '卡类型',
			dataIndex : 'cardTypeold',
			width : 150,
			editor : new Ext.form.ComboBox( {
				store: cardTypeStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})
			,renderer:cardRen
		}, {
			header : '权限',
			dataIndex : 'limit',
			hidden:true,
			width : 80,
			editor : new Ext.form.ComboBox( {
				store: limitStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})
		},{
			header : '状态',
			dataIndex : 'saState',
			width : 80,
			renderer: riskInfoState
		},
		{header: '修改人',dataIndex: 'creOprId'},
		{header: '修改时间',dataIndex: 'creTime',renderer: formatDt},
		{header: '审核人',dataIndex: 'upOprId'},
		{header: '审核时间',dataIndex: 'upTime',renderer: formatDt} ]);

		var menuArr = new Array();
		// 商户交易权限添加表单
		var ctlTxnInfForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 500,
			labelWidth : 90,
			waitMsgTarget : true,
			items : [ {
				xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				fieldLabel: '商户编号*',
				allowBlank: false,
				//store: mchntStore,
				hiddenName: 'mchtNo',
				id : 'idmchtNo',
				editable: true,
				blankText: '商户编号不能为空',
				emptyText: '请选择受控商户编号',
				width:300
			}, {
				fieldLabel : '交易渠道*',
				allowBlank : false,
				xtype: 'basecomboselect',
				store:channelStore,
				id : 'idchannel',
				hiddenName : 'channelOld',
				width : 300
			},{
				fieldLabel : '业务类型*',
				store:serverStore,
				xtype: 'basecomboselect',
				id : 'idbussType',
				hiddenName : 'bussTypeOld',
				width : 300
			}, {
				fieldLabel : '交易类型*',
				allowBlank : false,
				blankText : '交易类型不能为空',
				store:txnStore,
				xtype: 'basecomboselect',
				id : 'idtxnNum',
				hiddenName : 'txnNumOld',
				maskRe : /^[0-9]$/,
				width : 300
			}, {
				fieldLabel : '卡类型*',
				allowBlank : false,
				blankText : '卡类型不能为空',
				store:cardTypeStore,
				xtype: 'basecomboselect',
				id : 'idcardType',
				hiddenName : 'cardTypeOld',
				width : 300
			}/* , {
				fieldLabel : '权限*',
				allowBlank : false,
				value : "00",
				//blankText : '权限不能为空',
				store:limitStore,
				xtype: 'basecomboselect',
				id : 'idlimit',
				hiddenName : 'limit',
				width : 300
			}*/]
		});
		// 商户交易权限添加窗口
		var ctlTxnInfWin = new Ext.Window({
			title : '商户交易权限添加',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 500,
			autoHeight : true,
			layout : 'fit',
			items : [ ctlTxnInfForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [
					{
						text : '确定',
						handler : function() {
							if (ctlTxnInfForm.getForm().isValid()) {
								ctlTxnInfForm.getForm().submit(
								{
									url : 'T40214Action.asp?method=add',
									waitMsg : '正在提交，请稍后......',
									success : function(form,action) {
									showSuccessMsg(action.result.msg,ctlTxnInfWin);
									// 重置表单
									ctlTxnInfForm.getForm().reset();
									ctlTxnInfWin.hide();
									// 重新加载分公司列表
									grid.getStore().reload();
								},
								failure : function(form,action) {
									showErrorMsg(action.result.msg,ctlTxnInfWin);
								},
								params : {
									txnId : '40214',
									subTxnId : '01'
								}
								});
							}
						}
					}, {
						text : '重置',
						handler : function() {
							ctlTxnInfForm.getForm().reset();
						}
					}, {
						text : '关闭',
						handler : function() {
							ctlTxnInfWin.hide(grid);
						}
					} ]
				});
		// 添加
		var addMenu = {
			text : '添加',
			width : 85,
			iconCls : 'add',
			handler : function() {
				ctlTxnInfWin.show();
				ctlTxnInfWin.center();
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
					id:record.get('id'),
					channelOld : record.get('channelold'),
					bussTypeOld : record.get('bussTypeold'),
					txnNumOld : record.get('txnNumold'),
					cardTypeOld : record.get('cardTypeold')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T40214Action.asp?method=update',
				method : 'post',
				params : {
					mchtTranCtlList : Ext.encode(array),
					txnId : '40214',
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
									url : 'T40214Action.asp?method=delete',
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
										txnId : '40214',
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
		
		// 商户交易权限信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '商户交易权限信息',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : tblRiskMchtTranLimit,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : ctlTxnInfColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			tbar : menuArr,
			loadMask : {
				msg : '正在加载商户交易权限列表......'
			},	
			bbar : new Ext.PagingToolbar( {
				store : tblRiskMchtTranLimit,
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
				if(rec.get('saState')=='4'){//删除 待审核状态 禁修改删除
					grid.getTopToolbar().items.items[4].disable();
				}else if(rec.get('saState')=='3'){//修改待审核的禁止删除
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
			width : 200,
			autoHeight : true,
			items : [{
				name : 'mchtNo',
				fieldLabel : '商户编号',
				/*xtype : 'dynamicCombo',
				store:mchntStore,*/
				xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				id : 'idmchtNo2',
				hiddenName : 'mchtNo',
				width : 300
			}, {
				name : 'channel',
				fieldLabel : '交易渠道',
				allowBlank : false,
				xtype: 'basecomboselect',
				store:channelStore,
				id : 'idchannel2',
				hiddenName : 'channel',
				width : 300
			}, {
				name : 'bussType',
				fieldLabel : '业务类型',
				name : 'bussType',
				store:serverStore,
				xtype: 'basecomboselect',
				id : 'idbussType2',
				hiddenName : 'bussType',
				width : 300
			},{
				name : 'txnNum',
				fieldLabel : '交易类型',
				allowBlank : false,
				blankText : '交易类型不能为空',
				store:txnStore,
				xtype: 'basecomboselect',
				id : 'idtxnNum2',
				hiddenName : 'txnNum',
				maskRe : /^[0-9]$/,
				width : 300
			},{
				name : 'cardType',
				fieldLabel : '卡类型',
				allowBlank : false,
				blankText : '卡类型不能为空',
				store:cardTypeStore,
				xtype: 'basecomboselect',
				id : 'idcardType2',
				hiddenName : 'cardType',
				width : 300
			}/*,{
				name : 'limit',
				fieldLabel : '权限*',
				allowBlank : false,
				blankText : '权限不能为空',
				store:limitStore,
				xtype: 'basecomboselect',
				id : 'idlimit2',
				hiddenName : 'limit',
				width : 300
			}*/]
		});

		var queryWin = new Ext.Window( {
			title : '查询条件',
			layout : 'fit',
			width : 500,
			autoHeight : true,
			items : [ queryForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			resizable : false,
			closable : true,
			animateTarget : 'query',
			tools : [
					{
						id : 'minimize',
						handler : function(event, toolEl, panel, tc) {
							panel.tools.maximize.show();
							toolEl.hide();
							queryWin.collapse();
							queryWin.getEl().pause(1);
							queryWin.setPosition(10, Ext.getBody()
									.getViewSize().height - 30);
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
				tblRiskMchtTranLimit.load({
					params : {
						start : 0,
						mchtNo: queryForm.findById('idmchtNo2').getValue(),
						channel: queryForm.findById('idchannel2').getValue(),
						bussType: queryForm.findById('idbussType2').getValue(),
						txnNum: queryForm.findById('idtxnNum2').getValue(),
						cardType: queryForm.findById('idcardType2').getValue()/*,
						limit: queryForm.findById('idlimit2').getValue()*/
						}
					});
			}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
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
			renderTo : Ext.getBody(),
			items : [grid]
		});
	});
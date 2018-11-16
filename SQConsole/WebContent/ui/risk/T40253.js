Ext.onReady(function() {

	// 商户交易黑名单信息数据集
		var ctlTxnInfoStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=tblRiskMchtTranCtl'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {name: 'id',mapping: 'id'},{
				name : 'mchtNo',
				mapping : 'mchtNo'
			}, {
				name : 'channel',
				mapping : 'channel'
			}, {
				name : 'bussType',
				mapping : 'bussType'
			}, {
				name : 'txnNum',
				mapping : 'txnNum'
			}, {
				name : 'saState',
				mapping : 'saState'
			}, {
				name : 'oprID',
				mapping : 'oprID'
			}, {
				name : 'updateTime',
				mapping : 'updateTime'
			}, {
				name : 'mcc',
				mapping : 'mcc'
			}, {
				name : 'rislLvl',
				mapping : 'rislLvl'
			}, {
				name : 'licenceNo',
				mapping : 'licenceNo'
			}, {
				name : 'mchtNm',
				mapping : 'mchtNm'
			}, {
				name : 'applyDate',
				mapping : 'applyDate'
			}, {
				name : 'bankNo',
				mapping : 'bankNo'
			}, {
				name : 'reserved1',
				mapping : 'reserved1'
			}, {
				name : 'termCount',
				mapping : 'termCount'
			}]),
			autoLoad : true
		});
		ctlTxnInfoStore.load( {
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
	function txnRen(val){
		return txnStore.query("valueField",val).first().data.displayField;
		 
	}
	function txnChannel(val){
		return channelStore.query("valueField",val).first().data.displayField;
		 
	}
	function serverRen(val){
		return serverStore.query("valueField",val).first().data.displayField;
		 
	}
	var riskLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISKLVL',function(ret){
		riskLvlStore.loadData(Ext.decode(ret));
	});
	function riskLvl(val){
		return riskLvlStore.query("valueField",val).first().data.displayField;
	}
		var ctlTxnInfColModel = new Ext.grid.ColumnModel( [ {
			header : '商户编号',
			dataIndex : 'mchtNo',
			width : 100,
			sortable : true
		}, {
			header : '交易渠道',
			dataIndex : 'channel',
			width : 150,
			
			renderer:txnChannel
		}, {
			header : '业务类型',
			dataIndex : 'bussType',
			width : 150,
			renderer:serverRen
		}, {
			header : '交易类型',
			dataIndex : 'txnNum',
			width : 150,
			renderer:txnRen
		}, {
			header : '状态',	dataIndex : 'saState',width : 150,renderer: riskInfoState
		},{
			header: '商户中文名称',dataIndex: 'mchtNm',width: 200
		},{
			header: '商户MCC码',dataIndex: 'mcc',width: 80
		},{
			header: '商户风险等级',dataIndex: 'rislLvl',width: 80,renderer:riskLvl
		},{
			header: '营业执照编号',dataIndex: 'licenceNo',width: 100
		},{
			header: '终端数量',dataIndex: 'termCount',width: 80
		},{
			header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt
		}, {
			header: '所属分公司',dataIndex: 'bankNo',width: 80
		},{
			header : '操作人',dataIndex : 'oprID',width : 150
		}, {
			header : '审核人',dataIndex : 'reserved1',width : 150
		}, {
			header : '修改时间',dataIndex : 'updateTime',renderer: formatTs,width : 150
		} ]);

		var menuArr = new Array();
		// 商户交易黑名单添加表单
		var ctlTxnInfForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 500,
			labelWidth : 90,
			waitMsgTarget : true,
			items : [ {
				xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				//xtype: 'combo',
				fieldLabel: '商户编号*',
				allowBlank: false,
				//store: mchntStore,
				hiddenName: 'mchtNo',
				id : 'idmchtNo',
				editable: true,
				blankText: '商户编号不能为空',
				emptyText: '请选择受控商户编号',
				width:300,
				listeners: {
					'select': function() {
						T40216.checkHasMchnt(this.getValue(),function(ret){
							if(ret!=null){
						    var tblCtlMchtInf = Ext.decode(ret.substring(1,ret.length-1));
//						    showAlertMsg(tblCtlMchtInf.id,grid);
						    if(tblCtlMchtInf!=null&&tblCtlMchtInf.id != ''){
						    	showAlertMsg("该商户已在商户黑名单中，请重新选择。",grid);
						    	Ext.getCmp("idmchtNo").reset();
						    	return;
						    }
							}
						});
					}
				}
			}, {
				fieldLabel : '交易渠道*',
				allowBlank : false,
				xtype: 'basecomboselect',
				//baseParams : 'RISK_TRAN_CTL_CHANNEL',
				store:channelStore,
				id : 'idchannel',
				hiddenName : 'channel',
				width : 300
			},{
				fieldLabel : '业务类型*',
				id : 'bussType',
				name : 'bussType',
				//baseParams : 'RISK_TRAN_CTL_BUSSTYPE',
				store:serverStore,
				xtype: 'basecomboselect',
				id : 'idbussType',
				hiddenName : 'bussType',
				width : 300
			}, {
				fieldLabel : '交易类型*',
				allowBlank : false,
				blankText : '交易类型不能为空',
				//baseParams : 'RISK_TRAN_CTL_TXNTYPE',
				store:txnStore,
				xtype: 'basecomboselect',
				id : 'idtxnNum',
				hiddenName : 'txnNum',
				maskRe : /^[0-9]$/,
				width : 300
			} ]
		});
		// 商户交易黑名单添加窗口
		var ctlTxnInfWin = new Ext.Window(
				{
					title : '商户交易黑名单添加',
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
											url : 'T40208Action.asp?method=add',
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
											txnId : '40208',
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
					mchtNo : record.get('mchtNo'),
					channelOld : record.get('channel'),
					bussTypeOld : record.get('bussType'),
					txnNumOld : record.get('txnNum'),
					saState : record.get('saState')
					//oprID : record.get('oprID'),
					//channel : record.get('updateTime')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T40208Action.asp?method=update',
				method : 'post',
				params : {
					mchtTranCtlList : Ext.encode(array),
					txnId : '40208',
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
									url : 'T40208Action.asp?method=delete',
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
										txnId : '40208',
										subTxnId : '02'
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
		/*menuArr.push('-'); // [1]
		menuArr.push(addMenu); // [2]
		menuArr.push('-');
		menuArr.push(delMenu);
		menuArr.push('-');
		menuArr.push(upMenu);*/

		// 商户交易黑名单信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '商户交易黑名单信息',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			// autoExpandColumn: 'cardAccpName',
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : ctlTxnInfoStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : ctlTxnInfColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载商户黑名单交易列表......'
			},
			tbar : menuArr,
			bbar : new Ext.PagingToolbar( {
				store : ctlTxnInfoStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});

		grid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
						.frame();
				rec = grid.getSelectionModel().getSelected();
			}
		});

		/** *************************查询条件************************ */

		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 200,
			autoHeight : true,
			items : [{
				xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				//xtype : 'dynamicCombo',
				id : 'idmchtNo2',
				name :'mchtNo2',
				hiddenName: 'mchtNo2',
				//allowBlank : false,
				//store: mchntStore,
				width : 300,
				fieldLabel : '商户编号'
			}, {
				name : 'channel',
				fieldLabel : '交易渠道',
				//allowBlank : false,
				xtype: 'basecomboselect',
				store:channelStore,
				id : 'idchannel2',
				width : 300
			
			}, {
				name : 'bussType',
				fieldLabel : '业务类型',
				//name : 'bussType',
				store:serverStore,
				xtype: 'basecomboselect',
				id : 'idbussType2',
				//hiddenName : 'bussType',
				width : 300
			},{
				name : 'txnNum',
				fieldLabel : '交易类型',
				//allowBlank : false,
				//blankText : '交易类型不能为空',
				store:txnStore,
				xtype: 'basecomboselect',
				id : 'idtxnNum2',
				//hiddenName : 'txnNum',
				//maskRe : /^[0-9]$/,
				width : 300
			}]
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
				ctlTxnInfoStore.load({
					params : {
						start : 0,
						mchtNo: queryForm.findById('idmchtNo2').getValue(),
						channel: queryForm.findById('idchannel2').getValue(),
						bussType: queryForm.findById('idbussType2').getValue(),
						txnNum: queryForm.findById('idtxnNum2').getValue()
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
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
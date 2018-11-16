Ext.onReady(function() {

	// 商户数据集
//	var mchntStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	SelectOptionsDWR.getComboData('MCHNT_NO2',function(ret){
//		mchntStore.loadData(Ext.decode(ret));
//	});	
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC','*',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
//	//MCC数据集
//	var mchtMccStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	SelectOptionsDWR.getComboData('GrpMCC',function(ret){
//		mchtMccStore.loadData(Ext.decode(ret));
//	});	
	
	//商户MCC数据集
	var mchtMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCC2',function(ret){
		mchtMccStore.loadData(Ext.decode(ret));
	});
	//交易数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_CTL_TXNTYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
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
	//机构数据集-目的机构
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	//机构数据集-发卡机构
	var sendCardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('SEND_CARD',function(ret){
		sendCardStore.loadData(Ext.decode(ret));
	});
	// 地区代码数据集
	var cityCodeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
		cityCodeStore.loadData(Ext.decode(ret));
	});
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('RISK_TRAN_LIMIT_CARDTYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});	
	
	// 路由信息数据集
		var routStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=routInfo'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {
				name : 'cardBin',
				mapping : 'cardBin'
			}, {
				name : 'bussType',
				mapping : 'bussType'
			}, {
				name : 'txnNum',
				mapping : 'txnNum'
			}, {
				name : 'channel',
				mapping : 'channel'
			}, {
				name : 'areaNo',
				mapping : 'areaNo'
			}, {
				name : 'mchntId',
				mapping : 'merchId'
			}, {
				name : 'reserved',
				mapping : 'reserved'
			}, {
				name : 'destInstId',
				mapping : 'destInstId'
			}, {
				name : 'creTime',
				mapping : 'creTime'
			},{
				name : 'creOprId',
				mapping : 'creOprId'
			},{
				name : 'creatorId',
				mapping : 'creatorId'
			},{
				name : 'checkId',
				mapping : 'checkId'
			},{
				name : 'updateTime',
				mapping : 'updateTime'
			},{
				name : 'checkTime',
				mapping : 'checkTime'
			},{
				name : 'saState',
				mapping : 'saState'
			},{
				name : 'cardType',
				mapping : 'cardType'
			},{
				name : 'maxAmt',
				mapping : 'maxAmt'
			},{
				name : 'minAmt',
				mapping : 'minAmt'
			},{
				name:'mchtMcc',
				mappint:'mchtMcc'
			}]),
			autoLoad : true
		});
		
		function txnRen(val){
			return txnStore.query("valueField",val).first().data.displayField;
		}
		function channelRen(val){
			return channelStore.query("valueField",val).first().data.displayField;
		}
		function aerNo(val){
			return channelStore.query("valueField",val).first().data.displayField;
		}
		function serverRen(val){
			return serverStore.query("valueField",val).first().data.displayField;
		}
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
			  return agencyStore.query("valueField",val).first().data.displayField;
			}
		}
		function sendCardRen(val){
			if(sendCardStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return sendCardStore.query("valueField",val).first().data.displayField;
			}
		}
		function mchntRen(val){
			if(val.indexOf("*")!=-1){
				return '*-所有商户'; 
			}else if(mchntStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return mchntStore.query("valueField",val).first().data.displayField;
			}
		}
		function mchtMccRen(val){
			if(val.indexOf("*")!=-1){
				return '* - 所有MCC码'; 
			}else if(mchtMccStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return mchtMccStore.query("valueField",val).first().data.displayField;
			}
		}
		function cardRen(val){//卡类型
			return cardTypeStore.query("valueField",val).first().data.displayField;
		}
		function areaNo(val){
			if(val.indexOf("*")!=-1){
				return '*-所有地区'; 
			} else if(cityCodeStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return cityCodeStore.query("valueField",val).first().data.displayField;
			}
		
		}
		
		var routColModel = new Ext.grid.ColumnModel( [/*{
			header : '卡BIN编号',
			dataIndex : 'cardBin',
			width : 80,
			editor : new Ext.form.TextField( {
				maxLength : 30,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		},*/{
			header : '发卡机构',
			dataIndex : 'cardBin',
			width : 120
			,editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getSendCardNo',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		,renderer:sendCardRen
		},{
			header : '业务类型',
			dataIndex : 'bussType',
			width : 140
			,editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getBusinType_as',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		,renderer:serverRen
		}, {
			header : '交易类型',
			dataIndex : 'txnNum',
			width : 130
			,editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getTxnClt',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		,renderer:txnRen
		}, {
			header : '渠道',
			dataIndex : 'channel',
			width : 110
			,editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getChannel',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		,renderer:channelRen
		}, {
			header : '地区',
			dataIndex : 'areaNo',
			width : 90,
			renderer: areaNo,
			editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getAreaCodecode2',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		}, {
			header : '卡类型',
			dataIndex : 'cardType',
			width : 100,
			editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getRiskCardType',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
			,renderer:cardRen
		}, {//FIXME
			header : 'MCC码',
			dataIndex : 'mchtMcc',
			width : 110,
//			editor : new Ext.ux2.dynamicCombo( {
//				methodName: 'getMchntMcc',
//				selectOnFocus: true,
//				editable: true
//			}),
			editor : new Ext.form.ComboBox( {
				store: mchtMccStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				hiddenName: 'mchtmccc',
				allowBlank : false,
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				listeners:{
					'change':function(){
						rec.set('mchntId',"请选择商户号");
					}
				}
			}),
		     renderer:mchtMccRen
		}, {
			header : '受理商户编号',
			dataIndex : 'mchntId',
			width : 220,
//			editor : new Ext.ux2.dynamicCombo( {
//				methodName: 'getMchntNo2',
//				/*displayField: 'displayField',
//				valueField: 'valueField',
//				mode: 'local',
//				triggerAction: 'all',
//				forceSelection: true,
//				typeAhead: true,*/
//				selectOnFocus: true,
//				editable: true
////				allowBlank : false
//			})
			editor : new Ext.form.ComboBox( {
				store: mchntStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				hiddenName: 'mchtidd',
				allowBlank : false,
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				listeners : {
					'focus': function() {
						mchntStore.removeAll();
						SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC',rec.get("mchtMcc"),function(ret){
							mchntStore.loadData(Ext.decode(ret));
						});
					}
				}
			})
		,renderer:mchntRen
		}, {
			header : '目的机构',
			dataIndex : 'destInstId',
			width : 150
			,editor : new Ext.ux2.dynamicCombo( {
				methodName: 'getAgencyInf',
				/*displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,*/
				selectOnFocus: true,
				editable: true
			})
		,renderer:agencyRen
		},{	
			header : '最高金额',
			dataIndex : 'maxAmt',
			width : 85,
			editor: new Ext.form.TextField({
     		 	maxLength: 13,
     			allowBlank: false,
     			regex:/^[0-9]+\.{1}[0-9]{2}$/,
				regexText:'请输入两位小数'
	     })
		},{	
			header : '最低金额',
			dataIndex : 'minAmt',
			width : 85,
			editor: new Ext.form.TextField({
     		 	maxLength: 13,
     			allowBlank: false,
     			regex:/^[0-9]+\.{1}[0-9]{2}$/,
				regexText:'请输入两位小数'
	     })
		}, {
			header : '状态',	
			dataIndex : 'saState',
			width : 80,
			renderer: riskInfoState
		},{	
			header : '创建时间',
			dataIndex : 'creTime',
			width : 120,
			renderer: formatTs
		},{
			header : '创建人',
			dataIndex : 'creOprId',
			width : 80
		},{	
			header : '修改时间',
			dataIndex : 'updateTime',
			width : 120,
			renderer: formatTs
		},{
			header : '修改人',
			dataIndex : 'creatorId',
			width : 80
		},{
			header : '审核时间',
			dataIndex : 'checkTime',
			width : 120,
			renderer: formatTs
		},{
			header : '审核人',
			dataIndex : 'checkId',
			width : 80
		},{
			header : '主键',	
			dataIndex : 'reserved',
			width : 80,
			hidden: true
		} ]);

		var menuArr = new Array();
		
		var routInfoForm = new Ext.form.FormPanel( {// 路由添加表单
			frame : true,
			autoHeight : true,
			width : 600,
			labelWidth : 90,
			defaultType : 'textfield',
			waitMsgTarget : true,
			items : [ /*{
				fieldLabel : '卡BIN编号*',
				allowBlank : false,
				blankText : '卡BIN编号不能为空',
				emptyText : '请输入卡BIN编号',
				id : 'cardBin',
				name : 'cardBin',
//				maskRe : /^[0-9]$/,
				width : 200
//				blankText : '该输入项只能包含数字'
			},*/{
				fieldLabel : '发卡机构*',
				emptyText : '请选择发卡机构',
				allowBlank : false,
				xtype: 'dynamicCombo',
				methodName: 'getSendCardNo',
				id : 'idCardBin',
				name : 'cardBin',
				hiddenName : 'cardBin',
				width : 200
			},{
				fieldLabel : '业务类型*',
				allowBlank : false,
				emptyText : '请输入业务类型',
//				store: serverStore,
				blankText : '业务类型不能为空',
				xtype: 'dynamicCombo',
				methodName: 'getBusinType_as',
				id : 'idbussType',
				name: 'bussType',
				hiddenName : 'bussType',
				width : 450
			}, {
				fieldLabel : '交易类型*',
				allowBlank : false,
				blankText : '交易类型不能为空',
				emptyText : '请输入交易类型',
				methodName: 'getTxnClt',
				xtype: 'dynamicCombo',
				id : 'idtxnNum',
				name : 'txnNum',
				hiddenName : 'txnNum',
				width : 200
			}, {
				fieldLabel : '渠道*',
				allowBlank : false,
				emptyText : '请输入渠道',
				methodName: 'getChannel',
				xtype: 'dynamicCombo',
				id : 'idchannel',
				name : 'channel',
				hiddenName : 'channel',
				width : 200
			}, {
				fieldLabel : '地区*',
				allowBlank : false,
				emptyText : '请输入地区',
				xtype: 'dynamicCombo',
   			    methodName: 'getAreaCodecode2',
				id : 'idareaNo',
				name : 'areaNo',
				hiddenName : 'areaNo',
				width : 200
			}, {
				fieldLabel : '卡类型*',
				allowBlank : false,
				blankText : '卡类型不能为空',
				xtype: 'dynamicCombo',
   			    methodName: 'getRiskCardType',
				id : 'idcardType',
				hiddenName : 'cardType',
				width : 200
			},{//FIXME
       			xtype: 'dynamicCombo',
       			methodName: 'getMchntMcc',
				fieldLabel: 'MCC码*',
				id: 'idmchtMcc',
				hiddenName: 'mchtMcc',
				allowBlank: false,
				width:250,
				callFunction : function() {
					Ext.getCmp('idmchntId').reset();
					Ext.getCmp('idmchntId').parentP=this.value;
					Ext.getCmp('idmchntId').getStore().reload();
				}
	        }, {
				fieldLabel : '受理商户编号*',
				allowBlank : false,
				xtype: 'dynamicCombo',
			//	methodName: 'getMchntNo2',
				methodName: 'getMchntNoMcc2',
				id : 'idmchntId',
				name : 'mchntId',
				hiddenName : 'mchntId',
				width : 400
			}, {
				fieldLabel : '目的机构*',
				emptyText : '请选择目的机构',
				allowBlank : false,
				methodName: 'getAgencyInf',
				xtype: 'dynamicCombo',
				id : 'iddestInstId',
				name : 'destInstId',
				hiddenName : 'destInstId',
				width : 200
			}, {
				fieldLabel : '最低金额(单位：元)*',
				emptyText : '请输入最低金额',
				allowBlank : false,
				xtype: 'textfield',
				value:'0.00',
				decimalPrecision: 2,
				id : 'minAmt',
				name : 'minAmt',
				regex:/^[0-9]+\.{1}[0-9]{2}$/,
				regexText:'请输入两位小数',
				maxLength:13,
				width : 200
				/*listeners:{
					'select': function() {
						if(Ext.getCmp('maxAmt').getValue()!='' && (Ext.getCmp('minAmt').getValue() > Ext.getCmp('maxAmt').getValue())){
							showAlertMsg("最小金额不能低于最大金额！",routInfoForm);
							Ext.getCmp('minAmt').setValue('');
						}
					}
				}*/
				
			} , {
				fieldLabel : '最高金额(单位：元)*',
				emptyText : '请输入最高金额',
				allowBlank : false,
				xtype: 'textfield',
				value:'9999999999.99',
				id : 'maxAmt',
				name : 'maxAmt',
				regex:/^[0-9]+\.{1}[0-9]{2}$/,
				regexText:'请输入两位小数',
				maxLength:13,
				width : 200
				/*listeners:{
					'select': function() {
						if(Ext.getCmp('minAmt').getValue()!='' && (Ext.getCmp('minAmt').getValue() > Ext.getCmp('maxAmt').getValue())){
							showAlertMsg("最小金额不能低于最大金额！",routInfoForm);
							Ext.getCmp('maxAmt').setValue('');
						}
					}
				}*/
			}  ]
		});
		
		// 路由器添加窗口
		var routWin = new Ext.Window( {
				title : '路由信息添加',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 600,
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
								routInfoForm.getForm().submit({
									url : 'T11011Action.asp?method=add',
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
										txnId : '11011',
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
					reserved : record.get('reserved'),
					cardBin : record.get('cardBin'),
					bussType : record.get('bussType'),
					txnNum : record.get('txnNum'),
					channel : record.get('channel'),
					areaNo : record.get('areaNo').split('-')[0],
					cardType : record.get('cardType'),
					mchntId : record.get('mchntId'),
					destInstId : record.get('destInstId').split('-')[0],
					minAmt: record.get('minAmt'),
					maxAmt: record.get('maxAmt'),
					mchtMcc:record.get('mchtMcc')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T11011Action.asp?method=update',
				method : 'post',
				params : {
					parameterList : Ext.encode(array),
					txnId : '11011',
					subTxnId : '02'
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if (rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg, routInfoForm);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg, routInfoForm);
					}
					grid.getTopToolbar().items.items[6].disable();
					grid.getStore().reload();
					hideProcessMsg();
				//	routStore.load();
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
									url : 'T11011Action.asp?method=delete',
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
										reserved : rec.get('reserved'),
										txnId : '11011',
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

		menuArr.push(queryConditionMebu); 	// [0]
		menuArr.push('-');                	// [1]
		menuArr.push(addMenu);            	// [2]
		menuArr.push('-');					// [3]
		menuArr.push(delMenu);				// [4]
		menuArr.push('-');					// [5]
		menuArr.push(upMenu);				// [6]

		// 路由信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '路由信息维护',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : routStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : routColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载路由器信息列表......'
			},
			tbar : menuArr,
			bbar : new Ext.PagingToolbar( {
				store : routStore,
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
				}else if(rec.get('saState')=='3'){//修改待审核的状态禁止删除
					grid.getTopToolbar().items.items[4]. disable();
				}else if(rec.get('saState')=='1'){//已删除状态 禁止删除
					grid.getTopToolbar().items.items[4]. disable();
				}else if(rec.get('saState')=='0'){//新增待审核状态也禁止删除
					grid.getTopToolbar().items.items[4]. enable();
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
			width : 400,
			autoHeight : true,
			items : [ 
				/*{xtype : 'textfield',
				id : 'searchCardBin',
				width : 200,
				maxLength: 30,
				name : 'searchCardBin',
				fieldLabel : '卡Bin编号'
			}, */{
				xtype: 'dynamicCombo',
				fieldLabel : '发卡机构',
				methodName: 'getSendCardNo',
				id : 'searchCardBin',
				name : 'searchCardBin',
				width : 200
			},{xtype: 'dynamicCombo',
				methodName: 'getBusinType_as',
				id : 'searchBussType',
				width : 200,
				name : 'searchBussType',
				fieldLabel : '业务类型'
			}, {xtype: 'dynamicCombo',
				methodName:'getTxnClt',
				id : 'searchTxnNum',
				width : 200,
				name : 'searchTxnNum',
				fieldLabel : '交易类型'
			}, {xtype: 'dynamicCombo',
				methodName: 'getChannel',
				id : 'searchChannel',
				width : 200,
				name : 'searchChannel',
				fieldLabel : '渠道'
			}, {xtype: 'dynamicCombo',
//				store:cityCodeStore,
				methodName: 'getAreaCodecode2',
				id : 'searchAreaNo',
				width : 200,
				name : 'searchAreaNo',
				fieldLabel : '地区'
			}, {
		       	xtype: 'dynamicCombo',
				fieldLabel: '卡类型',
				methodName: 'getRiskCardType',
				width : 200,
				id: 'cardType',
				hiddenName: 'CARD_STYLE',
				editable: true
		   	}, {
				xtype: 'dynamicCombo',
				methodName: 'getMchntMcc',
				id : 'searchidmchtMcc',
				width : 250,
				hiddenName : 'searchmchtMcc',
				fieldLabel : 'MCC码',
				callFunction : function() {
					Ext.getCmp('searchMchntId').reset();
					Ext.getCmp('searchMchntId').parentP=this.value;
					Ext.getCmp('searchMchntId').getStore().reload();
				}
			}, {
				xtype: 'dynamicCombo',
				//methodName: 'getMchntNo2',
				methodName: 'getMchntNoMcc2',
				id : 'searchMchntId',
				width : 250,
				name : 'searchMchntId',
				fieldLabel : '受理商户编号'
			}, {xtype: 'dynamicCombo',
				methodName: 'getAgencyInf',
				id : 'searchDestInstId',
				width : 200,
				name : 'searchDestInstId',
				fieldLabel : '目的机构'
			},{
				xtype: 'dynamicCombo',
				id: 'orderOption',
				fieldLabel: '排序',
				methodName: 'getOrderOp',
				/*store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['binasc','按发卡行编号升序排列'],['bindesc','按发卡行编号降序排列'],
					       ['mchtasc','按受理商户编号升序排列'],['mchtdesc','按受理商户编号降序排列'],
					       ['timeasc','按修改时间升序排列'],['timedesc','按修改时间降序排列']],
					reader: new Ext.data.ArrayReader()
				}),*/
				anchor: '70%'
			},{
				xtype: 'datefield',
				id: 'startDate',
				name: 'startDate',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				fieldLabel: '开始日期'
			},{
				xtype: 'datefield',
				id: 'endDate',
				name: 'endDate',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				fieldLabel: '结束日期'
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
			buttons : [ {
				text : '查询',
				handler : function() {
					var startDateTemp = queryForm.findById('startDate').getValue();
		    		var endDateTemp = queryForm.findById('endDate').getValue();
		    		
		        	if(queryForm.findById('startDate').getValue()!=null && queryForm.findById('startDate').getValue()!=''){
		        		startDateTemp = queryForm.findById('startDate').getValue().format('Ymd');
					}
		        	if(queryForm.findById('endDate').getValue()!=null && queryForm.findById('endDate').getValue()!=''){
		        		endDateTemp = queryForm.findById('endDate').getValue().format('Ymd');
					}
		        	if(startDateTemp!=null && startDateTemp!='' && endDateTemp!=null && endDateTemp!=''){
		        		if(startDateTemp > endDateTemp){
			        		showErrorMsg('交易开始日期不能大于交易结束日期，请重新选择',queryForm);
			        		queryForm.findById('startDate').setValue('');
			        		queryForm.findById('endDate').setValue('');
			        		return;
			        	}
		        	}
		        	if(queryForm.getForm().isValid()){
		        		routStore.load();
		        	}
				}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
				}
			} ]
		});

		routStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				cardBin : queryForm.findById('searchCardBin').getValue(),
				bussType : queryForm.findById('searchBussType').getValue(),
				txnNum : queryForm.findById('searchTxnNum').getValue(),
				channel : queryForm.findById('searchChannel').getValue(),
				areaNo : queryForm.findById('searchAreaNo').getValue(),
				cardType: queryForm.findById('cardType').getValue(),
				mchntId : queryForm.findById('searchMchntId').getValue(),
				destInstId : queryForm.findById('searchDestInstId').getValue(),
				orderOption : queryForm.findById('orderOption').getValue(),
				mchtMcc : queryForm.findById('searchidmchtMcc').getValue(),
				startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
				endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd')
			});
		});

		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
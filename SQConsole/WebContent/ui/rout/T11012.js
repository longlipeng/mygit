Ext.onReady(function() {

	//机构数据集
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
	
	//MCC数据集
	var mchtMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('GrpMCC',function(ret){
		mchtMccStore.loadData(Ext.decode(ret));
	});	
	
	// 地区代码数据集
	var cityCodeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
		cityCodeStore.loadData(Ext.decode(ret));
	});	
	// 商户数据集
//	var mchntStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
//		mchntStore.loadData(Ext.decode(ret));
//	});		
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
	//机构数据集-发卡机构
	var sendCardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('SEND_CARD',function(ret){
		sendCardStore.loadData(Ext.decode(ret));
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
	
	// 路由信息数据集
		var routStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=routInfoToCheck'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {name : 'cardBin',mapping : 'cardBin'
			}, {name : 'bussType',mapping : 'bussType'
			}, {name : 'txnNum',mapping : 'txnNum'
			}, {name : 'channel',mapping : 'channel'
			}, {name : 'areaNo',mapping : 'areaNo'
			}, {name : 'mchntId',mapping : 'merchId'
			}, {name : 'reserved',mapping : 'reserved'
			}, {name : 'destInstId',mapping : 'destInstId'
			}, {name : 'creTime',mapping : 'creTime'
			},{name : 'creOprId',mapping : 'creOprId'
			},{name : 'creatorId',	mapping : 'creatorId'
			},{name : 'checkId',mapping : 'checkId'
			},{name : 'updateTime',mapping : 'updateTime'
			},{name : 'checkTime',mapping : 'checkTime'
			},{name : 'saState',mapping : 'saState'
			},{name : 'cardType',mapping : 'cardType'
			},{name : 'maxAmt',mapping : 'maxAmt'
			},{name : 'minAmt',mapping : 'minAmt'
			},{name : 'mchtMcc',mapping : 'mchtMcc'
			}]),
			autoLoad : true
		});
		
		function txnRen(val){
			return txnStore.query("valueField",val).first().data.displayField;
		}
		function channelRen(val){
			return channelStore.query("valueField",val).first().data.displayField;
		}
		function serverRen(val){
			return serverStore.query("valueField",val).first().data.displayField;
		}
		/*function agencyRen(val){
			return agencyStore.query("valueField",val).first().data.displayField;
		}*/
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
			  return agencyStore.query("valueField",val).first().data.displayField;
			}
		}
		function cardRen(val){//卡类型
			return cardTypeStore.query("valueField",val).first().data.displayField;
		}
		function areaNo(val){

			if(val.trim()=='*   -'){
				return '*-所有地区'; 
			} 
			else {
				return val;
			}
		
		}
		function mchntId(val){
			if(val.trim()=='*              -'){
				return '*-所有商户'; 
			} 
			else {
				return val;
			}
			
		}
		function sendCardRen(val){
			if(sendCardStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return sendCardStore.query("valueField",val).first().data.displayField;
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
		
//		function mchntRen(val){
//			return mchntStore.query("valueField",val).first().data.displayField;
//		}
		var sm = new Ext.grid.CheckboxSelectionModel(); 

		var routColModel = new Ext.grid.ColumnModel( [
		  sm,/*{
			header : '卡BIN编号',
			dataIndex : 'cardBin',
			width : 80
		},*/{
			header : '发卡机构',
			dataIndex : 'cardBin',
			width : 120,
			renderer:sendCardRen
		},{
			header : '业务类型',
			dataIndex : 'bussType',
			width : 140,
			renderer:serverRen
		}, {
			header : '交易类型',
			dataIndex : 'txnNum',
			width : 130,
			renderer:txnRen
		}, {
			header : '渠道',
			dataIndex : 'channel',
			width : 110,
			renderer:channelRen
		}, {
			header : '地区',
			dataIndex : 'areaNo',
			width : 90,
			renderer:areaNo
		}, { 
			header : '卡类型',
			dataIndex : 'cardType',
			width : 100,
			renderer:cardRen
		}, {
			header : 'MCC码',
			dataIndex : 'mchtMcc',
			width : 110,
			renderer: mchtMccRen
		}, {
			header : '受理商户编号',
			dataIndex : 'mchntId',
			width : 220,
			renderer: mchntId
		}, {
			header : '目的机构',
			dataIndex : 'destInstId',
			width : 150,
			renderer:agencyRen
		},{	
			header : '最高金额',
			dataIndex : 'maxAmt',
			width : 85
		},{	
			header : '最低金额',
			dataIndex : 'minAmt',
			width : 85
		}, {
			header : '状态',	
			dataIndex : 'saState',
			width : 80,
			renderer: riskInfoState
		} ,{	
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
			header : '主键',	
			dataIndex : 'reserved',
			width : 80,
			hidden: true
		} ]);
		

		var menuArray = new Array();
		var acceptMenu = {
				text: '通过',
				width: 85,
				iconCls: 'accept',
				disabled: true,
				handler:function() {
				 	if (grid.getSelectionModel().hasSelection()){
					   var records = grid.getSelectionModel().getSelections();
					   var selections = "";
					   for(var i=0 ; i<records.length ; i++){
						   selections += records[i].data.reserved;
						   selections += ";";
					   }
					   showConfirm('确认审核通过吗？',grid,function(bt) {
							if(bt == 'yes') {
//								showProcessMsg('正在提交审核信息，请稍后......');
								Ext.Ajax.request({
									url: 'T11012Action.asp?method=accept',
									params: {
									    reserved: selections,
										txnId: '11012',
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
					 }else{
						 alert('请选中要操作的记录!');
					 }
				}
			};
		
		var refuseMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: true,
			handler:function() {
				if (!grid.getSelectionModel().hasSelection()){
					alert('请选中要操作的记录!');
				}else{
				   showConfirm('确认拒绝吗？',grid,function(bt) {
						if(bt == 'yes') {
							showInputMsg('提示','请输入拒绝原因',true,refuse);
						}
				   });
				}
			}
		};
			
			// 拒绝按钮调用方法
			function refuse(bt,text) {
			   var records = grid.getSelectionModel().getSelections();
			   var selections2 = "";
			   for(var i=0 ; i<records.length ; i++){
				   selections2 += records[i].data.reserved;
				   selections2 += ";";
			   }
				if(bt == 'ok') {
					if(getLength(text.replace(/[ ]/g,"")) == 0) {
						alert('操作原因不能为空');
						showInputMsg('提示','请输入进行该操作的原因',true,refuse);
						return;
					}
					if(getLength(text) > 60) {
						alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
						showInputMsg('提示','请输入拒绝原因',true,refuse);
						return;
					}
//					showProcessMsg('正在提交审核信息，请稍后......');
					rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T11012Action.asp?method=refuse',
						params: {
							reserved: selections2,
							txnId: '11012',
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
					//hideProcessMsg();
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
		
		// 路由信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '路由信息审核',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : routStore,
			selModel:sm,
//			sm : new Ext.grid.RowSelectionModel( {
//				singleSelect : true
//			}),
			cm : routColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载路由器信息列表......'
			},
			tbar : menuArray,
			bbar : new Ext.PagingToolbar( {
				store : routStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});
		grid.getTopToolbar().items.items[2].enable();
		grid.getTopToolbar().items.items[4].enable();
		/** *************************查询条件************************ */

		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 400,
			autoHeight : true,
			items : [ /*{
				xtype : 'textfield',
				id : 'searchCardBin',
				width : 200,
				name : 'searchCardBin',
				fieldLabel : '卡Bin编号'
			},	*/{
				fieldLabel : '发卡机构',
				xtype: 'dynamicCombo',
				methodName: 'getSendCardNo',
				id : 'searchCardBin',
				name : 'searchCardBin',
				width : 200
			}, {xtype: 'dynamicCombo',
				methodName: 'getBusinType_as',
				id : 'searchBussType',
				width : 200,
				name : 'searchBussType',
				fieldLabel : '业务类型'
			}, {
				xtype: 'dynamicCombo',
				methodName:'getTxnClt',
				id : 'searchTxnNum',
				width : 200,
				name : 'searchTxnNum',
				fieldLabel : '交易类型'
			}, {
				xtype: 'dynamicCombo',
				methodName: 'getChannel',
				id : 'searchChannel',
				width : 200,
				name : 'searchChannel',
				fieldLabel : '渠道'
			}, {
				xtype: 'dynamicCombo',
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
			//	methodName: 'getMchntNo2',
				methodName: 'getMchntNoMcc2',
				id : 'searchMchntId',
				width : 250,
				name : 'searchMchntId',
				fieldLabel : '受理商户编号'
			}, {
				xtype: 'dynamicCombo',
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
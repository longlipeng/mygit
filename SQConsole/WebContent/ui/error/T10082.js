Ext.onReady(function() {
// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_CD',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	// 终端数据集
	var termStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('TERM_ID_MCHT','*',function(ret){
		termStore.loadData(Ext.decode(ret));
	});
	//机构数据集-发卡机构
	var sendCardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('SEND_CARD_INS',function(ret){
		sendCardStore.loadData(Ext.decode(ret));
	});
	//交易类型数据集
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TXN_TYPE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	// 补电子现金信息数据集
	var elecCashStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=elecCashInfoToCheck'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [{name : 'id',mapping : 'id'},
			{name : 'mchtCdUpd',mapping : 'mchtCd'},
			{name : 'termIdUpd',mapping : 'termId'},
			{name : 'acqInsIdCdUpd',mapping : 'acqInsIdCd'},
			{name : 'brhInsIdCdUpd',mapping : 'brhInsIdCd'},
			{name : 'panUpd',mapping : 'pan'},
			{name : 'txnNumUpd',mapping : 'txnNum'},
			{name : 'txnBatchNoUpd',mapping : 'txnBatchNo'},
			{name : 'txnCerNoUpd',mapping : 'txnCerNo'},
			{name : 'transAmtUpd',mapping : 'transAmt'},
			{name : 'transDateUpd',mapping : 'transDate'},
			{name : 'icCertUpd',mapping : 'icCert'},
			{name : 'tvrUpd',mapping : 'tvr'},
			{name : 'tsiUpd',mapping : 'tsi'},
			{name : 'aidUpd',mapping : 'aid'},
			{name : 'atcUpd',mapping : 'atc'},
			{name : 'appTagUpd',mapping : 'appTag'},
			{name : 'appPreNameUpd',mapping : 'appPreName'},
			{name : 'saStateUpd',mapping : 'saState'},
			{name : 'recCrtUsrUpd',mapping : 'recCrtUsr'},
			{name : 'recCrtTsUpd',mapping : 'recCrtTs'},
			{name : 'recUpdUsrUpd',mapping : 'recUpdUsr'},
			{name : 'recUpdTsUpd',mapping : 'recUpdTs'},
			{name : 'recCheUsrUpd',mapping : 'recCheUsr'},
			{name : 'recCheTsUpd',mapping : 'recCheTs'}
		]),
		autoLoad : true
	});
		//交易类型
		function txnRen(val){
			return txnStore.query("valueField",val).first().data.displayField;
		}
		//发卡机构
		function sendCardRen(val){
			if(sendCardStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return sendCardStore.query("valueField",val).first().data.displayField;
			}
		}
		//商户号
		function mchntRen(val){
			if(mchntStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return mchntStore.query("valueField",val).first().data.displayField;
			}
		}
		var elecCashModel = new Ext.grid.ColumnModel([
			{header : '商户编号',dataIndex : 'mchtCdUpd',width : 180,renderer:mchntRen},
			{header : '终端号',dataIndex : 'termIdUpd',	width : 60},
			{header : '收单行标识',dataIndex : 'acqInsIdCdUpd',width : 70},
			{header : '发卡行标识',dataIndex : 'brhInsIdCdUpd',width : 180,renderer:sendCardRen},
			{header : '卡号',dataIndex : 'panUpd',width : 80},
			{header : '交易类型',dataIndex : 'txnNumUpd',width : 90,renderer:txnRen},
			{header : '交易批次号',dataIndex : 'txnBatchNoUpd',width : 70},
			{header : '交易凭证号',dataIndex : 'txnCerNoUpd',width : 70},
			{header : '交易金额',dataIndex : 'transAmtUpd',	width : 80,renderer:formatAmtTrans},
			{header : '交易日期',dataIndex: 'transDateUpd',width: 60},
			{header : '状态',dataIndex : 'saStateUpd',width : 70,renderer: riskInfoState},
			{header : 'IC卡交易证书',dataIndex : 'icCertUpd',width : 130},
			{header : 'TVR',dataIndex: 'tvrUpd',width: 90},
			{header : 'TSI',dataIndex: 'tsiUpd',width: 50},
			{header : 'AID',dataIndex: 'aidUpd',width: 90},
			{header : 'ATC',dataIndex: 'atcUpd',width: 50},
			{header : '应用标签',dataIndex: 'appTagUpd',width: 90},
			{header : '应用首选名称',dataIndex: 'appPreNameUpd',width: 90},
			{header : '修改时间',dataIndex : 'recUpdTsUpd',width : 120,renderer: formatTs},
			{header : '修改人',dataIndex : 'recUpdUsrUpd',width : 80},
			{header : '审核时间',dataIndex : 'recCheTsUpd',width : 120,renderer: formatTs},
			{header : '审核人',dataIndex : 'recCheUsrUpd',width : 80},
			{header : '主键',	dataIndex : 'id',width : 80,hidden: true}
		]);

		var menuArray = new Array();
		var acceptMenu = {
				text: '通过',
				width: 85,
				iconCls: 'accept',
				disabled: true,
				handler:function() {
				 	if (grid.getSelectionModel().hasSelection()){
					   var rec = grid.getSelectionModel().getSelected();
					   showConfirm('确认审核通过吗？',grid,function(bt) {
							if(bt == 'yes') {
								showProcessMsg('正在提交审核信息，请稍后......');
								Ext.Ajax.request({
									url: 'T10082Action.asp?method=accept',
									params: {
									    id: rec.get('id'),
										txnId: '10082',
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
			    var rec = grid.getSelectionModel().getSelected();
				if(bt == 'ok') {
					if(getLength(text) > 60) {
						alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
						showInputMsg('提示','请输入拒绝原因',true,refuse);
						return;
					}
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T10082Action.asp?method=refuse',
						params: {
							id: rec.get('id'),
							txnId: '10082',
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
		
		// 路由信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '补电子现金消费信息审核',
			iconCls : 'T501',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			store : elecCashStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : elecCashModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载路由器信息列表......'
			},
			tbar : menuArray,
			bbar : new Ext.PagingToolbar( {
				store : elecCashStore,
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
			}
		});
		grid.getTopToolbar().items.items[2].enable();
		grid.getTopToolbar().items.items[4].enable();
		/** *************************查询条件************************ */
		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 400,
			autoHeight : true,
			items : [{
				xtype: 'datefield',
				id: 'startDate',
				name: 'startDate',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				fieldLabel: '交易开始日期'
			},{
				xtype: 'datefield',
				id: 'endDate',
				name: 'endDate',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				fieldLabel: '交易结束日期'
			},{
				xtype: 'dynamicCombo',
				methodName: 'getMchntIdAll',
				fieldLabel: '商户编号',
				hiddenName: 'HmchntNo',
				id: 'mchntNo',
				editable: true,
				width: 260,
				callFunction : function() {
					Ext.getCmp('idtermId').reset();
					Ext.getCmp('idtermId').parentP=this.value;
					Ext.getCmp('idtermId').getStore().reload();
				}
			},{
				xtype: 'dynamicCombo',
				methodName: 'getTermId',
				parentP:'',
				fieldLabel: '终端编号',
				hiddenName: 'termIdname',
				id: 'idtermId',
				editable: true,
				width: 200
			},{
				xtype: 'textfield',
				fieldLabel: '交易批次号',
				id: 'batchNo',
				regex: /^[0-9]+$/,
                maxLength : 6,
				width: 200
			},{
				xtype: 'textfield',
				fieldLabel: '交易凭证号',
				id: 'cerNo',
				regex: /^[0-9]+$/,
                maxLength : 6,
				width: 200
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
		        		elecCashStore.load();
		        	}
				}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
				}
			} ]
		});

		elecCashStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
				endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
				mchntNo : queryForm.findById('mchntNo').getValue(),
				termId : queryForm.findById('idtermId').getValue(),
				batchNo : queryForm.findById('batchNo').getValue(),
				cerNo : queryForm.findById('cerNo').getValue()
			});
		});

		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
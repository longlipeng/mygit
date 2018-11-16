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
			url : 'gridPanelStoreAction.asp?storeId=elecCashInfo'
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

		var menuArr = new Array();
		
		var queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
		// 添加
		var addMenu = {
			text : '添加',
			width : 85,
			iconCls : 'add',
			handler : function() {
				addForm.getForm().reset();
				Ext.getCmp('acqInsIdCd').setValue('30000000');
				addWin.show();
			}
		};
		var rec;
		var modifyMenu = {
			text: '修改',
			width: 85,
			iconCls: 'edit',
			disabled: true,
			handler:function(bt) {
				if (grid.getSelectionModel().hasSelection()) {
					rec = grid.getSelectionModel().getSelected();
					updateForm.getForm().reset();
					updateForm.getForm().loadRecord(rec);
					updateForm.getForm().clearInvalid();
					Ext.getCmp('transAmtUpd').setValue(formatTransAmt(rec.get('transAmtUpd')));
					Ext.getCmp('termIdUpdd').parentP=rec.get('mchtCdUpd');
					Ext.getCmp('termIdUpdd').inputValue=rec.get('termIdUpd');
					Ext.getCmp('termIdUpdd').getStore().reload();
					updateWin.show();
				}
			}
		}
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
								url : 'T10081Action.asp?method=delete',
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
									txnId : '10081',
									subTxnId : '03'
								}
							});
						}
					});
				}
			}
		};

		menuArr.push(queryConditionMebu); // [0]
		menuArr.push('-');                // [1]
		menuArr.push(addMenu);            // [2]
		menuArr.push('-');				//[3]
		menuArr.push(modifyMenu);		//[4]
		//menuArr.push('-');				//[5]
		//menuArr.push(delMenu);			//[6]	

		// 路由信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '补电子现金消费维护',
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
				msg : '正在加载补电子现金消费信息列表......'
			},
			tbar : menuArr,
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
				rec = grid.getSelectionModel().getSelected();
				if(rec.get('saStateUpd')=='0'){//新增待审核状态可修改
					grid.getTopToolbar().items.items[4].enable();
				}else{
					grid.getTopToolbar().items.items[4].disable();
				}
				/*if(rec.get('saStateUpd')=='4'){//删除待审核状态 禁修改、删除
					grid.getTopToolbar().items.items[4].disable();
					grid.getTopToolbar().items.items[6].disable();
				}else if(rec.get('saStateUpd')=='3'){//修改待审核的状态禁止删除，可修改
					grid.getTopToolbar().items.items[4].enable();
					grid.getTopToolbar().items.items[6].disable();
				}else if(rec.get('saStateUpd')=='1'){//已删除状态 禁止修改、删除
					grid.getTopToolbar().items.items[4].disable();
					grid.getTopToolbar().items.items[6].disable();
				}else if(rec.get('saStateUpd')=='0'){//新增待审核状态也禁止删除，可修改
					grid.getTopToolbar().items.items[4].enable();
					grid.getTopToolbar().items.items[6].disable();
				}else{
					grid.getTopToolbar().items.items[4].disable();
					grid.getTopToolbar().items.items[6].disable();
				}*/
			}
		});
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
		
		/*******************新增表单***********************/
		// 补电子现金消费新增表单
		var addForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 700,
			layout : 'column',
			waitMsgTarget : true,
			items : [{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
					fieldLabel : '发卡行标识*',
					emptyText : '请选择发卡行标识',
					allowBlank : false,
					xtype: 'dynamicCombo',
					methodName: 'getSendCardNo',
					id : 'brhInsIdCdd',
					hiddenName : 'brhInsIdCd',
					width : 300
				}]
			},{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
					xtype: 'dynamicCombo',
					methodName: 'getMchntIdAll',
					fieldLabel: '商户编号*',
					allowBlank : false,
					hiddenName: 'mchtCd',
					id: 'mchtCdd',
					editable: true,
					width: 300,
					callFunction : function() {
						Ext.getCmp('termIdd').reset();
						Ext.getCmp('termIdd').parentP=this.value;
						Ext.getCmp('termIdd').getStore().reload();
					}
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'dynamicCombo',
					methodName: 'getTermId',
					parentP:'',
					fieldLabel: '终端编号*',
					allowBlank : false,
					hiddenName: 'termId',
					id: 'termIdd',
					editable: true,
					minListWidth : 200,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '收单行标识*',
					allowBlank : false,
					id: 'acqInsIdCd',
					regex: /^[0-9]{8}$/,
					regexText : '收单行标识为8位数字',
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '卡号*',
					allowBlank : false,
					id: 'pan',
					regex: /^[0-9]+$/,
	                maxLength : 19,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					fieldLabel : '交易类型*',
					allowBlank : false,
					blankText : '交易类型不能为空',
					emptyText : '请输入交易类型',
					store: txnStore,
					xtype: 'basecomboselect',
					id : 'idtxnNum',
					hiddenName : 'txnNum',
					width : 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '交易批次号*',
					allowBlank : false,
					id: 'txnBatchNo',
					regex: /^[0-9]+$/,
	                maxLength : 6,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '交易凭证号*',
					allowBlank : false,
					id: 'txnCerNo',
					regex: /^[0-9]+$/,
	                maxLength : 6,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					id: 'transAmt',
					fieldLabel: '交易金额(元)*',
					allowBlank : false,
					blankText: '请输入交易金额',
		            vtype: 'isMoney',
					maxLength: 12,
					width:150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'datefield',
					id: 'transDate',
					format: 'Ymd',
					altFormats: 'Ymd',
					allowBlank : false,
					fieldLabel: '交易日期*'
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'IC卡交易证书*',
					allowBlank : false,
					id: 'icCert',
					regex : /^[0-9|A-F]+$/,
					regexText : 'IC卡交易证书必须是16进制字符0-F',
	                maxLength : 16,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'TVR*',
					allowBlank : false,
					id: 'tvr',
	                maxLength : 10,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'TSI*',
					allowBlank : false,
					id: 'tsi',
	                maxLength : 32,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'AID*',
					allowBlank : false,
					id: 'aid',
					regex : /^[0-9|A-F]+$/,
					regexText : 'AID必须是16进制字符0-F',
	                maxLength : 32,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'ATC*',
					allowBlank : false,
					id: 'atc',
					regex : /^[0-9|A-F]+$/,
					regexText : 'ATC必须是16进制字符0-F',
	                maxLength : 4,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '应用标签*',
					allowBlank : false,
					id: 'appTag',
	                maxLength : 60,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '应用首选名称*',
					allowBlank : false,
					id: 'appPreName',
	                maxLength : 60,
					width: 150
				}]
			}]
		});
		
		// 补电子现金消费窗口
		var addWin = new Ext.Window( {
			title : '补电子现金消费新增',
			initHidden : true,
			header : true,
			frame : true,
			closable : true,
			modal : true,
			width : 600,
			autoHeight : true,
			layout : 'fit',
			items : [addForm],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
				text : '确定',
				handler : function() {
					if (addForm.getForm().isValid()) {
						if(Ext.getCmp('transDate').getValue().format('Ymd') > new Date().format('Ymd')){
							showErrorMsg("交易日期大于今天",grid);
                   			return;
						}
						addForm.getForm().submit({
							url : 'T10081Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, addForm);
								// 重置表单
								addForm.getForm().reset();
								addWin.hide();
								// 重新加载列表
								grid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg,addForm);
							},
							params : {
								txnId : '10081',
								subTxnId : '01'
							}
						});
					}
				}
			}, {
				text : '重置',
				handler : function() {
					addForm.getForm().reset();
					Ext.getCmp('acqInsIdCd').setValue('30000000');
				}
			}, {
				text : '关闭',
				handler : function() {
					addWin.hide();
				}
			}]
		});
		
		/*******************修改表单***********************/
		// 补电子现金消费修改表单
		var updateForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 700,
			layout : 'column',
			waitMsgTarget : true,
			items : [{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
					fieldLabel : '发卡行标识*',
					emptyText : '请选择发卡行标识',
					allowBlank : false,
					xtype: 'dynamicCombo',
					methodName: 'getSendCardNo',
					id : 'brhInsIdCdUpdd',
					hiddenName : 'brhInsIdCdUpd',
					width : 300
				}]
			},{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
					xtype: 'dynamicCombo',
					methodName: 'getMchntIdAll',
					fieldLabel: '商户编号*',
					allowBlank : false,
					hiddenName: 'mchtCdUpd',
					id: 'mchtCdUpdd',
					editable: true,
					width: 300,
					callFunction : function() {
						Ext.getCmp('termIdUpdd').setValue('');
						Ext.getCmp('termIdUpdd').parentP=this.value;
						Ext.getCmp('termIdUpdd').inputValue='';
						Ext.getCmp('termIdUpdd').getStore().reload();
					}
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'dynamicCombo',
					methodName: 'getTermId',
					parentP:'',
					fieldLabel: '终端编号*',
					allowBlank : false,
					hiddenName: 'termIdUpd',
					id: 'termIdUpdd',
					editable: true,
					minListWidth : 200,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '收单行标识*',
					allowBlank : false,
					id: 'acqInsIdCdUpd',
					regex: /^[0-9]{8}$/,
					regexText : '收单行标识为8位数字',
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '卡号*',
					allowBlank : false,
					id: 'panUpd',
					regex: /^[0-9]+$/,
	                maxLength : 19,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					fieldLabel : '交易类型*',
					allowBlank : false,
					blankText : '交易类型不能为空',
					emptyText : '请输入交易类型',
					store: txnStore,
					xtype: 'basecomboselect',
					id : 'idtxnNumUpd',
					hiddenName : 'txnNumUpd',
					width : 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '交易批次号*',
					allowBlank : false,
					id: 'txnBatchNoUpd',
					regex: /^[0-9]+$/,
	                maxLength : 6,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '交易凭证号*',
					allowBlank : false,
					id: 'txnCerNoUpd',
					regex: /^[0-9]+$/,
	                maxLength : 6,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					id: 'transAmtUpd',
					fieldLabel: '交易金额(元)*',
					allowBlank : false,
					blankText: '请输入交易金额',
		            vtype: 'isMoney',
					maxLength: 12,
					width:150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'datefield',
					id: 'transDateUpd',
					format: 'Ymd',
					altFormats: 'Ymd',
					allowBlank : false,
					fieldLabel: '交易日期*'
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'IC卡交易证书*',
					allowBlank : false,
					id: 'icCertUpd',
					regex : /^[0-9|A-F]+$/,
					regexText : 'IC卡交易证书必须是16进制字符0-F',
	                maxLength : 16,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'TVR*',
					allowBlank : false,
					id: 'tvrUpd',
	                maxLength : 10,
					width: 150
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'TSI*',
					allowBlank : false,
					id: 'tsiUpd',
	                maxLength : 32,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'AID*',
					allowBlank : false,
					id: 'aidUpd',
					regex : /^[0-9|A-F]+$/,
					regexText : 'AID必须是16进制字符0-F',
	                maxLength : 32,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: 'ATC*',
					allowBlank : false,
					id: 'atcUpd',
					regex : /^[0-9|A-F]+$/,
					regexText : 'ATC必须是16进制字符0-F',
	                maxLength : 4,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '应用标签*',
					allowBlank : false,
					id: 'appTagUpd',
	                maxLength : 60,
					width: 150
				}]
			}, {
	        	columnWidth: .5,
	        	layout: 'form',
	       		items: [{
					xtype: 'textfield',
					fieldLabel: '应用首选名称*',
					allowBlank : false,
					id: 'appPreNameUpd',
	                maxLength : 60,
					width: 150
				}]
			}]
		});
		
		// 补电子现金消费窗口
		var updateWin = new Ext.Window( {
			title : '补电子现金消费修改',
			initHidden : true,
			header : true,
			frame : true,
			closable : true,
			modal : true,
			width : 600,
			autoHeight : true,
			layout : 'fit',
			items : [updateForm],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
				text : '确定',
				handler : function() {
					if (updateForm.getForm().isValid()) {
						if(Ext.getCmp('transDateUpd').getValue().format('Ymd') > new Date().format('Ymd')){
							showErrorMsg("交易日期大于今天",grid);
                   			return;
						}
						updateForm.getForm().submit({
							url : 'T10081Action.asp?method=update',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, updateForm);
								// 重置表单
								updateForm.getForm().reset();
								updateWin.hide();
								// 重新加载列表
								grid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg,updateForm);
							},
							params : {
								txnId : '10081',
								subTxnId : '02',
								id : rec.get('id')
							}
						});
					}
				}
			}, {
				text : '重置',
				handler : function() {
					updateForm.getForm().reset();
					rec = grid.getSelectionModel().getSelected();
					updateForm.getForm().reset();
					updateForm.getForm().loadRecord(rec);
					updateForm.getForm().clearInvalid();
					Ext.getCmp('transAmtUpd').setValue(formatTransAmt(rec.get('transAmtUpd')));
					Ext.getCmp('termIdUpdd').parentP=rec.get('mchtCdUpd');
					Ext.getCmp('termIdUpdd').inputValue=rec.get('termIdUpd');
					Ext.getCmp('termIdUpdd').getStore().reload();
				}
			}, {
				text : '关闭',
				handler : function() {
					updateWin.hide();
				}
			}]
		});

		function formatTransAmt(val) {
			if(val.length != 12) {
				return val;
			} else {
				var str = new String(val);
				var beginPosition = 0;
				var result ='';
				if(str.charAt(0) == '-'){
				    result='-';
				     for(var i = 1; i < 10; i++) {
				     	beginPosition = i;
						if(str.charAt(i) != '0') {
							break;
						}
					}
				}else{
					for(var i = 0; i < 10; i++) {
						beginPosition = i;
						if(str.charAt(i) != '0') {
							break;
						}
					}
				}
				return result + val.substring(beginPosition,10) + '.' + val.substring(10,12);
			}
		}
		
		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});
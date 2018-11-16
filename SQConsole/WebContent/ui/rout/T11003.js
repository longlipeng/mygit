Ext.onReady(function() {

	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('AGENCY_ID_BANK',"('0102')",function(ret){	//只查询工行机构
		agencyStore.loadData(Ext.decode(ret));
	});
	
	//商户MCC数据集
	var mchtMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCC2',function(ret){
		mchtMccStore.loadData(Ext.decode(ret));
	});
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC','*',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	// 终端数据集
	var termStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('TERM_ID_MCHNT','*',function(ret){
		termStore.loadData(Ext.decode(ret));
	});
	//索引值数据集
	var indexStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('INDEX',function(ret){
		indexStore.loadData(Ext.decode(ret));
	});
	// 工行终端通道信息数据集
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
		   {name : 'stat',mapping : 'stat'},
		   {name : 'creTime',mapping : 'creTime'},
		   {name : 'creOprId',mapping : 'creOprId'},
		   {name : 'moditime',mapping : 'moditime'},
		   {name : 'modioprid',mapping : 'modioprid'},
		   {name : 'checktime',mapping : 'checktime'},
		   {name : 'checkoprid',mapping : 'checkoprid'},
		   {name : 'reserve01',mapping : 'reserve01'}
		])
		,autoLoad : true
	});
		//所属机构
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return agencyStore.query("valueField",val).first().data.displayField;
			}
		}
		//商户MCC
		function mchtMccRen(val){
			if(mchtMccStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return mchtMccStore.query("valueField",val).first().data.displayField;
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
		//商户终端号
		function termRen(val){
			if(termStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return termStore.query("valueField",val).first().data.displayField;
			}
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
				hiddenName: 'terminss',
				allowBlank : false,
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			}),renderer:agencyRen
		}, {
			header : '商户MCC码',
			dataIndex : 'mchtmcc',
			width : 80,
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
						rec.set('mchtid',"请选择商户号");
					}
				}
			})//,renderer:mchtMccRen
		}, {
			header : '商户号',
			dataIndex : 'mchtid',
			width : 100,
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
						SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC',rec.get("mchtmcc"),function(ret){
							mchntStore.loadData(Ext.decode(ret));
						});
					},
					'change':function(){
						rec.set('mchttermid',"请选择终端号");
					}
				}
			}),renderer:mchntRen
		}, {
			header : '商户终端号',
			dataIndex : 'mchttermid',
			width : 80,
			editor : new Ext.form.ComboBox( {
				store: termStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				hiddenName: 'mchttermidd',
				allowBlank : false,
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				listeners : {
					'focus': function() {
						termStore.removeAll();
						SelectOptionsDWR.getComboDataWithParameter('TERM_ID_MCHNT',rec.get("mchtmcc")+"-"+rec.get("mchtid"),function(ret){
							termStore.loadData(Ext.decode(ret));
						});
					}
				}
			})//,renderer:termRen
		}, {
			header : '机构MCC码',
			dataIndex : 'insmcc',
			width : 80,
			editor : new Ext.form.ComboBox( {
				store: mchtMccStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				hiddenName: 'insmccc',
				allowBlank : false,
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
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
				maxLength : 15,
				allowBlank : false,
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
				parentP:"('0102')",		//工行机构编号前四位
				allowBlank : false,
				emptyText : '请选择所属机构',
				blankText : '所属机构不能为空',
				value: '01025840',
				xtype: 'dynamicCombo',
				id : 'idtermIns',
				hiddenName: 'termIns',
				width : 200
			},{
				fieldLabel : '商户MCC码*',
				methodName:'getMchntMcc',
				allowBlank : false,
				emptyText : '请选择商户MCC码',
				blankText : '商户MCC码不能为空',
				xtype: 'dynamicCombo',
				id : 'idmchtMcc',
				hiddenName: 'mchtMcc',
				width : 200,
				callFunction : function() {
					Ext.getCmp('idmchtId').reset();
					Ext.getCmp('idmchtId').parentP=this.value;
					Ext.getCmp('idmchtId').getStore().reload();
				}
			},{
				xtype: 'dynamicCombo',
				methodName: 'getMchntNoMcc',
				parentP:'',
				fieldLabel : '商户号*',
				blankText : '商户号不能为空',
				emptyText : '请输入商户号',
				id : 'idmchtId',
				hiddenName : 'mchtId',
				allowBlank : false,
				width : 200,
				callFunction : function() {
					Ext.getCmp('idmchtTermId').reset();
					Ext.getCmp('idmchtTermId').parentP=Ext.getCmp('idmchtMcc').getValue()+'-'+this.value;
					Ext.getCmp('idmchtTermId').getStore().reload();
				}
			},{
				xtype: 'dynamicCombo',
				methodName: 'getTermIdMchnt',
				parentP:'',
				fieldLabel: '商户终端号*',
				blankText : '商户终端号不能为空',
				emptyText: '请输入商户终端号',
				id: 'idmchtTermId',
				hiddenName: 'mchtTermId',
				allowBlank : false,
				width : 200
			}, {
				fieldLabel : '机构MCC码*',
				methodName:'getMchntMcc',
				allowBlank : false,
				emptyText : '请选择机构MCC码',
				blankText : '机构MCC码不能为空',
				xtype: 'dynamicCombo',
				id : 'idinsMcc',
				hiddenName: 'insMcc',
				width : 200
			},{
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
				maxLength : 15
			},{
				xtype: 'basecomboselect',
				store: indexStore,
				fieldLabel: '索引值*',
				blankText : '索引值不能为空',
				emptyText: '请选择索引值',
				id: 'idreserve01',
				hiddenName: 'reserve01',
				allowBlank : false,
				width : 100
			}/*,{
				fieldLabel : '索引值*',
				blankText : '请输入索引值',
				allowBlank : false,
				id : 'idreserve01',
				name : 'reserve01',
				regex: /^[0-9]+$/,
                maxLength : 4,
                minLength : 4,
				width : 80
			} */]
		});
		
		// 添加窗口
		var routWin = new Ext.Window( {
			title : '工行终端通道配置信息添加',
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
								txnId : '11003',
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
				if(record.get('mchtid') == '请选择商户号'){
					showErrorMsg("请选择商户号",grid);
					return;
				}
				if(record.get('mchttermid') == '请选择终端号'){
					showErrorMsg("请选择终端号",grid);
					return;
				}
				var data = {
					id : record.get('id'),
					termIns : record.get('termins'),
					mchtMcc : record.get('mchtmcc'),
					mchtId : record.get('mchtid'),
					mchtTermId : record.get('mchttermid'),
					insMcc : record.get('insmcc'),
					insMcht : record.get('insmcht'),
					insTerm : record.get('insterm'),
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
					txnId : '11003',
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
										txnId : '11003',
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
		
		// 工行终端通道配置信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '工行终端通道配置信息维护',
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
				msg : '正在加载工行终端通道配置信息列表......'
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
  			    parentP:"('0102')",		//工行机构编号前四位
  			    value: '01025840',
  			    hiddenName : 'searchAgent',
				id : 'idsearchAgent',
				width : 200
			}, {
				fieldLabel : '商户MCC码',
				xtype: 'dynamicCombo',
				methodName:'getMchntMcc',
				hiddenName: 'searchMchtMcc',
				id : 'idsearchMchtMcc',
				width : 200,
				callFunction : function() {
					Ext.getCmp('idsearchMchtId').reset();
					Ext.getCmp('idsearchMchtId').parentP=this.value;
					Ext.getCmp('idsearchMchtId').getStore().reload();
				}
			}, {
				fieldLabel : '商户号',
				parentP:'',
				xtype: 'dynamicCombo',
				methodName: 'getMchntNoMcc',
				id : 'idsearchMchtId',
				hiddenName : 'searchMchtId',
				width : 200,
				callFunction : function() {
					Ext.getCmp('idsearchMchtTermId').reset();
					Ext.getCmp('idsearchMchtTermId').parentP=Ext.getCmp('idsearchMchtMcc').getValue()+'-'+this.value;
					Ext.getCmp('idsearchMchtTermId').getStore().reload();
				}
			}, {
				xtype: 'dynamicCombo',
				methodName: 'getTermIdMchnt',
				parentP:'',
				fieldLabel: '商户终端号',
				id: 'idsearchMchtTermId',
				hiddenName: 'searchMchtTermId',
				width : 200
			},{
				fieldLabel : '机构MCC码',
				methodName:'getMchntMcc',
				xtype: 'dynamicCombo',
				id : 'idsearchInsMcc',
				hiddenName: 'searchInsMcc',
				width : 200
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
			buttons : [{
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
						termChannelStore.load();
		        	}
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
				insType : "('0102')",	//工行机构编号前四位
				termIns : queryForm.findById('idsearchAgent').getValue(),
				mchtMcc : queryForm.findById('idsearchMchtMcc').getValue(),
				mchtId : queryForm.findById('idsearchMchtId').getValue(),
				mchtTermId : queryForm.findById('idsearchMchtTermId').getValue(),
				insMcc : queryForm.findById('idsearchInsMcc').getValue(),
				insMcht : queryForm.findById('idsearchInsMcht').getValue(),
				insTerm : queryForm.findById('idsearchInsTerm').getValue(),
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
Ext.onReady(function() {

	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_NO_MCC','*',function(ret){
		mchntStore.loadData(Ext.decode(ret));
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
		
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return agencyStore.query("valueField",val).first().data.displayField;
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
	
		var routColModel = new Ext.grid.ColumnModel( [{
			header : '主键',	
			dataIndex : 'id',
			width : 80,
			hidden: true
		},{
			header : '所属机构',
			dataIndex : 'termins',
			width : 160,
			renderer:agencyRen
		}, {
			header : '商户MCC码',
			dataIndex : 'mchtmcc',
			width : 80
		}, {
			header : '商户号',
			dataIndex : 'mchtid',
			width : 100,
			renderer:mchntRen
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
		
		// 终端通道配置信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '通道商户终端查询',
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
			} , {
				xtype: 'basecomboselect',
				id: 'idstat',
				fieldLabel: '状态',
				width : 180,
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','新增待审核'],
						   ['1','已删除'],
					       ['2','正常'],
					       ['3','修改待审核'],
					       ['4','删除待审核']],
					reader: new Ext.data.ArrayReader()
				})
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
				termIns : queryForm.findById('idsearchAgent').getValue(),
				mchtMcc : queryForm.findById('idsearchMchtMcc').getValue(),
				mchtId : queryForm.findById('idsearchMchtId').getValue(),
				mchtTermId : queryForm.findById('idsearchMchtTermId').getValue(),
				insMcc : queryForm.findById('idsearchInsMcc').getValue(),
				insMcht : queryForm.findById('idsearchInsMcht').getValue(),
				insTerm : queryForm.findById('idsearchInsTerm').getValue(),
				stat : queryForm.findById('idstat').getValue(),
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
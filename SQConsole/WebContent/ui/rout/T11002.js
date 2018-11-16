Ext.onReady(function() {

	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('AGENCY_ID_BANK',owner,function(ret){	//只查询中行机构
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
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 待审核的中行终端通道配置信息数据集
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
				{name : 'creTime',mapping : 'creTime'},
		   		{name : 'creOprId',mapping : 'creOprId'},
				{name : 'moditime',mapping : 'moditime'},
				{name : 'modioprid',mapping : 'modioprid'},
				{name : 'checktime',mapping : 'checktime'},
				{name : 'checkoprid',mapping : 'checkoprid'},
				{name : 'reserve01',mapping : 'reserve01'},
				{name : 'channelType',mapping : 'channelType'},
				{name : 'maxAmt',mapping : 'maxAmt'},
				{name : 'minAmt',mapping : 'minAmt'}
			]),
			autoLoad : true
		});
		//所属机构
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
				return agencyStore.query("valueField",val).first().data.displayField;
			}
		}
		function channelType(val){
			if(val == '01'){
				return '本代本';
			}else if (val == '02'){
				return '本代他';
			}else if(val == '03'){
				return '转银联';
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
		
		var routColModel = new Ext.grid.ColumnModel( [
		                                              sm,{
			header : '主键',	
			dataIndex : 'id',
			width : 80,
			hidden: true
		},{
			header : '所属机构',
			dataIndex : 'termins',
			width : 115,
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
			width : 70
		}, {
			header : '商户号',
			dataIndex : 'mchtid',
			width : 190,
			renderer:mchntRen
		}, {
			header : '商户终端号',
			dataIndex : 'mchttermid',
			width : 80
		}, {
			header : '机构MCC码',
			dataIndex : 'insmcc',
			width : 70
		}, {
			header : '机构商户号',
			dataIndex : 'insmcht',
			width : 130
		}, {
			header : '机构终端号',
			dataIndex : 'insterm',
			width : 80
		}/*,{
			header : '主密钥',
			dataIndex : 'lmk',
			width : 120
		}*/
		,{
			header : '通道类型',
			dataIndex : 'channelType',
			width : 80,
			hidden: true,
			renderer: channelType
		},{
			header : '索引值',
			dataIndex : 'reserve01',
			width : 80,
			renderer: DecToHex
		},{	
			header : '交易金额下限',
			dataIndex : 'minAmt',
			width : 85
		},{	
			header : '交易金额上限',
			dataIndex : 'maxAmt',
			width : 85
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
							var modifyrecords = grid.getSelectionModel().getSelections();
							if(modifyrecords.length == 0){
								return;
							}
							var array = new Array();
							for(var index = 0;index <modifyrecords.length;index++ ){
								var record = modifyrecords[index];
								var data = {
										id : record.get('id')
								};
								array.push(data);
							}
							Ext.Ajax.request({
								url: 'T11016Action.asp?method=accept',
								params: {
								   infList: Ext.encode(array),
									txnId: '11002',
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
					if(getLength(text)== null || getLength(text) == ''){
						alert('拒绝原因不能为空');
						showInputMsg('提示','请输入拒绝原因',true,refuse);
						return;
					}
					if(getLength(text) > 60) {
						alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
						showInputMsg('提示','请输入拒绝原因',true,refuse);
						return;
					}
					showProcessMsg('正在提交审核信息，请稍后......');
					var modifyrecords = grid.getSelectionModel().getSelections();
					if(modifyrecords.length == 0){
						return;
					}
					var array = new Array();
					for(var index = 0;index <modifyrecords.length;index++ ){
						var record = modifyrecords[index];
						var data = {
								id : record.get('id')
						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T11016Action.asp?method=refuse',
						params: {
						   infList: Ext.encode(array),
						   txnId: '11002',
						   subTxnId: '02',
						   refuseInfo: text
						},
					/*rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T11016Action.asp?method=refuse',
						params: {
						    id: rec.get('id'),
							txnId: '11002',
							subTxnId: '02',
							refuseInfo: text
						},*/
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
		
		// 中行终端通道配置信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '终端通道配置信息审核',
			iconCls : 'T11016',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			clicksToEdit : true,
			sm: sm,
			store : termChannelStore,
//			sm : new Ext.grid.RowSelectionModel( {
//				singleSelect : true
//			}),
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
		grid.on({
			'dblclick':function(){
				rec = grid.getSelectionModel().getSelected();
				if(rec != null){
					showTermChannelDetail(agencyRen(rec.get('termins')),	//所属机构
					mchntRen(rec.get('mchtid')),		//商户ID
					DecToHex(rec.get('reserve01')),		//索引值
					formatTs(rec.get('creTime')),		//创建时间
					formatTs(rec.get('moditime')),		//修改时间
					rec,grid);
				}
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
  			    methodName:'getNormalAgentIDOnly',
  			    parentP: owner,		//中行机构编号前四位
//  			    value : '00031514',
  			    hiddenName : 'searchAgent',
				id : 'idsearchAgent',
				width : 200,
				callFunction:function(){
					Ext.getCmp('idchannelType').reset();
					Ext.getCmp('idchannelType').parentP=this.value;
					Ext.getCmp('idchannelType').getStore().reload();
					
				}
			},{
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
				methodName: 'getTermIdMchntOnly',
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
			},  {
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
			},/*{
				xtype: 'basecomboselect',
				id: 'idchannelType',
				fieldLabel: '通道类型',
				width : 180,
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['01','本代本'],
						   ['02','本代他'],
					       ['03','转银联']],
					reader: new Ext.data.ArrayReader()
				})
			}*/{
        		//	xtype: 'basecomboselect',
    			xtype:'dynamicCombo',
		    //     baseParams: 'TRAN_TYPE',
    			methodName: 'getTranType2',
//				labelStyle: 'padding-left: 5px',
//				fieldLabel: '渠道类型',
    			hidden: true,
				id: 'idchannelType',
				hiddenName: 'channelType',	
				allowBlank: true,
				anchor: '90%'
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
						termChannelStore.load();
		        	}
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
				insType : owner,	//中行机构编号前四位
				termIns : queryForm.findById('idsearchAgent').getValue(),
				mchtMcc : queryForm.findById('idsearchMchtMcc').getValue(),
				mchtId : queryForm.findById('idsearchMchtId').getValue(),
				mchtTermId : queryForm.findById('idsearchMchtTermId').getValue(),
				insMcc : queryForm.findById('idsearchInsMcc').getValue(),
				insMcht : queryForm.findById('idsearchInsMcht').getValue(),
				insTerm : queryForm.findById('idsearchInsTerm').getValue(),
				channelType : queryForm.findById('idchannelType').getValue(),
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
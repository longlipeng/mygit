Ext.onReady(function() {

	var curOp = "01";
	var curIndex = "";
	
	
	//省数据集
	var provinceStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('PROVINCES',function(ret){
		provinceStore.loadData(Ext.decode(ret));
	});
	
	//市数据集
	var citiesStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	SelectOptionsDWR.getComboData('CITIES',function(ret){
		citiesStore.loadData(Ext.decode(ret));
	});
	
	//经营场所权属
	 var manageOwner = new Ext.data.JsonStore({
	  fields: ['valueField','displayField'],
	  root: 'data'
	 });
	 SelectOptionsDWR.getComboData('MANAGEOWNER',function(ret){
		 manageOwner.loadData(Ext.decode(ret));
	 });
	 
	
	 
	//渠道合作商类型
	 var channelStore = new Ext.data.JsonStore({
	  fields: ['valueField','displayField'],
	  root: 'data'
	 });
	 SelectOptionsDWR.getComboData('CHANTYPE',function(ret){
		 channelStore.loadData(Ext.decode(ret));
	 });
	 
	 
	// 新增 修改 表单
	var mainForm = new Ext.form.FormPanel({
		autoHeight : true,
		frame : true,
		labelWidth : 230,
		waitMsgTarget : true,
		labelAlign : 'center',
		items : [ {
			layout : 'column',
			frame : true,
			defaults:{
				labelWidth:150
			},
			items : [ {
				columnWidth : 1,
				layout : 'form',
				hidden:true,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商编号*',
					id : 'agentNo',
					name : 'agentNo'
					/*regex: /^[0-9]{4}$/,
					regexText: '请输入4位数字',
					allowBlank:false,*/
				} ]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商名称*',
					maxLength:25,
					id : 'agentNm',
					name : 'agentNm',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '申请日期*',
					name : 'applyDate',
					id : 'applyDate',
					width:128,
					allowBlank:false,
					listeners:{
						'select':function(){
							if(Ext.getCmp('contraEndDt').getValue() != ''&&
									(Ext.getCmp('applyDate').getValue().format('Ymd')>Ext.getCmp('contraEndDt').getValue().format('Ymd'))){
								showAlertMsg("申请日期不能大于合同到期日，请重新选择！",mainForm);
								Ext.getCmp('applyDate').setValue('');
								Ext.getCmp('contraEndDt').setValue('');
							}
						
						}
						
					}
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '合同到期日*',
					name : 'contraEndDt',
					id : 'contraEndDt',
					width:128,
					allowBlank:false,
					listeners:{
						'select':function(){
							if(Ext.getCmp('applyDate').getValue() != ''&&
									(Ext.getCmp('applyDate').getValue().format('Ymd')>Ext.getCmp('contraEndDt').getValue().format('Ymd'))){
								showAlertMsg("申请日期不能大于合同到期日，请重新选择！",mainForm);
								Ext.getCmp('applyDate').setValue('');
								Ext.getCmp('contraEndDt').setValue('');
							}
						
						}
						
					}
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'dynamicCombo',
					fieldLabel : '代理商所属省份*',
					hiddenName: 'agentProvince',
					id : 'idagentProvince',
					width:128,
					allowBlank:false,
					methodName: 'getProvinces',
					callFunction : function() {
						Ext.getCmp('idagentCity').reset();
						Ext.getCmp('idagentCity').parentP=this.value;
						Ext.getCmp('idagentCity').getStore().reload();
					}
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'dynamicCombo',
					fieldLabel : '代理商所属城市*',
					/*name : 'agentCity',*/
					id : 'idagentCity',
					hiddenName:'agentCity',
					width:128,
					allowBlank:false,
					methodName: 'getCitys'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '邮政编码*',
					regex: /^[0-9]{6}$/,
					regexText:'请输入6位邮编',
					name : 'zipCode',
					id : 'zipCode',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '员工人数*',
					name : 'empNum',
					id : 'empNum',
					maxLength:12,
					regex: /^[0-9]+$/,
					regexText:'只能是数字',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '经营场所面积*',
					name : 'manageArea',
					id : 'manageArea',
					maxLength:10,
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'basecomboselect',
					fieldLabel : '经营场所权属*',
					hiddenName : 'manageOwner',
					store:manageOwner,
					allowBlank:false,
					width:128

				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商网站名称',
					maxLength:15,
					name : 'agentWebNm',
					id : 'agentWebNm'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商网站地址',
					maxLength:20,
					name : 'agentWebAdd',
					id : 'agentWebAdd'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : 'ICP备案号',
					maxLength:18,
					name : 'icpRecordNo',
					id : 'icpRecordNo'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : 'ICP备案公司名称',
					maxLength:20,
					name : 'icpCompNm',
					id : 'icpCompNm'
				} ]
			}/*, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '法人代表名称*',
					name : 'managerNm',
					id : 'managerNm',
					allowBlank:false
				} ]
			}*/, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '法人代表身份证号*',
					name : 'managerIdentNo',
					id : 'managerIdentNo',
					regex: /^[0-9]{15}$|^[0-9]{18}$|^\d{17}(\d|x|X)$/,
					regexText:'仅限15位和18位',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商营业执照编号*',
					regex: /^[0-9a-zA-Z]+$/,
					regexText:'只能由0-9,a-z,A-Z组成',
					name : 'licenceNo',
					id : 'licenceNo',
					maxLength:18,
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商组织机构代码编号*',
					maxLength:18,
					name : 'organizNo',
					id : 'organizNo',
					regex: /^[0-9]+$/,
					regexText:'只能是数字',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '收件地址*',
					name : 'address',
					id : 'address',
					allowBlank:false,
					maxLength:25,
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商法人代表名称*',
					name : 'agentManagerNm',
					id : 'agentManagerNm',
					maxLength:10,
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商法人代表电话*',
					name : 'agentManagerTel',
					id : 'agentManagerTel',
					regex: /^[0-9\-]+$/,
					regexText:'只能是数字',
					maxLength:20,
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商商务联系人名称*',
					name : 'agentConnNm',
					id : 'agentConnNm',
					maxLength:10,
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商商务联系人电话*',
					regex: /^[0-9\-]+$/,
					regexText:'只能是数字',
					maxLength:20,
					name : 'agentConnTel',
					id : 'agentConnTel',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '代理商公司电话*',
					name : 'agentTel',
					id : 'agentTel',
					maxLength:20,
					regex: /^[0-9\-]+$/,
					regexText:'只能是数字',
					allowBlank:false
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'dynamicCombo',
					methodName: 'getDistrict',
					fieldLabel : '所属大区*',
					name : 'district',
					hiddenName : 'district',
					allowBlank:false,
					width:128,
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '所属业务人员*',
					name : 'busiPerson',
					id : 'busiPerson',
					allowBlank:false,
					maxLength:10,
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'basecomboselect',
					fieldLabel : '渠道合作商类型*',
					hiddenName : 'channelType',
				//	id : 'channelType',
					store:channelStore,
					allowBlank:false,
					width:128
				} ]
			} ]
		} ],
		buttons : [
				{
					text : '保存',
					handler : function(btn) {
						var frm = mainForm.getForm();
						if (frm.isValid()) {

							if (1 != 1) {
							} else {
								frm.submit({
											url : 'T11501Action.asp?method='
													+ (curOp == '01' ? 'add'
															: 'update'),
											waitTitle : '请稍候',
											waitMsg : '正在提交表单数据,请稍候...',
											success : function(form, action) {
												showSuccessAlert(
														action.result.msg,
														mainForm);
												mainWin.hide(mainGrid);
												mainStore.reload();
												frm.resetAll();
											},
											failure : function(form, action) {
												showErrorMsg(action.result.msg,
														mainForm);
											},
											params : {
												txnId : '11501',
												agentNo_update: Ext.getCmp('agentNo').getValue(),
												subTxnId : curOp,
												index : curIndex
											}
										});
							}
						}
					}
				}, {
					text : '关闭',
					handler : function() {
						mainWin.hide(mainGrid);
						mainForm.getForm().resetAll();
					}
				} ]
	})

	// 新增 修改 弹框
	var mainWin = new Ext.Window({
		title : '代理商信息',
		iconCls : 'T11501',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		width : 670,
		autoHeight : true,
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		items : [ mainForm ]
	});

	

	// 增删查改 详情
	function query() {
		queryWin.show();
	}

	function add() {
		curOp = '01';
	//	Ext.getCmp('agentNo').enable();
		mainWin.show(mainGrid);
	}

	function update() {
		mainForm.getForm().resetAll();
		var sel = mainGrid.getSelectionModel().getSelected();
		if (sel == null) {
			showMsg("请选择一条记录后再进行操作。", mainGrid);
			return;
		}
		curOp = '02';
		Ext.getCmp('agentNo').disable();
		mainForm.getForm().loadRecord(sel);
		mainForm.getForm().clearInvalid();
		
		mainWin.show();
		/*baseStore.load({
			params : {
				agentNo : sel.data.agentNo
			},
			callback : function(records, options, success) {
				if (success) {
					curOp = '02';
					mainForm.getForm().loadRecord(baseStore.getAt(0));
					mainForm.getForm().clearInvalid();
					mainWin.show();
//					curIndex = sel.data.PARA_IDX;
				} else {
					showErrorMsg("加载信息失败，请刷新并检查数据后重试", mainGrid);
				}
			}
		});*/
	}

	function del() {
		var sel = mainGrid.getSelectionModel().getSelected();
		if (sel == null) {
			showMsg("请选择一条记录后再进行操作。", mainGrid);
			return;
		}
		showConfirm('确定要删除该条信息吗？', mainGrid, function(bt) {
			if (bt == "yes") {
				Ext.Ajax.request({
					url : 'T11501Action.asp?method=delete',
					success : function(rsp, opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							mainStore.reload();
							showSuccessAlert(rspObj.msg, mainForm);
						} else {
							showErrorMsg(rspObj.msg, mainGrid);
						}
					},
					failure : function() {
						hideMask();
					},
					params : {
						agentNo : sel.data.agentNo,
						txnId : '11501',
						subTxnId : '03'
					}
				});
			}
		})
	}
	;

	function detail() {
		var sel = mainGrid.getSelectionModel().getSelected();
		if (sel == null) {
			showMsg("请选择一条记录后再进行操作。", mainGrid);
			return;
		}
		showDetail(sel.data.agentNo, mainGrid);
	}

	// 数据集
	var mainStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=agent'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data'
		}, [ {
			name : 'agentNo',
			mapping : 'agentNo'
		}, {
			name : 'agentNm',
			mapping : 'agentNm'
		}, {
			name : 'applyDate',
			mapping : 'applyDate'
		}, {
			name : 'district',
			mapping : 'district'
		}, {
			name : 'busiPerson',
			mapping : 'busiPerson'
		},{
			name : 'agentProvince',
			mapping : 'agentProvince'
		}, {
			name : 'agentCity',
			mapping : 'agentCity'
		}, {
			name : 'zipCode',
			mapping : 'zipCode'
		}, {
			name : 'empNum',
			mapping : 'empNum'
		}, {
			name : 'manageArea',
			mapping : 'manageArea'
		}, {
			name : 'manageOwner',
			mapping : 'manageOwner'
		}, {
			name : 'agentWebNm',
			mapping : 'agentWebNm'
		}, {
			name : 'agentWebAdd',
			mapping : 'agentWebAdd'
		}, {
			name : 'icpRecordNo',
			mapping : 'icpRecordNo'
		}, {
			name : 'icpCompNm',
			mapping : 'icpCompNm'
		}, {
			name : 'managerNm',
			mapping : 'managerNm'
		}, {
			name : 'managerIdentNo',
			mapping : 'managerIdentNo'
		}, {
			name : 'licenceNo',
			mapping : 'licenceNo'
		}, {
			name : 'organizNo',
			mapping : 'organizNo'
		}, {
			name : 'address',
			mapping : 'address'
		}, {
			name : 'agentManagerNm',
			mapping : 'agentManagerNm'
		}, {
			name : 'agentManagerTel',
			mapping : 'agentManagerTel'
		}, {
			name : 'agentConnNm',
			mapping : 'agentConnNm'
		}, {
			name : 'agentConnTel',
			mapping : 'agentConnTel'
		}, {
			name : 'agentTel',
			mapping : 'agentTel'
		}, {
			name : 'channelType',
			mapping : 'channelType'
		}, {
			name : 'contraEndDt',
			mapping : 'contraEndDt'
		} ]),
		autoLoad : false
	});

	mainStore.load({});
	// 列模型
	var mainModel = new Ext.grid.ColumnModel([
	/* rowExpander, */
	{
		header : '代理商编号',
		dataIndex : 'agentNo',
		width : 120
	}, {
		header : '代理商名称',
		dataIndex : 'agentNm',
		width : 160
	/* ,id: 'PARA_ID' */}, {
		header : '申请日期',
		dataIndex : 'applyDate',
		width : 140
	}, {
		header : '所属大区',
		dataIndex : 'district',
		width : 120,
		renderer:function(val){return getRemoteTrans(val, "brh");}
	}, {
		header : '所属业务人员',
		dataIndex : 'busiPerson',
		width : 190
	} ]);

	// GRID
	var mainGrid = new Ext.grid.GridPanel({
		title : '代理商维护',
		iconCls : 'T11501',
		region : 'center',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : mainStore,
		cm : mainModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		/* autoExpandColumn: 'PARA_ID', */
		forceValidation : true,
		/* plugins: rowExpander, */
		tbar : [ {
			xtype : 'button',
			text : '查询',
			name : 'query',
			id : 'query',
			iconCls : 'query',
			width : 75,
			handler : function() {
				query();
			}
		}, '-', {
			xtype : 'button',
			text : '新增',
			name : 'add',
			id : 'add',
			iconCls : 'add',
			width : 75,
			handler : function() {
				add();
			}
		}, '-', {
			xtype : 'button',
			text : '修改',
			name : 'update',
			id : 'update',
			iconCls : 'edit',
			width : 75,
			handler : function() {
				update();
			}
		}, '-', {
			xtype : 'button',
			text : '删除',
			name : 'delete',
			id : 'delete',
			iconCls : 'delete',
			width : 75,
			handler : function() {
				del();
			}
		}, '-', {
			xtype : 'button',
			text : '查看详细信息',
			name : 'detail',
			id : 'detail',
			iconCls : 'detail',
			width : 85,
			handler : function() {
				detail();
			}
		} ],
		loadMask : {
			msg : '正在加载信息列表，请稍候......'
		},
		bbar : new Ext.PagingToolbar({
			store : mainStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});




    
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame : true,
		border : true,
		width : 300,
		autoHeight : true,
		items : [ {
			xtype : 'textfield',
			id : 'agentNm_query',
			name : 'agentNm_query',
			width:180,
			fieldLabel : '代理商名称'
		}, {
			xtype : 'datefield',
			id : 'applyDate_query',
			name : 'applyDate_query',
			fieldLabel : '申请日期',
			width:180,
			format: 'Ymd',
			altFormats: 'Ymd'
		}, {
			xtype : 'dynamicCombo',
			methodName: 'getDistrict',
			hiddenName : 'district_query',
			id:'IDdistrict_query',
			fieldLabel : '所属大区',
		    width:180
		}, {
			xtype : 'textfield',
			id : 'busiPerson_query',
			name : 'busiPerson_query',
			fieldLabel : '所属业务人员',
		    width:180
		} ]
	});
	// 查询 弹框
	var queryWin = new Ext.Window({
		title : '查询条件',
		layout : 'fit',
		width : 400,
		autoHeight : true,
		items : [queryForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',

		tools : [ {
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
				mainStore.load();
				queryWin.hide();
			}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	
	// 查询加载

	mainStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
			agentNm_query : queryForm.findById('agentNm_query').getValue(),
			applyDate_query : typeof(queryForm.findById('applyDate_query').getValue()) == 'string' ? '' : queryForm.findById('applyDate_query').getValue().dateFormat('Ymd'),
			district_query : queryForm.findById('IDdistrict_query').getValue(),
			busiPerson_query : queryForm.findById('busiPerson_query').getValue()
		});
	});
	
	// Main View
	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ mainGrid ],
		renderTo : Ext.getBody()
	})






// 详情 方法
function showDetail(id, El) { // 传入agentNo、mainGrid
	var baseStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'loadRecordAction.asp?storeId=getAgentInf'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data'
		}, [ {
			name : 'agentNo2',
			mapping : 'agentNo'
		}, {
			name : 'agentNm2',
			mapping : 'agentNm'
		}, {
			name : 'applyDate2',
			mapping : 'applyDate'
		}, {
			name : 'district2',
			mapping : 'district'
		}, {
			name : 'busiPerson2',
			mapping : 'busiPerson'
		}, {
			name : 'agentProvince2',
			mapping : 'agentProvince'
		}, {
			name : 'agentCity2',
			mapping : 'agentCity'
		}, {
			name : 'zipCode2',
			mapping : 'zipCode'
		}, {
			name : 'empNum2',
			mapping : 'empNum'
		}, {
			name : 'manageArea2',
			mapping : 'manageArea'
		}, {
			name : 'manageOwner2',
			mapping : 'manageOwner'
		}, {
			name : 'agentWebNm2',
			mapping : 'agentWebNm'
		}, {
			name : 'agentWebAdd2',
			mapping : 'agentWebAdd'
		}, {
			name : 'icpRecordNo2',
			mapping : 'icpRecordNo'
		}, {
			name : 'icpCompNm2',
			mapping : 'icpCompNm'
		}, {
			name : 'managerNm2',
			mapping : 'managerNm'
		}, {
			name : 'managerIdentNo2',
			mapping : 'managerIdentNo'
		}, {
			name : 'licenceNo2',
			mapping : 'licenceNo'
		}, {
			name : 'organizNo2',
			mapping : 'organizNo'
		}, {
			name : 'address2',
			mapping : 'address'
		}, {
			name : 'agentManagerNm2',
			mapping : 'agentManagerNm'
		}, {
			name : 'agentManagerTel2',
			mapping : 'agentManagerTel'
		}, {
			name : 'agentConnNm2',
			mapping : 'agentConnNm'
		}, {
			name : 'agentConnTel2',
			mapping : 'agentConnTel'
		}, {
			name : 'agentTel2',
			mapping : 'agentTel'
		}, {
			name : 'channelType2',
			mapping : 'channelType'
		}, {
			name : 'contraEndDt2',
			mapping : 'contraEndDt'
		} ]),
		autoLoad : false
	});

	baseStore.load({
		params : {
			agentNo : id
		},
		callback : function(records, options, success) {
			if (success) {
				curOp = '02';
				detailForm.getForm().loadRecord(baseStore.getAt(0));
				
				detailWin.show(El);
			} else {
				showErrorMsg("加载信息失败，请刷新数据后重试", El);
			}
		}
	});

	// 代理商详情表单
	var detailForm = new Ext.form.FormPanel({
		autoHeight : true,
		frame : true,
		labelWidth : 230,
		waitMsgTarget : true,
		// layout: 'column',
		border : false,
		labelAlign : 'center',
		items : [ {
			// title: '基本信息',
			layout : 'column',
			frame : true,
			items : [ {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商编号',
					id : 'agentNo2',
					name : 'agentNo2'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商名称',
					id : 'agentNm2',
					name : 'agentNm2'
				} ]
			} , {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '申请日期',
					name : 'applyDate2',
					id : 'applyDate2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '合同到期日',
					name : 'contraEndDt2',
					id : 'contraEndDt2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商所属省份',
					id : 'agentProvince2',
					name : 'agentProvince2'
					
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商所属城市',
					name : 'agentCity2',
					id : 'agentCity2'
				
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '邮政编码',
					name : 'zipCode2',
					id : 'zipCode2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '员工人数',
					name : 'empNum2',
					id : 'empNum2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '经营场所面积',
					name : 'manageArea2',
     				id : 'manageArea2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '经营场所权属',
					name : 'manageOwner2',
					id : 'manageOwner2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商网站名称',
					name : 'agentWebNm2',
					id : 'agentWebNm2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商网站地址',
					name : 'agentWebAdd2',
					id : 'agentWebAdd2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : 'ICP备案号',
					name : 'icpRecordNo2',
					id : 'icpRecordNo2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : 'ICP备案公司名称',
					name : 'icpCompNm2',
					id : 'icpCompNm2'
				} ]
			}/*, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '法人代表名称',
					name : 'managerNm2',
    				id : 'managerNm2'
				} ]
			}*/, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '法人代表身份证号',
					name : 'managerIdentNo2',
					id : 'managerIdentNo2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商营业执照编号',
					name : 'licenceNo2',
					id : 'licenceNo2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商组织机构代码证编号',
					name : 'organizNo2',
					id : 'organizNo2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '收件地址',
					name : 'address2',
					id : 'address2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商法人代表名称',
					name : 'agentManagerNm2',
					id : 'agentManagerNm2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商商务联系人名称',
					name : 'agentConnNm2',
					id : 'agentConnNm2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商商务联系人电话',
					name : 'agentConnTel2',
					id : 'agentConnTel2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商法人代表电话',
					name : 'agentManagerTel2',
					id : 'agentManagerTel2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '代理商电话',
					name : 'agentTel2',
					id : 'agentTel2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '所属大区',
					name : 'district2',
					id : 'district2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '所属业务人员',
					name : 'busiPerson2',
					id : 'busiPerson2'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'displayfield',
					fieldLabel : '渠道合作商类型',
					name : 'channelType2',
					id : 'channelType2'
				} ]
			}]
		} ]

	})

	// 代理商详情 弹框
	var detailWin = new Ext.Window({
		title : '代理商详细信息',
		iconCls : 'detail',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 670,
		autoHeight : true,
		buttonAlign : 'center',
		resizable : false,
		items : [ detailForm ]
	});
}

})

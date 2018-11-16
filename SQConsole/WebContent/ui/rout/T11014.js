Ext.onReady(function() {

	//通道代码数据集
	var routingChannelStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	SelectOptionsDWR.getComboData('ROUTINGCHANNEL',function(ret){
		routingChannelStore.loadData(Ext.decode(ret));
	});
	//机构数据集
	var agencyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCY_ID',function(ret){
		agencyStore.loadData(Ext.decode(ret));
	});
	
	// 路由切换表信息数据集
		var routStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=routeChgInf'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {name : 'destInstId', mapping : 'destInstId'}, 
			     {name : 'dftDestId', mapping : 'dftDestId'}, 
			     {name : 'chgFlag',	mapping : 'chgFlag'}, 
			     {name : 'chgDestId', mapping : 'chgDestId'},
			     {name : 'createTime', mapping : 'createTime'},
			     {name : 'createOprId', mapping : 'createOprId'},
			     {name : 'modiTime', mapping : 'modiTime'},
			     {name : 'modiOprId', mapping : 'modiOprId'
			}]),
			autoLoad : true
		});
		
		function agencyRen(val){
			if(agencyStore.query("valueField",val).first() == undefined){
				return val;
			}else{
			return agencyStore.query("valueField",val).first().data.displayField;
			}
		}
		function routingChannelRen(val){
			if(routingChannelStore.query("valueField",val).first() == undefined){
				return val;
			}else{
			return routingChannelStore.query("valueField",val).first().data.displayField;
			}
		}
		
		var routColModel = new Ext.grid.ColumnModel( [{
				header : '机构代码',
				dataIndex : 'destInstId',
				width :250,
				renderer:agencyRen
			},{
				header : '原目的通道ID',
				dataIndex : 'dftDestId',
				width : 150,
//				renderer:agencyInfo
				renderer:routingChannelRen,
				editor : new Ext.form.ComboBox( {
					store: routingChannelStore,
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
				header : '切换标志',
				dataIndex : 'chgFlag',
				width : 150,
				renderer:chgFlag
			},{
				header : '切换后目的通道ID',
				dataIndex : 'chgDestId',
				width : 150,
				renderer:routingChannelRen,
				editor : new Ext.form.ComboBox( {
					store: routingChannelStore,
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
				header : '创建时间',
				dataIndex : 'createTime',
				width : 120,
				renderer: formatTs
			},{
				header : '创建人',
				dataIndex : 'createOprId',
				width : 80
			},{
				header : '修改时间',
				dataIndex : 'modiTime',
				width : 120,
				renderer: formatTs
			},{
				header : '修改人',
				dataIndex : 'modiOprId',
				width : 80
			}]);

		var menuArr = new Array();
		// 路由切换添加表单
		var routInfoForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 750,
			labelWidth : 90,
//			defaultType : 'textfield',
			waitMsgTarget : true,
			items : [ {
				fieldLabel : '机构代码*',
				emptyText : '请选择机构代码',
				allowBlank : false,
				store: agencyStore,
				xtype: 'basecomboselect',
				id : 'iddestInstId',
				name : 'destInstId',
				hiddenName : 'destInstId',
				width : 150
			},{
				fieldLabel : '原目的ID*',
				allowBlank : false,
				xtype: 'basecomboselect',
				emptyText : '请选择原目的ID',
				store: routingChannelStore,
				id : 'iddftDestId',
				hiddenName : 'dftDestId',
				width : 150
			},{
				fieldLabel : '切换标志*',
				id : 'chgFlag',
				name : 'chgFlag',
				width : 200,
				xtype: 'radiogroup',
                disabled:false,
				items: [           
                  {boxLabel: '不切换(默认)', name: 'chgFlag', inputValue: 0,checked : true},          
                  {boxLabel: '切换', name: 'chgFlag', inputValue: 1}
                ]
			} ,{
				fieldLabel : '切换后目的ID*',
				allowBlank : false,
				xtype: 'basecomboselect',
				blankText : '切换后目的ID不能为空',
				emptyText : '请选择切换后目的ID',
				store: routingChannelStore,
				id : 'idchgDestId',
				hiddenName : 'chgDestId',
				width : 150
			} ]
		});
		
		// 添加窗口
		var routWin = new Ext.Window( {
				title : '路由切换信息添加',
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
								url : 'T11014Action.asp?method=add',
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
									destInstId : Ext.getCmp('iddestInstId').getValue(),
									txnId : '11014',
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
					chgDestId : record.get('chgDestId'),
					chgFlag : record.get('chgFlag'),
					dftDestId : record.get('dftDestId'),
					destInstId : record.get('destInstId')
				};
				array.push(data);
			}
			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.request( {
				url : 'T11014Action.asp?method=update',
				method : 'post',
				params : {
					parameterList : Ext.encode(array),
					txnId : '11014',
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
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
			grid.getTopToolbar().items.items[4].disable();
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
									url : 'T11014Action.asp?method=delete',
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);
											grid.getStore().reload();
											grid.getTopToolbar().items.items[2].disable();
										} else {
											showErrorMsg(rspObj.msg, grid);
										}
									},
									params : {
										destInstId : rec.get('destInstId'),
										txnId : '11014',
										subTxnId : '03'
									}
								});
							}
						});
				}
			}
		};

		// 切换
		var switchMenu = {
			text : '切换',
			width : 85,
			iconCls : 'recover',
			disabled : true,
			handler : function() {
				if (grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();

					showConfirm('确定要切换该路由吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.requestNeedAuthorise( {
									url : 'T11014Action.asp?method=switchFlag',
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);
											grid.getStore().reload();
											grid.getTopToolbar().items.items[6].disable();
										} else {
											showErrorMsg(rspObj.msg, grid);
										}
									},
									params : {
										destInstId : rec.get('destInstId'),
										txnId : '11014',
										subTxnId : '02'
									}
								});
							}
						});
				}
			}
		};
		
		menuArr.push(addMenu);    // [0]
		menuArr.push('-');        // [1]
		menuArr.push(delMenu);    // [2]
		menuArr.push('-');        // [3]
		menuArr.push(upMenu);     // [4]
		menuArr.push('-');        // [5]
		menuArr.push(switchMenu); // [6]

		// 路由切换信息
		var grid = new Ext.grid.EditorGridPanel( {
			title : '路由切换',
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
				msg : '正在加载路由切换信息列表......'
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
				grid.getTopToolbar().items.items[2]. enable();
				grid.getTopToolbar().items.items[6]. enable();
			}
		});
		// 每次在列表信息加载前都将保存按钮屏蔽
		grid.getStore().on('beforeload', function() {
			grid.getTopToolbar().items.items[4].disable();
			grid.getStore().rejectChanges();
		});
		grid.on( {
		// 在编辑单元格后使保存按钮可用
			'afteredit' : function(e) {
				if (grid.getTopToolbar().items.items[4] != undefined) {
					grid.getTopToolbar().items.items[4].enable();
				}
			}
		});

		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ grid ],
			renderTo : Ext.getBody()
		});
	});

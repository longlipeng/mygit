Ext.onReady(function() {
	
	// 可选分公司数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
//	SelectOptionsDWR.getComboData('BRANCH_NO',function(ret){
	SelectOptionsDWR.getComboData('BRH_BELOW_NORMAL',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var roleForLook = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ROLE_FOR_LOOK',function(ret){
		roleForLook.loadData(Ext.decode(ret));
	});
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoS'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprDegree',mapping: 'oprDegree'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'},
			{name: 'pwdOutDate',mapping: 'pwdOutDate'},
			{name: 'oprSta',mapping: 'oprSta'},
			{name: 'oprEmail',mapping: 'oprEmail'},
			{name: 'auditStat',mapping: 'auditStat'},
			{name: 'addOprId',mapping: 'addOprId'},
			{name: 'lastUpdOprId',mapping: 'lastUpdOprId'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'center'},
		{header: '所属分公司',dataIndex: 'brhId',align: 'center'},
		{header: '角色',dataIndex: 'oprDegree',align: 'center',renderer:function(data){
	    	if(null == data) return '';
	    	var record = roleForLook.getById(data);
	    	if(null != record){
	    		return record.data.displayField;
	    	}else{
	    		return '';
	    	}
	    }},
		{header: '名称',dataIndex: 'oprName',align: 'center',id:'oprName',
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '操作员名称不能为空',
			regex:/^[a-zA-Z0-9\u4e00-\u9fa5]+$/,
			regexText:'操作员数据有误重新输入',
			maxLength: 40,
			vtype: 'isOverMax'
		 })},
		{header: '性别',dataIndex: 'oprGender',align: 'center',renderer: gender,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','男'],['1','女']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择性别',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false
		})},
		{header: '注册日期',dataIndex: 'registerDt',width: 100,renderer: formatDt,align: 'center'},
		{header: '联系电话',dataIndex: 'oprTel',align: 'center',
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 30,
			vtype: 'isOverMax',
			regex:/^\d+$/,
			regexText:'电话号码格式输入有误，请重新输入'
		 })},
		{header: '手机',dataIndex: 'oprMobile',align: 'center',
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 30,
			vtype: 'isOverMax',
			regex:/^0{0,1}(13[0-9]|15[7-9]|153|156|18[0-9])[0-9]{8}$/,
			regexText:'手机号码格式输入有误，请重新输入'
		 })},
		 {header: '操作员邮箱',dataIndex: 'oprEmail',align: 'center',
			 editor: new Ext.form.TextField({
			 	allowBlank: true,
				maxLength: 50,
				vtype: 'isOverMax',
				regex:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
				regexText:'邮箱格式输入有误，请重新输入'
		})},
		{header: '授权码有效期',dataIndex: 'pwdOutDate',renderer: formatDt,align: 'center'},
		{header: '状态',dataIndex: 'oprSta',renderer: oprState,align: 'center',editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','可用'],['1','锁定']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择状态',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false})},
		{header: '审核状态',dataIndex: 'auditStat',renderer: oprState01,align: 'center'},
		{header: '新增人',dataIndex: 'addOprId',align: 'center'},
		{header: '最后更新人',dataIndex: 'lastUpdOprId',align: 'center'}
		
	]);
	
	/**
	 * 操作员性别
	 */
	function gender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}
	
	/**
	 * 操作员状态
	 */
	function oprState(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="可用"/>';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="锁定"/>';
		}
		return val;
	}
	function oprState01(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="red">审核不通过</font>';
		} else if(val == '2') {
			return '<font color="gray">添加待审查</font>';
		}else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		}else if(val == '4') {
			return '<font color="red">审核不通过</font>';
		}
		return '<font color="red">状态未知</font>';
		return val;
	}
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条操作员信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10401Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
									oprGrid.getStore().reload();
									oprGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
							},
							params: { 
								oprId: rec.get('oprId'),
								txnId: '10401',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = oprGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			if(modifiedRecords.length > 1) {
				alert("只允许修改一条记录！");  
				return;
			}
//			showProcessMsg('正在保存操作员信息，请稍后......');
			//存放要修改的分公司信息
			/*var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					oprId : record.get('oprId'),
					oprDegree: record.get('oprDegree'),
					oprName: record.get('oprName'),
					oprGender: record.get('oprGender'),
					oprSta: record.get('oprSta'),
					oprTel: record.get('oprTel'),
					oprMobile: record.get('oprMobile'),
					oprEmail:record.get('oprEmail')
				};
				array.push(data);
			}*/
			
			var record = modifiedRecords['0'];
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10401Action.asp?method=update',
				method: 'post',
				params: {
					oprId : record.get('oprId'),
					oprDegree: record.get('oprDegree'),
					oprName: record.get('oprName'),
					oprGender: record.get('oprGender'),
					oprSta: record.get('oprSta'),
					oprTel: record.get('oprTel'),
					oprMobile: record.get('oprMobile'),
					oprEmail:record.get('oprEmail'),
					auditStat:record.get('auditStat'),
//					oprInfoList: Ext.encode(array),
					txnId: '10401',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						oprGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,oprGrid);
					} else {
						oprGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,oprGrid);
					}
					oprGrid.getTopToolbar().items.items[4].disable();
					oprGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var edit = {
		text: '编辑分公司/角色',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			var rec = oprGrid.getSelectionModel().getSelected();
			
			SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',rec.get('brhId'),function(ret){
				gridRoleStore.removeAll();
				gridRoleStore.loadData(Ext.decode(ret));
				editForm.findById('editRole').setValue(rec.get('oprDegree'));
			});
			oprEditWin.show();
			oprEditWin.center();
			editForm.findById('editBrh').setValue(rec.get('brhId'));
			editForm.findById('editRole').setValue(rec.get('oprDegree'));
		}
	};
	
	var reset = {
		text: '授权码重置',
		width: 85,
		iconCls: 'reset',
		disabled: true,
		handler: function() {
			showConfirm('确定要 重置该操作员的授权码吗？',oprGrid,function(bt) {
				if(bt == "yes") {
					Ext.Ajax.requestNeedAuthorise({
						url: 'T10401Action.asp?method=reset',
						method: 'post',
						params: {
							oprId: oprGrid.getSelectionModel().getSelected().get('oprId'),
							auditStat: oprGrid.getSelectionModel().getSelected().get('auditStat'),
							txnId: '10401',
							subTxnId: '05'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,oprGrid);
							} else {
								showErrorMsg(rspObj.msg,oprGrid);
							}
							oprGrid.getTopToolbar().items.items[8].disable();
							oprGrid.getStore().reload();
							hideProcessMsg();
						}
					});
				}
			});
		}
	};
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(upMenu);		// [4]
	menuArr.push('-');			// [5]
	menuArr.push(edit);			// [6]
	menuArr.push('-');			// [7]
	menuArr.push(reset);		// [8]
	menuArr.push('-');			// [7]
	menuArr.push(queryCondition);		// [8]
	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '操作员信息维护',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'oprName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getTopToolbar().items.items[6].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
			//激活菜单
			oprGrid.getTopToolbar().items.items[2].enable();
			oprGrid.getTopToolbar().items.items[6].enable();
			//var rec = oprGrid.getSelectionModel().getSelected();
			oprGrid.getTopToolbar().items.items[8].enable();
			/*if(rec.get('oprSta') == '1') {
				oprGrid.getTopToolbar().items.items[8].enable();
			} else {
				oprGrid.getTopToolbar().items.items[8].disable();
			}*/
		}
	});
	
	// 可选分公司下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择分公司',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个分公司',
		fieldLabel: '所属分公司*',
		hiddenName: 'brhId'
	});
	
	// 根据分公司不同，显示不同级别的角色信息
	brhCombo.on('select',function() {
		SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',brhCombo.getValue(),function(ret){
			roleStore.removeAll();
			roleStore.loadData(Ext.decode(ret));
			roleCombo.setValue(roleStore.getAt(0).get('valueField'));
		});
	});
	
	// 权限数据集
	var roleStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	// 信息列表可选角色下拉列表，随分公司变动
	var roleCombo = new Ext.form.ComboBox({
		//store: roleStore,
		store:roleForLook,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择角色',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个角色',
		fieldLabel: '操作员角色*',
		hiddenName: 'oprDegree'
	});
	
	// 角色添加表单
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 330,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '操作员编号*',
			allowBlank: false,
			blankText: '操作员编号不能为空',
			emptyText: '请输入操作员编号',
			id: 'oprId',
			name: 'oprId',
			regex: /^[a-zA-Z0-9]{8}$/,
			regexText:'操作员编号由8位的数字或字母组成',
			width: 160,
			maxLength: 40,
			vtype: 'isOverMax'
		},
		brhCombo,
		roleCombo,
//		{
//			fieldLabel: '操作员参数',
//			maxLength: 10,
//			id: 'oprDegreeRsc',
//			name: 'oprDegreeRsc',
//			vtype: 'isOverMax',
//			width: 150
//		},
		{
			fieldLabel: '操作员姓名*',
			allowBlank: false,
			blankText: '操作员姓名不能为空',
			emptyText: '请输入操作员姓名',
			maxLength: 40,
			width: 160,
			id: 'oprName',
			name: 'oprName',
			vtype: 'isOverMax'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','男'],['1','女']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择性别',
			hiddenName: 'oprGender',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			fieldLabel: '性别*'
		},{
			inputType: 'password',
			fieldLabel: '授权码*',
			width: 160,
			allowBlank: false,
			blankText: '操作员授权码不能为空',
			vtype: 'isOverMax',
			id: 'oprPwd',
			name: 'oprPwd',
			regex: /^[a-zA-Z0-9]{8}$/,
			regexText: '授权码必须由8位数字或字母组成'
		},{
			fieldLabel: '操作员联系电话',
			allowBlank: true,
			maxLength: 30,
			vtype: 'isOverMax',
			id: 'oprTel',
			name: 'oprTel',
			width: 160,
			regex:/^\d+$/,
			regexText:'电话号码格式输入有误，请重新输入'
		},{
			fieldLabel: '操作员移动电话',
			allowBlank: true,
			maxLength: 30,
			vtype: 'isOverMax',
			id: 'oprMobile',
			name: 'oprMobile',
			vtype: 'isOverMax',
			width: 160,
			regex:/^0{0,1}(13[0-9]|15[7-9]|153|156|18[0-9])[0-9]{8}$/,
			regexText:'手机号码格式输入有误，请重新输入'
		},{
			fieldLabel: '操作员邮箱',
			allowBlank: true,
			maxLength: 50,
			vtype: 'isOverMax',
			id: 'oprEmail',
			name: 'oprEmail',
			vtype: 'email',
			width: 160,
			regex:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			regexText:'邮箱格式输入有误，请重新输入'
		}]
	});
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '操作员添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 330,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submitNeedAuthorise({
						url: 'T10401Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,oprInfoForm);
							//重置表单
							oprInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,oprInfoForm);
						},
						params: {
							txnId: '10401',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(oprGrid);
			}
		}]
	});
	
	// 权限数据集
	var gridRoleStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	// 信息列表可选分公司下拉列表
	var gridBrhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择分公司',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个分公司',
		fieldLabel: '分公司',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	// 根据分公司不同，显示不同级别的角色信息
	gridBrhCombo.on('select',function() {
		SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',gridBrhCombo.getValue(),function(ret){
			gridRoleStore.removeAll();
			gridRoleStore.loadData(Ext.decode(ret));
			var rec = gridRoleStore.getAt(0);
			editForm.findById('editRole').setValue(rec.get('valueField'));
		});
	});
	
	// 信息列表可选角色下拉列表，随分公司变动
	var gridRoleCombo = new Ext.form.ComboBox({
		//store: gridRoleStore,
		store:roleForLook,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择角色',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个角色',
		fieldLabel: '角色',
		id: 'editRole',
		name: 'editRole',
		hiddenName: 'oprDegreeEdit'
	});
	
	// 编辑操作员机构和权限
	var editForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [gridBrhCombo,gridRoleCombo]
	});
	
	// 操作员编辑窗口
	var oprEditWin = new Ext.Window({
		title: '编辑分公司/角色',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [editForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(editForm.getForm().isValid()) {
					var rec = oprGrid.getSelectionModel().getSelected();
					editForm.getForm().submitNeedAuthorise({
						url: 'T10401Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,editForm);
							//重新加载列表
							oprGrid.getStore().reload();
							oprEditWin.hide(oprGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,editForm);
						},
						params: {
//							oprId: oprGrid.getSelectionModel().getSelected().get('oprId'),
							oprId: rec.get('oprId'),
							brhId: editForm.findById('editBrh').getValue(),
							oprDegree: editForm.findById('editRole').getValue(),
							oprSta: rec.get('auditStat'),
							txnId: '10401',
							subTxnId: '04'
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				oprEditWin.hide(oprGrid);
			}
		}]
	});
	
/***************************查询条件*************************/
	
	// 可选分公司下拉列表
	var searchBrhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择分公司',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个分公司',
		fieldLabel: '分公司*',
		id: 'searchBrh',
		name: 'searchBrh',
		hiddenName: 'brhIdSearch'
	});
	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		labelWidth: 80,
		autoHeight: true,
		items: [/*{
			xtype: 'textfield',
			id: 'oprIdQuery',
			width: 160,
			name: 'oprIdQuery',
			vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},searchBrhCombo*/
		        {
					xtype:'dynamicCombo',
					fieldLabel: '操作员编号',
					methodName: 'getoprNo',
					id: 'idoprIdQuery',
					hiddenName: 'oprIdQuery',
					width: 150
				},{
					xtype:'dynamicCombo',
					fieldLabel: '分公司',
					methodName: 'getbrhId',
					id: 'searchBrh',
					hiddenName: 'brhIdSearch',
					width: 150
				}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
			oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			oprId: queryForm.findById('idoprIdQuery').getValue(),
			brhId: queryForm.findById('searchBrh').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});
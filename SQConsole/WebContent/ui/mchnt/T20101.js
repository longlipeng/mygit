Ext.onReady(function() {
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoTmp1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'custNo',mapping: 'custNo'},//客户号
			{name: 'riskGrade',mapping: 'riskGrade'},//客户号
			{name: 'engName',mapping: 'engName'},
			{name: 'routeFlag',mapping:'routeFlag'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'uscCode',mapping: 'uscCode'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'rislLvl',mapping: 'rislLvl'}
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			/*'<p>商户英文名称：{engName}</p>',*/
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',{
				getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户',dataIndex: 'mchtNo',sortable: true,width: 100},
		{header: '商户名称',dataIndex: 'mchtNm',width: 130,id: 'mchtNm'},
		{header: '客户号',dataIndex: 'custNo',width: 80,id: 'custNo'},
//		{header: '风险等级',dataIndex: 'riskGrade ',width: 80,id: 'riskGrade'},
		{header: '是否开通路由',dataIndex: 'routeFlag',width: 80,renderer: isRoute},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 120},
		{header: '社会信用代码',dataIndex: 'uscCode',width: 120},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt},
		{header: '终端数量',dataIndex: 'termCount',width: 60},
		{header: '商户级别',dataIndex: 'rislLvl',width: 50,renderer:getRislLvl}
	]);
	
	function getRislLvl(val){
		if(val == '0'){
		return "正常商户";
		}else if(val == '1'){
			return "中级风险商户";
		}else if(val == '2'){
			return "<font color='red'>高级风险商户</font>";
		}else if(val == '3'){
			return "<font color='red'>复审商户</font>";
		}
	}
	
	// 终端签到状态
	function isRoute(val) {
			if(val == '0')
				return "否";
			if(val == '1')
				return "是";
			return val;
		}
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'posAddr',mapping: 'posAddr'}
		])
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 60},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 60},
		{header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id:'idPosAddr',header: '终端地址',dataIndex: 'posAddr',width: 120,renderer: posAddrVal},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
	function posAddrVal(val){
		if(val!=null&&val!=''){
			return getRemoteTrans(val,"posAddrVal");
		}else{
			return val;
		}
	}
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var deleteMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler:function() {
				var mchtNo = mchntGrid.getSelectionModel().getSelected().get('mchtNo')
				showConfirm('确定删除商户信息吗？商户编号：'+mchtNo,mchntGrid,function(bt) {
					if(bt == 'yes') {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20101Action_delete.asp',
							success : function(form, action) {
								var rspObj = Ext.util.JSON.decode(form.responseText);
								if(rspObj.success){
									showSuccessMsg(rspObj.msg,mchntGrid);
									mchntGrid.getStore().reload();
								}else{
									showErrorMsg(rspObj.msg,mchntGrid);
								}
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mchntGrid);
							},
							params: { 
								mchtNo: mchtNo,
								txnId: '80402',
								subTxnId: '02'
							}
						});
					}
				});
			}
	};
	var queryRisk = {
			text: '查看风险等级',
			width: 85,
			iconCls: 'detail',
			disabled: true,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				var mchtNo = mchntGrid.getSelectionModel().getSelected().get('mchtNo');
				var custNo = mchntGrid.getSelectionModel().getSelected().get('custNo');
				if(custNo == "" || custNo == null || custNo == undefined){ // "",null,undefined
			        alert("客户号为空！");
			        return;
			    }
				Ext.Ajax.requestNeedAuthorise({
							url: 'T20101Action_queryRishGrade.asp',
							success : function(form, action) {
								var rspObj = Ext.util.JSON.decode(form.responseText);
								if(rspObj.success){
									showSuccessMsg(rspObj.msg,grid);
									mchntGrid.getStore().reload();
								}else{
									showSuccessMsg(rspObj.msg,mchntGrid);
								}
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mchntGrid);
							},
							params: { 
								mchtNo: mchtNo
							}
						})
			}	
	};
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
		var rec = mchntGrid.getSelectionModel().getSelected();
		if(rec.get('mchtStatus') == '0'){
			window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
		}/*else if(rec.get('mchtStatus') == '1'){
			window.location.href = Ext.contextPath + '/page/mchnt/T2010103.jsp?mchntId='+rec.get('mchtNo');
		}*/
		}
	};
	
	var stopMenu = {
		text: '冻结',
		width: 85,
		iconCls: 'stop',
		disabled: true,
		handler:function() {
			showConfirm('确定冻结吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				}
			});
		}
	};
	
	function stopBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('操作原因不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				return;
			}
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_stop.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '03',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var recoverMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,recoverBack);
				}
			});
		}
	};
	
	function recoverBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,recoverBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_recover.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '04',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var delMenu = {
			text: '注销',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('注销商户信息后不可恢复,确定注销吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入进行该操作的原因',true,delBack);
					}
				});
			}
		};
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('操作原因不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_del.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '05',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);	
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
	
	menuArr.push(editMenu);    //[0]
	menuArr.push('-');         //[1]
	menuArr.push(stopMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(recoverMenu); //[4]
	menuArr.push('-');         //[5]
	menuArr.push(delMenu);     //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	menuArr.push('-');         //[11]
	menuArr.push(deleteMenu);  //[12]
	menuArr.push('-');         //[13]
	menuArr.push(queryRisk);  //[14]
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};
	
	termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 330,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'idPosAddr',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息维护',
		region: 'center',
		iconCls: 'T20101',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
			}
		});
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
		//20120821修复bug：点击右边终端信息列表刷新按钮记录被清除
		Ext.apply(this.baseParams, {
            start: 0,
            mchntNo: mchntGrid.getSelectionModel().getSelected().data.mchtNo
        });
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();
		mchntGrid.getTopToolbar().items.items[12].disable();
		mchntGrid.getTopToolbar().items.items[14].disable();
	});
	
	var rec;
	
	menuArr.push(editMenu);    //[0]
	menuArr.push('-');         //[1]
	menuArr.push(stopMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(recoverMenu); //[4]
	menuArr.push('-');         //[5]
	menuArr.push(delMenu);     //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	menuArr.push('-');         //[11]
	menuArr.push(deleteMenu);  //[12]
	menuArr.push('-');         //[13]
	menuArr.push(queryRisk);  //[14]
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
//			if(rec.get('mchtStatus') == '0' || rec.get('mchtStatus') == '1' || rec.get('mchtStatus') == '3') {
			
			if(rec.get('mchtStatus') == '0') {	
			mchntGrid.getTopToolbar().items.items[0].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[0].disable();
			}
			if(rec.get('mchtStatus') == '1') {	
				mchntGrid.getTopToolbar().items.items[12].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[12].disable();
			}
			if(rec.get('mchtStatus') == '0') {
				mchntGrid.getTopToolbar().items.items[14].enable();
			}else{
				mchntGrid.getTopToolbar().items.items[14].disable();
			}
			if(rec.get('mchtStatus') == '0') {
				mchntGrid.getTopToolbar().items.items[2].enable();
				mchntGrid.getTopToolbar().items.items[8].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[2].disable();
				mchntGrid.getTopToolbar().items.items[8].disable();
			}
			if(rec.get('mchtStatus') == '6') {
				mchntGrid.getTopToolbar().items.items[4].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[4].disable();
			}
			if(rec.get('mchtStatus') == '0'||rec.get('mchtStatus') == '6') {
				mchntGrid.getTopToolbar().items.items[6].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[6].disable();
			}
			mchntGrid.getTopToolbar().items.items[8].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('endDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册开始日期不能大于注册结束日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false,
			listeners: {
				'select': function() {
					if(Ext.getCmp('startDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
						showAlertMsg("注册结束日期不能小于注册开始日期，请重新选择！",queryForm);
						Ext.getCmp('startDate').setValue('');
					}
				}
			}
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['1','新增待审核'],['3','修改待审核'],['5','冻结待审核'],['6','冻结'],['7','恢复待审核'],['8','注销'],['9','注销待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
		//	xtype: 'basecomboselect',
		//	baseParams: 'BRANCH_NO',
			xtype: 'dynamicCombo',
			methodName: 'getBranchListForSerch',
			fieldLabel: '所属分公司',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: '商户组别',
			hiddenName: 'mchtGrp',
			width: 380
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});
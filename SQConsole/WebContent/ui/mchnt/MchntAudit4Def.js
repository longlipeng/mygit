
//商户信息维护－－查看详细信息，在外部用函数封装
function auditMchnt4Def(recId,El){
  var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	
	//代理商数据集
	var agentStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENT_NORMAL',function(ret){
		agentStore.loadData(Ext.decode(ret));
	});
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
    //区、县数据集
	var areasStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf4Def'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'recId'
		},[
			{name: 'recId',mapping: 'recId'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'genMchtNo',mapping: 'genMchtNo'},
			{name: 'mchtNoIn',mapping: 'mchtNoIn'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'mchtShortName',mapping: 'mchtShortName'},
			{name: 'mchtType',mapping: 'mchtType'},
			{name: 'proCode',mapping: 'proCode'},
			{name: 'cityCode',mapping: 'cityCode'},
			{name: 'countryCode',mapping: 'countryCode'},
			{name: 'regAddress',mapping: 'regAddress'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'email',mapping: 'email'},
			{name: 'busiLicNo',mapping: 'busiLicNo'},
			{name: 'orgCertNo',mapping: 'orgCertNo'},
			{name: 'taxRegCertNo',mapping: 'taxRegCertNo'},
			{name: 'busiScope',mapping: 'busiScope'},
			{name: 'busi',mapping: 'busi'},
			{name: 'industry',mapping: 'industry'},
			{name: 'regDate',mapping: 'regDate'},
			{name: 'regCapital',mapping: 'regCapital'},
			{name: 'legNation',mapping: 'legNation'},
			{name: 'legalName',mapping: 'legalName'},
			{name: 'legCert',mapping: 'legCert'},
			{name: 'legalCardNo',mapping: 'legalCardNo'},
			{name: 'authorizerName',mapping: 'authorizerName'},
			{name: 'authorizerId',mapping: 'authorizerId'},
			{name: 'busiArea',mapping: 'busiArea'},
			{name: 'busiPlaceType',mapping: 'busiPlaceType'},
			{name: 'empCount',mapping: 'empCount'},
			{name: 'webName',mapping: 'webName'},
			{name: 'webUrl',mapping: 'webUrl'},
			{name: 'icpNo',mapping: 'icpNo'},
			{name: 'icpInsName',mapping: 'icpInsName'},
			{name: 'adjustUser',mapping: 'adjustUser'},
			{name: 'adjustUserTel',mapping: 'adjustUserTel'},
			{name: 'mchtTel',mapping: 'mchtTel'},
			{name: 'mchtFax',mapping: 'mchtFax'},
			{name: 'legalTel',mapping: 'legalTel'},
			{name: 'contacts',mapping: 'contacts'},
			{name: 'contactsPhone',mapping: 'contactsPhone'},
			{name: 'contactsTel',mapping: 'contactsTel'},
			{name: 'contactsEmail',mapping: 'contactsEmail'},
			{name: 'rcvProCode',mapping: 'rcvProCode'},
			{name: 'rcvCityCode',mapping: 'rcvCityCode'},
			{name: 'rcvCountryCode',mapping: 'rcvCountryCode'},
			{name: 'rcvAddr',mapping: 'rcvAddr'},
			{name: 'dSingleTransLimit',mapping: 'dSingleTransLimit'},
			{name: 'dDayTransLimit',mapping: 'dDayTransLimit'},
			{name: 'dMonTransLimit',mapping: 'dMonTransLimit'},
			{name: 'dSingleHighTrans',mapping: 'dSingleHighTrans'},
			{name: 'cSingleTransLimit',mapping: 'cSingleTransLimit'},
			{name: 'cDayTransLimit',mapping: 'cDayTransLimit'},
			{name: 'cMonTransLimit',mapping: 'cMonTransLimit'},
			{name: 'cSingleHighTrans',mapping: 'cSingleHighTrans'},
			{name: 'funcCreditCard',mapping: 'funcCreditCard'},
			{name: 'funcPreauth',mapping: 'funcPreauth'},
			{name: 'funcHolidayWithdraw',mapping: 'funcHolidayWithdraw'},
			{name: 'funcReturn',mapping: 'funcReturn'},
			{name: 'funcAutoWithdraw',mapping: 'funcAutoWithdraw'},
			{name: 'funcReturnWithFee',mapping: 'funcReturnWithFee'},
			{name: 'funcPosInExp',mapping: 'funcPosInExp'},
			{name: 'funcPosOutExp',mapping: 'funcPosOutExp'},
			{name: 'funcPosOut',mapping: 'funcPosOut'},
			{name: 'expander',mapping: 'expander'},
			{name: 'expanderTel',mapping: 'expanderTel'},
			{name: 'busiStartTime',mapping: 'busiStartTime'},
			{name: 'busiEndTime',mapping: 'busiEndTime'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'applyDesc',mapping: 'applyDesc'},
			{name: 'settleAccType',mapping: 'settleAccType'},
			{name: 'mchntTpGrp',mapping: 'mchntTpGrp'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'busiUser',mapping: 'busiUser'},
	      	{name: 'busiUserTel',mapping: 'busiUserTel'},
			{name: 'licStartDate',mapping: 'licStartDate'},
			{name: 'licEndDate',mapping: 'licEndDate'},
			{name: 'authorizerTel',mapping: 'authorizerTel'},
			{name: 'aveMonthIncome',mapping: 'aveMonthIncome'},
			{name: 'settleInterval',mapping: 'settleInterval'},
			{name: 'mchtSettleType',mapping: 'mchtSettleType'},
			{name: 'pubAccBank',mapping: 'pubAccBank'},
			{name: 'pubAccBranch',mapping: 'pubAccBranch'},
			{name: 'pubAccBranchNm',mapping: 'pubAccBranchNm'},
			{name: 'pubAccName',mapping: 'pubAccName'},
			{name: 'pubAccNo',mapping: 'pubAccNo'},
			{name: 'persAccBank',mapping: 'persAccBank'},
			{name: 'persAccBranch',mapping: 'persAccBranch'},
			{name: 'persAccBranchNm',mapping: 'persAccBranchNm'},
			{name: 'persAccName',mapping: 'persAccName'},
			{name: 'persAccNo',mapping: 'persAccNo'},
			{name: 'legAccBank',mapping: 'legAccBank'},
			{name: 'legAccBranch',mapping: 'legAccBranch'},
			{name: 'legAccBranchNm',mapping: 'legAccBranchNm'},
			{name: 'legAccName',mapping: 'legAccName'},
			{name: 'legAccNo',mapping: 'legAccNo'},
			{name: 'posCnt',mapping: 'posCnt'},
			{name: 'payCardType',mapping: 'payCardType'},
			{name: 'discNo',mapping: 'discNo'},
			{name: 'realFeePercent',mapping: 'realFeePercent'},
			{name: 'status',mapping: 'status'},
			{name: 'useable',mapping: 'useable'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'crtDate',mapping: 'crtDate'},
			{name: 'uptDate',mapping: 'uptDate'},
			
			
			//关键字段检查信息
			{name: 'ckMchtNm',mapping: 'ckMchtNm'},
			{name: 'ckLicenceNo',mapping: 'ckLicenceNo'},
			{name: 'ckIdentityNo',mapping: 'ckIdentityNo'},
			{name: 'ckBankLicenceNo',mapping: 'ckBankLicenceNo'},
			{name: 'ckManagerTel',mapping: 'ckManagerTel'},
			{name: 'ckAddr',mapping: 'ckAddr'},
			{name: 'ckMCC',mapping: 'ckMCC'}
			
		
			
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'}
		]),
		sortInfo: {field: 'floorMount', direction: 'ASC'},
		autoLoad: false
	});

	var cm = new Ext.grid.ColumnModel({
		columns: [{
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 100
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 90,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '回佣值',
            dataIndex: 'feeValue',
            width: 50
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 85
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 85
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		autoWidth: true,
		frame: true,
		border: true,
		height: 210,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true
	});
    var custom;
	var customEl;
	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
	
	function downPIC(id){
		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(4)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}
	function downPIC1(id){
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg1.getAt(id.substring(5)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCdT'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});


	var gridColumnModel = new Ext.grid.ColumnModel([
		{header: '计费代码',dataIndex: 'discCd',width: 60},
		{header: '计费名称',dataIndex: 'discNm',id:'discNm',width:190},
		{header: '所属机构',dataIndex: 'discOrg',width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true
	});
		//商户费率信息 store
	
	var PersonRecord = Ext.data.Record.create([
	                              	{
	                                      name: 'mchtNo',
	                                      type: 'string'
	                                  }, {
	                                      name: 'tmpNo',
	                                      type: 'string'
	                                  }, {
	                                      name: 'termId',
	                                      type: 'string'
	                                  },{
	                                      name: 'feeType',
	                                      type: 'string'
	                                  },{
	                                      name: 'cityCode',
	                                      type: 'string'
	                                  }, {
	                                      name: 'toBrchNo',
	                                      type: 'string'
	                                  }, {
	                                      name: 'fkBrchNo',
	                                      type: 'string'
	                                  },{
	                                      name: 'cardType',
	                                      type: 'string'
	                                  },{
	                                      name: 'channelNo',
	                                      type: 'string'
	                                  }, {
	                                      name: 'busType',
	                                      type: 'string'
	                                  }, {
	                                      name: 'txnNum',
	                                      type: 'string'
	                                  },{
	                                      name: 'discId',
	                                      type: 'string'
	                                  },{
	                                      name: 'satute',
	                                      type: 'string'
	                                  }]);
	
	var feeStore = new Ext.data.Store({
	
		/*proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfAlgo2'
		}),*/
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'tmpNo',mapping: 'tmpNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'cityCode',mapping: 'cityCode'},
			{name: 'toBrchNo',mapping: 'toBrchNo'},
			{name: 'fkBrchNo',mapping: 'fkBrchNo'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'channelNo',mapping: 'channelNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'discId',mapping: 'discId'},
			{name: 'satute',mapping: 'satute'}
			
		]),
		autoLoad: false
	});
	//商户费率信息 列表
	var feeColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '终端号',dataIndex: 'termId',sortable: true,width: 60},
		{header: '地区码',dataIndex: 'cityCode',sortable: true,width:100},
		{header: '发卡机构',dataIndex: 'fkBrchNo',sortable: true,width:100},
		{header: '目的机构',dataIndex: 'toBrchNo',sortable: true,width:100},
		{header: '卡类型',dataIndex: 'cardType',sortable: true,width:100},
		{header: '交易渠道',dataIndex: 'channelNo',sortable: true,width:100},
		{header: '业务类型',dataIndex: 'busType',sortable: true,width:100},
		{header: '交易类型',dataIndex: 'txnNum',sortable: true,width:100},
		{header: '计费代码',dataIndex: 'feeType',sortable: true,width:100},
		{header: '状态',dataIndex: 'satute',sortable: true,width:100,renderer:statue}
		
	]);
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '注销';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '注销未审核';
		}
	}
	//商户费率信息 表格
	var feeGridPanel = new Ext.grid.GridPanel({
		title: '商户费率信息',
		frame: true,
		border: true,
		height: 320,
		columnLines: true,
		stripeRows: true,
		store: feeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户费率信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: feeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})/*,
		tbar: ['终端号：',{
			xtype: 'textfield',
			id: 'term_ID',
			width: 60
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'searc',
			width: 60,
			handler: function(){
				feeStore.load({
				params: {
					start: 0,
					tmpNo: Ext.getCmp('tmpNo').getValue(),
					termId:Ext.getCmp('term_ID').getValue()
					}
				});
			}
		}]*/
	});
	
	/*feeStore.on('beforeload',function(){//20120924解决商户信息维护之查看详细信息页面中点击费率信息分页中刷新按钮信息会清空的bug
		Ext.apply(this.baseParams, {
			start: 0,
			tmpNo: Ext.getCmp('tmpNo').getValue(),
			termId:Ext.getCmp('term_ID').getValue()
		});
	});*/
	
	/**
	 * 双击弹出费率信息详情窗口
	 * 
	 */	
	var gridPanel_d = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		}
	});
	
	 var detailGrid_d = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		}
	});
	var detailForm = new Ext.form.FormPanel({
			labelWidth : 100,
			frame: true,
            layout: 'border',	
			items:[{
                	region: 'north',
                	height: 75,
                	layout: 'column',
                	items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			 xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					//id: 'fkBrchNo',
					name: 'fkBrchNo',
					maxLength: 8,
					width:150
		        }]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
	            	layout: 'form',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端代码',
						//id: 'termId',
						name: 'termId',
						maxLength: 8,
						width:100
			        }]
	        	},{
            		columnWidth: .33,
	        		xtype: 'panel',
	        		layout: 'form',
            		labelWidth: 70,
       				items: [{
       					xtype: 'displayfield',
       					labelStyle: 'padding-left: 5px',
       					fieldLabel: '计费代码',
						disabled:true,
						name: 'feeType',
						width:80
					}]
				},{
	        		columnWidth: .33,
	        		xtype: 'panel',
	        		labelWidth: 70,
			        layout: 'form',
		       		items: [{
		       			xtype: 'dynamicCombo',
		       			methodName: 'getAreaCode',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地区码',
						disabled:true,
						hiddenName: 'cityCode',
						width:150
		       		}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构',
					disabled:true,
					blankText: '请选择目的机构',
					//id: 'idTO_BRCH_NO_d',
					hiddenName: 'toBrchNo',
					//anchor: '90%',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						disabled:true,
						blankText: '请选择交易渠道',
						hiddenName: 'cardType',
						width:150
		        	}]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						blankText: '请输入交易渠道',
						disabled:true,
						hiddenName: 'channelNo',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						disabled:true,
						blankText: '请输入业务类型',
						//id: 'idBUSINESS_TYPE_d',
						hiddenName: 'busType',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						blankText: '请输入交易类型',
						hiddenName: 'txnNum',
						disabled:true,
						width:150
		        	}]
				}]
                },{
                	region: 'center',
                	items:[gridPanel_d]
                },{
                	region: 'east',
                	width:450,
                	items: [detailGrid_d]
                }]	
	});
	
		var detailWin1 = new Ext.Window({
					title : '费率详情',
				//	initHidden : true,
					header : true,
					frame : true,
					closable : false,
					modal : true,
					width : 800,
					height : 400,
					layout : 'fit',
					items : [ detailForm ],
					buttonAlign : 'center',
					closeAction : 'hide',
					iconCls : 'logo',
					resizable : false,
					buttons : [{
								text : '关闭',
								handler : function() {
									detailWin1.hide();
								}
							} ]
				});
		feeGridPanel.on({	//行双击事件
			'rowdblclick':function(){
				detailForm.getForm().loadRecord(feeGridPanel.getSelectionModel().getSelected());
				gridStore.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				store.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				detailWin1.show();	
		}
		}
	);
		/*********************商户附件列表start*******************************/
		var fileStore = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getDefMchntFiles'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'moduleCode',mapping: 'moduleCode'},
				{name: 'tblKey',mapping: 'tblKey'},
				{name: 'matType',mapping: 'matType'},
				{name: 'storeName',mapping: 'storeName'},
				{name: 'uploadName',mapping: 'uploadName'},
				{name: 'fileSize',mapping: 'fileSize'},
				{name: 'orpId',mapping: 'orpId'},
				{name: 'crtDate',mapping: 'crtDate'}
			]),
			autoLoad: false
		});
		var fileColumnModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: '文件名称',dataIndex: 'uploadName',sortable: true,id:'uploadName'},
			{header: '操作',dataIndex: 'opr',width:100,renderer:downButton}
			
		]);		
		
		function downButton(){
			 return "<input type='button' value='下载' onclick='downloadFile()'>"; 
		}
		window.downloadFile = function(){
			var fileRec=fileGridPanel.getSelectionModel().getSelected();
			var matType = fileRec.get('matType');
			var tblKey = fileRec.get('tblKey');
			var href=System[DOWN_WEBROOT]+"upload/doDownload.html?matType="+matType+"&tblKey="+tblKey+"&moduleCode=2";
			window.open(href);
		};
		
		var fileGridPanel = new Ext.grid.GridPanel({
			title: '文件列表',
			frame: true,
			border: true,
			height: 320,
			columnLines: true,
			stripeRows: true,
			autoExpandColumn: 'uploadName',
			store: fileStore,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			cm: fileColumnModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载商户附件信息列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: fileStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
		
		fileStore.on('beforeload',function(){
			Ext.apply(this.baseParams, {
				start: 0,
				mchntInd: Ext.getCmp('recId').getValue()
			});
		});
		
/********************商户附件列表end*****************************/		
		
/*********************商户操作日志start*******************************/
		var auditStore = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getMchntLogs'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'oprId',mapping: 'oprId'},
				{name: 'txnTime',mapping: 'txnTime'},
				{name: 'oprType',mapping: 'oprType'},
				{name: 'oprStatus',mapping: 'oprStatus'},
				{name: 'oprInfo',mapping: 'oprInfo'}
			]),
			autoLoad: false
		});
		//商户费率信息 列表
		var auditColumnModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: '操作人',dataIndex: 'oprId',sortable: true,width: 150,renderer:getOpr},
			{header: '操作时间',dataIndex: 'txnTime',sortable: true,width:150,renderer: formatTs},
			{header: '操作类型',dataIndex: 'oprType',sortable: true,width:100},
			{header: '操作状态',dataIndex: 'oprStatus',sortable: true,width:100},
			{header: '备注',dataIndex: 'oprInfo',sortable: true,id:'oprInfo'}
			
		]);
		
		function getOpr(val){
			if(val!=null&&val!=''){
			return getRemoteTrans(val,"getOprNm");
			}else{
				return val;
			}
		}
		
		
		var auditGridPanel = new Ext.grid.GridPanel({
			title: '商户操作记录',
			frame: true,
			border: true,
			height: 150,
			columnLines: true,
			stripeRows: true,
			autoExpandColumn: 'oprInfo',
			store: auditStore,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			cm: auditColumnModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载商户操作信息列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: auditStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
		
		auditStore.on('beforeload',function(){
			Ext.apply(this.baseParams, {
				start: 0,
				mchntInd: Ext.getCmp('recId').getValue()
			});
		});
		
/********************商户操作日志end*****************************/
	
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [
			/*{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'BRH_BELOW',
					fieldLabel: '所属分公司',
					labelStyle: 'padding-left: 5px',
//					id: 'idacqInstId',
					hiddenName: 'acqInstId',
					anchor: '90%'
		        }]
			},*/{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly',
	       			baseParams: 'MCHNT_GRP_ALL',
					fieldLabel: '商户名称',
					labelStyle: 'padding-left: 5px',
					id: 'mchtName',
					anchor: '90%'
				}]
			},
			{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
//			        	xtype: 'displayfield',
		       			xtype: 'combofordispaly',
	       				baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						labelStyle: 'padding-left: 5px',
						id:'mchntTpGrp',
						hiddenName: 'mchntTpGrp',
						anchor: '90%'
		        	}]
			},{
			    columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        //	xtype: 'displayfield',
		       			xtype: 'combofordispaly',
			        	baseParams: 'MCC2',
						fieldLabel: '商户MCC',
						labelStyle: 'padding-left: 5px',
						id: 'mcc',
						hiddenName: 'mcc',
						anchor: '90%'
		        	}]
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 420,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
                autoScroll: true,
				layout:'column',
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '商户简称',
						labelStyle: 'padding-left: 5px',
						id: 'mchtShortName'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'MCHNT_ATTR',
				        labelStyle: 'padding-left: 5px',
						fieldLabel: '商户性质',
						id: 'mchtType',
						hiddenName: 'mchtType'
				}]		
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '注册地址',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'regAddress',
						hiddenName: 'regAddress'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				       xtype: 'displayfield',
						fieldLabel: '邮政编号',
						labelStyle: 'padding-left: 5px',
						id: 'postCode',
						hiddenName: 'postCode'
				}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '电子邮件',
						labelStyle: 'padding-left: 5px',
						id: 'email',
						hiddenName: 'email'
					}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						labelStyle: 'padding-left: 5px',
						id: 'busiLicNo'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业组织机构代码',
						labelStyle: 'padding-left: 5px',
						id: 'orgCertNo',
						hiddenName: 'orgCertNo'
				}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						labelStyle: 'padding-left: 5px',
						id: 'taxRegCertNo',
						hiddenName: 'taxRegCertNo'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '经营范围',
						labelStyle: 'padding-left: 5px',
						id: 'busiScope',
						hiddenName: 'busiScope'
				}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                		xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '主营业务',
//						width:130,
						id: 'busi',
						name: 'busi'
	       			}]
                },{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '所属行业',
						labelStyle: 'padding-left: 5px',
						id: 'industry',
						hiddenName: 'industry'
						}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						hiddenName: 'regDate',
						labelStyle: 'padding-left: 5px',
						id: 'regDate',
						fieldLabel: '注册日期'
		        	}]
				
				}
				,{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				       xtype: 'displayfield',
						fieldLabel: '注册资金(万元)',
						labelStyle: 'padding-left: 5px',
						id: 'regCapital',
						hiddenName: 'regCapital'
				}]
				}
				,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'COUNTRYS',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人/负责人国籍',
						width:130,
						hiddenName: 'legNation'
					}]	
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '法人/负责人',
						labelStyle: 'padding-left: 5px',
						id: 'legalName',
						hiddenName: 'legalName'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'CERTIFICATE',
						fieldLabel: '法人/负责人证件类型',
						labelStyle: 'padding-left: 5px',
						id: 'legCert',
						hiddenName: 'legCert'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '法人/负责人证件号码',
						labelStyle: 'padding-left: 5px',
						id: 'legalCardNo',
						hiddenName: 'legalCardNo'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '联系人/授权人姓名',
						labelStyle: 'padding-left: 5px',
						id: 'authorizerName',
						hiddenName: 'authorizerName'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '授权人身份证号',
						labelStyle: 'padding-left: 5px',
						id: 'authorizerId',
						hiddenName: 'authorizerId'
						}]
				},{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属省',
	                    id: 'idprovince',
	                    allowBlank: false,
	                    store:provinceStore,
	                    hiddenName: 'proCode',
	                    width:130
	                }]
	            },{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属市',
	                    id:'idCityCode',
	                    hiddenName: 'cityCode',
	                    allowBlank: false,
	                    store:citiesStore,
	                    width:130
	                }]
	            },{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属区/县',
	                    id:'countryCodeD',
	                    hiddenName:'countryCode',
	                    width:130,
	                    store:areasStore
	                }]
	            },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业面积/平方米',
						name: 'busiArea',
						id: 'busiArea',
						maskRe: /^[0-9\\.]+$/,
						maxLength:10,
						width:130
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'combofordispaly',
						fieldLabel: '经营场所权属',
					//	methodName: 'getOwnerBusi',
						baseParams: 'OWNER_BUSI',
						labelStyle: 'padding-left: 5px',
						id: 'busiPlaceType',
						hiddenName: 'busiPlaceType'
						}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '员工人数',
						name: 'empCount',
						id: 'empCount',
						regex: /^[0-9]*$/,
						maxLength:8,
						width:130
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '公司网址',
						labelStyle: 'padding-left: 5px',
						id: 'webUrl',
						hiddenName: 'webUrl'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案号',
						labelStyle: 'padding-left: 5px',
						id: 'icpNo',
						hiddenName: 'icpNo'
						}]				
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案公司名称',
						labelStyle: 'padding-left: 5px',
						id: 'icpInsName',
						hiddenName: 'icpInsName'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户调单联系人名称',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'adjustUser',
						hiddenName: 'adjustUser'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户调单联系人电话',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'adjustUserTel',
						hiddenName: 'adjustUserTel'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户固定电话',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'mchtTel',
						hiddenName: 'mchtTel'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业传真',
						labelStyle: 'padding-left: 5px',
						id: 'mchtFax',
						hiddenName: 'mchtFax'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '法人电话',
						labelStyle: 'padding-left: 5px',
						id: 'legalTel',
						hiddenName: 'legalTel'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '联系人电话',
						labelStyle: 'padding-left: 5px',
						id: 'contactsTel',
						hiddenName: 'contactsTel'
					}]
					
				}]
            },{
                title:'申请信息',
                id: 'basic1',
                frame: true,
				layout:'column',
                items: [{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单笔借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						id: 'dSingleTransLimit',
						hiddenName: 'dSingleTransLimit'
					}]
					
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单日借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						id: 'dDayTransLimit',
						hiddenName: 'dDayTransLimit'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单月借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'dMonTransLimit',
						hiddenName: 'dMonTransLimit'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单笔预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'cSingleTransLimit',
						hiddenName: 'cSingleTransLimit'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单日预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'cDayTransLimit',
						hiddenName: 'cDayTransLimit'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单月预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'cMonTransLimit',
						hiddenName: 'cMonTransLimit'
					}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预授权功能',
						id: 'funcPreauth',
						name: 'funcPreauth',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
        		columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '退货功能',
					id: 'funcReturn',
					name: 'funcReturn',
					anchor: '90%',
					 disabled: true
	        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '拓展人电话',
				name: 'expanderTel',
				id: 'expanderTel',
			 	allowBlank:false,
				maxLength:50,
				width:130
        	}]
		},/*{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '拓展人',
				name: 'expander',
				id: 'expander',
			 	allowBlank:false,
				maxLength:50,
				width:130
        	}]
		},*/{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '商户营业开始时间',
				id: 'busiStartTime',
				name: 'busiStartTime',
				minValue: '00:00:00',
				maxValue: '23:59:59',
				altFormats: 'H:i:s',
				format: 'H:i:s',
				increment: 10,
				anchor: '55%'
        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '商户营业结束时间',
				id: 'busiEndTime',
				name: 'busiEndTime',
				minValue: '00:00:00',
				maxValue: '23:59:59',
				altFormats: 'H:i:s',
				format: 'H:i:s',
				increment: 10,
				anchor: '55%'
        	}]
		},/*{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
        	defaults: {
    			xtype: 'displayfield',
    			labelStyle: 'padding-left: 5px'
    		},
   			items: [
	        {
        		xtype: 'displayfield',
				fieldLabel: '所属业务人员*',
				width:150,
				id: 'operNm',
				name: 'operNm'
        	}]
		},*/{
            columnWidth: .33, 
            xtype: 'panel',
            layout: 'form',
            items: [{
                xtype: 'combofordispaly',
                labelStyle: 'padding-left: 5px',
                fieldLabel: '代理商',
                id: 'idagentNo',
                allowBlank: false,
                store:agentStore,
                hiddenName: 'agentNo',
                width:130
            }]
        },{
    		columnWidth: .33,
   			xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'checkbox',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '是否有POS内卡受理经验',
				id: 'funcPosInExp',
				name: 'funcPosInExp',
			   disabled: true
        	}]
	},{
    		columnWidth: .33,
   			xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'checkbox',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '是否有POS外卡受理经验',
				id: 'funcPosOutExp',
				name: 'funcPosOutExp',
			   disabled: true
        	}]
	},{
		columnWidth: .33,
			xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '是否申请外卡',
			id: 'funcPosOut',
			name: 'funcPosOut',
			 disabled: true
    	}]
},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '申请人留言',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'applyDesc',
						hiddenName: 'applyDesc'
					}]
				}]		
            },{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                readOnly: true,
                frame: true,
                items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'SETTLE_ACCT_TYPE',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '结算账户类型',
					hiddenName: 'settleAccType'
		        	}
        		]
        	},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户公司帐号开户行名称',
					width:150,
					id: 'pubAccBranchNm'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '商户公司帐号开户行机构代码',
					width:150,
					id: 'pubAccBranch'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '商户公司账户',
					width:150,
					id: 'pubAccName'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '商户公司账号',
					vtype: 'isOverMax',
					width:150,
					id: 'pubAccNo'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '法人账号开户行名称',
					width:150,
					id: 'legAccBranchNm'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '商户法人账号开户行机构代码',
					vtype: 'isOverMax',
					width:150,
					id: 'legAccBranch'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '法人账户',
					width:150,
					id: 'legAccName'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '商户法人账号',
					width:150,
					id: 'legAccNo'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '定向委托帐号开户行名称',
					width:150,
					id: 'persAccBranchNm'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '定向委托帐号帐号开户行机构代码',
					vtype: 'isOverMax',
					width:150,
					id: 'persAccBranch'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '定向委托帐号开户名',
					width:150,
					id: 'persAccName'
	        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden:true,
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					fieldLabel: '定向委托账号',
					width:150,
					id: 'persAccNo'
	        	}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户当前使用账号',
						labelStyle: 'padding-left: 5px',
						width:150,
						id: 'currAccount'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
                        xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户公司账号开户名',
                        maxLength: 39,
						vtype: 'isOverMax',
                        width:150,
                        id: 'settleAcctNm'
                    }]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
//		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户法人账号开户名',
						width:150,
						id: 'feeAcctNm'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
	       			hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托付款标志',
						id: 'dirFlag',
						name: 'dirFlag',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '自动提现标志',
						id: 'funcAutoWithdraw',
						name: 'funcAutoWithdraw',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '节假日提现标志',
						id: 'funcHolidayWithdraw',
						name: 'funcHolidayWithdraw',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '信用卡刷卡功能标志',
						id: 'funcCreditCard',
						name: 'funcCreditCard',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货返还手续费标志',
						id: 'funcReturnWithFee',
						name: 'funcReturnWithFee',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户结算方式T+N',
					labelStyle: 'padding-left: 5px',
					width:150,
					id: 'mchtSettleType'
				}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算周期*',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'settleInterval',
						width: 165,
						anchor: '90%'
		        	}]
				}/*,{
	        		columnWidth: .5,
	       			xtype: 'panel',
	       			hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否自动清算',
						disabled: true,
						id: 'autoStlFlg',
						name: 'autoStlFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货返还手续费',
						disabled: true,
						id: 'feeBackFlg',
						name: 'feeBackFlg'
		        	}]
				}*/
				,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						width:150,
						id: 'speSettleTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						width:150,
						id: 'speSettleLv'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费描述',
						width:150,
						id: 'speSettleDs'
		        	}]
				}
//				,{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '入账凭单打印机构',
//						width:150,
//						id: 'printInstId',
//						name: 'printInstId'
//		        	}]
//				}
//				,{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'checkbox',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '是否收支两条线',
//						disabled: true,
//						width:100,
//						id: 'autoStlFlg',
//						name: 'autoStlFlg'
//		        	}]
//				}
				]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                id:'feeForm',
                region: 'north',
//                height: 75,
                layout: 'column',
                items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	        	hidden:true,
	       		items: [{
	       			 xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					id: 'idFK_BRCH_NO',
					name: 'FK_BRCH_NO',
					maxLength: 8,
					width:150
		        	}]
			},{
		        		columnWidth: .33,
		        		labelWidth: 70,
		            	layout: 'form',
		            	hidden:true,
		        		items: [{
					        xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端代码',
							id: 'idTERM_ID',
							name: 'TERM_ID',
							maxLength: 8,
						    width:100
							 
				        }]
		        	},{
                		columnWidth: .33,
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
                		hidden:true,
	       				items: [{
	       					xtype: 'displayfield',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '费率点差',
							id: 'feeDiffer',
							name: 'feeDiffer',
							vtype:'is2Number', 
							width:80,
							maxLength: 12
						}]
					},{
                		columnWidth: .33,
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
//							disabled:true,
//							readOnly: true,
							width:80
						}]
					},{	 
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
		        		hidden:true,
	       				items: [{
                			xtype: 'displayfield',
							iconCls: 'recover',
							text:'临时编号',
							id: 'tmpNo',
							width: 60
                		}]
					},{
        		    columnWidth: .33,
        	     	xtype: 'panel',
        		    labelWidth: 70,
		            layout: 'form',
		            hidden:true,
	       		    items: [{
	       			xtype: 'combofordispaly',
	       			methodName: 'getAreaCodecode',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地区码',
					editable: true,
					id: 'idCITY_CODE',
					hiddenName: 'CITY_CODE',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
		        hidden:true,
	       		items: [{
			        xtype: 'combofordispaly',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构',
					blankText: '请选择目的机构',
					id: 'idTO_BRCH_NO',
					hiddenName: 'TO_BRCH_NO',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
		        hidden:true,
	       		items: [{
			        	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						blankText: '请选择交易渠道',
						id: 'idCARD_TYPE',
						hiddenName: 'CARD_TYPE',
						width:150
		        	}]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'combofordispaly',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						blankText: '请输入交易渠道',
						id: 'idCHANNEL_NO',
						hiddenName: 'CHANNEL_NO',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'combofordispaly',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						blankText: '请输入业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'combofordispaly',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						blankText: '请输入交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						width:150
		        	}]
				}]
                },{
			       xtype: 'panel',
			      html: '<br/><br/><br/><br/><br/><br/>'
                },{
                	region: 'center',
                	items:[feeGridPanel]
                }
                ]
		    }
		,{
				title:'证书影像',
                id: 'images',
                frame: true,
                layout: 'border',
                items: [{
 			       xtype: 'panel',
 			      html: '<br/><br/><br/><br/><br/><br/>'
                 },{
                 	region: 'center',
                 	items:[fileGridPanel]
                 }
                 ]
		    },{
				title:'审核',
                id: 'audit',
                frame: true,
                layout: 'border',
                autoScroll: true,
                items: [{
                	region: 'center',
                	items:[auditGridPanel]
                }/*,{
 			       xtype: 'panel',
 			      html: '<br/><br/><br/><br/><br/><br/>'
                 }*/,{
                     id:'auditForm',
                     region: 'south',
//                     height: 75,
                     autoHeight: true,
                     layout: 'column',
                     items: [{	 
     		        		xtype: 'panel',
     		        		hidden:true,
     		        		layout: 'form',
     		        		hidden:true,
     	       				items: [{
                     			xtype: 'displayfield',
     							iconCls: 'recover',
     							text:'商户索引',
     							id: 'recId',
     							width: 60
                     		}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '借记卡单笔交易限额',
     							allowBlank: true,
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'daySingleDebit',
     							name: 'daySingleDebit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '借记卡单日交易金额',
     							/*allowBlank: false,
     							blankText: '单日交易金额不能为空',*/
//     							emptyText: '请输入单日交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'dayAmtDebit',
     							name: 'dayAmtDebit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '借记卡单月交易金额',
     							/*allowBlank: false,
     							blankText: '单日交易金额不能为空',*/
//     							emptyText: '请输入单月交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'monAmtDebit',
     							name: 'monAmtDebit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '预付费卡单笔交易限额*',
     							allowBlank: false,
     							blankText: '单笔交易金额不能为空',
//     							emptyText: '请输入单笔交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'daySingleCredit',
     							name: 'daySingleCredit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '预付费卡单日交易金额*',
     							allowBlank: false,
     							blankText: '单日交易金额不能为空',
//     							emptyText: '请输入单日交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'dayAmtCredit',
     							name: 'dayAmtCredit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '预付费卡单月交易金额*',
     							allowBlank: false,
     							blankText: '单日交易金额不能为空',
//     							emptyText: '请输入单月交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'monAmtCredit',
     							name: 'monAmtCredit',
     							vtype: 'isMoney',
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'checkbox',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '是否开通路由',
     							id: 'isRoute',
     							name: 'isRoute',
     							checked:true,
     							width:150
     			        	}]
     					},{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     		       			xtype: 'checkbox',
     		   			   labelStyle: 'padding-left: 5px',
     		   			   fieldLabel: '是否加入白名单',
     		   			   id: 'iswhite',
     		   			  name: 'iswhite',
     		   			  anchor: '90%'
     			        	}]
     					},{
     		        		columnWidth: .5,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     		       			xtype: 'textarea',
     		   			   labelStyle: 'padding-left: 5px',
     		   			   fieldLabel: '备注*',
     		   			   allowBlank: false,
							blankText: '审核备注不能为空',
     		   			   id: 'oprInfo',
     		   			  name: 'oprInfo',
     		   			maxLength: 60,
     		   			  anchor: '90%'
     			        	}]
     					}],
     					buttonAlign: 'center',
     					buttons: [{
     						text: '通过',
     						handler: function() {
     							subAccept();
     						}
     					}, {
     						text: '退回',
     						handler: function() {
     							subBack();
     						}
     					},{
     						text: '拒绝',
     						handler: function() {
     							subrefuse();
     						}
     					}/*,{
     						text: '关闭',
     						handler: function() {
     							detailWin.close();			
     						}
     					}*/]
                     }]
		    }]
        }]
    });
	
	//审核拒绝
	function subrefuse(){
		/*var oprInfo = Ext.getCmp('oprInfo').getValue();
		if(oprInfo==''){
			showErrorMsg("请填写审核备注",mchntForm);
            return;
		}*/
		Ext.getCmp('daySingleCredit').allowBlank=true;
		Ext.getCmp('dayAmtCredit').allowBlank=true;
		Ext.getCmp('monAmtCredit').allowBlank=true;
		if(mchntForm.getForm().isValid()) {
		 mchntForm.getForm().submit({
				url: 'T20210Action.asp?method=refuse',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: recId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
					txnId: '20210',
					subTxnId: '03'
				},
				success: function(form,action) {
					showSuccessMsg(action.result.msg,El);
					detailWin.close();
					// 重新加载商户待审核信息
					El.getStore().reload();
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,mchntForm);
				}
			});
		}
		
	}
	
	//审核退回
	function subBack(){
		/*var oprInfo = Ext.getCmp('oprInfo').getValue();
		if(oprInfo==''){
			showErrorMsg("请填写审核备注",mchntForm);
            return;
		}*/
		Ext.getCmp('daySingleCredit').allowBlank=true;
		Ext.getCmp('dayAmtCredit').allowBlank=true;
		Ext.getCmp('monAmtCredit').allowBlank=true;
		if(mchntForm.getForm().isValid()) {
		 mchntForm.getForm().submit({
				url: 'T20210Action.asp?method=back',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: recId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
					txnId: '20210',
					subTxnId: '02'
				},
				success: function(form,action) {
					showSuccessMsg(action.result.msg,El);
					detailWin.close();
					// 重新加载商户待审核信息
					El.getStore().reload();
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,mchntForm);
				}
			});
		}
		
	}
	
	//审核通过
	function subAccept(){
		Ext.getCmp('daySingleCredit').allowBlank=false;
		Ext.getCmp('dayAmtCredit').allowBlank=false;
		Ext.getCmp('monAmtCredit').allowBlank=false;
		if(mchntForm.getForm().isValid()) {
			
			var daySingleDebit = mchntForm.getForm().findField('daySingleDebit').getValue();
            if(daySingleDebit.indexOf('.') == -1 && daySingleDebit.length>13){//20121115
            	showErrorMsg("借记卡单日交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            var dayAmtDebit = mchntForm.getForm().findField('dayAmtDebit').getValue();
            if(dayAmtDebit.indexOf('.') == -1 && dayAmtDebit.length>13){//20121115
            	showErrorMsg("借记卡单笔交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
           var monAmtDebit = mchntForm.getForm().findField('monAmtDebit').getValue();
           if(monAmtDebit.indexOf('.') == -1 && monAmtDebit.length>13){//20121115
            	showErrorMsg("借记卡单月交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            if(Number(dayAmtDebit) < Number(daySingleDebit))
            {
                showErrorMsg("[借记卡单日交易金额]不能小于[借记卡单笔交易金额]",mchntForm);
                return;
            }
           if(Number(monAmtDebit) < Number(dayAmtDebit))
            {
                showErrorMsg("[借记卡单月累计交易金额]不能小于[借记卡单日交易金额]",mchntForm);
                return;
            }
			var daySingleCredit = mchntForm.getForm().findField('daySingleCredit').getValue();
            if(daySingleCredit.indexOf('.') == -1 && daySingleCredit.length>13){//20121115
            	showErrorMsg("预付费卡单日交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            var dayAmtCredit = mchntForm.getForm().findField('dayAmtCredit').getValue();
            if(dayAmtCredit.indexOf('.') == -1 && dayAmtCredit.length>13){//20121115
            	showErrorMsg("预付费卡单笔交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
           var monAmtCredit = mchntForm.getForm().findField('monAmtCredit').getValue();
           if(monAmtCredit.indexOf('.') == -1 && monAmtCredit.length>13){//20121115
            	showErrorMsg("预付费卡单月交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            if(Number(dayAmtCredit) < Number(daySingleCredit))
            {
                showErrorMsg("[预付费卡单日交易金额]不能小于[贷记卡单笔交易金额]",mchntForm);
                return;
            }
           if(Number(monAmtCredit) < Number(dayAmtCredit))
            {
                showErrorMsg("[预付费卡单月累计交易金额]不能小于[贷记卡单日交易金额]",mchntForm);
                return;
            }
           
           mchntForm.getForm().submit({
				url: 'T20210Action.asp?method=acceptAdd',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: recId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
//					mchntInd:Ext.getCmp('mchntInd').getValue(),
					txnId: '20210',
					subTxnId: '01'
				},
				success: function(form,action) {
					showSuccessMsg(action.result.msg,El);
					detailWin.close();
					// 重新加载商户待审核信息
					El.getStore().reload();
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,mchntForm);
				}
			});
		}
	}

    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 980,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			recId: recId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
//				var discCode = baseStore.getAt(0).data.feeRate;
	 
			/*	var feeTypeValue = baseStore.getAt(0).data.feeType;
 
				var settleType=baseStore.getAt(0).data.settleType;*/
				/*if(settleType>'1'){
					Ext.getCmp('feeCycleID').show();
					Ext.getCmp('feeCycle').show();
				}*/
				if(baseStore.getAt(0).data.ckLicenceNo=="1"){
					Ext.getCmp('busiLicNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckLicenceNo=="2"){
					Ext.getCmp('busiLicNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckLicenceNo=="3"){
					Ext.getCmp('busiLicNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				if(baseStore.getAt(0).data.ckIdentityNo=="1"){
					Ext.getCmp('legalCardNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckIdentityNo=="2"){
					Ext.getCmp('legalCardNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckIdentityNo=="3"){
					Ext.getCmp('legalCardNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				if(baseStore.getAt(0).data.ckBankLicenceNo=="1"){
					Ext.getCmp('orgCertNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckBankLicenceNo=="2"){
					Ext.getCmp('orgCertNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckBankLicenceNo=="3"){
					Ext.getCmp('orgCertNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				
				if(baseStore.getAt(0).data.ckManagerTel=="1"){
					Ext.getCmp('legalTel').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckManagerTel=="2"){
					Ext.getCmp('legalTel').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckManagerTel=="3"){
					Ext.getCmp('legalTel').labelStyle='padding-left: 5px;color:yellow;';
				}
				
				/*if(baseStore.getAt(0).data.ckAddr=="1"){
					Ext.getCmp('addr').labelStyle='padding-left: 5px;color:red;';
				}*/
				if(baseStore.getAt(0).data.ckMchtNm=="1"){
					Ext.getCmp('mchtName').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckMchtNm=="2"){
					Ext.getCmp('mchtName').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckMchtNm=="3"){
					Ext.getCmp('mchtName').labelStyle='padding-left: 5px;color:orange;';
				}
				if(baseStore.getAt(0).data.ckMCC=="1"){
					Ext.getCmp('mcc').labelStyle='padding-left: 5px;background-color:yellow;';
				}
				detailWin.setTitle('商户详细信息');
				detailWin.show();

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchntTpGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					mchntForm.getForm().findField('mcc').setValue(baseStore.getAt(0).data.mcc);
				});
				//初始化省、市、县信息
                SelectOptionsDWR.getComboDataWithParameter('CITIES',baseStore.getAt(0).data.proCode,
						function(ret){
							citiesStore.loadData(Ext.decode(ret));
							Ext.getCmp('idCityCode').setValue(baseStore.getAt(0).data.cityCode);
						});
								
                SelectOptionsDWR.getComboDataWithParameter('AREAS',baseStore.getAt(0).data.cityCode,
						function(ret){
							areasStore.loadData(Ext.decode(ret));
							//mchntForm.getForm().findField('idareaD').setValue(baseStore.getAt(0).data.area);
							Ext.getCmp('countryCodeD').setValue(baseStore.getAt(0).data.countryCode);
						});
				//加载商户费率信息
			/*	feeStore.reload({
					params:{
						start:0,
						tmpNo: baseStore.getAt(0).data.tmpNo
					}
				});*/
                feeStore.insert(0, new  PersonRecord({   

                	feeType: baseStore.getAt(0).data.discNo,   

                	termId: '*',
                	cityCode:'*',
                	fkBrchNo: '*',
                	toBrchNo:'*',
                	cardType: '*',
                	channelNo:'*',
                	busType: '*',
                	txnNum:'*',
                	satute: '0'

                	})); 
				//加载商户操作信息
				auditStore.reload({
					params:{
						start:0,
						mchntInd: baseStore.getAt(0).data.recId
					}
				});
				//加载商户文件信息
				fileStore.reload({
					params:{
						start:0,
						mchntInd: baseStore.getAt(0).data.recId
					}
				});
				//路由默认选择
				Ext.getCmp('isRoute').checked=true;
				
				custom = new Ext.Resizable('showBigPic', {
					    wrap:true,
					    pinned:true,
					    minWidth: 50,
					    minHeight: 50,
					    preserveRatio: true,
					    dynamic:true,
					    handles: 'all',
					    draggable:true
					});
				customEl = custom.getEl();
				customEl.on('dblclick', function(){
					customEl.puff();
				});
				customEl.hide(true);

				/*storeImg.on('load',function(){
					for(var i=0;i<storeImg.getCount();i++){
						var rec = storeImg.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(val.id);
				    	});
				    	Ext.get(rec.btDw).on('click', function(obj,val){
        		        downPIC(val.id);
        	            });
					}
				});
				
				storeImg1.on('load',function(){
		for(var i=0;i<storeImg1.getCount();i++){
			var rec = storeImg1.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC1(val.id);
        	});
		}
	});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: mchntId
					}
				});
				storeImg1.reload({
					params: {
						start: 0,
						imagesId: mchntId
					}
				});		*/
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}
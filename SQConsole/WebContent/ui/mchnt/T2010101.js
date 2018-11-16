Ext.onReady(function() {
		var hasSub = false;
	
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		
		var flagStore = new Ext.data.JsonStore({
			fields: ['valueField','displayField'],
			root: 'data',
			id: 'valueField'
		});
		
		SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
			flagStore.loadData(Ext.decode(ret));
		});
		
		var hasSub = false;
		var fm = Ext.form;
		
		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount',
				idProperty: 'floorMount'
			},[															
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
	            width: 80,
	            sortable: true
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
	            width: 70
	        },{
	            header: '按比最低收费',
	            dataIndex: 'minFee',
	            width: 90
	        },{
	            header: '按比最高收费',
	            dataIndex: 'maxFee',
	            width: 90
	        }]
		})
		
	    var detailGrid = new Ext.grid.GridPanel({
			title: '详细信息',
			frame: true,
			border: true,
			height: 280,
			columnLines: true,	
			autoExpandColumn: 'floorMount',
			stripeRows: true,
			store: store,
			disableSelection: true,
			cm: cm,
			forceValidation: true,
			loadMask: {
				msg: '正在加载计费算法详细信息列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: gridStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
	
		var gridStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
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
		gridStore.load({
			params: {
				start: 0
			}
		});
		var gridColumnModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),									
			{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 100},
			{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
			{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:140,renderer:function(val){return getRemoteTrans(val, "brh")}}
		]);
		var gridPanel = new Ext.grid.GridPanel({
			title: '计费算法信息',
			frame: true,
			border: true,
			height: 280,
			columnLines: true,	
			autoExpandColumn: 'discNm',
			stripeRows: true,
			store: gridStore,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			cm: gridColumnModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载计费算法信息列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: gridStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			}),
			tbar: ['计费代码：',{
				xtype: 'numberfield',
				id: 'serdiscCd',
				width: 70,
				allowNegative: false,
	            maxValue: 100000
			},'-','计费名称：',{
				xtype: 'textfield',
				id: 'serdiscNm',
				width: 110
			},'-',{
				xtype: 'button',
				iconCls: 'query',
				text:'查询',
				id: 'widfalg',
				width: 60,
				handler: function(){
					gridStore.load();
				}
			},'-',{
				xtype: 'button',
				iconCls: 'accept',
				text:'设为该商户计费算法',
				id: 'setup',
				width: 60,
				disabled: true,
				handler: function(){
					Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd)
				}
			}]
		});
		
		gridStore.on('beforeload', function(){
			Ext.apply(this.baseParams, {
				start: 0,
				discCd: Ext.getCmp('serdiscCd').getValue(),
				discNm: Ext.getCmp('serdiscNm').getValue()
			});
		});
		
		gridPanel.getStore().on('beforeload',function() {
			Ext.getCmp('setup').disable();
		});
		
		gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
			Ext.getCmp('setup').enable();
			var id = gridPanel.getSelectionModel().getSelected().data.discCd
			store.load({
				params: {
					start: 0,
					discCd: gridPanel.getSelectionModel().getSelected().data.discCd
					}
				})
		});
		gridStore.on('beforeload', function() {
			store.removeAll();
		});
		
		var tapPanel = new Ext.TabPanel({
			frame: true,
            plain:false,
            activeTab: 0,
            height:360,
            deferredRender: false,
            enableTabScroll: true,
            defaults:{bodyStyle:'padding:10px'},
            items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						maxLength: 15,
						allowBlank: false,
						blankText: '请输入营业执照编号',
						vtype: 'isOverMax',
						id: 'licenceNo',
						name: 'licenceNo'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	layout: 'form',
					items: [{
						xtype: 'datefield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照有效期*',
						maxLength: 10,
						format: 'y-m-d',
						allowBlank: true,
						blankText: '请输入营业执照有效期',
						id: 'licenceEndDate',
						name: 'licenceEndDate',
						anchor: '85%'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构代码证号码*',
						allowBlank: false,
						blankText: '请输入机构代码证号码',
						id: 'bankLicenceNo',
						name: 'bankLicenceNo'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码',
						allowBlank: false,
						blankText: '请输入税务登记证号码',
						id: 'faxNo',
						name: 'faxNo'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_ATTR',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业性质',
						id: 'idetpsAttr',
						hiddenName: 'etpsAttr'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	        	hidden:true,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CRE_LVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业资质等级',
						id: 'idmchtCreLvl',
						hiddenName: 'mchtCreLvl'
		        	}]
	        	}]
			},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'RISKLVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户风险等级',
						width:150,
						hiddenName: 'rislLvl'
		        	}]
				},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMAIL*',
						allowBlank: false,
						blankText: '请输入商户电子邮件地址',
						vtype: 'email',
						id: 'commEmail',
						name: 'commEmail',
						maxLength: 50,
						maxlength: '电子邮件输入内容长度超出限制'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公司网址',
						id: 'homePage',
						name: 'homePage',
						maxLength: 50
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						allowBlank: false,
						blankText: '请输入商户地址',
						maxLength: 80,
						vtype: 'isOverMax',
						id: 'addr',
						name: 'addr'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编码',
						regex: /^[0-9]{6}$/,
						regexText: '邮政编码必须是6位数字',
						id: 'postCode',
						name: 'postCode'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表*',
						allowBlank: false,
						blankText: '请输入法人代表',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'manager',
						name: 'manager'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册资金',
						id: 'busAmt',
						name: 'busAmt'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件类型*',
						id: 'idartifCertifTp',
						hiddenName: 'artifCertifTp'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件号码*',
						allowBlank: false,
						blankText: '请输入法人代表证件号码',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo',
						name: 'identityNo'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人姓名*',
						allowBlank: false,
						blankText: '请输入联系人姓名',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'contact',
						name: 'contact'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电话*',
						allowBlank: false,
						blankText: '请输入联系人电话',
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'commTel',
						name: 'commTel'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业传真',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'fax'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业电话',
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'electrofax',
						name: 'electrofax'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'timefieldsp',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户营业开始时间*',
						id: 'openTime',
						name: 'openTime',
						allowBlank: false,
						blankText: '请输入商户营业开始时间',
						minValue: '00:00',
    					maxValue: '23:59',
    					altFormats: 'H:i',
    					format: 'H:i',
    					editable: true,
    					increment: 10
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'timefieldsp',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户营业结束时间*',
						id: 'closeTime',
						name: 'closeTime',
						allowBlank: false,
						blankText: '请输入商户营业结束时间',
						minValue: '00:00',
    					maxValue: '23:59',
    					altFormats: 'H:i',
    					format: 'H:i',
    					editable: true,
    					increment: 10
		        	}]
	        	}]
			}
//			,{
//	        	columnWidth: .5,
//	       		items: [{
//		        	xtype: 'panel',
//		        	layout: 'form',
//					items: [{
//			        	xtype: 'timefieldsp',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '冬季开门时间',
//						id: 'winterOpenTime',
//						name: 'winterOpenTime',
//						minValue: '00:00',
//    					maxValue: '23:55',
//    					altFormats: 'H:i',
//    					format: 'H:i',
//    					editable: true,
//    					increment: 10
//
//		        	}]
//	        	}]
//			},{
//	        	columnWidth: .5,
//	       		items: [{
//		        	xtype: 'panel',
//		        	layout: 'form',
//					items: [{
//			        	xtype: 'timefieldsp',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '冬季关门时间',
//						id: 'winterCloseTime',
//						name: 'winterCloseTime',
//						minValue: '00:00',
//    					maxValue: '23:55',
//    					altFormats: 'H:i',
//    					format: 'H:i',
//    					editable: true,	
//    					increment: 10
//		        	}]
//	        	}]
//			}
			],
		    listeners:{
		    	'tabchange':function(){
		    		if(hasSub){
			    		mchntForm.getForm().isValid();
		    		}
		    	}
		    	
		    }
            },{
                title:'附加信息',
                layout:'column',
                frame: true,
                items: [{
	        	columnWidth: .5,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持无磁无密交易',
						id: 'passFlag',
						name: 'passFlag'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持人工授权',
						id: 'manuAuthFlag',
						name: 'manuAuthFlag'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持折扣消费',
						id: 'discConsFlg',
						name: 'discConsFlg'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
			        	width: 380,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册地址',
						id: 'regaddr',
						name: 'regaddr'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
					items: [{
						fieldLabel: '签约柜员',
						id: 'prolTlr',
						name: 'prolTlr'
					},{
						fieldLabel: '初审人',
						id: 'preAudNm',
						name: 'preAudNm'
					},{
						fieldLabel: '协议编号',
						id: 'protocalId',
						name: 'protocalId'
		        	},{
						fieldLabel: '签约网点',
						id: 'agrBr',
						name: 'agrBr'
		        	},{
						fieldLabel: '客户经理工号',
						id: 'operNo',
						name: 'operNo'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
					items: [{
						xtype: 'datefield',
						fieldLabel: '签约日期',
						maxLength: 10,
						format: 'y-m-d',
						id: 'prolDate',
						name: 'prolDate',
						anchor: '85%'
		        	},{
						fieldLabel: '批准人',
						id: 'confirmNm',
						name: 'confirmNm'
					},{
						fieldLabel: '受理机构标示码',
						id: 'signInstId',
						name: 'signInstId'
		        	},{
						fieldLabel: '网点电话',
						id: 'netTel',
						name: 'netTel'
		        	},{
						fieldLabel: '客户经理姓名',
						id: 'operNm',
						name: 'operNm'
		        	}]
	        	}]
			}],
		    listeners:{
		    	'tabchange':function(){
		    		if(hasSub){
			    		mchntForm.getForm().isValid();
		    		}
		    	}
		    	
		    }},{
                title:'清算信息',
                layout:'column',
                frame: true,
                items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SET_CUR',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '外卡入账币种',
						id: 'idsetCur',
						hiddenName: 'setCur',
						anchor: '85%'
		        	},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						blankText: '请选择商户结算方式',
						fieldLabel: '商户结算方式*',
						id: 'idsettleType',
						hiddenName: 'settleType',
						anchor: '85%'
		        	}]
		        }]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '入账凭单打印机构*',
						id: 'acqInstId',
						name: 'acqInstId'
		        	},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SETTLE_CHN',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算渠道',
						id: 'idsettleChn',
						hiddenName: 'settleChn',
						anchor: '85%'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
	       		},{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算帐户开户行名',
						id: 'settleBankNm',
						name: 'settleBankNm',
						allowBlank: false,
						blankText: '请输入商户结算帐户开户行名'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算帐户开户名',
						id: 'settleAcctNm',
						name: 'settleAcctNm',
						allowBlank: false,
						blankText: '请输入商户结算帐户开户名'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户开户行同城清算号',
						id: 'openStlno',
						name: 'openStlno',
						allowBlank: false,
						blankText: '请输入商户开户行同城清算号'
		        	}]
		        }]
			},{
	        	columnWidth: .5,
	       		items: [{
	       		},{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算帐户开户行号',
						id: 'settleBankNo',
						name: 'settleBankNo',
						allowBlank: true
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算帐户号',
						id: 'settleAcct',
						name: 'settleAcct',
						allowBlank: false,
						blankText: '请输入商户结算帐户号'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户开户行同城交换号',
						id: 'changeStlno',
						name: 'changeStlno',
						allowBlank: false,
						blankText: '请输入商户开户行同城交换号'
		        	}]
		        }]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 40,
                	layout:'hbox',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
		        		labelWidth: 80,
	       				items: [{
			        		xtype: 'numberfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '计费代码',
							allowNegative: false,
							maxValue: 100000000,
							id: 'discCode',
							name: 'discCode',
							readOnly: true
		        		}]
					},{
		        		xtype: 'panel',
		        		layout: 'form',
		        		style: 'padding-left: 25px',
	       				items: [{xtype: 'button',
							iconCls: 'recover',
							text:'重置',
							id: 'resetbu',
							width: 60,
							handler: function(){
								Ext.getCmp('discCode').reset();
							}}]
					}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:460,
                	items: [detailGrid]
                }]
		    }],
		    listeners:{
		    	'tabchange':function(){
		    		if(hasSub){
			    		mchntForm.getForm().isValid();
		    		}
		    	}
		    }
    });
	
	var mchntForm = new Ext.FormPanel({
        title: '添加商户信息',
		region: 'center',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 140,
		waitMsgTarget: true,
		labelAlign: 'left',
        bodyStyle:'padding:5px',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: 1,
            	layout: 'form',
        		items: [{
        			xtype: 'radiogroup',
           			labelStyle: 'padding-left: 5px',
            		id: 'mchntTypeRadio',
            		name: 'mchntTypeRadio',
            		fieldLabel: '商户种类*',
            		allowBlank: false,
					blankText: '请选择商户种类',
            		items: [
            	   		{boxLabel: '普通商户', name: 'mchtGroupFlag', inputValue: 1},
            	    	{boxLabel: '二级商户', name: 'mchtGroupFlag', inputValue: 2}
            		],
            		listeners: {
						'change': function() {
							if(getRadioChecked("mchtGroupFlag") == '2'){
								Ext.getDom('groupBrh').style.display = ''; 
							}else{
								Ext.getDom('groupBrh').style.display = 'none'; 
							}
						}
					}
        		}]
        	},{
        		columnWidth: .5,
	        	id: 'groupBrh',
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_GROUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '集团商户*',
						id: 'idmchtGroupId',
						hiddenName: 'mchtGroupId'
		        	}]
	        }]},
//	        	{
//        		columnWidth: 1,
//            	layout: 'form',
//        		items: [{
//			        xtype: 'textfield',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '商户编号*',
//					allowBlank: false,
//					regex: /^[0-9]{15}$/,
//					regexText: '商户编号必须由15位数字组成',
//					id: 'mchntId',
//					name: 'mchntId',
//					readOnly: true,
//					disabled: true
//		        }]
//        	},
        	{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						blankText: '请选择商户组别',
						width: 400,
						id: 'idmchtGrp',
						hiddenName: 'mchtGrp',
						listeners: {
							'select': function() {
								mchntMccStore.removeAll();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',Ext.getCmp('idmchtGrp').getValue(),function(ret){
									mchntMccStore.loadData(Ext.decode(ret));
								});
							}
						}
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: false,
						lazyRender: true,
						width: 400,
						blankText: '请选择商户MCC',
						id: 'idmcc',
						hiddenName: 'mcc'
//						,
//			        	listeners: {
//			        		'select': function() {
//			        			if(getDomValue('mchntCity') != '' && getDomValue('mcc') != '') {
//			        				T20101.getMchntId('021' + getDomValue('mchntCity') + getDomValue('mcc'),function(ret){
//			        					Ext.getCmp('mchntId').setValue(ret);
//			        				});
//			        			}
//			        		}
//			        	}
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	layout: 'form',
					items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'CITY_CODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所在地区*',
						allowBlank: false,
						blankText: '请选择所在地区',
						id: 'idareaNo',
						hiddenName: 'areaNo'
//						,
//			        	listeners: {
//			        		'select': function() {
//			        			if(getDomValue('mchntCity') != '' && getDomValue('mcc') != '') {
//			        				T20101.getMchntId('021' + getDomValue('mchntCity') + getDomValue('mcc'),function(ret){
//			        					Ext.getCmp('mchntId').setValue(ret);
//			        				});
//			        			}
//			        		}
//			        	}
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'BRH_BELOW',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属分公司*',
						allowBlank: false,
						blankText: '请选择所属分公司',
						id: 'idacqInstId',
						hiddenName: 'acqInstId'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '中文名称*',
						allowBlank: false,
						blankText: '请输入商户中文名称',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'mchtNm',
						name: 'mchtNm'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '中文名称缩写',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'mchtNm1',
						name: 'mchtNm1'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '英文名称',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'engName',
						name: 'engName'
		        	},{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '拼音名称缩写',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'spellName',
						name: 'spellName'
		        	}]
	        	}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]	
        },tapPanel],
        buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mchntForm.getForm();
				hasSub = true;
				if (frm.isValid()) {
					if(1 != 1){
						//
					}else{
						btn.disable();
						frm.submit({
							url: 'T20101Action.asp?method=add',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessMsg(action.result.msg,mchntForm);
								btn.enable();
								frm.reset();
							},
							failure : function(form,action) {
								btn.enable();
								showErrorMsg(action.result.msg,mchntForm);
							},
							params: {
								txnId: '20101',
								subTxnId: '01'
							}
					});
				}
			}else{
//				var finded = true;
//				frm.items.each(function(f){
//           			if(finded && !f.validate()){
//           				alert(f.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.xtype);
//           				finded = false;
//           			}
//        		}
//        	);
			}}
        },{
            text: '重置',
            handler: function() {
            	hasSub = false;
				mchntForm.getForm().reset();
			}
        },{
            text: '取消',
            handler: function() {
				window.close();
			}
        }]
    });
        
    /**
	 * 动画显示时间选择控件
	 * @param direction 显示方向
	 */
	function showTimeWindow(direction) {
		timeWin.show();
		timeWin.getEl().slideIn(direction, {
		    easing: 'easeIn',
		    duration: .2
		});
	}
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
	
	Ext.getDom('groupBrh').style.display = 'none'; 

})

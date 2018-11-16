
//商户信息维护－－查看详细信息，在外部用函数封装
function auditMchnt4Add(mchntId,El){
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
	
	//图片显示Store
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	var storeImg1 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getZipInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
//		region: 'north',
        store: storeImg,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName={fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"><input type="button" id="{btDw}" value="下载"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	var dataview1 = new Ext.DataView({
		frame: true,
//		region: 'south',
        store: storeImg1,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div>',
                        	'<img id="{id}" width="120" height="90" src="../../ext/resources/images/zip.jpg" alt="{fileName}"/>',
							'<div style=""><input type="button" id="{btDw}" value="下载"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones1',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf4Add'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'manuAuthFlag',mapping: 'manuAuthFlag'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'discConsFlg',mapping: 'discConsFlg'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
			{name: 'openDays',mapping: 'openDays'},
			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name: 'areaNo',mapping: 'areaNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'homePage',mapping: 'homePage'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'tcc',mapping: 'tcc'},
			{name: 'etpsAttr',mapping: 'etpsAttr'},
			{name: 'mngMchtId',mapping: 'mngMchtId'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtAttr',mapping: 'mchtAttr'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'mchtGroupId',mapping: 'mchtGroupId'},
			{name: 'mchtEngNm',mapping: 'mchtEngNm'},
			{name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
			{name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'psamNum',mapping: 'psamNum'},
			{name: 'cdMacNum',mapping: 'cdMacNum'},
			{name: 'posNum',mapping: 'posNum'},
			{name: 'connType',mapping: 'connType'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'mchtFunction',mapping: 'mchtFunction'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			
			{name: 'isSyncretic',mapping: 'isSyncretic'},
			{name: 'uscCode',mapping: 'uscCode'},
//			{name: 'licenceNo',mapping: 'licenceNo'},
//			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
//			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'organizationType',mapping: 'organizationType'},
		
			
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'enableDate',mapping: 'enableDate'},
			{name: 'preAudNm',mapping: 'preAudNm'},
			{name: 'confirmNm',mapping: 'confirmNm'},
			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
			{name: 'netTel',mapping: 'netTel'},
			{name: 'prolDate',mapping: 'prolDate'},
			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
			{name: 'operNo',mapping: 'operNo'},
			{name: 'operNm',mapping: 'operNm'},
			{name: 'procFlag',mapping: 'procFlag'},
			{name: 'setCur',mapping: 'setCur'},
	      	{name: 'printInstId',mapping: 'printInstId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'acqBkName',mapping: 'acqBkName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'orgnNo',mapping: 'orgnNo'},
			{name: 'subbrhNo',mapping: 'subbrhNo'},
			{name: 'subbrhNm',mapping: 'subbrhNm'},
			{name: 'openTime',mapping: 'openTime'},
			{name: 'closeTime',mapping: 'closeTime'},
			{name: 'visActFlg',mapping: 'visActFlg'},
			{name: 'visMchtId',mapping: 'visMchtId'},
			{name: 'mstActFlg',mapping: 'mstActFlg'},
			{name: 'mstMchtId',mapping: 'mstMchtId'},
			{name: 'amxActFlg',mapping: 'amxActFlg'},
			{name: 'amxMchtId',mapping: 'amxMchtId'},
			{name: 'dnrActFlg',mapping: 'dnrActFlg'},
			{name: 'dnrMchtId',mapping: 'dnrMchtId'},
			{name: 'jcbActFlg',mapping: 'jcbActFlg'},
			{name: 'jcbMchtId',mapping: 'jcbMchtId'},
			{name: 'cupMchtFlg',mapping: 'cupMchtFlg'},
			{name: 'debMchtFlg',mapping: 'debMchtFlg'},
			{name: 'creMchtFlg',mapping: 'creMchtFlg'},
			{name: 'cdcMchtFlg',mapping: 'cdcMchtFlg'},
			{name: 'idOtherNo',mapping: 'idOtherNo'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'internalNo',mapping: 'internalNo'},
			{name: 'reject',mapping: 'reject'},
			{name: 'mchntAttr',mapping: 'mchntAttr'},
			{name: 'linkPer',mapping: 'linkPer'},
			{name: 'SettleAreaNo',mapping: 'SettleAreaNo'},
			{name: 'MainTlr',mapping: 'MainTlr'},
			{name: 'CheckTlr',mapping: 'CheckTlr'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'rateFlag',mapping: 'rateFlag'},
			{name: 'settleChn',mapping: 'settleChn'},
			{name: 'batTime',mapping: 'batTime'},
			{name: 'autoStlFlg',mapping: 'autoStlFlg'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeFixed',mapping: 'feeFixed'},
			{name: 'feeMaxAmt',mapping: 'feeMaxAmt'},
			{name: 'feeMinAmt',mapping: 'feeMinAmt'},
			{name: 'discCode',mapping: 'feeRate'},
			{name: 'feeDiv1',mapping: 'feeDiv1'},
			{name: 'feeDiv2',mapping: 'feeDiv2'},
			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'clearType',mapping: 'clearType'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
			{name: 'changeStlno',mapping: 'changeStlno'},
			{name: 'speSettleTp',mapping: 'speSettleTp'},
			{name: 'speSettleLv',mapping: 'speSettleLv'},
			{name: 'speSettleDs',mapping: 'speSettleDs'},
			{name: 'feeBackFlg',mapping: 'feeBackFlg'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'mchtNoHx',mapping: 'mchtNoHx'}, //北京商户号  
			{name: 'mchtSXNoHx',mapping: 'mchtSXNoHx'}, //随行付商户号
			{name: 'engNameAbbr',mapping: 'engNameAbbr'},
			{name:'tmpNo',mapping:'tmpNo'},
			{name:'depart',mapping:'depart'},
			{name:'nationality',mapping:'nationality'},
			{name:'foreignName',mapping:'foreignName'},
			{name:'busScope',mapping:'busScope'},
			{name:'busMain',mapping:'busMain'},
			{name:'mchtNoOld',mapping:'mchtNoOld'},
			{name:'mchtTypeCause',mapping:'mchtTypeCause'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'province',mapping:'province'},
			{name:'city',mapping:'city'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'area',mapping:'area'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'proxy',mapping:'proxy'},
			{name:'finManager',mapping:'finManager'},
			{name:'finPhone',mapping:'finPhone'},
			{name:'finTel',mapping:'finTel'},
			{name:'finFax',mapping:'finFax'},
			{name:'finEmail',mapping:'finEmail'},
			{name:'shopNum',mapping:'shopNum'},
			{name:'busArea',mapping:'busArea'},
			{name:'busZone',mapping:'busZone'},
			{name:'acreage',mapping:'acreage'},
			{name:'areaType',mapping:'areaType'},
			{name:'empNum',mapping:'empNum'},
			{name:'cashierNum',mapping:'cashierNum'},
			{name:'cashierDeskNum',mapping:'cashierDeskNum'},
			{name:'turnoverBefore',mapping:'turnoverBefore'},
			{name:'turnover',mapping:'turnover'},
			{name:'openDate',mapping:'openDate'},
			{name:'serLvl',mapping:'serLvl'},
			{name:'src',mapping:'src'},
			{name:'expander',mapping:'expander'},
			{name:'referrer',mapping:'referrer'},
			{name:'isWineshop',mapping:'isWineshop'},
			{name:'wineshopLvl',mapping:'wineshopLvl'},
			{name:'isMoreAcq',mapping:'isMoreAcq'},
			{name:'isAppOutSide',mapping:'isAppOutSide'},
			{name:'hasInnerPosExp',mapping:'hasInnerPosExp'},
			{name:'mcc2',mapping:'mcc2'},
			{name:'hasOurPosExp',mapping:'hasOurPosExp'},
			{name:'linkTel',mapping:'linkTel'},
			{name:'proxyTel',mapping:'proxyTel'},
			{name:'serType',mapping:'serType'},
			{name:'signInstId',mapping:'signInstId'},
			//====By Mike====用于显示“商户评估信息”页面的值==================
			{name:'feeDiffer',mapping:'feeDiffer'},
			{name:'managerYears',mapping:'managerYears'},
			{name:'singleAmt',mapping:'singleAmt'},
			{name:'monthTotalAmt',mapping:'monthTotalAmt'},
			{name:'evalWayFlag',mapping:'evalWayFlag'},
			{name:'evalLevel',mapping:'evalLevel'},
			{name:'evalEndTime',mapping:'evalEndTime'},
			//====By Mike======================
			
			//基本信息-2 by zhangqy
			{name: 'engName',mapping: 'engName'},
			{name: 'busiRange',mapping: 'busiRange'},
			{name: 'postalCode',mapping: 'postalCode'},
			{name: 'insAddr',mapping: 'insAddr'},
			{name: 'belInd',mapping: 'belInd'},
			{name: 'ownerBusi',mapping: 'ownerBusi'},
			{name: 'ICPrecordNo',mapping: 'ICPrecordNo'},
			{name: 'ICPcompName',mapping: 'ICPcompName'},
			{name: 'mailAddr',mapping: 'mailAddr'},
			{name: 'contRate',mapping: 'contRate'},
			{name: 'sinDebAmount',mapping: 'sinDebAmount'},
			{name: 'dayDebAmount',mapping: 'dayDebAmount'},
			{name: 'monDebAmount',mapping: 'monDebAmount'},
			{name: 'sinCreAmount',mapping: 'sinCreAmount'},
			{name: 'dayCreAmount',mapping: 'dayCreAmount'},
			{name: 'monCreAmount',mapping: 'monCreAmount'},
			{name: 'contName',mapping: 'contName'},
			{name: 'contTel',mapping: 'contTel'},
			{name: 'busiTel',mapping: 'busiTel'},
			{name: 'appComm',mapping: 'appComm'},
			{name: 'agentName',mapping: 'agentName'},
			{name: 'agentType',mapping: 'agentType'},
			{name: 'regionBel',mapping: 'regionBel'},
			{name: 'agentNo',mapping: 'agentNo'},
			
			{name: 'uscCodeDate',mapping: 'uscCodeDate'},
			{name: 'licenceNoDate',mapping: 'licenceNoDate'},
			{name: 'shareholder',mapping: 'shareholder'},
			{name: 'idshareholderTpmd',mapping: 'idshareholderTpmd'},
			{name: 'idshareholder',mapping: 'idshareholder'},
			{name: 'shareholderDate',mapping: 'shareholderDate'},
			{name: 'identityNoDate',mapping: 'identityNoDate'},
			{name: 'contactmd',mapping: 'contactmd'},
			{name: 'linkTelDate',mapping: 'linkTelDate'},
			{name: 'operateAddr',mapping: 'operateAddr'},
			
			//附加信息
			{name:'preAuthor',mapping:'preAuthor'},
			{name:'returnFunc',mapping:'returnFunc'},
			
			//关键字段检查信息
			{name: 'ckMchtNm',mapping: 'ckMchtNm'},
			{name: 'ckLicenceNo',mapping: 'ckLicenceNo'},
			{name: 'ckIdentityNo',mapping: 'ckIdentityNo'},
			{name: 'ckBankLicenceNo',mapping: 'ckBankLicenceNo'},
			{name: 'ckManagerTel',mapping: 'ckManagerTel'},
			{name: 'ckAddr',mapping: 'ckAddr'},
			{name: 'ckMCC',mapping: 'ckMCC'},
			
			//清算信息
			{name: 'currAccount',mapping: 'currAccount'},
			{name: 'compAccountBankCode',mapping: 'compAccountBankCode'},
			{name: 'compAccountBankName',mapping: 'compAccountBankName'},
			{name: 'bankAccountCode',mapping: 'bankAccountCode'},
			{name: 'corpBankName',mapping: 'corpBankName'},
			{name: 'dirFlag',mapping: 'dirFlag'},
			{name: 'dirBankCode',mapping: 'dirBankCode'},
			{name: 'dirBankName',mapping: 'dirBankName'},
			{name: 'dirAccountName',mapping: 'dirAccountName'},
			{name: 'dirAccount',mapping: 'dirAccount'},
			{name: 'autoFlag',mapping: 'autoFlag'},
			{name: 'holidaySetFlag',mapping: 'holidaySetFlag'},
			{name: 'creFlag',mapping: 'creFlag'},
			{name: 'returnFeeFlag',mapping: 'returnFeeFlag'},
			{name: 'autoFlag',mapping: 'autoFlag'},
			{name: 'legalNam',mapping: 'legalNam'},
			{name: 'companyNam',mapping: 'companyNam'},
			//add
			{name: 'dirOpenBank',mapping: 'dirOpenBank'},
			{name: 'dirBankProvince',mapping: 'dirBankProvince'},
			{name: 'dirBankCity',mapping: 'dirBankCity'},
			{name: 'compOpenBank',mapping: 'compOpenBank'},
			{name: 'compBankProvince',mapping: 'compBankProvince'},
			{name: 'compBankCity',mapping: 'compBankCity'},
			{name: 'corpOpenBank',mapping: 'corpOpenBank'},
			{name: 'corpBankProvince',mapping: 'corpBankProvince'},
			{name: 'corpBankCity',mapping: 'corpBankCity'},
			
			{name:'mchntInd',mapping:'mchntInd'}
			
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
	
	var feeStore = new Ext.data.Store({
	
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfAlgo2'
		}),
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
		}),
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
		}]
	});
	
	feeStore.on('beforeload',function(){//20120924解决商户信息维护之查看详细信息页面中点击费率信息分页中刷新按钮信息会清空的bug
		Ext.apply(this.baseParams, {
			start: 0,
			tmpNo: Ext.getCmp('tmpNo').getValue(),
			termId:Ext.getCmp('term_ID').getValue()
		});
	});
	
	
/*****************************************受益人信息*****************************************/
	
	//受益人信息 store
	var feeStore1 = new Ext.data.Store({
		//proxy:用于从某个途径读取原始数据
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=TblMchtBeneficiaryInfTmp'
		}),
		//reader:用于将原始数据转换成Record实例
		reader: new Ext.data.JsonReader({
			//root:表示当前页显示信息队列
			root: 'data',
			//totalProperty:表示后台数据的记录总数
			totalProperty: 'totalCount'
			//idProperty: 'beneficiaryId'
		},[
			{name: 'beneficiaryId',mapping: 'beneficiaryId'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'beneficiaryName',mapping: 'beneficiaryName'},
			{name: 'beneficiaryAddress',mapping: 'beneficiaryAddress'},
			{name: 'beneficiaryIdType',mapping: 'beneficiaryIdType'},
			{name: 'beneficiaryIdCard',mapping: 'beneficiaryIdCard'},
			{name: 'beneficiaryExpiration',mapping: 'beneficiaryExpiration'},
		]),
		autoLoad: false
		//对数据进行排序
		//field:排序的字段                 direction:排序的方式
	 	//sortInfo: {field: 'name', direction: 'DESC'}  
	});
	
	//受益人信息 列表
	var feeColumnModel1 = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '受益人编号',dataIndex: 'beneficiaryId',sortable: true,hidden:true,width:100},
		{header: '商户编号',dataIndex: 'mchtNo',sortable: true,hidden:true,width:100},
		{header: '受益人姓名',dataIndex: 'beneficiaryName',sortable: true,width:100},
		{header: '地址',dataIndex: 'beneficiaryAddress',sortable: true,width:100},
		{header: '身份证件种类',dataIndex: 'beneficiaryIdType',renderer: beneficiaryTypeVal,sortable: true,width:100},
		{header: '号码',dataIndex: 'beneficiaryIdCard',sortable: true,width:100},
		{header: '有效期限',dataIndex: 'beneficiaryExpiration',sortable: true,width:100},
	]);
	
	function beneficiaryTypeVal(value){
		if(value=='1'){
			return '身份证';
		}else if(value=='2'){
			return '护照';
		}
	}
	
	//受益人信息 表格
	var feeGridPanel1 = new Ext.grid.GridPanel({
		title: '受益人信息',
		frame: true,
		border: true,
		height: 190,
		columnLines: true,
		stripeRows: true,
		store: feeStore1,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel1,
		forceValidation: true,
		loadMask: {
			msg: '正在加载受益人信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: feeStore1,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: []
	});
	
	feeStore1.load({
		params:{
			start: 0,
			mchtNo: mchntId
		}
	});
	
	
	
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
				mchntInd: Ext.getCmp('mchntInd').getValue()
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
			{
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
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly',
	       			baseParams: 'MCHNT_GRP_ALL',
					fieldLabel: '商户名称',
					labelStyle: 'padding-left: 5px',
					id: 'mchtNm',
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
						id:'mchtGrp',
						hiddenName: 'mchtGrp',
						anchor: '90%'
		        	}]
			},{
			    columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
//			        	xtype: 'displayfield',
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
						id: 'mchtCnAbbr'
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
						id: 'etpsAttr',
						hiddenName: 'etpsAttr'
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
						id: 'regAddr',
						hiddenName: 'regAddr'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '经营地址',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20171220修改
						id: 'operateAddr',
						hiddenName: 'operateAddr'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				       xtype: 'displayfield',
						fieldLabel: '邮政编号',
						labelStyle: 'padding-left: 5px',
						id: 'postalCode',
						hiddenName: 'postalCode'
				}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '电子邮件',
						labelStyle: 'padding-left: 5px',
						id: 'commEmail',
						hiddenName: 'commEmail'
					}]
				},{
					columnWidth: .33,
			       	xtype: 'panel',
			       	layout: 'form',
					items: [{
			        xtype: 'combofordispaly',
					fieldLabel: '是否三证合一',
					labelStyle: 'padding-left: 5px',
					id: 'idSyncretic',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','是'],['1','否']],
						reader: new Ext.data.ArrayReader()
					}),
					hiddenName: 'isSyncretic'
					}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '统一社会信用代码',
						labelStyle: 'padding-left: 5px',
						id: 'uscCode'
		        	}]
				}/*,{
	        		columnWidth: .33,
		        	xtype: 'panel',
					id: 'idUscCodeDate',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
					    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '统一社会信用代码证有效期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
						id: 'uscCodeDate'
		        	}]
				}*/,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						labelStyle: 'padding-left: 5px',
						id: 'licenceNo'
		        	}]
				}/*,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idLicenceNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'licenceNoDate'
		        	}]
				}*/,{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业组织机构代码',
						labelStyle: 'padding-left: 5px',
						id: 'bankLicenceNo',
						hiddenName: 'bankLicenceNo'
				}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						labelStyle: 'padding-left: 5px',
						id: 'faxNo',
						hiddenName: 'faxNo'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '经营范围',
						labelStyle: 'padding-left: 5px',
						id: 'busiRange',
						hiddenName: 'busiRange'
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
						id: 'busMain',
						name: 'busMain'
	       			}]
                },{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '所属行业',
						labelStyle: 'padding-left: 5px',
						id: 'belInd',
						hiddenName: 'belInd'
						}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						hiddenName: 'applyDate',
						labelStyle: 'padding-left: 5px',
						id: 'applyDate',
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
						id: 'busAmt',
						hiddenName: 'busAmt'
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
	                    hiddenName: 'province',
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
	                    id:'idcity',
	                    hiddenName: 'city',
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
	                    id:'idareaD',
	                    hiddenName:'area',
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
						name: 'acreage',
						id: 'acreage',
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
//						methodName: 'getOwnerBusi',
						baseParams: 'OWNER_BUSI',
						labelStyle: 'padding-left: 5px',
						id: 'idOwnerBusi',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','自有'],['2','租赁']],
							reader: new Ext.data.ArrayReader()
						}),
						hiddenName: 'ownerBusi'
	        			
	       				
	       			}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '员工人数',
						name: 'empNum',
						id: 'empNum',
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
						id: 'homePage',
						hiddenName: 'homePage'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案号',
						labelStyle: 'padding-left: 5px',
						id: 'ICPrecordNo',
						hiddenName: 'ICPrecordNo'
						}]				
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案公司名称',
						labelStyle: 'padding-left: 5px',
						id: 'ICPcompName',
						hiddenName: 'ICPcompName'
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
						id: 'contName',
						hiddenName: 'contName'
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
						id: 'contTel',
						hiddenName: 'contTel'
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
						id: 'busiTel',
						hiddenName: 'busiTel'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业传真',
						labelStyle: 'padding-left: 5px',
						id: 'fax',
						hiddenName: 'fax'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '法定代表人电话',
						labelStyle: 'padding-left: 5px',
						id: 'managerTel',
						hiddenName: 'managerTel'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '联系人电话',
						labelStyle: 'padding-left: 5px',
						id: 'commTel',
						hiddenName: 'commTel'
					}]
					
				},{
					columnWidth: .33,
			       	xtype: 'panel',
			       	layout: 'form',
					items: [{
			        xtype: 'combofordispaly',
					fieldLabel: '商户级别',
					labelStyle: 'padding-left: 5px',
					id: 'idRislLvl',
					store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','正常商户'],['1','中级风险商户'],['2','高级风险商户'],['3','复审商户']],
					reader: new Ext.data.ArrayReader()
					}),
					hiddenName: 'rislLvl'
					}]
					},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户机构类型',
						id: 'idOrganizationType',
						hiddenName: 'organizationType',
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['2A','2A:农、林、牧、渔业'],['2B','2B:采矿业'],['2C','2C:制造业'],['2D','2D:电力、燃气及水的生产和供应业'],['2E','2E:建筑业'],['2F','2F:交通运输、仓储和邮政业'],['2G','2G:信息传输、计算机服务和软件业'],['2F','2F:交通运输、仓储和邮政业'],['2H','2H:批发和零售业'],['2I','2I:住宿和餐饮业'],['2J','2J:银行业'],['2K','2K:房地产业'],['2L','2L:租赁和商务服务业'],['2M','2M:科学研究、技术服务和地质勘查业'],['2N','2N:水利、环境和公共设施管理业'],['2O','2O:居民服务和其他服务业'],['2P','2P:教育'],['2Q','2Q:卫生、社会保障和社会福利业'],['2R','2R:文化、体育和娱乐业'],['2S','2S:公共管理和社会组织'],['2T','2T:国际组织']]
    	                })
		        	}]	
				}
					]
            },{
            	title:'基本信息扩充',
                id: 'standardConfig',
                frame: true,
                autoScroll: true,
				layout:'column',
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '控股股东或实际控制人姓名*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'shareholder',
							disabled: true,
							name: 'shareholder'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'ididshareholder',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATES',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件种类*',
						width:130,
						allowBlank: false,
						id:'idshareholderTp',
						disabled: true,
						hiddenName: 'idshareholderTpmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件号码*',
						allowBlank: false,
						maxLength: 25,
						vtype: 'is5Alphanum',
						disabled: true,
						id: 'idshareholder'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idshareholderDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件有效期*',
						maxLength: 8,
						minLength:8,  
						disabled: true,
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'shareholderDate'
		        	}]
				},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'COUNTRYS',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人国籍*',
						allowBlank: false,
						width:130,
						disabled: true,
						hiddenName: 'nationality',
						listeners: {
    					'select': function() {
							  var flag = mchntForm.getForm().findField('nationality').getValue();
							/*  if(flag=='86'){//中国国籍 则证件类型为身份证
							  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
							  	Ext.getCmp('idartifCertifTp').readOnly=true;
							  }else{//非中国国籍的则为 护照
							  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
							  	Ext.getCmp('idartifCertifTp').readOnly=false;
							  }*/
							  if(flag!='800'){//非外籍 则证件类型为身份证
								  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
								  	Ext.getCmp('idartifCertifTp').readOnly=true;
								  	//联系人授权人非必填
								  	Ext.getCmp('contact').allowBlank=false;
								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
								  	Ext.getCmp('linkTel').allowBlank=false;
								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证件号*');
								  	
								  }else{//非中国国籍的则为 护照
								  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
								  	Ext.getCmp('idartifCertifTp').readOnly=false;
								  //联系人授权人必填
								  	Ext.getCmp('contact').allowBlank=false;
								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
								  	Ext.getCmp('linkTel').allowBlank=false;
								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证件号*');
								  }
    					}
    				}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       					labelStyle: 'padding-left: 5px',
							xtype:'dynamicCombo',
							fieldLabel: '外籍名*',
							width:130,
							allowBlank: false,
							disabled: true,
							methodName: 'getForeignName',
							id: 'idForeignName',
							hiddenName: 'foreignName'
			        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textnotnull',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法定代表人/负责人*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'manager',
							disabled: true,
							name: 'manager'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件类型*',
						width:130,
						allowBlank: false,
						id:'idartifCertifTp',
						disabled: true,
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码*',
						maxLength: 18,
						disabled: true,
						vtype: 'is5Alphanum',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'ididentityNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
		                disabled: true,
						id: 'identityNoDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人姓名*',
						maxLength: 30,
						disabled: true,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'ididcontact',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人证件类型*',
						width:130,
						allowBlank: false,
						id:'idcontact',
						disabled: true,
						hiddenName: 'contactmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
	       				allowBlank: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证件号',
						maxLength: 20,
						vtype: 'is7Alphanum',
						disabled: true,
						id: 'linkTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证件号有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
		                disabled: true,
						id: 'linkTelDate'
		        	}]
				}
				]
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
						id: 'sinDebAmount',
						hiddenName: 'sinDebAmount'
					}]
					
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单日借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						id: 'dayDebAmount',
						hiddenName: 'dayDebAmount'
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
						id: 'monDebAmount',
						hiddenName: 'monDebAmount'
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
						id: 'sinCreAmount',
						hiddenName: 'sinCreAmount'
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
						id: 'dayCreAmount',
						hiddenName: 'dayCreAmount'
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
						id: 'monCreAmount',
						hiddenName: 'monCreAmount'
					}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预授权功能',
						id: 'preAuthor',
						name: 'preAuthor',
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
					id: 'returnFunc',
					name: 'returnFunc',
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
				name: 'netTel',
				id: 'netTel',
			 	allowBlank:false,
				maxLength:50,
				width:130
        	}]
		},{
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
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '商户营业开始时间',
				id: 'openTime',
				name: 'openTime',
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
				id: 'closeTime',
				name: 'closeTime',
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
		},{
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
				id: 'hasInnerPosExp',
				name: 'hasInnerPosExp',
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
				id: 'hasOurPosExp',
				name: 'hasOurPosExp',
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
			id: 'isAppOutSide',
			name: 'isAppOutSide',
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
						id: 'appComm',
						hiddenName: 'appComm'
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
					hiddenName: 'settleRpt'
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
					id: 'compAccountBankName'
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
					id: 'compAccountBankCode'
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
					id: 'settleAcct'
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
					id: 'corpBankName'
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
					id: 'bankAccountCode'
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
					id: 'feeAcct'
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
					id: 'dirBankName'
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
					id: 'dirBankCode'
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
					id: 'dirAccountName'
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
					id: 'dirAccount'
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
						id: 'autoFlag',
						name: 'autoFlag',
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
						id: 'holidaySetFlag',
						name: 'holidaySetFlag',
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
						id: 'creFlag',
						name: 'creFlag',
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
						id: 'returnFeeFlag',
						name: 'returnFeeFlag',
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
					id: 'settleChn'
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
						hiddenName: 'settleType',
						width: 165,
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对私账户名称',
						labelStyle: 'padding-left: 5px',
						width:150,
						id: 'legalNam'
					}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对公账户名称',
						labelStyle: 'padding-left: 5px',
						width:150,
						id: 'companyNam'
					}]
				}
				//add
				,{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					disabled:true,
				    xtype: 'dynamicCombo_1',
					methodName: 'getdirOpenBank',
					fieldLabel: '定向委托账号开户总行名称*',
					allowBlank: false,
					width:150,
					hiddenName: 'dir_open_bank',
					id:'dirOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					disabled:true,
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankProvince',
					fieldLabel: '定向委托开户行所在省*',
					allowBlank: false,
					width:150,
					hiddenName: 'dir_bank_province',
					id:'dirBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					disabled:true,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankCity',
					allowBlank: false,
					fieldLabel: '定向委托开户行所在市*',
					width:150,
					hiddenName: 'dir_bank_city',
					id:'dirBankCity'
		        	}]
				},{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					disabled:true,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					methodName: 'getOpenBank',
					fieldLabel: '对公账号开户总行名称*',
					width:150,
					hiddenName: 'comp_open_bank',
					id:'compOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					disabled:true,
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankProvince',
					allowBlank: false,
					fieldLabel: '对公账号开户行所在省*',
					width:150,
					hiddenName: 'comp_bank_province',
					id:'compBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					disabled:true,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankCity',
					allowBlank: false,
					fieldLabel: '对公账号开户行所在省*',
					width:150,
					hiddenName: 'comp_bank_city',
					id:'compBankCity'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					disabled:true,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					methodName: 'getCorpOpenBank',
					fieldLabel: '对私账号开户总行名称*',
					width:150,
					hiddenName: 'corp_open_bank',
					id:'corpOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					disabled:true,
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankProvince',
					allowBlank: false,
					fieldLabel: '对私账号开户行所在省*',
					width:150,
					hiddenName: 'corp_bank_province',
					id:'corpBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
					disabled:true,
					columnWidth: .33,
					allowBlank: false,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankCity',
					fieldLabel: '对私账号开户行所在市',
					width:150,
					hiddenName: 'corp_bank_city',
					id:'corpBankCity'
		        	}]
				}
				/*,{
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
                	region: 'center',
                	autoScroll: true,
        			items : [dataview,dataview1],
        			frame: true,
        			tbar: [{
        				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view',
						handler:function() {
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
							});
						}
					}]
                }]
		    },{
		    	title:'受益人信息',
                id: 'settle2',
                frame: true,
				layout:'column',
                items: [{
					region: 'center',
					items:[feeGridPanel1]
        		}]
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
     							id: 'mchntInd',
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
     					}/*,{
     		        		columnWidth: .33,
     			        	xtype: 'panel',
//     			        	labelWidth: 70,
     			        	layout: 'form',
//     			        	hidden:true,
     		       			items: [{
     							xtype: 'textfield',
     							labelStyle: 'padding-left: 5px',
     							fieldLabel: '贷记卡单笔交易限额',
//     							allowBlank: false,
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
     							fieldLabel: '贷记卡单日交易金额',
//     							allowBlank: false,
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
     							fieldLabel: '贷记卡单月交易金额',
//     							allowBlank: false,
     							blankText: '单日交易金额不能为空',
//     							emptyText: '请输入单月交易金额',
     							maxLength: 15,
     							maskRe: /^[0-9\\.]+$/,
     							vtype: 'isOverMax',
     							id: 'monAmtCredit',
     							name: 'monAmtCredit',
     							vtype: 'isMoney',
     							width:150
     			        	}*/,{
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
				url: 'T20201Action.asp?method=refuse',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: mchntId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
					txnId: '20201',
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
		 hideProcessMsg();
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
				url: 'T20201Action.asp?method=back',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: mchntId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
					txnId: '20201',
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
		 hideProcessMsg();
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
            	showErrorMsg("贷记卡单日交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            var dayAmtCredit = mchntForm.getForm().findField('dayAmtCredit').getValue();
            if(dayAmtCredit.indexOf('.') == -1 && dayAmtCredit.length>13){//20121115
            	showErrorMsg("贷记卡单笔交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
           var monAmtCredit = mchntForm.getForm().findField('monAmtCredit').getValue();
           if(monAmtCredit.indexOf('.') == -1 && monAmtCredit.length>13){//20121115
            	showErrorMsg("贷记卡单月交易金额整数部分不能超过13位，请重新填写",mchntForm);
                return;
            }
            if(Number(dayAmtCredit) < Number(daySingleCredit))
            {
                showErrorMsg("[贷记卡单日交易金额]不能小于[贷记卡单笔交易金额]",mchntForm);
                return;
            }
           if(Number(monAmtCredit) < Number(dayAmtCredit))
            {
                showErrorMsg("[贷记卡单月累计交易金额]不能小于[贷记卡单日交易金额]",mchntForm);
                return;
            }
           
           mchntForm.getForm().submit({
				url: 'T20201Action.asp?method=acceptAdd',
				waitMsg: '正在审核商户信息，请稍候......',
				params: {
					mchntId: mchntId,
//					oprInfo: Ext.getCmp('oprInfo').getValue(),
//					mchntInd:Ext.getCmp('mchntInd').getValue(),
					txnId: '20201',
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
           hideProcessMsg();
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
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
	 
				var feeTypeValue = baseStore.getAt(0).data.feeType;
 
				var settleType=baseStore.getAt(0).data.settleType;
				/*if(settleType>'1'){
					Ext.getCmp('feeCycleID').show();
					Ext.getCmp('feeCycle').show();
				}*/
				if(baseStore.getAt(0).data.ckLicenceNo=="1"){
					Ext.getCmp('licenceNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckLicenceNo=="2"){
					Ext.getCmp('licenceNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckLicenceNo=="3"){
					Ext.getCmp('licenceNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				if(baseStore.getAt(0).data.ckIdentityNo=="1"){
					Ext.getCmp('identityNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckIdentityNo=="2"){
					Ext.getCmp('identityNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckIdentityNo=="3"){
					Ext.getCmp('identityNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				if(baseStore.getAt(0).data.ckBankLicenceNo=="1"){
					Ext.getCmp('bankLicenceNo').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckBankLicenceNo=="2"){
					Ext.getCmp('bankLicenceNo').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckBankLicenceNo=="3"){
					Ext.getCmp('bankLicenceNo').labelStyle='padding-left: 5px;color:yellow;';
				}
				
				if(baseStore.getAt(0).data.ckManagerTel=="1"){
					Ext.getCmp('managerTel').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckManagerTel=="2"){
					Ext.getCmp('managerTel').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckManagerTel=="3"){
					Ext.getCmp('managerTel').labelStyle='padding-left: 5px;color:yellow;';
				}
				
				/*if(baseStore.getAt(0).data.ckAddr=="1"){
					Ext.getCmp('addr').labelStyle='padding-left: 5px;color:red;';
				}*/
				if(baseStore.getAt(0).data.ckMchtNm=="1"){
					Ext.getCmp('mchtNm').labelStyle='padding-left: 5px;color:red;';
				}else if(baseStore.getAt(0).data.ckMchtNm=="2"){
					Ext.getCmp('mchtNm').labelStyle='padding-left: 5px;color:green;';
				}else if(baseStore.getAt(0).data.ckMchtNm=="3"){
					Ext.getCmp('mchtNm').labelStyle='padding-left: 5px;color:orange;';
				}
				if(baseStore.getAt(0).data.ckMCC=="1"){
					Ext.getCmp('mcc').labelStyle='padding-left: 5px;background-color:yellow;';
				}
				detailWin.setTitle('商户详细信息[商户编号：' + mchntId + ']');
				detailWin.show();

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					mchntForm.getForm().findField('mcc').setValue(baseStore.getAt(0).data.mcc);
				});
				//初始化省、市、县信息
                SelectOptionsDWR.getComboDataWithParameter('CITIES',baseStore.getAt(0).data.province,
						function(ret){
							citiesStore.loadData(Ext.decode(ret));
							Ext.getCmp('idcity').setValue(baseStore.getAt(0).data.city);
						});
								
                SelectOptionsDWR.getComboDataWithParameter('AREAS',baseStore.getAt(0).data.city,
						function(ret){
							areasStore.loadData(Ext.decode(ret));
							//mchntForm.getForm().findField('idareaD').setValue(baseStore.getAt(0).data.area);
							Ext.getCmp('idareaD').setValue(baseStore.getAt(0).data.area);
						});
				//加载商户费率信息
				feeStore.reload({
					params:{
						start:0,
						tmpNo: baseStore.getAt(0).data.tmpNo
					}
				});
				
				//加载商户操作信息
				auditStore.reload({
					params:{
						start:0,
						mchntInd: baseStore.getAt(0).data.mchntInd
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

				storeImg.on('load',function(){
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
				});		
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}
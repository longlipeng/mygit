Ext.onReady(function() {
	var verifyResult = true;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	//分公司数据集
	var fcompanyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_NORMAL',function(ret){
		fcompanyStore.loadData(Ext.decode(ret));
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
	
	//行政区域码据集
	var xingzhengStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('XINGZHENG',function(ret){
		xingzhengStore.loadData(Ext.decode(ret));
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
	
	//******************图片处理部分**********开始********
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
	var custom = new Ext.Resizable('showBigPic', {
		    wrap:true,
		    pinned:true,
		    minWidth: 50,
		    minHeight: 50,
		    preserveRatio: true,
		    dynamic:true,
		    handles: 'all',
		    draggable:true
		});
	var customEl = custom.getEl();
	document.body.insertBefore(customEl.dom, document.body.firstChild);
	customEl.on('dblclick', function(){
		customEl.puff();
	});
	customEl.hide(true);
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
 	function delPIC(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(5)).data;
		 		T20100.deleteImgFile(rec.fileName,function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	storeImg.on('load',function(){
		for(var i=0;i<storeImg.getCount();i++){
			var rec = storeImg.getAt(i).data;
        	Ext.get(rec.btBig).on('click', function(obj,val){
        		showPIC(val.id);
        	});
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC(val.id);
        	});
		}
	});
	
	storeImg1.on('load',function(){
		for(var i=0;i<storeImg1.getCount();i++){
			var rec = storeImg1.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC1(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC1(val.id);
        	});
		}
	});
	
	function delPIC1(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg1.getAt(id.substring(6)).data;
		 		T20100.deleteImgFile(rec.fileName,function(ret){
		 			if("S" == ret){
		 				storeImg1.reload({
							params: {
								start: 0,
								imagesId: imagesId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
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

	var clearTypeStore = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: [['A','本行结算账户1'],['P','本行结算账户2(S)或单位卡'],['O','他行结算账户1'],['S','他行结算账户2(S)']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore1 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行结算账户1'],['P','本行结算账户2(S)或单位卡'],['O','他行结算账户1'],['S','他行结算账户2(S)']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['P','本行结算账户2(S)或单位卡'],['S','他行结算账户2']],
		reader: new Ext.data.ArrayReader()
	});
	
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
							'<div style=""><input type="button" id="{btBig}" value="放大"><input type="button" id="{btDw}" value="下载"><input type="button" id="{btDel}" value="删除"></div>',
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
							'<div style=""><input type="button" id="{btDw}" value="下载">&nbsp;<input type="button" id="{btDel}" value="删除"></div>',
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


     var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	
    // 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20100Action_upload.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.jpg;*.png;*.jpeg',
		fileTypesDescription : '图片文件',
		title: '证书影印文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId
				}
			});
			storeImg1.reload({
				params: {
					start: 0,
					imagesId: imagesId
				}
			});
		},
		postParams: {
			txnId: '20101',
			subTxnId: '06',
			imagesId: imagesId
		}
	});
	
	

	//******************图片处理部分**********结束********

    //******************计费算法部分**********开始********
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

	var hasSub = false;
	var hasUpload = "0";
	var fm = Ext.form;
	//费率算法详细信息 store
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
		autoLoad: false
	});
	//费率算法详细信息 列表
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
	});
	
	//费率算法详细信息 表格
    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		//autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//费率算法 store
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
	gridStore.load({
		params: {
			start: 0
		}
	});
	//费率算法 列表
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 60},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	
	//商户费率信息 store
	var feeStore = new Ext.data.Store({
		//proxy:用于从某个途径读取原始数据
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfAlgo2'
		}),
		//reader:用于将原始数据转换成Record实例
		reader: new Ext.data.JsonReader({
			//root:表示当前页显示信息队列
			root: 'data',
			//totalProperty:表示后台数据的记录总数
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
		//对数据进行排序
		//field:排序的字段                 direction:排序的方式
	 	//sortInfo: {field: 'name', direction: 'DESC'}  
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	/*oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});*/
	
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
		{header: '计费代码',dataIndex: 'feeType',sortable: true,width:100}
	]);
	
	//受益人信息 列表
	var feeColumnModel1 = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '受益人编号',dataIndex: 'beneficiaryId',sortable: true,hidden:true,width:100},
		{header: '商户编号',dataIndex: 'mchtNo',sortable: true,hidden:true,width:100},
		{header: '受益所有人',dataIndex: 'beneficiaryName',sortable: true,width:100},
		{header: '地址',dataIndex: 'beneficiaryAddress',sortable: true,width:100},
		{header: '证件种类',dataIndex: 'beneficiaryIdType',renderer: beneficiaryTypeVal,sortable: true,width:100},
		{header: '证件号码',dataIndex: 'beneficiaryIdCard',sortable: true,width:100},
		{header: '有效期限',dataIndex: 'beneficiaryExpiration',sortable: true,width:100},
	]);
	
	function beneficiaryTypeVal(value){
		if(value=='1'){
			return '身份证';
		}else if(value=='2'){
			return '护照';
		}
	}
	
	//费率算法 表格
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 250,
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
			xtype: 'textfield',
			id: 'serdiscCd',
			width: 60
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
		}]
	});
	
	//商户费率信息 表格
	var feeGridPanel = new Ext.grid.GridPanel({
		title: '商户费率信息',
		frame: true,
		border: true,
		height: 170,
		columnLines: true,
		//autoExpandColumn: 'discNm',
		stripeRows: true,
		store: feeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: feeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: [/*'终端号：',*/{
			xtype: 'textfield',
			id: 'term_ID',
			width: 60,
			hidden:true
		}/*,'-'*/,{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'searc',
			width: 60,
			hidden:true,
			handler: function(){
				feeStore.load({
				params: {
					start: 0,
					tmpNo: Ext.getCmp('tmpNo').getValue(),
					termId:Ext.getCmp('term_ID').getValue()
					}
				});
			}
		},'-',{
			xtype: 'button',
			text:'删除',
			iconCls: 'delete',
			//disabled: true,
			handler: function() {
				if(feeGridPanel.getSelectionModel().hasSelection()) {
					var rec = feeGridPanel.getSelectionModel().getSelected();
					showConfirm('确定要删除该条费率信息吗？',feeGridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T201001Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,feeGridPanel);
										feeGridPanel.getStore().reload();
										feeGridPanel.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,feeGridPanel);
									}
								},
								params: { 
									discId: rec.get('discId'),								
									txnId: '201001',
									subTxnId: '01'
								}
							});
						}
					});
				}
			}
		}]
	});
	
	//*****************************用来区分添加和修改方法    01为添加,02为修改*****************************//
	var curOp = '01';
	
	
	var beneficiaryForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		hidden: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '受益人编号*',
			id: 'beneficiaryId',
			name: 'beneficiaryId',
			width:150
		}]
	
	});
	
	
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
		tbar: [{
			xtype: 'button',
			text:'删除',
			iconCls: 'delete',
			//disabled: true,
			handler: function() {
				if(feeGridPanel1.getSelectionModel().hasSelection()) {
					var rec = feeGridPanel1.getSelectionModel().getSelected();
					showConfirm('确定要删除该条受益人信息吗？',feeGridPanel1,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T201001Action.asp?method=delete1',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,feeGridPanel1);
										feeGridPanel1.getStore().reload();
										//feeGridPanel1.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,feeGridPanel1);
									}
								},
								params: { 
									beneficiaryId: rec.get('beneficiaryId'),							
									txnId: '201001',
									subTxnId: '04'
								}
							});
						}
					});
				}
			}
		},'-',{
    		xtype: 'button',
			text: '添加',
			iconCls: 'add',
			handler: function() {
				//curOp = '01';
				cityCodeForm2.getForm().reset();
				cityCodeForm2.getForm().clearInvalid();
				cityCodeWin1.show();
				cityCodeWin1.center();
			}
		},'-',{
			xtype:  'button',
			text: '修改',
			iconCls: 'edit',
			id: 'setEdit',
			disabled:true,
			width: 60,
			handler: function(){
				//curOp = '02';
				cityCodeForm1.getForm().reset();
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				//加载选中行至打开窗体中文本中
				cityCodeForm1.getForm().loadRecord(rec);
				cityCodeForm1.getForm().clearInvalid();
				cityCodeWin2.show();
				cityCodeWin2.center();
			}
		}]
	});
	
	//选中数据显示修改按钮
	feeGridPanel1.getSelectionModel().on({
		'rowselect':function(){
			//修改按钮显示
			Ext.getCmp("setEdit").enable();
			//上传按钮显示
			Ext.getCmp("upload1").enable();
			//下载按钮显示
			Ext.getCmp("download").enable();
		}
	});
	
	feeStore1.load({
		params:{
			start: 0,
			//mchtNo: mchntForm.getForm().findField('mchtNoInPut').getValue()
		}
	});
	
	//受益人信息 表单
	var cityCodeForm1 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '受益人编号*',
			id: 'beneficiaryId',
			name: 'beneficiaryId',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '受益人编号不能为空',
			//可观输入信息是什么
			emptyText: '请输入受益人编号',
			//text文本输入框宽度
			width:150
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '受益人姓名*',
			id: 'beneficiaryName',
			name: 'beneficiaryName',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '受益人姓名不能为空',
			//可观输入信息是什么
			emptyText: '请输入受益人姓名',
			//text文本输入框宽度
			width:150
		},{
			//输入框类型
            xtype: 'textfield',
            //label文本值	
            fieldLabel: '地区名称*',
            id: 'beneficiaryAddress',
            name: 'beneficiaryAddress',
            //非空验证:allowBlank: false不为空,true可为空
            allowBlank: false,
            //非空提示信息
            blankText: '地区名称不能为空',
            //可观输入信息是什么
			emptyText: '请输入地区名称',
			//text文本输入框宽度
            width: 150
		},{
			//输入框类型
			xtype: 'basecomboselect',
			//label文本值
			fieldLabel: '证件种类*',
			//text文本输入框宽度
			width: 150,
			maxLength: 120,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '身份证件种类不能为空',
			//可观输入信息是什么
			emptyText: '请选择',
			id: 'beneficiaryIdType',
			hiddenName: 'beneficiaryIdTypemd',
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','身份证'],['2','护照']]
            })
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '证件号码*',
			id: 'beneficiaryIdCard',
			name: 'beneficiaryIdCard',
			//text文本输入框宽度
			width: 150,
			maxLength: 25,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '身份证号不能为空',
			//可观输入信息是什么
			emptyText: '请输入身份证号',
			vtype: 'is5Alphanum'
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '有效期限*',
			id: 'beneficiaryExpiration',
			name: 'beneficiaryExpiration',
			//text文本输入框宽度
			width: 150,
			maxLength: 8,
			minLength:8,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '有效期限不能为空',
			//可观输入信息是什么
			emptyText: '请输入有效期限',
			vtype: 'isNumber',
			vtypeText:'只能输入数字'
		}]
	});
	
	//受益人信息 表单
	var cityCodeForm2 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '受益人编号*',
			id: 'beneficiaryId1',
			name: 'beneficiaryId1',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '受益人编号不能为空',
			//可观输入信息是什么
			emptyText: '请输入受益人编号',
			//text文本输入框宽度
			width:150
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '受益人姓名*',
			id: 'beneficiaryName1',
			name: 'beneficiaryName1',
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '受益人姓名不能为空',
			//可观输入信息是什么
			emptyText: '请输入受益人姓名',
			//text文本输入框宽度
			width:150
		},{
			//输入框类型
            xtype: 'textfield',
            //label文本值	
            fieldLabel: '地区名称*',
            id: 'beneficiaryAddress1',
            name: 'beneficiaryAddress1',
            //非空验证:allowBlank: false不为空,true可为空
            allowBlank: false,
            //非空提示信息
            blankText: '地区名称不能为空',
            //可观输入信息是什么
			emptyText: '请输入地区名称',
			//text文本输入框宽度
            width: 150
		},{
			//输入框类型
			xtype: 'basecomboselect',
			//label文本值
			fieldLabel: '身份证件种类*',
			//text文本输入框宽度
			width: 150,
			maxLength: 120,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '身份证件种类不能为空',
			//可观输入信息是什么
			emptyText: '请选择',
			id: 'beneficiaryIdType1',
			hiddenName: 'beneficiaryIdTypemd1',
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','身份证'],['2','护照']]
            })
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '身份证号*',
			id: 'beneficiaryIdCard1',
			name: 'beneficiaryIdCard1',
			//text文本输入框宽度
			width: 150,
			maxLength: 25,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '身份证号不能为空',
			//可观输入信息是什么
			emptyText: '请输入身份证号',
			vtype: 'is5Alphanum'
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '有效期限*',
			id: 'beneficiaryExpiration1',
			name: 'beneficiaryExpiration1',
			//text文本输入框宽度
			width: 150,
			maxLength: 8,
			minLength:8,
			//非空验证:allowBlank: false不为空,true可为空
			allowBlank: false,
			//非空提示信息
			blankText: '有效期限不能为空',
			//可观输入信息是什么
			emptyText: '请输入有效期限',
			vtype: 'isNumber',
			vtypeText:'只能输入数字'
		}]
	});
	
	//受益人信息添加窗口
	var cityCodeWin1 = new Ext.Window({
		title: '受益人添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm2],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
	        	if(mchntForm.getForm().findField('mchtNoInPut').getValue()==''){
	            	showErrorMsg("清算信息未填写。",mchntForm);
	            	return;
	            }else if(cityCodeForm2.getForm().isValid()) {
					cityCodeForm2.getForm().submit({
						url: 'T201001Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cityCodeForm2);
							//添加成功后窗口关闭
							cityCodeWin1.hide();
							var mcht = mchntForm.getForm().findField('mchtNoInPut').getValue();
							//baseParams: 
							feeStore1.baseParams.mchtNo = mcht;
							//重新加载参数列表
							//feeGridPanel1.getStore().reload();
							feeStore1.reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cityCodeForm2);
						},
						params: {
							mchtNoInPut: mchntForm.getForm().findField('mchtNoInPut').getValue(),
							txnId: '201001',
							subTxnId: '05'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cityCodeForm2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin1.hide();
			}
		}]
	});
	
	//受益人信息修改窗口
	var cityCodeWin2 = new Ext.Window({
		title: '受益人修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm1],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
	        	if(mchntForm.getForm().findField('mchtNoInPut').getValue()==''){
	            	showErrorMsg("清算信息未填写。",mchntForm);
	            	return;
	            }else if(cityCodeForm1.getForm().isValid()) {
					cityCodeForm1.getForm().submit({
						url: 'T201001Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cityCodeForm1);
							//添加成功后窗口关闭
							cityCodeWin2.hide();
							//重新加载参数列表
							feeGridPanel1.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cityCodeForm1);
						},
						params: {
							mchtNoInPut: mchntForm.getForm().findField('mchtNoInPut').getValue(),
							txnId: '201001',
							subTxnId: '06'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cityCodeForm1.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin2.hide();
			}
		}]
	});

	//计费算法行选中事件
	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		//Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	store.on('beforeload', function() {
		//20120828修复bug：右边列表分页无法显示下一页
		//20120831修改：未选择左边列表数据，直接在右边列表中输入不存在的页数再点击刷新
		if (gridPanel.getSelectionModel().hasSelection()) {
			Ext.apply(this.baseParams, {
	            start: 0,
	            discCd: gridPanel.getSelectionModel().getSelected().data.discCd
	        });
		}else{
			showErrorMsg('请选择计费算法',groupWin);
		}
		
	});
	
	//费率算法 store 加载前 清空费率算法详细信息的 store
	gridStore.on('beforeload', function() {
		store.removeAll();
	});
	 //******************计费算法部分**********结束********
	//计费算法的 Form表单
	var feeDiscForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:450,
                	items: [detailGrid]
                }]
		}]
	});
	
	
    
	//弹出的 计费算法窗口
	var groupWin = new Ext.Window({
		title: '计费算法',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 870,
		autoHeight: true,
		items: [feeDiscForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if (gridPanel.getSelectionModel().hasSelection()) {
					 Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd);
					 groupWin.hide();
				}else{
					showErrorMsg('请选择计费算法',groupWin);
				}
			}
		},{
			text: '取消',
			handler: function() {
				groupWin.hide();
			}
		}]
	});

	var mchntForm = new Ext.FormPanel({
        title: '新增商户信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        store:fcompanyStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属分公司*',
					allowBlank: false,
					blankText: '请选择所属分公司',
					id: 'idacqInstId',
					hiddenName: 'acqInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户名称*',
					maxLength: '60',
					id: 'mchtNm',
					readOnly:false,
					allowBlank: false,
					anchor: '90%'
				}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
						anchor: '90%',
						listeners: {
							'select': function() {
								Ext.getCmp('idmcc').reset();
								Ext.getCmp('idmcc').parentP=this.value;
								Ext.getCmp('idmcc').getStore().reload();
							}
						}
		        	}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
	       				xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						methodName: 'getMcc',
						displayField: 'displayField',
						valueField: 'valueField',
						editable: true,
						allowBlank: false,
						anchor: '90%',
						blankText: '请选择商户MCC',
						id: 'idmcc',
						hiddenName: 'mcc'
//						listeners: {
//    					'select': function() {
//								Ext.getCmp('mchtNoInPut').setValue('');
//								Ext.getCmp('mchtNoByInPut').setValue('');
//							}
//						}
		        	}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]
        },{xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 500,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 130,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户简称*',
						allowBlank: false,
						maxLength:'60',
						id: 'mchtCnAbbr',
						name: 'mchtCnAbbr'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
			        	methodName: 'getEnt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户性质*',
						allowBlank: false,
						width:130,
						id:'idEtpsAttr',
						hiddenName: 'etpsAttr',
						 callFunction : function() {
							 if(this.value=='02'){//公司账户必填
								    Ext.getCmp('compAccountBankName').allowBlank=false;
								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称*');
								  	Ext.getCmp('settleAcct').allowBlank=false;
								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
								  	Ext.getCmp('compAccountBankCode').allowBlank=false;
								  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
							 }else if(mchntForm.getForm().findField('idsettleRpt').getValue()!='2'){
									//公司账户非必填
								 	Ext.getCmp('compAccountBankName').allowBlank=true;
								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称');
								  	Ext.getCmp('settleAcct').allowBlank=true;
								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户');
								  	Ext.getCmp('compAccountBankCode').allowBlank=true;
								  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行机构代码'); 
							 }
						 }
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册地址*',
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 100,
						allowBlank: false,
//						vtype: 'isOverMax',
						id: 'regAddr',
						name: 'regAddr',
						validator : function(value) {  
					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
					        if(length>100){  
					            return '该输入项不能超过100个字符或50个汉字';  
					        }  
					        return true;  
					    }
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经营地址*',
						maxLength: 100,
						allowBlank: false,
						id: 'operateAddr',
						name: 'operateAddr',
						validator : function(value) {  
					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
					        if(length>100){  
					            return '该输入项不能超过100个字符或50个汉字';  
					        }  
					        return true;  
					    }
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
			        	methodName: 'getSyncretic',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否三证合一*',
						allowBlank: false,
						width:130,
						id:'idSyncretic',
						hiddenName: 'isSyncretic',
						store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['0','是'],['1','否']]
    	                }),
						listeners: {
							'select': function() {
								var value = Ext.getCmp("idSyncretic").getValue();
								if(value == '0') {//显示社会信用代码信息
									Ext.getCmp('idUscCode').show(); 
									Ext.getCmp('uscCode').allowBlank=false;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码*');
									
									Ext.getCmp('idUscCodeDate').show(); 
									Ext.getCmp('uscCodeDate').allowBlank=false;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期*');
									
									Ext.getCmp('idLicenceNo').hide(); 
									Ext.getCmp('licenceNo').allowBlank=true;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号');
									
									Ext.getCmp('idLicenceNoDate').hide(); 
									Ext.getCmp('licenceNoDate').allowBlank=true;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期');
									
									Ext.getCmp('idBankLicenceNo').hide(); 
									Ext.getCmp('bankLicenceNo').allowBlank=true;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码');
									
									Ext.getCmp('idFaxNo').hide(); 
									Ext.getCmp('faxNo').allowBlank=true;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码');
								}else if(value=='1'){//显示营业执照信息
									Ext.getCmp('idLicenceNo').show(); 
									Ext.getCmp('licenceNo').allowBlank=false;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号*');
									
									Ext.getCmp('idLicenceNoDate').show(); 
									Ext.getCmp('licenceNoDate').allowBlank=false;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期*');
									
									Ext.getCmp('idBankLicenceNo').show(); 
									Ext.getCmp('bankLicenceNo').allowBlank=false;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码*');
									
									Ext.getCmp('idFaxNo').show(); 
									Ext.getCmp('faxNo').allowBlank=false;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码*');
									
									Ext.getCmp('idUscCode').hide(); 
									Ext.getCmp('uscCode').allowBlank=true;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码');
									
									Ext.getCmp('idUscCodeDate').hide(); 
									Ext.getCmp('uscCodeDate').allowBlank=true;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期');
								}
							}
						}
		        	}]
				} ,{
	        		columnWidth: .33,
		        	xtype: 'panel',
					id: 'idUscCode',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
					    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '统一社会信用代码*',
						maxLength: 19,
						id: 'uscCode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
					id: 'idUscCodeDate',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
					    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '统一社会信用代码证有效期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
						id: 'uscCodeDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idLicenceNo',
		        	layout: 'form',
	       			items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						maxLength: 20,
						vtype:'is6Alphanum', 
		                vtypeText:'只能输入字母或数字',
						id: 'licenceNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idLicenceNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'licenceNoDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
					id: 'idBankLicenceNo',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业组织机构代码*',
						maxLength: 20,
						allowBlank: false,
						vtype: 'is4Alphanum',
						id: 'bankLicenceNo',
//						allowBlank: false,20120727deleted
						name: 'bankLicenceNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'idFaxNo',
	       			items: [{
//						xtype: 'textnotnull',
	       				xtype: 'textfield',
	       				allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码*',
				//		minLength:15,
						maxLength: 15,
						//vtype: 'is3Alphanum',
						id: 'faxNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经营范围*',
						allowBlank: false,
						id: 'busiRanges',
						name: 'busiRanges'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'datefield',
						name: 'applyDate',
						labelStyle: 'padding-left: 5px',
						id: 'applyDate',
						fieldLabel: '注册日期*',
						allowBlank: false,
						width:128
					}]
				},{columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
					hidden:true,
	       			items: [{
						xtype: 'datefield',
						name: 'endDate',
						labelStyle: 'padding-left: 5px',
						id: 'endDate',
						fieldLabel: '到期日期*',
//						allowBlank: false,
						width:128
					}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册资金(万元)*',
						regex: /^(([1-9]{1}\d*)|([0]{1}))(\.(\d*[1-9]|[1-9]))?$/,//20120823修改的正则表达式
						//regex: /^(([0-9]+\.[0-9]*[0-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
						regexText: '注册资金必须是正确的金额格式',
						maxLength: 12,
//						vtype: 'isOverMax',
//						vtype: 'isMoney',
						id: 'busAmt',
						name: 'busAmt',
						allowBlank: false,
						width:128
		        	}]
				},{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'dynamicCombo',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属省*',
	                    id: 'idprovince',
	                    allowBlank: false,
	                    methodName: 'getProvinces',
	                    hiddenName: 'province',
	                    width:130,
	                    callFunction : function() {
			Ext.getCmp('idcity').reset();
			Ext.getCmp('idarea').reset();
			Ext.getCmp('idcity').parentP=this.value;
			Ext.getCmp('idcity').getStore().reload();
			Ext.getCmp('idarea').getStore().reload();
		}
	                    /*listeners: {
	    					'select': function() {
	    							citiesStore.removeAll();
									SelectOptionsDWR.getComboDataWithParameter('CITIES',mchntForm.getForm().findField('idprovince').getValue(),
										function(ret){
										citiesStore.loadData(Ext.decode(ret));
										var rec = citiesStore.getAt(0);
										Ext.getCmp('idcity').setValue(rec.get('valueField'));
									});
			    					}
			    				}*/
			                }]
			            },{
			                columnWidth: .33, 
			                xtype: 'panel',
			                layout: 'form',
			                items: [{
			                    xtype: 'dynamicCombo',
			                    labelStyle: 'padding-left: 5px',
			                    fieldLabel: '商户所属市*',
			                    id:'idcity',
			                    hiddenName: 'city',
			                    allowBlank: false,
			                    methodName: 'getCitys',
			                    width:130,
			                    callFunction : function() {
			Ext.getCmp('idarea').reset();
			Ext.getCmp('idarea').parentP=this.value;
			Ext.getCmp('idarea').getStore().reload();
			Ext.getCmp('idareaNo').setValue(this.value);
		}
			                    /*listeners: {
			    					'select': function() {
			                	            areasStore.removeAll();
			                	            Ext.getCmp('mchtNoInPut').setValue('');
											SelectOptionsDWR.getComboDataWithParameter('AREAS',mchntForm.getForm().findField('idcity').getValue(),
													function(ret){
												areasStore.loadData(Ext.decode(ret));
													var rec = areasStore.getAt(0);
												Ext.getCmp('idarea').setValue(rec.get('valueField'));
											});
			    					}
			    				}*/
	                }]
	            },{
                columnWidth: .33, 
                xtype: 'panel',
                layout: 'form',
                items: [{
                    xtype: 'dynamicCombo',
                    labelStyle: 'padding-left: 5px',
                    fieldLabel: '商户所属区/县',
                    id:'idarea',
                    hiddenName:'area',
                    width:130,
                    methodName: 'getAreas',
                    callFunction : function() {
    					Ext.getCmp('idareaNo').setValue(this.value);
    				}
                   /* listeners: {
    					'select': function() {
    						SelectOptionsDWR.getComboDataWithParameter('',mchntForm.getForm().findField('idarea').getValue(),
    								function(ret){	});
    					}
    				}*/
                }]
            },{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '经营场所权属',
					maxLength: '60',
					allowBlank: true,
					width:130,
					id: 'idOwnerBusi',
					methodName: 'getOwnerBusi',
	                hiddenName: 'ownerBusi'
	        	}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户调单联系人名称*',
						maxLength: '60',
						allowBlank: false,
						id: 'contName',
						name: 'contName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户调单联系人电话*',
						maxLength: '60',
						allowBlank: false,
						id: 'contTel',
						name: 'contTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						allowBlank:false,
						fieldLabel: '商户级别*',
						hiddenName: 'rislLvl',
    					increment: 10,
    					width:150,
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['0','正常商户'],['1','中级风险商户'],['2','高级风险商户']]
    	                })
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '电子邮件*',
						maxLength: 40,
						allowBlank: false,
						vtype: 'isOverMax',
						id: 'commEmail',
						name: 'commEmail',
						vtype: 'email'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户固定电话*',
						maxLength: '60',
						allowBlank: false,
						id: 'busiTel',
						name: 'busiTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						allowBlank:false,
						fieldLabel: '商户机构类型*',
						hiddenName: 'organizationType',
    					increment: 10,
    					width:150,
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['2A','2A:农、林、牧、渔业'],['2B','2B:采矿业'],['2C','2C:制造业'],['2D','2D:电力、燃气及水的生产和供应业'],['2E','2E:建筑业'],['2F','2F:交通运输、仓储和邮政业'],['2G','2G:信息传输、计算机服务和软件业'],['2F','2F:交通运输、仓储和邮政业'],['2H','2H:批发和零售业'],['2I','2I:住宿和餐饮业'],['2J','2J:银行业'],['2K','2K:房地产业'],['2L','2L:租赁和商务服务业'],['2M','2M:科学研究、技术服务和地质勘查业'],['2N','2N:水利、环境和公共设施管理业'],['2O','2O:居民服务和其他服务业'],['2P','2P:教育'],['2Q','2Q:卫生、社会保障和社会福利业'],['2R','2R:文化、体育和娱乐业'],['2S','2S:公共管理和社会组织'],['2T','2T:国际组织']]
    	                })
		        	
		        	}]	
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						allowBlank:false,
						fieldLabel: '企业规模*',
						hiddenName: 'etpsScale',
    					increment: 10,
    					width:150,
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['1','500人以上'],['2','200-500人'],['3','10-200人'],['4','10人以下']]
    	                })
	       			}]
				}]
        	},{
                title:'申请信息',
                layout:'column',
                id: 'basic1',
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单笔借记卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'sinDebAmount',
						name: 'sinDebAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单日借记卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'dayDebAmount',
						name: 'dayDebAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单月借记卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'monDebAmount',
						name: 'monDebAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单笔预付费卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'sinCreAmount',
						name: 'sinCreAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单日预付费卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'dayCreAmount',
						name: 'dayCreAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户申请单月预付费卡交易金额',
						maxLength: '60',
						allowBlank: true,
						id: 'monCreAmount',
						name: 'monCreAmount',
						regex:/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					    regexText:'请输入金额格式'
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
					anchor: '90%'
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
			name: 'returnFunc'
    	}]
	},{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '拓展人电话',
			allowBlank:true,
			vtype: 'isOverMax',
			name: 'netTel',
			id: 'netTel',
			regex: /^[0-9]*$/,
			maxLength:30,
			width:150
    	}]
	},{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '拓展人',
			name: 'expander',
			id: 'expander',
//		 	allowBlank:false,
			maxLength:50,//20121102修改
			width:130
    	}]
	},{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'timefieldsp',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户营业开始时间*',
			id: 'openTime',
			name: 'openTime',
			minValue: '00:00:00',
			maxValue: '23:59:59',
			altFormats: 'H:i:s',
			format: 'H:i:s',
			allowBlank: false,
			editable: true,
			increment: 1,
			anchor: '72%',
			listeners: {
				'select': function() {
					if(Ext.getCmp('closeTime').getValue()!='' && 
							(Ext.getCmp('openTime').getValue() > Ext.getCmp('closeTime').getValue())){
						showAlertMsg("开始时间不能大于结束时间，请重新选择！",mchntForm);
						Ext.getCmp('closeTime').setValue('');
						Ext.getCmp('openTime').setValue('');}
				}
			}
    	}]
	},{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'timefieldsp',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户营业结束时间*',
			id: 'closeTime',
			name: 'closeTime',
			minValue: '00:00:00',
			maxValue: '23:59:59',
			altFormats: 'H:i:s',
			allowBlank: false,
			format: 'H:i:s',
			editable: true,
			increment: 1,
			anchor: '72%',
			listeners: {
				'select': function() {
					if(Ext.getCmp('openTime').getValue()!='' && 
							(Ext.getCmp('openTime').getValue() > Ext.getCmp('closeTime').getValue())){
						showAlertMsg("开始时间不能大于结束时间，请重新选择！",mchntForm);
						Ext.getCmp('closeTime').setValue('');
						Ext.getCmp('openTime').setValue('');}
				}
			}
    	}]
	},/*{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        xtype: 'dynamicCombo',
        methodName: 'getAgentNo',
		labelStyle: 'padding-left: 5px',
		fieldLabel: '代理商*',
		allowBlank: false,
		blankText: '请选择代理商',
		id: 'idagentNo',
		hiddenName: 'agentNo',
		width:130,
		 callFunction : function() {
			 T20100.getBusiPerson(this.value,function(ret){
				Ext.getCmp('operNm').setValue(ret); 
			 });
		 }
    }]
	},*/{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
        		xtype: 'textnotnull',
        		allowBlank:true,
				fieldLabel: '所属业务人员',
				maxLength: 10,
				vtype: 'isOverMax',
				readOnly:true,
				width:130,
				id: 'operNm',
				name: 'operNm'
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
			name: 'hasInnerPosExp'
			 
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
			name: 'hasOurPosExp'
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
			name: 'isAppOutSide'
    	}]
	},{
		columnWidth: .33,
    	xtype: 'panel',
    	layout: 'form',
			items: [{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '申请人留言',
			maxLength: '60',
			allowBlank: true,
			id: 'appComm',
			name: 'appComm'
    	}]
	},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户英文名称',
						maxLength: '60',
						allowBlank: true,
						id: 'engName',
						regex: /^[A-Za-z]+$/,
						name: 'engName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户英文简称',
						maxLength: '60',
						allowBlank: true,
						id: 'mchtEnAbbr',
						regex: /^[A-Za-z]+$/,
						name: 'mchtEnAbbr'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '装机地址',
						maxLength: '60',
						allowBlank: true,
						id: 'insAddr',
						name: 'insAddr'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '合同邮寄地址',
						maxLength: '60',
						allowBlank: true,
						id: 'mailAddr',
						name: 'mailAddr'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户签约费率',
						maxLength: '60',
						allowBlank: true,
						id: 'contRate',
						name: 'contRate'
		        	}]
				},/*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '代理商名称',
						maxLength: '60',
						allowBlank: true,
						id: 'agentName',
						name: 'agentName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '代理商类型',
						maxLength: '60',
						allowBlank: true,
						id: 'agentType',
						name: 'agentType'
		        	}]
				},*/{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属大区',
						maxLength: '60',
						allowBlank: true,
						id: 'regionBel',
						name: 'regionBel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						maxLength: 100,
						allowBlank: false,
						width:150,
						id: 'addr',
						name: 'addr',
						value:'-',
						validator : function(value) {  
					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
					        if(length>100){  
					            return '该输入项不能超过100个字符或50个汉字';  
					        }  
					        return true;  
					    }
		        	},{
		        		xtype: 'textnotnull',
						fieldLabel: '商务经理工号*',
						width:150,
						id: 'operNo',
						name: 'operNo',
						value:'0',
						vtype: 'isNumber',
						maxLength: 8
		        	}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经营店名*',
						maxLength: 100,
						vtype: 'isOverMax',
						allowBlank: false,
						value:'-',
						width:130,
						id: 'busScope',
						name: 'busScope'
						}]
                },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
	       				xtype: 'textnotnull',
						fieldLabel: '终端数量*',
						maxLength: 30,
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						vtype: 'isOverMax',
						width:150,
						maxLength:8,
						value:0,
						id: 'cashierDeskNum',
						name: 'cashierDeskNum'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
	       			hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '直营连锁店数量*',
						allowBlank: false,
						maxLength:4,
						regex: /^[0-9]*$/,
						value:0,
						id: 'shopNum',
						name: 'shopNum'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '月消费总额*',
						name: 'monthTotalAmt',
						id: 'monthTotalAmt',
//						maskRe: /^[0-9\\.]+$/,
						vtype: 'isNumber',
						maxLength:12,
						value:0,
						width:130
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '单笔消费金额*',
						name: 'singleAmt',
						id: 'singleAmt',
//						maskRe: /^[0-9\\.]+$/,
						vtype: 'isNumber',
//						vtype: 'isMoney',
						maxLength:12,
						value:0,
						allowBlank: false,
						width:130
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SER_LVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '服务级别*',
						hiddenName: 'serLvl',
						value:'0',
						allowBlank: false,
						width:130
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
						xtype: 'datefield',
						name: 'openDate',
						labelStyle: 'padding-left: 5px',
						id: 'openDate',
						fieldLabel: '开业日期*',
						value:'20000101',
						allowBlank: false,
						width:128
					}]
				},/*{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预授权功能',
						maxLength: '60',
						allowBlank: true,
						id: 'preAuthor',
						name: 'preAuthor'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货功能',
						maxLength: '60',
						allowBlank: true,
						id: 'returnFunc',
						name: 'returnFunc'
		        	}]
				},*/{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'WINESHOP_LVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '酒店星级',
						hiddenName: 'wineshopLvl',
						width:130
		        	}]
				},{
		        		columnWidth: .33,
		       			xtype: 'panel',
		       			hidden:true,
			        	layout: 'form',
		       			items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '是否酒店行业',
							id: 'isWineshop',
							name: 'isWineshop',
							anchor: '90%'
			        	}]
				}]
			},/*{
                title:'附加信息-1',
                layout:'column',
                id: 'append',
                frame: true,
                items: []
			},{
                title:'附加信息-2',
                layout:'column',
                id: 'append1',
                frame: true,
                items: []
			},*//*{
                title:'附加信息-3',
                layout:'column',
                id: 'append2',
                frame: true,
                items: [{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预授权功能',
						maxLength: '60',
						allowBlank: true,
						id: 'preAuthor',
						name: 'preAuthor'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货功能',
						maxLength: '60',
						allowBlank: true,
						id: 'returnFunc',
						name: 'returnFunc'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '适用MCC',
						maxLength: '60',
						allowBlank: true,
						id: 'appMCC',
						name: 'appMCC'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否开通路由',
						maxLength: '60',
						allowBlank: true,
						id: 'openRoute',
						name: 'openRoute'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户单笔刷卡限额',
						maxLength: '60',
						allowBlank: true,
						id: 'sinLimit',
						name: 'sinLimit'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户单笔刷卡限额',
						maxLength: '60',
						allowBlank: true,
						id: 'dayLimit',
						name: 'dayLimit'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户单月刷卡限额',
						maxLength: '60',
						allowBlank: true,
						id: 'monLimit',
						name: 'monLimit'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户开通是否加入黑名单',
						maxLength: '60',
						allowBlank: true,
						id: 'openBlackList',
						name: 'openBlackList'
		        	}]
				}]
			
			},*//*{
                title:'清算信息-1',
                layout:'column',
                id: 'settle1',
                frame: true,
                items: []
			},*/{
	                title:'清算信息',
	                layout:'column',
	                id: 'settle',
	                frame: true,
	                items: [{	
						//columnWidth: .5,
						columnWidth: .33,
						xtype: 'panel',
						layout: 'form',
						hidden:true,
						items: [{	
							//columnWidth: .5,
								columnWidth: .33,
								xtype: 'panel',
								layout: 'form',
								hidden:true,
								items: [{
									xtype: 'dynamicCombo',
				                    labelStyle: 'padding-left: 5px',
				                    fieldLabel: '行政区号码*',
				                    id: 'idareaNo',
				                    allowBlank: false,
				                    methodName: 'getXingZheng',
				                    hiddenName: 'areaNo',
				                    width:130
								}]
							}]
						},{
			        		columnWidth: .33,
				        	xtype: 'panel',
				        	layout: 'form',
			       			items: [{
								xtype: 'textnotnull',
						        labelStyle: 'padding-left: 5px',
						        fieldLabel: '15位商户编号*',
								regex: /^\d{15}$/,
								regexText: '该输入框只能输入15位的数字',
								readOnly: true,  
								allowBlank: false,
						        //width:150,
						        id: 'mchtNoInPut',
						        name : 'mchtNoInPut'
				        	}]
						},{
							//columnWidth: .15,
							columnWidth: .33,
							xtype: 'panel',
							layout: 'form',
							items: [{
					        xtype: 'button',
					        text: '生成商户号',
					        id: 'verifyButton',
					        handler: function() {
					        	var bt = this;
					        	bt.disable();
					        	if(mchntForm.getForm().findField('idmcc').getValue()==''){
					            	showErrorMsg("生成商户号前请选择商户MCC。",mchntForm);
					            	bt.enable();
					            	return;
					            }else if( !Ext.getCmp('idareaNo').isValid() ){
					            	showErrorMsg("生成商户号前请选择行政商户所属市、县",mchntForm);
					            	bt.enable();
					            	return;
					        	}
					        	
					          	bt.enable();
				            	mchntForm.getForm().findField('idmcc').disable();
				            	mchntForm.getForm().findField('idareaNo').disable();
				            	T20100.createMchtNo(Ext.getCmp("idmcc").getValue(),Ext.getCmp('idareaNo').getValue(),function(ret){
				            		var sta = '';
				            		var msg = '';
				            		var err = '';
				            		var bSta = '';
				            		
		            		
				            		for(var key in ret){
				            			if(key=='result'){
				            				sta = ret[key];
				            			}else if(key=='bResult'){
				            				bSta = ret[key];
				            			}else if(key=='err'){
				            				err = ret[key];
				            			}else if(key=='msg'){
				            				msg = ret[key];
				            			}else if(key=='mchtNoInPut'){
				            				Ext.getCmp('mchtNoInPut').setValue(ret[key]);
				            			}else if(key=='mchtNoByInPut'){
				            				Ext.getCmp('mchtNoByInPut').setValue(ret[key].substring(0,15));//北京商户号
				            				Ext.getCmp('mchtSXNoByInPut').setValue(ret[key].substring(15));//随行付商户号
				            			}else if(key==''){
				            				
				            			}
				            		}
				            		if(sta == '00' && bSta == '00'){
				            			Ext.MessageBox.show({
											msg: "商户号生成成功",
											title: '成功',
											animEl: Ext.get(mchntForm.getEl()),
											buttons: Ext.MessageBox.OK,
											icon: 'message-success',
											modal: true,
											width: 300
										});
				            		}else if(bSta == '01'){
				            			Ext.MessageBox.show({
											msg: "该MCC商户号序列所剩不多，请尽快申请新号段",
											title: '成功',
											animEl: Ext.get(mchntForm.getEl()),
											buttons: Ext.MessageBox.OK,
											icon: 'message-success',
											modal: true,
											width: 300
										});	
				            		}else{
				            			showErrorMsg(err,mchntForm);
				            		}
				                	mchntForm.getForm().findField('idmcc').enable();
				                	mchntForm.getForm().findField('idareaNo').enable();
				                	bt.enable();
				                });
								}
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
					checked:'true',
					anchor: '90%'
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
					anchor: '90%'
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
				checked:'true',
				anchor: '90%'
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
		anchor: '90%'
	}]
},{
	        		//columnWidth: .5,
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '商户结算方式T+N*',
						maxLength: 2,
						maskRe: /^[0-9]{1,2}/,
						vtype: 'isOverMax',
						//width:150,
						id: 'settleChn'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'basecomboselect',
						fieldLabel: '商户结算周期*',
						baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'settleType',
						anchor: '60%',
						//value: '1',
						value:'0',
                        listeners: {
		                    'select': function() {

		                    }
	                    }
					}]
				},{
			        		columnWidth: 1,
			        		//columnWidth: .33,
			            	layout: 'form',
			            	xtype: 'panel',
			        		items: [{
			        			xtype: 'basecomboselect',
						        baseParams: 'SETTLE_ACCT_TYPE',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '结算账户类型*',
								id: 'idsettleRpt',
								hiddenName: 'settleRpt',
								allowBlank: false,
								width:150,
								//anchor: '30%',
						        listeners: {
						                    'select': function() {
							                    var value = mchntForm.getForm().findField('idsettleRpt').getValue();							                   
							                    var etpsAttrVal = mchntForm.getForm().findField('idEtpsAttr').getValue();
							                    if(value=='2'){//对公
							                    		//公司账户必填
													    Ext.getCmp('compAccountBankName1').show();
													    Ext.getCmp('compAccountBankName').allowBlank=false;
													  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
													  	Ext.getCmp('settleAcct1').show();
													  	Ext.getCmp('settleAcct').allowBlank=false;
													  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
													  	Ext.getCmp('compAccountBankCode1').show();
													  	Ext.getCmp('compAccountBankCode').allowBlank=true;
													  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
														 //定向委托账户非必填
													    Ext.getCmp('dirBankName1').hide();
													    Ext.getCmp('dirBankName').allowBlank=true;
													  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
													  	Ext.getCmp('dirAccountName1').hide();
													  	Ext.getCmp('dirAccountName').allowBlank=true;
													  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
													  	Ext.getCmp('dirAccount1').hide();
													  	Ext.getCmp('dirAccount').allowBlank=true;
													  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
													  	Ext.getCmp('dirBankCode1').hide();
													  	Ext.getCmp('dirBankCode').allowBlank=true;
													  	mchntForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
													  	//法人账户非必填
													    Ext.getCmp('corpBankName1').hide();
													    Ext.getCmp('corpBankName').allowBlank=true;
													  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
													  	Ext.getCmp('feeAcct1').hide();
													  	Ext.getCmp('feeAcct').allowBlank=true;
													  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户'); 	
													  	Ext.getCmp('bankAccountCode1').hide();
													  	Ext.getCmp('bankAccountCode').allowBlank=true;
													  	mchntForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码');
													  	//add
													  	Ext.getCmp('legalNam1').hide();
													  	Ext.getCmp('legalNam').allowBlank=true;
													  	mchntForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称*');
													  	Ext.getCmp('companyNam1').show();
													  	Ext.getCmp('companyNam').allowBlank=false;
													  	mchntForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称*');
													  	
													  	Ext.getCmp('dir_open_bank_11').hide();
													  	Ext.getCmp('dir_open_bank_1').allowBlank=true;
													  	mchntForm.getForm().findField('dir_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称');
													  	Ext.getCmp('dir_bank_province_11').hide();
													  	Ext.getCmp('dir_bank_province_1').allowBlank=true;
													  	mchntForm.getForm().findField('dir_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省');
													  	Ext.getCmp('dir_bank_city_11').hide();
													  	Ext.getCmp('dir_bank_city_1').allowBlank=true;
													  	mchntForm.getForm().findField('dir_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市');
													  	
													  	Ext.getCmp('corp_open_bank_11').hide();
													  	Ext.getCmp('corp_open_bank_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称');
													  	Ext.getCmp('corp_bank_province_11').hide();
													  	Ext.getCmp('corp_bank_province_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省');
													  	Ext.getCmp('corp_bank_city_11').hide();
													  	Ext.getCmp('corp_bank_city_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市');
													  	
													  	Ext.getCmp('comp_open_bank_11').show(); 
													  	Ext.getCmp('comp_open_bank_1').allowBlank=false;
														mchntForm.getForm().findField('comp_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称*');
														Ext.getCmp('comp_bank_province_11').show(); 
														Ext.getCmp('comp_bank_province_1').allowBlank=false;
														mchntForm.getForm().findField('comp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省*');
														Ext.getCmp('comp_bank_city_11').show(); 
														Ext.getCmp('comp_bank_city_1').allowBlank=false;
														mchntForm.getForm().findField('comp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市*');
													  	
							                    }else if(value=='1'){//对私
							                    	    if(etpsAttrVal!='02'){
									                    	//公司账户非必填
															    Ext.getCmp('compAccountBankName1').hide();
															    Ext.getCmp('compAccountBankName').allowBlank=true;
															  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称');
															  	Ext.getCmp('settleAcct1').hide();
															  	Ext.getCmp('settleAcct').allowBlank=true;
															  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户'); 	
															  	Ext.getCmp('compAccountBankCode1').hide();
															  	Ext.getCmp('compAccountBankCode').allowBlank=true;
															  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
									                    
							                    	    }
								                    	//法人账户必填
														    Ext.getCmp('corpBankName1').show(); 
														    Ext.getCmp('corpBankName').allowBlank=false;
														  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称*');
														  	Ext.getCmp('feeAcct1').show(); 
														  	Ext.getCmp('feeAcct').allowBlank=false;
														  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户*'); 
														  	Ext.getCmp('bankAccountCode1').show(); 
														  	Ext.getCmp('bankAccountCode').allowBlank=true;
														  	mchntForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 
														  //定向委托账户非必填
														    Ext.getCmp('dirBankName1').hide();
														    Ext.getCmp('dirBankName').allowBlank=true;
														  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
														  	Ext.getCmp('dirAccountName1').hide();
														  	Ext.getCmp('dirAccountName').allowBlank=true;
														  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
														  	Ext.getCmp('dirAccount1').hide();
														  	Ext.getCmp('dirAccount').allowBlank=true;
														  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
														  	Ext.getCmp('dirBankCode1').hide();
														  	Ext.getCmp('dirBankCode').allowBlank=true;
														  	mchntForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
														    //add
														  	Ext.getCmp('legalNam1').show();
														  	Ext.getCmp('legalNam').allowBlank=false;
														  	mchntForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称*');
														  	Ext.getCmp('companyNam1').hide();
														  	Ext.getCmp('companyNam').allowBlank=true;
														  	mchntForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称');
														  	
														  	Ext.getCmp('dir_open_bank_11').hide();
														  	Ext.getCmp('dir_open_bank_1').allowBlank=true;
														  	mchntForm.getForm().findField('dir_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称');
														  	Ext.getCmp('dir_bank_province_11').hide();
														  	Ext.getCmp('dir_bank_province_1').allowBlank=true;
														  	mchntForm.getForm().findField('dir_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省');
														  	Ext.getCmp('dir_bank_city_11').hide();
														  	Ext.getCmp('dir_bank_city_1').allowBlank=true;
														  	mchntForm.getForm().findField('dir_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市');
														  	
														  	Ext.getCmp('comp_open_bank_11').hide();
														  	Ext.getCmp('comp_open_bank_1').allowBlank=true;
														  	mchntForm.getForm().findField('comp_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称');
														  	Ext.getCmp('comp_bank_province_11').hide();
														  	Ext.getCmp('comp_bank_province_1').allowBlank=true;
														  	mchntForm.getForm().findField('comp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省');
														  	Ext.getCmp('comp_bank_city_11').hide();
														  	Ext.getCmp('comp_bank_city_1').allowBlank=true;
														  	mchntForm.getForm().findField('comp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市');
														  	
														  	Ext.getCmp('corp_open_bank_11').show();
														  	Ext.getCmp('corp_open_bank_1').allowBlank=false;
														  	mchntForm.getForm().findField('corp_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称*');
														  	Ext.getCmp('corp_bank_province_11').show();
														  	Ext.getCmp('corp_bank_province_1').allowBlank=false;
														  	mchntForm.getForm().findField('corp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省*');
														  	Ext.getCmp('corp_bank_city_11').show();
														  	Ext.getCmp('corp_bank_city_1').allowBlank=false;
														  	mchntForm.getForm().findField('corp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市*');
													  	
														  	
								                    }else if(value=='3'){//定向
								                    	if(etpsAttrVal!='02'){
									                    	//公司账户非必填
															 Ext.getCmp('compAccountBankName1').hide();
															 Ext.getCmp('compAccountBankName').allowBlank=true;
															 mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称');
															 Ext.getCmp('settleAcct1').hide();
															 Ext.getCmp('settleAcct').allowBlank=true;
															 mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户'); 	
															 Ext.getCmp('compAccountBankCode1').hide();
															 Ext.getCmp('compAccountBankCode').allowBlank=true;
															 mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
									                    
							                    	    }
								                    	//法人账户非必填
													    Ext.getCmp('corpBankName1').hide();
													    Ext.getCmp('corpBankName').allowBlank=true;
													  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
													  	Ext.getCmp('feeAcct1').hide();
													  	Ext.getCmp('feeAcct').allowBlank=true;
													  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户'); 
													  	Ext.getCmp('bankAccountCode1').hide();
													  	Ext.getCmp('bankAccountCode').allowBlank=true;
													  	mchntForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 
													  	
													  //定向委托账户必填
													    Ext.getCmp('dirBankName1').show();
													    Ext.getCmp('dirBankName').allowBlank=false;
													  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称*');
													  	Ext.getCmp('dirAccountName1').show();
													  	Ext.getCmp('dirAccountName').allowBlank=false;
													  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名*'); 
													  	Ext.getCmp('dirAccount1').show();
													  	Ext.getCmp('dirAccount').allowBlank=false;
													  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*');
													  	Ext.getCmp('dirBankCode1').show();
													  	Ext.getCmp('dirBankCode').allowBlank=true;
													  	mchntForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
													    //add
													  	Ext.getCmp('legalNam1').hide();
													  	Ext.getCmp('legalNam').allowBlank=true;
													  	mchntForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称');
													  	Ext.getCmp('companyNam1').hide();
													  	Ext.getCmp('companyNam').allowBlank=true;
													  	mchntForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称');
													  	
													  	Ext.getCmp('comp_open_bank_11').hide();
													  	Ext.getCmp('comp_open_bank_1').allowBlank=true;
														mchntForm.getForm().findField('dir_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称');
														Ext.getCmp('comp_bank_province_11').hide();
														Ext.getCmp('comp_bank_province_1').allowBlank=true;
														mchntForm.getForm().findField('comp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省');
														Ext.getCmp('comp_bank_city_11').hide();
														Ext.getCmp('comp_bank_city_1').allowBlank=true;
														mchntForm.getForm().findField('comp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市');
														  	
														Ext.getCmp('corp_open_bank_11').hide();
														Ext.getCmp('corp_open_bank_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称');
													  	Ext.getCmp('corp_bank_province_11').hide();
													  	Ext.getCmp('corp_bank_province_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省');
													  	Ext.getCmp('corp_bank_city_11').hide();
													  	Ext.getCmp('corp_bank_city_1').allowBlank=true;
													  	mchntForm.getForm().findField('corp_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市');
													  	  	
													  	Ext.getCmp('dir_open_bank_11').show();
													  	Ext.getCmp('dir_open_bank_1').allowBlank=false;
														mchntForm.getForm().findField('dir_open_bank_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称*');
														Ext.getCmp('dir_bank_province_11').show();
														Ext.getCmp('dir_bank_province_1').allowBlank=false;
														mchntForm.getForm().findField('dir_bank_province_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省*');
														Ext.getCmp('dir_bank_city_11').show();
														Ext.getCmp('dir_bank_city_1').allowBlank=false;
														mchntForm.getForm().findField('dir_bank_city_1').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市*');
														  	
							                    }							                    
						                    }
					                    }
									
								}]
				        },{
			        		columnWidth: .33,
				        	xtype: 'panel',
				        	hidden:true,
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户当前使用账号',
								maxLength: '60',
								allowBlank: true,
								id: 'currAccount',
								name: 'currAccount'
				        	}]
						},{
			        		columnWidth: .33,
				        	xtype: 'panel',
							id: 'compAccountBankName1',
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '对公账户开户行名称*',
								maxLength: '60',
								allowBlank: false,
								id: 'compAccountBankName',
								name: 'compAccountBankName'
				        	}]
						},{
			        		columnWidth: .33,
				        	xtype: 'panel',
							id: 'compAccountBankCode1',
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '对公账户开户行机构代码',
								maxLength: '60',
								allowBlank: true,
								id: 'compAccountBankCode',
								name: 'compAccountBankCode'
				        	}]
						},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '对公账号开户名*',
						maxLength: 60,
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'settleAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'settleAcct1',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '对公账户*',
						maxLength: 40,	
						vtype: 'isNumber',
						//width:150,
						id: 'settleAcct'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'corpBankName1',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对私账号开户行名称*',
						maxLength: '60',
						allowBlank: false,
						id: 'corpBankName',
						name: 'corpBankName'
		        	}]
				},{
	        		columnWidth: .33,
	        		id: 'bankAccountCode1',
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对私账号开户行机构代码',
						maxLength: '60',
						allowBlank: true,
						id: 'bankAccountCode',
						name: 'bankAccountCode'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	id: 'feeAcctNm1',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						//fieldLabel: '结算账户1账户户名*',
						fieldLabel: '对私账号开户名*',
						maxLength: 60,	
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'feeAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
					columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'feeAcct1',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						//fieldLabel: '结算账户1账户户号*',
						fieldLabel: '对私账户*',
						maxLength: 40,	
						vtype: 'isNumber',
						//width:150,
						id: 'feeAcct'
		        	}]
				},/*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户法人账号开户名',
						maxLength: '60',
						allowBlank: true,
						id: 'legalAccountName',
						name: 'legalAccountName'
		        	}]
				},*//*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户法人账号',
						maxLength: '60',
						allowBlank: true,
						id: 'corpAccount',
						name: 'corpAccount'
		        	}]
				},*//*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户公司账号开户名',
						maxLength: '60',
						allowBlank: true,
						id: 'compAccountName',
						name: 'compAccountName'
		        	}]
				},*//*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户公司账号',
						maxLength: '60',
						allowBlank: true,
						id: 'compAccount',
						name: 'compAccount'
		        	}]
				},*/{
		        		columnWidth: 1,
		       			xtype: 'panel',
		       			hidden:true,
			        	layout: 'form',
		       			items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '定向委托付款标志',
							id: 'dirFlag',
							name: 'dirFlag',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'dirBankName1',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户开户行名称',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankName',
						name: 'dirBankName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'dirBankCode1',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户账户开户行机构代码',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankCode',
						name: 'dirBankCode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'dirAccountName1',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户开户名',
						maxLength: '60',
						allowBlank: true,
						id: 'dirAccountName',
						name: 'dirAccountName'
		        	}]
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	id: 'dirAccount1',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账号',
						maxLength: '60',
						allowBlank: true,
						id: 'dirAccount',
						name: 'dirAccount'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'legalNam1',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对私账户名称*',		 
						maxLength: 100,
						allowBlank: false,
						vtype: 'isOverMax',
						id: 'legalNam',
						width:128
		        	}]
				},{
					id: 'companyNam1',
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:false,
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对公账户名称*',		 
						maxLength: 100,
						vtype: 'isOverMax',
						id: 'companyNam',
						width:128
		        	}]
				},
				//ADD
				{
					columnWidth: .33,
					id:'dir_open_bank_11',
		   			xtype: 'panel',
		        	layout: 'form',
		        	items: [{
				    xtype: 'dynamicCombo_1',
					methodName: 'getdirOpenBank',
					fieldLabel: '定向委托账号开户总行名称*',
					allowBlank: false,
					width:300,
					maxLength: 60,
					id:'dir_open_bank_1',
					hiddenName: 'dir_open_bank'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'dir_bank_province_11',
		        	items: [{
					columnWidth: .33,
					maxLength: 60,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankProvince',
					fieldLabel: '定向委托开户行所在省*',
					allowBlank: false,
					width:300,
					id:'dir_bank_province_1',
					hiddenName: 'dir_bank_province'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'dir_bank_city_11',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankCity',
					allowBlank: false,
					maxLength: 60,
					fieldLabel: '定向委托开户行所在市*',
					width:300,
					id:'dir_bank_city_1',
					hiddenName: 'dir_bank_city'
		        	}]
				},{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'comp_open_bank_11',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					maxLength: 60,
					methodName: 'getOpenBank',
					fieldLabel: '对公账号开户总行名称*',
					id:'comp_open_bank_1',
					width:300,
					hiddenName: 'comp_open_bank'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'comp_bank_province_11',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankProvince',
					allowBlank: false,
					maxLength: 60,
					fieldLabel: '对公账号开户行所在省*',
					width:300,
					id:'comp_bank_province_1',
					hiddenName: 'comp_bank_province'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
					id:'comp_bank_city_11',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankCity',
					allowBlank: false,
					maxLength: 60,
					fieldLabel: '对公账号开户行所在市*',
					width:300,
					id:'comp_bank_city_1',
					hiddenName: 'comp_bank_city'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
					id:'corp_open_bank_11',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					maxLength: 60,
					methodName: 'getCorpOpenBank',
					fieldLabel: '对私账号开户总行名称*',
					width:300,
					id:'corp_open_bank_1',
					hiddenName: 'corp_open_bank'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'corp_bank_province_11',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankProvince',
					allowBlank: false,
					maxLength: 60,
					fieldLabel: '对私账号开户行所在省*',
					width:300,
					id:'corp_bank_province_1',
					hiddenName: 'corp_bank_province'
		        	}]
				},
				{
					columnWidth: .33,
		   			xtype: 'panel',
		        	layout: 'form',
		        	id:'corp_bank_city_11',
		        	items: [{
					columnWidth: .33,
					allowBlank: false,
					maxLength: 60,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankCity',
					fieldLabel: '对私账号开户行所在市*',
					width:300,
					id:'corp_bank_city_1',
					hiddenName: 'corp_bank_city'
		        	}]
				},//END
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'combo',
						store: clearTypeStore,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						hiddenName: 'clearType',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						allowBlank: true,
						fieldLabel: '商户结算账户类型*',
						anchor: '60%',
						listWidth: 160,
                        listeners: {
	                     'select': function() {
                                resetVerify();
	                        }
	                    }
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城清算号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'openStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城交换号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'changeStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						maxLength: 12,
//						vtype: 'isOverMax',
						width:150,
						id: 'speSettleTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						maxLength: 48,
						vtype: 'isOverMax',
						width:150,
						id: 'speSettleLv'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费描述',
						maxLength: 255,
						vtype: 'isOverMax',
						width:150,
						id: 'speSettleDs'
		        	}]
				},{	
					columnWidth: .35,
					xtype: 'panel',
					layout: 'form',
						items: [{
						xtype: 'textfield',
				        labelStyle: 'padding-left: 5px',
				        fieldLabel: '对应北京银行商户号*',
				        hideLabel :true,
				        width:150,
				        allowBlank: false,
				        hidden : true,
				        id: 'mchtNoByInPut'
				   
				
				    }]
	         	},{	
					columnWidth: .35,
					xtype: 'panel',
					layout: 'form',
						items: [{
						xtype: 'textfield',
				        labelStyle: 'padding-left: 5px',
				        fieldLabel: '对应随行付商户号*',
				        width:150,
				        hideLabel :true,
				        hidden : true,
				        allowBlank: false,
				        id: 'mchtSXNoByInPut'											
				    }]
	         	}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	id:'feeForm',
                	//xtype:'form',
                	region: 'north',
                	height: 75,
                	layout: 'column',
                	items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	        	hidden:true,
	       		items: [{
	       			xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					id: 'idFK_BRCH_NO',
					name: 'FK_BRCH_NO',
					readOnly: true,
					maxLength: 8,
					value:'*',
						width:150
		        	}]
				},{
		        		columnWidth: .2,
		        		labelWidth: 70,
		            	layout: 'form',
		            	hidden:true,
		        		items: [{
					        xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端代码',
							id: 'idTERM_ID',
							name: 'TERM_ID',
							readOnly: true,
							maxLength: 8,
						value:'*',
						width:100
							 
				        }]
		        	},{
                		columnWidth: .2,
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					allowBlank: false,
	       					xtype: 'textnotnull',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '计费代码*',
							id: 'discCode',
							name: 'discCode',
//							disabled:true,
							readOnly: true,
							width:80
						}]
					},{	
						columnWidth: .15,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'选择计费代码',
							id: 'feeBt',
							width: 60,
							handler: function(){
								groupWin.show();
							}
                		}]
					},{	
						columnWidth: .1,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'添加',
							id: 'addbu',
							handler: function(){
								saveDiscAlgo2();
							}
                		}]
					},{	 
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
	       				items: [{
                			xtype: 'textfield',
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
	       			xtype: 'dynamicCombo',
	       			methodName: 'getAreaCodecode',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地区码',
					readOnly: true,
					editable: true,
					id: 'idCITY_CODE',
					hiddenName: 'CITY_CODE',
					value:'*',
						width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
		        hidden:true,
	       		items: [{
			        xtype: 'basecomboselect',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构',
					value:'*',
					readOnly: true,
					blankText: '请选择目的机构',
					id: 'idTO_BRCH_NO',
					hiddenName: 'TO_BRCH_NO',
					//anchor: '90%',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
		        hidden:true,
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						readOnly: true,
						baseParams: 'CARD_STYLE',
						//allowBlank: false,
						value:'*',
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
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						readOnly: true,
						value:'*',
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
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						readOnly: true,
						vtype: 'isOverMax',
						value:'*',
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
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						readOnly: true,
						value:'*',
						blankText: '请输入交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						width:150
		        	}]
				}]
                },{
                	region: 'center',
                	items:[feeGridPanel]
                }]
		    },{
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
									imagesId: imagesId
								}
							});
							storeImg1.reload({
								params: {
									start: 0,
									imagesId: imagesId
								}
							});
						}
					},{
						xtype: 'button',
						width: '80',
						text: '上传',
						id: 'upload',
						handler:function() {
							hasUpload = "1";
							dialog.show();
						}
					}]
                }]
		    },{
                title:'基本信息扩充',
                id: 'standardConfig',
                frame: true,
				layout:'column',
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
						fieldLabel: '控股股东或实际控制人证件种类*',
						width:130,
						allowBlank: false,
						id:'idshareholderTp',
						hiddenName: 'idshareholderTpmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人证件号码*',
						allowBlank: false,
						maxLength: 25,
						vtype: 'is5Alphanum',
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
						fieldLabel: '控股股东或实际控制人证件有效期*',
						allowBlank: false,
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'shareholderDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'COUNTRYS',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人国籍*',
						allowBlank: false,
						width:130,
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
								//外籍名是否隐藏 
								Ext.getCmp('foreignName').hide(); 
								//外籍名是否 
								Ext.getCmp('idForeignName').allowBlank=true;
							  	
							  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
							  	Ext.getCmp('idartifCertifTp').readOnly=true;
							  	//联系人授权人非必填
							  	Ext.getCmp('contact').allowBlank=false;
							  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
							  	Ext.getCmp('linkTel').allowBlank=false;
							  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
							}else{//非中国国籍的则为 护照
								Ext.getCmp('foreignName').show(); 
								Ext.getCmp('idForeignName').allowBlank=false;
							 	mchntForm.getForm().findField('artifCertifTp').setValue("06");
							 	Ext.getCmp('idartifCertifTp').readOnly=false;
								//联系人授权人必填
								Ext.getCmp('contact').allowBlank=false;
								mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
								Ext.getCmp('linkTel').allowBlank=false;
								mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
						   }
    					}
    				}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'foreignName',
		        	layout: 'form',
	       			items: [{
	       					labelStyle: 'padding-left: 5px',
							xtype:'dynamicCombo',
							fieldLabel: '外籍名*',
							allowBlank: false,
							width:130,							
							methodName: 'getForeignName',
							hiddenName: 'foreignName',
							id: 'idForeignName'
			        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法定代表人/负责人*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'manager',
							name: 'manager'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'ididartifCertifTp',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件类型*',
						width:130,
						allowBlank: false,
						id:'idartifCertifTp',
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码*',
						maxLength: 18,
						allowBlank: false,
						vtype: 'is5Alphanum',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						allowBlank:false,
						fieldLabel: '法定代表人性别*',
						hiddenName: 'legalGender',
    					increment: 10,
    					width:150,
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['0','男'],['1','女']]
    	                })
		        	
		        	}]	
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人地址*',
						maxLength: 60,
						allowBlank: false,
						id: 'legalAddr',
						name: 'legalAddr',
						validator : function(value) {  
					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
					        if(length>100){  
					            return '该输入项不能超过100个字符或50个汉字';  
					        }  
					        return true;  
					    }
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法定代表人/负责人职业*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'legalProfession',
							name: 'legalProfession'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'ididentityNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码有效日期*',
						allowBlank: false,
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'identityNoDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人电话*',
						allowBlank: false,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'managerTel',
						name: 'managerTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人姓名*',
						allowBlank: false,
						maxLength: 30,
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
						hiddenName: 'contactmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
	       				allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人证件号码*',
						allowBlank: false,
						maxLength: 20,
						vtype: 'is7Alphanum',
						id: 'linkTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证号有效日期*',
						allowBlank: false,
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'linkTelDate'
		        	}]
				}                
                ]
		    },{
                title:'附属信息',
                id: 'settle1',
                frame: true,
				layout:'column',
                items: [{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                		xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '主营业务',
						maxLength: 50,
//						allowBlank: false,
						vtype: 'isOverMax',
						width:130,
						id: 'busMain',
						name: 'busMain'
	       			}]
                },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编号',
						maxLength: '6',
//						allowBlank: false,
						regex: /^[0-9]\d{5}$/,
						id: 'postalCode',
						name: 'postalCode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电话',		 
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'commTel',
						width:128
		        	}]
				},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '员工人数',
					name: 'empNum',
					id: 'empNum',
					regex: /^[0-9]*$/,
					allowBlank: true,
					maxLength:8,
					width:130
	        	}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属行业',
						maxLength: '60',
//						allowBlank: false,
						id: 'belInd',
						name: 'belInd'
		        	}]
				},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '营业面积/平方米',
					name: 'acreage',
					id: 'acreage',
					vtype: 'isArea',
					maxLength:10,
//					allowBlank: false,
					width:130
	        	}]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'ICP备案公司名称',
						maxLength: '60',
						allowBlank: true,
						id: 'ICPcompName',
						name: 'ICPcompName'
		        	}]
				},{
	        	columnWidth: .33,
		        xtype: 'panel',
		        layout: 'form',
	       		items: [{
			       	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '公司网址',
					regex:/^[a-zA-Z]+$/,
				//	regexText:'必须是正确的地址格式，如http://www.xxx.com',
                    maxLength: 60,
					vtype: 'isOverMax',
					id: 'homePage',
					name: 'homePage',
					width:128
		        	}]
			},{
	        	columnWidth: .33,
		        xtype: 'panel',
		        layout: 'form',
	       		items: [{
					xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: 'ICP备案号',
					maxLength: '60',
					allowBlank: true,
					id: 'ICPrecordNo',
					name: 'ICPrecordNo'
		        }]
			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业传真',		 
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'fax'
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
		    }]}],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
            	subSave();
            }
        },{
            text: '重置',
            handler: function() {
            	hasSub = false;
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
        }]
    });
	
    
    Ext.getCmp("idmcc").on('select',function(){
    	T20402.getInfo(Ext.getCmp("idmcc").getValue(),function(ret){
    		if(ret=='0'){
    				showErrorMsg("找不到相应信息",grid);
    				mchntForm.getForm().findField('idmcc').reset();
    				return;
    		}
            var tblInfMchntTp = Ext.decode(ret.substring(1,ret.length-1));
            Ext.getCmp("idmcc").setValue(tblInfMchntTp.id.mchntTp);
  			//Ext.getCmp("mcc").setValue(tblInfMchntTp.descr);
  			Ext.getCmp('mchtNoInPut').setValue('');
			Ext.getCmp('mchtNoByInPut').setValue('');
        });
    });
    //保存商户费率信息
    function saveDiscAlgo2(){
    	var btn = Ext.getCmp('addbu');
    	var tmpNo=Ext.getCmp('tmpNo').getValue();
    	var TERMID=Ext.getCmp('idTERM_ID').getValue();
    	var CITY_CODE=Ext.getCmp('idCITY_CODE').getValue();
    	var TO_BRCH_NO=Ext.getCmp('idTO_BRCH_NO').getValue();
    	var FK_BRCH_NO=Ext.getCmp('idFK_BRCH_NO').getValue();
    	var CARD_TYPE=Ext.getCmp('idCARD_TYPE').getValue();
    	var CHANNEL_NO=Ext.getCmp('idCHANNEL_NO').getValue();
    	var BUSINESS_TYPE=Ext.getCmp('idBUSINESS_TYPE').getValue();
    	var TXN_NUM=Ext.getCmp('idTXN_NUM').getValue();
    	var discCode=Ext.getCmp('discCode').getValue();
    	
    	/*if(mchtNo==''||mchtNo.length!=15){
    		showErrorMsg('请输入15位商户编号',mchntForm);
    		return;
    	}*/
    	if(FK_BRCH_NO==''){
    		showErrorMsg('请选择发卡机构',mchntForm);
    		return;	
    	}
    	if(TERMID==''){
    		showErrorMsg('请输入终端代码',mchntForm);
    		return;
    	}
    	if(discCode==''){
    		showErrorMsg('请选择计费代码',mchntForm);
    		return;
    	}
    	if(CITY_CODE==''){
    		showErrorMsg('请选择地区码',mchntForm);
    		return;	
    	}
    	if(TO_BRCH_NO==''){
    		showErrorMsg('请选择目的机构',mchntForm);
    		return;	
    	}
    	if(CARD_TYPE==''){
    		showErrorMsg('请选择卡类型',mchntForm);
    		return;	
    	}
    	if(CHANNEL_NO==''){
    		showErrorMsg('请选择交易渠道',mchntForm);
    		return;	
    	}
    	if(BUSINESS_TYPE==''){
    		showErrorMsg('请选择业务类型',mchntForm);
    		return;	
    	}
    	if(TXN_NUM==''){
    		showErrorMsg('请选择交易类型',mchntForm);
    		return;	
    	}
    	T20100.addDiscAlgo2(tmpNo,discCode,TERMID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM,function(ret){
    		var sta = '';
			var msg = '';
			var tmpNo = '';
    		for(var key in ret){
    			if(key=='result'){
    				sta = ret[key];
    			}else if(key=='msg'){
    				msg=ret[key];	
    			}else if(key=='tmpNo'){
    				tmpNo=ret[key];
    			}
    		}
    		if(sta=='N'){
    			Ext.getCmp('tmpNo').setValue(tmpNo);
    			feeStore.load({
				params: {
					start: 0,
					tmpNo: tmpNo
					}
				});
    		}else{
    			showErrorMsg(msg,mchntForm);	
    		}
    	});

    }
    
    
    var checkIds = "T";
    function subSave(){
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
			
			if(feeStore.getCount()<1){
				showErrorMsg('请添加商户计费信息!',mchntForm);
				return;
			}
			Ext.getCmp('verifyButton').enable();
			var flag = mchntForm.getForm().findField('clearType').getValue();
			var settleType=mchntForm.getForm().findField('settleType').getValue();
			var settleChn=mchntForm.getForm().findField('settleChn').getValue();
			var licenceNo = Ext.getCmp('licenceNo').getValue();
			/*if(licenceNo.trim() == ''){
	    		showErrorMsg('营业执照号不能为空或空字符串',mchntForm);
	    		return;
	    	}else */if(licenceNo.indexOf('O') != '-1'){
	   			showErrorMsg('营业执照号不能包含大写字母O',mchntForm);
				return;
	   		}
	    	if(mchntForm.findById('settleChn').getValue().substr(0,1) == "-"){
				showErrorMsg('商户结算方式T+N不能为负数，请重新填写',mchntForm);
				mchntForm.findById('settleChn').setValue('');
				return ;
			}
	    	if(mchntForm.findById('dirFlag').getValue()==true){
	    		var dirBankName=mchntForm.getForm().findField('dirBankName').getValue();
	    		var dirAccountName=mchntForm.getForm().findField('dirAccountName').getValue();
	    		var dirAccount=mchntForm.getForm().findField('dirAccount').getValue();
	    		if(dirBankName==''||dirAccountName==''||dirAccount==''){
	    			showErrorMsg('请填写定向委托账户信息',mchntForm);
	    			return;
	    		}
	    		
	    	}
//	    	if(mchntForm.findById('feeCycle').getValue()!='' && mchntForm.findById('feeCycle').getValue().substr(0,1) == "-"){
//				showErrorMsg('手续费结算方式T+N不能为负数，请重新填写',mchntForm);
//				mchntForm.findById('feeCycle').setValue('');
//				return ;
//			}
			if(settleChn>31){
//				showErrorMsg('商户结算方式应小于T+31!',mchntForm);
//				Ext.getCmp("tab").setActiveTab('settle');
			}else if((settleType=='0' || settleType=='1')&& settleChn > 15){//20120822添加的验证条件
//				showErrorMsg('日结商户结算方式应小于T+15!',mchntForm);
//				Ext.getCmp("tab").setActiveTab('settle');
			}else if(!verifyResult){
				//额外验证
                Ext.getCmp('verifyButton').enable();
				showErrorMsg('保存商户信息之前请验证商户号!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
			}else{
				
				if(Ext.getCmp('contact').getValue()==''){
		    		Ext.getCmp('contact').setValue(' ');
		    	}
				
				//Ext.getCmp('settleBankNmI').setValue(Ext.get('settleBankNmI').dom.value);
				btn.disable();
				frm.submit({
					url: 'T20100Action_add.asp',
					waitTitle : '请稍候',
					waitMsg : '正在提交表单数据,请稍候...',
					success : function(form, action) {
						hasSub = false;
						checkIds = "T";
						showSuccessAlert("提交成功",mchntForm);
						btn.enable();
						frm.reset();
						feeGridPanel1.reset();
						hasUpload = "0";
						resetImagesId();
						feeStore.removeAll();
						Ext.getCmp('tmpNo').setValue("");
						feeStore.load({
						params: {
							start: 0,
							tmpNo: Ext.getCmp('tmpNo').getValue()
							}
						});
						storeImg.reload({
						params: {
							start: 0,
							imagesId: imagesId
							}
						});
						storeImg1.reload({
						params: {
							start: 0,
							imagesId: imagesId
							}
						});
					},
					failure : function(form,action) {
							btn.enable();
							hasSub = false;
							Ext.getCmp('verifyButton').disable();
							if (action.result.msg.substr(0,2) == 'CZ') {
								
								Ext.MessageBox.show({
									msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
									title: '确认提示',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.YESNO,
									icon: Ext.MessageBox.QUESTION,
									modal: true,
									width: 500,
									fn: function(bt) {
										if(bt == 'yes') {
											checkIds = "F";
											subSave();
										}
									}
								});
							} else {
								showErrorMsg(action.result.msg,mchntForm);
								Ext.getCmp('verifyButton').enable();
							}
					},
					params: {
						txnId: '20101',
						subTxnId: '01',
						hasUpload: hasUpload,
						imagesId: imagesId,
						checkIds: checkIds,
						//正常流程
						txType : '00'
					}
			});
		}
	}else{
		//自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic'||tab == 'basic1'|| tab == 'append' || tab == 'append1' || tab == 'append2' || tab == 'settle1'|| tab == 'settle2' ||tab == 'settle' || tab == 'feeamt'||tab=='standardConfig'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	}
    );
	}}

    mchntForm.getForm().findField('acqInstId').setValue(brhId);

    //mchntForm.getForm().findField('signInstId').setValue(cupBrhId);

    //为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    });

    gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue()
		});
	});
	feeStore.on('beforeload',function(){
		Ext.apply(this.baseParams, {
			start: 0,
			tmpNo: Ext.getCmp('tmpNo').getValue()
		});
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});
Ext.onReady(function() {
	//保存是否验证成功的变量
	//修改时默认为true,当信息变动时为false
	var verifyResult = true;
	
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
	
	 // 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20100Action_upload.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.jpg;*.png;*.jpeg;*.zip;*.rar',
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

	//渠道数据集
	var feeDiscStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('FEE_DISC_NORMAL',function(ret){
		feeDiscStore.loadData(Ext.decode(ret));
	});
	
	
	
	
	var clearTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡'],['O','他行对公账户'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore1 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡'],['O','他行对公账户'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['P','本行对私账户或单位卡'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
    var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	
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
	var fm = Ext.form;

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
	
	var store_d = new Ext.data.Store({
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
	
	var cm_d = new Ext.grid.ColumnModel({
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
	 var detailGrid_d = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store_d,
		disableSelection: true,
		cm: cm_d,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		}/*,
		bbar: new Ext.PagingToolbar({
			store: store_d,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
    var detailGrid = new Ext.grid.GridPanel({
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
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

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
	
	var gridStore_d = new Ext.data.Store({
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
	gridStore_d.load({
		params: {
			start: 0
		}
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 60},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	
	var gridColumnModel_d = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 60},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	
	var gridPanel_d = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore_d,
		disableSelection: true,
		//sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel_d,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		}/*,
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
	
	var gridPanel = new Ext.grid.GridPanel({
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
	gridPanel.getStore().on('beforeload',function() {
		//Ext.getCmp('setup').disable();
	});

	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
	//	Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	store.on('beforeload', function() {
		if (gridPanel.getSelectionModel().hasSelection()) {
			Ext.apply(this.baseParams, {
	            start: 0,
	            discCd: gridPanel.getSelectionModel().getSelected().data.discCd
	        });
		}else{
			showErrorMsg('请选择计费算法',groupWin);
		}
		
	});
	gridStore.on('beforeload', function() {
		store.removeAll();
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
//		autoLoad: false
		//对数据进行排序
		//field:排序的字段                 direction:排序的方式
	 	//sortInfo: {field: 'name', direction: 'DESC'}  
	});
	
	feeStore1.load({
		params:{
			start: 0,
			mchtNo: mchntId
		}
	});
	
	feeStore1.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: mchntId
		});
	});
	
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
	
	var imagesForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
        items: [{
        	xtype: 'panel',
        	height : 140,
			width : 500,
        	region: 'center',
			autoScroll: true,
			items : []
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
//			pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 5,
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
			id: 'setEdit1',
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
		},'-',{
			xtype: 'button',
			width: '60',
			iconCls: 'upload',
			text: '上传',
			id: 'upload1',
			disabled: true,
			handler:function() {
				//hasUpload = "1";
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				var dialog1 = new UploadDialog({
					uploadUrl : 'T20100Action_upload1.asp',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB',
					fileTypes : '*.CSV;*.csv;*.jpg;*.png;*.jpeg;',
					fileTypesDescription : '图片文件',
					title: '受益人图片文件上传',
					scope : this,
					animateTarget: 'upload',
					onEsc: function() {
						this.hide();
					},
					postParams: {
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration'),
						txnId: '20101',
						subTxnId: '08',
					}
				});
				dialog1.show();
			}
		},'-',{
			xtype: 'button',
			width: 60,
			text: '下载',
			id: 'download',
			iconCls: 'download',
			disabled: true,
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
//				cityCodeForm1.getForm().findField('beneficiaryId').setValue(rec.get('beneficiaryId'));
//				cityCodeForm1.getForm().findField('beneficiaryExpiration').setValue(rec.get('beneficiaryExpiration'));
				Ext.Ajax.request({
					url: 'T20100Action_download.asp',
//					waitMsg: '正在下载文件，请稍等......',
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+ rspObj.msg;
					},
					failure: function() {
						hideMask();
					},
					params: {
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
			}
		},'-',{
			xtype: 'button',
			width: 60,
			text: '预览',
			iconCls: 'query',
			id: 'images1',
			disabled: true,
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				imagesWin.show();
				imagesWin.center();
				/*picWin.show();
				for(var i = 0;i < storeImg2.getCount();i++){
					var rec = storeImg2.getAt(i).data;
					document.getElementById('bigPic').innerHTML = '<img width="120" height="90" src="' + 
					Ext.contextPath + '/PrintImage?fileName=' + rec.fileName + '&width=120&height=90" />';
				}*/
				
			}
		}]
	});
	
	//******************图片处理部分**********开始********
	var storeImg2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf1'
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
	
	var storeImg3 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getZipInf1'
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
	
	/*var custom5 = new Ext.Resizable('showBigPic1', {
	    wrap:true,
	    pinned:true,
	    minWidth: 50,
	    minHeight: 50,
	    preserveRatio: true,
	    dynamic:true,
	    handles: 'all',
	    draggable:true
	});
	var customEl5 = custom5.getEl();
	
	document.body.insertBefore(customEl5.dom, document.body.firstChild);
	customEl5.on('dblclick', function(){
		customEl5.puff();
	});
	customEl5.hide(true);*/
	
	/*function showPIC5(id){
 		var rec = storeImg2.getAt(id.substring(5)).data;
 		//custom5.resizeTo(rec.width1, rec.height1);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		//customEl5.center();
	    //customEl5.show(true);
 	}*/
	
	
	
	storeImg2.on('load',function(){
		for(var i=0;i<storeImg2.getCount();i++){
			var rec = storeImg2.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC5(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC5(val.id);
        	});
		}
	});
	
	storeImg3.on('load',function(){
		for(var i=0;i<storeImg3.getCount();i++){
			var rec = storeImg3.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC6(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC6(val.id);
        	});
		}
	});
	
	function delPIC5(id){
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				//alert(id);//btDels0  0
				var rec = storeImg2.getAt(id.substring(6)).data;
				//获取选中的第一条记录
				var rec1 = feeGridPanel1.getSelectionModel().getSelected();
		 		T20100.deleteImgFile1(rec.fileName,rec1.get('beneficiaryId'),rec1.get('beneficiaryExpiration'),function(ret){
		 			if("S" == ret){
		 				storeImg2.reload({
							params: {
								start: 0,
								imagesId: imagesId,
								beneficiaryId: rec1.get('beneficiaryId'),
								beneficiaryExpiration: rec1.get('beneficiaryExpiration')
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	
	function delPIC6(id){
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg3.getAt(id.substring(7)).data;
				//获取选中的第一条记录
				var rec1 = feeGridPanel1.getSelectionModel().getSelected();
		 		T20100.deleteImgFile1(rec.fileName,rec1.get('beneficiaryId'),rec1.get('beneficiaryExpiration'),function(ret){
		 			if("S" == ret){
		 				storeImg3.reload({
							params: {
								start: 0,
								imagesId: imagesId,
								beneficiaryId: rec1.get('beneficiaryId'),
								beneficiaryExpiration: rec1.get('beneficiaryExpiration')
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	
	function downPIC5(id){
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg2.getAt(id.substring(5)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}
	
	function downPIC6(id){
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg3.getAt(id.substring(6)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}
	
	var dataview2 = new Ext.DataView({
		frame: true,
//		region: 'north',
        store: storeImg2,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div>',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage1?fileName={downPath}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btDw}" value="下载"><input type="button" id="{btDel}" value="删除"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones2',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	var dataview3 = new Ext.DataView({
		frame: true,
//		region: 'south',
        store: storeImg3,
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
        id: 'phones3',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	
	//******************图片处理部分**********结束********
	
	var imageForm = new Ext.FormPanel({
        frame: true,
        layout: 'border',
        items: [{
        	xtype: 'panel',
        	region: 'center',
			autoScroll: true,
			items : [dataview2,dataview3]
        }]
	});
	
	var imagesWin = new Ext.Window({
		title: '受益人图片预览',
		layout: 'fit',
		animateTarget: 'picPanel',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		draggable: false,
		width: 400,
		height: 300,
		items: [imageForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '刷新',
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
			}
		},{
			text: '关闭',
			handler: function() {
				imagesWin.hide();
			}
		}]
	});
	
	
	/*var picWin = new Ext.Window({
		title: '图片预览',
		layout: 'fit',
		animateTarget: 'picPanel',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		draggable: false,
		width: 415,
		height: 430,
		layout: 'fit',
		closeAction: 'hide',
		resizable: false,
		html: '<div id="bigPic"><img style="width: 400px;heigth: 400px;"/></div>',
		buttons: [{
			text: '刷新',
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
			}
		},{
			text: '关闭',
			handler: function() {
				picWin.hide();
			}
		}]
	});*/
	
	
	//选中数据显示按钮
	feeGridPanel1.getSelectionModel().on({
		'rowselect':function(){
			//修改按钮显示
			Ext.getCmp("setEdit1").enable();
			//上传按钮显示
			Ext.getCmp("upload1").enable();
			//下载按钮显示
			Ext.getCmp("download").enable();
			//预览按钮显示
			Ext.getCmp("images1").enable();
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
			width:150,
			//hedden: true
		},{
			//输入框类型
			xtype: 'textfield',
			//label文本值
			fieldLabel: '受益所有人*',
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
			//hidden: true
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
				cityCodeForm2.getForm().submit({
					url: 'T201001Action.asp?method=add',
					waitMsg: '正在提交，请稍后......',
					success: function(form,action) {
						showSuccessMsg(action.result.msg,cityCodeForm2);
						//添加成功后窗口关闭
						cityCodeWin1.hide();
						var mcht = mchntForm.getForm().findField('mchtNoInput').getValue();
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
						mchtNoInPut: mchntForm.getForm().findField('mchtNoInput').getValue(),
						txnId: '201001',
						subTxnId: '05'
					}
				});
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
	        	if(mchntForm.getForm().findField('mchtNoInput').getValue()==''){
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
							mchtNoInPut: mchntForm.getForm().findField('mchtNoInput').getValue(),
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
	
	
	/*************************************清算列表*********************************/
	var curOp = '01';
	
	// 终端数据集
	var termStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('TERM_ID_MCHT',mchntId,function(ret){
		termStore.loadData(Ext.decode(ret));
	});
	
	//添加修改form
	var setEditForm = new Ext.FormPanel({
		frame: true,
		autoHeight: true,
		width: 700,
//		defaultType: 'textfield',
		labelWidth: 130,
		layout:'column',
		waitMsgTarget: true,
		items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combo',
						store: termStore,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						id:'setTermId',
						hiddenName: 'termId1',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						allowBlank: false,
						fieldLabel: '终端编号*',
						anchor: '60%',
						listWidth: 160
					}]
				},{
					columnWidth: .5,
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
	    		columnWidth: .5,
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
    		columnWidth: .5,
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
			columnWidth: .5,
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
					columnWidth: .5,
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
	        		columnWidth: .5,
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
			        		columnWidth: .5,
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
							                    var value = setEditForm.getForm().findField('idsettleRpt').getValue();
//							                    alert(value);
//							                    var etpsAttrVal = mchntForm.getForm().findField('idEtpsAttr').getValue();
//							                    alert('etpsAttrVal:'+etpsAttrVal);
							                    if(value == '1') {  //对私
							                    	/*if(etpsAttrVal!='02'){
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
							                   		}*/
							                    	
//							                    	Ext.getCmp('settleAcctNm').setValue('');
//								                    Ext.getCmp('feeAcctNm').setValue(Ext.getCmp('manager').getValue());
								                    
								                    //法人账户必填
													Ext.getCmp('corpBankName1').show(); 
													Ext.getCmp('corpBankName').allowBlank=false;
													setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称*');
													Ext.getCmp('feeAcct1').show(); 
													Ext.getCmp('feeAcct').allowBlank=false;
													setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户*'); 
													Ext.getCmp('bankAccountCode1').show(); 
													Ext.getCmp('bankAccountCode').allowBlank=true;
													setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 
													//定向委托账户非必填
													Ext.getCmp('dirBankName1').hide();
													Ext.getCmp('dirBankName').allowBlank=true;
//													setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
													Ext.getCmp('dirAccountName1').hide();
													Ext.getCmp('dirAccountName').allowBlank=true;
//													setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
													Ext.getCmp('dirAccount1').hide();
													Ext.getCmp('dirAccount').allowBlank=true;
//													setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
													Ext.getCmp('dirBankCode1').hide();
													Ext.getCmp('dirBankCode').allowBlank=true;
//													setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
													//add
													Ext.getCmp('legalNam1').show();
													Ext.getCmp('legalNam').allowBlank=false;
													setEditForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称*');
													Ext.getCmp('companyNam1').hide();
													Ext.getCmp('companyNam').allowBlank=true;
													setEditForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称');
													Ext.getCmp('dir_open_bank_1').hide();
													Ext.getCmp('dirOpenBank').allowBlank=true;
//													setEditForm.getForm().findField('dirOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称');
													Ext.getCmp('dir_bank_province_1').hide();
													Ext.getCmp('dirBankProvince').allowBlank=true;
//													setEditForm.getForm().findField('dirBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省');
													Ext.getCmp('dir_bank_city_1').hide();
													Ext.getCmp('dirBankCity').allowBlank=true;
//													setEditForm.getForm().findField('dirBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市');
													
													Ext.getCmp('comp_open_bank_1').hide();
													Ext.getCmp('compOpenBank').allowBlank=true;
//													setEditForm.getForm().findField('compOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称');
													Ext.getCmp('comp_bank_province_1').hide();
													Ext.getCmp('compBankProvince').allowBlank=true;
//													setEditForm.getForm().findField('compBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省');
													Ext.getCmp('comp_bank_city_1').hide();
													Ext.getCmp('compBankCity').allowBlank=true;
//													setEditForm.getForm().findField('compBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市');
														  	
							                    	Ext.getCmp('corp_open_bank_1').show();
													Ext.getCmp('corpOpenBank').allowBlank=false;
													setEditForm.getForm().findField('corpOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称*');
													Ext.getCmp('corp_bank_province_1').show();
													Ext.getCmp('corpBankProvince').allowBlank=false;
													setEditForm.getForm().findField('corpBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省*');
													Ext.getCmp('corp_bank_city_1').show();
													Ext.getCmp('corpBankCity').allowBlank=false;
													setEditForm.getForm().findField('corpBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市*');

													//公司账户必填
													Ext.getCmp('compAccountBankName1').hide();
													Ext.getCmp('compAccountBankName').allowBlank=true;
													setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
													Ext.getCmp('settleAcct1').hide();
													Ext.getCmp('settleAcct').allowBlank=true;
													setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
													Ext.getCmp('compAccountBankCode1').hide();
													Ext.getCmp('compAccountBankCode').allowBlank=true;
													setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
													
													
									        				/*Ext.getCmp('settleAcctNm').setValue('');
								                        	Ext.getCmp('feeAcctNm').setValue(Ext.getCmp('manager').getValue());
									        				//法人账户必填
								        				    Ext.getCmp('corpBankName').allowBlank=false;
								        				  	setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称*');
								        				  	Ext.getCmp('feeAcct').allowBlank=false;
								        				  	setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号*'); 
								        				  	Ext.getCmp('bankAccountCode').allowBlank=false;
								        				  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码*'); 
								        				    //定向委托账户非必填
								        				    Ext.getCmp('dirBankName').allowBlank=true;
								        				  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称');
								        				  	Ext.getCmp('dirAccountName').allowBlank=true;
								        				  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名'); 
								        				  	Ext.getCmp('dirAccount').allowBlank=true;
								        				  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
								        				  	Ext.getCmp('dirBankCode').allowBlank=true;
									        			  	setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码'); 
								        				    //公司账户非必填
								        					Ext.getCmp('compAccountBankName').allowBlank=true;
								        					setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司帐号开户行名称');
								        					Ext.getCmp('settleAcct').allowBlank=true;
								        					setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号'); 	
								        					Ext.getCmp('compAccountBankCode').allowBlank=true;
										        			setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); */									        		  
								        				
							        			} else if(value=='2'){ //对公
							        				
							        				//公司账户必填
													Ext.getCmp('compAccountBankName1').show();
													Ext.getCmp('compAccountBankName').allowBlank=false;
													setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
													Ext.getCmp('settleAcct1').show();
													Ext.getCmp('settleAcct').allowBlank=false;
													setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
													Ext.getCmp('compAccountBankCode1').show();
													Ext.getCmp('compAccountBankCode').allowBlank=true;
													setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
													//定向委托账户非必填
													Ext.getCmp('dirBankName1').hide();
													Ext.getCmp('dirBankName').allowBlank=true;
//													setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
													Ext.getCmp('dirAccountName1').hide();
													Ext.getCmp('dirAccountName').allowBlank=true;
//													setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
													Ext.getCmp('dirAccount1').hide();
													Ext.getCmp('dirAccount').allowBlank=true;
//													setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
													Ext.getCmp('dirBankCode1').hide();
													Ext.getCmp('dirBankCode').allowBlank=true;
//													setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
													//法人账户非必填
													Ext.getCmp('corpBankName1').hide();
													Ext.getCmp('corpBankName').allowBlank=true;
//													setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
													Ext.getCmp('feeAcct1').hide();
													Ext.getCmp('feeAcct').allowBlank=true;
//													setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户'); 	
													Ext.getCmp('bankAccountCode1').hide();
												  	Ext.getCmp('bankAccountCode').allowBlank=true;
//												  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码');
												  	//add
												  	Ext.getCmp('legalNam1').hide();
												  	Ext.getCmp('legalNam').allowBlank=true;
//												  	setEditForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称*');
												  	Ext.getCmp('companyNam1').show();
												  	Ext.getCmp('companyNam').allowBlank=false;
												  	setEditForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称*');
												  	
												  	Ext.getCmp('dir_open_bank_1').hide();
												  	Ext.getCmp('dirOpenBank').allowBlank=true;
//												  	setEditForm.getForm().findField('dirOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称');													  	
												  	Ext.getCmp('dir_bank_province_1').hide();
													Ext.getCmp('dirBankProvince').allowBlank=true;
//												  	setEditForm.getForm().findField('dirBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省');
												  	Ext.getCmp('dir_bank_city_1').hide();
												  	Ext.getCmp('dirBankCity').allowBlank=true;
//												  	setEditForm.getForm().findField('dirBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市');
												  	
												  	Ext.getCmp('corp_open_bank_1').hide();
												  	Ext.getCmp('corpOpenBank').allowBlank=true;
//												  	setEditForm.getForm().findField('corpOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称');
												  	Ext.getCmp('corp_bank_province_1').hide();
												  	Ext.getCmp('corpBankProvince').allowBlank=true;
//												  	setEditForm.getForm().findField('corpBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省');
												  	Ext.getCmp('corp_bank_city_1').hide();
												  	Ext.getCmp('corpBankCity').allowBlank=true;													  	
//													setEditForm.getForm().findField('corpBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市');
				 	
													Ext.getCmp('comp_open_bank_1').show(); 
												  	Ext.getCmp('compOpenBank').allowBlank=false;
													setEditForm.getForm().findField('compOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称*');
													Ext.getCmp('comp_bank_province_1').show(); 
													Ext.getCmp('compBankProvince').allowBlank=false;
													setEditForm.getForm().findField('compBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省*');
													Ext.getCmp('comp_bank_city_1').show(); 
													Ext.getCmp('compBankCity').allowBlank=false;
													setEditForm.getForm().findField('compBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市*');
													  	
							        				
							        				/*Ext.getCmp('settleAcctNm').setValue(Ext.getCmp('mchtNm').getValue());
						                        	Ext.getCmp('feeAcctNm').setValue('');
							        				//公司账户必填
							        				Ext.getCmp('compAccountBankName').allowBlank=false;
							        				setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司帐号开户行名称*');
							        				Ext.getCmp('settleAcct').allowBlank=false;
							        				setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号*'); 
							        				Ext.getCmp('compAccountBankCode').allowBlank=false;
							        				setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
							        				//定向委托账户非必填
							        				Ext.getCmp('dirBankName').allowBlank=true;
							        				setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称');
							        				Ext.getCmp('dirAccountName').allowBlank=true;
							        			  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名'); 
							        				Ext.getCmp('dirAccount').allowBlank=true;
							        				setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
							        				Ext.getCmp('dirBankCode').allowBlank=true;
								        			setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码'); 
							        				//法人账户非必填
							        				Ext.getCmp('corpBankName').allowBlank=true;
							        				setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
							        				Ext.getCmp('feeAcct').allowBlank=true;
							        				setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号');
							        				Ext.getCmp('bankAccountCode').allowBlank=true;
							        				setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码');*/ 

							        			}else if(value=='3'){
//							        				Ext.getCmp('settleAcctNm').setValue('');
//							        				Ext.getCmp('feeAcctNm').setValue('');		
							        				//法人账户非必填
													Ext.getCmp('corpBankName1').hide();
													Ext.getCmp('corpBankName').allowBlank=true;
//													setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
													Ext.getCmp('feeAcct1').hide();
													Ext.getCmp('feeAcct').allowBlank=true;
//													setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户'); 
													Ext.getCmp('bankAccountCode1').hide();
													Ext.getCmp('bankAccountCode').allowBlank=true;
//													setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 
													//定向委托账户必填
													Ext.getCmp('dirBankName1').show();
													Ext.getCmp('dirBankName').allowBlank=false;
													setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称*');
													Ext.getCmp('dirAccountName1').show();
													Ext.getCmp('dirAccountName').allowBlank=false;
													setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名*'); 
													Ext.getCmp('dirAccount1').show();
													Ext.getCmp('dirAccount').allowBlank=false;
													setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*');
													Ext.getCmp('dirBankCode1').show();
													Ext.getCmp('dirBankCode').allowBlank=true;
													setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户账户开户行机构代码'); 
													//add
													Ext.getCmp('legalNam1').hide();
													Ext.getCmp('legalNam').allowBlank=true;
//													setEditForm.getForm().findField('legalNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户名称');
													Ext.getCmp('companyNam1').hide();
													Ext.getCmp('companyNam').allowBlank=true;
//													setEditForm.getForm().findField('companyNam').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账户名称');
													  	
													Ext.getCmp('comp_open_bank_1').hide();
													Ext.getCmp('compOpenBank').allowBlank=true;
//													setEditForm.getForm().findField('compOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户总行名称');
													Ext.getCmp('comp_bank_province_1').hide();
													Ext.getCmp('compBankProvince').allowBlank=true;
//													setEditForm.getForm().findField('compBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在省');
													Ext.getCmp('comp_bank_city_1').hide();
													Ext.getCmp('compBankCity').allowBlank=true;
//													setEditForm.getForm().findField('compBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号开户行所在市');
														  	
													Ext.getCmp('corp_open_bank_1').hide();
													Ext.getCmp('corpOpenBank').allowBlank=true;
//													setEditForm.getForm().findField('corpOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户总行名称');
													Ext.getCmp('corp_bank_province_1').hide();
													Ext.getCmp('corpBankProvince').allowBlank=true;
//													setEditForm.getForm().findField('corpBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在省');
													Ext.getCmp('corp_bank_city_1').hide();
													Ext.getCmp('corpBankCity').allowBlank=true;
//													setEditForm.getForm().findField('corpBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行所在市');
													  	  	
													Ext.getCmp('dir_open_bank_1').show();
													Ext.getCmp('dirOpenBank').allowBlank=false;
													setEditForm.getForm().findField('dirOpenBank').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号开户总行名称*');
													Ext.getCmp('dir_bank_province_1').show();
													Ext.getCmp('dirBankProvince').allowBlank=false;
													setEditForm.getForm().findField('dirBankProvince').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在省*');
													Ext.getCmp('dir_bank_city_1').show();
													Ext.getCmp('dirBankCity').allowBlank=false;
													setEditForm.getForm().findField('dirBankCity').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托开户行所在市*');
														
							        				//公司账户必填
													Ext.getCmp('compAccountBankName1').hide();
													Ext.getCmp('compAccountBankName').allowBlank=true;
													setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
													Ext.getCmp('settleAcct1').hide();
													Ext.getCmp('settleAcct').allowBlank=true;
													setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
													Ext.getCmp('compAccountBankCode1').hide();
													Ext.getCmp('compAccountBankCode').allowBlank=true;
													setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
													
							        					/*Ext.getCmp('settleAcctNm').setValue('');
							        					Ext.getCmp('feeAcctNm').setValue('');
							        					//定向委托账户必填
								        			    Ext.getCmp('dirBankName').allowBlank=false;
								        			  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称*');
								        			  	Ext.getCmp('dirAccountName').allowBlank=false;
								        			  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名*'); 
								        			  	Ext.getCmp('dirAccount').allowBlank=false;
								        			  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*'); 
								        			  	Ext.getCmp('dirBankCode').allowBlank=false;
								        			  	setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码*'); 
								        				//公司账户非必填
								        				Ext.getCmp('compAccountBankName').allowBlank=true;
								        				setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司帐号开户行名称');
								        				Ext.getCmp('settleAcct').allowBlank=true;
								        				setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号'); 
								        				Ext.getCmp('compAccountBankCode').allowBlank=true;
									        			setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
								        				//法人账户非必填
								        				Ext.getCmp('corpBankName').allowBlank=true;
								        				setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
								        				Ext.getCmp('feeAcct').allowBlank=true;
								        				setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号');
								        				Ext.getCmp('bankAccountCode').allowBlank=true;
								        				setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码');*/
							        				}
							        				 
							        			}
						                    }
					                 //   }
									
								}]
				        },{
			        		columnWidth: .5,
				        	xtype: 'panel',
				        	id: 'currAccount1',
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
			        		columnWidth: .5,
				        	xtype: 'panel',
				        	id: 'compAccountBankName1',
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '对公帐号开户行名称*',
								maxLength: '60',
								allowBlank: false,
								id: 'compAccountBankName',
								name: 'compAccountBankName'
				        	}]
						},{
			        		columnWidth: .5,
				        	xtype: 'panel',
				        	layout: 'form',
							id: 'compAccountBankCode1',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '对公帐号开户行机构代码',
								maxLength: '60',
								allowBlank: true,
								id: 'compAccountBankCode',
								name: 'compAccountBankCode'
				        	}]
						},{
	        		//columnWidth: .5,
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '对公账号开户名++++',
						maxLength: 60,
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'settleAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'settleAcct1',
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
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
					id: 'corpBankName1',
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
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
					id: 'bankAccountCode1',
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
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						//fieldLabel: '结算账户1账户户名*',
						fieldLabel: '对私账号开户名*++++',
						maxLength: 60,	
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'feeAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
					columnWidth: .5,
		        	xtype: 'panel',
		        	id: 'feeAcct1',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						//fieldLabel: '结算账户1账户户号*',
						fieldLabel: '对私账号*',
						maxLength: 40,	
						vtype: 'isNumber',
						//width:150,
						id: 'feeAcct'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	id: 'dirBankName1',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托帐号开户行名称',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankName',
						name: 'dirBankName'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'dirBankCode1',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托帐号帐号开户行机构代码',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankCode',
						name: 'dirBankCode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'dirAccountName1',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托帐号开户名',
						maxLength: '60',
						allowBlank: true,
						id: 'dirAccountName',
						name: 'dirAccountName'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'dirAccount1',
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
	        		//columnWidth: .5,
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'legalNam1',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '对私账户名称*',
						maxLength: 100,
						vtype: 'isOverMax',
						//width:150,
						id: 'legalNam'
		        	}]
				},{
	        		//columnWidth: .5,
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'companyNam1',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '对公账户名称*',
						maxLength: 100,
						vtype: 'isOverMax',
						//width:150,
						id: 'companyNam'
		        	}]
				},
				//add	
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
		        	id:'dir_open_bank_1',
		        	items: [{
				    xtype: 'dynamicCombo_1',
					methodName: 'getdirOpenBank',
					fieldLabel: '定向委托账号开户总行名称*',
					allowBlank: false,
					width:150,
					hiddenName: 'dirOpenBank_1',
					id:'dirOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'dir_bank_province_1',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankProvince',
					fieldLabel: '定向委托开户行所在省*',
					allowBlank: false,
					width:150,
					hiddenName: 'dirBankProvince_1',
					id:'dirBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
		        	layout: 'form',
					id:'dir_bank_city_1',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getdirBankCity',
					allowBlank: false,
					fieldLabel: '定向委托开户行所在市*',
					width:150,
					hiddenName: 'dirBankCity_1',
					id:'dirBankCity'
		        	}]
				},{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'comp_open_bank_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					methodName: 'getOpenBank',
					fieldLabel: '对公账号开户总行名称*',
					width:150,
					hiddenName: 'compOpenBank_1',
					id:'compOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'comp_bank_province_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankProvince',
					allowBlank: false,
					fieldLabel: '对公账号开户行所在省*',
					width:150,
					hiddenName: 'compBankProvince_1',
					id:'compBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'comp_bank_city_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getBankCity',
					allowBlank: false,
					fieldLabel: '对公账号开户行所在市*',
					width:150,
					hiddenName: 'compBankCity_1',
					id:'compBankCity'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'corp_open_bank_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					allowBlank: false,
					methodName: 'getCorpOpenBank',
					fieldLabel: '对私账号开户总行名称*',
					width:150,
					hiddenName: 'corpOpenBank_1',
					id:'corpOpenBank'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'corp_bank_province_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankProvince',
					allowBlank: false,
					fieldLabel: '对私账号开户行所在省*',
					width:150,
					hiddenName: 'corpBankProvince_1',
					id:'corpBankProvince'
		        	}]
				},
				{
					columnWidth: .5,
		   			xtype: 'panel',
					id:'corp_bank_city_1',
		        	layout: 'form',
		        	items: [{
					columnWidth: .33,
					allowBlank: false,
					xtype: 'dynamicCombo_1',
					methodName: 'getCorpBankCity',
					fieldLabel: '对私账号开户行所在市',
					width:150,
					hiddenName: 'corpBankCity_1',
					id:'corpBankCity'
		        	}]
				},
				//end
				{
	        		columnWidth: .5,
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
			}],
		buttonAlign: 'center',
		buttons: [{
			text: '提交',
			handler: function() {
				if(setEditForm.getForm().isValid()) {
					var value = setEditForm.getForm().findField('idsettleRpt').getValue();
					if(value == '1') {   //如果选择的是法人账户
				//		alert(Ext.getCmp('feeAcctNm').getValue());
						Ext.getCmp('legalNam').setValue(Ext.getCmp('manager').getValue());		
				//		alert(Ext.getCmp('feeAcctNm').getValue());
					}
					setEditForm.getForm().submit({
						url:'T20101Action_set' + (curOp == '01'?'Add.asp':'Update.asp'),
						waitMsg: '正在提交商户清算信息，请稍候......',
						params: {
							mchtNo: mchntId,
							termId:Ext.getCmp('setTermId').getValue(),
							txnId: '20101',
							subTxnId: '07',
						  mchtNm:Ext.getCmp('settleAcctNm').getValue(),
//						  legalNam:Ext.getCmp('legalNam').getValue()
						  manager:Ext.getCmp('legalNam').getValue()
							
							
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,setEditForm);
							setEditWin.hide();
							// 重新加载商户清算
							setGridPanel.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,setEditForm);
						}
					});
				}
			}
		}, {
			text: '重填',
			handler: function() {
			if(curOp=='01'){
				setEditForm.getForm().resetAll();
				setEditForm.getForm().clearInvalid();
			}else{
			setEditForm.getForm().reset();
			var rec = setGridPanel.getSelectionModel().getSelected();
			 var value =rec.get('settleRpt');
			 if(value == '1') {
				Ext.getCmp('settleAcct').disable();
				Ext.getCmp('settleAcctNm').disable();;
			} else {
				Ext.getCmp('settleAcct').enable();
				Ext.getCmp('settleAcctNm').enable();
			}
			 }
			}
		}]
	});
	
	
	// 
	var setEditWin = new Ext.Window({
		title : '商户清算信息维护',
		animateTarget : 'modifyBt',
		layout : 'fit',
		width : 800,
		closeAction : 'hide',
		resizable : false,
		closable : true,
		modal : true,
		autoHeight : true,
		items : [ setEditForm ]
	});
	
	
	
		//store
	
	var setStore = new Ext.data.Store({
	
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfsettle'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'currAccount',mapping: 'currAccount'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'compAccountBankCode',mapping: 'compAccountBankCode'},
			{name: 'compAccountBankName',mapping: 'compAccountBankName'},
			{name: 'companyNam',mapping: 'companyNam'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'bankAccountCode',mapping: 'bankAccountCode'},
			{name: 'corpBankName',mapping: 'corpBankName'},
			
			{name: 'legalNam',mapping: 'legalNam'},
			
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'dirBankCode',mapping: 'dirBankCode'},
			{name: 'dirBankName',mapping: 'dirBankName'},
			{name: 'dirAccountName',mapping: 'dirAccountName'},
			{name: 'dirAccount',mapping: 'dirAccount'},
			{name: 'dirFlag',mapping: 'dirFlag'},
			{name: 'autoFlag',mapping: 'autoFlag'},
			{name: 'holidaySetFlag',mapping: 'holidaySetFlag'},
			{name: 'creFlag',mapping: 'creFlag'},
			{name: 'returnFeeFlag',mapping: 'returnFeeFlag'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'settleChn',mapping: 'settleChn'},
//			{name: 'companyNam',mapping: 'companyNam'},
			//add
			{name: 'dirOpenBank',mapping: 'dirOpenBank'},
			{name: 'dirBankProvince',mapping: 'dirBankProvince'},
			{name: 'dirBankCity',mapping: 'dirBankCity'},
			{name: 'compOpenBank',mapping: 'compOpenBank'},
			{name: 'compBankProvince',mapping: 'compBankProvince'},
			{name: 'compBankCity',mapping: 'compBankCity'},
			{name: 'corpOpenBank',mapping: 'corpOpenBank'},
			{name: 'corpBankProvince',mapping: 'corpBankProvince'},
			{name: 'corpBankCity',mapping: 'corpBankCity'}
		]),
		autoLoad: false
	});
	

				var setColumnModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header : '终端号',dataIndex : 'termId',sortable : true,width : 60}, 
					{
						header : '结算账户类型',
						dataIndex : 'settleRpt',
						sortable : true,
						width : 60,
						renderer : settleRptVal
					}, {
						header : '公司帐号开户行名称',
						dataIndex : 'compAccountBankName',
						sortable : true,
						width : 100
					}, {
						header : '公司帐号开户行机构代码',
						dataIndex : 'compAccountBankCode',
						sortable : true,
						width : 100
					},{
						header : '对公账户名称',
						dataIndex : 'companyNam',
						sortable : true,
						width : 100
					}, {
						header : '对公账号',
						dataIndex : 'settleAcct',
						sortable : true,
						width : 100
					}, {
						header : '法人账号开户行名称',
						dataIndex : 'corpBankName',
						sortable : true,
						width : 100
					}, {
						header : '对私账号开户行机构代码',
						dataIndex : 'bankAccountCode',
						sortable : true,
						width : 100
					}, {
						header : '对私账户名称',
						dataIndex : 'legalNam',
						sortable : true,
						width : 100
					}, {
						header : '对私账号',
						dataIndex : 'feeAcct',
						sortable : true,
						width : 100
					}, {
						header : '定向委托帐号开户行名称',
						dataIndex : 'dirBankName',
						sortable : true,
						width : 100
					}, {
						header : '定向委托帐号帐号开户行机构代码',
						dataIndex : 'dirBankCode',
						sortable : true,
						width : 100
					}, {
						header : '定向委托帐号开户名',
						dataIndex : 'dirAccountName',
						sortable : true,
						width : 100
					}, {
						header : '定向委托账号',
						dataIndex : 'dirAccount',
						sortable : true,
						width : 100
					}, {
						header : '自动提现标志',
						dataIndex : 'autoFlag',
						sortable : true,
						width : 90,
						renderer : flagVal
					}, {
						header : '节假日提现标志',
						dataIndex : 'holidaySetFlag',
						sortable : true,
						width : 90,
						renderer : flagVal
					}, {
						header : '信用卡刷卡功能标志',
						dataIndex : 'creFlag',
						sortable : true,
						width : 110,
						renderer : flagVal
					}, {
						header : '退货返还手续费标志',
						dataIndex : 'returnFeeFlag',
						sortable : true,
						width : 110,
						renderer : flagVal
					}, {
						header : '商户结算方式T+N',
						dataIndex : 'settleChn',
						sortable : true,
						width : 100
					}, {
						header : '商户结算周期',
						dataIndex : 'settleType',
						sortable : true,
						width : 100,
						renderer : settleTypeVal
					} 
					//add
					,{
						header : '定向委托账号开户总行名称',
						dataIndex : 'dirOpenBank',
						sortable : true,
						width : 100
					}, {
						header : '定向委托账号开户行所在省',
						dataIndex : 'dirBankProvince',
						sortable : true,
						width : 100
					},{
						header : '定向委托账号开户行所在市',
						dataIndex : 'dirBankCity',
						sortable : true,
						width : 100
					},{
						header : '对公账号开户总行名称',
						dataIndex : 'compOpenBank',
						sortable : true,
						width : 100
					},{
						header : '对公账号开户行所在省',
						dataIndex : 'compBankProvince',
						sortable : true,
						width : 100
					},{
						header : '对公账号开户行所在市',
						dataIndex : 'compBankCity',
						sortable : true,
						width : 100
					},{
						header : '对私账号开户总行名称',
						dataIndex : 'corpOpenBank',
						sortable : true,
						width : 100
					},{
						header : '对私账号开户行所在省',
						dataIndex : 'corpBankProvince',
						sortable : true,
						width : 100
					},{
						header : '对私账号开户行所在市',
						dataIndex : 'corpBankCity',
						sortable : true,
						width : 100
					}
					]);
	
	function flagVal(val){
		if(val=='0'){
			return '否';
		}
		if(val=='1'){
			return '是';
		}
	}
	
	function settleTypeVal(val){
		if(val=='0'){
			return '日结';
		}
		if(val=='1'){
			return '日结累计';
		}
		if(val=='2'){
			return '月结单笔';
		}
		if(val=='3'){
			return '月结包月';
		}
		if(val=='4'){
			return '月金额结累计计算';
		}
	}
	function settleRptVal(val){
		if(val=='1'){
			return '对私账户'; 
		}
		if(val=='2'){
			return '对公账户';
		}
		if(val=='3'){
			return '定向委托账户';
		}
	}
	//商户费率信息 表格
	var setGridPanel = new Ext.grid.GridPanel({
		title: '商户清算信息',
		frame: true,
		border: true,
		height: 190,
		columnLines: true,
		stripeRows: true,
		store: setStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: setColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户清算信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: setStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['终端号：',{
			xtype: 'textfield',
			id: 'setTerm_ID',
			width: 60
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'sears',
			width: 60,
			handler: function(){
				setStore.load({
				params: {
					start: 0,
					mchtNo: mchntId,
					termId:Ext.getCmp('setTerm_ID').getValue()
					}
				});
			}
		},'-',{
			xtype: 'button',
			iconCls: 'add',
			text:'添加',
			id: 'setAdd',
			width: 60,
			handler: function(){
			curOp='01';
			setEditForm.getForm().resetAll();
			setEditForm.getForm().clearInvalid();
			Ext.getCmp('setTermId').enable();
			Ext.getCmp('settleAcctNm').setValue(Ext.getCmp('mchtNm').getValue());
			Ext.getCmp('legalNam').setValue(Ext.getCmp('manager').getValue());
			setEditWin.show();
			}
		},'-',{
			xtype: 'button',
			iconCls: 'edit',
			text:'修改',
			id: 'setEdit',
			disabled:true,
			width: 60,
			handler: function(){
			curOp='02';
			setEditForm.getForm().reset();
			var rec = setGridPanel.getSelectionModel().getSelected();
			var etpsAttrVal = mchntForm.getForm().findField('idEtpsAttr').getValue();
			setEditForm.getForm().loadRecord(rec);
			setEditForm.getForm().clearInvalid();
			setEditWin.show();
			var value =rec.get('settleRpt');
			 if(value == '1') {  //选择法人账户
                 if(etpsAttrVal == '02')  //商户性质选公司
                 {	//法人账户必填
 				    Ext.getCmp('corpBankName').allowBlank=false;
 				  	setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称*');
 				  	Ext.getCmp('feeAcct').allowBlank=false;
 				  	setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号*'); 
 				  	Ext.getCmp('bankAccountCode').allowBlank=false;
 				  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码*'); 
 				  	
 				  	//定向委托账户非必填
 				    Ext.getCmp('dirBankName').allowBlank=true;
 				  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称');
 				  	Ext.getCmp('dirAccountName').allowBlank=true;
 				  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名'); 
 				  	Ext.getCmp('dirAccount').allowBlank=true;
 				  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
 				  	Ext.getCmp('dirBankCode').allowBlank=true;
     			  	setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码'); 
 				  //公司账户必填
 					Ext.getCmp('compAccountBankName').allowBlank=false;
 					setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
 					Ext.getCmp('settleAcct').allowBlank=false;
 					setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号*'); 	
 					Ext.getCmp('compAccountBankCode').allowBlank=false;
	        		setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
	        		
	        		}else if(etpsAttrVal != '02'){
	        			//法人账户必填
     				    Ext.getCmp('corpBankName').allowBlank=false;
     				  	setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称*');
     				  	Ext.getCmp('feeAcct').allowBlank=false;
     				  	setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号*'); 
     				  	Ext.getCmp('bankAccountCode').allowBlank=false;
     				  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码*'); 
     				  	
     				    //定向委托账户非必填
     				    Ext.getCmp('dirBankName').allowBlank=true;
     				  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称');
     				  	Ext.getCmp('dirAccountName').allowBlank=true;
     				  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名'); 
     				  	Ext.getCmp('dirAccount').allowBlank=true;
     				  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
     				  	Ext.getCmp('dirBankCode').allowBlank=true;
	        			setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码'); 
     				    //公司账户非必填
     					Ext.getCmp('compAccountBankName').allowBlank=true;
     					setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称');
     					Ext.getCmp('settleAcct').allowBlank=true;
     					setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号'); 	
     					Ext.getCmp('compAccountBankCode').allowBlank=true;
		        		setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码');
	        	}
     				
 			} else if(value=='2'){
 				 //公司账户必填
 				 Ext.getCmp('compAccountBankName').allowBlank=false;
 				 setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
 				 Ext.getCmp('settleAcct').allowBlank=false;
 				 setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号*'); 
 				 Ext.getCmp('compAccountBankCode').allowBlank=false;
 				 setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
 				 
 				 
 				 //定向委托账户非必填
 				 Ext.getCmp('dirBankName').allowBlank=true;
 				 setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称');
 				 Ext.getCmp('dirAccountName').allowBlank=true;
 				 setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名'); 
 				 Ext.getCmp('dirAccount').allowBlank=true;
 				 setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
 				 Ext.getCmp('dirBankCode').allowBlank=true;
     			 setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码'); 
 				 //法人账户非必填
 				 Ext.getCmp('corpBankName').allowBlank=true;
 				 setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
 				 Ext.getCmp('feeAcct').allowBlank=true;
 				 setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号');
 				 Ext.getCmp('bankAccountCode').allowBlank=true;
 				 setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 

 			}else if(value=='3'){
 				if(etpsAttrVal == '02')  //商户性质选公司
 				{
 					//定向委托账户必填
     			    Ext.getCmp('dirBankName').allowBlank=false;
     			  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称*');
     			  	Ext.getCmp('dirAccountName').allowBlank=false;
     			  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名*'); 
     			  	Ext.getCmp('dirAccount').allowBlank=false;
     			  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*'); 
     			  	Ext.getCmp('dirBankCode').allowBlank=false;
     			  	setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码*'); 
     				//公司账户必填
     				 Ext.getCmp('compAccountBankName').allowBlank=false;
     				  	setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
     				  	Ext.getCmp('settleAcct').allowBlank=false;
     				  	setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号*'); 
     				  	Ext.getCmp('compAccountBankCode').allowBlank=false;
	        				 setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
     				  	 //法人账户非必填
     				    Ext.getCmp('corpBankName').allowBlank=true;
     				  	setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
     				  	Ext.getCmp('feeAcct').allowBlank=true;
     				  	setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号');
     				  	Ext.getCmp('bankAccountCode').allowBlank=true;
     				  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码'); 
 				}else if(etpsAttrVal != '02'){
 					//定向委托账户必填
     			    Ext.getCmp('dirBankName').allowBlank=false;
     			  	setEditForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户行名称*');
     			  	Ext.getCmp('dirAccountName').allowBlank=false;
     			  	setEditForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号开户名*'); 
     			  	Ext.getCmp('dirAccount').allowBlank=false;
     			  	setEditForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*'); 
     			  	Ext.getCmp('dirBankCode').allowBlank=false;
     			  	setEditForm.getForm().findField('dirBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托帐号帐号开户行机构代码*'); 
     				//公司账户非必填
     				 Ext.getCmp('compAccountBankName').allowBlank=true;
     				  	setEditForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称');
     				  	Ext.getCmp('settleAcct').allowBlank=true;
     				  	setEditForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账号'); 
     				  	Ext.getCmp('compAccountBankCode').allowBlank=true;
	        				 setEditForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
     				  	 //法人账户非必填
     				    Ext.getCmp('corpBankName').allowBlank=true;
     				  	setEditForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行名称');
     				  	Ext.getCmp('feeAcct').allowBlank=true;
     				  	setEditForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号');
     				  	Ext.getCmp('bankAccountCode').allowBlank=true;
     				  	setEditForm.getForm().findField('bankAccountCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对私账号开户行机构代码');
 				}
 				 
 			}
			Ext.getCmp('setTermId').disable();
			setEditForm.getForm().loadRecord(rec);
			Ext.getCmp('setTermId').setValue(rec.get('termId'));
			setEditForm.getForm().clearInvalid();
			setEditWin.show();
			}
		},'-',{
			xtype: 'button',
			iconCls: 'delete',
			text:'删除',
			disabled:true,
			id: 'setDel',
			width: 60,
			handler: function(){
				if(setGridPanel.getSelectionModel().hasSelection()) {
					var rec = setGridPanel.getSelectionModel().getSelected();
					showConfirm('确定要删除该条清算信息吗？',setGridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T20101Action_setDel.asp',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,setGridPanel);
										setGridPanel.getStore().reload();
										Ext.getCmp('setDel').disable();
									} else {
										showErrorMsg(rspObj.msg,setGridPanel);
									}
								},
								params: { 
									mchtNo: mchntId,
							        termId:rec.get('termId'),
							        txnId: '20101',
							        subTxnId: '07'
								}
							});
						}
					});
				}
				}
		}]
	});
	
	setGridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(setGridPanel.getView().getRow(setGridPanel.getSelectionModel().last)).frame();
			
			// 
			var rec = setGridPanel.getSelectionModel().getSelected();

			Ext.getCmp('setEdit').enable();
			if(rec.get('termId')!='*'){
			Ext.getCmp('setDel').enable();	
			}
		}
	});
	
	setStore.on('beforeload',function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: mchntId,
			termId:Ext.getCmp('setTerm_ID').getValue()
		});
	});	
	
	/************************end******************************/
	

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNoInput',mapping: 'mchtNo'},
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
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			
			{name: 'legalGender',mapping: 'legalGender'},
			{name: 'legalAddr',mapping: 'legalAddr'},
			{name: 'legalProfession',mapping: 'legalProfession'},
			
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'endDate',mapping: 'endDate'},
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
			//基本信息-2 by zhangqy
			{name: 'engName',mapping: 'engName'},
			{name: 'busiRangeId',mapping: 'busiRangeId'},
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
			{name: 'custNo',mapping: 'custNo'},
			{name: 'riskGrade',mapping: 'riskGrade'},
			{name: 'appComm',mapping: 'appComm'},
			{name: 'agentName',mapping: 'agentName'},
			{name: 'agentType',mapping: 'agentType'},
			{name: 'regionBel',mapping: 'regionBel'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'uscCode',mapping: 'uscCode'},
			{name: 'isSyncretic',mapping: 'isSyncretic'},
			{name: 'organizationType',mapping: 'organizationType'},
			
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
			{name:'mchtNoHx',mapping:'mchtNoHx'},  //北京银行商户号
			{name: 'mchtSXNoHx',mapping: 'mchtSXNoHx'}, //随行付商户号
			//{name: 'engNameAbbr',mapping: 'engNameAbbr'},
			{name:'tmpNo',mapping:'tmpNo'},
			
			{name:'province',mapping:'province'},
			{name:'city',mapping:'city'},
			{name:'area',mapping:'area'},
			{name:'depart',mapping:'depart'},
			{name:'mchtTypeCause',mapping:'mchtTypeCause'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'busScope',mapping:'busScope'},
			{name:'busMain',mapping:'busMain'},
			{name:'linkTel',mapping:'linkTel'},
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
			{name:'isWineshop',mapping:'isWineshop'},
			{name:'wineshopLvl',mapping:'wineshopLvl'},
			{name:'isMoreAcq',mapping:'isMoreAcq'},
			{name:'isAppOutSide',mapping:'isAppOutSide'},
			{name:'hasInnerPosExp',mapping:'hasInnerPosExp'},
//			{name:'serType',mapping:'serType'},
			{name:'serLvl',mapping:'serLvl'},
			{name:'src',mapping:'src'},
			{name:'referrer',mapping:'referrer'},
			{name:'mchtNoOld',mapping:'mchtNoOld'},
			{name:'mcc2',mapping:'mcc2'},
			{name:'proxy',mapping:'proxy'},
			{name:'proxyTel',mapping:'proxyTel'},
			{name:'expander',mapping:'expander'},
			{name:'area',mapping:'area'},
			{name:'hasOurPosExp',mapping:'hasOurPosExp'},
			{name:'nationality',mapping:'nationality'},
			{name:'foreignName',mapping:'foreignName'},
			//====By Mike====用于显示“商户评估信息”页面的值==================
			{name:'feeDiffer',mapping:'feeDiffer'},
//			{name:'managerYears',mapping:'managerYears'},
			{name:'singleAmt',mapping:'singleAmt'},
			{name:'monthTotalAmt',mapping:'monthTotalAmt'},
			{name:'evalWayFlag',mapping:'evalWayFlag'},
			{name:'evalLevel',mapping:'evalLevel'},
			{name:'evalEndTime',mapping:'evalEndTime'},
			//====By Mike======================
			//附加信息-2（预授权、退货）
			{name:'preAuthor',mapping:'preAuthor'},
			{name:'returnFunc',mapping:'returnFunc'},
			//经营范围外建表字段
			{name:'busiRangeIds',mapping:'busiRangeIds'},
			{name:'mchtNo',mapping:'mchtNo'},
			{name:'busiRange',mapping:'busiRange'},
		]),
		autoLoad: false
	});

	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();

				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var settleType =  baseStore.getAt(0).data.settleType;
				//addlhf
				var isSyncretic =  baseStore.getAt(0).data.isSyncretic;
				if(isSyncretic == '0') {
									Ext.getCmp('idUscCode').show(); 
									Ext.getCmp('uscCode').allowBlank=false;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码*');
									
									Ext.getCmp('idUscCodeDate').show(); 
									Ext.getCmp('uscCodeDate').allowBlank=false;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期*');
									
									Ext.getCmp('idLicenceNo').hide(); 
									Ext.getCmp('licenceNo').allowBlank=true;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号');
									
									Ext.getCmp('idBankLicenceNo').hide(); 
									Ext.getCmp('bankLicenceNo').allowBlank=true;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码');
									
									Ext.getCmp('idLicenceNoDate').hide(); 
									Ext.getCmp('licenceNoDate').allowBlank=true;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期');
									
									Ext.getCmp('idFaxNo').hide(); 
									Ext.getCmp('faxNo').allowBlank=true;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码');
				}else if(isSyncretic=='1'){
									Ext.getCmp('idLicenceNo').show(); 
									Ext.getCmp('licenceNo').allowBlank=false;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号*');
									
									Ext.getCmp('idLicenceNoDate').show(); 
									Ext.getCmp('licenceNoDate').allowBlank=false;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期*');
									
									Ext.getCmp('idUscCodeDate').hide(); 
									Ext.getCmp('uscCodeDate').allowBlank=true;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期');
									
									
									Ext.getCmp('idBankLicenceNo').show(); 
									Ext.getCmp('bankLicenceNo').allowBlank=false;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码*');
									
									Ext.getCmp('idFaxNo').show(); 
									Ext.getCmp('faxNo').allowBlank=false;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码*');
									
									Ext.getCmp('idUscCode').hide(); 
									Ext.getCmp('uscCode').allowBlank=true;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码');
				}
				/*if(baseStore.getAt(0).data.feeRate!=0){
					//Ext.getCmp('discCode').setValue(baseStore.getAt(0).data.feeRate);
				}*/
				//2011/12/19变更
				/*if("" == baseStore.getAt(0).data.idOtherNo) {
					Ext.getCmp('idOtherNo').disable();
					Ext.getCmp('selectOtherNo').setValue(false);
				} else {
					Ext.getCmp('idOtherNo').enable();
					Ext.getCmp('selectOtherNo').setValue(true);
				}*/
				//end
//				imagesId = baseStore.getAt(0).data.mchtNo;

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					Ext.getCmp('idmcc').setValue(baseStore.getAt(0).data.mcc);
//					Ext.getCmp('idmcc2').setValue(baseStore.getAt(0).data.mcc2);
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
				/*mchntForm.getForm().findField('clearType').setValue(settleAcct.substring(0,1));
				mchntForm.getForm().findField('settleAcct').setValue(settleAcct.substring(1));*/
				
				//财务POS商户受控
				/*if(baseStore.getAt(0).data.mchtGroupFlag == '3'){
					mchntForm.getForm().findField("clearType").readOnly=true;
             		mchntForm.getForm().findField("settleType").readOnly=true;
				}*/
				   //初始化省、市、县信息
                      SelectOptionsDWR.getComboDataWithParameter('CITIES',baseStore.getAt(0).data.province,
							function(ret){
								Ext.getCmp('idcity').getStore().loadData(Ext.decode(ret));
								/*var rec = Ext.getCmp('idcity').getStore().getAt(0);
								Ext.getCmp('idcity').setValue(rec.get('valueField'));*/
								Ext.getCmp('idcity').setValue(mchntForm.getForm().findField('idcity').getValue());
								Ext.getCmp('idcity').parentP=baseStore.getAt(0).data.province;
						});
								
                       SelectOptionsDWR.getComboDataWithParameter('AREAS',baseStore.getAt(0).data.city,
							function(ret){
								Ext.getCmp('idarea').getStore().loadData(Ext.decode(ret));
								/*var rec1 = Ext.getCmp('idarea').getStore().getAt(0);
								Ext.getCmp('idarea').setValue(rec1.get('valueField'));*/
								Ext.getCmp('idarea').setValue(mchntForm.getForm().findField('idarea').getValue());
								Ext.getCmp('idarea').parentP=baseStore.getAt(0).data.city;
						});
                       
                       var cerTp=baseStore.getAt(0).data.nationality;
                       if(cerTp!='800'){//非外籍 则证件类型为身份证
						  	Ext.getCmp('idartifCertifTp').readOnly=true;
						  	//联系人授权人非必填
						  	Ext.getCmp('contact').allowBlank=true;
						  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/经(代)办人姓名*');
						  	Ext.getCmp('linkTel').allowBlank=true;
						  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
						  	
						  }else{//外籍的则为 护照
						  	Ext.getCmp('idartifCertifTp').readOnly=false;
						  //联系人授权人必填
						  	Ext.getCmp('contact').allowBlank=false;
						  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/经(代)办人姓名*');
						  	Ext.getCmp('linkTel').allowBlank=false;
						  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
						  }
				    /*Ext.getCmp('idprovince').setValue('');
                    Ext.getCmp('idprovince').getStore().reload();
                    Ext.getCmp('idprovince').setValue(baseStore.getAt(0).data.province);
                    Ext.getCmp('idcity').parentP=baseStore.getAt(0).data.province;
                    Ext.getCmp('idcity').setValue('');
                    Ext.getCmp('idcity').getStore().reload();
                    Ext.getCmp('idcity').setValue(baseStore.getAt(0).data.city);
					Ext.getCmp('idarea').parentP=baseStore.getAt(0).data.city;
					Ext.getCmp('idarea').setValue('');
					Ext.getCmp('idarea').getStore().reload();
					Ext.getCmp('idarea').setValue(baseStore.getAt(0).data.area);*/
				//加载商户费率信息
				feeStore.reload({
					params:{
						start:0,
						tmpNo: baseStore.getAt(0).data.tmpNo
					}
				});
				
				//加载商户清算信息
				setStore.reload({
					params:{
						start:0,
						mchtNo:mchntId
					}
				});
				
			}else{
				showErrorMsg("加载商户信息失败，请返回刷新数据后重试",mchntForm,function(){
					window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
				});
			}
		}
	});
	
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
		{header: '计费代码',dataIndex: 'feeType',sortable: true,width:100,
			editor : new Ext.form.ComboBox( {
				store: feeDiscStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})},
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
	var feeGridPanel = new Ext.grid.EditorGridPanel({
		title: '商户费率信息',
		frame: true,
		border: true,
		height: 170,
		columnLines: true,
		//autoExpandColumn: 'discNm',
		clicksToEdit: true,
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
	
	feeStore.on('beforeload',function(){//20120924解决商户信息维护之修改商户信息页面中点击费率信息分页中刷新按钮信息会清空的bug
		Ext.apply(this.baseParams, {
			start: 0,
			tmpNo: Ext.getCmp('tmpNo').getValue(),
			termId:Ext.getCmp('term_ID').getValue()
		});
	});
	
	/**
	 * 双击弹出费率信息详情窗口
	 * 
	 */
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
	
	var detailWin = new Ext.Window({
					title : '费率详情',
					initHidden : true,
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
					buttons : [ {
								text : '关闭',
								handler : function() {
									detailWin.hide();
								}
							} ]
				});
		feeGridPanel.on({	//行双击事件
			'rowdblclick':function(){
				detailForm.getForm().loadRecord(feeGridPanel.getSelectionModel().getSelected());
				gridStore_d.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				store_d.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				detailWin.show();	
		}
		}
	)

//	//重置账号验证
//	function resetVerify(){
//		verifyResult = false;
//	    Ext.getCmp('verifyName').setValue('');
//	    Ext.getCmp('verifySta').setValue('<font color="red">未验证</font>');
//	}
	
	var hasSub = false;

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var mchntForm = new Ext.FormPanel({
        title: '修改商户信息[商户编号：'+mchntId+']' ,
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [
//        		{
//        		columnWidth: .33,
//            	layout: 'form',
//            	xtype: 'panel',
//        		items: [{  //修改操作该字段不可修改
//			        xtype: 'combofordispaly',
//			        baseParams: 'MCHT_GROUP_FLAG',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '商户种类*',
//					id: 'idmchtLvl',
//					hiddenName: 'mchtLvl'
//		        }]
//        	},{
//        		columnWidth: .33,
//            	layout: 'form',
//            	xtype: 'panel',
//        		items: [{
//        			xtype: 'basecomboselect',
//			        baseParams: 'CONN_TYPE',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '商户类型*',
//					id: 'idconnType',
//					hiddenName: 'connType',
//					allowBlank: false,
//					anchor: '90%'
//				}]
//        	},
        		{	
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
				        width:150,
				        id: 'mchtNoInput'
			    }]
         	},{
	        	columnWidth: .33,
		        layout: 'form',
		        xtype: 'panel',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW_NORMAL',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属分公司*',
					allowBlank: false,
					blankText: '请选择所属分公司',
					id: 'idacqInstId',
					hiddenName: 'acqInstId'/*,
					anchor: '90%'*/
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户名称*',
					maxLength: '120',
					vtype: 'isOverMax',
					//readOnly:true,
					id: 'mchtNm',
					name:'mchtNm'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
						readOnly: true,
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
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
						blankText: '请选择商户MCC',
						id: 'idmcc',
						readOnly: true,
						hiddenName: 'mcc',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .1,
				xtype: 'panel',
				html:'<br/>'
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
        	items:[
        		{
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
								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称*');
								  	Ext.getCmp('settleAcct').allowBlank=false;
								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户*'); 
								  	Ext.getCmp('compAccountBankCode').allowBlank=false;
								  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码*'); 
							 }else if(mchntForm.getForm().findField('idsettleRpt').getValue()!='2'){
								//公司账户非必填
								 Ext.getCmp('compAccountBankName').allowBlank=true;
								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行名称');
								  	Ext.getCmp('settleAcct').allowBlank=true;
								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户');
								  	Ext.getCmp('compAccountBankCode').allowBlank=true;
								  	mchntForm.getForm().findField('compAccountBankCode').getEl().up('.x-form-item').down('.x-form-item-label').update('对公账户开户行机构代码'); 
							  
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
				}/*,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编号*',
						maxLength: '6',
						allowBlank: false,
						regex: /^[0-9]\d{5}$/,
						id: 'postalCode',
						name: 'postalCode'
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
						vtype: 'isOverMax',
						id: 'commEmail',
						name: 'commEmail',
						vtype: 'email'
		        	}]
				}*/
				,{
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
								if(value == '0') {
									Ext.getCmp('idUscCode').show(); 
									Ext.getCmp('uscCode').allowBlank=false;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码*');
									
									Ext.getCmp('idUscCodeDate').show(); 
									Ext.getCmp('uscCodeDate').allowBlank=false;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期*');
									
									Ext.getCmp('idLicenceNo').hide(); 
									Ext.getCmp('licenceNo').allowBlank=true;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号');
									
									Ext.getCmp('idBankLicenceNo').hide(); 
									Ext.getCmp('bankLicenceNo').allowBlank=true;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码');
									
									Ext.getCmp('idLicenceNoDate').hide(); 
									Ext.getCmp('licenceNoDate').allowBlank=true;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期');
									
									Ext.getCmp('idFaxNo').hide(); 
									Ext.getCmp('faxNo').allowBlank=true;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码');
								}else if(value=='1'){
									Ext.getCmp('idLicenceNo').show(); 
									Ext.getCmp('licenceNo').allowBlank=false;
									mchntForm.getForm().findField('licenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号*');
									
									Ext.getCmp('idLicenceNoDate').show(); 
									Ext.getCmp('licenceNoDate').allowBlank=false;
									mchntForm.getForm().findField('licenceNoDate').getEl().up('.x-form-item').down('.x-form-item-label').update('营业执照编号有效期*');
									
									Ext.getCmp('idUscCodeDate').hide(); 
									Ext.getCmp('uscCodeDate').allowBlank=true;
									mchntForm.getForm().findField('uscCodeDate').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码证有效期');
									
									
									Ext.getCmp('idBankLicenceNo').show(); 
									Ext.getCmp('bankLicenceNo').allowBlank=false;
									mchntForm.getForm().findField('bankLicenceNo').getEl().up('.x-form-item').down('.x-form-item-label').update('企业组织机构代码*');
									
									Ext.getCmp('idFaxNo').show(); 
									Ext.getCmp('faxNo').allowBlank=false;
									mchntForm.getForm().findField('faxNo').getEl().up('.x-form-item').down('.x-form-item-label').update('税务登记证号码*');
									
									Ext.getCmp('idUscCode').hide(); 
									Ext.getCmp('uscCode').allowBlank=true;
									mchntForm.getForm().findField('uscCode').getEl().up('.x-form-item').down('.x-form-item-label').update('统一社会信用代码');
								}
							}
						}
		        	}]
				}
				,{
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
					id: 'idCustNo',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户号',
						disabled:true,
						id: 'custNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
					id: 'idRiskGrade',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '风险等级',
						disabled:true,
						id: 'riskGrade'
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
						name: 'bankLicenceNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'idFaxNo',
	       			items: [{
	       				xtype: 'textfield',
	       				allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码*',
						maxLength: 15,
						id: 'faxNo'
		        	}]
				}
				,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经营范围*',
						maxLength: 60,
						allowBlank: false,
						id: 'busiRange',
						name: 'busiRange'
		        	}]
				},/*{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                		xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '主营业务*',
						maxLength: 50,
						allowBlank: false,
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
						fieldLabel: '所属行业',
						maxLength: '60',
//						allowBlank: false,
						id: 'belInd',
						name: 'belInd'
		        	}]
				},*/{
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
		        	hidden:true,
		        	layout: 'form',
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
            },/*{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '营业面积/平方米*',
					name: 'acreage',
					id: 'acreage',
					vtype: 'isArea',
					maxLength:10,
					allowBlank: false,
					width:130
	        	}]
			},*/{
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
			},/*{
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
			},*//*{
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
						fieldLabel: 'ICP备案公司名称',
						maxLength: '60',
						allowBlank: true,
						id: 'ICPcompName',
						name: 'ICPcompName'
		        	}]
				},*/{
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
				}/*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户固定电话',
						maxLength: '60',
						allowBlank: true,
						id: 'busiTel',
						name: 'busiTel'
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
				},*//*{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人电话*',
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
						fieldLabel: '联系人电话',		 
						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'commTel',
						width:128
		        	}]
				}*/
				,{
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
				}
				]
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
            	allowBlank:true,
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '拓展人电话',
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
//    		 	allowBlank:false,
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
    			anchor: '72%'
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
    			anchor: '72%'
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
    				fieldLabel: '所属业务人员',
    				maxLength: 10,
    				allowBlank:true,
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
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户地址*',
    						maxLength: 100,
    						allowBlank: true,
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
    		        		xtype: 'textfield',
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
    						allowBlank: true,
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
    	       				xtype: 'textfield',
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
    						allowBlank: true,
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
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '月消费总额*',
    						name: 'monthTotalAmt',
    						id: 'monthTotalAmt',
//    						maskRe: /^[0-9\\.]+$/,
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
//    						maskRe: /^[0-9\\.]+$/,
    						vtype: 'isNumber',
//    						vtype: 'isMoney',
    						maxLength:12,
    						value:0,
    						allowBlank: true,
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
    						allowBlank: true,
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
    						allowBlank: true,
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
    			},{
				title:'清算信息',
                id: 'settle',
                frame: true,
                layout: 'border',
                items: [{
			       xtype: 'panel',
			      html: '<br/><br/><br/><br/><br/><br/>'
                },{
                	region: 'center',
                	items:[setGridPanel]
                }
                ]
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
                	items: [/*{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		       			xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '发卡机构',
						id: 'idFK_BRCH_NO',
						name: 'FK_BRCH_NO',
						maxLength: 8,
						allowBlank: true,
						width:150
		        	}]
                	}*/{
                		columnWidth: .33,
    		        	xtype: 'panel',
    		        	labelWidth: 70,
    			        layout: 'form',
    		       		items: [{
        				fieldLabel : '发卡机构',
        				xtype: 'dynamicCombo',
        				methodName: 'getSendCardNo',
//        				id : 'idFK_BRCH_NO',
//        				name : 'FK_BRCH_NO',
        				id:'fkBrchNo',
        				name:'fkBrchNo',
        				width : 150
    		       		}]
        			},{
		        		columnWidth: .2,
		        		labelWidth: 70,
		            	layout: 'form',
		        		items: [{
					        xtype: 'textnotnull',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端代码',
							id: 'idTERM_ID',
							name: 'TERM_ID',
							maxLength: 8,
							allowBlank: true,
							width:100
				        }]
		        	},{
                		columnWidth: .2,
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'textnotnull',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
							disabled:true,
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
							name:'tmpNo',
							width: 60
                		}]
					},{
		        		columnWidth: .33,
		        		xtype: 'panel',
		        		labelWidth: 70,
				        layout: 'form',
			       		items: [{
			       			xtype: 'dynamicCombo',
			       			methodName: 'getAreaCodecode',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '地区码',
							editable: true,
							id: 'idCITY_CODE',
							hiddenName: 'CITY_CODE',
							//allowBlank: false,
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
						//allowBlank: false,
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
		       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						//allowBlank: false,
						//anchor: '90%',
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
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						//allowBlank: false,
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
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						//allowBlank: false,
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
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						//allowBlank: false,
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
//							hasUpload = "1";
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
						fieldLabel: '控股股东或实际控制人身份证件种类*',
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
						fieldLabel: '控股股东或实际控制人身份证件号码*',
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
						fieldLabel: '控股股东或实际控制人身份证件有效期*',
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
							  if (flag == '800') {
									Ext.getCmp('idForeignName').show(); 
									Ext.getCmp('foreignName').allowBlank=false;
									mchntForm.getForm().findField('idForeignName').getEl().up('.x-form-item').down('.x-form-item-label').update('外籍名*');
								}else{
									Ext.getCmp('idForeignName').hide(); 
									Ext.getCmp('foreignName').allowBlank=true;
									mchntForm.getForm().findField('idForeignName').getEl().up('.x-form-item').down('.x-form-item-label').update('外籍名');
								}  
							if(flag!='800'){//非外籍 则证件类型为身份证
							  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
							  	Ext.getCmp('idartifCertifTp').readOnly=true;
							  	//联系人授权人非必填
							  	Ext.getCmp('contact').allowBlank=true;
							  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
							  	Ext.getCmp('linkTel').allowBlank=true;
							  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
							}else{//非中国国籍的则为 护照
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
		        	id:'idForeignName',
		        	layout: 'form',
		        	hidden : true,
	       			items: [{
	       					labelStyle: 'padding-left: 5px',
							xtype:'dynamicCombo',
							fieldLabel: '外籍名*',
							width:130,							
							methodName: 'getForeignName',
//							id: 'idForeignName',
							hiddenName: 'foreignName'
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
						maxLength: 100,
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
	       				//allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证号*',
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
				} ]
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
		    }]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
				subSave();
            }
        },{
            text: '返回',
            handler: function() {
            	var tmpNo=Ext.getCmp('tmpNo').getValue();
            	 
            	T20100.rebackDiscAlgo2(tmpNo,mchntId,function(ret){  	});
            	T20100.rebackSettleInf(mchntId,function(ret){ });
            	window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
			}
        }]
    });
	
    function saveDiscAlgo2(){
    	var btn = Ext.getCmp('addbu');
    	var tmpNo=Ext.getCmp('tmpNo').getValue();
    	var TERMID=Ext.getCmp('idTERM_ID').getValue();
    	var CITY_CODE=Ext.getCmp('idCITY_CODE').getValue();
    	var TO_BRCH_NO=Ext.getCmp('idTO_BRCH_NO').getValue();
//    	var FK_BRCH_NO=Ext.getCmp('idFK_BRCH_NO').getValue();
    	var FK_BRCH_NO=Ext.getCmp('fkBrchNo').getValue();
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
    	var btn = Ext.getCmp('save')
		var frm = mchntForm.getForm();
		hasSub = true;
		if(feeStore.getCount()<1){
				showErrorMsg('请添加商户计费信息!',mchntForm);
				return;
			}
		if (frm.isValid()) {
			/*var flag = mchntForm.getForm().findField('clearType').getValue();
			var settleChn=mchntForm.getForm().findField('settleChn').getValue();
			var settleType2=mchntForm.getForm().findField('settleType').getValue();*/
			
			//201200904添加的验证条件
			/*if(settleChn>31){
				showErrorMsg('商户结算方式应小于T+31!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
				return;
			}else if((settleType2=='0' || settleType2=='1')&& settleChn > 15){
				showErrorMsg('日结商户结算方式应小于T+15!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
				return;
			}*/
			//if(!verifyResult&&(flag == 'A'||flag == 'P')){

	         //   if(flag == 'A'||flag == 'P'){
	         //       Ext.getCmp('verifyButton').enable();
			//		showErrorMsg('保存商户信息之前请验证账户账号!',mchntForm);
	         //   }
			//}else{

				
		 var modifiedRecords = feeGridPanel.getStore().getModifiedRecords();
				
				// 存放要修改的参数信息
			var array = new Array();
			for ( var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					discId:record.get('discId'),
					feeType : record.get('feeType')
				};
				array.push(data);
			}
			if(Ext.getCmp('contact').getValue()==''){
	    		Ext.getCmp('contact').setValue(' ');
	    	}	
			frm.submitNeedAuthorise({
//			frm.submit({
				url: 'T20101Action_update.asp',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					showSuccessAlert(action.result.msg,mchntForm,function(){
						window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
					});
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
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
					}
				},
				params: {
					txnId: '20101',
					subTxnId: '01',
					mchtNo: mchntId,
					checkIds: checkIds,
					algo2List : Ext.encode(array)
				}
			});
		}
	/*}*/else{
		//自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic'||tab == 'basic1' || tab == 'append'||tab == 'append1' || tab == 'settle' ||tab == 'settle1' ||tab == 'settle2' || tab == 'feeamt'||tab=='standardConfig'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	});
	}
    }
    
    //为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    })

	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue()
		});
	});

	//移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 1
		});
	};
	hideMainUIMask.defer(1000);
});
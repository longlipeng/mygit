//终端通道详细信息，在外部用函数封装
function showTermChannelDetail(termIns,			//所属机构
								mchtId,			//商户ID
								reserve,		//索引值
								creTime,		//创建时间
								modiTime,		//修改时间
								rec,El){
	var selectedRecModel = new Ext.data.Record.create([
		{name : 'termins',type : 'string'},
		{name : 'mchtmcc',type : 'string'},
		{name : 'mchtid',type : 'string'},
		{name : 'mchttermid',type : 'string'},
		{name : 'insmcc',type : 'string'},
		{name : 'insmcht',type : 'string'},
		{name : 'insterm',type : 'string'},
		{name : 'reserve01',type : 'string'},
		{name : 'lmk',type : 'string'},
		{name : 'creOprId',type : 'string'},
		{name : 'creTime',type : 'string'},
		{name : 'modioprid',type : 'string'},
		{name : 'moditime',type : 'string'}
	]);
	
	var detailForm = new Ext.FormPanel({
		region: 'center',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'right',
		autoHeight : true,
        items: [{
        	layout:'column',
        	items: [ {
        		columnWidth: 1,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly', 
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '所属机构',
					hiddenName: 'termins'
		        	}
        		]
        	},{
        		columnWidth: 1,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号',
					hiddenName: 'mchtid',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .5,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly', 
	       			labelStyle: 'padding-left: 5px',
					fieldLabel: '商户MCC码',
					hiddenName: 'mchtmcc'
				}]
			},{
	        	columnWidth: .5,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly',
	       			labelStyle: 'padding-left: 5px',
					fieldLabel: '商户终端号',
					hiddenName: 'mchttermid',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .5,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'combofordispaly',
			       	labelStyle: 'padding-left: 5px',
					fieldLabel: '机构MCC码',
					hiddenName: 'insmcc',
					anchor: '90%'
		        	}]
			},{
	        	columnWidth: .5,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly', 
			        labelStyle: 'padding-left: 5px',
					fieldLabel: '机构商户号',
					hiddenName: 'insmcht',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .5,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
					xtype: 'combofordispaly',
			       	labelStyle: 'padding-left: 5px',
					fieldLabel: '机构终端号',
					hiddenName: 'insterm',
					anchor: '90%'
		       	}]
			},{
	        	columnWidth: .5,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			       	xtype: 'combofordispaly',
			       	labelStyle: 'padding-left: 5px',
					fieldLabel: '索引值',
					hiddenName: 'reserve01',
					anchor: '90%'
		       	}]
			},{
	        	columnWidth: 1,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
					xtype: 'combofordispaly',
			   		labelStyle: 'padding-left: 5px',
					fieldLabel: '主密钥',
					hiddenName: 'lmk',
					anchor: '90%'
		       	}]
			},{
	        	columnWidth: .5,
		       	xtype: 'panel',
		       	layout: 'form',
	       		items: [{
					xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '创建人',
					hiddenName: 'creOprId'
		       	}]
			},{
	        	columnWidth: .5,
		       	xtype: 'panel',
		       	layout: 'form',
	       		items: [{
					xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '创建时间',
					hiddenName: 'creTime'
		       	}]
			},{
	        	columnWidth: .5,
		       	xtype: 'panel',
		       	layout: 'form',
	       		items: [{
			       	xtype: 'combofordispaly',
			       	labelStyle: 'padding-left: 5px',
					fieldLabel: '修改人',
					hiddenName: 'modioprid'
		       	}]
			},{
	        	columnWidth: .5,
		       	xtype: 'panel',
		       	layout: 'form',
	       		items: [{
			       	xtype: 'combofordispaly',
			       	labelStyle: 'padding-left: 5px',
					fieldLabel: '修改时间',
					hiddenName: 'moditime',
					renderer: formatTs
		       	}]
			}]
        }]
    });
	
    var detailWin = new Ext.Window({
    	title: '详情审核',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 600,
		autoHeight: true,
		items: [detailForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false,
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '通过',
			width: 85,
			iconCls: 'accept',
			handler: function() {
				showConfirm('确认审核通过吗？',El,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						var array = new Array();
						var data = {
								id : rec.get('id')
						};
						array.push(data);
						Ext.Ajax.request({
							url: 'T11016Action.asp?method=accept',
							params: {
							infList: Ext.encode(array),
								txnId: '11016',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,El);
								} else {
									showErrorMsg(rspObj.msg,El);
								}
								// 重新加载待审核信息
								detailWin.hide();
								El.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
		},{
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			handler: function() {
				showConfirm('确认拒绝吗？',El,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入拒绝原因',true,refuse);
					}
				});
			}
		},{
			text: '关闭',
			handler: function() {
				detailWin.hide();
			}
		}]
    });

    // 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			var array = new Array();
			var data = {
					id : rec.get('id')
			};
			array.push(data);
			Ext.Ajax.request({
				url: 'T11016Action.asp?method=refuse',
				params: {
				 infList: Ext.encode(array),
					txnId: '11016',
					subTxnId: '02',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
					showSuccessMsg(rspObj.msg,El);
				} else {
					showErrorMsg(rspObj.msg,El);
				}
				// 重新加载待审核信息
				detailWin.hide();
				El.getStore().reload();
			}
		});
		hideProcessMsg();
		}
	}
	
	function allSign(val){
	    if(val!='*'){
	    	return val;
	    }else{
	    	return '全部支持';
	   }
	}
	
	selectedModel = new selectedRecModel();
	selectedModel.set('termins', termIns);			//所属机构
	selectedModel.set('mchtmcc',allSign(rec.get('mchtmcc')));	//商户MCC
	selectedModel.set('mchtid', mchtId);			//商户ID
	selectedModel.set('mchttermid',allSign(rec.get('mchttermid')));		//商户终端号
	selectedModel.set('insmcc',allSign(rec.get('insmcc')));		//机构MCC
	selectedModel.set('insmcht',allSign(rec.get('insmcht')));	//机构商户号
	selectedModel.set('reserve01',reserve);		//索引值
	selectedModel.set('creTime',creTime);	//创建时间
	selectedModel.set('moditime',modiTime);	//修改时间
	
	detailForm.getForm().loadRecord(rec);
	detailForm.getForm().loadRecord(selectedModel);
	detailForm.getForm().clearInvalid();
	detailWin.show();
}

Ext.onReady(function(){
	var queryForm = new Ext.form.FormPanel({
		title: '银联管理类交易',
		frame: true,
		border: true,
		width: 600,
		autoHeight: true,
		renderTo: 'reportPanel',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
				xtype: 'panel',
				height: 160,
				width: 580,
				header: true,
				frame: true,
				html: '<font color=red size=3>银联管理类交易说明</font><br><br>' + 
						'&nbsp;&nbsp;&nbsp;&nbsp;1. 我行主动向银联发起的重置密钥操作;<br>' +
						'&nbsp;&nbsp;&nbsp;&nbsp;2. 银联收到我行的重置密钥请求,将启动密钥更新模块与我行同步密钥;<br>' +
						'&nbsp;&nbsp;&nbsp;&nbsp;3. 如需重置,请点击&nbsp;“银联密钥重置”&nbsp;按钮;<br>'+
						'&nbsp;&nbsp;&nbsp;&nbsp;4. 银联线路测试,测试银联线路的状态;<br>'+
						'&nbsp;&nbsp;&nbsp;&nbsp;5. 机构签到:在正式做业务之前,首先确保机构签到;<br>'+
						'&nbsp;&nbsp;&nbsp;&nbsp;6. 机构签退:机构签退后,收单业务不被受理;<br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '银联密钥重置(PINK)',
			iconCls: 'T30203',
			handler: function() {
			    showCmpProcess(queryForm,'正在银联密钥重置，请稍后......');
				ResetCup.resetCup('01',function(ret){
					
                		var result = ret.substring(0,1);
                        if(result == "S"){
                        	Ext.MessageBox.alert('提示','        银联密钥(PINK)重置成功！             ');
                        	hideCmpProcess(queryForm,'','');
                        }else{
                        	Ext.MessageBox.alert('提示','      银联密钥重置(PINK)失败，请重新操作！        ');
                        	hideCmpProcess(queryForm,'','');
                        }
                	});
                }
		},{
			text: '银联密钥重置(MACK)',
			iconCls: 'T30203',
			handler: function() {
			    showCmpProcess(queryForm,'正在银联密钥重置，请稍后......');
				ResetCup.resetCup('02',function(ret){
                		var result = ret.substring(0,1);
                        if(result == "S"){
                        	Ext.MessageBox.alert('提示','        银联密钥(MACK)重置成功！             ');
                        	hideCmpProcess(queryForm,'','');
                        }else{
                        	Ext.MessageBox.alert('提示','      银联密钥(MACK)重置失败，请重新操作！        ');
                        	hideCmpProcess(queryForm,'','');
                        }
                	});
                }
		},{
			text: '银联线路测试',
			iconCls: 'T30203',
			handler: function() {
			    showCmpProcess(queryForm,'正在银联线路测试，请稍后......');
				ResetCup.resetCup('03',function(ret){
                		var result = ret.substring(0,1);
                        if(result == "S"){
                        	Ext.MessageBox.alert('提示','        银联线路测试成功！             ');
                        	hideCmpProcess(queryForm,'','');
                        }else{
                        	Ext.MessageBox.alert('提示','      银联线路测试失败，请重新操作！        ');
                        	hideCmpProcess(queryForm,'','');
                        }
                	});
                }
		},{
			text: '机构签到',
			iconCls: 'T30203',
			handler: function() {
			    showCmpProcess(queryForm,'正在机构签到，请稍后......');
				ResetCup.resetCup('04',function(ret){
                		var result = ret.substring(0,1);
                        if(result == "S"){
                        	Ext.MessageBox.alert('提示','        机构签到成功！             ');
                        	hideCmpProcess(queryForm,'','');
                        }else{
                        	Ext.MessageBox.alert('提示','      机构签到失败，请重新操作！        ');
                        	hideCmpProcess(queryForm,'','');
                        }
                	});
                }
		},{
			text: '机构签退',
			iconCls: 'T30203',
			handler: function() {
			    showCmpProcess(queryForm,'正在机构签退，请稍后......');
				ResetCup.resetCup('05',function(ret){
                		var result = ret.substring(0,1);
                        if(result == "S"){
                        	Ext.MessageBox.alert('提示','        机构签退成功！             ');
                        	hideCmpProcess(queryForm,'','');
                        }else{
                        	Ext.MessageBox.alert('提示','      机构签退失败，请重新操作！        ');
                        	hideCmpProcess(queryForm,'','');
                        }
                	});
                }
		}]
	});

	/**
	 * 显示容器遮罩
	 * @param {} cmp	容器对象
	 * @param {} msg	遮罩信息
	 */
	function showCmpProcess(cmp,msg) {
		cmp.getEl().mask(msg);
	}
	/**
	 * 隐藏容器遮罩
	 * @param {} cmp		容器对象
	 * @param {} interval	延迟时间
	 */
	function hideCmpProcess(cmp,interval) {
		setTimeout(function() {
		    cmp.getEl().unmask();
		},interval);
	}
	 
	/*var a = new Ext.Window({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[queryForm]
	});	*/
	
});
Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '对账及清分报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80104',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['BANKDET','01 机构对账信息汇总表'],['HOSTPOSPDIS','02 机构对账不平交易明细表（本代本）'],
						['POSPCUPSDIS','03 机构对账不平交易明细表（本代他）'],['HOSTPOSPSUS','04 机构对账可疑交易明细表（本代本）'],
						['POSPCUPSSUS','05 机构对账可疑交易明细表（本代他）'],['OLDPOS','06 老POS交易明细表'],
						['NOPTRANS','07 银联跨行交易清分报表'],['OFFLINE','08 机构当日未对账脱机交易报表'],
						['OFFLINECTL','09 脱机交易获取文件失败信息表（总行）'],['OFFLINERFILE','10 银联返回的拒绝交易统计表'],
						['PMCHTACCT','11 对私账户脱机消费交易汇总']
					],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName',
			allowBlank: true
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'brhId',
			allowBlank: false,
			editable: true
		},{
			xtype: 'datefield',
			name: 'date',
			fieldLabel: '清算日期*',
			blankText: '请选择清算日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('reportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var brhId = queryForm.getForm().findField('brhId').getValue();
				if(reportName == 'OFFLINECTL'){
					if(brhId != '9900'){
						showErrorMsg('[脱机交易获取文件失败信息表（总行）]暂时只为总行提供',queryForm);
						return;
					}
				}
				queryForm.getForm().submit({
					url: 'T80104Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '打包下载',
			iconCls: 'icon_download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80104Action_downloadAll.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	queryForm.getForm().findField('brhId').setValue(BRHID);
	queryForm.getForm().findField('date').setValue(YESTERDAY);

})
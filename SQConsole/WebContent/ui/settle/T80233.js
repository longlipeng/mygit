Ext.onReady(function() {
	var queryForm = new Ext.form.FormPanel({
		title: '备付金报表上传文件下载',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80206',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idreportName',
			hiddenName: 'reportName',
			fieldLabel: '报表文件名称',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','支付机构客户备付金信息统计报表'],['2','支付机构客户备付金出金业务明细表'],['3','支付机构客户备付金业务实际出金明细表'],['4','支付机构客户资金账户转账业务统计表'],['5','支付机构客户资金账户余额统计表'],['6','支付机构招商银行特殊业务明细表'],['7','支付机构现金购卡业务统计表'],['8','支付机构预付卡现金赎回业务统计表'],['9','支付机构招商银行客户备付金业务未达账项统计表'],['10','支付机构招商银行客户备付金业务未达账项分析表'],['11','支付机构客户资金账户余额变动调节表'],['12','支付机构客户资金账户余额试算表'],['13','预付卡发行企业备付金账户中售卡押金统计表'],['14','招商银行汇明商务服务有限公司支付机构备付金银行账户余额统计表'],['15','银行客户备付金入金业务调节表'],['16','分银行账户的表1-2'],['17','支付机构管理账户情况统计'],['18','预付卡商户白名单'],['19','支付机构合作行每日账户余额列表'],['20','TXT文件下载']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '日期*',
			blankText: '请选择日期',
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
				var reportName = queryForm.getForm().findField('idreportName').getValue();
				
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var date = queryForm.getForm().findField('date').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表日期。',queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T80206Action_downloadYQ.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+ action.result.msg;
					},
//					failure : function(form, action) { 
//						showErrorMsg(action.result.msg,queryForm);
//					}
					failure : function(form, action) {
						showErrorMsg(action.result.msg,queryForm);
					}
				});
			}
		},
			{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})
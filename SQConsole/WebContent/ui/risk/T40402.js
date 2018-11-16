
Ext.onReady(function() {
	var mchntForm = new Ext.form.FormPanel({
        title: '新增事前风险控制信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 180,
		buttonAlign: 'center',
		waitMsgTarget: true,
		labelAlign: 'left',	
		autoHeight:true,
        items: [{
        	layout:'column',
        	items: [{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户名称*',
					id: 'idMCHT_NM',
					name: 'MCHT_NM',
					maxLength: 100,
					allowBlank: false,
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '营业执照号*',
					id: 'idLICENSE_NO',
					name: 'LICENSE_NO',
					maxLength: 20,
					vtype:'alphanum', 
	                vtypeText:'只能输入字母或数字',
					allowBlank: false,
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '组织机构代码',
					id: 'idORG_CODE',
					name: 'ORG_CODE',
					maxLength: 30,
//					allowBlank: false,2012.07.27 deleted
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '法人身份证号码*',
					id: 'idIDENTITY',
					name: 'IDENTITY',
					maxLength: 20,
					allowBlank: false,
					anchor: '90%'
		        }]   		
        	}]
				},{
				xtype: 'panel',
			    layout: 'form',
			    autoScroll:true,
			    title: '风险分数',
			    labelWidth: 200,
                height: 400,
                items: [{
                	region: 'center',
                	layout: 'form',
                	items: [{
	        		columnWidth: .33,
	        		layout: 'form',
	        		allowBlank : false,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '营业用地性质*',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender',
                     name:'gender',
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '自有', name: 'MCHT_TYPE', inputValue: 01},          
                           {boxLabel: '租用', name: 'MCHT_TYPE', inputValue: 02}
                           ]  
				},{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			 fieldLabel: '营业面积*',
	       			 labelStyle: 'padding-left: 5px',
	       			 layout: 'form',
	       			 id   : 'gender1',
                     name:'gender1',
                     columns: [250,.52,.48],
					items: [           
                           {boxLabel: '20平米以下', name: 'MAIN_BUS_NUM', inputValue: 01},          
                           {boxLabel: '21-50平米', name: 'MAIN_BUS_NUM', inputValue: 02},
                           {boxLabel: '51-100平米 ', name: 'MAIN_BUS_NUM', inputValue: 03},
                           {boxLabel: '101-200平米', name: 'MAIN_BUS_NUM', inputValue: 04},
                           {boxLabel: '200平米以上', name: 'MAIN_BUS_NUM', inputValue: 05}
                           ]
                           },
                           	{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			fieldLabel: '主营业务数量范围*',
	       			labelStyle: 'padding-left: 5px',
	       			id   : 'gender4',
                    name:'gender4',
                    columns: [250,.52,.48],
					items: [           
                       {boxLabel: '数量(0–1)', name: 'PARAM1', inputValue: 01},          
                       {boxLabel: '数量(2–3)', name: 'PARAM1', inputValue: 02},
                       {boxLabel: '数量(4–5)', name: 'PARAM1', inputValue: 03},
                       {boxLabel: '数量(6–7)', name: 'PARAM1', inputValue: 04},
                       {boxLabel: '数量(大于7)', name: 'PARAM1', inputValue: 05}
                       ]
           },
           /*	{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '员工数量',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender5',
                     name:'gender5',
                     columns: [250,.52,.48],
					items: [           
                           {boxLabel: '数量(0–5)', name: 'PARAM2', inputValue: 01},          
                           {boxLabel: '数量(6–20)', name: 'PARAM2', inputValue: 02},
                           {boxLabel: '数量(21 –50)', name: 'PARAM2', inputValue: 03},
                           {boxLabel: '数量(51–200)', name: 'PARAM2', inputValue: 04},
                           {boxLabel: '数量(大于200)', name: 'PARAM2', inputValue: 05}
                           ]        	
           },*/
           	/*{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '连锁门店数量',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender6',
                     name:'gender6',
                    columns: [250,.52,.48],
					items: [           
                           {boxLabel: '数量(单店)', name: 'PARAM3', inputValue: 01},          
                           {boxLabel: '数量(2–10)', name: 'PARAM3', inputValue: 02},
                           {boxLabel: '数量(11–20)', name: 'PARAM3', inputValue:03},
                           {boxLabel: '数量(21–50)', name: 'PARAM3', inputValue: 04},
                           {boxLabel: '数量(大于50)', name: 'PARAM3', inputValue: 05}
                           ]        	
           },*/
           	{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			fieldLabel: '商户来源*',
	       			labelStyle: 'padding-left: 5px',
	       			id   : 'gender7',
                    name:'gender7',
                    columns: [250,.52,.48],
					items: [           
                           {boxLabel: '自己发展', name: 'PARAM4', inputValue: 01},          
                           {boxLabel: '其他收单机构推荐', name: 'PARAM4', inputValue: 02},
                           {boxLabel: '其他第三方推荐', name: 'PARAM4', inputValue: 03},
                           {boxLabel: '主动上门要求装机', name: 'PARAM4', inputValue: 04}
                           ]        	
           },
         	{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			fieldLabel: '商户交易类型*',
	       			labelStyle: 'padding-left: 5px',
	       			id   : 'gender9',
                    name:'gender9',
                    columns: [250,.52,.48],
					items: [           
                           {boxLabel: '实地交易', name: 'PARAM6', inputValue: 01},          
                           {boxLabel: '电话购物', name: 'PARAM6', inputValue: 02},
                           {boxLabel: '邮购', name: 'PARAM6', inputValue: 03},
                           {boxLabel: '网上交易', name: 'PARAM6', inputValue: 04}
                           ]        	  
           },
           /*	{
          	        columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户付款方式',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender15',
                     name:'gender15',
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '赊购', name: 'PARAM7', inputValue: 01},          
                           {boxLabel: '分期付款', name: 'PARAM7', inputValue: 02},
                           {boxLabel: '钱货两清', name: 'PARAM7', inputValue: 03},
                           {boxLabel: '预付款', name: 'PARAM7', inputValue: 04}
                         
                           ]        	  
           }*/
                {  
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			 fieldLabel: '商户营业场所*',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender2',
                     name:'gender2',
                     //columns: [250,.50,.50,.38,.50,.33],
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '非封闭批发市场或街边商铺的批发类商户 ', name: 'RISK_INDUSTRY', inputValue: 01},          
                           {boxLabel: '非封闭小区居民楼服务类或贸易公司', name: 'RISK_INDUSTRY', inputValue: 02},
                           {boxLabel: '位于写字楼的零售或批发类商户 ', name: 'RISK_INDUSTRY', inputValue: 03},
                           {boxLabel: '封闭小区或商住两用楼的服务类商户 ', name: 'RISK_INDUSTRY', inputValue: 04},
                           {boxLabel: '封闭批发市场或封闭市场商铺的商户', name: 'RISK_INDUSTRY', inputValue: 05},
                            {boxLabel: '街边零售类商铺', name: 'RISK_INDUSTRY', inputValue: 06},
                            {boxLabel: '其他', name: 'RISK_INDUSTRY', inputValue: 07}
                           ]
                           },{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			 fieldLabel: '商户服务类别码*',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender8',
                     name:'gender8',
                    columns: [250,.50,.49],
					items: [           
                           {boxLabel: '单店经营的建材批发单位', name: 'PARAM5', inputValue: 01},          
                           {boxLabel: '单店经营的五金器材及用品批发单位', name: 'PARAM5', inputValue: 02},
                           {boxLabel: '单店经营的花木栽种用品,苗木,花卉批发单位', name: 'PARAM5', inputValue: 03},
                           {boxLabel: '单店经营的电器零件和设备单位', name: 'PARAM5', inputValue: 04},
                           {boxLabel: '市场内经营的计算机,算机外围设备批发单位', name: 'PARAM5', inputValue: 05},
                           {boxLabel: '无航空运输销售代理业务经营批准证书复印件票务代理', name: 'PARAM5', inputValue: 06},
                           {boxLabel: '单店经营的贵重珠宝销售单位', name: 'PARAM5', inputValue: 07},
                           {boxLabel: '各类单店经营的杂货店,便利店', name: 'PARAM5', inputValue: 08},
                           {boxLabel: '单店经营电信服务单位本地长途电话(固话,移动缴费类)', name: 'PARAM5', inputValue: 10},
                           {boxLabel: '单店经营的旅行社', name: 'PARAM5', inputValue: 09},
                           {boxLabel: '单店经营的通讯设备(电话销售)销售单位', name: 'PARAM5', inputValue: 11},
                           {boxLabel: '单店经营的计算机网络/信息服务单位', name: 'PARAM5', inputValue: 12},
                           {boxLabel: '单店经营的计算机软件商店', name: 'PARAM5', inputValue: 13},
                           {boxLabel: '单店经营的文具店,办公室,学校用品商店', name: 'PARAM5', inputValue: 14},
                           {boxLabel: '单店经营的五金商店', name: 'PARAM5', inputValue: 15},
                           {boxLabel: '单店经营的美容,美发类单位', name: 'PARAM5', inputValue: 16},
                           {boxLabel: '以上都不是', name: 'PARAM5', inputValue: 17}
                           ]        	
           },
           /*	{
                   columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户受理类型',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender11',
                     name:'gender11',
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '受理人民币卡', name: 'PARAM8', inputValue: 01},          
                           {boxLabel: '受理外卡', name: 'PARAM8', inputValue: 02},
                           {boxLabel: '外卡人民卡兼收', name: 'PARAM8', inputValue: 03}
                           ]        	  
           },*/
           /*	{
                   columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '收单经验',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender12',
                     name:'gender12',
                       columns: [250,.52,.48],
					items: [           
                           {boxLabel: '有人民币卡及外卡收单经验', name: 'PARAM9', inputValue: 01},          
                           {boxLabel: '仅有外卡收单经验', name: 'PARAM9', inputValue: 02},
                           {boxLabel: '仅有人民币卡收单经验', name: 'PARAM9', inputValue: 03},
                           {boxLabel: '无任何收单经验', name: 'PARAM9', inputValue: 04}                        
                           ]        	            
           },*/
          /* 	{
                    columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '是否曾被拒绝',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender13',
                     name:'gender13',
                       columns: [250,.52,.48],
					items: [           
                           {boxLabel: '不曾被拒绝过', name: 'PARAM10', inputValue: 01},          
                           {boxLabel: '以前曾被拒', name: 'PARAM10', inputValue: 02}                    
                           ]        	                 
           },*/ 
           {    
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			allowBlank : false,
	       			 fieldLabel: '商户注册资本*',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender3',
                     name:'gender3',
                     columns: [250,.52,.48],
					items: [
					       {boxLabel: '无', name: 'REG_FUND', inputValue: 07},
                           {boxLabel: '10万元以下 ', name: 'REG_FUND', inputValue: 01},          
                           {boxLabel: '10-20万元', name: 'REG_FUND', inputValue: 02},
                           {boxLabel: '20-50万元 ', name: 'REG_FUND', inputValue: 03},
                           {boxLabel: '50-100万元 ', name: 'REG_FUND', inputValue: 04},
                           {boxLabel: '100-500万元', name: 'REG_FUND', inputValue: 05},
                           {boxLabel: '500万以上', name: 'REG_FUND', inputValue: 06}]
                },{                     
                    columnWidth: .33,
	       			xtype: 'checkboxgroup',
	       			 fieldLabel: '申请安装地区*',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender14',
                     name:'gender14',
                      columns: [250,.52,.48],
					items: [  {       
						xtype: 'checkboxgroup',
						items: [  
                             {boxLabel: '广东', name: 'PARAM27',id:'PARAM27', inputValue: 01,
                             listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM27').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}	},          
                             {boxLabel: '湖北', name: 'PARAM15',id:'PARAM15', inputValue: 02,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM15').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }},
                             {boxLabel: '河南', name: 'PARAM16',id:'PARAM16', inputValue: 03,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM16').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }} ,
                             {boxLabel: '海南', name: 'PARAM17',id:'PARAM17', inputValue: 04,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM17').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }} 
                            ]
					}
					,{
					xtype: 'checkboxgroup',
						items: [  
                            {boxLabel: '山东', name: 'PARAM18',id:'PARAM18', inputValue: 05, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM18').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }} ,
                            {boxLabel: '浙江', name: 'PARAM21',id:'PARAM21', inputValue: 06, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM21').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}} ,
                            {boxLabel: '湖南', name: 'PARAM19',id:'PARAM19', inputValue: 07, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM19').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}},
                            {boxLabel: '湖南', name: 'PARAM26',id:'PARAM26', inputValue: 08,hidden:true}  
                             ]
					}
					,{
						xtype: 'checkboxgroup',
						items: [ 
                            {boxLabel: '河北', name: 'PARAM20',id:'PARAM20', inputValue: 09, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM20').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);}
						}}} ,
                            {boxLabel: '山西', name: 'PARAM22',id:'PARAM22', inputValue: 10, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM22').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);}
						}}} ,
                            {boxLabel: '江西', name: 'PARAM23',id:'PARAM23', inputValue: 11, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM23').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
										Ext.getCmp('PARAM25').setValue(false);
									}
						}}},
                            {boxLabel: '安徽', name: 'PARAM24',id:'PARAM24', inputValue: 12, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM24').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);}
						}}} 
                     ]}
					,{
						xtype: 'checkboxgroup',
						items: [ 
                            {boxLabel: '其他地区', name: 'PARAM25',id:'PARAM25', inputValue: 13, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM25').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);}
						}}} 
                            ]}
                           ]        	                                       
           },{
             	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	allowBlank: true,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'checkbox',
					fieldLabel: '业务人员是否上门核实',
					labelStyle: 'padding-left: 5px',
					id: 'idPARAM12',
					name: 'PARAM12'
				}]
           },{
             	 columnWidth: .33,
       			 xtype: 'checkboxgroup',
       			 allowBlank: true,
       			 fieldLabel: '其它',
       			 labelStyle: 'padding-left: 5px',
       			 id   : 'idPARAM13',
                 name:'PARAM13',
				 items: [           
                       {boxLabel: '连续经营3年以上', name: 'rbauto15',id:'idauto15', inputValue: 01} ,  
                       {boxLabel: '单日营业额2万元以上', name: 'rbauto16',id:'idauto16', inputValue: 01},
                       {boxLabel: '缴纳保证金', name: 'rbauto17',id:'idauto17', inputValue: 01} ,
                       {boxLabel: '缴纳机具押金', name: 'rbauto18',id:'idauto18', inputValue: 01}
                 ]}
               ,{
                   columnWidth: .33,
	        	   xtype: 'panel',
	        	   layout: 'form',
	       		   items: [{
	       		   xtype: 'dynamicCombo',
		            methodName: 'getEnt',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '企业性质*',
					allowBlank : false,
					editable: true,
					id: 'idPREMISES',
					hiddenName:'PREMISES',
					anchor: '90%'
				}]
           },{
           buttons: [{
               text: '保存',
               id: 'save',
               name: 'save',
               handler : function() {
        	   		if(mchntForm.findById('idMCHT_NM').getValue().trim() == ""){
        	   			showErrorMsg('商户名称不能为空或空字符串',mchntForm);
        	   			mchntForm.findById('idMCHT_NM').setValue('');
						return ;
        	   		}
        	   		if(mchntForm.findById('idLICENSE_NO').getValue().trim() == ""){
        	   			showErrorMsg('营业执照号不能为空或空字符串',mchntForm);
        	   			mchntForm.findById('idLICENSE_NO').setValue('');
						return ;
        	   		}else if(mchntForm.findById('idLICENSE_NO').getValue().indexOf('O') != '-1'){
        	   			showErrorMsg('营业执照号不能包含大写字母O',mchntForm);
						return;
        	   		}	
        	   		if(mchntForm.findById('idIDENTITY').getValue().trim() == ""){
        	   			showErrorMsg('法人身份证号码不能为空或空字符串',mchntForm);
        	   			mchntForm.findById('idIDENTITY').setValue('');
						return ;
        	   		}
           		subSave();
               }
           },{
               text: '重置',
               handler: function() {
   				mchntForm.getForm().reset();
   			}
           }]}]
                }]	
			}]
    });
     
    function subSave(){
    	if(mchntForm.getForm().isValid()) {
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		btn.disable();
		frm.submit({
				url:'T40402Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
						btn.enable();
					showSuccessDtl(action.result.msg,mchntForm);
					mchntForm.getForm().reset();
				},
				failure : function(form,action) {
					btn.enable();
					showErrorMsg(action.result.msg,mchntForm);	
				},
				params: {
					PARAM18:Ext.getCmp('PARAM18').getValue(),
					PARAM15:Ext.getCmp('PARAM15').getValue(),
					PARAM16:Ext.getCmp('PARAM16').getValue(),
					PARAM17:Ext.getCmp('PARAM17').getValue(),
					PARAM19:Ext.getCmp('PARAM19').getValue(),
					PARAM20:Ext.getCmp('PARAM20').getValue(),
					PARAM21:Ext.getCmp('PARAM21').getValue(),
					PARAM22:Ext.getCmp('PARAM22').getValue(),
					PARAM23:Ext.getCmp('PARAM23').getValue(),
					PARAM24:Ext.getCmp('PARAM24').getValue(),
					PARAM25:Ext.getCmp('PARAM25').getValue(),
					PARAM26:Ext.getCmp('PARAM26').getValue(),
					PARAM27:Ext.getCmp('PARAM27').getValue()
					}
			});
		}
    }
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});
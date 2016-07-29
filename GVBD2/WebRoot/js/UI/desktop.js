Ext.require([
    'Ext.form.*',
    'Ext.tab.*',
    'Ext.tip.QuickTipManager'
]);
Ext.onReady(function() {

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
    var win,import_val,production_val;
    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
    	 // fields一定要明确指定type，这样有很多好处，比如filter能直接设为true，便会默认按照store-fields设置好的type来确定filter的类型。
        fields : [{
                  name : 'name',
                  type : 'int'
               },{
                  name : 'value',
                  type : 'float'
               }, {
                  name : 'cx',
                  type : 'string'
               }, {
                  name : 'cy',
                  type : 'string'
               }],
        autoLoad: true,
        proxy: {
            // load using HTTP
            type: 'ajax',
            url: 'data/json.json',
            // the return will be XML, so lets set up a reader
            reader: {
            	root: "nodes"
            }
        }
    });
  
    function importfun(action) {
        if (!import_val) {
            var form = Ext.widget('form', {
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                enctype:'multipart/form-data',
                fileUpload:true,
                bodyPadding: 10,
                items: [{
                	id:'uploadfile',
                	xtype: 'textfield',
                    name: 'uploadfile',
                    inputType: 'file',
                    allowBlank: false
                },{
                	xtype: 'numberfield',
                	 id:'number1',
                     fieldLabel: '顶点数',
                     name: 'number',
                     margin:'10 0 10 0',
                     value: 1,
                     minValue: 1,
                     maxValue: 125,
                     allowNegative:false,
                     allowBlank: false
                }],

                buttons: [{
                    text: '取消',
                    handler: function() {
                        this.up('form').getForm().reset();
                        this.up('window').hide();
                    }
                }, {
                    text: '提交',
                    handler: function() {
                        if (this.up('form').getForm().isValid()) {
                        	console.log(Ext.getCmp('uploadfile').getValue());
                        	
                        	Ext.Ajax.request({
                        	    url: 'servlet/importData',
                        	    params: {uploadfile:Ext.getCmp('uploadfile').getValue(),number:Ext.getCmp('number1').getValue()},
                        	    async: false,
                        	    success: function(response){
                        	    	Ext.MessageBox.alert('ok!','数据生成成功');
                        	    },
                        	    error:function(response){
                        	    	Ext.MessageBox.alert('error!', '数据上传失败');
                        	    }
                        	});
//                        	this.up('form').getForm().submit({
//                                url: 'servlet/importData',
//                                waitMsg: 'Uploading file...',
//                                success: function(form,action){
//                                    msg('Success', 'Processed file on the server');
//                                }
//                            });
                            this.up('form').getForm().reset();
                            this.up('window').hide();
                            
                        }
                    }
                }]
            });
            import_val = Ext.widget('window', {
                title: action,
                closeAction: 'hide',
                width: 200,
                height: 150,
                layout: 'fit',
                resizable: true,
                modal: true,
                items: form
            });
        }
        import_val.show();
    }
    
    function production(action) {
        if (!production_val) {
            var form = Ext.widget('form', {
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                border: false,
                bodyPadding: 10,
                fieldDefaults: {
                    labelAlign: 'top',
                    labelWidth: 100,
                    labelStyle: 'font-weight:bold'
                },
                items: [{
                	xtype: 'numberfield',
                	id:'number',
                     fieldLabel: '顶点数',
                     name: 'number',
                     value: 1,
                     minValue: 1,
                     maxValue: 125,
                     allowNegative:false,
                     allowBlank: false
                },{
                	xtype: 'numberfield',
                	id:'avg',
                    fieldLabel: '基础度',
                    name: 'avg',
                    value: 1,
                    minValue: 1,
                    maxValue: 125,
                    allowBlank: false
                },{
                	xtype: 'numberfield',
                	id:'random',
                    fieldLabel: '随机度',
                    name: 'random',
                    value: 1,
                    minValue: 1,
                    maxValue: 125,
                    allowBlank: false
                }],

                buttons: [{
                    text: '取消',
                    handler: function() {
                        this.up('formimport').getForm().reset();
                        this.up('window').hide();
                    }
                }, {
                    text: '提交',
                    handler: function() {
                        if (this.up('form').getForm().isValid()) {
                        	Ext.Ajax.request({
                        	    url: 'servlet/proData',
                        	    params: {avg:Ext.getCmp('avg').getValue(),number:Ext.getCmp('number').getValue(),random:Ext.getCmp('random').getValue()},
                        	    async: false,
                        	    success: function(response){
                        	    	Ext.MessageBox.alert('ok!','数据生成成功');  
                        	    },
                        	    error:function(response){
                        	    	Ext.MessageBox.alert('error!','数据生成失败');
                        	    }
                        	});

                            this.up('form').getForm().reset();
                            this.up('window').hide();
                        }
                    }
                }]
            });

            production_val = Ext.widget('window', {
                title: action,
                closeAction: 'hide',
                width: 200,
                height: 250,
                layout: 'fit',
                resizable: true,
                modal: true,
                items: form
            });
        }
        production_val.show();
    }

    
    Ext.define('EB.view.content.SingleView', {
		extend : 'Ext.panel.Panel',
		alias : 'widget.singleview',
		layout : 'fit',
		title : '图',
		autoScroll:true,
		frame :true,//背景颜色
		initComponent : function() {
			this.callParent(arguments);
		},
		onRender : function() {
			var me = this;
			me.doc = Ext.getDoc();
			me.callParent(arguments);
			me.drawMap();
		},
		
		drawMap : function() {
			var width = 5000, height = 1000;
		    var zoom = d3.behavior.zoom().scaleExtent([1, 10]).on("zoom", zoomed); 
		    var svg = d3.select("#" + this.id + "-body").append("svg").attr("width", width).attr("height", height);
		    
			d3.json("data/json.json", function(json){
				var circles_group = svg.append("g").call(zoom);
				var lines = circles_group.selectAll("line").data(json.links).enter().append("line");			
				var lineAttribute=lines
				.attr("x1",function(d){return d.x1})
				.attr("y1",function(d){return d.y1})
				.attr("x2",function(d){return d.x2})
				.attr("y2",function(d){return d.y2})
				.attr("stroke","#000");
				var circles = circles_group.selectAll("circle").data(json.nodes).enter().append("circle");
				var circleAttributes = circles
				.attr("cx",function(d){return d.cx})
				.attr("cy",function(d){return d.cy})
				.attr("r",5)
				.attr("fill","#6495ed");							
										
				var texts = circles_group.selectAll("text").data(json.nodes).enter().append("text");						
				var textAttribute=texts
				.attr("dx",function(d){return d.cx})
				.attr("dy",function(d){return d.cy})
				.text(function(d){return d.name})						
				var lines = circles_group.selectAll("line").data(json.links).enter().append("line");						
				var lineAttribute=lines
				.attr("x1",function(d){return d.x1})
				.attr("y1",function(d){return d.y1})
				.attr("x2",function(d){return d.x2})
				.attr("y2",function(d){return d.y2})
				.attr("fill","#000");				
			});
			function zoomed() {
				d3.select(this).attr("transform", 
					"translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
			}
		}

	});   

   
  //
	var windowExt = Ext.create('Ext.container.Viewport', {
		layout : 'border',
		items : [{// 上方面板
			region : 'north',
			xtype : 'panel',
			bodyPadding : 5,
			title : 'GVBD---Graph Visualization Base on  Distributed system',
			renderTo : Ext.getBody(),
			tbar : [{
						xtype : 'button',
						text : '文件',
						// arrowAlign : 'bottom',
						menu : [
						        {text : '导入数据',handler: Ext.Function.pass(importfun, '导入数据')},
						        {text : '生成数据',handler: Ext.Function.pass(production, '生成数据')}
						       ]

					}, {
						xtype : 'button',
						text : '工作区',
						menu : [
						        {text : '新增工作区',handler: Ext.Function.pass(addTab, '导入数据')}
						       ]

					}, {
						xtype : 'button',
						text : '视图'
					}, {
						xtype : 'button',
						text : '工具'
					}, {
						xtype : 'button',
						text : '窗口'
					}, {
						xtype : 'button',
						text : '插件'
					}, {
						xtype : 'button',
						text : '帮助',
						handler: Ext.Function.pass(aboutus)
					}]

		  }, {	// 左侧面板
					region : 'west',
					collapsible : true,
					split : true,
					title : '数据操作',
					items : [{
						xtype : 'gridpanel',
						width : 291,
						height : 400,
						store: store,
						columns: [
					                { header: 'Name',  dataIndex: 'name',width:40},
					                { header: 'Value', dataIndex: 'value',width:50},
					                { header: 'CX', dataIndex: 'cx'},
					                { header: 'CY', dataIndex: 'cy'}
				                ],
				     listeners: { 'itemclick': function (view, record, item, index, e) {
				                    Ext.getCmp('analysisid').body.update("顶点数为：12<br />边数为：45");
				                  }
				      }
					},{
						xtype : 'form',
						width : 291,
						height : 500,
						
						title : '布局',
						collapsible: true, //可折叠
					    items:[{
					      	    width:          250,
							    xtype:          'combo',
							    margin:10,
								value:          '请选择一种布局方法',
								name:           'title',
								id:           'title',
								displayField:   'name',
								valueField:     'value',
								fieldLabel: '布局方法',
								mode:'local',
								store:Ext.create('Ext.data.Store', {
									fields : ['name', 'value'],
									data   : [
										   {name : '请选择一种布局方法',   value: '0'},
										   {name : 'ChengLayout',   value: 'ChengLayout'},
										   {name : 'ForceLayout',  value: 'ForceLayout'},
										   ]
								  }),
								 listeners:{
										'change':function(thisField,newValue,oldValue,epots){  
										        if(newValue === 'ChengLayout' ){
										                	Ext.getCmp("speed").setVisible(false);  
										                	Ext.getCmp("kvalue").setVisible(true);  
										                	Ext.getCmp("forceThreshold").setVisible(true); 
										                	Ext.getCmp("isDirected").setVisible(true);
										                	Ext.getCmp("cool").setVisible(true);
										                	Ext.getCmp("temperature").setVisible(true);
										                	Ext.getCmp("deep").setVisible(true);
										                	Ext.getCmp("times").setVisible(true);
										                	Ext.getCmp('save').setDisabled(false);
										        }else if (newValue === 'ForceLayout'){
										                	Ext.getCmp("speed").setVisible(true);  
										                	Ext.getCmp("kvalue").setVisible(true);  
										                	Ext.getCmp("forceThreshold").setVisible(true); 
										                	Ext.getCmp("isDirected").setVisible(true);
										                	Ext.getCmp("cool").setVisible(false);
										                	Ext.getCmp("temperature").setVisible(false);
										                	Ext.getCmp("deep").setVisible(false);
										                	Ext.getCmp("times").setVisible(false);
										                	Ext.getCmp('save').setDisabled(false);
										        }else{
										                	Ext.getCmp("speed").setVisible(false);  
										                	Ext.getCmp("kvalue").setVisible(false);  
										                	Ext.getCmp("forceThreshold").setVisible(false); 
										                	Ext.getCmp("isDirected").setVisible(false);
										                	Ext.getCmp("cool").setVisible(false);
										                	Ext.getCmp("temperature").setVisible(false);
										                	Ext.getCmp("deep").setVisible(false);
										                	Ext.getCmp("times").setVisible(false);
										                	Ext.getCmp('save').setDisabled(true);
										         }//else
								         }//change
						           }//listeners
					           },{
					               xtype: 'numberfield',
					               margin:10,
					               name: 'speed',
					               id:'speed',
					               fieldLabel: 'Speed 值',
					               value: 5,
					               minValue: 1,
					               maxValue: 50,
					               allowBlank: false,
					               hidden: true  
					           },{
					               xtype: 'numberfield',
					               margin:10,
					               name: 'kvalue',
					               id:'kvalue',
					               fieldLabel: 'K 值',
					               value: 1,
					               minValue: 1,
					               maxValue: 50,
					               allowBlank: false,
					               hidden: true  
					           },{
					               xtype: 'numberfield',
					               margin:10,
					               name: 'times',
					               id:'times',
					               fieldLabel: 'Times',
					               value: 200,
					               minValue: 1,
					               maxValue: 500,
					               allowBlank: false,
					               hidden: true  
					           },{
					               xtype: 'checkboxfield',
					               name: 'isDirected',
					               id:'isDirected',
					               margin:10,
					               fieldLabel: '是否是有向图',
					               boxLabel: '是',
					               hidden: true  
					           },{
					                xtype: 'numberfield',
					                fieldLabel: 'Cool 值',
					                name: 'cool',
					                id: 'cool',
					                value:0.95,
					                margin:10,
					                minValue: 1,
					                maxValue: 100,
					                allowDecimals: true,
					                decimalPrecision: 1,
					                step: 0.1,
					                allowBlank: false,
					                hidden: true  
					            },{
					                xtype: 'numberfield',
					                fieldLabel: 'ForceThreshold',
					                name: 'forceThreshold',
					                id: 'forceThreshold',
					                margin:10,
					                minValue: 1,
					                maxValue: 100,
					                allowDecimals: true,
					                decimalPrecision: 1,
					                step: 0.1,
					                allowBlank: false,
					                hidden: true  
					            },{
					                xtype: 'numberfield',
					                fieldLabel: 'Temperature',
					                name: 'temperature',
					                id: 'temperature',
					                margin:10,
					                minValue: 1,
					                maxValue: 100,
					                allowDecimals: true,
					                decimalPrecision: 1,
					                step: 0.1,
					                allowBlank: false,
					                hidden: true  
					            },{
						               xtype: 'numberfield',
						               margin:10,
						               name: 'deep',
						               id: 'deep',
						               fieldLabel: 'Deep 值',
						               value: 3,
						               minValue: 1,
						               maxValue: 50,
						               allowBlank: false,
						               hidden: true  
						           },{
						        	   xtype: "button", 
						        	   text: "Save",
						        	   id:"save",
						        	   margin:10,
						        	   width:100,
						        	   disabled:true,
						        	   handler: function() {
						        		   if (this.up('form').getForm().isValid()) {
					                        	Ext.Ajax.request({
					                        	    url: 'servlet/postData',
					                        	    params: {
					                        	    	title:Ext.getCmp('title').getValue(),
					                        	    	speed:Ext.getCmp('speed').getValue(),
					                        	    	kvalue:Ext.getCmp('kvalue').getValue(),
					                        	    	isDirected:Ext.getCmp('isDirected').getValue(),  
					                        	    	cool:Ext.getCmp('cool').getValue(), 
					                        	    	forceThreshold:Ext.getCmp('forceThreshold').getValue(), 
					                        	    	temperature:Ext.getCmp('temperature').getValue(), 
					                        	    	deep:Ext.getCmp('deep').getValue(), 
					                        	    	times:Ext.getCmp('times').getValue(), 
					                        	    },
					                        	    async: false,
					                        	    success: function(response){
					                        	    	Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent. We will respond as soon as possible.');
					                        	    },
					                        	    error:function(response){
					                        	    	Ext.MessageBox.alert('error!', 'Your inquiry has been sent. We will respond as soon as possible.');
					                        	    }
					                        	});
					                            this.up('form').getForm().reset();
						               }
						             }
						           },{
						        	   xtype: "button", 
						        	   text: "Cancel",
						        	   margin:10,
						        	   width:100,
						        	   handler: function() {
						                   this.up('form').getForm().reset();
						               }
						           }],
						      
					          
					}]
				}, {
					region : 'east',
					collapsible : true,
					split : true,
					title : '数据信息',
					items : [{
								xtype : 'panel',
								id: 'datainfoid',
								width : 200,
								height : 400,
								title : '统计',
								html : '顶点数1<br/>边数：2',
							},{
								xtype : 'panel',
								id: 'analysisid',
								width : 200,
								height : 500,
								title : '分析',
								html : '',
							}]
				}, {
					region : 'center',
					xtype : 'tabpanel', // TabPanel itself has no title
					items :	Ext.widget('singleview', {
								width : 5000,height : 5000
					})
			}]
	});
	function handleActivate() {
		var dataroot="data/json.json"; 
		$.getJSON(dataroot, function(data){ 
		   var node = data.nodes.length;
		   var link = data.links.length/2;
		   Ext.getCmp('datainfoid').body.update("顶点数为："+node+"<br />边数为："+link);
		}); 

	}
	handleActivate();
	   var  index = 0;
	   
	    function addTab () {
	    	var tabs = windowExt.getComponent('tabpanel');
	 	   console.log(tabs);
            ++index;
            tabs.add({
                html: 'Tab Body ' + index + '<br/><br/>',
                iconCls: 'tabs',
                title: 'New Tab ' + index
            }).show();
        }
	  function  aboutus(){
		  aboutus = Ext.widget('window', {
              title: '关于',
              modal: true,
              html: 'he boxLabel contains a HTML link to the Terms of Use page; a special click listener opens this'+
              'he boxLabel contains a HTML link to the Terms of Use page; a special click listener opens this',
              width: 700,
              height: 400,
              bodyStyle: 'padding: 10px 20px;',
              autoScroll: true,
             
          });
		  aboutus.show();
		  
		  
	  }
	
})

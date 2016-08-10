Ext.require([
    'Ext.form.*',
    'Ext.tab.*',
    'Ext.tip.QuickTipManager',
    'Ext.PagingToolbar',
    'Ext.chart.*'
]);

Ext.onReady(function() {

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
    
    var win,import_val,production_val,kmeans_val;
    var match = window.location.href;
    var s=match.indexOf("?"); 
    var jsonfile=match.substring(s+1);// t就是?后面的东西了 
    var datafile= 'data/'+jsonfile+'.json';
    var filename=jsonfile,filenumber;
    var storeChart = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data'],
        autoLoad: true, 
		  remoteSort: true,  
		  proxy: {
		      type: 'ajax',
		      url: 'dataLevel/'+jsonfile+'.json',
		      reader: {
		      	root: "data",
		      }
		  }
//        data: [
//            { 'name': 'metric one',   'data':10 },
//            { 'name': 'metric two',   'data': 7 },
//            { 'name': 'metric three', 'data': 5 },
//            { 'name': 'metric four',  'data': 2 },
//            { 'name': 'metric five1',  'data':27 },
//            { 'name': 'metric five2',  'data':27 },
//            { 'name': 'metric five3',  'data':27 },
//            { 'name': 'metric five4',  'data':27 },
//        ]
    });
    // create the Data Store

    var graphDataStore=Ext.create('Ext.data.Store',{
    	fields:[
                {name:'name',type:'string'},
                {name:'data',type:'int'}
            ],
//        data:[
//            {graphName:"A",graphData:700},
//            {graphName:"B",graphData:800},
//            {graphName:"C",graphData:600},
//            {graphName:"D",graphData:50}  
//        ]
          autoLoad: true, 
  		  remoteSort: true,  
  		  proxy: {
  		      type: 'ajax',
  		      url: 'dataTopK/'+jsonfile+'.json',
  		      reader: {
  		      	root: "data",
  		      }
  		  }
    });
    var store = Ext.create('Ext.data.Store', {
        fields : [{
            name : 'name',type : 'int'
         },{
            name : 'value',type : 'string'
         }, {
            name : 'cx',type : 'string'
         }, {
            name : 'cy',type : 'string'
         }],
		  autoLoad: true, 
		  remoteSort: true,  
		  pageSize: 15,
		  proxy: {
		      type: 'ajax',
		      url: datafile,
		      reader: {
		      	root: "nodes",
		      }
		  }
		});
    var pager = Ext.create("Ext.PagingToolbar", {
        //重要，指定分页所使用的store
        store:store,
        displayInfo:true,
        displayMsg:"第 {0}-{1}条 / 共 {2} 条",
        emptyMsg:"暂无记录"
    })
    var store1 = Ext.create('Ext.data.Store', {
    	fields : [{
    		name:'id',type : 'int'
    	},{
    		name:'value',type : 'string'
    	}],
    	autoLoad: true,
		  proxy: {
		      type: 'ajax',
		      url: 'servlet/getAlreadyData',
		      reader: {
			      	root: "data"
			 }
		  }  	
    });
    function importfun(action) {
        if (!import_val) {
        	 var uploadForm=new Ext.FormPanel({  
        	        id:'uploadForm',  
        	        width:520,  
        	        frame:true,  
        	        fileUpload: true,    
        	        autoHeight:true,  
        	        bodyStyle:'10px 10px 0px 10px',  
        	        labelWidth:50,  
        	        enctype: 'multipart/form-data',   
        	        defaults:{  
        	            anchor: '95%',  
        	            allowBlank: false  
        	        },  
        	        items:[{  
        	                xtype:'fileuploadfield',  
        	                emptyText: '请选择上传文件...',   
        	                fieldLabel: '文件',   
        	                id:'uploadFile',  
        	                name: 'upload',   
        	                allowBlank: false,     
        	                blankText: '文件名称不能为空.',     
        	                buttonCfg: {  
        	                            text: '选择...'// 上传文件时的本地查找按钮  
        	                }  
        	            },{
        	            	xtype: 'numberfield',
                       	    id:'number1',
                            fieldLabel: '顶点数',
                            name: 'number',
                            value: 1,
                            minValue: 1,
                            allowNegative:false,
                            allowBlank: false
        	            } 
        	        ],  
        	        buttons: [{  
        	                        text: '上传',  
        	                        handler: function(){  
        	                            if(uploadForm.getForm().isValid()){  
        	                                uploadForm.getForm().submit({  
        	                                    url:'servlet/importData',  
        	                                    method:'POST',  
        	                                    waitTitle: '请稍后',  
        	                                    waitMsg: '正在上传文档文件 ...',  
        	                                    success: function(fp, action){  
        	                                    	filenumber = Ext.getCmp("number1").getValue();
        	                                    	filename = action['result']['fileName'].substring(0,action['result']['fileName'].indexOf("."));
        	                                    	Ext.MessageBox.alert('成功', '导入数据成功'); 
        	                                        Ext.getCmp("uploadFile").reset();          // 指定文件字段的id清空其内容  
        	                                        import_val.hide();
        	                                    },  
        	                                    failure: function(fp, action){  
        	                                        Ext.MessageBox.alert('警告', '导入数据失败');    
        	                                        import_val.hide();  
        	                                    }  
        	                                });  
        	                            }  
        	                        }  
        	                    },{  
        	                        text: '重置',  
        	                        handler: function(){  
        	                            uploadForm.getForm().reset();  
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
                items: uploadForm
            });
        }
        import_val.show();
    }
    function kmeans(action) {
        if (!kmeans_val) {
        	 var kmeansForm=new Ext.FormPanel({  
        	        id:'kmeansForm',  
        	        width:520,  
        	        frame:true,    
        	        autoHeight:true,  
        	        bodyStyle:'10px 10px 0px 10px',  
        	        labelWidth:50,  
        	        items:[{
        	            	xtype: 'numberfield',
                       	    id:'number',
                            fieldLabel: 'K值',
                            name: 'number',
                            value: 1,
                            minValue: 1,
                            allowNegative:false,
                            allowBlank: false
        	            },{
        	            	xtype:'textfield',
        	            	id:'jsonname',
        	            	fieldLabel:'文件名',
        	            	name:'jsonname',
        	            	value:jsonfile,
                            hidden: true, 
                            hideLabel:true,
//        	            },{
//    					    xtype:          'combo',
//    					    height :        20,
//    					    emptyText:      '请选择一个文件',
//    						name:           'jsonname',
//    						id:           'jsonname',
//    						displayField:   'value',
//    						valueField:     'value',
//    						fieldLabel: '数据集',
//    						mode:'local',
//    						store:store1,
    				  }
        	        ],  
        	        buttons: [{  
        	                        text: '提交',  
        	                        handler: function(){  
        	                            if(kmeansForm.getForm().isValid() && jsonfile){
        	                            	console.log(jsonfile);
        	                                kmeansForm.getForm().submit({  
        	                                    url:'servlet/kmeans',  
        	                                    method:'POST',
        	                                    success: function(fp, action){   

        	                                    	Ext.MessageBox.alert('Ok!', '聚类生产中,请稍后.');
				                        	    	if(jsonfile){
				                        	    		window.location.href="UI/desktop.jsp?kmeans_"+jsonfile; 
				                        	    	}else{
				                        	    		document.location.reload();
				                        	    	}       	                                        
        	                                        kmeans_val.hide();
        	                                    },  
        	                                    failure: function(fp, action){  
        	                                        Ext.MessageBox.alert('警告', '数据聚类失败');    
        	                                        kmeans_val.hide();  
        	                                    }  
        	                                });  
        	                            }  
        	                        }  
        	                    },{  
        	                        text: '重置',  
        	                        handler: function(){  
        	                            kmeansForm.getForm().reset();  
        	                        }  
        	                    }]  
        	    });  
        	      
        	 
            kmeans_val = Ext.widget('window', {
                title: action,
                closeAction: 'hide',
                width: 300,
                height: 100,
                layout: 'fit',
                resizable: true,
                modal: true,
                items: kmeansForm
            });
        }
        kmeans_val.show();
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
                    fieldLabel: '文件名称',
                    id : 'filename',
                    afterLabelTextTpl: required,
                    xtype:'textfield',
                    name: 'name',
                    allowBlank: false
                },{
                	xtype: 'numberfield',
                	 id:'number',
                     fieldLabel: '顶点数',
                     name: 'number',
                     value: 1,
                     minValue: 1,
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
                        this.up('form').getForm().reset();
                        this.up('window').hide();
                    }
                }, {
                    text: '提交',
                    handler: function() {
                    	
                        if (this.up('form').getForm().isValid()) {
                        	Ext.Ajax.request({
                        	    url: 'servlet/proData',
                        	    params: {avg:Ext.getCmp('avg').getValue(),filename:Ext.getCmp('filename').getValue(),number:Ext.getCmp('number').getValue(),random:Ext.getCmp('random').getValue()},
                        	    async: false,
                        	    success: function(response){
                        	    	filename = Ext.getCmp('filename').getValue();
                        	    	filenumber = Ext.getCmp("number").getValue();
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
                height: 300,
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
			var width = 5000, height = 5000;
			//var radius = d3.scale.sqrt().range([0, 6]);//值域  
			var color = d3.scaleOrdinal().range(d3.schemeCategory20);
			var svg = d3.select("#" + this.id + "-body").append("svg").attr("width", width).attr("height", height);
			var tooltip = d3.select("#" + this.id + "-body").append("div").attr("class","tooltip").style("opacity",0.0);
		    transform = d3.zoomIdentity;
			d3.json(datafile, function(json){
				var circles_group = svg.append("g");
				var lines = circles_group.selectAll("line").data(json.links).enter().append("line");			
				var lineAttribute=lines
				.attr("x1",function(d){return d.x1})
				.attr("y1",function(d){return d.y1})
				.attr("x2",function(d){return d.x2})
				.attr("y2",function(d){return d.y2})
				.attr("stroke","#000");
				var circles = circles_group.selectAll("circle").data(json.nodes).enter().append("circle");
				var circleAttributes = circles
				.attr("cx",function(d){
					if(d.name==1)console.log(d.cx);
					return d.cx})
				.attr("cy",function(d){
					if(d.name==1)console.log(d.cy);
					return d.cy
					})
				.attr("r", 5)  
				.on("mousemove", mousemove)
				.on("mouseout", mouseout)
				//.attr("fill","#6495ed");
				.style("fill", function(d, i) { 
					if(d.color){
						return color(d.color);  	
					}else{
						return color(1); 
					}
					//console.log(i);
					
					
				});
				
//				circles.append("title")
//				.text(function(d){return d.value})
//				.style("font-size","100px");
				
				
//				var texts = circles_group.selectAll("text").data(json.nodes).enter().append("text");						
//				var textAttribute=texts
//				.attr("dx",function(d){return d.cx})
//				.attr("dy",function(d){return d.cy})
//				.style("text-anchor", "middle")
//				.attr("font-size",5)
//				.text(function(d){return d.value})						
				var lines = circles_group.selectAll("line").data(json.links).enter().append("line");						
				var lineAttribute=lines
				.attr("x1",function(d){return d.x1})
				.attr("y1",function(d){return d.y1})
				.attr("x2",function(d){return d.x2})
				.attr("y2",function(d){return d.y2})
				.attr("fill","#000");		
				svg.call(d3.zoom()
			    	    .scaleExtent([1 / 8, 8])
			    	    .on("zoom", zoomed));
				function mousemove(d, i){
					tooltip.html(d.value)
					.style("opacity",1.0);
				}
				function mouseout(d, i){
					tooltip.html(d.value)
					.style("opacity",0.0);
				}
			    function zoomed() {
			    	circles_group.attr("transform", d3.event.transform);
			    }

			    function dragged(d) {
			    	  d3.select(this).attr("cx", d.x = d3.event.x).attr("cy", d.y = d3.event.y);
			    }

			   function phyllotaxis(radius) {
			    	  var theta = Math.PI * (3 - Math.sqrt(5));
			    	  return function(i) {
			    	    var r = radius * Math.sqrt(i), a = theta * i;
			    	    return {
			    	      x: width / 2 + r * Math.cos(a),
			    	      y: height / 2 + r * Math.sin(a)
			    	    };
			    	  };
			   }
			});
			
//			function zoomed() {
//				d3.select(this).attr("transform", 
//					"translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
//			}
//			//设置圆点的半径，圆点的度越大weight属性值越大，可以对其做一点数学变换                               
//			function  radius (d){   
//			if(!d.weight){//节点weight属性没有值初始化为1（一般就是叶子了）  
//			d.weight=1;  
//			}                                                
//			    return Math.log(d.weight)*10;                                     
//			}    
//		    function mouseover() {  
//		        d3.select(this).select("circle").transition()  
//		            .duration(750)  
//		            .attr("r", function(d){  //设置圆点半径                        
//		          return radius (d)+10;                            
//		       }) ;  
//		      }  
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
						text : '工具',
						menu : [
							        {text : '聚类',handler: Ext.Function.pass(kmeans, '聚类')}
							   ]
	
					}, {
						xtype : 'button',
						text : '工作区'
					}, {
						xtype : 'button',
						text : '视图'
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
					    width:          250,
					    xtype:          'combo',
					    height :        20,
					    margin:         '5 10 5 10',
					    emptyText:          '请选择一种已有数据集',
						name:           'title',
						id:           'title1',
						displayField:   'value',
						valueField:     'value',
						fieldLabel: '数据集',
						mode:'local',
						store:store1,
						listeners:{
							'change':function(thisField,newValue,oldValue,epots){ 
								  window.location.href="UI/desktop.jsp?"+newValue; 
							   }
							}
				  },{
						xtype : 'gridpanel',
						width : 380,
						height : 400,
						store :store,
						columns: [
					                { header: '索引值',  dataIndex: 'name',width:60},
					                { header: '节点名称', dataIndex: 'value',width:120},
					                { header: 'X坐标', dataIndex: 'cx'},
					                { header: 'Y坐标', dataIndex: 'cy'}
				                ],
//				     listeners: { 'itemclick': function (view, record, item, index, e) {
//				                    Ext.getCmp('analysisid').body.update("所在行："+record.data.name+"<br /> " +
//				                    		"数据值："+record.data.value+"<br />坐标值：("+parseFloat(record.data.cx).toFixed(2)+","+parseFloat(record.data.cy).toFixed(2)+")");
//				                  }
//				      },
				      bbar:pager
					},{
						xtype : 'form',
						width : 380,
						height : 500,
						title : '布局',
						collapsible: true, //可折叠
					    items:[{
					      	    width:          250,
							    xtype:          'combo',
							    margin:         10,
							    emptyText:          '--请选择一种布局方法--',
								name:           'title',
								id:           'title',
								displayField:   'name',
								valueField:     'value',
								fieldLabel: '布局方法',
								mode:'local',
								store:Ext.create('Ext.data.Store', {
									fields : ['name', 'value'],
									data   : [
										   {name : 'ChengLayout',   value: 'ChengLayout'},
										   {name : 'FRLayout',  value: 'FRLayout'},
										   ]
								  }),
								 listeners:{
										'change':function(thisField,newValue,oldValue,epots){  
										        if(newValue === 'ChengLayout' ){
										                	
										                	Ext.getCmp("kvalue").setVisible(true);  
										                	
										                	Ext.getCmp("isDirected").setVisible(true);
										                	Ext.getCmp("cool").setVisible(true);
										                	Ext.getCmp("temperature").setVisible(true);
										                	Ext.getCmp("deep").setVisible(true);
										                	Ext.getCmp("times").setVisible(true);
										                	Ext.getCmp('save').setDisabled(false);
										        }else if (newValue === 'FRLayout'){
										                	
										                	Ext.getCmp("kvalue").setVisible(true);  
										                
										                	Ext.getCmp("isDirected").setVisible(true);
										                	Ext.getCmp("cool").setVisible(true);
										                	Ext.getCmp("temperature").setVisible(true);
										                	Ext.getCmp("deep").setVisible(false);
										                	Ext.getCmp("times").setVisible(true);
										                	Ext.getCmp('save').setDisabled(false);
										        }else{
										                	
										                	Ext.getCmp("kvalue").setVisible(false);  
										                	
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
					               name: 'kvalue',
					               id:'kvalue',
					               fieldLabel: 'K 值',
					               value: 1,
					               minValue: 0,
					               maxValue: 1,
					               step:0.1,
					               allowDecimals: true,
					               decimalPrecision: 2,
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
					                minValue: 0,
					                maxValue: 1,
					                allowDecimals: true,
					                decimalPrecision: 2,
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
					                value:140,
					                allowDecimals: true,
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
						        		   console.log(filenumber);
						        		   if (this.up('form').getForm().isValid() && filename && filenumber) {
					                        	Ext.Ajax.request({
					                        	    url: 'servlet/postData',
					                        	    params: {
					                        	    	title:Ext.getCmp('title').getValue(),
					                        	    	kvalue:Ext.getCmp('kvalue').getValue(),
					                        	    	isDirected:Ext.getCmp('isDirected').getValue(),  
					                        	    	cool:Ext.getCmp('cool').getValue(), 
					                        	    	temperature:Ext.getCmp('temperature').getValue(), 
					                        	    	deep:Ext.getCmp('deep').getValue(), 
					                        	    	times:Ext.getCmp('times').getValue(),
					                        	    	filenumber:filenumber,
					                        	    	filename:filename
					                        	    },
					                        	    async: false,
					                        	    success: function(response){
					                        	    	Ext.MessageBox.alert('Ok!', '布局生产中,请稍后.');
					                        	    	if(filename){
					                        	    		window.location.href="UI/desktop.jsp?"+filename; 
					                        	    	}else{
					                        	    		document.location.reload();
					                        	    	}
					                        	    },
					                        	    error:function(response){
					                        	    	Ext.MessageBox.alert('error!', '布局出错了.');
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
						width : 300,
						height : 500,
						title : '统计',
						collapsible: true, //可折叠
					    items:[{
							    	xtype : 'panel',
									id: 'datainfoid',
									width : 300,
									height : 40,
									html : '',
					        	},{
									xtype: 'chart',
									width : 300,
									height : 400,
						            store: storeChart,
						            shadow: true,
						            axes: [{
						                type: 'Numeric',
						                position: 'left',
						                fields: ['data'],
						                grid: true,
						                minimum: 0
						            }, {
						                type: 'Category',
						                position: 'bottom',
						                fields: ['name'],
						                title: 'Month'
						            }],
						            series: [{
						            	type: 'column',
						            	axis: 'left',
						            	highlight: true,
						                tips: {
						                    trackMouse: true,
						                    width: 100,
						                    height: 28,
						                    renderer: function(storeItem, item) {
						                      
						                      this.setTitle(storeItem.get('name') + ': ' + storeItem.get('data'));
						                    }
						                  },
						                  xField: 'name',
						                  yField: 'data'
						            }]
									
					    		}]	
							},{
								xtype : 'panel',
								width : 300,
								height : 400,
								title : '分析',
								collapsible: true, //可折叠
							    items:[{
											xtype: 'chart',
											width : 300,
											height : 300,
								            store: graphDataStore,
								            shadow: false,
								            series: [{
								            	type: 'pie',
								                field: 'data',
								                label: {//这里能够使拼饼上面显示，该拼饼属于的部分
								                    field: 'name',
								                    display: 'rotate',
								                    font: '14px Arial'
								                },
								                highlight: {//这里是突出效果的声明，margin越大，鼠标悬停在拼饼上面，拼饼突出得越多
								                    segment: {
								                        margin: 5
								                    }
								                },
								                tips: {
								                    trackMouse: true,
								                    width: 170,
								                    height: 28,
								                    renderer: function(storeItem, item) {
								                      //calculate percentage.
								                      var total = 0;
								                      graphDataStore.each(function(rec) {
								                          total += rec.get('data');
								                      });
								                      this.setTitle(storeItem.get('name') + ': ' + Math.round(storeItem.get('data') / total * 100) + '%');
								                    }
								                  },
								                animate: true
								            }]
											
							    		}]	
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
		
		$.getJSON(datafile, function(data){ 
			filenumber = data.nodes.length;
		   var link = data.links.length;
		   Ext.getCmp('datainfoid').body.update("顶点数为："+filenumber+"<br />边数为："+link);
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

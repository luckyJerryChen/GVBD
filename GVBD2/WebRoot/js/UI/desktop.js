
Ext.onReady(function() {
	Ext.define('EB.view.content.SingleView', {
				extend : 'Ext.panel.Panel',
				alias : 'widget.singleview',
				layout : 'fit',
				title : '布局',
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
					
					
					var width = 5000, height = 5000

					var target = d3.select("#" + this.id + "-body");
				    var svg = target.append("svg").attr("width", width).attr(
							"height", height);
					
					d3.json("json3.json", function(json){
	
          
						var lines = svg.selectAll("line").data(json.links).enter().append("line");
						
						var lineAttribute=lines
						.attr("x1",function(d){return d.x1})
						.attr("y1",function(d){return d.y1})
						.attr("x2",function(d){return d.x2})
						.attr("y2",function(d){return d.y2})
						.attr("stroke","#000")
						.attr("stroke-width","0.1rem");
						
 						var circles = svg.selectAll("circle").data(json.nodes).enter().append("circle");

 						var circleAttributes = circles
							.attr("cx",function(d){return d.cx})
							.attr("cy",function(d){return d.cy})
							.attr("r",10)
							.attr("fill","#6495ed");
							
							console.log(circles[1]);
							
						var texts = svg.selectAll("text").data(json.nodes).enter().append("text");
						
						var textAttribute=texts
						.attr("dx",function(d){return d.cx})
						.attr("dy",function(d){return d.cy})
						.text(function(d){return d.name})
						
						var lines = svg.selectAll("line").data(json.links).enter().append("line");
						
						var lineAttribute=lines
						.attr("x1",function(d){return d.x1})
						.attr("y1",function(d){return d.y1})
						.attr("x2",function(d){return d.x2})
						.attr("y2",function(d){return d.y2})
						.attr("stroke","#000")
						.attr("stroke-width","0.1rem");
						

					})
					
					
					/*

					var width = 5000, height = 5000

					var target = d3.select("#" + this.id + "-body");

					// console.log("#" + this.id + "-body");

					var svg = target.append("svg").attr("width", width).attr(
							"height", height);

					var force = d3.layout.force().gravity(.05).distance(100)
							.charge(-100).size([width, height]);

					// get from:
					// https://github.com/mbostock/d3/wiki/Force-Layout
					// example: force-directed images and labels
					d3.json("graph.json", function(json) {
								// console.log(json.nodes);
								force.nodes(json.nodes).links(json.links)
										.start();

								var link = svg.selectAll(".link")
										.data(json.links).enter()
										.append("line").attr("class", "link")
										.style("stroke", "#ccc").style(
												"stroke-width", 1);

								var node = svg.selectAll(".node")
										.data(json.nodes).enter().append("g")
										.attr("class", "node").call(force.drag);

								node.append("image").attr("xlink:href",
										"https://github.com/favicon.ico").attr(
										"x", -8).attr("y", -8)
										.attr("width", 16).attr("height", 16);

								node.append("text").attr("dx", 12).attr("dy",
										".35em").text(function(d) {
											return d.name
										});

								force.on("tick", function() {
											link.attr("x1", function(d) {
														return d.source.x;
													}).attr("y1", function(d) {
														return d.source.y;
													}).attr("x2", function(d) {
														return d.target.x;
													}).attr("y2", function(d) {
														return d.target.y;
													});

											node.attr("transform", function(d) {
														return "translate("
																+ d.x + ","
																+ d.y + ")";
													});
										});
							});

				*/
				
				
					/*
					var width = 5000, height = 5000

					var target = d3.select("#" + this.id + "-body");

					// console.log("#" + this.id + "-body");

					var svg = target.append("svg").attr("width", width).attr(
							"height", height);

					var force = d3.layout.force().gravity(.05).distance(100)
							.charge(-100).size([width, height]);

					// get from:
					// https://github.com/mbostock/d3/wiki/Force-Layout
					// example: force-directed images and labels
					d3.json("graph.json", function(json) {
								// console.log(json.nodes);
								//force.nodes(json.nodes).links(json.links)
								//		.start();

								var link = svg.selectAll(".link")
										.data(json.links).enter()
										.append("line").attr("class", "link")
										.style("stroke", "#ccc").style(
												"stroke-width", 1);

								var node = svg.selectAll(".node")
										.data(json.nodes).enter().append("g")
										.attr("class", "node").attr("cx",function(d){return d.cx}).attr("cy",function(d){return d.cy});

								node.append("image").attr("xlink:href",
										"https://github.com/favicon.ico").attr(
										"x", -8).attr("y", -8)
										.attr("width", 16).attr("height", 16);

								node.append("text").attr("dx", 12).attr("dy",
										".35em").text(function(d) {
											return d.name
										});

								force.on("tick", function() {
											link.attr("x1", function(d) {
														return d.source.x;
													}).attr("y1", function(d) {
														return d.source.y;
													}).attr("x2", function(d) {
														return d.target.x;
													}).attr("y2", function(d) {
														return d.target.y;
													});

											node.attr("transform", function(d) {
														return "translate("
																+ d.x + ","
																+ d.y + ")";
													});
										});
							});

				*/
				
					
					
				
				}

			});

	Ext.create('Ext.container.Viewport', {
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
						menu : [{
									text : '新建项目'
								}, {
									text : '导入数据'
								}, {
									text : '生成数据'
								}, {
									text : '保存'
								}]

					}, {
						xtype : 'button',
						text : '工作区'
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
						text : '帮助'
					}],

			items : [{
						xtype : 'button',
						text : '概览',
						region : 'center'

					}, {
						xtype : 'button',
						text : '数据资料'
					}, {
						xtype : 'button',
						text : '预览'
					}, {
						xtype : 'button',
						text : 'logo'
					}]

		}, {	// 左侧面板
					region : 'west',
					collapsible : true,
					split : true,
					title : '数据操作',

					items : [{
						xtype : 'tabpanel',
						width : 200,
						height : 400,
						// collapsible : true,
						activeTab : 0,
						// First tab active by default
						items : [{
							title : '分割',
							html : '请设置相应参数'
						}, {
							title : '排序',
							html : 'The first tab\'s content. Others may be added dynamically'
						}]
					}, {
						xtype : 'panel',
						width : 200,
						height : 400, // collapsible : true,
						title : '流程'
					}]
					// layout : 'vbox'

					// could use a TreePanel or AccordionLayout for navigational
					// items
				}, {
					region : 'south',
					xtype : 'panel',
					title : '属性',
					collapsible : true,
					html : '请选择顶点',
					split : true,
					height : 100,
					minHeight : 100
				}, {
					region : 'east',
					collapsible : true,
					split : true,
					title : '数据信息',

					items : [{
								xtype : 'panel',
								width : 200,
								height : 400, // collapsible : true,
								title : '上下文'
							}, {
								xtype : 'tabpanel',
								width : 200,
								height : 400,
								// collapsible : true,
								activeTab : 0,
								// First tab active by default
								items : [{
									title : '统计',
									html : '顶点数：6/n边数：8'
								}, {
									title : '滤波',
									html : 'The first tab\'s content. Others may be added dynamically'
								}]
							}]
				}, {

					// reigion:'center',
					// xtype : 'panel',
					// title : 'd3Graph',
					// autoLoad : { url : 'test/MyJsp.jsp', scripts : true} //
					// your HTML file containing d3 code.

					region : 'center',
					xtype : 'tabpanel', // TabPanel itself has no title
					activeTab : 0, // First tab active by default
					items : 
					
					
					Ext.widget('singleview', {
								width : 5000,
								height : 5000
							})//.render(Ext.getBody())
							
							
					// {

				// title : 'layout',
				// id : "layoutPanel",
				// autoLoad : {
				// url : 'test/MyJsp.jsp',
				// scripts : true
				// }

				// }
			}]
	});

})

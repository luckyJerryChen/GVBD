//	Ext.define('EB.view.content.SingleView', {
//    extend : 'Ext.panel.Panel',
//    alias : 'widget.singleview',
//    layout : 'fit',
//    title : 'single view',
//
//    initComponent : function() {
//        this.callParent(arguments);
//    },
//
//    onRender : function() {
//        var me = this;
//
//        me.doc = Ext.getDoc();
//        me.callParent(arguments);
//
//        me.drawMap();
//    },
//
//    drawMap : function() {
//        var width = 960, height = 500
//
//        var target = d3.select("#" + this.id+"-body");
//
//        var svg = target.append("svg").attr("width", width).attr("height",
//                height);
//
//        var force = d3.layout.force().gravity(.05).distance(100).charge(-100)
//                .size([width, height]);
//
//                // get from: https://github.com/mbostock/d3/wiki/Force-Layout
//                // example: force-directed images and labels
//        d3.json("graph.json", function(json) {
//            force.nodes(json.nodes).links(json.links).start();
//
//            var link = svg.selectAll(".link").data(json.links).enter()
//                    .append("line").attr("class", "link");
//
//            var node = svg.selectAll(".node").data(json.nodes).enter()
//                    .append("g").attr("class", "node").call(force.drag);
//
//            node.append("image").attr("xlink:href",
//                    "https://github.com/favicon.ico").attr("x", -8).attr("y",
//                    -8).attr("width", 16).attr("height", 16);
//
//            node.append("text").attr("dx", 12).attr("dy", ".35em").text(
//                    function(d) {
//                        return d.name
//                    });
//
//            force.on("tick", function() {
//                        link.attr("x1", function(d) {
//                                    return d.source.x;
//                                }).attr("y1", function(d) {
//                                    return d.source.y;
//                                }).attr("x2", function(d) {
//                                    return d.target.x;
//                                }).attr("y2", function(d) {
//                                    return d.target.y;
//                                });
//
//                        node.attr("transform", function(d) {
//                                    return "translate(" + d.x + "," + d.y + ")";
//                                });
//                    });
//        });
//    }
//
//});
	
Ext.onReady(function(){
Ext.define('SimplePanel', {  
    extend: 'Ext.panel.Panel',  
    alias: ['widget.simplepanel_007','widget.simplepanel_008'],  
        title: 'Yeah!'  
});  
  
//通过Ext.widget()创建实例  
Ext.widget('simplepanel_007',{  
    width : 100,  
    height : 100  
}).render(Ext.getBody());  
  
//通过xtype创建  
Ext.widget('simplepanel_007', {  
    width : 100,  
    items: [  
        {xtype: 'simplepanel_008', html: 'Foo'},  
        {xtype: 'simplepanel_008', html: 'Bar'}  
    ]  
}).render(Ext.getBody());  
})
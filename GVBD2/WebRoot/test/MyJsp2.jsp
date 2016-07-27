<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="background">
  
    <script src="//d3js.org/d3.v3.min.js" charset="utf-8"></script>  
        <script>	
   /*     
        var width = 960,
    height = 500

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);

var force = d3.layout.force()
    .gravity(.05)
    .distance(100)
    .charge(-100)
    .size([width, height]);

d3.json("graph.json", function(error, json) {
  if (error) throw error;
console.log(json.links);
  force
      .nodes(json.nodes)
      .links(json.links)
      .start();

  var link = svg.selectAll(".link")
      .data(json.links)
    .enter().append("line")
      .attr("class", "link");

  var node = svg.selectAll(".node")
      .data(json.nodes)
    .enter().append("g")
      .attr("class", "node")
      .call(force.drag);

  node.append("image")
      .attr("xlink:href", "https://github.com/favicon.ico")
      .attr("x", -8)
      .attr("y", -8)
      .attr("width", 16)
      .attr("height", 16);

  node.append("text")
      .attr("dx", 12)
      .attr("dy", ".35em")
      .text(function(d) { return d.name });

  force.on("tick", function() {
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
  });
});
        
        
        
        */
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        	   
					

        var width = 960, height = 500

        var target = d3.select("#" + this.id+"-body");

        var svg = target.append("svg").attr("width", width).attr("height",
                height);

        var force = d3.layout.force().gravity(.05).distance(100).charge(-100)
                .size([width, height]);

                // get from: https://github.com/mbostock/d3/wiki/Force-Layout
                // example: force-directed images and labels
        d3.json("graph.json", function(json) {
             console.log(json.nodes);
            force.nodes(json.nodes).links(json.links).start();

            var link = svg.selectAll(".link").data(json.links).enter()
                    .append("line").attr("class", "link");

            var node = svg.selectAll(".node").data(json.nodes).enter()
                    .append("g").attr("class", "node").call(force.drag);

            node.append("image").attr("xlink:href",
                    "https://github.com/favicon.ico").attr("x", -8).attr("y",
                    -8).attr("width", 16).attr("height", 16);

            node.append("text").attr("dx", 12).attr("dy", ".35em").text(
                    function(d) {
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
                                    return "translate(" + d.x + "," + d.y + ")";
                                });
                    });
        });

       </script>  
            
		this is layout
  </body>
</html>

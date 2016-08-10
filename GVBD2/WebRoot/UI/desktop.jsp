<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Graph Visualization Base on  Distributed system</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style>
  	body{
  	  position: absolute;
  	  left:0px;
  	  top:0px;
   }
  .tooltip{
	 font-family: simsun;
	 font-size: 24px;
	 width: 200px;
	 height: auto;
	 position: absolute;
	 left:30px;
	 top:20px;
	 text-align: center;
     border-style: solid; 
	 border-width: 1px;
	 background-color: white;
	 border-radius: 5px;
  }

</style>
<link rel="stylesheet" type="text/css"	href="js/extjs/resources/css/ext-all.css">
<script src="https://d3js.org/d3.v4.min.js"></script>
<script type="text/javascript" charset="UTF-8"	src="js/extjs/bootstrap.js"></script>
<script type="text/javascript" charset="UTF-8"	src="js/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" charset="UTF-8" src="js/UI/desktop.js"></script>
<script type="text/javascript" src="js/UI/jquery-1.8.0.js"></script></head>
<script type="text/javascript" src="js/extjs/examples/ux/data/PagingMemoryProxy.js"></script>
<body>
</body>
</html>

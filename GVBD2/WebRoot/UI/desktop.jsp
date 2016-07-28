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
<link rel="stylesheet" type="text/css"	href="js/extjs/resources/css/ext-all.css">
<script type="text/javascript" charset="utf-8" src="js/extjs/d3.js"></script>
<script type="text/javascript" charset="UTF-8"	src="js/extjs/bootstrap.js"></script>
<script type="text/javascript" charset="UTF-8"	src="js/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" charset="UTF-8" src="js/UI/desktop.js"></script>
<script type="text/javascript" src="js/UI/jquery-1.8.0.js"></script></head>
<body>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<link href="<%=basePath%>shop/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/prettyPhoto.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/price-range.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/animate.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/main.css" rel="stylesheet">
<link href="<%=basePath%>shop/css/responsive.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="<%=basePath%>shop/js/html5shiv.js"></script>
<script src="<%=basePath%>shop/js/respond.min.js"></script>
<![endif]-->   
<link rel="shortcut icon" href="<%=basePath%>shop/images/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=basePath%>shop/images/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=basePath%>shop/images/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=basePath%>shop/images/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="<%=basePath%>shop/images/ico/apple-touch-icon-57-precomposed.png">

<script src="<%=basePath%>shop/js/jquery.js"></script>
<script src="<%=basePath%>shop/js/price-range.js"></script>
<script src="<%=basePath%>shop/js/jquery.scrollUp.min.js"></script>
<script src="<%=basePath%>shop/js/bootstrap.min.js"></script>
<script src="<%=basePath%>shop/js/jquery.prettyPhoto.js"></script>
<script src="<%=basePath%>shop/js/main.js"></script>
<script src="<%=basePath%>shop/js/jquery.form.js"></script>
<script src="<%=basePath%>shop/js/publicSetup.js"></script>

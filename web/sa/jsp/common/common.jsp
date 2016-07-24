<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<link href="<%=basePath%>sa/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>sa/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="<%=basePath%>sa/css/plugins/timeline.css" rel="stylesheet">
<link href="<%=basePath%>sa/css/sb-admin-2.css" rel="stylesheet">
<%-- <link href="<%=basePath%>sa/css/plugins/morris.css" rel="stylesheet"> --%>
<link href="<%=basePath%>sa/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
    
<script src="<%=basePath%>sa/js/jquery-1.11.0.js"></script>
<script src="<%=basePath%>sa/js/bootstrap.min.js"></script>
<script src="<%=basePath%>sa/js/ajaxfileupload.js"></script>
<script src="<%=basePath%>sa/js/bootbox.min.js"></script>
<script src="<%=basePath%>sa/js/jquery.form.min.js"></script>
<script src="<%=basePath%>sa/js/plugins/metisMenu/metisMenu.min.js"></script>
<script src="<%=basePath%>sa/js/wdatePicker/WdatePicker.js"></script>
<%-- <script src="<%=basePath%>sa/js/plugins/morris/raphael.min.js"></script> --%>
<%-- <script src="<%=basePath%>sa/js/plugins/morris/morris.min.js"></script> --%>
<%-- <script src="<%=basePath%>sa/js/plugins/morris/morris-data.js"></script> --%>
<script src="<%=basePath%>sa/js/sb-admin-2.js"></script>
<script src="<%=basePath%>sa/js/sa-common.js"></script>
<script type="text/javascript">
var com_conf = {
	loading_icon : '<span><i class="fa fa-refresh fa-spin fa-2x fa-fw"></i></span>',
	loading_text_icon : '<span><i class="fa fa-refresh fa-spin fa-2x fa-fw"></i>处理中，请稍后...</span>'
};
</script>
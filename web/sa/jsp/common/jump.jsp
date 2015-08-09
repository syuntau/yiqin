<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<script src="<%=basePath%>sa/js/jquery-1.11.0.js"></script>
	<script src="<%=basePath%>sa/js/jquery.form.min.js"></script>
	<script src="<%=basePath%>sa/js/sa-common.js"></script>
<script type="text/javascript">
	//错误提示信息
	var msg = "${requestScope.notLoginError}";
	if (msg != "") {
		alert(msg);
		window.location.href="/sa/toLogin";
		//sa_common.postFormToLogin();
	}
</script>
</head>
</html>
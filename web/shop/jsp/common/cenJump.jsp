<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	//错误提示信息
	var msg = "${requestScope.notLoginError}";
	if (msg != "") {
		alert(msg);
	}
	window.top.location.href = "/index.action?home=top_login";
</script>
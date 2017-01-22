<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<script type="text/javascript">
var footer_template = {
	dispatcher : '/dispatcher',
	form : '<form method="post"></form>',
	nav : '<input type="hidden" name="para_nav" />'
};

var footerTo = function(para_id) {
	var $form = $(footer_template.form);
	var	$nav = $(footer_template.nav);
	$form.attr('action', footer_template.dispatcher).append($nav.val(para_id));
	$('#footer').append($form);
	$form.submit();
};
</script>
	
	<footer id="footer"><!--Footer-->
		<tiles:insertAttribute name="footer.top" />
		<tiles:insertAttribute name="footer.widget" />
		<tiles:insertAttribute name="footer.bottom" />
	</footer><!--/Footer-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var template = {
	dispatcher : '/dispatcher.action',
	form : '<form method="post"></form>',
	nav : '<input type="hidden" name="para_nav" />'
};

var shop_header = function() {
	var shop_header_action = {
		init : function() {
			shop_header.setCurrentNav();
			setNav();
		},
		
		setCurrentNav : function() {
			var currentNav = "<s:property value='#session.se_shop_nav' />";
			if (currentNav == '') {
				currentNav = 'top_home';
			}
			$('#header li a').removeClass('active');
			$('#' + currentNav).addClass('active').removeClass('shop_header');
		},
	};
	
	var setNav = function() {
		$('.shop_header').on('click', function() {
			var nav = $(this).attr('id');
			var $form = $(template.form);
			var	$nav = $(template.nav);
			$form.attr('action', template.dispatcher).append($nav.val(nav));
			$('#header').append($form);
			$form.submit();
		});
	};
	
	return shop_header_action;
}();

$(document).ready(function() {
	shop_header.init();
});
</script>
<header id="header"><!--header-->
	<tiles:insertAttribute name="header.top" />
	<tiles:insertAttribute name="header.middle" />
</header><!--/header-->
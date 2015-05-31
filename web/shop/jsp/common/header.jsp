<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var template = {
	dispatcher : '/dispatcher',
	form : '<form method="post"></form>',
	nav : '<input type="hidden" name="para_nav" />'
};

var shop_header = function() {
	var shop_header_action = {
		init : function() {
			appendRegister();
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
	
	var appendRegister = function(){
		var loginUser = "<s:property value='#session.userInfo.id'/>",
			showUser = "<s:text name='shop.user.regist.show.userid'/>";
		if(loginUser==showUser){
			var $shop_header_ul = $("#shop_header_ul"),
				$li = $("<li></li>"),
				$a = $('<a class="shop_header" id="top_register"><i class="fa fa-lock"></i> <s:text name="shop.header.top.register" /></a>');
			$shop_header_ul.append($li.append($a));
		}
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set var="roles" value="#session.yiqin_sa_user_roles"/>
<script type="text/javascript">
var template = {
	dispatcher : '/sa/dispatcher',
	form : '<form method="post"></form>',
	nav : '<input type="hidden" name="para_nav" />'
};
var sa_header = function() {
	var sa_header_action = {
		init : function() {
			sa_header.setCurrentNav();
			setNav();
		},
		
		setCurrentNav : function() {
			var currentNav = "<s:property value='#request.rq_sa_nav' />";
			if (currentNav == '') {
				currentNav = 'order';
			}
			$('#side-menu .side-menu-a').removeClass('active');
			$('#side-menu [val=' + currentNav + ']').addClass('active').parents().each(function() {
				if ($(this).hasClass('side-menu-li')) {
					$(this).addClass('active');
					$(this).find('ul').addClass('in');
				}
			});
		},
	};
	
	var setNav = function() {
		$('#side-menu .side-menu-a').on('click', function() {
			var nav = $(this).attr('val');
			var $form = $(template.form);
			var	$nav = $(template.nav);
			$form.attr('action', template.dispatcher).append($nav.val(nav));
			$('#side-menu').append($form);
			$form.submit();
		});
	};
	
	return sa_header_action;
}();

$(document).ready(function() {
	sa_header.init();
});
</script>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand" href="#" style="cursor: default;"><s:text name="sa.index.lbl.yiqin" /></a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <s:property value="#session.yiqin_sa_user.name"/> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="/sa/logout" val="logout"><i class="fa fa-sign-out fa-fw"></i><s:text name="sa.header.logout" /></a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                	<s:if test="%{#roles.indexOf('11000')>-1}">
                    <li>
                        <a href="#" class="side-menu-a" val="order-manager"><i class="fa fa-table fa-fw"></i> <s:text name="sa.header.order" /></a>
                    </li>
                    </s:if>
                	<s:if test="%{#roles.indexOf('12000')>-1}">
                    <li class="side-menu-li">
                        <a href="#"><i class="fa fa-user fa-fw"></i> <s:text name="sa.header.user" /><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                			<s:if test="%{#roles.indexOf('12100')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="user-sa"><s:text name="sa.header.user.sa" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('12200')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="user-admin"><s:text name="sa.header.user.admin" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('12400')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="user-sa"><s:text name="sa.header.user.admin" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('12300')>-1}">
                            <li>
                                <a href="#" val="customer-manage" class="side-menu-a"><s:text name="sa.header.customer.manage" /></a>
                            </li>
                            </s:if>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    </s:if>
                	<s:if test="%{#roles.indexOf('13000')>-1}">
                    <li class="side-menu-li">
                        <a href="#"><i class="fa fa-qrcode fa-fw"></i> <s:text name="sa.header.product" /><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                			<s:if test="%{#roles.indexOf('13300')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-brand"><s:text name="sa.header.product.brand" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('13400')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-category"><s:text name="sa.header.product.category" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('13100')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-attribute"><s:text name="sa.header.product.attribute" /></a>
                            </li>
                            </s:if>
                			<s:if test="%{#roles.indexOf('13200')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-item"><s:text name="sa.header.product.item" /></a>
                            </li>
                            </s:if>
                            <s:if test="%{#roles.indexOf('13500')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-image"><s:text name="sa.header.product.image" /></a>
                            </li>
                            </s:if>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    </s:if>
                	<s:if test="%{#roles.indexOf('14000')>-1}">
                    <li>
                        <a href="#" val="quick-shopping" class="side-menu-a"><i class="fa fa-heart fa-fw"></i> <s:text name="sa.header.quick.shopping" /></a>
                    </li>
                    </s:if>
                	<s:if test="%{#roles.indexOf('15000')>-1}">
                    <li class="side-menu-li">
                        <a href="#"><i class="fa fa-cog fa-fw"></i> <s:text name="sa.header.sys.set" /><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
		                	<s:if test="%{#roles.indexOf('15100')>-1}">
		                    <li>
		                        <a href="#" class="side-menu-a" val="reg-code"><s:text name="sa.header.sys.register.code" /></a>
		                    </li>
		                    </s:if>
                			<s:if test="%{#roles.indexOf('15200')>-1}">
                            <li>
                                <a href="#" class="side-menu-a" val="product-indexList"><s:text name="sa.header.sys.index.pro.list" /></a>
                            </li>
                            </s:if>
                        </ul>
                    </li>
                    </s:if>
<!--                     <li class="side-menu-li"> -->
<%--                         <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> <s:text name="sa.header.inventory" /><span class="fa arrow"></span></a> --%>
<!--                         <ul class="nav nav-second-level"> -->
<!--                             <li> -->
<%--                                 <a href="#" class="side-menu-a" val="inventory-info"><s:text name="sa.header.inventory.info" /></a> --%>
<!--                             </li> -->
<!--                             <li> -->
<%--                                 <a href="#" class="side-menu-a" val="inventory-order-by-customer"><s:text name="sa.header.inventory.order.by.customer" /></a> --%>
<!--                             </li> -->
<!--                             <li> -->
<%--                                 <a href="#" class="side-menu-a" val="inventory-all-orders"><s:text name="sa.header.inventory.all.orders" /></a> --%>
<!--                             </li> -->
<!--                         </ul> -->
<!--                         /.nav-second-level -->
<!--                     </li> -->
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

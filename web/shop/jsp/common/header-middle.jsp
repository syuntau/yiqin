<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>

	<div class="header-middle"><!--header-middle-->
		<div class="container">
			<div class="row">
				<div class="col-sm-8">
					<div class="logo pull-left">
						<a href="javaScript:window.location.href='index.action';"><img src="/shop/images/home/logo.png"/></a>
					</div>
					<div style="float: right">
						<div class="input-group">&nbsp;
<!-- 							<input type="text" class="form-control"> -->
<%-- 							<span class="input-group-btn"> --%>
<%-- 								<button class="btn btn-default" type="button"><s:text name="shop.header.nav.search" /></button> --%>
<%-- 							</span> --%>
						</div>
					</div>
<!-- 					<div class="search_box pull-right"> -->
<!-- 						<input type="text" placeholder="Search"/> -->
<!-- 					</div> -->
				</div>
				<div class="col-sm-4">
					<div class="mainmenu pull-right">
						<ul class="nav navbar-nav collapse navbar-collapse" id="top_header_mainmenu">
							<li><a class="shop_header active" id="top_home"><s:text name="shop.header.nav.home" /></a></li>
<%-- 							<li class="dropdown"><a id="top_product"><s:text name="shop.header.nav.product" /><i class="fa fa-angle-down"></i></a> --%>
<!--                                 <ul role="menu" class="sub-menu"> -->
<%-- 									<s:set var="products"><s:text name='shop.header.nav.products' /></s:set> --%>
<%-- 									<s:generator separator="," val="#products" var="productList"></s:generator> --%>
<%-- 									<s:iterator value="#productList" var="product"> --%>
<%-- 										<li><a  class="shop_header" id='top_p_<s:property value="%{#product}"/>'><s:text name="%{'shop.header.nav.product.' + #product}" /></a></li> --%>
<%-- 									</s:iterator> --%>
<!--                                 </ul> -->
<!--                             </li> -->
<%-- 							<li><a  class="shop_header" id="top_sales_promotion"><s:text name="shop.header.nav.sales.promotion" /></a></li> --%>
<%-- 							<li><a  class="shop_header" id="top_new_goods"><s:text name="shop.header.nav.new.goods" /></a></li><s:text name="" ></s:text> --%>
<%-- 							<li><a  class="shop_header"  id="top_office_product"><s:text name="shop.header.nav.product.office.product" /></a></li> --%>
<%-- 							<li><a  class="shop_header"  id="top_gift_card"><s:text name="shop.header.nav.product.gift.card" /></a></li> --%>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div><!--/header-middle-->

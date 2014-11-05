<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<div class="header_top"><!--header_top-->
		<div class="container">
			<div class="row">
				<div class="col-sm-6">
					<div class="contactinfo">
						<ul class="nav nav-pills">
							<li><a href=""><i class="fa fa-phone"></i> <s:text name="yiqin.phone" /></a></li>
							<li><a href=""><i class="fa fa-envelope"></i> <s:text name="yiqin.mail" /></a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="shop-menu pull-right">
						<ul class="nav navbar-nav">
							<li><a href=""><i class="fa fa-user"></i> <s:text name="shop.header.top.accout" /></a></li>
							<li><a href=""><i class="fa fa-star"></i> <s:text name="shop.header.top.wishlist" /></a></li>
							<li><a href="cart.html"><i class="fa fa-shopping-cart"></i> <s:text name="shop.header.top.cart" /></a></li>
							<li><a href="/login.action" class="active"><i class="fa fa-lock"></i> <s:text name="shop.header.top.login" /></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div><!--/header_top-->

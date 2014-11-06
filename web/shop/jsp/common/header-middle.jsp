<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>

	<div class="header-middle"><!--header-middle-->
		<div class="container">
			<div class="row">
				<div class="col-sm-4">
					<div class="logo pull-left">
						<a href="index.html"><img src="shop/images/home/logo.png" alt="" /></a>
					</div>
					<div class="form-group input-group">
						<input type="text" class="form-control"> <span
							class="input-group-btn">
							<button class="btn btn-default" type="button">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
<!-- 					<div class="search_box pull-right"> -->
<!-- 						<input type="text" placeholder="Search"/> -->
<!-- 					</div> -->
				</div>
				<div class="col-sm-8">
					<div class="mainmenu pull-right">
						<ul class="nav navbar-nav collapse navbar-collapse">
							<li><a href=""><s:text name="shop.header.nav.home" /></a></li>
							<li class="dropdown"><a><s:text name="shop.header.nav.product" /><i class="fa fa-angle-down"></i></a>
                                <ul role="menu" class="sub-menu">
                                	<c:set var="products"><fmt:message key="shop.header.nav.products" /></c:set>
									<c:forTokens items="consumables,paper,machines,stationery" delims="," var="product">
										<li><a href=""><fmt:bundle basename="app"><fmt:message key="shop.header.nav.product.${product}" /></fmt:bundle></a></li>
									</c:forTokens>
                                </ul>
                            </li>
							<li><a href=""><s:text name="shop.header.nav.sales.promotion" /></a></li>
							<li><a href=""><s:text name="shop.header.nav.new.goods" /></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div><!--/header-middle-->

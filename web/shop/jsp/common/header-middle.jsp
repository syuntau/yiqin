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
					<div class="col-lg-8" style="float: right">
						<div class="input-group">
							<input type="text" class="form-control"> <span
								class="input-group-btn">
								<button class="btn btn-default" type="button">
<!-- 									<i class="fa fa-search"></i> -->
									搜索
								</button>
							</span>
						</div>
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
									<s:set var="products"><s:text name='shop.header.nav.products' /></s:set>
									<s:generator separator="," val="#products" var="productList"></s:generator>
									<s:iterator value="#productList" var="product">
										<li><a href=""><s:text name="%{'shop.header.nav.product.' + #product}" /></a></li>
									</s:iterator>
                                </ul>
                            </li>
							<li><a href=""><s:text name="shop.header.nav.sales.promotion" /></a></li>
							<li><a href=""><s:text name="shop.header.nav.new.goods" /></a></li><s:text name="" ></s:text>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div><!--/header-middle-->

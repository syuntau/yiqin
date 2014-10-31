<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>  

	<div class="header-middle"><!--header-middle-->
		<div class="container">
			<div class="row">
				<div class="col-sm-4">
					<div class="logo pull-left">
						<a href="index.html"><img src="images/home/logo.png" alt="" /></a>
					</div>
					<div class="search_box pull-right">
						<input type="text" placeholder="Search"/>
					</div>
				</div>
				<div class="col-sm-8">
					<div class="mainmenu pull-right">
						<ul class="nav navbar-nav collapse navbar-collapse">
							<li><a href="index.html">Home</a></li>
							<li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                   <ul role="menu" class="sub-menu">
                                       <li><a href="shop.html">Products</a></li>
									<li><a href="product-details.html">Product Details</a></li> 
									<li><a href="checkout.html">Checkout</a></li> 
									<li><a href="cart.html">Cart</a></li> 
									<li><a href="login.html" class="active">Login</a></li> 
                                   </ul>
                               </li> 
							<li class="dropdown"><a href="#">Blog<i class="fa fa-angle-down"></i></a>
                                   <ul role="menu" class="sub-menu">
                                       <li><a href="blog.html">Blog List</a></li>
									<li><a href="blog-single.html">Blog Single</a></li>
                                   </ul>
                               </li> 
							<li><a href="404.html">404</a></li>
							<li><a href="contact-us.html">Contact</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div><!--/header-middle-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
		
		<div class="recommended_items"><!--recommended_items-->
		<h2 class="title text-center"><s:text name="shop.index.right.recommended.items" /></h2>
		
		<div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
			<div class="carousel-inner">
				<div class="item active">	
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend1.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend2.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend3.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
				</div>
				<div class="item">	
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend1.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend2.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="/shop/images/home/recommend3.jpg" alt="" />
									<h2>￥56.99</h2>
									<p>黑色经典版</p>
									<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
			 <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
				<i class="fa fa-angle-left"></i>
			  </a>
			  <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
				<i class="fa fa-angle-right"></i>
			  </a>			
		</div>
	</div><!--/recommended_items-->

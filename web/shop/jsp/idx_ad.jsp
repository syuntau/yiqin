<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

	<section id="slider"><!--slider-->
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<div id="slider-carousel" class="carousel slide" data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
							<li data-target="#slider-carousel" data-slide-to="1"></li>
							<li data-target="#slider-carousel" data-slide-to="2"></li>
						</ol>
						
						<div class="carousel-inner">
							<div class="item active">
								<div class="col-sm-6">
									<h1><span>易</span>勤商城</h1>
									<h2>本周特卖</h2>
									<p>全场满788 送100 </p>
									<button type="button" class="btn btn-default get">加入购物车</button>
								</div>
								<div class="col-sm-6">
									<img src="/shop/images/home/ad-1.png" class="girl img-responsive" alt="" />
<!-- 									<img src="/shop/images/home/pricing.png"  class="pricing" alt="" /> -->
								</div>
							</div>
							<div class="item">
								<div class="col-sm-6">
									<h1><span>易</span>勤商城</h1>
									<h2>本周特卖</h2>
									<p>全场满788 送100 </p>
									<button type="button" class="btn btn-default get">加入购物车</button>
								</div>
								<div class="col-sm-6">
									<img src="/shop/images/home/ad-2.png" class="girl img-responsive" alt="" />
<!-- 									<img src="/shop/images/home/pricing.png"  class="pricing" alt="" /> -->
								</div>
							</div>
							
							<div class="item">
								<div class="col-sm-6">
									<h1><span>易</span>勤商城</h1>
									<h2>本周特卖</h2>
									<p>全场满788 送100 </p>
									<button type="button" class="btn btn-default get">加入购物车</button>
								</div>
								<div class="col-sm-6">
									<img src="/shop/images/home/ad-3.png" class="girl img-responsive" alt="" />
<!-- 									<img src="/shop/images/home/pricing.png" class="pricing" alt="" /> -->
								</div>
							</div>
							
						</div>
						
						<a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
							<i class="fa fa-angle-left"></i>
						</a>
						<a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
							<i class="fa fa-angle-right"></i>
						</a>
					</div>
					
				</div>
			</div>
		</div>
	</section><!--/slider-->

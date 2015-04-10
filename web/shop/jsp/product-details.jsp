<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var yiqin_product_detail = function(){
	var action = {
		initProductDetail : function(){
			$("#detail_add_cart").click(function(){
				var productId = "<s:property value='#request.product_detail[0].productId'/>",
					customNum = $(this).prev().val(),
					selName = $("#select_product_info").find("a[class*=select-a]").attr("title"),
					REX_NUM = /^(0|[1-9][0-9]*)$/;
					if(customNum==null || customNum=="" || !REX_NUM.test(customNum) || customNum <=0){
						$(this).prev().val(1);
						return;
					}
					if($.type(selName)=="null" || $.type(selName)=="" || $.type(selName)=="undefined"){
						alert("请选择需要的颜色");
						return;
					}
				yiqin_public_js.addProductToCart(productId, customNum);
			});
			
			$("#detail_add_cart").prev().bind("blur focus",function(){
				var REX_NUM = /^(0|[1-9][0-9]*)$/,
					customNum = $(this).val();
				if(customNum==null || customNum=="" || !REX_NUM.test(customNum) || customNum <=0){
					$(this).val(this.defaultValue);
				}
			});
			
			$("#select_product_info a").each(function(){
				$(this).click(function(){
					$("#select_product_info a").each(function(){
						$(this).removeClass("select-a");
						$(this).prev().removeClass("select-b");
						$(this).parent().removeClass("selected-div");
					});
					$(this).addClass("select-a");
					$(this).prev().addClass("select-b");
					$(this).parent().addClass("selected-div");
				});
			});
		},
		
	};
	
	
	return action;
}();

$(document).ready(function(){
	yiqin_product_detail.initProductDetail();
});
</script>

<div class="col-sm-9 padding-right">
	<div class="product-details">
		<!--product-details-->
		<div class="col-sm-5">
			<div class="view-product">
				<img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="329" height="380"/>
				<input type="hidden" id="<s:property value='#request.product_detail[0].productId'/>" value="<s:property value='#request.product_detail[0].productId'/>">
			</div>
			<div id="similar-product" class="carousel slide" data-ride="carousel">
				<!-- Wrapper for slides -->
				<div class="carousel-inner">
					<div class="item active">
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
					</div>
					<div class="item">
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
					</div>
					<div class="item">
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#request.product_detail[0].imgUrl'/>" width="84" height="84"></a>
					</div>
				</div>
				<!-- Controls -->
				<a class="left item-control" href="#similar-product" data-slide="prev"> <i class="fa fa-angle-left"></i></a>
				<a class="right item-control" href="#similar-product" data-slide="next"> <i class="fa fa-angle-right"></i></a>
			</div>
		</div>
		<div class="col-sm-7">
			<div class="product-information">
				<!--/product-information-->
				<h2><s:property value='#request.product_detail[0].productName'/></h2>
				<p>
					<s:text name="shop.product.details.productId" />
					<s:property value='#request.product_detail[0].productId'/>
				</p>
				<span>
					<span><s:property value='#request.product_detail[0].price'/></span>
					<label><s:text name="cart.item.quantity" />:</label>
					<input type="text" value="1" />
					<button type="button" class="btn btn-fefault cart" id="detail_add_cart">
						<i class="fa fa-shopping-cart"></i>
						<s:text name="shop.add.to.cart" />
					</button>
				</span>
				<div id="select_product_info">
					<p>
						<b><s:text name="shop.product.details.color"/></b>
					</p>
					<div class="none-sel-div" style="margin-top:2px;margin-right:8px;margin-bottom:2px;">
						<b></b>
						<a href="#none" title="红色" class="none-detail-a">
							<img src="<s:property value='#request.product_detail[0].imgUrl'/>" class="share img-responsive" alt="红色" width="25" height="25"/>
							<i>红色</i>
						</a>
					</div>
					<div class="none-sel-div" style="margin-top:2px;margin-right:8px;margin-bottom:2px;">
						<b></b>
						<a href="#none" title="黑色" class="none-detail-a">
							<img src="<s:property value='#request.product_detail[0].imgUrl'/>" class="share img-responsive" alt="黑色" width="25" height="25"/>
							<i>黑色</i>
						</a>
					</div>
					<div class="none-sel-div" style="margin-top:2px;margin-right:8px;margin-bottom:2px;">
						<b></b>
						<a href="#none" title="蓝色" class="none-detail-a">
							<img src="<s:property value='#request.product_detail[0].imgUrl'/>" class="share img-responsive" alt="蓝色" width="25" height="25"/>
							<i>蓝色</i>
						</a>
					</div>
				</div>
			</div>
			<!--/product-information-->
		</div>
	</div>
	<!--/product-details-->

	<div class="category-tab shop-details-tab">
		<!--category-tab-->
		<div class="col-sm-12">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#details" data-toggle="tab"><s:text name="shop.product.details.details"></s:text></a>
				</li>
				<li>
					<a href="#speparam" data-toggle="tab"><s:text name="shop.product.details.spe.parameters"></s:text></a>
				</li>
				<li>
					<a href="#reviews" data-toggle="tab"><s:text name="shop.product.details.reviews"></s:text></a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane fade" id="details">
				<div class="col-sm-3">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
								<h2>$56</h2>
								<p>Easy Polo Black Edition</p>
								<button type="button" class="btn btn-default add-to-cart">
									<i class="fa fa-shopping-cart"></i>
									<s:text name="shop.add.to.cart" />
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="speparam">
				<div class="col-sm-3">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
								<h2>$56</h2>
								<p>Easy Polo Black Edition</p>
								<button type="button" class="btn btn-default add-to-cart">
									<i class="fa fa-shopping-cart"></i>Add to cart
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="tab-pane fade active in" id="reviews">
				<div class="col-sm-12">
					<ul>
						<li><a href=""><i class="fa fa-user"></i>EUGEN</a></li>
						<li><a href=""><i class="fa fa-clock-o"></i>12:41 PM</a></li>
						<li><a href=""><i class="fa fa-calendar-o"></i>31 DEC
								2014</a></li>
					</ul>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
						sed do eiusmod tempor incididunt ut labore et dolore magna
						aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco
						laboris nisi ut aliquip ex ea commodo consequat.Duis aute irure
						dolor in reprehenderit in voluptate velit esse cillum dolore eu
						fugiat nulla pariatur.</p>
					<p>
						<b>Write Your Review</b>
					</p>

					<form action="#">
						<span> <input type="text" placeholder="Your Name" /> <input
							type="email" placeholder="Email Address" />
						</span>
						<textarea name=""></textarea>
						<b>Rating: </b> <img src="<s:property value='#request.product_detail[0].imgUrl'/>"
							alt="" />
						<button type="button" class="btn btn-default pull-right">
							Submit</button>
					</form>
				</div>
			</div>

		</div>
	</div>
	<!--/category-tab-->

	<div class="recommended_items">
		<!--recommended_items-->
		<h2 class="title text-center">recommended items</h2>

		<div id="recommended-item-carousel" class="carousel slide"
			data-ride="carousel">
			<div class="carousel-inner">
				<div class="item active">
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
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
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="product-image-wrapper">
							<div class="single-products">
								<div class="productinfo text-center">
									<img src="<s:property value='#request.product_detail[0].imgUrl'/>" alt="" />
									<h2>$56</h2>
									<p>Easy Polo Black Edition</p>
									<button type="button" class="btn btn-default add-to-cart">
										<i class="fa fa-shopping-cart"></i>Add to cart
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<a class="left recommended-item-control"
				href="#recommended-item-carousel" data-slide="prev"> <i
				class="fa fa-angle-left"></i>
			</a> <a class="right recommended-item-control"
				href="#recommended-item-carousel" data-slide="next"> <i
				class="fa fa-angle-right"></i>
			</a>
		</div>
	</div>
	<!--/recommended_items-->
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:iterator value="#request.product_detail" var="product">
	<s:set name="productInfo" value="#product.value"></s:set>
	<s:set name="productId" value="#product.key"></s:set>
</s:iterator>

<script type="text/javascript">
var yiqin_product_detail = function(){
	var action = {
		initProductDetail : function(){
			$("#detail_add_cart").click(function(){
				var productId = "<s:property value='#productId'/>",
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
			$("#select_product_info a").click();
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
				<img src="<s:property value='#productInfo.imgurl'/>" width="329" height="380"/>
				<input type="hidden" id="<s:property value='#productId'/>" value="<s:property value='#productId'/>">
			</div>
			<div id="similar-product" class="carousel slide" data-ride="carousel">
				<!-- Wrapper for slides -->
				<div class="carousel-inner">
					<div class="item active">
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
					</div>
					<div class="item">
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
					</div>
					<div class="item">
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
						<a href=""><img src="<s:property value='#productInfo.imgurl'/>" width="84" height="84"></a>
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
				<h2><s:property value='#productInfo.name'/></h2>
				<p>
					<s:text name="shop.product.details.productId" />
					<s:property value='#productId'/>
				</p>
				<span>
					<span><s:property value='#productInfo.price'/></span>
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
						<a href="#none" title="<s:property value='#productInfo.color'/>" class="none-detail-a">
							<img src="<s:property value='#productInfo.imgurl'/>" class="share img-responsive" alt="<s:property value='#productInfo.color'/>" width="25" height="25"/>
							<i><s:property value='#productInfo.color'/></i>
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
			<ul class="category-tab-ul nav nav-tabs">
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
			<div class="tab-pane fade active in" id="details">
				<div class="col-sm-12">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo">
								<ul class="p-parameter-list">
									<li>
										<label>商品名称：</label>
										<span><s:property value='#productInfo.name'/></span>
									</li>
									<li>	
										<label>商品编号：</label>
										<span><s:property value='#productId'/></span>
									</li>
									<li>		
										<label>品牌：</label>
										<span><s:property value='#productInfo.brand'/></span>
									</li>
									<li>
										<label>颜色：</label>
										<span><s:property value='#productInfo.color'/></span>
									</li>
									<li>
										<label>产地：</label>
										<span><s:property value='#productInfo.producer'/></span>
									</li>
									<li>	
										<label>类别：</label>
										<span><s:property value='#productInfo.category'/></span>
									</li>
								</ul>
								<img src="<s:property value='#productInfo.imgurl'/>" alt="" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="speparam">
				<div class="col-sm-12">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								还没有编辑规格参数
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="reviews">
				<div class="col-sm-12">
					<div class="productinfo text-center">
						暂无评论
					</div>
<!-- 					<ul> -->
<!-- 						<li><a href=""><i class="fa fa-user"></i>EUGEN</a></li> -->
<!-- 						<li><a href=""><i class="fa fa-clock-o"></i>12:41 PM</a></li> -->
<!-- 						<li><a href=""><i class="fa fa-calendar-o"></i>31 DEC 2014</a></li> -->
<!-- 					</ul> -->
<!-- 					<p>评论内容</p> -->
<!-- 					<p> -->
<!-- 						<b>Write Your Review/写下你的评论</b> -->
<!-- 					</p> -->
<!-- 					<form action="#"> -->
<%-- 						<b>Rating: </b> <img src="<s:property value='#productInfo.imgurl'/>" alt="" /> --%>
<!-- 						<button type="button" class="btn btn-default pull-right"> -->
<!-- 							Submit</button> -->
<!-- 					</form> -->
				</div>
			</div>
		</div>
	</div>
	<!--/category-tab-->
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var toIndexPage = function(pageIndex){
	window.location.href = "findBestProduct?pageIndex="+pageIndex;
};
</script>

<div class="col-sm-9 padding-right">
	<div class="features_items" id="fenlei_product_items">
		<!--features_items-->
		<h2 class="title text-center">
			<s:text name="shop.index.right.features.items" />
		</h2>
		<s:if test="page.results==null">
			还没有生成您经常购买的商品哦！
		</s:if>
		<s:else>
			<s:iterator value="page.results" var="product">
				<div class="col-sm-4">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								<img src="<s:property value='#product.imgUrl'/>" width="255px"
									name="good_img" style="cursor: pointer;"
									onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>,'toProductDetails')" />
								<h2>
									￥
									<s:property value="#product.price" />
								</h2>
								<p>
									<s:property value="#product.productName" />
								</p>
								<a id="<s:property value='#product.productId'/>"
									href="javaScript:;" class="btn btn-default add-to-cart"
									onclick="yiqin_public_js.addProductToCart(<s:property value='#product.productId'/>, 1)"><i
									class="fa fa-shopping-cart"></i> <s:text name="shop.add.to.cart" /></a>
							</div>
						</div>
					</div>
				</div>
			</s:iterator>
		</s:else>
	</div>
	<!--features_items-->
	<s:if test="page.results!=null">
		<div style="text-align: center; width: 99%">
			<jsp:include page="/shop/jsp/common/page.jsp"></jsp:include>
		</div>
	</s:if>
</div>

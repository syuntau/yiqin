<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var toIndexPage = function(pageIndex){
	var paramVal = "<s:property value='paramVal'/>";
	window.location.href = "findBestProduct?pageIndex="+pageIndex+"&paramVal="+paramVal;
};


var common_product_nav = function() {
	var action = {
		showNav : function() {
			var $h4Title = $('#fenlei_product_items h2.title span');
			var nav = "";
			var navs = "<s:property value='#session.se_product_nav' />";
			if (!navs || navs == null || navs == '') {
				$h4Title.html('');
				return ;
			}
			var navArr = navs.split(":");
			if (navArr.length == 2) {
				nav += navArr[1];
				var navArr1 = navArr[0].split(",");
				if (navArr1.length == 2) {
					var productDetailFlag = '<s:property value="#request.req_shop_pro_detail"/>';
						nav = "( 当前 : " + navArr1[0] + " > " + navArr1[1] + " > <span style='color:#FE980F;'>" + nav + "</span> )";
						$h4Title.html(nav);
				} else {
					nav = "( 当前 : " + navArr1[0] + " > <span style='color:#FE980F;'>" + nav + "</span> )";
					$h4Title.html(nav);
				}
			} else {
				nav = "( 当前 : <span style='color:#FE980F'>" + navArr[0] + "</span> )";
				$h4Title.html(nav);
			}
		},
	};

	return action;
}();

$(document).ready(function() {
	common_product_nav.showNav();
});
</script>

<div class="padding-right" style="margin-top: -30px;">
	<div class="features_items" id="fenlei_product_items">
		<!--features_items-->
		<h2 class="title">
<%-- 			<s:text name="shop.index.right.features.items" /> --%>
        常用商品列表<span style="font-size:14px;color:#696763;font-weight:900;margin-left:15px;"></span>
		</h2>
		<s:if test="page.results==null">
		<h5 class="text-center" style="line-height: 200px;">
          还没有生成您经常购买的商品哦！
		</h5>
		</s:if>
		<s:else>
			<s:iterator value="page.results" var="product">
				<div class="col-sm-3">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								<img src="<s:property value='#product.imgUrl'/>" width="255px"
									name="good_img" style="cursor: pointer;"
									onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>,'toProductDetails')" />
								<h2>
									<s:if test="#session.userInfo==null">
										<span style="font-size:14px;"><s:text name="shop.product.list.zhekou.info"/></span>
									</s:if>
									<s:else>
										￥
										<s:property value="#product.zhekouPrice" />
									</s:else>
								</h2>
								<del>
									<s:text name="shop.product.label.yuan.price"/>
									<s:property value="#product.price" />
								</del>
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

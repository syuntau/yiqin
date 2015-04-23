<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var yiqin_shoplist_action = function(){
	var action = {
		findFilterAttr : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findFilterAttr",
	             dataType: "json",
	             success: function(data){
	            	 if(data=='1'){
	            		 alert("暂无检索过滤信息！");
	            	 }else if(data=='2'){
	            		 alert("加载失败，稍后再试！");
	            	 }else{
	            		 
	            	 }
              },
              beforeSend: function(){},
              complete: function(){},
              error: function(){}
	         });
		},
		
	};
	
	return action;
}();

var toIndexPage = function(pageIndex){
	var paramVal = "<s:property value='paramVal'/>",
		brand = "<s:property value='brand'/>",
		color = "<s:property value='color'/>",
		price = "<s:property value='price'/>";
	window.location.href = "productFilter?paramVal="+paramVal+"&brand="+encodeURIComponent(encodeURIComponent(brand))+"&color="+encodeURIComponent(encodeURIComponent(color))+"&price="+"&pageIndex="+pageIndex;
};

$(document).ready(function(){
	//yiqin_shoplist_action
});
</script>

<div class="col-sm-9 padding-right">
	<div class="s-title">
		<h4>
			<b>多功能一体机</b><em>&nbsp;商品筛选</em>
		</h4>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-6" style="width: 100%">
				<div class="chose_area">
					<ul class="user_option" style="padding-left:0px;">
						<li style="margin-bottom: 10px;border-bottom: 1px solid #DDD;">
							<label>品牌：</label><span style="margin-left:30px;"><a>惠普</a><a>佳能</a></span>
						</li>
						<li style="margin-bottom: 10px;border-bottom: 1px solid #DDD;">
							<label>价格：</label><span style="margin-left:30px;"><a>10-100</a><a>101-200</a></span>
						</li>
						<li style="margin-bottom: 10px;border-bottom: 1px solid #DDD;">
							<label>颜色：</label><span style="margin-left:30px;"><a>红色</a><a>蓝色</a></span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="features_items" id="fenlei_product_items">
		<!--features_items-->
		<h2 class="title text-center">
			<s:text name="shop.index.right.features.items" />
		</h2>
		<s:if test="page.results==null">
			没有找到任何商品
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
									class="fa fa-shopping-cart"></i> <s:text
										name="shop.add.to.cart" /></a>
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

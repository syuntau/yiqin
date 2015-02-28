<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
	var index_feature_temp = {
		feature_col_div : '<div class="col-sm-4"></div>',
		feature_img_div : '<div class="product-image-wrapper"></div>',
		feature_single_div : '<div class="single-products"></div>',
		feature_info_div : '<div class="productinfo text-center"></div>',
		feature_over_div : '<div class="product-overlay"></div>',
		feature_cont_div : '<div class="overlay-content"></div>',
		feature_h2_tile : '<h2 class="title text-center"><s:text name="shop.index.right.features.items" /></h2>',
		feature_img : '<img alt=""/>',
		feature_h2 : '<h2></h2>',
		feature_p : '<p></p>',
		feature_a : '<a href="javaScript:void(0)" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>',
	};
	
	var yiqin_indexfeatures_action = function(){
		var action = {
			searchProductsByCategory : function(cid){
				var $tese_product_items = $("#tese_product_items");
				$tese_product_items.empty();
				$tese_product_items.append($(index_feature_temp.feature_h2_tile));
				$.ajax({
		            type: "POST",
		            async: true,
		            url: "findProductsByCategory.action",
		            dataType: "json",
		            success: function(data){
		           		if(data=='1'){
		           			features_items.append("没有找到任何商品");
		           		}else if(data=='2'){
		           			features_items.append("加载失败，请稍后再试");
		           		}else{
		           			appendProductTo(data);
		           		}
		           },
		           beforeSend: function(){},
		           complete: function(){},
		           error: function(){}
		        });
			},
			
		};
		
		var appendProductTo = function(data){
			var $tese_product_items = $("#tese_product_items");
			$.each(data, function(i, val){
				var $feature_col_div = $(index_feature_temp.feature_col_div),
					$feature_img_div = $(index_feature_temp.feature_img_div),
					$feature_single_div = $(index_feature_temp.feature_single_div),
					$feature_info_div = $(index_feature_temp.feature_info_div),
					$feature_over_div = $(index_feature_temp.feature_over_div),
					$feature_cont_div = $(index_feature_temp.feature_cont_div),
					$feature_img = $(index_feature_temp.feature_img),
					$feature_h2 = $(index_feature_temp.feature_h2),
					$feature_p = $(index_feature_temp.feature_p),
					$feature_a = $(index_feature_temp.feature_a);
				
				$feature_col_div.append($feature_img_div.append($feature_single_div));
				$feature_single_div.append($feature_info_div).append($feature_over_div);
				
				$feature_info_div.append($feature_img.attr('src',val.imgUrl));
				$feature_info_div.append($feature_h2.append('￥'+val.price));
				$feature_info_div.append($feature_p.append(val.productName));
				$feature_info_div.append($feature_a.attr('id',val.productId));
				
				$feature_over_div.append($feature_cont_div);
				$feature_cont_div.append($feature_h2).append($feature_p).append($feature_a);
				
				$tese_product_items.append($feature_col_div);
			});
		};
		
		return action;
	}();
	
	$(document).read(function(){
		//yiqin_indexfeatures_action.searchProductsByCategory(cid);
	});
</script>

<div class="features_items" id="tese_product_items"><!--features_items-->
	<h2 class="title text-center"><s:text name="shop.index.right.features.items" /></h2>
	<div class="col-sm-4">
		<div class="product-image-wrapper">
			<div class="single-products">
					<div class="productinfo text-center">
						<img src="/shop/images/home/product1.jpg" alt="" />
						<h2>￥56.99</h2>
						<p>黑色经典版</p>
						<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
					</div>
					<div class="product-overlay">
						<div class="overlay-content">
							<h2>￥56.99</h2>
							<p>黑色经典版</p>
							<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>
						</div>
					</div>
			</div>
		</div>
	</div>
</div><!--features_items-->

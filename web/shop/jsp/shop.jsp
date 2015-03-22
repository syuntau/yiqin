<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var shoplist_feature_temp = {
		feature_col_div : '<div class="col-sm-4"></div>',
		feature_img_div : '<div class="product-image-wrapper"></div>',
		feature_single_div : '<div class="single-products"></div>',
		feature_info_div : '<div class="productinfo text-center"></div>',
		feature_over_div : '<div class="product-overlay"></div>',
		feature_cont_div : '<div class="overlay-content"></div>',
		feature_choose_div : '<div class="choose"></div>',
		feature_ul : '<ul class="nav nav-pills nav-justified"></ul>',
		feature_li : '<li></li>',
		feature_a_ch : '<a href=""><i class="fa fa-plus-square"></i></a>',
		feature_h2_tile : '<h2 class="title text-center"><s:text name="shop.index.right.features.items" /></h2>',
		feature_img : '<img></img>',
		feature_h2 : '<h2></h2>',
		feature_p : '<p></p>',
		feature_a : '<a href="javaScript:void(0)" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i><s:text name="shop.add.to.cart" /></a>',
	};
	
	var yiqin_shoplist_action = function(){
		var action = {
			searchProductsByCategory : function(cid){
				var $fenlei_product_items = $("#fenlei_product_items");
				$fenlei_product_items.empty();
				$fenlei_product_items.append($(shoplist_feature_temp.feature_h2_tile));
				$.ajax({
		            type: "POST",
		            async: true,
		            url: "findProductsByCategory",
		            data: "categoryId="+cid,
		            dataType: "json",
		            success: function(data){
		           		if(data=='1'){
		           			$fenlei_product_items.append("没有找到任何商品");
		           		}else if(data=='2'){
		           			$fenlei_product_items.append("加载失败，请稍后再试");
		           		}else{
		           			appendProductToList(data);
		           		}
		           },
		           beforeSend: function(){},
		           complete: function(){},
		           error: function(){}
		        });
			},
			
		};
		
		var appendProductToList = function(data){
			var $fenlei_product_items = $("#fenlei_product_items");
			$.each(data, function(i, val){
				var $feature_col_div = $(shoplist_feature_temp.feature_col_div),
					$feature_img_div = $(shoplist_feature_temp.feature_img_div),
					$feature_single_div = $(shoplist_feature_temp.feature_single_div),
					$feature_info_div = $(shoplist_feature_temp.feature_info_div),
					$feature_over_div = $(shoplist_feature_temp.feature_over_div),
					$feature_cont_div = $(shoplist_feature_temp.feature_cont_div),
					$feature_img = $(shoplist_feature_temp.feature_img),
					$feature_h2 = $(shoplist_feature_temp.feature_h2),
					$feature_p = $(shoplist_feature_temp.feature_p),
					$feature_a = $(shoplist_feature_temp.feature_a);
				
				$feature_col_div.append($feature_img_div.append($feature_single_div));
				$feature_single_div.append($feature_info_div);
				
				$feature_info_div.append($feature_img.attr({'src':val.imgUrl,'width':'255px','name':"good_img"}));
				$feature_img.css('cursor','pointer');
				$feature_img.click(function(){
					yiqin_public_js.toTilesAction(val.productId, "/toProductDetails");
				});
				$feature_info_div.append($feature_h2.append('￥'+val.price));
				$feature_info_div.append($feature_p.append(val.productName));
				$feature_info_div.append($feature_a.attr('id',val.productId));
				$feature_a.click(function(){
					yiqin_public_js.addProductToCart(this.id, 1);
				});
				
				$fenlei_product_items.append($feature_col_div);
			});
		};
		
		return action;
	}();
	
	$(document).ready(function(){
		yiqin_shoplist_action.searchProductsByCategory("<s:property value='#request.search_categoryId' />");
	});
</script>

<div class="col-sm-9 padding-right">
	<div class="features_items" id="fenlei_product_items"><!--features_items-->
<!-- 		<ul class="pagination"> -->
<!-- 			<li class="active"><a href="">1</a></li> -->
<!-- 			<li><a href="">2</a></li> -->
<!-- 			<li><a href="">3</a></li> -->
<!-- 			<li><a href="">&raquo;</a></li> -->
<!-- 		</ul> -->
	</div><!--features_items-->
</div>

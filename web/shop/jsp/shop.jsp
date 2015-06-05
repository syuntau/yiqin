<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var shop_temp = {
	filter_li : '<li style="margin-bottom: 10px;border-bottom: 1px solid #DDD;"></li>',
	filter_label : '<label></label>',
	filter_span : '<span style="margin-left:30px;"></span>',
	filter_a : '<a></a>',
};

var yiqin_shoplist_action = function(){
	var action = {
		findFilterAttr : function(categoryId){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findFilterAttr",
	             data: "categoryId="+categoryId,
	             dataType: "json",
	             success: function(data){
	            	var $shop_filter_ul = $("#shop_filter_ul");
	            	 if(data=='1'){
	            		 $shop_filter_ul.append("暂无筛选项信息！");
	            	 }else if(data=='2'){
	            		 $shop_filter_ul.append("筛选项信息加载失败！");
	            	 }else{
	            		 appendToFilter(data);
	            	 }
              },
              beforeSend: function(){},
              complete: function(){},
              error: function(){}
	         });
		},
		
	};
	
	var appendToFilter = function(data){
		var $shop_filter_ul = $("#shop_filter_ul"),
			brand = "<s:property value='brand'/>",
			color = "<s:property value='color'/>",
			price = "<s:property value='price'/>";
		$shop_filter_ul.empty();
			
		$.each(data, function(i,val){
			var $filter_li = $(shop_temp.filter_li),
				$filter_label = $(shop_temp.filter_label),
				$filter_span = $(shop_temp.filter_span),
				nameId = val.nameId,
				showValue = val.showValue,
				id = val.id;
			
			$filter_li.append($filter_label);
			$filter_label.append(val.name).append("：");
			var showValueArr = showValue.split(",");
			var valueArr = val.value.split(",");
			$.each(showValueArr, function(n,arr){
				$filter_a = $(shop_temp.filter_a);
				$filter_a.append(arr);
				if(nameId=='price'||nameId=='brandId'){
					arr = valueArr[n];
				}
				if(brand==id+"_"+arr || color==id+"_"+arr || price==id+"_"+arr){
					$filter_a.addClass('select-filter-a');
				}
				$filter_a.click(function(){
					if($(this).hasClass('select-filter-a')){
						filterProduct(nameId, "");
					}else{
						filterProduct(nameId, id+"_"+arr);
					}
				});
				$filter_span.append($filter_a);
			});
			$filter_li.append($filter_span);
			$shop_filter_ul.append($filter_li);
		});
	};
	
	return action;
}();

var filterProduct = function(filterType,filterVal){
	var paramVal = "<s:property value='paramVal'/>",
		brand = "<s:property value='brand'/>",
		color = "<s:property value='color'/>",
		price = "<s:property value='price'/>",
		pageIndex = 1;
	if(filterType=='brandId'){
		brand = filterVal;
	}else if(filterType=='price'){
		price = filterVal;
	}else if(filterType=='color'){
		color = filterVal;
	}else if(filterType=='page'){
		pageIndex = filterVal;
	}
	window.location.href = "productFilter?paramVal="+paramVal+"&brand="+encodeURIComponent(encodeURIComponent(brand))+"&color="+encodeURIComponent(encodeURIComponent(color))+"&price="+price+"&pageIndex="+pageIndex;
};

var toIndexPage = function(pageIndex){
	filterProduct('page', pageIndex);
};

$(document).ready(function(){
	var cateId = "<s:property value='paramVal'/>";
	if(cateId.length >=4 ){
		yiqin_shoplist_action.findFilterAttr(cateId);
	}else{
		$("#shop_filter_div").hide();
	}
});
</script>

<div class="col-sm-9 padding-right">
	<div class="container" id="shop_filter_div">
		<div class="s-title">
			<h4>
				<b></b><em>&nbsp;商品筛选</em>
			</h4>
		</div>
		<div class="row">
			<div class="col-sm-6" style="width: 100%">
				<div class="chose_area">
					<ul class="user_option" style="padding-left:0px;" id="shop_filter_ul"></ul>
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
									<s:property value="#product.zhekouPrice" />
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var shop_temp = {
	filter_li : '<li style="padding: 10px 5px;border-bottom: 1px solid #DDD;"></li>',
	filter_label : '<label></label>',
	filter_span : '<span></span>',
	filter_a : '<a></a>',
	filter_row : '<div class="row" />',
	filter_div : '<div />'
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
			filterStr = "<s:property value='filterStr'/>",
			filterArr = new Array();
		if(filterStr.length > 0){
			filterArr = filterStr.split(",");
		}
		$shop_filter_ul.empty();
		$.each(data, function(i,val){
			var $filter_li = $(shop_temp.filter_li),
				$filter_label = $(shop_temp.filter_label),
				$filterLbl = $(shop_temp.filter_div).addClass('col-sm-2'),
				$filterAll = $(shop_temp.filter_div).addClass('col-sm-1'),
				$filterItem = $(shop_temp.filter_div).addClass('col-sm-9'),
				$filterRow = $(shop_temp.filter_row),
				$filter_span = $(shop_temp.filter_span),
				nameId = val.nameId,
				showValue = val.showValue,
				id = val.id;
			
			$filter_li.append($filterRow);
			$filterRow.append($filterLbl.append($filter_label));
			$filter_label.append(val.name).append("：");
			
			$filter_a = $(shop_temp.filter_a);
			$filter_a.append("全部").attr('style','margin-left:-20px');
			$filterRow.append($filterAll.append($filter_a));
			$filter_a.click(function(){
				if(!$(this).hasClass('select-filter-a')){
					filterProduct(nameId, id+"_");
				}
			});
			if(filterStr.length == 0 || filterStr.indexOf(id)==-1){
				$filter_a.addClass('select-filter-a');
			}
			
			var showValueArr = showValue.split(",");
			var valueArr = val.value.split(",");
			$.each(showValueArr, function(n,arr){
				$filter_a = $(shop_temp.filter_a);
				$filter_a.append(arr);
				if(nameId=='price'||nameId=='brandId'){
					arr = valueArr[n];
				}
				if($.inArray(id+"_"+arr, filterArr) > -1){
					$filter_a.addClass('select-filter-a');
				}
				$filter_a.click(function(){
					if($(this).hasClass('select-filter-a')){
						filterProduct(nameId, id+"_");
					}else{
						filterProduct(nameId, id+"_"+arr);
					}
				});
				$filter_span.append($filter_a);
			});
			$filterRow.append($filterItem.append($filter_span));
			$shop_filter_ul.append($filter_li);
		});
	};
	
	return action;
}();

//重新组合过滤条件
var handleProductFilter = function(filterType,filterVal){
	var filterStr = "<s:property value='filterStr'/>";
	if(filterType!='page'){
		if(filterStr.length > 0){
			var clickfArr = filterVal.split("_");
			var newFilter = "";
			var filterArr = filterStr.split(",");
			for(var i=0;i<filterArr.length;i++){
				var fvArr = filterArr[i].split("_");
				if(clickfArr[0]!=fvArr[0]){
					newFilter += ","+filterArr[i];
				}
			}
			if(clickfArr[1]!=null && clickfArr[1].length>0){
				newFilter += ","+filterVal;
			}
			if(newFilter.length>0){
				newFilter = newFilter.substring(1);
			}
			return newFilter;
		}else{
			return filterVal;
		}
	}else{
		return filterStr;
	}
};

var filterProduct = function(filterType,filterVal){
	var paramVal = "<s:property value='paramVal'/>",
		pageIndex = 1;
	var filterStr = handleProductFilter(filterType, filterVal);
	if(filterType=='page'){
		pageIndex = filterVal;
	}
	window.location.href = "productFilter?paramVal="+paramVal+"&filterStr="+encodeURIComponent(encodeURIComponent(filterStr))+"&pageIndex="+pageIndex;
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

<div class="col-sm-9 padding-right" style="width: 921px">
	<div id="shop_filter_div">
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
<!-- 		<h2 class="title text-center"> -->
<%-- 			<s:text name="shop.index.right.features.items" /> --%>
<!-- 		</h2> -->
		<s:if test="page.results==null">
			没有找到任何商品
		</s:if>
		<s:else>
			<s:iterator value="page.results" var="product">
				<div class="col-sm-4">
					<div class="product-image-wrapper">
						<div class="single-products">
							<div class="productinfo text-center">
								<img src="<s:property value='#product.imgUrl'/>" width="255px" height="255px"
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

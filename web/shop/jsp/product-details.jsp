<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:iterator value="#request.product_detail" var="product">
	<s:set var="productInfo" value="#product"></s:set>
	<s:set var="productId" value="#product.productId"></s:set>
</s:iterator>

<script type="text/javascript">
var pro_detail_temp = {
	prodet_li : '<li></li>',
	prodet_label : '<label></label>',
	prodet_span : '<span></span>'
};

var yiqin_product_detail = function(){
	var action = {
		initProductDetail : function(){
			$("#detail_add_cart").click(function(){
				var productId = "<s:property value='#productId'/>",
					//customNum = $(this).prev().val(),
					customNum = $("#custom_num_input").val(),
					selName = $("#select_product_info").find("a[class*=select-a]").attr("title"),
					REX_NUM = /^(0|[1-9][0-9]*)$/;
					if(customNum==null || customNum=="" || !REX_NUM.test(customNum) || customNum <=0){
						$("#custom_num_input").val(1);
						return;
					}
					if($.type(selName)=="null" || $.type(selName)=="" || $.type(selName)=="undefined"){
						alert("请选择需要的颜色");
						return;
					}
				yiqin_public_js.addProductToCart(productId, customNum);
			});
			
			$("#custom_num_input").bind("blur focus",function(){
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
		
		findProductAttrDetail : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findProductDetail",
	             dataType: "json",
	             data : "productId=<s:property value='#productId'/>",
	             success: function(data){
	            	 var $details = $("#details").find("ul");
	            	 if(data=='1'){
	            		 $details.empty();
	            		 $details.append("暂无产品详细信息！");
	            	 }else if(data=='2'){
	            		 $details.empty();
	            		 $details.append("产品详细信息加载失败！");
	            	 }else{
	            		 appendToProductDetail(data);
	            	 }
              },
              beforeSend: function(){},
              complete: function(){},
              error: function(){}
	         });
		},
		
		initImage : function() {
			var imageUrls = "<s:property value='#productInfo.imgUrl'/>";
			var imgArr = imageUrls.split(",");
			$('.view-product img').attr('src', imgArr[0]);
			var cnt = 0;
			var $imgNavDiv = $('#similar-product .carousel-inner');
			var $div = $('<div class="item">');
			for (var i in imgArr) {
				if (cnt % 3 == 0) {
					$div = $('<div class="item">');
					$imgNavDiv.append($div);
				}
				var $img = $('<a style="cursor:pointer;"><img width="70" height="70"></a>');
				$img.attr('val', imgArr[i]);
				$img.find('img').attr('src', imgArr[i]);
				$img.on('click', function() {
					$('.view-product img').attr('src', $(this).attr('val'));
				});
				$div.append($img);
				cnt++;
			}
			$imgNavDiv.find('.item:first').addClass('active');
		}
	};
	
	var appendToProductDetail = function(data){
		var $details = $("#details").find("ul");
		$details.empty();
		
		$.each(data,function(key,val){
			if(val==null||val==""||val.length==0){
				$details.append("暂无产品详细信息！");
				return;
			}
			$.each(val,function(k,v){
				if(k.indexOf('图片')==-1){
					var $prodet_li = $(pro_detail_temp.prodet_li).css('width','100%'),
						$prodet_label = $(pro_detail_temp.prodet_label),
						$prodet_span = $(pro_detail_temp.prodet_span);
					
					$prodet_li.append($prodet_label).append($prodet_span);
					$prodet_label.append(k).append("：");
					$prodet_span.append(v);
					$details.append($prodet_li);
				}
			});
		});
	};
	
	return action;
}();

$(document).ready(function(){
	yiqin_product_detail.initProductDetail();
	yiqin_product_detail.findProductAttrDetail();
	yiqin_product_detail.initImage();
});
</script>

<!-- <div class="col-sm-9 padding-right" style="width: 921px"> -->
<div class="padding-right">
	<div class="product-details">
		<!--product-details-->
		<div class="col-sm-4">
			<div class="view-product">
				<img />
				<input type="hidden" id="<s:property value='#productId'/>" value="<s:property value='#productId'/>">
			</div>
			<div id="similar-product" class="carousel slide" data-ride="carousel">
				<!-- Wrapper for slides -->
				<div class="carousel-inner">
				</div>
				<!-- Controls -->
				<a class="left item-control" href="#similar-product" data-slide="prev"> <i class="fa fa-angle-left"></i></a>
				<a class="right item-control" href="#similar-product" data-slide="next"> <i class="fa fa-angle-right"></i></a>
			</div>
		</div>
		<div class="col-sm-8">
			<div class="product-information">
				<!--/product-information-->
				<h2><s:property value='#productInfo.productName'/></h2>
				<p>
					<s:text name="shop.product.details.productId" />
					<s:property value='#productId'/>
				</p>
				<span>
					<span>
						<s:if test="#session.userInfo==null">
							<span style="font-size:10px;"><s:text name="shop.product.list.zhekou.info"/></span>
						</s:if>
						<s:else>
							<s:property value='#productInfo.zhekouPrice'/>
						</s:else><br>
						<p><del style="font-size:14px;">
								<s:text name="shop.product.label.yuan.price"/>
							 	<s:property value="#productInfo.price" />
						   </del>
						</p>
					</span>
					<label><s:text name="cart.item.quantity" />:</label>
					<input type="text" value="1" id="custom_num_input"/>
<!-- 					<button type="button" class="btn btn-fefault cart" id="detail_add_cart"> -->
<!-- 						<i class="fa fa-shopping-cart"></i> -->
<%-- 						<s:text name="shop.add.to.cart" /> --%>
<!-- 					</button> -->
				</span>
				<div id="select_product_info">
					<p>
<%-- 						<b><s:text name="shop.product.details.color"/></b> --%>
						<b><s:text name="shop.product.details.param"/></b>
					</p>
					<div class="none-sel-div" style="margin-top:2px;margin-right:8px;margin-bottom:2px;">
						<b></b>
						<a href="#none" title="<s:property value='#productInfo.color'/>" class="none-detail-a">
<%-- 							<img src="<s:property value='#productInfo.imgUrl'/>" class="share img-responsive" alt="<s:property value='#productInfo.color'/>" width="25" height="25"/> --%>
							<i style="font-size: 16px;"><s:property value='#productInfo.color'/></i>
						</a>
<%-- 						<a href="#none" title="<s:property value='#productInfo.param'/>" class="none-detail-a"> --%>
<%-- 							<i style="font-size: 16px;"><s:property value='#productInfo.param'/></i> --%>
<!-- 						</a> -->
					</div>
				</div>
			</div>
			<button type="button" class="btn btn-fefault cart" id="detail_add_cart" style="margin-left:50px;font-size:20px;height:50px;width:180px;margin-top:60px">
				<i class="fa fa-shopping-cart"></i>
				<s:text name="shop.add.to.cart" />
			</button>
			<!--/product-information-->
		</div>
	</div>
	<!--/product-details-->

	<div class="category-tab shop-details-tab" style="float: left;margin-top: 20px;">
		<!--category-tab-->
		<div class="col-sm-12">
			<ul class="category-tab-ul nav nav-tabs">
<!-- 				<li class="active"> -->
				<li>
<%-- 					<a href="#details" data-toggle="tab"><s:text name="shop.product.details.details"></s:text></a> --%>
					<span><s:text name="shop.product.details.details"></s:text></span>
				</li>
<!-- 				<li> -->
<%-- 					<a href="#speparam" data-toggle="tab"><s:text name="shop.product.details.spe.parameters"></s:text></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a href="#reviews" data-toggle="tab"><s:text name="shop.product.details.reviews"></s:text></a> --%>
<!-- 				</li> -->
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane fade active in" id="details">
				<div class="col-sm-12">
<!-- 					<div class="product-image-wrapper"> -->
						<div class="single-products">
							<div class="productinfo">
								<ul class="p-parameter-list"></ul>
							</div>
						</div>
<!-- 					</div> -->
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
<%-- 						<b>Rating: </b> <img src="<s:property value='#productInfo.imgUrl'/>" alt="" /> --%>
<!-- 						<button type="button" class="btn btn-default pull-right"> -->
<!-- 							Submit</button> -->
<!-- 					</form> -->
				</div>
			</div>
		</div>
	</div>
	<!--/category-tab-->
</div>

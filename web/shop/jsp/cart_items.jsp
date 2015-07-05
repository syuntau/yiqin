<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_template = {
	cart_tr : '<tr></tr>',
	cart_td : '<td></td>',
	cart_h4 : '<h4></h4>',
	cart_a : '<a href="javaScript:void(0);"></a>',
	cart_i : '<i class="fa fa-times"></i>',
	cart_p : '<p></p>',
	cart_del: '<del></del>',
	cart_img : '<img/>',
	cart_div : '<div class="cart_quantity_button"></div>',
	cart_input : '<input class="cart_quantity_input" type="text" name="quantity" autocomplete="off" size="2"></input>',
};

var yiqin_cart_action = function(){
	var cart_action = {
		initCartInfo : function(touchType){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findCartInfo",
	             dataType: "json",
	             success: function(data){
	            	 var $cart_info_list = $('#cart_info_list');
	            	 $cart_info_list.empty();
	            	 if(data=='1'){
	            		 $("#to_check_btn").css("display", "none");
	            		 $cart_info_list.append("购物车还没有心爱的商品哦，赶快去抢购吧！");
	            	 }else if(data=='2'){
	            		 $("#to_check_btn").css("display", "none");
	            		 $cart_info_list.append("购物车坏了，正在抢修哦，稍后再来");
	            	 }else{
	            		 appendInfoToCart(data);
	            		 if(touchType=='delete'){
	            			 $cart_info_list.append("成功删除1件商品");
	            		 }
	            	 }
                 },
                 beforeSend: function(){},
                 complete: function(){},
                 error: function(){}
	         });
		},
		
		deleteCartProduct : function(productId){
			if (confirm("您是否真的需要从购物车里删除此商品？")) {
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteCartProduct",
		             data: "productId="+productId,
		             dataType: 'text',
		             success: function(data){
		            	 if(data=='none'){
		            		alert("您要删除的商品不存在，请刷新页面再试哦！");
		            	 }else if(data=='error'){
		            		alert("删除商品失败，请稍后再试！");
		            	 }else{
		            		 if(data != "notLoginError"){
		            			 $("#J_MiniCartNum").html(data);
		            		 }
		            		yiqin_cart_action.initCartInfo("delete");
		            	 }
	                },
	                beforeSend: function(){},
	                complete: function(){},
	                error: function(){}
		         });
		    }
		},
		
		changeCartProductNum : function(touchType,productId,pIndex,customNum){
			var $productTr = $("#"+pIndex+"_product"),
				$productInput = $("#"+pIndex+"_input"),
				price = $productTr.data(pIndex+"_price"),
				pNum = $productTr.data(pIndex+"_productNum"),
				REX_NUM = /^(0|[1-9][0-9]*)$/;
			if(touchType=="custom"){
				if(customNum==null || customNum=="" || !REX_NUM.test(customNum) || customNum <=0 || customNum==pNum){
					$productInput.val(pNum);
					return false;
				}
				pNum = customNum;
			}else{
				if(touchType=="up"){
					pNum = parseInt(pNum)+1;
				}else{
					if(pNum > 0){
						if((pNum-1) <= 0){
							$productInput.val(pNum);
							return false;
						}
						pNum = pNum-1;
					}else{
						return false;
					}
				}
			}
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "updateCartProductsNum",
	             data: "productId="+productId+"&productNum="+pNum,
	             dataType: 'text',
	             success: function(data){
	            	 if(data=='1'){
	            		alert("此商品不存在，请刷新页面再试哦！");
	            	 }else if(data=='2'){
	            		alert("操作商品失败，请稍后再试！");
	            	 }else if(data=='success'){
	            		 $productTr.data(pIndex+"_productNum",pNum);
	            		 $productInput.val(pNum);
	            		 $productTr.find("p[class='cart_total_price']").html(pNum*price);
	            	 }
               },
               beforeSend: function(){},
               complete: function(){},
               error: function(){}
	         });
		},
		
		toCartCheck : function(){
			$("#to_check_btn").click(function(){
				var $cart_info_list = $('#cart_info_list'),
					selectCartPid = $cart_info_list.find("tr");
				if(selectCartPid.length<=0){
					alert("您购物车还没有商品，赶快去选购商品吧！");
					return;
				}
				var productIds = "";
				$.each(selectCartPid, function(){
					var pid = $(this).data("_productId");
					productIds += ","+pid;
				});
				if(productIds != null && productIds != ""){
					productIds = productIds.substring(1);
				}
				yiqin_public_js.toTilesAction(productIds, "/toSettlementOrder");
			});
		},
	};
	
	var appendInfoToCart = function(data){
		 var $cart_info_list = $('#cart_info_list');
		 $cart_info_list.empty();
		 $.each(data, function(i, val){
			 var $cart_tr = $(cart_template.cart_tr),
				$cart_td = $(cart_template.cart_td),
				$cart_h4 = $(cart_template.cart_h4),
				$cart_a = $(cart_template.cart_a),
				$cart_i = $(cart_template.cart_i),
				$cart_p = $(cart_template.cart_p),
				$cart_del = $(cart_template.cart_del),
				$cart_img = $(cart_template.cart_img),
			 	$cart_div = $(cart_template.cart_div),
			 	$cart_input = $(cart_template.cart_input);
			 $cart_td.attr('class',"cart_product");
			 $cart_td.append($cart_a.append($cart_img));
			 $cart_a.click(function(){
				 yiqin_public_js.toTilesAction(val.productId, "/toProductDetails");
			 });
			 $cart_img.attr({"src":val.imgUrl,"width":"110px"});
			 $cart_tr.append($cart_td);
			 $cart_td = $(cart_template.cart_td),
			 $cart_a = $(cart_template.cart_a),
			 $cart_img = $(cart_template.cart_img);
			 $cart_td.attr('class',"cart_description");
			 $cart_td.append($cart_h4).append($cart_p.css('margin-top', '10px'));
			 $cart_h4.append($cart_a.append(val.productName).css('width', '457px'));
			 $cart_p.append('<s:text name="shop.product.details.productId"/>'+val.productId);
			 $cart_p = $(cart_template.cart_p);
			 $cart_p.append('<s:text name="cart.item.product.color"/>'+val.productInfo);
			 $cart_td.append($cart_p);
 			 $cart_a.click(function(){
 				yiqin_public_js.toTilesAction(val.productId, "/toProductDetails");
			 });
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_price");
 			$cart_td.append($cart_p.append(val.zhekouPrice));
 			$cart_td.append($cart_del.append(val.price));
 			$cart_tr.append($cart_td);
 			
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_quantity");
 			$cart_td.append($cart_div.append($cart_a).append($cart_input));
 			$cart_input.val(val.count).attr('id',i+"_input");
 			$cart_input.blur(function(){
 				var cNum = $(this).val();
 				yiqin_cart_action.changeCartProductNum('custom',val.productId,i,cNum);
 			});
 			$cart_a.attr('class',"cart_quantity_down").append(" - ");
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('down',val.productId,i,1);
			});
 			$cart_a = $(cart_template.cart_a);
 			$cart_a.attr('class',"cart_quantity_up").append(" + ");
 			$cart_div.append($cart_a);
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('up',val.productId,i,1);
			});
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_total").append($cart_p);
 			$cart_p.attr('class',"cart_total_price").append(val.count*val.zhekouPrice);
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_delete").append($cart_a.append($cart_i));
 			$cart_a.attr('class',"cart_quantity_delete");
 			$cart_a.click(function(){
 				 yiqin_cart_action.deleteCartProduct(val.productId);
			});
 			$cart_tr.append($cart_td);
 			
 			$cart_tr.data(i+"_price",val.zhekouPrice);
 			$cart_tr.data(i+"_productNum",val.count);
 			$cart_tr.data("_productId",val.productId);
 			$cart_tr.attr('id',i+'_product');
 			$cart_info_list.append($cart_tr);
         });
	};
	
	return cart_action;
}();

$(document).ready(function() {
	yiqin_cart_action.initCartInfo("init");
	yiqin_cart_action.toCartCheck();
});
</script>

<section id="cart_items">
	<div class="container">
		<div class="table-responsive cart_info">
			<table class="table table-condensed">
				<thead>
					<tr class="cart_menu">
						<td class="image"></td>
						<td class="description"><s:text name="cart.item.product"  /></td>
						<td class="price"><s:text name="cart.item.price"  /></td>
						<td class="quantity"><s:text name="cart.item.quantity"  /></td>
						<td class="total"><s:text name="cart.item.total"  /></td>
						<td></td>
					</tr>
				</thead>
				<tbody id="cart_info_list"></tbody>
			</table>
			<button type="button" id="to_check_btn" style="margin-top:20px;margin-bottom:20px;width:150px" class="btn btn-fefault pull-right">去结算</button>
		</div>
	</div>
</section>
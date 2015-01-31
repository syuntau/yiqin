<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_template = {
	cart_tr : '<tr></tr>',
	cart_td : '<td></td>',
	cart_h4 : '<h4></h4>',
	cart_a : '<a></a>',
	cart_i : '<i class="fa fa-times"></i>',
	cart_p : '<p></p>',
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
	             url: "findCartInfo.action",
	             dataType: "json",
	             success: function(data){
	            	 var $cart_info_list = $('#cart_info_list');
	            	 $cart_info_list.empty();
	            	 if(data=='1'){
	            		 $cart_info_list.append("购物车还没有心爱的商品哦，赶快去抢购吧！");
	            	 }else if(data=='2'){
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
		             url: "deleteCartProduct.action",
		             data: "productId="+productId,
		             dataType: 'text',
		             success: function(data){
		            	 if(data=='1'){
		            		alert("您要删除的商品不存在，请刷新页面再试哦！");
		            	 }else if(data=='2'){
		            		alert("删除商品失败，请稍后再试！");
		            	 }else if(data=='success'){
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
				if(customNum==null || customNum=="" || customNum <0 || !REX_NUM.test(customNum) || customNum==pNum){
					$productInput.val(pNum);
					return false;
				}
				pNum = customNum;
			}else{
				if(touchType=="up"){
					pNum = parseInt(pNum)+1;
				}else{
					if(pNum > 0){
						pNum = pNum-1;
					}else{
						return false;
					}
				}
			}
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "updateCartProductsNum.action",
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
				$cart_img = $(cart_template.cart_img),
			 	$cart_div = $(cart_template.cart_div),
			 	$cart_input = $(cart_template.cart_input);
			 $cart_td.attr('class',"cart_product");
			 $cart_td.append($cart_a.append($cart_img));
			 $cart_a.click(function(){
				 
			 });
			 $cart_img.attr("src",val.imageUrl);
			 $cart_tr.append($cart_td);
			 $cart_td = $(cart_template.cart_td),
			 $cart_a = $(cart_template.cart_a),
			 $cart_img = $(cart_template.cart_img);
			 $cart_td.attr('class',"cart_description");
			 $cart_td.append($cart_h4).append($cart_p);
			 $cart_h4.append($cart_a.append(val.productName));
			 $cart_p.append(val.productId);
 			 $cart_a.click(function(){
				 
			 });
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_price");
 			$cart_td.append($cart_p.append(val.price));
 			$cart_tr.append($cart_td);
 			
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_quantity");
 			$cart_td.append($cart_div.append($cart_a).append($cart_input));
 			$cart_input.val(val.productNum).attr('id',i+"_input");
 			$cart_input.blur(function(){
 				var cNum = $(this).val();
 				yiqin_cart_action.changeCartProductNum('custom',val.productId,i,cNum);
 			});
 			$cart_a.attr('class',"cart_quantity_up").append(" + ");
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('up',val.productId,i,1);
			});
 			$cart_a = $(cart_template.cart_a);
 			$cart_a.attr('class',"cart_quantity_down").append(" - ");
 			$cart_div.append($cart_a);
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('down',val.productId,i,1);
			});
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_total").append($cart_p);
 			$cart_p.attr('class',"cart_total_price").append(val.productNum*val.price);
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_delete").append($cart_a.append($cart_i));
 			$cart_a.attr('class',"cart_quantity_delete");
 			$cart_a.click(function(){
 				 yiqin_cart_action.deleteCartProduct(val.productId);
			});
 			$cart_tr.append($cart_td);
 			
 			$cart_tr.data(i+"_price",val.price);
 			$cart_tr.data(i+"_productNum",val.productNum);
 			$cart_tr.attr('id',i+'_product');
 			$cart_info_list.append($cart_tr);
         });
	};
	
	
	
	
	
	
	
	return cart_action;
}();

$(document).ready(function() {
	yiqin_cart_action.initCartInfo("init");
});
</script>

<section id="cart_items">
	<div class="container">
		<div class="breadcrumbs">
			<ol class="breadcrumb">
			  <li><a href="javaScript:void(0)" onclick="postFormToTopAction('top_home');">Home</a></li>
			  <li class="active">Shopping Cart</li>
			</ol>
		</div>
		<div class="table-responsive cart_info">
			<table class="table table-condensed">
				<thead>
					<tr class="cart_menu">
						<td class="image">Item</td>
						<td class="description"></td>
						<td class="price">Price</td>
						<td class="quantity">Quantity</td>
						<td class="total">Total</td>
						<td></td>
					</tr>
				</thead>
				<tbody id="cart_info_list"></tbody>
			</table>
		</div>
	</div>
</section>
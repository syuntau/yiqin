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
		initCodeCartInfo : function(touchType){
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
	            		 $cart_info_list.append("赶快搜索商品编码，添加商品吧。");
	            	 }else if(data=='2'){
	            		 $("#to_check_btn").css("display", "none");
	            		 $cart_info_list.append("购物车坏了，正在抢修哦，稍后再来");
	            	 }else{
	            		 appendCartPrdToCart(data);
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
		            		 $("#"+productId+"_product").remove();
		     				var $cart_info_list = $('#cart_info_list');
		     				if($cart_info_list.find("tr").length<=0){
		     					$("#to_check_btn").css("display", "none");
		     		       		$cart_info_list.append("赶快搜索商品编码，添加商品吧。");
		     				}
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
					return;
				}
				pNum = customNum;
			}else{
				if(touchType=="up"){
					pNum = parseInt(pNum)+1;
				}else{
					if(pNum > 0){
						if((pNum-1) <= 0){
							$productInput.val(pNum);
							return;
						}
						pNum = pNum-1;
					}else{
						return;
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
	            		 $productTr.find("p[class='cart_total_price']").html((pNum*price).toFixed(2));
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
		
		orderSearch : function(searchId){
			var searchObj = document.getElementById(searchId),
				searchStr = searchObj.value;
			if (searchStr==searchObj.defaultValue){
				searchStr = "";
			}
			searchStr = $.trim(searchStr);
			if(searchStr==""){
				alert("请输入商品编码！");
				return;
			}
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findProductInfo",
	             data: "productId="+searchStr,
	             dataType: 'json',
	             success: function(data){
	            	 var $cart_info_list = $('#cart_info_list');
	            	 if(data=='1'){
	            		 alert("输入的商品编码不存在！");
	            	 }else if(data=='2'){
	            		 alert("搜索失败，请重新再试！");
	            	 }else{
	 					 var selectCartPid = $cart_info_list.find("tr");
	 					if(selectCartPid.length>0){
	 						var productIds = "";
			 				 $.each(selectCartPid, function(){
			 					var pid = $(this).data("_productId");
			 					productIds += ","+pid;
			 				 });
			 				 if(productIds != null && productIds != ""){
			 					if(productIds.indexOf(searchStr)<0){
			 						addCodeProductToCart(searchStr,1,data);
			 					}else{
			 						alert("此商品已经在列表中");
			 					}
			 				 }
	 					}else{
	 						$cart_info_list.empty();
	 						addCodeProductToCart(searchStr,1,data);
	 					}
	 					searchObj.value = "";
	            	}
              },
              beforeSend: function(){},
              complete: function(){},
              error: function(){}
	         });
		},
	};
	
	var addCodeProductToCart = function(productId, num, prdData){
		$.ajax({
	        type: "POST",
	        async: true,
	        url: "addProductToCart",
	        data: "productId="+productId+"&num="+num,
	        dataType: "text",
	        success: function(data){
	       	 if(data=='none'){
	       		 alert("该商品不存在或已经下架！");
	       	 }else if(data=='error'){
	       		 alert("加入到购物车失败，请稍后再试！");
	       	 }else{
	       		 if(data != "notLoginError"){
		       		 $("#J_MiniCartNum").html(data);
		       		 appendInfoToCart(prdData);
					 $("#to_check_btn").css("display", "block");
	       		 }
	       	 }
	       },
	       beforeSend: function(){},
	       complete: function(){},
	       error: function(){}
	    });
	};
	
	var appendCartPrdToCart = function(data){
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
			 var imgArr = val.imgUrl.split(","), tempImage;
			 if (imgArr.length > 1) {
				 tempImage = imgArr[0];
			 } else {
				 tempImage = val.imgUrl;
			 }
			 $cart_img.attr({"src":tempImage,"width":"100px"});
			 $cart_tr.append($cart_td);
			 $cart_td = $(cart_template.cart_td),
			 $cart_a = $(cart_template.cart_a),
			 $cart_img = $(cart_template.cart_img);
			 $cart_td.attr('class',"cart_description");
			 $cart_td.append($cart_h4).append($cart_p.css('margin-top', '10px')).css('width', '457px');
			 $cart_h4.append($cart_a.append(val.productName));
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
 			$cart_input.val(val.count).attr('id',val.productId+"_input");
 			$cart_input.blur(function(){
 				var cNum = $(this).val();
 				yiqin_cart_action.changeCartProductNum('custom',val.productId,val.productId,cNum);
 			});
 			$cart_a.attr('class',"cart_quantity_down").append(" - ");
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('down',val.productId,val.productId,1);
			});
 			$cart_a = $(cart_template.cart_a);
 			$cart_a.attr('class',"cart_quantity_up").append(" + ");
 			$cart_div.append($cart_a);
 			$cart_a.click(function(){
 				yiqin_cart_action.changeCartProductNum('up',val.productId,val.productId,1);
			});
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_total").append($cart_p);
 			$cart_p.attr('class',"cart_total_price").append((val.count*val.zhekouPrice).toFixed(2));
 			$cart_tr.append($cart_td);
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_delete").append($cart_a.append($cart_i));
 			$cart_a.attr('class',"cart_quantity_delete");
 			$cart_a.click(function(){
 				 yiqin_cart_action.deleteCartProduct(val.productId);
			});
 			$cart_tr.append($cart_td);
 			
 			$cart_tr.data(val.productId+"_price",val.zhekouPrice);
 			$cart_tr.data(val.productId+"_productNum",val.count);
 			$cart_tr.data("_productId",val.productId);
 			$cart_tr.attr('id',val.productId+'_product');
 			$cart_info_list.append($cart_tr);
         });
	};
	
	var count = 0,tid;
	var appendInfoToCart = function(data){
		 var $cart_info_list = $('#cart_info_list');
		 if($cart_info_list.find('tr').length>0){
			 $cart_info_list.find('tr').css('border','1px solid#F7F7F0');
		 }
		 count = 0;
		 clearInterval(tid);
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
			 var imgArr = val.imgUrl.split(","), tempImage;
			 if (imgArr.length > 1) {
				 tempImage = imgArr[0];
			 } else {
				 tempImage = val.imgUrl;
			 }
			 $cart_img.attr({"src":tempImage,"width":"100px"});
			 $cart_tr.append($cart_td);
			 $cart_td = $(cart_template.cart_td),
			 $cart_a = $(cart_template.cart_a),
			 $cart_img = $(cart_template.cart_img);
			 $cart_td.attr('class',"cart_description");
			 $cart_td.append($cart_h4).append($cart_p.css('margin-top', '10px')).css('width', '457px');
			 $cart_h4.append($cart_a.append(val.productName));
			 $cart_p.append('<s:text name="shop.product.details.productId"/>'+val.productId);
			 $cart_p = $(cart_template.cart_p);
			 $cart_p.append('<s:text name="cart.item.product.color"/>'+val.color);
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
			$cart_input.val(1).attr('id',val.productId+"_input");
			$cart_input.blur(function(){
				var cNum = $(this).val();
				yiqin_cart_action.changeCartProductNum('custom',val.productId,val.productId,cNum);
			});
			$cart_a.attr('class',"cart_quantity_down").append(" - ");
			$cart_a.click(function(){
				yiqin_cart_action.changeCartProductNum('down',val.productId,val.productId,1);
			});
			$cart_a = $(cart_template.cart_a);
			$cart_a.attr('class',"cart_quantity_up").append(" + ");
			$cart_div.append($cart_a);
			$cart_a.click(function(){
				yiqin_cart_action.changeCartProductNum('up',val.productId,val.productId,1);
			});
			$cart_tr.append($cart_td);
			$cart_td = $(cart_template.cart_td),
			$cart_p = $(cart_template.cart_p);
			$cart_td.attr('class',"cart_total").append($cart_p);
			$cart_p.attr('class',"cart_total_price").append((1*val.zhekouPrice).toFixed(2));
			$cart_tr.append($cart_td);
			$cart_td = $(cart_template.cart_td),
			$cart_a = $(cart_template.cart_a);
			$cart_td.attr('class',"cart_delete").append($cart_a.append($cart_i));
			$cart_a.attr('class',"cart_quantity_delete");
			$cart_a.click(function(){
				 yiqin_cart_action.deleteCartProduct(val.productId);
			});
			$cart_tr.append($cart_td);
			
			$cart_tr.data(val.productId+"_price",val.zhekouPrice);
			$cart_tr.data(val.productId+"_productNum",1);
			$cart_tr.data("_productId",val.productId);
			$cart_tr.attr('id',val.productId+'_product');
			$cart_info_list.prepend($cart_tr);
			tid = setInterval(function(){
				if(count>=20){
					clearInterval(tid);
					return;
				}
				if(count%2==0){
					$cart_tr.css('border','2px solid#FE980F');
				}else{
					$cart_tr.css('border','1px solid#F7F7F0');
				}
				count++;
			},500);
        });
	};
	
	return cart_action;
}();

$(document).ready(function() {
	$("#ip_keyword").bind('input propertychange',function(){
		if(this.value==this.defaultValue || this.value==""){
			$(this).css({'font-size':'','font-weight':'normal'});
		}else{
			$(this).css({'font-size':'24px','font-weight':'bold'});
		}
	});
	yiqin_cart_action.initCodeCartInfo();
	yiqin_cart_action.toCartCheck();
});
</script>
<span id="search_error"></span>
<div class="search-01" style="padding-bottom:10px;padding-right:200px;padding-top:10px;font-size:20px;line-height:22px;">
	 编码下单：
	<input style="line-height:30px;height:30px;" id="ip_keyword" name="searchName" type="text" class="s-itxt" value="输入商品ID，按回车键" onfocus="if(this.value==this.defaultValue){this.value='';$(this).css({'font-size':'','font-weight':'normal'});}" onblur="if (this.value==''){this.value=this.defaultValue;$(this).css({'font-size':'','font-weight':'normal'});}" onkeydown="javascript:if(event.keyCode==13) yiqin_cart_action.orderSearch('ip_keyword');">
    <a href="javascript:;" class="btn-13" style="height:30px;" onclick="yiqin_cart_action.orderSearch('ip_keyword')">搜 索</a>
</div>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var yiqin_order_center = function(){
	var action = {
		initOrderCheck : function(){
			$("#my_order_left").css('color','#fdb45e');
			filterDateSelectInit();
			
			var searchObj = document.getElementById("ip_keyword"),
				searchName = "<s:property value='searchName'/>";
				if(searchName!=null && searchName!=""){
					searchObj.value = searchName;
					$("#submitDate").css("display","none");
					$("#submitStatus").css("display","none");
				}
				
			 var filterDate = "<s:property value='filterTime'/>";
			 $("#submitDate").val(filterDate);
			 
			 var filterStatus = "<s:property value='status'/>";
			 $("#submitStatus").val(filterStatus);
			
			 $("#submitDate, #submitStatus").change(function(){
				 yiqin_order_center.orderFilter();
			 });
			
		},
			
		orderSearch : function(searchId){
			var searchObj = document.getElementById(searchId),
				searchStr = searchObj.value,
				productName = "",
				productId = "",
				orderId = "";
			if (searchStr==searchObj.defaultValue){
				searchStr = "";
			}
			searchStr = $.trim(searchStr);
			if(searchStr==""){
				alert("请输入查询关键词！");
				return;
			}
			window.location.href = "findOrderList?searchName="+encodeURIComponent(encodeURIComponent(searchStr));
		},
		
		orderFilter : function(){
			var filterTime= $("#submitDate").val(),
				status = $("#submitStatus").val();
			window.location.href = "findOrderList?filterTime="+filterTime+"&status="+status;
		}
	};
	
	var filterDateSelectInit = function(){
		var $dateOption = $("<option></option>"),
			$dateSelect = $("#submitDate"),
			nowDate = new Date(),
			nowYear = nowDate.getFullYear();
		$dateSelect.append($dateOption.attr("value","1").append("最近三个月"));
		$dateOption = $("<option></option>");
		$dateSelect.append($dateOption.attr("value","2").append("今年内"));
		$dateOption = $("<option></option>");
		$dateSelect.append($dateOption.attr("value",nowYear-1).append((nowYear-1)+"年"));
		$dateOption = $("<option></option>");
		$dateSelect.append($dateOption.attr("value",nowYear-2).append((nowYear-2)+"年"));
		$dateOption = $("<option></option>");
		$dateSelect.append($dateOption.attr("value",nowYear-3).append((nowYear-3)+"年"));
		$dateOption = $("<option></option>");
		$dateSelect.append($dateOption.attr("value","3").append((nowYear-3)+"年以前"));
	};
	
	return action;
}();

var toIndexPage = function(pageIndex){
	var type = $("#submitDate").css("display"),
		filterTime = "<s:property value='filterTime'/>",
		searchStr = "<s:property value='searchName'/>",
		status = "<s:property value='status'/>";
	if(type=="none"){
		window.location.href = "findOrderList?searchName="+encodeURIComponent(encodeURIComponent(searchStr))+"&pageIndex="+pageIndex;
	}else{
		window.location.href = "findOrderList?filterTime="+filterTime+"&status="+status+"&pageIndex="+pageIndex;
	}
};

$(document).ready(function(){
	yiqin_order_center.initOrderCheck();
});
</script>

<div class="search-01">
	<input id="ip_keyword" name="searchName" type="text" class="s-itxt" value="商品名称、商品编号、订单编号" onfocus="if (this.value==this.defaultValue) this.value=''" onblur="if (this.value=='') this.value=this.defaultValue" onkeydown="javascript:if(event.keyCode==13) yiqin_order_center.orderSearch('ip_keyword');">
    <a href="javascript:;" class="btn-13" onclick="yiqin_order_center.orderSearch('ip_keyword')">查 询</a>
</div>
<div class="center-right padding-right">
	<section id="cart_items">
		<div>
			<div class="table-responsive cart_info" style="margin-bottom:10px;">
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image" width="40%">订单信息</td>
							<td width="10%" style="text-align:center;">单价</td>
							<td width="11%" style="text-align:center;">数量</td>
							<td width="10%" style="text-align:center;">总价</td>
							<td width="18%" style="text-align:center;">
								<select id="submitDate"></select>
							</td>
							<td width="10%" style="text-align:center;">
								<select id="submitStatus">
									<option value="10">全部状态</option>
									<option value="1">等待付款</option>
									<option value="2">等待收货</option>
									<option value="3">已完成</option>
									<option value="0">已取消</option>
								</select>
						    </td>
						</tr>
					</thead>
					<s:if test="page.results==null">
						<tbody>
							<tr>
								<td colspan="6">
									没有符合条件的订单信息
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else>
						<s:iterator value="page.results" var="order">
							<tbody>
								<tr style="background-color: #F0F0E9;font-weight:bold;">
									<td>
										<div class="summary" style="margin-left:20px">
											<span>订单号：<s:property value="#order.id"/></span>
											<span style="margin-left:50px">收货人：<s:property value="#order.name"/></span>
										</div>
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td style="text-align:center;">
										<span><s:property value='#order.zongjia'/></span>
									</td>
									<td style="text-align:center;">
										<span><s:property value='#order.crateDate'/></span>
									</td>
									<td style="text-align:center;">
										<span><s:property value='#order.status'/></span>
									</td>
								</tr>
								<s:iterator value="#order.productList" var="product" status="st">
									 <tr>
										<td class="cart_product">
											<a href="javaScript:;"><img onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>, 'toProductDetails');" src="<s:property value="#product.imgUrl"/>" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/></a>
											<h4><a href="javaScript:;" onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>, 'toProductDetails');"><s:property value="#product.productName"/></a></h4>
											<p><s:text name="shop.product.details.productId"/><s:property value="#product.productId"/></p>
											<p><s:text name="cart.item.product.color"/><s:property value="#product.productInfo"/></p>
										</td>
										<td class="cart_price" style="text-align:center;">
											<p><s:property value='#product.zhekouPrice'/></p>
											<p style="font-size:14px;color:#FE980F">
												<s:text name="shop.product.label.zhekou"/>
												<s:property value="#order.zhekou"/>
											</p>
											<del><s:text name="shop.product.label.yuan.price"/>
												 <s:property value="#product.price" />
											</del>
										</td>
										<td style="text-align:center;">
											<p><s:property value="#product.count"/></p>
										</td>
										<td style="text-align:center;">
											<p>
												<script type="text/javascript">
													document.write(<s:property value="#product.zhekouPrice"/>*<s:property value="#product.count"/>);
												</script>
											</p>
										</td>
										<td colspan="2"></td>
									</tr>
								</s:iterator>
							</tbody>
						</s:iterator>
					</s:else>
				</table>
			</div>
			<s:if test="page.results!=null">
				<div style="text-align: center;width: 99%">
					<jsp:include page="/shop/jsp/common/page.jsp"></jsp:include>
				</div>
			</s:if>
		</div>
	</section>
</div>
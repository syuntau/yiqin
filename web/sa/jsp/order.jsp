<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var sa_conf = {
	option : '<option></option>',
	tr : '<tr></tr>',
	td : '<td></td>',
};

var sa_order = function(){
	var action = {
		load_user : function() {
			var $userList = $('.user-list');
			$.ajax({
	            type: "post",
	            url: "editBestProduct_getUserList",
	            dataType: "json",
	            success: function(data) {
		           	 if (data=='2') {
		           		$userList.html("<s:text name='msg.no.item'><s:param><s:text name='msg.param.user' /></s:param></s:text>");
		           	 } else {
		           		var $userListSelect = $userList.find('select');
						$.each(data, function(i, val) {
							var $op = $(sa_conf.option);
							$op.val(val.id).html(val.name);
							$userListSelect.append($op);
						});
						var $firstOP = $(sa_conf.option);
						$firstOP.val('all').html("请选择客户...");
						$userListSelect.prepend($firstOP);
						$userList.find('option:first').attr('selected', true);
						$('.user-select').removeClass('display-off');
		           	 }
	            },
	            beforeSend: function() {
	            	$userList.prepend($(com_conf.loading_icon));
	        	},
	            complete: function() {
	            	$userList.find('span').remove();
	            	var userId = "<s:property value='userId'/>";
	            	if(userId!=null && userId!=""){
	            		$userList.find('select').val(userId);
	            	}
	            }
			});
		},
		
		modifyBootBox : function(orderId,statusStr,userId){
			bootbox.dialog({  
                message: "<span>订单号："+orderId+"</span><span style='margin-left:50px;'>当前状态："+statusStr+"</span>"
                		+"<div style='margin-top:20px;'>请选择要修改的订单状态：<select id='mod_sel_status'><option value='1'>等待收货</option><option value='2'>等待付款</option>"	
						+"<option value='3'>已完成</option><option value='0'>已取消</option></select></div>",  
                title: "订单状态修改", 
                buttons: {  
                    Cancel: {  
                        label: "取消",  
                        className: "btn-default",  
                    }
                    , OK: {  
                        label: "更新",  
                        className: "btn-primary",  
                        callback: function () {
                        	var status = $("#mod_sel_status").val();
                        	sa_order.updateOrderStatus(orderId, status, userId);
                        }  
                    }  
                }  
            });  
		},

		modifyOrder : function(id) {
			var orderId = id+'';
			var orderTmpList = '<s:property value="#request.orderTmpList" escape="false"/>';
			var orderArrray = JSON.parse(orderTmpList);
			var order = orderArrray[orderId];
			var productList = order.productList;
			var html = '<div style="max-height: 500px;overflow-y: auto;">';
			html += '<table class="table table-condensed">' +
						'<thead>' +
							'<tr class="cart_menu">' +
								'<td class="image"></td>' +
								'<td class="description">商品</td>' +
								'<td class="price">单价(元)</td>' +
								'<td class="quantity">数量</td>' +
								'<td></td>' +
							'</tr>' +
						'</thead>' +
						'<tbody id="cart_info_list">';
			$.each(productList, function(i, val) {
				html += '<tr>' +
							'<td class="cart_product">' +
								'<a><img src="' + val.imgUrl + '" width="50px"></a>' +
							'</td>' +
							'<td class="cart_description">' +
								'<p>' + val.productName + '</p>' +
								'<p style="margin-top: 10px;">商品ID：' + val.productId + '</p>' +
								'<p>颜色：' + val.productInfo + '</p>' +
							'</td>' +
							'<td class="cart_price"><p><input style="width:100px" onblur="sa_order.blurPriceInput(this)" class="item_price item_price_' + val.productId + '" idx="' + val.productId + '" origval="' + val.zhekouPrice + '" value="' + val.zhekouPrice + '" /></p><del>' + val.price + '</del></td>' +
							'<td class="cart_quantity">' +
								'<div class="cart_quantity_button">' +
									'<input class="cart_quantity_' + val.productId + '" onblur="sa_order.blurCountInput(this)" origval="' + val.count + '" value="' + val.count + '" type="text" name="quantity" autocomplete="off" size="3" id="0_input">' +
								'</div>' +
							'</td>' +
						'</tr>';
			});
			html +=	'<tr>' +
						'<td class="cart_product">备注</td>' +
						'<td class="cart_description">' +
							'<textarea style="width:300px;height:100px;">' + order.orderNote + '</textarea>' +
						'</td>' +
						'<td colspan = "2" class="cart_price"><p><input style="width:100px" class="beizhuzongjia" value="' + order.beizhuzongjia + '" /></p></td>' +
					'</tr>' +
					'<tr>' +
						'<td colspan = "2" style="text-align:right;color:red">订单折后总价：<button type="button" onclick="sa_order.recalculatePrice()" style="color:black">重新计算</button></td>' +
						'<td colspan = "2" style="color:red" class="recalculate_price_td">' + order.zongjia + '</td>' +
					'</tr>' +
					'</tbody></table></div>';
			bootbox.dialog({
				message : html,
				title : "订单修改 <span style='font-size:14px;color:red'>(订单号：" + orderId + ")</span>",
				buttons : {
                    Cancel: {  
                        label: "取消",  
                        className: "btn-default",  
                    }
                    , OK: {  
                        label: "更新",  
                        className: "btn-primary",  
                        callback: function () {
                        	sa_order.submitModifyOrder(order);
                        }  
                    }
				}
			});
		},

		blurPriceInput : function(obj) {
			var priceObj = $(obj);
			var productId = priceObj.attr('idx');
			var price = priceObj.val();
			var origPrice = priceObj.attr('origval');
			var regu = "(^[0-9]+[\.][0-9]{1,2}$)|(^[0-9]+$)";
			var re = new RegExp(regu);
			if (!re.test(price)) {
				alert("商品价格输入有误");
				priceObj.val(origPrice);
			} else {
				priceObj.val(sa_order.toDecimal2(price));
			}
		},
		
		blurCountInput : function(obj) {
			var countObj = $(obj);
			var count = countObj.val();
			var origCount = countObj.attr('origval');
			if (isNaN(count) || count < 1) {
				alert("商品数量必须大于0");
				countObj.val(origCount);
			}
		},

		toDecimal2 : function(x) {
			var f = parseFloat(x);
			if (isNaN(f)) {
			return false;
			}
			var f = Math.round(x*100)/100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
			rs = s.length;
			s += '.';
			}
			while (s.length <= rs + 2) {
			s += '0';
			}
			return s;
		},
		
		recalculatePrice : function() {
			var totalPrice = 0;
			$('.item_price').each(function() {
				var price = $(this).val();
				var productId = $(this).attr('idx');
				var count = $('.cart_quantity_' + productId).val();
				totalPrice += price * count;
			});
			var $beizhuzongjia = $('.beizhuzongjia');
			var beizhuzongjia = $beizhuzongjia.val();
			if (beizhuzongjia && beizhuzongjia.length > 0) {
				totalPrice += beizhuzongjia * 1;
			}
			totalPrice = sa_order.toDecimal2(totalPrice);
			$('.recalculate_price_td').text(totalPrice);
		},
		
		submitModifyOrder : function(order) {
			var productList = order.productList;
			$.each(productList, function(i, val) {
				var productId = val.productId;
				var price = $('.item_price_' + productId).val();
				val.zhekouPrice = price;
				var count = $('.cart_quantity_' + productId).val();
				val.count = count;
			});
		},

		updateOrderStatus : function(orderId,status,userId){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "updateOrderStatus",
	             data : "userId="+userId+"&orderId="+orderId+"&status="+status,
	             dataType: "text",
	             success: function(data){
	            	if(data=='1'){
	            		alert("修改成功！");
	            	}else if(data=='2'){
	            		alert("订单状态修改失败！");
	            	}else if(data=='3'){
	            		alert("修改异常，请稍后再试！");
	            	}
	             },
	             beforeSend: function(){},
	             complete: function(data){
	            	 sa_order.orderSearch();
	             },
	             error: function(){}
	         });
		},
		
		deleteOrder : function(orderId){
			if (confirm("确认删除此订单吗？")) {
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteOrder",
		             data: "orderId="+orderId,
		             dataType: 'text',
		             success: function(data){
		            	if(data=='1'){
		            		sa_order.orderSearch();
		            	}else if(data=='2'){
		            		alert("订单删除失败！");
		            	}else if(data=='3'){
		            		alert("删除程序异常，请稍后再试！");
		            	}
	                },
	                beforeSend: function(){},
	                complete: function(){},
	                error: function(){}
		         });
		    }
		},
		
		initOrderCheck : function(){
			 var startTime = "<s:property value='startTime'/>";
			 $("input[name=startTime]").val(startTime);
			 
			 var endTime = "<s:property value='endTime'/>";
			 $("input[name=endTime]").val(endTime);
			 
			 var filterStatus = "<s:property value='status'/>";
			 if(filterStatus==null || filterStatus==""){
				 filterStatus = 10;
			 }
			 $("#submitStatus").val(filterStatus);
			 
			 $('#order_search_btn').on('click', function() {
				 sa_order.orderSearch();
			 });
			 
			 $('#order_export_btn').on('click', function() {
				 sa_order.exportOrderExcel();
			 });
		},
			
		orderSearch : function(){
			$("#order_search_btn").after($(com_conf.loading_icon));
			var userId= $("#user_select").val(),
				startTime = $("input[name=startTime]").val(),
				endTime = $("input[name=endTime]").val(),
				status = $("#submitStatus").val();
			window.location.href = "findSAOrderList?startTime="+startTime+"&endTime="+endTime+"&userId="+userId+"&status="+status;
		},
		
		exportOrderExcel : function(){
			<s:if test="page.results==null">
				alert("没有要导出的订单数据");
				return;
			</s:if>
			var startTime = "<s:property value='startTime'/>",
				endTime = "<s:property value='endTime'/>",
				userId = "<s:property value='userId'/>";
			window.location.href = "exportOrderExcel?startTime="+startTime+"&endTime="+endTime+"&userId="+userId;
		},
		
		orderSwitch : function(orderId,type){
			if(type=="down"){
				$("#order_up_"+orderId).show();
				$("#order_down_"+orderId).parent().parent().parent().nextAll().show();
				$("#order_down_"+orderId).hide();
			}else{
				$("#order_down_"+orderId).show();
				$("#order_up_"+orderId).parent().parent().parent().nextAll().hide();
				$("#order_up_"+orderId).hide();
			}
		}
	};
	
	var toIndexPage = function(pageIndex){
		var startTime = "<s:property value='startTime'/>",
			endTime = "<s:property value='endTime'/>",
			userId = "<s:property value='userId'/>",
			status = "<s:property value='status'/>";
		window.location.href = "findSAOrderList?startTime="+startTime+"&endTime="+endTime+"&userId="+userId+"&status="+status+"&pageIndex="+pageIndex;
	};
	
	return action;	
}();

$(document).ready(function(){
	sa_order.load_user();
	sa_order.initOrderCheck();
});
</script>
        <!-- Navigation -->

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">订单管理</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12" style="margin-bottom:20px;">
                     <form role="form" class="form-inline user-form" style="float: left;">
                        <div class="form-group user-list">
                            <label class="user-select display-off"><s:text name="sa.pd.lbl.select.user" /></label>
                            <select class="form-control user-select display-off" id="user_select"></select>
                        </div>
                        <div class="form-group" style="margin-left:10px;">
                            <label class="user-select display-off">选择时间</label>
                            <input type="text" class="form-control" name="startTime" onclick="WdatePicker()"></input>至
                            <input type="text" class="form-control" name="endTime"  onclick="WdatePicker()"></input>
                        </div>
                        <div class="form-group" style="margin-left:10px;">
	                        <label class="user-select display-off">订单状态</label>
							<select id="submitStatus" class="form-control">
								<option value="10">全部状态</option>
								<option value="1">等待收货</option>
								<option value="2">等待付款</option>
								<option value="3">已完成</option>
								<option value="0">已取消</option>
							</select>
						</div>
                        <button type="button" class="btn btn-info btn-user-submit user-select display-off" id="order_search_btn"><s:text name="sa.btn.query" /></button>
                        
                        <button type="button" style="margin-left:30px;" class="btn btn-info btn-user-submit user-select display-off" id="order_export_btn">导出Excel</button>
                    </form>
                </div>
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image" width="35%">订单信息</td>
							<td width="10%" style="text-align:center;">单价</td>
							<td width="8%" style="text-align:center;">数量</td>
							<td width="10%" style="text-align:center;">总价</td>
							<td width="18%" style="text-align:center;">订单时间</td>
							<td width="10%" style="text-align:center;">订单状态</td>
							<td width="8%" style="text-align:center;">订单处理</td>
						</tr>
					</thead>
					<s:if test="page.results==null">
						<tbody>
							<tr>
								<td colspan="7">
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
									<div class="summary">
										 <a href="javaScript:sa_order.orderSwitch('<s:property value='#order.id'/>','down');" id="order_down_<s:property value='#order.id'/>"><i class="fa fa-chevron-right"></i></a>
										 <a href="javaScript:sa_order.orderSwitch('<s:property value='#order.id'/>','up');" id="order_up_<s:property value='#order.id'/>" style="display:none;"><i class="fa fa-chevron-down"></i></a>
										 <span style="margin-left:20px">订单号：<s:property value="#order.id"/></span>
										 <span class="dropdown" style="margin-left:50px;">
											<a id="dropdownMenu<s:property value='#order.id'/>" data-toggle="dropdown" style="cursor:pointer;">订单详细<i class="fa fa-table fa-fw"></i></a>
											<ul class="dropdown-menu" aria-labelledby="dropdownMenu<s:property value='#order.id'/>">
											    <li><a><span style="font-weight:bold;">订单号：</span><s:property value="#order.id"/></a></li>
											    <li><a><span style="font-weight:bold;">收货人：</span><s:property value="#order.name"/></a></li>
											    <li><a><span style="font-weight:bold;">联系电话：</span><s:property value="#order.mobile"/></a></li>
											    <li><a><span style="font-weight:bold;">收货地址：</span><s:property value="#order.address"/></a></li>
											    <li><a><span style="font-weight:bold;">发票类型：</span><s:property value="#order.fapiaolx"/></a></li>
											    <li><a><span style="font-weight:bold;">发票抬头：</span><s:property value="#order.fapiaotaitou"/></a></li>
											    <li><a><span style="font-weight:bold;">发票明细：</span><s:property value="#order.fapiaomingxi"/></a></li>
											    <li><a><span style="font-weight:bold;">订单备注：</span><s:property value="#order.orderNote"/></a></li>
											</ul>
										 </span>
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
									<a class="cursor-pointer" style="color:#337ab7" onclick="sa_order.modifyBootBox(<s:property value='#order.id'/>,'<s:property value='#order.status'/>','<s:property value='#order.userId'/>');">
									  <s:property value='#order.status'/>
									</a>
								</td>
								<td style="text-align:center;">
									<span onclick="sa_order.modifyOrder(<s:property value='#order.id'/>);">
										<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="修改订单"></i>
									</span>
									<span onclick="sa_order.deleteOrder(<s:property value='#order.id'/>);">
										<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="删除订单"></i>
									</span>
								</td>
							</tr>
							<s:iterator value="#order.productList" var="product" status="st">
								 <tr style="display:none;">
									<td class="cart_product">
										<a><img src="<s:property value="#product.imgUrl"/>" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/></a>
										<h4><a><s:property value="#product.productName"/></a></h4>
										<p><s:text name="shop.product.details.productId"/><s:property value="#product.productId"/></p>
										<p><s:text name="cart.item.product.color"/><s:property value="#product.productInfo"/></p>
									</td>
									<td class="cart_price" style="text-align:center;">
										<p><s:property value="#product.zhekouPrice"/></p>
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
												document.write((<s:property value="#product.zhekouPrice"/>*<s:property value="#product.count"/>).toFixed(2));
											</script>
										</p>
									</td>
									<td colspan="3"></td>
								</tr>
							</s:iterator>
						</tbody>
					</s:iterator>
				</s:else>
			</table>
        </div>
    </div>
        <!-- /#page-wrapper -->

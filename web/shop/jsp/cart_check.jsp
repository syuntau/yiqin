<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_check_temp = {
	check_li : '<li></li>',
	check_input_radio :'<input type="radio"/>',
	check_label : '<label></label>',
	check_strong : '<strong></strong>',
	check_span : '<span></span>',
	check_shou : '&nbsp;&nbsp;&nbsp;&nbsp;收',
	check_defType : '&nbsp;&nbsp;&nbsp;&nbsp;默认地址',
	saveOrUpdate : "",
	
	add_address_btn : '<button class="btn btn-default update" id="address_add" data-toggle="modal" data-target="#EditReceiveInfo">添加</button>',
	upt_address_btn : '<button class="btn btn-default update" id="address_update" data-toggle="modal" data-target="#EditReceiveInfo">修改</button>',
	del_address_btn : '<button class="btn btn-default update" id="address_delete">删除</button>',
	
	check_from : '<form method="post" action="submitOrder"></form>',
	check_input_hidden : '<input type="hidden"/>',
	
	fp_li : '<li style="margin-bottom:10px;"></li>',
	fp_table : '<table></table>',
	fp_tr : '<tr></tr>',
	fp_td : '<td></td>',
	fp_input : '<input type="radio" name="fapiao_info"></input>',
	
	add_invoice_btn : '<button class="btn btn-default update" id="invoice_add" data-toggle="modal" data-target="#EditInvoiceInfo">添加</button>',
	upt_invoice_btn : '<button class="btn btn-default update" id="invoice_update" data-toggle="modal" data-target="#EditInvoiceInfo">修改</button>',
	del_invoice_btn : '<button class="btn btn-default update" id="invoice_delete">删除</button>',
};

var yiqin_cart_check = function(){
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	
	var action = {
		submitOrder : function(){
			if(confirm("请确认订单信息是否正确，是否确认提交订单？")){
				var addressAttr = $("input:checked[name=accept_info]"),
					zhifu = 1,//$("input:checked[name=zhi_fu]").val(),
					peisong = 1,//$("input:checked[name=pei_song]").val(), 
					fapiaoAttr = $("input:checked[name=fapiao_info]"),
					ordernote = $("textarea[name=ordernote]").val().replace(REX, ""),
					productIds = "<s:property value='#request.submit_ProductIds'/>",
					$check_from = $(cart_check_temp.check_from);
					
				if(addressAttr==""||addressAttr.length==0){
					alert("您还没有配送地址信息，请先添加！");
					return;
				}
				addressAttr = addressAttr.val();
				
				if(fapiaoAttr==""||fapiaoAttr.length==0){
					alert("您还没有发票信息，请先添加！");
					return;
				}
				fapiaoAttr = fapiaoAttr.val();
					
				var $check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','addressAttr').val(addressAttr));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','zhifu').val(zhifu));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','peisong').val(peisong));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','invoiceAttr').val(fapiaoAttr));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','ordernote').val(ordernote));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','productIds').val(productIds));
					$(document.body).append($check_from);
					$check_from.submit();
			}
		},
			
		findUserAddress : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf",
	             dataType: "json",
	             success: function(data){
	            	appendToAddress(data);
                 },
                 beforeSend: function(){},
                 complete: function(){},
                 error: function(){}
	         });
		},
		
		findUserInvoice : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserInvoice",
	             dataType: "json",
	             success: function(data){
	            	appendToInvoice(data);
                 },
                 beforeSend: function(){},
                 complete: function(){},
                 error: function(){}
	         });
		},
		
		editAddressByAttr : function(attribute){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf",
	             data : "attribute="+attribute,
	             dataType: "json",
	             success: function(data){
	            	var $modal = $("#EditReceiveInfo");
	            	if(data=='1'){
	            		alert("本条地址信息已被删除，请刷新页面！");
	            	}else if(data=='2'){
	            		alert("加载地址信息失败，请稍后再试！");
	            	}else{
	            		var userName = data.value.split("_receive_")[0],
	       					telephone = data.value.split("_receive_")[1],
	       					address = data.value.split("_receive_")[2];
	            		$modal.find("input[name=receive_name]").val(userName);
	            		$modal.find("input[name=receive_tel]").val(telephone);
	            		$modal.find("input[name=receive_area]").val(address);
	            		$modal.data("receive_aType",data.attribute);
	            		if(data.attribute=="address_def"){
	            			$modal.find("input[name=receive_default]").prop("checked",true);
	            		}
	            	}
	             },
	             beforeSend: function(){},
	             complete: function(){},
	             error: function(){}
	         });
		},
		
		editInvoiceByAttr : function(attribute){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf",
	             data : "attribute="+attribute,
	             dataType: "json",
	             success: function(data){
	            	var $modal = $("#EditInvoiceInfo");
	            	if(data=='1'){
	            		alert("本条发票信息已被删除，请刷新页面！");
	            	}else if(data=='2'){
	            		alert("加载发票信息失败，请稍后再试！");
	            	}else{
	            		var fapiaolx = data.value.split("_invoice_")[0],
	       					fapiaott = data.value.split("_invoice_")[1],
	       					fapiaomx = data.value.split("_invoice_")[2];
	            		$modal.find("input[name=fapiao_lx][value='"+fapiaolx+"']").prop("checked",true);
	            		$modal.find("input[name=fapiaotaitou]").val(fapiaott);
	            		$modal.find("input[name=fapiaomingxi]").val(fapiaomx);
	            		$modal.find("input[name=fapiao_attr]").val(data.attribute);
	            	}
	             },
	             beforeSend: function(){},
	             complete: function(){},
	             error: function(){}
	         });
		},
		
		saveOrUpdateAddress : function(){
			var dataParm = checkReceiveAddress();
			if(!dataParm){
				return;
			}
			dataParm = dataParm + "&saveOrUpdate="+cart_check_temp.saveOrUpdate;
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "saveOrUpdateAddress",
	             data : dataParm,
	             dataType: "json",
	             success: function(data){
	            	 if(data=='1'){
	            		 $("#receive_error").html("信息填写不正确，请再次填写！");
	            		 return;
	            	 }else if(data=='2'){
	            		 $("#receive_error").html("保存失败，请稍后重试！");
	            		 return;
	            	 }else if(data=='3'){
	            		 yiqin_cart_check.findUserAddress();
	            		 return;
	            	 }else if(data=='4'){
	            		 $("#receive_error").html("地址数量超出最大数量10个");
	            		 return;
	            	 }
	             },
	             beforeSend: function(){
	            	 $("#receive_error").html("");
	             },
	             complete: function(){
	            	 $('#EditReceiveInfo').modal('hide');
	             },
	             error: function(){}
	         });
		},
		
		saveOrUpdateInvoice : function(){
			var dataParm = checkInvoiceInfo();
			if(!dataParm){
				return;
			}
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "saveOrUpdateInvoice",
	             data : dataParm,
	             dataType: "json",
	             success: function(data){
	            	 if(data=='1'){
	            		 $("#invoice_error").html("信息填写不正确，请再次填写！");
	            		 return;
	            	 }else if(data=='2'){
	            		 $("#invoice_error").html("保存失败，请稍后重试！");
	            		 return;
	            	 }else if(data=='3'){
	            		 yiqin_cart_check.findUserInvoice();
	            		 return;
	            	 }else if(data=='4'){
	            		 $("#invoice_error").html("发票信息数量超出最大数量5个");
	            		 return;
	            	 }
	             },
	             beforeSend: function(){
	            	 $("#invoice_error").html("");
	             },
	             complete: function(){
	            	 $('#EditInvoiceInfo').modal('hide');
	             },
	             error: function(){}
	         });
		},
		
		deleteUserAddress : function(attribute){
			if(confirm("确认要删除选择的地址信息吗？")){
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteUserConf",
		             data : "attribute="+attribute,
		             dataType: "json",
		             success: function(data){
		            	if(data=='1'){
		            		alert("本条收货信息不存在，请再次确认！");
		            	}else if(data=='2'){
		            		alert("删除失败，请稍后再试！");
		            	}else if(data=='3'){
		            		yiqin_cart_check.findUserAddress();
		            	}
		             },
		             beforeSend: function(){},
		             complete: function(){},
		             error: function(){}
		         });
			}
		},
		
		deleteUserInvoice : function(attribute){
			if(confirm("确认要删除选择的发票信息吗？")){
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteUserConf",
		             data : "attribute="+attribute,
		             dataType: "json",
		             success: function(data){
		            	if(data=='1'){
		            		alert("本条发票信息不存在，请再次确认！");
		            	}else if(data=='2'){
		            		alert("删除失败，请稍后再试！");
		            	}else if(data=='3'){
		            		yiqin_cart_check.findUserInvoice();
		            	}
		             },
		             beforeSend: function(){},
		             complete: function(){},
		             error: function(){}
		         });
			}
		},
		
		totalSelCartInfo : function(){
			var selCart = "<s:property value='#request.settlement_products'/>";
			if(selCart != null && selCart != ""){
				var $totalli = $("#total_area_price"),
					totalPrice = 0;
				$totalli.empty();
				
				<s:iterator value="#request.settlement_products">
    				var	toPrice = "<s:property value='count'/>"*"<s:property value='zhekouPrice'/>";
					totalPrice += toPrice;
				</s:iterator>
				
				$totalli.append("订单总价：").append(totalPrice.toFixed(2)+" 元");
			}
		},
	};
	
	var appendToInvoice = function(data){
		var $user_fp_info = $('<ul class="user_option" id="fapiao_info_ul"></ul>'),
			$invoice_info_div = $("#invoice_info_div"),
			$fp_li = $(cart_check_temp.fp_li),
			$add_invoice_btn = $(cart_check_temp.add_invoice_btn),
			$upt_invoice_btn = $(cart_check_temp.upt_invoice_btn),
			$del_invoice_btn = $(cart_check_temp.del_invoice_btn);
			$invoice_info_div.empty();
			
		$invoice_info_div.append($user_fp_info);
		if(data == '1'){
			$user_fp_info.append($fp_li);
			$fp_li.append("您还没有发票信息，请点击添加按钮添加");
			$invoice_info_div.append($add_invoice_btn);
			$add_invoice_btn.click(function(){
				emptyInvoiceModal();
			});
       	}else if(data == '2'){
       		$user_fp_info.append($fp_li);
       		$fp_li.append("发票信息加载失败，请刷新页面再试");
       	}else{
       		if(data.length<5){
       			$invoice_info_div.append($add_invoice_btn);
    			$add_invoice_btn.click(function(){
    				emptyInvoiceModal();
    			});
       		}
       		$invoice_info_div.append($upt_invoice_btn);
       		$invoice_info_div.append($del_invoice_btn);
       		$upt_invoice_btn.click(function(){
       			emptyInvoiceModal();
				var type = $("input:checked[name=fapiao_info]").val();
				yiqin_cart_check.editInvoiceByAttr(type);
			});
       		$del_invoice_btn.click(function(){
				var type = $("input:checked[name=fapiao_info]").val();
				yiqin_cart_check.deleteUserInvoice(type);
			});
       		$.each(data, function(n,val){
       			var $fp_li = $(cart_check_temp.fp_li),
	    			$fp_table = $(cart_check_temp.fp_table),
	    			$fp_tr = $(cart_check_temp.fp_tr),
	    			$fp_td = $(cart_check_temp.fp_td),
	    			$fp_input = $(cart_check_temp.fp_input),
       				fplx = val.value.split("_invoice_")[0],
       				fptt = val.value.split("_invoice_")[1],
       				fpxm = val.value.split("_invoice_")[2];
       			
       			$fp_li.attr('id',val.attribute).append($fp_table).css("margin-bottom","10px");
       			$fp_table.append($fp_tr.append($fp_td.attr("width","30px").append($fp_input.val(val.attribute))));
       			$fp_td = $(cart_check_temp.fp_td);
       			$fp_tr.append($fp_td.append("发票类型：").append(faPiaoLx(fplx)));
       			
       			$fp_tr = $(cart_check_temp.fp_tr);
       			$fp_td = $(cart_check_temp.fp_td);
       			$fp_table.append($fp_tr.append($fp_td.append("&nbsp;")));
       			$fp_td = $(cart_check_temp.fp_td);
       			$fp_tr.append($fp_td.append("发票抬头：").append(fptt));
       			
       			$fp_tr = $(cart_check_temp.fp_tr);
       			$fp_td = $(cart_check_temp.fp_td);
       			$fp_table.append($fp_tr.append($fp_td.append("&nbsp;")));
       			$fp_td = $(cart_check_temp.fp_td);
       			$fp_tr.append($fp_td.append("开票项目：").append(fpxm));
       			
   				if(n==0){
   					$fp_input.attr("checked",true);
   				}
   				$user_fp_info.append($fp_li);
       		});
       	}
	};
	
	var appendToAddress = function(data){
		var $user_acc_info = $('<ul class="user_option" id="user_acc_info"></ul>'),
			$acc_info_div = $("#acc_info_div"),
			$check_li = $(cart_check_temp.check_li),
			$add_address_btn = $(cart_check_temp.add_address_btn),
			$upt_address_btn = $(cart_check_temp.upt_address_btn),
			$del_address_btn = $(cart_check_temp.del_address_btn);
			$acc_info_div.empty();
			
			$acc_info_div.append($user_acc_info);
		if(data == '1'){
			$user_acc_info.append($check_li);
			$check_li.append("您还没有配送地址信息，请点击添加按钮添加");
			$acc_info_div.append($add_address_btn);
			$add_address_btn.click(function(){
				cart_check_temp.saveOrUpdate = "save";
				emptyReceiveModal();
			});
       	}else if(data == '2'){
       		$user_acc_info.append($check_li);
			$check_li.append("配送地址信息加载失败，请刷新页面再试");
       	}else{
       		if(data.length<10){
       			$acc_info_div.append($add_address_btn);
    			$add_address_btn.click(function(){
    				cart_check_temp.saveOrUpdate = "save";
    				emptyReceiveModal();
    			});
       		}
       		$acc_info_div.append($upt_address_btn);
       		$acc_info_div.append($del_address_btn);
       		$upt_address_btn.click(function(){
				cart_check_temp.saveOrUpdate = "update";
				emptyReceiveModal();
				var type = $("input:checked[name=accept_info]").val();
				yiqin_cart_check.editAddressByAttr(type);
			});
       		$del_address_btn.click(function(){
				var type = $("input:checked[name=accept_info]").val();
				yiqin_cart_check.deleteUserAddress(type);
			});
			var isDef = false;
			$.each(data, function(i,tempVal){
				if(tempVal.attribute=="address_def"){
					isDef = true;
				}
			});
       		$.each(data, function(n,val){
       			var $check_li = $(cart_check_temp.check_li),
       				$check_input_radio = $(cart_check_temp.check_input_radio),
       				$check_label = $(cart_check_temp.check_label),
       				$check_strong = $(cart_check_temp.check_strong),
       				$check_span = $(cart_check_temp.check_span),
       				check_shou = cart_check_temp.check_shou,
       				check_defType = cart_check_temp.check_defType,
       				userName = val.value.split("_receive_")[0],
       				telephone = val.value.split("_receive_")[1],
       				address = val.value.split("_receive_")[2];
       			
       			$check_li.attr('id',val.attribute).append($check_input_radio);
       			$check_input_radio.attr('name','accept_info').val(val.attribute);
       			if(isDef){
       				if(val.attribute=="address_def"){
       					$check_input_radio.attr("checked",true);
       				}
       			}else{
       				if(n==0){
       					$check_input_radio.attr("checked",true);
       				}
       			}
       			$check_li.append($check_label.append($check_strong).append(check_shou).append($check_span));
       			$check_strong.append(userName).attr('title',userName);
       			$check_span.append(telephone).css("margin-left","50px");
       			
       			$check_span = $(cart_check_temp.check_span);
       			$check_label.append($check_span);
       			$check_span.append(address).css("margin-left","100px");
       			if(val.attribute=="address_def"){
       				$check_span.append(check_defType);
       			}
       			
       			$user_acc_info.append($check_li);
       		});
       	}
	};
	
	var checkReceiveAddress = function(){
		var $modal = $("#EditReceiveInfo"),
			userName = $modal.find("input[name=receive_name]").val().replace(REX, ""),
			telephone = $modal.find("input[name=receive_tel]").val().replace(REX, ""),
			address = $modal.find("input[name=receive_area]").val().replace(REX, ""),
			defObj = $modal.find("input[name=receive_default]"),
			oldAttribute = $modal.data("receive_aType"),
			attribute = "";
		if(userName==""){
			$modal.find("input[name=receive_name]").focus();
			$("#receive_error").html("收货人员必须填写");
			return false;
		}
		if(telephone==""){
			$modal.find("input[name=receive_tel]").focus();
			$("#receive_error").html("联系电话必须填写");
			return false;
		}
		if(address==""){
			$modal.find("input[name=receive_area]").focus();
			$("#receive_error").html("收货地址必须填写");
			return false;
		}
		if(!checkShouHuoUser(userName)){
			$modal.find("input[name=receive_name]").focus();
			$("#receive_error").html("收货人员不能包含特殊字符");
			return false;
		}
		if (!checkPhone(telephone)) {
			$modal.find("input[name=receive_tel]").focus();
			$("#receive_error").html("联系电话格式错误，请重新输入");
			return false;
		}
		if(defObj.is(':checked')){
			attribute = "address_def";
		}
		return "attribute="+attribute+"&address="+address+"&telephone="+telephone+"&userName="+userName+"&oldAttribute="+oldAttribute;
	};
	
	var checkInvoiceInfo = function(){
		var $modal = $("#EditInvoiceInfo"),
			fapiaolx = $("input:checked[name=fapiao_lx]"),
			fapiaotaitou = $("input[name=fapiaotaitou]").val().replace(REX, ""),
			fapiaomingxi = $("input[name=fapiaomingxi]").val().replace(REX, ""),
			attribute = $("input[name=fapiao_attr]").val();
		if(fapiaolx==""||fapiaolx.length==0){
			$("#invoice_error").html("请选择发票类型");
			return false;
		}
		fapiaolx = fapiaolx.val();
		if(fapiaomingxi==""){
			$modal.find("input[name=fapiaomingxi]").focus();
			$("#invoice_error").html("请填写开票项目信息");
			return false;
		}
		return "attribute="+attribute+"&fapiaolx="+fapiaolx+"&fapiaotaitou="+fapiaotaitou+"&fapiaomingxi="+fapiaomingxi;
	};
	
	var faPiaoLx = function(fplx){
		var fapiaolx = "普通发票";
		if ("1"==fplx) {
			fapiaolx = "普通发票";
		} else if ("2"==fplx) {
			fapiaolx = "专用发票";
		}
		return fapiaolx;
	};
	
	var checkPhone = function(value) {
		var REX_PHONE = /^[1][0-9]{10}$/;
		var REX_DIANH = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		if (REX_PHONE.test(value) || REX_DIANH.test(value)) {
			return true;
		}
		return false;
	};
	
	var checkShouHuoUser = function(value){
		var NAMEEXP = /^[a-zA-Z\u4e00-\u9fa5- ]*$/;
		if (NAMEEXP.test(value)) {
			return true;
		}
		return false;
	};
	
	var emptyReceiveModal = function(){
		var $modal = $("#EditReceiveInfo");
		$("#receive_error").html("");
		$modal.find("input[name=receive_name]").val("");
		$modal.find("input[name=receive_tel]").val("");
		$modal.find("input[name=receive_area]").val("");
		$modal.find("input[name=receive_default]").prop("checked",false);
		$modal.data("receive_aType","");
	};
	
	var emptyInvoiceModal = function(){
		var $modal = $("#EditInvoiceInfo");
		$("#invoice_error").html("");
		$modal.find("input[name=fapiao_lx]").prop("checked",false);
		$modal.find("input[name=fapiaotaitou]").val("");
		$modal.find("input[name=fapiaomingxi]").val("");
		$modal.find("input[name=fapiao_attr]").val("");
	};

	return action;
}();

$(document).ready(function(){
	yiqin_cart_check.findUserAddress();
	yiqin_cart_check.findUserInvoice();
	yiqin_cart_check.totalSelCartInfo();
});
</script>

<section id="do_action">
	<div class="container">
		<div class="heading">
			<h3><b><s:text name="cart.check.title"></s:text></b></h3>
		</div>
		<div class="row">
			<div class="col-sm-100">
				<h5><b><s:text name="cart.check.receiver.information"></s:text></b></h5>
				<div class="chose_area" style="margin-bottom:10px;" id="acc_info_div">
<!-- 					<ul class="user_option" id="user_acc_info"></ul> -->
<!-- 					<button style="display:none" class="btn btn-default update" id="address_add" data-toggle="modal" data-target="#EditReceiveInfo">添加</button> -->
<!-- 					<button style="display:none" class="btn btn-default update" id="address_update" data-toggle="modal" data-target="#EditReceiveInfo">修改</button> -->
<!-- 					<button style="display:none" class="btn btn-default update" id="address_delete">删除</button> -->
				</div>
			</div>
		</div>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-6"> -->
<%-- 				<h5><b><s:text name="cart.check.zhifu"></s:text></b></h5> --%>
<!-- 				<div class="chose_area"> -->
<!-- 					<ul class="user_option"> -->
<!-- 						<li> -->
<!-- 							<input type="radio" name="zhi_fu" value="1" checked="checked"> -->
<!-- 							<label>货到付款</label> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<input type="radio" name="zhi_fu" value="2"> -->
<!-- 							<label>公司转账</label> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<input type="radio" name="zhi_fu" value="3"> -->
<!-- 							<label>邮局汇款</label> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="col-sm-6"> -->
<%-- 				<h5><b><s:text name="cart.check.peisong"></s:text></b></h5> --%>
<!-- 				<div class="chose_area"> -->
<!-- 					<ul class="user_option"> -->
<!-- 						<li> -->
<!-- 							<input type="radio" name="pei_song" value="1" checked="checked"> -->
<!-- 							<label>依勤送货</label> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<input type="radio" name="pei_song" value="2"> -->
<!-- 							<label>上门自提</label> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="row">
			<div class="col-sm-6" style="padding-left:0px;">
				<h5><b><s:text name="cart.check.fapiao"></s:text></b></h5>
				<div class="chose_area" style="margin-bottom:10px;height:155px;overflow-y:auto;" id="invoice_info_div">
				</div>
			</div>
			<div class="col-sm-6" style="padding-right:0px;padding-left:0px;">
				<h5><b><s:text name="cart.check.ordernote"></s:text></b></h5>
				<div class="chose_area" style="margin-bottom:10px;">
					<ul class="user_option">
						<li>
							<label>备注内容：</label>
							<textarea style="width:300px;height:83px;" name="ordernote"></textarea>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-100">
				<h5><b><s:text name="cart.check.product.list"></s:text></b></h5>
				<div class="total_area" style="margin-bottom:10px;padding:0px;">
					<div class="container">
						<div class="table-responsive cart_info">
							<table class="table table-condensed">
								<thead style="background-color:#FE980F;font-weight:bold;">
									<tr class="cart_menu">
										<td></td>
										<td><s:text name="cart.item.product"/></td>
										<td><s:text name="cart.item.price"/></td>
										<td><s:text name="cart.item.quantity"/></td>
										<td><s:text name="cart.item.total"/></td>
									</tr>
								</thead>
								<tbody>
									<s:if test="#request.settlement_products==null">
										没有要提交的购物清单信息								
									</s:if>
									<s:else>
										<s:iterator value="#request.settlement_products">
											<tr>
												<td class="cart_product"><img src="<s:property value='imgUrl'/>" width="110px"></td>
												<td class="cart_description" style="width:457px">
													<h4><s:property value='productName'/></h4>
													<p style="margin-top:10px">商品ID：<s:property value='productId'/></p>
													<p>颜色：<s:property value='productInfo'/></p>
												</td>
												<td class="cart_price">
													<p><s:property value="zhekouPrice"/></p>
													<del><s:property value='price'/></del>
												</td>
												<td class="cart_quantity">
													<p><s:property value='count'/></p>
												</td>
												<td class="cart_total">
													<p>
														<script type="text/javascript">
															document.write((<s:property value="zhekouPrice"/>*<s:property value="count"/>).toFixed(2));
														</script>
													</p>
												</td>
											</tr>
										</s:iterator>
									</s:else>
								</tbody>
							</table>
							<ul style="font-weight:bold;">
								<li style="float:right;" id="total_area_price"></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<a style="float:right;margin-right:50px;" class="btn btn-default check_out" href="javaScript:yiqin_cart_check.submitOrder();">提交订单</a>
		</div>
	</div>
	
	<!-- alert modal -->
	<div class="modal" id="EditReceiveInfo" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
		             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		             <h5 class="modal-title"><strong>填写收货信息</strong></h5>
	         	</div>
	         	<div class="modal-body">
					<ul>
						<li>
							<label>收货人员：</label>
							<input type="text" name="receive_name">
						</li>
						<li>
							<label>联系电话：</label>
							<input type="text" name="receive_tel">
						</li>
						<li>
							<label>收货地址：</label>
							<input type="text" name="receive_area" style="width:300px;">
						</li>
						<li>
							<label>是否设置为默认地址 </label>
							<input type="checkbox" name="receive_default" value="address_def">
						</li>
						<li>
							<span style="color:red" id="receive_error"></span>
						</li>
					</ul>
	         	</div>
	         	<div class="modal-footer">
					<a class="btn btn-default check_out" href="javaScript:yiqin_cart_check.saveOrUpdateAddress();">保存</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- alert modal -->
	<div class="modal" id="EditInvoiceInfo" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
		             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		             <h5 class="modal-title"><strong>填写发票信息</strong></h5>
	         	</div>
	         	<div class="modal-body">
	         		<input type="hidden" name="fapiao_attr">
					<ul>
						<li>
							<label>发票类型：</label>
							<input type="radio" name="fapiao_lx" value="1">&nbsp;&nbsp;普通发票
							<input type="radio" name="fapiao_lx" value="2">&nbsp;&nbsp;专用发票
						</li>
						<li>
							<label>发票抬头：</label>
							<input type="text" name="fapiaotaitou" style="width:300px;">
						</li>
						<li>
							<label>开票项目：</label>
							<input type="text" name="fapiaomingxi" style="width:300px;">
						</li>
						<li>
							<span style="color:red" id="invoice_error"></span>
						</li>
					</ul>
	         	</div>
	         	<div class="modal-footer">
					<a class="btn btn-default check_out" href="javaScript:yiqin_cart_check.saveOrUpdateInvoice();">保存</a>
				</div>
			</div>
		</div>
	</div>
</section>
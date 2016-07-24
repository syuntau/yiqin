<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var sa_reg_code = function(){
	var action = {
		load_regCode : function() {
			var $codeList = $('#reg_code_list_tb');
			$.ajax({
	            type: "post",
	            url: "findRegCode",
	            dataType: "json",
	            success: function(data) {
	            	 $codeList.empty();
		           	 if (data=='2') {
		           		$codeList.append('<tr><td class="cart_product" colspan="4">加载信息失败，稍后再试</td></tr>');
		           	 } if(data=='1'){
		           		$codeList.append('<tr><td class="cart_product" colspan="4">暂无邀请码信息</td></tr>');
		           	 }else {
						$.each(data, function(i, val) {
							var thtml = '<tr><td class="cart_product"><p>'+val.registerCode+'</p></td>'+
								'<td class="cart_price" style="text-align:center;"><p>'+val.name+'</p></td>'+
								'<td style="text-align:center;"><p>'+val.zhekou+'</p></td>'+
								'<td style="text-align:center;"><i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="删除邀请码" onclick="sa_reg_code.deleteRegCode(\''+val.registerCode+'\');"></i></td></tr>';
							$codeList.append(thtml);
						});
		           	 }
	            },
	            beforeSend: function() {
	            	$codeList.prepend($(com_conf.loading_icon));
	        	},
	            complete: function() {
	            	$codeList.find('span').remove();
	            }
			});
		},
		
		generateRegCode : function() {
			var name = $('input[name=name]').val(),
				zhekou = $('input[name=zhekou]').val();
			if(name=='' || zhekou==''){
				alert("输入项不能为空");
				return;
			}
			var regu = "(^[0-9]+[\.][0-9]{1,2}$)|(^[0-9]+$)";
			var re = new RegExp(regu);
			if (!re.test(zhekou)) {
				alert("商品折扣输入有误");
				return;
			} 
			var REX_NAME = /^([a-zA-Z0-9]{1}[a-zA-Z0-9_]{3,16})$/;
			if (name.length > 0 && !REX_NAME.test(name)) {
				alert("别名为4到16位字母数字或下划线组成");
				return;
			}
			$.ajax({
	             type: "POST",
	             url: "generateRegCode",
	             data : "name="+name+"&zhekou="+zhekou,
	             success: function(data){
	            	if(data=='1'){
	            		sa_reg_code.load_regCode();
	            	}else if(data=='2'){
	            		alert("必填项不能为空！");
	            	}else if(data=='3'){
	            		alert("生成邀请码异常，请稍后再试！");
	            	}
	             },
	             beforeSend: function(){
	            	 $('#reg_code_btn').attr('disabled','disabled');
	             },
	             complete: function(data){
	            	 $('#reg_code_btn').removeAttr('disabled','disabled');
	             },
	             error: function(){}
	         });
		},
		
		deleteRegCode : function(code){
			if (confirm("确认删除此邀请码？")) {
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteRegCode",
		             data: "regCode="+code,
		             dataType: 'text',
		             success: function(data){
		            	if(data=='1'){
		            		sa_reg_code.load_regCode();
		            	}else if(data=='2'){
		            		alert("邀请码删除失败！");
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
	};
	return action;	
}();

$(document).ready(function(){
	sa_reg_code.load_regCode();
	$('#reg_code_btn').click(function(){
		sa_reg_code.generateRegCode();
	});
});
</script>
     <!-- Page Content -->
     <div id="page-wrapper">
         <div class="row">
             <div class="col-lg-12">
                 <h3 class="page-header">生成邀请码</h3>
             </div>
             <!-- /.col-lg-12 -->
         </div>
         <!-- /.row -->
         <div class="row">
             <div class="col-lg-12" style="margin-bottom:20px;">
                  <form class="form-inline" style="float: left;">
                     <div class="form-group" style="margin-left:10px;">
                         <label class="user-select">别名：</label>
                         <input type="text" class="form-control" name="name"></input>
                     </div>
                     <div class="form-group" style="margin-left:10px;">
                      <label class="user-select">折扣：</label>
                      <input type="text" class="form-control" name="zhekou"></input>
					</div>
                     <button type="button" class="btn btn-info btn-user-submit user-select" id="reg_code_btn">生成邀请码</button>
                 </form>
             </div>
			<table class="table table-condensed">
				<thead>
					<tr class="cart_menu">
						<td width="25%">注册邀请码</td>
						<td width="25%" style="text-align:center;">别名</td>
						<td width="25%" style="text-align:center;">折扣</td>
						<td width="8%" style="text-align:center;">邀请码操作</td>
					</tr>
				</thead>
				<tbody id="reg_code_list_tb"></tbody>
		</table>
     </div>
 </div>
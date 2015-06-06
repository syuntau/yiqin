<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var customer_manage = {
	conf : {
		option : '<option></option>',
	},
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
						var $op = $(customer_manage.conf.option);
						$op.val(val.id).html(val.name);
						$userListSelect.append($op);
					});
					$userList.find('option:first').attr('selected', true);
					$('.user-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	$userList.prepend($(com_conf.loading_icon));
        	},
            complete: function() {
            	$userList.find('span').remove();
            }
		});
	},
	addZheKou : function() {
		$('.btn-customer-zhekou').on('click', function() {
			var userId = $('.user-list select').find('option:selected').val();
			var zheKou = $('.customer-zhekou').val();
			$.ajax({
	        	type: "post",
				url: "editCustomer_addZheKou",
				data: 'zheKou='+zheKou+ '&userId=' + userId,
				dataType: "json",
				success: function(data) {
					if (data=='1') {
						alert("<s:text name='msg.err.param'></s:text>");
					} else if (data=='3') {
						alert("<s:text name='msg.err.db'></s:text>");
					} else {
						alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
					}
	            },
	            beforeSend: function() {
		            var $loadingTextIcon = $(com_conf.loading_text_icon);
		            $('.zhekou-form').append($loadingTextIcon);
	        	},
	            complete: function() {
	            	$('.zhekou-form').find('span').remove();
	            }
			});
		});
	},
	loadZheKou : function() {
		var userId = $('.user-list select').find('option:selected').val();
		$.ajax({
        	type: "post",
			url: "editCustomer_getZK",
			data: 'userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='2') {
					$('.customer-zhekou').val(1);
				} else {
					$('.customer-zhekou').val(data.value);
				}
            },
            beforeSend: function() {
	            var $loadingTextIcon = $(com_conf.loading_text_icon);
		        $('.zhekou-form').append($loadingTextIcon);
        	},
            complete: function() {
            	$('.zhekou-form').find('span').remove();
            }
		});
	},
	check_customer : function() {
		$('.btn-user-submit').on('click', function() {
			$('.customer-hr').removeClass('display-off');
			$('.customer-section').removeClass('display-off');
			customer_manage.loadZheKou();
		});
	}
};

$(document).ready(function() {
	customer_manage.load_user();
	customer_manage.addZheKou();
	customer_manage.check_customer();
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.header.customer.manage" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12" >
                    <form role="form" class="form-inline user-form" >
                        <div class="form-group user-list">
                            <label class="user-select display-off"><s:text name="sa.pd.lbl.select.user" /></label>
                            <select class="form-control user-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-user-submit user-select display-off"><s:text name="sa.btn.query" /></button>
                    </form>
		            <hr class="customer-hr display-off">
		            <div class="row customer-section display-off">
                		<s:if test="%{#roles.indexOf('12301')>-1}">
		                <div class="col-lg-12" style="margin: 10px 0px;">
		                    <form role="form" class="form-inline zhekou-form" >
		                        <div class="form-group">
	                                <label><s:text name="sa.customer.zhekou" /></label>
	                                <input type="text" class="form-control customer-zhekou" maxlength="5" width="100px" name="zhekou">
		                        </div>
		                        
		                        <button type="button" class="btn btn-info btn-customer-zhekou"><s:text name="sa.btn.save" /></button>
		                    </form>
		                </div>
		                </s:if>
		                <!-- /.col-lg-12 -->
		            </div>
		            <!-- /.row -->
                </div>
            </div>
            <!-- /.row -->
            
        </div>
        <!-- /#page-wrapper -->

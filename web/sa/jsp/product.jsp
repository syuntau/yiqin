<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var pro_att = {
	conf : {
		option : '<option></option>'
	},
	initCategory : function() {
		pro_att.loadFirstCategory();
	},
	loadFirstCategory : function() {
		$.ajax({
            type: "post",
            url: "getCategoryList_getFirst",
            dataType: "json",
            success: function(data) {alert(data);
	           	 if (data=='1') {
	           		$('.category-form').html("暂无产品分类信息！");
	           	 } else {
					$.each(data, function(i, val) {
						var $op = $(pro_att.conf.option);
						$op.val(val.id).html(val.name);
						$('.category-list').append($op);
					});
					$('.category-list').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
        		$('.category-form').prepend(com_conf.loading_icon);
        	},
            complete: function() {
        		$('.category-form .icon-spinner').parent().remove();
            }
		});
	},
	
}
$(document).ready(function() {
	pro_att.initCategory();
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.product.att.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <form role="form category-form" class="form-inline">
                        <div class="form-group category-list first-category display-off ">
                            <label>Selects</label>
                            <select class="form-control"></select>
                        </div>
                        <div class="form-group category-list second-category display-off ">
                            <select class="form-control"></select>
                        </div>
                        <div class="form-group category-list third-category display-off ">
                            <select class="form-control"></select>
                        </div>
                        
                        <button type="submit" class="btn btn-default display-off">Submit</button>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

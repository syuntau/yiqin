<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

var product_nav = function() {
	var action = {
		showNav : function() {
			var nav = "";
			var navs = "<s:property value='#session.se_product_nav' />";
			if (!navs || navs == null || navs == '') {
				$('.product-nav div.col-sm-12').html('');
				return ;
			}
			var navArr = navs.split(":");
			if (navArr.length == 2) {
				nav += navArr[1];
				var navArr1 = navArr[0].split(",");
				if (navArr1.length == 2) {
					nav = "在 <span style='color:#696763;font-weight:900'>" + navArr1[0] + " > " + navArr1[1] 
							+ " > </span><span style='color:#FE980F;font-weight:900'>" + nav + "</span> 中筛选";
					$('.product-nav div.col-sm-12').html('');
					$('#shop_filter_div div h4').html(nav);
				} else {
					nav = "当前 : <span style='color:#696763;font-weight:900'>" + navArr1[0]
							+ " > </span><span style='color:#FE980F;font-weight:900'>" + nav + "</span>";
					$('.product-nav div.col-sm-12').html(nav);
				}
			} else {
				nav = "当前 : <span style='color:#FE980F;font-weight:900'>" + navArr[0] + "</span>";
				$('.product-nav div.col-sm-12').html(nav);
			}
		},
	};

	return action;
}();

$(document).ready(function() {
	product_nav.showNav();
});
</script>

<div class="product-nav" style="padding-left: 15px;margin-top: -30px;">
	<div class="col-sm-12"></div>
</div>

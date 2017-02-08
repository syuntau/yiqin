<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var	category_conf = {
	cate_li : '<li></li>',
	cate_right_a : '<a data-toggle="tab"></a>',
	cate_panel_a : '<a href="javaScript:void(0)"></a>',
	best_currentNav : "<s:property value='#session.se_shop_nav_best_product' />"
};

var yiqin_category = function(){
	var action = {
		findTopCategorys : function(){
			var url = "findCategoryTree";
			$.ajax({
		         type: "POST",
		         async: true,
		         url: url,
		         dataType: "json",
		         success: function(data){
		        	 if(data=='1'){
		        		 alert("暂无顶级分类信息！");
		        	 }else if(data=='2'){
		        		 alert("加载失败，稍后再试！");
		        	 }else{
		        		 appendCategoryTree(data);
		        	 }
		       },
		       beforeSend: function(){},
		       complete: function(){},
		       error: function(){}
		     });
		},
	}

	
	var appendCategoryTree = function(data) {

		var cateDiv = '<div class="dropdown" style="padding-top: 4px;">' +
							'<a role="button" href="javaScript:void(0)" class="shop_header first-level"></a>' +
						'</div>';
		var cateUl = '<ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu"></ul>';
		var cateLi = '<li><a class="shop_header"></a></li>';
		var cateLiDi = '<li class="divider"></li>';
		var cateLiSub = '<li class="dropdown-submenu">' +
							'<a class="shop_header second-level" href="javaScript:void(0)"></a>' +
							'<ul class="dropdown-menu"></ul>' +
						'</li>';

		$.each(data, function(i, val) {
			var $catDiv = $(cateDiv);
			$catDiv.find('a.first-level').attr('id', 'top_' + val.id).click(function() {
				if(category_conf.best_currentNav=="best_product_nav"){
					yiqin_public_js.toTilesAction(val.id, "/findBestProduct");
				}else{
					yiqin_public_js.toTilesAction(val.id, "/productFilter");
				}
			});
			var subCategoryList1 = val.subCategoryList;
			if (subCategoryList1 && subCategoryList1 != null && subCategoryList1.length > 0) {
				$catDiv.find('#top_' + val.id).html(val.name + ' <span class="caret"></span>');
				
				var $cateUl = $(cateUl);
				$catDiv.append($cateUl);
				
				$.each(subCategoryList1, function(i, val) {
					var $cateLi = $(cateLi);
					
					var subCategoryList2 = val.subCategoryList;
					if (subCategoryList2 && subCategoryList2 != null && subCategoryList2.length > 0) {
						$cateLi = $(cateLiSub);
						$.each(subCategoryList2, function(i, val) {
							var $subCateLi = $(cateLi);

							$subCateLi.find('a').attr('id', 'top_' + val.id).html(val.name).click(function() {
								if(category_conf.best_currentNav=="best_product_nav"){
									yiqin_public_js.toTilesAction(val.id, "/findBestProduct");
								}else{
									yiqin_public_js.toTilesAction(val.id, "/productFilter");
								}
							});
							$cateLi.find('ul').append($subCateLi);
						});
					}

					$cateLi.find('a.second-level').attr('id', 'top_' + val.id).html(val.name).click(function() {
						if(category_conf.best_currentNav=="best_product_nav"){
							yiqin_public_js.toTilesAction(val.id, "/findBestProduct");
						}else{
							yiqin_public_js.toTilesAction(val.id, "/productFilter");
						}
					});
					
					$cateUl.append($cateLi);
				});
			}
			$('#top_header_mainmenu').append($('<li />').append($catDiv));
			
		});
		shop_header.setCurrentNav();
	};

	return action;
}();

$(document).ready(function() {
	yiqin_category.findTopCategorys();
});
</script>

<div class="header-bottom"><!--header-bottom-->
	<div class="container">
		<div class="row">
			<div class="col-sm-9">
				<div class="mainmenu pull-left">
					<ul class="nav navbar-nav collapse navbar-collapse" id="top_header_mainmenu">
						<li>
							<a class="shop_header active" id="top_home"><s:text name="shop.header.nav.home" /></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="search_box pull-right">
				</div>
			</div>
		</div>
	</div>
</div><!--/header-bottom-->

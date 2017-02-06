<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var	category_conf = {
	cate_div : '<div></div>',
	cate_h4 : '<h4 class="panel-title"></h4>',
	cate_coll_a : '<a data-toggle="collapse" data-parent="#accordian" />',
	cate_span_i : '<span class="badge pull-right"><i class="fa fa-plus"></i></span>',
	cate_ul : '<ul></ul>',
	

	cate_li : '<li></li>',
	cate_right_a : '<a data-toggle="tab"></a>',
	cate_panel_a : '<a href="javaScript:void(0)"></a>',
	best_currentNav : "<s:property value='#session.se_shop_nav_best_product' />"
};

var yiqin_category = function(){
	var action = {
		findTopCategorys : function(){
			$.ajax({
		         type: "POST",
		         async: true,
		         url: "findCategoryTree",
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
		$.each(data, function(i, val) {
			String cateDiv = '<div class="dropdown" style="padding-top: 4px;">' +
								'<a role="button" href="javaScript:void(0)" class="shop_header top"></a>' +
							'</div>';
			String cateUl = '<ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu"></ul>';
			String cateLi = '<li><a class="shop_header">{cat-name}</a></li>';
			String cateLiDi = '<li class="divider"></li>';
			String cateLiSub = '<li class="dropdown-submenu">' +
									'<a tabindex="-1" href="#">Hover me for more options</a>' +
									'<ul class="dropdown-menu"></ul>' +
								'</li>';

			var $catDiv = $(cateDiv);
			$catDiv.find('a.shop_header.top').attr('id', 'top_' + val.id).click(function() {
				if(category_conf.best_currentNav=="best_product_nav"){
					yiqin_public_js.toTilesAction(val.id, "/findBestProduct");
				}else{
					yiqin_public_js.toTilesAction(val.id, "/productFilter");
				}
			});
			cateDiv = cateDiv.replace(/#cat-id#/, val.id);
			if (val.subCategoryList && val.subCategoryList != null && val.subCategoryList.length > 0) {
				$catDiv.find('#top_' + val.id).html(val.name + ' <span class="caret"></span>');
				
				String $cateUl = $(cateUl);
				$catDiv.append($cateUl);
				
				$.each(val.subCategoryList, function(i, val) {
					var $
				});
			}
			
			
			
			var $cate_li = $(category_conf.cate_li),
			$cate_panel_a = $(category_conf.cate_panel_a);
			$cate_li.append($cate_panel_a);
			$cate_panel_a.click(function(){
				if(category_conf.best_currentNav=="best_product_nav"){
					yiqin_public_js.toTilesAction(val.id, "/findBestProduct");
				}else{
					yiqin_public_js.toTilesAction(val.id, "/productFilter");
				}
			});
			$cate_panel_a.attr('class',"shop_header").append(val.name);
			$cate_panel_a.attr('id',"top_"+val.id);
			$("#top_header_mainmenu").append($cate_li);
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
						<li>
						<div class="dropdown" style="padding-top: 4px;">
							<a role="button" href="" class="shop_header active">
					            <s:text name="shop.header.nav.home" /> <span class="caret"></span>
					        </a>
							<ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
							  <li><a href="#">Some action</a></li>
							  <li><a href="#">Some other action</a></li>
							  <li class="divider"></li>
							  <li class="dropdown-submenu">
							    <a href="#" class="active">Hover me for more options</a>
							    <ul class="dropdown-menu">
							      <li><a href="#">Second level</a></li>
							      <li><a href="#" class="active">Second level</a></li>
							      <li><a href="#">Second level</a></li>
							    </ul>
							  </li>
							</ul>
						</div>
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

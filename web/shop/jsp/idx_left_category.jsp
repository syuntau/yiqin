<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var	category_temp = {
	cate_div : '<div></div>',
	cate_h4 : '<h4 class="panel-title"></h4>',
	cate_coll_a : '<a data-toggle="collapse" data-parent="#accordian">',
	cate_span_i : '<span class="badge pull-right"><i class="fa fa-plus"></i></span>',
	cate_ul : '<ul></ul>',
	cate_li : '<li></li>',
	cate_panel_a : '<a href="javaScript:void(0)"></a>',
	cate_right_a : '<a data-toggle="tab"></a>'
};

var yiqin_category_action = function(){
	var action = {
		initCategoryInfo : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findCategory.action",
	             dataType: "json",
	             success: function(data){
	            	 var $category_list = $('#accordian');
	            	 if(data=='1'){
	            		 $category_list.html("暂无产品分类信息！");
	            	 }else if(data=='2'){
	            		 $category_list.html("加载失败，稍后再试！");
	            	 }else{
	            		 appendToCategory(data);
	            		 appendRightCategory(data);
	            	 }
                },
                beforeSend: function(){},
                complete: function(){},
                error: function(){}
	         });
		},
		
		
	};
	
	var appendToCategory = function(data){
		 var $category_list = $('#accordian');
		 $category_list.empty();
		 
		 $.each(data, function(i, val){
			 var $cate_div = $(category_temp.cate_div),
			 	 $cate_div_heading = $(category_temp.cate_div),
			 	 $cate_div_coll = $(category_temp.cate_div),
			 	 $cate_div_body = $(category_temp.cate_div),
			 	 $cate_h4 = $(category_temp.cate_h4),
			 	 $cate_coll_a = $(category_temp.cate_coll_a),
			 	 $cate_span_i = $(category_temp.cate_span_i),
			 	 $cate_ul = $(category_temp.cate_ul),
			 	 $cate_li = $(category_temp.cate_li),
			 	 $cate_panel_a = $(category_temp.cate_panel_a);
			 
			 $cate_div.attr('class','panel panel-default');
			 $cate_div_heading.attr('class','panel-heading').append($cate_h4);
			 $cate_div.append($cate_div_heading);
			 if(val.subCategoryList.length>0){
				 $cate_div.append($cate_div_coll);
				 $cate_h4.append($cate_coll_a.attr('href','#'+val.id));
				 $cate_coll_a.append($cate_span_i).append(val.name);
// 				 <s:text name="shop.index.left.product.consumables" />
				 $cate_div_coll.attr('class',"panel-collapse collapse").attr('id',val.id); 
				 $cate_div_body.attr('class',"panel-body").append($cate_ul);
				 $cate_div_coll.append($cate_div_body);
 				 $.each(val.subCategoryList, function(n,sub){
 					$cate_li = $(category_temp.cate_li),
 					$cate_panel_a = $(category_temp.cate_panel_a);
 					$cate_ul.append($cate_li);
 					$cate_panel_a.click(function(){
 						
 					});
 					$cate_li.append($cate_panel_a.append(sub.name));
//  					'<s:text name="shop.index.left.product.consumables.printer" />'
				 });
			 }else{
				 $cate_panel_a.click(function(){
					 
				 });
				 $cate_h4.append($cate_panel_a.append(val.name));
// 				 '<s:text name="shop.index.left.product.consumables" />'
			 }
			 $category_list.append($cate_div);
		 });
	};
	
	var appendRightCategory = function(data){
		$("#right_category_tab").empty();
		$.each(data, function(i, val){
			var $cate_li = $(category_temp.cate_li),
			$cate_right_a = $(category_temp.cate_right_a);
			if(i==0){
				$cate_li.attr('class', "active");
			}
			$cate_li.append($cate_right_a);
			$cate_right_a.attr('href',"#"+val.id).append(val.name);
			$("#right_category_tab").append($cate_li);
		});
	};
	
	return action;
}();


$(document).ready(function() {
	yiqin_category_action.initCategoryInfo();
});
</script>

<div class="panel-group category-products" id="accordian"><!--category-productsr-->
<!-- 	<div class="panel panel-default"> -->
<!-- 		<div class="panel-heading"> -->
<!-- 			<h4 class="panel-title"> -->
<!-- 				<a data-toggle="collapse" data-parent="#accordian" href="#consumables"> -->
<%-- 					<span class="badge pull-right"><i class="fa fa-plus"></i></span> --%>
<%-- 					<s:text name="shop.index.left.product.consumables" /> --%>
<!-- 				</a> -->
<!-- 			</h4> -->
<!-- 		</div> -->
<!-- 		<div id="consumables" class="panel-collapse collapse"> -->
<!-- 			<div class="panel-body"> -->
<!-- 				<ul> -->
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.consumables.printer" /> </a></li> --%>
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.consumables.copier" /> </a></li> --%>
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.consumables.fax" /> </a></li> --%>
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="panel panel-default"> -->
<!-- 		<div class="panel-heading"> -->
<!-- 			<h4 class="panel-title"> -->
<!-- 				<a data-toggle="collapse" data-parent="#accordian" href="#paper"> -->
<%-- 					<span class="badge pull-right"><i class="fa fa-plus"></i></span> --%>
<%-- 					<s:text name="shop.index.left.product.paper" /> --%>
<!-- 				</a> -->
<!-- 			</h4> -->
<!-- 		</div> -->
<!-- 		<div id="paper" class="panel-collapse collapse"> -->
<!-- 			<div class="panel-body"> -->
<!-- 				<ul> -->
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.paper.copy" /></a></li> --%>
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.paper.print" /></a></li> --%>
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.paper.art" /></a></li> --%>
<%-- 					<li><a href="#"><s:text name="shop.index.left.product.paper.other" /></a></li> --%>
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="panel panel-default"> -->
<!-- 		<div class="panel-heading"> -->
<%-- 			<h4 class="panel-title"><a href="#"><s:text name="shop.index.left.product.machines" /></a></h4> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="panel panel-default"> -->
<!-- 		<div class="panel-heading"> -->
<%-- 			<h4 class="panel-title"><a href="#"><s:text name="shop.index.left.product.stationery" /></a></h4> --%>
<!-- 		</div> -->
<!-- 	</div> -->
</div><!--/category-products-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var	category_temp = {
	cate_div : '<div></div>',
	cate_h4 : '<h4 class="panel-title"></h4>',
	cate_coll_a : '<a data-toggle="collapse" data-parent="#accordian">',
	cate_span_i : '<span class="badge pull-right"><i class="fa fa-plus"></i></span>',
	cate_ul : '<ul></ul>',
	cate_li : '<li></li>',
	cate_panel_a : '<a href="javaScript:void(0)"></a>',
	cate_right_a : '<a data-toggle="tab"></a>',
};

var yiqin_category_action = function(){
	var action = {
		findCategoryByTopCate : function(topCateId){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findCategory",
	             data: "topCateId="+topCateId,
	             dataType: "json",
	             success: function(data){
	            	 var $category_list = $('#accordian');
	            	 if(data=='1'){
	            		 $category_list.html("暂无产品分类信息！");
	            	 }else if(data=='2'){
	            		 $category_list.html("加载失败，稍后再试！");
	            	 }else{
	            		 appendToCategory(data);
	            	 }
                },
                beforeSend: function(){},
                complete: function(){
                	var categoryId = "<s:property value='paramVal'/>";
                	if(categoryId != null && categoryId != ""){
                		if(categoryId.length>=4){
                			$("a[href='#first_"+categoryId.substring(0,2)+"']").click();
                			$("#two_"+categoryId.substring(0,4)).css('color','#fdb45e');
                			$("#shop_filter_div b").html($("#two_"+categoryId.substring(0,4)).html());
                		}else{
                			if(categoryId.length==2){
                				$("#first_"+categoryId).css('color','#fdb45e');
                			}
                		}
                	}
                },
                error: function(){}
	         });
		},
		
		findTopCategorys : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findTopCategory",
	             dataType: "json",
	             success: function(data){
	            	 if(data=='1'){
	            		 alert("暂无顶级分类信息！");
	            	 }else if(data=='2'){
	            		 alert("加载失败，稍后再试！");
	            	 }else{
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
		 var currentNav = "<s:property value='#session.se_shop_nav' />";
		 if("top_home"==currentNav){
			 $.each(data, function(i, val){
				 appendTo(val.subCategoryList,$category_list);				 
			 });
		 }else{
			 appendTo(data,$category_list);
		 }
	};
	
	var appendTo = function(beginList,cate_accordian){
		if(beginList.length>0){
			 $.each(beginList, function(n,sub){
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
				 
				 if(sub.subCategoryList.length>0){
					 $cate_div.append($cate_div_coll);
					 $cate_h4.append($cate_coll_a.attr('href','#first_'+sub.id));
					 $cate_coll_a.append($cate_span_i).append(sub.name);
					 $cate_div_coll.attr('class',"panel-collapse collapse").attr('id',"first_"+sub.id);
					 $cate_div_body.attr('class',"panel-body").append($cate_ul);
					 $cate_div_coll.append($cate_div_body);
					 
					$.each(sub.subCategoryList, function(k,nextSub){
	 					$cate_li = $(category_temp.cate_li),
	 					$cate_panel_a = $(category_temp.cate_panel_a);
	 					$cate_panel_a.attr('id',"two_"+nextSub.id);
	 					$cate_ul.append($cate_li);
	 					$cate_panel_a.click(function(){
	 						yiqin_public_js.toTilesAction(nextSub.id, "/productFilter");
	 					});
	 					$cate_li.append($cate_panel_a.append(nextSub.name));
					});
				 }else{
					 $cate_panel_a.click(function(){
						yiqin_public_js.toTilesAction(sub.id, "/productFilter");
					 });
					 $cate_h4.append($cate_panel_a.append(sub.name).attr('id',"first_"+sub.id));
				 }
				 cate_accordian.append($cate_div);
			 });
		 }
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
			$cate_right_a.attr('href',"#right_"+val.id).append(val.name);
			$("#right_category_tab").append($cate_li);
			
			var $cate_li = $(category_temp.cate_li),
			$cate_panel_a = $(category_temp.cate_panel_a);
			$cate_li.append($cate_panel_a);
			$cate_panel_a.click(function(){
				yiqin_public_js.toTilesAction(val.id, "/productFilter");
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
	yiqin_category_action.findTopCategorys();
	var currentNav = "<s:property value='#session.se_shop_nav' />";
	if (currentNav != '' && currentNav != null) {
		var topcid =  currentNav.split("_")[1];
		if(!isNaN(topcid)){
			yiqin_category_action.findCategoryByTopCate(topcid);
		}else{
			yiqin_category_action.findCategoryByTopCate("");
		}
	}else{
		yiqin_category_action.findCategoryByTopCate("");
	}
});
</script>
<h2><s:text name="shop.index.left.category"/></h2>
<div class="panel-group category-products" id="accordian"><!--category-productsr--></div><!--/category-products-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var pro_att = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" title="删除属性"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" title="修改属性"></i>',
	},
	initCategory : function() {
		pro_att.loadFirstCategory();
		pro_att.initSubmitCategory();
		pro_att.initUploadSubmit();
		
	},
	loadFirstCategory : function() {
		var $firstCategory = $('.first-category');
		$.ajax({
            type: "post",
            url: "getCategoryList_getFirst",
            dataType: "json",
            success: function(data) {
	           	 if (data=='2') {
	           		$firstCategory.html("暂无产品分类信息！");
	           	 } else {
	           		var $firstCategorySelect = $firstCategory.find('select');
					$.each(data, function(i, val) {
						var $op = $(pro_att.conf.option);
						$op.val(val.id).html(val.name);
						$firstCategorySelect.append($op);
					});
					$firstCategory.find('option:first').attr('selected', true);
					pro_att.loadCategory('second-category', $firstCategory.find('option:selected').val());
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pro_att.hideAttr();
            	$firstCategory.prepend($(com_conf.loading_icon));
        	},
            complete: function() {
            	$firstCategory.find('span').remove();
            }
		});
	},
	loadCategory : function(type, catetoryId) {
		var $categoryDiv = $('.' + type);
		$.ajax({
            type: "post",
            url: "getCategoryList_getList",
            data: 'cId='+catetoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		pro_att.hideCategory(type);
	           		$categoryDiv.append("<span>查询参数有误！</span>");
	           	 } else if (data=='2') {
		           	pro_att.hideCategory(type);
		           	$categoryDiv.append("<span>暂无产品分类信息！</span>");
	           	 } else {
	           		$categoryDiv.find('span').remove();
	           		var $categoryDivSelect = $categoryDiv.find('select');
					$.each(data, function(i, val) {
						var $op = $(pro_att.conf.option);
						$op.val(val.id).html(val.name);
						$categoryDivSelect.append($op);
					});
					$categoryDivSelect.find('option:first').attr('selected', true);
					if (type == 'second-category') {
						pro_att.loadCategory('third-category', $('.second-category').find('option:selected').val());
					}
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pro_att.hideAttr();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$categoryDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$categoryDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	hideCategory : function(type) {
		if (type == 'second-category') {
       		$('.second-category select, .third-category select').addClass('display-off');
       		$('.btn-category-submit').addClass('display-off');
		} else if (type == 'third-category') {
       		$('.third-category select').addClass('display-off');
       		$('.btn-category-submit').addClass('display-off');
		}
	},
	hideAttr : function() {
		$('.attr-hr').addClass('display-off');
		$('.upload-attr').addClass('display-off');
		$('.attr-section .attr-panel').addClass('display-off');
		$('.attr-section .attr-panel').parent().find('span').remove();
	},
	changeCategory : function() {
		$('.first-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.second-category select, .third-category select').empty();
			pro_att.loadCategory('second-category', selectCategoryId);
		});
		$('.second-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.third-category select').empty();
			pro_att.loadCategory('third-category', selectCategoryId);
		});
	},
	initSubmitCategory : function() {
		$('.btn-category-submit').on('click', function() {
			pro_att.loadAttributeList();
		})
	},
	loadAttributeList : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("所选分类错误，请重试！");
			return ;
		}
		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');
		$.ajax({
            type: "post",
            url: "getAttribute_getList",
            data: 'cId='+catetoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$attrDiv.find('.attr-panel').parent().append("<span>查询参数有误！</span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span>暂无属性信息！</span>");
	           	 } else {
	           		var $tbody = $attrDiv.find('tbody');
					$.each(data, function(i, val) {
						var $tr = $(pro_att.conf.tr);
						var _id = $(pro_att.conf.td).html(val.id);
						var _nameId = $(pro_att.conf.td).html(val.nameId);
						var _name = $(pro_att.conf.td).html(val.name);
						var _value = $(pro_att.conf.td).html(val.value);
						var _categoryId = $(pro_att.conf.td).html(val.categoryId);
						var _filter = $(pro_att.conf.td).html(val.filter);
						var _filterType = $(pro_att.conf.td).html(val.filterType);
						var _showValue = $(pro_att.conf.td).html(val.showValue);
						var _sort = $(pro_att.conf.td).html(val.sort);
						var $iRemove = $(pro_att.conf.i_remove);
						$iRemove.on('click', function() {
							alert("remove id : " + val.id);
						});
						var $iEdit = $(pro_att.conf.i_edit);
						$iEdit.on('click', function() {
							alert("edit id : " + val.id);
						});
						var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);

						$tr.append(_id).append(_nameId).append(_name).append(_value)
							.append(_categoryId).append(_filter).append(_filterType)
							.append(_showValue).append(_sort).append(_setting);

						$tbody.append($tr);
					});
					$attrDiv.find('.attr-panel').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	$attrHR.removeClass('display-off');
           		$attrDiv.find('span').remove();
				$('.upload-attr').addClass('display-off');
           		$attrDiv.find('.attr-panel').addClass('display-off');
           		var $tbody = $attrDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$attrDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$attrDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	initUploadSubmit : function() {
		$('.btn-upload').on('click', function() {
			pro_att.uploadAttribute();
		});
	},
	uploadAttribute : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("所选分类错误，请重试！");
			return ;
		}
		console.log("file : " + $('.upload-attr-file').val());
		var fPath = $('.upload-attr-file').val().replace(/C:\\fakepath\\/i, '');
		console.log("file 1 : " + fPath);
		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');
		$.ajax({
            type: "post",
            url: "uploadAttribute",
            data: 'fPath=' + fPath,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$attrDiv.find('.attr-panel').parent().append("<span>查询参数有误！</span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span>暂无属性信息！</span>");
	           	 } else {
	           		var $tbody = $attrDiv.find('tbody');
					$.each(data, function(i, val) {
						var $tr = $(pro_att.conf.tr);
						var _id = $(pro_att.conf.td).html(val.id);
						var _nameId = $(pro_att.conf.td).html(val.nameId);
						var _name = $(pro_att.conf.td).html(val.name);
						var _value = $(pro_att.conf.td).html(val.value);
						var _categoryId = $(pro_att.conf.td).html(val.categoryId);
						var _filter = $(pro_att.conf.td).html(val.filter);
						var _filterType = $(pro_att.conf.td).html(val.filterType);
						var _showValue = $(pro_att.conf.td).html(val.showValue);
						var _sort = $(pro_att.conf.td).html(val.sort);
						var $iRemove = $(pro_att.conf.i_remove);
						$iRemove.on('click', function() {
							alert("remove id : " + val.id);
						});
						var $iEdit = $(pro_att.conf.i_edit);
						$iEdit.on('click', function() {
							alert("edit id : " + val.id);
						});
						var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);

						$tr.append(_id).append(_nameId).append(_name).append(_value)
							.append(_categoryId).append(_filter).append(_filterType)
							.append(_showValue).append(_sort).append(_setting);

						$tbody.append($tr);
					});
					$attrDiv.find('.attr-panel').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	$attrHR.removeClass('display-off');
           		$attrDiv.find('span').remove();
				$('.upload-attr').addClass('display-off');
           		$attrDiv.find('.attr-panel').addClass('display-off');
           		var $tbody = $attrDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$attrDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$attrDiv.find('.fa-refresh').parent().remove();
            }
		});
	}
}
$(document).ready(function() {
	pro_att.initCategory();
	pro_att.changeCategory();
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
                    <form role="form" class="form-inline category-form">
                        <div class="form-group category-list first-category">
                            <label class="category-select  display-off">选择分类</label>
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list second-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list third-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-default btn-category-submit category-select display-off">查询</button>
                        <div class="form-group" style="padding-left:20px">
                            <input type="file" class="upload-attr-file upload-attr display-off">
                        </div>
                        <button type="button" class="btn btn-default btn-upload upload-attr display-off">上传</button>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <hr class="attr-hr display-off">
            
            <div class="row attr-section">
                <div class="col-lg-12">
                    <div class="panel panel-default attr-panel display-off">
                        <div class="panel-heading">
                            属性列表
                            <button type="button" class="btn btn-link btn-remove-all-attr">删除所有属性</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover attr-table">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>nameId</th>
                                            <th>name</th>
                                            <th>value</th>
                                            <th>categoryId</th>
                                            <th>filter</th>
                                            <th>filterType</th>
                                            <th>showValue</th>
                                            <th>sort</th>
                                            <th>setting</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

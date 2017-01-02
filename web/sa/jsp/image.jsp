<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var product_id = null;
	$(document).ready(function() {
		imageFun.initFun();
	});
	var imageFun = {
			
			initFun : function (){
				$('#reg_image_btn').off().on({
					'click':function(){
						imageFun.searchImage();
					}
				});
				$('.btn-upload').on('click', function() {
					imageFun.uploadImage();
				});
			},
			
			searchImage : function (){
				var productId = $('.product_id').val();
				if(isEmpty(productId)){
					alert('不能为空！');
					return;
				}
				
				$.ajax({
		            type: "post",
		            url: "searchImage",
		            dataType: "json",
		            data : {
		            	productId : productId
		            },
		            success: function(data) {
			           	 if (data=='2') {
			           		
			           	 } else {
			           		$('.image_list').empty();
			           		if(isEmpty(data)){
			           			alert('查询为空！');
			           		}else{
			           			product_id = productId;
			           			$.each(data , function (key,val){
				           			var html = 
				    					'<div class="col-sm-6 col-md-3">'+
				    						'<div class="thumbnail">'+
				    							'<img src="'+val+'" alt="...">'+
				    							'<div class="caption">'+
				    								'<p> '+val.replace('/img/','')+' <a href="javascript:void(0)" class="btn btn-primary image_delete_btn" imageId = "'+val.replace('/img/','')+'"  role="button">删除</a></p>'+
				    							'</div>'+
				    						'</div>'+
				    					'</div>';
				    				$('.image_list').append(html);
				           		});
			           			
			           			$('.image_delete_btn').off().on({
			           				'click' : function (){
			           					var imageId = $(this).attr('imageId');
			           					imageFun.deleteImage(imageId);
			           				}
			           			});
			           		}
			           		
			           	 }
		            },
		            beforeSend: function() {
		            	/* pd_attr.hideAttr();
		            	$firstCategory.prepend($(com_conf.loading_icon)); */
		        	},
		            complete: function() {
		            	/* $firstCategory.find('span').remove(); */
		            }
				});
				
			},
			uploadImage : function (){
				$.ajaxFileUpload({
		            url: "uploadImage",
		            dataType : 'text',
		            fileElementId: 'imageFile',
		            success: function(data) {
		            	console.log(data);
		            	alert(data == '100');
		            	alert(data == 100);
			           	if (data=='100') {
			           		imageFun.searchImage();
			           	} else {
				        	
			           	}
		            },
		            error : function(data,status,e) {
		            	alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.upload' /></s:param></s:text>");
			           	$itemDiv.find('.fa-refresh').parent().remove();
			           	$('.upload-item').removeClass('display-off');
		            }
				});
			},
			deleteImage : function (imageId){
				$.ajax({
		            type: "post",
		            url: "deleteImage",
		            dataType: "text",
		            data : {
		            	deleteImageFileName : imageId
		            },
		            success: function(data) {
			           	 if (data=='2') {
			           		
			           	 } else {
			           		
			           	 }
		            },
		            beforeSend: function() {
		            	/* pd_attr.hideAttr();
		            	$firstCategory.prepend($(com_conf.loading_icon)); */
		        	},
		            complete: function() {
		            	/* $firstCategory.find('span').remove(); */
		            }
				});
			}
	}
	var isEmpty = function(value, allowEmptyString) {
        return (value === null) || (value === undefined)
        || (!allowEmptyString ? value === '' : false)
        || ($.isArray(value) && value.length === 0);
	}
</script>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">
				<s:text name="sa.pd.image.title" />
			</h3>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12" style="margin-bottom: 20px;">
			<form class="form-inline" style="float: left;">
				<div class="form-group" style="margin-left: 10px;">
					<label class="user-select">商品id：</label> <input type="text"
						class="form-control product_id" name="product_id">
				</div>
				<button type="button"
					class="btn btn-info btn-user-submit " id="reg_image_btn">查询</button>
			</form>
			<form role="form" class="form-inline category-form"
				enctype="multipart/form-data">
				<div class="form-group" style="padding-left: 20px">
					<input type="file" id="imageFile" name="imageFile" accept="image/*"
						class="upload-item-file upload-image ">
				</div>
				<button type="button" class="btn btn-info btn-upload upload-image ">
					<s:text name="sa.btn.upload" />
				</button>
			</form>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<div class="row image_list">
		
	</div>

	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

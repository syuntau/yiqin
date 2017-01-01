<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		var html = 
			'<div class="col-sm-6 col-md-4">'+
				'<div class="thumbnail">'+
					'<img src="https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1483287653&di=31dae1a151a58550aa07f7a5186633fd&src=http://file20.mafengwo.net/M00/D5/00/wKgB3FGgy-CAb90wAAiCjVBl2v445.groupinfo.w600.jpeg" alt="...">'+
					'<div class="caption">'+
						'<p><a href="#" class="btn btn-primary" role="button">删除</a></p>'+
					'</div>'+
				'</div>'+
			'</div>';
		$('.image_list').append(html);
		$('.image_list').append(html);
		$('.image_list').append(html);
	});
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
						class="form-control" name="zhekou">
				</div>
				<button type="button"
					class="btn btn-info btn-user-submit user-select" id="reg_code_btn">查询</button>
			</form>
			<form role="form" class="form-inline category-form"
				enctype="multipart/form-data">
				<div class="form-group category-list first-category">
					<label class="category-select  display-off"><s:text
							name="sa.pd.lbl.select.category" /></label> <select
						class="form-control category-select display-off"></select>
				</div>
				<div class="form-group category-list second-category">
					<select class="form-control category-select display-off"></select>
				</div>
				<div class="form-group category-list third-category">
					<select class="form-control category-select display-off"></select>
				</div>

				<button type="button"
					class="btn btn-info btn-category-submit category-select display-off">
					<s:text name="sa.btn.query" />
				</button>
				<s:if test="%{#roles.indexOf('13205')>-1}">
					<button type="button"
						class="btn btn-info btn-download-product-submit display-off">
						<s:text name="sa.btn.download" />
					</button>
				</s:if>
				<div class="form-group" style="padding-left: 20px">
					<input type="file" id="imageFile" name="imageFile"
						class="upload-item-file upload-item ">
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
	<hr class="item-hr display-off">

	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

        <!-- Navigation -->
<script type="text/javascript">
$(document).ready(function() {
	(function() {

		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');
		var bar = $('.bar');
		var percent = $('.percent');

		$('.attribute-form').ajaxForm({
			url : 'uploadAttribute',
		    dataType: "json",
		    beforeSend: function() {console.log("uploadAttribute beforeSend in...");
		        var percentVal = '0%';
		        bar.width(percentVal)
		        percent.html(percentVal);
		    },
		    uploadProgress: function(event, position, total, percentComplete) {
		        var percentVal = percentComplete + '%';
		        bar.width(percentVal)
		        percent.html(percentVal);
		    },
		    success: function() {console.log("uploadAttribute success in...");
		       	if (data=='1') {
		       		$attrDiv.find('.attr-panel').parent().append("<span>查询参数有误！</span>");
		       	} else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span>暂无属性信息！</span>");
		       	} else {
			        var percentVal = '100%';
			        bar.width(percentVal)
			        percent.html(percentVal);
		       	}
		    },
			complete: function() {console.log("uploadAttribute complete in...");
		    	
			}
		});
	})();
});
</script>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Index</h1>
                    <form role="form" class="form-inline attribute-form" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="file" id="upload_attribute" name="fPath" class="upload-attr-file">
                        </div>
                        <button type="submit" class="btn btn-info btn-upload">Upload</button>
                        <div class="progress">
					        <div class="bar" style="width: 100%;"></div>
					        <div class="percent">100%</div>
					    </div>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

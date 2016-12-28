<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function() {
	alert(111);
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.pd.item.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <form role="form" class="form-inline category-form" enctype="multipart/form-data">
                        <div class="form-group category-list first-category">
                            <label class="category-select  display-off"><s:text name="sa.pd.lbl.select.category" /></label>
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list second-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list third-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-category-submit category-select display-off"><s:text name="sa.btn.query" /></button>
                        <s:if test="%{#roles.indexOf('13205')>-1}">
                        <button type="button" class="btn btn-info btn-download-product-submit display-off"><s:text name="sa.btn.download" /></button>
                        </s:if>
                        <div class="form-group" style="padding-left:20px">
                            <input type="file" id="itemFile" name="itemFile" class="upload-item-file upload-item ">
                        </div>
                        <button type="button" class="btn btn-info btn-upload upload-item "><s:text name="sa.btn.upload" /></button>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <hr class="item-hr display-off">
            
            <div class="row item-section">
                <div class="col-lg-12">
                    <div class="panel panel-default item-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.item.list.title" />
                			<s:if test="%{#roles.indexOf('13201')>-1}">
                            <button type="button" class="btn btn-link btn-remove-all-item"><s:text name="sa.pd.item.btn.remove.all" /></button>
                            </s:if>
                			<s:if test="%{#roles.indexOf('13202')>-1}">
                            <button type="button" class="btn btn-link btn-add-item"><s:text name="sa.pd.item.btn.add" /></button>
                            </s:if>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover attr-table">
                                    <thead>
                                        <tr>
                                            <th>Product Id</th>
                                            <th>Product Name</th>
                							<s:if test="%{#roles.indexOf('13203')>-1 || #roles.indexOf('13204')>-1}">
                                            <th>setting</th>
                                            </s:if>
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

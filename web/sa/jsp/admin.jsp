<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">

$(document).ready(function() {
	
});
</script>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><s:text name="sa.header.user.sa" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><s:text name="sa.user.lbl.sa" /></div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form">
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.old.password" /></label>
                                            <input type="password" class="form-control">
                                            <p class="help-block"></p>
                                        </div>
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.new.password" /></label>
                                            <input type="password" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.comfirm.new.password" /></label>
                                            <input type="password" class="form-control">
                                        </div>
                                        <button type="submit" class="btn btn-default"><s:text name="sa.btn.modify" /></button>
                                        <button type="reset" class="btn btn-default"><s:text name="sa.btn.reset" /></button>
                                    </form>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
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

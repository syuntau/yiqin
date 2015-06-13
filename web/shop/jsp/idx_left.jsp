<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style type="text/css">
  div.affix {
    position: fixed;
  	width: 234px;
  	top: 10px;
  	background-color: #FE980F;
  }
  div.affix-bottom {
    position: fixed;
  	width: 234px;
  	bottom: 240px;
  	background-color: #FE980F;
  }
</style>
	<div class="col-sm-3" id="left-category-nav" style="width: 234px;background-color: #FE980F;padding-left: 5px;padding-right: 5px;">
<!-- 		<div class="left-sidebar" data-spy="affix" data-offset-top="116" data-offset-bottom="240" > -->
		<div class="left-sidebar" >
			<tiles:insertAttribute name="category" />
<%-- 			<tiles:insertAttribute name="brands" /> --%>
<%-- 			<tiles:insertAttribute name="ad" /> --%>
		</div>
	</div>

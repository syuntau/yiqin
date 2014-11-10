<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

	<div class="col-sm-3">
		<div class="left-sidebar">
			<tiles:insertAttribute name="category" />
			<tiles:insertAttribute name="brands" />
			<tiles:insertAttribute name="ad" />		
		</div>
	</div>

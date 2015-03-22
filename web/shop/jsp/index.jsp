<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

	<tiles:insertAttribute name="ad" ></tiles:insertAttribute>

	<section>
		<div class="container">
			<div class="row">
				<tiles:insertAttribute name="left" />
				<tiles:insertAttribute name="right" />
			</div>
		</div>
	</section>

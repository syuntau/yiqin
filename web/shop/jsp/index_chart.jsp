<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

	<section >
		<div class="container">
			<div class="row">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</section>

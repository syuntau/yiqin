<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<body>
		<c:if test="${not empty page}">
			<ul class="pagination">
			<c:if test="${page.hasPrev}">
				<li><a href="javascript:toIndexPage('1')">〖首页〗 </a></li>
				<li><a href="javascript:toIndexPage('${page.currentPageIndex-1 }')">上一页</a></li>
			</c:if>
			<c:forEach begin="${page.beginPageIndex}" end="${page.endPageIndex}"
				var="i">
				<c:choose>
					<c:when test="${page.currentPageIndex==i}">
						<li class="active"><a>${i }</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:toIndexPage('${i}')">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${page.hasNext}">
				<li><a href="javascript:toIndexPage('${page.currentPageIndex+1}')">下一页</a></li>
				<li><a href="javascript:toIndexPage('${page.totalPageNum}')">〖末页〗</a></li>
			</c:if>
             	<li><a>共${page.totalPageNum}页 / 共${page.totalRowNum }条数据</a></li>
             </ul>
		</c:if>
	</body>
</html>

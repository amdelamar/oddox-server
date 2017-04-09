<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>Blog - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->

	<!-- CONTENT BEGIN -->
	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Blog</h1>
				
				<!-- POSTS START -->
				<s:if test="posts != null">
				<s:if test="posts.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no posts were found. Please try again later?</p>
				</s:if>
				<s:else>
					
					<s:iterator value="posts" status="r">
						<%@include file="/WEB-INF/fragment/card-post.jspf" %>
					</s:iterator>
					
					<div class="w3-container w3-row">
						<div class="w3-col s4 m4 l4">
						<s:if test="prevPage == true">
							<a class="w3-btn w3-round w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-left" href="/blog/page/<s:property value="page - 1" />"><span class="icon-arrow-left w3-large w3-margin-right"></span> Prev Page</a>
						</s:if><s:else>&nbsp;</s:else>
						</div>
						<div class="w3-col s4 m4 l4 w3-center">
							<span class="w3-small w3-text-grey">Page <s:property value="page" /></span>
						</div>
						<div class="w3-col s4 m4 l4">
						<s:if test="nextPage == true">
							<a class="w3-btn w3-round w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-right" href="/blog/page/<s:property value="page + 1" />"><span class="icon-arrow-right w3-large w3-margin-right"></span>Next Page</a>
						</s:if><s:else>&nbsp;</s:else>
						</div>
					</div>
				
				</s:else>
				</s:if>
				<!-- POSTS END -->
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Management Menu - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Management Menu</h1>		
				
				<div class="w3-row">
					<div class="w3-container w3-padding w3-col s12 m12 l6">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">Blog Posts</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
								<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newpost"><span class="icon-quill w3-large"></span>&nbsp;New Post</a> 
								<span class="w3-small w3-text-grey nowrap">Add a new blog post.</span>
								</p>
								<p>
								<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/posts"><span class="icon-quill w3-large"></span>&nbsp;View Posts</a>
								<span class="w3-small w3-text-grey nowrap">Modify a blog post.</span>
								</p>
							</div>
						</div>
					</div>
					<div class="w3-container w3-padding w3-col s12 m12 l6">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">Authors</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
								<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newuser"><span class="icon-author w3-large"></span>&nbsp;Add Author</a>
								<span class="w3-small w3-text-grey nowrap">Add a new Author.</span>
								</p>
								<p><a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/users"><span class="icon-author w3-large"></span>&nbsp;View Authors</a>
								<span class="w3-small w3-text-grey nowrap">Modify an Author.</span>
								</p>
							</div>
						</div>
					</div>	
				</div>
				
				<div class="w3-row">
					<div class="w3-container w3-padding w3-col s12 m12 l12">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">My Settings</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
								<p>
								<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/edituser/<s:property value="#session.USER.uriName" />">
								<s:if test="#session.USER.thumbnail != null && !#session.USER.thumbnail.trim().isEmpty()">
									<img class="w3-round w3-large" style="vertical-align: middle;" src="<s:property value="#session.USER.thumbnail" />" height="24" width="24" />
								</s:if>
								<s:else>
									<span class="icon-author w3-large"></span>
								</s:else>								
								&nbsp;Edit My Page</a>
								<span class="w3-small w3-text-grey nowrap">Modify your public author page.</span>
								</p>
								<p>
								<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/settings/account">Edit Username / Email</a> 
								<span class="w3-small w3-text-grey nowrap">Modify your username or email address.</span>
								</p>
								<p>
								<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/settings/password">Change Password</a>
								<span class="w3-small w3-text-grey nowrap">Modify your password to login.</span>
								</p>
							</div>
						</div>
					</div>			
				</div>
				
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>
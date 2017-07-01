<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Login - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-dark">
		<div class="page w3-row">
		
			<div id="page-content">
			
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">
					
					<div class="w3-border w3-round w3-padding w3-theme-light">
						<div class="w3-margin-0 w3-padding-0 w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Please Login</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/login" method="post">
							<p>
								<input type="text" size="50" maxlength="100" name="username" id="username" value="<s:property value="username" />" required autofocus placeholder="Username" class="w3-input w3-round-large w3-border" />
							</p>
							<p>
								<input type="password" size="50" maxlength="200" name="password" id="password" value="" required placeholder="Password" class="w3-input w3-round-large w3-border" />
							</p>
							<p class="w3-center">
								<button class="w3-btn-wide w3-round w3-blue w3-hover-indigo" type="submit" value="Login" title="Login">Login</button>
							</p>
						</form>
						
							<p class="w3-small w3-text-grey w3-center">
								<a href="/manage/forgot?type=username">Forgot Username?</a>&nbsp;|&nbsp; 
								<a href="/manage/forgot?type=password">Forgot Password?</a>
							</p>
						</div>
					</div>
				</div>
				<div class="w3-container w3-padding w3-col m3 l3"></div>
			</div>
		</div>
	</article>	
</body>
</html>
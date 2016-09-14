<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Web Template App</title>
	 
	<spring:url value="/resources/core/css/templateApp.css" var="coreCss" />
	<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
	
	<spring:url value="/resources/core/js/templateApp.js" 	var="coreJs" />
	<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
	<spring:url value="/resources/core/js/jquery.min.js" 	var="jqueryJs" />
	
	<link href="${bootstrapCss}" rel="stylesheet" />
	<link href="${coreCss}" rel="stylesheet" />
	 
	<script src="${coreJs}"></script>
	<script src="${bootstrapJs}"></script>
	<script src="${jqueryJs}"></script>
</head>
 
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">${message}</a>
	</div>
  </div>
</nav>
 
<div class="jumbotron">
  <div class="container">
	<h1>${title}</h1>
	<p>
		<c:if test="${not empty name}">
			Welcome, ${name}
		</c:if>
 
		<c:if test="${empty name}">
			Hello, Who are you?
		</c:if>
    </p>
    <p>
		<a class="btn btn-primary btn-lg" href="swagger-ui.html" role="button">See API REST Example</a>
	</p>
	</div>
</div>
 
<div class="container">
 
	<div class="row">
		<div class="col-md-12">
			<h2>Heading</h2>
			<p>ABC</p>
			
			<br><hr><br>
	
			<h2>Messages</h2>
			<table>
			    <tr>
			        <td><b>welcome: </b></td>
			        <td><spring:message code="welcome" /></td>
			    </tr>
			    <tr>
			        <td><b>this.is.a.demo: </b></td>
			        <td><spring:message code="this.is.a.demo" /></td>
			    </tr>
			</table>
			
			<br><hr><br>
			
			<h2>Properties</h2>
			<table>
			    <tr>
			        <td><b>db.url: </b></td>
			        <td>
			        	<spring:eval expression="@environment.getProperty('db.url')" />
			        </td>
			    </tr>
			    <tr>
			        <td><b>image.path: </b></td>
			        <td>
     			        <spring:eval expression="@environment.getProperty('image.path')" />
     			    </td>
			    </tr>
			</table>
		</div>
	</div>
	
	<br><br><br><br><hr>
	  <footer>
	    <a href="?lang=en">English </a> | <a href="?lang=es">Espa&ntilde;ol </a>
	    | <a href="?lang=es_AR">Espa&ntilde;ol (AR) </a> | <a href="?lang=es_ES">Espa&ntilde;ol (ES) </a>
		<p><a href="https://github.com/acurci/jproject-templateApp">Follow me in Github</a></p>
	  </footer>
</div>
</body>
</html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>jproject-templateApp</title>
	 
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
	<h2><spring:message code="title" /></h2>
	<p> 
		<spring:message code="welcome" />
		<c:if test="${not empty name}">
			, ${name}
		</c:if>
    </p>
    <p>
		<a class="btn btn-primary btn-lg" href="swagger-ui.html" role="button"><spring:message code="go.api" /></a>
	</p>
	</div>
</div>
 
<div class="container">
 
	<div class="row">
		<div class="col-md-12">
			<h2><spring:message code="messages" /></h2>
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
			
			<hr><br>
			
			<h2><spring:message code="properties" /></h2>
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
		<p><a href="https://github.com/acurci/jproject-templateApp">${follow.me.in.github}</a></p>
	  </footer>
</div>
</body>
</html>
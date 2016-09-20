<%@taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<body>
		<h1><spring:message code="title.secure.page" /></h1>
		<h2><spring:message code="welcome" />: ${pageContext.request.userPrincipal.name}</h2>
		<br>
		<br>
		<br>
		<a href='<c:url value="/login?logout"/>'><spring:message code="logout" /></a>
	</body>
</html>
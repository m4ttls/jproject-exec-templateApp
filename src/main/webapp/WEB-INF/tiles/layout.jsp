<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta content="IE=edge" http-equiv="X-UA-Compatible">
		<meta content="width=device-width, initial-scale=1" name="viewport">
		<meta content="templateApp" name="description">
		<meta content="templateApp" name="keywords">
		<meta content="Ing. Alejandro Daniel Curci (acurci@gmail.com.ar) " name="author">

		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />

		<title><tiles:insertAttribute name="title" ignore="true" /></title>

		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ui.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui-1.10.2.custom.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.css" />" />

		<style type="text/css">
			
			#page-wrap{
				margin: auto;
				height: auto;
				min-height:100%;
				padding-bottom: 20px;
			}
			 
			#page-wrap:after{
				height:20px;
				display:block;
				clear:both;
			}
			 
			#footer{
				height: 30px;
				margin-top: -30px;
				background: #3B5998;
			}
						
		</style>	

		<script type="text/javascript">
			var url_application = '${pageContext.servletContext.contextPath}';
		</script>	

		<script src="<c:url value="/resources/js/commons.utils.js" />" ></script>
		<script src="<c:url value="/resources/js/date.format.js" />" ></script>
			
		<!-- JQuery -->
		<script src="<c:url value="/resources/js/jquery-1.10.2.js" />" ></script>
		<script src="<c:url value="/resources/js/jquery-ui-1.10.2.custom.min.js" />"></script>
	
		<!-- Versión compilada y comprimida del JavaScript de Bootstrap -->
		<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
		<script src="<c:url value="/resources/i18n/i18n_utils.js" />"></script>	
	</head>
	<body>
		<div id="commons_modal" class="modal"></div>
		<div id="page-wrap" style="background-color: rgb(226, 226, 226);" class="container-fluid wrapper">
			<div class="row header">
				<tiles:insertAttribute name="header" />
			</div>
			<div id="body-wrap" class="row fill" style="background-color: rgb(226, 226, 226);">
				<div class="col-md-12" style="background-color: rgb(226, 226, 226); margin-top: 10px;">
					<tiles:insertAttribute name="body" />
				</div>	
			</div>
			</div>
	</body>
</html>

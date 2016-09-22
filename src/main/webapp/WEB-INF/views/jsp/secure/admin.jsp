<%@taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		<link href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" 		rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/css/bootstrap.min.css" rel="stylesheet" />
		<link href="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/css/ui.jqgrid.css" 			rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/plugins/searchFilter.css" 	rel="stylesheet">

		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/js/i18n/grid.locale-en.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/js/i18n/grid.locale-es.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/plugins/jquery.searchFilter.js"></script>
	
		<spring:url value="/resources/core/css/templateApp.css" 	var="coreCss" />
		<spring:url value="/resources/core/js/templateApp.js" 		var="coreJs" />
		<link href="${coreCss}" rel="stylesheet" />
		<script src="${coreJs}"></script>

	
		<script type="text/javascript">
		$(document).ready(function() {
				
			jQuery("#list2").jqGrid({ 
				url: '<c:url value="/foos"/>', 
				datatype: "json", 
				colNames:['Id','Version', 'Code', 'Description','Type','Vigencia Desde','Vigencia Hasta'], 
				colModel:[ {name:'id',			 index:'id' 			}, 
				           {name:'version',		 index:'version' 		}, 
				           {name:'code',		 index:'code' 			}, 
				           {name:'description',	 index:'description'}, 
				           {name:'type',		 index:'type', 	align:"center"}, 
				           {name:'vigenciaDesde',index:'vigenciaDesde',	align:"center"}, 
				           {name:'vigenciaHasta',index:'vigenciaHasta', 	align:"center"} ], 
				           rowNum:10, 
				           rowList:[10,20,30],
						   jsonReader : {
								root : "rows",
								page : "page",
								total : "total",
								records : "records"
							},
				           pager: '#pager2', 
				           sortname: 'id', 
				           viewrecords: true, 
				           sortorder: "desc", 
				           caption:"Foo entities (JQrid Example)" }); 
			
			jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
		
		});
		
		</script>
	</head>
	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
		  <div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#"><spring:message code="title.secure.page" /></a>
			</div>
		  </div>
		</nav>
		<div class="jumbotron">
		  <div class="container">
			<p> 
				<h2><spring:message code="welcome" />: ${pageContext.request.userPrincipal.name}</h2>
		    </p>
		    <br>
		    <p>
				<a class="btn btn-primary btn-lg" href="<c:url value='/'/>" role="button"><spring:message code="home" /></a>
			</p>
		    <p>
				<a class="btn btn-primary btn-lg" href="<c:url value='/swagger-ui.html'/>" role="button"><spring:message code="go.api" /></a>
			</p>
		    <p>
				<a class="btn btn-danger btn-lg" href="<c:url value='/login?logout'/>" role="button"><spring:message code="logout" /></a>
			</p>
			</div>
		</div>
		<br>
		<br>
		<table id="list2"></table> <div id="pager2"></div>
	</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>

<style>

	html, body {
	    height: 100%;
	    background: #e2e2e2
	}
	
	.header {
	    background: #313b53;
		color: #e2e2e2;
		min-height: 100px;
	}
	
	.popup {
	    background-color: #FFFFFF;
	    border-radius: 10px;
	    box-shadow: 0 0 25px 5px #999999;
	    color: #111111;
	    min-width: 450px;
	    padding: 25px;
	}

	.button.b-close, .button.bClose {
	    border-radius: 7px;
	    box-shadow: none;
	    font: bold 131% sans-serif;
	    padding: 0 6px 2px;
	    position: absolute;
	    right: -7px;
	    top: -7px;
	}
	
	
	#label_user, #span_user{
	font-size: 75%;
	}
	
	#span_user{
	padding:.2em .6em .3em;
	color:#fff
	}
	
</style>

<div class="col-xs-8" style='background: url("../resources/images/fondo_header2.gif") repeat-x scroll 0 0 rgba(0, 0, 0, 0);
    height: 102px;'>
    <img class="img-responsive" style="height:70%" alt="templateApp" 
    src="../resources/images/templateApp.jpeg">
</div>
<div class="col-xs-4" style="vertical-align:bottom;background: url('../resources/images/fondo_header2.gif') repeat-x scroll 0 0 rgba(0, 0, 0, 0); height: 102px;" align="right">
	<label style="font-size: 75%; color: rgb(0, 0, 0); margin-top: 80px;">version: <spring:message code="application.version" /></label>
</div> 
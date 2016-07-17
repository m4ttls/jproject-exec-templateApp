// Variables Globales
var ACTION_CREATE = "CREATE_OBJECT";
var ACTION_UPDATE = "UPDATE_OBJECT";
var ACTION_DELETE = "DELETE_OBJECT";
var ACTION_READ = "READ_OBJECT";

var interval_id = null;
var period_millisecond = 240000 /* 4 minutos */;

var tiffInitialized = false;
//Variable temporal que indica si se visualizan o no los repos, hasta que se haga la division y se defina como proceder.
var REPOSITORIES_ENABLED = false; 

var formatDate = function(cellval, opts, rowObject, action) {
		if(cellval != null){
			var date = new Date(cellval+" 00:00:00");
			if(isNaN( date.getTime())){
				if($.isNumeric(cellval)){
					date = new Date(cellval)
				}else{
					var strDate = cellval.split("-");
					date = new Date(strDate[2], strDate[1] - 1, strDate[0]);
				}
			}
			
			return $.fn.fmatter.call(this, "date", date, $.extend({},
					$.jgrid.formatter.date, opts), rowObject, action);
		}
		return "";
};

// Funciones
function empty(data) {
	if (typeof (data) == 'number' || typeof (data) == 'boolean') {
		return false;
	}
	if (typeof (data) == 'undefined' || data === null) {
		return true;
	}
	if (typeof (data.length) != 'undefined') {
		return data.length == 0;
	}
	var count = 0;
	for ( var i in data) {
		if (data.hasOwnProperty(i)) {
			count++;
		}
	}
	return count == 0;
}

function generarUnico() {
	var date = new Date();
	var seconds = date.getSeconds();
	var milliseconds = date.getMilliseconds();
	var time = date.getTime();
	return "" + seconds + milliseconds + time;
}

function isNumeric(cadena) {
	return (/^([0-9])*$/.test(cadena));
}

function isLetrasAndZero(cadena) {
	return (/^([0A-Za-z])*$/.test(cadena));
}

function isLetras(cadena) {
	return (/^([A-Za-z])*$/.test(cadena));
}

function isGeolocation(cadena) {
	return (/^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/.test(cadena));
}

/**
 * Wrapper para los callbacks de las llamadas ajax que verifica si lo devuelto
 * es el formulario de login, en cuyo caso redirige efectivamente a ese
 * formulario
 */
function cbWrapper(data, funct) {
	if ($("#form_login", data).size() > 0)
		window.location.href = url_logout;
	else
		funct(data);
}

function refresh_page() {
	location.reload();
}

/** Utilidad para obtener un parámetro pasado por la URL */
function GetURLParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}

/**
 * Aplica restricciones de teclado a los campos que tienen asociadas las clases
 * Integer BigDecimal y Date
 */
function aplicarRestriccionesDataType(container) {

	$("#" + container + " .Integer").keypress(function(e) {
		if (e.which != 8 /* Bckspc */
				&& e.which != 0 /* Del */
				&& e.which != 13 /* Intro */
				&& e.which != 45 /* - */
				&& (e.which < 48 || e.which > 57)) {
			return false;
		}
	});
	
	$("#" + container + " .Color").keypress(function(e) {
		if (e.which != 8 /* Bckspc */
				&& e.which != 0 /* Del */
				&& e.which != 13 /* Intro */
				&& e.which != 45 /* - */
				&& (!(e.which >= 48 && e.which <= 57) &&  /* numeros */
				!(e.which >= 65 && e.which <= 70) &&  /* A-F */
				!(e.which >= 97 && e.which <= 102))) /* a-f */
		{
			return false;
		}
	});
	
	$("#" + container + " .Color").keyup(function(){
		if($(this).val().substr(0, 1) != null 
			&& $(this).val().substr(0, 1) != '#'){
				$(this).val('#'+$(this).val());
		}
		    
	    if($(this).val().length > 7){
			$(this).val($(this).val().substr(0, 7));
		}
	});

	$("#" + container + " .BigDecimal").keypress(function(e) {
		if (e.which != 8 /* Bckspc */
				&& e.which != 0 /* Del */
				&& e.which != 13 /* Intro */
				&& e.which != 44 /* , */
				&& e.which != 45 /* - */
				&& e.which != 46 /* . */
				&& (e.which < 48 || e.which > 57)) /* numeros */
		{
			return false;
		}
	});

	$("#" + container + " .Date").keypress(function(e) {
		if (e.which != 8 /* Bckspc */
				&& e.which != 0 /* Del */
				&& e.which != 13 /* Intro */
				&& e.which != 47 /* / */
				&& (e.which < 48 || e.which > 57)) /* numeros */
		{
			return false;
		}
	});

}

function formatDate(input) {
	var datePart = input.match(/\d+/g), year = datePart[0], // get only two
	// digits
	month = datePart[1], day = datePart[2];

	return day + '/' + month + '/' + year;
}

function checkErrors_(jqXHR, cerrarAuto) {
	if (jqXHR.responseText) { 
		if (String(jqXHR.responseText).indexOf('"userid":-1')!=-1) {
			window.location.href = url_logout;
		} else {

			// Dependiendo el formato, saca el mensaje del json o viene directo el string.
			var messageToShow;
			if (jqXHR.responseJSON != null && jqXHR.responseJSON.error != null && jqXHR.responseJSON.error.message !=null){
				if(jqXHR.responseJSON.error.exception 
						&& jqXHR.responseJSON.error.exception!='com.curcico.jproject.core.exception.BusinessException'){
					messageToShow = 'Ocurri\u00f3 un error interno.';
					if(jqXHR.responseJSON.error.errorId)
						messageToShow = messageToShow + ' C\u00f3digo de error: ' + jqXHR.responseJSON.error.errorId;
					
				} else {
					messageToShow = jqXHR.responseJSON.error.message;
				}
			}else{
				messageToShow = jqXHR.responseText;
			}
			
			if(!cerrarAuto){
				commons_modal_danger("ERROR", _(messageToShow));
			}else{
				commons_modal_danger_auto_close("ERROR", _(messageToShow));
			}
		}
	}
}

function checkErrors(jqXHR) {
	checkErrors_(jqXHR, false);
}

function commons_modal(content, cerrarAuto) {
	console.debug("commons_modal");
	$('.modal-backdrop').remove();
	$('#commons_modal').html('');
	$('#commons_modal')
			.html(
					'<div class="modal-dialog">'
							+ '<div id="lkt_modal_content" class="modal-content" ></div>'
							+ '</div>');
	$('.modal-dialog').css('width', '75%')
	$('#lkt_modal_content').html(content);
	$('#commons_modal').modal('show');
	$('#commons_modal').show();
	$('#commons_modal').css("z-index", 800);
	$('.modal-backdrop').css("z-index", 799);
	
	
	if(cerrarAuto){
		setTimeout(function(){
			$( "#commons_modal_alert_btn_close" ).trigger( "click" );
		}, 1000);
	}
}

function commons_wait(action) {
	console.debug("commons_wait: " + action);
	if (action && action == 'show') {
		$('#commons_modal').html('');
		$('#commons_modal').html(
				'<div style="padding: 20% 47%;">'
						+ '<img class="img-responsive" src="' + url_application
						+ '/resources/images/loading_onepage.gif">' + '</div>');
		$('#commons_modal').modal('show');
		interval_id = setInterval(dummieRequest, period_millisecond);
	} else {
		$('#commons_modal').modal('hide');
		$('#commons_modal').html('');
		$('.modal-backdrop').remove();
		clearInterval(interval_id);
		interval_id = null;
	}
}

/*
 * Funcion que realiza una peticion para que no caduque la http session.
 */
function dummieRequest() {
	$.ajax({
		url : url_application + '/listenerDummie/invoke',
		type : 'GET',
		dataType : 'text',
		beforeSend : function() {
			console.debug("beforeSend dummieRequest");
		},
		success : function(data) {
			console.debug("successSend dummieRequest: " + data);
		},
		error : function(jqXHR, ajaxOptions, thrownError) {
			checkErrors(jqXHR);
		}
	});
}

function commons_modal_danger(title, content, callback) {
	commons_modal_alert(title, content, 'alert-danger', callback);
}

function commons_modal_danger_auto_close(title, content, callback) {
	commons_modal_alert(title, content, 'alert-danger', callback, true);
}

function commons_modal_warning(title, content, callback) {
	commons_modal_alert(title, content, 'alert-warning', callback);
}

function commons_modal_info(title, content, callback) {
	commons_modal_alert(title, content, 'alert-info', callback);
}

function commons_modal_info_without_footer(title, content, callback) {
	commons_modal_alert_without_footer(title, content, 'alert-info', callback);
}

function commons_modal_success(title, content, callback) {
	commons_modal_alert(title, content, 'alert-success', callback);
}

function commons_modal_alert(title, content, type, callback, cerrarAuto) {
	console.debug("commons_modal_alert: " + " " + title + " " + type + " "
			+ callback);

	var div = document.createElement("div");
	div.setAttribute("id", "lkt_modal_principal_container");

	var header = document.createElement("div");
	header.setAttribute("class", "modal-header alert " + type);
	header.innerHTML = '<button class="close" aria-hidden="true" data-dismiss="modal" type="button"><i class="icon-small icon-remove"></i></button>'
			+ '<h4 class="modal-title" id="myModalLabel">' + title + '</h4>';

	var body = document.createElement("div");
	body.setAttribute("id", "lkt_modal_body");
	body.setAttribute("class", "modal-body");
	$(body).html(content);

	var footer = document.createElement("div");
	footer.setAttribute("class", "modal-footer");
	footer.innerHTML = '<button id="commons_modal_alert_btn_close"  type="button" class="btn btn-primary" data-dismiss="modal" '
			+ (callback ? ' onclick="' + callback + '" ' : '')
			+ ' >'
			+ (callback ? 'Aceptar' : 'Cerrar') + '</button>';

	$(div).append(header);
	$(div).append(body);
	$(div).append(footer);
	commons_wait('hide');

	commons_modal(div, cerrarAuto);
	$('input').blur();
}

function commons_modal_alert_without_footer(title, content, type, callback, cerrarAuto) {
	console.debug("commons_modal_alert: " + " " + title + " " + type + " "
			+ callback);

	var div = document.createElement("div");
	div.setAttribute("id", "lkt_modal_principal_container");

	var header = document.createElement("div");
	header.setAttribute("class", "modal-header alert " + type);
	header.innerHTML = '<button class="close" aria-hidden="true" data-dismiss="modal" type="button"><i class="icon-small icon-remove"></i></button>'
			+ '<h4 class="modal-title" id="myModalLabel">' + title + '</h4>';

	var body = document.createElement("div");
	body.setAttribute("id", "lkt_modal_body");
	body.setAttribute("class", "modal-body");
	$(body).html(content);

	$(div).append(header);
	$(div).append(body);
	commons_wait('hide');

	commons_modal(div, cerrarAuto);
	$('input').blur();
}

function sleep(miliseconds) {
	var currentTime = new Date().getTime();

	while (currentTime + miliseconds >= new Date().getTime()) {
	}
}

function enableUploadDiv(myDropzone, action) {
	enableUploadDiv(myDropzone, action, true, true, true, false, false)
}

function enableUploadDiv(myDropzone, action, verifyDocumentDefinition, cleanForm, hideDropzone, showSuccessMessage, removeFilesAfterSuccess) {
	if (myDropzone && myDropzone != null) {
		$('#uploadForm').show();
	} else {
		myDropzone = new Dropzone(
				"form#uploadForm",
				{
					init : function() {
						var submitButton = document
								.querySelector("#submit-all")
						this.on("removedfile", function(file) {
							//delete_sheet(file);
							if (this.files.length == 0) {
								$("#submit-all").hide();
							}
						});
						submitButton.addEventListener("click", function() {
							if($('select#select_family option').length > 0){
								$("#dialog-family").dialog({
									autoOpen : false,
									buttons : [{
										text: "Enviar",
										click : function() {
											if($('select#select_family option').length > 0){
												$('#family').val($('#select_family').val());
											}else{
												$('#family').val('');
											}
											$("#dialog-family").dialog("close");
											myDropzone.processQueue();
										}
									}],
									width : 200
								});
								$("#dialog-family").dialog("open");
							}else{
								myDropzone.processQueue();								
							}
							
							if (hideDropzone){
								$('#loadFile').hide();
							}
							
							if (cleanForm){
								limpiarForm('divFormulario');
							}
							if (verifyDocumentDefinition){
								getDocumentTypeDefinitionSucess(definicionDocumentoSeleccionado);
							}
							
						});
						this.on("addedfile", function(file) {
							$("#submit-all").show();
						});
						this.on("sendingmultiple", function(file) {
							$("#submit-all").attr('disabled', 'disabled');
							// alert("sendingmultiple");
						});
						this.on("error", function(file, response) {
							commons_modal_danger("Error", response.code + "  \n"
									+ response);
						});
						this.on("completemultiple", function(file, response) {
							$("#submit-all").removeAttr("disabled");
						});
						this.on("successmultiple", function(file) {
							// alert("successmultiple");
						});
						this.on("queuecomplete", function(file) {
							// alert("queuecomplete");
							// myDropzone.removeAllFiles();
						});
						// Execute when file uploads are complete
					    this.on("complete", function() {
					    	if (showSuccessMessage){
								commons_modal_success("Archivos subidos", "Archivos enviados correctamente!", 'location.reload()');
							}
					    });
					},
					url : url_application + action,
					thumbnailWidth : 100,
					thumbnailHeight : 100,
					maxThumbnailFilesize : 50,
					clickable : true,
					ignoreHiddenFiles : true,
					addRemoveLinks : false,
					autoProcessQueue : false,
					uploadMultiple : true,
					parallelUploads : 100,
					dictDefaultMessage : 'Arrastre aqu\u00ed los documentos para subir.',
					dictRemoveFile : 'Eliminar'
				});
		$('#uploadForm').show();
	}

}

function commons_modal_confirm(title, description, type, callback, data) {
	console.debug("commons_modal_alert: " + " " + title + " " + type + " "
			+ callback + " " + data);

	var div = document.createElement("div");
	div.setAttribute("id", "lkt_modal_principal_container");

	var header = document.createElement("div");
	header.setAttribute("class", "modal-header alert " + type);
	header.innerHTML = '<h4 class="modal-title" id="myModalLabel">' + title
			+ '</h4>';

	var body = document.createElement("div");
	body.setAttribute("id", "lkt_modal_body");
	body.setAttribute("class", "modal-body");

	var content = '<div align="center" style="font-size: 18px;">' + description + '</div>'
			+ '<br/>'
			+ '<div style="float:left;width:45%;">'
			+ '<input id="btn_confirm_ok" style="width: 100%;" onclick="executeJS('
			+ callback
			+ ','
			+ "'".concat(data).concat("'")
			+ ');" type="button" class="btn btn-info commons-button" value="Aceptar"></input>'
			+ '</div>'
			+ '<div style="float:right;width:45%;">'
			+ '<input id="btn_confirm_cancel" style="width: 100%;" onclick="confirmCancel();" type="button" class="btn btn-info commons-button" value="Cancelar"></input>'
			+ '</div>' + '<br/>'
	$(body).html(content);

	var footer = document.createElement("div");
	footer.setAttribute("class", "modal-footer");

	$(div).append(header);
	$(div).append(body);
	$(div).append(footer);
	commons_wait('hide');

	commons_modal(div);
}

function executeJS(callback, data) {
	callback(data);
	
}

function confirmCancel() {
	$('#commons_modal').modal('hide');
	return false;
}

function populateSelect(selectId, options, index, indexName){
	populateSelect(selectId, options, index, indexName, null);
}

function populateSelect(selectId, options, index, indexName, defaultItem){
	
	$('#'+selectId+' option').remove();

	var select = document.getElementById(selectId);
	if (defaultItem!=null){
		var option = document.createElement("option");
		option.value = -1;
		option.text = defaultItem;
		select.appendChild(option);
	}
	
	if(options != null && Object.keys(options).length > 0){
		for(var e in options) {
		    var option = document.createElement("option");
		    if(index != null){
		    	option.value = (options[e])[index];
		    }else{
		    	option.value = e;
		    }
		    if(indexName != null){
		    	option.text = (options[e])[indexName];
		    }else{
		    	option.text = options[e];
		    }
		    
	    	select.appendChild(option);
		    
		}
	}
}

function ajaxInvoker(url, sucessFunction){
	$.ajax({
		type : "GET",
		url : url,
		async:false,
		headers : {
			"Accept" : 'application/json; charset=utf-8'
		},
		success : function(respuesta) {
			if (respuesta.status == "OK") {
				if(respuesta.data)
					eval(sucessFunction)(respuesta.data);
				else if(respuesta.resultados)
					eval(sucessFunction)(respuesta.resultados);
				else
					eval(sucessFunction)(respuesta);
			} else if (respuesta.status == "ERROR") {
				// Parche para poder parsearlo en checkErrors
				respuesta.responseText =  respuesta.message;
				checkErrors(respuesta);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			checkErrors(jqXHR);
		}
	});
}

function getRowList(cantRegGrilla){	
	var list =[15, 30, 50, 100 ];
	var index=list.indexOf(cantRegGrilla);
	
	if(index==-1){
		list.push(cantRegGrilla);
	}
	
	return list;
}

function getRESTJsonCollection(aplicationPath) {
	var urlConsulta = url_application + aplicationPath;
	var result = null;
	$.ajax({
		type : "GET",
		url : urlConsulta,
		async:false,
		headers : {
			"Accept" : 'application/json; charset=utf-8'
		},
		beforeSend : function() {
			commons_wait('show');
		},
		success : function(data) {
			commons_wait('hide');
			if (data.status = "OK")
				result = data.resultados;
		},
		error : function(jqXHR, ajaxOptions, thrownError) {
			commons_wait('hide');
			checkErrors(jqXHR);
		}
	});
	return result;
}


function getJsonElement(aplicationPath) {
	var urlConsulta = url_application + aplicationPath;
	var result = null;
	$.ajax({
		type : "GET",
		url : urlConsulta,
		async:false,
		headers : {
			"Accept" : 'application/json; charset=utf-8'
		},
		beforeSend : function() {
			commons_wait('show');
		},
		success : function(data) {
			commons_wait('hide');
			if (data.status = "OK")
				result = data;
		},
		error : function(jqXHR, ajaxOptions, thrownError) {
			commons_wait('hide');
			checkErrors(jqXHR);
		}
	});
	return result;
}
	
	function addClickEventToDivs(divContainer, func){
		
		var formId = $('#'+divContainer ).prop('id');			
		frm = document.getElementById(formId);
		
		if (!frm) return;
		
		var listado=frm.children;
		
		for(i=0; i<listado.length; i++)
		{				
		  	var d =listado[i]; 
		  	if (d.getAttribute('clickeable') == 'true' && d.id.indexOf("wkf") > -1){
		  		d.onclick = func;
		    }
		}	
	}
	
	function selectNode(divContainer, nodeId){
		
		var formId = $('#'+divContainer ).prop('id');			
		frm = document.getElementById(formId);
		
		if (!frm) return;
		
		var listado=frm.children;
		
		// Busca el que estaba seleccionado antes y restaura el color
		for(i=0; i<listado.length; i++)
		{				
		  	var d =listado[i]; 
		  	if (d.getAttribute('nodeSelected') == 'true' && d.id.indexOf("wkf") > -1){
		  		
		  		perforNodeSelection(d, false, (d.getAttribute('origcolor')!=null ? d.getAttribute('origcolor') : null))
		    }
		}	
		
		formId = $('#'+nodeId ).prop('id');			
		d = document.getElementById(formId);
		
		perforNodeSelection(d, true, "#00CCFF")
	}
	
	function perforNodeSelection(node, select, color){
	
		node.setAttribute('nodeSelected', select);
		
		if (color !=null ){
			node.style.backgroundColor = color;
	}	
}
	function limpiarForm(div){
		
		var formId = $('#'+div ).prop('id');			
		frm = document.getElementById(formId);
		
		if (!frm) return;
		
		var listado=frm.children[0];
		
		for(i=0; i<listado.length; i++)
		{				
		  	var obj =listado[i]; 
			var type=obj.type;
			if(type=='text'){
				obj.value='';
			}
		}			
	} 

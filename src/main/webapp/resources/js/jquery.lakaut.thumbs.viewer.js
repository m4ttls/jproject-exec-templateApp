(function( $ ){
	
	var settings;
	var pagination;
	var thumbnails = new Array();
	var workingWithTiff = false;
	var isFirstPage = false;
	var option_defaults = {
			'rows': 	2,
			'columns': 	4, 
			'no_images': 'Documento sin im&aacute;genes', 
			'urlApp': '', 
			'urlThumb':'', 
			'urlImage': '', 
			'imageClass': 'thumbImage', 
			'noImageClass': 'thumbOther', 
			'onClickImage': null, 
			'onClickNoImage': null, 
			'afterRenderPage': null,
			'actionDeleteImg':null,
			'actionRotateImg':null, 
			'actionCustomImg':null
	};
	
	var methods = {
				init : function( options ) { 
					console.log('init');

					// vacio el contenedor 
					if($('#sig_thumbs_viewer_pagination_images').html()!=undefined){
						$('#sig_thumbs_viewer_pagination_images').twbsPagination('destroy');
					}

					$(this).html('');
					
					// Inicializo los valores defaults.
					settings = $.extend(option_defaults, options );
					// creo el paginador
					var divPaginador = document.createElement("div");
					divPaginador.setAttribute('id', 'sig_thumbs_viewer_div_paginador');
					divPaginador.setAttribute('style','width: 100%;display: block; margin-right: 10px; margin-left: 10px;height: 35px;');
					divPaginador.setAttribute('class','text-center row');
					$(divPaginador).html('<ul id="sig_thumbs_viewer_pagination_images" style="display: -moz-compact;"></ul>')
					$(this).append(divPaginador);
					
					// creo el contenedor de thumbnails
					var divContenedorThumbs = document.createElement("div");
					divContenedorThumbs.setAttribute('id', 'sig_thumbs_viewer_div_thumbnails');
					divContenedorThumbs.setAttribute('style','width: 100%;display: block; margin-right: 10px; margin-left: 10px;');
					divContenedorThumbs.setAttribute('class','text-center row');
					$(this).append(divContenedorThumbs);
					
					$('#sig_thumbs_viewer_pagination_images').hide();
										
				},
				show : function(data) {
					console.log('show');

					thumbnails = new Array();
					thumbnails = data.resultados;
					
					// Evalúo si existen resultados
					if (thumbnails.length==0) {
						var divThumbs = document.createElement("div");
						divThumbs.setAttribute('class','jumbotron');
						divThumbs.setAttribute('style','text-align: center;');
						$(divThumbs).html('<h2>' + settings['no_images'] + '</h2>')
						$('#sig_thumbs_viewer_div_thumbnails').append(divThumbs);
						$(this).show();
						return;
					}
					
					$('#sig_thumbs_viewer_div_thumbnails').empty();
					var cantThumbsPorPagina = eval(settings['rows']*settings['columns']);
					var cantidadPaginas = Math.ceil(thumbnails.length/cantThumbsPorPagina);
					var cantidadPaginas = Math.ceil(thumbnails.length/cantThumbsPorPagina);
					var cantidadPaginasVisibles = (cantidadPaginas>5?5:cantidadPaginas);
					
					preparePages();
					showPage(1);
					
					console.log('vacio el paginador');
					$('#sig_thumbs_viewer_pagination_images').empty();

					console.log('inicio el paginador');
					pagination = $('#sig_thumbs_viewer_pagination_images').twbsPagination({
						totalPages: cantidadPaginas,
						visiblePages: cantidadPaginasVisibles, 
						first: '<i class="icon-large icon-fast-backward"></i>',
						prev:  '<i class="icon-large icon-backward"></i>',
						next:  '<i class="icon-large icon-forward"></i>',
						last:  '<i class="icon-large icon-fast-forward"></i>',
						paginationClass: 'pagination pagination-sm pagination-centered', onPageClick: function (event, page) {
							console.log("Event click page: " + page );
							$(".thumb-page").hide();
							showPage(page);
							event.preventDefault();
						}			
				    });
					$('#sig_thumbs_viewer_pagination_images').attr('style', 'display:-moz-compact');
					//$('#sig_thumbs_viewer_pagination_images').hide();

					pagination.data('currentTotalPages', cantidadPaginas);
					console.log('seteo el paginador');
					//pagination.data('currentPage', 1);
					pagination.twbsPagination('init', {
						totalPages: cantidadPaginas,
						visiblePages: cantidadPaginasVisibles, 
						currentPage: 1
					});
					$(this).show();
				},
				clean : function( ) { 
					$('#sig_thumbs_viewer_div_thumbnails').empty();
					$('#sig_thumbs_viewer_pagination_images').empty();
					$(this).hide();
				},
				hide : function( ) { 
					$(this).hide();
				},
				update : function( content ) { 
				  //codigo
				}, 
				destroy : function (){
					if($('#sig_thumbs_viewer_pagination_images').html()!=undefined){
						$('#pagination-images').twbsPagination('destroy');
					}
					settings = $.extend(option_defaults, option_defaults);
					$(this).empty();
				}, 
				
		  };
	
	
	var preparePages = function (){
		console.log('preparePages');
		$('#sig_thumbs_viewer_div_thumbnails').empty();
		var cantThumbsPorPagina = eval(settings['rows']*settings['columns']);
		var pageName = "";	
		for(var i in thumbnails) {
			if(i%cantThumbsPorPagina==0){
				pageName = "thumb-page-" + (i/cantThumbsPorPagina+1);
				$('#sig_thumbs_viewer_div_thumbnails').append("<div id='" + pageName + "' class='thumb-page' />");
			}
		}
	};
	
	var showPage = function(page){
		console.log('showPage: ' + page);
		$(".thumb-page").hide();
		$(".thumb-page").empty();
		var pageName;
		var cantThumbsPorPagina = eval(settings['rows']*settings['columns']);
		var cantThumbsPorFila 	= eval(cantThumbsPorPagina/settings['rows']);
		var cantidadPaginas = Math.ceil(thumbnails.length/cantThumbsPorPagina);
		var cantidadPaginasVisibles = (cantidadPaginas>5?5:cantidadPaginas);
		var porcentajeAncho = (100/eval(settings['columns']));
		var divAbierto = false;
		var object = null;
		console.log('thumbnails.length: ' + thumbnails.length);
		isFirstPage = true;
		var showThumbs = false;
		for(var i in thumbnails) {
			if(i%(cantThumbsPorFila)==0){
				pageName = "thumb-page-" + Math.floor(i/cantThumbsPorPagina+1);
				rowName = "thumb-row-" + ((i/cantThumbsPorFila)+1);
				if(page && i>=eval((page-1)*cantThumbsPorPagina) && i<(page*cantThumbsPorPagina)){
					showThumbs = true;
				} else {
					showThumbs = false;
				}
				$("#" + rowName).empty();
				$('#' + pageName).append("<div id='" + rowName + "' class='thumbsrow' ></div>");
				console.log(rowName);
			}
			var path = (thumbnails[i]).path;
			var name = (thumbnails[i]).name;
			var mimetype = (thumbnails[i]).mimetype;
			var thumbnail = (thumbnails[i]).thumbnail;
			var classAction = "";
			if(mimetype==null || (mimetype!=null && mimetype.indexOf("image") > -1 )){
				classAction = settings['imageClass'];
			} else {
				classAction = settings['noImageClass'];
			}
			if (mimetype && mimetype.indexOf("tif") > -1 ) {
				loadImageTif(settings['urlApp'], settings['urlThumb'] + path, name, classAction, rowName, eval(i)+1, porcentajeAncho, showThumbs);
			} else {
				loadImageGeneric(settings['urlApp'], settings['urlImage'] + path, settings['urlThumb'] + thumbnail, name, classAction, rowName, eval(i)+1, porcentajeAncho, showThumbs, settings['actionDeleteImg'], settings['actionRotateImg']);
			}		
		};
		$( "a." + settings['imageClass'] ).off('click');
		$( "a." + settings['imageClass'] ).click(function(e) {
			if(settings['onClickImage'] && settings['onClickImage']!=null && settings['onClickImage']!=''){
				eval(settings['onClickImage'])(this, e);
			} 
		});
		$( "a." + settings['noImageClass'] ).off('click');
		$( "a." +settings['noImageClass'] ).click(function(e) {
			if(settings['onClickNoImage'] && settings['onClickNoImage']!=null && settings['onClickNoImage']!=''){
				eval(settings['onClickNoImage'])(this, e);
			} 
		});
		
		//** ACTIONS **//
		if(settings['actionDeleteImg'] && settings['actionDeleteImg']!=null && settings['actionDeleteImg']!=''){
			$("a." + settings['actionDeleteImg']).off('click');
			$("a." + settings['actionDeleteImg']).click(function(e){
				object = this;
				$.each(thumbnails, function(index, value){
					if(value.name == object.id){
						eval(settings['actionDeleteImg'])(value);
					}
				})
			});
		}
		
		if(settings['actionRotateImg'] && settings['actionRotateImg']!=null && settings['actionRotateImg']!=''){
			$("a." + settings['actionRotateImg']).off('click');
			$("a." + settings['actionRotateImg']).click(function(e){
				object = this;
				$.each(thumbnails, function(index, value){
					if(value.name == object.id){
						eval(settings['actionRotateImg'])(value);
					}
				})
			});
		}
		
		if(settings['actionCustomImg'] && settings['actionCustomImg']!=null && settings['actionCustomImg']!=''){
			$("a." + settings['actionCustomImg']).off('click');
			$("a." + settings['actionCustomImg']).click(function(e){
				object = this;
				$.each(thumbnails, function(index, value){
					if(value.name == object.id){
						eval(settings['actionCustomImg'])(value);
					}
				})
			});
		}
		//** ACTIONS **//
		
		/**
		 * Delay para evitar que se muestren los thumbnails hasta 
		 * que no esten dibujados completamente, igual con los botones
		 * de acciones (esto solo es estetico).
		 */
		$("#thumb-page-" + page).delay(0).show(0);
		
		if(settings['afterRenderPage'] && settings['afterRenderPage']!=null && settings['afterRenderPage']!=''){
			eval(settings['afterRenderPage'])("thumb-page-" + page);
		}
	};
	
	var loadImageTif = function (urlApp, path, name, classAction, rowName, orden, porcentajeAncho, showThumb) {

		if(!workingWithTiff){
			Tiff.initialize({TOTAL_MEMORY: 16777216 * 10});
			workingWithTiff=true;
		}
		
		var xhr = new XMLHttpRequest();
		xhr.open('GET', urlApp + '?fullPath=' + path + '/' + name);
		xhr.responseType = 'arraybuffer';
		xhr.onload = function (e) {
			var buffer = xhr.response;
			var tiff = new Tiff({buffer: buffer, });
			var canvas = tiff.toCanvas();
			// var width = tiff.width();
			// var height = tiff.height();
			if (canvas) {
				
				var html_imagen = document.createElement("div");
				html_imagen.setAttribute('class', 'col-xs-3 thumbnail ' + classAction);
				html_imagen.setAttribute('href', urlApp + '?fullPath=' + path + '/' + name);
				html_imagen.setAttribute('title', 'Hoja ' + orden);
				html_imagen.setAttribute('style', 'width: ' + porcentajeAncho + '%; padding-left:1px;padding-right:1px;');
				
				if(showThumb){
					var html_thumb = document.createElement("img");
					html_thumb.setAttribute('class', 'img-responsive');
					html_thumb.setAttribute('id', name.substr(0, name.lastIndexOf('.')) || name);
					html_thumb.setAttribute('name', name.substr(0, name.lastIndexOf('.')) || name);
					html_thumb.setAttribute('src', tiff.toDataURL());
					
					$(html_imagen).html(html_thumb);					
				}
				$('#' + rowName).append(html_imagen);
				
				$( "div." + settings['imageClass'] ).off('click');
				$( "div." + settings['imageClass'] ).click(function(e) {
					if(settings['onClickImage'] && settings['onClickImage']!=null && settings['onClickImage']!=''){
						eval(settings['onClickImage'])(this, e);
					} 
				});
			};
		};
		xhr.send();
	};

	var loadImageGeneric = function (urlApp, path, thumbnail, name, classAction, rowName, orden, porcentajeAncho, showThumb, actionDelete, actionRotate, thumbnails) {
		var div_imagen = document.createElement("div");
		div_imagen.setAttribute('class', 'col-xs-3');
		div_imagen.setAttribute('style', 'width: ' + porcentajeAncho + '%; padding-left:1px;padding-right:1px; border:1px solid #ddd;border-radius:4px;background:#fff;position:relative;');
		
		var div_row_1 = document.createElement("div");
		div_row_1.setAttribute('class', 'row-1');
		$(div_imagen).append(div_row_1);

		var html_imagen = document.createElement("a");
		html_imagen.setAttribute('class', 'thumbnail ' + classAction);
		html_imagen.setAttribute('href', urlApp + path);
		html_imagen.setAttribute('target', '_blank');
		html_imagen.setAttribute('title', 'Hoja ' + orden);
		html_imagen.setAttribute('style', 'border:none');
		$(div_row_1).append(html_imagen);
		
		if(showThumb){
			var html_thumb = document.createElement("img");
			html_thumb.setAttribute('class', 'img-responsive');
			html_thumb.setAttribute('id', name.substr(0, name.lastIndexOf('.')) || name);
			html_thumb.setAttribute('name', name.substr(0, name.lastIndexOf('.')) || name);
			html_thumb.setAttribute('src',  urlApp + thumbnail);
			$(html_imagen).html(html_thumb);
		}
		
		//Actions div 
		var isDelete = (actionDelete && actionDelete != null && actionDelete != '');
		var isRotate = (actionRotate && actionRotate != null && actionRotate != '');
		
		if(isDelete || isRotate){			
			var div_row_2 = document.createElement("div");
			div_row_2.setAttribute('class', 'row-2');
			div_row_2.setAttribute('style', 'position:absolute;bottom:0;left:0; background:#fff; padding:0px 0px 2px 2px;border-radius');
			$(div_imagen).append(div_row_2);
			
			if(isDelete){
				$(div_row_2).append('<a id="'+ name+'" class="'+actionDelete+' btn btn-danger" style="padding:3px;"><i title="Eliminar" class="glyphicon glyphicon-trash"></i></a>');
			}
			if(isRotate){
				$(div_row_2).append('<a id="'+ name+'" class="'+actionRotate+' btn btn-primary" style="padding:3px; margin-left: 2px;"><i title="Girar" class="glyphicon glyphicon-retweet"></i></a>');
			}
		}
		
		$('#' + rowName).append(div_imagen);
	};
	
	$.fn.thumbsViewer=function( method ){
		// Si existe la función la llamamos
		if ( methods[method] ) {
		  return methods[ method ]
		  .apply( this, 
			Array.prototype.slice.call( arguments, 1 )
		  );
		} else if ( typeof method === 'object' || ! method ) {
		  //Si no se pasa ningún parámetro o el parámetro es 
		  //un objeto de configuración llamamos al inicializador	
		  return methods.init.apply( this, arguments );
		} else {
		  //En el resto de los casos mostramos un error
		  $.error( 'La función ' +  method + ' no existe en jQuery.sig_thumbs_viewer' );
		}    
	};
	
})( jQuery );
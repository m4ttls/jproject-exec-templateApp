(function($){
	$.fn.extend({
		loadComboActions: function(documentId,combo){
			var urlConsulta = url_application + '/actions/getActions/'+ documentId;
			
			$.ajax({
				url : urlConsulta,
				type : 'POST',
				async : false,
				headers : {
					"Accept" : 'application/json; charset=utf-8'
				},
				beforeSend : function() {
					commons_wait('show');
				},
				success : function(data) {
					$('#'+combo).empty();
					if(data.length!=0){
						populateSelect(combo, data, 'id', 'code');
					}
				},
				error : function(jqXHR, ajaxOptions, thrownError) {
					commons_wait('hide');
					checkErrors(jqXHR);
				}
			});	
			
				
			}
		});
	
	$.fn.extend({
		changeStatus: function(idDoc,actionid){
			
			var urlConsulta = url_application + '/documents/changeStatus/' + idDoc + '/' + actionid;
			
			$.ajax({
				url : urlConsulta,
				type : 'POST',
				async : false,
				headers : {
					"Accept" : 'application/json; charset=utf-8'
				},
				beforeSend : function() {
					commons_wait('show');
				},
				success : function(data) {					
					commons_wait('hide');								
				},
				error : function(jqXHR, ajaxOptions, thrownError) {
					commons_wait('hide');
					checkErrors(jqXHR);
				}
			});	
			}
		});
	
})(jQuery)




 


$(document).ready(function(){
	var userLang = navigator.language || navigator.userLanguage;
	if(userLang) 
		userLang = userLang.substr(0, 2)
	else
		userLang = "es"
	include (url_application + "/resources/i18n/commons_messages_" + userLang +".js");
	
});

function include(file_path){
	var j = document.createElement("script");
	j.type = "text/javascript";
	j.src = file_path;
	document.body.appendChild(j);
}

function _(s) {
	   if (typeof(i18n)!='undefined' && i18n[s]) {
	      return i18n[s];
	   }
	   return s;
	}
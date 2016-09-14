package com.curcico.jproject.web.controller;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;


@Component
public class BaseController {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	LocaleResolver locale;
	
		// Metodos
	protected String getBundle(String key, Object[] args,  Locale locale) {
		try {
			return messageSource.getMessage(key, args, locale)   ;
		} catch (MissingResourceException e){
			if(key!= null && !key.isEmpty()){
				return key.replace('.', ' ');
			}
			throw e;
		}
	}
	
	protected String getBundle(String key, Locale locale) {
		return getBundle(key, null, locale);
	}
	
	protected String getBundle(String key) {		
		return getBundle(key, null, Locale.getDefault());
	}

	protected String getParametersToURL(Map<String, Object> map){
		String result = "'";
		if(map != null){
			result += "?";
			boolean isFirst = false;
			for (java.util.Map.Entry<String, Object> e : map.entrySet()) {
				if(!isFirst){
					result += "&";
				}else{
					isFirst = true;
				}
				result += e.getKey() + "=" + e.getValue();
			}
			
		}
		
		return result+= "'";
	}

	
}

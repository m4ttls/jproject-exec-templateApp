package com.curcico.jproject.web.controller;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.curcico.jproject.core.entities.BaseEntity;
import com.curcico.jproject.core.services.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

/** Servicios REST de ejemplo
 * @author acurci
 *
 */
abstract class CommonsController<T extends BaseEntity, S extends Service<T>> extends BaseController {
	
	Logger logger = Logger.getLogger(getClass());


	@SuppressWarnings("unchecked")
	public final Class<T> typeParameterClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	
	@Autowired
	protected S service;
	
	
	public CommonsController() {
    }
	
	
	/** Realiza la conversión de json a objetos y viceversa
	 * @param dataBinder
	*/
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(this.typeParameterClass, new PropertyEditorSupport() {
	    	
	    	Logger logger = Logger.getLogger(getClass());
	        T value;
	        
	        @Override
	        public T getValue() {
	            return value;
	        }

			@SuppressWarnings("unchecked")
			@Override
	        public void setAsText(String text) throws IllegalArgumentException {
	        	try {
	        		value  = (T) new ObjectMapper().readValue(text, value.getClass());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					value = null;
				}
	        }
	    });
	}
}

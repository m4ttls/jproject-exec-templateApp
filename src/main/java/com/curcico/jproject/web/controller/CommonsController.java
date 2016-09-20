package com.curcico.jproject.web.controller;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.daos.ManagerFetchs;
import com.curcico.jproject.core.daos.SearchOption;
import com.curcico.jproject.core.entities.BaseEntity;
import com.curcico.jproject.core.services.Service;
import com.curcico.jproject.core.wrapper.GridWrapper;
import com.curcico.jproject.entities.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import springfox.documentation.annotations.ApiIgnore;

/** Generic REST Service
 * @author acurci (mailto:acurci@gmail.com)
 *
 * @param <T>
 * @param <S>
 */
abstract class CommonsController<T extends BaseEntity, S extends Service<T>> 
		extends BaseController { 
	Logger logger = Logger.getLogger(getClass());


	@SuppressWarnings("unchecked")
	public final Class<T> typeParameterClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Autowired
	protected S service;
	
	public CommonsController() {
    }
	
	protected void isAuthorized(HttpServletRequest request, 
			HttpServletResponse response) throws AccessDeniedException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()){
			String clase = typeParameterClass.getName().toString().toUpperCase();
			String[] path = clase.trim().split("\\.");
			String entityName = path[path.length-1];
			//TODO buscar la manera de obtener el prefijo ROLE_ de la configuracion de spring
			String role = "ROLE_"  +  request.getMethod().toUpperCase() + "_" + entityName;
			if(auth.getAuthorities().contains(role))
				return;
		}
		throw new AccessDeniedException("access.denied");
	}
	
	@PreAuthorize(value="@isAuthorized(principal, #request, #response)")
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	@ApiOperation(value = "Return a GridWrapper object with a Foo collection.")
	public GridWrapper<T> getCollection( 
			@ApiParam(value = "Filters over Foo entity", required = false, example = "{\"field\":\"id\", \"op\":\"eq\", \"data\":\"1\"}") 
				@RequestParam(value = "filters", required = false) String filters,
			@ApiParam(value = "Fetchs required", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr,
			@ApiParam(value = "Page to show", required = false) 
				@RequestParam(value = "page",    required = false) Integer page,
			@ApiParam(value = "Records per page", required = false) 
				@RequestParam(value = "rows",    required = false) Integer rows,
			@ApiParam(value = "Order By", required = false) 
				@RequestParam(value = "sidx",    required = false) String sidx,
			@ApiParam(value = "Order mode (ASC or DESC)", required = false)
				@RequestParam(value = "sord",    required = false) String sord, 
			@ApiParam(value = "Search field", required = false)
				@RequestParam(value = "searchField",  required = false) String searchField,
			@ApiParam(value = "Search operator", required = false)
				@RequestParam(value = "searchOper",   required = false) String searchOper,
			@ApiParam(value = "Search data", required = false)
				@RequestParam(value = "searchString", required = false) String searchString, 
			@ApiIgnore @ApiParam(value="Dummy param for generics discovery", hidden=true, access="internal", required=false)  T dummy, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		List<ConditionEntry> conditions = ConditionEntry.transformFilters(this.typeParameterClass, filters);
		if(searchField!=null && searchField.length()>0)
			conditions.add(ConditionEntry.getConditionSimple(Foo.class, searchField, SearchOption.getSearchOption(searchOper), searchString));
		Set<ManagerFetchs> fetchs = null;
		GridWrapper<T> wrapper = (GridWrapper<T>)service.findByFiltersGridWrapper(conditions, 
				page, rows, sidx, sord, fetchs);
		return wrapper;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Return a Foo entity by Id ")
	public T getEntity(
			@ApiParam(value = "Foo entity Id", required = true) 
				@PathVariable Integer id, 
			@ApiParam(value = "Fetchs required", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr, 
				HttpServletRequest request, 
				HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		Set<ManagerFetchs> fetchs =null;
		T ejemplo = service.loadEntityById(id, fetchs);
		if (ejemplo==null) throw new NotFoundException("entity.not.found");
		return ejemplo;
	}

	
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete a Foo entity.")
	@ResponseBody
	public T delete(
			@PathVariable Integer id, 
			@RequestParam(value = "version",   required = true) Integer version, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		T entity = service.delete(id, version);
		return entity;
	}
	
	
	@ResponseStatus(value= HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Create a new Foo entity.")
	public T save(@RequestBody T entity, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		entity = service.createOrUpdate(entity, 1);
		return entity;
	}	

    
	@ResponseStatus(value= HttpStatus.OK)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Update a Foo entity.")
	public T update(@RequestBody T entity, 
					@PathVariable Integer id, 
					HttpServletRequest request, 
					HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		entity = service.createOrUpdate(entity, 1);
		return entity;

	} 
	
	/** Init Bider
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

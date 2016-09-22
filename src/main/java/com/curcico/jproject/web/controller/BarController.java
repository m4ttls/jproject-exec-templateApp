package com.curcico.jproject.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.daos.ConditionSimple;
import com.curcico.jproject.core.daos.ManagerFetchs;
import com.curcico.jproject.core.daos.SearchOption;
import com.curcico.jproject.core.wrapper.GridWrapper;
import com.curcico.jproject.entities.Bar;
import com.curcico.jproject.entities.Foo;
import com.curcico.jproject.services.BarService;
import com.curcico.jproject.webutils.controller.CommonController;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

@RestController
@Api(description = "Foo's Bar REST service.")
@RequestMapping(path = "/foos/{fooId}")
public class BarController 
	extends CommonController {

	@Autowired
	BarService service;
	
	Logger logger = Logger.getLogger(getClass());

	protected void isAuthorized(HttpServletRequest request, 
			HttpServletResponse response) throws AccessDeniedException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()){
			String clase = Bar.class.getName().toString().toUpperCase();
			String[] path = clase.trim().split("\\.");
			String entityName = path[path.length-1];
			//TODO buscar la manera de obtener el prefijo ROLE_ de la configuracion de spring
			String role = "ROLE_"  +  request.getMethod().toUpperCase() + "_" + entityName;
			logger.debug("Check authorities from " + auth.getName() + " for " + role);
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			boolean hasRole = false;
			for (GrantedAuthority authority : authorities) {
				hasRole = authority.getAuthority().equals(role);
				if (hasRole) {
					return;
				}
			}
		}
		throw new AccessDeniedException("access.denied");
	}
	
	@GetMapping(value="/bars", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	@ApiOperation(value = "Return a GridWrapper object with the Bar collection.")
	public GridWrapper<Bar> getCollection(
			@ApiParam(value = "Foo entity Id", required = true) 
				@PathVariable Integer fooId, 
			@ApiParam(value = "Filters over Bar entity", required = false, example = "{\"field\":\"id\", \"op\":\"eq\", \"data\":\"1\"}") 
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
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		List<ConditionEntry> conditions = ConditionEntry.transformFilters(Bar.class, filters);
		if(searchField!=null && searchField.length()>0)
			conditions.add(ConditionEntry.getConditionSimple(Bar.class, searchField, SearchOption.getSearchOption(searchOper), searchString));
		conditions.add(new ConditionSimple("foo.id", SearchOption.EQUAL, fooId));
		Set<ManagerFetchs> fetchs = ManagerFetchs.transformFetchs(fetchsStr);
		GridWrapper<Bar> wrapper = (GridWrapper<Bar>)service.findByFiltersGridWrapper(conditions, 
				page, rows, sidx, sord, fetchs);
		return wrapper;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/bars/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Return a Bar entity by Id ")
	public Bar getEntity(
			@ApiParam(value = "Foo entity Id", required = true) 
				@PathVariable Integer fooId, 
			@ApiParam(value = "Bar entity Id", required = true) 
				@PathVariable Integer id, 
			@ApiParam(value = "Fetchs required", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr, 
				HttpServletRequest request, 
				HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		List<ConditionEntry> conditions = new ArrayList<>();
		conditions.add(new ConditionSimple("foo.id", SearchOption.EQUAL, fooId));
		conditions.add(new ConditionSimple("id", SearchOption.EQUAL, id));
		Set<ManagerFetchs> fetchs = ManagerFetchs.transformFetchs(fetchsStr);
		Bar ejemplo = service.loadEntityByFilters(conditions, fetchs);
		if (ejemplo==null) throw new NotFoundException("entity.not.found");
		return ejemplo;
	}

	
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping(path = "/bars/{id}")
	@ApiOperation(value = "Delete a Bars entity from de Foo's colleción.")
	@ResponseBody
	public Bar delete(
			@ApiParam(value = "Foo entity Id", required = true) 
				@PathVariable Integer fooId, 
			@ApiParam(value = "Bar entity Id", required = true) 
				@PathVariable Integer id, 
			@ApiParam(value = "Bar entity version", required = true) 
				@RequestParam(value = "version",   required = true) Integer version, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		Bar bar = new Bar();
		bar.setId(id);
		bar.setVersion(version);
		Foo foo = new Foo();
		foo.setId(fooId);
		return service.delete(bar, -1);
	}
	
	
	@ResponseStatus(value= HttpStatus.CREATED)
	@PostMapping(path = "/bars", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Create a new Bar entity in to Foo's collection.")
	public Bar save(
			@ApiParam(value = "Foo entity Id", required = true) 
				@PathVariable Integer fooId,
			@ApiParam(value = "Bar entity", required = true) 	
				@RequestBody Bar entity, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		isAuthorized(request, response);
		Foo foo = new Foo();
		foo.setId(fooId);
		entity.setFoo(foo);
		entity = service.createOrUpdate(entity, 1);
		return entity;
	}	

    
	@ResponseStatus(value= HttpStatus.OK)
    @PutMapping(path = "/bars/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Update a Bar entity.")
	public Bar update(@ApiParam(value = "Foo entity Id", required = true) 
						@PathVariable Integer fooId,
					  @ApiParam(value = "Bar entity", required = true) 	
					    @RequestBody Bar entity, 
					  @ApiParam(value = "Bar Id", required = true) 	
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
	    dataBinder.registerCustomEditor(Bar.class, new PropertyEditorSupport() {
	    	
	    	Logger logger = Logger.getLogger(getClass());
	    	Bar value;
	        
	        @Override
	        public Bar getValue() {
	            return value;
	        }

			@SuppressWarnings("unchecked")
			@Override
	        public void setAsText(String text) throws IllegalArgumentException {
	        	try {
	        		value  = (Bar) new ObjectMapper().readValue(text, value.getClass());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					value = null;
				}
	        }
	    });
	}
	
	
	
}

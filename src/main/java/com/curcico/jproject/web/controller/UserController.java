package com.curcico.jproject.web.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.daos.ConditionSimple;
import com.curcico.jproject.core.daos.ManagerFetchs;
import com.curcico.jproject.core.daos.SearchOption;
import com.curcico.jproject.core.wrapper.GridWrapper;
import com.curcico.jproject.entities.Bar;
import com.curcico.jproject.entities.User;
import com.curcico.jproject.services.UserService;
import com.curcico.jproject.webutils.controller.CommonController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "User's REST service.")
@RequestMapping(path = "/users")
public class UserController 
	extends CommonController {

	@Autowired
	UserService service;
	
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
	
	@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	@ApiOperation(value = "Return a GridWrapper object with the User collection.")
	public GridWrapper<User> getCollection(
			@ApiParam(value = "User entity Id", required = true) 
				@PathVariable Integer userId, 
			@ApiParam(value = "Filters over User entity", required = false, example = "{\"field\":\"id\", \"op\":\"eq\", \"data\":\"1\"}") 
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
		conditions.add(new ConditionSimple("user.id", SearchOption.EQUAL, userId));
		Set<ManagerFetchs> fetchs = ManagerFetchs.transformFetchs(fetchsStr);
		GridWrapper<User> wrapper = (GridWrapper<User>)service.findByFiltersGridWrapper(conditions, 
				page, rows, sidx, sord, fetchs);
		return wrapper;
	}	
	
}

package com.curcico.jproject.web.controller;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.curcico.jproject.entities.Ejemplo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import springfox.documentation.annotations.ApiIgnore;

/** Servicios REST de ejemplo
 * @author acurci
 *
 * @param <T>
 * @param <S>
 */
abstract class CommonsController<T extends BaseEntity, S extends Service<T>> extends BaseController { 
	Logger logger = Logger.getLogger(getClass());


	@SuppressWarnings("unchecked")
	public final Class<T> typeParameterClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Autowired
	protected S service;
	
	public CommonsController() {
    }
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	@ApiOperation(value = "Retorna un objeto GridWrapper con una coleccion de entidades Ejemplo ")
	public GridWrapper<T> getCollection( 
			@ApiParam(value = "Filtros sobre la entidad", required = false, example = "{\"field\":\"id\", \"op\":\"eq\", \"data\":\"1\"}") 
				@RequestParam(value = "filters", required = false) String filters,
			@ApiParam(value = "Listado de instanciaciones requeridas de composiciones de la entidad", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr,
			@ApiParam(value = "Pagina solicitada", required = false) 
				@RequestParam(value = "page",    required = false) Integer page,
			@ApiParam(value = "Cantidad de registros por pagina", required = false) 
				@RequestParam(value = "rows",    required = false) Integer rows,
			@ApiParam(value = "Campo por el que se desea ordenar los resultados", required = false) 
				@RequestParam(value = "sidx",    required = false) String sidx,
			@ApiParam(value = "Modo por el que se desea ordenar los resultados (asc = Ascendente / desc = Descendente)", required = false)
				@RequestParam(value = "sord",    required = false) String sord, 
			@ApiParam(value = "Campo por el que se desea buscar", required = false)
				@RequestParam(value = "searchField",  required = false) String searchField,
			@ApiParam(value = "Operador de busqueda", required = false)
				@RequestParam(value = "searchOper",   required = false) String searchOper,
			@ApiParam(value = "Valor por el que se desea filtrar la busqueda", required = false)
				@RequestParam(value = "searchString", required = false) String searchString, 
			@ApiIgnore @ApiParam(value="Solo para el reconocimiento por generics", hidden=true, access="internal", required=false)  T dummy
			) throws Exception {
		List<ConditionEntry> conditions = ConditionEntry.transformFilters(this.typeParameterClass, filters);
		if(searchField!=null && searchField.length()>0)
			conditions.add(ConditionEntry.getConditionSimple(Ejemplo.class, searchField, SearchOption.getSearchOption(searchOper), searchString));
		Set<ManagerFetchs> fetchs = null;
		GridWrapper<T> wrapper = (GridWrapper<T>)service.findByFiltersGridWrapper(conditions, 
				page, rows, sidx, sord, fetchs);
		return wrapper;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Retorna la entidad Ejemplo correspondiente al id ",     
				  notes = "Retorna una unica entidad coincidente con el id pasado en la url. "
					+ "Admite el parametro fetch el listado de las composiciones a inicializar. ")
	public T getEntity(
			@ApiParam(value = "Id de la entidad", required = true) 
				@PathVariable Integer id, 
			@ApiParam(value = "Listado de instanciaciones requeridas de composiciones de la entidad", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr) 
					throws Exception {
		Set<ManagerFetchs> fetchs =null;
		T ejemplo = service.loadEntityById(id, fetchs);
		if (ejemplo==null) throw new NotFoundException("entity.not.found");
		return ejemplo;
	}

	
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Elimina una entidad Ejemplo ")
	@ResponseBody
	public T delete(
			@PathVariable Integer id, 
			@RequestParam(value = "version",   required = true) Integer version) 
									throws Exception {
		T entity = service.delete(id, version);
		return entity;
	}
	
	
	@ResponseStatus(value= HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Crea una nueva entidad")
	public T save(@RequestBody T entity) throws Exception {
		entity = service.createOrUpdate(entity, 1);
		return entity;
	}	

    
	@ResponseStatus(value= HttpStatus.OK)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Actualiza una entidad Ejemplo ")
	public T update(@RequestBody T entity, 
					@PathVariable Integer id) 
									throws Exception {
		entity = service.createOrUpdate(entity, 1);
		return entity;

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

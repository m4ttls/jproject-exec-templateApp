package com.curcico.jproject.web.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.daos.ManagerFetchs;
import com.curcico.jproject.core.daos.SearchOption;
import com.curcico.jproject.core.wrapper.GridWrapper;
import com.curcico.jproject.entities.Ejemplo;
import com.curcico.jproject.services.EjemploService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** Servicios REST de ejemplo
 * @author acurci
 *
 */
@Controller
@Api(value = "Operaciones sobre la entidad Ejemplo")
@RequestMapping(path = "/ejemplos")
public class EjemploController extends CommonsController<Ejemplo, EjemploService> {

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Elimina una entidad Ejemplo ")
	@ResponseBody
	public ResponseEntity<Object> delete(
			@PathVariable Integer id, 
			@RequestParam(value = "userId",    required = true) Integer userId) 
									throws Exception {
		service.delete(service.loadEntityById(id), userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
    
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Retorna un objeto GridWrapper con una coleccion de entidades Ejemplo ")
	public @ResponseBody ResponseEntity<GridWrapper<Ejemplo>> getCollection( 
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
				@RequestParam(value = "searchString", required = false) String searchString	
			)
					throws Exception {
		List<ConditionEntry> conditions = ConditionEntry.transformFilters(this.typeParameterClass, filters);
		if(searchField!=null && searchField.length()>0){
			conditions.add(ConditionEntry.getConditionSimple(Ejemplo.class, searchField, SearchOption.getSearchOption(searchOper), searchString));
		}
		Set<ManagerFetchs> fetchs = null;
		GridWrapper<Ejemplo> wrapper = 
				service.findByFiltersGridWrapper(conditions, 
				page, rows, sidx, sord, fetchs);
		return new ResponseEntity<GridWrapper<Ejemplo>>(wrapper, HttpStatus.OK);
	}
	

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Retorna por id una ",     
				  notes = "Retorna una unica entidad coincidente con el id pasado en la url. "
					+ "Admite el parametro fetch el listado de las composiciones a inicializar. ")
	public @ResponseBody ResponseEntity<Ejemplo> getEntity(
			@ApiParam(value = "Id de la entidad", required = true) 
				@PathVariable Integer id, 
			@ApiParam(value = "Listado de instanciaciones requeridas de composiciones de la entidad", required = false) 
				@RequestParam(value = "fetchs",  required = false) String fetchsStr) 
					throws Exception {
		Set<ManagerFetchs> fetchs =null;
		Ejemplo ejemplo = service.loadEntityById(id, fetchs);
		if (ejemplo==null) return new ResponseEntity<Ejemplo>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Ejemplo>(ejemplo, HttpStatus.OK);
	}	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Crea una entidad Ejemplo ")
	public @ResponseBody ResponseEntity<Ejemplo> save(@RequestBody Ejemplo e) throws Exception {
		e = service.createOrUpdate(e, 1);
		return new ResponseEntity<Ejemplo>(e, HttpStatus.CREATED);
	}	

	
	@PostMapping(path="/file")	
    @ApiOperation(value = "Ejemplo de llamado con un multipart")
	public @ResponseBody String saveMultipart(
			@ApiParam(value = "Entidad", required = true) @RequestParam(value="ejemplo", required=true)   String ejemplo, 
			@ApiParam(value = "Archivo", required = true) @RequestPart(value="file", required=true) 	  MultipartFile file, 
			HttpServletRequest request) 
					throws Exception {
		String status = "";
		Ejemplo ejemploObj = new ObjectMapper().readValue(ejemplo, Ejemplo.class);
		Enumeration<String> headers = request.getHeaderNames();
		for (; headers.hasMoreElements();) {
			String key = headers.nextElement();
			status += key + " : " + request.getHeader(key) + "\n"; 
		}
		status += (file==null?"File is null - ":"Filesize is " + file.getSize()) + "\n"
				+ (ejemploObj==null?"Ejemplo is null\n":"Ejemplo is: " + ejemploObj.getCodigo());
		return status;
	}
    
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Actualiza una entidad Ejemplo ")
	@ResponseBody
	public ResponseEntity<Ejemplo> update(@RequestBody Ejemplo e, 
					@PathVariable Integer id) 
									throws Exception {
		e = service.createOrUpdate(e, 1);
		return new ResponseEntity<Ejemplo>(e, HttpStatus.OK);

	} 
}

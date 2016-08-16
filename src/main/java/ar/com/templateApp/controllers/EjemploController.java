package ar.com.templateApp.controllers;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.templateApp.entities.Ejemplo;
import ar.com.templateApp.services.EjemploService;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.wrapper.GridWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Servicios REST de ejemplo
 * @author acurci
 *
 */
@Controller
@RequestMapping("/ejemplo")
public class EjemploController {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	EjemploService service;
	
	@Value("${image.path}")
	private String imagePath;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("HelloWorldPage");
	}
	
	
//	@PreAuthorize("hasAuthority('EJEMPLO_GET')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getAscendenteSigea(@PathVariable Integer id) 
													throws Exception {
		return new ResponseEntity<Object>(service.loadEntityById(id), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAuthority('EJEMPLO_GET')")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getByFilters(
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) throws Exception {
		GridWrapper<Ejemplo> wrapper = 
				service.findByFiltersGridWrapper(ConditionEntry.transformNativeFilters(filters), 
				page, rows, sidx, sord, null);
		return new ResponseEntity<Object>(wrapper, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Ejemplo save(@RequestBody Ejemplo e) throws Exception {
		return service.createOrUpdate(e, 1);
	}	
	
	
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<byte[]> getImage() throws IOException {
		String filename = "test.jpg";
		Path path = Paths.get(imagePath + File.separator + filename);
	    byte[] data = Files.readAllBytes(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("Content-Disposition", "inline; filename=" + filename);
	    headers.setContentLength(data.length);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);

	}
	
	
	
	/** Realiza la conversión de json a objetos y viceversa
	 * @param dataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(Ejemplo.class, new PropertyEditorSupport() {
	    	
	    	Logger logger = Logger.getLogger(getClass());
	        Object value;
	        @Override
	        public Object getValue() {
	            return value;
	        }

	        @Override
	        public void setAsText(String text) throws IllegalArgumentException {
	        	try {
	        		value  = new ObjectMapper().readValue(text, Ejemplo.class);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					value = null;
				}
	        }
	    });
	} 
}

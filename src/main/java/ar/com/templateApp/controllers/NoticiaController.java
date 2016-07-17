package ar.com.templateApp.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.templateApp.services.EjemploService;

/** Servicios REST de ejemplo
 * @author acurci
 *
 */
@Controller
@RequestMapping("/ejemplo")
public class NoticiaController {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	EjemploService service;
	
	/** Redirección a una pagina
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unsegurePage.htm", method = RequestMethod.GET)
	public ModelAndView showMobile(ModelMap modelMap, 
			@RequestParam(value = "id", required = false) Integer id) 
					throws Exception {
        ModelAndView mv = new ModelAndView("unsegurePage");
        mv.addObject("id", id);
        return mv;
	}
	
	
	/** Servicio web
	 * */
	@PreAuthorize("hasAuthority('EJEMPLO_GET')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getAscendenteSigea(@PathVariable Integer id) 
													throws Exception {
		return new ResponseEntity<Object>(service.findById(id), HttpStatus.OK);
	}


}

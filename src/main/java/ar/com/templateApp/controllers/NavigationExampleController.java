package ar.com.templateApp.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pages")
public class NavigationExampleController {

	Logger logger = Logger.getLogger(getClass());

	@RequestMapping(value="/login.htm", method = RequestMethod.GET)
	public ModelAndView showMobile(ModelMap modelMap) 
					throws Exception {
		return new ModelAndView("login");
	}
	
	
}

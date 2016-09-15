package com.curcico.jproject.web.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController extends BaseController{
	
	Logger logger = Logger.getLogger(getClass());

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Locale locale) {
		logger.error("TEST LOG");
		model.addAttribute("message", getBundle("welcome.message", locale));
		return "hello";
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {
		logger.error("TEST LOG");
		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", name);
		return model;
	}

}
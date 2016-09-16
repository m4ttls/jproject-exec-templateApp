package com.curcico.jproject.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.curcico.jproject.entities.Ejemplo;
import com.curcico.jproject.services.EjemploService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "Operaciones sobre una entidad ejemplo.")
@RequestMapping(path = "/ejemplos")
public class EjemploController 
	extends CommonsController<Ejemplo, EjemploService> {

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

}

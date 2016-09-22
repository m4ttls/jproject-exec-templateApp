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

import com.curcico.jproject.entities.Foo;
import com.curcico.jproject.services.FooService;
import com.curcico.jproject.webutils.controller.GenericsController;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "Foo REST service.")
@RequestMapping(path = "/foos")
public class FooController 
	extends GenericsController<Foo, FooService> {


	
	@PostMapping(path="/file")	
    @ApiOperation(value = "Multipart + json example")
	public @ResponseBody String saveMultipart(
			@ApiParam(value = "Entidad", required = true) 
				@RequestParam(value="foo", required=true)   String foo, 
			@ApiParam(value = "Archivo", required = true) 
				@RequestPart(value="file", required=true) 	MultipartFile file, 
			HttpServletRequest request) 
					throws Exception {
		String status = "";
		Foo fooObj = new ObjectMapper().readValue(foo, Foo.class);
		Enumeration<String> headers = request.getHeaderNames();
		for (; headers.hasMoreElements();) {
			String key = headers.nextElement();
			status += key + " : " + request.getHeader(key) + "\n"; 
		}
		status += (file==null?"File is null - ":"Filesize is " + file.getSize()) + "\n"
				+ (fooObj==null?"Foo is null\n":"Foo is: " + fooObj.getCode());
		return status;
	}

}

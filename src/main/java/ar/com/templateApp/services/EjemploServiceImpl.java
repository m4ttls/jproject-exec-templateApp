package ar.com.templateApp.services;

import org.springframework.stereotype.Service;

import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;

import ar.com.templateApp.daos.EjemploDao;
import ar.com.templateApp.entities.Ejemplo;

@Service
public class EjemploServiceImpl extends
		TimeRangeEntityServiceImpl<Ejemplo, EjemploDao> implements
		EjemploService {

}

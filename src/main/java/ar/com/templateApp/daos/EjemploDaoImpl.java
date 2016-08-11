package ar.com.templateApp.daos;

import org.springframework.stereotype.Repository;

import ar.com.templateApp.entities.Ejemplo;

import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;

@Repository
public class EjemploDaoImpl extends TimeRangeEntityDaoImpl<Ejemplo>
							implements EjemploDao{
	
}

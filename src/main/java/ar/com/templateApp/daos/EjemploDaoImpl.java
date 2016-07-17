package ar.com.templateApp.daos;

import org.springframework.stereotype.Repository;

import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;

import ar.com.templateApp.entities.Ejemplo;

@Repository
public class EjemploDaoImpl extends TimeRangeEntityDaoImpl<Ejemplo>
							implements EjemploDao{

	// Constructor
	public EjemploDaoImpl() {
		super(Ejemplo.class);
	}	
}

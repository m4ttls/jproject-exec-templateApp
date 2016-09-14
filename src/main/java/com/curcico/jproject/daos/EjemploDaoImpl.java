package com.curcico.jproject.daos;

import org.springframework.stereotype.Repository;



import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;
import com.curcico.jproject.entities.Ejemplo;

@Repository
public class EjemploDaoImpl extends TimeRangeEntityDaoImpl<Ejemplo	>
							implements EjemploDao{
	
}

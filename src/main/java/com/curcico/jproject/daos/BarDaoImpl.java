package com.curcico.jproject.daos;

import org.springframework.stereotype.Repository;

import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;
import com.curcico.jproject.entities.Bar;

@Repository
public class BarDaoImpl extends TimeRangeEntityDaoImpl<Bar>
							implements BarDao{
	
}

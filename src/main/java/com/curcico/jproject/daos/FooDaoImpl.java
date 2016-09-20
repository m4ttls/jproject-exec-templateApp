package com.curcico.jproject.daos;

import org.springframework.stereotype.Repository;



import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;
import com.curcico.jproject.entities.Foo;

@Repository
public class FooDaoImpl extends TimeRangeEntityDaoImpl<Foo	>
							implements FooDao{
	
}

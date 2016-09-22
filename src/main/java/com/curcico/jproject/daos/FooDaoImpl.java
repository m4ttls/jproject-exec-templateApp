package com.curcico.jproject.daos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.curcico.jproject.core.daos.ManagerFetchs;
import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;
import com.curcico.jproject.entities.Foo;

@Repository
public class FooDaoImpl extends TimeRangeEntityDaoImpl<Foo	>
							implements FooDao{
	
	
	/**Declare the availables fetchs here */
	@Override
	public Set<ManagerFetchs> getFetchs() {
		Set<ManagerFetchs> fetchs = new HashSet<ManagerFetchs>();
		fetchs.add(new ManagerFetchs("bars"));
		return fetchs;
	}
	
}

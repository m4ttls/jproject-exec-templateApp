package com.curcico.jproject.daos;

import org.springframework.stereotype.Repository;

import com.curcico.jproject.core.daos.TimeRangeEntityDaoImpl;
import com.curcico.jproject.entities.User;

@Repository
public class UserDaoImpl extends TimeRangeEntityDaoImpl<User>
implements UserDao{
	
}
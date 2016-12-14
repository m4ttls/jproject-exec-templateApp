package com.curcico.jproject.services;

import org.springframework.stereotype.Service;

import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;
import com.curcico.jproject.daos.UserDao;
import com.curcico.jproject.entities.User;

@Service
public class UserServiceImpl extends
TimeRangeEntityServiceImpl<User, UserDao> implements
UserService {

}

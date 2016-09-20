package com.curcico.jproject.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.curcico.jproject.core.exception.BaseException;
import com.curcico.jproject.core.exception.BusinessException;
import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;
import com.curcico.jproject.daos.FooDao;
import com.curcico.jproject.entities.Foo;

@Service
public class FooServiceImpl extends
		TimeRangeEntityServiceImpl<Foo, FooDao> implements
		FooService {
	
	@Override
	protected boolean entityValidate(Foo entity) throws BaseException {
		if(entity.getCode()==null || entity.getCode().length()<4)
			throw new BusinessException("code.required");
		return super.entityValidate(entity);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public Foo createOrUpdate(Foo entity, Integer userId) throws BaseException {
		Foo e = new Foo();
		if(entity.getId()!=null && !entity.getId().equals(0))
			e = this.loadEntityById(entity.getId());
		e.extractMutableValues(entity);
		return super.createOrUpdate(e, userId);
	}

}

package com.curcico.jproject.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.curcico.jproject.core.exception.BaseException;
import com.curcico.jproject.core.exception.BusinessException;
import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;
import com.curcico.jproject.daos.EjemploDao;
import com.curcico.jproject.entities.Ejemplo;

@Service
public class EjemploServiceImpl extends
		TimeRangeEntityServiceImpl<Ejemplo, EjemploDao> implements
		EjemploService {
	
	@Override
	protected boolean entityValidate(Ejemplo entity) throws BaseException {
		if(entity.getCodigo()==null || entity.getCodigo().length()<4)
			throw new BusinessException("code.required");
		return super.entityValidate(entity);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public Ejemplo createOrUpdate(Ejemplo entity, Integer userId) throws BaseException {
		Ejemplo e = new Ejemplo();
		if(entity.getId()!=null)
			e = this.loadEntityById(entity.getId());
		e.extractMutableValues(entity);
		return super.createOrUpdate(e, userId);
	}

}

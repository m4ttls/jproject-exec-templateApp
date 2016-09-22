package com.curcico.jproject.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcico.jproject.core.daos.ConditionEntry;
import com.curcico.jproject.core.daos.ConditionSimple;
import com.curcico.jproject.core.daos.SearchOption;
import com.curcico.jproject.core.exception.BaseException;
import com.curcico.jproject.core.exception.BusinessException;
import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;
import com.curcico.jproject.daos.BarDao;
import com.curcico.jproject.entities.Bar;
import com.curcico.jproject.entities.Foo;

@Service
public class BarServiceImpl extends
		TimeRangeEntityServiceImpl<Bar, BarDao> implements
		BarService {
	
	@Autowired
	FooService fooService;
	
	@Override
	protected synchronized boolean entityValidate(Bar entity) throws BaseException {
		if(entity.getDate()==null) entity.setDate(new java.util.Date());
		if(entity.getDetail()==null || entity.getDetail().isEmpty())
			throw new BusinessException("detail.required");
		if(entity.getFoo()==null || entity.getFoo().getId()==null)
			throw new BusinessException("foo.reference.required");
		Foo foo = fooService.loadEntityById(entity.getFoo().getId());
		if(foo==null)
			throw new BusinessException("foo.reference.not.found");
		entity.setFoo(foo);	
		return super.entityValidate(entity);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public Bar delete(Bar entity, Integer userId) throws BaseException {
		if(entity==null || entity.getId()==null)
			throw new BusinessException("entity.can.not.be.null");
		List<ConditionEntry> conditions = new ArrayList<>();
		conditions.add(new ConditionSimple("foo.id", SearchOption.EQUAL, entity.getFoo().getId()));
		conditions.add(new ConditionSimple("id", SearchOption.EQUAL, entity.getId()));
		Bar bar = this.loadEntityByFilters(conditions);
		if(bar==null)
			throw new BusinessException("entity.not.found");
		bar.setVersion(entity.getVersion());
		return super.delete(bar, userId);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public Bar createOrUpdate(Bar entity, Integer userId) throws BaseException {
		if(entity.getId()!=null){
			List<ConditionEntry> conditions = new ArrayList<>();
			conditions.add(new ConditionSimple("foo.id", SearchOption.EQUAL, entity.getFoo().getId()));
			conditions.add(new ConditionSimple("id", SearchOption.EQUAL, entity.getId()));
			Bar bar = this.loadEntityByFilters(conditions);
			if(bar==null)
				throw new BusinessException("entity.not.found");
			bar.setVersion(entity.getVersion());			
		}
		return super.createOrUpdate(entity, userId);
	}
	

}

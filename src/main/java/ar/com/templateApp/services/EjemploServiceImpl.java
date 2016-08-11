package ar.com.templateApp.services;

import org.springframework.stereotype.Service;

import ar.com.templateApp.daos.EjemploDao;
import ar.com.templateApp.entities.Ejemplo;

import com.curcico.jproject.core.exception.BaseException;
import com.curcico.jproject.core.exception.BusinessException;
import com.curcico.jproject.core.services.TimeRangeEntityServiceImpl;

@Service
public class EjemploServiceImpl extends
		TimeRangeEntityServiceImpl<Ejemplo, EjemploDao> implements
		EjemploService {
	
	@Override
	protected boolean entityValidate(Ejemplo entity) throws BaseException {
		if(entity.getCodigo()==null || entity.getCodigo().length()<4)
			throw new BusinessException("El código es obligatorio y debe tener un mínimo de 4 caracteres");
		return super.entityValidate(entity);
	}

}

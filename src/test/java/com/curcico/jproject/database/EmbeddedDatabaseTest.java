package com.curcico.jproject.database;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.curcico.jproject.core.exception.BaseException;
import com.curcico.jproject.entities.Bar;
import com.curcico.jproject.services.BarService;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmbeddedDatabaseTest {

	@Autowired
	BarService barSrv;
	
	@Before
	public void setup() {
		
	}	
	
	@Test
	public void insertTest(){
		Bar bar = new Bar();
		bar.setId(1);
		bar.setDetail("Test #"+bar.getId());
		try {
			barSrv.createOrUpdate(bar, 1);
			assertTrue( barSrv.findAll().size() > 0 );
		} catch (BaseException e) {
			assertTrue(false);
		}
	}
}

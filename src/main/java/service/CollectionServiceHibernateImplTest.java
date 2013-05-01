package service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import domain.Collection;

public class CollectionServiceHibernateImplTest {
	
	private static ICollecionService collecionService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		collecionService = new CollectionServiceHibernateImpl();
	}

	@Test
	public void testInsert() {
		Collection col = new Collection();
		col.setName("test col1");
		
		Long id = collecionService.insertOrUpdate(col);
		
		assertNotNull(id);
	}

}

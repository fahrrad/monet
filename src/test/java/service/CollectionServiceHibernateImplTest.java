package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Collection;
import domain.Work;

public class CollectionServiceHibernateImplTest {

	private static ICollecionService collecionService;
	private static IWorkService workService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		collecionService = new CollectionServiceHibernateImpl();
		workService = new WorkServiceHibernateImpl();
	}
	
	
	@After
	public void destroy(){
		for(Collection col : collecionService.getAll()){
			System.out.println("about to delete " + col.getId());
			collecionService.delete(col.getId());
		}
	}

	@Test
	public void testInsertEmptyCollection() {
		Collection col = new Collection();
		col.setName("test col1 blahblab");

		Long id = collecionService.insertOrUpdate(col);

		assertNotNull(id);
	}

	@Test
	public void testGetById() {
		Collection col = new Collection();
		col.setName("test col1");

		Long id = collecionService.insertOrUpdate(col);

		Collection found = collecionService.getById(id);
		assertEquals(col.getName(), found.getName());
		assertEquals(col.getWorks().size(), found.getWorks().size());

	}
	
	@Test
	public void testDeleteCollections(){
		Collection col = new Collection();
		col.setName("delete this collection");

		Long id = collecionService.insertOrUpdate(col);
		assertNotNull(id);
		
		collecionService.delete(id);
		
		col = collecionService.getById(id);
		assertNull(col);
	}
	
	@Test
	public void testSavingWithWorks() {
		Work w1 = new Work("title", "creator");
		workService.insertOrUpdate(w1);
		
		Collection col = new Collection();
		col.setName("test col");
		collecionService.insertOrUpdate(col);
		
		col.addWork(w1);
		collecionService.insertOrUpdate(col);
		
		Collection found = collecionService.getByName("test col");
		assertEquals(1, found.getWorks().size());
		assertEquals("Work should be what we put in", w1, found.getWorks().get(0));
		
	}

	@Test
	public void testGettingCollectionByName() {
		Collection col = new Collection();
		col.setName("uniqueCol");

		collecionService.insertOrUpdate(col);

		Collection found = collecionService.getByName("uniqueCol");

		assertEquals(col.getId(), found.getId());
	}

	@Test
	public void testGettingAllTheCollections() {
		Collection col = new Collection();
		col.setName("col24");

		collecionService.insertOrUpdate(col);
		Collection col2 = new Collection();
		col2.setName("col33");

		collecionService.insertOrUpdate(col2);

		List<Collection> collections = collecionService.getAll();

		assertTrue(collections.size() >= 2);
		assertTrue(collections.contains(col2));
		
		

	}

	@Test
	public void testRenamingCollection() {
		Collection col = new Collection();
		col.setName("col2");

		Long id = collecionService.insertOrUpdate(col);

		col = collecionService.getById(id);

		col.setName("new name");
		collecionService.insertOrUpdate(col);

		Collection found = collecionService.getById(id);
		assertEquals("new name", found.getName());

	}
	
	@Test(expected=HibernateException.class)
	public void testNoDuplicateCollectionNames(){
		Collection col1 = new Collection();
		col1.setName("dup");
		collecionService.insertOrUpdate(col1);
		
		Collection col2 = new Collection();
		col2.setName("dup");
		collecionService.insertOrUpdate(col2);
	}

}

package service;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.HibernateException;
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
	public void testSAvingWithWorks() {
		Work w1 = new Work("daiseys", "manot");
		workService.insertOrUpdate(w1);
		Work w2 = new Work("daiseys 2", "manot");
		workService.insertOrUpdate(w2);
		Work w3 = new Work("daiseys 3", "manot");
		workService.insertOrUpdate(w3);

		Collection col = new Collection("manot");
		col.addWork(w1);
		col.addWork(w2);
		col.addWork(w3);

		Long id = collecionService.insertOrUpdate(col);
		Collection found = collecionService.getById(id);

		assertEquals(3, found.getWorks().size());
		assertEquals("manot", found.getWorks().get(0).getCreator());
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
		col.setName("col33");

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

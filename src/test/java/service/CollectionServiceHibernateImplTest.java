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
		
		for(Work work : workService.getAll()){
			workService.delete(work.getId());
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
	public void testWhatHappensToCascading(){
		assertEquals("Expected 0 work", 0, workService.getAll().size());

		Collection col1 = new Collection("Col1");
		Long colId = collecionService.insertOrUpdate(col1);
		
		Work w1 = new Work("title1", "creator1");
		Long workId = workService.insertOrUpdate(w1);
		w1.setId(workId);
		
		assertEquals("Expected 1 work", 1, workService.getAll().size());
		assertEquals("Expected 1 collection", 1, collecionService.getAll().size());
		
		assertNotNull(colId);
		assertNotNull(workId);
		
		col1.addWork(w1);
		collecionService.insertOrUpdate(col1);
		col1 = collecionService.getById(colId);

		assertEquals("One work", 1, col1.getWorks().size());
		
		// Should be there after select
		w1 = workService.getById(workId);
		assertEquals("One collection", 1, w1.getCollecties().size());
		
		
		assertEquals("Expected 1 work", 1, workService.getAll().size());
		assertEquals("Expected 1 collection", 1, collecionService.getAll().size());
		
		collecionService.delete(colId);
		w1 = workService.getById(workId);
		
		assertEquals("Expected 1 work", 1, workService.getAll().size());
		assertEquals("Expected 0 collection", 0, collecionService.getAll().size());
		assertEquals("No collection", 0, w1.getCollecties().size());
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
		Long workId = workService.insertOrUpdate(w1);
		w1.setId(workId);
		
		Collection col = new Collection();
		col.setName("test col");
		Long colId = collecionService.insertOrUpdate(col);
		col.setId(colId);
		
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

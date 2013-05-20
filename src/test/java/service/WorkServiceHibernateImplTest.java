package service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import domain.Work;

public class WorkServiceHibernateImplTest {

	private static IWorkService workService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workService = new WorkServiceHibernateImpl();
	}

	@Test
	public void testGetById() {
		Work work = new Work();
		work.setCreator("picasso");
		work.setTitle("guernica");

		Long id = workService.insertOrUpdate(work);

		System.out.println("id = " + id);
		assertNotNull(id);

		Work foundWork = workService.getById(id);
		assertEquals(foundWork, work);

	}

	@Test
	public void testInsertOrUpdate() {
		Work work = new Work();
		work.setCreator("picasso");
		work.setTitle("guernica");

		Long id = workService.insertOrUpdate(work);

		System.out.println("id = " + id);
		assertNotNull(id);

	}

	@Test
	public void testIfIdsGetFilledIn() {
		Work work = new Work();
		work.setCreator("picasso");
		work.setTitle("guernica");

		assertEquals(null, work.getId());

		Long id = workService.insertOrUpdate(work);

		assertNotNull(id);
		assertEquals(id, work.getId());
	}
	
	
	@Test
	public void testGetAllWork(){
		Work work = new Work();
		work.setCreator("picasso");
		work.setTitle("guernica");
		
		Work work2 = new Work();
		work2.setCreator("picasso");
		work2.setTitle("guernica");
		
		Work work3 = new Work();
		work3.setCreator("picasso");
		work3.setTitle("guernica");
		
		workService.insertOrUpdate(work);
		workService.insertOrUpdate(work2);
		workService.insertOrUpdate(work3);
		
		int workCount = workService.getAll().size();
		
		assertTrue(workCount >= 3 );
	}
	
	
	@Test
	public void testGetByQuery(){
		Work work = new Work();
		work.setCreator("picasso");
		work.setTitle("guernica");
		
		Work work2 = new Work();
		work2.setCreator("dali");
		work2.setTitle("rozen");
		
		Work work3 = new Work();
		work3.setCreator("ward");
		work3.setTitle("aurore at night");
		
		long id1 = workService.insertOrUpdate(work);
		long id2 = workService.insertOrUpdate(work2);
		long id3 = workService.insertOrUpdate(work3);
		
		int workCount = workService.getByQuery("from Work where title like '%aurore%'").size();
		assertTrue(workCount == 1 );
	}
}

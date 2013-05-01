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
}

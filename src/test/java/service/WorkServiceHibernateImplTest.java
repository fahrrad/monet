package service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Work;

public class WorkServiceHibernateImplTest {

	private static IWorkService workService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workService = new WorkServiceHibernateImpl();
	}
	
	@Before
	public void setup(){
		for(Work work : workService.getAll()){
			workService.delete(work.getId());
		}
		
	}
	
	@Test
	public void testdelete(){
		Work work = new Work();
		work.setCreator("picasso2");
		work.setTitle("guernica2");
		
		Long id = workService.insertOrUpdate(work);
		
		assertNotNull(id);
		
		workService.delete(id);
		
		Work found = workService.getById(id);
		System.out.println(found);
		
		assertNull(found);
		
		
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
	
	
	@SuppressWarnings("unused")
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
	
	@Test
	public void testSavingAWork(){
		Work work = new Work();
		work.setOpmerking("Opmerking");
		work.setAdresEigenaar("adres eigenaar");
		work.setAfbeeldingspad("afbeeldingspad");
		work.setBreedte(20.00);
		work.setHoogte(10.00);
		work.setCreator("kunstenaar");
		work.setJaar(2010);
		work.setMedium("medium");
		work.setPersonen("personen");
		work.setThema("Thema");
		work.setTitle("Titel");
		work.setVorigeEigenaar("vorige eigenaar");
		
		
		long id = workService.insertOrUpdate(work);
		
		Work found = workService.getById(id);
		
		assertEquals("opmerking", "Opmerking", found.getOpmerking());
		assertEquals("adres eigenaar", "adres eigenaar", found.getAdresEigenaar());
		assertEquals("afbeeldingspad", "afbeeldingspad", found.getAfbeeldingspad());
		assertEquals("hoogte", 20.00, found.getBreedte(), 0.0001);
		assertEquals("hoogte", 10.00, found.getHoogte(), 0.0001);
		assertEquals("kunstenaar", "kunstenaar", found.getCreator());
		assertEquals("jaar", 2010, found.getJaar());
		assertEquals("medium", "medium", found.getMedium());
		assertEquals("Opmerking", "Opmerking", found.getOpmerking());
		assertEquals("personen", "personen", found.getPersonen());
		assertEquals("Thema", "Thema", found.getThema());
		assertEquals("Titel", "Titel", found.getTitle());
		assertEquals("vorige eigenaar", "vorige eigenaar", found.getVorigeEigenaar());
		
		
	}
}

package com.honchar.springDemo.chapter07.hibernate_crud;

//import com.honchar.springDemo.chapter07.hibernate_crud.config.AdvancedConfig;
import com.honchar.springDemo.chapter07.hibernate_crud.dao.SingerDao;
import com.honchar.springDemo.chapter07.hibernate_crud.entities.Album;
import com.honchar.springDemo.chapter07.hibernate_crud.entities.Singer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SingerDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(SingerDaoTest.class);

	private GenericXmlApplicationContext ctx;
	//private GenericApplicationContext ctx;
	private SingerDao singerDao;

	@BeforeAll
	public void setUp(){
		//ctx = new AnnotationConfigApplicationContext(AdvancedConfig.class);
		// OR //
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context-init.xml");
		ctx.refresh();
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		singerDao = ctx.getBean(SingerDao.class);
		assertNotNull(singerDao);
	}

	@Test
	public void testFindAll(){
		List<Singer> singers = singerDao.findAll();
		assertEquals(3, singers.size());
		listSingers(singers);
	}

	@Test
	public void testFindAllWithAlbum(){
		List<Singer> singers = singerDao.findAllWithAlbum();
		assertEquals(3, singers.size());
		listSingersWithAlbum(singers);
	}

	@Test
	public void testFindByID(){
		Singer  singer = singerDao.findById(1L);
		assertNotNull(singer);
		logger.info(singer.toString());
	}

	@Test
	public void testInsert(){
		Singer singer = new Singer();
		singer.setFirstName("BB");
		singer.setLastName("King");
		singer.setBirthDate(new Date(
				(new GregorianCalendar(1940, Calendar.SEPTEMBER, 16)).getTime().getTime()));

		Album album = new Album();
		album.setTitle("My Kind of Blues");
		album.setReleaseDate(new java.sql.Date(
				(new GregorianCalendar(1961, Calendar.AUGUST, 18)).getTime().getTime()));
		singer.addAlbum(album);

		album = new Album();
		album.setTitle("A Heart Full of Blues");
		album.setReleaseDate(new java.sql.Date(
				(new GregorianCalendar(1962, Calendar.APRIL, 20)).getTime().getTime()));
		singer.addAlbum(album);

		singerDao.save(singer);
		assertNotNull(singer.getId());

		List<Singer> singers = singerDao.findAllWithAlbum();
		assertEquals(3, singers.size());
		listSingersWithAlbum(singers);
	}

	@Test
	public void testUpdate(){
		Singer singer = singerDao.findById(1L);
		assertNotNull(singer);
		assertEquals("Mayer", singer.getLastName());
		Album album = singer.getAlbums().stream().filter(a -> a.getTitle().equals("Battle Studies"))
				.findFirst().orElse(null);

		singer.setFirstName("John Clayton");
		singer.removeAlbum(album);
		singerDao.save(singer);

		listSingersWithAlbum(singerDao.findAllWithAlbum());
	}

	@Test
	public void testDelete(){
		Singer singer = singerDao.findById(4L);
		//making sure such singer exists
		assertNotNull(singer);
		singerDao.delete(singer);

		listSingersWithAlbum(singerDao.findAllWithAlbum());
	}

	private static void listSingers(List<Singer> singers) {
		logger.info(" ---- Listing singers:");
		for (Singer singer : singers) {
			logger.info(singer.toString());
		}
	}

	private static void listSingersWithAlbum(List<Singer> singers) {
		logger.info(" ---- Listing singers with instruments:");
		singers.forEach(s -> {
			logger.info(s.toString());
			if (s.getAlbums() != null) {
				s.getAlbums().forEach(a -> logger.info("\t" + a.toString()));
			}
			if (s.getInstruments() != null) {
				s.getInstruments().forEach(i -> logger.info("\tInstrument: " + i.getInstrumentId()));
			}
		});
	}

	@AfterAll
	public void tearDown(){
		ctx.close();
	}

}

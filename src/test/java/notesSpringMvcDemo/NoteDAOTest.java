package notesSpringMvcDemo;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

//import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alekseyk99.spring.RepositoryConfig;
import com.alekseyk99.spring.model.Note;
import com.alekseyk99.spring.repository.NoteDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@Transactional
public class NoteDAOTest {

	@Import({RepositoryConfig.class})
	@Configuration
    static class ContextConfiguration {
	
		/**
		 * Test database properties 
		 * @return PropertyPlaceholderConfigurer PlaceHolder with test properties
		 */
		@Bean
	    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	    {
	        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
	        Properties properties = new Properties();
	        properties.put("jdbc.driverClassName","org.hsqldb.jdbcDriver");
	        properties.put("jdbc.url","jdbc:hsqldb:mem:sandboxDb");
	        properties.put("jdbc.username","sa");
	        properties.put("jdbc.password","");
	        properties.put("jpa.generate_ddl","true");
	        properties.put("jpa.show_sql","true");
	        properties.put("jpa.database_platform","org.hibernate.dialect.HSQLDialect");
	        ppc.setProperties(properties);
	        return ppc;
	    }

	}
	
	@Autowired
	private NoteDAO noteDao;
	
	@Test
	public void shouldAddNote() {
	    //assertEquals("Check DB is empty first", 0, noteDao.getAllNotes().size());
	    assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	noteDao.addNote(new Note().setSubject("subject 1").setText("test дом test house flat"));
	    //assertEquals("Check Note has been created", 1, noteDao.getAllNotes().size());
    	assertThat(noteDao.getAllNotes()).as("Check Note has been created").hasSize(1);
	}

	@Test
	public void shouldDeleteNote() {
    	noteDao.addNote(new Note().setSubject("subject 1").setText("test дом test house flat"));
    	List<Note> list = noteDao.getAllNotes();
	    //assertEquals("Check Note has been created", 1, list.size());
    	assertThat(noteDao.getAllNotes()).as("Check Note has been created").hasSize(1);
	    noteDao.deleteNote(list.get(0).getId());
		//assertEquals("Check DB is empty after delete", 0, noteDao.getAllNotes().size());
    	assertThat(noteDao.getAllNotes()).as("Check DB is empty after delete").hasSize(0);

	}

	@Test
	public void shouldReturnNotes() {
		//assertEquals("Check DB is empty first", 0, noteDao.getAllNotes().size());
		assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	
		noteDao.addNote(new Note().setSubject("subject 1").setText("test1"));
    	noteDao.addNote(new Note().setSubject("subject 2").setText("test2"));
    	//assertEquals("Should return 2 Note", 2, noteDao.getAllNotes().size());
    	assertThat(noteDao.getAllNotes()).as("Should return 2 Note").hasSize(2);
	    //assertEquals("Should return only 1 Note", 1, noteDao.getFilteredNotes("test1").size());
	    assertThat(noteDao.getFilteredNotes("test1")).as("Should return only 1 Note").hasSize(1);
	}

	@Test
	public void shouldUpdateNote() {
		assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	
		noteDao.addNote(new Note().setSubject("subject 1").setText("test1"));
    	noteDao.addNote(new Note().setSubject("subject 2").setText("test2"));
    	//assertEquals("Should return 2 Note", 2, noteDao.getAllNotes().size());
    	List<Note> list = noteDao.getAllNotes(); 
    	assertThat(list).as("Should return 2 Note").hasSize(2);
    	
    	Note note = noteDao.updateNote(list.get(0).setSubject("subject 3").setText("test3"));
	    assertThat(note.getSubject()).as("Should change subect").isEqualTo("subject 3");
	    assertThat(note.getText()).as("Should change text").isEqualTo("test3");
	    assertThat(note.getCreateTime()).as("Should set create time").isNotNull();
	    assertThat(note.getModifyTime()).as("Should set modify time").isNotNull();
	}

}

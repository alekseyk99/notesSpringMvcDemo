package com.alekseyk99.spring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.alekseyk99.spring.configuration.TestConfiguration;
import com.alekseyk99.spring.dto.NoteDto;
import com.alekseyk99.spring.model.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@Transactional
public class NoteDAOTest {

	@Autowired
	private NoteDAO noteDao;
	
	@Test
	public void shouldAddNote() {
	    assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	noteDao.addNote("subject 1","test дом test house flat");
    	assertThat(noteDao.getAllNotes()).as("Check Note has been created").hasSize(1);
	}

	@Test
	public void shouldDeleteNote() {
    	noteDao.addNote("subject 1", "test дом test house flat");
    	List<Note> list = noteDao.getAllNotes();
    	assertThat(noteDao.getAllNotes()).as("Check Note has been created").hasSize(1);
	    noteDao.deleteNote(list.get(0).getId());
    	assertThat(noteDao.getAllNotes()).as("Check DB is empty after delete").hasSize(0);

	}

	@Test
	public void shouldReturnNotes() {
		assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	
		noteDao.addNote("subject 1","test1");
    	noteDao.addNote("subject 2","test2");
    	assertThat(noteDao.getAllNotes()).as("Should return 2 Note").hasSize(2);
	    assertThat(noteDao.getFilteredNotes("test1")).as("Should return only 1 Note").hasSize(1);
	}

	@Test
	public void shouldUpdateNote() {
		assertThat(noteDao.getAllNotes()).as("Check DB is empty first").hasSize(0);
    	
		noteDao.addNote("subject 1", "test1");
    	noteDao.addNote("subject 2", "test2");
    	List<Note> list = noteDao.getAllNotes(); 
    	assertThat(list).as("Should return 2 Note").hasSize(2);
    	
    	NoteDto dto = new NoteDto().setId(list.get(0).getId()).setSubject("subject 3").setText("test3");
    	Note note = noteDao.updateNote(dto);
	    assertThat(note.getSubject()).as("Should change subect").isEqualTo("subject 3");
	    assertThat(note.getText()).as("Should change text").isEqualTo("test3");
	    assertThat(note.getCreateTime()).as("Should set create time").isNotNull();
	    assertThat(note.getModifyTime()).as("Should set modify time").isNotNull();
	}

}

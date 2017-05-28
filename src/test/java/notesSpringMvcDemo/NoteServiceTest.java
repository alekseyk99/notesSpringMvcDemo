package notesSpringMvcDemo;

//import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.alekseyk99.spring.controller.NoteService;
import com.alekseyk99.spring.controller.NoteServiceImpl;
import com.alekseyk99.spring.model.Note;
import com.alekseyk99.spring.repository.NoteDAO;
import com.alekseyk99.spring.repository.NoteDAOImpl;

public class NoteServiceTest {
	
	NoteService noteService;
	Timestamp time;
	long startPeriod,endPeriod;
	List<Note> list;
	NoteDAO noteDAO;
	
	@Before
	public void before() {
	
		
	list = new ArrayList<Note>(5);
	long timeEpoch = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
	time  = new Timestamp(timeEpoch);
	startPeriod  = timeEpoch-10000;
	endPeriod  = timeEpoch+10000;
	
	
	list.add(new Note().setId(list.size()).setSubject("subject 1").setText("test дом test house flat").setCreateTime(time).setModifyTime(time));
	list.add(new Note().setId(list.size()).setSubject("subject 2").setText("tesT дом app").setCreateTime(time).setModifyTime(time));
	list.add(new Note().setId(list.size()).setSubject("subject 3").setText("test дом House dog").setCreateTime(time).setModifyTime(time));
	list.add(new Note().setId(list.size()).setSubject("subject 4").setText("test дом house app cat ").setCreateTime(time).setModifyTime(time));
	list.add(new Note().setId(list.size()).setSubject("subject 5").setText("test дом app house cat ").setCreateTime(time).setModifyTime(time));
	list.add(new Note().setId(list.size()).setSubject("subject 5").setText("home").setCreateTime(time).setModifyTime(time));
	
	noteDAO =Mockito.mock(NoteDAOImpl.class);
	noteService = new NoteServiceImpl(noteDAO);
		
	}

	@Test
	public void shouldReturnTopWords() {
		
		Mockito.when(noteDAO.getNotesWithinPeriod(startPeriod,endPeriod)).thenReturn(list);
		List<String> listTopWords = noteService.findTopWords(startPeriod,endPeriod);
		//assertEquals(Arrays.asList("test", "дом", "house", "app","cat"),listTopWords);
	    assertThat(listTopWords).hasSize(5);
	    assertThat(listTopWords).containsExactly("test", "дом", "house", "app","cat");

	}
	
	@Test
	public void shouldReturnList() {
		Mockito.when(noteDAO.getFilteredNotes("tt")).thenReturn(list);
		List<Note> listNotes = noteService.getFilteredList("tt");
		//assertEquals(5,listNotes.size());
		assertThat(listNotes).hasSize(6);
	}
	
	

}

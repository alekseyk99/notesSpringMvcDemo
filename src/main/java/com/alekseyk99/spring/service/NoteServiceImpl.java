package com.alekseyk99.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alekseyk99.spring.dto.NoteDto;
import com.alekseyk99.spring.model.Note;
import com.alekseyk99.spring.repository.NoteDAO;

/**
 * NoteService implementation class NoteServiceImpl
 */
@Service("noteService")
public class NoteServiceImpl implements NoteService {

	/**
	 * Injected DAO layer
	 */
	private final NoteDAO noteDao;
	private final ModelMapper mapper;
	
	@Autowired
	public NoteServiceImpl(NoteDAO noteDao) {
		this.noteDao = noteDao;
		this.mapper = new ModelMapper();
	}

    /**
	 * @see NoteService#getAllList()
	 */
	@Override
	public List<NoteDto> getAllList() {
		List<NoteDto> result = new ArrayList<>();
		for (Note note : noteDao.getAllNotes()) {
		    result.add(mapper.map(note, NoteDto.class));
		};
		return result;
	}
	
	/**
	 * @see NoteService#add(Note)
	 */
	@Override
	public NoteDto add(NoteDto dto) {
	    Note newNote = noteDao.addNote(dto.getSubject(), dto.getText());
		return mapper.map(newNote, NoteDto.class);
	}

	/**
	 * @see NoteService#deleteNote(int)
	 */
	@Override
	public void deleteNote(int id) {
		noteDao.deleteNote(id);
	}
	
	/**
	 * @see NoteService#updateNote(Note)
	 */
	@Override
	public NoteDto updateNote(NoteDto note) {
		return mapper.map(noteDao.updateNote(note), NoteDto.class);
	}

	/**
	 * @see NoteService#getFilteredList(String)
	 */
	@Override
	public List<NoteDto> getFilteredList(String filter) {
		if ((filter == null) || ("".equals(filter))) {
			return getAllList();
		}
		List<Note> notes = noteDao.getFilteredNotes(filter.toLowerCase());
		List<NoteDto> result = new ArrayList<>();
        for (Note note : notes) {
            result.add(mapper.map(note, NoteDto.class));
        };
        return result;
	}
	
	/**
	 * @see NoteService#findTopWords(long, long)
	 */
	@Override
	public List<String> findTopWords(long startPeriod, long endPeriod) {
	
		return noteDao.
					getNotesWithinPeriod(startPeriod,endPeriod).
					stream().
					flatMap((n) -> Pattern.compile("\\s+").splitAsStream(n.getText())).
					collect(Collectors.toMap(
							String::toLowerCase,
							p -> Integer.valueOf(1),
							Integer::sum
							)
					).
					entrySet().
					stream().
					sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).
					limit(5).
					map(Map.Entry::getKey).
					collect(Collectors.toList());
		
	}

}

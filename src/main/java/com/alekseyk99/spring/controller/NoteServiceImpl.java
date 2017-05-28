package com.alekseyk99.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	public NoteServiceImpl(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}

	/**
	 * @see NoteService#getAllList()
	 */
	@Override
	public List<Note> getAllList() {
		return noteDao.getAllNotes();
	}
	
	/**
	 * @see NoteService#add(Note)
	 */
	@Override
	public Note add(Note note) {
		return noteDao.addNote(note);
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
	public Note updateNote(Note note) {
		return noteDao.updateNote(note);
	}

	/**
	 * @see NoteService#getFilteredList(String)
	 */
	@Override
	public List<Note> getFilteredList(String filter) {
		if ((filter == null) || ("".equals(filter))) {
			return getAllList();
		}
		return noteDao.getFilteredNotes(filter.toLowerCase());
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

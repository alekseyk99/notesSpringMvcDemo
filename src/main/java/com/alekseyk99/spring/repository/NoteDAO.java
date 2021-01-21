package com.alekseyk99.spring.repository;

import java.util.List;
import com.alekseyk99.spring.dto.NoteDto;
import com.alekseyk99.spring.model.Note;

/**
 * DAO layer interface
 * 
 *
 */
public interface NoteDAO {

	/** 
	 * Add new Note
	 * @param note Note object with ID = null
	 * @return Created Note object 
	 */
	Note addNote(String subject, String text);

	/**
	 * Delete Note by Id 
	 * @param index Note ID
	 */
	void deleteNote(int index);

	/** 
	 * Get list of all notes
	 * @return List of all notes
	 */
	List<Note> getAllNotes();

	/** 
	 * Get filtered list
	 * @param filter String to filter with
	 * @return Filtered list or all notes if filter is null or empty
	 */
	List<Note> getFilteredNotes(String filter);

	/** 
	 * Get list of notes within provided period
	 * @param startPeriod begin of period as EPOCH time
	 * @param endPeriod end of period as EPOCH time
	 * @return List of notes within provided period
	 */
	List<Note> getNotesWithinPeriod(long startPeriod, long endPeriod);

	/** 
	 * Update Subject and Text of Note object
	 * @param note Note object with ID
	 * @return Updated Note object 
	 */
	Note updateNote(NoteDto note);

}

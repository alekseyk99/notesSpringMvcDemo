package com.alekseyk99.spring.service;

import java.util.List;
import com.alekseyk99.spring.dto.NoteDto;

/**
 * Service layer interface
 *
 */
public interface NoteService {

	/** 
	 * Get list of all notes
	 * @return List of all notes
	 */
	List<NoteDto> getAllList();

	/** 
	 * Add new Note
	 * @param note Note object with ID = null
	 * @return Created Note object 
	 */
	NoteDto add(NoteDto note);

	/**
	 * Delete Note by Id 
	 * @param id Note ID
	 */
	void deleteNote(int id);

	/** 
	 * Update Subject and Text of Note object
	 * @param note Note object with ID
	 * @return Updated Note object 
	 */
	NoteDto updateNote(NoteDto note);

	/** 
	 * Get filtered list
	 * @param filter String to filter with
	 * @return Filtered list or all notes if filter is null or empty
	 */
	List<NoteDto> getFilteredList(String filter);

	/** 
	 * Get 5 most often used words
	 * 
	 * @param startPeriod begin of period as EPOCH time
	 * @param endPeriod end of period as EPOCH time
	 * @return List of most often used words
	 */
	List<String> findTopWords(long startPeriod, long endPeriod);

}
package com.alekseyk99.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.alekseyk99.spring.dto.NoteDto;
import com.alekseyk99.spring.model.Note;
import com.alekseyk99.spring.service.NoteService;

/**
 * Handles requests for the application pages
 */
@Controller
@ControllerAdvice // needed to catch NoHandlerFoundException
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    NoteService noteService;
    
	/**
     * Simple controller for "/" that returns a JSP view.
     *
     * @param model Spring model
     * @return View name as String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String listNoteRoot(Model model) {
		logger.debug("run / servlet");
		model.addAttribute("listNote", noteService.getAllList());
		return "notes";
	}
	
    @RequestMapping(value="/addNote", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody NoteDto addNote(@RequestBody NoteDto note) throws Exception {
	    logger.debug("addNote note={}", note);
	    if (note.getId() != null) {
	    	throw new IllegalArgumentException("Id should be null");
	    }
	    return noteService.add(note);
    }

    @RequestMapping(value="/deleteNote", method=RequestMethod.POST )
    public @ResponseBody String deleteNote(@RequestParam("id") Integer id, Model model) throws Exception {
	    logger.debug("deleteNote id={}",id);
	    if (id == null) {
	    	throw new IllegalArgumentException("Id cannot be null");
	    }
	    noteService.deleteNote(id);
	    return "ok";
    }

    @RequestMapping(value="/updateNote", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody NoteDto updateNote(@RequestBody NoteDto note) throws Exception {
	    logger.debug("updateNote note={}", note);
	    if (note.getId() == null) {
	    	throw new IllegalArgumentException("Id cannot be null");
	    }
		return noteService.updateNote(note);
    }

    @RequestMapping(value="/filterNote", method=RequestMethod.POST )
    public @ResponseBody List<NoteDto> filterNote(@RequestParam("filter") String filter, Model model) throws Exception {
	    logger.debug("filterNote filter={}",filter);
	    return noteService.getFilteredList(filter);
    }
    
    @RequestMapping(value="/findTopWords", method=RequestMethod.POST )
    public @ResponseBody List<String> findTopWords(@RequestParam("startPeriod") long startPeriod, @RequestParam("endPeriod") long endPeriod, Model model) throws Exception {
	    logger.debug("findTopWords startPeriod={} endPeriod={}",startPeriod,endPeriod);
	    return noteService.findTopWords(startPeriod,endPeriod);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException exception) {
 	   logger.error("IllegalArgumentException: {}",exception.getMessage());
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception exception) {
    	logger.error("NotFoundException: {}",exception.getMessage());
    	return "not_found";
    }
    
    //@ExceptionHandler
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void defaultHandleException(Exception exception) {
    	 logger.error("Exception: {}",exception);
    }

    
}

package com.alekseyk99.spring.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alekseyk99.spring.model.Note;

/**
 * DAO implementation class NoteDAOImpl
 */
@Repository("NoteDAO")
public class NoteDAOImpl implements NoteDAO {
	
		/** Injected EntityManager */
	    private EntityManager entityManager;

	    @PersistenceContext
	    public void setEntityManager(EntityManager entityManager) {
	    	this.entityManager = entityManager;
	    }

	    /** 
		 * @see NoteDAO#addNote(Note)
		 */
	    @Override
		@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	    public Note addNote(Note note) {
	        entityManager.persist(note);
	        return note;
	    }

	    /**
		 * @see NoteDAO#deleteNote(int)
		 */
	    @Override
		@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	    public void deleteNote(int index) {
	        //entityManager.remove(note);
	        entityManager.
	        	createQuery("DELETE FROM Note n WHERE n.id = :id").
	        	setParameter("id", index).
	        	executeUpdate();
	    }

	    /**
		 * @see NoteDAO#getAllNotes()
		 */
		// Uncomment if clean read is needed
		//@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
	    @Override
		public List<Note> getAllNotes() {
			return  (List<Note>) entityManager.
					createQuery("SELECT n from Note as n",Note.class).
					getResultList();
	    }

	    /**
		 * @see NoteDAO#getFilteredNotes(String)
		 */
		// Uncomment if clean read is needed
		//@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
	    @Override
		public List<Note> getFilteredNotes(String filter) {
			return (List<Note>) entityManager.
				 createQuery("SELECT n from Note as n where lower(n.text) like :filter or lower(n.subject) like :filter",Note.class).
				 setParameter("filter", "%"+filter+"%").
				 getResultList();
		}

		/**
		 * @see NoteDAO#getNotesWithinPeriod(long, long)
		 */
		// Uncomment if clean read is needed
		//@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
		@Override
		public List<Note> getNotesWithinPeriod(long startPeriod, long endPeriod) {
			return (List<Note>) entityManager.
					 createQuery("SELECT n from Note as n where n.createTime >= :startPeriod and n.createTime < :endPeriod",Note.class).
					 setParameter("startPeriod", new Timestamp(startPeriod)).
					 setParameter("endPeriod", new Timestamp(endPeriod)).
					 getResultList();
		}

		/**
		 * @see NoteDAO#updateNote(Note)
		 */
		@Override
		@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
		public Note updateNote(Note note) {
			// #find is needed because #merge does not get field "createTime" 
			return	entityManager.merge(
						entityManager.
						find(Note.class, note.getId()).
						setSubject(note.getSubject()).
						setText(note.getText())
					);
		}

}

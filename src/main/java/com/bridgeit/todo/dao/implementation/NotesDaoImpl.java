package com.bridgeit.todo.dao.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.NotesDao;
import com.bridgeit.todo.model.Note;

@Repository
public class NotesDaoImpl implements NotesDao{

	@Autowired
	SessionFactory factory;
	
	public void saveNote(Note note) {
		Session session = factory.getCurrentSession();
		session.save(note);
	}

	public List<Note> noteWithId(int id) {
		Session session = factory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Note> query = session.createQuery("from Note where id = "+id);
		List<Note> list = query.list();
		return list;
	}

	public void updateNote(Note note) {
		Session session = factory.getCurrentSession();
		session.update(note);
	}

	@SuppressWarnings("unchecked")
	public void delete(int id) {
		Session session = factory.getCurrentSession();
		Query<Note> query = session.createQuery("delete from Note where id="+id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Note> allNotes(int id) {
		Session session = factory.getCurrentSession();
		Query<Note> query = session.createQuery("from Note where User_ID = "+id);
		List<Note> list = query.list();
		return list;
	}

}

package com.bridgeit.todo.dao.implementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.NotesDao;
import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.WebScrap;

@Repository
public class NotesDaoImpl implements NotesDao{

	@Autowired
	SessionFactory factory;
	
	public int saveNote(Note note) {
		Session session = factory.getCurrentSession();
		Serializable sid = session.save(note);
		return (Integer) sid;
	}

	public Note noteWithId(int id) {
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("id", id));
		
		Note note=(Note) criteria.uniqueResult();
		return note;
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
		
		Query<Note> query1 = session.createQuery("from Note where User_ID = "+id);
		List<Note> list = query1.list();
		
		Query<Note> query2 = session.createQuery("select noteid from Collaborater where sharedwith = "+id);
		List<Note> list2 = query2.list();
		
		List<Note> allList = new ArrayList<Note>();
		allList.addAll(list);
		allList.addAll(list2);
		
		
		return allList;
	}

	
	public void saveCollab(Collaborater collaborater) {
		Session session = factory.getCurrentSession();
		session.save(collaborater);
	}

	public void saveSrapInDb(WebScrap scraper) {
		Session session = factory.getCurrentSession();
		session.save(scraper);
	}

	@SuppressWarnings("unchecked")
	public List<WebScrap> allScraper(int noteid) {
		Session session = factory.getCurrentSession();
		Query<WebScrap> query1 = session.createQuery("from WebScrap where noteid = "+noteid);
		List<WebScrap> list = query1.list();
		return list;
	}

}

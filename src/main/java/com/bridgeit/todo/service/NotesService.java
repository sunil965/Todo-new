package com.bridgeit.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.NotesDao;
import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;

@Service
@Transactional
public class NotesService {

	@Autowired
	NotesDao dao;

	public void saveNoteInfo(Note note) {
		dao.saveNote(note);
	}

	@Transactional(readOnly=true)
	public Note getNote(int id) {
		return dao.noteWithId(id);
	}

	public void updateNote(Note note) {
		dao.updateNote(note);
	}

	public void deleteNote(int id) {
		dao.delete(id);
	}

	@Transactional(readOnly=true)
	public List<Note> getAllNote(int id) {
		return dao.allNotes(id);
	}

	public List<Note> getAllArchivedNote(boolean check) {
		return null;
	}

	public void saveSharedNote(Collaborater collaborater) {
		dao.saveCollab(collaborater);
	}
	
	
}

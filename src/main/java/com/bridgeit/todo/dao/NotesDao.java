package com.bridgeit.todo.dao;

import java.util.List;

import com.bridgeit.todo.model.Note;

public interface NotesDao {

	void saveNote(Note note);

	List<Note> noteWithId(int id);

	void updateNote(Note note);

	void delete(int id);

	List<Note> allNotes(int id);

}

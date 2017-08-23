package com.bridgeit.todo.dao.interface;

import java.util.List;

import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;

public interface NotesDao {

	void saveNote(Note note);

	Note noteWithId(int id);

	void updateNote(Note note);

	void delete(int id);

	List<Note> allNotes(int id);

	void saveCollab(Collaborater collaborater);

}

package com.bridgeit.todo.dao;

import java.util.List;

import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.WebScrap;

public interface NotesDao {

	int saveNote(Note note);

	Note noteWithId(int id);

	void updateNote(Note note);

	void delete(int id);

	List<Note> allNotes(int id);

	void saveCollab(Collaborater collaborater);

	void saveSrapInDb(WebScrap scraper);

	List<WebScrap> allScraper(int noteid);

}

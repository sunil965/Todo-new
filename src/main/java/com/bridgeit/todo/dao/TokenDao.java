package com.bridgeit.todo.dao;

import com.bridgeit.todo.model.Token;

public interface TokenDao {

	void saveToken(Token token);

	void deleteTokenInDB(String deletetoken);

	Token getTokenData(String accesstoken);

	void updateNewAccessToken(Token generateNewAccessToken);

	/*List<Note> noteWithId(int id);

	void updateNote(Note note);

	void delete(int id);

	List<Note> allNotes(int id);*/
}

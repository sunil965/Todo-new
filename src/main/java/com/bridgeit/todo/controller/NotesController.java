package com.bridgeit.todo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.Response;
import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.NotesService;

/**
 * This controller performs functionality of CURD for Notes.
 * @author Sunil Kumar
 *
 */
@RestController
public class NotesController {

	@Autowired
	NotesService service;
	@Autowired
	UserResponse myresponse;

	Note note = new Note();
	
	/* ************* CREATE NOTE ****************/

	/**
	 * This controller method persist Note in Notes_Table of TodoApp Database. 
	 * @param note {@link Note}
	 * @param request {@link HttpServletRequest}
	 * @return {@link ResponseEntity<String>}
	 */
	@RequestMapping(value = "/rest/create", method = RequestMethod.POST)
	public ResponseEntity<Response> createNotes(@RequestBody Note note, HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("UserInSession");

		try {
			note.setUser(user);
			note.setDate(new Date());
			service.saveNoteInfo(note);
			System.out.println("Note Created !!!!");
			myresponse.setStatus(1);
			myresponse.setMessage("Note Created Sucessfully");
			return new ResponseEntity<Response>(myresponse,HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Note Creation failed !!!!");
			myresponse.setStatus(-1);
			myresponse.setMessage("Note Not Created");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}

	/* ************* GET NOTE BY ID ****************/

	/**
	 * This controller method Get Information Of A Note From Notes_Table of TodoApp Database.
	 * @param id {@link Integer}
	 * @return {@link ResponseEntity<List>}
	 */
	@RequestMapping(value = "/rest/getNote/{id}")
	public ResponseEntity<List<Note>> getNoteDetails(@PathVariable("id") int id) {
		List<Note> note = service.getNote(id);
		if (note != null) {
			return new ResponseEntity<List<Note>>(note, HttpStatus.OK);
		}
		return new ResponseEntity<List<Note>>(note, HttpStatus.NOT_FOUND);
	}

	/* ************* UPDATE NOTE ****************/

	/**
	 * This controller method Update Note in Notes_Table of TodoApp Database.
	 * @param note {@link Note}
	 * @return {@link ResponseEntity<String>}
	 */
	@RequestMapping("/rest/editNote")
	public ResponseEntity<Response> updateNoteDetails(@RequestBody Note note) {
		try {
			service.updateNote(note);
			myresponse.setStatus(1);
			myresponse.setMessage("Note Updated Sucessfully");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		} catch (Exception e) {
			myresponse.setStatus(-1);
			myresponse.setMessage("Note Updatation Fails");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}

	/* ************* DELETE NOTE ****************/

	/**
	 * This Controller Method Delete Note From Notes_Table of TodoApp Database.
	 * @param id {@link Integer}
	 * @return {@link ResponseEntity<String>}
	 */
	@RequestMapping("/rest/delete/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int id) {
		try {
			service.deleteNote(id);
			myresponse.setStatus(1);
			myresponse.setMessage("Note Deleted");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		} catch (Exception e) {
			myresponse.setStatus(-1);
			myresponse.setMessage("Note Deletion Fails");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}

	/* ************* GET ALL NOTES OF A USER ****************/

	/**
	 * This Controller Method Get All Note From Notes_Table of TodoApp Database.
	 * @param id {@link Integer}
	 * @return {@link ResponseEntity<List>}
	 */
	@RequestMapping(value = "/rest/getAllNotes")
	public ResponseEntity<List<Note>> getAllNoteOfUser(HttpServletRequest request) {

		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("UserInSession");
		int uid = user.getId();

		List<Note> note = service.getAllNote(uid);
		if (note != null) {
			return new ResponseEntity<List<Note>>(note, HttpStatus.OK);
		}
		return new ResponseEntity<List<Note>>(note, HttpStatus.NOT_FOUND);
	}
/*
	 ************* GET LIST OF ARCHIVE NOTES ***************

	public ResponseEntity<List> listOfArchived() {
		boolean check = note.isArchive();
		List<Note> note = service.getAllArchivedNote(check);
		return new ResponseEntity<List>(HttpStatus.OK);
	}*/
}

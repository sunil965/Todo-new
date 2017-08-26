package com.bridgeit.todo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.Response;
import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.model.WebScrap;
import com.bridgeit.todo.service.NotesService;
import com.bridgeit.todo.service.UserServices;

/**
 * This controller performs functionality of CURD for Notes and WebScraper.
 * @author Sunil Kumar
 *
 */
@RestController
public class NotesController {

	@Autowired
	NotesService service;
	
	@Autowired
	UserServices userservice;
	
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
		
		WebScrap isScraper = service.createScrapping(note.getDescription());
		
		try {
			note.setUser(user);
			note.setDate(new Date());
			int noteid = service.saveNoteInfo(note);
			
			note.setId(noteid);
			if (isScraper != null) {
				isScraper.setNoteid(noteid);
				service.createScraper(isScraper);
			}
			myresponse.setStatus(1);
			myresponse.setMessage("Note Created Sucessfully");
			return new ResponseEntity<Response>(myresponse,HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Note Creation failed !!!!");
			myresponse.setStatus(-1);
			myresponse.setMessage("Note Not Created");
			return new ResponseEntity<Response>(myresponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* ************* GET NOTE BY ID ****************/

	/**
	 * This controller method Get Information Of A Note From Notes_Table of TodoApp Database.
	 * @param id {@link Integer}
	 * @return {@link ResponseEntity<List>}
	 */
	@RequestMapping(value = "/rest/getNote/{id}")
	public ResponseEntity<Note> getNoteDetails(@PathVariable("id") int id) {
		Note note = service.getNote(id);
		if (note != null) {
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		}
		return new ResponseEntity<Note>(note, HttpStatus.NOT_FOUND);
	}

	/* ************* UPDATE NOTE ****************/

	/**
	 * This controller method Update Note in Notes_Table of TodoApp Database.
	 * @param note {@link Note}
	 * @return {@link ResponseEntity<String>}
	 */
	@RequestMapping(value="/rest/editNote", method = RequestMethod.POST)
	public ResponseEntity<Response> updateNoteDetails(@RequestBody Note note) {
		try {
			note.setDate(new Date());
			service.updateNote(note);
			myresponse.setStatus(1);
			myresponse.setMessage("Note Updated Sucessfully");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		} catch (Exception e) {
			myresponse.setStatus(-1);
			myresponse.setMessage("Note Updatation Fails");
			return new ResponseEntity<Response>(myresponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
		note = addScraperInNote(note); // Retuned note after adding scrappers.
		if (note != null) {
			return new ResponseEntity<List<Note>>(note, HttpStatus.OK);
		}
		return new ResponseEntity<List<Note>>(note, HttpStatus.NOT_FOUND);
	}
					// Add Scrapers To Their Respective Note And Return To getAllNoteOfUser().
	 private List addScraperInNote(List notes) {
		 for (int i = 0; i < notes.size(); i++) {
				Note todoNotes =  (Note) notes.get(i);
				List<WebScrap> scrapers = getAllScraper(todoNotes.getId());
				todoNotes.setScrapers(scrapers);
			}
		return notes;
	}
	 				// Get Scrapers From Database Table WebScrap And Return To addScraperInNote().
	private List<WebScrap> getAllScraper(int noteid) {
		try {
			return service.getScraperById(noteid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/************** CREATE COLLABORATION ****************/

	@PostMapping(value="createCollab")
	public ResponseEntity<Response> createCollaboration(@RequestBody Map<String, Object> loginMap) {
		System.out.println(loginMap.get("noteid")+" "+loginMap.get("sharedwith"));
		
		int id = (Integer) loginMap.get("noteid");
		Note note = service.getNote(id);
		
		User firstuser = userservice.getUserById(note.getUser().getEmail());
		
		String otheruser = (String) loginMap.get("sharedwith");
		User seconduser = userservice.getUserById(otheruser);
		
		Collaborater collaborater = new Collaborater();
		collaborater.setNoteid(note);
		collaborater.setOwner(firstuser.getId());
		collaborater.setSharedwith(seconduser.getId());
		
		try {
			service.saveSharedNote(collaborater);
			myresponse.setStatus(1);
			myresponse.setMessage("Collaboration Created Sucessfully");
			return new ResponseEntity<Response>(myresponse,HttpStatus.OK);
		} 
		catch (Exception e) {
			System.out.println("Collaboration Creation failed !!!!");

			e.printStackTrace();
			myresponse.setStatus(-1);
			myresponse.setMessage("Collaboration Not Created");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}
}

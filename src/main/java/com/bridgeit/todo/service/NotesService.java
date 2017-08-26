package com.bridgeit.todo.service;

import java.net.URI;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.NotesDao;
import com.bridgeit.todo.model.Collaborater;
import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.WebScrap;
import com.bridgeit.todo.validation.UrlValidate;

@Service
@Transactional
public class NotesService {

	@Autowired
	NotesDao dao;

	public int saveNoteInfo(Note note) {
		int id = dao.saveNote(note);
		return id;
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

	/*public List<Note> getAllArchivedNote(boolean check) {
		return null;
	}*/

	public void saveSharedNote(Collaborater collaborater) {
		dao.saveCollab(collaborater);
	}

	public WebScrap createScrapping(String description) {
		WebScrap scraper = null;
		try {
			if (description != null) {
				
				String url = UrlValidate.isValidateUrl(description);
				URI uri = new URI(url);
				String hostName = uri.getHost();
				
				String title = null;
				String imgUrl = null;
				Document document = Jsoup.connect(url).get();
				Elements metaOgTitle = document.select("meta[property=og:title]");
				Elements metaOgImage = document.select("meta[property=og:image]");
				
				if (metaOgTitle != null) {
					title = metaOgTitle.attr("content");
				} else {
					title = document.text();
				}

//				metaOgImage = document.select("meta[property=og:image]");
				if (metaOgImage != null) {
					imgUrl = metaOgImage.attr("content");
				}

				scraper = new WebScrap();
				scraper.setScraptitle(title);
				scraper.setImageurl(imgUrl);
				scraper.setScraphost(hostName);
				scraper.setWeburl(url);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return scraper;
	}

	public void createScraper(WebScrap scraper) {
		dao.saveSrapInDb(scraper);
	}

	public List<WebScrap> getScraperById(int noteid) {
		return dao.allScraper(noteid);
	}
	
	
}

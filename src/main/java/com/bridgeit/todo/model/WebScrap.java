package com.bridgeit.todo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="WebScrap")
public class WebScrap implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generate", strategy = "increment")
	@GeneratedValue(generator = "generate")
	private int id;
	
	private String scraptitle;
	
	private String scraphost;
	
	private String imageurl;
	
	private String weburl;
	
	/*@ManyToOne(optional = false)
	@JoinColumn*/
	private int noteid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getScraptitle() {
		return scraptitle;
	}
	public void setScraptitle(String scraptitle) {
		this.scraptitle = scraptitle;
	}
	public String getScraphost() {
		return scraphost;
	}
	public void setScraphost(String scraphost) {
		this.scraphost = scraphost;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public int getNoteid() {
		return noteid;
	}
	public void setNoteid(int noteid) {
		this.noteid = noteid;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	public String toString() {
		return "WebScrap [id=" + id + ", scraptitle=" + scraptitle + ", scraphost=" + scraphost + ", imageurl="
				+ imageurl + ", weburl=" + weburl + ", noteid=" + noteid + "]";
	}
}

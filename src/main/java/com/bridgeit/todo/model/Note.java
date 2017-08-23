package com.bridgeit.todo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Notes_Table")
public class Note {

	@Id
	@GenericGenerator(name = "generate", strategy = "increment")
	@GeneratedValue(generator = "generate")
	@Column(name = "ID")
	private int id;

	@Column(name = "Title")
	private String title;

	@Column(name = "Description")
	private String description;

	@Column(name = "Date")
	private Date date;
	
	@Column(name = "Color")
	private String color;
	
	@Column(name = "Reminder")
	private Date reminddate;
	
	@Column(name = "Archive")
	private boolean archive;
	
	@Column(name = "Trash")
	private boolean trash;
	
	@Column(name = "isPin")
	private boolean pin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "User_ID")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	public Date getReminddate() {
		return reminddate;
	}

	public void setReminddate(Date reminddate) {
		this.reminddate = reminddate;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", color="
				+ color + ", reminddate=" + reminddate + ", archive=" + archive + ", trash=" + trash + ", pin=" + pin
				+ ", user=" + user + "]";
	}

	
}

package com.bridgeit.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Collaborater {

	@Id
	@GenericGenerator(name = "generate", strategy = "increment")
	@GeneratedValue(generator = "generate")
	private int id;

	private int owner;

	@ManyToOne(optional = false)
	@JoinColumn
	private Note noteid;

	private int sharedwith;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}


	public Note getNoteid() {
		return noteid;
	}

	public void setNoteid(Note noteid) {
		this.noteid = noteid;
	}

	public int getSharedwith() {
		return sharedwith;
	}

	public void setSharedwith(int sharedwith) {
		this.sharedwith = sharedwith;
	}

	@Override
	public String toString() {
		return "Collaborater [id=" + id + ", owner=" + owner + ", noteid=" + noteid + ", sharedwith=" + sharedwith
				+ "]";
	}

}

package com.bridgeit.todo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class Token implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="token", strategy="increment")
	@GeneratedValue(generator="token")
	private int id;
	private String accesstoken;
	private String refreshtoken;
	private Date createdOn;
	
	@OneToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="User_ID",referencedColumnName="ID")
	private  User  user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getRefreshtoken() {
		return refreshtoken;
	}
	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Token(int id, String accesstoken, String refreshtoken, Date createdOn, User user) {
		super();
		this.id = id;
		this.accesstoken = accesstoken;
		this.refreshtoken = refreshtoken;
		this.createdOn = createdOn;
		this.user = user;
	}

	public Token() {
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return "Token [id=" + id + ", accesstoken=" + accesstoken + ", refreshtoken=" + refreshtoken + ", createdOn="
				+ createdOn + ", user=" + user + "]";
	}
	
}

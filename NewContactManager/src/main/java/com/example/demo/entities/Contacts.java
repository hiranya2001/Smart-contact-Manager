package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Contacts {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cId;
	private String name;
	private String secondName;
	private String Email;
	private String phone;
	private String image;
	private String work;
	@Column(length=100)
	private String description;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Contacts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contacts(int cId, String name, String secondName, String email, String phone, String image, String work,
			String description, User user) {
		super();
		this.cId = cId;
		this.name = name;
		this.secondName = secondName;
		Email = email;
		this.phone = phone;
		this.image = image;
		this.work = work;
		this.description = description;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Contacts [cId=" + cId + ", name=" + name + ", secondName=" + secondName + ", Email=" + Email
				+ ", phone=" + phone + ", image=" + image + ", work=" + work + ", description=" + description
				+ ", user=" + user + "]";
	}
	
	

}

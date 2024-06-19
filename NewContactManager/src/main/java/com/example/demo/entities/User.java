package com.example.demo.entities;

import java.util.List;

import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)
	private String email;
	@NotBlank(message="Name Field is Required")
	@Size(min=3,max=12,message="Name must be between 3 to 12 words")
	private String name;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(length=500)
	private String about;
	private String role;
	private boolean enabled;
	private String imageUrl;
	@OneToMany(cascade=CascadeType.ALL , mappedBy="user" ,fetch=FetchType.LAZY ,orphanRemoval=true)
	private List<Contacts> contacts;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<Contacts> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public User(int id, String email, String name, String about, String role, boolean enabled,String password) {
		super();
		this.id = id;
		this.email = email;
		this.password=password;
		this.name = name;
		this.about = about;
		this.role = role;
		this.enabled = enabled;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", about=" + about + ", role=" + role
				+ ", enabled=" + enabled + "+password=" + password + "]";
	}
	
	
	

}

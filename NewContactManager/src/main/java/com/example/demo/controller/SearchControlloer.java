package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Contacts;
import com.example.demo.entities.User;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.UserRepository;

@RestController
public class SearchControlloer {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<List<Contacts>> Search(@PathVariable("query")String query ,Principal principal){
	String userName=	principal.getName();
	User user=this.userRepository.getUserByEmail(userName);
	List<Contacts> contact=this.contactRepository.findByNameContainingAndUser(query, user);
	
	return ResponseEntity.ok(contact);
		
	}
}

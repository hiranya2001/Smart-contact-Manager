package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.User;
import com.example.demo.helper.Message;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class SmartConroller {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/home")
	public String Home(Model model) {
		model.addAttribute("title", "Smart-Contact Mangger");
		return "home";
	}
	
	@GetMapping("/about")
	public String About() {
		return "about";
	}
	
	@GetMapping("/signUp")
	public String Register(Model model) {
		model.addAttribute("user", new User());
		return "signUp";
	}
	
	@PostMapping("/do_Register")
	public String Fill(@Valid @ModelAttribute("user")User user,BindingResult result ,@RequestParam(value="agreement",defaultValue = "false")boolean agreement,Model m,HttpSession session)   {
		try {
		
		if(!agreement) {
			throw new Exception("Terms and condition should obey");
			 
		}
		
		if(result.hasErrors()) {
			m.addAttribute("user",user);
 			return "signUp";
		}
		user.setEnabled(true);
		user.setRole("ROLE_Normal_User");
		user.setImageUrl("default.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));


	User res=	userRepository.save(user);
		m.addAttribute(res);
		session.setAttribute("message",new Message("Successfully Registered","alert-success"));
		return "signUp";
		
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message",new Message("Something went Wrong","alert-danger"));
			return "signUp";
		}
		
		
		
	}
	
	
	
	@GetMapping("/signin")
	public String Login(Model m) {
		m.addAttribute("title","Login Page");
		return "login";
	}
	
	

}

package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.validation.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Contacts;
import com.example.demo.entities.User;
import com.example.demo.helper.Message;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String userName=principal.getName();
		//get the user using Username(Email)
		User user= userRepository.getUserByEmail(userName);
		model.addAttribute("user", user);
	}
	
	
	@GetMapping("/index")
	public String home(Model m  ) {
		m.addAttribute("title","User-DashBoard");
	 
		return "/normal/User-Dashboard";
		
	}
	
	
	
	@GetMapping("/addContact")
	public String addContact(Model m) {
		m.addAttribute("title", "Add Your Conatact");
		m.addAttribute("contact", new Contacts());
		return "/normal/add-contact";
	}

	@PostMapping("/process-contact")
	public String processContact(Principal principal,@ModelAttribute Contacts contact,@RequestParam("Profileimage")MultipartFile file, HttpSession session) {
		
		try {
		if(file.isEmpty()) {
			System.out.println("File is empty set Your image file again");
		}
		else {
			contact.setImage(file.getOriginalFilename());
			 //Path filePath = Paths.get(uploadDir, fileName);
			
			File filepath=new ClassPathResource("static/Images").getFile();
// This might fail in a packaged app
			java.nio.file.Path path=Paths.get(filepath.getAbsolutePath()+File.separator+file.getOriginalFilename());
			 
 			Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
 			System.out.print("Image uploaded successfully");
		}
		
		String username=principal.getName();
		User user=userRepository.getUserByEmail(username);
		contact.setUser(user);
		user.getContacts().add(contact);
		userRepository.save(user);
		session.setAttribute("message",new Message("Contact Added Successfully!! Add More","alert-success"));
		System.out.println(user);
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message",new Message("Something Went Wrong!! Try Again","alert-danger"));

		}
		
		return"/normal/add-contact";
	}
	
	
	
	
	
	@GetMapping("/showContact/{page}")
	public String ShowContact(@PathVariable("page")Integer page, Model m,Principal pricipal) {
		m.addAttribute("title", "Contacts Of the Users");
    String userName=		pricipal.getName();
    User user=userRepository.getUserByEmail(userName);
    Pageable pageabel= PageRequest.of(page, 5);
  Page <Contacts>contacts=  this.contactRepository.findContactByUser(user.getId(), pageabel);
  m.addAttribute("contacts",contacts);
  m.addAttribute("currentpage",page);
  m.addAttribute("totalpage",contacts.getTotalPages());
  
		return "/normal/show_contact";
	}
	
	
	@GetMapping("/viewContact/{id}")
	public String ViewContact(@PathVariable("id")int id,Principal principal,Model m) {
	Optional<Contacts> contact=contactRepository.findById(id);
	Contacts contact1=contact.get();
String userName=	principal.getName();
User user=   this.userRepository.getUserByEmail(userName);
if(user.getId()==contact1.getUser().getId()) {
	
	m.addAttribute("contact", contact1);
	m.addAttribute("title", "Contact-Details");

}
		return "/normal/viewcontact";
	}
	
	@GetMapping("/deleteContact/{cId}")
	public String DeleteContact(@PathVariable("cId")int cid,HttpSession session,Model m,Principal principal)throws Exception {
	Optional<Contacts> optionalcontact=	this.contactRepository.findById(cid);
	Contacts contact=  optionalcontact.get();
	String userName=		principal.getName();
    User user=userRepository.getUserByEmail(userName);
	
	if(contact.getUser().getId()==user.getId()) {
		//contact.setUser(null);
		user.getContacts().remove(contact);
		this.userRepository.save(user);
		
		session.setAttribute("message", new Message("Contact Deleted Successfully","alert-success"));
		
		File file=new ClassPathResource("/static/Images").getFile();
		java.nio.file.Path path=Paths.get(file.getAbsolutePath()+""+File.separator+""+contact.getImage());
		Files.delete(path);
		this.contactRepository.delete(contact);
		
	}
		return"redirect:/user/showContact/0";
	}
	
	
	@PostMapping("/updatecontact/{id}")
	public String updateContact(Principal principal,@PathVariable("id") int id,Model m) {
		
		String userName=principal.getName();
	    User user=userRepository.getUserByEmail(userName);
	    Contacts contact=this.contactRepository.findById(id).get();
	    m.addAttribute("contact", contact);
		return "/normal/update_contact";
	}
	
	

	@PostMapping("/process")
	public String ProcessContact(@ModelAttribute("contact")Contacts contact,MultipartFile file,HttpSession session,Principal principal)throws Exception {
		
		try {
	Optional<Contacts> optional=this.contactRepository.findById(contact.getcId());
	Contacts oldContact=optional.get();
	if(!file.isEmpty()) {
			File files=new ClassPathResource("/static/Images").getFile();
			java.nio.file.Path path=Paths.get(files.getAbsolutePath()+""+File.separator+""+contact.getImage());
			Files.delete(path);
			//Files.deleteIfExists(path); 
			
			File saveFile=new ClassPathResource("/static/Images").getFile();
			java.nio.file.Path wpath=Paths.get(saveFile.getAbsolutePath()+""+File.separator+""+contact.getImage());
			 Files.copy(file.getInputStream(), wpath, StandardCopyOption.REPLACE_EXISTING);
			 
			 contact.setImage(file.getOriginalFilename());
 			
		}else {
			contact.setImage(oldContact.getImage());
		}
		User user=this.userRepository.getUserByEmail(principal.getName());
		contact.setUser(user);
		
		this.contactRepository.save(contact);
		session.setAttribute("message", new Message("Your contact is updated","success"));
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return"redirect:/user/showContact/0";

		
	}
	
	@GetMapping("/profile")
	public String showProfile(Principal principal ,Model m) {
	String username=	principal.getName();
	User user=this.userRepository.getUserByEmail(username);
	m.addAttribute("title","User Profile");
	m.addAttribute("user", user);
		return "normal/profile";
	}
	
	
}











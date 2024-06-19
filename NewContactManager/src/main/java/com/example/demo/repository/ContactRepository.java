package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Contacts;
import com.example.demo.entities.User;
 
public interface ContactRepository extends JpaRepository<Contacts,Integer>{
	
	@Query("from Contacts as c Where c.user.id=:userId")
	Page<Contacts>findContactByUser(@Param("userId") int  Id,Pageable pageable);
	
	public List<Contacts> findByNameContainingAndUser(String name,User user);

}

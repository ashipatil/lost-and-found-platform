package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer>{

	UserDetailsEntity findByEmailAndPassword(String email, String password);
	
	@Query("SELECT COUNT(u) FROM UserDetailsEntity u")
	int countAllUsers();

	@Query("SELECT COUNT(u) FROM UserDetailsEntity u WHERE u.role = 'ADMIN'")
	int countAllAdmins();



}
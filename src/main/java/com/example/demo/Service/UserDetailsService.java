package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ItemUserEntity;
import com.example.demo.Entity.UserDetailsEntity;
import com.example.demo.Repository.ItemUserRepo;
import com.example.demo.Repository.UserDetailsRepo;

@Service
public class UserDetailsService {
	
	@Autowired
	private UserDetailsRepo ur;

	public List<UserDetailsEntity> getalluser() {
		return ur.findAll();
	}

	public long getallusercount() {
		// TODO Auto-generated method stub
		return ur.count();
	}
}

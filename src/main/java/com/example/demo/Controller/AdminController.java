package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.UserDetailsEntity;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/home")
	public String adminHome(HttpSession session, Model model) {
		UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
		
		System.out.println("Admin detials: "+user);
		if(user != null && "ADMIN".equalsIgnoreCase(user.getRole()))
		{
			return "admindashboard";
		}
		return "redirect:/landing/login";
	}
}


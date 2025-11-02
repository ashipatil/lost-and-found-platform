package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.UserDetailsEntity;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/home")
	public String userhome(HttpSession session) {
		UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
		
		if (user != null && "USER".equalsIgnoreCase(user.getRole())) {
			return "redirect:/dash/userdashboard";
		}
		return "redirect:/landing/login";
	}
}

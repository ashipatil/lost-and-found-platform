package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ItemUserRepo;
import com.example.demo.Service.EmailService;

@RequestMapping("/mail")
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
	private ItemUserRepo ir;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> payload) {
	    String email = payload.get("email");
	
	    if (email == null || email.isEmpty()) {
	    	return ResponseEntity.badRequest().body("Email is missing");
	    }
	
	    try {
		    emailService.sendEmail(
		    email,
		    "Account created successfully!",
		    "Welcome to SYS Bank. Continue your bank journey... Thank you!"
		    );
		    return ResponseEntity.ok("Email sent to " + email);
	    } catch (Exception e) {
		    e.printStackTrace();
		    return ResponseEntity.internalServerError().body("Failed to send email");
	    }
    }

}


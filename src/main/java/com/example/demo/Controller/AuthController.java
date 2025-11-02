package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Entity.UserDetailsEntity;
import com.example.demo.Repository.UserDetailsRepo;
import com.example.demo.Service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/landing")
public class AuthController {
	
	@Autowired
	private UserDetailsRepo ur;
	
	@Autowired
    private EmailService emailService;
	
	@GetMapping("/landingpage")
	public String landingpage() {
		return "landingPage";
	}
	
	@GetMapping("/login")
	public String loginpage(Model model) {
		model.addAttribute("user", new UserDetailsEntity());
		return "login";
	}
	
	@GetMapping("/register")
	public String registerpage(Model model) {
		model.addAttribute("user", new UserDetailsEntity());
		return "register";
	}

	@PostMapping("/register")
	public String add(@ModelAttribute UserDetailsEntity user, Model model) {
	    try {
	        user.setRole("USER");
	        ur.save(user);

	        // ✅ HTML Welcome Email with Logo
	        try {
	        	String message = String.format("""
	        			<!DOCTYPE html>
	        			<html>
	        			  <head>
	        			    <style>
	        			      body {
	        			        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	        			        background-color: #f9f9f9;
	        			        margin: 0;
	        			        padding: 0;
	        			        color: #333;
	        			      }
	        			      .container {
	        			        background-color: #ffffff;
	        			        max-width: 600px;
	        			        margin: auto;
	        			        padding: 30px;
	        			        border-radius: 8px;
	        			        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	        			      }
	        			      ul {
	        			        padding-left: 20px;
	        			      }
	        			      li {
	        			        color: #8e44ad;
	        			        margin-bottom: 6px;
	        			      }
	        			      a {
	        			        color: #3498db;
	        			        text-decoration: none;
	        			      }
	        			      .footer {
	        			        display: flex;
	        			        align-items: flex-start;
	        			        gap: 15px;
	        			        margin-top: 30px;
	        			        border-top: 1px solid #ccc;
	        			        padding-top: 20px;
	        			      }
	        			      .footer img {
	        			        width: 80px;
	        			        height: auto;
	        			        border-radius: 8px;
	        			      }
	        			      .footer-text {
	        			        font-size: 14px;
	        			        color: #333;
	        			        line-height: 1.5; 
	        			      }
	        			      .footer-text strong {
	        			        display: block;
	        			        margin-top: 2px; 
	        			        font-weight: 600;
	        			        color: #2c3e50;
	        			      }
	        			    </style>
	        			  </head>
	        			  <body>
	        			    <div class="container">
	        			      <p>Dear %s,</p>

	        			      <p>
	        			        Thank you for signing up with <strong>Lost &amp; Found Hub</strong> – a trusted platform for reconnecting people with their lost items.
	        			      </p>

	        			      <p>Your account is now active. You can now:</p>
	        			      <ul>
	        			        <li>Report lost items</li>
	        			        <li>Claim found items</li>
	        			        <li>Browse reports to assist others</li>
	        			      </ul>

	        			      <p>
	        			        If you have any questions or need help, feel free to contact our support team at
	        			        <a href="mailto:lostandfoundofficials@gmail.com">lostandfoundofficials@gmail.com</a>.
	        			      </p>

	        			      <div class="footer">
	        			        <img src="https://preview.redd.it/5lzdqhgbc3d81.png?width=1080&crop=smart&auto=webp&s=ba3add550b768382e39f834649c97d8aee87fda9" alt="Lost and Found Logo" />
	        			        <div class="footer-text">
	        			          Thanks for joining us!<br/>
	        			          <strong>Lost &amp; Found Administration Team</strong>
	        			        </div>
	        			      </div>
	        			    </div>
	        			  </body>
	        			</html>
	        			""", user.getName());

	            emailService.sendEmail(
	                user.getEmail(),
	                "Welcome to Lost & Found Hub",
	                message
	            );
	        } catch (Exception emailEx) {
	            emailEx.printStackTrace();
	            model.addAttribute("error", "Account created, but failed to send confirmation email.");
	            return "register";
	        }

	        return "redirect:/landing/login?success=true";

	    } catch (DataIntegrityViolationException e) {
	        model.addAttribute("error", "Email or username already exists");
	        return "register";
	    } catch (Exception e) {
	        model.addAttribute("error", "An error occurred while creating your account");
	        return "register";
	    }
	}

	@PostMapping("/login")
	public String loginuser(@RequestParam String email, @RequestParam String password, Model model, HttpSession  session) {
		UserDetailsEntity u = ur.findByEmailAndPassword(email, password);
		if(u!= null) {
			session.setAttribute("loggedInUser", u);
			
			if("ADMIN".equalsIgnoreCase(u.getRole())) {
				return "redirect:/admin/home";
			}else {
				return "redirect:/user/home";
			}
		}else {
			model.addAttribute("error","Invalid credentials");
			return "redirect:/landing/login";
		}
	}

}

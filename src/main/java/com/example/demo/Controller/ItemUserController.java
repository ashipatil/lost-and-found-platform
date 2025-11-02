package com.example.demo.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.ClaimItemEntity;
import com.example.demo.Entity.ItemUserEntity;
import com.example.demo.Entity.PromoDetails;
import com.example.demo.Entity.UserDetailsEntity;
import com.example.demo.Repository.ClaimItemRepo;
import com.example.demo.Repository.ItemUserRepo;
import com.example.demo.Repository.UserDetailsRepo;
import com.example.demo.Service.ClaimItemService;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.ItemUserService;
import com.example.demo.Service.PromoService;
import com.example.demo.Service.UserDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dash")
public class ItemUserController {

    @Autowired
    private ItemUserService is;
    
    @Autowired
    private UserDetailsService us;
    
    @Autowired
    private ClaimItemService cs;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PromoService ps;

    @Autowired
    private ItemUserRepo ir;
    
    @Autowired
    private ClaimItemRepo cr;
    
    @Autowired
    private UserDetailsRepo ur;
    
// ==========================================================================================
// ==========================================================================================
// 											USER SIDE
// ==========================================================================================
// ==========================================================================================
    
    
    
    @GetMapping("/userdashboard")
    public String Userhomepage(HttpSession session,Model model) {
    	UserDetailsEntity user = (UserDetailsEntity)session.getAttribute("loggedInUser");
    	if (user == null) {
            return "redirect:/landing/login";
        }
    	model.addAttribute("user", user);
        return "userdashboard";
    }
    
    @GetMapping("/priorreqdetails")
    public String priorreq() {
    	return "priorreq";
    }
    
    @GetMapping("/logout")
    public String logout() {
    	return "landingPage";
    }
    
    @GetMapping("/reportitem")
    public String reportitem(Model model) {
        model.addAttribute("Item", new ItemUserEntity());
        return "reportitem";
    }
    
    @PostMapping("/send-confirmation")
    @ResponseBody
    public ResponseEntity<String> sendConfirmationEmail(HttpSession session) {
        System.out.println("📩 /send-confirmation endpoint hit!");

        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("❌ No user in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        System.out.println("✅ Logged-in user: " + user.getEmail());

        String subject = "Payment Confirmation - Lost & Found Hub";
        String message = String.format("Hello %s, Your payment is confirmed!", user.getName());

        try {
            emailService.sendEmail(user.getEmail(), subject, message);
            System.out.println("✅ EmailService.sendEmail() called");
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email sending failed");
        }
    }

    @PostMapping("/submit-item")
    public String submitItem(@RequestParam("reportType") String reportType,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("description") String description,
                             @RequestParam("category") String category,
                             @RequestParam("dateLostFound") String dateLostFound,
                             @RequestParam("location") String location,
                             @RequestParam("itemPhotoPath") MultipartFile itemPhoto,
                             @RequestParam("contactEmail") String contactEmail,
                             @RequestParam("contactPhone") String contactPhone,
                             HttpSession session) throws IOException {

    	UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");


        // ✅ Debug check
        if (user == null) {
            
            return "redirect:/landing/login"; // Or show error page
        }

        ItemUserEntity item = new ItemUserEntity();
        item.setReportType(reportType);
        item.setItemName(itemName);
        item.setDescription(description);
        item.setCategory(category);
        item.setDateLostFound(dateLostFound);
        item.setLocation(location);
        item.setContactEmail(contactEmail);
        item.setContactPhone(contactPhone);
        item.setUser(user); // 👈 This sets u_id
        item.setItemstatus("Not Claimed");

        if (!itemPhoto.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(itemPhoto.getBytes());
            item.setItemPhotoPath(base64Image);
        }

        is.submititem(item);
        return "redirect:/dash/myreports";
    }
    
//    @PostMapping("/submit-promo")
//    public String submitPromo(@RequestParam("itemName") String itemName,
//            @RequestParam("description") String description,
//            @RequestParam("category") String category,
//            @RequestParam("dateLostFound") String dateLostFound,
//            @RequestParam("location") String location,
//            @RequestParam(value = "itemPhotoPath", required = false) MultipartFile itemPhoto,
//            @RequestParam("contactEmail") String contactEmail,
//            @RequestParam(value = "contactPhone", required = false) String contactPhone,
//                             HttpSession session) throws IOException {
//
//    	UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
//
//
//        // ✅ Debug check
//        if (user == null) {
//            
//            return "redirect:/landing/login"; // Or show error page
//        }
//
//        PromoDetails report  = new PromoDetails();
//        report.setItemName(itemName);
//        report.setDescription(description);
//        report.setCategory(category);
//        report.setDateLostFound(LocalDate.parse(dateLostFound));
//        report.setLocation(location);
//        report.setContactEmail(contactEmail);
//        report.setContactPhone(contactPhone);
//        report.setUser(user);
//
//        if (!itemPhoto.isEmpty()) {
//            String base64Image = Base64.getEncoder().encodeToString(itemPhoto.getBytes());
//            report.setItemPhotoPath(base64Image);
//        }
//
//        ps.submitpromo(report);
//        return "redirect:/dash/userdashboard";
//    }
    
    @PostMapping("/submit-promo")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitPromo(
            @RequestParam("itemName") String itemName,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("dateLostFound") String dateLostFound,
            @RequestParam("location") String location,
            @RequestParam(value = "itemPhotoPath", required = false) MultipartFile itemPhoto,
            @RequestParam("contactEmail") String contactEmail,
            @RequestParam(value = "contactPhone", required = false) String contactPhone,
            HttpSession session) throws IOException {

        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");

        if (user == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        PromoDetails report = new PromoDetails();
        report.setItemName(itemName);
        report.setDescription(description);
        report.setCategory(category);
        report.setDateLostFound(LocalDate.parse(dateLostFound));
        report.setLocation(location);
        report.setContactEmail(contactEmail);
        report.setContactPhone(contactPhone);
        report.setUser(user);

        if (itemPhoto != null && !itemPhoto.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(itemPhoto.getBytes());
            report.setItemPhotoPath(base64Image);
        }

        PromoDetails savedPromo = ps.submitpromo(report);  // 🔁 See next step
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("id", savedPromo.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/promoposter/{id}")
    public String viewPromoPoster(@PathVariable int id, Model model) {
        PromoDetails promo = ps.getPromoById(id);
        model.addAttribute("promo", promo);
        return "viewpromo"; // Thymeleaf page to render poster
    }
    
    @GetMapping("/userviewallitem")
    public String usergetallitem(Model model) {
        model.addAttribute("item", is.getallitem());
        return "userviewall";
    }
    
    @GetMapping("/claimitem/{id}")
    public String showClaimForm(@PathVariable int id, Model model, HttpSession session) {
    	UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
        
        ItemUserEntity item = is.getitemById(id);
        
        ClaimItemEntity claim = new ClaimItemEntity();
        claim.setItem(item);                // set item relation
        claim.setId(item.getId());          // assign item ID to claim       
        claim.setUser(user);                // set user relation
        claim.setName(user.getName());      // autofill name
        claim.setEmail(user.getEmail());    // autofill email
        claim.setItemName(item.getItemName());
        claim.setCategory(item.getCategory());
        claim.setDescription(item.getDescription());

        model.addAttribute("claim", claim);  // Bind claim to the form
        return "claimform";
    }
    
    @PostMapping("/submit-claim")
    public String submitclaimform(HttpSession session, @ModelAttribute ClaimItemEntity claim) {
        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
        claim.setUser(user);

        
        ItemUserEntity item = is.getItemById(claim.getItem().getId()); // ✅
        item.setItemstatus("Pending");
        is.submititem(item); 
      
        claim.setItem(item);
        claim.setClaimstatus("Pending");

        cs.submitclaimform(claim);
        return "redirect:/dash/userdashboard";
    }
    
    @GetMapping("/viewdetails/{id}")
	public String viewitemsDetails(@PathVariable int id, Model model) {
    	ItemUserEntity item = is.getitemById(id);
    	model.addAttribute("item", item);
    	return "itemdetail";
    }
    
    @GetMapping("/myreports")
    public String showMyReports(HttpSession session, Model model) {
        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/landing/login"; // session expired or not logged in
        }
        
        List<ItemUserEntity> myItems = ir.findByUser_Id(user.getId());

        model.addAttribute("items", myItems);
        return "myreports";
    }
    
    @GetMapping("/edit/{id}")
   	public String editItem (@PathVariable ("id") int id, Model model) {  
       	ItemUserEntity item = is.getitemById(id);
   		model.addAttribute("item", item); // 
   		return "useredititem";
   	}
    
    @PostMapping("/update-item")
    public String updateItem(@RequestParam("id") int id,
                             @RequestParam("reportType") String reportType,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("description") String description,
                             @RequestParam("category") String category,
                             @RequestParam("dateLostFound") String dateLostFound,
                             @RequestParam("location") String location,
                             @RequestParam("itemPhotoPath") MultipartFile itemPhoto,
                             @RequestParam("contactEmail") String contactEmail,
                             @RequestParam("contactPhone") String contactPhone,
                             HttpSession session) throws IOException {

        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/landing/login";

        ItemUserEntity existing = is.getitemById(id);
        if (existing == null) return "redirect:/dash/myreports?error=notfound";

        // Update fields
        existing.setReportType(reportType);
        existing.setItemName(itemName);
        existing.setDescription(description);
        existing.setCategory(category);
        existing.setDateLostFound(dateLostFound);
        existing.setLocation(location);
        existing.setContactEmail(contactEmail);
        existing.setContactPhone(contactPhone);
        existing.setUser(user);

        if (!itemPhoto.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(itemPhoto.getBytes());
            existing.setItemPhotoPath(base64Image);
        }

        is.submititem(existing);
        return "redirect:/dash/myreports";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") int id) {
        is.deleteitem(id);
        return "redirect:/dash/myreports";
    }

    @GetMapping("/statusmanage")
    public String showAllItems(Model model) {
        List<ItemUserEntity> items = ir.findAll();
        model.addAttribute("allItems", items);
        return "userstatus"; // Name of the HTML file
    }
    
    @GetMapping("/promodetails")
    public String showPromotionDetails() {
    
        return "promodetails";
    } 
    
    @GetMapping("/usersearchstatus")
    public String userShowAllItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String reportType,
            Model model) {

        List<ItemUserEntity> items;

        if ((category == null || category.isEmpty()) && (reportType == null || reportType.isEmpty())) {
            items = ir.findAll();
        } else if (category != null && !category.isEmpty() && (reportType == null || reportType.isEmpty())) {
            items = ir.findByCategory(category);
        } else if ((category == null || category.isEmpty()) && reportType != null && !reportType.isEmpty()) {
            items = ir.findByReportType(reportType);
        } else {
            items = ir.findByCategoryAndReportType(category, reportType);
        }

        model.addAttribute("allItems", items);
        return "userstatus";
    }
// ==========================================================================================
// ==========================================================================================
//											ADMIN SIDE
// ==========================================================================================
// ==========================================================================================
    
    
    
    @GetMapping("/admindashboard")
    public String Adminhomepage(Model model) {
    	int totalUser = ur.countAllUsers();
        int totalAdmin = ur.countAllAdmins();
        int totalItems = ir.countAllItems();
        int totalLost = ir.countByReportType("Lost");
        int totalFound = ir.countByReportType("Found");

        int totalClaims = cr.countAllClaims();
        int approvedClaims = cr.countByStatus("Approved");
        int rejectedClaims = cr.countByStatus("Rejected");
        int pendingClaims = cr.countByStatus("Pending");

        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalAdmin", totalAdmin);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalLost", totalLost);
        model.addAttribute("totalFound", totalFound);

        model.addAttribute("totalClaims", totalClaims);
        model.addAttribute("approvedClaims", approvedClaims);
        model.addAttribute("rejectedClaims", rejectedClaims);
        model.addAttribute("pendingClaims", pendingClaims); 

        return "admindashboard";
    }
    
    @GetMapping("/adminviewallitem")
    public String admingetallitem(Model model) {
    	model.addAttribute("item", is.getallitem());
    	return "adminviewall";
    }
    
    @GetMapping("/admin-edit/{id}")
   	public String adminEditItem (@PathVariable ("id") int id, Model model) {  
       	ItemUserEntity item = is.getitemById(id);
   		model.addAttribute("item", item); // 
   		return "adminedititem";
   	}
    
    @PostMapping("/admin-update-item")
    public String adminUpdateItem(@RequestParam("id") int id,
                             @RequestParam("reportType") String reportType,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("description") String description,
                             @RequestParam("category") String category,
                             @RequestParam("dateLostFound") String dateLostFound,
                             @RequestParam("location") String location,
                             @RequestParam("itemPhotoPath") MultipartFile itemPhoto,
                             @RequestParam("contactEmail") String contactEmail,
                             @RequestParam("contactPhone") String contactPhone,
                             HttpSession session) throws IOException {

        UserDetailsEntity user = (UserDetailsEntity) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/landing/login";

        ItemUserEntity existing = is.getitemById(id);
        if (existing == null) return "redirect:/dash/admindashboard?error=notfound";

        // Update fields
        existing.setReportType(reportType);
        existing.setItemName(itemName);
        existing.setDescription(description);
        existing.setCategory(category);
        existing.setDateLostFound(dateLostFound);
        existing.setLocation(location);
        existing.setContactEmail(contactEmail);
        existing.setContactPhone(contactPhone);
        existing.setUser(user);

        if (!itemPhoto.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(itemPhoto.getBytes());
            existing.setItemPhotoPath(base64Image);
        }

        is.submititem(existing);
        return "redirect:/dash/admindashboard";
    }
   
    @GetMapping("/admin-delete/{id}")
    public String adminDeleteItem(@PathVariable("id") int id) {
        is.deleteitem(id);
        return "redirect:/dash/admindashboard";
    }
    
    @GetMapping("/viewalluser")
    public String viewalluser(Model model) {
    	model.addAttribute("totalUser", us.getallusercount());
    	model.addAttribute("user", us.getalluser());
    	return "viewalluser";
    }
    
    @GetMapping("/viewallclaimreports")
    public String viewallclaimreports(Model model) {
        
        // Step 1: Fetch the list from the service
        List<ClaimItemEntity> claims = cs.getallclaim();  // 'cs' is your ClaimItemService

        // Step 2: Calculate totals
        long totalApproved = claims.stream()
                .filter(c -> "Approved".equalsIgnoreCase(c.getClaimstatus()))
                .count();

        long totalRejected = claims.stream()
                .filter(c -> "Rejected".equalsIgnoreCase(c.getClaimstatus()))
                .count();

        // Step 3: Add attributes for Thymeleaf
        model.addAttribute("claim", claims);
        model.addAttribute("approvedCount", totalApproved);
        model.addAttribute("rejectedCount", totalRejected);

        return "allclaimreports"; // your Thymeleaf view name
    }

    
    @GetMapping("/viewclaimdetails/{id}")
    public String viewClaimDetails(@PathVariable int id, Model model) {
        ClaimItemEntity claim = cs.getClaimById(id); // Assuming this method exists in your service
        model.addAttribute("claim", claim);
        return "viewclaimdetails"; // Name of your Thymeleaf HTML page
    }
    
    @GetMapping("/deleteclaimreport/{id}")
    public String deleteClaim(@PathVariable int id) {
        cs.deleteClaim(id); 
        return "redirect:/dash/viewallclaimreports";
    }
    
    @GetMapping("/adminstatusmanage")
    public String adminshowAllItems(Model model) {
        List<ItemUserEntity> items = ir.findAll();
        model.addAttribute("allItems", items);
        return "adminstatus"; // Name of the HTML file
    }
    
    @GetMapping("/adminviewdetails/{id}")
	public String adminviewitemsDetails(@PathVariable int id, Model model) {
    	ItemUserEntity item = is.getitemById(id);
    	model.addAttribute("item", item);
    	return "adminitemdetail";
    }
    

    @PostMapping("/claimapprove/{id}")
    public String approveClaim(@PathVariable int id) {
        ClaimItemEntity claim = cs.getClaimById(id);
        if (claim != null) {
            claim.setClaimstatus("Approved");

            ItemUserEntity item = claim.getItem();
            if (item != null) {
                item.setItemstatus("Claimed");
                is.submititem(item);
            }

            cs.submitclaimform(claim);

            // ✅ Email with logo
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
            		    a {
            		      color: #3498db;
            		      text-decoration: none;
            		    }
            		    .section {
            		      margin-top: 15px;
            		      line-height: 1.6;
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
            		      Thank you for submitting your claim for <strong>%s</strong>.
            		    </p>

            		    <p>
            		      We are pleased to inform you that your claim has been <strong style='color: #27ae60;'>approved</strong> after successful verification.
            		    </p>

            		    <div class="section">
            		      <p>You may now contact the individual who reported the item to arrange its collection:</p>
            		      <p>
            		        <strong>Reporter Contact Information</strong><br/>
            		        Name: %s<br/>
            		        Email: <a href="mailto:%s">%s</a><br/>
            		        Phone: %s
            		      </p>
            		    </div>

            		    <p>
            		      Please connect with them at your earliest convenience to coordinate the handover.
            		    </p>

            		    <p>
            		      We’re proud to support meaningful reunions through the <strong>Lost &amp; Found Hub</strong> – a community-driven platform committed to helping individuals reclaim their belongings.
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
            		""",
            		claim.getName(),
            		claim.getItemName(),
            		item.getUser().getName(),
            		item.getUser().getEmail(),
            		item.getUser().getEmail(),
            		item.getContactPhone()
            		);



            try {
                emailService.sendEmail(claim.getEmail(), "Claim Approved - Lost & Found Hub", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/dash/viewallclaimreports";
    }
    
    @PostMapping("/claimreject/{id}")
    public String rejectClaim(@PathVariable int id) {
        ClaimItemEntity claim = cs.getClaimById(id);
        if (claim != null) {
            claim.setClaimstatus("Rejected");

            ItemUserEntity item = claim.getItem();
            if (item != null) {
                item.setItemstatus("Not Claimed");
                is.submititem(item);
            }

            cs.submitclaimform(claim);

            // ✅ Rejection Email with logo
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
            		    a {
            		      color: #3498db;
            		      text-decoration: none;
            		    }
            		    .section {
            		      margin-top: 15px;
            		      line-height: 1.6;
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
            		      Thank you for submitting your claim for <strong>%s</strong>.
            		    </p>

            		    <p>
            		      We regret to inform you that your claim for the item has been <strong style='color: #e74c3c;'>rejected</strong> as the details provided did not sufficiently match the report.
            		    </p>

            		    <p>
            		      If you believe this decision was made in error or have additional supporting information, you may contact our support team at
            		    </p>

            		    <p>
            		      📧 <a href="mailto:lostandfoundofficials@gmail.com">lostandfoundofficials@gmail.com</a>
            		    </p>

            		    <p>
            		      We appreciate your understanding and thank you for being a valued part of the <strong>Lost &amp; Found Hub</strong> community.
            		    </p>

            		    <div class="footer">
            		      <img src="https://preview.redd.it/5lzdqhgbc3d81.png?width=1080&crop=smart&auto=webp&s=ba3add550b768382e39f834649c97d8aee87fda9" alt="Lost and Found Logo" />
            		      <div class="footer-text">
            		        Thanks for being with us!<br/>
            		        <strong>Lost &amp; Found Administration Team</strong>
            		      </div>
            		    </div>
            		  </div>
            		</body>
            		</html>
            		""",
            		claim.getName(),
            		claim.getItemName()
            		);



            try {
                emailService.sendEmail(claim.getEmail(), "Claim Rejected - Lost & Found Hub", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/dash/viewallclaimreports";
    }
    
    
    @GetMapping("/searchstatus")
    public String showAllItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String reportType,
            Model model) {

        List<ItemUserEntity> items;

        if ((category == null || category.isEmpty()) && (reportType == null || reportType.isEmpty())) {
            items = ir.findAll();
        } else if (category != null && !category.isEmpty() && (reportType == null || reportType.isEmpty())) {
            items = ir.findByCategory(category);
        } else if ((category == null || category.isEmpty()) && reportType != null && !reportType.isEmpty()) {
            items = ir.findByReportType(reportType);
        } else {
            items = ir.findByCategoryAndReportType(category, reportType);
        }

        model.addAttribute("allItems", items);
        return "adminstatus";
    }
}

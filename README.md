# 🧳 Lost & Found Platform

**Tech Stack:** Java, Spring Boot, MySQL, Thymeleaf, Bootstrap  

A secure and user-friendly Lost & Found management platform with item reporting, claim tracking, admin moderation, and promotional plan integration.

---

## 🚀 Features
- 🔐 User Authentication (role-based: user/admin)
- 📝 Lost item reporting & claim management
- 🧾 Admin moderation and analytics dashboard
- ✉️ Email notifications for updates and approvals
- 💸 Payment integration for promotional visibility (TV, newspaper, posters)
- 📊 Dashboard insights (users, items, claims)

---

## ⚙️ Tech Stack
- Backend: Spring Boot (Java)
- Frontend: Thymeleaf + Bootstrap
- Database: MySQL
- Security: Spring Security + Role-based Access
- Deployment: (Add your server if deployed)

---

## 🧠 How to Run Locally
1. Clone the repo:
   ```bash
   git clone https://github.com/ashipatil/lost-and-found-platform.git

2. Open it in your IDE (IntelliJ, Eclipse, VS Code)

3. Update MySQL credentials in application.properties:

   spring.datasource.url=jdbc:mysql://localhost:3306/lostfound_db
   
   spring.datasource.username=root
   spring.datasource.password=yourpassword

5. Run the application:

   mvn spring-boot:run


6. Open http://localhost:8080 in browser

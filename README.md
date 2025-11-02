# 🧳 Lost and Found Platform

A **secure and efficient Lost & Found management system** built using **Java, Spring Boot, MySQL, Thymeleaf, and Bootstrap**.  
This platform helps users report lost items, manage claims, and allows admins to monitor and approve them — all with strong authentication and email alerts.

---

## 🚀 Features

- 🧾 **Lost/Found Item Reporting** – Easily submit lost or found item details  
- 🔒 **User Authentication & Role-Based Access** – Secure login for users and admins  
- 📬 **Email Notifications** – Alerts for registration, claim approval/rejection, and status updates  
- ⚙️ **Claim Management** – Submit and review item claims with admin moderation  
- 💳 **Promotional Payment Integration** – Optional paid feature to promote lost item reports via posters, social media, TV, etc.  
- 📊 **Admin Dashboard Analytics** – Real-time stats for users, items, and claims using Thymeleaf  
- 🧠 **Secure Backend** – Spring Security + well-structured queries for data integrity  

---

## 🧰 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Java, Spring Boot |
| **Frontend** | Thymeleaf, Bootstrap |
| **Database** | MySQL |
| **Security** | Spring Security |
| **Email Service** | JavaMail |
| **Payment Gateway** | Razorpay API |
| **Template Engine** | Thymeleaf |

---

## ⚙️ How to Run Locally

1. **Clone the repository**
   ```bash
   git clone https://github.com/ashipatil/lost-and-found-platform.git

2. Open the project in your IDE (STS, IntelliJ IDEA, or Eclipse)

3. Set up the database
   Create a MySQL database (e.g., lostfound_db)
   Update your credentials in src/main/resources/application.properties:

   spring.datasource.url=jdbc:mysql://localhost:3306/lostfound_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update

4. Run the project
   Use Run As → Spring Boot App or execute: mvn spring-boot:run

5. Open http://localhost:8080 in browser

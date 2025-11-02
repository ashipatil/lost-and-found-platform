package com.example.demo.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class PromoDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    private String itemName;

    @Column(length = 1000)
    private String description;

    private String category;

    private LocalDate dateLostFound;

    private String location;
    
    @Lob
    private String itemPhotoPath; // store relative or absolute path to image

    private String contactEmail;
 
    private String contactPhone; 
    
    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "u_id")
    private UserDetailsEntity user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDateLostFound() {
		return dateLostFound;
	}

	public void setDateLostFound(LocalDate dateLostFound) {
		this.dateLostFound = dateLostFound;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItemPhotoPath() {
		return itemPhotoPath;
	}

	public void setItemPhotoPath(String itemPhotoPath) {
		this.itemPhotoPath = itemPhotoPath;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public UserDetailsEntity getUser() {
		return user;
	}

	public void setUser(UserDetailsEntity user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PromoDetails [id=" + id + ", itemName=" + itemName + ", description=" + description + ", category="
				+ category + ", dateLostFound=" + dateLostFound + ", location=" + location + ", itemPhotoPath="
				+ itemPhotoPath + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone + ", user=" + user
				+ "]";
	}

	public PromoDetails(int id, String itemName, String description, String category, LocalDate dateLostFound,
			String location, String itemPhotoPath, String contactEmail, String contactPhone, UserDetailsEntity user) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.description = description;
		this.category = category;
		this.dateLostFound = dateLostFound;
		this.location = location;
		this.itemPhotoPath = itemPhotoPath;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.user = user;
	}

	public PromoDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

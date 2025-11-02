package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class ItemUserEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @NotNull
	    private String reportType; 
	    
	    @NotNull
	    private String itemName;
	    
	    @NotNull
	    private String description;
	    
	    @NotNull
	    private String category;
	    
	    @NotNull
	    private String dateLostFound;
	    
	    @NotNull
	    private String location;
	    
	    @NotNull
	    @Lob
	    private String itemPhotoPath; 

	    @NotNull
	    @Email
	    private String contactEmail;
	    
	    @NotNull
	    @Size(max = 10)
	    private String contactPhone;
	    
	    @NotNull
	    private String itemstatus;
	    
	    @JsonBackReference
		@ManyToOne
		@JoinColumn(name = "u_id")
	    private UserDetailsEntity user;
	   
		@JsonManagedReference
		@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
		private List<ClaimItemEntity> claim;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getReportType() {
			return reportType;
		}

		public void setReportType(String reportType) {
			this.reportType = reportType;
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

		public String getDateLostFound() {
			return dateLostFound;
		}

		public void setDateLostFound(String dateLostFound) {
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

		public String getItemstatus() {
			return itemstatus;
		}

		public void setItemstatus(String itemstatus) {
			this.itemstatus = itemstatus;
		}

		public UserDetailsEntity getUser() {
			return user;
		}

		public void setUser(UserDetailsEntity user) {
			this.user = user;
		}

		public List<ClaimItemEntity> getClaim() {
			return claim;
		}

		public void setClaim(List<ClaimItemEntity> claim) {
			this.claim = claim;
		}

		@Override
		public String toString() {
			return "ItemUserEntity [id=" + id + ", reportType=" + reportType + ", itemName=" + itemName
					+ ", description=" + description + ", category=" + category + ", dateLostFound=" + dateLostFound
					+ ", location=" + location + ", itemPhotoPath=" + itemPhotoPath + ", contactEmail=" + contactEmail
					+ ", contactPhone=" + contactPhone + ", itemstatus=" + itemstatus + ", user=" + user + ", claim="
					+ claim + "]";
		}

		public ItemUserEntity(int id, @NotNull String reportType, @NotNull String itemName, @NotNull String description,
				@NotNull String category, @NotNull String dateLostFound, @NotNull String location,
				@NotNull String itemPhotoPath, @NotNull @Email String contactEmail,
				@NotNull @Size(max = 10) String contactPhone, @NotNull String itemstatus, UserDetailsEntity user,
				List<ClaimItemEntity> claim) {
			super();
			this.id = id;
			this.reportType = reportType;
			this.itemName = itemName;
			this.description = description;
			this.category = category;
			this.dateLostFound = dateLostFound;
			this.location = location;
			this.itemPhotoPath = itemPhotoPath;
			this.contactEmail = contactEmail;
			this.contactPhone = contactPhone;
			this.itemstatus = itemstatus;
			this.user = user;
			this.claim = claim;
		}

		public ItemUserEntity() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		
	}

package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class ClaimItemEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	 
	 	// Personal Information
	 	@NotNull
	    private String name;
	 	
	 	@NotNull
	 	@Email
	    private String email;
	 	
	 	@NotNull
	 	@Size(min = 10)
	    private String phone;

	    // Item Information
	 	@NotNull
	    private String itemName;
	 	
	 	@NotNull
	    private String category;
	 	
	 	@NotNull
	    private String description;
	 	
	 	@NotNull
	    private String dateLost;
	 	 
	 	@NotNull
	    private String location;

	    // Verification
	 	@NotNull
	    private String proof;
	 	
	 	@NotNull
	    private String claimstatus;
	 	
	    @JsonBackReference
		@ManyToOne
		@JoinColumn(name = "u_id")
	    private UserDetailsEntity user;

		@JsonBackReference
		@ManyToOne
		@JoinColumn(name = "i_id")
	    private ItemUserEntity item;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDateLost() {
			return dateLost;
		}

		public void setDateLost(String dateLost) {
			this.dateLost = dateLost;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getProof() {
			return proof;
		}

		public void setProof(String proof) {
			this.proof = proof;
		}

		public UserDetailsEntity getUser() {
			return user;
		}

		public void setUser(UserDetailsEntity user) {
			this.user = user;
		}

		public ItemUserEntity getItem() {
			return item;
		}

		public void setItem(ItemUserEntity item) {
			this.item = item;
		}

		public String getClaimstatus() {
			return claimstatus;
		}

		public void setClaimstatus(String claimstatus) {
			this.claimstatus = claimstatus;
		}

		@Override
		public String toString() {
			return "ClaimItemEntity [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
					+ ", itemName=" + itemName + ", category=" + category + ", description=" + description
					+ ", dateLost=" + dateLost + ", location=" + location + ", proof=" + proof + ", claimstatus="
					+ claimstatus + ", user=" + user + ", item=" + item + "]";
		}

		public ClaimItemEntity(int id, @NotNull String name, @NotNull @Email String email,
				@NotNull @Size(min = 10) String phone, @NotNull String itemName, @NotNull String category,
				@NotNull String description, @NotNull String dateLost, @NotNull String location, @NotNull String proof,
				@NotNull String claimstatus, UserDetailsEntity user, ItemUserEntity item) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.phone = phone;
			this.itemName = itemName;
			this.category = category;
			this.description = description;
			this.dateLost = dateLost;
			this.location = location;
			this.proof = proof;
			this.claimstatus = claimstatus;
			this.user = user;
			this.item = item;
		}

		public ClaimItemEntity() {
			super();
			// TODO Auto-generated constructor stub
		}

		

			
}

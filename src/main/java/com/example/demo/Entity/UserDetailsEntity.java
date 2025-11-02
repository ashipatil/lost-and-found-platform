package com.example.demo.Entity;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserDetailsEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@NotNull
	@Size(min = 3)
    private String name;
	
	@NotNull
    @Email
    private String email;
	
	@NotNull
    @Pattern(regexp = "^[a-zA-Z0-9@#$%&*]{6,20}")
    private String password;

	@NotNull
	private String role;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ItemUserEntity> items;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PromoDetails> promo;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ClaimItemEntity> claim;

	// Getters and setters...

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<ItemUserEntity> getItems() {
		return items;
	}

	public void setItems(List<ItemUserEntity> items) {
		this.items = items;
	}

	public List<ClaimItemEntity> getClaim() {
		return claim;
	}

	public void setClaim(List<ClaimItemEntity> claim) {
		this.claim = claim;
	}

	// ✅ Fixed: Removed lazy collections from toString()
	@Override
	public String toString() {
		return "UserDetailsEntity [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", role=" + role + "]";
	}

	public UserDetailsEntity(int id, @NotNull @Size(min = 3) String name, @NotNull @Email String email,
			@NotNull @Pattern(regexp = "^[a-zA-Z0-9@#$%&*]{6,20}") String password, @NotNull String role,
			List<ItemUserEntity> items, List<ClaimItemEntity> claim) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.items = items;
		this.claim = claim;
	}

	public UserDetailsEntity() {
		super();
	}
}

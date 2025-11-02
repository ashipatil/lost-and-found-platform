package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ClaimItemEntity;
import com.example.demo.Repository.ClaimItemRepo;

@Service
public class ClaimItemService {
	
	@Autowired
	private ClaimItemRepo cr;

	public void submitclaimform(ClaimItemEntity claim) {
		cr.save(claim);
	}

	public List<ClaimItemEntity> getallclaim() {
		
		return cr.findAll();
	}

	public ClaimItemEntity getClaimById(int id) {
		 return cr.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));
	}

	public void deleteClaim(int id) {
		cr.deleteById(id);
	}
	
	
}

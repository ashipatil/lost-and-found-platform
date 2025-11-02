package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.ClaimItemEntity;

@Repository
public interface ClaimItemRepo extends JpaRepository<ClaimItemEntity, Integer> {
	
	@Query("SELECT COUNT(c) FROM ClaimItemEntity c")
	int countAllClaims();

	@Query("SELECT COUNT(c) FROM ClaimItemEntity c WHERE c.claimstatus = :status")
	int countByStatus(@Param("status") String status);


}

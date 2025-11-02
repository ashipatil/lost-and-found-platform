package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.PromoDetails;

@Repository
public interface PromoRepo extends JpaRepository<PromoDetails, Integer>{

}

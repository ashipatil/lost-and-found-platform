package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.PromoDetails;
import com.example.demo.Repository.PromoRepo;

@Service
public class PromoService {
	
	@Autowired
	private PromoRepo pr;

	public PromoDetails submitpromo(PromoDetails report) {
	    return pr.save(report);
	}

	public PromoDetails getPromoById(int id) {
        return pr.findById(id).orElse(null);
    }

}

package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.ItemUserEntity;
import com.example.demo.Entity.UserDetailsEntity;
import com.example.demo.Repository.ItemUserRepo;
import com.example.demo.Repository.UserDetailsRepo;

@Service
public class ItemUserService {
	
	@Autowired
	private ItemUserRepo ir;
	
	@Autowired
	private UserDetailsRepo ur;

	public void submititem(ItemUserEntity item) {
		ir.save(item);
	}

	public List<ItemUserEntity> getallitem() {
		
		return ir.findAll();
	}

	public ItemUserEntity getitemById(int id) {
		// TODO Auto-generated method stub
		return ir.findById(id)
		        .orElseThrow(() -> new RuntimeException("Item Details not found with ID: " + id));
	}

	public ItemUserEntity getItemById(int itemId) {
		// TODO Auto-generated method stub
		return ir.findById(itemId)
		        .orElseThrow(() -> new RuntimeException("Item Details not found with ID: " + itemId));
	}

	public void deleteitem(int id) {
		ir.deleteById(id);
	}
	
	  public List<ItemUserEntity> filterItems(String category, String reportType) {
	        if ((category == null || category.isEmpty()) && (reportType == null || reportType.isEmpty())) {
	            return ir.findAll();
	        } else if (category != null && !category.isEmpty() && (reportType == null || reportType.isEmpty())) {
	            return ir.findByCategory(category);
	        } else if ((category == null || category.isEmpty()) && reportType != null && !reportType.isEmpty()) {
	            return ir.findByReportType(reportType);
	        } else {
	            return ir.findByCategoryAndReportType(category, reportType);
	        }
	    }

}

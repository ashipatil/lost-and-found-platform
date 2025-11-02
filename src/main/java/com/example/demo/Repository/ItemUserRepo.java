package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Controller.ItemUserController;
import com.example.demo.Entity.ItemUserEntity;

@Repository
public interface ItemUserRepo extends JpaRepository<ItemUserEntity, Integer>{
	
	// ItemUserEntityRepository.java
//	 List<ItemUserEntity> findByUser_User_id(int userId);

	List<ItemUserEntity> findByUser_Id(int id);


	List<ItemUserEntity> findByCategory(String category);
	List<ItemUserEntity> findByReportType(String reportType);
	List<ItemUserEntity> findByCategoryAndReportType(String category, String reportType);
	
	@Query("SELECT COUNT(i) FROM ItemUserEntity i")
	int countAllItems();

	@Query("SELECT COUNT(i) FROM ItemUserEntity i WHERE i.reportType = :type")
	int countByReportType(@Param("type") String type);



}

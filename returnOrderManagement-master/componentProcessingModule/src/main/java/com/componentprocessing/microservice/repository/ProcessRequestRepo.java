package com.componentprocessing.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.componentprocessing.microservice.model.ProcessRequest;

@Repository
public interface ProcessRequestRepo extends JpaRepository<ProcessRequest, String> {
     
	 @Modifying
	 @Query(value="delete from RETURN_ORDER_REQUEST where CREDIT_CARD_NUMBER=:number" , nativeQuery = true)
     void deleteByCreditCardNumber(@Param(value = "number") Integer number);
	 
}

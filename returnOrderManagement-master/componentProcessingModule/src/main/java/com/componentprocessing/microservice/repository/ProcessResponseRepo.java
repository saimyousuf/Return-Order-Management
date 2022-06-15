package com.componentprocessing.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.componentprocessing.microservice.model.ProcessResponse;

@Repository
public interface ProcessResponseRepo extends JpaRepository<ProcessResponse, String> {
    
	List<ProcessResponse> findByRequestId(String requestId);
	
	 @Modifying
	 @Query(value="delete from RETURN_ORDER_RESPONSE where REQUEST_ID=:requestId" , nativeQuery=true)
	 void deleteByRequestId(@Param("requestId") String requestId);

}
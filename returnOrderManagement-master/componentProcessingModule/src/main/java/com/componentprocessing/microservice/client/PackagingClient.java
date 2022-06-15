package com.componentprocessing.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.componentprocessing.microservice.dto.PackagingAndDeliveryDTO;

import feign.Headers;


@Headers("Content-Type: application/json")
@FeignClient(name="pack-feign",url = "localhost:5002/packaging") 
public interface PackagingClient {
	
	@PostMapping("/getPackagingDeliveryCharge/{type}/{count}")
	PackagingAndDeliveryDTO save(@PathVariable String type , @PathVariable int count,@RequestHeader(name = "auth", required = true) String token);
	
	
	
	

}

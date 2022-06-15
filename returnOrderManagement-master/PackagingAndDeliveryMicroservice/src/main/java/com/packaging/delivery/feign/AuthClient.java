package com.packaging.delivery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.packaging.delivery.dto.ValidatingDTO;



@FeignClient(name = "auth-client", url = "http://localhost:5000")
public interface AuthClient {
	
	 @GetMapping(value = "/validate")
     public ValidatingDTO getsValidity(@RequestHeader(name = "auth", required = true) String token);

}

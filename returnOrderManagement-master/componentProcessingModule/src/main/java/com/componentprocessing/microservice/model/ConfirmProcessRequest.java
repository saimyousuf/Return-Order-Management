package com.componentprocessing.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class ConfirmProcessRequest {

	 
	
	private String requestId;
	
    private int creditCardNumber;
    
    private int CreditLimit;
    
    private int totalProcessingCharges;

	
	

}

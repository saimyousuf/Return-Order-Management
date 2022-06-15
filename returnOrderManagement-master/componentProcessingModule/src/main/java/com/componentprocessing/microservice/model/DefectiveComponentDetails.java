package com.componentprocessing.microservice.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class DefectiveComponentDetails {
	
	
	private String ComponentName; 
	
	private String ComponentType;
	
	private int Quantity;
	
	
	
}

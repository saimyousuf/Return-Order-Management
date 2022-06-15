package com.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


	public class ValidatingDTO {
	   
	    @JsonProperty
	    private boolean validStatus;
	    

		public boolean isValidStatus() {
			return validStatus;
		}

		public void setValidStatus(boolean validStatus) {
			this.validStatus = validStatus;
		}
	    
	    
	    
	    
	}



package com.componentprocessing.microservice.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.microservice.model.ProcessResponse;
import com.componentprocessing.microservice.repository.ProcessResponseRepo;

@Service
public class ProcessResponseService {

	@Autowired
	private ProcessResponseRepo processResponseRepo;

	public List<ProcessResponse> getProcessResponses() {
		
		 return this.processResponseRepo.findAll();

	}
	
	public List<ProcessResponse> getRequestId(String requestId){
		return this.processResponseRepo.findByRequestId(requestId);
	}
	
	@Transactional
	public void deleteResponseDetails(String requestId) {
		 this.processResponseRepo.deleteByRequestId(requestId);
	}

}

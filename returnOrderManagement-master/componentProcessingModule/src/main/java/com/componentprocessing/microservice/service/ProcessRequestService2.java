package com.componentprocessing.microservice.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.microservice.model.ProcessRequest;
import com.componentprocessing.microservice.repository.ProcessRequestRepo;

@Service
public class ProcessRequestService2 {

	@Autowired
	private ProcessRequestRepo processRequestRepo;

	public List<ProcessRequest> getProcessRequests() {
		return this.processRequestRepo.findAll();
	}

	@Transactional
	public void removeProcessRequest(Integer creditCardNumber) {

		this.processRequestRepo.deleteByCreditCardNumber(creditCardNumber);

	}

}

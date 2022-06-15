package com.componentprocessing.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.componentprocessing.microservice.dto.PackagingAndDeliveryDTO;

import feign.Headers;

@Headers("Content-Type: application/json")
@FeignClient(name = "payment-service", url = "localhost:5003/payment")
public interface PaymentClient {

	
	
	@PostMapping("/processpayment/{requestId}/{cardNumber}/{creditLimit}/{processingCharge}")
	PackagingAndDeliveryDTO paymentDetails( @PathVariable String requestId,@PathVariable int cardNumber,
			@PathVariable int creditLimit, @PathVariable int processingCharge,@RequestHeader(name = "auth", required = true) String token);
	
	@GetMapping("/helloPay")
	public String fromPayment();
	
	@GetMapping("/ProcessPayment/{CreditCardNumber}/{CreditLimit}/{ProcessingCharge}")
	public int ProcessPayment(@PathVariable Integer CreditCardNumber, @PathVariable int CreditLimit,
			@PathVariable int ProcessingCharge);
	
	@PostMapping("/ReversePayment")
	public int ReversePayment(@PathVariable Integer CreditCardNumber, @PathVariable int CreditLimit,
			@PathVariable int ProcessingCharge);
	
	

}

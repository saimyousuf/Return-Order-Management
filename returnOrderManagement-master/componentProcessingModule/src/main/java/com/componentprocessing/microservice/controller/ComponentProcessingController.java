package com.componentprocessing.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.componentprocessing.microservice.client.AuthClient;
import com.componentprocessing.microservice.client.PaymentClient;
import com.componentprocessing.microservice.dto.APIResponseDTO;
import com.componentprocessing.microservice.dto.CancelRequestDTO;
import com.componentprocessing.microservice.dto.PackagingAndDeliveryDTO;
import com.componentprocessing.microservice.dto.PaymentStatusDTO;
import com.componentprocessing.microservice.exceptions.ComponentTyepNotFoundException;
import com.componentprocessing.microservice.exceptions.InvalidTokenException;
import com.componentprocessing.microservice.model.ConfirmProcessRequest;
import com.componentprocessing.microservice.model.ProcessRequest;
import com.componentprocessing.microservice.model.ProcessResponse;
import com.componentprocessing.microservice.service.ProcessRequestService2;
import com.componentprocessing.microservice.service.ProcessResponseService;
import com.componentprocessing.microservice.service.RepairServiceImpl;
import com.componentprocessing.microservice.service.ReplacementServiceImpl;

import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/componentprocessingmodule")
@Api(value = "Component Processing Resource endpoint")
public class ComponentProcessingController {

	@Autowired
	private RepairServiceImpl repairServiceImplObj;
	@Autowired
	private ReplacementServiceImpl replacementServiceImplObj;
	@Autowired
	private ProcessResponse processResponseObj;
	@Autowired
	private AuthClient authClient;
	@Autowired
	private PaymentClient paymentClient;

	@Autowired
	private ProcessRequestService2 processRequestService2;

	@Autowired
	private ProcessResponseService processResponseService;

	@PostMapping(path = "/ProcessDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "to send the processRequestObj to the user", response = ProcessRequest.class, httpMethod = "POST")
	public ResponseEntity<ProcessResponse> processResponseDetails(@RequestBody ProcessRequest processRequestObj,
			@RequestHeader(name = "auth", required = true) String token) throws InvalidTokenException {

		log.info(token);
		try {

			if (!authClient.getsValidity(token).isValidStatus()) {

				throw new InvalidTokenException("Token is either expired or invalid...");
			}

		} catch (FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");

		}

		log.info("Checking if component is Integral or Accessory - BEGINS");
		if (("Integral").equalsIgnoreCase(processRequestObj.getComponentType())) {
			log.info("Retrieving the ProcessResponse object for Integral - BEGINS");

			processResponseObj = repairServiceImplObj.processService(processRequestObj, token);

			log.info("Retrieving the ProcessResponse object for Integral - ENDS");
		} else if (("Accessory").equalsIgnoreCase(processRequestObj.getComponentType())) {
			log.info("Retrieving the ProcessResponse object for Accessory - BEGINS");

			processResponseObj = replacementServiceImplObj.processService(processRequestObj, token);

			log.info("Retrieving the ProcessResponse object for Accessory - ENDS");
		} else {
			throw new ComponentTyepNotFoundException("Wrong Component Type");
		}

		log.info("Checking if component is Integral or Accessory - ENDS");

		return new ResponseEntity<>(processResponseObj, HttpStatus.OK);

	}

	@PostMapping(path = "/completeProcessing/{requestId}/{creditCardNumber}/{creditLimit}/{processingCharge}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentStatusDTO> messageConfirmation(@PathVariable String requestId,
			@PathVariable Integer creditCardNumber, @PathVariable Integer creditLimit,
			@PathVariable Integer processingCharge, @RequestHeader(name = "auth", required = true) String token)
			throws InvalidTokenException {

		log.info(token);
		try {
			if (!authClient.getsValidity(token).isValidStatus()) {

				throw new InvalidTokenException("Token is either expired or invalid...");
			}

		} catch (FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");

		}
		log.info("Controller Component");
		try {
			return new ResponseEntity<>(new PaymentStatusDTO(replacementServiceImplObj.messageConfirmation(requestId,
					creditCardNumber, creditLimit, processingCharge, token)), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new PaymentStatusDTO(replacementServiceImplObj.messageConfirmation(requestId,
					creditCardNumber, creditLimit, processingCharge, token)), HttpStatus.FORBIDDEN);
		}

	}

	@GetMapping(path = "/health-check")
	public ResponseEntity<String> healthCheck() {
		log.info("ComponentProcessing Microservice is Up and Running....");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

//	o	GET: /ProcessDetail (Input: ProcessRequest | Output: ProcessResponse)

	@GetMapping("/ProcessDetail")
	public ProcessResponse getProcessDetail(@RequestBody ProcessRequest processRequest,
			@RequestHeader(name = "auth", required = true) String token) {

		if (processRequest.getComponentType().equalsIgnoreCase("integral-repair")) {
			return repairServiceImplObj.processService(processRequest, token);

		}

		return replacementServiceImplObj.processService(processRequest, token);
	}

//	o	POST: /CompleteProcessing (Input: ConfirmProcessRequest) | Output: string response of the success of operation)

	@PostMapping("/CompleteProcessing")
	public ResponseEntity<?> CompleteProcessing(@RequestBody ConfirmProcessRequest confirmProcessRequest,
			@RequestHeader(name = "auth", required = true) String token) {

		PackagingAndDeliveryDTO charge = paymentClient.paymentDetails(confirmProcessRequest.getRequestId(),
				confirmProcessRequest.getCreditCardNumber(), confirmProcessRequest.getCreditLimit(),
				confirmProcessRequest.getTotalProcessingCharges(), token);
       APIResponseDTO response  = new APIResponseDTO();
       response.setMessage("Successfull Processing completed and the charge is " + Math.abs(charge.getCharge()));
       
		return ResponseEntity.ok(response);

	}

//  o	POST: /CancelRequest (Input: RequestId, CreditCardNumber | Output: string response of the success of operation)

	@PostMapping("/CancelRequest")
	public ResponseEntity<?> cancelRequest(@RequestBody CancelRequestDTO cancelRequestDTO,
			@RequestHeader(name = "auth", required = true) String token) throws InvalidTokenException {

		log.info(token);
		try {

			if (!authClient.getsValidity(token).isValidStatus()) {

				throw new InvalidTokenException("Token is either expired or invalid...");
			}

		} catch (FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");

		}
		
		String requestId = cancelRequestDTO.getRequestId();
		Integer creditCardNumber = cancelRequestDTO.getCreditCardNumber();

		this.processRequestService2.removeProcessRequest(creditCardNumber);
		this.processResponseService.deleteResponseDetails(requestId);

		APIResponseDTO response = new APIResponseDTO();
		response.setMessage("Order with the id \" + cancelRequestDTO.getRequestId() + \" cancelled\"");

		return ResponseEntity.ok(response);
	}

//	o	GET: /TrackRequest (Input: RequestId | Output: string response as delivery date)

	@GetMapping("/TrackRequest/{requestId}")
	public ResponseEntity<?> trackRequest(@PathVariable("requestId") String requestId) {

		APIResponseDTO response = new APIResponseDTO();

		List<ProcessResponse> requestIds = this.processResponseService.getRequestId(requestId);
		if (requestIds.size() == 0) {
			response.setMessage("Id :" + requestId + " not found");
			return ResponseEntity.ok(response);
		}
		ProcessResponse p = requestIds.get(0);
		response.setMessage(
				"Your order with id " + requestId + " will be delivered on " + p.getDateOfDelivery().toString());
		return ResponseEntity.ok(response);
	}

	// o GET all the process responses

	@GetMapping("/ProcessResponses")
	public ResponseEntity<?> getAllProcessResponses(@RequestHeader(name = "auth", required = true) String token)
			throws InvalidTokenException {

		log.info(token);
		try {

			if (!authClient.getsValidity(token).isValidStatus()) {

				throw new InvalidTokenException("Token is either expired or invalid...");
			}

		} catch (FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");

		}

		List<ProcessResponse> processResponses = this.processResponseService.getProcessResponses();

		return new ResponseEntity<>(processResponses, HttpStatus.OK);

	}

	@GetMapping("/ProcessRequests")
	public ResponseEntity<?> getAllProcessRequests(@RequestHeader(name = "auth", required = true) String token)
			throws InvalidTokenException {

		log.info(token);
		try {

			if (!authClient.getsValidity(token).isValidStatus()) {

				throw new InvalidTokenException("Token is either expired or invalid...");
			}

		} catch (FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");

		}

		List<ProcessRequest> processRequests = this.processRequestService2.getProcessRequests();

		return new ResponseEntity<>(processRequests, HttpStatus.OK);

	}

}
package com.maxtrain.bootcamp.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	
	@Autowired
	private RequestRepository reqRepo;
	
	// ALL
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequest() {
		var request = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(request, HttpStatus.OK);
	}
	// PK
	@GetMapping("{id}")
	public ResponseEntity<Request> GetById(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	// GET Review Status
	@GetMapping("reviews/{userId}")
	public ResponseEntity<Iterable<Request>> getRequestsInReview(@PathVariable int userId) {
		var requests = reqRepo.findByStatusAndUserIdNot("REVIEW", userId);
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	 //Add
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request == null || request.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var requ = reqRepo.save(request);
		return new ResponseEntity<Request>(requ, HttpStatus.CREATED);
	}
	// PUT Review Status
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request) {
		var status = (request.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		request.setStatus(status);
		return putRequest(id, request);
	}
	
	// PUT Approve
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("APPROVED");
		return putRequest(id, request);
	}
	
	// PUT Reject
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("REJECTED");
		return putRequest(id, request);
	}
	
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if(request == null || request.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var requ = reqRepo.findById(request.getId());
		if(requ.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}			
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// Delete
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	reqRepo.delete(request.get());
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

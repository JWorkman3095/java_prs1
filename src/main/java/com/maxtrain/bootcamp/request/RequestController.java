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
	
	@GetMapping // ALL
	public ResponseEntity<Iterable<Request>> getRequest() {
		var request = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(request, HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Request> GetById(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	// GET Review Status
	//@GetMapping("/review/{id}")
	//public list<request> getAllReviewRequests(PathVariable int id) {
		
	//}
	
	 //Add
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request == null || request.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var requ = reqRepo.save(request);
		return new ResponseEntity<Request>(requ, HttpStatus.CREATED);
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

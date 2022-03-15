package com.maxtrain.bootcamp.requestline;

//import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import com.maxtrain.bootcamp.request.Request;
import com.maxtrain.bootcamp.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")

public class RequestlineController {
	
	@Autowired
	private RequestLineRepository reqlRepo;
	@Autowired
	private RequestRepository reqRepo;
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity recalcRequestTotal(int requestId) {
		var reqOpt = reqRepo.findById(requestId);
		if(reqOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var request = reqOpt.get();
		var requestTotal = 0;
		for(var requestline : request.getRequestlines()) {
			requestTotal += requestline.getProduct().getPrice()*requestline.getQuantity();
		}
		request.setTotal(requestTotal);
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// ALL
	@GetMapping 
	public ResponseEntity<Iterable<RequestLine>> getRequestlines(){
		var requestlines = reqlRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(requestlines, HttpStatus.OK);
	}
	// PK
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> getRequestline(@PathVariable int id) {
		var requ = reqlRepo.findById(id);
		if (requ.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(requ.get(), HttpStatus.OK);
	}
	//POST
	@PostMapping
	public ResponseEntity<RequestLine> postRequestline(@RequestBody RequestLine requestline) throws Exception {
		if(requestline == null || requestline.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var reql = reqlRepo.save(requestline);
		var respEntity = this.recalcRequestTotal(reql.getRequest() .getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculation request failed!");
		}
		return new ResponseEntity<RequestLine>(reql, HttpStatus.CREATED);
	}
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestline(@PathVariable int id, @RequestBody RequestLine requestline) throws Exception {
		if(requestline == null || requestline.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var reqlOpt = reqlRepo.findById(requestline.getId());
		if(reqlOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var reql = reqlOpt.get();
		reqlRepo.save(reql);
		var respEntity = this.recalcRequestTotal(reql.getRequest().getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculation request failed!");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//Delete
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestline(@PathVariable int id) throws Exception {
		var requestOpt = reqlRepo.findById(id);
		if(requestOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		var request = requestOpt.get();
		reqlRepo.delete(request);
		var respEntity = this.recalcRequestTotal(request.getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculation request failed");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}

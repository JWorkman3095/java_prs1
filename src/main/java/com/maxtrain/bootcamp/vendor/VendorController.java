package com.maxtrain.bootcamp.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")

public class VendorController {

	@Autowired
	private VendorRepository vendorRepo;
	
	//ALL
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors(){
		var vendors = vendorRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	//PK
	@GetMapping("{id}")
	public ResponseEntity<Vendor> GetById(@PathVariable int id) {
		var vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	//Code
	@GetMapping("code/{code}")
	public ResponseEntity<Vendor> getVendorByCode(@PathVariable String code) {
		var vendor = vendorRepo.findByCode(code);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	 //Add
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		if(vendor == null || vendor.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var vend = vendorRepo.save(vendor);
		return new ResponseEntity<Vendor>(vend, HttpStatus.CREATED);
	}
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if(vendor == null || vendor.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var vend = vendorRepo.findById(vendor.getId());
		if(vend.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}			
		vendorRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
		
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		var vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	vendorRepo.delete(vendor.get());
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}

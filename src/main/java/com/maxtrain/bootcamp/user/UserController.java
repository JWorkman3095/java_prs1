package com.maxtrain.bootcamp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
//sending and receiving json
@RestController
@RequestMapping("/api/users") 

public class UserController {

	@Autowired
	private UserRepository useRepo;
	
	@GetMapping // ALL
	public ResponseEntity<Iterable<User>> getUser() {
		var user = useRepo.findAll();
		return new ResponseEntity<Iterable<User>>(user, HttpStatus.OK);
	}
	@GetMapping ("{id}") //PK
	public ResponseEntity<User> getUser(@PathVariable int id) {
		var user = useRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);		
	}
	@PostMapping // ADD POST
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if (user == null || user.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var use = useRepo.save(user);
		return new ResponseEntity<User>(use, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}") // Add PUT
	public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
		if (user == null || user.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var use = useRepo.findById(user.getId());
		if(use.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		useRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		var use = useRepo.findById(id);
		if (use.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		useRepo.delete(use.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

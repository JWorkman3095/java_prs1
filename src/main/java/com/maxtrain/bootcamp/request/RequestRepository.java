package com.maxtrain.bootcamp.request;

import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository <Request, Integer>{
	
	Iterable<Request> findByUserIdNotAndStatus(String status, int userId);
}

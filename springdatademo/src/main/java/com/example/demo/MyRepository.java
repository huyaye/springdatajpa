package com.example.demo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID>{
	<E extends T> E save(E entity);
	
	List<T> findAll();
}

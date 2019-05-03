package com.kou.sbmm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kou.sbmm.entity.Spmmdb0001;


public interface Spmmdb0001Service {
	void save(Spmmdb0001 spmmdb0001);
	
	void delete(Integer id);

	List<Spmmdb0001> getAll();

	Optional<Spmmdb0001> findById(Integer id);
	
	boolean checkSinuped(String mail);
	
	Spmmdb0001 findByMail(String mail);

}
package com.kou.sbmm.service;

import java.util.List;
import java.util.Optional;

import com.kou.sbmm.entity.Spmmdb0002;


public interface Spmmdb0002Service {
	void save(Spmmdb0002 spmmdb0002);
	
	void delete(Integer id);

	List<Spmmdb0002> getAll();

	Optional<Spmmdb0002> findById(Integer id);
	
	List<Spmmdb0002> findList(Integer accid);

}
package com.kou.sbmm.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kou.sbmm.entity.Spmmdb0001;

public interface Spmmdb0001Dao extends CrudRepository<Spmmdb0001, Integer> {
	public List<Spmmdb0001> findByAccmail(@Param("accmail") String accmail);
	

}
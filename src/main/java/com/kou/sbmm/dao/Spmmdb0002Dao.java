package com.kou.sbmm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kou.sbmm.entity.Spmmdb0002;

@Repository
public interface Spmmdb0002Dao extends CrudRepository<Spmmdb0002, Integer> {
	@Query("FROM Spmmdb0002 r WHERE r.accid = :accid or r.msgpublictype = '1' order by r.createdatetime desc")
	public List<Spmmdb0002> findList(@Param("accid") Integer accid);
	

}
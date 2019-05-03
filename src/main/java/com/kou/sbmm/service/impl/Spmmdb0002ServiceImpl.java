package com.kou.sbmm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kou.sbmm.entity.Spmmdb0001;
import com.kou.sbmm.entity.Spmmdb0002;
import com.kou.sbmm.service.Spmmdb0002Service;
import com.kou.sbmm.dao.Spmmdb0002Dao;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;

@Component
public class Spmmdb0002ServiceImpl implements Spmmdb0002Service {
	@Autowired
	private Spmmdb0002Dao Spmmdb0002Dao;

	/**
	 * save,update,delete方法需要绑定事务 使用@Transactional进行事务绑定
	 */
	/**
	 * 保存数据
	 * 
	 * @param Spmmdb0002
	 */
	// @Transactional
	@Override
	public void save(Spmmdb0002 Spmmdb0002) {
		Spmmdb0002Dao.save(Spmmdb0002);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(Integer id) {
		Spmmdb0002Dao.deleteById(id);
	}

	@Override
	public Optional<Spmmdb0002> findById(Integer id) {
		return Spmmdb0002Dao.findById(id);
	}

	@Override
	public List<Spmmdb0002> getAll() {
		Iterable<Spmmdb0002> Spmmdb0002s = Spmmdb0002Dao.findAll();
		Iterator<Spmmdb0002> iter = Spmmdb0002s.iterator();
		List<Spmmdb0002> res = new ArrayList<>();
		while (iter.hasNext()) {
			res.add(iter.next());
		}

		return res;
	}

	@Override
	public List<Spmmdb0002> findList(Integer accid) {
		Iterable<Spmmdb0002> Spmmdb0002s = Spmmdb0002Dao.findList(accid);
		Iterator<Spmmdb0002> iter = Spmmdb0002s.iterator();
		List<Spmmdb0002> res = new ArrayList<>();
		if (res != null) {
			while (iter.hasNext()) {
				res.add(iter.next());
			}
		}

		return res;
	}

}
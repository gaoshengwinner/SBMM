package com.kou.sbmm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kou.sbmm.entity.Spmmdb0001;
import com.kou.sbmm.service.Spmmdb0001Service;
import com.kou.sbmm.dao.Spmmdb0001Dao;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;

@Component
public class Spmmdb0001ServiceImpl implements Spmmdb0001Service {
	@Autowired
	private Spmmdb0001Dao Spmmdb0001Dao;

	/**
	 * save,update,delete方法需要绑定事务 使用@Transactional进行事务绑定
	 */
	/**
	 * 保存数据
	 * 
	 * @param Spmmdb0001
	 */
	//@Transactional
	@Override
	public void save(Spmmdb0001 Spmmdb0001) {
		Spmmdb0001Dao.save(Spmmdb0001);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(Integer id) {
		Spmmdb0001Dao.deleteById(id);
	}

	
	@Override
	public Optional<Spmmdb0001> findById(Integer id) {
		return Spmmdb0001Dao.findById(id);
	}

	/**
     * 查询数据
     * @return
     */
    @Override
    public List<Spmmdb0001> getAll() {

        Iterable<Spmmdb0001> Spmmdb0001s = Spmmdb0001Dao.findAll();
        Iterator<Spmmdb0001> iter = Spmmdb0001s.iterator();
        List<Spmmdb0001> res = new ArrayList<>();
        while(iter.hasNext()) {
            res.add(iter.next());
        }

        return res;
    }
    
    @Override
    public  boolean checkSinuped(String mail) {
    	List<Spmmdb0001> list = Spmmdb0001Dao.findByAccmail(mail);
    	return list == null || list.size()==0 ? true : false;
    	
    }
    
    @Override
    public Spmmdb0001 findByMail(String mail){
    	
    	List<Spmmdb0001> list = Spmmdb0001Dao.findByAccmail(mail);
    	if (list == null || list.size() < 1) {
    		return null;
    	}
    	return list.get(0);
    	
    }
    
}
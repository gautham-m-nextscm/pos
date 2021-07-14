package com.shubham.increff.service;

import com.shubham.increff.dao.BrandCategoryDao;
import com.shubham.increff.pojo.BrandCategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandCategoryService {

    @Autowired
    private BrandCategoryDao dao;

    @Transactional
    public void add(BrandCategoryPojo p) {
        normalize(p);
        dao.insert(p);
    }


    @Transactional(rollbackOn = ApiException.class)
    public BrandCategoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandCategoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandCategoryPojo p) throws ApiException {
        normalize(p);
        BrandCategoryPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        dao.update(p);//transaction
    }

    @Transactional
    public BrandCategoryPojo getCheck(int id) throws ApiException {
        BrandCategoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand category with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(BrandCategoryPojo p) {
        p.setBrand(p.getBrand().toLowerCase().trim());
        p.setCategory(p.getCategory().toLowerCase().trim());
    }
}

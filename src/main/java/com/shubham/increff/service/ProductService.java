package com.shubham.increff.service;

import com.shubham.increff.dao.ProductDao;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Transactional
    public void add(ProductPojo p) {
        normalize(p);
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setBrand_category(p.getBrand_category());
        ex.setMrp(p.getMrp());
        ex.setBarcode(p.getBarcode());
        dao.update(p);
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }
//
    protected static void normalize(ProductPojo p) {
        p.setName(p.getName().toLowerCase().trim());
        p.setBarcode(p.getBarcode().toLowerCase().trim());
    }
}

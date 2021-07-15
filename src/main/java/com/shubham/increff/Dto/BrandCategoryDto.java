package com.shubham.increff.Dto;

import com.shubham.increff.model.BrandCategoryData;
import com.shubham.increff.model.BrandCategoryForm;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.BrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService service;


    public void add(BrandCategoryForm form){
        BrandCategoryPojo pojo = convert(form);
        service.add(pojo);
    }
    public List<BrandCategoryData> getAll() {
        List<BrandCategoryPojo> BrandCategoryPojoList = service.getAll();
        List<BrandCategoryData> BrandCategoryDataList = new ArrayList<BrandCategoryData>();
        for (BrandCategoryPojo p : BrandCategoryPojoList) {
            BrandCategoryDataList.add(convert(p));
        }
        return BrandCategoryDataList;
    }

    public BrandCategoryData get(int id) throws ApiException {
        BrandCategoryPojo pojo = service.get(id);
        return convert(pojo);
    }

    public void update( int id, BrandCategoryForm form) throws ApiException {
        BrandCategoryPojo pojo = convert(form);
        service.update(id, pojo);
    }

    private static BrandCategoryData convert(BrandCategoryPojo pojo) {
        BrandCategoryData data = new BrandCategoryData();
        data.setBrand(pojo.getBrand());
        data.setCategory(pojo.getCategory());
        data.setId(pojo.getId());
        return data;
    }

    private static BrandCategoryPojo convert(BrandCategoryForm form) {
        BrandCategoryPojo pojo = new BrandCategoryPojo();
        pojo.setBrand(form.getBrand());
        pojo.setCategory(form.getCategory());
        return pojo;
    }
}

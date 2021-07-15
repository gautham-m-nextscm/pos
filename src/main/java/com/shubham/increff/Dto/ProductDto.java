package com.shubham.increff.Dto;

import com.shubham.increff.model.*;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.pojo.ProductPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.BrandCategoryService;
import com.shubham.increff.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDto {

    @Autowired
    private ProductService service;
    @Autowired
    private  BrandCategoryService brandCategoryService;

    public void add(ProductForm form) {
        ProductPojo pojo = convert(form);
        service.add(pojo);
    }
    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojoList = service.getAll();
        List<ProductData> productData = new ArrayList<ProductData>();
        for (ProductPojo productPojo : productPojoList) {
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrand_category());
            productData.add(convert(productPojo,brandCategoryPojo));
        }
        return productData;
    }
    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo = service.get(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrand_category());
        return convert(productPojo,brandCategoryPojo);
    }
    public void update( int id, ProductForm form) throws ApiException {
        ProductPojo pojo = convert(form);
        service.update(id, pojo);
    }


    private static ProductPojo convert(ProductForm form) {
        ProductPojo pojo = new ProductPojo();
        pojo.setName(form.getName());
        pojo.setBarcode(form.getBarcode());
        pojo.setBrand_category(form.getBrand_category());
        pojo.setMrp(form.getMrp());
        return pojo;
    }


    private static ProductData convert(ProductPojo productPojo,BrandCategoryPojo brandCategoryPojo) {
        ProductData productData = new ProductData();
        productData.setName(productPojo.getName());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand_category(productPojo.getBrand_category());
        productData.setMrp(productPojo.getMrp());
        productData.setId(productPojo.getId());
        productData.setBrand(brandCategoryPojo.getBrand());
        productData.setCategory(brandCategoryPojo.getCategory());
        return productData;
    }
}

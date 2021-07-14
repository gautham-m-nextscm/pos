package com.shubham.increff.controller;

import com.shubham.increff.model.BrandCategoryData;
import com.shubham.increff.model.BrandCategoryForm;
import com.shubham.increff.model.ProductData;
import com.shubham.increff.model.ProductForm;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.pojo.ProductPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.BrandCategoryService;
import com.shubham.increff.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @ApiOperation(value = "Add Products")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) {
        ProductPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Get product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        ProductPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Update  Product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        ProductPojo p = convert(f);
        service.update(id, p);
    }

    private static ProductData convert(ProductPojo p) {
        ProductData d = new ProductData();
        d.setName(p.getName());
        d.setBarcode(p.getBarcode());
        d.setBrand_category(p.getBrand_category());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        return d;
    }


    private static ProductPojo convert(ProductForm f) {
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrand_category(f.getBrand_category());
        p.setMrp(f.getMrp());
        return p;
    }

}

package com.shubham.increff.controller;

import com.shubham.increff.Dto.ProductDto;
import com.shubham.increff.model.*;
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
    private ProductDto dto;

    @ApiOperation(value = "Add Products")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) {
        dto.add(form);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Get product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Update  Product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
        dto.update(id, form);
    }

}

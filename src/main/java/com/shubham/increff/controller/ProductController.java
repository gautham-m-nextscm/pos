package com.shubham.increff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductController {


    @ApiOperation(value = "Hello World")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World";
    }



}

package com.shubham.increff.controller;

import com.shubham.increff.Dto.OrderDto;
import com.shubham.increff.model.*;
import com.shubham.increff.pojo.OrderPojo;
import com.shubham.increff.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api
@RestController
public class OrderController {
    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Create Order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public OrderPojo add(@RequestBody OrderForm orderForm) throws ApiException {
       return orderDto.createOrder(orderForm);
    }

    @ApiOperation(value = "Gets Order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) throws ApiException {
        return orderDto.getOrderById(id);
    }

    @ApiOperation(value = "Get all Orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData>  getAll() throws ApiException {
        return orderDto.getAllOrders();
    }


    @ApiOperation(value = "Update Order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderUpdateForm orderUpdateForm) throws ApiException {
        orderDto.updateOrder(id, orderUpdateForm);
    }
    public void update(OrderPojo p) {
    }


}

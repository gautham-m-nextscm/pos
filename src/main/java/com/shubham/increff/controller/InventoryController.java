package com.shubham.increff.controller;

import com.shubham.increff.Dto.InventoryDto;
import com.shubham.increff.model.InventoryData;
import com.shubham.increff.model.InventoryForm;
import com.shubham.increff.pojo.InventoryPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryController {
    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Add Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryPojo inventoryPojo) {
        dto.add(inventoryPojo);
    }

    @ApiOperation(value = "Gets list of all available inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Gets Inventory by Product ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Updates  Brand and Category")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventoryForm) throws ApiException {
        dto.update(id, inventoryForm);
    }

}

package com.shubham.increff.controller;

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
    private InventoryService service;

    @ApiOperation(value = "Add Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryPojo p) {
        service.add(p);
    }

    @ApiOperation(value = "Gets list of all available inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() {
        List<InventoryPojo> list = service.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Gets Inventory by Product ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        InventoryPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Updates  Brand and Category")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.update(id, p);
    }

    private static InventoryData convert(InventoryPojo p) {
        InventoryData d = new InventoryData();
        d.setQuantity(p.getQuantity());
        d.setId(p.getId());
        return d;
    }

    private static InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        return p;
    }

}

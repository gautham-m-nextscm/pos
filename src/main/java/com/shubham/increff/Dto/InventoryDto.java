package com.shubham.increff.Dto;

import com.shubham.increff.model.InventoryData;
import com.shubham.increff.model.InventoryForm;
import com.shubham.increff.model.ProductBrandCategoryForm;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.pojo.InventoryPojo;
import com.shubham.increff.pojo.ProductPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.BrandCategoryService;
import com.shubham.increff.service.InventoryService;
import com.shubham.increff.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryDto {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandCategoryService brandCategoryService;

    public void add(InventoryPojo inventoryPojo) {
        service.add(inventoryPojo);
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = service.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            ProductPojo productPojo = productService.get(inventoryPojo.getId());
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrand_category());
            inventoryDataList.add(convert(inventoryPojo,productPojo,brandCategoryPojo));
        }
        return inventoryDataList;
    }

    public InventoryData get(int id) throws ApiException {
        InventoryPojo inventoryPojo = service.get(id);
        ProductPojo productPojo = productService.get(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrand_category());
        return convert(inventoryPojo,productPojo,brandCategoryPojo);
    }

    public void update(int id,InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = convert(inventoryForm);
        service.update(id, inventoryPojo);
    }

    private static InventoryData convert(InventoryPojo inventoryPojo,ProductPojo productPojo,BrandCategoryPojo brandCategoryPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setBarcode(productPojo.getBarcode());
        inventoryData.setBrand_category(productPojo.getBrand_category());
        inventoryData.setName(productPojo.getName());
        inventoryData.setMrp(productPojo.getMrp());
        inventoryData.setBrand(brandCategoryPojo.getBrand());
        inventoryData.setCategory(brandCategoryPojo.getCategory());
        return inventoryData;
    }

    private static InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        return p;
    }

}

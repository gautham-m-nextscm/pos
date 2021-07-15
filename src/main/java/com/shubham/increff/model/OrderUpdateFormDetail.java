package com.shubham.increff.model;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderUpdateFormDetail {

    private String barcode;
    private double sellingprice;
    private int quantity;
    private int orderItemId;

}

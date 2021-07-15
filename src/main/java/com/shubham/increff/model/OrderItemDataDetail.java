package com.shubham.increff.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderItemDataDetail extends OrderFormDetail {

    private int productId;
    private String name;
    private  String brand;
    private  String category;
    private  int orderItemId;

}

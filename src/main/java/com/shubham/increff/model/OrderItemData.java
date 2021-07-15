package com.shubham.increff.model;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderItemData {
    private Date orderDateTime;
    private List<OrderItemDataDetail> orderItemDataDetailList;
}

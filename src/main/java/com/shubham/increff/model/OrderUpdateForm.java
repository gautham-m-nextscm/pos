package com.shubham.increff.model;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderUpdateForm {
    private Date orderDateTime;
    private List<OrderUpdateFormDetail> orderUpdateFormDetailList;
}

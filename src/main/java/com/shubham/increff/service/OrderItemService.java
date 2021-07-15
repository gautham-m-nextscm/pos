package com.shubham.increff.service;

import com.shubham.increff.dao.OrderDao;
import com.shubham.increff.dao.OrderItemDao;
import com.shubham.increff.pojo.OrderItemPojo;
import com.shubham.increff.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional
    public void add(OrderItemPojo orderItemPojo) {
        orderItemDao.insert(orderItemPojo);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateOrderItem(int id, OrderItemPojo p) throws ApiException {
        OrderItemPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        orderItemDao.update(p);
    }
    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public OrderItemPojo getCheck(int id) throws ApiException {
        OrderItemPojo p = orderItemDao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    @Transactional
    public List<OrderItemPojo> getAllOrderItemByOrderId(int orderId) {
        return orderItemDao.selectAllByOrderId(orderId);
    }
}

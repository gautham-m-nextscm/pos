package com.shubham.increff.service;

import com.shubham.increff.dao.OrderDao;
import com.shubham.increff.dao.ProductDao;
import com.shubham.increff.pojo.InventoryPojo;
import com.shubham.increff.pojo.OrderPojo;
import com.shubham.increff.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void createOrder(OrderPojo orderPojo) {
         orderDao.insert(orderPojo);
    }
    @Transactional
    public int getId(OrderPojo orderPojo) {
        return orderDao.getId(orderPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo p = orderDao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updateOrder(int id, OrderPojo p) throws ApiException {
        OrderPojo ex = getCheck(id);
        ex.setDate_time(p.getDate_time());
        orderDao.update(p);
    }
    @Transactional
    public List<OrderPojo> getAll() {
        return orderDao.selectAll();
    }
}

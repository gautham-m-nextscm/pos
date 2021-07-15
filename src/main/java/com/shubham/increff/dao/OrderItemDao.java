package com.shubham.increff.dao;

import com.shubham.increff.pojo.OrderItemPojo;
import com.shubham.increff.pojo.OrderPojo;
import com.shubham.increff.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao {

    private static String select_all_by_order_id = "select p from OrderItemPojo p where orderid=:orderId";
    private static String select_id = "select p from OrderItemPojo p where id=:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo) {
        em.persist(orderItemPojo);
    }

    public OrderItemPojo select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    public void update(OrderItemPojo p) {
    }
    public List<OrderItemPojo> selectAllByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(select_all_by_order_id);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderItemPojo.class);
    }

}

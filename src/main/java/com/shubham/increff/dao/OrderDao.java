package com.shubham.increff.dao;

import com.shubham.increff.pojo.OrderPojo;
import com.shubham.increff.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao {

    private static String select_all = "select p from OrderPojo p";
    private static String select_id = "select p from OrderPojo p where id=:id";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo orderPojo) {
        em.persist(orderPojo);
    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public int getId(OrderPojo orderPojo) {
        int id = orderPojo.getId();
        return  id;
    }
    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all);
        return query.getResultList();
    }
    public void update(OrderPojo p) {
    }

    TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }

}

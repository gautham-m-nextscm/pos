package com.shubham.increff.dao;

import com.shubham.increff.pojo.BrandCategoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandCategoryDao {

    private static String select_id = "select p from BrandCategoryPojo p where id=:id";
    private static String select_all = "select p from BrandCategoryPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandCategoryPojo p) {
        em.persist(p);
    }

    public BrandCategoryPojo select(int id) {
        TypedQuery<BrandCategoryPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<BrandCategoryPojo> selectAll() {
        TypedQuery<BrandCategoryPojo> query = getQuery(select_all);
        return query.getResultList();
    }

    public void update(BrandCategoryPojo p) {
    }

    TypedQuery<BrandCategoryPojo> getQuery(String jpql) {
        return em.createQuery(jpql, BrandCategoryPojo.class);
    }
}

package com.javarush.filippova.dao;

import com.javarush.filippova.domain.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO {
    private final SessionFactory sessionFactory;

    public CityDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getItems(int offset, int limit) {
        Query<City> query = sessionFactory.getCurrentSession()
                .createQuery("select c from City c", City.class)
                .setFirstResult(offset)
                .setMaxResults(limit);
        //query.setFirstResult(offset);
        //query.setMaxResults(limit);
        return query.list();
    }

    public int getTotalCount() {
        return Math.toIntExact(sessionFactory.getCurrentSession()
                .createQuery("select count(c) from City c", Long.class)
                .uniqueResult());
        //return Math.toIntExact(query.uniqueResult());
    }

    public City getById(Integer id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select c from City c join fetch c.country where c.id = :ID", City.class)
                .setParameter("ID", id)
                .getSingleResult();
        //query.setParameter("ID", id);
        //return query.getSingleResult();
    }
}

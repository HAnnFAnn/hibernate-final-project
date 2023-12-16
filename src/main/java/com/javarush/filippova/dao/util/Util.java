package com.javarush.filippova.dao.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.filippova.dao.CityDAO;
import com.javarush.filippova.dao.CountryDAO;
import com.javarush.filippova.dao.config.PropertiesSessionFactoryProvider;
import com.javarush.filippova.domain.City;
import com.javarush.filippova.domain.Country;
import io.lettuce.core.RedisClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class Util {

    private final SessionFactory sessionFactory;
    private final RedisClient redisClient;
    private final ObjectMapper mapper;
    public final CityDAO cityDAO;
    public final CountryDAO countryDAO;

    private SessionFactory prepareRelationalDb() {
        final SessionFactory sessionFactory = new PropertiesSessionFactoryProvider().getSessionFactory();
        return sessionFactory;
    }

    public Util() {
        sessionFactory = prepareRelationalDb();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        redisClient = prepareRedisClient();
        mapper = new ObjectMapper();
    }

    public List<City> fetchData(Util util) {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();

            List<Country> countries = countryDAO.getAll();
            int totalCount = cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityDAO.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

    public void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

    private RedisClient prepareRedisClient() {
        return null;
    }

}

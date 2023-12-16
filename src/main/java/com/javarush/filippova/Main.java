package com.javarush.filippova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.filippova.dao.CityDAO;
import com.javarush.filippova.dao.CountryDAO;
import com.javarush.filippova.dao.config.PropertiesSessionFactoryProvider;
import com.javarush.filippova.dao.util.Util;
import com.javarush.filippova.domain.City;
import io.lettuce.core.RedisClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class Main {
//    private final SessionFactory sessionFactory;
//    private final RedisClient redisClient;
//
//    private final ObjectMapper mapper;
//
//    private final CityDAO cityDAO;
//    public final CountryDAO countryDAO;
//
//    private SessionFactory prepareRelationalDb() {
//        final SessionFactory sessionFactory = new PropertiesSessionFactoryProvider().getSessionFactory();
//        return sessionFactory;
//    }
//
//    public Main() {
//        sessionFactory = prepareRelationalDb();
//        cityDAO = new CityDAO(sessionFactory);
//        countryDAO = new CountryDAO(sessionFactory);
//
//        redisClient = prepareRedisClient();
//        mapper = new ObjectMapper();
//    }
//


    public static void main(String[] args) {
       // Main main = new Main();
        //00000
        Util util = new Util();
        List<City> allCities = util.fetchData(util);
        util.shutdown();
    }





}

package com.javarush.filippova;

import com.javarush.filippova.dao.redis.CityCountry;
import com.javarush.filippova.dao.util.Util;
import com.javarush.filippova.domain.City;

import java.util.List;

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
        Util util = new Util();
        List<City> allCities = util.fetchData();
        List<CityCountry> preparedData = util.transformData(allCities);

        util.pushToRedis(preparedData);

        util.shutdown();
    }
}

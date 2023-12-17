import com.fasterxml.jackson.core.JsonProcessingException;
import com.javarush.filippova.dao.redis.CityCountry;
import com.javarush.filippova.dao.util.Util;
import com.javarush.filippova.domain.City;
import com.javarush.filippova.domain.CountryLanguage;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MainTest {

    static Util util = new Util();

    @BeforeAll
    static void setup() {
        List<City> allCities = util.fetchData();
        List<CityCountry> preparedData = util.transformData(allCities);
        util.pushToRedis(preparedData);
    }

    @AfterAll
    static void tear() {
        util.getSessionFactory().close();
        util.getRedisClient().close();
    }

    @Test
    void TestRedisQueryFasterThanMySQLQuery() {
        //закроем текущую сессию, чтоб точно делать запрос к БД, а не вытянуть данные из кэша
        util.getSessionFactory().getCurrentSession().close();

        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        assertTrue((stopRedis - startRedis) < (stopMysql - startMysql));

    }

    private void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = util.getRedisClient().connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    util.getMapper().readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void testMysqlData(List<Integer> ids) {
        try (Session session = util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = util.getCityDAO().getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        }
    }
}

package jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Author Mi
 * @Date 2022/4/18 13:01
 * @Version 1.0
 */


/**
 * 方法名和命令相同
 */
public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.78.132",6379);
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }


    @Test
    public void test1(){
        Jedis jedis = new Jedis("192.168.78.132",6379);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        jedis.set("test1","testValue");
        String test1 = jedis.get("test1");
        System.out.println(test1);
    }


    @Test
    public void test2(){
        Jedis jedis = new Jedis("192.168.78.132",6379);
        jedis.sadd("testSet","setValue1","setValue2");
        Set<String> testSet = jedis.smembers("testSet");
        System.out.println(testSet);
    }

}

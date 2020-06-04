package com.baidu.aopcont;

import com.baidu.aopcont.bo.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AopcontApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("this is what ?");
    }


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRedisDemo() {
        String key = "hashKey02";
        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "22");
        map.put("sal", "300.89");

        // json 中将对象转为json，json转为对象
        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper, false);

        // 使用阿里的序列化方式将数据的值  序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 将对象转为map
        redisTemplate.opsForHash().putAll(key,jackson2HashMapper.toHash(map));

        Map<Object, Object> hashKey = redisTemplate.opsForHash().entries(key);

        // 将map转为对象
        Person person = objectMapper.convertValue(hashKey, Person.class);

        System.out.println(person);
    }

    @Test
    public void testJedisDemo() {
        Jedis jedis = new Jedis("192.168.190.128");

        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "22");
        map.put("sal", "300.89");
        //jedis.hset(setKey, map);

        String setKey = "hashKey";

        Map<String, String> stringStringMap = jedis.hgetAll(setKey);
        System.out.println(stringStringMap);
    }


    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.190.128");
        Map<String, String> hashKey03 = jedis.hgetAll("hashKey03");
        System.out.println(hashKey03);
    }

    @Test
    public void testGson() {
        String key = "jsonKey";
        Gson gson = new Gson();

//        Person person = new Person();
//        person.setName("图里深");
//        person.setSal(300.88);
//        person.setAge(54);
//        person.setBirthday(new Date());
//        redisTemplate.opsForValue().set(key, gson.toJson(person));

        String jsonKey = redisTemplate.opsForValue().get(key);
        Person getPerson = gson.fromJson(jsonKey, Person.class);
        System.out.println(getPerson);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getPerson.getBirthday()));
    }

    @Test
    public void testGson2() {
        String key = "mapjsonKey";
        Gson gson = new Gson();

//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "宏昼");
//        map.put("age", 23);
//        map.put("sal", 888.99);
//        map.put("birthday", new Date());
//        redisTemplate.opsForValue().set(key, gson.toJson(map));

        String jsonKey = redisTemplate.opsForValue().get(key);
        Map<String, Object> getPerson = gson.fromJson(jsonKey, Map.class);
        Double age = (Double) getPerson.get("age");
        age = age + 3;
        getPerson.put("age", age);

        double sal = (double) getPerson.get("sal");
        sal = sal + 2.2;
        getPerson.put("sal", sal);

//        Date birthday = (Date) getPerson.get("birthday");
        System.out.println(getPerson.get("birthday"));

        System.out.println(getPerson);

        getPerson.put("birthday", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        getPerson.put("age", age);
        getPerson.put("sal", sal);
        redisTemplate.opsForValue().set(key, gson.toJson(getPerson));

        String map2 = redisTemplate.opsForValue().get(key);
        HashMap hashMap = gson.fromJson(map2, HashMap.class);
        System.out.println(hashMap);
    }

    @Test
    public void testGsonGetValue() throws ParseException {
        String key = "mapjsonKey";
        Gson gson = new Gson();
        String map = redisTemplate.opsForValue().get(key);
        HashMap hashMap = gson.fromJson(map, HashMap.class);
        String name = (String) hashMap.get("name");
        System.out.println(name);

        Double sal = (Double) hashMap.get("sal");
        System.out.println(sal);

        Double age = (Double) hashMap.get("age");
        System.out.println(age);

        String birthday = (String) hashMap.get("birthday");
        System.out.println(birthday);

        System.out.println(hashMap);

        Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(birthday);
        System.out.println(parse);
    }

    @Test
    public void testDateGson() {
        Gson gson = new Gson();
        String key = "jsonKey";

        String map = redisTemplate.opsForValue().get(key);
        Person person = gson.fromJson(map, Person.class);

        System.out.println(person);
        Date birthday = person.getBirthday();
        System.out.println(birthday);
    }

}

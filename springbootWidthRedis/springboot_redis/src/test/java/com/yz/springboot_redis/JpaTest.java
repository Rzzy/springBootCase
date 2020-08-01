package com.yz.springboot_redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yz.springboot_redis.domain.User;
import com.yz.springboot_redis.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootRedisApplication.class)
public class JpaTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test() throws JsonProcessingException {
        // 从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("user.findAll").get();

        // 如果redis中没有数据的化
        if(null == userListData) {
            // 查询数据库获得数据
            List<User> users = userRepository.findAll();
            // 转换成json格式
            ObjectMapper om = new ObjectMapper();

            userListData = om.writeValueAsString(users);

            redisTemplate.boundValueOps("user.findAll").set(userListData);

            System.out.println("从数据库获取数据");
        } else {
            System.out.println("从缓存中获取数据");
        }


    }
}

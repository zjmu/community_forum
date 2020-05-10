package com.jmu.sesrver;

import com.jmu.server.ServerApplication;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.entity.Manager;
import com.jmu.server.module.blackList.mapper.BlackListExtMapper;
import com.jmu.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@Slf4j
class SesrverApplicationTests {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BlackListExtMapper blackListExtMapper;
    @Test
    void contextLoads() {
        System.out.println(blackListExtMapper.deleteFromBlackList(80));
    }

}

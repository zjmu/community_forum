package com.jmu.client.module.reason.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReasonServiceImplTest {

    @Value("${disable.score}")
    private String scoreValue;
    @Test
    void selectReason() {
        System.out.println((byte) 0 - Integer.valueOf(scoreValue).byteValue());
    }
}
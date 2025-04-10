package com.example.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pharmafive.controller.RegistrationController;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class HelloWorldApplicationTests {

    @Autowired
    private RegistrationController helloWorldController;

    @Test
    void contextLoads() {
        // to ensure that controller is getting created inside the application context
        assertNotNull(helloWorldController);
    }

}

package com.example.kkaddak.api;

import com.example.kkaddak.core.CoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes ={CoreApplication.class, ModuleApiApplication.class})
@ActiveProfiles("apitest")
class ModuleApiApplicationTests {

    @Test
    void contextLoads() {
    }

}

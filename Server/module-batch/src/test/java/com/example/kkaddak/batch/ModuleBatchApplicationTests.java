package com.example.kkaddak.batch;

import com.example.kkaddak.batch.config.KkaddakDataSourceConfig;
import com.example.kkaddak.core.CoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {CoreApplication.class, KkaddakDataSourceConfig.class})
@ActiveProfiles("batchtest")
class ModuleBatchApplicationTests {

    @Test
    void contextLoads() {
    }

}

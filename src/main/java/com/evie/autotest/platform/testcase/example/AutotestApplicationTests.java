package com.evie.autotest.platform.testcase.example;

import com.evie.autotest.HelloController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author ryw@xinyi
 */
@SpringBootTest
class AutotestApplicationTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    void contextLoads() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }

}

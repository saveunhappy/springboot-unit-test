package com.luv2code.springbootunittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

//@SpringBootTest
@WebMvcTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SpringbootUnitTestApplicationTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void init(WebApplicationContext applicationContext, RestDocumentationContextProvider contextProvider){
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(contextProvider))
                .build();
    }

    @Test
    void getDog() throws Exception {
        String json = "{\"id\": 12,\"name\":\"Miki\"}";
        mockMvc.perform(RestDocumentationRequestBuilders.post("/dog/show/entity")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) //成功响应
                .andExpect(MockMvcResultMatchers.jsonPath("name","Miki").exists()) //结果匹配
                .andDo(MockMvcRestDocumentation.document("dog",
                        requestFields(PayloadDocumentation.fieldWithPath("name").description("名字"),
                                PayloadDocumentation.fieldWithPath("id").description("实体id")),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("name").description("名字"),
                                PayloadDocumentation.fieldWithPath("id").description("实体id")
                        ))); //输出文档
    }
}

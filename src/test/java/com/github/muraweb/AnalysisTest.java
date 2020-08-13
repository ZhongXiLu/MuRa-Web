package com.github.muraweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(AnalysisController.class)   // Mock server
class AnalysisTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalysisRepository analysisRepository;

    @Test
    public void getAnalysisTest() throws Exception {
        RequestBuilder getAnalysisRequest = MockMvcRequestBuilders.get("/analysis");
        MvcResult result = mockMvc.perform(getAnalysisRequest).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void postAnalysisTest() throws Exception {
        RequestBuilder getAnalysisRequest = MockMvcRequestBuilders
                .post("/analysis")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("gitRepo","https://github.com/OpenFeign/feign")
                .param("singleModule", "false")
                .param("module", "core")
                .param("CK", "true")
                .param("CC", "true")
                .param("USG", "true")
                .param("H", "true")
                .param("LC", "true")
                .param("IMP", "false");
        MvcResult result = mockMvc.perform(getAnalysisRequest).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

}

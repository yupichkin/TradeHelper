package com.example.demo.controllers;

import com.example.demo.services.MergedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MergedService mergedService;

    @Test
    public void testGettingRedirect() throws Exception {
        String mockedString = "mockedString";
        String expectedUrl = "exampleOfGifUrl.com";
        when(mergedService.getRedirectUrlByCurrencyName(mockedString)).thenReturn(expectedUrl);

        this.mockMvc.perform(get("/getSituation/mockedString"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(expectedUrl));
    }

}
package com.example.demo.services;

import com.example.demo.feignClients.FeignGifClient;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
@ComponentScan("com.example.demo")
public class GifServiceTest {

    @MockBean
    private FeignGifClient feignGifClient;

    @Autowired
    private GifService gifService;

    @Test
    public void testGifUrlGetting() {
        String mockedUrl = "google.com";
        Map<String, String> mapWithUrl = new LinkedHashMap<>();
        mapWithUrl.put("url", mockedUrl);
        Map<String, Map> mapWithData = new LinkedHashMap<>();
        mapWithData.put("data", mapWithUrl);

        ResponseEntity<Map> mockedEntity = new ResponseEntity<>(mapWithData, HttpStatus.OK);
        Mockito.when(feignGifClient.getGifByWord("I7jUVqIfi3JqqCIb9kiOMpaVoeAnvtFI", "testTag"))
                .thenReturn(mockedEntity);
        String gotUrl = gifService.getGifUrlByTag("testTag");
        Assertions.assertEquals(mockedUrl, gotUrl);
    }
}

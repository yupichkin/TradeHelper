package com.example.demo.services;

import com.example.demo.feignClients.FeignGifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GifService{

    @Value("${gif.api.key}")
    private String api_key;

    @Autowired
    private FeignGifClient feignGifClient;

    public String getGifUrlByTag(String tag) {
        ResponseEntity<Map> response = feignGifClient.getGifByWord(api_key, tag);
        try {
            Map map = (Map) response.getBody().get("data"); //getting info map about gif
            String gifUrl = (String) map.get("url"); //getting url to gif
            return gifUrl;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return "";
        }
    }
}

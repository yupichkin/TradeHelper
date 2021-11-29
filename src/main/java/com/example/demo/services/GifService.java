package com.example.demo.services;

import com.example.demo.feignClients.FeignGifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class GifService{

    @Value("${gif.api.key}")
    private String api_key;

    @Autowired
    private FeignGifClient feignGifClient;

    public String getGifUrlByTag(String tag) {
        try {
            ResponseEntity<Map> response = feignGifClient.getRandomGifByTag(api_key, tag);
            Map map = (Map) response.getBody().get("data"); //getting info map about gif
            return (String) map.get("url"); //getting url to gif
        } catch (ClassCastException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Gif service response problem"); //problem due response unboxing
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Gif service unavailable now"); //any other problem
        }
    }
}

package com.example.demo.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value="feignGif", url="${gif.api.baseurl}")
public interface FeignGifClient {
    @GetMapping
    public ResponseEntity<Map> getGifByWord(@RequestParam("api_key") String api_key, @RequestParam("tag") String tag);
}

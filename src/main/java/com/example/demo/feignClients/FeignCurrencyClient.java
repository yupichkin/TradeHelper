package com.example.demo.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value="feignCurrency", url="${openexchangerates.api.baseurl}")
public interface FeignCurrencyClient {
    @GetMapping("latest.json")
    public ResponseEntity<Map> getCurrencyInfoForToday(@RequestParam("app_id") String app_id, @RequestParam("base") String baseCurrency,
                                                       @RequestParam("symbols") String anotherCurrency);
    @GetMapping("historical/{date}.json")
    public ResponseEntity<Map> getCurrencyInfoForYesterday(@PathVariable String date, @RequestParam("app_id") String appId, @RequestParam("base") String baseCurrency,
                                                           @RequestParam("symbols") String anotherCurrency);
    @GetMapping("currencies.json")
    public ResponseEntity<Map> getAvailableCurrencies();
}


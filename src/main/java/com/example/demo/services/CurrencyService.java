package com.example.demo.services;

import com.example.demo.feignClients.FeignCurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

@Service
public class CurrencyService {
    @Value("${openexchangerates.api.key}")
    private String api_key;

    @Value("${openexchangerates.api.basecurrency}")
    private String baseCurrency;

    @Autowired
    private FeignCurrencyClient feignCurrencyClient;

    public Double getCurrencyForToday(String currency) {
        String currencyInUpperCase = currency.toUpperCase(Locale.ROOT);
        ResponseEntity<Map> response = feignCurrencyClient.getCurrencyInfoForToday(
                api_key, baseCurrency, currencyInUpperCase);
        return unboxResponseAndGetCurrency(response, currencyInUpperCase);
    }

    public Double getCurrencyForYesterday(String currency) {
        String currencyInUpperCase = currency.toUpperCase(Locale.ROOT);
        String yesterdayDate = getYesterdayDate();
        ResponseEntity<Map> response = feignCurrencyClient.getCurrencyInfoForYesterday(yesterdayDate,
                api_key, baseCurrency, currencyInUpperCase);
        return unboxResponseAndGetCurrency(response, currencyInUpperCase);
    }

    public boolean isCurrencyAvailableOnApi(String currency) {
        ResponseEntity<Map> availableCurrencies = feignCurrencyClient.getAvailableCurrencies();
        return availableCurrencies.getBody().get(currency.toUpperCase(Locale.ROOT)) != null;
    }

    private Double unboxResponseAndGetCurrency(ResponseEntity<Map> response, String currency) {
        Map body = response.getBody();
        Map rates = (Map) body.get("rates"); //TODO: rates into application properties
        //hardcode segment begins
        Object object = rates.get(currency);
        String className = object.getClass().getName(); //because sometime (usd/usd gives 1 as Integer) we can get Integer,
                                                        //and we can't simply cast it in to Double
        if(className.equals("java.lang.Integer")) {
            return (double) (Integer) rates.get(currency);
        }
        return (double) rates.get(currency);

    }

    private String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

}

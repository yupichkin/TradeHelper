package com.example.demo.services;

import com.example.demo.feignClients.FeignCurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Value("${openexchangerates.api.response.currencyValueKey}")
    private String keyForCurrencyValue;

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
        ResponseEntity<Map> availableCurrenciesAsResponse = feignCurrencyClient.getAvailableCurrencies();
        Map availableCurrencies = availableCurrenciesAsResponse.getBody();
        if(availableCurrencies != null) {
            return availableCurrencies.get(currency.toUpperCase(Locale.ROOT)) != null;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Currency service unavailable now"); //any other problem of currency api
        }
    }

    private Double unboxResponseAndGetCurrency(ResponseEntity<Map> response, String currency) {
        Object object = null;
        try {
            Map body = response.getBody();
            Map rates = (Map) body.get(keyForCurrencyValue);
            object = rates.get(currency);
        }catch (ClassCastException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Currency service response problem"); //problem due response unboxing
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Currency service unavailable now"); //any other problem of api
        }
        String className = object.getClass().getName(); //because there a case (usd/usd gives 1 as Integer) we can get Integer,
        //and we can't simply cast it in to Double
        if (className.equals(Integer.class.getName())) {
            return (double) (Integer) object;
        }
        return (double) object;
    }

    private String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

}

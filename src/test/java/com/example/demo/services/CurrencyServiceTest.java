package com.example.demo.services;

import com.example.demo.feignClients.FeignCurrencyClient;
import com.example.demo.feignClients.FeignGifClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
@ComponentScan("com.example.demo")
public class CurrencyServiceTest {

    @MockBean
    private FeignCurrencyClient feignCurrencyClient;

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void testGetCurrencyForTodayAndLowerCaseCurrencyCode() {
        Double mockedCurrency = 1.0;
        String testCurrencyCodeUpper = "RUB";
        String testCurrencyCodeLower = "rub";

        Map<String, Object> mapWithCurrency = new LinkedHashMap<>();
        mapWithCurrency.put(testCurrencyCodeUpper, mockedCurrency);
        Map<String, Map> mapWithRates = new LinkedHashMap<>();
        mapWithRates.put("rates", mapWithCurrency);

        ResponseEntity<Map> mockedEntity = new ResponseEntity<>(mapWithRates, HttpStatus.OK);
        Mockito.when(feignCurrencyClient.getCurrencyInfoForToday("2e2d2948695846be9749b3da94aa6261", "USD", testCurrencyCodeUpper))
                .thenReturn(mockedEntity);
        Double gotCurrency = currencyService.getCurrencyForToday(testCurrencyCodeLower);
        Assertions.assertEquals(mockedCurrency, gotCurrency);
    }

    @Test
    public void testGetCurrencyForYesterday() {
        Double mockedCurrency = 1.0;
        String testCurrencyCodeUpper = "RUB";
        String testCurrencyCodeLower = "rub";

        Map<String, Object> mapWithCurrency = new LinkedHashMap<>();
        mapWithCurrency.put(testCurrencyCodeUpper, mockedCurrency);
        Map<String, Map> mapWithRates = new LinkedHashMap<>();
        mapWithRates.put("rates", mapWithCurrency);
        String date = getDate();
        ResponseEntity<Map> mockedEntity = new ResponseEntity<>(mapWithRates, HttpStatus.OK);
        Mockito.when(feignCurrencyClient.getCurrencyInfoForYesterday(date, "2e2d2948695846be9749b3da94aa6261", "USD", testCurrencyCodeUpper))
                .thenReturn(mockedEntity);
        Double gotCurrency = currencyService.getCurrencyForYesterday(testCurrencyCodeLower);
        Assertions.assertEquals(mockedCurrency, gotCurrency);
    }

    @Test
    public void testIsCurrencyAvailableOnApi() {
        String mockedCurrencyCode = "RUB";
        String checkedCurrency = "rub";
        String nonExistentCurrency = "virginGalacticShares";
        Map<String, String> mapWithRUB = new LinkedHashMap<>();
        mapWithRUB.put("RUB", "somethingIsNotNull");
        ResponseEntity<Map> mockedEntity = new ResponseEntity<>(mapWithRUB, HttpStatus.OK);
        Mockito.when(feignCurrencyClient.getAvailableCurrencies())
                .thenReturn(mockedEntity);
        boolean isRUBAvailable = currencyService.isCurrencyAvailableOnApi(checkedCurrency);
        boolean isNonExistentCurrencyAvailable = currencyService.isCurrencyAvailableOnApi(nonExistentCurrency);
        Assertions.assertTrue(isRUBAvailable);
        Assertions.assertFalse(isNonExistentCurrencyAvailable);
    }



    private String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }
}

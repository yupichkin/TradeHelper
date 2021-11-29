package com.example.demo.services;

import com.example.demo.MergedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@SpringBootTest
@ComponentScan("com.example.demo")
public class MergedServiceTest {
    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private GifService gifService;

    @Autowired
    private MergedService mergedService;

    @Value("${gif.tags.more}")
    private String moreTag;

    @Value("${gif.tags.less}")
    private String lessTag;

    @Value("${gif.tags.equal}")
    private String equalTag;

    @Test
    void testCorrectRedirectionWhenCurrencyGreater() {
        String mockedCurrency = "mockedCurrency";
        Double todayCurrency = 1.1;
        Double yesterdayCurrency = 1.0;
        String expectedResult = "more";

        Mockito.when(currencyService.getCurrencyForToday(mockedCurrency)).thenReturn(todayCurrency);
        Mockito.when(currencyService.getCurrencyForYesterday(mockedCurrency)).thenReturn(yesterdayCurrency);

        Mockito.when(gifService.getGifUrlByTag(moreTag)).thenReturn(expectedResult);
        Mockito.when(gifService.getGifUrlByTag(lessTag)).thenReturn("less");
        Mockito.when(gifService.getGifUrlByTag(equalTag)).thenReturn("equal");

        Mockito.when( currencyService.isCurrencyAvailableOnApi(mockedCurrency)).thenReturn(true);

        String result = mergedService.getRedirectUrlByCurrencyName(mockedCurrency);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testCorrectRedirectionWhenCurrencyLess() {
        String mockedCurrency = "mockedCurrency";
        Double todayCurrency = 0.9;
        Double yesterdayCurrency = 1.0;
        String expectedResult = "less";

        Mockito.when(currencyService.getCurrencyForToday(mockedCurrency)).thenReturn(todayCurrency);
        Mockito.when(currencyService.getCurrencyForYesterday(mockedCurrency)).thenReturn(yesterdayCurrency);

        Mockito.when(gifService.getGifUrlByTag(moreTag)).thenReturn("more");
        Mockito.when(gifService.getGifUrlByTag(lessTag)).thenReturn(expectedResult);
        Mockito.when(gifService.getGifUrlByTag(equalTag)).thenReturn("equal");

        Mockito.when( currencyService.isCurrencyAvailableOnApi(mockedCurrency)).thenReturn(true);

        String result = mergedService.getRedirectUrlByCurrencyName(mockedCurrency);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testCorrectRedirectionWhenCurrencyEqual() {
        String mockedCurrency = "mockedCurrency";
        Double todayCurrency = 1.0;
        Double yesterdayCurrency = 1.0;
        String expectedResult = "equal";

        Mockito.when(currencyService.getCurrencyForToday(mockedCurrency)).thenReturn(todayCurrency);
        Mockito.when(currencyService.getCurrencyForYesterday(mockedCurrency)).thenReturn(yesterdayCurrency);

        Mockito.when(gifService.getGifUrlByTag(moreTag)).thenReturn("more");
        Mockito.when(gifService.getGifUrlByTag(lessTag)).thenReturn("less");
        Mockito.when(gifService.getGifUrlByTag(equalTag)).thenReturn(expectedResult);

        Mockito.when( currencyService.isCurrencyAvailableOnApi(mockedCurrency)).thenReturn(true);

        String result = mergedService.getRedirectUrlByCurrencyName(mockedCurrency);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testCorrectRedirectionWhenCurrencyUnavailable() {
        String mockedCurrency = "mockedCurrency";
        Double todayCurrency = 1.0;
        Double yesterdayCurrency = 1.0;
        String expectedResult = "equal";

        Mockito.when(currencyService.isCurrencyAvailableOnApi(mockedCurrency)).thenReturn(false);

        Mockito.when(currencyService.getCurrencyForToday(mockedCurrency)).thenReturn(todayCurrency);
        Mockito.when(currencyService.getCurrencyForYesterday(mockedCurrency)).thenReturn(yesterdayCurrency);

        Mockito.when(gifService.getGifUrlByTag(moreTag)).thenReturn("more");
        Mockito.when(gifService.getGifUrlByTag(lessTag)).thenReturn("less");
        Mockito.when(gifService.getGifUrlByTag(equalTag)).thenReturn(expectedResult);

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            String result = mergedService.getRedirectUrlByCurrencyName(mockedCurrency);
        });

        String expectedMessage = "The currency code is incorrect or this currency doesn't exist";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}

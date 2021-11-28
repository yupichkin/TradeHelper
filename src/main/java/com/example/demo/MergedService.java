package com.example.demo;

import com.example.demo.services.CurrencyService;
import com.example.demo.services.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MergedService {
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GifService gifService;
    //TODO: need to return exception with detailed message

    @Value("${gif.tags.more}")
    private String moreTag;

    @Value("${gif.tags.less}")
    private String lessTag;

    @Value("${gif.tags.equal}")
    private String equalTag;

    String getRedirectUrlByCurrencyName(String currencyAsString) {
        if (!currencyService.isCurrencyAvailableOnApi(currencyAsString)) {
           return "";
        }
        Double todayCurrency = currencyService.getCurrencyForToday(currencyAsString);
        Double yesterdayCurrency = currencyService.getCurrencyForYesterday(currencyAsString);

        if (todayCurrency > yesterdayCurrency) {
            return gifService.getGifUrlByTag(moreTag);
        } else if (todayCurrency < yesterdayCurrency) {
            return gifService.getGifUrlByTag(lessTag);
        } else {
            //for scenario when world exchanges do not work (usually on weekends)
            // and the exchange rate does not change over the past day
            return gifService.getGifUrlByTag(equalTag);
        }
    }


    //cannot create currency by his code, so remains iteration(
}

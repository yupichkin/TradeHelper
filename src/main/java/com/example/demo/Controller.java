package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class Controller {
    @Autowired
    MergedService mergedService;

    @GetMapping("/getSituation/{currencyCode}")
    public RedirectView getSituationAboutCurrency(@PathVariable String currencyCode) {
        String url = mergedService.getRedirectUrlByCurrencyName(currencyCode);
        return new RedirectView(url);
    }
}
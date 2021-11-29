package com.example.demo;

import com.example.demo.services.CurrencyService;
import com.example.demo.services.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
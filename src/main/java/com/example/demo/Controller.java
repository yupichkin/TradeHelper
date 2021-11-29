package com.example.demo;

import com.example.demo.services.CurrencyService;
import com.example.demo.services.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class Controller {
    @Autowired
    GifService gifService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    MergedService mergedService;

    @GetMapping("/getSituation/{currencyCode}")
    public RedirectView getSituationAboutCurrency(@PathVariable String currencyCode) {
        String url = mergedService.getRedirectUrlByCurrencyName(currencyCode);
        return new RedirectView(url);
    }

    @GetMapping("/getGif/{tag}")
    public RedirectView getRedirectToGif(@PathVariable String tag) {
        String gifUrl = gifService.getGifUrlByTag(tag);
        //ResponseEntity<Map> = new ResponseEntity<Map>()
        return new RedirectView(gifUrl);
    }

    @GetMapping("/getCurrency/{currency}")
    public String getCurrencyForToday(@PathVariable String currency) {
        Double currencyForToday = currencyService.getCurrencyForToday(currency);
        //ResponseEntity<Map> = new ResponseEntity<Map>()
        return "idk";
    }

    @RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity() {
        HttpHeaders headers = new HttpHeaders();
        byte[] picture = new byte[0];
        try {
            File resource = new ClassPathResource(
                    "valorant.png").getFile();
            picture = Files.readAllBytes(resource.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //InputStream in = context.getResourceAsStream("valorant.png");

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(picture);
    }
}
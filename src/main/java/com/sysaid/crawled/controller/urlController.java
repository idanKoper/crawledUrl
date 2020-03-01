package com.sysaid.crawled.controller;

import com.sysaid.crawled.model.urlEntity;
import com.sysaid.crawled.service.urlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class urlController {

    @Autowired
    private urlService service;

    private HashSet<String> links;

    private urlEntity urlEntity;

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public ResponseEntity<?> processPage(@RequestParam String url, @RequestParam int number) {
        if (service.getByUrl(url) == null) {
            links = new HashSet<>();
            try {
                //Fetch the HTML code
                Document document = Jsoup.connect(url).get();
                //Parse the HTML to inner links
                Elements linksOnPage = document.select("a[href]"); // a with href

                //extracted URL to Set
                for (Element page : linksOnPage) {
                    String link = page.attr("abs:href");
                    if (StringUtils.isNotEmpty(link))
                        links.add(link);
                }
                List<String> listEntity = new ArrayList<>();
                listEntity.addAll(links);
                urlEntity = new urlEntity(url, listEntity);
                service.save(urlEntity);

            } catch (IOException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            } catch (NoSuchElementException e) {
                ResponseEntity<Object> objectResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return objectResponseEntity;
            }
        }
        else{
            ResponseEntity<urlEntity> urlEntityResponseEntity = processPage(url);
            return new ResponseEntity<>(urlEntityResponseEntity, HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(urlEntity, HttpStatus.OK);
    }

    @GetMapping("/GetAllInnerLinks")
    public List<urlEntity> getAllUrls() {
        return service.listAll();
    }

    @GetMapping("/GetByUrl")
    public ResponseEntity<urlEntity> processPage(@RequestParam String url) {
        try {
            urlEntity = service.getByUrl(url);
            if (urlEntity == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(urlEntity, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
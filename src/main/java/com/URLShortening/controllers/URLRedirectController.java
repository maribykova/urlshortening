package com.URLShortening.controllers;

import com.URLShortening.entities.URL;
import com.URLShortening.entities.URLNotFoundException;
import com.URLShortening.service.URLShorteningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/r")
public class URLRedirectController {
    private final Logger log = LoggerFactory.getLogger(URLRedirectController.class);

    private final URLShorteningService service;

    public URLRedirectController(URLShorteningService service) {
        this.service = service;
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        Optional<URL> optional = service.findByShortUrl(shortUrl);
        if (optional.isPresent()) {
            log.info("Redirecting to Short path is" + shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(optional.get().getUrl())).build();
        } else {
            throw new URLNotFoundException(shortUrl);
        }
    }

}

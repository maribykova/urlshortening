package com.URLShortening.controllers;

import com.URLShortening.entities.URL;
import com.URLShortening.entities.URLNotFoundException;
import com.URLShortening.service.URLShorteningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Controller
@RequestMapping("/urls")
public class URLsController {
    private final Logger log = LoggerFactory.getLogger(URLsController.class);

    private final URLShorteningService service;

    public URLsController(URLShorteningService service) {
        this.service = service;
    }

    @GetMapping
    public String showUrls(Model model) {
        model.addAttribute("url", new URL());
        model.addAttribute("urls", service.getAllUrls());
        return "urls";
    }

    @GetMapping("{id}")
    public String showUrl(@PathVariable Integer id, Model model) {
        Optional<URL> optional = service.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("url", optional.get());
        } else {
            throw new URLNotFoundException(id);
        }
        return "urls";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addUrl(/*@Valid*/ URL url, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Errors: " + errors);
            return "urls";
        }

        log.info("Saving url: " + url.toString());
        service.generateShortURLAndSave(url);
        return "redirect:/urls";
    }


}

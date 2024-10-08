package com.URLShortening.service;

import com.URLShortening.entities.URL;
import com.URLShortening.repositories.URLCacheRepository;
import com.URLShortening.repositories.URLRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class URLShorteningService {

    private final URLRepository dao;

    private final URLCacheRepository cache;

    public void initializeDatabase() {
        if (dao.count() == 0) {
            dao.saveAll(Arrays.asList(
                    new URL(1, "https://www.google.com", LocalDateTime.now()
                            ,"XXYYYZ"),
                    new URL(2, "https://www.reddit.com", LocalDateTime.now(),"AAAB55"),
                    new URL(5, "https://www.yahoo.com", LocalDateTime.now(),"WWWDFD")
            ));
        }
    }
    @Autowired
    public URLShorteningService(URLRepository dao, URLCacheRepository cache) {
        this.dao = dao;
        this.cache = cache;
    }

    public List<URL> getAllUrls(){
        return dao.findAll();
    }


    public Optional<URL> findByShortUrl(String shortUrl) {
        Optional<URL> url = cache.findById(shortUrl);
        if ( url.isPresent() ) {
            return url;
        }

        return dao.findURLByShortUrl(shortUrl);
    }


    public Optional<URL> findById(int id) {
        return dao.findById(id);
    }

    public URL saveURL(URL url) {
        return dao.save(url);
    }

    public URL generateShortURLAndSave(URL url){
        if (url.getUrl() != null && ! url.getUrl().startsWith("http")){
            url.getUrl().replaceFirst("https://","");
            url.getUrl().replaceFirst("http://","");
        }
        if (url.getShortUrl() == null){
            url.setShortUrl(randomCode());
        }
        url.setCreatedDate(LocalDateTime.now());
        return dao.save(url);
    }

    String randomCode() {
        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64 - 31)) ^ lo;
        hi = (hi >> (64 - 31)) ^ hi;
        String s = String.format("%010d", Math.abs(hi) + Math.abs(lo));
        return s.substring(s.length() - 10);
    }
}

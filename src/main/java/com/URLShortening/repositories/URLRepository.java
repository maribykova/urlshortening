package com.URLShortening.repositories;

import com.URLShortening.entities.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface URLRepository extends CrudRepository<URL,Integer> {
    URL findURLById(Integer id);
    List<URL> findAll();
    URL findURLByUrl(String url);
    Optional<URL> findURLByShortUrl(String shortUrl);
    //List<Product> findTop10ByNameContainsOrderByPrice(String regex);
}

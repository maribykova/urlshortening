package com.URLShortening.repositories;

import com.URLShortening.entities.URL;
import org.springframework.data.repository.CrudRepository;

public interface  URLCacheRepository extends CrudRepository<URL, String> {

}



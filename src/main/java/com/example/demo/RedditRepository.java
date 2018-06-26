package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface RedditRepository extends CrudRepository<Reddit,Long>{
    Iterable <Reddit> findAllByTitleContainingIgnoreCase(String search);
    Iterable <Reddit> findAllByOrderByDateDesc();
}
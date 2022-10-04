package com.aronalvarenga.news.controller;

import com.aronalvarenga.news.entity.News;
import com.aronalvarenga.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

// http://localhost:8080/news
@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsRepository newsRepository;

    public NewsController(@Autowired NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(newsRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody News news) {
        news.setDateTime(new Date());
        return new ResponseEntity(newsRepository.save(news), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody News news) {
        Optional<News> newsToEdit = newsRepository.findById(news.getId());

        if(newsToEdit.isPresent()) {
            news.setDateTime(new Date());
            return new ResponseEntity(newsRepository.save(news), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        Optional<News> newsToEdit = newsRepository.findById(id);

        if(newsToEdit.isPresent()) {
            newsRepository.deleteById(id);
            return new ResponseEntity("News successfully deleted!", HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}

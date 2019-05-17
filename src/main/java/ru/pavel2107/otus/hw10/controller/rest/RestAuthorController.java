package ru.pavel2107.otus.hw10.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.otus.hw10.domain.Author;
import ru.pavel2107.otus.hw10.service.AuthorService;

import java.util.List;

@RestController
@CrossOrigin
public class RestAuthorController {

    private AuthorService service;

    @Autowired
    public RestAuthorController(AuthorService service){
        this.service = service;
    }

    @GetMapping( value = "/rest/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Author> listAuthors(){
        List<Author> list = service.findAll();
        return list;
    }

    @DeleteMapping( value = "/rest/authors")
    @ResponseStatus( value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam( value = "id") Long id){
        service.delete( id);
    }

    @GetMapping( value = "/rest/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Author get( @PathVariable( value = "id") Long id){
        Author g =  service.find( id);
        return g;
    }

    @PostMapping( value = "/rest/authors", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void save(@RequestBody Author author){
      service.save( author);
    }
}

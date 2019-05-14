package ru.pavel2107.otus.hw10.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.otus.hw10.domain.Book;
import ru.pavel2107.otus.hw10.service.BookService;

import java.util.List;

@RestController
@CrossOrigin
public class RestBookController {

    private BookService service;

    @Autowired
    public RestBookController(BookService service){
        this.service = service;
    }

    @GetMapping( value = "/rest/books/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> listBooks(){
        List<Book> list = service.findAll();
        return list;
    }

    @DeleteMapping( value = "/rest/books/delete")
    @ResponseStatus( value = HttpStatus.NO_CONTENT)
    public void delete( @RequestParam( value = "id") Long id){
        service.delete( id);
    }


    @GetMapping( value = "/rest/books/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book get( @RequestParam( value = "id") Long id){
        Book g =  service.find( id);
        return g;
    }

    @PostMapping( value = "/rest/books/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void save(@RequestBody Book book){
        book = service.save( book);
    }


}

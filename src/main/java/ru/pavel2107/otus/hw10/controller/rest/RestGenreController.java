package ru.pavel2107.otus.hw10.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.otus.hw10.domain.Genre;
import ru.pavel2107.otus.hw10.service.GenreService;

import java.util.List;

@RestController
@CrossOrigin
public class RestGenreController {

    private GenreService service;

    @Autowired
    public RestGenreController(GenreService service){
        this.service = service;
    }

    @GetMapping( value = "/rest/genres/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Genre> listGenres(){
        List<Genre> list = service.findAll();
        return list;
    }

    @DeleteMapping( value = "/rest/genres/delete")
    @ResponseStatus( HttpStatus.NO_CONTENT)
    public void delete( @RequestParam( value = "id") Long id){
        service.delete( id);
    }


    @GetMapping( value = "/rest/genres/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Genre get( @RequestParam( value = "id") Long id){
        Genre g =  service.find( id);

        return g;
    }

    @PostMapping( value = "/rest/genres/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void save(@RequestBody Genre  genre){
        genre =service.save( genre);
    }
}

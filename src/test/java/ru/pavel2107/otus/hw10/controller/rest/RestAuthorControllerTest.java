package ru.pavel2107.otus.hw10.controller.rest;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.pavel2107.otus.hw10.repository.datajpa.AuthorRepository;
import ru.pavel2107.otus.hw10.service.AuthorService;
import ru.pavel2107.otus.hw10.service.AuthorServiceImpl;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@DisplayName( "Контроллер авторов")
@SpringBootTest
@AutoConfigureMockMvc
public class RestAuthorControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    public void listAuthors() throws Exception{
        MvcResult result= mockMvc
                .perform(
                        get( "/rest/authors/list")
                        .contentType( MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo( print())
                .andExpect( status().isOk())
                .andReturn();
        assertNotNull( result.getResponse().getContentAsString());
    }
}
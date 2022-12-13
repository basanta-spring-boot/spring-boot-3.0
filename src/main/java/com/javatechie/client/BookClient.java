package com.javatechie.client;

import com.javatechie.dto.Book;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/books", accept = "application/json", contentType = "application/json")
public interface BookClient {

    @GetExchange
    List<Book> getAllBooks();
}

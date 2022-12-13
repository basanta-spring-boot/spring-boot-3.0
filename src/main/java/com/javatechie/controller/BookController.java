package com.javatechie.controller;

import com.javatechie.client.BookClient;
import com.javatechie.dto.Book;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    List<Book> books = new ArrayList<>();

    @Autowired(required = true)
    private BookClient bookClient;


    @Autowired
    private ObservationRegistry registry;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        books.add(book);
        List<Book> allBooks = bookClient.getAllBooks();
        log.info("Number of books available : {}", allBooks);
        return getResultsAndSendMetrics(book, "addBook");
    }

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) {
        return getResultsAndSendMetrics(books.stream()
                .filter(book -> book.getId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Book not found")), "getBookById");
    }


    private Book getResultsAndSendMetrics(Book book, String apiName) {
        return Observation.createNotStarted(apiName, registry)
                .observe(() -> book);
    }


}

package com.lashan.crudtesting.controller;

import com.lashan.crudtesting.dto.BookDto;
import com.lashan.crudtesting.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll(){
        log.info("Book Find All calling");
        return ResponseEntity
                .ok()
                .body(bookService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody BookDto book){
        return ResponseEntity
                .ok()
                .body(bookService.save(book));
    }

    @PutMapping("/update")
    public ResponseEntity<BookDto> update(@RequestBody BookDto book){
        return ResponseEntity
                .ok()
                .body(bookService.update(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id") Long id){
        return ResponseEntity
                .ok()
                .body(bookService.findById(id));
    }

    @GetMapping("isactive/{id}")
    public ResponseEntity<Boolean> findBookIsActive(@PathVariable("id") Long id){
        return ResponseEntity
                .ok()
                .body(bookService.bookIsActive(id));
    }

}

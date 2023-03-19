package com.lashan.crudtesting.service.impl;

import com.lashan.crudtesting.dto.BookDto;
import com.lashan.crudtesting.entity.Book;
import com.lashan.crudtesting.exception.DataNotFoundException;
import com.lashan.crudtesting.repository.BookRepository;
import com.lashan.crudtesting.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    /**
     * This method used to save new book
     *
     * @param book BookDto
     * @return saved book id
     */
    @Override
    public Long save(BookDto book) {
        Book saveBook = modelMapper.map(book, Book.class);
        saveBook = bookRepository.save(saveBook);
        return saveBook.getId();
    }

    /**
     * This Method used to Update Book
     *
     * @param book BookDto
     * @return Updated Book Detail
     */
    @Override
    public BookDto update(BookDto book) {
        Book boo = bookRepository
                .findById(book.getId())
                .orElseThrow(() -> new DataNotFoundException(book.getId() + " Not Found"));
        boo.setName(book.getName());
        boo.setIsActive(book.getIsActive());
        boo = bookRepository.save(boo);
        return modelMapper
                .map(boo, BookDto.class);
    }

    /**
     * This Method used to delete a book
     *
     * @param id deleting the book belong to this id
     */
    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * This method used to get book data by relevant id
     *
     * @param id requested book id
     * @return BookDto
     */
    @Override
    public BookDto findById(Long id) {
        Book boo = bookRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(id + " Not Found"));
        return modelMapper.map(boo, BookDto.class);
    }

    /**
     * This method used to get all Books
     *
     * @return List of BookDto
     */
    @Override
    public List<BookDto> findAll() {
        List<BookDto> books = bookRepository
                .findAll()
                .stream()
                .map(book -> modelMapper
                        .map(book, BookDto.class))
                .collect(Collectors.toList());
        return books;
    }

    /**
     * this method used to check book is active
     *
     * @param id requested book id
     * @return if is active return true otherwise false
     */
    @Override
    public boolean bookIsActive(Long id) {
        Book boo = bookRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(id + " Not Found"));
        return bookRepository.findBookIsActive(id);
    }
}

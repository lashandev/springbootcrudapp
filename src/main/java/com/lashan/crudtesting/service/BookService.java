package com.lashan.crudtesting.service;

import com.lashan.crudtesting.dto.BookDto;

import java.util.List;

public interface BookService {
    /**
     * This method used to save new book
     * @param book BookDto
     * @return saved book id
     */
    Long save(BookDto book);

    /**
     * This Method used to Update Book
     * @param book BookDto
     * @return Updated Book Detail
     */
    BookDto update(BookDto book);

    /**
     * This Method used to delete a book
     * @param id deleting the book belong to this id
     */
    void delete(Long id);

    /**
     * This method used to get book data by relevant id
     * @param id requested book id
     * @return BookDto
     */
    BookDto findById(Long id);

    /**
     * This method used to get all Books
     * @return List of BookDto
     */
    List<BookDto> findAll();

    /**
     * this method used to check book is active
     * @param id requested book id
     * @return if is active return true otherwise false
     */
    boolean bookIsActive(Long id);
}

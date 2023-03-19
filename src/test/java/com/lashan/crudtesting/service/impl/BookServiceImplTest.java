package com.lashan.crudtesting.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.lashan.crudtesting.dto.BookDto;
import com.lashan.crudtesting.entity.Book;
import com.lashan.crudtesting.exception.DataNotFoundException;
import com.lashan.crudtesting.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDto bookDto;

    private Book book;

    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Book", true);
        bookDto = new BookDto(1L, "Test Book", true);
        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    void save() {
        when(modelMapper.map(any(BookDto.class), any())).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Long savedBookId = bookService.save(bookDto);
        Assertions.assertEquals(1L, savedBookId);
        verify(modelMapper, times(1)).map(any(BookDto.class), any());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void update() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), ArgumentMatchers.<Class<BookDto>>any()))
                .thenReturn(bookDto);
        BookDto updatedBook = bookService.update(bookDto);
        Assertions.assertEquals(bookDto, updatedBook);
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(modelMapper, times(1)).map(any(Book.class), ArgumentMatchers.<Class<BookDto>>any());
    }

    @Test
    void updateShouldThrowDataNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class, () -> bookService.update(bookDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(0)).save(any(Book.class));
        verify(modelMapper, times(0)).map(any(Book.class), ArgumentMatchers.<Class<BookDto>>any());
    }

    @Test
    void delete() {
        doNothing().when(bookRepository).deleteById(anyLong());
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void findById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), ArgumentMatchers.<Class<BookDto>>any()))
                .thenReturn(bookDto);
        BookDto foundBook = bookService.findById(1L);
        Assertions.assertEquals(bookDto, foundBook);
        verify(bookRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(Book.class), ArgumentMatchers.<Class<BookDto>>any());
    }

    @Test
    void findByIdShouldThrowDataNotFoundException() {

    }

    @Test
    void testSaveBook() {
        BookDto bookDto = BookDto.builder().name("Test Book").isActive(true).build();
        Book savedBook = Book.builder().id(1L).name("Test Book").isActive(true).build();
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        Long bookId= bookService.save(bookDto);
        assertEquals(1L, bookId);
    }

    @Test
    void testUpdateBook() {
        BookDto bookDto = BookDto.builder().id(1L).name("Test Book").isActive(true).build();
        Book book = Book.builder().id(1L).name("Old Book Name").isActive(false).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDto updatedBook = bookService.update(bookDto);
        assertEquals("Test Book", updatedBook.getName());
        assertTrue(updatedBook.getIsActive());
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);
        assertDoesNotThrow(() -> bookService.delete(1L));
    }

    @Test
    void testFindById() {
        Book book = Book.builder().id(1L).name("Test Book").isActive(true).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookDto foundBook = bookService.findById(1L);
        assertEquals("Test Book", foundBook.getName());
        assertTrue(foundBook.getIsActive());
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(
                Book.builder().id(1L).name("Book 1").isActive(true).build(),
                Book.builder().id(2L).name("Book 2").isActive(false).build()
        );
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> bookDtos = bookService.findAll();
        assertEquals(2, bookDtos.size());
        assertEquals("Book 1", bookDtos.get(0).getName());
        assertTrue(bookDtos.get(0).getIsActive());
        assertEquals("Book 2", bookDtos.get(1).getName());
        assertFalse(bookDtos.get(1).getIsActive());
    }
}


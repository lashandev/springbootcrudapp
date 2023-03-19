package com.lashan.crudtesting.repository;

import com.lashan.crudtesting.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT isactive FROM book WHERE id=?1",nativeQuery = true)
    boolean findBookIsActive(Long id);
}

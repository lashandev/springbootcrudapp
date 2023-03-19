package com.lashan.crudtesting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 11, unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Book Name Can't be Empty")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "isactive", nullable = false)
    private Boolean isActive;
}

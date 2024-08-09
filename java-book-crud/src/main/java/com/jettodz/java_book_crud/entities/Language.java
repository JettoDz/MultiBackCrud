package com.jettodz.java_book_crud.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_language")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer id;

    @Column(name = "language_code")
    private String code;

    @Column(name = "language_name")
    private String name;

}

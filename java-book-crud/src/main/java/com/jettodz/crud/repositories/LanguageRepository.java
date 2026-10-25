package com.jettodz.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jettodz.crud.entities.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}

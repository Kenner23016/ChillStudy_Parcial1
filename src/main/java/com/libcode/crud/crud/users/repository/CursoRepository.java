package com.libcode.crud.crud.users.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.libcode.crud.crud.users.entities.Curso;
import com.libcode.crud.crud.users.entities.NivelDificultad;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    /* ----- Filtros con paginación ----- */

    Page<Curso> findByCategoriaIgnoreCaseAndDificultad(
            String categoria, NivelDificultad dificultad, Pageable pageable);

    Page<Curso> findByCategoriaIgnoreCase(String categoria, Pageable pageable);

    Page<Curso> findByDificultad(NivelDificultad dificultad, Pageable pageable);

    /* ----- Para poblar el combo de categorías ----- */
    @Query("select distinct c.categoria from Curso c order by c.categoria")
    List<String> findDistinctCategorias();
}


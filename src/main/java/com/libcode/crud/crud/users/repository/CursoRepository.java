package com.libcode.crud.crud.users.repository;

import com.libcode.crud.crud.users.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}

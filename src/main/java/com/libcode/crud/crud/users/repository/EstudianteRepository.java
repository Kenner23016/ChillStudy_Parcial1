package com.libcode.crud.crud.users.repository;

import com.libcode.crud.crud.users.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}

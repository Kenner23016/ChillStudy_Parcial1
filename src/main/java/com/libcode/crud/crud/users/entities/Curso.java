package com.libcode.crud.crud.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;   // ➕ Nueva columna

    @Enumerated(EnumType.STRING) // Guarda el texto BASICO | INTERMEDIO | AVANZADO
    private NivelDificultad dificultad; // ➕ Nueva columna

    /* ---------------- getters & setters ---------------- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public NivelDificultad getDificultad() { return dificultad; }
    public void setDificultad(NivelDificultad dificultad) { this.dificultad = dificultad; }
}

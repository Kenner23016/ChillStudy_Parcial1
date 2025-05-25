package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.Estudiante;
import com.libcode.crud.crud.users.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository repo;

    @GetMapping
    public List<Estudiante> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Estudiante crear(@RequestBody Estudiante e) {
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public Estudiante actualizar(@PathVariable Long id, @RequestBody Estudiante e) {
        e.setId(id);
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

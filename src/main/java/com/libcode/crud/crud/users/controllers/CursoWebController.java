package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.Curso;
import com.libcode.crud.crud.users.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
public class CursoWebController {

    @Autowired
    private CursoRepository cursoRepo;

    // Mostrar formulario para crear nuevo curso
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("curso", new Curso());
        return "form-curso";
    }

    // Guardar curso (crear o editar)
    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute("curso") Curso curso, RedirectAttributes redirect, Model model) {
        boolean esNuevo = (curso.getId() == null);
        cursoRepo.save(curso);

        if (esNuevo) {
            redirect.addFlashAttribute("mensaje", "🎉 Curso registrado exitosamente.");
            return "redirect:/cursos/nuevo";
        }

        redirect.addFlashAttribute("mensaje", "✏️ Curso actualizado correctamente.");
        return "redirect:/cursos/lista";
    }

    // Mostrar lista de cursos con paginación
    @GetMapping("/lista")
    public String mostrarCursos(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 cursos por página
        Page<Curso> cursosPage = cursoRepo.findAll(pageable);

        model.addAttribute("cursos", cursosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cursosPage.getTotalPages());

        return "consulta-cursos";
    }

    // Editar curso
    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de curso inválido: " + id));
        model.addAttribute("curso", curso);
        return "form-curso";
    }

    // Eliminar curso
    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            cursoRepo.deleteById(id);
            redirect.addFlashAttribute("mensaje", "🗑️ Curso eliminado exitosamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "⚠️ No se puede eliminar el curso porque está asignado a un historial.");
        }
        return "redirect:/cursos/lista";
    }
}

package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.HistorialAcademico;
import com.libcode.crud.crud.users.repository.CursoRepository;
import com.libcode.crud.crud.users.repository.EstudianteRepository;
import com.libcode.crud.crud.users.repository.HistorialAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historial")
public class HistorialWebController {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private HistorialAcademicoRepository historialRepo;

    // 👉 Mostrar formulario
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("historial", new HistorialAcademico());
        model.addAttribute("estudiantes", estudianteRepo.findAll());
        model.addAttribute("cursos", cursoRepo.findAll());
        return "registro-historial";
    }

    // ✅ Guardar (nuevo o editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("historial") HistorialAcademico historial, RedirectAttributes redirect) {
        boolean esNuevo = (historial.getId() == null);
        historialRepo.save(historial);

        if (esNuevo) {
            redirect.addFlashAttribute("mensaje", "🎉 Historial registrado correctamente.");
            return "redirect:/historial/nuevo";
        }

        redirect.addFlashAttribute("mensaje", "✏️ Historial actualizado correctamente.");
        return "redirect:/historial/lista";
    }

    // 📋 Mostrar lista con paginación
    @GetMapping("/lista")
    public String mostrarLista(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<HistorialAcademico> pagina = historialRepo.findAll(PageRequest.of(page, 5));
        model.addAttribute("historiales", pagina.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagina.getTotalPages());
        return "consulta-historial";
    }

    // ✏️ Editar
    @GetMapping("/editar/{id}")
    public String editarHistorial(@PathVariable Long id, Model model) {
        HistorialAcademico historial = historialRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("historial", historial);
        model.addAttribute("estudiantes", estudianteRepo.findAll());
        model.addAttribute("cursos", cursoRepo.findAll());
        return "registro-historial";
    }

    // 🗑️ Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarHistorial(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            historialRepo.deleteById(id);
            redirect.addFlashAttribute("mensaje", "🗑️ Registro eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "⚠️ No se puede eliminar el registro.");
        }
        return "redirect:/historial/lista";
    }
}

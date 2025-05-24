package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.Carrera;
import com.libcode.crud.crud.users.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carreras")
public class CarreraWebController {

    @Autowired
    private CarreraRepository carreraRepo;

    // Mostrar formulario nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("carrera", new Carrera());
        return "form-carrera";
    }

    // Guardar carrera (crear o editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("carrera") Carrera carrera, RedirectAttributes redirect) {
        boolean esNuevo = (carrera.getId() == null);
        carreraRepo.save(carrera);

        if (esNuevo) {
            redirect.addFlashAttribute("mensaje", "🎉 Carrera registrada exitosamente.");
            return "redirect:/carreras/nuevo";
        }

        redirect.addFlashAttribute("mensaje", "✏️ Carrera actualizada correctamente.");
        return "redirect:/carreras/lista";
    }

    // Mostrar lista paginada
    @GetMapping("/lista")
    public String mostrarLista(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Carrera> carreras = carreraRepo.findAll(pageable);

        model.addAttribute("carreras", carreras.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", carreras.getTotalPages());
        return "consulta-carreras";
    }

    // Editar carrera
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Carrera carrera = carreraRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("carrera", carrera);
        return "form-carrera";
    }

    // Eliminar carrera
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            carreraRepo.deleteById(id);
            redirect.addFlashAttribute("mensaje", "🗑️ Carrera eliminada exitosamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "⚠️ No se puede eliminar la carrera porque está asignada a un estudiante.");
        }
        return "redirect:/carreras/lista";
    }
}

package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.Interes;
import com.libcode.crud.crud.users.repository.InteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/intereses")
public class InteresWebController {

    @Autowired
    private InteresRepository interesRepo;

    // Mostrar formulario nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("interes", new Interes());
        return "form-interes";
    }

    // Guardar (nuevo o editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("interes") Interes interes, RedirectAttributes redirect) {
        boolean esNuevo = (interes.getId() == null);
        interesRepo.save(interes);

        if (esNuevo) {
            redirect.addFlashAttribute("mensaje", "🎉 Interés registrado correctamente.");
            return "redirect:/intereses/nuevo";
        }

        redirect.addFlashAttribute("mensaje", "✏️ Interés actualizado correctamente.");
        return "redirect:/intereses/lista";
    }

    // Mostrar lista paginada
    @GetMapping("/lista")
    public String mostrarLista(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 5;
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Interes> interesesPage = interesRepo.findAll(paging);

        model.addAttribute("intereses", interesesPage.getContent());
        model.addAttribute("totalPages", interesesPage.getTotalPages());
        model.addAttribute("currentPage", interesesPage.getNumber());

        return "consulta-intereses";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Interes interes = interesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("interes", interes);
        return "form-interes";
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            interesRepo.deleteById(id);
            redirect.addFlashAttribute("mensaje", "🗑️ Interés eliminado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "⚠️ No se puede eliminar el interés porque está asignado a un estudiante.");
        }
        return "redirect:/intereses/lista";
    }
}

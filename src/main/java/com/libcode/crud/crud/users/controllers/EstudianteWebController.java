package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.entities.Estudiante;
import com.libcode.crud.crud.users.repository.CarreraRepository;
import com.libcode.crud.crud.users.repository.EstudianteRepository;
import com.libcode.crud.crud.users.repository.InteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteWebController {

    @Autowired
    private EstudianteRepository repo;

    @Autowired
    private CarreraRepository carreraRepo;

    @Autowired
    private InteresRepository interesRepo;  // ✅ nuevo

    // 🧑‍🎓 Mostrar formulario nuevo estudiante
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("carreras", carreraRepo.findAll());
        model.addAttribute("intereses", interesRepo.findAll()); // ✅ nuevo
        return "form-estudiante";
    }

    // 📋 Mostrar lista con paginación
    @GetMapping("/lista")
    public String mostrarLista(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Estudiante> pagina = repo.findAll(PageRequest.of(page, 5));
        model.addAttribute("pagina", pagina);
        model.addAttribute("estudiantes", pagina.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagina.getTotalPages());
        return "consulta-estudiantes";
    }

    // ✅ Guardar (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante, RedirectAttributes redirect) {
        boolean esNuevo = (estudiante.getId() == null);
        repo.save(estudiante);

        if (esNuevo) {
            redirect.addFlashAttribute("mensaje", "🎉 Estudiante registrado exitosamente.");
            return "redirect:/estudiantes/nuevo";
        }

        redirect.addFlashAttribute("mensaje", "✏️ Estudiante actualizado correctamente.");
        return "redirect:/estudiantes/lista";
    }

    // 🛑 Evitar error 405 si alguien entra por GET a /guardar
    @GetMapping("/guardar")
    public String redirigirGuardarGet() {
        return "redirect:/estudiantes/nuevo";
    }

    // ✏️ Editar estudiante
    @GetMapping("/editar/{id}")
    public String editarEstudiante(@PathVariable Long id, Model model) {
        Estudiante estudiante = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("carreras", carreraRepo.findAll());
        model.addAttribute("intereses", interesRepo.findAll()); // ✅ nuevo
        return "form-estudiante";
    }

    // 🗑️ Eliminar estudiante
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            repo.deleteById(id);
            redirect.addFlashAttribute("mensaje", "🗑️ Estudiante eliminado exitosamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "⚠️ No se puede eliminar el estudiante porque tiene historial registrado.");
        }
        return "redirect:/estudiantes/lista";
    }
}

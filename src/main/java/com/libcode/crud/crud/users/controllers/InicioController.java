package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.repository.EstudianteRepository;
import com.libcode.crud.crud.users.repository.CursoRepository;
import com.libcode.crud.crud.users.repository.HistorialAcademicoRepository;
import com.libcode.crud.crud.users.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private HistorialAcademicoRepository historialRepo;

    @Autowired
    private CarreraRepository carreraRepo;

    @GetMapping({"/", "/inicio"})
    public String inicio(Model model) {
        long totalEstudiantes = estudianteRepo.count();
        long totalCursos = cursoRepo.count();
        long totalHistoriales = historialRepo.count();
        long totalCarreras = carreraRepo.count();

        model.addAttribute("totalEstudiantes", totalEstudiantes);
        model.addAttribute("totalCursos", totalCursos);
        model.addAttribute("totalHistoriales", totalHistoriales);
        model.addAttribute("totalCarreras", totalCarreras);

        return "inicio"; // Archivo HTML ubicado en templates/inicio.html
    }
}

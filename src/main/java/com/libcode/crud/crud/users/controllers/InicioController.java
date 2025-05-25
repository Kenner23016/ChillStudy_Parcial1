package com.libcode.crud.crud.users.controllers;

import com.libcode.crud.crud.users.repository.EstudianteRepository;
import com.libcode.crud.crud.users.repository.CursoRepository;
import com.libcode.crud.crud.users.repository.HistorialAcademicoRepository;
import com.libcode.crud.crud.users.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

    @GetMapping("/")
    public String homePublica() {
        return "home";
    }

    @GetMapping("/inicio")
    public String inicio(@AuthenticationPrincipal OidcUser usuario, Model model) {
        if (usuario == null) {
            return "redirect:/";
        }

        // ✅ 1. Leer los roles desde el token JWT usando el namespace
        List<String> roles = usuario.getClaimAsStringList("https://chillstudy.com/roles");

        // ✅ 2. DEBUG: Mostrar los roles que vienen en el token
        System.out.println("🛡️ Roles desde el token:");
        if (roles != null) {
            roles.forEach(r -> System.out.println(" -> " + r));
        } else {
            System.out.println(" -> No se encontraron roles en el token.");
        }

        // ✅ 3. DEBUG: Mostrar los authorities que Spring Security está reconociendo
        System.out.println("🔐 Authorities reconocidos por Spring:");
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            System.out.println(" -> " + authority.getAuthority());
        }

        // Variables para la vista
        model.addAttribute("roles", roles);
        model.addAttribute("nombreUsuario", usuario.getFullName());
        model.addAttribute("totalEstudiantes", estudianteRepo.count());
        model.addAttribute("totalCursos", cursoRepo.count());
        model.addAttribute("totalHistoriales", historialRepo.count());
        model.addAttribute("totalCarreras", carreraRepo.count());

        return "inicio";
    }
}

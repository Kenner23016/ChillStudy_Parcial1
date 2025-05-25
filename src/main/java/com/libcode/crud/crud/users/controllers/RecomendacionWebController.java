package com.libcode.crud.crud.users.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecomendacionWebController {

    @GetMapping("/recomendaciones")
    public String mostrarRecomendaciones() {
        return "recomendaciones";
    }
}

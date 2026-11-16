package br.com.fiap.energia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> status() {
        return Map.of(
                "api", "Energia API",
                "status", "online",
                "recursos", new String[]{"/unidades", "/leituras"});
    }
}

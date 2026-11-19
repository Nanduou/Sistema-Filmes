package com.fametro.filmes.controller;

import com.fametro.filmes.model.Diretor;
import com.fametro.filmes.repository.DiretorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class DiretorController {

    private final DiretorRepository diretorRepository;

    public DiretorController(DiretorRepository diretorRepository) {
        this.diretorRepository = diretorRepository;
    }

    @GetMapping("/diretores")
    public String listar(Model model) {
        model.addAttribute("diretores", diretorRepository.findAll());
        return "diretores";
    }

    @GetMapping("/diretores/novo")
    public String novo(Model model) {
        model.addAttribute("diretor", new Diretor());
        return "form-diretor";
    }

    @GetMapping("/diretores/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Diretor diretor = diretorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("diretor", diretor);
        return "form-diretor";
    }

    @PostMapping("/diretores/salvar")
    public String salvar(@ModelAttribute Diretor diretor) {
        diretorRepository.save(diretor);
        return "redirect:/diretores";
    }

    @GetMapping("/diretores/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        diretorRepository.deleteById(id);
        return "redirect:/diretores";
    }
}

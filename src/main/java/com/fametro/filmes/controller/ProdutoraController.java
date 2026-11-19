package com.fametro.filmes.controller;

import com.fametro.filmes.model.Produtora;
import com.fametro.filmes.repository.ProdutoraRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class ProdutoraController {

    private final ProdutoraRepository produtoraRepository;

    public ProdutoraController(ProdutoraRepository produtoraRepository) {
        this.produtoraRepository = produtoraRepository;
    }

    @GetMapping("/produtoras")
    public String listar(Model model) {
        model.addAttribute("produtoras", produtoraRepository.findAll());
        return "produtoras";
    }

    @GetMapping("/produtoras/nova")
    public String nova(Model model) {
        model.addAttribute("produtora", new Produtora());
        return "form-produtora";
    }

    @GetMapping("/produtoras/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produtora produtora = produtoraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("produtora", produtora);
        return "form-produtora";
    }

    @PostMapping("/produtoras/salvar")
    public String salvar(@ModelAttribute Produtora produtora) {
        produtoraRepository.save(produtora);
        return "redirect:/produtoras";
    }

    @GetMapping("/produtoras/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoraRepository.deleteById(id);
        return "redirect:/produtoras";
    }
}

package com.fametro.filmes.controller;

import com.fametro.filmes.model.Categoria;
import com.fametro.filmes.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias";
    }

    @GetMapping("/categorias/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "form-categoria";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("categoria", categoria);
        return "form-categoria";
    }

    @PostMapping("/categorias/salvar")
    public String salvar(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}

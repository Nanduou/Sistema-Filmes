package com.fametro.filmes.controller;

import com.fametro.filmes.model.Filme;
import com.fametro.filmes.repository.FilmeRepository;
import com.fametro.filmes.repository.ProdutoraRepository;
import com.fametro.filmes.repository.DiretorRepository;
import com.fametro.filmes.repository.CategoriaRepository;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class FilmeController {

    private final FilmeRepository filmeRepository;
    private final ProdutoraRepository produtoraRepository;
    private final DiretorRepository diretorRepository;
    private final CategoriaRepository categoriaRepository;

    public FilmeController(FilmeRepository filmeRepository,
            ProdutoraRepository produtoraRepository,
            DiretorRepository diretorRepository,
            CategoriaRepository categoriaRepository) {
        this.filmeRepository = filmeRepository;
        this.produtoraRepository = produtoraRepository;
        this.diretorRepository = diretorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/filmes")
    public String listar(@RequestParam(required = false) String busca, Model model) {
        var filmes = new ArrayList<Filme>();
        filmeRepository.findAll().forEach(filmes::add);
        if (busca != null && !busca.isBlank()) {
            String termo = busca.toLowerCase(Locale.ROOT);
            filmes.removeIf(f -> !f.getTitulo().toLowerCase(Locale.ROOT).contains(termo)
                    && !f.getDiretor().getNome().toLowerCase(Locale.ROOT).contains(termo)
                    && !f.getCategoria().getNome().toLowerCase(Locale.ROOT).contains(termo)
                    && !f.getProdutora().getNome().toLowerCase(Locale.ROOT).contains(termo));
        }
        model.addAttribute("filmes", filmes);
        model.addAttribute("busca", busca);
        return "filmes";
    }

    @GetMapping("/filmes/novo")
    public String novo(Model model) {
        model.addAttribute("filme", new Filme());
        carregarOpcoes(model);
        return "form-filme";
    }

    @GetMapping("/filmes/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("filme", filme);
        carregarOpcoes(model);
        return "form-filme";
    }

    @PostMapping("/filmes/salvar")
    public String salvar(@ModelAttribute Filme filme) {
        filmeRepository.save(filme);
        return "redirect:/filmes";
    }

    @GetMapping("/filmes/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        filmeRepository.deleteById(id);
        return "redirect:/filmes";
    }

    private void carregarOpcoes(Model model) {
        model.addAttribute("produtoras", produtoraRepository.findAll());
        model.addAttribute("diretores", diretorRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
    }
}

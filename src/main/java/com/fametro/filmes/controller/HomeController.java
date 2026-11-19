package com.fametro.filmes.controller;

import com.fametro.filmes.repository.CategoriaRepository;
import com.fametro.filmes.repository.DiretorRepository;
import com.fametro.filmes.repository.FilmeRepository;
import com.fametro.filmes.repository.ProdutoraRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final FilmeRepository filmeRepository;
    private final CategoriaRepository categoriaRepository;
    private final DiretorRepository diretorRepository;
    private final ProdutoraRepository produtoraRepository;

    public HomeController(FilmeRepository filmeRepository,
                          CategoriaRepository categoriaRepository,
                          DiretorRepository diretorRepository,
                          ProdutoraRepository produtoraRepository) {
        this.filmeRepository = filmeRepository;
        this.categoriaRepository = categoriaRepository;
        this.diretorRepository = diretorRepository;
        this.produtoraRepository = produtoraRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        List<com.fametro.filmes.model.Filme> filmes = new ArrayList<>();
        filmeRepository.findAll().forEach(filmes::add);
        BigDecimal soma = filmes.stream()
                .map(com.fametro.filmes.model.Filme::getNota)
                .filter(nota -> nota != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long avaliados = filmes.stream().filter(f -> f.getNota() != null).count();
        BigDecimal media = avaliados == 0 ? BigDecimal.ZERO
                : soma.divide(BigDecimal.valueOf(avaliados), 1, java.math.RoundingMode.HALF_UP);

        model.addAttribute("totalFilmes", filmeRepository.count());
        model.addAttribute("totalCategorias", categoriaRepository.count());
        model.addAttribute("totalDiretores", diretorRepository.count());
        model.addAttribute("totalProdutoras", produtoraRepository.count());
        model.addAttribute("mediaNotas", media);
        model.addAttribute("ultimosFilmes", filmes.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5).toList());
        return "index";
    }
}
